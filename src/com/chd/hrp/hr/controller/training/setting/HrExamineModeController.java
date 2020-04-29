package com.chd.hrp.hr.controller.training.setting;

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
import com.chd.hrp.hr.entity.training.setting.HrExamineMode;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrExamineModeService;


/**
 * 考核方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/examinemode")
public class HrExamineModeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrExamineModeController.class);

	// 引入Service服务
	@Resource(name = "hrExamineModeService")
	private final HrExamineModeService hrExamineModeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/examineModeMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/examinemode/examineModeMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addExamineModePage", method = RequestMethod.GET)
	public String addExamineModePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/examinemode/addExamineModePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addExamineMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addExamineMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("way_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("way_name").toString()));

			String ExamineMode = hrExamineModeService.addExamineMode(mapVo);

			return JSONObject.parseObject(ExamineMode);
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
	@RequestMapping(value = "/updateExamineModePage", method = RequestMethod.GET)
	public String updateExamineModePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrExamineMode hrExamineMode = new HrExamineMode();

		hrExamineMode = hrExamineModeService.queryByCode(mapVo);

		if (hrExamineMode != null) {

			mode.addAttribute("group_id", hrExamineMode.getGroup_id());
			mode.addAttribute("hos_id", hrExamineMode.getHos_id());
			mode.addAttribute("way_code", hrExamineMode.getWay_code());
			mode.addAttribute("way_name", hrExamineMode.getWay_name());
			mode.addAttribute("spell_code", hrExamineMode.getSpell_code());
			mode.addAttribute("wbx_code", hrExamineMode.getWbx_code());
			mode.addAttribute("is_stop", hrExamineMode.getIs_stop());
			mode.addAttribute("note", hrExamineMode.getNote());
		}

		return "hrp/hr/training/setting/examinemode/updateExamineModePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamineMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateExamineMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("way_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("way_name").toString()));

			String ExamineMode = hrExamineModeService.updateExamineMode(mapVo);

			return JSONObject.parseObject(ExamineMode);
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
	@RequestMapping(value = "/deleteExamineMode", method = RequestMethod.POST)
	@ResponseBody

	public String deleteExamineMode(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrExamineMode> listVo = JSONArray.parseArray(paramVo, HrExamineMode.class);
		try {
			for (HrExamineMode hrExamineMode : listVo) {
				str =hrExamineModeService.queryUseExamineMode(hrExamineMode);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的考核方式被以下业务使用：【补考安排】。\",\"state\":\"false\"}");
			}
			return hrExamineModeService.deleteExamineMode(listVo);

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
	@RequestMapping(value = "/queryExamineMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExamineMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrExamineModeService.queryExamineMode(getPage(mapVo));

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
		String reJson = hrExamineModeService.importDate(mapVo);
		return reJson;
	}

}
