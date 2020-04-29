/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.hosyearbudg;
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
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.downtotop.hosyearbudg.MedInHYBudgService;
/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class MedInHYBudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInHYBudgController.class);
	
	//引入Service服务
	@Resource(name = "medInHYBudgService")
	private final MedInHYBudgService medInHYBudgService = null;
   
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/hosYearMedInPage", method = RequestMethod.GET)
	public String medInHYBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgMain";

	}
	
	/**
	 * @Description 
	 * 预算分解维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgResolveMainPage", method = RequestMethod.GET)
	public String medInHYBudgResolveMainPage(Model mode) throws Exception {
			
		return "hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgResolveMain";

	}


	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgAddPage", method = RequestMethod.GET)
	public String medInHYBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgAdd";

	}

	/**
	 * @Description 
	 * 保存数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/saveMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedInHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1])   ;
			mapVo.put("budg_value", ids[2]);
			if(!"-1".equals(ids[3])){
				mapVo.put("last_year_income", ids[3]);
			}else{
				mapVo.put("last_year_income", 0);
			}
			mapVo.put("remark", ids[4]);
			mapVo.put("hos_suggest", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[6]);
			}
			
			mapVo.put("rowNo", ids[7]);
			mapVo.put("flag", ids[8]);
			mapVo.put("dept_suggest_sum", "");
			
			listVo.add(mapVo);
		}
       
		String budgMedIncomeHosYearJson = "";
		
		try {
			
			budgMedIncomeHosYearJson = medInHYBudgService.save(listVo);
			
		} catch (Exception e) {
			
			budgMedIncomeHosYearJson = e.getMessage() ;
		}
		

		return JSONObject.parseObject(budgMedIncomeHosYearJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/addMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1])   ;
			mapVo.put("budg_value", ids[2]);
			if("-1".equals(ids[3])){
				return JSONObject.parseObject("{\"error\":\"科目"+ids[1]+"的上年收入未维护,请维护后操作\"}");
			}else{
			}
			mapVo.put("remark", ids[4]);
			mapVo.put("hos_suggest", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[6]);
			}
			
			mapVo.put("dept_suggest_sum", "");
			
			listVo.add(mapVo);
		}
       
		String budgMedIncomeHosYearJson = "";
		
		try {
			
			budgMedIncomeHosYearJson = medInHYBudgService.addBatch(listVo);
			
		} catch (Exception e) {
			
			budgMedIncomeHosYearJson = e.getMessage() ;
		}
		

		return JSONObject.parseObject(budgMedIncomeHosYearJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgUpdatePage", method = RequestMethod.GET)
	public String medInHYBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeHosYear budgMedIncomeHosYear = new BudgMedIncomeHosYear();
    
		budgMedIncomeHosYear = medInHYBudgService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeHosYear.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeHosYear.getHos_id());
		mode.addAttribute("copy_code", budgMedIncomeHosYear.getCopy_code());
		mode.addAttribute("year", budgMedIncomeHosYear.getYear());
		mode.addAttribute("subj_code", budgMedIncomeHosYear.getSubj_code());
		mode.addAttribute("count_value", budgMedIncomeHosYear.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeHosYear.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeHosYear.getRemark());
		mode.addAttribute("last_year_income", budgMedIncomeHosYear.getLast_year_income());
		mode.addAttribute("hos_suggest", budgMedIncomeHosYear.getHos_suggest());
		mode.addAttribute("dept_suggest_sum", budgMedIncomeHosYear.getDept_suggest_sum());
		
		return "hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/updateMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1])   ;
			mapVo.put("budg_value", ids[2]);
			mapVo.put("last_year_income", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("hos_suggest", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[6]);
			}
			
			mapVo.put("dept_suggest_sum", "");
			listVo.add(mapVo);
	      
	    }
	  
		String budgMedIncomeHosYearJson = medInHYBudgService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeHosYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/addOrUpdateMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedInHYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedIncomeHosYearJson ="";
		

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
	  
		budgMedIncomeHosYearJson = medInHYBudgService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeHosYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/deleteMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeHosYearJson = medInHYBudgService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeHosYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/queryMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInHYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeHosYear = medInHYBudgService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHosYear);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度医疗收入预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgImportPage", method = RequestMethod.GET)
	public String medInHYBudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/hosyearbudg/medInHYBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度医疗收入预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/hosyearbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院年度医疗收入预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/hosyearbudg/readMedInHYBudgFiles",method = RequestMethod.POST)  
    public String readMedInHYBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeHosYear> list_err = new ArrayList<BudgMedIncomeHosYear>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeHosYear budgMedIncomeHosYear = new BudgMedIncomeHosYear();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		

		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						budgMedIncomeHosYear.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						budgMedIncomeHosYear.setSubj_code(temp[1]);
						
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空 ;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgMedIncomeHosYear.setLast_year_income(Double.valueOf(temp[2]));
						mapVo.put("last_year_income", temp[2]);
					
					} else {
						
						mapVo.put("last_year_income", "");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgMedIncomeHosYear.setBudg_value(Double.valueOf(temp[3]));
						
						mapVo.put("budg_value", temp[3]);
					
					} else {
						
						mapVo.put("budg_value", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgMedIncomeHosYear.setRemark(temp[4]);
						
						mapVo.put("remark", temp[4]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgMedIncomeHosYear.setDept_suggest_sum(Double.valueOf(temp[5]));
						
						mapVo.put("dept_suggest_sum", temp[5]);
					
					} else {
						
						mapVo.put("dept_suggest_sum", "");
						
					}
					 
					
				BudgMedIncomeHosYear data_exc_extis = medInHYBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeHosYear);
					
				} else {
					mapVo.put("count_value", "");
					mapVo.put("hos_suggest", "");
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				String dataJson = medInHYBudgService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeHosYear data_exc = new BudgMedIncomeHosYear();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院年度医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/addBatchMedInHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInHYBudg(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeHosYear> list_err = new ArrayList<BudgMedIncomeHosYear>();
		
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
			
			BudgMedIncomeHosYear budgMedIncomeHosYear = new BudgMedIncomeHosYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedIncomeHosYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedIncomeHosYear.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgMedIncomeHosYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgMedIncomeHosYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedIncomeHosYear.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgMedIncomeHosYear.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
					budgMedIncomeHosYear.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
		    		mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
		    		} else {
						
						err_sb.append("医院意见分解为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
						
					budgMedIncomeHosYear.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
		    		mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
		    		} else {
						
						err_sb.append("科室意见汇总为空  ");
						
					}
					
					
				BudgMedIncomeHosYear data_exc_extis = medInHYBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeHosYear);
					
				} else {
			  
					String dataJson = medInHYBudgService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeHosYear data_exc = new BudgMedIncomeHosYear();
			
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
	 * 查询部门
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/queryBudgDept", method = RequestMethod.POST)
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
		
		String dept = medInHYBudgService.queryBudgDept(mapVo);
		return dept;

	}
    
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/collectBudgMedIncomeHosYearDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgMedIncomeHosYearDown(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String collect  = medInHYBudgService.collectMedInHYBudgUp(mapVo) ;

		return JSONObject.parseObject(collect);
	}
	
	/**
	 * 根据科目查询上年收入
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/hosyearbudg/queryLastYearIncome", method = RequestMethod.POST)
	@ResponseBody
	public String queryLastYearIncome(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String lastYearIncome  = medInHYBudgService.queryLastYearIncome(mapVo) ;
		
		return lastYearIncome;

	}
}

