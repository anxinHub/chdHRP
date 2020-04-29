/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.require.dept;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedRequireMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.dept.MedRequireConfirmService;
import com.chd.hrp.med.service.requrie.dept.MedRequirePlanService;
/**
 * 
 * @Description:
 * 科室需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedRequirePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedRequirePlanController.class);
	
	//引入Service服务
	@Resource(name = "medRequirePlanService")
	private final MedRequirePlanService medRequirePlanService = null;
	
	@Resource(name = "medRequireConfirmService")
	private final MedRequireConfirmService medRequireConfirmService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	/**
	 * @Description 
	 * 科室需求计划编制--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanMainPage", method = RequestMethod.GET)
	public String medRequireMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08031", MyConfig.getSysPara("08031"));
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		return "hrp/med/require/dept/plan/medDeptRequriedPlanMain";
	}
	
	/**
	 * 购置计划查询页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medRequireMainJson = medRequirePlanService.queryPlan(getPage(mapVo));

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 科室需求计划编制添加响应仓库
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanStorePage", method = RequestMethod.GET)
	public String medDepartRequriedPlanStorePage(Model mode) throws Exception {
		return "hrp/med/require/dept/plan/medDeptRequriedPlanStore";

	}
	
	/**
	 * 响应仓库跳转到主页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanMain", method = RequestMethod.GET)
	public String medDepartRequriedPlanMain(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name"));
		return "hrp/med/require/dept/plan/medDeptRequriedPlanMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanAddPage", method = RequestMethod.GET)
	public String medRequireMainAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("store_id") != null && mapVo.get("store_no") != null
		   && !"".equals(mapVo.get("store_id")) && !"".equals(mapVo.get("store_no"))){
			
			mode.addAttribute("store_initValue", mapVo.get("store_id")+","+mapVo.get("store_no"));
			mode.addAttribute("store_id", mapVo.get("store_id"));
			mode.addAttribute("store_no", mapVo.get("store_no"));
			mode.addAttribute("store_name",String.valueOf(mapVo.get("store_name")));
		}else{
			mode.addAttribute("store_initValue", "");
			mode.addAttribute("store_id","");
			mode.addAttribute("store_no","");
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		//System.out.println("************store_name:"+mapVo.get("store_name").toString());
		
		
		/*Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());*/
		return "hrp/med/require/dept/plan/medDeptRequriedPlanAdd";
	}
	
	/**
	 * 配套导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanSupportImp", method = RequestMethod.GET)
	public String medDepartRequridPlanSupportImp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_name",mapVo.get("dept_name").toString());
		System.out.println("++++++++++++++++++++++++++++++++++++++");
		System.out.println(mode);
		System.out.println(mapVo.get("store_id"));
		
		
		Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		return "hrp/med/require/dept/plan/medDeptRequriedPlanSupportImp";

	}
	
	/**
	 * 配套表导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedDeptSupport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptSupport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medRequireMainJson = medRequirePlanService.queryDeptSupport(mapVo);

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	/**
	 * 安全库存导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanSafeImpPage", method = RequestMethod.GET)
	public String medDeptRequriedPlanSafeImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		
		return "hrp/med/require/dept/plan/medDeptRequriedPlanSafeImp";

	}
	/**
	 * 安全库存导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedDeptRequeriedPlanSafe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequeriedPlanSafe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medRequireMainJson = medRequirePlanService.queryStoreSafe(mapVo);

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 科室消耗导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanDeptImp", method = RequestMethod.GET)
	public String medDepartRequridPlanDeptImp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("set_intStore",mapVo.get("store_id") + "," + mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		/*Map<String, Object> map = medCommonService.queryMedParas();
		mode.addAttribute("paras", medCommonService.queryMedParas());*/
		
		return "hrp/med/require/dept/plan/medDeptRequriedPlanDeptImp";

	}
	/**
	 * 科室消耗导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedDeptExpend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptExpend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medRequireMainJson = medRequirePlanService.queryDeptExpend(mapVo);
		return JSONObject.parseObject(medRequireMainJson);	
	}
	
	
	
	/**
	 * @Description 
	 * 添加数据 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/addMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			medRequireMainJson = medRequirePlanService.add(mapVo);
			
		} catch (Exception e) {
			
			medRequireMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medRequireMainJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedPlanUpdatePage", method = RequestMethod.GET)
	public String medDeptRequriedPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		Map<String, Object> medDeptMain = medRequirePlanService.queryByCode(mapVo);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08032", MyConfig.getSysPara("08032"));
		
		mode.addAttribute("medDeptMain", medDeptMain);
		return "hrp/med/require/dept/plan/medDeptRequriedPlanUpdate";
	}
	
	/**
	 * 终止计划
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/abortMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMedDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medRequirePlanService.updateAbortMedDeptPlan(listVo);
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	/**
	 * 提交单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/submitMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMedDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medRequirePlanService.updateSubmitMedDeptPlan(listVo);
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	/**
	 * 取消提交
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/unSubmitMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMedDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medRequireMainJson = medRequirePlanService.updateUnSubmitMedDeptPlan(listVo);
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	/**
	 * 删除购置计划
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/deleteMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMedDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
			//用于删除与申请单之前的对应关系
			mapVo.put("rela_type", "5");
			mapVo.put("rela_id", id.split("@")[0]);
            listVo.add(mapVo);
        }
		
		String medRequireMainJson;
		
		try {
			
			medRequireMainJson = medRequirePlanService.deleteMedDeptPlan(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medRequireMainJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	/**
	 * 复制 购置计划
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/copyMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMedDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
    		mapVo.put("day", date1[2]);
            listVo.add(mapVo);
        }
		
		String medRequireMainJson;
		
		try {
			
			medRequireMainJson = medRequirePlanService.copyMedDeptPlan(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medRequireMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	/**
	 * 更新页面查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedDeptRequriedDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequriedDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	    
		String medRequireMainJson = medRequirePlanService.queryDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medRequireMainJson); 
		
		//return "hrp/med/require/plan/medDeptRequriedPlanUpdate";
	}
	
	/**
	 * @Description 
	 * 更新数据 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/updateMedDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
			
			medRequireMainJson = medRequirePlanService.update(mapVo);
			
		} catch (Exception e) {
			
			medRequireMainJson = e.getMessage();
		}
	  
		
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	
	/**
	 * @Description 
	 * 更新数据 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/addOrUpdateMedRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedRequireMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medRequireMainJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		try {
			
			medRequireMainJson = medRequirePlanService.addOrUpdate(detailVo);
			
		} catch (Exception e) {
			
			medRequireMainJson = e.getMessage();
		}
		
		}
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/deleteMedRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedRequireMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			medRequireMainJson = medRequirePlanService.deleteBatch(listVo);
			
		} catch (Exception e) {
			
			medRequireMainJson = e.getMessage();
		}
			
	  return JSONObject.parseObject(medRequireMainJson);
			
	}
	
	
	/**
	 * 科室购置计划--审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/submitMedDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMedDeptRequriedConfirms(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            mapVo.put("user_id", SessionManager.getUserId());
            
            mapVo.put("check_date", new Date());
            
            listVo.add(mapVo);
        }
		
		String medRequireMainJson = medRequireConfirmService.updateAudit(listVo);
		return JSONObject.parseObject(medRequireMainJson);
	}
	
	/**
	 * 科室购置计划--取消审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/unSubmitMedDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMedDeptRequriedConfirms(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
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
		
		String medRequireMainJson = medRequireConfirmService.updateAuditCancle(listVo);	
		return JSONObject.parseObject(medRequireMainJson);
	}
	/**
	 * 科室购置计划--退回科室
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/medDeptRequriedConfirmReturnPage", method = RequestMethod.GET)
	public String medDeptRequriedConfirmReturnPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("reqIds", mapVo.get("reqIds"));
		return "hrp/med/require/dept/plan/medDeptRequriedConfirmReturn";

	}
	
	/**
	 * 科室购置计划--退回科室保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/dept/plan/returnMedDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> returnMedDeptRequriedConfirms(@RequestParam Map<String, Object> mapVo) throws Exception {
		String return_reason= mapVo.get("return_reason").toString();
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String reqIds = mapVo.get("reqIds").toString();
		String[] reqId = reqIds.split(",");
        for (int i = 0; i < reqId.length; i++) {
        	Map<String, Object> mapVo1 = new HashMap<String, Object>();
        	mapVo1.put("hos_id", SessionManager.getHosId());
        	mapVo1.put("group_id", SessionManager.getGroupId());
        	mapVo1.put("copy_code", SessionManager.getCopyCode());
        	mapVo1.put("acc_year", SessionManager.getAcctYear());
        	mapVo1.put("req_id", reqId[i]);
        	mapVo1.put("return_reason", return_reason);
        	listVo.add(mapVo1);
        }
		
		String medRequireMain = medRequireConfirmService.updateReturn(listVo);
		return JSONObject.parseObject(medRequireMain);
		
	}
	/**
	 * @Description 
	 * 查询数据 MED_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/queryMedRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedRequireMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medRequireMain = medRequirePlanService.query(getPage(mapVo));

		return JSONObject.parseObject(medRequireMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MED_REQUIRE_MAIN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/medRequireMainImportPage", method = RequestMethod.GET)
	public String medRequireMainImportPage(Model mode) throws Exception {

		return "hrp/med/require/plan/medRequireMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MED_REQUIRE_MAIN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/require/dept/plan/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","MED_REQUIRE_MAIN.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 MED_REQUIRE_MAIN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/require/dept/plan/readMedRequireMainFiles",method = RequestMethod.POST)  
    public String readMedRequireMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedRequireMain> list_err = new ArrayList<MedRequireMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedRequireMain medRequireMain = new MedRequireMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medRequireMain.setReq_id(Long.valueOf(temp[3]));
		    		mapVo.put("req_id", temp[3]);
					
					} else {
						
						err_sb.append("计划单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medRequireMain.setReq_code(temp[4]);
		    		mapVo.put("req_code", temp[4]);
					
					} else {
						
						err_sb.append("计划单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medRequireMain.setBrif(temp[5]);
		    		mapVo.put("brif", temp[5]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medRequireMain.setDept_no(Long.valueOf(temp[6]));
		    		mapVo.put("dept_no", temp[6]);
					
					} else {
						
						err_sb.append("科室变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medRequireMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medRequireMain.setStock_no(Long.valueOf(temp[8]));
		    		mapVo.put("stock_no", temp[8]);
					
					} else {
						
						err_sb.append("响应库房变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medRequireMain.setStock_id(Long.valueOf(temp[9]));
		    		mapVo.put("stock_id", temp[9]);
					
					} else {
						
						err_sb.append("响应库房ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medRequireMain.setMaker(Long.valueOf(temp[10]));
		    		mapVo.put("maker", temp[10]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medRequireMain.setMake_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("make_date", temp[11]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medRequireMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medRequireMain.setCheck_date(DateUtil.stringToDate(temp[13]));
		    		mapVo.put("check_date", temp[13]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medRequireMain.setState(Integer.valueOf(temp[14]));
		    		mapVo.put("state", temp[14]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					medRequireMain.setReq_type(Integer.valueOf(temp[15]));
		    		mapVo.put("req_type", temp[15]);
					
					} else {
						
						err_sb.append("计划类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					medRequireMain.setIs_collect(Integer.valueOf(temp[16]));
		    		mapVo.put("is_collect", temp[16]);
					
					} else {
						
						err_sb.append("单据汇总为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					medRequireMain.setIs_submit(Integer.valueOf(temp[17]));
		    		mapVo.put("is_submit", temp[17]);
					
					} else {
						
						err_sb.append("是否提交为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					medRequireMain.setIs_return(Integer.valueOf(temp[18]));
		    		mapVo.put("is_return", temp[18]);
					
					} else {
						
						err_sb.append("是否退回为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					medRequireMain.setReturn_reason(temp[19]);
		    		mapVo.put("return_reason", temp[19]);
					
					} else {
						
						err_sb.append("退回理由为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					medRequireMain.setOther_inv(temp[20]);
		    		mapVo.put("other_inv", temp[20]);
					
					} else {
						
						err_sb.append("其他需求物资为空  ");
						
					}
					 
					
				MedRequireMain data_exc_extis = medRequirePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medRequireMain.setError_type(err_sb.toString());
					
					list_err.add(medRequireMain);
					
				} else {
			  
					String dataJson = medRequirePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedRequireMain data_exc = new MedRequireMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 MED_REQUIRE_MAIN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/require/dept/plan/addBatchMedRequireMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedRequireMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedRequireMain> list_err = new ArrayList<MedRequireMain>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			MedRequireMain medRequireMain = new MedRequireMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("req_id"))) {
						
					medRequireMain.setReq_id(Long.valueOf((String)jsonObj.get("req_id")));
		    		mapVo.put("req_id", jsonObj.get("req_id"));
		    		} else {
						
						err_sb.append("计划单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("req_code"))) {
						
					medRequireMain.setReq_code((String)jsonObj.get("req_code"));
		    		mapVo.put("req_code", jsonObj.get("req_code"));
		    		} else {
						
						err_sb.append("计划单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
					medRequireMain.setBrif((String)jsonObj.get("brif"));
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					medRequireMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("科室变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					medRequireMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_no"))) {
						
					medRequireMain.setStock_no(Long.valueOf((String)jsonObj.get("stock_no")));
		    		mapVo.put("stock_no", jsonObj.get("stock_no"));
		    		} else {
						
						err_sb.append("响应库房变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_id"))) {
						
					medRequireMain.setStock_id(Long.valueOf((String)jsonObj.get("stock_id")));
		    		mapVo.put("stock_id", jsonObj.get("stock_id"));
		    		} else {
						
						err_sb.append("响应库房ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medRequireMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_date"))) {
						
					medRequireMain.setMake_date(DateUtil.stringToDate((String)jsonObj.get("make_date")));
		    		mapVo.put("make_date", jsonObj.get("make_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medRequireMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					medRequireMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medRequireMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("req_type"))) {
						
					medRequireMain.setReq_type(Integer.valueOf((String)jsonObj.get("req_type")));
		    		mapVo.put("req_type", jsonObj.get("req_type"));
		    		} else {
						
						err_sb.append("计划类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_collect"))) {
						
					medRequireMain.setIs_collect(Integer.valueOf((String)jsonObj.get("is_collect")));
		    		mapVo.put("is_collect", jsonObj.get("is_collect"));
		    		} else {
						
						err_sb.append("单据汇总为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_submit"))) {
						
					medRequireMain.setIs_submit(Integer.valueOf((String)jsonObj.get("is_submit")));
		    		mapVo.put("is_submit", jsonObj.get("is_submit"));
		    		} else {
						
						err_sb.append("是否提交为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_return"))) {
						
					medRequireMain.setIs_return(Integer.valueOf((String)jsonObj.get("is_return")));
		    		mapVo.put("is_return", jsonObj.get("is_return"));
		    		} else {
						
						err_sb.append("是否退回为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("return_reason"))) {
						
					medRequireMain.setReturn_reason((String)jsonObj.get("return_reason"));
		    		mapVo.put("return_reason", jsonObj.get("return_reason"));
		    		} else {
						
						err_sb.append("退回理由为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("other_inv"))) {
						
					medRequireMain.setOther_inv((String)jsonObj.get("other_inv"));
		    		mapVo.put("other_inv", jsonObj.get("other_inv"));
		    		} else {
						
						err_sb.append("其他需求物资为空  ");
						
					}
					
					
				MedRequireMain data_exc_extis = medRequirePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medRequireMain.setError_type(err_sb.toString());
					
					list_err.add(medRequireMain);
					
				} else {
			  
					String dataJson = medRequirePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedRequireMain data_exc = new MedRequireMain();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
}

