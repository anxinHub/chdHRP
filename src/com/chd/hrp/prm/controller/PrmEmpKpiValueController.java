
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.prm.entity.PrmEmpKpiValue;
import com.chd.hrp.prm.service.PrmEmpKpiValueService;

/**
 * 
 * @Description:
 * 0308 职工KPI指标数据准备表
 * @Table:
 * PRM_EMP_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmEmpKpiValueController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpKpiValueController.class);
	
	//引入Service服务
	@Resource(name = "prmEmpKpiValueService")
	private final PrmEmpKpiValueService prmEmpKpiValueService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/prmEmpKpiValueMainPage", method = RequestMethod.GET)
	public String prmEmpKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpivalue/prmEmpKpiValueMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/prmEmpKpiValueAddPage", method = RequestMethod.GET)
	public String prmEmpKpiValueAddPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpivalue/prmEmpKpiValueAdd";

	}

	/**
	 * @Description 
	 * 添加数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/addPrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmpKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmEmpKpiValueJson = prmEmpKpiValueService.addPrmEmpKpiValue(mapVo);

		return JSONObject.parseObject(prmEmpKpiValueJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/prmEmpKpiValueUpdatePage", method = RequestMethod.GET)
	public String prmEmpKpiValueUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmEmpKpiValue prmEmpKpiValue = new PrmEmpKpiValue();
    
		prmEmpKpiValue = prmEmpKpiValueService.queryPrmEmpKpiValueByCode(mapVo);
		
		mode.addAttribute("group_id", prmEmpKpiValue.getGroup_id());
		mode.addAttribute("hos_id", prmEmpKpiValue.getHos_id());
		mode.addAttribute("copy_code", prmEmpKpiValue.getCopy_code());
		mode.addAttribute("acc_year", prmEmpKpiValue.getAcc_year());
		mode.addAttribute("acc_month", prmEmpKpiValue.getAcc_month());
		mode.addAttribute("goal_code", prmEmpKpiValue.getGoal_code());
		mode.addAttribute("kpi_code", prmEmpKpiValue.getKpi_code());
		mode.addAttribute("emp_no", prmEmpKpiValue.getEmp_no());
		mode.addAttribute("emp_id", prmEmpKpiValue.getEmp_id());
		mode.addAttribute("kpi_value", prmEmpKpiValue.getKpi_value());
		mode.addAttribute("is_audit", prmEmpKpiValue.getIs_audit());
		mode.addAttribute("user_code", prmEmpKpiValue.getUser_code());
		mode.addAttribute("audit_date", prmEmpKpiValue.getAudit_date());
		
		return "hrp/prm/prmempkpivalue/prmEmpKpiValueUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/updatePrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmEmpKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmEmpKpiValueJson = prmEmpKpiValueService.updatePrmEmpKpiValue(mapVo);
		
		return JSONObject.parseObject(prmEmpKpiValueJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/deletePrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpKpiValue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("acc_year", ids[3])   ;
				mapVo.put("acc_month", ids[4])   ;
				mapVo.put("goal_code", ids[5])   ;
				mapVo.put("kpi_code", ids[6])   ;
				mapVo.put("emp_no", ids[7])   ;
				mapVo.put("emp_id", ids[8]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String prmEmpKpiValueJson = prmEmpKpiValueService.deleteBatchPrmEmpKpiValue(listVo);
			
	  return JSONObject.parseObject(prmEmpKpiValueJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/queryPrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmEmpKpiValue = prmEmpKpiValueService.queryPrmEmpKpiValue(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiValue);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/queryPrmEmpKpiValueScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiValueScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String prmEmpKpiValue = prmEmpKpiValueService.queryPrmEmpKpiValueScheme(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiValue);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 0308 职工KPI指标数据准备表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/prmEmpKpiValueImportPage", method = RequestMethod.GET)
	public String prmEmpKpiValueImportPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpivalue/prmEmpKpiValueImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 0308 职工KPI指标数据准备表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmempkpivalue/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\业务功能","职工kpi指标数据采集.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 0308 职工KPI指标数据准备表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/prm/prmempkpivalue/importPrmEmpKpiValue",method = RequestMethod.POST)
	 @ResponseBody
    public Map<String,Object> importPrmEmpKpiValue(@RequestParam Map<String,Object> mapVo,Model mode) throws IOException { 
			
		 if (mapVo.get("group_id") == null) {
			 mapVo.put("group_id", SessionManager.getGroupId());
		 }
			
		 if (mapVo.get("hos_id") == null) {
			 mapVo.put("hos_id", SessionManager.getHosId());
		 }
			
		 if (mapVo.get("copy_code") == null) {
			 mapVo.put("copy_code", SessionManager.getCopyCode());
		 }
		 
		 String prmEmpKpiValueJson = null;
		 
		 try {
			
			 prmEmpKpiValueJson = prmEmpKpiValueService.importPrmEmpKpiValue(mapVo);
			 
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmEmpKpiValueJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(prmEmpKpiValueJson);
		
    }  
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/createPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String prmHosKpiValue = prmEmpKpiValueService.createPrmEmpTargetData(mapVo);
		
		return JSONObject.parseObject(prmHosKpiValue);
			
	}
    
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/auditPrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmEmpKpiValue(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String prmHosTargetDataJson = "";
		
		try {
			
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
				
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
				
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
			
			mapVo.put("user_code",SessionManager.getUserCode());
			mapVo.put("is_audit", 1);
			mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			prmHosTargetDataJson = prmEmpKpiValueService.auditPrmEmpKpiValue(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
        
		return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/reAuditPrmEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmEmpKpiValue(@RequestParam Map<String,Object>mapVo, Model mode) throws Exception {
		
		
		String prmHosTargetDataJson = "";
		
		try {
			
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
				
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
				
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
			
			mapVo.put("user_code","");
			mapVo.put("is_audit", 0);
			mapVo.put("audit_date","");
			
			prmHosTargetDataJson = prmEmpKpiValueService.reAuditPrmEmpKpiValue(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
         
   
         return JSONObject.parseObject(prmHosTargetDataJson);
	}
    
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/saveBatchEmpKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> 	saveBatchEmpKpiValue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
	
	    
	       for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");

				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("acc_year", ids[3])   ;
				mapVo.put("acc_month", ids[4])   ;
				mapVo.put("kpi_code", ids[5])   ;
				mapVo.put("goal_code", ids[6]);
				mapVo.put("emp_no", ids[7]);
				mapVo.put("emp_id", ids[8]);
				mapVo.put("kpi_value", ids[9]);
				
	            listVo.add(mapVo);
	      
	    }
	       
			String prmEmpKpiValueJson = prmEmpKpiValueService.updateBatchPrmEmpKpiValue(listVo);
			
	  return JSONObject.parseObject(prmEmpKpiValueJson);
	}
}

