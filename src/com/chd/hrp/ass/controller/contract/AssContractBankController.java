/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.contract;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.contract.AssContractBank;
import com.chd.hrp.ass.service.contract.AssContractBankService;

/**
 * 
 * @Description: 050502 资产合同银行保函
 * @Table: ASS_CONTRACT_BANK
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractBankController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractBankController.class);

	// 引入Service服务
	@Resource(name = "assContractBankService")
	private final AssContractBankService assContractBankService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/assContractBankMainPage", method = RequestMethod.GET)
	public String assContractBankMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractbank/assContractBankMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/assContractBankAddPage", method = RequestMethod.GET)
	public String assContractBankAddPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbank/assContractBankAdd";

	}

	/**
	 * @Description 添加数据 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/addAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractBank(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBankJson = "";
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

		mapVo.put("state", "0");

		String create_date = mapVo.get("create_date") == null ? "" : mapVo.get("create_date").toString();

		mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));

		try {

			assContractBankJson = assContractBankService.addOrUpdate(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBankJson);

	}

	/**
	 * @Description 更新跳转页面 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/assContractBankUpdatePage", method = RequestMethod.GET)
	public String assContractBankUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractBank assContractBank = new AssContractBank();

		assContractBank = assContractBankService.queryByCode(mapVo);

		mode.addAttribute("group_id", assContractBank.getGroup_id());

		mode.addAttribute("hos_id", assContractBank.getHos_id());

		mode.addAttribute("copy_code", assContractBank.getCopy_code());

		mode.addAttribute("acct_year", assContractBank.getAcct_year());

		mode.addAttribute("bond_no", assContractBank.getBond_no());

		mode.addAttribute("contract_id", assContractBank.getContract_id());

		mode.addAttribute("create_date", assContractBank.getCreate_date());

		mode.addAttribute("pay_money", assContractBank.getPay_money());

		mode.addAttribute("state", assContractBank.getState());

		mode.addAttribute("reason", assContractBank.getReason());

		mode.addAttribute("bank_info", assContractBank.getBank_info());

		mode.addAttribute("bank_emp", assContractBank.getBank_emp());

		mode.addAttribute("bank_tel", assContractBank.getBank_tel());

		mode.addAttribute("bank_account", assContractBank.getBank_account());

		mode.addAttribute("delegate", assContractBank.getDelegate());

		mode.addAttribute("delegate_emp", assContractBank.getDelegate_emp());

		mode.addAttribute("delegate_tel", assContractBank.getDelegate_tel());

		mode.addAttribute("hos_info", assContractBank.getHos_info());

		return "hrp/ass/asscontractbank/assContractBankUpdate";
	}

	/**
	 * @Description 更新数据 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/updateAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractBank(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBankJson = "";

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

			assContractBankJson = assContractBankService.update(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBankJson);
	}

	/**
	 * @Description 更新数据 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/addOrUpdateAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractBank(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBankJson = "";

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

			if (detailVo.get("acct_year") == null) {

				detailVo.put("acct_year", SessionManager.getAcctYear());

			}

			try {

				assContractBankJson = assContractBankService.addOrUpdate(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assContractBankJson);
	}

	/**
	 * @Description 删除数据 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/deleteAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractBank(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assContractBankJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bond_no", ids[3]);

			listVo.add(mapVo);

		}

		try {

			assContractBankJson = assContractBankService.deleteBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBankJson);

	}

	/**
	 * @Description 查询数据 050502 资产合同银行保函
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/queryAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractBank(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assContractBank = assContractBankService.query(getPage(mapVo));

		return JSONObject.parseObject(assContractBank);

	}

	/**
	 * @Description 导入跳转页面 050502 资产合同银行保函
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/assContractBankImportPage", method = RequestMethod.GET)
	public String assContractBankImportPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbank/assContractBankImport";

	}

	/**
	 * @Description 下载导入模版 050502 资产合同银行保函
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscontractbank/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050502 资产合同银行保函.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050502 资产合同银行保函
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/readAssContractBankFiles", method = RequestMethod.POST)
	public String readAssContractBankFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssContractBank> list_err = new ArrayList<AssContractBank>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractBank assContractBank = new AssContractBank();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", SessionManager.getAcctYear());

				if (StringTool.isNotBlank(temp[4])) {

					assContractBank.setBond_no(temp[4]);
					mapVo.put("bond_no", temp[4]);

				} else {

					err_sb.append("保函单号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractBank.setContract_id(Long.valueOf(temp[5]));
					mapVo.put("contract_id", temp[5]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractBank.setCreate_date(temp[6]);
					mapVo.put("create_date", temp[6]);

				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractBank.setPay_money(Double.valueOf(temp[7]));
					mapVo.put("pay_money", temp[7]);

				} else {

					err_sb.append("担保金额为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractBank.setState(Integer.valueOf(temp[8]));
					mapVo.put("state", temp[8]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractBank.setReason(temp[9]);
					mapVo.put("reason", temp[9]);

				} else {

					err_sb.append("保函内容为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractBank.setBank_info(temp[10]);
					mapVo.put("bank_info", temp[10]);

				} else {

					err_sb.append("银行信息为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractBank.setBank_emp(temp[11]);
					mapVo.put("bank_emp", temp[11]);

				} else {

					err_sb.append("银行负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractBank.setBank_tel(temp[12]);
					mapVo.put("bank_tel", temp[12]);

				} else {

					err_sb.append("银行联系电话为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractBank.setBank_account(temp[13]);
					mapVo.put("bank_account", temp[13]);

				} else {

					err_sb.append("银行帐号为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractBank.setDelegate(temp[14]);
					mapVo.put("delegate", temp[14]);

				} else {

					err_sb.append("委托方为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractBank.setDelegate_emp(temp[15]);
					mapVo.put("delegate_emp", temp[15]);

				} else {

					err_sb.append("委托方负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assContractBank.setDelegate_tel(temp[16]);
					mapVo.put("delegate_tel", temp[16]);

				} else {

					err_sb.append("委托人联系电话为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					assContractBank.setHos_info(temp[17]);
					mapVo.put("hos_info", temp[17]);

				} else {

					err_sb.append("医院信息为空  ");

				}

				AssContractBank data_exc_extis = assContractBankService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBank.setError_type(err_sb.toString());

					list_err.add(assContractBank);

				} else {

					try {

						String dataJson = assContractBankService.add(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBank data_exc = new AssContractBank();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050502 资产合同银行保函
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbank/addBatchAssContractBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssContractBank(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssContractBank> list_err = new ArrayList<AssContractBank>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssContractBank assContractBank = new AssContractBank();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("bond_no"))) {

					assContractBank.setBond_no((String) jsonObj.get("bond_no"));
					mapVo.put("bond_no", jsonObj.get("bond_no"));
				} else {

					err_sb.append("保函单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractBank.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assContractBank.setCreate_date((String) jsonObj.get("create_date"));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {

					assContractBank.setPay_money(Double.valueOf((String) jsonObj.get("pay_money")));
					mapVo.put("pay_money", jsonObj.get("pay_money"));
				} else {

					err_sb.append("担保金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assContractBank.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("reason"))) {

					assContractBank.setReason((String) jsonObj.get("reason"));
					mapVo.put("reason", jsonObj.get("reason"));
				} else {

					err_sb.append("保函内容为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bank_info"))) {

					assContractBank.setBank_info((String) jsonObj.get("bank_info"));
					mapVo.put("bank_info", jsonObj.get("bank_info"));
				} else {

					err_sb.append("银行信息为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bank_emp"))) {

					assContractBank.setBank_emp((String) jsonObj.get("bank_emp"));
					mapVo.put("bank_emp", jsonObj.get("bank_emp"));
				} else {

					err_sb.append("银行负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bank_tel"))) {

					assContractBank.setBank_tel((String) jsonObj.get("bank_tel"));
					mapVo.put("bank_tel", jsonObj.get("bank_tel"));
				} else {

					err_sb.append("银行联系电话为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bank_account"))) {

					assContractBank.setBank_account((String) jsonObj.get("bank_account"));
					mapVo.put("bank_account", jsonObj.get("bank_account"));
				} else {

					err_sb.append("银行帐号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delegate"))) {

					assContractBank.setDelegate((String) jsonObj.get("delegate"));
					mapVo.put("delegate", jsonObj.get("delegate"));
				} else {

					err_sb.append("委托方为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delegate_emp"))) {

					assContractBank.setDelegate_emp((String) jsonObj.get("delegate_emp"));
					mapVo.put("delegate_emp", jsonObj.get("delegate_emp"));
				} else {

					err_sb.append("委托方负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delegate_tel"))) {

					assContractBank.setDelegate_tel((String) jsonObj.get("delegate_tel"));
					mapVo.put("delegate_tel", jsonObj.get("delegate_tel"));
				} else {

					err_sb.append("委托人联系电话为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("hos_info"))) {

					assContractBank.setHos_info((String) jsonObj.get("hos_info"));
					mapVo.put("hos_info", jsonObj.get("hos_info"));
				} else {

					err_sb.append("医院信息为空  ");

				}

				AssContractBank data_exc_extis = assContractBankService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBank.setError_type(err_sb.toString());

					list_err.add(assContractBank);

				} else {

					try {

						String dataJson = assContractBankService.add(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBank data_exc = new AssContractBank();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
