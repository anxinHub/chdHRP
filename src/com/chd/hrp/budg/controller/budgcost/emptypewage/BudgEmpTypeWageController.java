/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.emptypewage;
import java.io.IOException;
import java.util.*;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgEmpTypeWage;
import com.chd.hrp.budg.service.budgcost.emptypewage.BudgEmpTypeWageService;
/**
 * 
 * @Description:
 * 科室职工平均工资
 * @Table:
 * BUDG_EMP_TYPE_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEmpTypeWageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEmpTypeWageController.class);
	
	//引入Service服务
	@Resource(name = "budgEmpTypeWageService")
	private final BudgEmpTypeWageService budgEmpTypeWageService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/budgEmpTypeWageMainPage", method = RequestMethod.GET)
	public String budgEmpTypeWageMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/emptypewage/budgEmpTypeWageMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/budgEmpTypeWageAddPage", method = RequestMethod.GET)
	public String budgEmpTypeWageAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/emptypewage/budgEmpTypeWageAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/addBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEmpTypeWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgEmpTypeWageJson = budgEmpTypeWageService.add(mapVo);

		return JSONObject.parseObject(budgEmpTypeWageJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/budgEmpTypeWageUpdatePage", method = RequestMethod.GET)
	public String budgEmpTypeWageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String , Object> Map = budgEmpTypeWageService.queryDataByCode(mapVo);
		
		String yearMonth = Map.get("year").toString() + Map.get("month");
		
		mode.addAttribute("group_id", Map.get("year"));
		mode.addAttribute("hos_id", Map.get("year"));
		mode.addAttribute("copy_code", Map.get("year"));
		mode.addAttribute("yearMonth", yearMonth);
		mode.addAttribute("dept_id", Map.get("dept_id"));
		mode.addAttribute("dept_code", Map.get("dept_code"));
		mode.addAttribute("dept_name", Map.get("dept_name"));
		mode.addAttribute("emp_type_code", Map.get("emp_type_code"));
		mode.addAttribute("type_name", Map.get("type_name"));
		mode.addAttribute("wage_item_code", Map.get("wage_item_code"));
		mode.addAttribute("wage_item_name", Map.get("wage_item_name"));
		mode.addAttribute("amount", Map.get("amount"));
		
		return "hrp/budg/budgcost/emptypewage/budgEmpTypeWageUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/updateBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEmpTypeWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		String budgEmpTypeWageJson = budgEmpTypeWageService.update(mapVo);
		
		return JSONObject.parseObject(budgEmpTypeWageJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/addOrUpdateBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgEmpTypeWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgEmpTypeWageJson ="";
		
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
	  
		budgEmpTypeWageJson = budgEmpTypeWageService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgEmpTypeWageJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/deleteBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEmpTypeWage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgEmpTypeWageJson = budgEmpTypeWageService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgEmpTypeWageJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/queryBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEmpTypeWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgEmpTypeWage = budgEmpTypeWageService.query(getPage(mapVo));

		return JSONObject.parseObject(budgEmpTypeWage);
		
	}
	
	/**
	 * @Description 
	 * 查询工资项目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/queryBudgWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String wageItem = budgEmpTypeWageService.queryBudgWageItem(mapVo);
		
		return JSONObject.parseObject(wageItem);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室职工平均工资
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/budgEmpTypeWageImportPage", method = RequestMethod.GET)
	public String budgEmpTypeWageImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/emptypewage/budgEmpTypeWageImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室职工平均工资
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgcost/emptypewage/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","科室职工平均工资.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室职工平均工资
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgcost/emptypewage/readBudgEmpTypeWageFiles",method = RequestMethod.POST)  
    public String readBudgEmpTypeWageFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgEmpTypeWage> list_err = new ArrayList<BudgEmpTypeWage>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgEmpTypeWage budgEmpTypeWage = new BudgEmpTypeWage();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgEmpTypeWage.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgEmpTypeWage.setDept_id(Long.valueOf(temp[5]));
		    		mapVo.put("dept_id", temp[5]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgEmpTypeWage.setEmp_type_code(temp[7]);
		    		mapVo.put("emp_type_code", temp[7]);
					
					} else {
						
						err_sb.append("职称编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgEmpTypeWage.setWage_item_code(temp[8]);
		    		mapVo.put("wage_item_code", temp[8]);
					
					} else {
						
						err_sb.append("工资项目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgEmpTypeWage.setAmount(Double.valueOf(temp[9]));
		    		mapVo.put("amount", temp[9]);
					
					} else {
						
						err_sb.append("金额为空  ");
						
					}
					 
					
				BudgEmpTypeWage data_exc_extis = budgEmpTypeWageService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgEmpTypeWage.setError_type(err_sb.toString());
					
					list_err.add(budgEmpTypeWage);
					
				} else {
			  
					String dataJson = budgEmpTypeWageService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgEmpTypeWage data_exc = new BudgEmpTypeWage();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室职工平均工资
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/addBatchBudgEmpTypeWage", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgEmpTypeWage(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgEmpTypeWage> list_err = new ArrayList<BudgEmpTypeWage>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgEmpTypeWage budgEmpTypeWage = new BudgEmpTypeWage();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (StringTool.isNotBlank(jsonObj.get("year"))) {
				
			budgEmpTypeWage.setYear((String)jsonObj.get("year"));
    		mapVo.put("year", jsonObj.get("year"));
    		} else {
				
				err_sb.append("年度为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
				
			budgEmpTypeWage.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
    		mapVo.put("dept_id", jsonObj.get("dept_id"));
    		} else {
				
				err_sb.append("部门ID为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("emp_type_code"))) {
				
			budgEmpTypeWage.setEmp_type_code((String)jsonObj.get("emp_type_code"));
    		mapVo.put("emp_type_code", jsonObj.get("emp_type_code"));
    		} else {
				
				err_sb.append("职称编码为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("wage_item_code"))) {
				
			budgEmpTypeWage.setWage_item_code((String)jsonObj.get("wage_item_code"));
    		mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
    		} else {
				
				err_sb.append("工资项目编码为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("amount"))) {
				
			budgEmpTypeWage.setAmount(Double.valueOf((String)jsonObj.get("amount")));
    		mapVo.put("amount", jsonObj.get("amount"));
    		} else {
				
				err_sb.append("金额为空  ");
				
			}
			
			BudgEmpTypeWage data_exc_extis = budgEmpTypeWageService.queryByCode(mapVo);
			
			if (data_exc_extis != null) {
				
				err_sb.append("编码已经存在！ ");
				
			}
			if (err_sb.toString().length() > 0) {
				
				budgEmpTypeWage.setError_type(err_sb.toString());
				
				list_err.add(budgEmpTypeWage);
				
			} else {
		  
				String dataJson = budgEmpTypeWageService.add(mapVo);
				
			}
			
		}
			
		} catch (DataAccessException e) {
			
			BudgEmpTypeWage data_exc = new BudgEmpTypeWage();
			
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
	 * 计算数据 获取科室职工平均工资
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/emptypewage/collectBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgEmpWageTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		
		String budgEmpTypeWageJson = null ;
		
		try {
			
			budgEmpTypeWageJson = budgEmpTypeWageService.collectBudgEmpWageTotal(mapVo);
			
		} catch (Exception e) {
			
			budgEmpTypeWageJson = e.getMessage();
			
		}
		return JSONObject.parseObject(budgEmpTypeWageJson);
	}
	
}

