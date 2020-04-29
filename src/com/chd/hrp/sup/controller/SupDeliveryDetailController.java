/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.controller;

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
import com.chd.hrp.sup.entity.SupDeliveryDetail;
import com.chd.hrp.sup.service.SupDeliveryDetailService;

/**
 * @Description: 100102 送货单明细表
 * @Table: SUP_DELIVERY_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupDeliveryDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(SupDeliveryDetailController.class);

	// 引入Service服务
	@Resource(name = "supDeliveryDetailService")
	private final SupDeliveryDetailService supDeliveryDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/supDeliveryDetailMainPage", method = RequestMethod.GET)
	public String supDeliveryDetailMainPage(Model mode) throws Exception {

		return "hrp/sup/supdeliverydetail/supDeliveryDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/supDeliveryDetailAddPage", method = RequestMethod.GET)
	public String supDeliveryDetailAddPage(Model mode) throws Exception {

		return "hrp/sup/supdeliverydetail/supDeliveryDetailAdd";

	}

	@RequestMapping(value = "/hrp/sup/supdeliverydetail/queryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String supJson = supDeliveryDetailService.queryDetail(mapVo);

		return JSONObject.parseObject(supJson);
	}
	
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/queryDetailByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String supJson =  supDeliveryDetailService.queryDetailByStore(mapVo);

		return JSONObject.parseObject(supJson);
	}
	
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/queryDetailWithDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailWithDelivery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String supJson = supDeliveryDetailService.queryDetailWithDelivery(mapVo);

		return JSONObject.parseObject(supJson);
	}

	
	/**
	 * @Description 添加数据 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/addSupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupDeliveryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

		String supDeliveryDetailJson = supDeliveryDetailService.add(mapVo);

		return JSONObject.parseObject(supDeliveryDetailJson);

	}

	/**
	 * @Description 更新跳转页面 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/supDeliveryDetailUpdatePage", method = RequestMethod.GET)
	public String supDeliveryDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

		SupDeliveryDetail supDeliveryDetail = new SupDeliveryDetail();

		supDeliveryDetail = supDeliveryDetailService.queryByCode(mapVo);

		mode.addAttribute("delivery_id", supDeliveryDetail.getDetail_id());
		mode.addAttribute("delivery_no", supDeliveryDetail.getDelivery_no());
		mode.addAttribute("group_id", supDeliveryDetail.getGroup_id());
		mode.addAttribute("hos_id", supDeliveryDetail.getHos_id());
		mode.addAttribute("copy_code", supDeliveryDetail.getCopy_code());
		mode.addAttribute("acc_year", supDeliveryDetail.getAcc_year());
		mode.addAttribute("acc_month", supDeliveryDetail.getAcc_month());
		mode.addAttribute("inv_id", supDeliveryDetail.getInv_id());
		mode.addAttribute("inv_no", supDeliveryDetail.getInv_no());
		mode.addAttribute("inv_model", supDeliveryDetail.getInv_model());
		mode.addAttribute("unit_code", supDeliveryDetail.getUnit_code());
		mode.addAttribute("price", supDeliveryDetail.getPrice());
		mode.addAttribute("amount", supDeliveryDetail.getAmount());
		mode.addAttribute("amount_money", supDeliveryDetail.getAmount_money());
		mode.addAttribute("batch_no", supDeliveryDetail.getBatch_no());
		mode.addAttribute("fac_date", supDeliveryDetail.getFac_date());
		mode.addAttribute("inva_date", supDeliveryDetail.getInva_date());
		mode.addAttribute("disinfect_date", supDeliveryDetail.getDisinfect_date());
		mode.addAttribute("sn", supDeliveryDetail.getSn());
		mode.addAttribute("fac_id", supDeliveryDetail.getFac_id());
		mode.addAttribute("fac_no", supDeliveryDetail.getFac_no());
		mode.addAttribute("note", supDeliveryDetail.getNote());

		return "hrp/sup/supdeliverydetail/supDeliveryDetailUpdate";
	}

	/**
	 * @Description 更新数据 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/updateSupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupDeliveryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

		String supDeliveryDetailJson = supDeliveryDetailService.update(mapVo);

		return JSONObject.parseObject(supDeliveryDetailJson);
	}

	/**
	 * @Description 更新数据 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/addOrUpdateSupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateSupDeliveryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supDeliveryDetailJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

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

			supDeliveryDetailJson = supDeliveryDetailService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(supDeliveryDetailJson);
	}

	/**
	 * @Description 删除数据 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/deleteSupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupDeliveryDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("delivery_no", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("hos_id", ids[2]);
			mapVo.put("copy_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("inv_id", ids[6]);
			mapVo.put("inv_no", ids[7]);

			listVo.add(mapVo);

		}

		String supDeliveryDetailJson = supDeliveryDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(supDeliveryDetailJson);

	}

	/**
	 * @Description 查询数据 100102 送货单明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/querySupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupDeliveryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

		String supDeliveryDetail = supDeliveryDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(supDeliveryDetail);

	}

	/**
	 * @Description 导入跳转页面 100102 送货单明细表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/supDeliveryDetailImportPage", method = RequestMethod.GET)
	public String supDeliveryDetailImportPage(Model mode) throws Exception {

		return "hrp/sup/supdeliverydetail/supDeliveryDetailImport";

	}

	/**
	 * @Description 下载导入模版 100102 送货单明细表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/sup/supdeliverydetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sup\\downTemplate", "100102 送货单明细表.xls");

		return null;
	}

	/**
	 * @Description 导入数据 100102 送货单明细表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/readSupDeliveryDetailFiles", method = RequestMethod.POST)
	public String readSupDeliveryDetailFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
	        throws IOException {

		List<SupDeliveryDetail> list_err = new ArrayList<SupDeliveryDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				SupDeliveryDetail supDeliveryDetail = new SupDeliveryDetail();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					supDeliveryDetail.setDetail_id(Long.valueOf(temp[0]));
					mapVo.put("delivery_id", temp[0]);

				} else {

					err_sb.append("送货单明细ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					supDeliveryDetail.setDelivery_no(temp[1]);
					mapVo.put("delivery_no", temp[1]);

				} else {

					err_sb.append("送货单号为空  ");

				}

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[5])) {

					supDeliveryDetail.setAcc_year(temp[5]);
					mapVo.put("acc_year", temp[5]);

				} else {

					err_sb.append("统计年度为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					supDeliveryDetail.setAcc_month(temp[6]);
					mapVo.put("acc_month", temp[6]);

				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					supDeliveryDetail.setInv_id(Long.valueOf(temp[7]));
					mapVo.put("inv_id", temp[7]);

				} else {

					err_sb.append("材料D为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					supDeliveryDetail.setInv_no(Long.valueOf(temp[8]));
					mapVo.put("inv_no", temp[8]);

				} else {

					err_sb.append("材料NO为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					mapVo.put("inv_model", temp[9]);

				} else {

					err_sb.append("规格型号为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					mapVo.put("unit_code", temp[10]);

				} else {

					err_sb.append("计量单位为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					supDeliveryDetail.setPrice(Double.valueOf(temp[11]));
					mapVo.put("price", temp[11]);

				} else {

					err_sb.append("单价为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					supDeliveryDetail.setAmount(Double.valueOf(temp[12]));
					mapVo.put("amount", temp[12]);

				} else {

					err_sb.append("数量为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					supDeliveryDetail.setAmount_money(Double.valueOf(temp[13]));
					mapVo.put("amount_money", temp[13]);

				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					mapVo.put("batch_no", temp[14]);

				} else {

					err_sb.append("批号为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					supDeliveryDetail.setInva_date(DateUtil.stringToDate(temp[15]));
					mapVo.put("inva_date", temp[15]);

				} else {

					err_sb.append("有效日期为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					supDeliveryDetail.setDisinfect_date(DateUtil.stringToDate(temp[16]));
					mapVo.put("disinfect_date", temp[16]);

				} else {

					err_sb.append("灭菌效期为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					mapVo.put("sn", temp[17]);

				} else {

					err_sb.append("条形码为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					supDeliveryDetail.setFac_id(Long.valueOf(temp[18]));
					mapVo.put("fac_id", temp[18]);

				} else {

					err_sb.append("生产厂家为空  ");

				}

				if (StringTool.isNotBlank(temp[19])) {

					mapVo.put("note", temp[19]);

				} else {

					err_sb.append("备注为空  ");

				}

				SupDeliveryDetail data_exc_extis = supDeliveryDetailService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supDeliveryDetail.setError_type(err_sb.toString());

					list_err.add(supDeliveryDetail);

				} else {

					String dataJson = supDeliveryDetailService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupDeliveryDetail data_exc = new SupDeliveryDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 100102 送货单明细表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverydetail/addBatchSupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSupDeliveryDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<SupDeliveryDetail> list_err = new ArrayList<SupDeliveryDetail>();

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

		mapVo.put("mod_code", "10");

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());
		
		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				SupDeliveryDetail supDeliveryDetail = new SupDeliveryDetail();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("delivery_id"))) {

					supDeliveryDetail.setDetail_id(Long.valueOf((String) jsonObj.get("delivery_id")));
					mapVo.put("delivery_id", jsonObj.get("delivery_id"));
				} else {

					err_sb.append("送货单明细ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delivery_no"))) {

					supDeliveryDetail.setDelivery_no((String) jsonObj.get("delivery_no"));
					mapVo.put("delivery_no", jsonObj.get("delivery_no"));
				} else {

					err_sb.append("送货单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("acc_year"))) {

					supDeliveryDetail.setAcc_year((String) jsonObj.get("acc_year"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
				} else {

					err_sb.append("统计年度为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("acc_month"))) {

					supDeliveryDetail.setAcc_month((String) jsonObj.get("acc_month"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {

					supDeliveryDetail.setInv_id(Long.valueOf((String) jsonObj.get("inv_id")));
					mapVo.put("inv_id", jsonObj.get("inv_id"));
				} else {

					err_sb.append("材料D为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_no"))) {

					supDeliveryDetail.setInv_no(Long.valueOf((String) jsonObj.get("inv_no")));
					mapVo.put("inv_no", jsonObj.get("inv_no"));
				} else {

					err_sb.append("材料NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_model"))) {

					mapVo.put("inv_model", jsonObj.get("inv_model"));
				} else {

					err_sb.append("规格型号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("unit_code"))) {

					mapVo.put("unit_code", jsonObj.get("unit_code"));
				} else {

					err_sb.append("计量单位为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("price"))) {

					supDeliveryDetail.setPrice(Double.valueOf((String) jsonObj.get("price")));
					mapVo.put("price", jsonObj.get("price"));
				} else {

					err_sb.append("单价为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("amount"))) {

					supDeliveryDetail.setAmount(Double.valueOf((String) jsonObj.get("amount")));
					mapVo.put("amount", jsonObj.get("amount"));
				} else {

					err_sb.append("数量为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("amount_money"))) {

					supDeliveryDetail.setAmount_money(Double.valueOf((String) jsonObj.get("amount_money")));
					mapVo.put("amount_money", jsonObj.get("amount_money"));
				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("batch_no"))) {

					mapVo.put("batch_no", jsonObj.get("batch_no"));
				} else {

					err_sb.append("批号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inva_date"))) {

					supDeliveryDetail.setInva_date(DateUtil.stringToDate((String) jsonObj.get("inva_date")));
					mapVo.put("inva_date", jsonObj.get("inva_date"));
				} else {

					err_sb.append("有效日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("disinfect_date"))) {

					supDeliveryDetail.setDisinfect_date(DateUtil.stringToDate((String) jsonObj.get("disinfect_date")));
					mapVo.put("disinfect_date", jsonObj.get("disinfect_date"));
				} else {

					err_sb.append("灭菌效期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sn"))) {

					mapVo.put("sn", jsonObj.get("sn"));
				} else {

					err_sb.append("条形码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {

					supDeliveryDetail.setFac_id(Long.valueOf((String) jsonObj.get("fac_id")));
					mapVo.put("fac_id", jsonObj.get("fac_id"));
				} else {

					err_sb.append("生产厂家为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("备注为空  ");

				}

				SupDeliveryDetail data_exc_extis = supDeliveryDetailService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supDeliveryDetail.setError_type(err_sb.toString());

					list_err.add(supDeliveryDetail);

				} else {

					String dataJson = supDeliveryDetailService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupDeliveryDetail data_exc = new SupDeliveryDetail();

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
