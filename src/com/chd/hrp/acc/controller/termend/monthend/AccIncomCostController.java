package com.chd.hrp.acc.controller.termend.monthend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccIncomCostService;

/** 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccIncomCostController extends BaseController {
	private static Logger logger = Logger.getLogger(AccIncomCostController.class);

	@Resource(name = "accIncomCostService")
	private final AccIncomCostService accIncomCostService = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
 
	/**
	 * 收支结转<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/accIncomCostPage", method = RequestMethod.GET)
	public String accIncomCostMainPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		mode.addAttribute("is_exec_budg", MyConfig.getSysPara("040"));
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		return "hrp/acc/termend/monthend/incomcost/accIncomCostMain";
	}
	
	/**
	 * 科目设置<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/accIncomCostSubjPage", method = RequestMethod.GET)
	public String accIncomCostSubjPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/acc/termend/monthend/incomcost/accIncomCostSubj";
	}
	
	/**
	 * 收支结转<BR>
	 * 模板查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/queryAccIncomCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccIncomCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z006");
		}
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 收支结转<BR>
	 * 模板保存
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/saveAccIncomCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccIncomCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z006");
		} 
		if (mapVo.get("template_type_name") == null) {
			mapVo.put("template_type_name", "收支结转");
		}
		String msg = "";
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			 
			msg = accTermendTemplateService.addAccTermendTemplate(mapVo);
		}else{ 
			msg = accTermendTemplateService.updateAccTermendTemplate(getPage(mapVo));
		}
		

		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 收支结转<BR>
	 * 模板删除
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/deleteAccIncomCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccIncomCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("template_id", id);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			listVo.add(mapVo);
		}
		String msg = accTermendTemplateService.deleteBatchAccTermendTemplate(listVo);

		return JSONObject.parseObject(msg);
	}

	/**
	 * 收支结转<BR>
	 * 凭证生成
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/addAccIncomCostVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccIncomCostVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		try{
			String msgStr = accIncomCostService.addAccIncomCostVouch(getPage(mapVo));
			return JSONObject.parseObject(msgStr);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
		
	}
	
	// 财务结余科目下拉框（企业）
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/querySubjAllFirm", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjAllFirm(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String subj = accIncomCostService.querySubjAllFirm(mapVo);
		return subj;
	}
	

}
