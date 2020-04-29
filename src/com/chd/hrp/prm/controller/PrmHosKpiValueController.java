
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
import com.chd.hrp.prm.entity.PrmHosKpiValue;
import com.chd.hrp.prm.service.PrmHosKpiValueService;

/**
 * 
 * @Description:
 * 0208 医院KPI指标数据准备表
 * @Table:
 * PRM_HOS_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmHosKpiValueController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmHosKpiValueController.class);
	
	//引入Service服务
	@Resource(name = "prmHosKpiValueService")
	private final PrmHosKpiValueService prmHosKpiValueService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/prmHosKpiValueMainPage", method = RequestMethod.GET)
	public String prmHosKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpivalue/prmHosKpiValueMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/prmHosKpiValueAddPage", method = RequestMethod.GET)
	public String prmHosKpiValueAddPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpivalue/prmHosKpiValueAdd";

	}

	/**
	 * @Description 
	 * 添加数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/addPrmHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmHosKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmHosKpiValueJson = prmHosKpiValueService.addPrmHosKpiValue(mapVo);

		return JSONObject.parseObject(prmHosKpiValueJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/prmHosKpiValueUpdatePage", method = RequestMethod.GET)
	public String prmHosKpiValueUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmHosKpiValue prmHosKpiValue = new PrmHosKpiValue();
    
		prmHosKpiValue = prmHosKpiValueService.queryPrmHosKpiValueByCode(mapVo);
		
		mode.addAttribute("group_id", prmHosKpiValue.getGroup_id());
		mode.addAttribute("hos_id", prmHosKpiValue.getHos_id());
		mode.addAttribute("copy_code", prmHosKpiValue.getCopy_code());
		mode.addAttribute("acc_year", prmHosKpiValue.getAcc_year());
		mode.addAttribute("acc_month", prmHosKpiValue.getAcc_month());
		mode.addAttribute("goal_code", prmHosKpiValue.getGoal_code());
		mode.addAttribute("kpi_code", prmHosKpiValue.getKpi_code());
		mode.addAttribute("check_hos_id", prmHosKpiValue.getCheck_hos_id());
		mode.addAttribute("kpi_value", prmHosKpiValue.getKpi_value());
		mode.addAttribute("is_audit", prmHosKpiValue.getIs_audit());
		mode.addAttribute("user_code", prmHosKpiValue.getUser_code());
		mode.addAttribute("audit_date", prmHosKpiValue.getAudit_date());
		
		return "hrp/prm/prmhoskpivalue/prmHosKpiValueUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/updatePrmHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmHosKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmHosKpiValueJson = prmHosKpiValueService.updatePrmHosKpiValue(mapVo);
		
		return JSONObject.parseObject(prmHosKpiValueJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/deletePrmHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpiValue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_hos_id", ids[7]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String prmHosKpiValueJson = prmHosKpiValueService.deleteBatchPrmHosKpiValue(listVo);
			
	  return JSONObject.parseObject(prmHosKpiValueJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/queryPrmHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmHosKpiValue = prmHosKpiValueService.queryPrmHosKpiValue(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiValue);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/queryPrmHosKpiValueScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiValueScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmHosKpiValue = prmHosKpiValueService.queryPrmHosKpiValueScheme(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiValue);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 0208 医院KPI指标数据准备表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/prmHosKpiValueImportPage", method = RequestMethod.GET)
	public String prmHosKpiValueImportPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpivalue/prmHosKpiValueImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 0208 医院KPI指标数据准备表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmhoskpivalue/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\业务功能","院级kpi指标数据采集.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 0208 医院KPI指标数据准备表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/prm/prmhoskpivalue/importPrmHosKpiValue",method = RequestMethod.POST)  
	 @ResponseBody
    public Map<String,Object> importPrmHosKpiValue(@RequestParam Map<String,Object> mapVo,Model mode) throws IOException { 
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
				
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
				
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String importJson = null;
		try {
			
			importJson = prmHosKpiValueService.importPrmHosKpiValue(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			
			importJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(importJson);
    }
    
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/createHosKpiValue", method = RequestMethod.POST)
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
		
		String prmHosKpiValue = prmHosKpiValueService.createPrmHosTargetData(mapVo);
		
		return JSONObject.parseObject(prmHosKpiValue);
			
	}
    
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/auditPrmHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosKpiValue(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String prmHosTargetDataJson = null;
		 
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
			 
			prmHosTargetDataJson = prmHosKpiValueService.auditPrmHosKpiValue(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
         
		return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/reAuditHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHosKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String prmHosTargetDataJson = null;
		
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
			
        	prmHosTargetDataJson = prmHosKpiValueService.reauditPrmHosKpiValue(mapVo);
        	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}
         
         
		return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/saveBatchHosKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> 	saveBatchHosKpiValue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_hos_id", ids[6]);
				mapVo.put("goal_code", ids[7]);
				mapVo.put("kpi_value", ids[8]);
				
	            listVo.add(mapVo);
	      
	    }
	       
			String PrmHosKpiValueJson = prmHosKpiValueService.updateBatchPrmHosKpiValue(listVo);
			
	  return JSONObject.parseObject(PrmHosKpiValueJson);
	}

}

