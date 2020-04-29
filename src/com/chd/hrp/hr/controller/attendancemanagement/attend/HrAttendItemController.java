package com.chd.hrp.hr.controller.attendancemanagement.attend;

import java.util.HashMap;
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
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrAttendItemService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrCommonService;

/**
 * 考勤项目设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAttendItemController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendItemController.class);

	// 引入Service服务
	@Resource(name = "hrAttendItemService")
	private final HrAttendItemService hrAttendItemService = null;

	@Resource(name = "hrCommonService")
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
	@RequestMapping(value = "/hrAttendItemMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/attend/attenditem/attendItemMainPage";

	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hraddAttendItemPage", method = RequestMethod.GET)
	public String hraddAttendItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("attend_types", mapVo.get("attend_types"));
		return "hrp/hr/attendancemanagement/attend/attenditem/addAttendItemPage";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendItem", method = RequestMethod.POST)
	@ResponseBody
	public String addAttendItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hosEmpKindJson = hrAttendItemService.addAttendItem(mapVo);

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
	@RequestMapping(value = "/updateAttendItemPage", method = RequestMethod.GET)
	public String updateAttendItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrAttendItem hrAttendItem = hrAttendItemService.queryByCodeAttendItem(mapVo);
		mode.addAttribute("attend_code", hrAttendItem.getAttend_code());
		mode.addAttribute("attend_name", hrAttendItem.getAttend_name());
		mode.addAttribute("attend_shortname", hrAttendItem.getAttend_shortname());
		mode.addAttribute("attend_types", hrAttendItem.getAttend_types());
		mode.addAttribute("is_default", hrAttendItem.getIs_default());
		mode.addAttribute("attend_ed_is", hrAttendItem.getAttend_ed_is());
		mode.addAttribute("is_jx", hrAttendItem.getIs_jx());
		mode.addAttribute("is_stop", hrAttendItem.getIs_stop());
		mode.addAttribute("is_calculate", hrAttendItem.getIs_calculate());
		mode.addAttribute("control_type", hrAttendItem.getControl_type());
		mode.addAttribute("max_ed", hrAttendItem.getMax_ed());
		mode.addAttribute("note", hrAttendItem.getNote());

		return "hrp/hr/attendancemanagement/attend/attenditem/updateAttendItemPage";

	}

	/**
	 * @Description 修改数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAttendItem", method = RequestMethod.POST)
	@ResponseBody
	public String updateAttendItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hosEmpKindJson = hrAttendItemService.updateAttendItem(mapVo);

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
	@RequestMapping(value = "/deleteAttendItem", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAttendItem(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrAttendItem> listVo = JSONArray.parseArray(paramVo, HrAttendItem.class);
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		StringBuffer attend_codes = new StringBuffer();
		try {
			for (HrAttendItem hrAttendItem : listVo) {
				hrAttendItem.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAttendItem.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				//内置数据错误，暂时去掉判断
				str += str + hrBaseService.isExistsDataByTable("HR_ATTEND_ITEM", hrAttendItem.getAttend_code()) == null ? "" : hrBaseService
						.isExistsDataByTable("HR_ATTEND_ITEM", hrAttendItem.getAttend_code());
				System.err.println(str.getClass());
				if (!str.equals("null")&&Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
				
				attend_codes.append(hrAttendItem.getAttend_code()).append(",");
			}
		
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的考勤项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}");
			}
			if(attend_codes.length() > 0){
				mapVo.put("attend_codes", attend_codes.substring(0, attend_codes.length()-1));
			}
			
			return hrAttendItemService.deleteAttendItem(mapVo);

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
	@RequestMapping(value = "/queryAttendItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String stationTransef = hrAttendItemService.queryAttendItem(mapVo);

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * 单位信息下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosName", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String subjType = hrAttendItemService.queryHosName(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			return json.toString();
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 选项下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryOption(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("user_id", SessionManager.getUserId());
			String subjType = hrAttendItemService.queryHrFiiedData(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			return json.toString();
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
