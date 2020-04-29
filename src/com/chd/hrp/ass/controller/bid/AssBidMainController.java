/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bid;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.bid.AssBidFile;
import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.service.bid.AssBidDetailService;
import com.chd.hrp.ass.service.bid.AssBidFileService;
import com.chd.hrp.ass.service.bid.AssBidMainService;
import com.chd.hrp.ass.service.bid.AssBidPlanMapService;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;

/**
 * 
 * @Description: 050401 招标管理
 * @Table: ASS_BID_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBidMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBidMainController.class);

	// 引入Service服务
	@Resource(name = "assBidMainService")
	private final AssBidMainService assBidMainService = null;

	@Resource(name = "assBidDetailService")
	private final AssBidDetailService assBidDetailService = null;

	@Resource(name = "assBidFileService")
	private final AssBidFileService assBidFileService = null;

	@Resource(name = "assPlanDeptDetailService")
	private final AssPlanDeptDetailService assPlanDeptDetailService = null;

	@Resource(name = "assBidPlanMapService")
	private final AssBidPlanMapService assBidPlanMapService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainMainPage", method = RequestMethod.GET)
	public String assBidMainMainPage(Model mode) throws Exception {

		return "hrp/ass/assbidmain/assBidMainMain";

	}

	/**
	 * @Description 审核主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidToExamineMainMainPage", method = RequestMethod.GET)
	public String assBidToExamineMainMainPage(Model mode) throws Exception {

		return "hrp/ass/assbidmain/assBidToExamineMainMain";

	}

	/**
	 * @Description 投标管理主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidFileMainMainPage", method = RequestMethod.GET)
	public String assBidFileMainMainPage(Model mode) throws Exception {

		return "hrp/ass/assbidmain/assBidFileMainMain";

	}

	/**
	 * @Description 中标管理主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainMainQueryPage", method = RequestMethod.GET)
	public String assBidMainMainQueryPage(Model mode) throws Exception {

		return "hrp/ass/assbidmain/assBidMainMainQuery";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainAddPage", method = RequestMethod.GET)
	public String assBidMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbidmain/assBidMainAdd";

	}

	/**
	 * @Description 跳转引入购置计划页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainImportPlanPage", method = RequestMethod.GET)
	public String assPlanDeptImportApplyPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbidmain/assBidMainImportPlan";

	}

	/**
	 * @Description 添加数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/addAssBidMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("is_group", "0");

		String ven_id_no = mapVo.get("ven_id").toString();

		mapVo.put("ven_id", ven_id_no.split("@")[0]);

		mapVo.put("ven_no", ven_id_no.split("@")[1]);
		String assBidMainJson = "";
		try {

			assBidMainJson = assBidMainService.addOrUpdateAssBidMain(mapVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		JSONObject jsonObj = JSONArray.parseObject(assBidMainJson);

		String bid_id = (String) jsonObj.get("bid_id");

		String bid_no = (String) jsonObj.get("bid_no");

		String assBidDetailJson = "";

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
	/**
	 * @Description 添加数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/addAssBidMainImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidMainImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

			nMapVo.put("is_group", "0");

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
			assPlanDeptJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\""
					+mapVo.get("group_id") + "|" + mapVo.get("hos_id")+ "|" + mapVo.get("copy_code") + "|"+ object.get("bid_id") + "|"+nMapVo.get("state")+"\"}";
			

		} else {
			assPlanDeptJson = "{\"error\":\"引入计划单失败！\"}";
		}
		return JSONObject.parseObject(assPlanDeptJson);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/updateToExamine", method = RequestMethod.POST)
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

			AssBidMain assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

			mapVo.put("audit_emp", SessionManager.getUserId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			if (assBidMain.getState() != 0) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核\"}");
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
	@RequestMapping(value = "/hrp/ass/assbidmain/updateNotToExamine", method = RequestMethod.POST)
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

			AssBidMain assBidMain = assBidMainService.queryAssBidMainByCode(mapVo);

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

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
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainUpdatePage", method = RequestMethod.GET)
	public String assBidMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		return "hrp/ass/assbidmain/assBidMainUpdate";
	}
	
	/**
	 * @Description 查看跳转页面 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainViewPage", method = RequestMethod.GET)
	public String assBidMainViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		return "hrp/ass/assbidmain/assBidMainView";
	}

	/**
	 * @Description 更新数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/updateAssBidMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBidMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	 * @Description 删除数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/deleteAssBidMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBidMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
	@RequestMapping(value = "/hrp/ass/assbidmain/queryAssBidMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

	@RequestMapping(value = "/hrp/ass/assbidmain/queryAssBidToExamineMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidToExamineMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

	@RequestMapping(value = "/hrp/ass/assbidmain/queryAssBidQueryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidQueryMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

	@RequestMapping(value = "/hrp/ass/assbidmain/queryAssBidFileMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidFileMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assBidMain = assBidMainService.queryAssBidFileMain(getPage(mapVo));

		return JSONObject.parseObject(assBidMain);

	}

	// 上传投标文件
	@RequestMapping(value = "hrp/ass/assbidmain/importBidFile", method = RequestMethod.POST)
	public String importBidFile(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> mapVo) throws IOException {

		String result = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("bid_file_id") == null || mapVo.get("bid_file_id").toString().equals("null")) {

			List<File> fileList = UploadUtil.upload(plupload, "assBidFile", request, response);

			for (File file : fileList) {

				mapVo.put("file_name", file.getName());

				mapVo.put("file_url", "assBidFile");

				result = assBidFileService.addAssBidFile(mapVo);
			}

		} else {

			AssBidFile assBidFile = assBidFileService.queryAssBidFileByCode(mapVo);

			assBidFileService.deleteAssBidFile(mapVo);

			FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\") + assBidFile.getFile_url()
					+ "\\" + assBidFile.getFile_name());

			List<File> fileList = UploadUtil.upload(plupload, "assBidFile", request, response);

			for (File file : fileList) {

				mapVo.put("file_name", file.getName());

				mapVo.put("file_url", "assBidFile");

				result = assBidFileService.addAssBidFile(mapVo);
			}
		}

		response.getWriter().print(result);

		return null;
	}

	/**
	 * @Description 导入跳转页面 050401 招标管理
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidmain/assBidMainImportPage", method = RequestMethod.GET)
	public String assBidMainImportPage(Model mode, String bid_id, String bid_file_id) throws Exception {

		mode.addAttribute("bid_id", bid_id);

		mode.addAttribute("bid_file_id", bid_file_id);

		return "hrp/ass/assbidmain/assBidMainImport";

	}

}
