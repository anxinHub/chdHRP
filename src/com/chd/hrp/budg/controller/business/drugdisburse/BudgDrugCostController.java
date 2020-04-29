/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.drugdisburse;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDrugCost;
import com.chd.hrp.budg.service.budgcost.empwagecost.BudgEmpWageTotalService;
import com.chd.hrp.budg.service.business.drugdisburse.BudgDrugCostService;

/** 
 * @Description:
 * 科室药品支出
 * @Table:
 * BUDG_DRUG_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgDrugCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDrugCostController.class);
	
	//引入Service服务
	@Resource(name = "budgDrugCostService")
	private final BudgDrugCostService budgDrugCostService = null;
   
	//引入Service服务(导入用 )
	@Resource(name = "budgEmpWageTotalService")
	private final BudgEmpWageTotalService budgEmpWageTotalService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostMainPage", method = RequestMethod.GET)
	public String budgDrugCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostAddPage", method = RequestMethod.GET)
	public String budgDrugCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/addBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDrugCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
    	mapVo.put("copy_code", SessionManager.getCopyCode());
       
		String budgDrugCostJson = budgDrugCostService.add(mapVo);

		return JSONObject.parseObject(budgDrugCostJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostUpdatePage", method = RequestMethod.GET)
	public String budgDrugCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = budgDrugCostService.queryDataByCode(mapVo);
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("copy_code", map.get("copy_code"));
		mode.addAttribute("year", map.get("year"));
		mode.addAttribute("month", map.get("month"));
		mode.addAttribute("dept_id", map.get("dept_id"));
		mode.addAttribute("dept_name", map.get("dept_name"));
		mode.addAttribute("med_type_id", map.get("med_type_id"));
		mode.addAttribute("med_type_name", map.get("med_type_name"));
		mode.addAttribute("amount", map.get("amount"));
		        
		return "hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/updateBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDrugCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
	  
		String budgDrugCostJson = budgDrugCostService.update(mapVo);
		
		return JSONObject.parseObject(budgDrugCostJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/addOrUpdateBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDrugCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDrugCostJson ="";
		
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
	  
		budgDrugCostJson = budgDrugCostService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDrugCostJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/deleteBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDrugCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("med_type_id", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDrugCostJson = budgDrugCostService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDrugCostJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室药品支出
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/queryBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgDrugCost = budgDrugCostService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDrugCost);
		
	}
	
   /**
	 * @Description 
	 * 导入跳转页面 科室药品支出
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostImportPage", method = RequestMethod.GET)
	public String budgDrugCostImportPage(Model mode) throws Exception {

		return "hrp/budg/business/drugdisburse/budgdrugcost/budgDrugCostImport";

	}
	
	/**
	 * @Description 
	 * 下载导入模版 科室药品支出
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 */
	 @RequestMapping(value="hrp/budg/business/drugdisburse/budgdrugcost/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\cost\\med","科室药品支出.xls");
	    
	    return null; 
	 }
	/**
	 * @Description 
	 * 导入数据 科室药品支出
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value="/hrp/budg/business/drugdisburse/budgdrugcost/readBudgDrugCostFiles",method = RequestMethod.POST)  
    public String readBudgDrugCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"}; 
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> deptList =  budgEmpWageTotalService.queryDeptData(paraMap) ;
		
		//查询 药品分类基本信息(根据编码 匹配ID用)
		List<Map<String,Object>> typeList = budgDrugCostService.queryMedTypeData(paraMap) ;
		
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
					
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				 
				if (ExcelReader.validExceLColunm(temp, 0)) {
					
					map.put("year",temp[0]);
					
		    		mapVo.put("year", temp[0]);
				
				} else {
					
					err_sb.append("年度为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					if(!Arrays.asList(monthData).contains(temp[1])){
						
						err_sb.append("月份输入不合法（两位数字01-12）;");
						
					}else{
						
			    		mapVo.put("month", temp[1]);
						
					}
					map.put("month",temp[1]);
				
				} else {
					
					err_sb.append("月份为空 ;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					int flag = 0 ;
					
					for(Map<String,Object> item : deptList){
						
						if(temp[2].equals(item.get("dept_code"))){
							
							map.put("dept_id", String.valueOf(item.get("dept_id")));
							map.put("dept_code",temp[2]);
							map.put("dept_name", String.valueOf(item.get("dept_name")));
				    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						map.put("dept_code",temp[2]);
						map.put("dept_name", temp[3]);
						err_sb.append("科室编码输入错误,不存在该科室;");
					}
				
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,4)) {
					int flag = 0 ;
					
					for(Map<String,Object> item : typeList){
						
						if(temp[4].equals(item.get("med_type_code"))){
							
							map.put("med_type_id", String.valueOf(item.get("med_type_id")));
							map.put("med_type_code",temp[4]);
							map.put("med_type_name", String.valueOf(item.get("med_type_name")));
				    		mapVo.put("med_type_id", String.valueOf(item.get("med_type_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						
						map.put("med_type_code",temp[4]);
						map.put("med_type_name", temp[5]);
						
						err_sb.append("药品类别编码输入错误,不存在该药品类别;");
					}
				
				} else {
					
					err_sb.append("药品类别编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,6)) {
					
					map.put("amount",Double.valueOf(temp[6]));
		    		mapVo.put("amount", temp[6]);
				
				} else {
					
					err_sb.append("金额为空;");
					
				}
					 
				//校验数据 是否存在
				int count = budgDrugCostService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
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
					
					String dataJson = budgDrugCostService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String, Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;	 
    }  
    /**
	 * @Description 
	 * 批量添加数据 科室药品支出
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/drugdisburse/budgdrugcost/addBatchBudgDrugCost", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDrugCost(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDrugCost> list_err = new ArrayList<BudgDrugCost>();
		
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
			
			BudgDrugCost budgDrugCost = new BudgDrugCost();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDrugCost.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgDrugCost.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgDrugCost.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("med_type_id"))) {
						
					budgDrugCost.setMed_type_id((String)jsonObj.get("med_type_id"));
		    		mapVo.put("med_type_id", jsonObj.get("med_type_id"));
		    		} else {
						
						err_sb.append("药品分类编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgDrugCost.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					
				int count = budgDrugCostService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDrugCost.setError_type(err_sb.toString());
					
					list_err.add(budgDrugCost);
					
				} else {
			  
					String dataJson = budgDrugCostService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDrugCost data_exc = new BudgDrugCost();
			
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

