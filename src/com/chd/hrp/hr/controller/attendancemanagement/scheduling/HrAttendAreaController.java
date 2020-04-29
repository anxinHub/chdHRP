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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendAreaService;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrAttendClassService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 排班区域设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrAttendAreaController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrAttendAreaController.class);

	// 引入Service服务
	@Resource(name = "hrAttendAreaService")
	private final HrAttendAreaService hrAttendAreaService = null;
	
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
	@RequestMapping(value = "/hrAttendAreaMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/attendarea/attendAreaMainPage";
	}
	//
	/**
	 * 添加科室跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/attendAreaAddPage", method = RequestMethod.GET)
	public String attendAreaAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
	
		return "hrp/hr/attendancemanagement/scheduling/attendarea/attendAreaAdd";
	}
	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAreaAddPage", method = RequestMethod.GET)
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
		HrAttendArea hrAttendArea = new HrAttendArea();
		hrAttendArea = hrAttendAreaService.queryByCode(mapVo);
	
		mode.addAttribute("attend_areacode", hrAttendArea.getAttend_areacode());
		mode.addAttribute("attend_area_name", hrAttendArea.getAttend_area_name());
		mode.addAttribute("attend_class_typecode", hrAttendArea.getAttend_class_typecode());
		mode.addAttribute("pb_gz", hrAttendArea.getPb_gz());
		mode.addAttribute("db_gz", hrAttendArea.getDb_gz());
		mode.addAttribute("yh_code", hrAttendArea.getYh_code());
		
		return "hrp/hr/attendancemanagement/scheduling/attendarea/attendAreaUpate";

	}
	
	/**
	 * 区域科室页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewDept", method = RequestMethod.GET)
	public String viewDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("attend_areacode", mapVo.get("attend_areacode"));
		mode.addAttribute("attend_area_name", mapVo.get("attend_area_name"));
		return "hrp/hr/attendancemanagement/scheduling/attendarea/viewDept";

	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendArea", method = RequestMethod.POST)
	@ResponseBody
	public String addAttendArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			hrJson = hrAttendAreaService.addAttendArea(mapVo);
			return hrJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
        
	}
	/**
	 * @Description 修改数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendArea", method = RequestMethod.POST)
	@ResponseBody
	public String updateAttendArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			hrJson = hrAttendAreaService.updateAttendArea(mapVo);
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
	@RequestMapping(value = "/deleteAttendArea", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAttendArea(@RequestParam String paramVo, Model mode) throws Exception {
	int  str =0;
			boolean falg = true;
		 List<HrAttendArea> listVo = JSONArray.parseArray(paramVo, HrAttendArea.class);
		
		try {
			if (listVo.size()>0) {
				  for (HrAttendArea hrAttendArea : listVo) {
				    	 hrAttendArea.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				    	 hrAttendArea.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				    		str =hrAttendAreaService.queryAreacode(hrAttendArea) ;
							if (str!=0 ) {
								falg = false;
								continue;
							}
					}
				  if (!falg) {
						return ("{\"error\":\"删除失败，选择的区域正被排班处理使用。\",\"state\":\"false\"}");
							}
				
			}
			return hrAttendAreaService.deleteAttendArea(listVo);
		

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
	@RequestMapping(value = "/queryAttendArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendArea(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String stationTransef = hrAttendAreaService.queryAttendArea(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAreaDept", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAssTypeDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String subjType = hrAttendAreaService.queryAreaDept(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			return json.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	
	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAreaDeptCheck", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAreaDeptCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String subjType = hrAttendAreaService.queryAreaDeptCheck(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		/*
		 * String str = "{\"id\":\"-1\",\"name\":\"全部折叠\"}"; JSONObject jsonObj
		 * = JSONArray.parseObject(str); json.add(0, jsonObj);
		 */
		return json.toString();
	}
}
