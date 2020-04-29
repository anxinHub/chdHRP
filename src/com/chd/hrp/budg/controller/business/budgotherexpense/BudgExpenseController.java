/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgotherexpense;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgExpense;
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseService;
/**
 * 
 * @Description:
 * 科室其他费用预算
 * @Table:
 * BUDG_EXPENSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgExpenseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgExpenseController.class);
	
	//引入Service服务
	@Resource(name = "budgExpenseService")
	private final BudgExpenseService budgExpenseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/budgExpenseMainPage", method = RequestMethod.GET)
	public String budgExpenseMainPage(Model mode) throws Exception {

		return "hrp/budg/business/budgotherexpense/budgexpense/budgExpenseMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/budgExpenseAddPage", method = RequestMethod.GET)
	public String budgExpenseAddPage(Model mode) throws Exception {

		return "hrp/budg/business/budgotherexpense/budgexpense/budgExpenseAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/addBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgExpense(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgExpenseJson = budgExpenseService.add(mapVo);

		return JSONObject.parseObject(budgExpenseJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/budgExpenseUpdatePage", method = RequestMethod.GET)
	public String budgExpenseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    
    	Map<String, Object> map = budgExpenseService.queryDataByCode(mapVo);
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("copy_code", map.get("copy_code"));
		mode.addAttribute("budg_year", map.get("budg_year"));
		mode.addAttribute("month", map.get("month"));
		mode.addAttribute("dept_id", map.get("dept_id"));
		mode.addAttribute("dept_name", map.get("dept_name"));
		mode.addAttribute("payment_item_id", map.get("payment_item_id"));
		mode.addAttribute("payment_item_name", map.get("payment_item_name"));
		mode.addAttribute("cost_budg", map.get("cost_budg"));
		mode.addAttribute("remark", map.get("remark"));
		
		return "hrp/budg/business/budgotherexpense/budgexpense/budgExpenseUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/updateBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgExpense(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    
		String budgExpenseJson = budgExpenseService.update(mapVo);
		
		return JSONObject.parseObject(budgExpenseJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/addOrUpdateBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgExpense(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgExpenseJson ="";
		
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
	  
		budgExpenseJson = budgExpenseService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgExpenseJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/deleteBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgExpense(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("payment_item_id", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgExpenseJson = budgExpenseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgExpenseJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室其他费用预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/queryBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpense(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    
		String budgExpense = budgExpenseService.query(getPage(mapVo));

		return JSONObject.parseObject(budgExpense);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室其他费用预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/budgExpenseImportPage", method = RequestMethod.GET)
	public String budgExpenseImportPage(Model mode) throws Exception {

		return "hrp/budg/business/budgotherexpense/budgexpense/budgExpenseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室其他费用预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/budgotherexpense/budgexpense/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\elseexpense","科室其他费用预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室其他费用预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/budgotherexpense/budgexpense/readBudgExpenseFiles",method = RequestMethod.POST)  
    public String readBudgExpenseFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthList = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		paraMap.put("hos_id", SessionManager.getHosId());   
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据code 匹配ID)
		List<Map<String,Object>> deptData = budgExpenseService.queryDeptData(paraMap);
		
		//查询 支出项目基本信息(根据code 匹配ID)
		List<Map<String,Object>> payItemData = budgExpenseService.queryPayItemData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
	    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		         
				 
	    		mapVo.put("hos_id", SessionManager.getHosId());   
	    		         
				 
	    		mapVo.put("copy_code", SessionManager.getCopyCode());   
	    		         
	    		for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])
							&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
	    		
				if (ExcelReader.validExceLColunm(temp,0)) {
					
					map.put("budg_year", temp[0]);
		    		mapVo.put("budg_year", temp[0]);
				
				} else {
					
					err_sb.append("预算年度为空  ");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					if(Arrays.asList(monthList).contains(temp[1])){
						mapVo.put("month", temp[1]);
					}else{
						err_sb.append("月份不符合规则(01-12);");
					}
					
					map.put("month",temp[1]);
				
				} else {
					
					err_sb.append("月份为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					int flag = 0 ;
					for(Map<String,Object> item: deptData ){
						
						if(temp[2].equals(item.get("dept_code"))){
							
							mapVo.put("dept_id", item.get("dept_id"));
							
							mapVo.put("dept_no", item.get("dept_no"));
							
							flag = 1 ;
							
							break ;
						}
					}
					
					if(flag == 0){
						err_sb.append("部门编码不存在或已停用,输入错误;");
					}
					
					map.put("dept_code",temp[2]);
					map.put("dept_name",temp[3]);
				
				} else {
					
					err_sb.append("部门编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,4)) {
					
					int flag = 0 ;
					
					for(Map<String,Object> item : payItemData){
						
						if(temp[4].equals(item.get("payment_item_code"))){
							
							mapVo.put("payment_item_id", item.get("payment_item_id"));
							
							flag = 1;
							
							break ;
						}
					}
					
					if(flag == 0 ){
						err_sb.append("支出项目编码不存在或已停用,输入错误;");
					}
					
					map.put("payment_item_code",temp[4]);
					
					map.put("payment_item_name",temp[5]);
				
				} else {
					
					err_sb.append("支出项目编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,6)) {
					
					map.put("cost_budg",Double.valueOf(temp[6]));
		    		mapVo.put("cost_budg", temp[6]);
					
				} else {
					
					err_sb.append("支出预算为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,7)) {
					
					map.put("remark",temp[7]);
		    		mapVo.put("remark", temp[7]);
					
				} else {
					
					map.put("remark","");
		    		mapVo.put("remark", "");
					
				}
				
				int count = budgExpenseService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在!");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String json = budgExpenseService.addBatch(addList);
					
				}else{
					
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.put("error_type", "没有数据，无法导入.");
					
					list_err.add(map);
					
				}
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室其他费用预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/addBatchBudgExpense", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgExpense(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgExpense> list_err = new ArrayList<BudgExpense>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgExpense budgExpense = new BudgExpense();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgExpense.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgExpense.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgExpense.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payment_item_id"))) {
						
					budgExpense.setPayment_item_id(Long.valueOf((String)jsonObj.get("payment_item_id")));
		    		mapVo.put("payment_item_id", jsonObj.get("payment_item_id"));
		    		} else {
						
						err_sb.append("支出项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cost_budg"))) {
						
					budgExpense.setCost_budg(Double.valueOf((String)jsonObj.get("cost_budg")));
		    		mapVo.put("cost_budg", jsonObj.get("cost_budg"));
		    		} else {
						
						err_sb.append("支出预算为空  ");
						
					}
					
					
				BudgExpense data_exc_extis = budgExpenseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgExpense.setError_type(err_sb.toString());
					
					list_err.add(budgExpense);
					
				} else {
			  
					String dataJson = budgExpenseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgExpense data_exc = new BudgExpense();
			
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
	 * 生成  根据年度生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgotherexpense/budgexpense/generateBudgExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBugExpendse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String bugExpendseJson = budgExpenseService.generateBudgExpense(mapVo);
		
		return JSONObject.parseObject(bugExpendseJson);
		
	}
	
}

