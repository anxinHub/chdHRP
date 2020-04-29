package com.chd.hrp.hr.controller.organize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.hr.entity.orangnize.HosDuty;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HosDutyInfoService;

@Controller
@RequestMapping(value = "/hrp/hr/duty")
public class HosDutyInfoController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosDutyInfoController.class);

	// 引入Service服务
	@Resource(name = "hosDutyInfoService")
	private final HosDutyInfoService hosDutyInfoService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyInfoMainPage", method = RequestMethod.GET)
	public String dutyInfoMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutyinfo/dutyInfoMainPage";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyInfoAddPage", method = RequestMethod.GET)
	public String dutyInfoAddPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutyinfo/dutyInfoAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHosDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));

			String hosEmpKindJson = hosDutyInfoService.addHrDuty(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyInfoUpdatePage", method = RequestMethod.GET)
	public String dutyInfoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		HosDuty hrDuty = new HosDuty();

		hrDuty = hosDutyInfoService.queryHrDutyByCode(mapVo);

		if (hrDuty != null) {

			mode.addAttribute("group_id", hrDuty.getGroup_id());
			mode.addAttribute("hos_id", hrDuty.getHos_id());
			mode.addAttribute("duty_code", hrDuty.getDuty_code());
			mode.addAttribute("duty_name", hrDuty.getDuty_name());
			mode.addAttribute("duty_level_code", hrDuty.getDuty_level_code());
			mode.addAttribute("kind_code", hrDuty.getKind_code());
			mode.addAttribute("is_stop", hrDuty.getIs_stop());
			mode.addAttribute("note", hrDuty.getNote());
		}

		return "hrp/hr/organize/dutyinfo/dutyInfoUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHosDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));

			String hosEmpKindJson = hosDutyInfoService.updateHrDuty(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHosDuty", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHosDuty(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HosDuty> listVo = JSONArray.parseArray(paramVo, HosDuty.class);
		try {
			for (HosDuty hrDuty : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_DUTY", hrDuty.getDuty_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HOS_DUTY", hrDuty.getDuty_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的职务名称被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
				}
			return hosDutyInfoService.deleteBatchHrDuty(listVo);
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
	@RequestMapping(value = "/queryHosDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hosEmpKind = hosDutyInfoService.queryHrDuty(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}

	/**
	 * @Description 查询数据(左侧菜单) 职务名称
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosDutyTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStoreTabStrucTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hosDutyInfoService.queryHrDutyTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHHD", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHHD(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hosDutyInfoService.importDate(mapVo);
		return reJson;
	}
}
