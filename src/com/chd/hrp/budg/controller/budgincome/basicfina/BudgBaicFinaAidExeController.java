/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.basicfina;
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
import com.chd.hrp.budg.entity.BudgBasicFinaAidExe;
import com.chd.hrp.budg.service.budgincome.basicfina.BudgBasicFinaAidExeService;
/**
 * 
 * @Description:
 * 财政基本补助收入预算执行
 * @Table:
 * BUDG_BASIC_FINA_AID_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgBaicFinaAidExeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgBaicFinaAidExeController.class);
	
	//引入Service服务
	@Resource(name = "budgBasicFinaAidExeService")
	private final BudgBasicFinaAidExeService budgBasicFinaAidExeService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeMainPage", method = RequestMethod.GET)
	public String budgBasicFinaAidExeMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeAddPage", method = RequestMethod.GET)
	public String budgBasicFinaAidExeAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/addBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBasicFinaAidExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgBasicFinaAidExeJson = budgBasicFinaAidExeService.add(mapVo);

		return JSONObject.parseObject(budgBasicFinaAidExeJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeUpdatePage", method = RequestMethod.GET)
	public String budgBasicFinaAidExeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		Map<String,Object> budgBasicFinaAidExe = new HashMap<String,Object>();
    
		budgBasicFinaAidExe = budgBasicFinaAidExeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgBasicFinaAidExe.get("group_id"));
		mode.addAttribute("hos_id", budgBasicFinaAidExe.get("hos_id"));
		mode.addAttribute("copy_code", budgBasicFinaAidExe.get("copy_code"));
		mode.addAttribute("budg_year", budgBasicFinaAidExe.get("budg_year"));
		mode.addAttribute("month", budgBasicFinaAidExe.get("month"));
		mode.addAttribute("subj_code", budgBasicFinaAidExe.get("subj_code"));
		mode.addAttribute("exe_value", budgBasicFinaAidExe.get("exe_value"));
		
		return "hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/updateBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgBasicFinaAidExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		String budgBasicFinaAidExeJson = budgBasicFinaAidExeService.update(mapVo);
		
		return JSONObject.parseObject(budgBasicFinaAidExeJson);
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
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/addOrUpdateBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgBasicFinaAidExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgBasicFinaAidExeJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
	
			detailVo.put("hos_id", SessionManager.getHosId());   
	
			detailVo.put("copy_code", SessionManager.getCopyCode());   
		  
	  
			budgBasicFinaAidExeJson = budgBasicFinaAidExeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgBasicFinaAidExeJson);
	}
	
	/**
	 * @Description 
	 * 保存数据  （包含 添加、修改 ）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/saveBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgBasicFinaAidExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
				
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());   
			
			mapVo.put("hos_id", SessionManager.getHosId());   
	
			mapVo.put("copy_code", SessionManager.getCopyCode());  
			mapVo.put("budg_year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("subj_code", ids[2]);
			mapVo.put("exe_value", ids[3]);
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
	    }
		
		String budgBasicFinaAidExeJson = null ;
		try {
			
			budgBasicFinaAidExeJson = budgBasicFinaAidExeService.saveBudgBasicFinaAidExe(listVo);
			
		} catch (Exception e) {
			
			budgBasicFinaAidExeJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgBasicFinaAidExeJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/deleteBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBasicFinaAidExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgBasicFinaAidExeJson = budgBasicFinaAidExeService.deleteBatch(listVo);
			
		return JSONObject.parseObject(budgBasicFinaAidExeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/queryBudgBasicFinaAidExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBasicFinaAidExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgBasicFinaAidExe = budgBasicFinaAidExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgBasicFinaAidExe);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeImportPage", method = RequestMethod.GET)
	public String budgBasicFinaAidExeImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/basicfina/exe/budgBasicFinaAidExeImport";

	}
	/**
	 * @Description 
	 * 下载  财政基本补助收入预算执行导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/basicfina/exe/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","财政基本补助收入预算执行字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  财政基本补助收入预算执行 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/basicfina/exe/readBudgBasicFinaAidExeFiles",method = RequestMethod.POST)  
    public String readBudgBasicFinaAidExeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgBasicFinaAidExe> list_err = new ArrayList<BudgBasicFinaAidExe>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgBasicFinaAidExe budgBasicFinaAidExe = new BudgBasicFinaAidExe();
				
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
						
						budgBasicFinaAidExe.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgBasicFinaAidExe.setMonth(temp[1]);
			    		mapVo.put("month", temp[1]);
			    		
			    		
			    		if(!Arrays.asList(monthData).contains(temp[1]) ){
			    			
			    			err_sb.append("月份输入不合法（必须两位数字）");
			    		}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
						budgBasicFinaAidExe.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		int count = budgBasicFinaAidExeService.querySubjCodeExist(mapVo);
			    		if(count == 0){
			    			err_sb.append("预算科目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("预算科目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgBasicFinaAidExe.setExe_value(Double.valueOf(temp[3]));
			    		mapVo.put("exe_value", temp[3]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
				if (err_sb.toString().length() > 0) {
					
					budgBasicFinaAidExe.setError_type(err_sb.toString());
					
					list_err.add(budgBasicFinaAidExe);
					
				} else {
					
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgBasicFinaAidExeService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicFinaAidExe data_exc = new BudgBasicFinaAidExe();
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/basicfina/exe/queryBudgIncomeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIncomeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String code = budgBasicFinaAidExeService.queryBudgIncomeSubj(mapVo);
		return code;

	}
}

