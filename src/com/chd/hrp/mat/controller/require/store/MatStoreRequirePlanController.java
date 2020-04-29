/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.store;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.store.MatStoreRequirePlanService;
/**
 * 
 * @Description:
 * 仓库需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatStoreRequirePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStoreRequirePlanController.class);
	
	//引入Service服务
	@Resource(name = "matStoreRequirePlanService")
	private final MatStoreRequirePlanService matStoreRequirePlanService = null;
	
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	/**
	 * @Description 
	 * 仓库需求计划编制--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreRequriedPlanMainPage", method = RequestMethod.GET)
	public String matStoreRequriedPlanMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanMain";
	}
	
	/**
	 * 仓库需求计划 --查询页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matRequireMainJson = matStoreRequirePlanService.queryStorePlan(getPage(mapVo));

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	/**
	 * @Description 
	 * 仓库需求计划编制--添加
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreRequriedPlanAddPage", method = RequestMethod.GET)
	public String matStoreRequriedPlanAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanAdd";
	}
	
	/**
	 * 配套导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreRequriedPlanSupportImp", method = RequestMethod.GET)
	public String matDepartRequridPlanSupportImp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		//System.out.println("=============="+mapVo);
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanSupportImp";

	}
	
	/**
	 * 配套表导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryMatStoreSupport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreSupport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson = matStoreRequirePlanService.queryStoreSupport(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--保存
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/addMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(mapVo.get("make_date") == null || "".equals(mapVo.get("make_date"))){
			return JSONObject.parseObject("{\"error\":\"编制日期不能为空\",\"state\":\"false\"}");
		}
		
		//截取期间
		String[] date = mapVo.get("make_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		mapVo.put("day", date[2]);
		
		String matRequireMainJson;
		
		try {
			matRequireMainJson = matStoreRequirePlanService.add(mapVo);
		} catch (Exception e) {
			matRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--更新页面跳转
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreRequriedPlanUpdatePage", method = RequestMethod.GET)
	public String matStoreRequriedPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matDeptMain = matStoreRequirePlanService.queryByCode(mapVo);
		
		mode.addAttribute("matDeptMain", matDeptMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanUpdate";
	}
	
	/**
	 * 仓库需求计划编制--查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryMatStoreRequriedDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreRequriedDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	    
		String matRequireMainJson = matStoreRequirePlanService.queryStoreDetail(mapVo);
		return JSONObject.parseObject(matRequireMainJson); 
		
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--更新
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/updateMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson;
		try {
			matRequireMainJson = matStoreRequirePlanService.update(mapVo);
		} catch (Exception e) {
			matRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/store/plan/deleteMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("req_id", ids[3]);
				listVo.add(mapVo);
			}
			
		String matRequireMainJson;
		try {
			matRequireMainJson = matStoreRequirePlanService.deleteBatch(listVo);
		} catch (Exception e) {
			matRequireMainJson = e.getMessage();
		}
	  return JSONObject.parseObject(matRequireMainJson);
			
	}
	
	//科室需求生成页面跳转
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreRequriedPlanDeptImpPage", method = RequestMethod.GET)
	public String matStoreRequriedPlanDeptImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanDeptImp";
	}
	
	//科室需求计划中仓库列表
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryStoreByDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreByDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String hrpMatSelect = matStoreRequirePlanService.queryStoreByDept(getPage(mapVo));
		return hrpMatSelect;
	}
	
	//仓库与材料对应关系中申领仓库
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryStoreByInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreByInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String hrpMatSelect = matStoreRequirePlanService.queryStoreByInv(getPage(mapVo));
		return hrpMatSelect;
	}
	/**
	 * 科室需求计划生成 -- 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryMatStoreByDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreByDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matRequireMainJson = matStoreRequirePlanService.queryDeptGDetail(getPage(mapVo));

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	//组装明细数据
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryDeptCollectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptCollectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matStoreRequirePlanService.collectDeptData(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 汇总科室需求计划单据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/store/plan/matStoreReqPlanByDeptAddPage", method = RequestMethod.GET)
	public String matStoreReqPlanByDeptAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		/*mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_text", mapVo.get("dept_text"));*/
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/store/plan/matStoreRequriedPlanByDeptAdd";
		
	}
	
	
	//中止
	@RequestMapping(value = "/hrp/mat/require/store/plan/abortMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMatStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            listVo.add(mapVo);
        }
		
		String matRequireMainJson = matStoreRequirePlanService.abortMatStoreRequriedPlan(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	//提交
	@RequestMapping(value = "/hrp/mat/require/store/plan/submitMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMatStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            listVo.add(mapVo);
        }
		
		String matRequireMainJson = matStoreRequirePlanService.updateSubmitMatStorePlan(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	//取消提交
	@RequestMapping(value = "/hrp/mat/require/store/plan/unSubmitMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMatStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            listVo.add(mapVo);
        }
		
		String matRequireMainJson = matStoreRequirePlanService.updateUnSubmitMatStorePlan(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	//审核
	@RequestMapping(value = "/hrp/mat/require/store/plan/submitMatStoreRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMatStoreRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            mapVo.put("checker", SessionManager.getUserId());
            
            mapVo.put("check_date", sdf.format(new Date()));
            
            listVo.add(mapVo);
        }
		String matRequireMainJson = matStoreRequirePlanService.submitMatStoreRequriedConfirm(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	//取消审核
	@RequestMapping(value = "/hrp/mat/require/store/plan/unSubmitMatStoreRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMatStoreRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("req_code", id.split("@")[1]);//实际实体类变量
            mapVo.put("copy_code", SessionManager.getCopyCode());//实际实体类变量
            mapVo.put("hos_id", SessionManager.getHosId());//实际实体类变量
            mapVo.put("group_id", SessionManager.getGroupId());//实际实体类变量
            mapVo.put("acc_year", SessionManager.getAcctYear());//实际实体类变量
            listVo.add(mapVo);
        }
		
		String matRequireMainJson = matStoreRequirePlanService.unSubmitMatStoreRequriedConfirm(listVo);	
		return JSONObject.parseObject(matRequireMainJson);
	}
	//复制
	@RequestMapping(value = "/hrp/mat/require/store/plan/copyMatStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMatStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		Date date  = new Date();
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("copy_code", id.split("@")[2]);
			mapVo.put("req_id", id.split("@")[3]);
            mapVo.put("req_code", id.split("@")[4]);
            mapVo.put("maker", SessionManager.getUserId());
            
    		String new_date = DateUtil.dateToString(date).toString();
    		new_date = new_date.substring(0, 10);
    		mapVo.put("make_date", new_date);
            //截取期间
    		String[] date1 = new_date.substring(0, 10).toString().split("-");
    		mapVo.put("year", date1[0]);
    		mapVo.put("month", date1[1]);
    		mapVo.put("r_day", date1[2]);
            listVo.add(mapVo);
        }
		
		String matRequireMainJson;
		try {
			matRequireMainJson = matStoreRequirePlanService.copyMatStorePlan(listVo);
		} catch (Exception e) {
			matRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	
	@RequestMapping(value = "/hrp/mat/require/store/plan/queryMatStoreSupportData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreSupportData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		} 
					
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
					
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
					
		String detailData = matStoreRequirePlanService.queryMatStoreSupportData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}
}

