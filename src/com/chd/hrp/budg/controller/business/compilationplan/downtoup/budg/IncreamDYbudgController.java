/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.budg;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgIndexDict;
import com.chd.hrp.budg.entity.BudgWorkDeptYear;
import com.chd.hrp.budg.entity.BudgWorkHosYear;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.base.budgindexdict.BudgIndexDictService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
/**
 * 
 * @Description:
 * 科室年度业务预算零基预算 
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class IncreamDYbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(IncreamDYbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearService")
	private final BudgWorkDeptYearService budgWorkDeptYearService = null;
	
	@Resource(name = "budgSelectService")
	private final BudgSelectService budgSelectService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgMainPage", method = RequestMethod.GET)
	public String increamDYBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgAddPage", method = RequestMethod.GET)
	public String increamDYBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgAdd";

	}
	
	/**
	 * @Description 
	 * 增长比例获取 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/getGrowRatePage", method = RequestMethod.GET)
	public String getGrowRatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("year")));
		
		mode.addAttribute("year", year);
		mode.addAttribute("start_year", year-2);
		mode.addAttribute("end_year", year-1);
		
		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/getGrowRate";

	}

	/**
	 * @Description 
	 * 添加数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/addIncreamDYBudgDownNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncreamDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("resolve_rate", "");
		
		mapVo.put("state","");
		mapVo.put("reason","");
		mapVo.put("refer_value","");
			
		
		String budgWorkDeptYearJson = null;
		
		try {
			
			budgWorkDeptYearJson = budgWorkDeptYearService.add(mapVo);
			
		} catch (Exception e) {
			
			budgWorkDeptYearJson = e.getMessage();
		}
				

		return JSONObject.parseObject(budgWorkDeptYearJson);
		
	}
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/saveIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIncreamDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("count_value", ids[6]);
			mapVo.put("budg_value", ids[7]);
			mapVo.put("remark", ids[8]);
			
			mapVo.put("rowNo", ids[9]) ;
			mapVo.put("flag", ids[10]) ;
			//空字段
			mapVo.put("state","");
			mapVo.put("reason","");
			mapVo.put("refer_value","");
			
			mapVo.put("resolve_rate", "");
			listVo.add(mapVo);
		}
		
       
		String budgWorkDeptYearJson = null ;
		
		try {
			
			budgWorkDeptYearJson = budgWorkDeptYearService.save(listVo);

		} catch (Exception e) {
			
			budgWorkDeptYearJson = e.getMessage() ;
		}
		
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgUpdatePage", method = RequestMethod.GET)
	public String increamDYBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("year") == null){
        mapVo.put("year", SessionManager.getAcctYear());
		}
		
		BudgWorkDeptYear budgWorkDeptYear = budgWorkDeptYearService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptYear.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptYear.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptYear.getCopy_code());
		mode.addAttribute("year", budgWorkDeptYear.getYear());
		mode.addAttribute("dept_id", budgWorkDeptYear.getDept_id());
		mode.addAttribute("index_code", budgWorkDeptYear.getIndex_code());
		mode.addAttribute("count_value", budgWorkDeptYear.getCount_value());
		mode.addAttribute("budg_value", budgWorkDeptYear.getBudg_value());
		mode.addAttribute("remark", budgWorkDeptYear.getRemark());
		mode.addAttribute("grow_rate", budgWorkDeptYear.getGrow_rate());
		mode.addAttribute("grow_value", budgWorkDeptYear.getGrow_value());
		mode.addAttribute("last_year_workload", budgWorkDeptYear.getLast_year_workload());
		mode.addAttribute("hos_suggest_resolve", budgWorkDeptYear.getHos_suggest_resolve());
		mode.addAttribute("dept_suggest", budgWorkDeptYear.getDept_suggest());
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/IncreamDYBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/updateIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncreamDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("count_value", ids[7]);
			
			mapVo.put("remark", ids[8]);
			if("-1".equals(ids[9])){
				mapVo.put("hos_suggest_resolve", "");
			}else{
				mapVo.put("hos_suggest_resolve", ids[9]);
			}
			
			mapVo.put("resolve_rate", "");
			mapVo.put("dept_suggest", "");
			
			listVo.add(mapVo);
		}
	  
		String budgWorkDeptYearJson = budgWorkDeptYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/addOrUpdateIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateIncreamDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptYearJson ="";
		

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
	  
		budgWorkDeptYearJson = budgWorkDeptYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/deleteIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncreamDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4]);
				mapVo.put("index_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/queryIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncreamDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgHosIndependentSubj = budgWorkDeptYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度业务预算零基预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgImportPage", method = RequestMethod.GET)
	public String increamDYBudgImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/increamDYBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度业务预算零基预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\downtotop","科室年度业务预算增量预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度业务预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/readIncreamDYBudgFiles",method = RequestMethod.POST)  
    public String readIncreamDYBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year",temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						errMap.put("index_code",temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("budg_level", "03");
			    		
			    		mapVo.put("edit_method", "02");
			    		
			    		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
						if(index.length() == 2){
							err_sb.append("该指标编码在所填预算年度业务预算编制方案中编制方法不是增量预算;");
						}
					
					} else {
						
						err_sb.append("指标编码为空 ;");
						
					}
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						errMap.put("dept_code",temp[2]);
						
						mapVo.put("dept_code",temp[2]);
						//根据 指标编码、 科室编码 查询  预算指标对应科室 
						Map<String,Object> dept = budgWorkDeptYearService.queryIndexDeptSet(mapVo);
						
						if(dept == null  ){
							
							err_sb.append("指标编码与科室不存在对应关系;");
							
						}else{
							
							mapVo.put("dept_id", dept.get("dept_id"));
						}
						
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						errMap.put("last_year_workload",temp[3]);
			    		mapVo.put("last_year_workload", temp[3]);
						
					} else {
						
						err_sb.append("上年业务量为空;");
						
					}
					
					if(!ExcelReader.validExceLColunm(temp,4) && !ExcelReader.validExceLColunm(temp,5)){
						
						err_sb.append("增长比例和增长额两项不能同时为空;");
						
					}else if (ExcelReader.validExceLColunm(temp,4) && ExcelReader.validExceLColunm(temp,5)){
						err_sb.append("增长比例和增长额两项只能填写其中一项;");
					}
					
					Map<String, Object> map = budgWorkDeptYearService.queryGetWay(mapVo);
					if(map != null){
						if("04".equals(map.get("get_way"))){
							if (ExcelReader.validExceLColunm(temp,4)) {
								
								errMap.put("grow_rate",Double.parseDouble(temp[4]));
					    		mapVo.put("grow_rate", temp[4]);
					    		
					    		mapVo.put("count_value", Double.parseDouble(temp[3])*(1 + Double.parseDouble(temp[4])/100));
					    		
					    		mapVo.put("budg_value", mapVo.get("count_value"));
					    		
					    		if(ExcelReader.validExceLColunm(temp,5)){
					    			
					    			errMap.put("grow_value",temp[5]);
					    			
					    			err_sb.append("该指标取值方法是历史数据*增长比例,增长额不能填写;");
					    			
					    		}else{
					    			
					    			errMap.put("grow_value","");
					    			mapVo.put("grow_value", "");
					    		}
							
							}else{
								err_sb.append("该指标取值方法为历史数据*增长比例,增长比例不得为空;");
							}
						}
						
						if("05".equals(map.get("get_way"))){
							
							if (ExcelReader.validExceLColunm(temp,4)) {
								
								errMap.put("grow_value",temp[4]);
								
								err_sb.append("该指标取值方法为历史数据+增长额,增长比例不能填写;");
								
							}else{
								errMap.put("grow_rate","");
				    			mapVo.put("grow_rate", "");
							}
							
							if (ExcelReader.validExceLColunm(temp,5)){
								
								errMap.put("grow_value",Double.parseDouble(temp[5]));
								
								mapVo.put("grow_value", temp[5]);
								
								mapVo.put("count_value", Double.parseDouble(temp[3])+ Double.parseDouble(temp[5]));
								
								mapVo.put("budg_value", mapVo.get("count_value"));
								
							}else{
								err_sb.append("该指标取值方法为历史数据+增长额,增长额不得为空;");
							}
						}
					}else{
						
						if (ExcelReader.validExceLColunm(temp,4)) {
							
							errMap.put("grow_rate",Double.parseDouble(temp[4]));
				    		mapVo.put("grow_rate", temp[4]);
						}else{
							
							errMap.put("grow_rate","");
			    			mapVo.put("grow_rate", "");
						}
						
						if (ExcelReader.validExceLColunm(temp,5)){
							
							errMap.put("grow_value",Double.parseDouble(temp[5]));
							
							mapVo.put("grow_value", temp[5]);
							
						}else{
							
							errMap.put("grow_value","");
			    			mapVo.put("grow_value", "");
						}
						
						err_sb.append("该指标填写年度编制方案没有维护或编制方案中编制方法不是增量预算;");
					}
					
					
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						errMap.put("remark",temp[6]);
			    		mapVo.put("remark", temp[6]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					/*if (ExcelReader.validExceLColunm(temp,7)) {
						
						errMap.put("hos_suggest_resolve",Double.valueOf(temp[7]));
			    		mapVo.put("hos_suggest_resolve", temp[7]);
					
					}else{
						mapVo.put("hos_suggest_resolve", "");
					}*/
				 
					
				/*		BudgWorkHosYear data_exc_extis = budgWorkDeptYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				*/
				int count = budgWorkDeptYearService.queryDataExist(mapVo);

				if (count >0) {
					
					err_sb.append("数据已经存在！ ");
					
				}
					
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					mapVo.put("resolve_rate", "");
					
					mapVo.put("state","");
					mapVo.put("reason","");
					mapVo.put("refer_value","");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgWorkDeptYearService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院年度业务预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/addBatchIncreamDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchIncreamDYBudgDown(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosYear> list_err = new ArrayList<BudgWorkHosYear>();
		
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
			
			BudgWorkHosYear budgWorkHosYear = new BudgWorkHosYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
						budgWorkHosYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
						budgWorkHosYear.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
						budgWorkHosYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
						budgWorkHosYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
						mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
						budgWorkHosYear.setRemark((String)jsonObj.get("remark"));
						mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
						budgWorkHosYear.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
						mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
						
						budgWorkHosYear.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
						mapVo.put("grow_value", jsonObj.get("grow_value"));
		    		} else {
						
						err_sb.append("增加额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_workload"))) {
						
						budgWorkHosYear.setLast_year_workload(Double.valueOf((String)jsonObj.get("last_year_workload")));
						mapVo.put("last_year_workload", jsonObj.get("last_year_workload"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
						budgWorkHosYear.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
						mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
		    		} else {
						
						err_sb.append("医院意见为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
						
						budgWorkHosYear.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
						mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
		    		} else {
						
						err_sb.append("科室意见汇总为空  ");
						
					}
					
					
					BudgWorkHosYear data_exc_extis = budgWorkDeptYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYear);
					
				} else {
			  
					String dataJson = budgWorkDeptYearService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYear data_exc = new BudgWorkHosYear();
			
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
	 * 查询 所传 指标的 上年业务量
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/queryDeptYearLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("budg_level", "03");
		
		mapVo.put("edit_method", "02");
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))  - 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgWorkDeptYearService.queryDeptYearLastYearWork(mapVo) ;

		return str;

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

			mapVo.put("edit_method", "02");// 编制方法（EDIT_METHOD）01零基预算  02增量预算 	03确定预算  04概率预算

			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkDeptYearService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【科室年度业务预算编制方案】数据.\"}");
			}
			
			
			if(list.size() == 0){
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【科室年度业务预算编制方案】数据.\"}");
			}
			
			for(Map<String, Object> item : list ){
				
				if(item.get("dept_id") != null ){//指标 存在 对应科室关系时  生成相应数据  否则不生成
					//根据主键  查询数据是否存在
					int count  = budgWorkDeptYearService.queryDataExist(item);
					
					//不存在  组装 生成数据
					if(count == 0){
						
						//构建 查询上年业务量  参数 Map
						Map<String,Object> paraMap =  new HashMap<String,Object>();
						
						paraMap.put("group_id", SessionManager.getGroupId())   ;
						paraMap.put("hos_id", SessionManager.getHosId())   ;
						paraMap.put("copy_code", SessionManager.getCopyCode())   ;
						paraMap.put("budg_year", item.get("year"));
						paraMap.put("index_code", item.get("index_code"));
						paraMap.put("dept_id", item.get("dept_id"));
						paraMap.put("dept_code", item.get("dept_code"));
						paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
						
						mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

						
						//查询上年业务量
						Map<String,Object> map = JSONObject.parseObject(budgWorkDeptYearService.queryDeptYearLastYearWork(paraMap));
						
						if(map == null){
							
							item.put("last_year_workload", "");
							
						}else{
							item.put("last_year_workload", map.get("last_year_workload"));
						}
						
						item.put("count_value", "");
						item.put("budg_value", "");
						item.put("remark", "");
						item.put("grow_rate", "");
						item.put("grow_value", "");
						item.put("resolve_rate", "");
						item.put("state", "");
						item.put("reason", "");
						item.put("refer_value", "");
				        listVo.add(item); 
						
					}
				}
				
			}
			
			String budgWorkDeptYearJson = null ;
			
				
			if(listVo.size() > 0 ){
				
				budgWorkDeptYearJson =budgWorkDeptYearService.addBatch(listVo);
		         
			}else{
				budgWorkDeptYearJson = "{\"error\":\"指标对应科室数据未维护或数据已全部生成,无法生成.\"}";
			}
				
			return JSONObject.parseObject(budgWorkDeptYearJson);	
	}
	
	/**
	 *  预算指标下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/queryBudgIndex", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String index = budgWorkDeptYearService.queryBudgIndex(mapVo);
		return index;

	}
	
	/**
	 *  根据指标 关联查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/queryBudgIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String index = budgWorkDeptYearService.queryBudgIndexDeptSet(mapVo);
		return index;

	}
	

	/**
	 * 增长比例获取
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/getGrowRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGrowRate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = budgWorkDeptYearService.getGrowRate(mapVo) ;

		return JSONObject.parseObject(str);

	}
	
	/**
	 * @Description 
	 * 医院年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/incream/updateGrowRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGrowRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("grow_rate", ids[3]);
			
			listVo.add(mapVo);
		}
	  
		String budgWorkHosYearJson = budgWorkDeptYearService.updateGrowRate(listVo);
		
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
}

