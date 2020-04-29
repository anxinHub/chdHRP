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
import com.chd.hrp.budg.entity.BudgElseIncome;
import com.chd.hrp.budg.service.budgincome.elseincome.BudgElseIncomeService;
/**
 * 
 * @Description:
 * 其他收入预算
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseIncomeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseIncomeController.class);
	
	//引入Service服务
	@Resource(name = "budgElseIncomeService")
	private final BudgElseIncomeService budgElseIncomeService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeMainPage", method = RequestMethod.GET)
	public String budgElseIncomeMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/budgElseIncomeAddPage", method = RequestMethod.GET)
	public String budgElseIncomeAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeAdd";

	}
	
	/**
	 * @Description 
	 * 批量设置页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/budgElseIncomeUpdateBatchPage", method = RequestMethod.GET)
	public String budgElseIncomeUpdateBatchPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeUpdateBatch";

	}
	
	
	/**
	 * @Description 
	 * 生成数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/addBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgElseIncomeJson = budgElseIncomeService.addBudgElseIncome(mapVo);

		return JSONObject.parseObject(budgElseIncomeJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/budgElseIncomeUpdatePage", method = RequestMethod.GET)
	public String budgElseIncomeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgElseIncome = new HashMap<String,Object>();
    
		budgElseIncome = budgElseIncomeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgElseIncome.get("group_id"));
		mode.addAttribute("hos_id", budgElseIncome.get("hos_id"));
		mode.addAttribute("copy_code", budgElseIncome.get("copy_code"));
		mode.addAttribute("budg_year", budgElseIncome.get("budg_year"));
		mode.addAttribute("month", budgElseIncome.get("month"));
		mode.addAttribute("subj_code", budgElseIncome.get("subj_code"));
		mode.addAttribute("amount", budgElseIncome.get("amount"));
		mode.addAttribute("remark", budgElseIncome.get("remark"));
		
		return "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/updateBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgElseIncomeJson = budgElseIncomeService.update(mapVo);
		
		return JSONObject.parseObject(budgElseIncomeJson);
	}
	
	
	/**
	 * @Description 
	 * 批量设置  更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/updateBatchBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchBudgElseIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("last_income", ids[3]);
			
			if("-1".equals(ids[4])){
				
				mapVo.put("grow_rate", "");
				
			}else{
				
				mapVo.put("grow_rate", ids[4]);
				
				mapVo.put("budg_value", Double.parseDouble(ids[3])*(1+Double.parseDouble(ids[4])/100)) ;
			}
			
			if("-1".equals(ids[5])){
				
				mapVo.put("grow_value", "");
				
			}else{
				
				mapVo.put("grow_value", ids[5]);
				
				mapVo.put("budg_value", Double.parseDouble(ids[3])+ Double.parseDouble(ids[5])) ;
			}
			
			if("-1".equals(ids[6])){
				
				mapVo.put("remark", "");
				
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			
			listVo.add(mapVo);
	    }
		
		String budgElseIncomeJson = null ;
		
		try {
			
			budgElseIncomeJson = budgElseIncomeService.updateBatch(listVo);
			
		} catch (Exception e) {
				
			budgElseIncomeJson = e.getMessage();

		}
		
		return JSONObject.parseObject(budgElseIncomeJson);
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
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/addOrUpdateBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseIncomeJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgElseIncomeJson = budgElseIncomeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseIncomeJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/saveBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgElseIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("last_income", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("remark", ids[7]);
			mapVo.put("rowNo", ids[8]);
			mapVo.put("flag", ids[9]);//添加、修改标识
			
			listVo.add(mapVo);
	    }
		
		String budgElseIncomeJson = null ;
		try {
			
			budgElseIncomeJson = budgElseIncomeService.saveBudgElseIncome(listVo);
			
		} catch (Exception e) {
			
			budgElseIncomeJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgElseIncomeJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/deleteBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3]);
				mapVo.put("month", ids[4]);
				mapVo.put("subj_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgElseIncomeJson = budgElseIncomeService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgElseIncomeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/queryBudgElseIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if( mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
        
		
		String budgElseIncome = budgElseIncomeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseIncome);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/budgElseIncomeImportPage", method = RequestMethod.GET)
	public String budgElseIncomeImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/elseincome/compilation/budgElseIncomeImport";

	}
	/**
	 * @Description 
	 * 下载  其他收入预算导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/elseincome/compilation/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","其他收入预算字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  其他收入预算      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/elseincome/compilation/readBudgElseIncomeFiles",method = RequestMethod.POST)  
    public String readBudgElseIncomeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgElseIncome> list_err = new ArrayList<BudgElseIncome>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgElseIncome budgElseIncome = new BudgElseIncome();
				
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
						
						budgElseIncome.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgElseIncome.setMonth(temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgElseIncome.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgElseIncomeService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgElseIncome.setGrow_rate(Double.valueOf(temp[3]));
			    		mapVo.put("grow_rate", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgElseIncome.setRemark(temp[4]);
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgElseIncome.setError_type(err_sb.toString());
					
					list_err.add(budgElseIncome);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgElseIncomeService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgElseIncome data_exc = new BudgElseIncome();
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgElseIncomeService.queryBudgIncomeSubj(mapVo);
		return code;

	}
	
	/**
	 * 根据填写数据 查询 上年收入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/setLastIncome", method = RequestMethod.POST)
	@ResponseBody
	public String setLastIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		// 年度计算
		Integer budg_year = Integer.parseInt(String.valueOf(mapVo.get("budg_year"))) -1;
		
		mapVo.put("budg_year", budg_year) ;
		
		String code = budgElseIncomeService.setLastIncome(mapVo);
		return code;

	}
	
	/**
	 * 新导入功能
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/elseincome/compilation/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = budgElseIncomeService.importExcel(mapVo);
		return reJson;
	}
}

