
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.prm.entity.PrmHosTargetData;
import com.chd.hrp.prm.service.PrmHosTargetDataService;
import com.chd.hrp.prm.service.PrmTargetService;
import com.chd.hrp.sys.service.InfoDictService;

/**
 * 
 * @Description: 0212 院级绩效指标数据表
 * @Table: PRM_HOS_TARGET_DATA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmHosTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmHosTargetDataController.class);

	// 引入Service服务
	@Resource(name = "prmHosTargetDataService")
	private final PrmHosTargetDataService prmHosTargetDataService = null;

	@Resource(name = "prmTargetService")
	private final PrmTargetService prmTargetService = null;

	@Resource(name = "infoDictService")
	private final InfoDictService infoDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/prmHosTargetDataMainPage", method = RequestMethod.GET)
	public String prmHosTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhostargetdata/prmHosTargetDataMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/prmHosTargetDataAddPage", method = RequestMethod.GET)
	public String prmHosTargetDataAddPage(Model mode) throws Exception {

		return "hrp/prm/prmhostargetdata/prmHosTargetDataAdd";

	}

	/**
	 * @Description 添加数据 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/addPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmHosTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmHosTargetDataJson = prmHosTargetDataService.addPrmHosTargetData(mapVo);

		return JSONObject.parseObject(prmHosTargetDataJson);

	}

	/**
	 * @Description 更新跳转页面 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/prmHosTargetDataUpdatePage", method = RequestMethod.GET)
	public String prmHosTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmHosTargetData prmHosTargetData = new PrmHosTargetData();

		prmHosTargetData = prmHosTargetDataService.queryPrmHosTargetDataByCode(mapVo);

		mode.addAttribute("group_id", prmHosTargetData.getGroup_id());
		mode.addAttribute("hos_id", prmHosTargetData.getHos_id());
		mode.addAttribute("copy_code", prmHosTargetData.getCopy_code());
		mode.addAttribute("acc_year", prmHosTargetData.getAcc_year());
		mode.addAttribute("acc_month", prmHosTargetData.getAcc_month());
		mode.addAttribute("target_code", prmHosTargetData.getTarget_code());
		mode.addAttribute("check_hos_id", prmHosTargetData.getCheck_hos_id());
		mode.addAttribute("target_value", prmHosTargetData.getTarget_value());
		mode.addAttribute("is_audit", prmHosTargetData.getIs_audit());
		mode.addAttribute("user_code", prmHosTargetData.getUser_code());
		mode.addAttribute("audit_date", prmHosTargetData.getAudit_date());

		return "hrp/prm/prmhostargetdata/prmHosTargetDataUpdate";
	}

	/**
	 * @Description 更新数据 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/updatePrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmHosTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmHosTargetDataJson = prmHosTargetDataService.updatePrmHosTargetData(mapVo);

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmhostargetdata/saveBatchPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchPrmHosTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("target_code", ids[5]);
			mapVo.put("check_hos_id", ids[6]);
			mapVo.put("target_value", ids[7]);

			listVo.add(mapVo);

		}

		String prmHosTargetDataJson = prmHosTargetDataService.saveBatchPrmHosTargetData(listVo);

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmhostargetdata/reAuditPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetData(@RequestParam Map<String,Object> mapVo, Model mode)
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
		
		mapVo.put("is_audit", 0);
		mapVo.put("audit_date","");
		mapVo.put("user_code","");
		
		String prmHosTargetDataJson = "";
		
		try {
			
			prmHosTargetDataJson = prmHosTargetDataService.reAuditPrmHosTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmhostargetdata/auditPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosTargetData(@RequestParam Map<String,Object> mapVo, Model mode)
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
		
		mapVo.put("is_audit", 1);
		mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		mapVo.put("user_code", SessionManager.getUserCode());
		
		String prmHosTargetDataJson = "";
		
		try {
			
			prmHosTargetDataJson = prmHosTargetDataService.auditPrmHosTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
	
		

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	/**
	 * @Description 删除数据 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/deletePrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
			mapVo.put("target_code", ids[5]);
			mapVo.put("check_hos_id", ids[6]);

			PrmHosTargetData prmHosTargetData = prmHosTargetDataService.queryPrmHosTargetDataByCode(mapVo);

			if (prmHosTargetData.getIs_audit() > 0) {
				return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除\"}");
			}

			listVo.add(mapVo);

		}

		String prmHosTargetDataJson = prmHosTargetDataService.deleteBatchPrmHosTargetData(listVo);

		return JSONObject.parseObject(prmHosTargetDataJson);

	}

	/**
	 * @Description 查询数据 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/queryPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmHosTargetData = prmHosTargetDataService.queryPrmHosTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmHosTargetData);

	}

	/**
	 * @Description 查询数据 0212 院级绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/queryPrmHosTargetPrmTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosTargetPrmTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmHosTargetData = prmHosTargetDataService.queryPrmHosTargetPrmTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmHosTargetData);

	}

	/**
	 * @Description 导入跳转页面 0212 院级绩效指标数据表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/prmHosTargetDataImportPage", method = RequestMethod.GET)
	public String prmHosTargetDataImportPage(Model mode) throws Exception {

		return "hrp/prm/prmhostargetdata/prmHosTargetDataImport";

	}

	/**
	 * @Description 下载导入模版 0212 院级绩效指标数据表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmhostargetdata/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "prm\\业务功能", "院级指标数据采集.xlsx");

		return null;
	}

	/**
	 * @Description 导入
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/importPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPrmHosTargetData(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String prmHosTargetDataJson = "";
		try {
			
			prmHosTargetDataJson = prmHosTargetDataService.importPrmHosTargetData(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	/**
	 * @Description CREATE
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/createPrmHosTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String prmHosTargetData = prmHosTargetDataService.createPrmHosTargetData(mapVo, paramVo);

		return JSONObject.parseObject(prmHosTargetData);

	}

}
