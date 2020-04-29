/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.dbzavg;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDbzAvgInDays;
import com.chd.hrp.budg.service.business.compilationbasic.dbzavg.BudgDbzAvgInDaysService;
/**
 * 
 * @Description:
 * 单病种平均住院日
 * @Table:
 * BUDG_DBZ_AVG_IN_DAYS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDbzAvgInDaysController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDbzAvgInDaysController.class);
	
	//引入Service服务
	@Resource(name = "budgDbzAvgInDaysService")
	private final BudgDbzAvgInDaysService budgDbzAvgInDaysService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/

	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysMainPage", method = RequestMethod.GET)
	public String budgDbzAvgInDaysMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysAddPage", method = RequestMethod.GET)
	public String budgDbzAvgInDaysAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysAdd";

	}
	/**
	 * @Description 
	 * 保存数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/saveBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDbzAvgInDays(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("disease_code", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("avg_in_days", "");
			}else{
				mapVo.put("avg_in_days", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgDbzAvgInDaysJson = null ;
		
		try {
			
			budgDbzAvgInDaysJson = budgDbzAvgInDaysService.save(listVo);
			
		} catch (Exception e) {
			
			budgDbzAvgInDaysJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgDbzAvgInDaysJson);
	}
	/**
	 * @Description 
	 * 添加数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/addBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDbzAvgInDays(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 	
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgDbzAvgInDaysJson = budgDbzAvgInDaysService.add(mapVo);
		return JSONObject.parseObject(budgDbzAvgInDaysJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysUpdatePage", method = RequestMethod.GET)
	public String budgDbzAvgInDaysUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDbzAvgInDays budgDbzAvgInDays = new BudgDbzAvgInDays();
    
		budgDbzAvgInDays = budgDbzAvgInDaysService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDbzAvgInDays.getGroup_id());
		mode.addAttribute("hos_id", budgDbzAvgInDays.getHos_id());
		mode.addAttribute("copy_code", budgDbzAvgInDays.getCopy_code());
		mode.addAttribute("year", budgDbzAvgInDays.getYear());
		mode.addAttribute("disease_code", budgDbzAvgInDays.getDisease_code());
		mode.addAttribute("insurance_code", budgDbzAvgInDays.getInsurance_code());
		mode.addAttribute("avg_in_days", budgDbzAvgInDays.getAvg_in_days());
		
		return "hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/updateBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDbzAvgInDays(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("disease_code", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			if("-1".equals(ids[3])){
				
				mapVo.put("avg_in_days", "");
				
			}else{
				
				mapVo.put("avg_in_days", ids[3]);
			}
			
	      listVo.add(mapVo);	      
	    }             
	  
		String budgDbzAvgInDaysJson = budgDbzAvgInDaysService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDbzAvgInDaysJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/addOrUpdateBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDbzAvgInDays(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDbzAvgInDaysJson ="";
		

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
	  
		budgDbzAvgInDaysJson = budgDbzAvgInDaysService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDbzAvgInDaysJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/deleteBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDbzAvgInDays(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("disease_code", ids[4])   ;
				mapVo.put("insurance_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDbzAvgInDaysJson = budgDbzAvgInDaysService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDbzAvgInDaysJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 单病种平均住院日
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/queryBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDbzAvgInDays(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDbzAvgInDays = budgDbzAvgInDaysService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDbzAvgInDays);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 单病种平均住院日
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysImportPage", method = RequestMethod.GET)
	public String budgDbzAvgInDaysImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 单病种平均住院日
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/dbzavg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","单病种平均住院日模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 单病种平均住院日
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/dbzavg/readBudgDbzAvgInDaysFiles",method = RequestMethod.POST)  
    public String readBudgDbzAvgInDaysFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDbzAvgInDays> list_err = new ArrayList<BudgDbzAvgInDays>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDbzAvgInDays budgDbzAvgInDays = new BudgDbzAvgInDays();
				
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
		    		
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
					budgDbzAvgInDays.setYear(temp[0]);
		    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgDbzAvgInDays.setDisease_code(temp[1]);
						
			    		mapVo.put("disease_code", temp[1]);
			    		
			    		int count = budgDbzAvgInDaysService.queryBudgDiseaseCode(mapVo);
			    		
			    		if(count == 0 ){
			    			
			    			err_sb.append("病种编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgDbzAvgInDays.setInsurance_code(temp[2]);
						
			    		mapVo.put("insurance_code", temp[2]);
			    		
			    		int count  = budgDbzAvgInDaysService.queryBudgInsuranceCode(mapVo);
			    		
			    		if(count == 0 ){
			    			err_sb.append("填写的病种编码与医保类型编码的关系未维护;");
			    		}
			    		
					} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgDbzAvgInDays.setAvg_in_days(Double.valueOf(temp[3]));
						
			    		mapVo.put("avg_in_days", temp[3]);
					
					} else {
						
						err_sb.append("平均住院日为空  ");
						
					}
					 
					
				BudgDbzAvgInDays data_exc_extis = budgDbzAvgInDaysService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDbzAvgInDays.setError_type(err_sb.toString());
					
					list_err.add(budgDbzAvgInDays);
					
				} else {
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				String dataJson = budgDbzAvgInDaysService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgDbzAvgInDays data_exc = new BudgDbzAvgInDays();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 单病种平均住院日
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/addBatchBudgDbzAvgInDays", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDbzAvgInDays(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDbzAvgInDays> list_err = new ArrayList<BudgDbzAvgInDays>();
		
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
			
			BudgDbzAvgInDays budgDbzAvgInDays = new BudgDbzAvgInDays();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDbzAvgInDays.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("disease_code"))) {
						
					budgDbzAvgInDays.setDisease_code((String)jsonObj.get("disease_code"));
		    		mapVo.put("disease_code", jsonObj.get("disease_code"));
		    		} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgDbzAvgInDays.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("avg_in_days"))) {
						
					budgDbzAvgInDays.setAvg_in_days(Double.valueOf((String)jsonObj.get("avg_in_days")));
		    		mapVo.put("avg_in_days", jsonObj.get("avg_in_days"));
		    		} else {
						
						err_sb.append("平均住院日为空  ");
						
					}
					
					
				BudgDbzAvgInDays data_exc_extis = budgDbzAvgInDaysService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDbzAvgInDays.setError_type(err_sb.toString());
					
					list_err.add(budgDbzAvgInDays);
					
				} else {
			  
					String dataJson = budgDbzAvgInDaysService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDbzAvgInDays data_exc = new BudgDbzAvgInDays();
			
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
	 * 增量生成 单病种费用标准
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				// 查询要生成的数据
				List<Map<String,Object>> list = budgDbzAvgInDaysService.queryDate(mapVo);
				
				for(Map<String, Object> item : list ){
						
						item.put("avg_in_days","");
						
				        listVo.add(item); 
				}
				
				String budgDbzAvgInDaysJson = null ;
				
				if(listVo.size() > 0 ){
					
					budgDbzAvgInDaysJson = budgDbzAvgInDaysService.addBatch(listVo);
					
				}else{
					
					budgDbzAvgInDaysJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
					
				}
				
              return JSONObject.parseObject(budgDbzAvgInDaysJson);	
	} 	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/queryBudgYBTP", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTP(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String ybType = budgDbzAvgInDaysService.queryBudgYBTP(mapVo);
		
		return ybType;

	}
	
	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/queryBudgSingleDC", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSingleDC(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String typeNature = budgDbzAvgInDaysService.queryBudgSingleDC(mapVo);
		return typeNature;

	}
	
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dbzavg/budgDbzAvgInDaysImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgDbzAvgInDaysImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgDbzAvgInDaysService.budgDbzAvgInDaysImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
}

