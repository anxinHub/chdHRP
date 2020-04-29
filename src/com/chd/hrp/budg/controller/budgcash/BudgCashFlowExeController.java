/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcash;
import java.io.IOException;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.entity.BudgCashFlow;
import com.chd.hrp.budg.service.budgcash.BudgCashFlowExeService;
/**
 * 
 * @Description:
 * 现金流量预算
 * @Table:
 * BUDG_CASH_FLOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCashFlowExeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCashFlowExeController.class);
	
	//引入Service服务
	@Resource(name = "budgCashFlowExeService")
	private final BudgCashFlowExeService budgCashFlowExeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeMainPage", method = RequestMethod.GET)
	public String budgCashFlowMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeAddPage", method = RequestMethod.GET)
	public String budgCashFlowAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/addBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String budgCashFlowExeJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			budgCashFlowExeJson = budgCashFlowExeService.add(mapVo);
			
			
		} catch (Exception e) {
			budgCashFlowExeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowExeJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeUpdatePage", method = RequestMethod.GET)
	public String budgCashFlowUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	
		BudgCashFlow budgCashFlow = new BudgCashFlow();
    
		budgCashFlow = budgCashFlowExeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgCashFlow.getGroup_id());
		mode.addAttribute("hos_id", budgCashFlow.getHos_id());
		mode.addAttribute("copy_code", budgCashFlow.getCopy_code());
		mode.addAttribute("year", budgCashFlow.getYear());
		mode.addAttribute("cash_item_id", budgCashFlow.getcash_item_id());
		mode.addAttribute("month", budgCashFlow.getMonth());
		mode.addAttribute("amount", budgCashFlow.getAmount());
		
		return "hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/updateBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCashFlowExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3])   ;
			mapVo.put("month", ids[4]);
			mapVo.put("cash_item_id", ids[5])   ;
			
	      listVo.add(mapVo);
	    }
	    
		String budgCashFlowExeJson = budgCashFlowExeService.updateBatch(listVo);
			
	   return JSONObject.parseObject(budgCashFlowExeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/addOrUpdateBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashFlowExeJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		budgCashFlowExeJson = budgCashFlowExeService.addOrUpdate(mapVo);

		return JSONObject.parseObject(budgCashFlowExeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/deleteBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCashFlow(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("month", ids[4]);
				mapVo.put("cash_item_id", ids[5])   ;
				
	      listVo.add(mapVo);
	    }
	    
		String budgCashFlowExeJson = budgCashFlowExeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgCashFlowExeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/queryBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgCashFlowExe = budgCashFlowExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgCashFlowExe);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 现金流量执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/importBudgCashFlowExePage", method = RequestMethod.GET)
	public String budgCashFlowImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashflowexe/budgCashFlowExeImport";

	}
	
	/**
	 * @Description 
	 * 对现金流量数据执行表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量执行表  budg_cash
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/collectBudgCashFlowExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgCashFlowExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashFlowExeJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			budgCashFlowExeJson = budgCashFlowExeService.collectBudgCashFlowExe(mapVo);
			
		} catch (Exception e) {
			budgCashFlowExeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowExeJson);
	}
    
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/queryBudgYear", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String budgYear = budgCashFlowExeService.queryBudgYear(mapVo);
		return budgYear;

	}
	
	
	/**
	 * 预算月份  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflowexe/queryBudgMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String month = budgCashFlowExeService.queryBudgMonth(mapVo);
		
		return month;
		
	}
	
}

