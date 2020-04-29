package com.chd.hrp.ass.controller.adjustMan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
 

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.ass.service.adjustMan.AssAdjustService;

@Controller
public class AssAdjustController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAdjustController.class);

	@Resource(name = "assAdjustService")
	private final AssAdjustService assAdjustService = null;

	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;

	/**
	 * @Description 维护页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	// 主页面跳转
	@RequestMapping(value = "/hrp/ass/adjustMan/assAdjustMainPage", method = RequestMethod.GET)
	public String assAdjustMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/adjustMan/assAdjustMain";

	}

	/**
	 * @Description 维护页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	// 添加页面跳转
	@RequestMapping(value = "/hrp/ass/adjustMan/assAdjustAddPage", method = RequestMethod.GET)
	public String assAdjustAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		// 单价字段小数点后保留位数 系统参数编码
		//String para_vaue = (String) SessionManager.getAccParaMap().get("04006");

		mode.addAttribute("para_value", MyConfig.getSysPara("05006"));// 单价字段小数点后保留位数
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/adjustMan/assAdjustAdd";

	}

	/**
	 * @Description 维护页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	// 修改页面跳转
	@RequestMapping(value = "/hrp/ass/adjustMan/updateAssAdjustPage", method = RequestMethod.GET)
	public String updateAssAdjustPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String para_vaue = MyConfig.getSysPara("05006");

		mode.addAttribute("para_value", para_vaue);// 单价字段小数点后保留位数

		mode.addAttribute("para_value", para_vaue);

		Map<String, Object> matAdjust = assAdjustService.queryByCode(mapVo);

		mode.addAttribute("group_id", matAdjust.get("group_id"));
		mode.addAttribute("hos_id", matAdjust.get("hos_id"));
		mode.addAttribute("copy_code", matAdjust.get("copy_code"));
		mode.addAttribute("adjust_id", matAdjust.get("adjust_id"));
		mode.addAttribute("adjust_code", matAdjust.get("adjust_code"));
		mode.addAttribute("create_date", matAdjust.get("create_date"));
		mode.addAttribute("note", matAdjust.get("note"));
		mode.addAttribute("maker", matAdjust.get("maker"));
		mode.addAttribute("make_date", matAdjust.get("make_date"));
		mode.addAttribute("checker", matAdjust.get("checker"));
		mode.addAttribute("adjust_date", matAdjust.get("adjust_date"));
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/adjustMan/assAdjustUpdate";

	}

	/**
	 * @Description 调价单 查询
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/queryAssAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAdjust(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assAdjustJson = "";
		if (mapVo.get("show_detail").equals("1")) {
			assAdjustJson = assAdjustService.queryDetails(getPage(mapVo));
		} else {
			assAdjustJson = assAdjustService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assAdjustJson);
	}

	// 资产字典列表
	@RequestMapping(value = "/hrp/ass/adjustMan/queryAssList", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInvList = assAdjustService.queryAssList(getPage(mapVo));
		return assInvList;
	}

	/**
	 * @Description 调价单 添加
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/addAssAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssAdjust(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assAdjustJson;

		try {

			assAdjustJson = assAdjustService.add(mapVo);

		} catch (Exception e) {

			assAdjustJson = e.getMessage();

		}

		return JSONObject.parseObject(assAdjustJson);
	}

	/**
	 * @Description 调价单 查询
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/queryAssAdjustDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAdjustDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assAdjustDetailJson = assAdjustService.queryAssAdjustDetailByCode(getPage(mapVo));

		return JSONObject.parseObject(assAdjustDetailJson);
	}

	/**
	 * @Description 调价单 批量删除
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/deleteBatchAssAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAssAdjust(@RequestParam Map<String, Object> mapVo, Model mode)
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

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		String paramVo = mapVo.get("paramVo").toString();

		if (paramVo != null && !"".equals(paramVo)) {

			for (String id : paramVo.split(",")) {

				Map<String, Object> map = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("adjust_id", ids[3]);
				listVo.add(map);
			}
		}

		String assAdjustJson;

		try {

			assAdjustJson = assAdjustService.deleteBatch(listVo);

		} catch (Exception e) {

			assAdjustJson = e.getMessage();
		}

		return JSONObject.parseObject(assAdjustJson);
	}

	/**
	 * @Description 调价单 更新调价单主表及明细表数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/updateAssAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssAdjust(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assAdjustJson;

		try {

			assAdjustJson = assAdjustService.update(mapVo);

		} catch (Exception e) {

			assAdjustJson = e.getMessage();
		}

		return JSONObject.parseObject(assAdjustJson);
	}

	/**
	 * @Description 调价单 审核 修改状态
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/adjustMan/updateAssAdjustState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssAdjustState(@RequestParam Map<String, Object> mapVo, Model mode)
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

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String paramVo = mapVo.get("paramVo").toString();
		for (String id : paramVo.split(",")) {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			map.put("group_id", mapVo.get("group_id"));
			map.put("hos_id", mapVo.get("hos_id"));
			map.put("copy_code", mapVo.get("copy_code"));
			map.put("adjust_id", ids[3]);
			map.put("state", ids[4]);
			map.put("checker", SessionManager.getUserId());
			map.put("adjust_date", sdf.format(new Date()));
			listVo.add(map);
		}

		String assAdjustJson;
		try {
			assAdjustJson = assAdjustService.updateAssAdjustState(listVo);// 调价单需求不明确，暂时注释
		} catch (Exception e) {
			assAdjustJson = e.getMessage();
		}

		return JSONObject.parseObject(assAdjustJson);
	}

}
