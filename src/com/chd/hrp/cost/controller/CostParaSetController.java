/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.entity.CostParaSet;
import com.chd.hrp.cost.service.CostParaSetService;

/**
 * @Description: 成本_分摊参数设置
 * @Table: COST_PARA_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostParaSetController extends BaseController {

	private static Logger logger = Logger.getLogger(CostParaSetController.class);

	// 引入Service服务
	@Resource(name = "costParaSetService")
	private final CostParaSetService costParaSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/costParaSetMainPage", method = RequestMethod.GET)
	public String costParaSetMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/cost/costparaset/costParaSetMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costparaset/costParaSetExtendPage", method = RequestMethod.GET)
	public String costParaSetExtendPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/cost/costparaset/costParaSetExtend";

	}


	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/costParaSetAddPage", method = RequestMethod.GET)
	public String costParaSetAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/cost/costparaset/costParaSetAdd";

	}

	/**
	 * @Description 添加数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/addCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String costParaSetJson = costParaSetService.add(mapVo);

		return JSONObject.parseObject(costParaSetJson);

	}

	/**
	 * @Description 更新跳转页面 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/costParaSetUpdatePage", method = RequestMethod.GET)
	public String costParaSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		CostParaSet costParaSet = new CostParaSet();

		costParaSet = costParaSetService.queryByCode(mapVo);

		mode.addAttribute("group_id", costParaSet.getGroup_id());
		mode.addAttribute("hos_id", costParaSet.getHos_id());
		mode.addAttribute("copy_code", costParaSet.getCopy_code());
		mode.addAttribute("acc_year", costParaSet.getAcc_year());
		mode.addAttribute("acc_month", costParaSet.getAcc_month());
		mode.addAttribute("natur_code", costParaSet.getNatur_code());
		mode.addAttribute("type_code", costParaSet.getType_code());
		mode.addAttribute("para_value", costParaSet.getPara_value());
		mode.addAttribute("para_name", costParaSet.getPara_name());
		mode.addAttribute("natur_name", costParaSet.getNatur_name());
		mode.addAttribute("type_name", costParaSet.getType_name());

		return "hrp/cost/costparaset/costParaSetUpdate";
	}

	/**
	 * @Description 更新数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/updateCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costParaSetJson = costParaSetService.update(mapVo);

		return JSONObject.parseObject(costParaSetJson);
	}

	/**
	 * @Description 更新数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/addOrUpdateCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateCostParaSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String costParaSetJson = "";

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

			costParaSetJson = costParaSetService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(costParaSetJson);
	}

	/**
	 * @Description 删除数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/deleteCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("natur_code", ids[5]);
			mapVo.put("type_code", ids[6]);

			listVo.add(mapVo);

		}

		String costParaSetJson = costParaSetService.deleteBatch(listVo);

		return JSONObject.parseObject(costParaSetJson);

	}

	/**
	 * @Description 删除数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String costParaSetJson = costParaSetService.delete(mapVo);

		return JSONObject.parseObject(costParaSetJson);

	}

	/**
	 * @Description 查询数据 成本_分摊参数设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/queryCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costParaSet = costParaSetService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaSet);

	}

	/**
	 * @Description 导入跳转页面 成本_分摊参数设置
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/costParaSetImportPage", method = RequestMethod.GET)
	public String costParaSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costparaset/costParaSetImport";

	}

	/**
	 * @Description 下载导入模版 成本_分摊参数设置
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/cost/costparaset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "分摊参数设置.xls");

		return null;
	}

	/**
	 * @Description 导入数据 成本_分摊参数设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/readCostParaSetFiles", method = RequestMethod.POST)
	public String readCostParaSetFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<CostParaSet> list_err = new ArrayList<CostParaSet>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostParaSet costParaSet = new CostParaSet();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0])) {

					costParaSet.setAcc_year(temp[0]);
					mapVo.put("acc_year", temp[0]);

				} else {

					err_sb.append("统计年份为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					costParaSet.setAcc_month(temp[1]);
					mapVo.put("acc_month", temp[1]);

				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					costParaSet.setNatur_code(temp[2]);
					mapVo.put("natur_code", temp[2]);

				} else {

					err_sb.append("科室性质为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					costParaSet.setType_code(temp[3]);
					mapVo.put("type_code", temp[3]);

				}
				if (StringTool.isNotBlank(temp[4])) {

					costParaSet.setType_code(temp[4]);
					mapVo.put("type_code", temp[4]);

				} else {

					err_sb.append("分摊类型为空  ");

				}
				if (StringTool.isNotBlank(temp[5])) {

					costParaSet.setType_code(temp[5]);
					mapVo.put("type_code", temp[5]);

				}

				if (StringTool.isNotBlank(temp[6])) {

					costParaSet.setPara_value(temp[6]);
					mapVo.put("para_value", temp[6]);

				} else {

					err_sb.append("分摊参数为空  ");

				}
				if (temp.length > 7) {
					if (StringTool.isNotBlank(temp[7])) {

						costParaSet.setPara_value(temp[7]);
						mapVo.put("para_value", temp[7]);

					}
				}

				CostParaSet data_exc_extis = costParaSetService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					costParaSet.setError_type(err_sb.toString());

					list_err.add(costParaSet);

				} else {

					String dataJson = costParaSetService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			CostParaSet data_exc = new CostParaSet();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 成本_分摊参数设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/addBatchCostParaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostParaSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<CostParaSet> list_err = new ArrayList<CostParaSet>();

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

				CostParaSet costParaSet = new CostParaSet();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("acc_year"))) {

					costParaSet.setAcc_year((String) jsonObj.get("acc_year"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
				} else {

					err_sb.append("统计年份为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("acc_month"))) {

					costParaSet.setAcc_month((String) jsonObj.get("acc_month"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("natur_code"))) {

					costParaSet.setNatur_code((String) jsonObj.get("natur_code"));
					mapVo.put("natur_code", jsonObj.get("natur_code"));
				} else {

					err_sb.append("科室性质为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("type_code"))) {

					costParaSet.setType_code((String) jsonObj.get("type_code"));
					mapVo.put("type_code", jsonObj.get("type_code"));
				} else {

					err_sb.append("分摊类型为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("para_value"))) {

					costParaSet.setPara_value((String) jsonObj.get("para_value"));
					mapVo.put("para_value", jsonObj.get("para_value"));
				} else {

					err_sb.append("分摊参数为空  ");

				}

				CostParaSet data_exc_extis = costParaSetService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					costParaSet.setError_type(err_sb.toString());

					list_err.add(costParaSet);

				} else {

					String dataJson = costParaSetService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			CostParaSet data_exc = new CostParaSet();

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
	 * @Description 查询数据部门字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		List<?> l_map = costParaSetService.queryByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String l_map = costParaSetService.generate(mapVo);

		return JSONObject.parseObject(l_map);
	}

	/**
	 * @Description 继承
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costparaset/inheritance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inheritance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String l_map = costParaSetService.inheritance(mapVo);

		return JSONObject.parseObject(l_map);
	}

	
	
}
