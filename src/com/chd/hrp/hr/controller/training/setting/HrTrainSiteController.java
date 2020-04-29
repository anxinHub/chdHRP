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
import com.chd.hrp.hr.entity.training.setting.HrTrainSite;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrTrainSiteService;


/**
 * 培训地点
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/trainsite")
public class HrTrainSiteController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTrainSiteController.class);

	// 引入Service服务
	@Resource(name = "hrTrainSiteService")
	private final HrTrainSiteService hrTrainSiteService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/trainSiteMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/trainsite/trainSiteMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTrainSitePage", method = RequestMethod.GET)
	public String addTrainSitePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/trainsite/addTrainSitePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTrainSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addTrainSite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("site_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("site_name").toString()));

			String TrainSite = hrTrainSiteService.addTrainSite(mapVo);

			return JSONObject.parseObject(TrainSite);
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
	@RequestMapping(value = "/updateTrainSitePage", method = RequestMethod.GET)
	public String updateTrainSitePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrTrainSite hrTrainSite = new HrTrainSite();

		hrTrainSite = hrTrainSiteService.queryByCode(mapVo);

		if (hrTrainSite != null) {

			mode.addAttribute("group_id", hrTrainSite.getGroup_id());
			mode.addAttribute("hos_id", hrTrainSite.getHos_id());
			mode.addAttribute("site_code", hrTrainSite.getSite_code());
			mode.addAttribute("site_name", hrTrainSite.getSite_name());
			mode.addAttribute("spell_code", hrTrainSite.getSpell_code());
			mode.addAttribute("wbx_code", hrTrainSite.getWbx_code());
			mode.addAttribute("is_stop", hrTrainSite.getIs_stop());
			mode.addAttribute("note", hrTrainSite.getNote());
		}

		return "hrp/hr/training/setting/trainsite/updateTrainSitePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTrainSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTrainSite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("site_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("site_name").toString()));

			String TrainSite = hrTrainSiteService.updateTrainSite(mapVo);

			return JSONObject.parseObject(TrainSite);
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
	@RequestMapping(value = "/deleteTrainSite", method = RequestMethod.POST)
	@ResponseBody

	public String deleteTrainSite(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrTrainSite> listVo = JSONArray.parseArray(paramVo, HrTrainSite.class);
		try {
			for (HrTrainSite hrTrainSite : listVo) {
				str =hrTrainSiteService.queryUseTrainSite(hrTrainSite);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的培训地点被以下业务使用：【培训记录】。\",\"state\":\"false\"}");
			}
			return hrTrainSiteService.deleteTrainSite(listVo);

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
	@RequestMapping(value = "/queryTrainSite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTrainSite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrTrainSiteService.queryTrainSite(getPage(mapVo));

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
		String reJson = hrTrainSiteService.importDate(mapVo);
		return reJson;
	}

}
