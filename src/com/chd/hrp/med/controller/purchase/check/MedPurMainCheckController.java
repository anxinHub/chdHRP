package com.chd.hrp.med.controller.purchase.check;

import java.text.SimpleDateFormat;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.purchase.check.MedPurMainCheckService;

/**
 * 
 * @Description:
 * 08113 采购计划审核
 * @Table:
 * MED_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedPurMainCheckController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPurMainCheckController.class);
	
	@Resource(name = "medPurMainCheckService")
	private final MedPurMainCheckService medPurMainCheckService = null;
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/med/purchase/check/medPurMainCheckMainPage", method = RequestMethod.GET)
	public String medPurMainCheckMainPage(Model mode) throws Exception {

		return "hrp/med/purchase/check/medPurMainCheckMain";

	}
	
	/**
	 * @Description 
	 * 采购计划审核 查询采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/queryMedPurMainCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurMainCheckMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String medPurMainCheckJson = medPurMainCheckService.queryMedPurMain(getPage(mapVo));
		
		return JSONObject.parseObject(medPurMainCheckJson);

	}
	
	/**
	 * @Description 
	 * 采购计划 审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/checkMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String paramVo = mapVo.get("paramVo").toString();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("pur_id", ids[3]);
				map.put("state", ids[4]);
				map.put("checker", SessionManager.getUserId());
				map.put("check_date", sdf.format(new Date()));
				listVo.add(map);
	    }
			
		String medPurMainCheckJson = medPurMainCheckService.checkMedPurMain(listVo);
		
		return JSONObject.parseObject(medPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 采购计划 取消审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/cancelCheckMedPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelCheckMedPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo = mapVo.get("paramVo").toString();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("pur_id", ids[3]);
				map.put("state", ids[4]);
				listVo.add(map);
	    }
			
		String medPurMainCheckJson = medPurMainCheckService.cancelCheckMedPurMain(listVo);
		
		return JSONObject.parseObject(medPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//审核明细页面跳转
	@RequestMapping(value = "/hrp/med/purchase/check/medPurMainCheckDetailPage", method = RequestMethod.GET)
	public String updateMedPurMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		Map<String,Object> medPurMainMap = medPurMainCheckService.queryMedPurMainByCode(mapVo);
		
		mode.addAttribute("group_id", medPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", medPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",medPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", medPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", medPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", medPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",medPurMainMap.get("dept_no"));
		mode.addAttribute("dept_code",medPurMainMap.get("dept_code"));
		mode.addAttribute("dept_name", medPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", medPurMainMap.get("make_date"));
		mode.addAttribute("arrive_date", medPurMainMap.get("arrive_date"));
		mode.addAttribute("pur_type", medPurMainMap.get("pur_type"));
		mode.addAttribute("brif", medPurMainMap.get("brif"));
		
		mode.addAttribute("pur_hos_id",medPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_no",medPurMainMap.get("pur_hos_no"));
		mode.addAttribute("pur_hos_code",medPurMainMap.get("pur_hos_code"));
		mode.addAttribute("pur_hos_name", medPurMainMap.get("pur_hos_name"));
		
		mode.addAttribute("req_hos_id", medPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_no", medPurMainMap.get("req_hos_no"));
		mode.addAttribute("req_hos_code", medPurMainMap.get("req_hos_code"));
		mode.addAttribute("req_hos_name", medPurMainMap.get("req_hos_name"));
		
		mode.addAttribute("pay_hos_id", medPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_no", medPurMainMap.get("pay_hos_no"));
		mode.addAttribute("pay_hos_code", medPurMainMap.get("pay_hos_code"));
		mode.addAttribute("pay_hos_name", medPurMainMap.get("pay_hos_name"));
		
		mode.addAttribute("is_dir", medPurMainMap.get("is_dir"));
		mode.addAttribute("dir_dept_id", medPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", medPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", medPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", medPurMainMap.get("dir_dept_name"));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		
		return "hrp/med/purchase/check/medPurMainCheckDetail";

	}
	
	/**
	 * @Description 
	 * 采购计划审核 按主表ID查询采购计划明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/queryMedPurMainDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medPurDetailJson = medPurMainCheckService.queryMedPurMainDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medPurDetailJson);

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/purMainCheckPrintSetPage", method = RequestMethod.GET)
	public String purMainCheckPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/purchase/check/queryMatCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=medPurMainCheckService.queryMedCheckByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
}
