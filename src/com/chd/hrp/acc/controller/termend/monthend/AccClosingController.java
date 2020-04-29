package com.chd.hrp.acc.controller.termend.monthend;

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
import com.chd.hrp.acc.service.termend.monthend.AccClosingService;

/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccClosingController extends BaseController {
	private static Logger logger = Logger.getLogger(AccClosingController.class);

	@Resource(name = "accClosingService")
	private final AccClosingService accClosingService = null;
	
	/**
	 * 结账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingPage", method = RequestMethod.GET)
	public String accClosingMainPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		mode.addAttribute("a01024", MyConfig.getSysPara("01024"));
		mode.addAttribute("a01025", MyConfig.getSysPara("01025"));
		return "hrp/acc/termend/monthend/closing/accClosingMain";
	}
	
	/**
	 * 结账<BR>
	 * 维护iframe页面跳转-开始结账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingStartPage", method = RequestMethod.GET)
	public String accClosingStartPage(Model mode) throws Exception {

		return "hrp/acc/termend/monthend/closing/accClosingStart";
	}
	
	/**
	 * 结账<BR>
	 * 维护iframe页面跳转-核对凭证
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingCheckVouchPage", method = RequestMethod.GET)
	public String accClosingCheckVouchPage(Model mode) throws Exception {

		return "hrp/acc/termend/monthend/closing/accClosingCheckVouch";
	}
	
	/**
	 * 结账<BR>
	 * 维护iframe页面跳转-核对账簿
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingCheckLederPage", method = RequestMethod.GET)
	public String accClosingCheckLederPage(Model mode) throws Exception {

		return "hrp/acc/termend/monthend/closing/accClosingCheckLeder";
	}
	
	/**
	 * 结账<BR>
	 * 维护iframe页面跳转-完成结账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingFinishPage", method = RequestMethod.GET)
	public String accClosingFinishPage(Model mode) throws Exception {

		return "hrp/acc/termend/monthend/closing/accClosingFinish";
	}

	/**
	 * 结账<BR>
	 * 维护金额链接跳转核算账明细页面
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingCheckDetailPage", method = RequestMethod.GET)
	public String accClosingCheckDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		return "hrp/acc/termend/monthend/closing/accClosingCheckDetail";
	}
	
	/**
	 * 结账<BR>
	 * 维护金额链接跳转凭证明细页面
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/accClosingVouchDetailPage", method = RequestMethod.GET)
	public String accClosingVouchDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		return "hrp/acc/termend/monthend/closing/accClosingVouchDetail";
	}
	

	/**
	 * 结账<BR>
	 * 期间查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosing(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		if(mapVo.get("acc_month") == null || "".equals(mapVo.get("acc_month"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		String accTemplateData = accClosingService.queryAccClosing(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 校验是否可以开始结账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosingCheckStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosingCheckStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		if(mapVo.get("acc_month") == null || "".equals(mapVo.get("acc_month"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		if (mapVo.get("check_wage") == null) {
			mapVo.put("check_wage", "0");
		}
		if (mapVo.get("check_cash") == null) {
			mapVo.put("check_cash", "0");
		}
		
		String accTemplateData = accClosingService.queryAccClosingCheckStart(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 核对总账与辅助账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosingCheckLederToCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosingCheckLederToCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		if(mapVo.get("acc_month") == null || "".equals(mapVo.get("acc_month"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		String accTemplateData = accClosingService.queryAccClosingCheckLederToCheck(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 核对总账与凭证
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosingCheckLederToVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosingCheckLederToVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		if(mapVo.get("acc_month") == null || "".equals(mapVo.get("acc_month"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		String accTemplateData = accClosingService.queryAccClosingCheckLederToVouch(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 未记账凭证查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosingCheckVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosingCheckVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))){
			return JSONObject.parseObject("\"error\":\"无效的期间\"");
		}
		String accTemplateData = accClosingService.queryAccClosingCheckVouch(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 当前会计期间
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/getAccClosingYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccClosingYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accTemplateData = accClosingService.getAccClosingYearMonth(mapVo);

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 获取辅助核算项
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/getAccClosingCheckNamesBySubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAccClosingCheckNamesBySubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accTemplateData = accClosingService.getAccClosingCheckNamesBySubj(mapVo);

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 结账<BR>
	 * 结账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/confirmAccClosing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAccClosing(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accJson;
		try {
			accJson = accClosingService.confirmAccClosing(mapVo);
		} catch (Exception e) {
			accJson = e.getMessage();
		}

		return JSONObject.parseObject(accJson);
	}

	/**
	 * 结账<BR>
	 * 反结账
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/reconfirmAccClosing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reconfirmAccClosing(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accJson;
		try {
			accJson = accClosingService.reconfirmAccClosing(mapVo);
		} catch (Exception e) {
			accJson = e.getMessage();
		}
		
		return JSONObject.parseObject(accJson);
	}

	/**
	 * 结账<BR>
	 * 本期结账涉及到的凭证数目
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/closing/queryAccClosingCountVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccClosingCountVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String countVouch = accClosingService.queryAccClosingCountVouch(mapVo);

		return JSONObject.parseObject(countVouch);
	}
}
