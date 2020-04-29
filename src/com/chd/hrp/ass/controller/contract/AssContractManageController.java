/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.contract;

import java.io.IOException;
import java.text.DateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.service.commonbuilder.AccCurService;
import com.chd.hrp.ass.entity.contract.AssContractManage;
import com.chd.hrp.ass.service.contract.AssContractManageService;

/**
 * 
 * @Description: 付款信息050501 资产合同主表
 * @Table: ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractManageController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractManageController.class);

	// 引入Service服务
	@Resource(name = "assContractManageService")
	private final AssContractManageService assContractManageService = null;

	@Resource(name = "accCurService")
	private final AccCurService accCurService = null;

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/assContractManageAddPage", method = RequestMethod.GET)
	public String assContractManageAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("contract_id", mapVo.get("contract_id"));

		mapVo.put("contract_no", mapVo.get("contract_no"));

		mode.addAttribute("contract_id", mapVo.get("contract_id"));

		mode.addAttribute("contract_no", mapVo.get("contract_no"));

		mode.addAttribute("contract_cur_code", mapVo.get("cur_code"));

		return "hrp/ass/asscontractmain/assContractManageAdd";

	}

	/**
	 * @Description 添加数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/addAssContractManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractManage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractManageJson = "";

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

		String start_date = String.valueOf(mapVo.get("start_date"));

		if (StringUtils.isNotEmpty(start_date)) {

			mapVo.put("start_date", DateUtil.stringToDate(start_date, "yyyy-MM-dd"));

		}

		String end_date = String.valueOf(mapVo.get("end_date"));

		if (StringUtils.isNotEmpty(end_date)) {

			mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));

		}

		String fact_pay_date = String.valueOf(mapVo.get("fact_pay_date"));

		if (StringUtils.isNotEmpty(fact_pay_date)) {

			mapVo.put("fact_pay_date", DateUtil.stringToDate(fact_pay_date, "yyyy-MM-dd"));

		}

		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		mapVo.put("create_date", DateUtil.stringToDate(dateFormat.format(date), "yyyy/MM/dd"));

		Map<String, Object> curMap = new HashMap<String, Object>();

		curMap.put("acc_year", SessionManager.getAcctYear());

		curMap.put("group_id", mapVo.get("group_id"));

		curMap.put("hos_id", mapVo.get("hos_id"));

		curMap.put("copy_code", mapVo.get("copy_code"));

		curMap.put("cur_code", mapVo.get("cur_code"));

		curMap.put("acc_year", SessionManager.getAcctYear());

		AccCur usdCur = accCurService.queryAccCurByCode(curMap);

		if (mapVo.get("cur_code").equals("RMB") && mapVo.get("contract_cur_code").equals("USD")) {

			double pay_money = usdCur.getCur_rate() * Double.parseDouble(mapVo.get("pay_money").toString());

			mapVo.put("pay_money", pay_money);

		}

		try {

			assContractManageJson = assContractManageService.add(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractManageJson);

	}

	/**
	 * @Description 更新跳转页面 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/assContractManageUpdatePage", method = RequestMethod.GET)
	public String assContractManageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractManage assContractManage = new AssContractManage();

		assContractManage = assContractManageService.queryByCode(mapVo);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("id", assContractManage.getId());

		mode.addAttribute("group_id", assContractManage.getGroup_id());

		mode.addAttribute("hos_id", assContractManage.getHos_id());

		mode.addAttribute("copy_code", assContractManage.getCopy_code());

		mode.addAttribute("contract_id", assContractManage.getContract_id());

		mode.addAttribute("contract_no", assContractManage.getContract_no());

		mode.addAttribute("payment_id", assContractManage.getPayment_id());

		mode.addAttribute("summary", assContractManage.getSummary());

		mode.addAttribute("cur_code", assContractManage.getCur_code());

		mode.addAttribute("pay_money", assContractManage.getPay_money());

		if (assContractManage.getStart_date() == null) {

			mode.addAttribute("start_date", assContractManage.getStart_date());

		} else {

			mode.addAttribute("start_date", dateFormat.format(assContractManage.getStart_date()));

		}

		if (assContractManage.getEnd_date() == null) {

			mode.addAttribute("end_date", assContractManage.getEnd_date());
		} else {

			mode.addAttribute("end_date", dateFormat.format(assContractManage.getEnd_date()));

		}

		if (assContractManage.getFact_pay_date() == null) {

			mode.addAttribute("fact_pay_date", assContractManage.getFact_pay_date());
		} else {

			mode.addAttribute("fact_pay_date", dateFormat.format(assContractManage.getFact_pay_date()));
		}

		mode.addAttribute("state", assContractManage.getState());

		mode.addAttribute("is_state", assContractManage.getIs_state());

		if (assContractManage.getCreate_date() == null) {

			mode.addAttribute("create_date", assContractManage.getCreate_date());
		} else {

			mode.addAttribute("create_date", dateFormat.format(assContractManage.getCreate_date()));
		}

		return "hrp/ass/asscontractmain/assContractManageUpdate";
	}

	/**
	 * @Description 更新数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/updateAssContractManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractManage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractManageJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String start_date = String.valueOf(mapVo.get("start_date"));

		mapVo.put("start_date", DateUtil.stringToDate(start_date, "yyyy-MM-dd"));

		String end_date = String.valueOf(mapVo.get("end_date"));

		mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));

		String fact_pay_date = String.valueOf(mapVo.get("fact_pay_date"));

		mapVo.put("fact_pay_date", DateUtil.stringToDate(fact_pay_date, "yyyy-MM-dd"));

		String create_date = String.valueOf(mapVo.get("create_date"));

		mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));

		// String ven_id_no=mapVo.get("ven_id").toString();
		// mapVo.put("ven_id", ven_id_no.split("@")[0]);
		// mapVo.put("ven_no", ven_id_no.split("@")[1]);

		try {

			assContractManageJson = assContractManageService.update(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assContractManageJson);
	}

	/**
	 * @Description 更新数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/addOrUpdateAssContractManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractManage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractManageJson = "";

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

			try {

				assContractManageJson = assContractManageService.addOrUpdate(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assContractManageJson);
	}

	/**
	 * @Description 删除数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/deleteAssContractManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractManage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assContractManageJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("payment_id", ids[3]);

			mapVo.put("id", ids[4]);

			listVo.add(mapVo);

		}

		try {

			assContractManageJson = assContractManageService.deleteBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assContractManageJson);

	}

	/**
	 * @Description 查询数据 050501 资产合同主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractmain/queryAssContractManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractManage(@RequestParam Map<String, Object> mapVo, Model mode)
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

		// String sign_date = String.valueOf(mapVo.get("sign_date"));
		// mapVo.put("sign_date", DateUtil.stringToDate(sign_date,
		// "yyyy-MM-dd"));
		// String start_date = String.valueOf(mapVo.get("start_date"));
		// mapVo.put("start_date", DateUtil.stringToDate(start_date,
		// "yyyy-MM-dd"));
		// String end_date = String.valueOf(mapVo.get("end_date"));
		// mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));
		// String create_date = String.valueOf(mapVo.get("create_date"));
		// mapVo.put("create_date", DateUtil.stringToDate(create_date,
		// "yyyy-MM-dd"));
		// String check_date = String.valueOf(mapVo.get("check_date"));
		// mapVo.put("check_date", DateUtil.stringToDate(check_date,
		// "yyyy-MM-dd"));

		String assContractManage = assContractManageService.query(getPage(mapVo));

		return JSONObject.parseObject(assContractManage);

	}

}
