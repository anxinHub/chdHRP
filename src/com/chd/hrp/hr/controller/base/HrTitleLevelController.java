package com.chd.hrp.hr.controller.base;

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
import com.chd.hrp.hr.entity.base.HrTitleLevel;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrTitleLevelService;

/**
 * 职称等级
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/base")
public class HrTitleLevelController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTitleLevelController.class);

	// 引入Service服务
	@Resource(name = "hrTitleLevelService")
	private final HrTitleLevelService hrTitleLevelService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/titleLevelMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/base/titleLevel/titleLevelMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTitleLevelPage", method = RequestMethod.GET)
	public String addTitleLevelPage(Model mode) throws Exception {

		return "hrp/hr/base/titleLevel/addTitleLevelPage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTitleLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTitleLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_level_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_level_name").toString()));

			String titleLevel = hrTitleLevelService.addTitleLevel(mapVo);

			return JSONObject.parseObject(titleLevel);
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
	@RequestMapping(value = "/updateTitleLevelPage", method = RequestMethod.GET)
	public String updateTitleLevelPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrTitleLevel hrTitleLevel = new HrTitleLevel();

		hrTitleLevel = hrTitleLevelService.queryByCode(mapVo);

		if (hrTitleLevel != null) {

			mode.addAttribute("group_id", hrTitleLevel.getGroup_id());
			mode.addAttribute("hos_id", hrTitleLevel.getHos_id());
			mode.addAttribute("title_level_code", hrTitleLevel.getTitle_level_code());
			mode.addAttribute("title_level_name", hrTitleLevel.getTitle_level_name());
			mode.addAttribute("spell_code", hrTitleLevel.getSpell_code());
			mode.addAttribute("wbx_code", hrTitleLevel.getWbx_code());
			mode.addAttribute("is_stop", hrTitleLevel.getIs_stop());
			mode.addAttribute("note", hrTitleLevel.getNote());
		}

		return "hrp/hr/base/titleLevel/updateTitleLevelPage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTitleLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTitleLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_level_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_level_name").toString()));

			String titleLevel = hrTitleLevelService.updateTitleLevel(mapVo);

			return JSONObject.parseObject(titleLevel);
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
	@RequestMapping(value = "/deleteTitleLevel", method = RequestMethod.POST)
	@ResponseBody

	public String deleteTitleLevel(@RequestParam String paramVo, Model mode) throws Exception {
		    String str = "";
			boolean falg = true;
		List<HrTitleLevel> listVo = JSONArray.parseArray(paramVo, HrTitleLevel.class);
		try {
			for (HrTitleLevel hrTitleLevel : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_TITLE_LEVEL", hrTitleLevel.getTitle_level_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_TITLE_LEVEL", hrTitleLevel.getTitle_level_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的职称等级被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return hrTitleLevelService.deleteTitleLevel(listVo);

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
	@RequestMapping(value = "/queryTitleLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTitleLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrTitleLevelService.queryTitleLevel(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHTL", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHTL(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTitleLevelService.importDate(mapVo);
		return reJson;
	}

}
