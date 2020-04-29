/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.disposal;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.entity.disposal.AssDisposalMain;
import com.chd.hrp.ass.service.disposal.AssDisposalMainService;

/**
 * 
 * @Description: 051001资产处置主表
 * @Table: ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDisposalApproveController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDisposalApproveController.class);

	// 引入Service服务
	@Resource(name = "assDisposalMainService")
	private final AssDisposalMainService assDisposalMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalApprovePage", method = RequestMethod.GET)
	public String assDisposalApprovePage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalApprove";

	}

	/**
	 * @Description 051001资产处置主表 (审批数据)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/approveAssDisposal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveAssDisposal(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);

			mapVo.put("state", "2");

			listVo.add(mapVo);

		}
		try {
			String assDisposalMainJson = assDisposalMainService.updateBatchAssDisposalMain(listVo);

			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

	}

	/**
	 * @Description 审批页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalApproveAuditPage", method = RequestMethod.GET)
	public String assDisposalApproveAuditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		System.out.println(mapVo.get("dis_id"));

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalMain assDisposalMain = new AssDisposalMain();

		assDisposalMain = assDisposalMainService.queryAssDisposalMainByCode(mapVo);

		mode.addAttribute("group_id", assDisposalMain.getGroup_id());

		mode.addAttribute("hos_id", assDisposalMain.getHos_id());

		mode.addAttribute("copy_code", assDisposalMain.getCopy_code());

		mode.addAttribute("dis_id", assDisposalMain.getDis_id());

		return "hrp/ass/assdisposalmain/assDisposalApproveAudit";

	}

	@RequestMapping(value = "/hrp/ass/assdisposalmain/updateAssDisposalApproveAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDisposalApproveAudit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String assDisposalMainJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_dis_id", mapVo.get("dis_id"));
		try {
			assDisposalMainJson = assDisposalMainService.updateBatchAssDisposalMain1(mapVo);
			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

}
