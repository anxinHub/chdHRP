/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.require.store;
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
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.store.MedStoreRequirePlanService;
/**
 * 
 * @Description:
 * 仓库需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedStoreRequirePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreRequirePlanController.class);
	
	//引入Service服务
	@Resource(name = "medStoreRequirePlanService")
	private final MedStoreRequirePlanService medStoreRequirePlanService = null;
	
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	/**
	 * @Description 
	 * 仓库需求计划编制--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/medStoreRequriedPlanMainPage", method = RequestMethod.GET)
	public String medStoreRequriedPlanMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08031", MyConfig.getSysPara("08031"));

		return "hrp/med/require/store/plan/medStoreRequriedPlanMain";
	}
	
	/**
	 * 仓库需求计划 --查询页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/store/plan/queryMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		String medRequireMainJson = medStoreRequirePlanService.queryStorePlan(getPage(mapVo));

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	/**
	 * @Description 
	 * 仓库需求计划编制--添加
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/medStoreRequriedPlanAddPage", method = RequestMethod.GET)
	public String medStoreRequriedPlanAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/store/plan/medStoreRequriedPlanAdd";
	}
	/**
	 * @Description 
	 * 仓库需求计划编制--保存
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/addMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medRequireMainJson;
		
		try {
			medRequireMainJson = medStoreRequirePlanService.add(mapVo);
		} catch (Exception e) {
			medRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--更新页面跳转
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/medStoreRequriedPlanUpdatePage", method = RequestMethod.GET)
	public String medStoreRequriedPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> medDeptMain = medStoreRequirePlanService.queryByCode(mapVo);
		
		mode.addAttribute("medDeptMain", medDeptMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/store/plan/medStoreRequriedPlanUpdate";
	}
	
	/**
	 * 仓库需求计划编制--查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/store/plan/queryMedStoreRequriedDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreRequriedDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	    
		String medRequireMainJson = medStoreRequirePlanService.queryStoreDetail(mapVo);
		return JSONObject.parseObject(medRequireMainJson); 
		
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--更新
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/updateMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medRequireMainJson;
		try {
			medRequireMainJson = medStoreRequirePlanService.update(mapVo);
		} catch (Exception e) {
			medRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	/**
	 * @Description 
	 * 仓库需求计划编制--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/store/plan/deleteMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			
		String medRequireMainJson;
		try {
			medRequireMainJson = medStoreRequirePlanService.deleteBatch(listVo);
		} catch (Exception e) {
			medRequireMainJson = e.getMessage();
		}
	  return JSONObject.parseObject(medRequireMainJson);
			
	}
	
	//科室需求生成页面跳转
	@RequestMapping(value = "/hrp/med/require/store/plan/medStoreRequriedPlanDeptImpPage", method = RequestMethod.GET)
	public String medStoreRequriedPlanDeptImpPage(Model mode) throws Exception {
		
		mode.addAttribute("p08031", MyConfig.getSysPara("08031"));
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		return "hrp/med/require/store/plan/medStoreRequriedPlanDeptImp";
	}
	
	//科室需求计划中仓库列表
	@RequestMapping(value = "/hrp/med/require/store/plan/queryStoreByDept", method = RequestMethod.POST)
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
		String hrpMedSelect = medStoreRequirePlanService.queryStoreByDept(getPage(mapVo));
		return hrpMedSelect;
	}
	
	//仓库与材料对应关系中申领仓库
	@RequestMapping(value = "/hrp/med/require/store/plan/queryStoreByInv", method = RequestMethod.POST)
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
		String hrpMedSelect = medStoreRequirePlanService.queryStoreByInv(getPage(mapVo));
		return hrpMedSelect;
	}
	/**
	 * 科室需求计划生成 -- 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/store/plan/queryMedStoreByDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreByDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		String medRequireMainJson = medStoreRequirePlanService.queryDeptGDetail(getPage(mapVo));

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	//组装明细数据
	@RequestMapping(value = "/hrp/med/require/store/plan/queryDeptCollectData", method = RequestMethod.POST)
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
		
		String detailData = medStoreRequirePlanService.collectDeptData(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 汇总科室需求计划单据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/store/plan/medStoreReqPlanByDeptAddPage", method = RequestMethod.GET)
	public String medStoreReqPlanByDeptAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/require/store/plan/medStoreRequriedPlanByDeptAdd";
		
	}
	
	
	//中止
	@RequestMapping(value = "/hrp/med/require/store/plan/abortMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMedStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medStoreRequirePlanService.abortMedStoreRequriedPlan(listVo);
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	//提交
	@RequestMapping(value = "/hrp/med/require/store/plan/submitMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMedStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medStoreRequirePlanService.updateSubmitMedStorePlan(listVo);
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	//取消提交
	@RequestMapping(value = "/hrp/med/require/store/plan/unSubmitMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMedStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medStoreRequirePlanService.updateUnSubmitMedStorePlan(listVo);
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	//审核
	@RequestMapping(value = "/hrp/med/require/store/plan/submitMedStoreRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMedStoreRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String medRequireMainJson = medStoreRequirePlanService.submitMedStoreRequriedConfirm(listVo);
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	//取消审核
	@RequestMapping(value = "/hrp/med/require/store/plan/unSubmitMedStoreRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMedStoreRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
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
		
		String medRequireMainJson = medStoreRequirePlanService.unSubmitMedStoreRequriedConfirm(listVo);	
		return JSONObject.parseObject(medRequireMainJson);
	}
	//复制
	@RequestMapping(value = "/hrp/med/require/store/plan/copyMedStoreRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMedStoreRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String medRequireMainJson;
		try {
			medRequireMainJson = medStoreRequirePlanService.copyMedStorePlan(listVo);
		} catch (Exception e) {
			medRequireMainJson = e.getMessage();
		}
		return JSONObject.parseObject(medRequireMainJson);
	}
}

