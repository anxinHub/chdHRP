package com.chd.hrp.hr.controller.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItemFz;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrAttendItemFzService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrCommonService;

/**
 * 考勤项目分组设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAttendItemFzController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendItemFzController.class);

	// 引入Service服务 
	@Resource(name = "hrAttendItemFzService")
	private final HrAttendItemFzService hrAttendItemFzService = null;
	
	@Resource(name="hrCommonService")
	private final HrCommonService hrCommonService = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendItemFzMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/attend/attenditemfz/attendItemFzMainPage";

	}
	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hraddAttendItemFzPage", method = RequestMethod.GET)
	public String hraddAttendItemFzPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/attend/attenditemfz/addAttendItemFzPage";

	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendItemFz", method = RequestMethod.POST)
	@ResponseBody
	public String addAttendItemFz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
try {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}

	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}

	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	String hosEmpKindJson = hrAttendItemFzService.addAttendItemFz(mapVo);

	return hosEmpKindJson;

	} catch (Exception e) {
		return "{\"error\":\"" + e.getMessage() + "\"}";
	}
	
}
	



	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendItemFzPage", method = RequestMethod.GET)
	public String updateAttendItemFzPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrAttendItemFz hrAttendItemFz = new HrAttendItemFz();
		hrAttendItemFz = hrAttendItemFzService.queryByCodeAttendItemFz(mapVo);
		
		mode.addAttribute("attend_code_fz", hrAttendItemFz.getAttend_code_fz());
		mode.addAttribute("attend_name_fz", hrAttendItemFz.getAttend_name_fz());
		mode.addAttribute("attend_result_is_fz", hrAttendItemFz.getAttend_result_is_fz());
	
		
		return "hrp/hr/attendancemanagement/attend/attenditemfz/updateAttendItemFzPage";

	}
	
	/**
	 * @Description 修改数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendItemFz", method = RequestMethod.POST)
	@ResponseBody
	public String updateAttendItemFz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
try {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}

	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}

	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	String hosEmpKindJson = hrAttendItemFzService.updateAttendItemFz(mapVo);

	return hosEmpKindJson;

	} catch (Exception e) {
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
	@RequestMapping(value = "/deleteAttendItemFz", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAttendItemFz(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrAttendItemFz> listVo = JSONArray.parseArray(paramVo, HrAttendItemFz.class);
		try {
			 for (HrAttendItemFz hrAttendItemFz : listVo) {
				 hrAttendItemFz.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				 hrAttendItemFz.setHos_id(Integer.parseInt(SessionManager.getHosId()));

					str = str + hrBaseService.isExistsDataByTable("HR_ATTEND_ITEM_FZ", hrAttendItemFz.getAttend_code_fz())== null ? ""
							: hrBaseService.isExistsDataByTable("HR_ATTEND_ITEM_FZ", hrAttendItemFz.getAttend_code_fz());
					if (Strings.isNotBlank(str)) {
						falg = false;
						continue;
					}
				
			}
				if (!falg) {
					return ("{\"error\":\"删除失败，选择的考勤项目分组被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}");
				}
				
			
			 return hrAttendItemFzService.deleteAttendItemFz(listVo);

		} catch (Exception e) {
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
	@RequestMapping(value = "/queryAttendItemFz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendItemFz(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			String stationTransef = hrAttendItemFzService.queryAttendItemFz(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
}
