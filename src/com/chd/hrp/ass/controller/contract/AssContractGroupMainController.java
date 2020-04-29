/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.ass.entity.contract.AssContractDetail;
import com.chd.hrp.ass.entity.contract.AssContractMain;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.chd.hrp.ass.service.contract.AssContractGroupHosService;
import com.chd.hrp.ass.service.contract.AssContractMainService;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * 
 * @Description: 050501 资产合同主表
 * @Table: ASS_CONTRACT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractGroupMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractGroupMainController.class);

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	@Resource(name = "assContractMainService")
	private final AssContractMainService assContractMainService = null;

	@Resource(name = "assContractDetailService")
	private final AssContractDetailService assContractDetailService = null;

	@Resource(name = "assContractGroupHosService")
	private final AssContractGroupHosService assContractGroupHosService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/assContractGroupMainMainPage", method = RequestMethod.GET)
	public String assContractMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractgroupmain/assContractGroupMainMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/assContractGroupMainAddPage", method = RequestMethod.GET)
	public String assContractMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asscontractgroupmain/assContractGroupMainAdd";

	}

	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/assContractGroupImportHosPage", method = RequestMethod.GET)
	public String assContractGroupImportHosPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractgroupmain/assContractGroupImportHos";

	}

	/**
	 * @Description 添加数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/addAssContractGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String sign_date = String.valueOf(mapVo.get("sign_date"));
		if (StringUtils.isNotEmpty(sign_date)) {
			mapVo.put("sign_date", DateUtil.stringToDate(sign_date, "yyyy-MM-dd"));
		}
		String start_date = String.valueOf(mapVo.get("start_date"));
		if (StringUtils.isNotEmpty(start_date)) {
			mapVo.put("start_date", DateUtil.stringToDate(start_date, "yyyy-MM-dd"));
		}
		String end_date = String.valueOf(mapVo.get("end_date"));
		if (StringUtils.isNotEmpty(end_date)) {
			mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));
		}

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		mapVo.put("create_emp", SessionManager.getUserId());
		mapVo.put("create_date", DateUtil.stringToDate(dateFormat.format(date), "yyyy/MM/dd"));
		mapVo.put("check_emp", null);
		mapVo.put("check_date", null);
		mapVo.put("contract_money", "0");
		mapVo.put("state", "0");
		mapVo.put("is_init", "0");
		mapVo.put("is_group", "1");
		String ven_id_no = mapVo.get("ven_id").toString();
		mapVo.put("ven_id", ven_id_no.split("@")[0]);
		mapVo.put("ven_no", ven_id_no.split("@")[1]);

		String assContractMainJson = assContractMainService.addOrUpdateAssContractMain(mapVo);

		JSONObject jsonObj = JSONArray.parseObject(assContractMainJson);
		String contract_id = (String) jsonObj.get("contract_id");
		String contract_no = (String) jsonObj.get("contract_no");
		String assContractDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}
			if (detailVo.get("contract_detail_id") == null) {
				detailVo.put("contract_detail_id", "0");
			}
			detailVo.put("contract_id", contract_id);
			detailVo.put("contract_no", contract_no);
			detailVo.put("ass_code", mapVo.get("ass_code"));

			if (detailVo.containsKey("send_date")) {
				detailVo.put("send_date", DateUtil.stringToDate(detailVo.get("send_date").toString(), "yyyy-MM-dd"));
			}

			String id = detailVo.get("ass_id").toString();
			String fid = detailVo.get("fac_id").toString();
			detailVo.put("ass_id", id.split("@")[0]);
			detailVo.put("ass_no", id.split("@")[1]);
			detailVo.put("fac_id", fid.split("@")[0]);
			detailVo.put("fac_no", fid.split("@")[1]);

			try {
				assContractDetailJson = assContractDetailService.addOrUpdateAssContractDetail(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		JSONObject json = JSONArray.parseObject(assContractDetailJson);
		json.put("contract_id", contract_id);
		json.put("contract_no", contract_no);
		return JSONObject.parseObject(json.toJSONString());
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/updateToExamine", method = RequestMethod.POST)
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

			mapVo.put("contract_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			mapVo.put("check_date", new Date());

			AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

			if (assContractMain.getState() != 0) {
				flag = false;
				break;
			}
			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");
		}

		try {
			assInMainJson = assContractMainService.updateToExamine(listVo);
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
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/updateNotToExamine", method = RequestMethod.POST)
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

			mapVo.put("contract_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			mapVo.put("check_date", new Date());

			AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

			if (assContractMain.getState() != 1) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");
		}
		try {
			assInMainJson = assContractMainService.updateNotToExamine(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/assContractGroupMainUpdatePage", method = RequestMethod.GET)
	public String assContractGroupMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssContractMain assContractMain = new AssContractMain();

		assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assContractMain.getGroup_id());
		mode.addAttribute("hos_id", assContractMain.getHos_id());
		mode.addAttribute("copy_code", assContractMain.getCopy_code());
		mode.addAttribute("contract_id", assContractMain.getContract_id());
		mode.addAttribute("contract_no", assContractMain.getContract_no());
		mode.addAttribute("contract_type", assContractMain.getContract_type());
		mode.addAttribute("contract_ori_no", assContractMain.getContract_ori_no());
		mode.addAttribute("contract_name", assContractMain.getContract_name());
		mode.addAttribute("ass_year", assContractMain.getAss_year());
		mode.addAttribute("ass_month", assContractMain.getAss_month());
		mode.addAttribute("ven_id", assContractMain.getVen_id());
		mode.addAttribute("ven_no", assContractMain.getVen_no());
		if (assContractMain.getSign_date() == null) {
			mode.addAttribute("sign_date", assContractMain.getSign_date());
		} else {
			mode.addAttribute("sign_date", dateFormat.format(assContractMain.getSign_date()));
		}
		mode.addAttribute("buy_type", assContractMain.getBuy_type());
		if (assContractMain.getStart_date() == null) {
			mode.addAttribute("start_date", assContractMain.getStart_date());
		} else {
			mode.addAttribute("start_date", dateFormat.format(assContractMain.getStart_date()));
		}
		if (assContractMain.getEnd_date() == null) {
			mode.addAttribute("end_date", assContractMain.getEnd_date());
		} else {
			mode.addAttribute("end_date", dateFormat.format(assContractMain.getEnd_date()));
		}
		mode.addAttribute("contract_detail", assContractMain.getContract_detail());
		mode.addAttribute("service_detail", assContractMain.getService_detail());
		mode.addAttribute("emp_main", assContractMain.getEmp_main());
		mode.addAttribute("provider", assContractMain.getProvider());
		mode.addAttribute("is_bid", assContractMain.getIs_bid());
		mode.addAttribute("contract_money", assContractMain.getContract_money());
		mode.addAttribute("create_emp", assContractMain.getCreate_emp());
		if (assContractMain.getCreate_date() == null) {
			mode.addAttribute("create_date", assContractMain.getCreate_date());
		} else {
			mode.addAttribute("create_date", dateFormat.format(assContractMain.getCreate_date()));
		}
		mode.addAttribute("check_emp", assContractMain.getCheck_emp());
		if (assContractMain.getCheck_date() == null) {
			mode.addAttribute("check_date", assContractMain.getCheck_date());
		} else {
			mode.addAttribute("check_date", dateFormat.format(assContractMain.getCheck_date()));
		}
		mode.addAttribute("state", assContractMain.getState());
		mode.addAttribute("is_group", assContractMain.getIs_group());
		mode.addAttribute("cur_code", assContractMain.getCur_code());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asscontractgroupmain/assContractGroupMainUpdate";
	}

	/**
	 * @Description 付款信息跳转页面 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/assContractGroupMainManagePage", method = RequestMethod.GET)
	public String assContractMainGroupManagePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

		mode.addAttribute("group_id", assContractMain.getGroup_id());
		mode.addAttribute("hos_id", assContractMain.getHos_id());
		mode.addAttribute("copy_code", assContractMain.getCopy_code());
		mode.addAttribute("contract_id", assContractMain.getContract_id());
		mode.addAttribute("contract_no", assContractMain.getContract_no());
		mode.addAttribute("contract_name", assContractMain.getContract_name());
		mode.addAttribute("cur_code", assContractMain.getCur_code());
		mode.addAttribute("state", assContractMain.getState());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractgroupmain/assContractGroupMainManage";
	}

	/**
	 * @Description 删除数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/deleteAssContractGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractGroupMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assContractMainJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("contract_id", ids[3]);

			AssContractMain assContractMain = assContractMainService.queryAssContractMainByCode(mapVo);

			if (assContractMain.getState() != 0) {
				flag = false;
				break;
			}
			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		try {
			assContractDetailService.deleteBatchAssContractDetail(listVo);
			assContractMainJson = assContractMainService.deleteBatchAssContractMain(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assContractMainJson);

	}

	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/importHosContractGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHosContractGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String assInMainJson = "";
		String contract_id = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<AssContractDetail> list = assContractDetailService.queryAssContractDetailHosList(mapVo);
		if (list.size() > 0) {
			Map<String, Object> mainMapVo = new HashMap<String, Object>();
			mainMapVo.put("group_id", mapVo.get("group_id"));
			mainMapVo.put("hos_id", mapVo.get("hos_id"));
			mainMapVo.put("copy_code", mapVo.get("copy_code"));
			mainMapVo.put("ass_year", sysBaseService.getSysYearMonth("ass_flag").substring(0, 4));
			mainMapVo.put("ass_month", sysBaseService.getSysYearMonth("ass_flag").substring(4));
			mainMapVo.put("contract_type", mapVo.get("contract_type"));
			mainMapVo.put("contract_ori_no", mapVo.get("contract_ori_no"));
			mainMapVo.put("contract_name", mapVo.get("contract_name"));
			mainMapVo.put("buy_type", mapVo.get("buy_type"));
			mainMapVo.put("contract_detai", mapVo.get("contract_detai"));
			mainMapVo.put("service_detail", mapVo.get("service_detail"));
			mainMapVo.put("emp_main", mapVo.get("emp_main"));
			mainMapVo.put("provider", mapVo.get("provider"));
			mainMapVo.put("is_bid", mapVo.get("is_bid"));
			mainMapVo.put("cur_code", mapVo.get("cur_code"));
			mainMapVo.put("ven_id", mapVo.get("ven_id"));
			mainMapVo.put("ven_no", mapVo.get("ven_no"));
			String sign_date = String.valueOf(mapVo.get("sign_date"));
			if (StringUtils.isNotEmpty(sign_date)) {
				mainMapVo.put("sign_date", DateUtil.stringToDate(sign_date, "yyyy-MM-dd"));
			}
			String start_date = String.valueOf(mapVo.get("start_date"));
			if (StringUtils.isNotEmpty(start_date)) {
				mainMapVo.put("start_date", DateUtil.stringToDate(start_date, "yyyy-MM-dd"));
			}
			String end_date = String.valueOf(mapVo.get("end_date"));
			if (StringUtils.isNotEmpty(end_date)) {
				mainMapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));
			}
			mainMapVo.put("create_emp", SessionManager.getUserId());
			mainMapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
			mainMapVo.put("state", "0");
			mainMapVo.put("is_init", "0");
			mainMapVo.put("is_group", "1");
			mainMapVo.put("contract_id", "0");
			String str = assContractMainService.addOrUpdateAssContractMain(mainMapVo);
			JSONObject jsonObj = JSONArray.parseObject(str);
			contract_id = (String) jsonObj.get("contract_id");
			String contract_no = (String) jsonObj.get("contract_no");
			for (AssContractDetail temp : list) {
				Map<String, Object> detailMapVo = new HashMap<String, Object>();
				detailMapVo.put("group_id", temp.getGroup_id());
				detailMapVo.put("hos_id", temp.getHos_id());
				detailMapVo.put("copy_code", temp.getCopy_code());
				detailMapVo.put("contract_id", contract_id);
				detailMapVo.put("contract_no", contract_no);
				detailMapVo.put("ass_id", temp.getAss_id());
				detailMapVo.put("ass_no", temp.getAss_no());
				detailMapVo.put("ass_spec", temp.getAss_spec());
				detailMapVo.put("ass_model", temp.getAss_model());
				detailMapVo.put("ass_brand", temp.getAss_brand());
				detailMapVo.put("fac_id", temp.getFac_id());
				detailMapVo.put("fac_no", temp.getFac_no());
				detailMapVo.put("contract_amount", temp.getContract_amount());
				detailMapVo.put("contract_price", temp.getContract_price());
				detailMapVo.put("send_date", temp.getSend_date());
				detailMapVo.put("keep_repair_time", temp.getKeep_repair_times());
				detailMapVo.put("times_unit", temp.getTimes_unit());
				detailMapVo.put("is_fix", temp.getIs_fix());
				detailMapVo.put("is_accept", temp.getIs_accept());
				detailMapVo.put("is_bid", temp.getIs_bid());
				detailMapVo.put("contract_detail_id", "0");

				assContractDetailService.addOrUpdateAssContractDetail(detailMapVo);
			}
		}

		String[] contract_ids = mapVo.get("contract_ids").toString().split(",");

		for (int i = 0; i < contract_ids.length; i++) {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", mapVo.get("group_id"));
			entityMap.put("hos_id", mapVo.get("hos_id"));
			entityMap.put("copy_code", mapVo.get("copy_code"));
			entityMap.put("hos_contract_id", contract_ids[i]);
			entityMap.put("group_contract_id", contract_id);
			try {
				assInMainJson = assContractGroupHosService.addAssContractGroupHos(entityMap);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 查询数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractgroupmain/queryAssContractGroupMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractGroupMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_group", "1");
		mapVo.put("is_init", "0");
		String assContractMain = assContractMainService.queryAssContractMain(getPage(mapVo));

		return JSONObject.parseObject(assContractMain);

	}

}
