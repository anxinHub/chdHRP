package com.chd.hrp.ass.controller.inspection;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.controller.ins.AssInsDetailController;
import com.chd.hrp.ass.entity.inspection.AssInspectionDetail;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
import com.chd.hrp.ass.service.inspection.AssInspectionDetailService;
import com.chd.hrp.ass.service.inspection.AssInspectionMainService;

@Controller
public class AssInspectionDetailController extends BaseController {
	private static Logger logger = Logger.getLogger(AssInsDetailController.class);

	// 引入Service服务
	@Resource(name = "assInspectionDetailService")
	private final AssInspectionDetailService assInspectionDetailService = null;
	@Resource(name = "assInspectionMainService")
	private final AssInspectionMainService assInspectionMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/assInspectionDetailMainPage", method = RequestMethod.GET)
	public String assInspectionDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assinspectiondetail/assInspectionDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/assInspectionDetailAddPage", method = RequestMethod.GET)
	public String assInspectionDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assinspectiondetail/assInspectionDetailAdd";

	}

	/**
	 * @Description 添加数据 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/addAssInspectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInspectionDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		try {
			String assInspectionDetailJson = assInspectionDetailService.addAssInspectionDetail(mapVo);

			return JSONObject.parseObject(assInspectionDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/assInspectionDetailUpdatePage", method = RequestMethod.GET)
	public String assInspectionDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssInspectionDetail assInspectionDetail = new AssInspectionDetail();

		assInspectionDetail = assInspectionDetailService.queryAssInspectionDetailByCode(mapVo);

		mode.addAttribute("group_id", assInspectionDetail.getGroup_id());
		mode.addAttribute("hos_id", assInspectionDetail.getHos_id());
		mode.addAttribute("copy_code", assInspectionDetail.getCopy_code());
		mode.addAttribute("ins_id", assInspectionDetail.getIns_id());
		mode.addAttribute("detail_id", assInspectionDetail.getDetail_id());
		mode.addAttribute("ass_card_no", assInspectionDetail.getAss_card_no());
		mode.addAttribute("state", assInspectionDetail.getState());
		mode.addAttribute("is_rep", assInspectionDetail.getIs_rep());
		mode.addAttribute("is_main", assInspectionDetail.getIs_main());
		mode.addAttribute("is_result", assInspectionDetail.getIs_result());
		mode.addAttribute("note", assInspectionDetail.getNote());

		return "hrp/ass/assinspectiondetail/assInspectionDetailUpdate";
	}

	/**
	 * @Description 更新数据 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/updateAssInspectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInspectionDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String assInspectionDetailJson = assInspectionDetailService.updateAssInspectionDetail(mapVo);

			return JSONObject.parseObject(assInspectionDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/addOrUpdateAssInspectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssInspectionDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assInspectionDetailJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AssInspectionMain A = assInspectionMainService.queryAssInspectionMainByUniqueness(mapVo);
		try {
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				if (detailVo.get("group_id") == null) {
					detailVo.put("group_id", SessionManager.getGroupId());
				}

				if (detailVo.get("hos_id") == null) {
					detailVo.put("hos_id", SessionManager.getHosId());
				}

				if (detailVo.get("copy_code") == null) {
					detailVo.put("copy_code", SessionManager.getCopyCode());
				}

				detailVo.put("ins_id", mapVo.get("ins_id"));

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				}

				assInspectionDetailJson = assInspectionDetailService.addOrUpdateAssInspectionDetail(detailVo);

			}
			return JSONObject.parseObject(assInspectionDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 删除数据 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/deleteAssInspectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInspectionDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ins_id", ids[3]);
			mapVo.put("detail_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assInspectionDetailJson = assInspectionDetailService.deleteBatchAssInspectionDetail(listVo);

			return JSONObject.parseObject(assInspectionDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 050601 资产巡检明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspectiondetail/queryAssInspectionDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInspectionDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		if (!mapVo.containsKey("ins_id")) {
			mapVo.put("ins_id", "0");
		}
		String assInspectionDetail = assInspectionDetailService.queryAssInspectionDetail(getPage(mapVo));

		return JSONObject.parseObject(assInspectionDetail);

	}

}
