/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.empwagecost;
import java.io.File;
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
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgEmpAwardsTotal;
import com.chd.hrp.budg.entity.BudgEmpWageTotal;
import com.chd.hrp.budg.service.budgcost.empwagecost.BudgEmpWageTotalService;
/**
 * 
 * @Description:
 * 科室职工工资总表
 * @Table:
 * BUDG_EMP_WAGE_TOTAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEmpWageTotalController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEmpWageTotalController.class);
	
	//引入Service服务
	@Resource(name = "budgEmpWageTotalService")
	private final BudgEmpWageTotalService budgEmpWageTotalService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/budgEmpWageTotalMainPage", method = RequestMethod.GET)
	public String budgEmpWageTotalMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empwagecost/budgempwagetotal/budgEmpWageTotalMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/budgEmpWageTotalAddPage", method = RequestMethod.GET)
	public String budgEmpWageTotalAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empwagecost/budgempwagetotal/budgEmpWageTotalAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/addBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEmpWageTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
    	mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgEmpWageTotalJson = budgEmpWageTotalService.add(mapVo);

		return JSONObject.parseObject(budgEmpWageTotalJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/budgEmpWageTotalUpdatePage", method = RequestMethod.GET)
	public String budgEmpWageTotalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String , Object> Map = budgEmpWageTotalService.queryDataByCode(mapVo);
		
		String yearMonth = Map.get("year").toString() + Map.get("month");
		
		mode.addAttribute("group_id", Map.get("year"));
		mode.addAttribute("hos_id", Map.get("year"));
		mode.addAttribute("copy_code", Map.get("year"));
		mode.addAttribute("yearMonth", yearMonth);
		mode.addAttribute("dept_id", Map.get("dept_id"));
		mode.addAttribute("dept_code", Map.get("dept_code"));
		mode.addAttribute("dept_name", Map.get("dept_name"));
		mode.addAttribute("emp_id", Map.get("emp_id"));
		mode.addAttribute("emp_name", Map.get("emp_name"));
		mode.addAttribute("emp_type_code", Map.get("emp_type_code"));
		mode.addAttribute("type_name", Map.get("type_name"));
		mode.addAttribute("wage_item_code", Map.get("wage_item_code"));
		mode.addAttribute("wage_item_name", Map.get("wage_item_name"));
		mode.addAttribute("amount", Map.get("amount"));
		
		return "hrp/budg/budgcost/empwagecost/budgempwagetotal/budgEmpWageTotalUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/updateBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEmpWageTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		String budgEmpWageTotalJson = budgEmpWageTotalService.update(mapVo);
		
		return JSONObject.parseObject(budgEmpWageTotalJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/addOrUpdateBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgEmpWageTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgEmpWageTotalJson ="";
		
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
	  
		budgEmpWageTotalJson = budgEmpWageTotalService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgEmpWageTotalJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/deleteBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEmpWageTotal(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("emp_id", ids[6])   ;
				//mapVo.put("wage_item_code", ids[7]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgEmpWageTotalJson = budgEmpWageTotalService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgEmpWageTotalJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室职工工资总表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/queryBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEmpWageTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgEmpWageTotal = budgEmpWageTotalService.query(getPage(mapVo));

		return JSONObject.parseObject(budgEmpWageTotal);
		
	}
	
	/**
	 * @Description 
	 * 查询工资项目
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/queryBudgWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String wageItem = budgEmpWageTotalService.queryBudgWageItem(mapVo);
		
		return JSONObject.parseObject(wageItem);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室职工工资总表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/budgEmpWageTotalImportPage", method = RequestMethod.GET)
	public String budgEmpWageTotalImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/empwagecost/budgempwagetotal/budgEmpWageTotalImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室职工工资总表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgcost/empwagecost/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 

			List<List> list = new ArrayList();
			
			List<String> listdata = new ArrayList<String>();
			
			listdata.add("年度");
			
			listdata.add("月份");
			
			listdata.add("科室编码");
			
			listdata.add("科室名称");
			
			listdata.add("职工编码");
			
			listdata.add("职工名称");
			
			listdata.add("职工类别名称");
			
			
			mapVo.put("group_id", SessionManager.getGroupId());
		       
			mapVo.put("hos_id", SessionManager.getHosId());
	     
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//查询所有工资项目
			List<Map<String, Object>> itemList = budgEmpWageTotalService.queryBudgWageItemList(mapVo);
			
			for (int i = 0; i < itemList.size(); i++) {
				
				Map<String, Object> map = itemList.get(i);
				
				if(!"".equals(map.get("wage_item_name"))&& null != map.get("wage_item_name")){
					
					listdata.add(map.get("wage_item_name").toString());
					
				}
				
			}

			list.add(listdata);
			
			String ctxPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\" + "excelTemplate\\"+"\\budg\\cost\\wage\\";
			
			String downLoadPath = ctxPath + "科室职工工资总表.xls";
			
			ExcelWriter.exportExcel(new File(downLoadPath), list);
			
			printTemplate(request, response, "budg\\cost\\wage","科室职工工资总表.xls");

			return null; 			
	    
	 }
	 /**
	 * @Description 
	 * 导入数据 科室职工工资总表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgcost/empwagecost/readBudgEmpWageTotalFiles",method = RequestMethod.POST)  
    public String readBudgEmpWageTotalFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("group_id", SessionManager.getGroupId());   
		 
		map.put("hos_id", SessionManager.getHosId());   
		 
		map.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> deptData = budgEmpWageTotalService.queryDeptData(map) ;
		
		//查询 职工基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> empData = budgEmpWageTotalService.queryEmpData(map) ;
		
		//查询所有职工类别信息
		List<Map<String, Object>> empTypeList = budgEmpWageTotalService.queryBudgEmpType(map);
		
		//查询所有工资项目
		List<Map<String, Object>> itemList = budgEmpWageTotalService.queryBudgWageItemList(map);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[4].equals(error[4])){
							err_sb.append("第"+i+"行数据与第 "+j+"行职工信息数据冲突;");
						}
							
					}
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						errMap.put("year",temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						if(Arrays.asList(monthData).contains(temp[1])){
							
							errMap.put("month",temp[1]);
							
				    		mapVo.put("month", temp[1]);
				    		
						}else{
							
							err_sb.append("月份输入不合法(必须两位有效数字01-12);");
						}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : deptData){
							
							if(temp[2].equals(item.get("dept_code"))){
								
								errMap.put("dept_id", String.valueOf(item.get("dept_id")));
								errMap.put("dept_code",temp[2]);
								errMap.put("dept_name", String.valueOf(item.get("dept_name")));
					    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
					    		flag = 1;
					    		
					    		break ;
							}
							
						}
						
						if(flag == 0){
							errMap.put("dept_code",temp[2]);
							errMap.put("dept_name", temp[3]);
							err_sb.append("科室编码输入错误,不存在该科室;");
						}
						
					
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						int flag = 0 ;
						
						for(Map<String,Object> item : empData ){
							
							if(temp[4].equals(item.get("emp_code"))){
								
								if(String.valueOf(item.get("dept_id")).equals(mapVo.get("dept_id"))){
									
									errMap.put("emp_id", item.get("emp_id"));
									errMap.put("emp_code", temp[4]);
									errMap.put("emp_name", item.get("emp_name"));
									mapVo.put("emp_id", item.get("emp_id"));
									
						    		flag = 1 ;
						    		break ;
						    		
								}else{
									
									flag = 2  ;
								}
								
							}
							
						}
						
						if(flag == 0 ){
							errMap.put("emp_code",temp[4]);
							errMap.put("emp_name", temp[5]);
							err_sb.append("职工编码不存在,输入错误;");
							
						}else if(flag == 2){
							
							err_sb.append("职工和科室对应关系错误;");
							
						}
						
					} else {
						
						err_sb.append("职工编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item :  empTypeList){
							
							if(temp[6].equals(item.get("emp_type_name"))){
								
								errMap.put("emp_type_code", item.get("emp_type_code"));
								
								errMap.put("emp_type_name", temp[6]);
								
					    		mapVo.put("emp_type_code",item.get("emp_type_code"));
								
					    		flag = 1 ;
					    		
					    		break ;
							}
							
						}
						
						if(flag == 0 ){
							errMap.put("emp_type_name", temp[6]);
							
							err_sb.append("职工类别名称不存在,输入错误;");
						}
					
					
					} else {
						
						err_sb.append("职工类别名称为空;");
						
					}
					 
					for(int j = 0; j < temp.length-7; j++){
						
						Map<String,Object> addMap = new HashMap<String,Object>();// 添加数据 用map
						
						Map<String,Object> pageMap = new HashMap<String,Object>();// 返回页面错误信心用 map
						
						addMap.putAll(mapVo);
						
						pageMap.putAll(errMap);
						
						if (ExcelReader.validExceLColunm(temp,j+7)) {
							
							for(Map<String,Object> item : itemList){
								
								if(list.get(0)[j+7].equals(item.get("wage_item_name"))){
									
									pageMap.put("wage_item_code", String.valueOf(item.get("wage_item_code")));
									
									pageMap.put("wage_item_name", item.get("wage_item_name"));
									
									addMap.put("wage_item_code", String.valueOf(item.get("wage_item_code")));
									break ;
								}
							}
							
							pageMap.put("amount", temp[j+7]);
							
							addMap.put("amount", temp[j+7]);
							
						}
						
						int count  = budgEmpWageTotalService.queryDataExist(addMap);
						
						StringBuffer errStr = new StringBuffer();
						
						if (count > 0 ) {
							
							errStr.append(String.valueOf(pageMap.get("err_sb"))=="null"?"":String.valueOf(pageMap.get("err_sb"))).append("数据已经存在！ ") ;
							
						}
							
						if (errStr.toString().length() > 0) {
							
							pageMap.put("error_type",errStr.toString());
							
							list_err.add(pageMap);
							
						}else{
							
							addList.add(addMap);
						}
						
					}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String dataJson = budgEmpWageTotalService.addBatch(addList);
					
				}else{
					Map<String,Object> data_exc = new HashMap<String,Object>();
					
					data_exc.put("error_type","没有数据,无法导入");
					
					list_err.add(data_exc);
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
	 * 批量添加数据 科室职工工资总表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/empwagecost/addBatchBudgEmpWageTotal", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgEmpWageTotal(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgEmpWageTotal> list_err = new ArrayList<BudgEmpWageTotal>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
			
			mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgEmpWageTotal budgEmpWageTotal = new BudgEmpWageTotal();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (StringTool.isNotBlank(jsonObj.get("year"))) {
				
			budgEmpWageTotal.setYear((String)jsonObj.get("year"));
    		mapVo.put("year", jsonObj.get("year"));
    		} else {
				
				err_sb.append("年度为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("month"))) {
				
			budgEmpWageTotal.setMonth((String)jsonObj.get("month"));
    		mapVo.put("month", jsonObj.get("month"));
    		} else {
				
				err_sb.append("月为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
				
			budgEmpWageTotal.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
    		mapVo.put("dept_id", jsonObj.get("dept_id"));
    		} else {
				
				err_sb.append("部门ID为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
				
			budgEmpWageTotal.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
    		mapVo.put("emp_id", jsonObj.get("emp_id"));
    		} else {
				
				err_sb.append("职工ID为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("emp_type_code"))) {
				
			budgEmpWageTotal.setEmp_type_code((String)jsonObj.get("emp_type_code"));
    		mapVo.put("emp_type_code", jsonObj.get("emp_type_code"));
    		} else {
				
				err_sb.append("职称编码为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("wage_item_code"))) {
				
			budgEmpWageTotal.setWage_item_code((String)jsonObj.get("wage_item_code"));
    		mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
    		} else {
				
				err_sb.append("工资项目编码为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("amount"))) {
				
			budgEmpWageTotal.setAmount(Double.valueOf((String)jsonObj.get("amount")));
    		mapVo.put("amount", jsonObj.get("amount"));
    		} else {
				
				err_sb.append("金额为空  ");
				
			}
			
			BudgEmpWageTotal data_exc_extis = budgEmpWageTotalService.queryByCode(mapVo);
			
			if (data_exc_extis != null) {
				
				err_sb.append("编码已经存在！ ");
				
			}
			if (err_sb.toString().length() > 0) {
				
				budgEmpWageTotal.setError_type(err_sb.toString());
				
				list_err.add(budgEmpWageTotal);
				
			} else {
		  
				String dataJson = budgEmpWageTotalService.add(mapVo);
				
			}
			
		}
			
		} catch (DataAccessException e) {
			
			BudgEmpWageTotal data_exc = new BudgEmpWageTotal();
			
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

