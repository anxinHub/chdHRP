
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
import com.chd.hrp.prm.service.PrmEmpTargetDataCalculateService;
import com.chd.hrp.prm.service.PrmEmpTargetDataService;

/**
 * 
 * @Description:
 * 0412 职工绩效指标数据表
 * @Table:
 * PRM_EMP_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmEmpTargetDataCalculateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpTargetDataCalculateController.class);
	
	//引入Service服务
	@Resource(name = "prmEmpTargetDataCalculateService")
	private final PrmEmpTargetDataCalculateService prmEmpTargetDataCalculateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/prmEmpTargetDataCalculateMainPage", method = RequestMethod.GET)
	public String prmEmpTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmemptargetdata/prmEmpTargetDataCalculateMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 0412 职工绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/queryPrmEmpTargetPrmTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpTargetPrmTargetDataCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String PrmEmpTargetPrmTargetDataCalculate = prmEmpTargetDataCalculateService.queryPrmEmpTargetPrmTargetDataCalculate(getPage(mapVo));

		return JSONObject.parseObject(PrmEmpTargetPrmTargetDataCalculate);
		
	}
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/auditPrmEmpTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmEmpTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmEmpTargetDataCalculateJson = "";
		
          for (String id : paramVo.split(",")) {
 
   
              String[] ids=id.split("@");
               
       	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("target_code", ids[5])   ;
			    mapVo.put("emp_no", ids[6]);
			    mapVo.put("emp_id", ids[7]);
			    mapVo.put("user_code", SessionManager.getUserCode());
			    mapVo.put("is_audit", 1);
			    mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			    

			    prmEmpTargetDataCalculateJson = prmEmpTargetDataCalculateService.auditPrmEmpTargetDataCalculate(mapVo);
          }
          
    
          
			return JSONObject.parseObject(prmEmpTargetDataCalculateJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/reAuditPrmEmpTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmEmpTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmEmpTargetDataCalculateJson = "";
		
          for (String id : paramVo.split(",")) {
 
   
              String[] ids=id.split("@");
               
       	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("target_code", ids[5])   ;
			    mapVo.put("emp_no", ids[6]);
			    mapVo.put("emp_id", ids[7]);
			    mapVo.put("user_code", "");
			    mapVo.put("is_audit", 0);
			    mapVo.put("audit_date","");
			    
			  
               
			    prmEmpTargetDataCalculateJson = prmEmpTargetDataCalculateService.reAuditPrmEmpTargetDataCalculate(mapVo);
          }
          
    
          
			return JSONObject.parseObject(prmEmpTargetDataCalculateJson);
	}
	
	
	
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/createPrmEmpTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmEmpTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		  Map<String, Object> mapVo =  new HashMap<String, Object>();
		  
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
		
		String prmHosTargetDataCalculate = prmEmpTargetDataCalculateService.createPrmEmpTargetDataCalculate(mapVo, paramVo);
		
		return JSONObject.parseObject(prmHosTargetDataCalculate);
			
	}
    
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/saveBatchPrmEmpTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchPrmEmpTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
	
	    
	       for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");

				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("acc_year", ids[3])   ;
				mapVo.put("acc_month", ids[4])   ;
				mapVo.put("target_code", ids[5])   ;
				mapVo.put("emp_no", ids[6]);
				mapVo.put("emp_id", ids[7]);
				mapVo.put("target_value", ids[8]);
				
	            listVo.add(mapVo);
	      
	    }
	       
			String prmEmpTargetDataJson = prmEmpTargetDataCalculateService.updateBatchPrmEmpTargetDataCalculate(listVo);
			
	  return JSONObject.parseObject(prmEmpTargetDataJson);
	}
	
}

