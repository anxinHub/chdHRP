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
import com.chd.hrp.ass.entity.repair.AssRepairRecDetail;
import com.chd.hrp.ass.service.repair.AssRepairRecDetailService;

/**
 * 
 * @Description: 051201 资产维修记录明细
 * @Table: ASS_REPAIR_REC_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssRepairRecDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRepairRecDetailController.class);

	// 引入Service服务
	@Resource(name = "assRepairRecDetailService")
	private final AssRepairRecDetailService assRepairRecDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/assRepairRecDetailMainPage", method = RequestMethod.GET)
	public String assRepairRecDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assrepairrecdetail/assRepairRecDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/assRepairRecDetailAddPage", method = RequestMethod.GET)
	public String assRepairRecDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assrepairrecdetail/assRepairRecDetailAdd";

	}

	/**
	 * @Description 添加数据 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/addAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String assRepairRecDetailJson = assRepairRecDetailService.addAssRepairRecDetail(mapVo);

			return JSONObject.parseObject(assRepairRecDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/assRepairRecDetailUpdatePage", method = RequestMethod.GET)
	public String assRepairRecDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssRepairRecDetail assRepairRecDetail = new AssRepairRecDetail();

		assRepairRecDetail = assRepairRecDetailService.queryAssRepairRecDetailByCode(mapVo);

		mode.addAttribute("group_id", assRepairRecDetail.getGroup_id());

		mode.addAttribute("hos_id", assRepairRecDetail.getHos_id());

		mode.addAttribute("copy_code", assRepairRecDetail.getCopy_code());

		//mode.addAttribute("repair_rec_id", assRepairRecDetail.getRec_detail_id());

		//mode.addAttribute("rec_detail_id", assRepairRecDetail.getRec_detail_id());

		mode.addAttribute("inv_code", assRepairRecDetail.getInv_code());

		mode.addAttribute("inv_name", assRepairRecDetail.getInv_name());

		mode.addAttribute("inv_price", assRepairRecDetail.getInv_price());

		mode.addAttribute("inv_num", assRepairRecDetail.getInv_num());

		mode.addAttribute("stuff_money", assRepairRecDetail.getStuff_money());

		return "hrp/ass/assrepairrecdetail/assRepairRecDetailUpdate";
	}

	/**
	 * @Description 更新数据 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/updateAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String assRepairRecDetailJson = assRepairRecDetailService.updateAssRepairRecDetail(mapVo);
			return JSONObject.parseObject(assRepairRecDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/addOrUpdateAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assRepairRecDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			detailVo.put("repair_rec_id", mapVo.get("repair_rec_id"));

			if (detailVo.get("rec_detail_id") == null) {

				detailVo.put("rec_detail_id", "0");

			}
			try {
				assRepairRecDetailJson = assRepairRecDetailService.addOrUpdateAssRepairRecDetail(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assRepairRecDetailJson);
	}

	/**
	 * @Description 删除数据 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/deleteAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRepairRecDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("repair_rec_id", ids[3]);
			mapVo.put("rec_detail_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assRepairRecDetailJson = assRepairRecDetailService.deleteBatchAssRepairRecDetail(listVo);

			return JSONObject.parseObject(assRepairRecDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051201 资产维修记录明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/queryAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (!mapVo.containsKey("repair_rec_id")) {
			mapVo.put("repair_rec_id", "0");
		}
		String assRepairRecDetail = assRepairRecDetailService.queryAssRepairRecDetail(getPage(mapVo));

		return JSONObject.parseObject(assRepairRecDetail);

	}

	/**
	 * @Description 导入跳转页面 051201 资产维修记录明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assrepairrecdetail/assRepairRecDetailImportPage", method = RequestMethod.GET)
	public String assRepairRecDetailImportPage(Model mode) throws Exception {

		return "hrp/ass/assrepairrecdetail/assRepairRecDetailImport";

	}

	/**
	 * @Description 下载导入模版 051201 资产维修记录明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assrepairrecdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051201 资产维修记录明细.xls");

		return null;
	}


}
