/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.empbonus;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgEmpAwardsTotal;
import com.chd.hrp.budg.entity.BudgEmpWageTotal;
import com.chd.hrp.budg.service.empbonus.BudgEmpAwardsTotalService;
/**
 * 
 * @Description:
 * 科室职工奖金总表 
 * @Table:
 * BUDG_EMP_AWARDS_TOTAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEmpAwardsTotalController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEmpAwardsTotalController.class);
	
	//引入Service服务
	@Resource(name = "budgEmpAwardsTotalService")
	private final BudgEmpAwardsTotalService budgEmpAwardsTotalService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalMainPage", method = RequestMethod.GET)
	public String budgEmpAwardsTotalMainPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalAddPage", method = RequestMethod.GET)
	public String budgEmpAwardsTotalAddPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/addBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEmpAwardsTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgEmpAwardsTotalJson = budgEmpAwardsTotalService.add(mapVo);

		return JSONObject.parseObject(budgEmpAwardsTotalJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalUpdatePage", method = RequestMethod.GET)
	public String budgEmpAwardsTotalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		Map<String,Object> budgEmpAwardsTotalMap= budgEmpAwardsTotalService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgEmpAwardsTotalMap.get("group_id"));
		mode.addAttribute("hos_id", budgEmpAwardsTotalMap.get("hos_id"));
		mode.addAttribute("copy_code", budgEmpAwardsTotalMap.get("copy_code"));
		mode.addAttribute("year", budgEmpAwardsTotalMap.get("year"));
		mode.addAttribute("month", budgEmpAwardsTotalMap.get("month"));
		mode.addAttribute("dept_id", budgEmpAwardsTotalMap.get("dept_id"));
		mode.addAttribute("dept_name", budgEmpAwardsTotalMap.get("dept_name"));
		mode.addAttribute("emp_id", budgEmpAwardsTotalMap.get("emp_id"));
		mode.addAttribute("emp_name", budgEmpAwardsTotalMap.get("emp_name"));
		mode.addAttribute("emp_type_code", budgEmpAwardsTotalMap.get("emp_type_code"));
		mode.addAttribute("type_name", budgEmpAwardsTotalMap.get("type_name"));
		mode.addAttribute("awards_item_code", budgEmpAwardsTotalMap.get("awards_item_code"));
		mode.addAttribute("awards_item_name", budgEmpAwardsTotalMap.get("awards_item_name"));
		mode.addAttribute("amount", budgEmpAwardsTotalMap.get("amount"));
		
		return "hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/updateBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEmpAwardsTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgEmpAwardsTotalJson = budgEmpAwardsTotalService.update(mapVo);
		
		return JSONObject.parseObject(budgEmpAwardsTotalJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/addOrUpdateBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgEmpAwardsTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgEmpAwardsTotalJson ="";
		

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
	  
		budgEmpAwardsTotalJson = budgEmpAwardsTotalService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgEmpAwardsTotalJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/deleteBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEmpAwardsTotal(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("emp_type_code", ids[7])   ;
				mapVo.put("awards_item_code", ids[8]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgEmpAwardsTotalJson = budgEmpAwardsTotalService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgEmpAwardsTotalJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/queryBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEmpAwardsTotal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgEmpAwardsTotal = budgEmpAwardsTotalService.query(getPage(mapVo));

		return JSONObject.parseObject(budgEmpAwardsTotal);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalImportPage", method = RequestMethod.GET)
	public String budgEmpAwardsTotalImportPage(Model mode) throws Exception {

		return "hrp/budg/empbonus/budgempawardstotal/budgEmpAwardsTotalImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/empbonus/budgempawardstotal/downTemplate", method = RequestMethod.GET)  
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
		
		//查询所有奖金项目
		List<Map<String, Object>> itemList = budgEmpAwardsTotalService.queryBudgAwardsItem(mapVo);
		
		for (int i = 0; i < itemList.size(); i++) {
			
			Map<String, Object> map = itemList.get(i);
			
			if(!"".equals(map.get("awards_item_name"))&& null != map.get("awards_item_name")){
				
				listdata.add(map.get("awards_item_name").toString());
				
			}
			
		}

		list.add(listdata);
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\budg\\cost\\awards\\";
		
		String downLoadPath = ctxPath + "科室职工奖金总表.xls";
		
		ExcelWriter.exportExcel(new File(downLoadPath), list);
		
		printTemplate(request, response, "budg\\cost\\awards","科室职工奖金总表.xls");

		return null; 
	 }
	    			
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/empbonus/budgempawardstotal/readBudgEmpAwardsTotalFiles",method = RequestMethod.POST)  
    public String readBudgEmpAwardsTotalFiles(Plupload plupload,HttpServletRequest request,
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
		List<Map<String,Object>> deptData = budgEmpAwardsTotalService.queryDeptData(map) ;
		
		//查询 职工基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> empData = budgEmpAwardsTotalService.queryEmpData(map) ;
		
		//查询所有职工类别信息
		List<Map<String, Object>> empTypeList = budgEmpAwardsTotalService.queryBudgEmpType(map);
		
		//查询所有奖金项目
		List<Map<String, Object>> itemList = budgEmpAwardsTotalService.queryBudgAwardsItem(map);
		
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
								
								errMap.put("dept_id",Long.valueOf(String.valueOf(item.get("dept_id"))));
								errMap.put("dept_code",item.get("dept_code"));
								errMap.put("dept_name",item.get("dept_name"));
					    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
					    		flag = 1;
					    		break ;
							}
							
						}
						
						if(flag == 0){
							errMap.put("dept_code",temp[2]);
							errMap.put("dept_name",temp[3]);
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
									
									errMap.put("emp_id",Long.valueOf(String.valueOf(item.get("emp_id"))));
									errMap.put("emp_code",temp[4]);
									errMap.put("emp_name",item.get("emp_name"));
						    		mapVo.put("emp_id", item.get("emp_id"));
						    		
						    		flag = 1  ;
						    		
						    		break ;
								}else{
									
									flag = 2 ;
								}
								
							}
							
						}
						
						if(flag == 0 ){
							errMap.put("emp_code",temp[4]);
							errMap.put("emp_name",temp[5]);
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
								
								errMap.put("emp_type_code",item.get("emp_type_code"));
								
								errMap.put("emp_type_name",temp[6]);
								
					    		mapVo.put("emp_type_code", item.get("emp_type_code"));
								
					    		flag = 1 ;
					    		break ;
							}
							
						}
						
						if(flag == 0 ){
							errMap.put("emp_type_name",temp[6]);
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
								
								if(list.get(0)[j+7].equals(item.get("awards_item_name"))){
									
									pageMap.put("awards_item_code",String.valueOf(item.get("awards_item_code")));
									pageMap.put("awards_item_name",String.valueOf(item.get("awards_item_name")));
									addMap.put("awards_item_code", String.valueOf(item.get("awards_item_code")));
						    		
						    		break ;
								}
							}
							
							pageMap.put("amount",Double.valueOf(temp[j+7]));
							
							addMap.put("amount", temp[j+7]);
							
						}
						
						//根据主键查询数据是否已存在
			    		int count  = budgEmpAwardsTotalService.queryDataExist(addMap);
						
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
					
					String dataJson = budgEmpAwardsTotalService.addBatch(addList);
					
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
	 * 批量添加数据  
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/empbonus/budgempawardstotal/addBatchBudgEmpAwardsTotal", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgEmpAwardsTotal(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgEmpAwardsTotal> list_err = new ArrayList<BudgEmpAwardsTotal>();
		
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
			
			BudgEmpAwardsTotal budgEmpAwardsTotal = new BudgEmpAwardsTotal();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgEmpAwardsTotal.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgEmpAwardsTotal.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgEmpAwardsTotal.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
						
					budgEmpAwardsTotal.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
		    		mapVo.put("emp_id", jsonObj.get("emp_id"));
		    		} else {
						
						err_sb.append("职工ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_type_code"))) {
						
					budgEmpAwardsTotal.setEmp_type_code((String)jsonObj.get("emp_type_code"));
		    		mapVo.put("emp_type_code", jsonObj.get("emp_type_code"));
		    		} else {
						
						err_sb.append("职称编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgEmpAwardsTotal.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgEmpAwardsTotal.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					
				BudgEmpAwardsTotal data_exc_extis = budgEmpAwardsTotalService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgEmpAwardsTotal.setError_type(err_sb.toString());
					
					list_err.add(budgEmpAwardsTotal);
					
				} else {
			  
					String dataJson = budgEmpAwardsTotalService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgEmpAwardsTotal data_exc = new BudgEmpAwardsTotal();
			
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

