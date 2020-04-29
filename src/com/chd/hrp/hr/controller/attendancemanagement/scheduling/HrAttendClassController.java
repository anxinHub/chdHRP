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
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClass;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 班次设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrAttendClassController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrAttendClassController.class);

	// 引入Service服务
	@Resource(name = "hrAttendClassService")
	private final HrAttendClassService hrAttendClassService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendClassMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/attendclass/attendClassMainPage";
	}
	/**
	 * 添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/attendClassAddPage", method = RequestMethod.GET)
	public String attendClassAddPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/attendclass/attendClassAdd";
	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendClassPage", method = RequestMethod.GET)
	public String updateHrDeptleavePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrAttendClass hrAttendClass = new HrAttendClass();
		hrAttendClass = hrAttendClassService.queryByCode(mapVo);
		   SimpleDateFormat format0 = new SimpleDateFormat("HH:mm");
		mode.addAttribute("attend_area_name", hrAttendClass.getAttend_area_name());
		mode.addAttribute("attend_area_name", hrAttendClass.getAttend_area_name());
		mode.addAttribute("attend_class_typecode", hrAttendClass.getAttend_class_typecode());
		mode.addAttribute("attend_classcode", hrAttendClass.getAttend_classcode());
		mode.addAttribute("attend_code", hrAttendClass.getAttend_code());
		mode.addAttribute("attend_classname", hrAttendClass.getAttend_classname());
		mode.addAttribute("attend_classsname", hrAttendClass.getAttend_classsname());
		mode.addAttribute("attend_begtime1", format0.format(hrAttendClass.getAttend_begtime1()));
		mode.addAttribute("attend_endtime1", format0.format(hrAttendClass.getAttend_endtime1()));
		if(hrAttendClass.getAttend_begtime2()!=null){
			mode.addAttribute("attend_begtime2", format0.format(hrAttendClass.getAttend_begtime2()));
		}
		if(hrAttendClass.getAttend_endtime2()!=null){
			mode.addAttribute("attend_endtime2", format0.format(hrAttendClass.getAttend_endtime2()));
		}
		
	if (hrAttendClass.getAttend_begtime3()!=null) {
		mode.addAttribute("attend_begtime3", format0.format(hrAttendClass.getAttend_begtime3()));
	}if (hrAttendClass.getAttend_endtime3()!=null) {
		mode.addAttribute("attend_endtime3", format0.format(hrAttendClass.getAttend_endtime3()));
	}
		
		mode.addAttribute("attend_days", hrAttendClass.getAttend_days());
		
		return "hrp/hr/attendancemanagement/scheduling/attendclass/attendClassUpdate";

	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendClass", method = RequestMethod.POST)
	@ResponseBody
	public String  addAttendClass(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

	
		String hrJson;
		try {
			hrJson = hrAttendClassService.addAttendClass(mapVo);
			return hrJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	
        
	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendClass", method = RequestMethod.POST)
	@ResponseBody
	public String  updateAttendClass(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

	
		String hrJson;
		try {
			hrJson = hrAttendClassService.updateAttendClass(mapVo);
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
	@RequestMapping(value = "/deleteAttendClass", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAttendClass(@RequestParam String paramVo, Model mode) throws Exception {
		int str =0;
			boolean falg = true;
		 List<HrAttendClass> listVo = JSONArray.parseArray(paramVo, HrAttendClass.class);
		
		try {
		   if (listVo.size()>0) {
			for (HrAttendClass hrAttendClass : listVo) {
				hrAttendClass.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAttendClass.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				Map<String, Object> mapVo = new HashMap<String, Object>();
				str =hrAttendClassService.queryAttendScheduling(hrAttendClass) ;
			if (str!=0 ) {
				
				falg = false;
				continue;
			}
	}
  if (!falg) {
		return ("{\"error\":\"删除失败，选择的分类正被排班区域使用。\",\"state\":\"false\"}");
			}


		}
		   return hrAttendClassService.deleteAttendClass(listVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendClass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendClass(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		try {
			String stationTransef = hrAttendClassService.queryAttendClass(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
//查询班次
	@RequestMapping(value = "/queryAttendAreacode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendAreacode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrAttendClassService.queryAttendAreacode(mapVo);
		return json;

	}
	//
	
}
