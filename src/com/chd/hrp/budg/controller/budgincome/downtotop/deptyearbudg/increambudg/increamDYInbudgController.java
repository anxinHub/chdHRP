/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.increambudg;
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
import com.chd.hrp.budg.entity.BudgDeptIndependentSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjService;
/**
 * 
 * @Description:
 * 科室年度医疗预算增量预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class increamDYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(increamDYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptIndependentSubjService")
	private final BudgDeptIndependentSubjService budgDeptIndependentSubjService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgPage", method = RequestMethod.GET)
	public String increamDYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgAddPage", method = RequestMethod.GET)
	public String increamDYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 增长比例获取 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/getGrowRatePage", method = RequestMethod.GET)
	public String getGrowRatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("year")));
		
		mode.addAttribute("year", year);
		mode.addAttribute("start_year", year-2);
		mode.addAttribute("end_year", year-1);
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/getGrowRate";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/saveIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIncreamDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("grow_rate", ids[6]);
			mapVo.put("grow_value", ids[7]);
			mapVo.put("last_year_income", ids[8]);
			
			mapVo.put("rowNo", ids[9]);
			
			mapVo.put("flag", ids[10]);
			
			mapVo.put("resolve_rate", "");
			mapVo.put("state", "");
			mapVo.put("reason", "");
			mapVo.put("refer_value", "");
			
			listVo.add(mapVo);
		}
       
		String budgDeptIndependentSubjJson = null;
		try {
			budgDeptIndependentSubjJson = budgDeptIndependentSubjService.save(listVo);
		} catch (Exception e) {
			budgDeptIndependentSubjJson = e.getMessage();
		}

		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/addIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncreamDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("grow_rate", ids[6]);
			mapVo.put("grow_value", ids[7]);
			mapVo.put("last_year_income", ids[8]);
			mapVo.put("hos_suggest", ids[9]);
			mapVo.put("hos_suggest_resolve", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("dept_suggest_sum", "");
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[10]);
				mapVo.put("dept_suggest", ids[10]);
			}
			
			mapVo.put("resolve_rate", "");
			listVo.add(mapVo);
		}
		
       
		String budgHosIndependentSubjJson = budgDeptIndependentSubjService.addBatch(listVo);

		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgUpdatePage", method = RequestMethod.GET)
	public String increamDYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
    
		budgDeptIndependentSubj = budgDeptIndependentSubjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptIndependentSubj.getGroup_id());
		mode.addAttribute("hos_id", budgDeptIndependentSubj.getHos_id());
		mode.addAttribute("copy_code", budgDeptIndependentSubj.getCopy_code());
		mode.addAttribute("year", budgDeptIndependentSubj.getYear());
		mode.addAttribute("subj_code", budgDeptIndependentSubj.getSubj_code());
		mode.addAttribute("dept_id", budgDeptIndependentSubj.getDept_id());
		mode.addAttribute("count_value", budgDeptIndependentSubj.getCount_value());
		mode.addAttribute("budg_value", budgDeptIndependentSubj.getBudg_value());
		mode.addAttribute("remark", budgDeptIndependentSubj.getRemark());
		mode.addAttribute("grow_rate", budgDeptIndependentSubj.getGrow_rate());
		mode.addAttribute("grow_value", budgDeptIndependentSubj.getGrow_value());
		mode.addAttribute("last_year_income", budgDeptIndependentSubj.getLast_year_income());
		mode.addAttribute("hos_suggest", budgDeptIndependentSubj.getHos_suggest());
		mode.addAttribute("dept_suggest_sum", budgDeptIndependentSubj.getDept_suggest_sum());
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/updateIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncreamDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("remark", ids[5]);
			mapVo.put("grow_rate", ids[6]);
			mapVo.put("grow_value", ids[7]);
			mapVo.put("last_year_income", ids[8]);
			mapVo.put("hos_suggest", ids[9]);
			mapVo.put("hos_suggest_resolve", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("dept_suggest_sum", "");
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[10]);
				mapVo.put("dept_suggest", ids[10]);
			}
			
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/addOrUpdateIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateIncreamDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptIndependentSubjJson ="";
		

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
	  
		budgDeptIndependentSubjJson = budgDeptIndependentSubjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/deleteIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncreamDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				mapVo.put("dept_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/queryIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncreamDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		/*if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}*/
		String budgDeptIndependentSubj = budgDeptIndependentSubjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室年度医疗预算增量预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgImportPage", method = RequestMethod.GET)
	public String increamDYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/increamDYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度医疗预算增量预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室年度医疗预算增量预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度医疗预算增量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/readIncreamDYInBudgFiles",method = RequestMethod.POST)  
    public String readIncreamDYInbudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDeptIndependentSubj> list_err = new ArrayList<BudgDeptIndependentSubj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
					
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						budgDeptIndependentSubj.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgDeptIndependentSubj.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		mapVo.put("edit_method","02");
			    		
			    		int index=budgDeptIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
						if(index == 0 ){
							err_sb.append("该年度收入预算科目编码的编制方法不是增量预算或数据不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						mapVo.put("dept_code", temp[2]);
						List<Map<String, Object>> dept=budgDeptIndependentSubjService.queryDeptId(mapVo);
						for (Map<String, Object> map : dept) {
				
								budgDeptIndependentSubj.setDept_id(Long.valueOf(map.get("id").toString()));
					    		mapVo.put("dept_id", map.get("id"));
							
						}
						/*budgDeptIndependentSubj.setDept_id(Long.valueOf(temp[2]));
			    		mapVo.put("dept_id", temp[2]);*/
						
						if(mapVo.get("dept_id")==null){
							err_sb.append("请检查部门是否为预算科室;");
						}
						
					} else {
						
						err_sb.append("部门ID为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 3)){
						budgDeptIndependentSubj.setLast_year_income(Double.valueOf(temp[3]));
						mapVo.put("last_year_income", temp[3]);
					}else{
						err_sb.append("上年收入为空;");
					}
					
					
					if(!ExcelReader.validExceLColunm(temp,4) && !ExcelReader.validExceLColunm(temp,5)){
						
						err_sb.append("增长比例和增长额两项不能同时为空;");
						
					}else if (ExcelReader.validExceLColunm(temp,4) && ExcelReader.validExceLColunm(temp,5)){
						err_sb.append("增长比例和增长额两项只能填写其中一项;");
					}
					
					
					//根据数据编码查询取值方法并判断
					String getway=budgDeptIndependentSubjService.queryGetWayFromBudgMedIncomeEditPlan(mapVo);
					if(getway!=null&&getway!=""){
						//04 增长比例  05 增长额
						if("04".equals(getway)){
							if(ExcelReader.validExceLColunm(temp, 4)){
								budgDeptIndependentSubj.setGrow_rate(Double.valueOf(temp[4]));
								
								mapVo.put("grow_rate", temp[4]);
								if(ExcelReader.validExceLColunm(temp, 5)){
									budgDeptIndependentSubj.setGrow_value(Double.valueOf(temp[5]));
									err_sb.append("该指标取值方法不是历史数据+增长额, 增长额不得填写;");
								}else{
									budgDeptIndependentSubj.setGrow_value(null);
									mapVo.put("grow_value", "");
								}
								
							}else{
								err_sb.append("该指标取值方法为历史数据*增长比例,增长比例不得为空;");
							}
						}
							
						if("05".equals(getway)){
							if(ExcelReader.validExceLColunm(temp, 4)){
								
								budgDeptIndependentSubj.setGrow_rate(Double.valueOf(temp[4]));
								err_sb.append("该指标取值方法不是历史数据*增长比例,增长比例不得填写;");
							}else{
								budgDeptIndependentSubj.setGrow_rate(null);
								mapVo.put("grow_rate", "");
							}
							if(ExcelReader.validExceLColunm(temp, 5)){
								budgDeptIndependentSubj.setGrow_value(Double.valueOf(temp[5]));
								mapVo.put("grow_rate", "");
								mapVo.put("grow_value", temp[5]);
								
							}else{
								err_sb.append("该指标取值方法为历史数据+增长额,增长额不得为空;");
							}
						}		
							
					}else{
						//如果取值方法为空   则页面显示数据完整信息   并添加错误说明
						if(ExcelReader.validExceLColunm(temp, 4)){
							budgDeptIndependentSubj.setGrow_rate(Double.valueOf(temp[4]));
						}else{
							budgDeptIndependentSubj.setGrow_rate(null);
						}
						
						if(ExcelReader.validExceLColunm(temp, 5)){
							budgDeptIndependentSubj.setGrow_value(Double.valueOf(temp[5]));
						}else{
							budgDeptIndependentSubj.setGrow_value(null);
						}
						
						err_sb.append("该指标填写年度编制方案没有维护或编制方案中编制方法不是增量预算;");
					}
					
					
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgDeptIndependentSubj.setBudg_value(Double.valueOf(temp[6]));
			    		mapVo.put("budg_value", temp[6]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					
					
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						budgDeptIndependentSubj.setRemark(temp[7]);
			    		mapVo.put("remark", temp[7]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,8)) {
						
						budgDeptIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[8]));
			    		mapVo.put("hos_suggest", temp[8]);
			    		mapVo.put("hos_suggest_resolve", temp[8]);
					}else{
						mapVo.put("hos_suggest", "");
						mapVo.put("hos_suggest_resolve", "");
					}
				BudgDeptIndependentSubj data_exc_extis = budgDeptIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgDeptIndependentSubj);
					
				} else {
					//空值处理
					mapVo.put("count_value", "");
					mapVo.put("state", "");
					mapVo.put("reason", "");
					mapVo.put("refer_value", "");
					mapVo.put("resolve_rate", "");
					addList.add(mapVo);
					
				}
				
			}
			
			//错误信息为空   数据正常添加
			if(list_err.size() == 0 ){
				
				String dataJson = budgDeptIndependentSubjService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgDeptIndependentSubj data_exc = new BudgDeptIndependentSubj();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室年度医疗预算增量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/addBatchIncreamDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchIncreamDYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptIndependentSubj> list_err = new ArrayList<BudgDeptIndependentSubj>();
		
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
			
			BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (StringTool.isNotBlank(jsonObj.get("year"))) {
				
			budgDeptIndependentSubj.setYear((String)jsonObj.get("year"));
    		mapVo.put("year", jsonObj.get("year"));
    		} else {
				
				err_sb.append("年度为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
				
			budgDeptIndependentSubj.setSubj_code((String)jsonObj.get("subj_code"));
    		mapVo.put("subj_code", jsonObj.get("subj_code"));
    		} else {
				
				err_sb.append("科目编码为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
				
			budgDeptIndependentSubj.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
    		mapVo.put("count_value", jsonObj.get("count_value"));
    		} else {
				
				err_sb.append("计算值为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
				
			budgDeptIndependentSubj.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
    		mapVo.put("budg_value", jsonObj.get("budg_value"));
    		} else {
				
				err_sb.append("预算值为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("remark"))) {
				
			budgDeptIndependentSubj.setRemark((String)jsonObj.get("remark"));
    		mapVo.put("remark", jsonObj.get("remark"));
    		} else {
				
				err_sb.append("说明为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
				
			budgDeptIndependentSubj.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
    		} else {
				
				err_sb.append("增长比例为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
				
			budgDeptIndependentSubj.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
    		mapVo.put("grow_value", jsonObj.get("grow_value"));
    		} else {
				
				err_sb.append("增加额为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
				
			budgDeptIndependentSubj.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
    		} else {
				
				err_sb.append("上年收入为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
				
			budgDeptIndependentSubj.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
    		mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
    		} else {
				
				err_sb.append("医院意见为空  ");
				
			}
			
			if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
				
			budgDeptIndependentSubj.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
    		mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
    		} else {
				
				err_sb.append("科室意见汇总为空  ");
				
			}
			
			mapVo.put("state", "");
			mapVo.put("reason", "");
			mapVo.put("refer_value", "");
			
				BudgDeptIndependentSubj data_exc_extis = budgDeptIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgDeptIndependentSubj);
					
				} else {
			  
					String dataJson = budgDeptIndependentSubjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptIndependentSubj data_exc = new BudgDeptIndependentSubj();
			
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
	 * 根据科目查询上年收入
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/queryLastYearIncome", method = RequestMethod.POST)
	@ResponseBody
	public String queryLastYearIncome(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("edit_method", "02") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgDeptIndependentSubjService.queryLastYearIncome(mapVo) ;

		return str;

	}
	/**
	 * 查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/queryBudgDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String dept = budgDeptIndependentSubjService.queryBudgDept(mapVo);
		return dept;

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "02");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算
			// 查询要生成的数据
			List<Map<String,Object>> list = budgDeptIndependentSubjService.queryData(mapVo);
			
			//查询要生成的部门
			List<Map<String,Object>>  deptList = budgDeptIndependentSubjService.queryDataDeptList(mapVo);
			if(list.size() == 0){
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
			for (Map<String, Object> map : deptList) {
				if(map.get("dept_id") != null){
			for(Map<String, Object> item : list ){
				Map<String, Object>  item1 = new HashMap<String, Object>();
				item1.put("count_value", "");
				item1.put("budg_value", "");
				item1.put("remark", "");
				item1.put("grow_rate", "");
				item1.put("resolve_rate", "");
				item1.put("grow_value", "");
				item1.put("dept_id", map.get("dept_id"));
				item1.put("state", "");
				item1.put("reason", "");
				item1.put("refer_value", "");
				item1.put("copy_code",item.get("copy_code"));
				item1.put("year",item.get("year"));
				item1.put("hos_id",item.get("hos_id"));
				item1.put("group_id",item.get("group_id"));
				item1.put("subj_code",item.get("subj_code"));
				item1.put("last_year_income",item.get("last_year_income"));
						
				        listVo.add(item1); 
					}
				}
			
				
			}
			
			String budgWorkHosYearJson = null ;
				
			if(listVo.size() > 0 ){
				
				budgWorkHosYearJson = budgDeptIndependentSubjService.addBatch(listVo) ;
				if("true".equals(JSONObject.parseObject(budgWorkHosYearJson).get("state"))){
					budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
				}
				
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"历史执行数据未维护或数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 	
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/getGrowRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGrowRate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = budgDeptIndependentSubjService.getGrowRate(mapVo) ;

		return JSONObject.parseObject(str);

	}
	/**
	 * @Description 
	 * 科室年度医疗收入预算增量预算  更新 增长比例 及相关数据数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/increambudg/updateGrowRate", method = RequestMethod.POST)
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
			mapVo.put("subj_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("grow_rate", ids[3]);
			
			listVo.add(mapVo);
		}
	  
		String budgWorkHosYearJson = null ;
		try {
			budgWorkHosYearJson = budgDeptIndependentSubjService.updateGrowRate(listVo);
		} catch (Exception e) {
			budgWorkHosYearJson = e.getMessage() ;
		}
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
}

