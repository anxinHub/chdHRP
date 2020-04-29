package com.chd.hrp.hpm.controller;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.serviceImpl.AphiEmpSchemeSeqServiceImpl;
import com.chd.hrp.hpm.serviceImpl.AphiEmpSchemeServiceImpl;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpSchemeSeqController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiEmpSchemeSeqController.class);

	@Resource(name = "aphiEmpSchemeSeqService")
	private final AphiEmpSchemeSeqServiceImpl aphiEmpSchemeSeqService = null;

	@Resource(name = "aphiEmpSchemeService")
	private final AphiEmpSchemeServiceImpl aphiEmpSchemeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqMainPage", method = RequestMethod.GET)
	public String hpmEmpSchemeSeqMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqAddPage", method = RequestMethod.GET)
	public String hpmEmpSchemeSeqAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqAdd";

	}
	
	// 审核页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqAuditPage", method = RequestMethod.GET)
	public String hpmEmpSchemeSeqAuditPage(Model mode) throws Exception {
		
		
		return "hrp/hpm/hpmempschemeseq/hpmEmpSchemeSeqAudit";

	}

	//审核
	@RequestMapping(value = "/hrp/hpm/hpmempschemeseq/auditHpmEmpSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmEmpSchemeSeq(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		String empSchemeSeqJson = aphiEmpSchemeSeqService.addEmpSchemeSeq(mapVo);

		return JSONObject.parseObject(empSchemeSeqJson);

	} 

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmempschemeseq/queryHpmEmpSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpSchemeSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		mapVo.put("copy_code", COPY_CODE);
		
		
		if(MyConfig.getSysPara("09001") == null && "".equals(MyConfig.getSysPara("09001") )){
			mapVo.put("para_value", 0);
		} else {
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		

		String empSchemeSeq = aphiEmpSchemeService.queryEmpScheme(getPage(mapVo));

		return JSONObject.parseObject(empSchemeSeq);

	}

}
