package com.chd.hrp.acc.controller.autovouch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.autovouch.AccFinancialAccountingComparisonService;

@Controller
public class AccFinancialAccountingComparisonController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccFinancialAccountingComparisonController.class);
	
	@Resource(name = "accFinancialAccountingComparisonService")
	private final AccFinancialAccountingComparisonService accFinancialAccountingComparisonService = null;

	/**
	* 主页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/financialAccountingComparisonMainPage", method = RequestMethod.GET)
	public String financialAccountingComparisonMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p050", MyConfig.getSysPara("050"));
		
		return "hrp/acc/autovouch/financialaccountingcomparison/financialAccountingComparisonMain";

	}
	
	/**
	* 设置页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/addFinancialAccountingComparisonPage", method = RequestMethod.GET)
	public String addFinancialAccountingComparisonPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("SUBJ_CODE_K",mapVo.get("SUBJ_CODE_K"));
		return "hrp/acc/autovouch/financialaccountingcomparison/financialAccountingComparisonAdd";

	}
	
	/**
	* 设置页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/addFinancialAccountingComparisonBySourcePage", method = RequestMethod.GET)
	public String addFinancialAccountingComparisonBySourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("acc_subj_code",mapVo.get("acc_subj_code"));
		mode.addAttribute("init_values",mapVo.get("init_values"));
		return "hrp/acc/autovouch/financialaccountingcomparison/financialAccountingComparisonAddBySource";

	}
	
	/**
	* 查询
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/queryFinancialAccountingComparison", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFinancialAccountingComparison(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSONObject.parseObject(accFinancialAccountingComparisonService.queryFinancialAccountingComparison(getPage(mapVo)));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 添加
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/addFinancialAccountingComparison", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFinancialAccountingComparison(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String by_source = "0";
		if(mapVo.get("by_source") != null && !"".equals(mapVo.get("by_source").toString())){
			by_source = mapVo.get("by_source").toString();
		}
		if("0".equals(by_source)){
			try {
				return JSONObject.parseObject(accFinancialAccountingComparisonService.addFinancialAccountingComparison(mapVo));
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
			}
		}else{
			Map<String, Object> retMap;
			
			try {
				retMap = accFinancialAccountingComparisonService.addFinancialAccountingComparisonBySource(mapVo);
			} catch (Exception e) {
				retMap = new HashMap<String, Object>();
				retMap.put("error", "操作失败！");
			}
			
			return retMap;
		}
	}
	
	/**
	* 删除预算会计科目
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/deleteFinancialAccountingComparison", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFinancialAccountingComparison(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSONObject.parseObject(accFinancialAccountingComparisonService.deleteFinancialAccountingComparison(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 查询财务会计科目
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/queryFinancialAccountingComparisonSubjC", method = RequestMethod.POST)
	@ResponseBody
	public String queryFinancialAccountingComparisonSubjC(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSON.toJSONString(accFinancialAccountingComparisonService.queryFinancialAccountingComparisonSubjC(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 查询预算会计科目
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/queryFinancialAccountingComparisonSubjK", method = RequestMethod.POST)
	@ResponseBody
	public String queryFinancialAccountingComparisonSubjK(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSON.toJSONString(accFinancialAccountingComparisonService.queryFinancialAccountingComparisonSubjK(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 查询预算会计科目
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/importFinancialAccountingComparison", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importFinancialAccountingComparison(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSON.parseObject(accFinancialAccountingComparisonService.importFinancialAccountingComparison(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 自动对照
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/updateSmartSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSmartSubj(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			if(Integer.parseInt(SessionManager.getAcctYear())<2019){
				return JSON.parseObject("{\"error\":\"只支持2019年以后开始对照\",\"state\":\"false\"}");
			}
			
			
			return JSON.parseObject(accFinancialAccountingComparisonService.updateSmartSubj(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	* 查询资金来源
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/financialaccountingcomparison/queryHosSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSource(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSON.toJSONString(accFinancialAccountingComparisonService.queryHosSource(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
}
