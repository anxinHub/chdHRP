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
import com.chd.hrp.ass.entity.contract.AssContractClaim;
import com.chd.hrp.ass.service.contract.AssContractClaimService;

/**
 * 
 * @Description: 050502 资产合同索赔
 * @Table: ASS_CONTRACT_CLAIM
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssContractClaimController extends BaseController {

	private static Logger logger = Logger.getLogger(AssContractClaimController.class);

	// 引入Service服务
	@Resource(name = "assContractClaimService")
	private final AssContractClaimService assContractClaimService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/assContractClaimMainPage", method = RequestMethod.GET)
	public String assContractClaimMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscontractclaim/assContractClaimMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/assContractClaimAddPage", method = RequestMethod.GET)
	public String assContractClaimAddPage(Model mode) throws Exception {

		return "hrp/ass/asscontractclaim/assContractClaimAdd";

	}

	/**
	 * @Description 添加数据 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/addAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssContractClaim(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractClaimJson = "";

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

			assContractClaimJson = assContractClaimService.addOrUpdate(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assContractClaimJson);

	}

	/**
	 * @Description 更新跳转页面 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/assContractClaimUpdatePage", method = RequestMethod.GET)
	public String assContractClaimUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssContractClaim assContractClaim = new AssContractClaim();

		assContractClaim = assContractClaimService.queryByCode(mapVo);

		mode.addAttribute("group_id", assContractClaim.getGroup_id());
		mode.addAttribute("hos_id", assContractClaim.getHos_id());
		mode.addAttribute("copy_code", assContractClaim.getCopy_code());
		mode.addAttribute("acct_year", assContractClaim.getAcct_year());
		mode.addAttribute("claim_no", assContractClaim.getClaim_no());
		mode.addAttribute("contract_id", assContractClaim.getContract_id());
		mode.addAttribute("ven_id", assContractClaim.getVen_id());
		mode.addAttribute("ven_no", assContractClaim.getVen_no());
		mode.addAttribute("create_date", assContractClaim.getCreate_date());
		mode.addAttribute("breach_money", assContractClaim.getBreach_money());
		mode.addAttribute("state", assContractClaim.getState());
		mode.addAttribute("claim_flag", assContractClaim.getClaim_flag());
		mode.addAttribute("first_emp", assContractClaim.getFirst_emp());
		mode.addAttribute("second_emp", assContractClaim.getSecond_emp());
		mode.addAttribute("begin_date", assContractClaim.getBegin_date());
		mode.addAttribute("end_date", assContractClaim.getEnd_date());
		mode.addAttribute("claim_basis", assContractClaim.getClaim_basis());
		mode.addAttribute("claim_reason", assContractClaim.getClaim_reason());

		return "hrp/ass/asscontractclaim/assContractClaimUpdate";
	}

	/**
	 * @Description 更新数据 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/updateAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssContractClaim(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractClaimJson = "";
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
			assContractClaimJson = assContractClaimService.update(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractClaimJson);
	}

	/**
	 * @Description 更新数据 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/addOrUpdateAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssContractClaim(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractClaimJson = "";

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
				assContractClaimJson = assContractClaimService.addOrUpdate(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assContractClaimJson);
	}

	/**
	 * @Description 删除数据 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/deleteAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssContractClaim(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String assContractClaimJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("claim_no", ids[3]);

			listVo.add(mapVo);

		}

		try {
			assContractClaimJson = assContractClaimService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractClaimJson);

	}

	/**
	 * @Description 查询数据 050502 资产合同索赔
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/queryAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractClaim(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String assContractClaim = assContractClaimService.query(getPage(mapVo));

		return JSONObject.parseObject(assContractClaim);

	}

	/**
	 * @Description 导入跳转页面 050502 资产合同索赔
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/assContractClaimImportPage", method = RequestMethod.GET)
	public String assContractClaimImportPage(Model mode) throws Exception {

		return "hrp/ass/asscontractclaim/assContractClaimImport";

	}

	/**
	 * @Description 下载导入模版 050502 资产合同索赔
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscontractclaim/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050502 资产合同索赔.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050502 资产合同索赔
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/readAssContractClaimFiles", method = RequestMethod.POST)
	public String readAssContractClaimFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssContractClaim> list_err = new ArrayList<AssContractClaim>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractClaim assContractClaim = new AssContractClaim();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("acct_year", SessionManager.getAcctYear());

				if (StringTool.isNotBlank(temp[4])) {

					assContractClaim.setClaim_no(temp[4]);
					mapVo.put("claim_no", temp[4]);

				} else {

					err_sb.append("索赔单号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractClaim.setContract_id(Long.valueOf(temp[5]));
					mapVo.put("contract_id", temp[5]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractClaim.setVen_id(Integer.valueOf(temp[6]));
					mapVo.put("ven_id", temp[6]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractClaim.setVen_no(temp[7]);
					mapVo.put("ven_no", temp[7]);

				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractClaim.setCreate_date(temp[8]);
					mapVo.put("create_date", temp[8]);

				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractClaim.setBreach_money(Double.valueOf(temp[9]));
					mapVo.put("breach_money", temp[9]);

				} else {

					err_sb.append("赔偿金额为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractClaim.setState(Integer.valueOf(temp[10]));
					mapVo.put("state", temp[10]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractClaim.setClaim_flag(temp[11]);
					mapVo.put("claim_flag", temp[11]);

				} else {

					err_sb.append("索赔方为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractClaim.setFirst_emp(temp[12]);
					mapVo.put("first_emp", temp[12]);

				} else {

					err_sb.append("甲方负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractClaim.setSecond_emp(temp[13]);
					mapVo.put("second_emp", temp[13]);

				} else {

					err_sb.append("乙方负责人为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractClaim.setBegin_date(temp[14]);
					mapVo.put("begin_date", temp[14]);

				} else {

					err_sb.append("开始日期为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractClaim.setEnd_date(temp[15]);
					mapVo.put("end_date", temp[15]);

				} else {

					err_sb.append("结束日期为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assContractClaim.setClaim_basis(temp[16]);
					mapVo.put("claim_basis", temp[16]);

				} else {

					err_sb.append("索赔依据为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					assContractClaim.setClaim_reason(temp[17]);
					mapVo.put("claim_reason", temp[17]);

				} else {

					err_sb.append("索赔原因为空  ");

				}

				AssContractClaim data_exc_extis = assContractClaimService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractClaim.setError_type(err_sb.toString());

					list_err.add(assContractClaim);

				} else {

					try {

						String dataJson = assContractClaimService.add(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractClaim data_exc = new AssContractClaim();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050502 资产合同索赔
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscontractclaim/addBatchAssContractClaim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssContractClaim(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssContractClaim> list_err = new ArrayList<AssContractClaim>();

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

				AssContractClaim assContractClaim = new AssContractClaim();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("claim_no"))) {

					assContractClaim.setClaim_no((String) jsonObj.get("claim_no"));
					mapVo.put("claim_no", jsonObj.get("claim_no"));
				} else {

					err_sb.append("索赔单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractClaim.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assContractClaim.setVen_id(Integer.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assContractClaim.setVen_no((String) jsonObj.get("ven_no"));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assContractClaim.setCreate_date((String) jsonObj.get("create_date"));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("登记日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("breach_money"))) {

					assContractClaim.setBreach_money(Double.valueOf((String) jsonObj.get("breach_money")));
					mapVo.put("breach_money", jsonObj.get("breach_money"));
				} else {

					err_sb.append("赔偿金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assContractClaim.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("claim_flag"))) {

					assContractClaim.setClaim_flag((String) jsonObj.get("claim_flag"));
					mapVo.put("claim_flag", jsonObj.get("claim_flag"));
				} else {

					err_sb.append("索赔方为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("first_emp"))) {

					assContractClaim.setFirst_emp((String) jsonObj.get("first_emp"));
					mapVo.put("first_emp", jsonObj.get("first_emp"));
				} else {

					err_sb.append("甲方负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("second_emp"))) {

					assContractClaim.setSecond_emp((String) jsonObj.get("second_emp"));
					mapVo.put("second_emp", jsonObj.get("second_emp"));
				} else {

					err_sb.append("乙方负责人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("begin_date"))) {

					assContractClaim.setBegin_date((String) jsonObj.get("begin_date"));
					mapVo.put("begin_date", jsonObj.get("begin_date"));
				} else {

					err_sb.append("开始日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("end_date"))) {

					assContractClaim.setEnd_date((String) jsonObj.get("end_date"));
					mapVo.put("end_date", jsonObj.get("end_date"));
				} else {

					err_sb.append("结束日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("claim_basis"))) {

					assContractClaim.setClaim_basis((String) jsonObj.get("claim_basis"));
					mapVo.put("claim_basis", jsonObj.get("claim_basis"));
				} else {

					err_sb.append("索赔依据为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("claim_reason"))) {

					assContractClaim.setClaim_reason((String) jsonObj.get("claim_reason"));
					mapVo.put("claim_reason", jsonObj.get("claim_reason"));
				} else {

					err_sb.append("索赔原因为空  ");

				}

				AssContractClaim data_exc_extis = assContractClaimService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractClaim.setError_type(err_sb.toString());

					list_err.add(assContractClaim);

				} else {

					try {

						String dataJson = assContractClaimService.add(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssContractClaim data_exc = new AssContractClaim();

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
