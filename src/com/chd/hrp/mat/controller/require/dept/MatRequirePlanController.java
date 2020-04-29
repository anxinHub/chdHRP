/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.dept;
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
import com.chd.hrp.mat.entity.MatRequireMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.dept.MatRequireConfirmService;
import com.chd.hrp.mat.service.requrie.dept.MatRequirePlanService;
/**
 * 
 * @Description:
 * 科室需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatRequirePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatRequirePlanController.class);
	
	//引入Service服务
	@Resource(name = "matRequirePlanService")
	private final MatRequirePlanService matRequirePlanService = null;
	
	@Resource(name = "matRequireConfirmService")
	private final MatRequireConfirmService matRequireConfirmService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	/**
	 * @Description 
	 * 科室需求计划编制--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanMainPage", method = RequestMethod.GET)
	public String matRequireMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanMain";
	}
	
	/**
	 * 购置计划查询页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String sta=mapVo.get("state").toString().replaceAll(";", ",");
		mapVo.put("stateNew", sta);
		
		String matRequireMainJson = matRequirePlanService.queryPlan(getPage(mapVo));

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * @Description 
	 * 科室需求计划编制添加响应仓库
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanStorePage", method = RequestMethod.GET)
	public String matDepartRequriedPlanStorePage(Model mode) throws Exception {
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanStore";

	}
	
	/**
	 * 响应仓库跳转到主页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanMain", method = RequestMethod.GET)
	public String matDepartRequriedPlanMain(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name"));
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanAddPage", method = RequestMethod.GET)
	public String matRequireMainAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanAdd";
	}
	
	/**
	 * 配套导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanSupportImp", method = RequestMethod.GET)
	public String matDepartRequridPlanSupportImp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_name",mapVo.get("dept_name").toString());

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanSupportImp";

	}
	
	/**
	 * 配套表导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatDeptSupport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptSupport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson = matRequirePlanService.queryDeptSupport(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	/**
	 * 安全库存导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanSafeImpPage", method = RequestMethod.GET)
	public String matDeptRequriedPlanSafeImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanSafeImp";

	}
	/**
	 * 安全库存导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatDeptRequeriedPlanSafe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequeriedPlanSafe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matRequireMainJson = matRequirePlanService.queryStoreSafe(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 科室消耗导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanDeptImp", method = RequestMethod.GET)
	public String matDepartRequridPlanDeptImp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name",mapVo.get("store_name").toString());
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanDeptImp";

	}
	/**
	 * 科室消耗导入查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatDeptExpend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptExpend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson = matRequirePlanService.queryDeptExpend(mapVo);
		return JSONObject.parseObject(matRequireMainJson);	
	}
	
	
	
	/**
	 * @Description 
	 * 添加数据 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/addMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			matRequireMainJson = matRequirePlanService.add(mapVo);
			
		} catch (Exception e) {
			
			matRequireMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matRequireMainJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedPlanUpdatePage", method = RequestMethod.GET)
	public String matDeptRequriedPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		Map<String, Object> matDeptMain = matRequirePlanService.queryByCode(mapVo);
		
		mode.addAttribute("matDeptMain", matDeptMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04032", MyConfig.getSysPara("04032"));
		
		return "hrp/mat/require/dept/plan/matDeptRequriedPlanUpdate";
	}
	
	/**
	 * 终止计划
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/abortMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> abortMatDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matRequireMainJson = matRequirePlanService.updateAbortMatDeptPlan(listVo);
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	/**
	 * 提交单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/submitMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMatDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matRequireMainJson = matRequirePlanService.updateSubmitMatDeptPlan(listVo);
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	/**
	 * 取消提交
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/unSubmitMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMatDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matRequireMainJson = matRequirePlanService.updateUnSubmitMatDeptPlan(listVo);
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	/**
	 * 删除购置计划
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/deleteMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> deleteMatDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matRequireMainJson;
		
		try {
			
			matRequireMainJson = matRequirePlanService.deleteMatDeptPlan(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matRequireMainJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * 复制 购置计划
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/copyMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMatDeptRequriedPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String matRequireMainJson;
		
		try {
			
			matRequireMainJson = matRequirePlanService.copyMatDeptPlan(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matRequireMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * 更新页面查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatDeptRequriedDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	    
		String matRequireMainJson = matRequirePlanService.queryDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matRequireMainJson); 
		
		//return "hrp/mat/require/plan/matDeptRequriedPlanUpdate";
	}
	
	/**
	 * @Description 
	 * 更新数据 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/updateMatDeptRequriedPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDeptRequriedPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
			
			matRequireMainJson = matRequirePlanService.update(mapVo);
			
		} catch (Exception e) {
			
			matRequireMainJson = e.getMessage();
		}
	  
		if(matRequireMainJson.contains("true")){
			
			matRequirePlanService.updateMatApplyRelaTables(mapVo);
		}
		
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	
	/**
	 * @Description 
	 * 更新数据 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/addOrUpdateMatRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatRequireMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matRequireMainJson ="";
		
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
			
			matRequireMainJson = matRequirePlanService.addOrUpdate(detailVo);
			
		} catch (Exception e) {
			
			matRequireMainJson = e.getMessage();
		}
		
		}
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/deleteMatRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatRequireMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			matRequireMainJson = matRequirePlanService.deleteBatch(listVo);
			
		} catch (Exception e) {
			
			matRequireMainJson = e.getMessage();
		}
			
	  return JSONObject.parseObject(matRequireMainJson);
			
	}
	
	
	/**
	 * 科室购置计划--审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/submitMatDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMatDeptRequriedConfirms(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matRequireMainJson = matRequireConfirmService.updateAudit(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * 科室购置计划--取消审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/unSubmitMatDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMatDeptRequriedConfirms(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
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
		
		String matRequireMainJson = matRequireConfirmService.updateAuditCancle(listVo);	
		return JSONObject.parseObject(matRequireMainJson);
	}
	/**
	 * 科室购置计划--退回科室
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matDeptRequriedConfirmReturnPage", method = RequestMethod.GET)
	public String matDeptRequriedConfirmReturnPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("reqIds", mapVo.get("reqIds"));
		return "hrp/mat/require/dept/plan/matDeptRequriedConfirmReturn";

	}
	
	/**
	 * 科室购置计划--退回科室保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/plan/returnMatDeptRequriedConfirms", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> returnMatDeptRequriedConfirms(@RequestParam Map<String, Object> mapVo) throws Exception {
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
		
		String matRequireMain = matRequireConfirmService.updateReturn(listVo);
		return JSONObject.parseObject(matRequireMain);
		
	}
	/**
	 * @Description 
	 * 查询数据 MAT_REQUIRE_MAIN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/queryMatRequireMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatRequireMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matRequireMain = matRequirePlanService.query(getPage(mapVo));

		return JSONObject.parseObject(matRequireMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MAT_REQUIRE_MAIN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/matRequireMainImportPage", method = RequestMethod.GET)
	public String matRequireMainImportPage(Model mode) throws Exception {

		return "hrp/mat/require/plan/matRequireMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MAT_REQUIRE_MAIN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/require/dept/plan/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","MAT_REQUIRE_MAIN.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 MAT_REQUIRE_MAIN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/require/dept/plan/readMatRequireMainFiles",method = RequestMethod.POST)  
    public String readMatRequireMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatRequireMain> list_err = new ArrayList<MatRequireMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatRequireMain matRequireMain = new MatRequireMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matRequireMain.setReq_id(Long.valueOf(temp[3]));
		    		mapVo.put("req_id", temp[3]);
					
					} else {
						
						err_sb.append("计划单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matRequireMain.setReq_code(temp[4]);
		    		mapVo.put("req_code", temp[4]);
					
					} else {
						
						err_sb.append("计划单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matRequireMain.setBrif(temp[5]);
		    		mapVo.put("brif", temp[5]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matRequireMain.setDept_no(Long.valueOf(temp[6]));
		    		mapVo.put("dept_no", temp[6]);
					
					} else {
						
						err_sb.append("科室变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matRequireMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matRequireMain.setStock_no(Long.valueOf(temp[8]));
		    		mapVo.put("stock_no", temp[8]);
					
					} else {
						
						err_sb.append("响应库房变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matRequireMain.setStock_id(Long.valueOf(temp[9]));
		    		mapVo.put("stock_id", temp[9]);
					
					} else {
						
						err_sb.append("响应库房ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matRequireMain.setMaker(Long.valueOf(temp[10]));
		    		mapVo.put("maker", temp[10]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matRequireMain.setMake_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("make_date", temp[11]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matRequireMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matRequireMain.setCheck_date(DateUtil.stringToDate(temp[13]));
		    		mapVo.put("check_date", temp[13]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					matRequireMain.setState(Integer.valueOf(temp[14]));
		    		mapVo.put("state", temp[14]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					matRequireMain.setReq_type(Integer.valueOf(temp[15]));
		    		mapVo.put("req_type", temp[15]);
					
					} else {
						
						err_sb.append("计划类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					matRequireMain.setIs_collect(Integer.valueOf(temp[16]));
		    		mapVo.put("is_collect", temp[16]);
					
					} else {
						
						err_sb.append("单据汇总为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					matRequireMain.setIs_submit(Integer.valueOf(temp[17]));
		    		mapVo.put("is_submit", temp[17]);
					
					} else {
						
						err_sb.append("是否提交为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					matRequireMain.setIs_return(Integer.valueOf(temp[18]));
		    		mapVo.put("is_return", temp[18]);
					
					} else {
						
						err_sb.append("是否退回为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					matRequireMain.setReturn_reason(temp[19]);
		    		mapVo.put("return_reason", temp[19]);
					
					} else {
						
						err_sb.append("退回理由为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					matRequireMain.setOther_inv(temp[20]);
		    		mapVo.put("other_inv", temp[20]);
					
					} else {
						
						err_sb.append("其他需求物资为空  ");
						
					}
					 
					
				MatRequireMain data_exc_extis = matRequirePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matRequireMain.setError_type(err_sb.toString());
					
					list_err.add(matRequireMain);
					
				} else {
			  
					String dataJson = matRequirePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatRequireMain data_exc = new MatRequireMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 MAT_REQUIRE_MAIN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/require/dept/plan/addBatchMatRequireMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatRequireMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatRequireMain> list_err = new ArrayList<MatRequireMain>();
		
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
			
			MatRequireMain matRequireMain = new MatRequireMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("req_id"))) {
						
					matRequireMain.setReq_id(Long.valueOf((String)jsonObj.get("req_id")));
		    		mapVo.put("req_id", jsonObj.get("req_id"));
		    		} else {
						
						err_sb.append("计划单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("req_code"))) {
						
					matRequireMain.setReq_code((String)jsonObj.get("req_code"));
		    		mapVo.put("req_code", jsonObj.get("req_code"));
		    		} else {
						
						err_sb.append("计划单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
					matRequireMain.setBrif((String)jsonObj.get("brif"));
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					matRequireMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("科室变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					matRequireMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_no"))) {
						
					matRequireMain.setStock_no(Long.valueOf((String)jsonObj.get("stock_no")));
		    		mapVo.put("stock_no", jsonObj.get("stock_no"));
		    		} else {
						
						err_sb.append("响应库房变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_id"))) {
						
					matRequireMain.setStock_id(Long.valueOf((String)jsonObj.get("stock_id")));
		    		mapVo.put("stock_id", jsonObj.get("stock_id"));
		    		} else {
						
						err_sb.append("响应库房ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matRequireMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("make_date"))) {
						
					matRequireMain.setMake_date(DateUtil.stringToDate((String)jsonObj.get("make_date")));
		    		mapVo.put("make_date", jsonObj.get("make_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matRequireMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					matRequireMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matRequireMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("req_type"))) {
						
					matRequireMain.setReq_type(Integer.valueOf((String)jsonObj.get("req_type")));
		    		mapVo.put("req_type", jsonObj.get("req_type"));
		    		} else {
						
						err_sb.append("计划类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_collect"))) {
						
					matRequireMain.setIs_collect(Integer.valueOf((String)jsonObj.get("is_collect")));
		    		mapVo.put("is_collect", jsonObj.get("is_collect"));
		    		} else {
						
						err_sb.append("单据汇总为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_submit"))) {
						
					matRequireMain.setIs_submit(Integer.valueOf((String)jsonObj.get("is_submit")));
		    		mapVo.put("is_submit", jsonObj.get("is_submit"));
		    		} else {
						
						err_sb.append("是否提交为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_return"))) {
						
					matRequireMain.setIs_return(Integer.valueOf((String)jsonObj.get("is_return")));
		    		mapVo.put("is_return", jsonObj.get("is_return"));
		    		} else {
						
						err_sb.append("是否退回为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("return_reason"))) {
						
					matRequireMain.setReturn_reason((String)jsonObj.get("return_reason"));
		    		mapVo.put("return_reason", jsonObj.get("return_reason"));
		    		} else {
						
						err_sb.append("退回理由为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("other_inv"))) {
						
					matRequireMain.setOther_inv((String)jsonObj.get("other_inv"));
		    		mapVo.put("other_inv", jsonObj.get("other_inv"));
		    		} else {
						
						err_sb.append("其他需求物资为空  ");
						
					}
					
					
				MatRequireMain data_exc_extis = matRequirePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matRequireMain.setError_type(err_sb.toString());
					
					list_err.add(matRequireMain);
					
				} else {
			  
					String dataJson = matRequirePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatRequireMain data_exc = new MatRequireMain();
			
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

