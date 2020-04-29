/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.plan;
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
import com.chd.hrp.budg.entity.BudgMedIncomeEditPlan;
import com.chd.hrp.budg.service.budgincome.downtotop.medinbudgeditplan.BudgMedIncomeEditPlanService;

/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptYearEditPlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptYearEditPlanController.class);
	
	//引入Service服务
	@Resource(name = "budgMedIncomeEditPlanService")
	private final BudgMedIncomeEditPlanService budgMedIncomeEditPlanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/deptYearEditPlanPage", method = RequestMethod.GET)
	public String budgDeptYearEditPlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanAddPage", method = RequestMethod.GET)
	public String budgDeptYearEditPlanAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanAdd";

	}

	/**
	 * @Description 
	 * 添加数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/addBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptYearEditPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		String budgDeptYearEditPlanJson = null;
		
		try {
			
			budgDeptYearEditPlanJson = budgMedIncomeEditPlanService.add(mapVo);
			
		} catch (Exception e) {

			budgDeptYearEditPlanJson = e.getMessage();
			
		}
		

		return JSONObject.parseObject(budgDeptYearEditPlanJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanUpdatePage", method = RequestMethod.GET)
	public String budgDeptYearEditPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
			
		}
		
		BudgMedIncomeEditPlan budgDeptYearEditPlan = new BudgMedIncomeEditPlan();
    
		budgDeptYearEditPlan = budgMedIncomeEditPlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptYearEditPlan.getGroup_id());
		mode.addAttribute("hos_id", budgDeptYearEditPlan.getHos_id());
		mode.addAttribute("copy_code", budgDeptYearEditPlan.getCopy_code());
		mode.addAttribute("budg_year", budgDeptYearEditPlan.getBudg_year());
		mode.addAttribute("subj_code", budgDeptYearEditPlan.getSubj_code());
		mode.addAttribute("is_single_count", budgDeptYearEditPlan.getIs_single_count());
		mode.addAttribute("edit_method", budgDeptYearEditPlan.getEdit_method());
		mode.addAttribute("get_way", budgDeptYearEditPlan.getGet_way());
		mode.addAttribute("formula_id", budgDeptYearEditPlan.getFormula_id());
		mode.addAttribute("fun_id", budgDeptYearEditPlan.getFun_id());
		mode.addAttribute("resolve_or_sum", budgDeptYearEditPlan.getResolve_or_sum());
		mode.addAttribute("resolve_way", budgDeptYearEditPlan.getResolve_way());
		
		return "hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/updateBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptYearEditPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
       
		String budgDeptYearEditPlanJson = null;
		
		try {
			
			budgDeptYearEditPlanJson = budgMedIncomeEditPlanService.update(mapVo);
			
		} catch (Exception e) {

			budgDeptYearEditPlanJson = e.getMessage() ;
			
		}
		
		
		return JSONObject.parseObject(budgDeptYearEditPlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/addOrUpdateBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptYearEditPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptYearEditPlanJson ="";
		

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
	  
		budgDeptYearEditPlanJson = budgMedIncomeEditPlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptYearEditPlanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/deleteBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptYearEditPlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptYearEditPlanJson = null ;
		
		try {
			
			budgDeptYearEditPlanJson = budgMedIncomeEditPlanService.deleteBatch(listVo);
			
		} catch (Exception e) {

			budgDeptYearEditPlanJson = e.getMessage() ;
			
		}
		
			
	  return JSONObject.parseObject(budgDeptYearEditPlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/queryBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptYearEditPlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgDeptYearEditPlan = budgMedIncomeEditPlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptYearEditPlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanImportPage", method = RequestMethod.GET)
	public String budgDeptYearEditPlanImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptyearplan/budgDeptYearEditPlanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/plan/deptyearplan/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室年度医疗收入预算编制方案.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/plan/deptyearplan/readBudgDeptYearEditPlanFiles",method = RequestMethod.POST)  
    public String readBudgDeptYearEditPlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeEditPlan> list_err = new ArrayList<BudgMedIncomeEditPlan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				//错误信息 字符串  用于拼接错误信息
				StringBuffer err_sb = new StringBuffer();
				
				//创建对象  封装属性数据
				BudgMedIncomeEditPlan budgDeptYearEditPlan = new BudgMedIncomeEditPlan();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    	
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		//确保数据不重复  否则添加错误信息
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						budgDeptYearEditPlan.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						budgDeptYearEditPlan.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		//根据科目编码查询改科目是否已经存在
			    		Integer index = budgMedIncomeEditPlanService.queryBudgMedIncomeEditPlanByCode(mapVo);
			    		
			    		if(index > 0 ){
							err_sb.append("该年度收入预算科目编码已经存在,请勿重复添加!;");
						}
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					//首先确认编制方法和分解或汇总二选一的关系   有且只有一个有值   
					if(!ExcelReader.validExceLColunm(temp,3) && !ExcelReader.validExceLColunm(temp,7)){
						
						err_sb.append("编制方法和分解或汇总两项不能同时为空;");
						
					}else if (ExcelReader.validExceLColunm(temp,3) && ExcelReader.validExceLColunm(temp,7)){
						err_sb.append("编制方法和分解或汇总两项只能填写其中一项;");
					}
					
					//根据是否独立核算确定  该科目是走编制方法  还是 分解或汇总
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgDeptYearEditPlan.setIs_single_count(Integer.valueOf(temp[2]));
			    		mapVo.put("is_single_count", temp[2]);
			    		
			    		if("1".equals(temp[2])){ //0  否 , 1  是
			    			//1 代表 是     则走编制方法    编制方法和取值方法 不得为空 
			    			if (ExcelReader.validExceLColunm(temp,3)) {
								
								budgDeptYearEditPlan.setEdit_method(temp[3]);
					    		mapVo.put("edit_method", temp[3]);
					    		
					    		//判断取值方法  编制方法不为空  则取值方法必填
					    		if (ExcelReader.validExceLColunm(temp,4)) {
									
									budgDeptYearEditPlan.setGet_way(temp[4]);
						    		mapVo.put("get_way", temp[4]);
						    		
						    		//如果取值方法编码为02  则取值方法为取值函数  取值函数ID不得为空
 						    		if("02".equals(temp[4])){
						    			if (ExcelReader.validExceLColunm(temp,6)) {
						    				
											budgDeptYearEditPlan.setFun_id(temp[6]);
								    		mapVo.put("fun_id", temp[6]);
								    		
								    		//计算公式不得填写
								    		if (ExcelReader.validExceLColunm(temp,5)) {
												budgDeptYearEditPlan.setFormula_id(temp[5]);
												err_sb.append("该科目取值方法为取值函数,计算公式ID不得填写!");
											} else {
												mapVo.put("formula_id", "");
											}
										} else {
											err_sb.append("该科目取值方法为取值函数,取值函数ID不得为空!");
										}
						    		} //如果 取值函数编码为 03 则取值方法为计算公式  计算公式不得为空
 						    		else if("03".equals(temp[4])){
						    			if (ExcelReader.validExceLColunm(temp,5)) {
											
											budgDeptYearEditPlan.setFormula_id(temp[5]);
								    		mapVo.put("formula_id", temp[5]);
								    		
								    		//取值函数不得填写
								    		if (ExcelReader.validExceLColunm(temp,6)) {
							    				
												budgDeptYearEditPlan.setFun_id(temp[6]);
												err_sb.append("该科目取值方法为计算公式,取值函数ID不得填写!");
								    		}else {
								    			mapVo.put("fun_id", "");
											}	
										} else {
											err_sb.append("该科目取值方法为计算公式,计算公式ID不得为空!");
										}
						    		} else {//如果 取值方法既不是取值函数  也不是计算公式  则取值函数 计算公式 都应该为空
						    			if (ExcelReader.validExceLColunm(temp,5)) {
											
											budgDeptYearEditPlan.setFormula_id(temp[5]);
											err_sb.append("该科目取值方法不是计算公式,计算公式不得填写! ");
										} else {
											mapVo.put("formula_id", "");
										}
										if (ExcelReader.validExceLColunm(temp,6)) {
											
											budgDeptYearEditPlan.setFun_id(temp[6]);
											err_sb.append("该科目取值方法不是取值函数,取值函数不得填写!  ");
										} else {
											mapVo.put("fun_id", "");
										}
						    		}
 						    		//走编制方法    则分解或汇总 及 分解方法为空  否则添加错误信息
 						    		if (ExcelReader.validExceLColunm(temp,7)) {
										
										budgDeptYearEditPlan.setResolve_or_sum(temp[7]);
							    		err_sb.append("该科目为独立核算项,取编制方法,分解或汇总不得填写!!");
									} else {
										mapVo.put("resolve_or_sum","");
									}
									 
									if (ExcelReader.validExceLColunm(temp,8)) {
										
										budgDeptYearEditPlan.setResolve_way(temp[8]);
										err_sb.append("该科目为独立核算项,取编制方法,分解方法不得填写!! ");
									} else {
										mapVo.put("resolve_way", "");
									}
								} else {
									err_sb.append("该科目为独立核算项,编制方法不为空,则取值方法不得为空!");
								}
							} else {
								err_sb.append("该科目为独立核算项,编制方法不得为空!");
							}
			    		}//否则  当是否独立核算为0时  代表选择分解或汇总  则分解或者汇总不得为空  
			    		else if("0".equals(temp[2])){
			    			
			    			if (ExcelReader.validExceLColunm(temp,7)) {
								
								budgDeptYearEditPlan.setResolve_or_sum(temp[7]);
					    		mapVo.put("resolve_or_sum", temp[7]);
					    		//分解或者汇总不为空   判断是分解还是汇总   01 分解    02 汇总
					    		if("01".equals(temp[7])){
					    			//01 为分解    分解方法不得为空
					    			if (ExcelReader.validExceLColunm(temp,8)) {
										
										budgDeptYearEditPlan.setResolve_way(temp[8]);
							    		mapVo.put("resolve_way", temp[8]);
									} else {
										err_sb.append("该科目编制为分解, 分解方法不得为空!!!");
									}
					    		}else {
					    			//否则  该科目编制是汇总   分解方法不得填写  应为空  
					    			if (ExcelReader.validExceLColunm(temp,8)) {
										//不为空  则填充数据页面回显  并添加错误信息
										budgDeptYearEditPlan.setResolve_way(temp[8]);
										err_sb.append("该科目编制为汇总, 分解方法不得填写!!!");
									
									} else {
										mapVo.put("resolve_way", "");
									}
					    		}
					    		
					    		//走分解或汇总   则编制方法,取值方法等均为空  否则填充数据页面回显  并添加错误信息
					    		if (ExcelReader.validExceLColunm(temp,3)) {
									budgDeptYearEditPlan.setEdit_method(temp[3]);
									err_sb.append("该科目编制为分解或汇总,编制方法不得填写!!");
								} else {
									mapVo.put("edit_method", "");
								}
								 
								if (ExcelReader.validExceLColunm(temp,4)) {
									budgDeptYearEditPlan.setGet_way(temp[4]);
									err_sb.append("该科目编制为分解或汇总,取值方法不得填写!!");
								} else {
									mapVo.put("get_way", "");
								}
								 
								if (ExcelReader.validExceLColunm(temp,5)) {
									budgDeptYearEditPlan.setFormula_id(temp[5]);
									err_sb.append("该科目编制为分解或汇总,计算公式ID不得填写!! ");
								} else {
									mapVo.put("formula_id", "");
								}
								 
								if (ExcelReader.validExceLColunm(temp,6)) {
									budgDeptYearEditPlan.setFun_id(temp[6]);
									err_sb.append("该科目编制为分解或汇总,取值函数ID不得填写!!  ");
								} else {
									mapVo.put("fun_id", "");
								}
					    		
							} else {
								err_sb.append("分解或汇总为空  ");
							}  
			    		}
				} 
				else {
					err_sb.append("是否独立核算为空或填写错误!");
					//如果是否独立核算为空或填写错误  则抓取后面所有字段信息 回显到页面 
					if (ExcelReader.validExceLColunm(temp,3)) {
						budgDeptYearEditPlan.setEdit_method(temp[3]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
					if (ExcelReader.validExceLColunm(temp,4)) {
						budgDeptYearEditPlan.setEdit_method(temp[4]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
					if (ExcelReader.validExceLColunm(temp,5)) {
						budgDeptYearEditPlan.setEdit_method(temp[5]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
					if (ExcelReader.validExceLColunm(temp,6)) {
						budgDeptYearEditPlan.setEdit_method(temp[6]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
					if (ExcelReader.validExceLColunm(temp,7)) {
						budgDeptYearEditPlan.setEdit_method(temp[7]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
					if (ExcelReader.validExceLColunm(temp,8)) {
						budgDeptYearEditPlan.setEdit_method(temp[8]);
					}else {
						budgDeptYearEditPlan.setEdit_method("");
					}
			    }
					 
				BudgMedIncomeEditPlan data_exc_extis = budgMedIncomeEditPlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptYearEditPlan.setError_type(err_sb.toString());
					
					list_err.add(budgDeptYearEditPlan);
					
				} else {
					addList.add(mapVo);
				}
			}
			
			if(list_err.size()==0){
				String dataJson = budgMedIncomeEditPlanService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgMedIncomeEditPlan data_exc = new BudgMedIncomeEditPlan();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/addBatchBudgDeptYearEditPlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptYearEditPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeEditPlan> list_err = new ArrayList<BudgMedIncomeEditPlan>();
		
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
			
			BudgMedIncomeEditPlan budgDeptYearEditPlan = new BudgMedIncomeEditPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgDeptYearEditPlan.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgDeptYearEditPlan.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_single_count"))) {
						
					budgDeptYearEditPlan.setIs_single_count(Integer.valueOf((String)jsonObj.get("is_single_count")));
		    		mapVo.put("is_single_count", jsonObj.get("is_single_count"));
		    		} else {
						
						err_sb.append("是否独立核算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("edit_method"))) {
						
					budgDeptYearEditPlan.setEdit_method((String)jsonObj.get("edit_method"));
		    		mapVo.put("edit_method", jsonObj.get("edit_method"));
		    		} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_way"))) {
						
					budgDeptYearEditPlan.setGet_way((String)jsonObj.get("get_way"));
		    		mapVo.put("get_way", jsonObj.get("get_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgDeptYearEditPlan.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgDeptYearEditPlan.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_or_sum"))) {
						
					budgDeptYearEditPlan.setResolve_or_sum((String)jsonObj.get("resolve_or_sum"));
		    		mapVo.put("resolve_or_sum", jsonObj.get("resolve_or_sum"));
		    		} else {
						
						err_sb.append("分解或汇总为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
					budgDeptYearEditPlan.setResolve_way((String)jsonObj.get("resolve_way"));
		    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					
					
					BudgMedIncomeEditPlan data_exc_extis = budgMedIncomeEditPlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptYearEditPlan.setError_type(err_sb.toString());
					
					list_err.add(budgDeptYearEditPlan);
					
				} else {
			  
					String dataJson = budgMedIncomeEditPlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeEditPlan data_exc = new BudgMedIncomeEditPlan();
			
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
	 * 生成数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptyearplan/generateBudgDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgDeptYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgHosYearIncomePlan = budgMedIncomeEditPlanService.generateBudgDeptYearIncomePlan(mapVo);

		return JSONObject.parseObject(budgHosYearIncomePlan);
		
	}
	
}

