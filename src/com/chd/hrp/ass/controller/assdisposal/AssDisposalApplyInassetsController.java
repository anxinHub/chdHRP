package com.chd.hrp.ass.controller.assdisposal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.assdisposal.inassets.AssDisposalApplyDetailInassets;
import com.chd.hrp.ass.entity.assdisposal.inassets.AssDisposalApplyInassets;
import com.chd.hrp.ass.service.assdisposal.inassets.AssDisposalApplyDetailInassetsService;
import com.chd.hrp.ass.service.assdisposal.inassets.AssDisposalApplyInassetsService;

/**
 * 
 * @Description: 051001资产处置申报(无形资产)
 * @Table: ASS_DISPOSAL_A_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalApplyInassetsController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDisposalApplyInassetsController.class);

	// 引入Service服务
	@Resource(name = "assDisposalApplyInassetsService")
	private final AssDisposalApplyInassetsService assDisposalApplyInassetsService = null;

	@Resource(name = "assDisposalApplyDetailInassetsService")
	private final AssDisposalApplyDetailInassetsService assDisposalApplyDetailInassetsService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/assDisposalApplyInassetsMainPage", method = RequestMethod.GET)
	public String assBackInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/assDisposalApplyInassetsAddPage", method = RequestMethod.GET)
	public String assBackInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assdisposal/disposalapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/saveAssDisposalApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplyInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		AssDisposalApplyInassets assDisposalApplyInassets = assDisposalApplyInassetsService.queryByCode(mapVo);
		if(assDisposalApplyInassets != null){
			if(assDisposalApplyInassets.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplyInassetsJson = assDisposalApplyInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplyInassetsJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/assDisposalApplyInassetsUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplyInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyInassets assDisposalApplyInassets = new AssDisposalApplyInassets();

		assDisposalApplyInassets = assDisposalApplyInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyInassets.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyInassets.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyInassets.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyInassets.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyInassets.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyInassets.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyInassets.getNote());
		mode.addAttribute("create_emp", assDisposalApplyInassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyInassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyInassets.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyInassets.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyInassets.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyInassets.getState());
		mode.addAttribute("state_name", assDisposalApplyInassets.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/deleteAssDisposalApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_a_no", ids[3]);
			
			AssDisposalApplyInassets assDisposalApplyInassets = assDisposalApplyInassetsService.queryByCode(mapVo);
			if(assDisposalApplyInassets != null){
				if(assDisposalApplyInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplyInassetsJson = assDisposalApplyInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplyInassetsJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/queryAssDisposalApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplyInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackInassets = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackInassets = assDisposalApplyInassetsService.query(getPage(mapVo));
			 
		}else{

			assBackInassets = assDisposalApplyInassetsService.queryDetails(getPage(mapVo));
			 
		}


		return JSONObject.parseObject(assBackInassets);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/queryAssDisposalApplyDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("dis_a_no").toString();
		String car[] = carno.split(",");
		final List<String> ids = new ArrayList<String>();
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("dis_a_no", ids);
		String assBackDetailInassets = assDisposalApplyDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/deleteAssDisposalApplyDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailInassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("dis_a_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssDisposalApplyInassets assDisposalApplyInassets = assDisposalApplyInassetsService.queryByCode(mapVo);
				if(assDisposalApplyInassets != null){
					if(assDisposalApplyInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailInassetsJson = assDisposalApplyDetailInassetsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailInassetsJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assdisposal/disposalapply/updateConfirmDisposalApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplyInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_a_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssDisposalApplyInassets assDisposalApplyInassets = assDisposalApplyInassetsService.queryByCode(mapVo);
			if(assDisposalApplyInassets == null || assDisposalApplyInassets.getState() == 2){
				continue;
			}
			
			List<AssDisposalApplyDetailInassets> detailList = assDisposalApplyDetailInassetsService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailInassets detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyInassets.getDispose_type());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assDisposalApplyInassetsJson = assDisposalApplyInassetsService.updateConfirmDisposalApplyInassets(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyInassetsJson);

	}
	
}
