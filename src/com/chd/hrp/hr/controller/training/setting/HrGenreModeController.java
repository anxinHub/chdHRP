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
import com.chd.hrp.hr.entity.training.setting.HrGenreMode;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrGenreModeService;


/**
 * 培训方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/genremode")
public class HrGenreModeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrGenreModeController.class);

	// 引入Service服务
	@Resource(name = "hrGenreModeService")
	private final HrGenreModeService hrGenreModeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/genreModeMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/genremode/genreModeMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGenreModePage", method = RequestMethod.GET)
	public String addgenreModePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/genremode/addGenreModePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGenreMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addgenreMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String genreMode = hrGenreModeService.addGenreMode(mapVo);

			return JSONObject.parseObject(genreMode);
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
	@RequestMapping(value = "/updateGenreModePage", method = RequestMethod.GET)
	public String updategenreModePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrGenreMode hrgenreMode = new HrGenreMode();

		hrgenreMode = hrGenreModeService.queryByCode(mapVo);

		if (hrgenreMode != null) {

			mode.addAttribute("group_id", hrgenreMode.getGroup_id());
			mode.addAttribute("hos_id", hrgenreMode.getHos_id());
			mode.addAttribute("way_code", hrgenreMode.getWay_code());
			mode.addAttribute("way_name", hrgenreMode.getWay_name());
			mode.addAttribute("spell_code", hrgenreMode.getSpell_code());
			mode.addAttribute("wbx_code", hrgenreMode.getWbx_code());
			mode.addAttribute("is_stop", hrgenreMode.getIs_stop());
			mode.addAttribute("note", hrgenreMode.getNote());
		}

		return "hrp/hr/training/setting/genremode/updateGenreModePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateGenreMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updategenreMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("way_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("way_name").toString()));

			String genreMode = hrGenreModeService.updateGenreMode(mapVo);

			return JSONObject.parseObject(genreMode);
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
	@RequestMapping(value = "/deleteGenreMode", method = RequestMethod.POST)
	@ResponseBody

	public String deletegenreMode(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrGenreMode> listVo = JSONArray.parseArray(paramVo, HrGenreMode.class);
		try {
			for (HrGenreMode hrgenreMode : listVo) {
				str =hrGenreModeService.queryUseGenreMode(hrgenreMode);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的培训方式被以下业务使用：【培训计划】。\",\"state\":\"false\"}");
			}
			return hrGenreModeService.deleteGenreMode(listVo);

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
	@RequestMapping(value = "/queryGenreMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querygenreMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrGenreModeService.queryGenreMode(getPage(mapVo));

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
		String reJson = hrGenreModeService.importDate(mapVo);
		return reJson;
	}

}
