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
import com.chd.hrp.hr.entity.orangnize.HrStationSalary;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrStationSalaryService;

/**
 * 
 * @author Administrator 岗位薪资标准维护
 */
@Controller
@RequestMapping(value = "/hrp/hr/organize/station")
public class HrStationSalaryController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStationSalaryController.class);

	// 引入Service服务
	@Resource(name = "hrStationSalaryService")
	private final HrStationSalaryService hrStationSalaryService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationSalaryMainPage", method = RequestMethod.GET)
	public String StationSalaryMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/stationsalary/stationSalaryMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationSalaryPage", method = RequestMethod.GET)
	public String StationSalaryAddPage(Model mode) throws Exception {

		return "hrp/hr/organize/stationsalary/stationSalaryAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStationSalary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrStationSalaryService.addStationSalary(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
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
	@RequestMapping(value = "/updateStationSalaryPage", method = RequestMethod.GET)
	public String updateStationSalaryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrStationSalary hrStationSalary = new HrStationSalary();

		hrStationSalary = hrStationSalaryService.queryByCodeStationSalary(mapVo);

		return "hrp/hr/organize/stationsalary/stationSalaryUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStationSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStationSalary(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrStationSalaryService.updateStationSalary(mapVo);

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
	@RequestMapping(value = "/deleteStationSalary", method = RequestMethod.POST)
	@ResponseBody

	public String deleteStationSalary(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrStationSalary> listVo = JSONArray.parseArray(paramVo, HrStationSalary.class);
		try {
			/**
			 * 判断是否被引用
			 */
			for (HrStationSalary hrStationSalary : listVo) {
				/*
				 * str = str + hrBaseService.isExistsDataByTable("HR_DUTY",
				 * hrStationSalary.getKind_code())== null ? "" :
				 * hrBaseService.isExistsDataByTable("HR_DUTY", hrStationSalary.getKind_code());
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
			hrStationSalaryService.deleteBatchStationSalary(listVo);
			return null;

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
	@RequestMapping(value = "/queryStationSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStationSalary(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String StationSalaryTransef = hrStationSalaryService.queryHrStationSalary(getPage(mapVo));

		return JSONObject.parseObject(StationSalaryTransef);

	}
	/**
	 * @Description 查询数据(左侧菜单) 岗位薪资标准
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStationSalaryTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStationDefTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrStationSalaryService.queryStationSalaryDefTree(mapVo);

	}
}
