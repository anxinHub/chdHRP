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
import com.chd.hrp.ass.entity.contract.AssContractBond;
import com.chd.hrp.ass.service.contract.AssContractBondService;

/**
 * 
 * @Description: 050502 资产合同保证金
 * @Table: ASS_CONTRACT_BOND
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractBondController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractBondController.class);

	// 引入Service服务
	@Resource(name = "assContractBondService")
	private final AssContractBondService assContractBondService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/assContractBondMainPage", method = RequestMethod.GET)
	public String assContractBondMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractbond/assContractBondMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/assContractBondAddPage", method = RequestMethod.GET)
	public String assContractBondAddPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbond/assContractBondAdd";

	}

	/**
	 * @Description 添加数据 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/addAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractBond(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBondJson = "";

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

			assContractBondJson = assContractBondService.addOrUpdate(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBondJson);

	}

	/**
	 * @Description 更新跳转页面 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/assContractBondUpdatePage", method = RequestMethod.GET)
	public String assContractBondUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractBond assContractBond = new AssContractBond();

		assContractBond = assContractBondService.queryByCode(mapVo);

		mode.addAttribute("group_id", assContractBond.getGroup_id());

		mode.addAttribute("hos_id", assContractBond.getHos_id());

		mode.addAttribute("copy_code", assContractBond.getCopy_code());

		mode.addAttribute("acct_year", assContractBond.getAcct_year());

		mode.addAttribute("bond_no", assContractBond.getBond_no());

		mode.addAttribute("contract_id", assContractBond.getContract_id());

		mode.addAttribute("ven_id", assContractBond.getVen_id());

		mode.addAttribute("ven_no", assContractBond.getVen_no());

		mode.addAttribute("create_date", assContractBond.getCreate_date());

		mode.addAttribute("bond_flag", assContractBond.getBond_flag());

		mode.addAttribute("pay_flag", assContractBond.getPay_flag());

		mode.addAttribute("pay_money", assContractBond.getPay_money());

		mode.addAttribute("state", assContractBond.getState());

		mode.addAttribute("bill_no", assContractBond.getBill_no());

		mode.addAttribute("check_no", assContractBond.getCheck_no());

		mode.addAttribute("reason", assContractBond.getReason());

		return "hrp/ass/asscontractbond/assContractBondUpdate";
	}

	/**
	 * @Description 更新数据 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/updateAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractBond(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBondJson = "";

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

			assContractBondJson = assContractBondService.update(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBondJson);
	}

	/**
	 * @Description 更新数据 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/addOrUpdateAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractBond(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBondJson = "";

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

				assContractBondJson = assContractBondService.addOrUpdate(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}
		}

		return JSONObject.parseObject(assContractBondJson);
	}

	/**
	 * @Description 删除数据 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/deleteAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractBond(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assContractBondJson = "";

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

			assContractBondJson = assContractBondService.deleteBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBondJson);

	}

	/**
	 * @Description 查询数据 050502 资产合同保证金
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/queryAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractBond(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assContractBond = assContractBondService.query(getPage(mapVo));

		return JSONObject.parseObject(assContractBond);

	}

	/**
	 * @Description 导入跳转页面 050502 资产合同保证金
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/assContractBondImportPage", method = RequestMethod.GET)
	public String assContractBondImportPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbond/assContractBondImport";

	}

	/**
	 * @Description 下载导入模版 050502 资产合同保证金
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscontractbond/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050502 资产合同保证金.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050502 资产合同保证金
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/readAssContractBondFiles", method = RequestMethod.POST)
	public String readAssContractBondFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssContractBond> list_err = new ArrayList<AssContractBond>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractBond assContractBond = new AssContractBond();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", SessionManager.getAcctYear());

				if (StringTool.isNotBlank(temp[4])) {

					assContractBond.setBond_no(temp[4]);
					mapVo.put("bond_no", temp[4]);

				} else {

					err_sb.append("保证金单号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractBond.setContract_id(Long.valueOf(temp[5]));
					mapVo.put("contract_id", temp[5]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractBond.setVen_id(Integer.valueOf(temp[6]));
					mapVo.put("ven_id", temp[6]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractBond.setVen_no(temp[7]);
					mapVo.put("ven_no", temp[7]);

				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractBond.setCreate_date(temp[8]);
					mapVo.put("create_date", temp[8]);

				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractBond.setBond_flag(temp[9]);
					mapVo.put("bond_flag", temp[9]);

				} else {

					err_sb.append("保证金类型为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractBond.setPay_flag(temp[10]);
					mapVo.put("pay_flag", temp[10]);

				} else {

					err_sb.append("付款方式为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractBond.setPay_money(Double.valueOf(temp[11]));
					mapVo.put("pay_money", temp[11]);

				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractBond.setState(Integer.valueOf(temp[12]));
					mapVo.put("state", temp[12]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractBond.setBill_no(temp[13]);
					mapVo.put("bill_no", temp[13]);

				} else {

					err_sb.append("发票号为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractBond.setCheck_no(temp[14]);
					mapVo.put("check_no", temp[14]);

				} else {

					err_sb.append("支票号为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractBond.setReason(temp[15]);
					mapVo.put("reason", temp[15]);

				} else {

					err_sb.append("原因为空  ");

				}

				AssContractBond data_exc_extis = assContractBondService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBond.setError_type(err_sb.toString());

					list_err.add(assContractBond);

				} else {

					try {

						String dataJson = assContractBondService.add(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBond data_exc = new AssContractBond();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050502 资产合同保证金
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbond/addBatchAssContractBond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssContractBond(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssContractBond> list_err = new ArrayList<AssContractBond>();

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

				AssContractBond assContractBond = new AssContractBond();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("bond_no"))) {

					assContractBond.setBond_no((String) jsonObj.get("bond_no"));
					mapVo.put("bond_no", jsonObj.get("bond_no"));
				} else {

					err_sb.append("保证金单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractBond.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assContractBond.setVen_id(Integer.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assContractBond.setVen_no((String) jsonObj.get("ven_no"));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assContractBond.setCreate_date((String) jsonObj.get("create_date"));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bond_flag"))) {

					assContractBond.setBond_flag((String) jsonObj.get("bond_flag"));
					mapVo.put("bond_flag", jsonObj.get("bond_flag"));
				} else {

					err_sb.append("保证金类型为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("pay_flag"))) {

					assContractBond.setPay_flag((String) jsonObj.get("pay_flag"));
					mapVo.put("pay_flag", jsonObj.get("pay_flag"));
				} else {

					err_sb.append("付款方式为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {

					assContractBond.setPay_money(Double.valueOf((String) jsonObj.get("pay_money")));
					mapVo.put("pay_money", jsonObj.get("pay_money"));
				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assContractBond.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bill_no"))) {

					assContractBond.setBill_no((String) jsonObj.get("bill_no"));
					mapVo.put("bill_no", jsonObj.get("bill_no"));
				} else {

					err_sb.append("发票号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("check_no"))) {

					assContractBond.setCheck_no((String) jsonObj.get("check_no"));
					mapVo.put("check_no", jsonObj.get("check_no"));
				} else {

					err_sb.append("支票号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("reason"))) {

					assContractBond.setReason((String) jsonObj.get("reason"));
					mapVo.put("reason", jsonObj.get("reason"));
				} else {

					err_sb.append("原因为空  ");

				}

				AssContractBond data_exc_extis = assContractBondService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBond.setError_type(err_sb.toString());

					list_err.add(assContractBond);

				} else {

					try {

						String dataJson = assContractBondService.add(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBond data_exc = new AssContractBond();

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
