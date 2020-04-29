/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.med;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.entity.BudgMedCost;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg.MedInDYBudgService;
import com.chd.hrp.budg.service.business.med.BudgMedCostService;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
/**
 * 
 * @Description:
 * 医疗支出预算
 * @Table:
 * BUDG_MED_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedCostController.class);
	
	//引入Service服务
	@Resource(name = "budgMedCostService")
	private final BudgMedCostService budgMedCostService = null;
	
	
	@Resource(name = "medInDYBudgService")
	private final MedInDYBudgService medInDYBudgService = null;
	
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;
	
	@Resource(name = "budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostMainPage", method = RequestMethod.GET)
	public String budgMedCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/med/cost/budgMedCostMain";

	}
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedMonthCostMainPage", method = RequestMethod.GET)
	public String budgMedMonthCostMainPage(Model mode) throws Exception {

		return "hrp/budg/business/med/cost/budgMedMonthCostMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostAddPage", method = RequestMethod.GET)
	public String budgMedCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/med/cost/budgMedCostAdd";

	}
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedMonthCostAddPage", method = RequestMethod.GET)
	public String budgMedMonthCostAddPage(Model mode) throws Exception {

		return "hrp/budg/business/med/cost/budgMedMonthCostAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/addBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgMedCostJson = budgMedCostService.add(mapVo);

		return JSONObject.parseObject(budgMedCostJson);
		
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/addBudgMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedMonthCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       mapVo.put("state", "01");
       mapVo.put("tableName", "BUDG_MED_COST_DY");
		String budgMedCostJson = budgMedCostService.addMonth(mapVo);

		return JSONObject.parseObject(budgMedCostJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostUpdatePage", method = RequestMethod.GET)
	public String budgMedCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedCost budgMedCost = new BudgMedCost();
    
		budgMedCost = budgMedCostService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedCost.getGroup_id());
		mode.addAttribute("hos_id", budgMedCost.getHos_id());
		mode.addAttribute("copy_code", budgMedCost.getCopy_code());
		mode.addAttribute("budg_year", budgMedCost.getBudg_year());
		mode.addAttribute("month", budgMedCost.getMonth());
		mode.addAttribute("dept_id", budgMedCost.getDept_id());
		mode.addAttribute("subj_code", budgMedCost.getSubj_code());
		mode.addAttribute("cost_budg", budgMedCost.getCost_budg());
		mode.addAttribute("last_month_carried", budgMedCost.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgMedCost.getCarried_next_month());
		
		return "hrp/budg/business/med/cost/budgMedCostUpdate";
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostMonthAuditPage", method = RequestMethod.GET)
	public String budgMedCostMonthAuditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		mode.addAttribute("state", mapVo.get("state"));
		mode.addAttribute("type", mapVo.get("type"));
		return "hrp/budg/business/med/cost/budgMedCostMonthAudit";
	}
	
@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostMonthUpdatePage", method = RequestMethod.GET)
	public String budgMedCostMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mapVo.put("tableName", "BUDG_MED_COST_DY");
		List<Map<String,Object>> list= budgMedCostService.queryDeptMedCostMonth(mapVo);
		if(list.size()==1){
			mode.addAttribute("budg_year", list.get(0).get("BUDG_YEAR"));
			mode.addAttribute("dept_id", list.get(0).get("DEPT_ID"));
			mode.addAttribute("subj_code", list.get(0).get("SUBJ_CODE"));
			mode.addAttribute("base_value", list.get(0).get("BASE_VALUE"));
			mode.addAttribute("grow_rate", list.get(0).get("GROW_RATE"));
			mode.addAttribute("budg_value", list.get(0).get("BUDG_VALUE"));
			mode.addAttribute("remark", list.get(0).get("REMARK"));
		}
		return "hrp/budg/business/med/cost/budgMedCostMonthUpdate";
	}	
	/**
	 * @Description 
	 * 更新数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/updateBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String budgMedCostJson = budgMedCostService.update(mapVo);
		
		return JSONObject.parseObject(budgMedCostJson);
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/updateBudgMedCostMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedCostMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		mapVo.put("tableName", "BUDG_MED_COST_DY");
		String budgMedCostJson = budgMedCostService.updateMonth(mapVo);
		
		return JSONObject.parseObject(budgMedCostJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/addOrUpdateBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedCostJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
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
	  
		budgMedCostJson = budgMedCostService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedCostJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/deleteBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				mapVo.put("subj_code", ids[5]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgMedCostJson = budgMedCostService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedCostJson);
			
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/deleteBudgMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedMonthCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("budg_year", ids[0])   ;
				mapVo.put("dept_id", ids[1])   ;
				mapVo.put("subj_code", ids[2]);
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				listVo.add(mapVo);
	      
	    }
	    
	 String budgMedCostJson = budgMedCostService.delMonth("BUDG_MED_COST_DY",listVo);
			
	  return JSONObject.parseObject(budgMedCostJson);
			
	}
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/auditOrUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditOrUnAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {			
		switch(mapVo.get("state").toString()){
			case "01":
				mapVo.put("checker","");
				mapVo.put("check_date","");
				break;
			case "02":
				if(mapVo.get("type").toString().equals("1")){
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				}else{
					mapVo.put("checker","");
					mapVo.put("check_date","");
				}
				break;
			case "03":
				mapVo.put("checker",SessionManager.getUserId());
				mapVo.put("check_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
				break;
				 
		}
		
		List<Map<String,Object>> listVo=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		String param=mapVo.get("paramVo").toString();
		if(param.endsWith(","))
			param=param.substring(0,param.length()-1);
		String [] p1=param.split(",");
		String[] params=null;
		for(int m=0;m<p1.length;m++){
			params=p1[m].split("@");
			for(int i=0;i<params.length;i++){
				map=new HashMap<String,Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("budg_year", params[0]);
				map.put("dept_id", params[1]);
				map.put("subj_code", params[2]);
				map.put("state", mapVo.get("state"));
				map.put("checker", mapVo.get("checker"));
				map.put("check_date", mapVo.get("check_date"));
				map.put("op", mapVo.get("op"));
				listVo.add(map);
			}
		}
		String matOrderMain=null;
		if(mapVo.get("type").toString().equals("1"))
			matOrderMain = budgMedCostService.auditOrUnAudit("BUDG_MED_COST_DY",listVo);
		else
			matOrderMain=budgMedCostService.affiOrUnAffi("BUDG_MED_COST_DY",listVo);
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 审核、销审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/affiOrUnAffi", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> affiOrUnAffi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//审核
			switch(mapVo.get("state").toString()){
				case "02":
					mapVo.put("affi_emp","");
					mapVo.put("affi_date","");
					break;
				case "03":
					mapVo.put("affi_emp",SessionManager.getUserId());
					mapVo.put("affi_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
					break;
				 
			}
			
		String matOrderMain = budgMedCostService.affiOrUnAffi("BUDG_MED_COST_DY",null);		
		return JSONObject.parseObject(matOrderMain);
	}
	/**
	 * @Description 
	 * 查询数据  科室医疗支出预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/queryDeptMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedCost = budgMedCostService.queryDeptMedCost(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
	@RequestMapping(value = "/hrp/budg/business/med/cost/queryDeptMedMonthCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptMedMonthCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mapVo.put("tableName", "BUDG_MED_COST_DY");
		String budgMedCost = budgMedCostService.queryMonth(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医疗支出预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostImportPage", method = RequestMethod.GET)
	public String budgMedCostImportPage(Model mode) throws Exception {

		return "hrp/budg/business/med/cost/budgMedCostImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医疗支出预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/med/cost/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\med","科室医疗支出预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医疗支出预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/med/cost/readBudgMedCostFiles",method = RequestMethod.POST)  
    public String readBudgMedCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedCost> list_err = new ArrayList<BudgMedCost>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthList = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
        
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode()); 
		
		//科室基本信息查询（根据code 匹配ID  用）
		List<Map<String,Object>> deptData = budgCommonInfoService.queryDeptData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedCost budgMedCost = new BudgMedCost();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])
								&& temp[4].equals(error[4])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						budgMedCost.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						budgMedCost.setMonth(temp[1]);
						
						if(Arrays.asList(monthList).contains(temp[1])){
							mapVo.put("month", temp[1]);
						}else{
							err_sb.append("月份不符合规则(01-12);");
						}
			    		
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgMedCost.setDept_code(temp[2]);
						budgMedCost.setDept_name(temp[3]);
						
						int flag = 0 ;
						
			    		for(Map<String,Object> item : deptData){
			    			
			    			if(temp[2].equals(item.get("dept_code"))){
			    				
			    				mapVo.put("dept_id", item.get("dept_id"));
			    				
			    				flag = 1 ;
			    				
			    				break ;
			    			}
			    			
			    		}
			    		if(flag == 0 ){
			    			
			    			err_sb.append("预算科室编码不存在或已停用,输入错误;");
			    		}
					
					} else {
						
						err_sb.append("预算科室编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						budgMedCost.setSubj_code(temp[4]);
						budgMedCost.setSubj_name(temp[5]);
						
						Map<String, Object> subjMapVo = new HashMap<String, Object>();
						
						subjMapVo.put("group_id", SessionManager.getGroupId());   
						 
						subjMapVo.put("hos_id", SessionManager.getHosId());   
						 
						subjMapVo.put("copy_code", SessionManager.getCopyCode());   
			    			
						subjMapVo.put("budg_year", temp[0]);
						
						subjMapVo.put("subj_code", temp[4]);
		    			
		    			int count = budgMedCostService.querySubjExist(subjMapVo);
		    			
		    			if(count > 0 ){
		    				
		    				mapVo.put("subj_code", temp[4]);
		    				
		    			}else{
		    				err_sb.append("该年度科目不存在 ");
		    			}
					
					} else {
						
						err_sb.append("科目编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgMedCost.setCost_budg(Double.valueOf(temp[6]));
			    		mapVo.put("cost_budg", temp[6]);
					
					} else {
						
						err_sb.append("支出预算为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp, 7)) {
					
						budgMedCost.setLast_month_carried(Double.valueOf(temp[7]));
						
			    		mapVo.put("last_month_carried", temp[7]);
					
					}else{
						budgMedCost.setLast_month_carried(null);
						
			    		mapVo.put("last_month_carried", "");
					}
					
					if (ExcelReader.validExceLColunm(temp, 8)) {
						
						budgMedCost.setCarried_next_month(Double.valueOf(temp[8]));
						
			    		mapVo.put("carried_next_month", temp[8]);
						
					}else{
						
						budgMedCost.setCarried_next_month(null);
						
			    		mapVo.put("carried_next_month", "");
					}
					 
					
				int count = budgMedCostService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCost.setError_type(err_sb.toString());
					
					list_err.add(budgMedCost);
					
				}else{
					
					addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String dataJson = budgMedCostService.addBatch(addList);
				}else{
					
					BudgMedCost data_exc = new BudgMedCost();
					
					data_exc.setError_type("没有数据，无法导入.");
					
					list_err.add(data_exc);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedCost data_exc = new BudgMedCost();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	/**
	 * @Description 
	 * 下载导入模版 医疗支出预算年度
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/med/cost/downTemplateYear", method = RequestMethod.GET)  
	 public String downTemplateYear(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\med","科室医疗支出预算年度.xls");
	    
	    return null; 
	 }
	 /**
		 * @Description 
		 * 导入跳转页面 医疗支出预算年度
		 * @param  mode
		 * @return String
		 * @throws Exception
		*/
		@RequestMapping(value = "/hrp/budg/business/med/cost/budgMedCostYearImportPage", method = RequestMethod.GET)
		public String budgMedCostYearImportPage(Model mode) throws Exception {

			return "hrp/budg/business/med/cost/budgMedCostYearImport";

		}
	 /**
		 * @Description 
		 * 导入数据 医疗支出预算年度
		 * @param  plupload
		 * @param  request
		 * @param  response
		 * @param  mode
		 * @return String
		 * @throws IOException
		*/
		@RequestMapping(value="/hrp/budg/business/med/cost/readBudgMedCostYearFiles",method = RequestMethod.POST)  
	    public String readBudgMedCostYearFiles(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
			
			List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> paraMap = new HashMap<String,Object>();
			
			paraMap.put("group_id", SessionManager.getGroupId());   
	        
			paraMap.put("hos_id", SessionManager.getHosId());   
			 
			paraMap.put("copy_code", SessionManager.getCopyCode()); 
			
			//科室基本信息查询（根据code 匹配ID  用）
			List<Map<String,Object>> deptData = budgCommonInfoService.queryDeptData(paraMap);
			
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					Map<String, Object> errMap = new HashMap<String, Object>();
					
					String temp[] = list.get(i);// 行
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
			    		mapVo.put("group_id", SessionManager.getGroupId());   
						 
			    		mapVo.put("hos_id", SessionManager.getHosId());   
						 
			    		mapVo.put("copy_code", SessionManager.getCopyCode());   
			    		         
			    		for(int j = i + 1 ; j < list.size(); j++){
							String error[] = list.get(j);
							if(temp[0].equals(error[0])&& temp[1].equals(error[1]) && temp[3].equals(error[3])){
								err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
							}
						}

						if (StringTool.isNotBlank(temp[0])) {
							
							errMap.put("budg_year",temp[0]);
				    		mapVo.put("budg_year", temp[0]);
						
						} else {
							
							err_sb.append("预算年度为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[1])) {
							
							errMap.put("dept_code",temp[1]);
							
				    		mapVo.put("dept_code", temp[1]);
				    		
							int flag = 0 ;
							
				    		for(Map<String,Object> item : deptData){
				    			
				    			if(temp[1].equals(item.get("dept_code"))){
				    				
				    				mapVo.put("dept_id", item.get("dept_id"));
				    				
				    				flag = 1 ;
				    				
				    				break ;
				    			}
				    			
				    		}
				    		if(flag == 0 ){
				    			
				    			err_sb.append("预算科室编码不存在或已停用,输入错误;");
				    		}
						
						} else {
							
							err_sb.append("预算科室编码为空;");
							
						}
						 
						if (StringTool.isNotBlank(temp[2])) {
							
							errMap.put("dept_name",temp[2]);
				    		mapVo.put("dept_name", temp[2]);
						
						} else {
							
							err_sb.append("科室名称为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[3])) {
							
							Map<String, Object> subjMapVo = new HashMap<String, Object>();
							
							subjMapVo.put("group_id", SessionManager.getGroupId());   
							 
							subjMapVo.put("hos_id", SessionManager.getHosId());   
							 
							subjMapVo.put("copy_code", SessionManager.getCopyCode());   
				    			
							subjMapVo.put("budg_year", temp[0]);
							
							subjMapVo.put("subj_code", temp[3]);
			    			
			    			int count = budgMedCostService.querySubjExist(subjMapVo);
			    			
			    			errMap.put("subj_code",temp[3]);
		    				mapVo.put("subj_code", temp[3]);
		    				
			    			if(count > 0 ){

			    			}else{
			    				err_sb.append("该年度预算科目不存在 或不是末级科目");
			    			}
						
						} else {
							
							err_sb.append("预算科目编码为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[4])) {
							
							errMap.put("subj_name",temp[4]);
				    		mapVo.put("subj_name", temp[4]);
						
						} else {
							
							err_sb.append("预算科目名称为空;");
							
						}
						
						if (StringTool.isNotBlank(temp[5])) {
							
							errMap.put("budg_value",temp[5]);
				    		mapVo.put("budg_value", temp[5]);
						
						} else {
							
							err_sb.append("支出预算为空;");
							
						}

						int count = budgMedCostService.queryImportDataExist(mapVo);
						
						if (count > 0 ) {
							
							err_sb.append("数据已经存在！ ");
							
						}
							
						 
					if (err_sb.toString().length() > 0) {
						
						errMap.put("error_type",err_sb.toString());
						
						list_err.add(errMap);
						
					} else {
						
					  addList.add(mapVo);
					}
				}
				
				if(list_err.size() == 0){
					
					if(addList.size() > 0 ){
						
						String dataJson = budgMedCostService.addBatchDy(addList);
					}else{
						
						Map<String, Object> errMap = new HashMap<String, Object>();	
						
						errMap.put("error_type","没有导入数据,请检查导入文件是否有数据");
						
						list_err.add(errMap);
						
					}
					
				}
			} catch (DataAccessException e) {
				
				Map<String, Object> errMap = new HashMap<String, Object>();	
				
				errMap.put("error_type","导入系统出错");
				
				list_err.add(errMap);
				
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
			
	    }  
	/**
	 * @Description 查询数据  状态
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>v
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/queryBudgCostMonthState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgExpenseApplyState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("tableName", "BUDG_MED_COST_DY");
		List<String> list= budgMedCostService.queryBudgMedCostState(mapVo);		
		if(list.size() == 0){
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}else{
			String  str = "" ;
			for(String item : list){
				str += item +"," ;
			}
			return JSONObject.parseObject("{\"state\":\"false\"}");
		}
	}
   /**
	 * @Description 
	 * 批量添加数据 医疗支出预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/med/cost/addBatchBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMedCost(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedCost> list_err = new ArrayList<BudgMedCost>();
		
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
			
			BudgMedCost budgMedCost = new BudgMedCost();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgMedCost.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedCost.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedCost.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedCost.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cost_budg"))) {
						
					budgMedCost.setCost_budg(Double.valueOf((String)jsonObj.get("cost_budg")));
		    		mapVo.put("cost_budg", jsonObj.get("cost_budg"));
		    		} else {
						
						err_sb.append("支出预算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgMedCost.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgMedCost.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgMedCost data_exc_extis = budgMedCostService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCost.setError_type(err_sb.toString());
					
					list_err.add(budgMedCost);
					
				} else {
			  
					String dataJson = budgMedCostService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedCost data_exc = new BudgMedCost();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	/**
	 * @Description 
	 * 生成  根据年度生成本年度支出预算数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/generateBudgMedCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgMedCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String budgMedCostJson = budgMedCostService.generateBudgMedCost(mapVo);
		
		return JSONObject.parseObject(budgMedCostJson);
		
	}
	/**
	 * 费用汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/collectMedMonthExpenses", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectMedMonthExpenses(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.collectMedMonthExpenses(mapVo) ;

		return JSONObject.parseObject(collect);
	}
	/**
	 * 费用汇总检测费用数据是否存在未确认的记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/queryYearDeptExpensesDataExistNoCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearDeptExpensesDataExistNoCheck(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.queryYearDeptExpensesDataExistNoCheck(mapVo) ;
		
		return JSONObject.parseObject(collect);
	}
	/**
	 * 费用汇总检测费用项目对应预算科目是否已生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/med/cost/queryYearDeptSubjDataExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryYearDeptSubjDataExist(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("budg_year", SessionManager.getCopyCode());
		String collect  = budgMedCostService.queryYearDeptSubjDataExist(mapVo) ;

		return JSONObject.parseObject(collect);
	}
	
}

