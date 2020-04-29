package com.chd.hrp.hr.controller.organize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HosDutyLevelService;


@Controller
@RequestMapping(value = "/hrp/hr/duty")
public class HosDutyLevelController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosDutyLevelController.class);
	// 引入Service服务
	@Resource(name = "hosDutyLevelService")
	private final HosDutyLevelService hosDutyLevelService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyLevelMainPage", method = RequestMethod.GET)
	public String dutyLevelMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutylevel/dutyLevelMainPage";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyLevelAddPage", method = RequestMethod.GET)
	public String dutyLevelAddPage(Model mode) throws Exception {
		return "hrp/hr/organize/dutylevel/dutyLevelAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHosDutyLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosDutyLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_level_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_level_name").toString()));

		String hrJson;
		try {
			hrJson = hosDutyLevelService.addHrDutyLevel(mapVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/dutyLevelUpdatePage", method = RequestMethod.GET)
	public String dutyLevelUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HosDutyLevel hrDutyLevel = new HosDutyLevel();

		hrDutyLevel = hosDutyLevelService.queryByCode(mapVo);

		if (hrDutyLevel != null) {

			mode.addAttribute("group_id", hrDutyLevel.getGroup_id());
			mode.addAttribute("hos_id", hrDutyLevel.getHos_id());
			mode.addAttribute("duty_level_code", hrDutyLevel.getDuty_level_code());
			mode.addAttribute("duty_level_name", hrDutyLevel.getDuty_level_name());
			mode.addAttribute("is_stop", hrDutyLevel.getIs_stop());
			mode.addAttribute("note", hrDutyLevel.getNote());
		}

		return "hrp/hr/organize/dutylevel/dutyLevelUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHosDutyLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosDutyLevel(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_level_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_level_name").toString()));

			String hosEmpKindJson = hosDutyLevelService.updateHrDutyLevel(mapVo);

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
	@RequestMapping(value = "/deleteHosDutyLevel", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHosDutyLevel(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		List<HosDutyLevel> listVo = JSONArray.parseArray(paramVo, HosDutyLevel.class);
		try {

			/*	for (HosDutyLevel hrDutyLevel : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_DUTY_LEVEL", hrDutyLevel.getDuty_level_code()) == null ? "" : hrBaseService
						.isExistsDataByTable("HOS_DUTY_LEVEL", hrDutyLevel.getDuty_level_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}

			}*/
			List<HosDutyLevel> falg=hosDutyLevelService.queryListDuty(listVo);
			if (falg.size()>0) {
				return ("{\"error\":\"删除失败，选择的职务等级被以下业务使用：【职务信息】。\",\"state\":\"false\"}");
			}
			return hosDutyLevelService.deleteHrDutyLevel(listVo);

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
	@RequestMapping(value = "/queryHosDutyLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosDutyLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String hosEmpKind = hosDutyLevelService.queryHrDutyLevel(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}

	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importDateHDL", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHDL(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		String reJson = hosDutyLevelService.importDate(mapVo);
		return reJson;
	}
}
