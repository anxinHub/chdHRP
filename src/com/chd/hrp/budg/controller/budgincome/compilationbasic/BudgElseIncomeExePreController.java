/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.compilationbasic;
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
 * 其他收入历史执行数据
 * @Table:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgElseIncomeExePreController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgElseIncomeExePreController.class);
	
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
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreMainPage", method = RequestMethod.GET)
	public String budgElseIncomeExePreMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreAddPage", method = RequestMethod.GET)
	public String budgElseIncomeExePreAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/addBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgElseIncomeExePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgElseIncomeExePreJson = budgElseIncomeExecuteService.add(mapVo);

		return JSONObject.parseObject(budgElseIncomeExePreJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreUpdatePage", method = RequestMethod.GET)
	public String budgElseIncomeExePreUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgElseIncomeExePre = new HashMap<String,Object>();
    
		budgElseIncomeExePre = budgElseIncomeExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgElseIncomeExePre.get("group_id"));
		mode.addAttribute("hos_id", budgElseIncomeExePre.get("hos_id"));
		mode.addAttribute("copy_code", budgElseIncomeExePre.get("copy_code"));
		mode.addAttribute("year", budgElseIncomeExePre.get("year"));
		mode.addAttribute("month", budgElseIncomeExePre.get("month"));
		mode.addAttribute("subj_code", budgElseIncomeExePre.get("subj_code"));
		mode.addAttribute("amount", budgElseIncomeExePre.get("amount"));
		mode.addAttribute("remark", budgElseIncomeExePre.get("remark"));
		
		return "hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/updateBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgElseIncomeExePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgElseIncomeExePreJson = budgElseIncomeExecuteService.update(mapVo);
		
		return JSONObject.parseObject(budgElseIncomeExePreJson);
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
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/addOrUpdateBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgElseIncomeExePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgElseIncomeExePreJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgElseIncomeExePreJson = budgElseIncomeExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgElseIncomeExePreJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/saveBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgElseIncomeExePre(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String budgElseIncomeExePreJson = null ;
		try {
			
			budgElseIncomeExePreJson = budgElseIncomeExecuteService.saveBudgElseIncomeExecute(listVo);
			
		} catch (Exception e) {
			
			budgElseIncomeExePreJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgElseIncomeExePreJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/deleteBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgElseIncomeExePre(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgElseIncomeExePreJson = budgElseIncomeExecuteService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgElseIncomeExePreJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/queryBudgElseIncomeExePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgElseIncomeExePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if( mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
        
		
		String budgElseIncomeExePre = budgElseIncomeExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgElseIncomeExePre);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreImportPage", method = RequestMethod.GET)
	public String budgElseIncomeExePreImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/compilationbasic/elseincomeexe/budgElseIncomeExePreImport";

	}
	/**
	 * @Description 
	 * 下载  其他收入历史执行数据导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/compilationbasic/elseincomeexe/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income","其他收入历史执行数据模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  其他收入历史执行数据      
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/compilationbasic/elseincomeexe/readBudgElseIncomeExePreFiles",method = RequestMethod.POST)  
    public String readBudgElseIncomeExePreFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String, Object> errMap = new HashMap<String, Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])&& temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year",temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						errMap.put("month",temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						errMap.put("subj_code",temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		mapVo.put("is_last", 1);
			    		
			    		int count = budgElseIncomeExecuteService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在或已停用或不是末级科目;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
						errMap.put("subj_name",temp[3]);
			    		mapVo.put("subj_name", temp[4]);
					
					} else {
						
						err_sb.append("科目名称为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
						errMap.put("amount",temp[4]);
			    		mapVo.put("amount", temp[4]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						errMap.put("remark",temp[5]);
						mapVo.put("remark", temp[5]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				if(addList.size() > 0){
					String dataJson = budgElseIncomeExecuteService.addBatch(addList);
				}else{
					
					Map<String, Object> errMap = new HashMap<String, Object>();	
					
					errMap.put("error_type","没有导入数据,请检查导入文件是否有数据");
					
					list_err.add(errMap);
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String, Object> errMap = new HashMap<String, Object>();	
			
			errMap.put("error_type","导入系统出错");
			
			list_err.add(errMap);
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgElseIncomeExecuteService.queryBudgIncomeSubj(mapVo);
		return code;

	}
	
	/**
	 * @Description 
	 * 财务取数页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/getDatafromAccPage", method = RequestMethod.GET)
	public String getDatafromAccPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/budg/budgincome/compilationbasic/elseincomeexe/getDatafromAcc";
		
	}
	
	/**
	 * @Description 
	 * 财务取数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/elseincomeexe/getDatafromAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDatafromAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//String budgElseIncomeExePre = budgElseIncomeExecuteService.getDatafromAcc(mapVo);
		
		String budgElseIncomeExePre = budgElseIncomeExecuteService.getDatafromAcc2(mapVo);

		return JSONObject.parseObject(budgElseIncomeExePre);
		
	}
}

