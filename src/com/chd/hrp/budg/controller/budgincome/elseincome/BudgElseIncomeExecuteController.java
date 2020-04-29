/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.elseincome;
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
import com.chd.hrp.budg.entity.BudgElseIncomeExecute;
import com.chd.hrp.budg.service.budgincome.elseincome.BudgElseIncomeExecuteService;
/**
 * 
 * @Description:
 * 其他收入预算执行
 * @Table:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseIncomeExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseIncomeExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgElseIncomeExecuteService")
	private final BudgElseIncomeExecuteService budgElseIncomeExecuteService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteMainPage", method = RequestMethod.GET)
	public String budgElseIncomeExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteAddPage", method = RequestMethod.GET)
	public String budgElseIncomeExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/addBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseIncomeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgElseIncomeExecuteJson = budgElseIncomeExecuteService.add(mapVo);

		return JSONObject.parseObject(budgElseIncomeExecuteJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteUpdatePage", method = RequestMethod.GET)
	public String budgElseIncomeExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgElseIncomeExecute = new HashMap<String,Object>();
    
		budgElseIncomeExecute = budgElseIncomeExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgElseIncomeExecute.get("group_id"));
		mode.addAttribute("hos_id", budgElseIncomeExecute.get("hos_id"));
		mode.addAttribute("copy_code", budgElseIncomeExecute.get("copy_code"));
		mode.addAttribute("year", budgElseIncomeExecute.get("year"));
		mode.addAttribute("month", budgElseIncomeExecute.get("month"));
		mode.addAttribute("subj_code", budgElseIncomeExecute.get("subj_code"));
		mode.addAttribute("amount", budgElseIncomeExecute.get("amount"));
		mode.addAttribute("remark", budgElseIncomeExecute.get("remark"));
		
		return "hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/updateBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseIncomeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgElseIncomeExecuteJson = budgElseIncomeExecuteService.update(mapVo);
		
		return JSONObject.parseObject(budgElseIncomeExecuteJson);
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
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/addOrUpdateBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseIncomeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseIncomeExecuteJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgElseIncomeExecuteJson = budgElseIncomeExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseIncomeExecuteJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/saveBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgElseIncomeExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String budgElseIncomeExecuteJson = null ;
		try {
			
			budgElseIncomeExecuteJson = budgElseIncomeExecuteService.saveBudgElseIncomeExecute(listVo);
			
		} catch (Exception e) {
			
			budgElseIncomeExecuteJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgElseIncomeExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/deleteBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseIncomeExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgElseIncomeExecuteJson = budgElseIncomeExecuteService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgElseIncomeExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/queryBudgElseIncomeExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseIncomeExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if( mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
        
		
		String budgElseIncomeExecute = budgElseIncomeExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseIncomeExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteImportPage", method = RequestMethod.GET)
	public String budgElseIncomeExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/execute/budgElseIncomeExecuteImport";

	}
	/**
	 * @Description 
	 * 下载  其他收入预算执行导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/elseincome/execute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","其他收入预算执行字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  其他收入预算执行      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/elseincome/execute/readBudgElseIncomeExecuteFiles",method = RequestMethod.POST)  
    public String readBudgElseIncomeExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgElseIncomeExecute> list_err = new ArrayList<BudgElseIncomeExecute>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgElseIncomeExecute budgElseIncomeExecute = new BudgElseIncomeExecute();
				
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
						
						budgElseIncomeExecute.setYear(temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgElseIncomeExecute.setMonth(temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgElseIncomeExecute.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgElseIncomeExecuteService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgElseIncomeExecute.setAmount(Double.valueOf(temp[3]));
			    		mapVo.put("amount", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgElseIncomeExecute.setRemark(temp[4]);
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgElseIncomeExecute.setError_type(err_sb.toString());
					
					list_err.add(budgElseIncomeExecute);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgElseIncomeExecuteService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgElseIncomeExecute data_exc = new BudgElseIncomeExecute();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	
	/**
	 * 收入预算科目下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/execute/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgElseIncomeExecuteService.queryBudgIncomeSubj(mapVo);
		return code;

	}
}

