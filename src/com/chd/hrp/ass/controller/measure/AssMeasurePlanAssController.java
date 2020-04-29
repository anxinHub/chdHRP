/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.measure;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.ass.entity.measure.AssMeasurePlanAss;
import com.chd.hrp.ass.service.measure.AssMeasurePlanAssService;

/**
 * 
 * @Description: 051204 检查计量计划资产明细
 * @Table: ASS_MEASURE_PLAN_ASS
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMeasurePlanAssController extends BaseController {

	private static Logger logger = Logger.getLogger(AssMeasurePlanAssController.class);

	// 引入Service服务
	@Resource(name = "assMeasurePlanAssService")
	private final AssMeasurePlanAssService assMeasurePlanAssService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/assMeasurePlanAssMainPage", method = RequestMethod.GET)
	public String assMeasurePlanAssMainPage(Model mode) throws Exception {

		return "hrp/ass/assmeasureplanass/assMeasurePlanAssMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/assMeasurePlanAssAddPage", method = RequestMethod.GET)
	public String assMeasurePlanAssAddPage(Model mode) throws Exception {

		return "hrp/ass/assmeasureplanass/assMeasurePlanAssAdd";

	}

	/**
	 * @Description 添加数据 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/addAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMeasurePlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {

			String assMeasurePlanAssJson = assMeasurePlanAssService.addOrUpdateAssMeasurePlanAss(mapVo);

			return JSONObject.parseObject(assMeasurePlanAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/assMeasurePlanAssUpdatePage", method = RequestMethod.GET)
	public String assMeasurePlanAssUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMeasurePlanAss assMeasurePlanAss = new AssMeasurePlanAss();

		assMeasurePlanAss = assMeasurePlanAssService.queryAssMeasurePlanAssByCode(mapVo);

		mode.addAttribute("group_id", assMeasurePlanAss.getGroup_id());

		mode.addAttribute("hos_id", assMeasurePlanAss.getHos_id());

		mode.addAttribute("copy_code", assMeasurePlanAss.getCopy_code());

		mode.addAttribute("plan_id", assMeasurePlanAss.getPlan_id());

		mode.addAttribute("ass_card_no", assMeasurePlanAss.getAss_card_no());

		mode.addAttribute("is_inner", assMeasurePlanAss.getIs_inner());

		mode.addAttribute("outer_measure_org", assMeasurePlanAss.getOuter_measure_org());

		mode.addAttribute("measure_cycle", assMeasurePlanAss.getMeasure_cycle());

		mode.addAttribute("measure_kind", assMeasurePlanAss.getMeasure_kind());

		mode.addAttribute("plan_exec_date", assMeasurePlanAss.getPlan_exec_date());

		mode.addAttribute("measure_desc", assMeasurePlanAss.getMeasure_desc());

		return "hrp/ass/assmeasureplanass/assMeasurePlanAssUpdate";
	}

	/**
	 * @Description 更新数据 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/updateAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMeasurePlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String assMeasurePlanAssJson = assMeasurePlanAssService.updateAssMeasurePlanAss(mapVo);

			return JSONObject.parseObject(assMeasurePlanAssJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/addOrUpdateAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMeasurePlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMeasurePlanAssJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

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

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			detailVo.put("plan_id", mapVo.get("plan_id"));

			if (detailVo.get("detail_id") == null) {
				detailVo.put("detail_id", "0");
			}
			if (detailVo.get("plan_exec_date") == null) {

				detailVo.put("plan_exec_date", "");

			} else {

				detailVo.put("plan_exec_date",
						DateUtil.stringToDate(detailVo.get("plan_exec_date").toString(), "yyyy-MM-dd"));

			}
			try {
				assMeasurePlanAssJson = assMeasurePlanAssService.addOrUpdateAssMeasurePlanAss(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMeasurePlanAssJson);
	}

	/**
	 * @Description 删除数据 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/deleteAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMeasurePlanAss(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();


			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			//mapVo.put("plan_id", ids[3]);

			mapVo.put("ass_card_no", id);

			listVo.add(mapVo);

		}
		try {
			String assMeasurePlanAssJson = assMeasurePlanAssService.deleteBatchAssMeasurePlanAss(listVo);

			return JSONObject.parseObject(assMeasurePlanAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051204 检查计量计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/queryAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasurePlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (!mapVo.containsKey("plan_id")) {
			mapVo.put("plan_id", "0");
		}
		String assMeasurePlanAss = assMeasurePlanAssService.queryAssMeasurePlanAss(getPage(mapVo));

		return JSONObject.parseObject(assMeasurePlanAss);

	}

	/**
	 * @Description 导入跳转页面 051204 检查计量计划资产明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/assMeasurePlanAssImportPage", method = RequestMethod.GET)
	public String assMeasurePlanAssImportPage(Model mode) throws Exception {

		return "hrp/ass/assmeasureplanass/assMeasurePlanAssImport";

	}

	/**
	 * @Description 下载导入模版 051204 检查计量计划资产明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assmeasureplanass/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051204 检查计量计划资产明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 051204 检查计量计划资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/readAssMeasurePlanAssFiles", method = RequestMethod.POST)
	public String readAssMeasurePlanAssFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssMeasurePlanAss> list_err = new ArrayList<AssMeasurePlanAss>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssMeasurePlanAss assMeasurePlanAss = new AssMeasurePlanAss();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assMeasurePlanAss.setPlan_id(Long.valueOf(temp[3]));
					mapVo.put("plan_id", temp[3]);

				} else {

					err_sb.append("计划ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assMeasurePlanAss.setAss_card_no(temp[4]);
					mapVo.put("ass_card_no", temp[4]);

				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assMeasurePlanAss.setIs_inner(Integer.valueOf(temp[5]));
					mapVo.put("is_inner", temp[5]);

				} else {

					err_sb.append("是否内部检测为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assMeasurePlanAss.setOuter_measure_org(temp[6]);
					mapVo.put("outer_measure_org", temp[6]);

				} else {

					err_sb.append("外部计量单位为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assMeasurePlanAss.setMeasure_cycle(Integer.valueOf(temp[7]));
					mapVo.put("measure_cycle", temp[7]);

				} else {

					err_sb.append("计量周期为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assMeasurePlanAss.setMeasure_kind(Integer.valueOf(temp[8]));
					mapVo.put("measure_kind", temp[8]);

				} else {

					err_sb.append("计量类别为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assMeasurePlanAss.setPlan_exec_date(DateUtil.stringToDate(temp[9]));
					mapVo.put("plan_exec_date", temp[9]);

				} else {

					err_sb.append("计划计量日期为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assMeasurePlanAss.setMeasure_desc(temp[10]);
					mapVo.put("measure_desc", temp[10]);

				} else {

					err_sb.append("计量说明为空  ");

				}

				AssMeasurePlanAss data_exc_extis = assMeasurePlanAssService.queryAssMeasurePlanAssByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMeasurePlanAss.setError_type(err_sb.toString());

					list_err.add(assMeasurePlanAss);

				} else {
					try {
						String dataJson = assMeasurePlanAssService.addAssMeasurePlanAss(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssMeasurePlanAss data_exc = new AssMeasurePlanAss();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 051204 检查计量计划资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplanass/addBatchAssMeasurePlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssMeasurePlanAss(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssMeasurePlanAss> list_err = new ArrayList<AssMeasurePlanAss>();

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

				AssMeasurePlanAss assMeasurePlanAss = new AssMeasurePlanAss();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("plan_id"))) {

					assMeasurePlanAss.setPlan_id(Long.valueOf((String) jsonObj.get("plan_id")));
					mapVo.put("plan_id", jsonObj.get("plan_id"));
				} else {

					err_sb.append("计划ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {

					assMeasurePlanAss.setAss_card_no((String) jsonObj.get("ass_card_no"));
					mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_inner"))) {

					assMeasurePlanAss.setIs_inner(Integer.valueOf((String) jsonObj.get("is_inner")));
					mapVo.put("is_inner", jsonObj.get("is_inner"));
				} else {

					err_sb.append("是否内部检测为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("outer_measure_org"))) {

					assMeasurePlanAss.setOuter_measure_org((String) jsonObj.get("outer_measure_org"));
					mapVo.put("outer_measure_org", jsonObj.get("outer_measure_org"));
				} else {

					err_sb.append("外部计量单位为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_cycle"))) {

					assMeasurePlanAss.setMeasure_cycle(Integer.valueOf((String) jsonObj.get("measure_cycle")));
					mapVo.put("measure_cycle", jsonObj.get("measure_cycle"));
				} else {

					err_sb.append("计量周期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_kind"))) {

					assMeasurePlanAss.setMeasure_kind(Integer.valueOf((String) jsonObj.get("measure_kind")));
					mapVo.put("measure_kind", jsonObj.get("measure_kind"));
				} else {

					err_sb.append("计量类别为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("plan_exec_date"))) {

					assMeasurePlanAss.setPlan_exec_date(DateUtil.stringToDate((String) jsonObj.get("plan_exec_date")));
					mapVo.put("plan_exec_date", jsonObj.get("plan_exec_date"));
				} else {

					err_sb.append("计划计量日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_desc"))) {

					assMeasurePlanAss.setMeasure_desc((String) jsonObj.get("measure_desc"));
					mapVo.put("measure_desc", jsonObj.get("measure_desc"));
				} else {

					err_sb.append("计量说明为空  ");

				}

				AssMeasurePlanAss data_exc_extis = assMeasurePlanAssService.queryAssMeasurePlanAssByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMeasurePlanAss.setError_type(err_sb.toString());

					list_err.add(assMeasurePlanAss);

				} else {
					try {
						String dataJson = assMeasurePlanAssService.addAssMeasurePlanAss(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssMeasurePlanAss data_exc = new AssMeasurePlanAss();

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
