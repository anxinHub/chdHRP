package com.chd.hrp.ass.controller.check.inassets;

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
import com.chd.hrp.ass.entity.check.inassets.AssChkAdetailInassets;
import com.chd.hrp.ass.entity.check.inassets.AssChkAinassets;
import com.chd.hrp.ass.service.check.inassets.AssChkAdetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkAinassetsService;

/**
 * 
 * @Description: 051001资产盘亏申报(无形资产)
 * @Table: ASS_CHK_A_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkAinassetsController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkAinassetsController.class);

	// 引入Service服务
	@Resource(name = "assChkAinassetsService")
	private final AssChkAinassetsService assChkAinassetsService = null;

	@Resource(name = "assChkAdetailInassetsService")
	private final AssChkAdetailInassetsService assChkAdetailInassetsService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */						
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/assChkApplyInassetsMainPage", method = RequestMethod.GET)
	public String assBackInassetsMainPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/asscheck/chkapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/assChkApplyInassetsAddPage", method = RequestMethod.GET)
	public String assBackInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asscheck/chkapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产盘亏申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/saveAssChkApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkAinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkAinassets assChkAinassets = assChkAinassetsService.queryByCode(mapVo);
		if(assChkAinassets != null){
			if(assChkAinassets.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkAinassetsJson = assChkAinassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkAinassetsJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/assChkApplyInassetsUpdatePage", method = RequestMethod.GET)
	public String assChkAinassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkAinassets assChkAinassets = new AssChkAinassets();

		assChkAinassets = assChkAinassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAinassets.getGroup_id());
		mode.addAttribute("hos_id", assChkAinassets.getHos_id());
		mode.addAttribute("copy_code", assChkAinassets.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAinassets.getAss_chk_no());
		mode.addAttribute("note", assChkAinassets.getNote());
		mode.addAttribute("create_emp", assChkAinassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAinassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAinassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAinassets.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAinassets.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAinassets.getAudit_emp_name());
		mode.addAttribute("state", assChkAinassets.getState());
		mode.addAttribute("state_name", assChkAinassets.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asscheck/chkapply/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/deleteAssChkApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_chk_no", ids[3]);
			
			AssChkAinassets assChkAinassets = assChkAinassetsService.queryByCode(mapVo);
			if(assChkAinassets != null){
				if(assChkAinassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChkAinassetsJson = assChkAinassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkAinassetsJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/queryAssChkApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackInassets = assChkAinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackInassets);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/queryAssChkApplyDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("ass_chk_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("ass_chk_no", ids);
		String assBackDetailInassets = assChkAdetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/deleteAssChkApplyDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAdetailInassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_chk_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssChkAinassets assChkAinassets = assChkAinassetsService.queryByCode(mapVo);
				if(assChkAinassets != null){
					if(assChkAinassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkAdetailInassetsJson = assChkAdetailInassetsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkAdetailInassetsJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkapply/updateConfirmChkApplyInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkAinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_chk_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssChkAinassets assChkAinassets = assChkAinassetsService.queryByCode(mapVo);
			if(assChkAinassets == null || assChkAinassets.getState() == 2){
				continue;
			}
			
			List<AssChkAdetailInassets> detailList = assChkAdetailInassetsService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkAdetailInassets detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", 12);
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已盘亏的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作盘亏确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkAinassetsJson = assChkAinassetsService.updateConfirmChkAinassets(listVo,listCardVo);

		return JSONObject.parseObject(assChkAinassetsJson);

	}
	
}
