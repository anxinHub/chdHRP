/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bid;

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
import com.chd.hrp.ass.entity.bid.AssBidDetail;
import com.chd.hrp.ass.entity.bid.AssBidMain;
import com.chd.hrp.ass.service.bid.AssBidDetailService;
import com.chd.hrp.ass.service.bid.AssBidMainService;
import com.chd.hrp.ass.service.plan.AssPlanDeptService;

/**
 * 
 * @Description: 050401 招标资产明细
 * @Table: ASS_BID_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBidDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBidDetailController.class);

	// 引入Service服务
	@Resource(name = "assBidDetailService")
	private final AssBidDetailService assBidDetailService = null;

	@Resource(name = "assBidMainService")
	private final AssBidMainService assBidMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/assBidDetailMainPage", method = RequestMethod.GET)
	public String assBidDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assbiddetail/assBidDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/assBidDetailAddPage", method = RequestMethod.GET)
	public String assBidDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assbiddetail/assBidDetailAdd";

	}

	/**
	 * @Description 添加数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/addAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assBidDetailJson = "";

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

			assBidDetailJson = assBidDetailService.addAssBidDetail(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBidDetailJson);

	}

	/**
	 * @Description 更新跳转页面 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/assBidDetailUpdatePage", method = RequestMethod.GET)
	public String assBidDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssBidDetail assBidDetail = new AssBidDetail();

		assBidDetail = assBidDetailService.queryAssBidDetailByCode(mapVo);

		mode.addAttribute("group_id", assBidDetail.getGroup_id());

		mode.addAttribute("hos_id", assBidDetail.getHos_id());

		mode.addAttribute("copy_code", assBidDetail.getCopy_code());

		mode.addAttribute("bid_id", assBidDetail.getBid_id());

		mode.addAttribute("bid_detail_id", assBidDetail.getBid_detail_id());

		mode.addAttribute("bid_no", assBidDetail.getBid_no());

		mode.addAttribute("ass_id", assBidDetail.getAss_id());

		mode.addAttribute("ass_no", assBidDetail.getAss_no());

		mode.addAttribute("ass_spec", assBidDetail.getAss_spec());

		mode.addAttribute("ass_model", assBidDetail.getAss_model());

		mode.addAttribute("ass_num", assBidDetail.getAss_num());

		mode.addAttribute("ass_price", assBidDetail.getAss_price());

		mode.addAttribute("ass_money", assBidDetail.getAss_money());

		mode.addAttribute("fac_id", assBidDetail.getFac_id());

		mode.addAttribute("fac_no", assBidDetail.getFac_no());

		mode.addAttribute("bid_note", assBidDetail.getBid_note());

		return "hrp/ass/assbiddetail/assBidDetailUpdate";
	}

	/**
	 * @Description 更新数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/updateAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBidDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBidDetailJson = "";

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

			assBidDetailJson = assBidDetailService.updateAssBidDetail(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBidDetailJson);
	}

	/**
	 * @Description 更新数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/addOrUpdateAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssBidDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBidDetailJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		AssBidMain bidDetail = assBidMainService.queryAssBidMainByUniqueness(mapVo);

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

			int ass_price = Integer.parseInt(detailVo.get("ass_price").toString());

			int ass_num = Integer.parseInt(detailVo.get("ass_num").toString());

			detailVo.put("ass_money", ass_price * ass_num);

			detailVo.put("bid_id", bidDetail.getBid_id());

			detailVo.put("bid_no", mapVo.get("bid_no"));

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

		return JSONObject.parseObject(assBidDetailJson);
	}

	/**
	 * @Description 删除数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/deleteAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBidDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assBidDetailJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bid_id", ids[3]);

			mapVo.put("bid_detail_id", ids[4]);

			listVo.add(mapVo);

		}

		try {

			assBidDetailJson = assBidDetailService.deleteBatchAssBidDetail(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBidDetailJson);

	}

	/**
	 * @Description 查询数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/queryAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String assBidDetail = assBidDetailService.queryAssBidDetail(getPage(mapVo));

		return JSONObject.parseObject(assBidDetail);

	}

	/**
	 * @Description 导入跳转页面 050401 招标资产明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/assBidDetailImportPage", method = RequestMethod.GET)
	public String assBidDetailImportPage(Model mode) throws Exception {

		return "hrp/ass/assbiddetail/assBidDetailImport";

	}

	/**
	 * @Description 下载导入模版 050401 招标资产明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assbiddetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050401 招标资产明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050401 招标资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/readAssBidDetailFiles", method = RequestMethod.POST)
	public String readAssBidDetailFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssBidDetail> list_err = new ArrayList<AssBidDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssBidDetail assBidDetail = new AssBidDetail();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assBidDetail.setBid_id(Long.valueOf(temp[3]));
					mapVo.put("bid_id", temp[3]);

				} else {

					err_sb.append("招标ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assBidDetail.setBid_detail_id(Long.valueOf(temp[4]));
					mapVo.put("bid_detail_id", temp[4]);

				} else {

					err_sb.append("招标明细ID为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assBidDetail.setBid_no(temp[5]);
					mapVo.put("bid_no", temp[5]);

				} else {

					err_sb.append("招标编号为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assBidDetail.setAss_id(Long.valueOf(temp[6]));
					mapVo.put("ass_id", temp[6]);

				} else {

					err_sb.append("资产ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assBidDetail.setAss_no(Long.valueOf(temp[7]));
					mapVo.put("ass_no", temp[7]);

				} else {

					err_sb.append("资产变更NO为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assBidDetail.setAss_spec(temp[8]);
					mapVo.put("ass_spec", temp[8]);

				} else {

					err_sb.append("规格为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assBidDetail.setAss_model(temp[9]);
					mapVo.put("ass_model", temp[9]);

				} else {

					err_sb.append("型号为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assBidDetail.setAss_num(Integer.valueOf(temp[10]));
					mapVo.put("ass_num", temp[10]);

				} else {

					err_sb.append("数量为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assBidDetail.setAss_price(Double.valueOf(temp[11]));
					mapVo.put("ass_price", temp[11]);

				} else {

					err_sb.append("价格为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assBidDetail.setAss_money(Double.valueOf(temp[12]));
					mapVo.put("ass_money", temp[12]);

				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assBidDetail.setFac_id(Long.valueOf(temp[13]));
					mapVo.put("fac_id", temp[13]);

				} else {

					err_sb.append("生产厂家ID为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assBidDetail.setFac_no(Long.valueOf(temp[14]));
					mapVo.put("fac_no", temp[14]);

				} else {

					err_sb.append("生产厂家NO为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assBidDetail.setBid_note(temp[15]);
					mapVo.put("bid_note", temp[15]);

				} else {

					err_sb.append("备注为空  ");

				}

				AssBidDetail data_exc_extis = assBidDetailService.queryAssBidDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBidDetail.setError_type(err_sb.toString());

					list_err.add(assBidDetail);

				} else {

					try {

						String dataJson = assBidDetailService.addAssBidDetail(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssBidDetail data_exc = new AssBidDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050401 招标资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbiddetail/addBatchAssBidDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssBidDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssBidDetail> list_err = new ArrayList<AssBidDetail>();

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

				AssBidDetail assBidDetail = new AssBidDetail();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("bid_id"))) {

					assBidDetail.setBid_id(Long.valueOf((String) jsonObj.get("bid_id")));
					mapVo.put("bid_id", jsonObj.get("bid_id"));
				} else {

					err_sb.append("招标ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_detail_id"))) {

					assBidDetail.setBid_detail_id(Long.valueOf((String) jsonObj.get("bid_detail_id")));
					mapVo.put("bid_detail_id", jsonObj.get("bid_detail_id"));
				} else {

					err_sb.append("招标明细ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_no"))) {

					assBidDetail.setBid_no((String) jsonObj.get("bid_no"));
					mapVo.put("bid_no", jsonObj.get("bid_no"));
				} else {

					err_sb.append("招标编号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_id"))) {

					assBidDetail.setAss_id(Long.valueOf((String) jsonObj.get("ass_id")));
					mapVo.put("ass_id", jsonObj.get("ass_id"));
				} else {

					err_sb.append("资产ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_no"))) {

					assBidDetail.setAss_no(Long.valueOf((String) jsonObj.get("ass_no")));
					mapVo.put("ass_no", jsonObj.get("ass_no"));
				} else {

					err_sb.append("资产变更NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_spec"))) {

					assBidDetail.setAss_spec((String) jsonObj.get("ass_spec"));
					mapVo.put("ass_spec", jsonObj.get("ass_spec"));
				} else {

					err_sb.append("规格为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_model"))) {

					assBidDetail.setAss_model((String) jsonObj.get("ass_model"));
					mapVo.put("ass_model", jsonObj.get("ass_model"));
				} else {

					err_sb.append("型号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_num"))) {

					assBidDetail.setAss_num(Integer.valueOf((String) jsonObj.get("ass_num")));
					mapVo.put("ass_num", jsonObj.get("ass_num"));
				} else {

					err_sb.append("数量为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_price"))) {

					assBidDetail.setAss_price(Double.valueOf((String) jsonObj.get("ass_price")));
					mapVo.put("ass_price", jsonObj.get("ass_price"));
				} else {

					err_sb.append("价格为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_money"))) {

					assBidDetail.setAss_money(Double.valueOf((String) jsonObj.get("ass_money")));
					mapVo.put("ass_money", jsonObj.get("ass_money"));
				} else {

					err_sb.append("金额为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {

					assBidDetail.setFac_id(Long.valueOf((String) jsonObj.get("fac_id")));
					mapVo.put("fac_id", jsonObj.get("fac_id"));
				} else {

					err_sb.append("生产厂家ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("fac_no"))) {

					assBidDetail.setFac_no(Long.valueOf((String) jsonObj.get("fac_no")));
					mapVo.put("fac_no", jsonObj.get("fac_no"));
				} else {

					err_sb.append("生产厂家NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_note"))) {

					assBidDetail.setBid_note((String) jsonObj.get("bid_note"));
					mapVo.put("bid_note", jsonObj.get("bid_note"));
				} else {

					err_sb.append("备注为空  ");

				}

				AssBidDetail data_exc_extis = assBidDetailService.queryAssBidDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBidDetail.setError_type(err_sb.toString());

					list_err.add(assBidDetail);

				} else {

					try {

						String dataJson = assBidDetailService.addAssBidDetail(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssBidDetail data_exc = new AssBidDetail();

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
