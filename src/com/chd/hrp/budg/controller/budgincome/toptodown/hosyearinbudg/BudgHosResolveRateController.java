/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.hosyearinbudg;
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
import com.chd.hrp.budg.entity.BudgHosResolveRate;
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
import com.chd.hrp.budg.service.budgincome.budghosresolverate.BudgHosResolveRateService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearService;

/**
 * 
 * @Description:
 * 医院医疗收入科目分解比例维护
 * @Table:
 * BUDG_HOS_RESOLVE_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosResolveRateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosResolveRateController.class);
	
	//引入Service服务
	@Resource(name = "budgHosResolveRateService")
	private final BudgHosResolveRateService budgHosResolveRateService = null;
    
	@Resource(name = "budgMedIncomeHosYearService")
	private final BudgMedIncomeHosYearService budgMedIncomeHosYearService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/budgHosResolveRateMainPage", method = RequestMethod.GET)
	public String budgHosResolveRateMainPage(Model mode) throws Exception {

		return "hrp/budg/budghosresolverate/budgHosResolveRateMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/budgHosResolveRateAddPage", method = RequestMethod.GET)
	public String budgHosResolveRateAddPage(Model mode) throws Exception {

		return "hrp/budg/budghosresolverate/budgHosResolveRateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addBudgHosResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgHosResolveRateJson = budgHosResolveRateService.add(mapVo);

		return JSONObject.parseObject(budgHosResolveRateJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/budgHosResolveRateUpdatePage", method = RequestMethod.GET)
	public String budgHosResolveRateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgHosResolveRate budgHosResolveRate = new BudgHosResolveRate();
    
		budgHosResolveRate = budgHosResolveRateService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosResolveRate.getGroup_id());
		mode.addAttribute("hos_id", budgHosResolveRate.getHos_id());
		mode.addAttribute("copy_code", budgHosResolveRate.getCopy_code());
		mode.addAttribute("year", budgHosResolveRate.getYear());
		mode.addAttribute("subj_code", budgHosResolveRate.getSubj_code());
		mode.addAttribute("last_year_income", budgHosResolveRate.getLast_year_income());
		mode.addAttribute("grow_rate", budgHosResolveRate.getGrow_rate());
		mode.addAttribute("resolve_rate", budgHosResolveRate.getResolve_rate());
		mode.addAttribute("remark", budgHosResolveRate.getRemark());
		
		return "hrp/budg/budghosresolverate/budgHosResolveRateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/updateBudgHosResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgHosResolveRateJson = budgHosResolveRateService.update(mapVo);
		
		return JSONObject.parseObject(budgHosResolveRateJson);
	}
	
	/**
	 * @Description 
	 * 批量更新数据 医院年度医疗预算分解预算分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/updateResolveRateHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResolveHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@",-1);
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("last_year_income", ids[2]);
			mapVo.put("resolve_rate", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			if("-1".equals(ids[5])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[5]);
			}
			BudgMedIncomeHosYear bmihy = budgMedIncomeHosYearService.queryByCode(mapVo);
			if(bmihy!=null){
				mapVo.put("budg_value", bmihy.getBudg_value());
				mapVo.put("hos_suggest", bmihy.getHos_suggest());
				mapVo.put("dept_suggest_sum",bmihy.getDept_suggest_sum());
				mapVo.put("count_value", "");
				listVo.add(mapVo);
				}
			
		}
	  
		String budgHosResolveRateJson = budgHosResolveRateService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosResolveRateJson);
	}
	
	
	/**
	 * @Description 
	 * 更新数据 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addOrUpdateBudgHosResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosResolveRateJson ="";
		

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
	  
		budgHosResolveRateJson = budgHosResolveRateService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosResolveRateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/deleteBudgHosResolveRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosResolveRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@",-1);
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgHosResolveRateJson = budgHosResolveRateService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosResolveRateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院医疗收入科目分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/queryResolveRateHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResolveRateHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosResolveRate = budgHosResolveRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosResolveRate);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院医疗收入科目分解比例维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveRateHYInBudgImportPage", method = RequestMethod.GET)
	public String budgHosResolveRateImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveRateHYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院医疗收入科目分解比例维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveRatedownTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院医疗收入科目分解比例维护.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院医疗收入科目分解比例维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/readResolveRateHYInBudgFiles",method = RequestMethod.POST)  
    public String readResolveRateHYInBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgHosResolveRate> list_err = new ArrayList<BudgHosResolveRate>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String ,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgHosResolveRate budgHosResolveRate = new BudgHosResolveRate();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
					budgHosResolveRate.setYear(temp[0]);
		    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
					budgHosResolveRate.setSubj_code(temp[1]);
		    		mapVo.put("subj_code", temp[1]);
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
					budgHosResolveRate.setLast_year_income(Double.valueOf(temp[2]));
		    		mapVo.put("last_year_income", temp[2]);
					
					} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
					budgHosResolveRate.setGrow_rate(Double.valueOf(temp[3]));
		    		mapVo.put("grow_rate", temp[3]);
					
					} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
					budgHosResolveRate.setResolve_rate(Double.valueOf(temp[4]));
		    		mapVo.put("resolve_rate", temp[4]);
					
					} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
					budgHosResolveRate.setRemark(temp[5]);
		    		mapVo.put("remark", temp[5]);
					
					} else {
						
						err_sb.append("说明为空  ");
						
					}
					 
					
				BudgHosResolveRate data_exc_extis = budgHosResolveRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosResolveRate.setError_type(err_sb.toString());
					
					list_err.add(budgHosResolveRate);
					
				} 
				addList.add(mapVo);
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgHosResolveRateService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgHosResolveRate data_exc = new BudgHosResolveRate();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院医疗收入科目分解比例维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addBatchBudgHosResolveRate", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosResolveRate(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgHosResolveRate> list_err = new ArrayList<BudgHosResolveRate>();
		
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
			
			BudgHosResolveRate budgHosResolveRate = new BudgHosResolveRate();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgHosResolveRate.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgHosResolveRate.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgHosResolveRate.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgHosResolveRate.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgHosResolveRate.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgHosResolveRate.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgHosResolveRate data_exc_extis = budgHosResolveRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosResolveRate.setError_type(err_sb.toString());
					
					list_err.add(budgHosResolveRate);
					
				} else {
			  
					String dataJson = budgHosResolveRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgHosResolveRate data_exc = new BudgHosResolveRate();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

