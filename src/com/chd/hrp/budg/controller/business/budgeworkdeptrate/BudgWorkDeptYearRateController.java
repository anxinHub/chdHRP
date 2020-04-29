/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgeworkdeptrate;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgWorkDeptYearRate;
import com.chd.hrp.budg.service.business.budgeworkdeptrate.BudgWorkDeptYearRateService;
/**
 * 
 * @Description:
 * 科室年度业务概率预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkDeptYearRateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkDeptYearRateController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearRateService")
	private final BudgWorkDeptYearRateService budgWorkDeptYearRateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/

	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/budgWorkDeptRateMainPage", method = RequestMethod.GET)
	public String budgWorkDeptYearRateMainPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateAddPage", method = RequestMethod.GET)
	public String budgWorkDeptYearRateAddPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/addBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkDeptYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgWorkDeptYearRateJson = budgWorkDeptYearRateService.add(mapVo);

		return JSONObject.parseObject(budgWorkDeptYearRateJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateUpdatePage", method = RequestMethod.GET)
	public String budgWorkDeptYearRateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptYearRate budgWorkDeptYearRate = new BudgWorkDeptYearRate();
    
		budgWorkDeptYearRate = budgWorkDeptYearRateService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptYearRate.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptYearRate.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptYearRate.getCopy_code());
		mode.addAttribute("year", budgWorkDeptYearRate.getYear());
		mode.addAttribute("dept_id", budgWorkDeptYearRate.getDept_id());
		mode.addAttribute("index_code", budgWorkDeptYearRate.getIndex_code());
		mode.addAttribute("measure_name", budgWorkDeptYearRate.getMeasure_name());
		mode.addAttribute("measure_value", budgWorkDeptYearRate.getMeasure_value());
		mode.addAttribute("rate", budgWorkDeptYearRate.getRate());
		mode.addAttribute("count_value", budgWorkDeptYearRate.getCount_value());
		
		return "hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/updateBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkDeptYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgWorkDeptYearRateJson = budgWorkDeptYearRateService.update(mapVo);
		
		return JSONObject.parseObject(budgWorkDeptYearRateJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/addOrUpdateBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkDeptYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptYearRateJson ="";
		

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
	  
		budgWorkDeptYearRateJson = budgWorkDeptYearRateService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptYearRateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/deleteBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkDeptYearRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				mapVo.put("index_code", ids[5])   ;
				mapVo.put("measure_name", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptYearRateJson = budgWorkDeptYearRateService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearRateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/queryBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkDeptYearRate = budgWorkDeptYearRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYearRate);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室年度业务概率预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateImportPage", method = RequestMethod.GET)
	public String budgWorkDeptYearRateImportPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkdeptrate/budgWorkDeptYearRateImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度业务概率预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/budgeworkdeptrate/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","科室年度业务概率预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度业务概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/budgeworkdeptrate/readBudgWorkDeptYearRateFiles",method = RequestMethod.POST)  
    public String readBudgWorkDeptYearRateFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkDeptYearRate> list_err = new ArrayList<BudgWorkDeptYearRate>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkDeptYearRate budgWorkDeptYearRate = new BudgWorkDeptYearRate();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgWorkDeptYearRate.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgWorkDeptYearRate.setDept_id(Long.valueOf(temp[4]));
		    		mapVo.put("dept_id", temp[4]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgWorkDeptYearRate.setIndex_code(temp[5]);
		    		mapVo.put("index_code", temp[5]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgWorkDeptYearRate.setMeasure_name(temp[6]);
		    		mapVo.put("measure_name", temp[6]);
					
					} else {
						
						err_sb.append("运营尺度名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgWorkDeptYearRate.setMeasure_value(Double.valueOf(temp[7]));
		    		mapVo.put("measure_value", temp[7]);
					
					} else {
						
						err_sb.append("运营预期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgWorkDeptYearRate.setRate(Double.valueOf(temp[8]));
		    		mapVo.put("rate", temp[8]);
					
					} else {
						
						err_sb.append("概率为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgWorkDeptYearRate.setCount_value(Double.valueOf(temp[9]));
		    		mapVo.put("count_value", temp[9]);
					
					} else {
						
						err_sb.append("计算值为空  ");
						
					}
					 
					
				BudgWorkDeptYearRate data_exc_extis = budgWorkDeptYearRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptYearRate.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptYearRate);
					
				} else {
			  
					String dataJson = budgWorkDeptYearRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptYearRate data_exc = new BudgWorkDeptYearRate();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室年度业务概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkdeptrate/addBatchBudgWorkDeptYearRate", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkDeptYearRate(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptYearRate> list_err = new ArrayList<BudgWorkDeptYearRate>();
		
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
			
			BudgWorkDeptYearRate budgWorkDeptYearRate = new BudgWorkDeptYearRate();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptYearRate.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptYearRate.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkDeptYearRate.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("measure_name"))) {
						
					budgWorkDeptYearRate.setMeasure_name((String)jsonObj.get("measure_name"));
		    		mapVo.put("measure_name", jsonObj.get("measure_name"));
		    		} else {
						
						err_sb.append("运营尺度名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("measure_value"))) {
						
					budgWorkDeptYearRate.setMeasure_value(Double.valueOf((String)jsonObj.get("measure_value")));
		    		mapVo.put("measure_value", jsonObj.get("measure_value"));
		    		} else {
						
						err_sb.append("运营预期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rate"))) {
						
					budgWorkDeptYearRate.setRate(Double.valueOf((String)jsonObj.get("rate")));
		    		mapVo.put("rate", jsonObj.get("rate"));
		    		} else {
						
						err_sb.append("概率为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgWorkDeptYearRate.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					
				BudgWorkDeptYearRate data_exc_extis = budgWorkDeptYearRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptYearRate.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptYearRate);
					
				} else {
			  
					String dataJson = budgWorkDeptYearRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptYearRate data_exc = new BudgWorkDeptYearRate();
			
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

