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
import com.chd.hrp.hr.entity.orangnize.HosStationLevel;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HosStationLevelService;

@Controller
@RequestMapping(value = "/hrp/hr/organize/station")
public class HosStationLevelController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosStationLevelController.class);
	// 引入Service服务
	@Resource(name = "hosStationLevelService")
	private final HosStationLevelService hosStationLevelService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationLevelMainPage", method = RequestMethod.GET)
	public String stationLevelMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/stationlevel/stationLevelMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationLevelAddPage", method = RequestMethod.GET)
	public String stationLevelAddPage(Model mode) throws Exception {
		return "hrp/hr/organize/stationlevel/stationLevelAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStationLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_level_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_level_name").toString()));
		try {
			String stationLevelJson = hosStationLevelService.addHrStationLevel(mapVo);

			return JSONObject.parseObject(stationLevelJson);
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
	@RequestMapping(value = "/stationLevelUpdatePage", method = RequestMethod.GET)
	public String stationLevelUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HosStationLevel hrStationLevel = new HosStationLevel();

		hrStationLevel = hosStationLevelService.queryHrStationLevelByCode(mapVo);

		if (hrStationLevel != null) {

			mode.addAttribute("group_id", hrStationLevel.getGroup_id());
			mode.addAttribute("hos_id", hrStationLevel.getHos_id());
			mode.addAttribute("station_level_code", hrStationLevel.getStation_level_code());
			mode.addAttribute("station_level_name", hrStationLevel.getStation_level_name());
			mode.addAttribute("is_stop", hrStationLevel.getIs_stop());
			mode.addAttribute("note", hrStationLevel.getNote());
		}

		return "hrp/hr/organize/stationlevel/stationLevelUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStationLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStationLevel(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_level_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_level_name").toString()));
		try {
			String hosEmpKindJson = hosStationLevelService.updateHrStationLevel(mapVo);

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
	@RequestMapping(value = "/deleteStationLevel", method = RequestMethod.POST)
	@ResponseBody
	public String deleteStationLevel(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		List<HosStationLevel> listVo = JSONArray.parseArray(paramVo, HosStationLevel.class);
		try {

	/*		for (HosStationLevel hrDutyLevel : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_STATION_LEVEL", hrDutyLevel.getStation_level_code()) == null ? "" : hrBaseService
						.isExistsDataByTable("HOS_STATION_LEVEL", hrDutyLevel.getStation_level_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}

			}*/
			List<HosStationLevel> falg=hosStationLevelService.queryListLevel(listVo);
			if (falg.size()>0) {
				return ("{\"error\":\"删除失败，选择的职务等级被以下业务使用：【岗位信息】。\",\"state\":\"false\"}");
			}
			return hosStationLevelService.deleteHrStationLevel(listVo);

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
	@RequestMapping(value = "/queryStationLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStationLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String hosEmpKind = hosStationLevelService.queryStationLevel(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}

	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importDateHDL", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHDL(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request) throws Exception {
		String reJson = hosStationLevelService.importDate(mapVo);
		return reJson;
	}
}
