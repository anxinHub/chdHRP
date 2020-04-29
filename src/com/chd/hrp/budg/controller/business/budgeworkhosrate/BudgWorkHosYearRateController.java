/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgeworkhosrate;
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
import com.chd.hrp.budg.entity.BudgWorkHosYearRate;
import com.chd.hrp.budg.service.business.budgeworkhosrate.BudgWorkHosYearRateService;
/**
 * 
 * @Description:
 * 医院年度业务概率预算
 * @Table:
 * BUDG_WORK_HOS_YEAR_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkHosYearRateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkHosYearRateController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosYearRateService")
	private final BudgWorkHosYearRateService budgWorkHosYearRateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/budgWorkHosRateMainPage", method = RequestMethod.GET)
	public String budgWorkHosYearRateMainPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateAddPage", method = RequestMethod.GET)
	public String budgWorkHosYearRateAddPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/addBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkHosYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgWorkHosYearRateJson = budgWorkHosYearRateService.add(mapVo);

		return JSONObject.parseObject(budgWorkHosYearRateJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateUpdatePage", method = RequestMethod.GET)
	public String budgWorkHosYearRateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkHosYearRate budgWorkHosYearRate = new BudgWorkHosYearRate();
    
		budgWorkHosYearRate = budgWorkHosYearRateService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosYearRate.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosYearRate.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosYearRate.getCopy_code());
		mode.addAttribute("year", budgWorkHosYearRate.getYear());
		mode.addAttribute("index_code", budgWorkHosYearRate.getIndex_code());
		mode.addAttribute("measure_name", budgWorkHosYearRate.getMeasure_name());
		mode.addAttribute("measure_value", budgWorkHosYearRate.getMeasure_value());
		mode.addAttribute("rate", budgWorkHosYearRate.getRate());
		mode.addAttribute("count_value", budgWorkHosYearRate.getCount_value());
		
		return "hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/updateBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgWorkHosYearRateJson = budgWorkHosYearRateService.update(mapVo);
		
		return JSONObject.parseObject(budgWorkHosYearRateJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/addOrUpdateBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkHosYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosYearRateJson ="";
		

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
	  
		budgWorkHosYearRateJson = budgWorkHosYearRateService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosYearRateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/deleteBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosYearRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("index_code", ids[4])   ;
				mapVo.put("measure_name", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosYearRateJson = budgWorkHosYearRateService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosYearRateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度业务概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/queryBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosYearRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosYearRate = budgWorkHosYearRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosYearRate);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度业务概率预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateImportPage", method = RequestMethod.GET)
	public String budgWorkHosYearRateImportPage(Model mode) throws Exception {

		return "hrp/budg/business/budgeworkhosrate/budgWorkHosYearRateImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度业务概率预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/budgeworkhosrate/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","医院年度业务概率预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度业务概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/budgeworkhosrate/readBudgWorkHosYearRateFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosYearRateFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkHosYearRate> list_err = new ArrayList<BudgWorkHosYearRate>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkHosYearRate budgWorkHosYearRate = new BudgWorkHosYearRate();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgWorkHosYearRate.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgWorkHosYearRate.setIndex_code(temp[4]);
		    		mapVo.put("index_code", temp[4]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgWorkHosYearRate.setMeasure_name(temp[5]);
		    		mapVo.put("measure_name", temp[5]);
					
					} else {
						
						err_sb.append("运营尺度名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgWorkHosYearRate.setMeasure_value(Double.valueOf(temp[6]));
		    		mapVo.put("measure_value", temp[6]);
					
					} else {
						
						err_sb.append("运营预期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgWorkHosYearRate.setRate(Double.valueOf(temp[7]));
		    		mapVo.put("rate", temp[7]);
					
					} else {
						
						err_sb.append("概率为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgWorkHosYearRate.setCount_value(Double.valueOf(temp[8]));
		    		mapVo.put("count_value", temp[8]);
					
					} else {
						
						err_sb.append("计算值为空  ");
						
					}
					 
					
				BudgWorkHosYearRate data_exc_extis = budgWorkHosYearRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYearRate.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYearRate);
					
				} else {
			  
					String dataJson = budgWorkHosYearRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYearRate data_exc = new BudgWorkHosYearRate();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院年度业务概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkhosrate/addBatchBudgWorkHosYearRate", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkHosYearRate(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosYearRate> list_err = new ArrayList<BudgWorkHosYearRate>();
		
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
			
			BudgWorkHosYearRate budgWorkHosYearRate = new BudgWorkHosYearRate();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkHosYearRate.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkHosYearRate.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("measure_name"))) {
						
					budgWorkHosYearRate.setMeasure_name((String)jsonObj.get("measure_name"));
		    		mapVo.put("measure_name", jsonObj.get("measure_name"));
		    		} else {
						
						err_sb.append("运营尺度名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("measure_value"))) {
						
					budgWorkHosYearRate.setMeasure_value(Double.valueOf((String)jsonObj.get("measure_value")));
		    		mapVo.put("measure_value", jsonObj.get("measure_value"));
		    		} else {
						
						err_sb.append("运营预期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("rate"))) {
						
					budgWorkHosYearRate.setRate(Double.valueOf((String)jsonObj.get("rate")));
		    		mapVo.put("rate", jsonObj.get("rate"));
		    		} else {
						
						err_sb.append("概率为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgWorkHosYearRate.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					
				BudgWorkHosYearRate data_exc_extis = budgWorkHosYearRateService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYearRate.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYearRate);
					
				} else {
			  
					String dataJson = budgWorkHosYearRateService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYearRate data_exc = new BudgWorkHosYearRate();
			
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

