package com.chd.hrp.htcg.controller.audit;

import java.util.Map;

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
import com.chd.hrp.htcg.service.audit.HtcgSchemeDrgsCheckService;
import com.chd.hrp.htcg.serviceImpl.audit.HtcgSchemeDrgsCheckServiceImpl;

@Controller
public class HtcgSchemeDrgsCheckController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeDrgsCheckController.class);

	@Resource(name = "htcgSchemeDrgsCheckService")
	private final HtcgSchemeDrgsCheckService htcgSchemeDrgsCheckService = null;

	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/htcgSchemeDrgsCheckMainPage", method = RequestMethod.GET)
	public String querySchemeDrgsCheckMianPage(Model model) throws Exception {
		return "hrp/htcg/audit/schemeDrgsCheck/htcgSchemeDrgsCheckMain";
	}

	/**
	 * 病种方案审核查询页面
	 * 
	 * @param mapVo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/queryHtcgSchemeDrgsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeDrgsCheck(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDrugDict = htcgSchemeDrgsCheckService.queryHtcgSchemeDrgsCheck(getPage(mapVo));
		return JSONObject.parseObject(htcDrugDict);
	}

	/**
	 * 审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/auditHtcgSchemeDrgsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHtcgSchemeDrgsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcgSchemeDrgsCheckJson  = "";
		try {
			htcgSchemeDrgsCheckJson = htcgSchemeDrgsCheckService.auditHtcgSchemeDrgsCheck(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeDrgsCheckJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgSchemeDrgsCheckJson);
	}

	/**
	 * 消审
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/reAuditHtcgSchemeDrgsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHtcgSchemeDrgsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcgSchemeDrgsCheckJson  = "";
		try {
			htcgSchemeDrgsCheckJson =  htcgSchemeDrgsCheckService.reAuditHtcgSchemeDrgsCheck(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeDrgsCheckJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgSchemeDrgsCheckJson);
	}
	
	/**
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/flagHtcgSchemeDrgsCheckDrgsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> flagHtcgSchemeDrgsCheckDrgsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String audit =htcgSchemeDrgsCheckService.flagHtcgSchemeDrgsCheckDrgsCheck(mapVo);
		return JSONObject.parseObject(audit);
	}
	
	/**
	 * 病种方案综合查询
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/htcgSchemeDrgsCheckReportMainPage", method = RequestMethod.GET)
	public String htcgSchemeDrgsCheckReportMainPage(Model model) throws Exception {
		return "hrp/htcg/audit/schemeDrgsCheck/htcgSchemeDrgsCheckReportMain";
	}
	
	/**
	 * 病种方案查询
	 * 
	 * @param mapVo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/audit/schemeDrgsCheck/querySchemeDrgsCheckReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySchemeDrgsCheckReport(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDrugDict = htcgSchemeDrgsCheckService.querySchemeDrgsCheckReport(getPage(mapVo));
		return JSONObject.parseObject(htcDrugDict);
	}
}
