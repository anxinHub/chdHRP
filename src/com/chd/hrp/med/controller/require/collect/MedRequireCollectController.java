/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.require.collect;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.collect.MedRequireCollectService;
/**
 * 
 * @Description:
 * 科室购置计划汇总
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedRequireCollectController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedRequireCollectController.class);
	
	//引入Service服务
	@Resource(name = "medRequireCollectService")
	private final MedRequireCollectService medRequireCollectService = null;
   
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	/**
	 * 科室需求计划汇总--主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/medDeptRequriedCollectMainPage", method = RequestMethod.GET)
	public String medDepartRequriedCollectMainPage(Model mode) throws Exception {
		
		Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collect/medDeptRequriedCollectMainPage";
	}
	
	/**
	 * 科室需求计划汇总--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/queryMedDeptRequriedCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequriedCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		//科室需求是否使用审核操作
		String para_value = MyConfig.getSysPara("08031");
		
		String medRequireMainJson;
		
		if(para_value == "1" || "1".equals(para_value)){
			
			medRequireMainJson = medRequireCollectService.queryCollect(getPage(mapVo));
			
		}else{
			
			medRequireMainJson = medRequireCollectService.queryCollectNotCheck(getPage(mapVo));
		}
		

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	
	/**
	 * 科室需求计划汇总--计划单明细页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/medDeptCollectDetailPage", method = RequestMethod.GET)
	public String medDepartRequriedCollectDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("inv_code", mapVo.get("inv_code"));
		mode.addAttribute("inv_name", mapVo.get("inv_name"));
		
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("inv_no", mapVo.get("inv_no"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("begin_date", mapVo.get("begin_date"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		
		Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/require/collect/medDeptRequriedCollectDetailPage";

	}
	
	/**
	 * 科室需求计划汇总--汇总明细 计划单--查看明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/queryMedCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCollectDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireMainJson = medRequireCollectService.queryCollectDetail(mapVo);
		System.out.println(JSONObject.parseObject(medRequireMainJson));
		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 计划单--查看明细 跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/queryMedDeptCollectDetailPlan", method = RequestMethod.GET)
	public String queryMedDeptCollectDetailPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
		
		Map<String, Object> medDeptMain = medRequireCollectService.queryByCode(mapVo);
		
		
		mode.addAttribute("req_id", medDeptMain.get("req_id"));
		mode.addAttribute("req_code", medDeptMain.get("req_code"));
		
		mode.addAttribute("dept_id", medDeptMain.get("dept_id"));
		mode.addAttribute("dept_no", medDeptMain.get("dept_no"));
		mode.addAttribute("dept_code", medDeptMain.get("dept_code"));
		mode.addAttribute("dept_name", medDeptMain.get("dept_name"));
		
		mode.addAttribute("stock_id", medDeptMain.get("stock_id"));
		mode.addAttribute("stock_no", medDeptMain.get("stock_no"));
		mode.addAttribute("store_code", medDeptMain.get("store_code"));
		mode.addAttribute("store_name", medDeptMain.get("store_name") );
		
		mode.addAttribute("make_date", medDeptMain.get("make_date"));
		mode.addAttribute("other_inv", medDeptMain.get("other_inv"));
		mode.addAttribute("state", mapVo.get("state"));
		
		Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collect/medDeptRequriedCollectDetailPlan";
		
	}
	/**
	 * 汇总页面查看计划单页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/queryMedDeptCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptCollectDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
		
		String medRequireMain = medRequireCollectService.queryConfirmDetail(getPage(mapVo));

		return JSONObject.parseObject(medRequireMain);
		
	}
	
	
	/**
	 * 保存&提交页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/medDeptRequriedCollectSaveAndSub", method = RequestMethod.GET)
	public String medDeptRequriedCollectSaveAndSub(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/med/require/collect/medDeptRequriedCollectSaveAndSub";
	}
	
	/**
	 * 汇总保存&提交
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/saveMedDeptRequriedCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedDeptRequriedCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String medRequireMainJson = medRequireCollectService.addCollectNotDir(mapVo);
		
		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 科室需求计划汇总--定向汇总页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/medDeptRequriedDirCollectMainPage", method = RequestMethod.GET)
	public String medDeptRequriedDirCollectMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		//Map<String, Object> map = medCommonService.queryMedParas();
		//mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collect/medDeptRequriedDirCollectMain";
	}
	
	/**
	 * 科室需求计划汇总--定向汇总页面查询
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/queryMedDeptRequriedDirCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequriedDirCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
		
		//科室需求是否使用审核操作
		String para_value = MyConfig.getSysPara("08031");
		
		String medRequireMain; 
		
		if(para_value == "1" || "1".equals(para_value)){
			
			medRequireMain = medRequireCollectService.queryDirCollect(mapVo);
			
		}else{
			
			medRequireMain = medRequireCollectService.queryDirCollectNotCheck(mapVo);
		}
		

		return JSONObject.parseObject(medRequireMain);
		
	}
	
	/**
	 * 定向汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collect/saveMedDeptRequriedDirCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedDeptRequriedDirCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		mapVo.put("is_dir", "1");
		String medRequireMainJson = medRequireCollectService.addCollect(mapVo);
		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
		
	
}

