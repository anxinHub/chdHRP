/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.collect;
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
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.collect.MatRequireCollectService;
/**
 * 
 * @Description:
 * 科室购置计划汇总
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatRequireCollectController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatRequireCollectController.class);
	
	//引入Service服务
	@Resource(name = "matRequireCollectService")
	private final MatRequireCollectService matRequireCollectService = null;
   
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	
	/**
	 * 科室需求计划汇总--主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/matDeptRequriedCollectMainPage", method = RequestMethod.GET)
	public String matDepartRequriedCollectMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collect/matDeptRequriedCollectMainPage";
	}
	
	/**
	 * 科室需求计划汇总--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/queryMatDeptRequriedCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String para_value = MyConfig.getSysPara("04031");
		
		String matRequireMainJson;
		
		if(para_value == "1" || "1".equals(para_value)){
			
			matRequireMainJson = matRequireCollectService.queryCollect(getPage(mapVo));
			
		}else{
			
			matRequireMainJson = matRequireCollectService.queryCollectNotCheck(getPage(mapVo));
		}
		

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	
	/**
	 * 科室需求计划汇总--计划单明细页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/matDeptCollectDetailPage", method = RequestMethod.GET)
	public String matDepartRequriedCollectDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collect/matDeptRequriedCollectDetailPage";

	}
	
	/**
	 * 科室需求计划汇总--汇总明细 计划单--查看明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/queryMatCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCollectDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireMainJson = matRequireCollectService.queryCollectDetail(mapVo);
		//System.out.println(JSONObject.parseObject(matRequireMainJson));
		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 计划单--查看明细 跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/queryMatDeptCollectDetailPlan", method = RequestMethod.GET)
	public String queryMatDeptCollectDetailPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		Map<String, Object> matDeptMain = matRequireCollectService.queryByCode(mapVo);
		
		
		mode.addAttribute("req_id", matDeptMain.get("req_id"));
		mode.addAttribute("req_code", matDeptMain.get("req_code"));
		
		mode.addAttribute("dept_id", matDeptMain.get("dept_id"));
		mode.addAttribute("dept_no", matDeptMain.get("dept_no"));
		mode.addAttribute("dept_code", matDeptMain.get("dept_code"));
		mode.addAttribute("dept_name", matDeptMain.get("dept_name"));
		
		mode.addAttribute("stock_id", matDeptMain.get("stock_id"));
		mode.addAttribute("stock_no", matDeptMain.get("stock_no"));
		mode.addAttribute("store_code", matDeptMain.get("store_code"));
		mode.addAttribute("store_name", matDeptMain.get("store_name") );
		
		mode.addAttribute("make_date", matDeptMain.get("make_date"));
		mode.addAttribute("other_inv", matDeptMain.get("other_inv"));
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collect/matDeptRequriedCollectDetailPlan";
		
	}
	/**
	 * 汇总页面查看计划单页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/queryMatDeptCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptCollectDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String matRequireMain = matRequireCollectService.queryConfirmDetail(getPage(mapVo));

		return JSONObject.parseObject(matRequireMain);
		
	}
	
	
	/**
	 * 保存&提交页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/matDeptRequriedCollectSaveAndSub", method = RequestMethod.GET)
	public String matDeptRequriedCollectSaveAndSub(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/mat/require/collect/matDeptRequriedCollectSaveAndSub";
	}
	
	/**
	 * 汇总保存&提交
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/saveMatDeptRequriedCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatDeptRequriedCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
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
		
		String matRequireMainJson = matRequireCollectService.addCollectNotDir(mapVo);
		
		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 科室需求计划汇总--定向汇总页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/matDeptRequriedDirCollectMainPage", method = RequestMethod.GET)
	public String matDeptRequriedDirCollectMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collect/matDeptRequriedDirCollectMain";
	}
	
	/**
	 * 科室需求计划汇总--定向汇总页面查询
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/queryMatDeptRequriedDirCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedDirCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String para_value = MyConfig.getSysPara("04031");
		
		String matRequireMain; 
		
		if(para_value == "1" || "1".equals(para_value)){
			
			matRequireMain = matRequireCollectService.queryDirCollect(mapVo);
			
		}else{
			
			matRequireMain = matRequireCollectService.queryDirCollectNotCheck(mapVo);
		}
		

		return JSONObject.parseObject(matRequireMain);
		
	}
	
	/**
	 * 定向汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collect/saveMatDeptRequriedDirCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatDeptRequriedDirCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
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
		String matRequireMainJson = matRequireCollectService.addCollect(mapVo);
		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
		
	
}

