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
import com.chd.hrp.prm.service.PrmHosTargetDataCalculateService;


@Controller
public class PrmHosTargetDataCalculateController extends BaseController{
  
	
	private static Logger logger = Logger.getLogger(PrmHosTargetDataController.class);
	
	
	//引入Service服务
	@Resource(name = "prmHosTargetDataCalculateService")
	private final PrmHosTargetDataCalculateService prmHosTargetDataCalculateService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/prmHosTargetDataCalculateMainPage", method = RequestMethod.GET)
	public String prmHosTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhostargetdata/prmHosTargetDataCalculateMain";

	}
	
	
	/**
	 * @Description 
	 * 查询数据 0212 院级绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/queryPrmHosTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosTargetDataCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmHosTargetData = prmHosTargetDataCalculateService.queryPrmHosTargetPrmTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmHosTargetData);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 生成
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/createPrmHosTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
	
		
		
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
		
		String prmHosTargetData = prmHosTargetDataCalculateService.createPrmHosTargetDataCalculate(mapVo, paramVo);
		
		return JSONObject.parseObject(prmHosTargetData);
			
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/auditPrmHosTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmHosTargetDataCalculateJson = "";
		
          for (String id : paramVo.split(",")) {
 
   
              String[] ids=id.split("@");
               
       	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("target_code", ids[5])   ;
			    mapVo.put("check_hos_id", ids[6]);
			    mapVo.put("user_code", SessionManager.getUserCode());
			    mapVo.put("is_audit", 1);
			    mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			    
			  
			    prmHosTargetDataCalculateJson = prmHosTargetDataCalculateService.auditPrmHosTargetDataCalculate(mapVo);
          }
          
    
          
			return JSONObject.parseObject(prmHosTargetDataCalculateJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/reAuditPrmHosTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		 String prmHosTargetDataCalculateJson = "";
		
         for (String id : paramVo.split(",")) {

  
             String[] ids=id.split("@");
              
      	    	mapVo.put("group_id", ids[0])   ;
			    mapVo.put("hos_id", ids[1])   ;
			    mapVo.put("copy_code", ids[2])   ;
			    mapVo.put("acc_year", ids[3])   ;
			    mapVo.put("acc_month", ids[4])   ;
			    mapVo.put("target_code", ids[5])   ;
			    mapVo.put("check_hos_id", ids[6]);
			    mapVo.put("user_code","");
			    mapVo.put("is_audit", 0);
			    mapVo.put("audit_date","");
			    
			  
              
			    prmHosTargetDataCalculateJson = prmHosTargetDataCalculateService.reAuditPrmHosTargetDataCalculate(mapVo);
         }
         
   
         
			return JSONObject.parseObject(prmHosTargetDataCalculateJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/updateBatchPrmHosTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchPrmHosTargetDataCalculate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_hos_id", ids[6]);
				mapVo.put("target_value", ids[7]);
				
	            listVo.add(mapVo);
	      
	    }
	       
			String prmHosTargetDataCalculateJson = prmHosTargetDataCalculateService.updateBatchPrmHosTargetDataCalculate(listVo);
			
	  return JSONObject.parseObject(prmHosTargetDataCalculateJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmhostargetdata/collectPrmHosTargetCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectPrmHosTargetCalculate(
			@RequestParam Map<String, Object> mapVo, Model model)
			throws Exception {
		
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
	      
			String resultHosJson = prmHosTargetDataCalculateService.collectPrmHosTargetCalculate(mapVo);
			
	     return JSONObject.parseObject(resultHosJson);
	}
	
}
