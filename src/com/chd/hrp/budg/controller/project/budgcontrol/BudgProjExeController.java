/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.budgcontrol;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgChargeStandardDict;
import com.chd.hrp.budg.entity.BudgProjExe;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStanDeptSetService;
import com.chd.hrp.budg.service.base.budgcharge.BudgChargeStandardDictService;
import com.chd.hrp.budg.service.project.budgcontrol.BudgProjExeService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_PROJ_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjExeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjExeController.class);
	
	//引入Service服务
	@Resource(name = "budgProjExeService")
	private final BudgProjExeService budgProjExeService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/budgProjExeMainPage", method = RequestMethod.GET)
	public String BudgProjExeMainPage(Model mode) throws Exception {

		return "hrp/budg/project/execute/budgProjExeMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/BudgProjExeAddPage", method = RequestMethod.GET)
	public String BudgProjExeAddPage(Model mode) throws Exception {

		return "hrp/budg/project/execute/budgProjExeAdd";

	}
	
	
	/**
	 * @Description 
	 * 添加数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/addBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
       
		String budgProjExeJson = budgProjExeService.add(mapVo);

		return JSONObject.parseObject(budgProjExeJson);
		
	}
	
	
	/**
	 * @Description 
	 * 更新跳转页面  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/budgProjExeUpdatePage", method = RequestMethod.GET)
	public String BudgProjExeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());

		
		Map<String,Object> budgProjExe = new HashMap<String,Object>();
    
		budgProjExe = budgProjExeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgProjExe.get("group_id"));
		mode.addAttribute("hos_id", budgProjExe.get("hos_id"));
		mode.addAttribute("copy_code", budgProjExe.get("copy_code"));
		mode.addAttribute("year", budgProjExe.get("year"));
		mode.addAttribute("month", budgProjExe.get("month"));
		mode.addAttribute("proj_id", budgProjExe.get("proj_id"));
		mode.addAttribute("source_id", budgProjExe.get("source_id"));
		mode.addAttribute("payment_item_id", budgProjExe.get("payment_item_id"));
		mode.addAttribute("cost_amount", budgProjExe.get("cost_amount"));
		
		return "hrp/budg/project/execute/budgProjExeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/updateBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("proj_id", ids[2])   ;
			mapVo.put("source_id", ids[3]);
			mapVo.put("payment_item_id", ids[4]);
			mapVo.put("cost_amount", ids[5]);
			
	      listVo.add(mapVo);
	      
	    }
		  
		String BudgProjExeJson = budgProjExeService.updateBatch(listVo);
		
		return JSONObject.parseObject(BudgProjExeJson);
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
	@RequestMapping(value = "/hrp/budg/project/execute/addOrUpdateBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjExeJson ="";
		

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
	  
		budgProjExeJson = budgProjExeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgProjExeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/deleteBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjExe(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("year", ids[0])   ;
				mapVo.put("month", ids[1])   ;
				mapVo.put("proj_id", ids[2])   ;
				mapVo.put("source_id", ids[3]);
				mapVo.put("payment_item_id", ids[4]);
				
		      listVo.add(mapVo);
		      
		  }
		    
			String budgProjExeJson = budgProjExeService.deleteBatch(listVo);
				
		  return JSONObject.parseObject(budgProjExeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据  
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/queryBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgProjExe = budgProjExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgProjExe);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/BudgProjExeImportPage", method = RequestMethod.GET)
	public String BudgProjExeImportPage(Model mode) throws Exception {

		return "hrp/budg/project/execute/BudgProjExeImport";

	}
	/**
	 * @Description 
	 * 下载  费用标准导入模版        
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/project/execute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\proj","预算项目执行导入模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入  
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/project/execute/readBudgProjExeFiles",method = RequestMethod.POST)  
    public String readBudgProjExeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgChargeStandardDict> list_err = new ArrayList<BudgChargeStandardDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgProjExe budgProjExe = new BudgProjExe();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【费用标准编码】重复;");
						}
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行【费用标准名称】重复;");
						}	
					}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgProjExeService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgChargeStandardDict data_exc = new BudgChargeStandardDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
	
	/**
	 * 项目下拉框  添加使用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/execute/queryBudgProj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String proj = budgProjExeService.queryBudgProj(mapVo);
		return proj;

	}
	
	/**
	 * 资金来源下拉框  添加使用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/execute/queryBudgSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String source = budgProjExeService.queryBudgSource(mapVo);
		return source;

	}
	
	/**
	 * 支出项目下拉框  添加使用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/execute/queryBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String item = budgProjExeService.queryBudgPaymentItem(mapVo);
		return item;

	}
    
	
	/**
	 * @Description 
	 * 确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/execute/confirmBudgProjExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmBudgProjExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
       
		String budgProjExeJson = null ;
		try {
			
			budgProjExeJson = budgProjExeService.confirmBudgProjExe(mapVo);
			
		} catch (Exception e) {
			
			budgProjExeJson = e.getMessage();
		}

		return JSONObject.parseObject(budgProjExeJson);
		
	}
}

