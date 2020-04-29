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
import com.chd.hrp.ass.entity.contract.AssContractBreach;
import com.chd.hrp.ass.service.contract.AssContractBreachService;

/**
 * 
 * @Description: 050502 资产合同违约
 * @Table: ASS_CONTRACT_BREACH
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractBreachController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractBreachController.class);

	// 引入Service服务
	@Resource(name = "assContractBreachService")
	private final AssContractBreachService assContractBreachService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/assContractBreachMainPage", method = RequestMethod.GET)
	public String assContractBreachMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractbreach/assContractBreachMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/assContractBreachAddPage", method = RequestMethod.GET)
	public String assContractBreachAddPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbreach/assContractBreachAdd";

	}

	/**
	 * @Description 添加数据 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/addAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractBreach(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBreachJson = "";

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

		String begin_date = mapVo.get("begin_date") == null ? "" : mapVo.get("begin_date").toString();

		mapVo.put("begin_date", DateUtil.stringToDate(begin_date, "yyyy-MM-dd"));

		String end_date = mapVo.get("end_date") == null ? "" : mapVo.get("end_date").toString();

		mapVo.put("end_date", DateUtil.stringToDate(end_date, "yyyy-MM-dd"));

		try {

			assContractBreachJson = assContractBreachService.addOrUpdate(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBreachJson);

	}

	/**
	 * @Description 更新跳转页面 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/assContractBreachUpdatePage", method = RequestMethod.GET)
	public String assContractBreachUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractBreach assContractBreach = new AssContractBreach();

		assContractBreach = assContractBreachService.queryByCode(mapVo);

		mode.addAttribute("group_id", assContractBreach.getGroup_id());

		mode.addAttribute("hos_id", assContractBreach.getHos_id());

		mode.addAttribute("copy_code", assContractBreach.getCopy_code());

		mode.addAttribute("acct_year", assContractBreach.getAcct_year());

		mode.addAttribute("breach_no", assContractBreach.getBreach_no());

		mode.addAttribute("contract_id", assContractBreach.getContract_id());

		mode.addAttribute("ven_id", assContractBreach.getVen_id());

		mode.addAttribute("ven_no", assContractBreach.getVen_no());

		mode.addAttribute("create_date", assContractBreach.getCreate_date());

		mode.addAttribute("breach_money", assContractBreach.getBreach_money());

		mode.addAttribute("state", assContractBreach.getState());

		mode.addAttribute("breach_flag", assContractBreach.getBreach_flag());

		mode.addAttribute("first_emp", assContractBreach.getFirst_emp());

		mode.addAttribute("second_emp", assContractBreach.getSecond_emp());

		mode.addAttribute("begin_date", assContractBreach.getBegin_date());

		mode.addAttribute("end_date", assContractBreach.getEnd_date());

		mode.addAttribute("breach_reason", assContractBreach.getBreach_reason());

		mode.addAttribute("result", assContractBreach.getResult());

		return "hrp/ass/asscontractbreach/assContractBreachUpdate";
	}

	/**
	 * @Description 更新数据 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/updateAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractBreach(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBreachJson = "";

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

			assContractBreachJson = assContractBreachService.update(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBreachJson);
	}

	/**
	 * @Description 更新数据 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/addOrUpdateAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractBreach(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractBreachJson = "";

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

				assContractBreachJson = assContractBreachService.addOrUpdate(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}
		}

		return JSONObject.parseObject(assContractBreachJson);
	}

	/**
	 * @Description 删除数据 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/deleteAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractBreach(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assContractBreachJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("breach_no", ids[3]);

			listVo.add(mapVo);

		}

		try {

			assContractBreachJson = assContractBreachService.deleteBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assContractBreachJson);

	}

	/**
	 * @Description 查询数据 050502 资产合同违约
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/queryAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractBreach(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assContractBreach = assContractBreachService.query(getPage(mapVo));

		return JSONObject.parseObject(assContractBreach);

	}

	/**
	 * @Description 导入跳转页面 050502 资产合同违约
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/assContractBreachImportPage", method = RequestMethod.GET)
	public String assContractBreachImportPage(Model mode) throws Exception {

		return "hrp/ass/asscontractbreach/assContractBreachImport";

	}

	/**
	 * @Description 下载导入模版 050502 资产合同违约
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscontractbreach/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050502 资产合同违约.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050502 资产合同违约
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/readAssContractBreachFiles", method = RequestMethod.POST)
	public String readAssContractBreachFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssContractBreach> list_err = new ArrayList<AssContractBreach>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractBreach assContractBreach = new AssContractBreach();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", SessionManager.getAcctYear());

				if (StringTool.isNotBlank(temp[4])) {

					assContractBreach.setBreach_no(temp[4]);
					mapVo.put("breach_no", temp[4]);

				} else {

					err_sb.append("违约单号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractBreach.setContract_id(Long.valueOf(temp[5]));
					mapVo.put("contract_id", temp[5]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractBreach.setVen_id(Integer.valueOf(temp[6]));
					mapVo.put("ven_id", temp[6]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractBreach.setVen_no(temp[7]);
					mapVo.put("ven_no", temp[7]);

				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractBreach.setCreate_date(temp[8]);
					mapVo.put("create_date", temp[8]);

				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractBreach.setBreach_money(Double.valueOf(temp[9]));
					mapVo.put("breach_money", temp[9]);

				} else {

					err_sb.append("违约金额为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractBreach.setState(Integer.valueOf(temp[10]));
					mapVo.put("state", temp[10]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractBreach.setBreach_flag(temp[11]);
					mapVo.put("breach_flag", temp[11]);

				} else {

					err_sb.append("违约方为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractBreach.setFirst_emp(temp[12]);
					mapVo.put("first_emp", temp[12]);

				} else {

					err_sb.append("甲方负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractBreach.setSecond_emp(temp[13]);
					mapVo.put("second_emp", temp[13]);

				} else {

					err_sb.append("乙方负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractBreach.setBegin_date(temp[14]);
					mapVo.put("begin_date", temp[14]);

				} else {

					err_sb.append("开始日期为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractBreach.setEnd_date(temp[15]);
					mapVo.put("end_date", temp[15]);

				} else {

					err_sb.append("结束日期为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assContractBreach.setBreach_reason(temp[16]);
					mapVo.put("breach_reason", temp[16]);

				} else {

					err_sb.append("违约原因为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					assContractBreach.setResult(temp[17]);
					mapVo.put("result", temp[17]);

				} else {

					err_sb.append("处理结果为空  ");

				}

				AssContractBreach data_exc_extis = assContractBreachService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBreach.setError_type(err_sb.toString());

					list_err.add(assContractBreach);

				} else {

					try {

						String dataJson = assContractBreachService.add(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBreach data_exc = new AssContractBreach();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050502 资产合同违约
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractbreach/addBatchAssContractBreach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssContractBreach(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssContractBreach> list_err = new ArrayList<AssContractBreach>();

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

				AssContractBreach assContractBreach = new AssContractBreach();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("breach_no"))) {

					assContractBreach.setBreach_no((String) jsonObj.get("breach_no"));
					mapVo.put("breach_no", jsonObj.get("breach_no"));
				} else {

					err_sb.append("违约单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractBreach.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assContractBreach.setVen_id(Integer.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assContractBreach.setVen_no((String) jsonObj.get("ven_no"));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assContractBreach.setCreate_date((String) jsonObj.get("create_date"));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("breach_money"))) {

					assContractBreach.setBreach_money(Double.valueOf((String) jsonObj.get("breach_money")));
					mapVo.put("breach_money", jsonObj.get("breach_money"));
				} else {

					err_sb.append("违约金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assContractBreach.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("breach_flag"))) {

					assContractBreach.setBreach_flag((String) jsonObj.get("breach_flag"));
					mapVo.put("breach_flag", jsonObj.get("breach_flag"));
				} else {

					err_sb.append("违约方为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("first_emp"))) {

					assContractBreach.setFirst_emp((String) jsonObj.get("first_emp"));
					mapVo.put("first_emp", jsonObj.get("first_emp"));
				} else {

					err_sb.append("甲方负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("second_emp"))) {

					assContractBreach.setSecond_emp((String) jsonObj.get("second_emp"));
					mapVo.put("second_emp", jsonObj.get("second_emp"));
				} else {

					err_sb.append("乙方负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("begin_date"))) {

					assContractBreach.setBegin_date((String) jsonObj.get("begin_date"));
					mapVo.put("begin_date", jsonObj.get("begin_date"));
				} else {

					err_sb.append("开始日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("end_date"))) {

					assContractBreach.setEnd_date((String) jsonObj.get("end_date"));
					mapVo.put("end_date", jsonObj.get("end_date"));
				} else {

					err_sb.append("结束日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("breach_reason"))) {

					assContractBreach.setBreach_reason((String) jsonObj.get("breach_reason"));
					mapVo.put("breach_reason", jsonObj.get("breach_reason"));
				} else {

					err_sb.append("违约原因为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("result"))) {

					assContractBreach.setResult((String) jsonObj.get("result"));
					mapVo.put("result", jsonObj.get("result"));
				} else {

					err_sb.append("处理结果为空  ");

				}

				AssContractBreach data_exc_extis = assContractBreachService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractBreach.setError_type(err_sb.toString());

					list_err.add(assContractBreach);

				} else {

					try {

						String dataJson = assContractBreachService.add(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractBreach data_exc = new AssContractBreach();

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
