/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.disposal;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.disposal.AssDisposalDetail;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.disposal.AssDisposalDetailService;
import com.chd.hrp.ass.service.disposal.AssDisposalMainService;

/**
 * 
 * @Description: 051001资产处置主表
 * @Table: ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDisposalMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDisposalMainController.class);

	// 引入Service服务
	@Resource(name = "assDisposalMainService")
	private final AssDisposalMainService assDisposalMainService = null;

	@Resource(name = "assDisposalDetailService")
	private final AssDisposalDetailService assDisposalDetailService = null;

	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalMainMainPage", method = RequestMethod.GET)
	public String assDisposalMainMainPage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalMainMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalMainAddPage", method = RequestMethod.GET)
	public String assDisposalMainAddPage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalMainAdd";

	}

	/**
	 * @Description 按盘亏生成页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalMainInitPage", method = RequestMethod.GET)
	public String assDisposalMainInitPage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalMainInit";

	}

	/**
	 * @Description 添加数据 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/addAssDisposalMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDisposalMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String assDisposalDetailJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

		mapVo.put("audit_emp", "");

		mapVo.put("audit_date", "");

		mapVo.put("state", "0");
		try {
			String assDisposalMainJson = assDisposalMainService.addOrUpdateAssDisposalMain(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assDisposalMainJson);
			String dis_id = (String) jsonObj.get("dis_id");
			String ass_dis_no = (String) jsonObj.get("ass_dis_no");

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("ass_dis_id", dis_id);

				if (detailVo.get("ass_detail_id") == null) {
					detailVo.put("ass_detail_id", "0");
				} else {
					detailVo.put("ass_detail_id", detailVo.get("ass_detail_id"));
				}

				assDisposalDetailJson = assDisposalDetailService.addOrUpdateAssDisposalDetail(detailVo);

			}
			JSONObject json = JSONArray.parseObject(assDisposalDetailJson);
			json.put("dis_id", dis_id);
			json.put("ass_dis_no", ass_dis_no);
			return JSONObject.parseObject(json.toJSONString());
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalMainUpdatePage", method = RequestMethod.GET)
	public String assDisposalMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalMain assDisposalMain = new AssDisposalMain();

		assDisposalMain = assDisposalMainService.queryAssDisposalMainByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assDisposalMain.getGroup_id());

		mode.addAttribute("hos_id", assDisposalMain.getHos_id());

		mode.addAttribute("copy_code", assDisposalMain.getCopy_code());

		mode.addAttribute("year_month", assDisposalMain.getYear_month());

		mode.addAttribute("dis_id", assDisposalMain.getDis_id());

		mode.addAttribute("ass_dis_no", assDisposalMain.getAss_dis_no());

		mode.addAttribute("bus_type_code", assDisposalMain.getBus_type_code());

		mode.addAttribute("bus_type_name", assDisposalMain.getBus_type_name());

		mode.addAttribute("ass_nature", assDisposalMain.getAss_nature());

		mode.addAttribute("naturs_name", assDisposalMain.getNaturs_name());

		mode.addAttribute("card", assDisposalMain.getCard());

		mode.addAttribute("store_id", assDisposalMain.getStore_id());

		mode.addAttribute("store_no", assDisposalMain.getStore_no());

		mode.addAttribute("store_name", assDisposalMain.getStore_name());

		mode.addAttribute("dept_id", assDisposalMain.getDept_id());

		mode.addAttribute("dept_no", assDisposalMain.getDept_no());

		mode.addAttribute("dept_name", assDisposalMain.getDept_name());

		if (assDisposalMain.getNote() == null) {

			mode.addAttribute("note", "");

		} else {

			mode.addAttribute("note", assDisposalMain.getNote());

		}

		mode.addAttribute("create_emp", assDisposalMain.getCreate_emp());

		mode.addAttribute("create_date", sdf.format(assDisposalMain.getCreate_date()));

		if (assDisposalMain.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assDisposalMain.getAudit_emp());

		}

		if (assDisposalMain.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assDisposalMain.getAudit_date()));

		}

		return "hrp/ass/assdisposalmain/assDisposalMainUpdate";
	}

	/**
	 * @Description 更新数据 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/updateAssDisposalMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDisposalMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("audit_date").toString() == "") {

			mapVo.put("audit_date", "");

		}

		else {

			mapVo.put("audit_date", DateUtil.stringToDate(mapVo.get("audit_date").toString(), "yyyy-MM-dd"));

		}
		try {
			String assDisposalMainJson = assDisposalMainService.updateAssDisposalMain(mapVo);

			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/addOrUpdateAssDisposalMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssDisposalMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assDisposalMainJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			try {
				assDisposalMainJson = assDisposalMainService.addOrUpdateAssDisposalMain(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assDisposalMainJson);
	}

	/**
	 * @Description 删除数据 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/deleteAssDisposalMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assDisposalMainJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);
			AssDisposalMain assDisposalMain = assDisposalMainService.queryAssDisposalMainByCode(mapVo);
			if (assDisposalMain.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除! \"}");
		}
		try {
			assDisposalDetailService.deleteBatchAssDisposalDetail(listVo);
			assDisposalMainJson = assDisposalMainService.deleteBatchAssDisposalMain(listVo);
			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/queryAssDisposalMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDisposalMain = assDisposalMainService.queryAssDisposalMain(getPage(mapVo));

		return JSONObject.parseObject(assDisposalMain);

	}

	/**
	 * @Description 查询数据(按照新建状态) 051001资产处置主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/queryAssDisposalState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDisposalMain = assDisposalMainService.queryAssDisposalState(getPage(mapVo));

		return JSONObject.parseObject(assDisposalMain);

	}

	/**
	 * @Description 051001资产处置主表 (审核数据)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/auditAssDisposal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAssDisposal(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);

			mapVo.put("ass_nature", ids[4]);

			mapVo.put("bus_type_code", ids[5]);

			mapVo.put("state", "1");

			mapVo.put("audit_emp", SessionManager.getUserId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			Map<String, Object> mapVoDetail = new HashMap<String, Object>();

			mapVoDetail.put("group_id", ids[0]);

			mapVoDetail.put("hos_id", ids[1]);

			mapVoDetail.put("copy_code", ids[2]);

			mapVoDetail.put("dis_id", ids[3]);

			List<AssDisposalDetail> listDetail = assDisposalDetailService.queryAssDisposalDetailExists(mapVoDetail);

			for (AssDisposalDetail detail : listDetail) {

				Map<String, Object> mapCard = new HashMap<String, Object>();

				mapCard.put("group_id", detail.getGroup_id());

				mapCard.put("hos_id", detail.getHos_id());

				mapCard.put("copy_code", detail.getCopy_code());

				mapCard.put("ass_card_no", detail.getAss_card_no());

				if (mapVo.get("ass_nature") == "01") {

					//AssCard assCard = assCardSpecialService.queryByCode(mapCard);

				} else if (mapVo.get("ass_nature") == "02") {

					//AssCard assCard = assCardGeneralService.queryByCode(mapCard);

				} else if (mapVo.get("ass_nature") == "03") {

					//AssCard assCard = assCardHouseService.queryByCode(mapCard);

				} else if (mapVo.get("ass_nature") == "04") {

					//AssCard assCard = assCardOtherService.queryByCode(mapCard);

				}

				Map<String, Object> mapBusType = new HashMap<String, Object>();

				mapBusType.put("group_id", mapCard.get("group_id"));

				mapBusType.put("hos_id", mapCard.get("hos_id"));

				mapBusType.put("copy_code", mapCard.get("copy_code"));

				mapBusType.put("ass_card_no", mapCard.get("ass_card_no"));

				mapBusType.put("buy_type", mapVo.get("bus_type_code"));

				mapBusType.put("ass_nature", mapVo.get("ass_nature"));

				//assCardSpecialService.updateDisposal(mapBusType);

			}

			listVo.add(mapVo);

		}
		try {
			String assDisposalMainJson = assDisposalMainService.updateBatchAssDisposalMain(listVo);

			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 051001资产处置主表 (记账)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/accountAssDisposal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accountAssDisposal(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String assDisposalMainJson = "";
		boolean flag = true;
		// List<Map<String, Object>> listVo= new
		// ArrayList<Map<String,Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);

			// mapVo.put("state", "4");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			if (!ids[4].equals("0")) {

				flag = false;

				break;

			}
			try {
				assDisposalMainJson = assDisposalMainService.updateAccountAssDisposalMain(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"记账失败 状态必须是新建状态才能记账! \"}");
		}
		return JSONObject.parseObject(assDisposalMainJson);

	}

	/**
	 * @Description 051001资产处置主表 (记账)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/removeAssDisposal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeAssDisposal(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String assDisposalMainJson = "";
		boolean flag = true;
		// List<Map<String, Object>> listVo= new
		// ArrayList<Map<String,Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);

			// mapVo.put("state", "5");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			if (!ids[4].equals("0")) {

				flag = false;

				break;

			}
			try {
				assDisposalMainJson = assDisposalMainService.updateRemoveAssDisposalMain(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"销账失败 状态必须是新建状态才能销账! \"}");

		}

		return JSONObject.parseObject(assDisposalMainJson);

	}

}
