/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

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
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.service.maintain.AssMaintainRecItemService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecService;

/**
 * 
 * @Description: 051203 保养记录项目明细
 * @Table: ASS_MAINTAIN_REC_ITEM
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainRecItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AssMaintainRecItemController.class);

	// 引入Service服务
	
	@Resource(name = "assMaintainRecItemService")
	private final AssMaintainRecItemService assMaintainRecItemService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/assMaintainRecItemMainPage", method = RequestMethod.GET)
	public String assMaintainRecItemMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecitem/assMaintainRecItemMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/assMaintainRecItemAddPage", method = RequestMethod.GET)
	public String assMaintainRecItemAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecitem/assMaintainRecItemAdd";

	}

	/**
	 * @Description 添加数据 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/addAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainRecItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assMaintainRecItemJson = assMaintainRecItemService.addAssMaintainRecItem(mapVo);

			return JSONObject.parseObject(assMaintainRecItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/assMaintainRecItemUpdatePage", method = RequestMethod.GET)
	public String assMaintainRecItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMaintainRecItem assMaintainRecItem = new AssMaintainRecItem();

		assMaintainRecItem = assMaintainRecItemService.queryAssMaintainRecItemByCode(mapVo);

		mode.addAttribute("group_id", assMaintainRecItem.getGroup_id());

		mode.addAttribute("hos_id", assMaintainRecItem.getHos_id());

		mode.addAttribute("copy_code", assMaintainRecItem.getCopy_code());

		mode.addAttribute("rec_id", assMaintainRecItem.getRec_id());

		mode.addAttribute("ass_card_no", assMaintainRecItem.getAss_card_no());

		mode.addAttribute("item_code", assMaintainRecItem.getItem_code());

		mode.addAttribute("item_name", assMaintainRecItem.getItem_name());

		mode.addAttribute("is_maintain", assMaintainRecItem.getIs_maintain());

		mode.addAttribute("is_normal", assMaintainRecItem.getIs_normal());

		mode.addAttribute("note", assMaintainRecItem.getNote());

		return "hrp/ass/assmaintainrecitem/assMaintainRecItemUpdate";
	}

	/**
	 * @Description 更新数据 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/updateAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainRecItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assMaintainRecItemJson = assMaintainRecItemService.updateAssMaintainRecItem(mapVo);

			return JSONObject.parseObject(assMaintainRecItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/addOrUpdateAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainRecItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainRecItemJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				detailVo.put("item_code", detailVo.get("maintain_item_code"));

				detailVo.put("item_name", detailVo.get("maintain_item_name"));

				detailVo.put("maintain_money", detailVo.get("maintain_money"));

				detailVo.put("maintain_unit", detailVo.get("maintain_unit"));

				detailVo.put("maintain_hours", detailVo.get("maintain_hours"));

				detailVo.put("rec_id", mapVo.get("rec_id"));

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				} else {
					detailVo.put("detail_id", detailVo.get("detail_id"));
				}

				assMaintainRecItemJson = assMaintainRecItemService.addOrUpdateAssMaintainRecItem(detailVo);

			}
			return JSONObject.parseObject(assMaintainRecItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 删除数据 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/deleteAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainRecItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("rec_id", ids[3]);

			mapVo.put("ass_card_no", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assMaintainRecItemJson = assMaintainRecItemService.deleteBatchAssMaintainRecItem(listVo);

			return JSONObject.parseObject(assMaintainRecItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051203 保养记录项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/queryAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainRecItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		if (!mapVo.containsKey("rec_id")) {
			mapVo.put("rec_id", "0");
		}
		String assMaintainRecItem = assMaintainRecItemService.queryAssMaintainRecItem(getPage(mapVo));

		return JSONObject.parseObject(assMaintainRecItem);

	}

	/**
	 * @Description 导入跳转页面 051203 保养记录项目明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/assMaintainRecItemImportPage", method = RequestMethod.GET)
	public String assMaintainRecItemImportPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecitem/assMaintainRecItemImport";

	}

	/**
	 * @Description 下载导入模版 051203 保养记录项目明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assmaintainrecitem/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051203 保养记录项目明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 051203 保养记录项目明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/readAssMaintainRecItemFiles", method = RequestMethod.POST)
	public String readAssMaintainRecItemFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssMaintainRecItem> list_err = new ArrayList<AssMaintainRecItem>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssMaintainRecItem assMaintainRecItem = new AssMaintainRecItem();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assMaintainRecItem.setRec_id(Long.valueOf(temp[3]));
					mapVo.put("rec_id", temp[3]);

				} else {

					err_sb.append("保养ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assMaintainRecItem.setAss_card_no(temp[4]);
					mapVo.put("ass_card_no", temp[4]);

				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assMaintainRecItem.setItem_code(temp[5]);
					mapVo.put("item_code", temp[5]);

				} else {

					err_sb.append("项目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assMaintainRecItem.setItem_name(temp[6]);
					mapVo.put("item_name", temp[6]);

				} else {

					err_sb.append("项目名称为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assMaintainRecItem.setIs_maintain(Integer.valueOf(temp[7]));
					mapVo.put("is_maintain", temp[7]);

				} else {

					err_sb.append("是否执行为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assMaintainRecItem.setIs_normal(Integer.valueOf(temp[8]));
					mapVo.put("is_normal", temp[8]);

				} else {

					err_sb.append("是否正常为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assMaintainRecItem.setNote(temp[9]);
					mapVo.put("note", temp[9]);

				} else {

					err_sb.append("备注为空  ");

				}

				AssMaintainRecItem data_exc_extis = assMaintainRecItemService.queryAssMaintainRecItemByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainRecItem.setError_type(err_sb.toString());

					list_err.add(assMaintainRecItem);

				} else {
					try {
						String dataJson = assMaintainRecItemService.addAssMaintainRecItem(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainRecItem data_exc = new AssMaintainRecItem();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 051203 保养记录项目明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecitem/addBatchAssMaintainRecItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssMaintainRecItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssMaintainRecItem> list_err = new ArrayList<AssMaintainRecItem>();

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

				AssMaintainRecItem assMaintainRecItem = new AssMaintainRecItem();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("rec_id"))) {

					assMaintainRecItem.setRec_id(Long.valueOf((String) jsonObj.get("rec_id")));
					mapVo.put("rec_id", jsonObj.get("rec_id"));
				} else {

					err_sb.append("保养ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {

					assMaintainRecItem.setAss_card_no((String) jsonObj.get("ass_card_no"));
					mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("item_code"))) {

					assMaintainRecItem.setItem_code((String) jsonObj.get("item_code"));
					mapVo.put("item_code", jsonObj.get("item_code"));
				} else {

					err_sb.append("项目编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("item_name"))) {

					assMaintainRecItem.setItem_name((String) jsonObj.get("item_name"));
					mapVo.put("item_name", jsonObj.get("item_name"));
				} else {

					err_sb.append("项目名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_maintain"))) {

					assMaintainRecItem.setIs_maintain(Integer.valueOf((String) jsonObj.get("is_maintain")));
					mapVo.put("is_maintain", jsonObj.get("is_maintain"));
				} else {

					err_sb.append("是否执行为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_normal"))) {

					assMaintainRecItem.setIs_normal(Integer.valueOf((String) jsonObj.get("is_normal")));
					mapVo.put("is_normal", jsonObj.get("is_normal"));
				} else {

					err_sb.append("是否正常为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assMaintainRecItem.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("备注为空  ");

				}

				AssMaintainRecItem data_exc_extis = assMaintainRecItemService.queryAssMaintainRecItemByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainRecItem.setError_type(err_sb.toString());

					list_err.add(assMaintainRecItem);

				} else {
					try {
						String dataJson = assMaintainRecItemService.addAssMaintainRecItem(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainRecItem data_exc = new AssMaintainRecItem();

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
