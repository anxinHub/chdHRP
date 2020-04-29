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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgCashFlow;
import com.chd.hrp.budg.service.budgcash.BudgCashFlowService;
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
public class BudgCashFlowController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCashFlowController.class);
	
	//引入Service服务
	@Resource(name = "budgCashFlowService")
	private final BudgCashFlowService budgCashFlowService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/budgCashFlowMainPage", method = RequestMethod.GET)
	public String budgCashFlowMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashflow/budgCashFlowMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/budgCashFlowAddPage", method = RequestMethod.GET)
	public String budgCashFlowAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashflow/budgCashFlowAdd";

	}

	/**
	 * @Description 
	 * 添加数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/addBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String budgCashFlowJson = budgCashFlowService.add(mapVo);

		return JSONObject.parseObject(budgCashFlowJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/budgCashFlowUpdatePage", method = RequestMethod.GET)
	public String budgCashFlowUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	
		BudgCashFlow budgCashFlow = new BudgCashFlow();
    
		budgCashFlow = budgCashFlowService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgCashFlow.getGroup_id());
		mode.addAttribute("hos_id", budgCashFlow.getHos_id());
		mode.addAttribute("copy_code", budgCashFlow.getCopy_code());
		mode.addAttribute("budg_year", budgCashFlow.getBudg_year());
		mode.addAttribute("cash_item_id", budgCashFlow.getcash_item_id());
		mode.addAttribute("month", budgCashFlow.getMonth());
		mode.addAttribute("amount", budgCashFlow.getAmount());
		
		return "hrp/budg/budgcash/budgcashflow/budgCashFlowUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/updateBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	  
		String budgCashFlowJson = budgCashFlowService.update(mapVo);
		
		return JSONObject.parseObject(budgCashFlowJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/addOrUpdateBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashFlowJson ="";
		

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgCashFlowJson = budgCashFlowService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgCashFlowJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/deleteBudgCashFlow", method = RequestMethod.POST)
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
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("cash_item_id", ids[4])   ;
				mapVo.put("month", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgCashFlowJson = budgCashFlowService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgCashFlowJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 现金流量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/queryBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgCashFlow = budgCashFlowService.query(getPage(mapVo));

		return JSONObject.parseObject(budgCashFlow);
		
	}
	
   /**
	 * @Description 
	 * 批量添加数据 现金流量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/addBatchBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgCashFlow(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgCashFlow> list_err = new ArrayList<BudgCashFlow>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgCashFlow budgCashFlow = new BudgCashFlow();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgCashFlow.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_item_id"))) {
						
					budgCashFlow.setcash_item_id((String)jsonObj.get("cash_item_id"));
		    		mapVo.put("cash_item_id", jsonObj.get("cash_item_id"));
		    		} else {
						
						err_sb.append("现金流量项目为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgCashFlow.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgCashFlow.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					
				BudgCashFlow data_exc_extis = budgCashFlowService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgCashFlow.setError_type(err_sb.toString());
					
					list_err.add(budgCashFlow);
					
				} else {
			  
					String dataJson = budgCashFlowService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgCashFlow data_exc = new BudgCashFlow();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	/**
	 * @Description 
	 * 1 后台查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总 之后  插入或更新入现金流量表   budg_cash_flow
	 * 2 对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashflow/collectBudgCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashFlowJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			budgCashFlowJson = budgCashFlowService.collectBudgCashFlow(mapVo);
			
		} catch (Exception e) {
			budgCashFlowJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgCashFlowJson);
	}
    
}

