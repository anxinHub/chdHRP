/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

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
import com.chd.hrp.hr.entity.base.HrTitle;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrTitleService;

/**
 * 
 * @Description: 职称信息
 * @Table: HR_TITLE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/base")
public class HrTitleController extends BaseController {

	private static Logger logger = Logger.getLogger(HrTitleController.class);

	// 引入Service服务
	@Resource(name = "hrTitleService")
	private final HrTitleService hrTitleService = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/titleInfoMainPage", method = RequestMethod.GET)
	public String hrTitleMainPage(Model mode) throws Exception {

		return "hrp/hr/base/hrtitle/titleMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTitleInfoPage", method = RequestMethod.GET)
	public String hrTitleAddPage(Model mode) throws Exception {

		return "hrp/hr/base/hrtitle/titleAdd";

	}

	/**
	 * @Description 添加数据 职称信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTitleInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));

			String hrTitleJson = hrTitleService.addHrTitle(mapVo);

			return JSONObject.parseObject(hrTitleJson);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 更新跳转页面 职称信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTitleInfoPage", method = RequestMethod.GET)
	public String hrTitleUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		HrTitle hrTitle = new HrTitle();

		hrTitle = hrTitleService.queryByCode(mapVo);
		if (hrTitle != null) {

			mode.addAttribute("group_id", hrTitle.getGroup_id());
			mode.addAttribute("hos_id", hrTitle.getHos_id());
			mode.addAttribute("title_code", hrTitle.getTitle_code());
			mode.addAttribute("title_name", hrTitle.getTitle_name());
			mode.addAttribute("is_stop", hrTitle.getIs_stop());
			mode.addAttribute("sup_code", hrTitle.getSup_code());
			mode.addAttribute("is_last", hrTitle.getIs_last());
			mode.addAttribute("spell_code", hrTitle.getSpell_code());
			mode.addAttribute("wbx_code", hrTitle.getWbx_code());
			mode.addAttribute("note", hrTitle.getNote());
		}
		return "hrp/hr/base/hrtitle/titleUpdate";
	}

	/**
	 * @Description 更新数据 职称信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTitleInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));

			String hrTitleJson = hrTitleService.updateHrTitle(mapVo);

			return JSONObject.parseObject(hrTitleJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据 职称信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTitleInfo", method = RequestMethod.POST)
	@ResponseBody

	public String deleteTitleInfo(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrTitle> listVo = JSONArray.parseArray(paramVo, HrTitle.class);
		try {
			for (HrTitle hrTitle : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_TITLE", hrTitle.getTitle_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_TITLE", hrTitle.getTitle_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的职称信息被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return hrTitleService.deleteBatchTitle(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据 职称信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTitleInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String hrTitle = hrTitleService.queryHrTitle(getPage(mapVo));

		return JSONObject.parseObject(hrTitle);

	}

	/**
	 * 左侧列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTitleInfoTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAssTypeDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrTitleService.queryTitleInfoTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		return json.toString();
	}

	/**
	 * 查询上级部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySupCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String querySupCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrTitleService.querySupCode(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toString();
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHT", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHT(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTitleService.importDate(mapVo);
		return reJson;
	}

}
