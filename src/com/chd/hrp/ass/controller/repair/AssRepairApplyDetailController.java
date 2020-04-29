/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.repair;

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
import com.chd.hrp.ass.entity.repair.AssRepairApplyDetail;
import com.chd.hrp.ass.service.repair.AssRepairApplyDetailService;

/**
 * 
 * @Description: 051201 资产维修明细
 * @Table: ASS_REPAIR_APPLY_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssRepairApplyDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRepairApplyDetailController.class);

	// 引入Service服务
	@Resource(name = "assRepairApplyDetailService")
	private final AssRepairApplyDetailService assRepairApplyDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/assRepairApplyDetailMainPage", method = RequestMethod.GET)
	public String assRepairApplyDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapplydetail/assRepairApplyDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/assRepairApplyDetailAddPage", method = RequestMethod.GET)
	public String assRepairApplyDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapplydetail/assRepairApplyDetailAdd";

	}

	/**
	 * @Description 添加数据 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/addAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {

			String assRepairApplyDetailJson = assRepairApplyDetailService.addAssRepairApplyDetail(mapVo);

			return JSONObject.parseObject(assRepairApplyDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/assRepairApplyDetailUpdatePage", method = RequestMethod.GET)
	public String assRepairApplyDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssRepairApplyDetail assRepairApplyDetail = new AssRepairApplyDetail();

		assRepairApplyDetail = assRepairApplyDetailService.queryAssRepairApplyDetailByCode(mapVo);

		mode.addAttribute("group_id", assRepairApplyDetail.getGroup_id());
		mode.addAttribute("hos_id", assRepairApplyDetail.getHos_id());
		mode.addAttribute("copy_code", assRepairApplyDetail.getCopy_code());
		mode.addAttribute("detail_id", assRepairApplyDetail.getDetail_id());
		mode.addAttribute("apply_id", assRepairApplyDetail.getApply_id());
		mode.addAttribute("ass_card_no", assRepairApplyDetail.getAss_card_no());
		mode.addAttribute("trouble_desc", assRepairApplyDetail.getTrouble_desc());

		return "hrp/ass/assrepairapplydetail/assRepairApplyDetailUpdate";
	}

	/**
	 * @Description 更新数据 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/updateAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String assRepairApplyDetailJson = assRepairApplyDetailService.updateAssRepairApplyDetail(mapVo);

			return JSONObject.parseObject(assRepairApplyDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/addOrUpdateAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRepairApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assRepairApplyDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			detailVo.put("apply_id", mapVo.get("apply_id"));

			if (detailVo.get("detail_id") == null) {

				detailVo.put("detail_id", "0");

			}
			try {
				assRepairApplyDetailJson = assRepairApplyDetailService.addOrUpdateAssRepairApplyDetail(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assRepairApplyDetailJson);
	}

	/**
	 * @Description 删除数据 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/deleteAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairApplyDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("detail_id", ids[3]);
			mapVo.put("apply_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);

			listVo.add(mapVo);

		}
		try {
			String assRepairApplyDetailJson = assRepairApplyDetailService.deleteBatchAssRepairApplyDetail(listVo);

			return JSONObject.parseObject(assRepairApplyDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051201 资产维修明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/queryAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (!mapVo.containsKey("apply_id")) {
			mapVo.put("apply_id", "0");
		}
		String assRepairApplyDetail = assRepairApplyDetailService.queryAssRepairApplyDetail(getPage(mapVo));

		return JSONObject.parseObject(assRepairApplyDetail);

	}

	/**
	 * @Description 导入跳转页面 051201 资产维修明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/assRepairApplyDetailImportPage", method = RequestMethod.GET)
	public String assRepairApplyDetailImportPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapplydetail/assRepairApplyDetailImport";

	}

	/**
	 * @Description 下载导入模版 051201 资产维修明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assrepairapplydetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051201 资产维修明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 051201 资产维修明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/readAssRepairApplyDetailFiles", method = RequestMethod.POST)
	public String readAssRepairApplyDetailFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssRepairApplyDetail> list_err = new ArrayList<AssRepairApplyDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssRepairApplyDetail assRepairApplyDetail = new AssRepairApplyDetail();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assRepairApplyDetail.setDetail_id(Long.valueOf(temp[3]));
					mapVo.put("detail_id", temp[3]);

				} else {

					err_sb.append("申请明细序号为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assRepairApplyDetail.setApply_id(Long.valueOf(temp[4]));
					mapVo.put("apply_id", temp[4]);

				} else {

					err_sb.append("申请单号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assRepairApplyDetail.setAss_card_no(temp[5]);
					mapVo.put("ass_card_no", temp[5]);

				} else {

					err_sb.append("固定资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assRepairApplyDetail.setTrouble_desc(temp[6]);
					mapVo.put("trouble_desc", temp[6]);

				} else {

					err_sb.append("故障说明为空  ");

				}

				AssRepairApplyDetail data_exc_extis = assRepairApplyDetailService
						.queryAssRepairApplyDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRepairApplyDetail.setError_type(err_sb.toString());

					list_err.add(assRepairApplyDetail);

				} else {
					try {
						String dataJson = assRepairApplyDetailService.addAssRepairApplyDetail(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssRepairApplyDetail data_exc = new AssRepairApplyDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 051201 资产维修明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assrepairapplydetail/addBatchAssRepairApplyDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssRepairApplyDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssRepairApplyDetail> list_err = new ArrayList<AssRepairApplyDetail>();

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

				AssRepairApplyDetail assRepairApplyDetail = new AssRepairApplyDetail();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("detail_id"))) {

					assRepairApplyDetail.setDetail_id(Long.valueOf((String) jsonObj.get("detail_id")));
					mapVo.put("detail_id", jsonObj.get("detail_id"));
				} else {

					err_sb.append("申请明细序号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("apply_id"))) {

					assRepairApplyDetail.setApply_id(Long.valueOf((String) jsonObj.get("apply_id")));
					mapVo.put("apply_id", jsonObj.get("apply_id"));
				} else {

					err_sb.append("申请单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {

					assRepairApplyDetail.setAss_card_no((String) jsonObj.get("ass_card_no"));
					mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
				} else {

					err_sb.append("固定资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("trouble_desc"))) {

					assRepairApplyDetail.setTrouble_desc((String) jsonObj.get("trouble_desc"));
					mapVo.put("trouble_desc", jsonObj.get("trouble_desc"));
				} else {

					err_sb.append("故障说明为空  ");

				}

				AssRepairApplyDetail data_exc_extis = assRepairApplyDetailService
						.queryAssRepairApplyDetailByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRepairApplyDetail.setError_type(err_sb.toString());

					list_err.add(assRepairApplyDetail);

				} else {
					try {
						String dataJson = assRepairApplyDetailService.addAssRepairApplyDetail(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssRepairApplyDetail data_exc = new AssRepairApplyDetail();

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
