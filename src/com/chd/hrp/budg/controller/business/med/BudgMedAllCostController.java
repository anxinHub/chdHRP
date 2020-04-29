/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.med;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.budg.entity.BudgMedCost;
import com.chd.hrp.budg.service.business.med.BudgMedCostService;
/**
 * 
 * @Description:
 * 医疗支出预算
 * @Table:
 * BUDG_MED_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedAllCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedAllCostController.class);
	
	//引入Service服务
	@Resource(name = "budgMedCostService")
	private final BudgMedCostService budgMedCostService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostMainPage", method = RequestMethod.GET)
	public String budgMedCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/med/all/budgMedCostMain";

	}
	
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedMonthCostMainPage", method = RequestMethod.GET)
	public String budgMedMonthCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/med/all/budgMedMonthCostMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostAddPage", method = RequestMethod.GET)
	public String budgMedCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/med/all/budgMedCostAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/addBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
       
		String budgMedCostJson = budgMedCostService.add(mapVo);

		return JSONObject.parseObject(budgMedCostJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostUpdatePage", method = RequestMethod.GET)
	public String budgMedCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		BudgMedCost budgMedCost = new BudgMedCost();
    
		budgMedCost = budgMedCostService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedCost.getGroup_id());
		mode.addAttribute("hos_id", budgMedCost.getHos_id());
		mode.addAttribute("copy_code", budgMedCost.getCopy_code());
		mode.addAttribute("budg_year", budgMedCost.getBudg_year());
		mode.addAttribute("month", budgMedCost.getMonth());
		mode.addAttribute("dept_id", budgMedCost.getDept_id());
		mode.addAttribute("subj_code", budgMedCost.getSubj_code());
		mode.addAttribute("cost_budg", budgMedCost.getCost_budg());
		mode.addAttribute("last_month_carried", budgMedCost.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgMedCost.getCarried_next_month());
		
		return "hrp/budg/business/med/all/budgMedCostUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/updateBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgMedCostJson = budgMedCostService.update(mapVo);
		
		return JSONObject.parseObject(budgMedCostJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/addOrUpdateBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedCostJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
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
	  
		budgMedCostJson = budgMedCostService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedCostJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/deleteBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("subj_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedCostJson = budgMedCostService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedCostJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/queryAllMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgMedCost = budgMedCostService.queryAllMedCost(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医疗支出预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostImportPage", method = RequestMethod.GET)
	public String budgMedCostImportPage(Model mode) throws Exception {

		return "hrp/budg/business/med/all/budgMedCostImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医疗支出预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/med/all/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\med","医疗支出预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医疗支出预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/med/all/readBudgMedCostFiles",method = RequestMethod.POST)  
    public String readBudgMedCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedCost> list_err = new ArrayList<BudgMedCost>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedCost budgMedCost = new BudgMedCost();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgMedCost.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgMedCost.setMonth(temp[4]);
		    		mapVo.put("month", temp[4]);
					
					} else {
						
						err_sb.append("月为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgMedCost.setDept_id(Long.valueOf(temp[5]));
		    		mapVo.put("dept_id", temp[5]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgMedCost.setSubj_code(temp[6]);
		    		mapVo.put("subj_code", temp[6]);
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgMedCost.setCost_budg(Double.valueOf(temp[7]));
		    		mapVo.put("cost_budg", temp[7]);
					
					} else {
						
						err_sb.append("支出预算为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgMedCost.setLast_month_carried(Double.valueOf(temp[8]));
		    		mapVo.put("last_month_carried", temp[8]);
					
					} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgMedCost.setCarried_next_month(Double.valueOf(temp[9]));
		    		mapVo.put("carried_next_month", temp[9]);
					
					} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					 
					
				BudgMedCost data_exc_extis = budgMedCostService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCost.setError_type(err_sb.toString());
					
					list_err.add(budgMedCost);
					
				} else {
			  
					String dataJson = budgMedCostService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedCost data_exc = new BudgMedCost();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医疗支出预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/med/all/addBatchBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMedCost(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedCost> list_err = new ArrayList<BudgMedCost>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
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
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgMedCost budgMedCost = new BudgMedCost();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgMedCost.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedCost.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedCost.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedCost.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cost_budg"))) {
						
					budgMedCost.setCost_budg(Double.valueOf((String)jsonObj.get("cost_budg")));
		    		mapVo.put("cost_budg", jsonObj.get("cost_budg"));
		    		} else {
						
						err_sb.append("支出预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgMedCost.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgMedCost.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgMedCost data_exc_extis = budgMedCostService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCost.setError_type(err_sb.toString());
					
					list_err.add(budgMedCost);
					
				} else {
			  
					String dataJson = budgMedCostService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedCost data_exc = new BudgMedCost();
			
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
	 * @Description 查询数据  状态
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>v
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/all/queryBudgCostMonthState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpenseApplyState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("tableName", "BUDG_MED_COST_HY");
		List<String> list= budgMedCostService.queryBudgMedCostState(mapVo);
		
		if(list.size() == 0){
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}else{
			String  str = "" ;
			for(String item : list){
				str += item +"," ;
			}
			return JSONObject.parseObject("{\"state\":\"false\"}");
		}
	}
    
	@RequestMapping(value = "/hrp/budg/business/med/all/deleteBudgMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedMonthCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				mapVo.put("budg_year", ids[0])   ;
				mapVo.put("dept_id", null)   ;
				mapVo.put("subj_code", ids[2]);
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				listVo.add(mapVo);
	      
	    }
	    
	 String budgMedCostJson = budgMedCostService.delMonth("BUDG_MED_COST_HY",listVo);
			
	  return JSONObject.parseObject(budgMedCostJson);
			
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/all/updateBudgMedCostMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedCostMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		mapVo.put("tableName", "BUDG_MED_COST_HY");
		String budgMedCostJson = budgMedCostService.updateMonth(mapVo);
		
		return JSONObject.parseObject(budgMedCostJson);
	}
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostMonthUpdatePage", method = RequestMethod.GET)
	public String budgMedCostMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		mapVo.put("tableName", "BUDG_MED_COST_HY");
		List<Map<String,Object>> list= budgMedCostService.queryDeptMedCostMonth(mapVo);
		if(list.size()==1){
			mode.addAttribute("budg_year", list.get(0).get("BUDG_YEAR"));
			mode.addAttribute("subj_code", list.get(0).get("SUBJ_CODE"));
			mode.addAttribute("base_value", list.get(0).get("BASE_VALUE"));
			mode.addAttribute("grow_rate", list.get(0).get("GROW_RATE"));
			mode.addAttribute("budg_value", list.get(0).get("BUDG_VALUE"));
			mode.addAttribute("remark", list.get(0).get("REMARK"));
		}
		return "hrp/budg/business/med/all/budgMedCostMonthUpdate";
	}	
	
	@RequestMapping(value = "/hrp/budg/business/med/all/addBudgMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedMonthCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       mapVo.put("state", "01");
       mapVo.put("tableName", "BUDG_MED_COST_HY");
		String budgMedCostJson = budgMedCostService.addMonth(mapVo);

		return JSONObject.parseObject(budgMedCostJson);
		
	}
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedMonthCostAddPage", method = RequestMethod.GET)
	public String budgMedMonthCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/med/all/budgMedMonthCostAdd";

	}
	@RequestMapping(value = "/hrp/budg/business/med/all/budgMedCostMonthAuditPage", method = RequestMethod.GET)
	public String budgMedCostMonthAuditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("type", mapVo.get("type"));
		return "hrp/budg/business/med/cost/budgMedCostMonthAudit";
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/all/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {			
		switch(mapVo.get("state").toString()){
			case "01":
				mapVo.put("checker","");
				mapVo.put("check_date","");
				break;
			case "02":
				if(mapVo.get("type").toString().equals("1")){
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				}else{
					mapVo.put("checker","");
					mapVo.put("check_date","");
				}
				break;
			case "03":
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				break;
				 
		}
		
		List<Map<String,Object>> listVo=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		String param=mapVo.get("paramVo").toString();
		String [] p1=param.split(",");
		String[] params=null;
		for(int m=0;m<p1.length;m++){
			params=p1[m].split("@");
			for(int i=0;i<params.length;i++){
				map=new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("budg_year", params[0]);
				map.put("dept_id", params[1]);
				map.put("subj_code", params[2]);
				map.put("state", mapVo.get("state"));
				map.put("checker", mapVo.get("checker"));
				map.put("check_date", mapVo.get("check_date"));
				map.put("op", mapVo.get("op"));
				
			}
			listVo.add(map);
		}
		String matOrderMain=null;
		if(mapVo.get("type").toString().equals("1"))
			matOrderMain = budgMedCostService.auditOrUnAudit("BUDG_MED_COST_HY",listVo);
		else
			matOrderMain=budgMedCostService.affiOrUnAffi("BUDG_MED_COST_HY",listVo);
		return JSONObject.parseObject(matOrderMain);
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/all/queryDeptMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptMedMonthCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		mapVo.put("tableName", "BUDG_MED_COST_HY");
		String budgMedCost = budgMedCostService.queryMonth(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	/**
	 * 科室汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/all/collectMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectMedMonthCost(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.collectMedMonthCost(mapVo) ;

		return JSONObject.parseObject(collect);
	}
	/**
	 * 科室汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/all/queryYearDeptDataExistNoCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearDeptDataExistNoCheck(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.queryYearDeptDataExistNoCheck(mapVo) ;
		
		return JSONObject.parseObject(collect);
	}
	/**
	 * 科室汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/all/queryYearHosDataExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearHosDataExist(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.queryYearHosDataExist(mapVo) ;

		return JSONObject.parseObject(collect);
	}
}

