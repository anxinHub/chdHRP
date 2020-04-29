/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.budgprojsetup;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgProjSetUp;
import com.chd.hrp.budg.service.project.budgprojsetup.BudgProjSetUpService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_PROJ_SET_UP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjSetUpController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjSetUpController.class);
	
	//引入Service服务
	@Resource(name = "budgProjSetUpService")
	private final BudgProjSetUpService budgProjSetUpService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/budgProjSetUpPage", method = RequestMethod.GET)
	public String budgProjSetUpMainPage(Model mode) throws Exception {

		return "hrp/budg/project/budgprojsetup/budgProjSetUpMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/budgProjSetUpAddPage", method = RequestMethod.GET)
	public String budgProjSetUpAddPage(Model mode) throws Exception {

		return "hrp/budg/project/budgprojsetup/budgProjSetUpAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/addBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjSetUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
       
		String budgProjSetUpJson = budgProjSetUpService.addBudgProjSetUp(mapVo);

		return JSONObject.parseObject(budgProjSetUpJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/budgProjSetUpUpdatePage", method = RequestMethod.GET)
	public String budgProjSetUpUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		BudgProjSetUp budgProjSetUp = new BudgProjSetUp();
    

		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        mapVo.put("acct_year", SessionManager.getAcctYear());
        
		Map<String,Object> mapdata = budgProjSetUpService.queryBudgProjSetUpByCode(mapVo);
		
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		
		mode.addAttribute("group_id", mapdata.get("group_id"));
		
		mode.addAttribute("hos_id", mapdata.get("hos_id"));
		
		mode.addAttribute("proj_id", mapdata.get("proj_id"));
		
		mode.addAttribute("proj_code", mapdata.get("proj_code"));

		mode.addAttribute("apply_code", mapdata.get("apply_code"));
		
		mode.addAttribute("proj_name", mapdata.get("proj_name"));
		
		mode.addAttribute("type_code", mapdata.get("type_code"));
		
		mode.addAttribute("type_name", mapdata.get("type_name"));
		
		mode.addAttribute("level_code", mapdata.get("level_code"));
		
		mode.addAttribute("level_name", mapdata.get("level_name"));
		
		mode.addAttribute("proj_simple", mapdata.get("proj_simple"));
		
		mode.addAttribute("use_code", mapdata.get("use_code"));
		
		mode.addAttribute("use_name", mapdata.get("use_name"));
		
		mode.addAttribute("con_emp_id", mapdata.get("con_emp_id"));
		
		mode.addAttribute("con_emp_name", mapdata.get("con_emp_name"));
		
		mode.addAttribute("con_phone", mapdata.get("con_phone"));
		
		mode.addAttribute("acc_emp_id", mapdata.get("acc_emp_id"));
		
		mode.addAttribute("acc_emp_name", mapdata.get("acc_emp_name"));
		
		mode.addAttribute("acc_phone", mapdata.get("acc_phone"));
		
		mode.addAttribute("dept_id", mapdata.get("dept_id"));
		
		mode.addAttribute("dept_no", mapdata.get("dept_no"));
		
		mode.addAttribute("dept_name", mapdata.get("dept_name"));
		
		mode.addAttribute("app_emp_id", mapdata.get("app_emp_id"));
		
		mode.addAttribute("app_emp_name", mapdata.get("app_emp_name"));
		
		if(!"".equals(mapdata.get("app_date")) && mapdata.get("app_date")!=null )
		{
		mode.addAttribute("app_date", dateFormater.format(mapdata.get("app_date")) );
		}
		
		mode.addAttribute("app_phone", mapdata.get("app_phone"));
		
		mode.addAttribute("email", mapdata.get("email"));
		
		mode.addAttribute("note", mapdata.get("note"));
		
		if(!"".equals(mapdata.get("set_up_date")) && mapdata.get("set_up_date")!=null )
		{
		mode.addAttribute("set_up_date", dateFormater.format(mapdata.get("set_up_date")));
		}
		
		
		mode.addAttribute("state", mapdata.get("state"));
		
		mode.addAttribute("value_code", mapdata.get("value_code"));
		
		mode.addAttribute("value_name", mapdata.get("value_name"));
		
		mode.addAttribute("is_stop", mapdata.get("is_stop"));
		
		mode.addAttribute("is_carry", mapdata.get("is_carry"));
		
		
		return "hrp/budg/project/budgprojsetup/budgProjSetUpUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/updateBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgProjSetUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
	  
		String budgProjSetUpJson = budgProjSetUpService.updateBudgProjSetUp(mapVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 审核 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/auditBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBudgProjSetUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		  DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		  String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("checker", SessionManager.getUserId())   ;
			mapVo.put("check_date", time)   ;
			mapVo.put("proj_code", ids[3]);
			mapVo.put("apply_code", ids[4]);
			
      listVo.add(mapVo);
      
    }
		
		String budgProjSetUpJson = budgProjSetUpService.auditBudgProjSetUp(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 消审 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/cancelauditBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelauditBudgProjSetUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("checker", "")   ;
			mapVo.put("check_date", "")   ;
			mapVo.put("proj_code", ids[3]);
			mapVo.put("apply_code", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = budgProjSetUpService.cancelauditBudgProjSetUp(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 提交 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/submitBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitBudgProjSetUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("checker", SessionManager.getUserId())   ;
			mapVo.put("check_date", time)   ;
			mapVo.put("proj_code", ids[3]);
			mapVo.put("apply_code", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = budgProjSetUpService.submitBudgProjSetUp(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	/**
	 * @Description 
	 * 撤回 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/recallBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> recallBudgProjSetUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id",SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("checker", SessionManager.getUserId())   ;
			mapVo.put("check_date", time)   ;
			mapVo.put("proj_code", ids[3]);
			mapVo.put("apply_code", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String budgProjSetUpJson = budgProjSetUpService.recallBudgProjSetUp(listVo);
		
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/addOrUpdateBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgProjSetUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgProjSetUpJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		budgProjSetUpJson = budgProjSetUpService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgProjSetUpJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/deleteBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgProjSetUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id",SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("apply_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgProjSetUpJson = budgProjSetUpService.deleteBudgProjSetUp(listVo);
			
	  return JSONObject.parseObject(budgProjSetUpJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/queryBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjSetUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
		String budgProjSetUp = budgProjSetUpService.queryBudgProjSetUp(getPage(mapVo));

		return JSONObject.parseObject(budgProjSetUp);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/budgProjSetUpImportPage", method = RequestMethod.GET)
	public String budgProjSetUpImportPage(Model mode) throws Exception {

		return "hrp/budg/project/budgprojsetup/budgProjSetUpImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/project/budgprojsetup/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/project/budgprojsetup/readBudgProjSetUpFiles",method = RequestMethod.POST)  
    public String readBudgProjSetUpFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgProjSetUp> list_err = new ArrayList<BudgProjSetUp>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgProjSetUp budgProjSetUp = new BudgProjSetUp();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgProjSetUp.setApply_code(temp[3]);
		    		mapVo.put("apply_code", temp[3]);
					
					} else {
						
						err_sb.append("立项申请单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgProjSetUp.setProj_code(temp[4]);
		    		mapVo.put("proj_code", temp[4]);
					
					} else {
						
						err_sb.append("项目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgProjSetUp.setProj_name(temp[5]);
		    		mapVo.put("proj_name", temp[5]);
					
					} else {
						
						err_sb.append("项目名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgProjSetUp.setType_code(temp[6]);
		    		mapVo.put("type_code", temp[6]);
					
					} else {
						
						err_sb.append("项目类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgProjSetUp.setLevel_code(temp[7]);
		    		mapVo.put("level_code", temp[7]);
					
					} else {
						
						err_sb.append("项目级别为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgProjSetUp.setUse_code(temp[8]);
		    		mapVo.put("use_code", temp[8]);
					
					} else {
						
						err_sb.append("项目用途为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgProjSetUp.setCon_emp_id(Long.valueOf(temp[9]));
		    		mapVo.put("con_emp_id", temp[9]);
					
					} else {
						
						err_sb.append("项目负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgProjSetUp.setCon_phone(temp[10]);
		    		mapVo.put("con_phone", temp[10]);
					
					} else {
						
						err_sb.append("项目负责人电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgProjSetUp.setAcc_emp_id(Long.valueOf(temp[11]));
		    		mapVo.put("acc_emp_id", temp[11]);
					
					} else {
						
						err_sb.append("财务负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgProjSetUp.setAcc_phone(temp[12]);
		    		mapVo.put("acc_phone", temp[12]);
					
					} else {
						
						err_sb.append("财务负责人电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgProjSetUp.setDept_no(Long.valueOf(temp[13]));
		    		mapVo.put("dept_no", temp[13]);
					
					} else {
						
						err_sb.append("填报部门变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					budgProjSetUp.setDept_id(Long.valueOf(temp[14]));
		    		mapVo.put("dept_id", temp[14]);
					
					} else {
						
						err_sb.append("填报部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					budgProjSetUp.setApp_emp_id(Long.valueOf(temp[15]));
		    		mapVo.put("app_emp_id", temp[15]);
					
					} else {
						
						err_sb.append("填报人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					budgProjSetUp.setApp_date(DateUtil.stringToDate(temp[16]));
		    		mapVo.put("app_date", temp[16]);
					
					} else {
						
						err_sb.append("填报日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					budgProjSetUp.setApp_phone(temp[17]);
		    		mapVo.put("app_phone", temp[17]);
					
					} else {
						
						err_sb.append("联系电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					budgProjSetUp.setEmail(temp[18]);
		    		mapVo.put("email", temp[18]);
					
					} else {
						
						err_sb.append("EMAIL为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					budgProjSetUp.setNote(temp[19]);
		    		mapVo.put("note", temp[19]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					budgProjSetUp.setChecker(Long.valueOf(temp[20]));
		    		mapVo.put("checker", temp[20]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					budgProjSetUp.setCheck_date(DateUtil.stringToDate(temp[21]));
		    		mapVo.put("check_date", temp[21]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					budgProjSetUp.setSpell_code(temp[22]);
		    		mapVo.put("spell_code", temp[22]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					budgProjSetUp.setWbx_code(temp[23]);
		    		mapVo.put("wbx_code", temp[23]);
					
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					budgProjSetUp.setState(temp[24]);
		    		mapVo.put("state", temp[24]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				BudgProjSetUp data_exc_extis = budgProjSetUpService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjSetUp.setError_type(err_sb.toString());
					
					list_err.add(budgProjSetUp);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgProjSetUpService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjSetUp data_exc = new BudgProjSetUp();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/project/budgprojsetup/addBatchBudgProjSetUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgProjSetUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgProjSetUp> list_err = new ArrayList<BudgProjSetUp>();
		
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
			
			BudgProjSetUp budgProjSetUp = new BudgProjSetUp();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("apply_code"))) {
						
					budgProjSetUp.setApply_code((String)jsonObj.get("apply_code"));
		    		mapVo.put("apply_code", jsonObj.get("apply_code"));
		    		} else {
						
						err_sb.append("立项申请单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_code"))) {
						
					budgProjSetUp.setProj_code((String)jsonObj.get("proj_code"));
		    		mapVo.put("proj_code", jsonObj.get("proj_code"));
		    		} else {
						
						err_sb.append("项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_name"))) {
						
					budgProjSetUp.setProj_name((String)jsonObj.get("proj_name"));
		    		mapVo.put("proj_name", jsonObj.get("proj_name"));
		    		} else {
						
						err_sb.append("项目名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					budgProjSetUp.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("项目类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("level_code"))) {
						
					budgProjSetUp.setLevel_code((String)jsonObj.get("level_code"));
		    		mapVo.put("level_code", jsonObj.get("level_code"));
		    		} else {
						
						err_sb.append("项目级别为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("use_code"))) {
						
					budgProjSetUp.setUse_code((String)jsonObj.get("use_code"));
		    		mapVo.put("use_code", jsonObj.get("use_code"));
		    		} else {
						
						err_sb.append("项目用途为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("con_emp_id"))) {
						
					budgProjSetUp.setCon_emp_id(Long.valueOf((String)jsonObj.get("con_emp_id")));
		    		mapVo.put("con_emp_id", jsonObj.get("con_emp_id"));
		    		} else {
						
						err_sb.append("项目负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("con_phone"))) {
						
					budgProjSetUp.setCon_phone((String)jsonObj.get("con_phone"));
		    		mapVo.put("con_phone", jsonObj.get("con_phone"));
		    		} else {
						
						err_sb.append("项目负责人电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_emp_id"))) {
						
					budgProjSetUp.setAcc_emp_id(Long.valueOf((String)jsonObj.get("acc_emp_id")));
		    		mapVo.put("acc_emp_id", jsonObj.get("acc_emp_id"));
		    		} else {
						
						err_sb.append("财务负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_phone"))) {
						
					budgProjSetUp.setAcc_phone((String)jsonObj.get("acc_phone"));
		    		mapVo.put("acc_phone", jsonObj.get("acc_phone"));
		    		} else {
						
						err_sb.append("财务负责人电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					budgProjSetUp.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("填报部门变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgProjSetUp.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("填报部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("app_emp_id"))) {
						
					budgProjSetUp.setApp_emp_id(Long.valueOf((String)jsonObj.get("app_emp_id")));
		    		mapVo.put("app_emp_id", jsonObj.get("app_emp_id"));
		    		} else {
						
						err_sb.append("填报人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("app_date"))) {
						
					budgProjSetUp.setApp_date(DateUtil.stringToDate((String)jsonObj.get("app_date")));
		    		mapVo.put("app_date", jsonObj.get("app_date"));
		    		} else {
						
						err_sb.append("填报日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("app_phone"))) {
						
					budgProjSetUp.setApp_phone((String)jsonObj.get("app_phone"));
		    		mapVo.put("app_phone", jsonObj.get("app_phone"));
		    		} else {
						
						err_sb.append("联系电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("email"))) {
						
					budgProjSetUp.setEmail((String)jsonObj.get("email"));
		    		mapVo.put("email", jsonObj.get("email"));
		    		} else {
						
						err_sb.append("EMAIL为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					budgProjSetUp.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					budgProjSetUp.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					budgProjSetUp.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgProjSetUp.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgProjSetUp.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					budgProjSetUp.setState((String)jsonObj.get("state"));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				BudgProjSetUp data_exc_extis = budgProjSetUpService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgProjSetUp.setError_type(err_sb.toString());
					
					list_err.add(budgProjSetUp);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgProjSetUpService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgProjSetUp data_exc = new BudgProjSetUp();
			
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

