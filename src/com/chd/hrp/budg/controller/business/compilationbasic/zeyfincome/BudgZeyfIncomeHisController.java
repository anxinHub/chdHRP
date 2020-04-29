/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.zeyfincome;
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
import com.chd.hrp.budg.entity.BudgDeptChargeStan;
import com.chd.hrp.budg.entity.BudgZeyfIncomeHis;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.business.compilationbasic.zeyfincome.BudgZeyfIncomeHisService;
/**
 * 
 * @Description:
 * 总额预付历史收入数据
 * @Table:
 * BUDG_ZEYF_INCOME_HIS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgZeyfIncomeHisController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgZeyfIncomeHisController.class);
	
	//引入Service服务
	@Resource(name = "budgZeyfIncomeHisService")
	private final BudgZeyfIncomeHisService budgZeyfIncomeHisService = null;
	@Resource(name ="budgSelectService")
	private BudgSelectService budgSelectService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/

	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZEYFIncomeHisMainPage", method = RequestMethod.GET)
	public String budgZeyfIncomeHisMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisAddPage", method = RequestMethod.GET)
	public String budgZeyfIncomeHisAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 科室基本指标数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/saveBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgZeyfIncomeHis(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("insurance_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("yb_income", "");
			}else{
				mapVo.put("yb_income", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgZeyfIncomeHisJson = null ;
		
		try {
			
			budgZeyfIncomeHisJson = budgZeyfIncomeHisService.save(listVo);
			
		} catch (Exception e) {
			
			budgZeyfIncomeHisJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgZeyfIncomeHisJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/addBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgZeyfIncomeHis(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {			
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("dept_id", ids[1])   ;
			mapVo.put("dept_name", ids[2]);
			mapVo.put("insurance_code", ids[3]);
			mapVo.put("insurance_name",ids[4]);
			mapVo.put("yb_income",ids[5]);
	        listVo.add(mapVo);
	    }             
		String budgZeyfIncomeHisJson = budgZeyfIncomeHisService.addBatch(listVo);
		return JSONObject.parseObject(budgZeyfIncomeHisJson);		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisUpdatePage", method = RequestMethod.GET)
	public String budgZeyfIncomeHisUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgZeyfIncomeHis budgZeyfIncomeHis = budgZeyfIncomeHisService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgZeyfIncomeHis.getGroup_id());
		mode.addAttribute("hos_id", budgZeyfIncomeHis.getHos_id());
		mode.addAttribute("copy_code", budgZeyfIncomeHis.getCopy_code());
		mode.addAttribute("year", budgZeyfIncomeHis.getYear());
		mode.addAttribute("dept_id", budgZeyfIncomeHis.getDept_id());
		mode.addAttribute("insurance_code", budgZeyfIncomeHis.getInsurance_code());
		mode.addAttribute("yb_income", budgZeyfIncomeHis.getYb_income());
		
		return "hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/updateBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgZeyfIncomeHis(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {		
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("year", ids[3])   ;
			mapVo.put("dept_id", ids[4])   ;
			mapVo.put("insurance_code", ids[5]);
			if("-1".equals(ids[6])){
				
				mapVo.put("yb_income","");
				
			}else{
				
				mapVo.put("yb_income",ids[6]);
			}
			
	        listVo.add(mapVo);
	    }             
	  
		String budgZeyfIncomeHisJson = budgZeyfIncomeHisService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgZeyfIncomeHisJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/addOrUpdateBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgZeyfIncomeHis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgZeyfIncomeHisJson ="";
		

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
	  
		budgZeyfIncomeHisJson = budgZeyfIncomeHisService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgZeyfIncomeHisJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/deleteBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgZeyfIncomeHis(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("insurance_code", ids[5]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgZeyfIncomeHisJson = budgZeyfIncomeHisService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgZeyfIncomeHisJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 总额预付历史收入数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/queryBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgZeyfIncomeHis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgZeyfIncomeHis = budgZeyfIncomeHisService.query(getPage(mapVo));

		return JSONObject.parseObject(budgZeyfIncomeHis);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 总额预付历史收入数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImportPage", method = RequestMethod.GET)
	public String budgZeyfIncomeHisImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 总额预付历史收入数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/zeyfincome/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","总额预付历史收入数据采集模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 总额预付历史收入数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/zeyfincome/readBudgZeyfIncomeHisFiles",method = RequestMethod.POST)  
    public String readBudgZeyfIncomeHisFiles(Plupload plupload,HttpServletRequest request,
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
					
					errMap.put("year", temp[0]);
					
		    		mapVo.put("year", temp[0]);
		    		
				} else {
					
					err_sb.append("年度为空;");
					
				}
				if (StringTool.isNotBlank(temp[1])) {	
					
					errMap.put("dept_code", temp[1]);
					
					mapVo.put("dept_code", temp[1]); 
					
					Map<String,Object> map = budgZeyfIncomeHisService.queryBudgDeptId(mapVo);
					
					if(map != null){
						
						mapVo.put("dept_id", map.get("dept_id"));
						
					}else{
						
						err_sb.append("科室编码不存在或不是预算科室;");
					}
		    		
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[2])) {
						
					errMap.put("insurance_code", temp[2]);
					
		    		mapVo.put("insurance_code", temp[2]);
	    		
		    		int count = budgZeyfIncomeHisService.queryInsuranceCodeExist(mapVo);
		    		
		    		if(count == 0 ){
		    			err_sb.append("医保类型编码不存在;");
		    		}
				
				} else {
					
					err_sb.append("医保类型编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[3])) {
					
					errMap.put("yb_income", temp[3]);
					
		    		mapVo.put("yb_income", temp[3]);
					
				}else{
					
					err_sb.append("医保收入为空;");
				}
				
				if(mapVo.get("dept_id") != null){
					//根据主键查询数据是否存在
					int count  = budgZeyfIncomeHisService.queryDataExist(mapVo);
					
					if(count > 0){
						
						err_sb.append("数据已存在;");
					}
				}
				
				if(err_sb.length() >  0){
					
					errMap.put("error_type",err_sb.toString()); 
					
					list_err.add(errMap);
					
				}else{
					
					addList.add(mapVo);
				}
			
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgZeyfIncomeHisService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("error_type","系统导入出错");
			
			list_err.add( map);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 总额预付历史收入数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/addBatchBudgZeyfIncomeHis", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgZeyfIncomeHis(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgZeyfIncomeHis> list_err = new ArrayList<BudgZeyfIncomeHis>();
		
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
			
			BudgZeyfIncomeHis budgZeyfIncomeHis = new BudgZeyfIncomeHis();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgZeyfIncomeHis.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgZeyfIncomeHis.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgZeyfIncomeHis.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("yb_income"))) {
						
					budgZeyfIncomeHis.setYb_income(Double.valueOf((String)jsonObj.get("yb_income")));
		    		mapVo.put("yb_income", jsonObj.get("yb_income"));
		    		} else {
						
						err_sb.append("医保收入为空  ");
						
					}
					
					
				BudgZeyfIncomeHis data_exc_extis = budgZeyfIncomeHisService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgZeyfIncomeHis.setError_type(err_sb.toString());
					
					list_err.add(budgZeyfIncomeHis);
					
				} else {
			  
					String dataJson = budgZeyfIncomeHisService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgZeyfIncomeHis data_exc = new BudgZeyfIncomeHis();
			
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
	 * 医保类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/queryBudgYBT", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
				
	    	mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	    
		}
	
		String ybType = budgZeyfIncomeHisService.queryBudgYBT(mapVo);
	
		return ybType;
	
	}	
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImportNewPage", method = RequestMethod.GET)
	public String budgZeyfIncomeHisImportNewPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImpo";

	}
	
	/**
	 * @Description 
	 * 最新导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/zeyfincome/budgZeyfIncomeHisImport", method = RequestMethod.POST)
	@ResponseBody
	public String budgZeyfIncomeHisImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgZeyfIncomeHisService.budgZeyfIncomeHisImport(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
}

