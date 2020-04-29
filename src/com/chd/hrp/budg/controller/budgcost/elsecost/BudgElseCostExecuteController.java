/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.elsecost;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgElseCostExecute;
import com.chd.hrp.budg.service.budgcost.eslecost.BudgElseCostExecuteService;
/**
 * 
 * @Description:
 * 其他支出预算执行
 * @Table:
 * BUDG_ELSE_COST_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseCostExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseCostExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgElseCostExecuteService")
	private final BudgElseCostExecuteService budgElseCostExecuteService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteMainPage", method = RequestMethod.GET)
	public String budgElseCostExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteAddPage", method = RequestMethod.GET)
	public String budgElseCostExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/addBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgElseCostExecuteJson = budgElseCostExecuteService.add(mapVo);

		return JSONObject.parseObject(budgElseCostExecuteJson);
		
	}
	
	/**
	 * @Description 
	 * 其他支出执行数据 采集  (财务取数 )
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/collectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgElseCostExecuteJson = null ;
		
		try {
			
			budgElseCostExecuteJson = budgElseCostExecuteService.collectData(mapVo);
			
		} catch (Exception e) {
			
			budgElseCostExecuteJson = e.getMessage() ;
		}
       
		return JSONObject.parseObject(budgElseCostExecuteJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteUpdatePage", method = RequestMethod.GET)
	public String budgElseCostExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgElseCostExecute = new HashMap<String,Object>();
    
		budgElseCostExecute = budgElseCostExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgElseCostExecute.get("group_id"));
		mode.addAttribute("hos_id", budgElseCostExecute.get("hos_id"));
		mode.addAttribute("copy_code", budgElseCostExecute.get("copy_code"));
		mode.addAttribute("year", budgElseCostExecute.get("year"));
		mode.addAttribute("month", budgElseCostExecute.get("month"));
		mode.addAttribute("subj_code", budgElseCostExecute.get("subj_code"));
		mode.addAttribute("amount", budgElseCostExecute.get("amount"));
		mode.addAttribute("remark", budgElseCostExecute.get("remark"));
		
		return "hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/updateBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgElseCostExecuteJson = budgElseCostExecuteService.update(mapVo);
		
		return JSONObject.parseObject(budgElseCostExecuteJson);
	}
	
	/**
	 * ***************暂时不用此方法*******************
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/addOrUpdateBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseCostExecuteJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgElseCostExecuteJson = budgElseCostExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseCostExecuteJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/saveBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgElseCostExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());   
			
			mapVo.put("hos_id", SessionManager.getHosId());   
	
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("amount", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
	    }
		
		String budgElseCostExecuteJson = null ;
		try {
			
			budgElseCostExecuteJson = budgElseCostExecuteService.saveBudgElseCostExecute(listVo);
			
		} catch (Exception e) {
			
			budgElseCostExecuteJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgElseCostExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/deleteBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseCostExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3]);
				mapVo.put("month", ids[4]);
				mapVo.put("subj_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgElseCostExecuteJson = budgElseCostExecuteService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgElseCostExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/queryBudgElseCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if( mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
        
		
		String budgElseCostExecute = budgElseCostExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseCostExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteImportPage", method = RequestMethod.GET)
	public String budgElseCostExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/elsecost/execute/budgElseCostExecuteImport";

	}
	/**
	 * @Description 
	 * 下载  其他支出预算执行导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgcost/elsecost/execute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","其他支出预算执行字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  其他支出预算执行      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgcost/elsecost/execute/readBudgElseCostExecuteFiles",method = RequestMethod.POST)  
    public String readBudgElseCostExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgElseCostExecute> list_err = new ArrayList<BudgElseCostExecute>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgElseCostExecute budgElseCostExecute = new BudgElseCostExecute();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【预算科目名称】重复;");
						}	
					}
					if (StringTool.isNotBlank(temp[0])) {
						
						budgElseCostExecute.setYear(temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgElseCostExecute.setMonth(temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgElseCostExecute.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgElseCostExecuteService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgElseCostExecute.setAmount(Double.valueOf(temp[3]));
			    		mapVo.put("amount", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgElseCostExecute.setRemark(temp[4]);
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgElseCostExecute.setError_type(err_sb.toString());
					
					list_err.add(budgElseCostExecute);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgElseCostExecuteService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgElseCostExecute data_exc = new BudgElseCostExecute();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	
	/**
	 * 支出预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/elsecost/execute/queryBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgElseCostExecuteService.queryBudgCostSubj(mapVo);
		return code;

	}
}

