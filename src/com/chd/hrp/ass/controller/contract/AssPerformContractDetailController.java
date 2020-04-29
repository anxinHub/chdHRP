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
import com.chd.hrp.ass.entity.contract.AssContractDetail;
import com.chd.hrp.ass.entity.contract.AssContractMain;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.chd.hrp.ass.service.contract.AssContractMainService;

/**
 * 
 * @Description: 050501 资产合同明细
 * @Table: ASS_CONTRACT_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPerformContractDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPerformContractDetailController.class);

	// 引入Service服务
	@Resource(name = "assContractDetailService")
	private final AssContractDetailService assContractDetailService = null;
	@Resource(name = "assContractMainService")
	private final AssContractMainService assContractMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/assPerformContractDetailMainPage", method = RequestMethod.GET)
	public String assPerformContractDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assperformcontractdetail/assPerformContractDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/assPerformContractDetailAddPage", method = RequestMethod.GET)
	public String assPerformContractDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assperformcontractdetail/assPerformContractDetailAdd";

	}

	/**
	 * @Description 添加数据 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/addAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPerformContractDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String assContractDetailJson = "";
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
			assContractDetailJson = assContractDetailService.addAssContractDetail(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractDetailJson);

	}

	/**
	 * @Description 更新跳转页面 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/assPerformContractDetailUpdatePage", method = RequestMethod.GET)
	public String assPerformContractDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssContractDetail assContractDetail = new AssContractDetail();

		assContractDetail = assContractDetailService.queryAssContractDetailByCode(mapVo);

		mode.addAttribute("group_id", assContractDetail.getGroup_id());
		mode.addAttribute("hos_id", assContractDetail.getHos_id());
		mode.addAttribute("copy_code", assContractDetail.getCopy_code());
		mode.addAttribute("contract_id", assContractDetail.getContract_id());
		mode.addAttribute("contract_detail_id", assContractDetail.getContract_detail_id());
		mode.addAttribute("contract_no", assContractDetail.getContract_no());
		mode.addAttribute("ass_id", assContractDetail.getAss_id());
		mode.addAttribute("ass_no", assContractDetail.getAss_no());
		mode.addAttribute("ass_spec", assContractDetail.getAss_spec());
		mode.addAttribute("ass_model", assContractDetail.getAss_model());
		mode.addAttribute("ass_brand", assContractDetail.getAss_brand());
		mode.addAttribute("fac_id", assContractDetail.getFac_id());
		mode.addAttribute("fac_no", assContractDetail.getFac_no());
		mode.addAttribute("contract_amount", assContractDetail.getContract_amount());
		mode.addAttribute("contract_price", assContractDetail.getContract_price());
		mode.addAttribute("send_date", assContractDetail.getSend_date());
		mode.addAttribute("keep_repair_times", assContractDetail.getKeep_repair_times());
		mode.addAttribute("times_unit", assContractDetail.getTimes_unit());
		mode.addAttribute("is_fix", assContractDetail.getIs_fix());
		mode.addAttribute("is_accept", assContractDetail.getIs_accept());
		mode.addAttribute("is_bid", assContractDetail.getIs_bid());
		mode.addAttribute("note", assContractDetail.getNote());

		return "hrp/ass/assperformcontractdetail/assPerformContractDetailUpdate";
	}

	/**
	 * @Description 更新数据 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/updateAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPerformContractDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractDetailJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		try {
			assContractDetailJson = assContractDetailService.updateAssContractDetail(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractDetailJson);
	}

	/**
	 * @Description 更新数据 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/addOrUpdateAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssPerformContractDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assContractDetailJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AssContractMain contractMain = assContractMainService.queryAssContractMainByUniqueness(mapVo);
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

			detailVo.put("contract_id", contractMain.getContract_id());
			if (detailVo.get("contract_detail_id") == null) {
				detailVo.put("contract_detail_id", "0");
			}
			detailVo.put("contract_no", mapVo.get("contract_no"));
			detailVo.put("ass_code", mapVo.get("ass_code"));

			if (StringUtils.isNotEmpty(detailVo.get("send_date").toString())) {
				detailVo.put("send_date", DateUtil.stringToDate(detailVo.get("send_date").toString(), "yyyy-MM-dd"));
			}

			String id = detailVo.get("ass_id").toString();
			String fid = detailVo.get("fac_id").toString();
			System.out.println("id=" + id + ">>>" + mapVo.get("contract_detail_id"));
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
		return JSONObject.parseObject(assContractDetailJson);
	}

	/**
	 * @Description 删除数据 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/deleteAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPerformContractDetail(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		String assContractDetailJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("contract_id", ids[3]);
			mapVo.put("contract_detail_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			assContractDetailJson = assContractDetailService.deleteBatchAssContractDetail(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assContractDetailJson);

	}

	/**
	 * @Description 查询数据 050501 资产合同明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/queryAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPerformContractDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String assContractDetail = assContractDetailService.queryAssContractDetailByUpdate(getPage(mapVo));

		return JSONObject.parseObject(assContractDetail);

	}

	/**
	 * @Description 导入跳转页面 050501 资产合同明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/assPerformContractDetailImportPage", method = RequestMethod.GET)
	public String assPerformContractDetailImportPage(Model mode) throws Exception {

		return "hrp/ass/assperformcontractdetail/assPerformContractDetailImport";

	}

	/**
	 * @Description 下载导入模版 050501 资产合同明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assperformcontractdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050501 资产合同明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050501 资产合同明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/readAssPerformContractDetailPerforms", method = RequestMethod.POST)
	public String readAssPerformContractDetailPerforms(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssContractDetail> list_err = new ArrayList<AssContractDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssContractDetail assContractDetail = new AssContractDetail();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assContractDetail.setContract_id(Long.valueOf(temp[3]));
					mapVo.put("contract_id", temp[3]);

				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assContractDetail.setContract_detail_id(Integer.valueOf(temp[4]));
					mapVo.put("contract_detail_id", temp[4]);

				} else {

					err_sb.append("合同明细号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assContractDetail.setContract_no(temp[5]);
					mapVo.put("contract_no", temp[5]);

				} else {

					err_sb.append("合同号为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assContractDetail.setAss_id(Long.valueOf(temp[6]));
					mapVo.put("ass_id", temp[6]);

				} else {

					err_sb.append("资产ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assContractDetail.setAss_no(Long.valueOf(temp[7]));
					mapVo.put("ass_no", temp[7]);

				} else {

					err_sb.append("资产变更NO为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assContractDetail.setAss_spec(temp[8]);
					mapVo.put("ass_spec", temp[8]);

				} else {

					err_sb.append("规格为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assContractDetail.setAss_model(temp[9]);
					mapVo.put("ass_model", temp[9]);

				} else {

					err_sb.append("型号为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assContractDetail.setAss_brand(temp[10]);
					mapVo.put("ass_brand", temp[10]);

				} else {

					err_sb.append("品牌为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assContractDetail.setFac_id(Long.valueOf(temp[11]));
					mapVo.put("fac_id", temp[11]);

				} else {

					err_sb.append("生产厂家ID为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assContractDetail.setFac_no(Long.valueOf(temp[12]));
					mapVo.put("fac_no", temp[12]);

				} else {

					err_sb.append("生产厂家NO为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assContractDetail.setContract_amount(Integer.valueOf(temp[13]));
					mapVo.put("contract_amount", temp[13]);

				} else {

					err_sb.append("合同数量为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assContractDetail.setContract_price(Double.valueOf(temp[14]));
					mapVo.put("contract_price", temp[14]);

				} else {

					err_sb.append("合同单价为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assContractDetail.setSend_date(DateUtil.stringToDate(temp[15]));
					mapVo.put("send_date", temp[15]);

				} else {

					err_sb.append("交货日期为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assContractDetail.setKeep_repair_times(Double.valueOf(temp[16]));
					mapVo.put("keep_repair_times", temp[16]);

				} else {

					err_sb.append("保修期为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					assContractDetail.setTimes_unit(Integer.valueOf(temp[17]));
					mapVo.put("times_unit", temp[17]);

				} else {

					err_sb.append("保修期单位为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					assContractDetail.setIs_fix(Integer.valueOf(temp[18]));
					mapVo.put("is_fix", temp[18]);

				} else {

					err_sb.append("是否安装为空  ");

				}

				if (StringTool.isNotBlank(temp[19])) {

					assContractDetail.setIs_accept(Integer.valueOf(temp[19]));
					mapVo.put("is_accept", temp[19]);

				} else {

					err_sb.append("是否验收为空  ");

				}

				if (StringTool.isNotBlank(temp[20])) {

					assContractDetail.setIs_bid(Integer.valueOf(temp[20]));
					mapVo.put("is_bid", temp[20]);

				} else {

					err_sb.append("是否招标为空  ");

				}

				if (StringTool.isNotBlank(temp[21])) {

					assContractDetail.setNote(temp[21]);
					mapVo.put("note", temp[21]);

				} else {

					err_sb.append("备注为空  ");

				}

				AssContractDetail data_exc_extis = assContractDetailService.queryAssContractDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractDetail.setError_type(err_sb.toString());

					list_err.add(assContractDetail);

				} else {

					try {
						String dataJson = assContractDetailService.addAssContractDetail(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssContractDetail data_exc = new AssContractDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050501 资产合同明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assperformcontractdetail/addBatchAssPerformContractDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssPerformContractDetail(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<AssContractDetail> list_err = new ArrayList<AssContractDetail>();

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

				AssContractDetail assContractDetail = new AssContractDetail();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("contract_id"))) {

					assContractDetail.setContract_id(Long.valueOf((String) jsonObj.get("contract_id")));
					mapVo.put("contract_id", jsonObj.get("contract_id"));
				} else {

					err_sb.append("合同ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_detail_id"))) {

					assContractDetail
							.setContract_detail_id(Integer.valueOf((String) jsonObj.get("contract_detail_id")));
					mapVo.put("contract_detail_id", jsonObj.get("contract_detail_id"));
				} else {

					err_sb.append("合同明细号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_no"))) {

					assContractDetail.setContract_no((String) jsonObj.get("contract_no"));
					mapVo.put("contract_no", jsonObj.get("contract_no"));
				} else {

					err_sb.append("合同号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_id"))) {

					assContractDetail.setAss_id(Long.valueOf((String) jsonObj.get("ass_id")));
					mapVo.put("ass_id", jsonObj.get("ass_id"));
				} else {

					err_sb.append("资产ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_no"))) {

					assContractDetail.setAss_no(Long.valueOf((String) jsonObj.get("ass_no")));
					mapVo.put("ass_no", jsonObj.get("ass_no"));
				} else {

					err_sb.append("资产变更NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_spec"))) {

					assContractDetail.setAss_spec((String) jsonObj.get("ass_spec"));
					mapVo.put("ass_spec", jsonObj.get("ass_spec"));
				} else {

					err_sb.append("规格为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_model"))) {

					assContractDetail.setAss_model((String) jsonObj.get("ass_model"));
					mapVo.put("ass_model", jsonObj.get("ass_model"));
				} else {

					err_sb.append("型号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_brand"))) {

					assContractDetail.setAss_brand((String) jsonObj.get("ass_brand"));
					mapVo.put("ass_brand", jsonObj.get("ass_brand"));
				} else {

					err_sb.append("品牌为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {

					assContractDetail.setFac_id(Long.valueOf((String) jsonObj.get("fac_id")));
					mapVo.put("fac_id", jsonObj.get("fac_id"));
				} else {

					err_sb.append("生产厂家ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fac_no"))) {

					assContractDetail.setFac_no(Long.valueOf((String) jsonObj.get("fac_no")));
					mapVo.put("fac_no", jsonObj.get("fac_no"));
				} else {

					err_sb.append("生产厂家NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_amount"))) {

					assContractDetail.setContract_amount(Integer.valueOf((String) jsonObj.get("contract_amount")));
					mapVo.put("contract_amount", jsonObj.get("contract_amount"));
				} else {

					err_sb.append("合同数量为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("contract_price"))) {

					assContractDetail.setContract_price(Double.valueOf((String) jsonObj.get("contract_price")));
					mapVo.put("contract_price", jsonObj.get("contract_price"));
				} else {

					err_sb.append("合同单价为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("send_date"))) {

					assContractDetail.setSend_date(DateUtil.stringToDate((String) jsonObj.get("send_date")));
					mapVo.put("send_date", jsonObj.get("send_date"));
				} else {

					err_sb.append("交货日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("keep_repair_times"))) {

					assContractDetail.setKeep_repair_times(Double.valueOf((String) jsonObj.get("keep_repair_times")));
					mapVo.put("keep_repair_times", jsonObj.get("keep_repair_times"));
				} else {

					err_sb.append("保修期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("times_unit"))) {

					assContractDetail.setTimes_unit(Integer.valueOf((String) jsonObj.get("times_unit")));
					mapVo.put("times_unit", jsonObj.get("times_unit"));
				} else {

					err_sb.append("保修期单位为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_fix"))) {

					assContractDetail.setIs_fix(Integer.valueOf((String) jsonObj.get("is_fix")));
					mapVo.put("is_fix", jsonObj.get("is_fix"));
				} else {

					err_sb.append("是否安装为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_accept"))) {

					assContractDetail.setIs_accept(Integer.valueOf((String) jsonObj.get("is_accept")));
					mapVo.put("is_accept", jsonObj.get("is_accept"));
				} else {

					err_sb.append("是否验收为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_bid"))) {

					assContractDetail.setIs_bid(Integer.valueOf((String) jsonObj.get("is_bid")));
					mapVo.put("is_bid", jsonObj.get("is_bid"));
				} else {

					err_sb.append("是否招标为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assContractDetail.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("备注为空  ");

				}

				AssContractDetail data_exc_extis = assContractDetailService.queryAssContractDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assContractDetail.setError_type(err_sb.toString());

					list_err.add(assContractDetail);

				} else {

					try {
						String dataJson = assContractDetailService.addAssContractDetail(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssContractDetail data_exc = new AssContractDetail();

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
