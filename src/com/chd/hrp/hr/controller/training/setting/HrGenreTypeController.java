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
import com.chd.hrp.hr.entity.training.setting.HrGenreType;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrGenreTypeService;


/**
 * 培训类别
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/genretype")
public class HrGenreTypeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrGenreTypeController.class);

	// 引入Service服务
	@Resource(name = "hrGenreTypeService")
	private final HrGenreTypeService hrGenreTypeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/genreTypeMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/genretype/genreTypeMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGenreTypePage", method = RequestMethod.GET)
	public String addGenreTypePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/genretype/addGenreTypePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addGenreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGenreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

			String GenreType = hrGenreTypeService.addGenreType(mapVo);

			return JSONObject.parseObject(GenreType);
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
	@RequestMapping(value = "/updateGenreTypePage", method = RequestMethod.GET)
	public String updateGenreTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrGenreType hrGenreType = new HrGenreType();

		hrGenreType = hrGenreTypeService.queryByCode(mapVo);

		if (hrGenreType != null) {

			mode.addAttribute("group_id", hrGenreType.getGroup_id());
			mode.addAttribute("hos_id", hrGenreType.getHos_id());
			mode.addAttribute("type_code", hrGenreType.getType_code());
			mode.addAttribute("type_name", hrGenreType.getType_name());
			mode.addAttribute("spell_code", hrGenreType.getSpell_code());
			mode.addAttribute("wbx_code", hrGenreType.getWbx_code());
			mode.addAttribute("is_stop", hrGenreType.getIs_stop());
			mode.addAttribute("note", hrGenreType.getNote());
		}

		return "hrp/hr/training/setting/genretype/updateGenreTypePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateGenreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGenreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}


			String GenreType = hrGenreTypeService.updateGenreType(mapVo);

			return JSONObject.parseObject(GenreType);
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
	@RequestMapping(value = "/deleteGenreType", method = RequestMethod.POST)
	@ResponseBody

	public String deleteGenreType(@RequestParam String paramVo, Model mode) throws Exception {
		   int str=0;
			boolean falg = true;
		List<HrGenreType> listVo = JSONArray.parseArray(paramVo, HrGenreType.class);
		try {
			for (HrGenreType hrGenreType : listVo) {
				str =hrGenreTypeService.queryUseGenreType(hrGenreType);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的培训类别被以下业务使用：【培训计划】。\",\"state\":\"false\"}");
			}
			return hrGenreTypeService.deleteGenreType(listVo);

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
	@RequestMapping(value = "/queryGenreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGenreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrGenreTypeService.queryGenreType(getPage(mapVo));

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
		String reJson = hrGenreTypeService.importDate(mapVo);
		return reJson;
	}

}
