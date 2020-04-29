/**
* @Copyright: Copyright (c) 2015-3-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;

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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.entity.CostIncomeItemArrt;
import com.chd.hrp.cost.serviceImpl.CostDeptCostDataServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostDeptTypeDictServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostIncomeDetailServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostIncomeItemArrtServiceImpl;
/**
* @Title. @Description.
* 构成分析
* @Author: XueWanLi
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostCompositionAnalysisController extends BaseController{
	private static Logger logger = Logger.getLogger(CostCompositionAnalysisController.class);
	
	@Resource(name = "costDeptCostDataService")
	private final CostDeptCostDataServiceImpl costDeptCostDataService = null;
	
	
	@Resource(name = "costDeptTypeDictService")
	private final CostDeptTypeDictServiceImpl costDeptTypeDictService = null;
	
	@Resource(name = "costIncomeItemArrtService")
	private final CostIncomeItemArrtServiceImpl costIncomeItemArrtService = null;
	
	@Resource(name = "costIncomeDetailService")
	private final CostIncomeDetailServiceImpl costIncomeDetailService = null;
	
	/**
	*开单收入构成分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/billingRevenueStructureAnalysisMainPage", method = RequestMethod.GET)
	public String billingRevenueStructureAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costcompositionanalysis/billingRevenueStructureAnalysisMain";
	}
	
	/**
	*执行收入构成分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/executeRevenueStructureAnalysisMainPage", method = RequestMethod.GET)
	public String executeRevenueStructureAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costcompositionanalysis/executeRevenueStructureAnalysisMain";
	}
	
	
	/**
	*成本构成分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/costStructureAnalysisMainPage", method = RequestMethod.GET)
	public String costStructureAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costcompositionanalysis/costStructureAnalysisMain";
	}
	
	/**
	*构建成本构成动态表头<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/loadCostStructureTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadCostStructureTitle(@RequestParam Map<String, Object> mapVo, Model mode){
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptTypeDict = costDeptTypeDictService.queryCostDeptTypeDict(getPage(mapVo));
		return JSONObject.parseObject(costDeptTypeDict);
	}
	
	/**
	*构建收入构成动态表头<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/loadIncomeStructureTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadIncomeStructureTitle(@RequestParam Map<String, Object> mapVo, Model mode){
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptTypeDict = costIncomeItemArrtService.queryCostIncomeItemArrt(getPage(mapVo));
		return JSONObject.parseObject(costDeptTypeDict);
	}
	
	/**
	*成本构成分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/queryCostStructureAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostStructureAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("date_type") != null && !"".equals(mapVo.get("date_type"))){
				String dateType = mapVo.get("date_type").toString();
				 if(dateType.equals("0")){
					 mapVo.put("year_month1", mapVo.get("year_month1").toString()+"01");
					 mapVo.put("year_month2", mapVo.get("year_month2").toString()+"12");
				 }else{
					 mapVo.put("year_month1", SessionManager.getAcctYear()+mapVo.get("year_month1").toString());
					 mapVo.put("year_month2", SessionManager.getAcctYear()+mapVo.get("year_month2").toString());
				 }
			}
			
		String costBonusDetailMap = costDeptCostDataService.queryCostStructureAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	/**
	*开单收入构成分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/queryBillingRevenueStructureAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBillingRevenueStructureAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("date_type") != null && !"".equals(mapVo.get("date_type"))){
				String dateType = mapVo.get("date_type").toString();
				 if(dateType.equals("0")){
					 mapVo.put("year_month1", mapVo.get("year_month1").toString()+"01");
					 mapVo.put("year_month2", mapVo.get("year_month2").toString()+"12");
				 }else{
					 mapVo.put("year_month1", SessionManager.getAcctYear()+mapVo.get("year_month1").toString());
					 mapVo.put("year_month2", SessionManager.getAcctYear()+mapVo.get("year_month2").toString());
				 }
			}
			StringBuffer priceSb = new StringBuffer();
			StringBuffer str = new StringBuffer();
			List<CostIncomeItemArrt> list = costIncomeItemArrtService.queryCostIncomeItemArrtList(mapVo);
			for(int i = 0; i < list.size(); i ++){
				priceSb.append("sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) price_"+list.get(i).getIncome_item_id()+",");
				if(str.length() == 0){
					str.append(" sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) ");
				}else{
					str.append(" + sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) ");
				}
				
			}
			str.append("  price_total, ");
			mapVo.put("deptType", "0");
			mapVo.put("price_total", str.toString());
			mapVo.put("title", priceSb.toString());
		String costBonusDetailMap = "";
				//costIncomeDetailService.queryRevenueStructureAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	/**
	*执行收入构成分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costcompositionanalysis/queryExecuteRevenueStructureAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExecuteRevenueStructureAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(mapVo.get("date_type") != null && !"".equals(mapVo.get("date_type"))){
				String dateType = mapVo.get("date_type").toString();
				 if(dateType.equals("0")){
					 mapVo.put("year_month1", mapVo.get("year_month1").toString()+"01");
					 mapVo.put("year_month2", mapVo.get("year_month2").toString()+"12");
				 }else{
					 mapVo.put("year_month1", SessionManager.getAcctYear()+mapVo.get("year_month1").toString());
					 mapVo.put("year_month2", SessionManager.getAcctYear()+mapVo.get("year_month2").toString());
				 }
			}
			StringBuffer priceSb = new StringBuffer();
			StringBuffer str = new StringBuffer();
			List<CostIncomeItemArrt> list = costIncomeItemArrtService.queryCostIncomeItemArrtList(mapVo);
			for(int i = 0; i < list.size(); i ++){
				priceSb.append("sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) price_"+list.get(i).getIncome_item_id()+",");
				if(str.length() == 0){
					str.append(" sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) ");
				}else{
					str.append(" + sum(decode(ciav.INCOME_ITEM_ID,"+list.get(i).getIncome_item_id()+",ciav.AMOUNT,0)) ");
				}
				
			}
			str.append("  price_total, ");
			mapVo.put("deptType", "1");
			mapVo.put("price_total", str.toString());
			mapVo.put("title", priceSb.toString());
		String costBonusDetailMap = "";
		//costIncomeDetailService.queryRevenueStructureAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	
	
}
