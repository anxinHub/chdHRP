package com.chd.hrp.mat.controller.purchase.check;

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
import com.chd.hrp.mat.service.purchase.check.MatPurMainCheckService;

/**
 * 
 * @Description:
 * 04113 采购计划审核
 * @Table:
 * MAT_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatPurMainCheckController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPurMainCheckController.class);
	
	@Resource(name = "matPurMainCheckService")
	private final MatPurMainCheckService matPurMainCheckService = null;
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/check/matPurMainCheckMainPage", method = RequestMethod.GET)
	public String matPurMainCheckMainPage(Model mode) throws Exception {

		return "hrp/mat/purchase/check/matPurMainCheckMain";

	}
	
	/**
	 * @Description 
	 * 采购计划审核 查询采购计划
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/check/queryMatPurMainCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurMainCheckMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
		String matPurMainCheckJson = matPurMainCheckService.queryMatPurMain(getPage(mapVo));
		
		return JSONObject.parseObject(matPurMainCheckJson);

	}
	
	/**
	 * @Description 
	 * 采购计划 审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/check/checkMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
		String matPurMainCheckJson = matPurMainCheckService.checkMatPurMain(listVo);
		
		return JSONObject.parseObject(matPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 采购计划 取消审核
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/check/cancelCheckMatPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelCheckMatPurMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
		String matPurMainCheckJson = matPurMainCheckService.cancelCheckMatPurMain(listVo);
		
		return JSONObject.parseObject(matPurMainCheckJson);
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//审核明细页面跳转
	@RequestMapping(value = "/hrp/mat/purchase/check/matPurMainCheckDetailPage", method = RequestMethod.GET)
	public String updateMatPurMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		Map<String,Object> matPurMainMap = matPurMainCheckService.queryMatPurMainByCode(mapVo);
		
		mode.addAttribute("group_id", matPurMainMap.get("group_id"));
		mode.addAttribute("hos_id", matPurMainMap.get("hos_id"));
		mode.addAttribute("copy_code",matPurMainMap.get("copy_code"));
		mode.addAttribute("pur_id", matPurMainMap.get("pur_id"));
		mode.addAttribute("pur_code", matPurMainMap.get("pur_code"));
		mode.addAttribute("dept_id", matPurMainMap.get("dept_id"));
		mode.addAttribute("dept_no",matPurMainMap.get("dept_no"));
		mode.addAttribute("dept_code",matPurMainMap.get("dept_code"));
		mode.addAttribute("dept_name", matPurMainMap.get("dept_name"));
		mode.addAttribute("make_date", matPurMainMap.get("make_date"));
		mode.addAttribute("arrive_date", matPurMainMap.get("arrive_date"));
		mode.addAttribute("pur_type", matPurMainMap.get("pur_type"));
		mode.addAttribute("brif", matPurMainMap.get("brif"));
		
		mode.addAttribute("pur_hos_id",matPurMainMap.get("pur_hos_id"));
		mode.addAttribute("pur_hos_no",matPurMainMap.get("pur_hos_no"));
		mode.addAttribute("pur_hos_code",matPurMainMap.get("pur_hos_code"));
		mode.addAttribute("pur_hos_name", matPurMainMap.get("pur_hos_name"));
		
		mode.addAttribute("req_hos_id", matPurMainMap.get("req_hos_id"));
		mode.addAttribute("req_hos_no", matPurMainMap.get("req_hos_no"));
		mode.addAttribute("req_hos_code", matPurMainMap.get("req_hos_code"));
		mode.addAttribute("req_hos_name", matPurMainMap.get("req_hos_name"));
		
		mode.addAttribute("pay_hos_id", matPurMainMap.get("pay_hos_id"));
		mode.addAttribute("pay_hos_no", matPurMainMap.get("pay_hos_no"));
		mode.addAttribute("pay_hos_code", matPurMainMap.get("pay_hos_code"));
		mode.addAttribute("pay_hos_name", matPurMainMap.get("pay_hos_name"));
		
		mode.addAttribute("is_dir", matPurMainMap.get("is_dir"));
		mode.addAttribute("dir_dept_id", matPurMainMap.get("dir_dept_id"));
		mode.addAttribute("dir_dept_no", matPurMainMap.get("dir_dept_no"));
		mode.addAttribute("dir_dept_code", matPurMainMap.get("dir_dept_code"));
		mode.addAttribute("dir_dept_name", matPurMainMap.get("dir_dept_name"));
		
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		return "hrp/mat/purchase/check/matPurMainCheckDetail";

	}
	
	/**
	 * @Description 
	 * 采购计划审核 按主表ID查询采购计划明细
	 * @param  mapVo
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/check/queryMatPurMainDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatPurDetail(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matPurDetailJson = matPurMainCheckService.queryMatPurMainDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matPurDetailJson);

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/purchase/check/purMainCheckPrintSetPage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/mat/purchase/check/queryMatCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=matPurMainCheckService.queryMatCheckByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
}
