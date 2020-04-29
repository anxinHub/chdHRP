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
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalApplyDetailLand;
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalApplyLand;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalApplyDetailLandService;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalApplyLandService;

/**
 * 
 * @Description: 051001资产处置申报(土地)
 * @Table: ASS_DISPOSAL_A_LAND
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalApplyLandController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDisposalApplyLandController.class);

	// 引入Service服务
	@Resource(name = "assDisposalApplyLandService")
	private final AssDisposalApplyLandService assDisposalApplyLandService = null;

	@Resource(name = "assDisposalApplyDetailLandService")
	private final AssDisposalApplyDetailLandService assDisposalApplyDetailLandService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/assDisposalApplyLandMainPage", method = RequestMethod.GET)
	public String assBackLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/assDisposalApplyLandAddPage", method = RequestMethod.GET)
	public String assBackLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdisposal/disposalapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/saveAssDisposalApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplyLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalApplyLand assDisposalApplyLand = assDisposalApplyLandService.queryByCode(mapVo);
		if(assDisposalApplyLand != null){
			if(assDisposalApplyLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplyLandJson = assDisposalApplyLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplyLandJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/assDisposalApplyLandUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplyLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyLand assDisposalApplyLand = new AssDisposalApplyLand();

		assDisposalApplyLand = assDisposalApplyLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyLand.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyLand.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyLand.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyLand.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyLand.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyLand.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyLand.getNote());
		mode.addAttribute("create_emp", assDisposalApplyLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyLand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyLand.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyLand.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyLand.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyLand.getState());
		mode.addAttribute("state_name", assDisposalApplyLand.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/deleteAssDisposalApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyLand assDisposalApplyLand = assDisposalApplyLandService.queryByCode(mapVo);
			if(assDisposalApplyLand != null){
				if(assDisposalApplyLand.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplyLandJson = assDisposalApplyLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplyLandJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/queryAssDisposalApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplyLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackLand = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackLand = assDisposalApplyLandService.query(getPage(mapVo));
			 
		}else{

			assBackLand = assDisposalApplyLandService.queryDetails(getPage(mapVo));
			 
		}


		return JSONObject.parseObject(assBackLand);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/queryAssDisposalApplyDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailLand = assDisposalApplyDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/deleteAssDisposalApplyDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalApplyLand assDisposalApplyLand = assDisposalApplyLandService.queryByCode(mapVo);
				if(assDisposalApplyLand != null){
					if(assDisposalApplyLand.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailLandJson = assDisposalApplyDetailLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailLandJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalapply/updateConfirmDisposalApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplyLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyLand assDisposalApplyLand = assDisposalApplyLandService.queryByCode(mapVo);
			if(assDisposalApplyLand == null || assDisposalApplyLand.getState() == 2){
				continue;
			}
			
			List<AssDisposalApplyDetailLand> detailList = assDisposalApplyDetailLandService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailLand detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyLand.getDispose_type());
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

		String assDisposalApplyLandJson = assDisposalApplyLandService.updateConfirmDisposalApplyLand(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyLandJson);

	}
	
}
