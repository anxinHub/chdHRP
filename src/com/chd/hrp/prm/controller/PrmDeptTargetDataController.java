
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
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.service.PrmDeptTargetDataService;
import com.chd.hrp.prm.service.PrmTargetMethodService;

/**
 * 
 * @Description:
 * 0312 科室绩效指标数据表
 * @Table:
 * PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmDeptTargetDataController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmDeptTargetDataController.class);
	
	//引入Service服务
	@Resource(name = "prmDeptTargetDataService")
	private final PrmDeptTargetDataService prmDeptTargetDataService = null;
	
	@Resource(name = "prmTargetMethodService")
	private final PrmTargetMethodService prmTargetMethodService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/prmDeptTargetDataMainPage", method = RequestMethod.GET)
	public String prmDeptTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdepttargetdata/prmDeptTargetDataMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/prmDeptTargetDataAddPage", method = RequestMethod.GET)
	public String prmDeptTargetDataAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdepttargetdata/prmDeptTargetDataAdd";

	}

	/**
	 * @Description 
	 * 添加数据 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/addPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmDeptTargetDataJson = prmDeptTargetDataService.addPrmDeptTargetData(mapVo);

		return JSONObject.parseObject(prmDeptTargetDataJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/prmDeptTargetDataUpdatePage", method = RequestMethod.GET)
	public String prmDeptTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmDeptTargetData prmDeptTargetData = new PrmDeptTargetData();
    
		prmDeptTargetData = prmDeptTargetDataService.queryPrmDeptTargetDataByCode(mapVo);
		
		mode.addAttribute("group_id", prmDeptTargetData.getGroup_id());
		mode.addAttribute("hos_id", prmDeptTargetData.getHos_id());
		mode.addAttribute("copy_code", prmDeptTargetData.getCopy_code());
		mode.addAttribute("acc_year", prmDeptTargetData.getAcc_year());
		mode.addAttribute("acc_month", prmDeptTargetData.getAcc_month());
		mode.addAttribute("target_code", prmDeptTargetData.getTarget_code());
		mode.addAttribute("dept_no", prmDeptTargetData.getDept_no());
		mode.addAttribute("dept_id", prmDeptTargetData.getDept_id());
		mode.addAttribute("target_value", prmDeptTargetData.getTarget_value());
		mode.addAttribute("is_audit", prmDeptTargetData.getIs_audit());
		mode.addAttribute("user_code", prmDeptTargetData.getUser_code());
		mode.addAttribute("audit_date", prmDeptTargetData.getAudit_date());
		
		return "hrp/prm/prmdepttargetdata/prmDeptTargetDataUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/updatePrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmDeptTargetDataJson = prmDeptTargetDataService.updatePrmDeptTargetData(mapVo);
		
		return JSONObject.parseObject(prmDeptTargetDataJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/deletePrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptTargetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("target_code", ids[5])   ;
				mapVo.put("dept_no", ids[6])   ;
				mapVo.put("dept_id", ids[7]);
				
				PrmDeptTargetData prmDeptTargetData = prmDeptTargetDataService.queryPrmDeptTargetDataByCode(mapVo);
				
				if(prmDeptTargetData.getIs_audit() > 0){
					  return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除\"}");
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
			String prmDeptTargetDataJson = prmDeptTargetDataService.deleteBatchPrmDeptTargetData(listVo);
			
	  return JSONObject.parseObject(prmDeptTargetDataJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/queryPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String prmDeptTargetData = prmDeptTargetDataService.queryPrmDeptTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmDeptTargetData);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 0312 科室绩效指标数据表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/queryPrmDeptTargetPrmTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptTargetPrmTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmDeptTargetData = prmDeptTargetDataService.queryPrmDeptTargetPrmTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmDeptTargetData);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 0312 科室绩效指标数据表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/prmDeptTargetDataImportPage", method = RequestMethod.GET)
	public String prmDeptTargetDataImportPage(Model mode) throws Exception {

		return "hrp/prm/prmdepttargetdata/prmDeptTargetDataImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 0312 科室绩效指标数据表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmdepttargetdata/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\业务功能","科室指标数据采集.xlsx");
	    
	    return null; 
	 }
    

	/**
	 * @Description 
	 * CREATE 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/createPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmDeptTargetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
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
		
		String prmHosTargetData  = prmDeptTargetDataService.createPrmDeptTargetData(mapVo, paramVo);
		
		return JSONObject.parseObject(prmHosTargetData);
			
	}
	
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/auditPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosTargetData(@RequestParam Map<String,Object>mapVo) throws Exception {
		
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
			
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("user_code", SessionManager.getUserCode());
		mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
		
		mapVo.put("is_audit", 1);

		
		String prmDeptTargetDataJson = "";
		
		try {
			
			prmDeptTargetDataJson = prmDeptTargetDataService.auditPrmdeptTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			prmDeptTargetDataJson = e.getMessage();
		}
          
          
		return JSONObject.parseObject(prmDeptTargetDataJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/reAuditPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmDeptTargetData(@RequestParam Map<String,Object> mapVo) throws Exception {
		
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
		mapVo.put("audit_date","");
		
		mapVo.put("is_audit", 0);
		
		String prmDeptTargetDataJson = "";
		
		try {
			
			prmDeptTargetDataJson = prmDeptTargetDataService.auditPrmdeptTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmDeptTargetDataJson = e.getMessage();
		}
		
          
        return JSONObject.parseObject(prmDeptTargetDataJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/saveBatchPrmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchPrmDeptTargetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dept_no", ids[6]);
				mapVo.put("dept_id", ids[7]);
				mapVo.put("target_value", ids[8]);
				
	            listVo.add(mapVo);
	      
	    }
	       
			String prmHosTargetDataJson = prmDeptTargetDataService.updateBatchPrmDeptTargetData(listVo);
			
	  return JSONObject.parseObject(prmHosTargetDataJson);
	}
	
	public Map<String,PrmDeptDict> getDeptMap(List<PrmDeptDict> deptDictList){
		
		if(deptDictList.size() == 0 ){
			
			return null;
		}
		
		Map<String,PrmDeptDict> deptMap = new HashMap<String,PrmDeptDict>();
		
		for(PrmDeptDict deptDict : deptDictList){
			
			deptMap.put(deptDict.getDept_name(), deptDict);
		}
		
		return deptMap;
	}
	
	// 导入
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/importPrmDeptTargerData", method = RequestMethod.POST)
	@ResponseBody
	public String importPrmDeptTargerData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String importJson = null ;
		
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
			
			  mapVo.put("user_id", SessionManager.getUserId());
			importJson = prmDeptTargetDataService.importPrmDeptTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			importJson = e.getMessage();
		}
		
		return importJson;
	}
}

