package com.chd.hrp.hr.controller.organize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.hr.entity.orangnize.HrCompetencyTarget;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrCompetencyTargetService;

/**
 * 
 * @author Administrator 能力素质指标INDICATOR
 */

@Controller
@RequestMapping(value = "/hrp/hr/organize/competency")
public class HrCompetencyTargetController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCompetencyTargetController.class);

	// 引入Service服务

	@Resource(name = "hrCompetencyTargetService")
	private final HrCompetencyTargetService hrCompetencyTargetService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/competencyTargetMainPage", method = RequestMethod.GET)
	public String competencyTargetMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/competency/competencyTargetMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompetencyTargetPage", method = RequestMethod.GET)
	public String addCompetencyTargetPage(Model mode) throws Exception {

		return "hrp/hr/organize/competency/addCompetencyTarget";

	}

	/**
	 * 添加保存
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompetencyTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CompetencyTargetAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("indicator_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("indicator_name").toString()));
		try {
			String deptJson = hrCompetencyTargetService.CompetencyTargetAdd(mapVo);

			return JSONObject.parseObject(deptJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompetencyTargetPage", method = RequestMethod.GET)
	public String updateCompetencyTargetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrCompetencyTarget hrCompetencyTarget = new HrCompetencyTarget();

		hrCompetencyTarget = hrCompetencyTargetService.queryByCode(mapVo);
		mode.addAttribute("group_id", hrCompetencyTarget.getGroup_id());
		mode.addAttribute("hos_id", hrCompetencyTarget.getGroup_id());
		mode.addAttribute("copy_code", hrCompetencyTarget.getCopy_code());
		mode.addAttribute("indicator_code", hrCompetencyTarget.getIndicator_code());
		mode.addAttribute("indicator_name", hrCompetencyTarget.getIndicator_name());
		mode.addAttribute("is_stop", hrCompetencyTarget.getIs_stop());
		mode.addAttribute("note", hrCompetencyTarget.getNote());
		return "hrp/hr/organize/competency/updateCompetencyTarget";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompetencyTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCompetencyTarget(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("indicator_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("indicator_name").toString()));
		try {
			String hosEmpindicatorJson = hrCompetencyTargetService.updateCompetencyTarget(mapVo);

			return JSONObject.parseObject(hosEmpindicatorJson);
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
	@RequestMapping(value = "/deleteCompetencyTarget", method = RequestMethod.POST)
	@ResponseBody

	public String deleteCompetencyTarget(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrCompetencyTarget> listVo = JSONArray.parseArray(paramVo, HrCompetencyTarget.class);
		try {
			/**
			 * 判断是否被引用
			 */
			for (HrCompetencyTarget hrCompetencyTarget : listVo) {
				/*
				 * str = str + hrBaseService.isExistsDataByTable("HR_DUTY",
				 * hrCompetencyTarget.getindicator_code())== null ? "" :
				 * hrBaseService.isExistsDataByTable("HR_DUTY",
				 * hrCompetencyTarget.getindicator_code());
				 */
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的职工休假申请被以下业务使用：【" + str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}");
			}
			return hrCompetencyTargetService.deleteBatchCompetencyTarget(listVo);
			

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
	@RequestMapping(value = "/queryCompetencyTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCompetencyTarget(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String CompetencyTargetTransef = hrCompetencyTargetService.queryHrCompetencyTarget(getPage(mapVo));

		return JSONObject.parseObject(CompetencyTargetTransef);

	}

}
