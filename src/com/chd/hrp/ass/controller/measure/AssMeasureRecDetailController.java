/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.measure;

import java.io.IOException;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.measure.AssMeasureRecDetail;
import com.chd.hrp.ass.service.measure.AssMeasurePlanAssService;
import com.chd.hrp.ass.service.measure.AssMeasureRecDetailService;

/**
 * 
 * @Description: 051204 检测计量记录明细
 * @Table: ASS_MEASURE_REC_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMeasureRecDetailController extends BaseController { 

	private static Logger logger = Logger.getLogger(AssMeasureRecDetailController.class);

	// 引入Service服务
	@Resource(name = "assMeasureRecDetailService")
	private final AssMeasureRecDetailService assMeasureRecDetailService = null;
	
	@Resource(name = "assMeasurePlanAssService")
	private final AssMeasurePlanAssService assMeasurePlanAssService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/assMeasureRecDetailMainPage", method = RequestMethod.GET)
	public String assMeasureRecDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurerecdetail/assMeasureRecDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/assMeasureRecDetailAddPage", method = RequestMethod.GET)
	public String assMeasureRecDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurerecdetail/assMeasureRecDetailAdd";

	}

	/**
	 * @Description 添加数据 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/addAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMeasureRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assMeasureRecDetailJson = assMeasureRecDetailService.addAssMeasureRecDetail(mapVo);

			return JSONObject.parseObject(assMeasureRecDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/assMeasureRecDetailUpdatePage", method = RequestMethod.GET)
	public String assMeasureRecDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMeasureRecDetail assMeasureRecDetail = new AssMeasureRecDetail();

		assMeasureRecDetail = assMeasureRecDetailService.queryAssMeasureRecDetailByCode(mapVo);

		mode.addAttribute("group_id", assMeasureRecDetail.getGroup_id());
		mode.addAttribute("hos_id", assMeasureRecDetail.getHos_id());
		mode.addAttribute("copy_code", assMeasureRecDetail.getCopy_code());
		mode.addAttribute("rec_id", assMeasureRecDetail.getRec_id());
		mode.addAttribute("ass_card_no", assMeasureRecDetail.getAss_card_no());
		mode.addAttribute("measure_result", assMeasureRecDetail.getMeasure_result());
		mode.addAttribute("measure_memo", assMeasureRecDetail.getMeasure_memo());
		mode.addAttribute("measure_idea", assMeasureRecDetail.getMeasure_idea());

		return "hrp/ass/assmeasurerecdetail/assMeasureRecDetailUpdate";
	}

	/**
	 * @Description 更新数据 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/updateAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMeasureRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String assMeasureRecDetailJson = assMeasureRecDetailService.updateAssMeasureRecDetail(mapVo);

			return JSONObject.parseObject(assMeasureRecDetailJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/addOrUpdateAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMeasureRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMeasureRecDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			detailVo.put("rec_id", mapVo.get("rec_id"));

			if (detailVo.get("detail_id") == null) {
				detailVo.put("detail_id", "0");
			}
			try {
				assMeasureRecDetailJson = assMeasureRecDetailService.addOrUpdateAssMeasureRecDetail(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMeasureRecDetailJson);
	}

	/**
	 * @Description 删除数据 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/deleteAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMeasureRecDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			String assMeasureRecDetailJson = assMeasureRecDetailService.deleteBatchAssMeasureRecDetail(listVo);

			return JSONObject.parseObject(assMeasureRecDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051204 检测计量记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/queryAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasureRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (!mapVo.containsKey("rec_id")) {
			mapVo.put("rec_id", "0");
		}
		
		if(("1").equals(mapVo.get("ass_nature")) || ("2").equals(mapVo.get("ass_nature"))  || ("3").equals(mapVo.get("ass_nature")) || ("4").equals(mapVo.get("ass_nature")) || ("5").equals(mapVo.get("ass_nature")) || ("6").equals(mapVo.get("ass_nature"))){
			String ass_nature = "0"+ mapVo.get("ass_nature");
			mapVo.put("ass_nature", ass_nature);
		}
		String assMeasureRecDetail = assMeasureRecDetailService.queryAssMeasureRecDetail(getPage(mapVo));

		return JSONObject.parseObject(assMeasureRecDetail);

	}

	/**
	 * @Description 导入跳转页面 051204 检测计量记录明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/assMeasureRecDetailImportPage", method = RequestMethod.GET)
	public String assMeasureRecDetailImportPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurerecdetail/assMeasureRecDetailImport";

	}

	/**
	 * @Description 下载导入模版 051204 检测计量记录明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assmeasurerecdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051204 检测计量记录明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 051204 检测计量记录明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/readAssMeasureRecDetailFiles", method = RequestMethod.POST)
	public String readAssMeasureRecDetailFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssMeasureRecDetail> list_err = new ArrayList<AssMeasureRecDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssMeasureRecDetail assMeasureRecDetail = new AssMeasureRecDetail();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assMeasureRecDetail.setRec_id(Long.valueOf(temp[3]));
					mapVo.put("rec_id", temp[3]);

				} else {

					err_sb.append("计量记录ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assMeasureRecDetail.setAss_card_no(temp[4]);
					mapVo.put("ass_card_no", temp[4]);

				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assMeasureRecDetail.setMeasure_result(Integer.valueOf(temp[5]));
					mapVo.put("measure_result", temp[5]);

				} else {

					err_sb.append("计量结果为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assMeasureRecDetail.setMeasure_memo(temp[6]);
					mapVo.put("measure_memo", temp[6]);

				} else {

					err_sb.append("计量说明为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assMeasureRecDetail.setMeasure_idea(temp[7]);
					mapVo.put("measure_idea", temp[7]);

				} else {

					err_sb.append("处理意见为空  ");

				}

				AssMeasureRecDetail data_exc_extis = assMeasureRecDetailService.queryAssMeasureRecDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMeasureRecDetail.setError_type(err_sb.toString());

					list_err.add(assMeasureRecDetail);

				} else {
					try {
						String dataJson = assMeasureRecDetailService.addAssMeasureRecDetail(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssMeasureRecDetail data_exc = new AssMeasureRecDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 051204 检测计量记录明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerecdetail/addBatchAssMeasureRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssMeasureRecDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssMeasureRecDetail> list_err = new ArrayList<AssMeasureRecDetail>();

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

				AssMeasureRecDetail assMeasureRecDetail = new AssMeasureRecDetail();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("rec_id"))) {

					assMeasureRecDetail.setRec_id(Long.valueOf((String) jsonObj.get("rec_id")));
					mapVo.put("rec_id", jsonObj.get("rec_id"));
				} else {

					err_sb.append("计量记录ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {

					assMeasureRecDetail.setAss_card_no((String) jsonObj.get("ass_card_no"));
					mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_result"))) {

					assMeasureRecDetail.setMeasure_result(Integer.valueOf((String) jsonObj.get("measure_result")));
					mapVo.put("measure_result", jsonObj.get("measure_result"));
				} else {

					err_sb.append("计量结果为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_memo"))) {

					assMeasureRecDetail.setMeasure_memo((String) jsonObj.get("measure_memo"));
					mapVo.put("measure_memo", jsonObj.get("measure_memo"));
				} else {

					err_sb.append("计量说明为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("measure_idea"))) {

					assMeasureRecDetail.setMeasure_idea((String) jsonObj.get("measure_idea"));
					mapVo.put("measure_idea", jsonObj.get("measure_idea"));
				} else {

					err_sb.append("处理意见为空  ");

				}

				AssMeasureRecDetail data_exc_extis = assMeasureRecDetailService.queryAssMeasureRecDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMeasureRecDetail.setError_type(err_sb.toString());

					list_err.add(assMeasureRecDetail);

				} else {
					try {
						String dataJson = assMeasureRecDetailService.addAssMeasureRecDetail(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssMeasureRecDetail data_exc = new AssMeasureRecDetail();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	/**
	 * @Description 引入计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/queryMeasurePlanRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMeasurePlanRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String assMaintainPlanRecJson = "";
		
		assMaintainPlanRecJson = assMeasurePlanAssService.queryMeasurePlanRec(getPage(mapVo));
		
		return JSONObject.parseObject(assMaintainPlanRecJson);

	}

}
