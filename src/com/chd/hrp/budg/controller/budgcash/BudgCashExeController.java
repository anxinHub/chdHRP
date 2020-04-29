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
import com.chd.hrp.budg.entity.BudgCash;
import com.chd.hrp.budg.service.budgcash.BudgCashExeService;
/**
 * 
 * @Description:
 * 现金存量预算
 * @Table:
 * BUDG_CASH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCashExeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCashExeController.class);
	
	//引入Service服务
	@Resource(name = "budgCashExeService")
	private final BudgCashExeService budgCashExeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						 
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/budgCashExeMainPage", method = RequestMethod.GET)
	public String budgCashMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashexe/budgCashExeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/budgCashAddExePage", method = RequestMethod.GET)
	public String budgCashAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcash/budgcashexe/budgCashExeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/addBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCash(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgCashExeJson = budgCashExeService.add(mapVo);

		return JSONObject.parseObject(budgCashExeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/budgCashExeUpdatePage", method = RequestMethod.GET)
	public String budgCashUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgCash budgCash = new BudgCash();
    
		budgCash = budgCashExeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgCash.getGroup_id());
		mode.addAttribute("hos_id", budgCash.getHos_id());
		mode.addAttribute("copy_code", budgCash.getCopy_code());
		mode.addAttribute("budg_year", budgCash.getBudg_year());
		mode.addAttribute("month", budgCash.getMonth());
		mode.addAttribute("cash_begain", budgCash.getCash_begain());
		mode.addAttribute("cash_in", budgCash.getCash_in());
		mode.addAttribute("cash_out", budgCash.getCash_out());
		mode.addAttribute("cash_add", budgCash.getCash_add());
		mode.addAttribute("cash_end", budgCash.getCash_end());
		
		return "hrp/budg/budgcash/budgcashexe/budgCashExeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/updateBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCash(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
	  
		String budgCashExeJson = budgCashExeService.update(mapVo);
		
		return JSONObject.parseObject(budgCashExeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/addOrUpdateBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgCash(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgCashExeJson ="";
		
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
	  
		budgCashExeJson = budgCashExeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgCashExeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/deleteBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCashExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
		    	mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("year", ids[0])   ;
				mapVo.put("month", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgCashExeJson = budgCashExeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgCashExeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 现金存量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/queryBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCash(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
		String budgCashExe = budgCashExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgCashExe);
		
	}
	
   /**
	 * @Description 
	 * 批量添加数据 现金存量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcashexe/addBatchBudgCashExe", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgCash(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgCash> list_err = new ArrayList<BudgCash>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgCash budgCash = new BudgCash();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgCash.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgCash.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_begain"))) {
						
					budgCash.setCash_begain(Double.valueOf((String)jsonObj.get("cash_begain")));
		    		mapVo.put("cash_begain", jsonObj.get("cash_begain"));
		    		} else {
						
						err_sb.append("期初现金存量为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_in"))) {
						
					budgCash.setCash_in(Double.valueOf((String)jsonObj.get("cash_in")));
		    		mapVo.put("cash_in", jsonObj.get("cash_in"));
		    		} else {
						
						err_sb.append("现金流入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_out"))) {
						
					budgCash.setCash_out(Double.valueOf((String)jsonObj.get("cash_out")));
		    		mapVo.put("cash_out", jsonObj.get("cash_out"));
		    		} else {
						
						err_sb.append("现金流出为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_add"))) {
						
					budgCash.setCash_add(Double.valueOf((String)jsonObj.get("cash_add")));
		    		mapVo.put("cash_add", jsonObj.get("cash_add"));
		    		} else {
						
						err_sb.append("现金净增加额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cash_end"))) {
						
					budgCash.setCash_end(Double.valueOf((String)jsonObj.get("cash_end")));
		    		mapVo.put("cash_end", jsonObj.get("cash_end"));
		    		} else {
						
						err_sb.append("期末现金存量为空  ");
						
					}
					
					
				BudgCash data_exc_extis = budgCashExeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgCash.setError_type(err_sb.toString());
					
					list_err.add(budgCash);
					
				} else {
			  
					String dataJson = budgCashExeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgCash data_exc = new BudgCash();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

