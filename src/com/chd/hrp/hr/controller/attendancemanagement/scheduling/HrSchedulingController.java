package com.chd.hrp.hr.controller.attendancemanagement.scheduling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.s;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrScheduling;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrSchedulingEmp;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 排班处理设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrSchedulingController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrSchedulingController.class);

	// 引入Service服务
	@Resource(name = "hrSchedulingService")
	private final HrSchedulingService hrSchedulingService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrSchedulingMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/scheduling/main";
	}

	/**
	 * 添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/SchedulingAddPage", method = RequestMethod.GET)
	public String SchedulingAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
	  mode.addAttribute("attend_areacode", mapVo.get("attend_areacode"));
		return "hrp/hr/attendancemanagement/scheduling/scheduling/schedulingAdd";
	}
	
	/**
	 * 添加人员跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/schedulingEmpPage", method = RequestMethod.GET)
	public String schedulingEmpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("attend_areacode", mapVo.get("attend_areacode"));
		return "hrp/hr/attendancemanagement/scheduling/scheduling/schedulingEmpPage";
	}
	
	/**
	 * 批量添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAllPage", method = RequestMethod.GET)
	public String addAll(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("attend_areacode", mapVo.get("attend_areacode"));
		mode.addAttribute("selectData", mapVo.get("selectData"));
		return "hrp/hr/attendancemanagement/scheduling/scheduling/addAllPage";
	}
	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPB", method = RequestMethod.POST)
	@ResponseBody
	public String queryPB(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if(mapVo.get("attend_areacode")==null || mapVo.get("attend_areacode").toString().equals("")){
			return "{\"error\":\"排班区域不能为空\"}";
		}
		
		if(mapVo.get("begin_date")==null || mapVo.get("begin_date").toString().equals("")){
			return "{\"error\":\"排班日期不能为空\"}";
		}
		
		if(mapVo.get("end_date")==null || mapVo.get("end_date").toString().equals("")){
			return "{\"error\":\"排班日期不能为空\"}";
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		try{
			String stationTransef = hrSchedulingService.queryPB(mapVo);
			return stationTransef;
		}catch(Exception e){
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		

	}

	

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePB", method = RequestMethod.POST)
	@ResponseBody
	public String  savePB(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		if(mapVo.get("paramVo")==null || mapVo.get("paramVo").toString().equals("")){
			return "{\"error\":\"没有排班数据\"}";
		}
		
		try {
			String hrJson=hrSchedulingService.savePB(mapVo);
			return hrJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
        
	}
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteScheduling", method = RequestMethod.POST)
	@ResponseBody
	public String deleteScheduling(@RequestParam String paramVo, Model mode) throws Exception {
//		   String str = "";
//			boolean falg = true;
//		 List<HrScheduling> listVo = JSONArray.parseArray(paramVo, HrScheduling.class);
//		 List<HrScheduling> listVo1 =new ArrayList<HrScheduling>();
		try {
//			Map<String, Object> mapVo=new HashMap<String, Object>();
//			
//			 for (HrScheduling hrScheduling : listVo) {
//				 if(hrScheduling.getAttend_pbcheck_state()==0){
//						mapVo.put("group_id", SessionManager.getGroupId());
//						mapVo.put("hos_id", SessionManager.getHosId());
//						mapVo.put("attend_pbcode", hrScheduling.getAttend_pbcode());
//						mapVo.put("attend_pbrule", hrScheduling.getAttend_pbrule());
//					 String msg=hrSchedulingService.queryPbfile(mapVo);
//					if(msg .equals("")){
//						 hrScheduling.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
//						 hrScheduling.setHos_id(Integer.parseInt(SessionManager.getHosId()));
//					}else{
//						return ("{\"error\":\"删除失败，请选择未封存数据删除。\",\"state\":\"false\"}");
//					}
//					 listVo1.add(hrScheduling);
//				 }else{
//
//						return ("{\"error\":\"删除失败，请选择未审签数据删除。\",\"state\":\"false\"}");
//				 }
//				
//
//					str = str + hrBaseService.isExistsDataByTable("HR_ATTEND_PB", hrScheduling.getAttend_pbcode())== null ? ""
//							: hrBaseService.isExistsDataByTable("HR_ATTEND_PB", hrScheduling.getAttend_pbcode());
//					if (Strings.isNotBlank(str)) {
//						falg = false;
//						continue;
//					}
//				
//			}
//				if (!falg) {
//					return ("{\"error\":\"删除失败，选择的排班项目被以下业务使用：【" + str.substring(0, str.length() - 1)
//					+ "】。\",\"state\":\"false\"}");
//				}
//				
//			return hrSchedulingService.deleteScheduling(listVo1);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	//
	/**
	 * @Description 删除人员数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSchedulingEmp", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSchedulingEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
//		 String str = "";
//		boolean falg = true;
//		 List<HrSchedulingEmp> listVo = JSONArray.parseArray(mapVo.get("ParamVo").toString(), HrSchedulingEmp.class);
		if(mapVo.get("attend_areacode")==null || mapVo.get("attend_areacode").toString().equals("")){
			return "{\"error\":\"排班区域不能为空\"}";
		}
		
		if(mapVo.get("begin_date")==null || mapVo.get("begin_date").toString().equals("")){
			return "{\"error\":\"排班日期不能为空\"}";
		}
		
		if(mapVo.get("end_date")==null || mapVo.get("end_date").toString().equals("")){
			return "{\"error\":\"排班日期不能为空\"}";
		}
		
		if(mapVo.get("emp_id")==null || mapVo.get("emp_id").toString().equals("")){
			return "{\"error\":\"请选择职工\"}";
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		try {

//			 for (HrSchedulingEmp hrSchedulingEmp : listVo) {
//				 hrSchedulingEmp.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
//				 hrSchedulingEmp.setHos_id(Integer.parseInt(SessionManager.getHosId()));
//				 hrSchedulingEmp.setAttend_pbcode(mapVo.get("attend_pbcode").toString());
//					
//				
//			}
			return hrSchedulingService.deleteSchedulingEmp(mapVo);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * @Description 复制上周
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyWeek", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  copyWeek(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if(mapVo.get("attend_areacode")==null || mapVo.get("attend_areacode").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班区域不能为空\"}");
		}
		
		if(mapVo.get("begin_date")==null || mapVo.get("begin_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班日期不能为空\"}");
		}
		
		if(mapVo.get("end_date")==null || mapVo.get("end_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班日期不能为空\"}");
		}
		
		if(mapVo.get("sel_begin_date")==null || mapVo.get("sel_begin_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"请选择需要复制的日期范围\"}");
		}
		
		if(mapVo.get("sel_end_date")==null || mapVo.get("sel_end_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"请选择需要复制的日期范围\"}");
		}
		
		if(mapVo.get("pb_gz")==null || mapVo.get("pb_gz").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班规则不能为空\"}");
		}
		
		if(mapVo.get("db_gz")==null || mapVo.get("db_gz").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"倒班规则期不能为空\"}");
		}
		
		if(mapVo.get("emp_id")==null || mapVo.get("emp_id").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"请选择职工\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));


		try{
			String datas = hrSchedulingService.copyWeek(mapVo);
			return JSONObject.parseObject(datas);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	
	/**
	 * @Description 继承上周
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/extend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  extend(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		try{
			String datas = hrSchedulingService.extend(mapVo);
			return JSONObject.parseObject(datas);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 审签
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditCountersign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> auditCountersign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		
		try {
			String datas = hrSchedulingService.auditCountersign(mapVo);
			return JSONObject.parseObject(datas);
      
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	/**
	 * 按排班区域查询部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeptTreeByArea", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDeptTreeByArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String subjType = hrSchedulingService.queryDeptTreeByArea(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		
		return json.toString();
	}
	
	/**
	 * 按排班区域查询职工
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpByArea", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryEmpByArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		
		String subjType = hrSchedulingService.queryEmpByArea(getPage(mapVo));
		
		return subjType;
	}
	
	/**
	 * 复制上周/月页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyWeekPage", method = RequestMethod.GET)
	public String copyWeekPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("attend_pbrule", mapVo.get("attend_pbrule"));
		mode.addAttribute("begin_date", mapVo.get("begin_date"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		return "hrp/hr/attendancemanagement/scheduling/scheduling/copyWeek";
	}
	/**
	 * 继承上周/月页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/extendPage", method = RequestMethod.GET)
	public String extendPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("attend_pbrule", mapVo.get("attend_pbrule"));
		mode.addAttribute("begin_date", mapVo.get("begin_date"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		return "hrp/hr/attendancemanagement/scheduling/scheduling/extendPage";
	}
	
	/**
	 * @Description 按科室查询排班数据
	 */
	@RequestMapping(value = "/queryPbByDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPbByDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if(mapVo.get("begin_date")==null || mapVo.get("begin_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班日期不能为空\"}");
		}
		
		if(mapVo.get("end_date")==null || mapVo.get("end_date").toString().equals("")){
			return JSONObject.parseObject("{\"error\":\"排班日期不能为空\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());

		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		try{
			String stationTransef = hrSchedulingService.queryPbByDept(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}
