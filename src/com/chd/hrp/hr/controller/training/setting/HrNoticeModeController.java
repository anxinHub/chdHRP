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
import com.chd.hrp.hr.entity.training.setting.HrNoticeMode;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.setting.HrNoticeModeService;


/**
 * 通知方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/setting/noticemode")
public class HrNoticeModeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrNoticeModeController.class);

	// 引入Service服务
	@Resource(name = "hrNoticeModeService")
	private final HrNoticeModeService hrNoticeModeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/noticeModeMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/setting/noticemode/noticeModeMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNoticeModePage", method = RequestMethod.GET)
	public String addNoticeModePage(Model mode) throws Exception {

		return "hrp/hr/training/setting/noticemode/addNoticeModePage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNoticeMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNoticeMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String NoticeMode = hrNoticeModeService.addNoticeMode(mapVo);

			return JSONObject.parseObject(NoticeMode);
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
	@RequestMapping(value = "/updateNoticeModePage", method = RequestMethod.GET)
	public String updateNoticeModePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrNoticeMode hrNoticeMode = new HrNoticeMode();

		hrNoticeMode = hrNoticeModeService.queryByCode(mapVo);

		if (hrNoticeMode != null) {

			mode.addAttribute("group_id", hrNoticeMode.getGroup_id());
			mode.addAttribute("hos_id", hrNoticeMode.getHos_id());
			mode.addAttribute("way_code", hrNoticeMode.getWay_code());
			mode.addAttribute("way_name", hrNoticeMode.getWay_name());
			mode.addAttribute("spell_code", hrNoticeMode.getSpell_code());
			mode.addAttribute("wbx_code", hrNoticeMode.getWbx_code());
			mode.addAttribute("is_stop", hrNoticeMode.getIs_stop());
			mode.addAttribute("note", hrNoticeMode.getNote());
		}

		return "hrp/hr/training/setting/noticemode/updateNoticeModePage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNoticeMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNoticeMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("way_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("way_name").toString()));

			String NoticeMode = hrNoticeModeService.updateNoticeMode(mapVo);

			return JSONObject.parseObject(NoticeMode);
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
	@RequestMapping(value = "/deleteNoticeMode", method = RequestMethod.POST)
	@ResponseBody

	public String deleteNoticeMode(@RequestParam String paramVo, Model mode) throws Exception {
		    int str =0;
			boolean falg = true;
		List<HrNoticeMode> listVo = JSONArray.parseArray(paramVo, HrNoticeMode.class);
		try {
			for (HrNoticeMode hrNoticeMode : listVo) {
				str =hrNoticeModeService.queryUseNoticeMode(hrNoticeMode);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的通知方式被以下业务使用：【培训通知】。\",\"state\":\"false\"}");
			}
			return hrNoticeModeService.deleteNoticeMode(listVo);

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
	@RequestMapping(value = "/queryNoticeMode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryNoticeMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hosEmpKind = hrNoticeModeService.queryNoticeMode(getPage(mapVo));

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
		String reJson = hrNoticeModeService.importDate(mapVo);
		return reJson;
	}

}
