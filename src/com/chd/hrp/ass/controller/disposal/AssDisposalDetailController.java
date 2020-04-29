/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.disposal;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.hrp.ass.entity.disposal.AssDisposalDetail;
import com.chd.hrp.ass.service.disposal.AssDisposalDetailService;
import com.chd.hrp.ass.service.disposal.AssDisposalMainService;

/**
 * 
 * @Description: 051001 资产处置明细
 * @Table: ASS_DISPOSAL_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDisposalDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDisposalDetailController.class);

	// 引入Service服务
	@Resource(name = "assDisposalDetailService")
	private final AssDisposalDetailService assDisposalDetailService = null;

	@Resource(name = "assDisposalMainService")
	private final AssDisposalMainService assDisposalMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/assDisposalDetailMainPage", method = RequestMethod.GET)
	public String assDisposalDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assdisposaldetail/assDisposalDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/assDisposalDetailAddPage", method = RequestMethod.GET)
	public String assDisposalDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assdisposaldetail/assDisposalDetailAdd";

	}

	/**
	 * @Description 添加数据 051001 资产处置明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/addAssDisposalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDisposalDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String assDisposalDetailJson = assDisposalDetailService.addAssDisposalDetail(mapVo);

			return JSONObject.parseObject(assDisposalDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 按合同生成数据 050601 资产验收明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/initAssDisposalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssDisposalDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String initDisposalDetailJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String confirm_date = String.valueOf(mapVo.get("confirm_date"));
		if (StringUtils.isNotEmpty(confirm_date)) {
			mapVo.put("confirm_date", DateUtil.stringToDate(confirm_date, "yyyy-MM-dd"));
		}
		mapVo.put("dis_id", mapVo.get("dis_id"));
		mapVo.put("ass_dis_no", mapVo.get("ass_dis_no"));
		mapVo.put("year_month", mapVo.get("year_month"));
		mapVo.put("bus_type_code", mapVo.get("bus_type_code"));
		mapVo.put("ass_nature", mapVo.get("ass_nature"));
		String store_id_no = mapVo.get("store_id").toString();
		mapVo.put("store_id", store_id_no.split("@")[0]);
		mapVo.put("store_no", store_id_no.split("@")[1]);
		String dept_id_no = mapVo.get("dept_id").toString();
		mapVo.put("dept_id", dept_id_no.split("@")[0]);
		mapVo.put("dept_no", dept_id_no.split("@")[1]);
		mapVo.put("confirm_id", mapVo.get("confirm_id"));
		mapVo.put("create_emp", SessionManager.getUserId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("create_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));
		mapVo.put("state", "0");
		try {
			initDisposalDetailJson = assDisposalMainService.addOrUpdateAssDisposalMain(mapVo);

			JSONObject jsonObj = JSONArray.parseObject(initDisposalDetailJson);

			String dis_id = (String) jsonObj.get("dis_id");
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

				System.out.println("===" + dis_id);
				mapVo.put("ass_dis_id", dis_id);
				System.out.println("==========================" + dis_id);

				initDisposalDetailJson = assDisposalDetailService.initAssDisposalDetail(mapVo);
			}
			// String initAcceptDetailJson =
			// assAcceptDetailService.initAssAcceptDetail(mapVo);

			return JSONObject.parseObject(initDisposalDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051001 资产处置明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/assDisposalDetailUpdatePage", method = RequestMethod.GET)
	public String assDisposalDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssDisposalDetail assDisposalDetail = new AssDisposalDetail();

		assDisposalDetail = assDisposalDetailService.queryAssDisposalDetailByCode(mapVo);

		mode.addAttribute("group_id", assDisposalDetail.getGroup_id());

		mode.addAttribute("hos_id", assDisposalDetail.getHos_id());

		mode.addAttribute("copy_code", assDisposalDetail.getCopy_code());

		mode.addAttribute("ass_dis_id", assDisposalDetail.getAss_dis_id());

		mode.addAttribute("ass_detail_id", assDisposalDetail.getAss_detail_id());

		mode.addAttribute("ass_card_no", assDisposalDetail.getAss_card_no());

		mode.addAttribute("deal_money", assDisposalDetail.getDeal_money());

		mode.addAttribute("deal_oncome", assDisposalDetail.getDeal_oncome());

		mode.addAttribute("note", assDisposalDetail.getNote());

		return "hrp/ass/assdisposaldetail/assDisposalDetailUpdate";
	}

	/**
	 * @Description 删除数据 051001 资产处置明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/deleteAssDisposalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_detail_id", ids[3]);

			listVo.add(mapVo);

		}
		try {
			String assDisposalDetailJson = assDisposalDetailService.deleteBatchAssDisposalDetail(listVo);

			return JSONObject.parseObject(assDisposalDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051001 资产处置明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposaldetail/queryAssDisposalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (!mapVo.containsKey("ass_dis_id")) {
			mapVo.put("ass_dis_id", "0");
		}
		String assDisposalDetail = assDisposalDetailService.queryAssDisposalDetail(getPage(mapVo));

		return JSONObject.parseObject(assDisposalDetail);

	}

}
