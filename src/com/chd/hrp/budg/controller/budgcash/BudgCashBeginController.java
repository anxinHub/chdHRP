/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcash;

import java.util.*;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.budg.entity.BudgCashBegin;
import com.chd.hrp.budg.service.budgcash.BudgCashBeginService;

/**
 * 
 * @Description:
 * 期初货币资金
 * @Table:
 * BUDG_CASH_BEGIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCashBeginController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCashBeginController.class);
	
	//引入Service服务
	@Resource(name = "budgCashBeginService")
	private final BudgCashBeginService budgCashBeginService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/budgCashBeginMainPage", method = RequestMethod.GET)
	public String budgCashBeginMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	//查询预算系统 启用时间 条件  mod_code 0206
    	mapVo.put("mod_code", "0206");
    	
    	BudgCashBegin budgCashBegin = budgCashBeginService.queryBudgCashBeginByCode(mapVo);
    	String state_date = budgCashBeginService.queryStartDate(mapVo);
    	
    	//将拼装好的启用时间字符串 携带至页面回显
    	mode.addAttribute("start_date",state_date);
    	
    	if(budgCashBegin == null) {
    		mode.addAttribute("cash_begin_year","");
    		mode.addAttribute("cash_in", "");
    		mode.addAttribute("cash_out", "");
    		mode.addAttribute("cash_add", "");
    		mode.addAttribute("cash_begin", "");
    		mode.addAttribute("state", "0");
    	}else {
    		mode.addAttribute("cash_begin_year",budgCashBegin.getCash_begin_year());
    		mode.addAttribute("cash_in", budgCashBegin.getCash_in());
    		mode.addAttribute("cash_out", budgCashBegin.getCash_out());
    		mode.addAttribute("cash_add", budgCashBegin.getCash_add());
    		mode.addAttribute("cash_begin", budgCashBegin.getCash_begin());
    		mode.addAttribute("state", budgCashBegin.getState());
    	}
		
		return "hrp/budg/budgcash/budgcashbegin/budgCashBeginMain";

	}
	
	/**
	 * @Description 
	 * 查询数据 期初现金流量累计
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/queryBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashFlowBegin(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgCashFlowBegin = budgCashBeginService.queryBudgCashFlowBegin(mapVo);

		return JSONObject.parseObject(budgCashFlowBegin);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 期初货币资金
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/saveBudgCashBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgCashBegin(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashBeginJson = "";
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
	    	
			budgCashBeginJson = budgCashBeginService.addBudgCashBegin(mapVo);
			
		} catch (Exception e) {
			budgCashBeginJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashBeginJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 期初现金流量累计
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/addBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCashFlowBegin(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgCashFlowBeginJson = "";
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
	    try {
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("cash_item_id", ids[0]);
				mapVo.put("amount", ids[1]);
				mapVo.put("remark", ids[2]);
				
		      listVo.add(mapVo);
		    }
			budgCashFlowBeginJson = budgCashBeginService.addBudgCashFlowBegin(listVo);
		} catch (Exception e) {
			budgCashFlowBeginJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowBeginJson);
	}
	
	/**
	 * @Description 
	 * 添加或修改数据 期初现金流量累计
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/addOrUpdateBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCashFlowBegin(@RequestParam Map<String,Object> beginMap, Model mode) throws Exception {
		
		String budgCashFlowBeginJson = "";
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		try {
			
			for ( String id: beginMap.get("ParamVo").toString().split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("cash_item_id", ids[0]);
				mapVo.put("amount", ids[1]);
				if("-1".equals(ids[2])){
					mapVo.put("remark", "");
				}else{
					mapVo.put("remark", ids[2]);
				}
				listVo.add(mapVo);
			}
			
			//删除数据  同时更新期初货币资金
			Map<String, Object> initMap = JSONObject.parseObject(beginMap.get("changedData").toString());
			initMap.put("group_id", SessionManager.getGroupId());
			initMap.put("hos_id", SessionManager.getHosId());
			initMap.put("copy_code", SessionManager.getCopyCode());
			
			budgCashFlowBeginJson = budgCashBeginService.addOrUpdateBudgCashFlowBegin(listVo,initMap);
		} catch (Exception e) {
			budgCashFlowBeginJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowBeginJson);
	}
	
	/**
	 * @Description 
	 * 更新 期初现金流量累计 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/updateBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCashFlowBegin(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgCashFlowBeginJson = "";
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();			
				String[] ids=id.split("@");
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("cash_item_id", ids[0]);
				mapVo.put("amount", ids[1]);
				if("-1".equals(ids[2])){
					
					mapVo.put("remark", "");
					
				}else{
					
					mapVo.put("remark", ids[2]);
				}
				
		      listVo.add(mapVo);	      
		    }             
			budgCashFlowBeginJson = budgCashBeginService.updateBudgCashFlowBegin(listVo);
			
			
		} catch (Exception e) {
			budgCashFlowBeginJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowBeginJson);
    
	}
	
	/**
	 * @Description 
	 * 批量删除期初现金流量累计<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/deleteBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCashFlowBegin(@RequestParam Map<String,Object> beginMap, Model mode) throws Exception {
		String budgCashFlowBeginJson = "";
		try {
			double cash_in = 0;
			double cash_out = 0;
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String id: beginMap.get("ParamVo").toString().split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("cash_item_id", ids[3])   ;
				
				if("0".equals(ids[4])){
					cash_in += Double.parseDouble(ids[5].toString());
				}else{
					cash_out += Double.parseDouble(ids[5].toString());
				}
				
				listVo.add(mapVo);
			}
			
			//删除数据  同时更新期初货币资金
			Map<String, Object> initMap = JSONObject.parseObject(beginMap.get("changedData").toString());
			initMap.put("group_id", SessionManager.getGroupId());
			initMap.put("hos_id", SessionManager.getHosId());
			initMap.put("copy_code", SessionManager.getCopyCode());
			
			initMap.put("cash_in", Double.parseDouble(initMap.get("cash_in").toString())-cash_in);
			initMap.put("cash_out", Double.parseDouble(initMap.get("cash_out").toString())-cash_out);
			initMap.put("cash_add", Double.parseDouble(initMap.get("cash_in").toString())-Double.parseDouble(initMap.get("cash_out").toString()));
			initMap.put("cash_begin", Double.parseDouble(initMap.get("cash_begin_year").toString())+Double.parseDouble(initMap.get("cash_add").toString()));
			
			budgCashFlowBeginJson = budgCashBeginService.deleteBudgCashFlowBegin(listVo,initMap);
		} catch (Exception e) {
			budgCashFlowBeginJson = e.getMessage();
		}
			
		return JSONObject.parseObject(budgCashFlowBeginJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 期初货币资金
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/queryBudgCashBegin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashBegin(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgCashBegin = budgCashBeginService.queryBudgCashBegin(mapVo);

		return JSONObject.parseObject(budgCashBegin);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/queryCashItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgCashBegin = budgCashBeginService.queryCashItem(mapVo);
		
		return budgCashBegin;
		
	}
	
	/**
	 * @Description 
	 * 记账  更改数据状态
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/chargeBudgCashBeginState", method = RequestMethod.POST)
	@ResponseBody
	public String chargeBudgCashBeginState(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgCashBegin = budgCashBeginService.chargeBudgCashBeginState(mapVo);
		
		return budgCashBegin;
		
	}
	
	/**
	 * @Description 
	 * 反记账  更改数据状态
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/unChargeBudgCashBeginState", method = RequestMethod.POST)
	@ResponseBody
	public String unChargeBudgCashBeginState(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgCashBegin = budgCashBeginService.unChargeBudgCashBeginState(mapVo);
		
		return budgCashBegin;
		
	}
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/importBudgCashFlowBeginPage", method = RequestMethod.GET)
	public String budgHosBasicIndexImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashbegin/budgCashFlowBeginImport";

	}
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/importBudgCashFlowBegin", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgBasicIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgCashBeginService.importBudgCashFlowBegin(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	/**
	 * 查询数据状态 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashbegin/queryBudgCashBeginState", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCashBeginState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=budgCashBeginService.queryBudgCashBeginState(mapVo);
		
		return reJson;
	}
	
}

