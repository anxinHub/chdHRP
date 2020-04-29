
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgdept;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDeptSet;
import com.chd.hrp.budg.entity.BudgYbPayModeSet;
import com.chd.hrp.budg.service.base.budgdept.BudgDeptSetService;
/**
 * 
 * @Description:
 * 预算科室定义
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class BudgDeptSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptSetController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptSetService")
	private final BudgDeptSetService budgDeptSetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdept/budgdeptset/budgDeptSetMainPage", method = RequestMethod.GET)
	public String budgPayModeMainPage(Model mode) throws Exception {
		return "hrp/budg/base/budgdept/budgdeptset/budgDeptSetMain";

	}


	/**
	 * @Description 
	 * 添加数据 预算科室定义
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdept/budgdeptset/updateBudgDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("dept_id", ids[2])   ;
			mapVo.put("type_code", ids[3]);
			mapVo.put("is_budg", ids[4]);
			mapVo.put("natur_code", ids[5]);
			mapVo.put("out_code", ids[6]);
			mapVo.put("emp_id", ids[7]);
			mapVo.put("is_manager", ids[8]);
			mapVo.put("is_stock", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("para_code", "");
			}else{
				mapVo.put("para_code", ids[10]);
			}
			
			listVo.add(mapVo); 
	    }
		String budgPayModeJson = null ;
		try {
			
			budgPayModeJson = budgDeptSetService.updateBatch(listVo);
			
		} catch (Exception e) {

			budgPayModeJson = e.getMessage();
		}
		 

		return JSONObject.parseObject(budgPayModeJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 预算科室定义
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdept/budgdeptset/queryBudgDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPayMode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		
		if(mapVo.get("budg_year") == null){	
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String BudgPayMode = budgDeptSetService.query(getPage(mapVo));
		return JSONObject.parseObject(BudgPayMode);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 预算科室定义
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdept/budgdeptset/budgDeptSetImportPage", method = RequestMethod.GET)
	public String budgPayModeImportPage(Model mode) throws Exception {
		return "hrp/budg/base/budgdept/budgdeptset/budgDeptSetImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 预算科室定义
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgdept/budgdeptset/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","预算科室定义模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 预算科室定义
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgdept/budgdeptset/readBudgDeptSetFiles",method = RequestMethod.POST)  
    public String readBudgDeptSetFiles(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDeptSet> list_err = new ArrayList<BudgDeptSet>();		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);	
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {				
				StringBuffer err_sb = new StringBuffer();				
				BudgDeptSet budgDeptSet = new BudgDeptSet();				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());	
				mapVo.put("copy_code", SessionManager.getCopyCode());						
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行的部门ID、部门类型编码重复;");
					}
						
				}
				
				if (StringTool.isNotBlank(temp[0])) {						
					budgDeptSet.setDept_id(temp[0]);			
					mapVo.put("dept_id", temp[0]);
					int count  = budgDeptSetService.queryDeptIdExist(mapVo);
					if(count == 0){
						err_sb.append("部门ID不存在或已停用;");
					}
				} else {						
					err_sb.append("部门ID为空;");						
				}
				
				if (StringTool.isNotBlank(temp[1])) {						
					budgDeptSet.setType_code(temp[1]);			
					mapVo.put("type_code", temp[1]);
					int count  = budgDeptSetService.queryTypeCodeExist(mapVo);
					if(count == 0){
						err_sb.append("部门类型编码不存在或已停用;");
					}
				} else {						
					err_sb.append("部门类型编码为空;");						
				}
				
				if (StringTool.isNotBlank(temp[2])) {						
					budgDeptSet.setIs_budg(Integer.valueOf(temp[2]));			
					mapVo.put("is_budg", temp[2]);
				} else {						
					err_sb.append("是否预算科室为空;");						
				}
				if (err_sb.toString().length() > 0) {					
					budgDeptSet.setError_type(err_sb.toString());					
					list_err.add(budgDeptSet);					
				} else {			  
						addList.add(mapVo);			
				}				
			}
			if(list_err.size() == 0){
				String dataJson = budgDeptSetService.updateBatch(addList);
			}
			
		} catch (DataAccessException e) {			
			BudgDeptSet data_exc = new BudgDeptSet();			
			data_exc.setError_type("导入系统出错");			
			list_err.add(data_exc);			
		}		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));		
		return null;		
    }
	
}

