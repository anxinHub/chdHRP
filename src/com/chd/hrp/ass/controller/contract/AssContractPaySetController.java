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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.contract.AssContractPaySet;
import com.chd.hrp.ass.service.contract.AssContractPaySetService;

/**
 * 
 * @Description: 050502 资产合同分期付款设置
 * @Table: ASS_CONTRACT_PAY_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractPaySetController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractPaySetController.class);

	// 引入Service服务
	@Resource(name = "assContractPaySetService")
	private final AssContractPaySetService assContractPaySetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/assContractPaySetMainPage", method = RequestMethod.GET)
	public String assContractPaySetMainPage(Model mode) throws Exception {

		return "hrp/ass/asscontractpayset/assContractPaySetMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/assContractPaySetAddPage", method = RequestMethod.GET)
	public String assContractPaySetAddPage(Model mode) throws Exception {

		return "hrp/ass/asscontractpayset/assContractPaySetAdd";

	}

	/**
	 * @Description 添加数据 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/addAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractPaySet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractPaySetJson = "";

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

			assContractPaySetJson = assContractPaySetService.addAssContractPaySet(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assContractPaySetJson);

	}

	/**
	 * @Description 更新跳转页面 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/assContractPaySetUpdatePage", method = RequestMethod.GET)
	public String assContractPaySetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractPaySet assContractPaySet = new AssContractPaySet();

		assContractPaySet = assContractPaySetService.queryAssContractPaySetByCode(mapVo);

		mode.addAttribute("group_id", assContractPaySet.getGroup_id());

		mode.addAttribute("hos_id", assContractPaySet.getHos_id());

		mode.addAttribute("copy_code", assContractPaySet.getCopy_code());

		mode.addAttribute("acct_year", assContractPaySet.getAcct_year());

		mode.addAttribute("contract_id", assContractPaySet.getContract_id());

		mode.addAttribute("contract_no", assContractPaySet.getContract_no());

		mode.addAttribute("payment_id", assContractPaySet.getPayment_id());

		mode.addAttribute("summary", assContractPaySet.getSummary());

		mode.addAttribute("payment_money", assContractPaySet.getPayment_money());

		mode.addAttribute("foreign_money", assContractPaySet.getForeign_money());

		mode.addAttribute("pay_money", assContractPaySet.getPay_money());

		mode.addAttribute("start_date", assContractPaySet.getStart_date());

		mode.addAttribute("end_date", assContractPaySet.getEnd_date());

		mode.addAttribute("fact_pay_date", assContractPaySet.getFact_pay_date());

		mode.addAttribute("state", assContractPaySet.getState());

		mode.addAttribute("is_state", assContractPaySet.getIs_state());

		mode.addAttribute("create_date", assContractPaySet.getCreate_date());

		return "hrp/ass/asscontractpayset/assContractPaySetUpdate";
	}

	/**
	 * @Description 更新数据 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/updateAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractPaySet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractPaySetJson = "";

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

			assContractPaySetJson = assContractPaySetService.updateAssContractPaySet(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractPaySetJson);
	}

	/**
	 * @Description 更新数据 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/addOrUpdateAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractPaySet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractPaySetJson = "";

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

				assContractPaySetJson = assContractPaySetService.addOrUpdateAssContractPaySet(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assContractPaySetJson);
	}

	/**
	 * @Description 删除数据 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/deleteAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractPaySet(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assContractPaySetJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("acct_year", ids[3]);

			mapVo.put("contract_id", ids[4]);

			listVo.add(mapVo);

		}

		try {

			assContractPaySetJson = assContractPaySetService.deleteBatchAssContractPaySet(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractPaySetJson);

	}

	/**
	 * @Description 查询数据 050502 资产合同分期付款设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/queryAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractPaySet(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assContractPaySet = assContractPaySetService.queryAssContractPaySet(getPage(mapVo));

		return JSONObject.parseObject(assContractPaySet);

	}

	/**
	 * @Description 导入跳转页面 050502 资产合同分期付款设置
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/assContractPaySetImportPage", method = RequestMethod.GET)
	public String assContractPaySetImportPage(Model mode) throws Exception {

		return "hrp/ass/asscontractpayset/assContractPaySetImport";

	}

	/**
	 * @Description 下载导入模版 050502 资产合同分期付款设置
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscontractpayset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050502 资产合同分期付款设置.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050502 资产合同分期付款设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/readAssContractPaySetFiles", method = RequestMethod.POST)
	public String readAssContractPaySetFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssContractPaySet> list_err = new ArrayList<AssContractPaySet>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractPaySet assContractPaySet = new AssContractPaySet();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", SessionManager.getAcctYear());

				if (StringTool.isNotBlank(temp[4])) {

					assContractPaySet.setContract_id(Long.valueOf(temp[4]));
					mapVo.put("contract_id", temp[4]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractPaySet.setContract_no(temp[5]);
					mapVo.put("contract_no", temp[5]);

				} else {

					err_sb.append("合同号为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractPaySet.setPayment_id(Integer.valueOf(temp[6]));
					mapVo.put("payment_id", temp[6]);

				} else {

					err_sb.append("付款期号为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractPaySet.setSummary(temp[7]);
					mapVo.put("summary", temp[7]);

				} else {

					err_sb.append("摘要为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractPaySet.setPayment_money(Double.valueOf(temp[8]));
					mapVo.put("payment_money", temp[8]);

				} else {

					err_sb.append("本币金额为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractPaySet.setForeign_money(Double.valueOf(temp[9]));
					mapVo.put("foreign_money", temp[9]);

				} else {

					err_sb.append("外币金额为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractPaySet.setPay_money(Double.valueOf(temp[10]));
					mapVo.put("pay_money", temp[10]);

				} else {

					err_sb.append("付款金额为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractPaySet.setStart_date(DateUtil.stringToDate(temp[11]));
					mapVo.put("start_date", temp[11]);

				} else {

					err_sb.append("付款期间开始为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractPaySet.setEnd_date(DateUtil.stringToDate(temp[12]));
					mapVo.put("end_date", temp[12]);

				} else {

					err_sb.append("付款期间结束为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractPaySet.setFact_pay_date(DateUtil.stringToDate(temp[13]));
					mapVo.put("fact_pay_date", temp[13]);

				} else {

					err_sb.append("实付日期为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractPaySet.setState(Integer.valueOf(temp[14]));
					mapVo.put("state", temp[14]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractPaySet.setIs_state(Integer.valueOf(temp[15]));
					mapVo.put("is_state", temp[15]);

				} else {

					err_sb.append("启用状态为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assContractPaySet.setCreate_date(DateUtil.stringToDate(temp[16]));
					mapVo.put("create_date", temp[16]);

				} else {

					err_sb.append("创建日期为空  ");

				}

				AssContractPaySet data_exc_extis = assContractPaySetService.queryAssContractPaySetByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractPaySet.setError_type(err_sb.toString());

					list_err.add(assContractPaySet);

				} else {

					try {

						String dataJson = assContractPaySetService.addAssContractPaySet(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractPaySet data_exc = new AssContractPaySet();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050502 资产合同分期付款设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractpayset/addBatchAssContractPaySet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssContractPaySet(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssContractPaySet> list_err = new ArrayList<AssContractPaySet>();

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

				AssContractPaySet assContractPaySet = new AssContractPaySet();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractPaySet.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_no"))) {

					assContractPaySet.setContract_no((String) jsonObj.get("contract_no"));
					mapVo.put("contract_no", jsonObj.get("contract_no"));
				} else {

					err_sb.append("合同号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("payment_id"))) {

					assContractPaySet.setPayment_id(Integer.valueOf((String) jsonObj.get("payment_id")));
					mapVo.put("payment_id", jsonObj.get("payment_id"));
				} else {

					err_sb.append("付款期号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("summary"))) {

					assContractPaySet.setSummary((String) jsonObj.get("summary"));
					mapVo.put("summary", jsonObj.get("summary"));
				} else {

					err_sb.append("摘要为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("payment_money"))) {

					assContractPaySet.setPayment_money(Double.valueOf((String) jsonObj.get("payment_money")));
					mapVo.put("payment_money", jsonObj.get("payment_money"));
				} else {

					err_sb.append("本币金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("foreign_money"))) {

					assContractPaySet.setForeign_money(Double.valueOf((String) jsonObj.get("foreign_money")));
					mapVo.put("foreign_money", jsonObj.get("foreign_money"));
				} else {

					err_sb.append("外币金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {

					assContractPaySet.setPay_money(Double.valueOf((String) jsonObj.get("pay_money")));
					mapVo.put("pay_money", jsonObj.get("pay_money"));
				} else {

					err_sb.append("付款金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("start_date"))) {

					assContractPaySet.setStart_date(DateUtil.stringToDate((String) jsonObj.get("start_date")));
					mapVo.put("start_date", jsonObj.get("start_date"));
				} else {

					err_sb.append("付款期间开始为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("end_date"))) {

					assContractPaySet.setEnd_date(DateUtil.stringToDate((String) jsonObj.get("end_date")));
					mapVo.put("end_date", jsonObj.get("end_date"));
				} else {

					err_sb.append("付款期间结束为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fact_pay_date"))) {

					assContractPaySet.setFact_pay_date(DateUtil.stringToDate((String) jsonObj.get("fact_pay_date")));
					mapVo.put("fact_pay_date", jsonObj.get("fact_pay_date"));
				} else {

					err_sb.append("实付日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assContractPaySet.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_state"))) {

					assContractPaySet.setIs_state(Integer.valueOf((String) jsonObj.get("is_state")));
					mapVo.put("is_state", jsonObj.get("is_state"));
				} else {

					err_sb.append("启用状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assContractPaySet.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_date")));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("创建日期为空  ");

				}

				AssContractPaySet data_exc_extis = assContractPaySetService.queryAssContractPaySetByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractPaySet.setError_type(err_sb.toString());

					list_err.add(assContractPaySet);

				} else {

					try {

						String dataJson = assContractPaySetService.addAssContractPaySet(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractPaySet data_exc = new AssContractPaySet();

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
