
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
import com.chd.hrp.prm.service.PrmHosKpiValueCalculateService;

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
public class PrmHosKpiValueCalculateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmHosKpiValueCalculateController.class);
	
	//引入Service服务
	@Resource(name = "prmHosKpiValueCalculateService")
	private final PrmHosKpiValueCalculateService prmHosKpiValueCalculateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/prmHosKpiValueCalculateMainPage", method = RequestMethod.GET)
	public String prmHosKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpivalue/prmHosKpiValueCalculateMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 0208 医院KPI指标数据准备表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/queryPrmHosKpiValueSchemeCalculate", method = RequestMethod.POST)
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
		String prmHosKpiValue = prmHosKpiValueCalculateService.queryPrmHosKpiValueSchemeCalculate(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiValue);
		
	}
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/createHosKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosTargetDataCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String prmHosKpiValue = prmHosKpiValueCalculateService.createPrmHosTargetDataCalculate(mapVo);
		
		return JSONObject.parseObject(prmHosKpiValue);
			
	}
    
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/auditPrmHosKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			  
			    prmHosTargetDataJson = prmHosKpiValueCalculateService.auditPrmHosKpiValueCalculate(mapVo);
         }
         
   
         
			return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/reAuditHosKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHosKpiValueCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			    

			    prmHosTargetDataJson = prmHosKpiValueCalculateService.reauditPrmHosKpiValueCalculate(mapVo);
         }
         
   
         
			return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhoskpivalue/saveBatchHosKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> 	saveBatchHosKpiValueCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	       
			String HosKpiValueCalculateJson = prmHosKpiValueCalculateService.updateBatchPrmHosKpiValueCalculate(listVo);
			
	  return JSONObject.parseObject(HosKpiValueCalculateJson);
	}
}

