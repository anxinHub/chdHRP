
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.dict;

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
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.service.dict.AssBillNoService;

/**
 * 
 * @Description: 050110 资产单据号规则设置
 * @Table: ASS_BILL_NO
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBillNoController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBillNoController.class);

	// 引入Service服务
	@Resource(name = "assBillNoService")
	private final AssBillNoService assBillNoService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/assBillNoMainPage", method = RequestMethod.GET)
	public String assBillNoMainPage(Model mode) throws Exception {

		return "hrp/ass/assbillno/assBillNoMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/assBillNoAddPage", method = RequestMethod.GET)
	public String assBillNoAddPage(Model mode) throws Exception {

		return "hrp/ass/assbillno/assBillNoAdd";

	}

	/**
	 * @Description 添加数据 050110 资产单据号规则设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/addAssBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBillNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assBillNoJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bill_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bill_name").toString()));

		try {

			assBillNoJson = assBillNoService.addAssBillNo(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBillNoJson);

	}

	/**
	 * @Description 更新跳转页面 050110 资产单据号规则设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/assBillNoUpdatePage", method = RequestMethod.GET)
	public String assBillNoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBillNo assBillNo = new AssBillNo();

		assBillNo = assBillNoService.queryAssBillNoByCode(mapVo);

		mode.addAttribute("group_id", assBillNo.getGroup_id());
		mode.addAttribute("hos_id", assBillNo.getHos_id());
		mode.addAttribute("copy_code", assBillNo.getCopy_code());
		mode.addAttribute("bill_table", assBillNo.getBill_table());
		mode.addAttribute("bill_name", assBillNo.getBill_name());
		mode.addAttribute("pref", assBillNo.getPref());
		mode.addAttribute("seq_no", assBillNo.getSeq_no());
		mode.addAttribute("wbx_code", assBillNo.getWxb_code());
		mode.addAttribute("spell_code", assBillNo.getSpell_code());
		mode.addAttribute("max_no", assBillNo.getMax_no());

		return "hrp/ass/assbillno/assBillNoUpdate";
	}

	/**
	 * @Description 更新数据 050110 资产单据号规则设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/updateAssBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBillNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assBillNoJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		// mapVo.put("spell_code",
		// StringTool.toPinyinShouZiMu(mapVo.get("bill_name").toString()));
		// mapVo.put("wbx_code",
		// StringTool.toWuBi(mapVo.get("bill_name").toString()));
		try {

			assBillNoJson = assBillNoService.updateAssBillNo(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBillNoJson);
	}

	/**
	 * @Description 删除数据 050110 资产单据号规则设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/deleteAssBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBillNo(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assBillNoJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bill_table", ids[3]);

			listVo.add(mapVo);

		}

		try {

			assBillNoJson = assBillNoService.deleteBatchAssBillNo(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBillNoJson);

	}

	/**
	 * @Description 查询数据 050110 资产单据号规则设置
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/queryAssBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assBillNo = assBillNoService.queryAssBillNo(getPage(mapVo));

		return JSONObject.parseObject(assBillNo);

	}

	/**
	 * @Description 导入跳转页面 050110 资产单据号规则设置
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbillno/assBillNoImportPage", method = RequestMethod.GET)
	public String assBillNoImportPage(Model mode) throws Exception {

		return "hrp/ass/assbillno/assBillNoImport";

	}

	/**
	 * @Description 下载导入模版 050110 资产单据号规则设置
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assbillno/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "downTemplate\\资产单据号规则设置", ".xls");

		return null;
	}

	/**
	 * @Description 导入数据 050110 资产单据号规则设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/assbillno/readAssBillNoFiles", method = RequestMethod.POST)
	public String readAssBillNoFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssBillNo> list_err = new ArrayList<AssBillNo>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssBillNo assBillNo = new AssBillNo();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					assBillNo.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assBillNo.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assBillNo.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					assBillNo.setBill_table(temp[3]);

					mapVo.put("bill_table", temp[3]);

				} else {

					err_sb.append("单据编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assBillNo.setBill_name(temp[4]);

					mapVo.put("bill_name", temp[4]);

				} else {

					err_sb.append("单据名称为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assBillNo.setPref(temp[6]);

					mapVo.put("pref", temp[6]);

				} else {

					err_sb.append("标准前缀为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assBillNo.setSeq_no(Integer.valueOf(temp[7]));

					mapVo.put("seq_no", temp[7]);

				} else {

					err_sb.append("流水号位数为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assBillNo.setWxb_code(temp[10]);

					mapVo.put("wxb_code", temp[10]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assBillNo.setSpell_code(temp[11]);

					mapVo.put("spell_code", temp[11]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				AssBillNo data_exc_extis = assBillNoService.queryAssBillNoByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBillNo.setError_type(err_sb.toString());

					list_err.add(assBillNo);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					try {

						String dataJson = assBillNoService.addAssBillNo(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssBillNo data_exc = new AssBillNo();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050110 资产单据号规则设置
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/assbillno/addBatchAssBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssBillNo(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssBillNo> list_err = new ArrayList<AssBillNo>();

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

				AssBillNo assBillNo = new AssBillNo();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					assBillNo.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					assBillNo.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					assBillNo.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("bill_table"))) {

					assBillNo.setBill_table(jsonObj.get("bill_table").toString());

					mapVo.put("bill_table", jsonObj.get("bill_table"));

				} else {

					err_sb.append("单据编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("bill_name"))) {

					assBillNo.setBill_name((String) jsonObj.get("bill_name"));

					mapVo.put("bill_name", jsonObj.get("bill_name"));

				} else {

					err_sb.append("单据名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("pref"))) {

					assBillNo.setPref((String) jsonObj.get("pref"));

					mapVo.put("pref", jsonObj.get("pref"));

				} else {

					err_sb.append("标准前缀为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("seq_no"))) {

					assBillNo.setSeq_no(Integer.valueOf((String) jsonObj.get("seq_no")));

					mapVo.put("is_year_pref", jsonObj.get("seq_no"));

				} else {

					err_sb.append("是否使用年前缀为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("wxb_code"))) {

					assBillNo.setWxb_code((String) jsonObj.get("wxb_code"));

					mapVo.put("wxb_code", jsonObj.get("wxb_code"));

				} else {

					err_sb.append("五笔码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					assBillNo.setSpell_code((String) jsonObj.get("spell_code"));

					mapVo.put("spell_code", jsonObj.get("spell_code"));

				} else {

					err_sb.append("拼音码为空  ");

				}

				AssBillNo data_exc_extis = assBillNoService.queryAssBillNoByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBillNo.setError_type(err_sb.toString());

					list_err.add(assBillNo);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					try {

						String dataJson = assBillNoService.addAssBillNo(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssBillNo data_exc = new AssBillNo();

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
