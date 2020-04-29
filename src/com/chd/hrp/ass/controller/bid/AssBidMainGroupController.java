/** 
* @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bid;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.bid.AssBidDetail;
import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.service.bid.AssBidDetailService;
import com.chd.hrp.ass.service.bid.AssBidGroupHosService;
import com.chd.hrp.ass.service.bid.AssBidMainService;
import com.chd.hrp.ass.service.bid.AssBidPlanMapService;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * 
 * @Description: 050401 招标管理
 * @Table: ASS_BID_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBidMainGroupController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBidMainGroupController.class);

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	@Resource(name = "assBidMainService")
	private final AssBidMainService assBidMainService = null;

	@Resource(name = "assBidDetailService")
	private final AssBidDetailService assBidDetailService = null;

	@Resource(name = "assPlanDeptDetailService")
	private final AssPlanDeptDetailService assPlanDeptDetailService = null;

	@Resource(name = "assBidGroupHosService")
	private final AssBidGroupHosService assBidGroupHosService = null;

	@Resource(name = "assBidPlanMapService")
	private final AssBidPlanMapService assBidPlanMapService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/assBidGroupMainMainPage", method = RequestMethod.GET)
	public String assBidMainMainPage(Model mode) throws Exception {

		return "hrp/ass/assbidgroupmain/assBidGroupMainMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/assBidGroupMainAddPage", method = RequestMethod.GET)
	public String assBidMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbidgroupmain/assBidGroupMainAdd";

	}

	@RequestMapping(value = "/hrp/ass/assbidgroupmain/assBidGroupMainImportHosPage", method = RequestMethod.GET)
	public String assBidGroupMainImportHosPage(Model mode) throws Exception {

		return "hrp/ass/assbidgroupmain/assBidGroupMainImportHos";

	}

	/**
	 * @Description 跳转引入购置计划页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/assBidGroupMainImportPlanPage", method = RequestMethod.GET)
	public String assPlanDeptImportApplyPage(Model mode) throws Exception {

		return "hrp/ass/assbidgroupmain/assBidGroupMainImportPlan";

	}

	/**
	 * @Description 添加数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/addAssBidGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String bid_date = String.valueOf(mapVo.get("bid_date"));

		if (StringUtils.isNotEmpty(bid_date)) {

			mapVo.put("bid_date", DateUtil.stringToDate(bid_date, "yyyy-MM-dd"));

		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

		mapVo.put("create_emp", SessionManager.getUserId());

		mapVo.put("audit_emp", null);

		mapVo.put("audit_date", null);

		mapVo.put("state", "0");

		String ven_id_no = mapVo.get("ven_id").toString();

		mapVo.put("ven_id", ven_id_no.split("@")[0]);

		mapVo.put("ven_no", ven_id_no.split("@")[1]);

		mapVo.put("is_group", "1");

		String assBidMainJson = assBidMainService.addOrUpdateAssBidMain(mapVo);

		JSONObject jsonObj = JSONArray.parseObject(assBidMainJson);

		String bid_id = (String) jsonObj.get("bid_id");

		String bid_no = (String) jsonObj.get("bid_no");

		String assBidDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}

			int ass_price = Integer.parseInt(detailVo.get("ass_price").toString());

			int ass_num = Integer.parseInt(detailVo.get("ass_num").toString());

			detailVo.put("ass_money", ass_price * ass_num);

			detailVo.put("bid_id", bid_id);

			detailVo.put("bid_no", bid_no);

			String id = detailVo.get("ass_id").toString();

			if (detailVo.get("fac_id") == null || detailVo.get("fac_id") == "") {

				detailVo.put("fac_id", "");

				detailVo.put("fac_no", "");

			} else {

				String fid = detailVo.get("fac_id").toString();

				detailVo.put("fac_id", fid.split("@")[0]);

				detailVo.put("fac_no", fid.split("@")[1]);

			}

			detailVo.put("ass_id", id.split("@")[0]);

			detailVo.put("ass_no", id.split("@")[1]);

			try {

				assBidDetailJson = assBidDetailService.addOrUpdateAssBidDetail(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}

		}

		JSONObject json = JSONArray.parseObject(assBidDetailJson);

		json.put("bid_id", bid_id);

		json.put("bid_no", bid_no);

		return JSONObject.parseObject(json.toJSONString());

	}
	///

	@RequestMapping(value = "/hrp/ass/assbidgroupmain/addAssBidGroupMainImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidGroupMainImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assPlanDeptJson = "";

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

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<AssPlanDeptDetail> Dataillist = assPlanDeptDetailService.queryAssPlanDeptDetailByBidList(mapVo);

		if (Dataillist.size() > 0) {

			Map<String, Object> nMapVo = new HashMap<String, Object>();
			// 表的主键

			nMapVo.put("group_id", mapVo.get("group_id"));

			nMapVo.put("hos_id", mapVo.get("hos_id"));

			nMapVo.put("copy_code", mapVo.get("copy_code"));

			nMapVo.put("create_emp", SessionManager.getUserId());

			nMapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			nMapVo.put("ven_id", mapVo.get("ven_id"));

			nMapVo.put("ven_no", mapVo.get("ven_no"));

			nMapVo.put("bid_date", new Date());

			nMapVo.put("audit_emp", null);

			nMapVo.put("audit_date", null);

			nMapVo.put("state", "0");

			nMapVo.put("bid_note", "引入购置计划");

			nMapVo.put("is_group", "1");

			assPlanDeptJson = assBidMainService.addAssBidMain(nMapVo);

			Map<String, Object> object = (Map<String, Object>) ChdJson.fromJson(assPlanDeptJson);

			for (AssPlanDeptDetail list : Dataillist) {

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", list.getGroup_id());

				map.put("hos_id", list.getHos_id());

				map.put("copy_code", list.getCopy_code());

				map.put("bid_id", object.get("bid_id"));

				map.put("bid_no", object.get("bid_no"));

				map.put("ass_id", list.getAss_id());

				map.put("ass_no", list.getAss_no());

				map.put("ass_spec", list.getAss_spec());

				map.put("ass_model", list.getAss_model());

				map.put("ass_num", list.getPlan_amount());

				map.put("ass_price", "0");

				map.put("ass_money", "0");

				map.put("fac_id", list.getFac_id());

				map.put("fac_no", list.getFac_no());

				map.put("bid_note", list.getNote());

				try {

					assPlanDeptJson = assBidDetailService.addAssBidDetail(map);

				} catch (Exception e) {

					return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

				}

			}

			for (String id : mapVo.get("plan_ids").toString().split(",")) {

				Map<String, Object> bidPlanMapvo = new HashMap<String, Object>();

				bidPlanMapvo.put("group_id", mapVo.get("group_id"));

				bidPlanMapvo.put("hos_id", mapVo.get("hos_id"));

				bidPlanMapvo.put("copy_code", mapVo.get("copy_code"));

				bidPlanMapvo.put("bid_id", object.get("bid_id"));

				bidPlanMapvo.put("plan_id", id);

				assBidMainService.addAssBidMainImportPlan(bidPlanMapvo);

			}
			assPlanDeptJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id") + "|"
					+ mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + object.get("bid_id") + "|"
					+ nMapVo.get("state") + "\"}";

		} else {
			assPlanDeptJson = "{\"error\":\"引入计划单失败！\"}";
		}

		return JSONObject.parseObject(assPlanDeptJson);

	}

	@RequestMapping(value = "/hrp/ass/assbidgroupmain/importHosBidGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHosBidGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assInMainJson = "";

		String bid_id = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<AssBidDetail> list = assBidDetailService.queryAssBidDetailList(mapVo);

		if (list.size() > 0) {

			Map<String, Object> mainMapVo = new HashMap<String, Object>();

			mainMapVo.put("group_id", mapVo.get("group_id"));

			mainMapVo.put("hos_id", mapVo.get("hos_id"));

			mainMapVo.put("copy_code", mapVo.get("copy_code"));

			mainMapVo.put("create_emp", SessionManager.getUserId());

			mainMapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			mainMapVo.put("ven_id", mapVo.get("ven_id"));

			mainMapVo.put("ven_no", mapVo.get("ven_no"));

			mainMapVo.put("bid_date", new Date());

			mainMapVo.put("audit_emp", null);

			mainMapVo.put("audit_date", null);

			mainMapVo.put("state", "0");

			mainMapVo.put("bid_note", "引入医院招标");

			mainMapVo.put("is_group", "1");

			String str = assBidMainService.addAssBidMain(mainMapVo);

			JSONObject jsonObj = JSONArray.parseObject(str);

			bid_id = (String) jsonObj.get("bid_id");

			String bid_no = (String) jsonObj.get("bid_no");

			for (AssBidDetail temp : list) {

				Map<String, Object> detailMapVo = new HashMap<String, Object>();

				detailMapVo.put("group_id", temp.getGroup_id());

				detailMapVo.put("hos_id", temp.getHos_id());

				detailMapVo.put("copy_code", temp.getCopy_code());

				detailMapVo.put("bid_id", bid_id);

				detailMapVo.put("bid_no", bid_no);

				detailMapVo.put("ass_id", temp.getAss_id());

				detailMapVo.put("ass_no", temp.getAss_no());

				detailMapVo.put("ass_spec", temp.getAss_spec());

				detailMapVo.put("ass_model", temp.getAss_model());

				detailMapVo.put("ass_num", temp.getAss_num());

				detailMapVo.put("ass_price", "0");

				detailMapVo.put("ass_money", "0");

				detailMapVo.put("fac_id", temp.getFac_id());

				detailMapVo.put("fac_no", temp.getFac_no());

				detailMapVo.put("bid_note", temp.getBid_note());

				assBidDetailService.addAssBidDetail(detailMapVo);

			}
		}

		String[] bid_ids = mapVo.get("bid_ids").toString().split(",");

		for (int i = 0; i < bid_ids.length; i++) {

			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("group_id", mapVo.get("group_id"));

			entityMap.put("hos_id", mapVo.get("hos_id"));

			entityMap.put("copy_code", mapVo.get("copy_code"));

			entityMap.put("hos_bid_id", bid_ids[i]);

			entityMap.put("group_bid_id", bid_id);

			try {

				assInMainJson = assBidGroupHosService.addAssBidGroupHos(entityMap);

				assInMainJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id") + "|"
						+ mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + bid_id + "|0\"}";

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bid_id", ids[3]);

			mapVo.put("audit_emp", SessionManager.getUserId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			AssBidMain assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

			if (assBidMain.getState() != 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");
		}

		try {
			assInMainJson = assBidMainService.updateToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bid_id", ids[3]);

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			AssBidMain assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

			if (assBidMain.getState() != 1) {
				flag = false;
				break;
			}
			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");
		}

		try {
			assInMainJson = assBidMainService.updateNotToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/assBidGroupMainUpdatePage", method = RequestMethod.GET)
	public String assBidGroupMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssBidMain assBidMain = new AssBidMain();

		assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assBidMain.getGroup_id());

		mode.addAttribute("hos_id", assBidMain.getHos_id());

		mode.addAttribute("copy_code", assBidMain.getCopy_code());

		mode.addAttribute("bid_id", assBidMain.getBid_id());

		mode.addAttribute("bid_no", assBidMain.getBid_no());

		if (assBidMain.getBid_date() == null) {

			mode.addAttribute("bid_date", assBidMain.getBid_date());

		} else {

			mode.addAttribute("bid_date", sdf.format(assBidMain.getBid_date()));

		}

		mode.addAttribute("ven_id", assBidMain.getVen_id());

		mode.addAttribute("ven_no", assBidMain.getVen_no());

		mode.addAttribute("link_man", assBidMain.getLink_man());

		mode.addAttribute("tel_mobile", assBidMain.getTel_mobile());

		mode.addAttribute("tel_office", assBidMain.getTel_office());

		mode.addAttribute("state", assBidMain.getState());

		mode.addAttribute("create_emp", assBidMain.getCreate_emp());

		if (assBidMain.getCreate_date() == null) {

			mode.addAttribute("create_date", assBidMain.getCreate_date());

		} else {

			mode.addAttribute("create_date", sdf.format(assBidMain.getCreate_date()));

		}

		mode.addAttribute("audit_emp", assBidMain.getAudit_emp());

		if (assBidMain.getAudit_date() == null) {

			mode.addAttribute("audit_date", assBidMain.getAudit_date());

		} else {

			mode.addAttribute("audit_date", sdf.format(assBidMain.getAudit_date()));

		}

		mode.addAttribute("is_group", assBidMain.getIs_group());

		mode.addAttribute("bid_note", assBidMain.getBid_note());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbidgroupmain/assBidGroupMainUpdate";

	}

	/**
	 * @Description 更新数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/updateAssBidGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBidGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBidMainJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String bid_date = String.valueOf(mapVo.get("bid_date"));

		if (StringUtils.isNotEmpty(bid_date)) {

			mapVo.put("bid_date", DateUtil.stringToDate(bid_date, "yyyy-MM-dd"));
		}

		String create_date = String.valueOf(mapVo.get("create_date"));

		if (StringUtils.isNotEmpty(create_date)) {

			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));

		}

		String audit_date = String.valueOf(mapVo.get("audit_date"));

		if (StringUtils.isNotEmpty(audit_date)) {

			mapVo.put("audit_date", DateUtil.stringToDate(audit_date, "yyyy-MM-dd"));
		}

		String ven_id_no = mapVo.get("ven_id").toString();

		mapVo.put("ven_id", ven_id_no.split("@")[0]);

		mapVo.put("ven_no", ven_id_no.split("@")[1]);

		try {

			assBidMainJson = assBidMainService.updateAssBidMain(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBidMainJson);

	}

	/**
	 * @Description 更新数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/addOrUpdateAssBidGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssBidGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBidMainJson = "";

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

			detailVo.put("is_group", "1");

			try {

				assBidMainJson = assBidMainService.addOrUpdateAssBidMain(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}

		}

		return JSONObject.parseObject(assBidMainJson);
	}

	/**
	 * @Description 删除数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/deleteAssBidGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBidGroupMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assBidMainJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bid_id", ids[3]);

			mapVo.put("group_bid_id", ids[3]);

			AssBidMain assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

			if (assBidMain.getState() != 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态不是新建状态的不能进行删除! \"}");
		}

		try {
			assBidGroupHosService.deleteBatchAssBidGroupHos(listVo);
			assBidPlanMapService.deleteBatchAssBidPlanMap(listVo);
			assBidDetailService.deleteBatchAssBidDetail(listVo);
			assBidMainJson = assBidMainService.deleteBatchAssBidMain(listVo);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBidMainJson);

	}

	/**
	 * @Description 查询数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidgroupmain/queryAssBidGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapVo.put("is_group", "1");

		String assBidMain = assBidMainService.queryAssBidMain(getPage(mapVo));

		return JSONObject.parseObject(assBidMain);

	}

	@RequestMapping(value = "/hrp/ass/assbidgroupmain/queryAssBidGroupHosMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidGroupHosMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapVo.put("is_group", "0");

		String assBidMain = assBidMainService.queryAssBidMain(getPage(mapVo));

		return JSONObject.parseObject(assBidMain);

	}

}
