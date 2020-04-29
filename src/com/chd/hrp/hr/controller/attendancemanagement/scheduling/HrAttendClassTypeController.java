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
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClassType;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassTypeService;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 班次分类设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrAttendClassTypeController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrAttendClassTypeController.class);

	// 引入Service服务
	@Resource(name = "hrAttendClassTypeService")
	private final HrAttendClassTypeService hrAttendClassTypeService = null;
	
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
	@RequestMapping(value = "/hrAttendClassTypeMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/attendclasstype/attendClassTypeMainPage";
	}
	/**
	 * 添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/attendClassTypeAddPage", method = RequestMethod.GET)
	public String attendAreaAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
	
		return "hrp/hr/attendancemanagement/scheduling/attendclasstype/attendClassTypeAdd";
	}
	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendClassTypePage", method = RequestMethod.GET)
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
		HrAttendClassType hrAttendClassType = new HrAttendClassType();
		hrAttendClassType = hrAttendClassTypeService.queryByCode(mapVo);
	
		mode.addAttribute("attend_class_typecode", hrAttendClassType.getAttend_class_typecode());
		mode.addAttribute("attend_class_typename", hrAttendClassType.getAttend_class_typename());
	
		
		return "hrp/hr/attendancemanagement/scheduling/attendclasstype/attendClassTypeUpate";

	}
	
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendClassType", method = RequestMethod.POST)
	@ResponseBody
	public String addAttendClassType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			hrJson = hrAttendClassTypeService.addAttendClassType(mapVo);
			return hrJson;
	        
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	
	}
	//
	
	/**
	 * @Description 修改数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendClassType", method = RequestMethod.POST)
	@ResponseBody
	public String updateAttendClassType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		return	hrJson = hrAttendClassTypeService.updateAttendClassType(mapVo);
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
	@RequestMapping(value = "/deleteAttendClassType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAttendClassType(@RequestParam String paramVo, Model mode) throws Exception {
		int str = 0;
			boolean falg = true;
		List<HrAttendClassType> listVo = JSONArray.parseArray(paramVo, HrAttendClassType.class);
	
		
		try {
			return hrAttendClassTypeService.deleteAttendClassType(listVo);
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
	@RequestMapping(value = "/queryAttendClassType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendClassType(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrAttendClassTypeService.queryAttendClassType(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	
}
