
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.prm.service.PrmEmpKpiValueCalculateService;

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
public class PrmEmpKpiValueCalculateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpKpiValueCalculateController.class);
	
	//引入Service服务
	@Resource(name = "prmEmpKpiValueCalculateService")
	private final PrmEmpKpiValueCalculateService prmEmpKpiValueCalculateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/prmEmpKpiValueCalculateMainPage", method = RequestMethod.GET)
	public String prmEmpKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpivalue/prmEmpKpiValueCalculateMain";

	}

	/**
	 * @Description 
	 * 查询数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/queryPrmEmpKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiValueCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String prmEmpKpiValueCalculate = prmEmpKpiValueCalculateService.queryPrmEmpKpiValueCalculate(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiValueCalculate);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 0308 职工KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/queryPrmEmpKpiValueSchemeCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiValueSchemeCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmEmpKpiValueCalculate = prmEmpKpiValueCalculateService.queryPrmEmpKpiValueSchemeCalculate(getPage(mapVo));;

		return JSONObject.parseObject(prmEmpKpiValueCalculate);
		
	}
	
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/createPrmEmpTargetDataCalculate", method = RequestMethod.POST)
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
		
		String prmHosKpiValue = prmEmpKpiValueCalculateService.createPrmEmpTargetDataCalculate(mapVo);
		
		return JSONObject.parseObject(prmHosKpiValue);
			
	}
    
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/auditPrmEmpKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmHosTargetDataJson = "";
		
         for (String id : paramVo.split(",")) {

  
             String[] ids=id.split("@");
              
      	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("goal_code", ids[5])   ;
			    mapVo.put("kpi_code", ids[6])   ;
			    mapVo.put("check_hos_id", ids[7]);
			    mapVo.put("user_code",SessionManager.getUserCode());
			    mapVo.put("is_audit", 1);
			    mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			   
			    prmHosTargetDataJson = prmEmpKpiValueCalculateService.auditPrmEmpKpiValueCalculate(mapVo);
         }
         
   
         
			return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/reAuditPrmEmpKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHosKpiValue(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmHosTargetDataJson = "";
		
         for (String id : paramVo.split(",")) {

  
             String[] ids=id.split("@");
              
      	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("goal_code", ids[5])   ;
			    mapVo.put("kpi_code", ids[6])   ;
			    mapVo.put("check_hos_id", ids[7]);
			    mapVo.put("user_code","");
			    mapVo.put("is_audit", 0);
			    mapVo.put("audit_date","");
			    

			    prmHosTargetDataJson = prmEmpKpiValueCalculateService.reAuditPrmEmpKpiValueCalculate(mapVo);
         }
         
   
         
			return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpivalue/saveBatchEmpKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> 	saveBatchEmpKpiValueCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	       
			String prmEmpKpiValueCalculateJson = prmEmpKpiValueCalculateService.updateBatchPrmEmpKpiValueCalculate(listVo);
			
	  return JSONObject.parseObject(prmEmpKpiValueCalculateJson);
	}
}

