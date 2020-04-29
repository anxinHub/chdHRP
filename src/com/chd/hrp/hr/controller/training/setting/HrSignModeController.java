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
import com.chd.hrp.hr.entity.training.setting.HrSignMode;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrSignModeService;


/**
 * 签到方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/signmode")
public class HrSignModeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrSignModeController.class);

	// 引入Service服务
	@Resource(name = "hrSignModeService")
	private final HrSignModeService hrSignModeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/signModeMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/signmode/signModeMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSignModePage", method = RequestMethod.GET)
	public String addsignModePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/signmode/addSignModePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSignMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addsignMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String signMode = hrSignModeService.addSignMode(mapVo);

			return JSONObject.parseObject(signMode);
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
	@RequestMapping(value = "/updateSignModePage", method = RequestMethod.GET)
	public String updatesignModePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrSignMode hrsignMode = new HrSignMode();

		hrsignMode = hrSignModeService.queryByCode(mapVo);

		if (hrsignMode != null) {

			mode.addAttribute("group_id", hrsignMode.getGroup_id());
			mode.addAttribute("hos_id", hrsignMode.getHos_id());
			mode.addAttribute("way_code", hrsignMode.getWay_code());
			mode.addAttribute("way_name", hrsignMode.getWay_name());
			mode.addAttribute("spell_code", hrsignMode.getSpell_code());
			mode.addAttribute("wbx_code", hrsignMode.getWbx_code());
			mode.addAttribute("is_stop", hrsignMode.getIs_stop());
			mode.addAttribute("note", hrsignMode.getNote());
		}

		return "hrp/hr/training/setting/signmode/updateSignModePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSignMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatesignMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("way_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("way_name").toString()));

			String signMode = hrSignModeService.updateSignMode(mapVo);

			return JSONObject.parseObject(signMode);
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
	@RequestMapping(value = "/deleteSignMode", method = RequestMethod.POST)
	@ResponseBody

	public String deletesignMode(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrSignMode> listVo = JSONArray.parseArray(paramVo, HrSignMode.class);
		try {
			for (HrSignMode hrSignMode : listVo) {
				str =hrSignModeService.queryUseSignMode(hrSignMode);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的通知方式被以下业务使用：【培训通知】。\",\"state\":\"false\"}");
			}
			return hrSignModeService.deleteSignMode(listVo);

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
	@RequestMapping(value = "/querySignMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querysignMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrSignModeService.querySignMode(getPage(mapVo));

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
		String reJson = hrSignModeService.importDate(mapVo);
		return reJson;
	}

}
