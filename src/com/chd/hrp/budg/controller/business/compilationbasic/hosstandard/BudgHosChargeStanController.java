/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.hosstandard;
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
import com.chd.hrp.budg.entity.BudgDeptBasicIndexData;
import com.chd.hrp.budg.entity.BudgHosChargeStan;
import com.chd.hrp.budg.service.business.compilationbasic.hosstandard.BudgHosChargeStanService;
/**
 * 
 * @Description:
 * 医院费用标准维护
 * @Table:
 * BUDG_HOS_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosChargeStanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosChargeStanController.class);
	
	//引入Service服务
	@Resource(name = "budgHosChargeStanService")
	
	private final BudgHosChargeStanService budgHosChargeStanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanMainPage", method = RequestMethod.GET)
	public String budgHosChargeStanMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanAddPage", method = RequestMethod.GET)
	public String budgHosChargeStanAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/saveBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgHosChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("charge_stan_code", ids[1]);
			mapVo.put("budg_value", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgHosChargeStanJson = null ;
		
		try {
			
			budgHosChargeStanJson = budgHosChargeStanService.save(listVo);
			
		} catch (Exception e) {
			
			budgHosChargeStanJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgHosChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/addBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
      
		String budgHosChargeStanJson = budgHosChargeStanService.add(mapVo);

		return JSONObject.parseObject(budgHosChargeStanJson);	
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanUpdatePage", method = RequestMethod.GET)
	public String budgHosChargeStanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgHosChargeStan budgHosChargeStan = new BudgHosChargeStan();
    
		budgHosChargeStan = budgHosChargeStanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosChargeStan.getGroup_id());
		mode.addAttribute("hos_id", budgHosChargeStan.getHos_id());
		mode.addAttribute("copy_code", budgHosChargeStan.getCopy_code());
		mode.addAttribute("year", budgHosChargeStan.getYear());
		mode.addAttribute("charge_stan_code", budgHosChargeStan.getCharge_stan_code());
		mode.addAttribute("budg_value", budgHosChargeStan.getBudg_value());
		mode.addAttribute("remark", budgHosChargeStan.getRemark());
		
		return "hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/updateBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
        for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("charge_stan_code", ids[1]);
			
			mapVo.put("budg_value", ids[2])   ;
			
			if("-1".equals(ids[3])){
				
				mapVo.put("remark", "")   ;
				
			}else{
				
				mapVo.put("remark", ids[3])   ;
			}
			
	        listVo.add(mapVo);
	      
	    }       
	  
		String budgHosChargeStanJson = budgHosChargeStanService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/addOrUpdateBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosChargeStanJson ="";
		

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
	  
		budgHosChargeStanJson = budgHosChargeStanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/deleteBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("charge_stan_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgHosChargeStanJson = budgHosChargeStanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosChargeStanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/queryBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String budgHosChargeStan = budgHosChargeStanService.query(getPage(mapVo));
		return JSONObject.parseObject(budgHosChargeStan);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 医院费用标准维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImportPage", method = RequestMethod.GET)
	public String budgHosChargeStanImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院费用标准维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/hosstandard/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","医院费用标准维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院费用标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/hosstandard/readBudgHosChargeStanFiles",method = RequestMethod.POST)  
    public String readBudgHosChargeStanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgHosChargeStan> list_err = new ArrayList<BudgHosChargeStan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgHosChargeStan budgHosChargeStan = new BudgHosChargeStan();
				
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
					budgHosChargeStan.setYear(temp[0]);
		    		mapVo.put("year", temp[0]);
				} else {
					
					err_sb.append("年度为空;");
					
				}
				if (StringTool.isNotBlank(temp[1])) {
						
					budgHosChargeStan.setCharge_stan_code(temp[1]);
					
		    		mapVo.put("charge_stan_code", temp[1]);
		    		
		    		mapVo.put("charge_stan_nature", "01");
		    		
		    		int count = budgHosChargeStanService.queryChargeStanCodeExist(mapVo);
		    		
		    		if(count == 0 ){
		    			
		    			err_sb.append("填写费用标准编码不存在或其费用标准性质不是医院;");
		    		}
				
				} else {
					
					err_sb.append("费用标准编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[2])) {
					
					budgHosChargeStan.setBudg_value(Double.parseDouble(temp[2]));
					
		    		mapVo.put("budg_value", temp[2]);
					
				}else{
					err_sb.append("预算值为空;");
				}
				if (ExcelReader.validExceLColunm(temp, 3)) {
					
					budgHosChargeStan.setRemark(temp[3]);
					
		    		mapVo.put("remark", temp[3]);
					
				}else{
					
					mapVo.put("remark", "");
				}
				
				BudgHosChargeStan stan = budgHosChargeStanService.queryByCode(mapVo);
				
				if(stan != null){
					err_sb.append("数据已存在;");
				}
				
				if(err_sb.length() >  0){
					
					budgHosChargeStan.setError_type(err_sb.toString()); 
					
					list_err.add(budgHosChargeStan);
					
				}else{
					
					addList.add(mapVo);
				}
			}
			
			if(list_err.size() == 0){
				String dataJson = budgHosChargeStanService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			BudgHosChargeStan budgHosChargeStan = new BudgHosChargeStan();
			budgHosChargeStan.setError_type("系统导入出错");
			list_err.add( budgHosChargeStan);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院费用标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/addBatchBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosChargeStan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgHosChargeStan> list_err = new ArrayList<BudgHosChargeStan>();
		
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
			
			BudgHosChargeStan budgHosChargeStan = new BudgHosChargeStan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgHosChargeStan.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("charge_stan_code"))) {
						
					budgHosChargeStan.setCharge_stan_code((String)jsonObj.get("charge_stan_code"));
		    		mapVo.put("charge_stan_code", jsonObj.get("charge_stan_code"));
		    		} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgHosChargeStan.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgHosChargeStan.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgHosChargeStan data_exc_extis = budgHosChargeStanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosChargeStan.setError_type(err_sb.toString());
					
					list_err.add(budgHosChargeStan);
					
				} else {
			  
					String dataJson = budgHosChargeStanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgHosChargeStan data_exc = new BudgHosChargeStan();
			
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
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("charge_stan_nature","01")   ;
		
		//查询 生成数据
		List<Map<String,Object>> list=budgHosChargeStanService.queryBudgDeptChargeStanData(mapVo);
		for(Map<String,Object> item : list){
			
			item.put("year",mapVo.get("year")) ;
			//校验数据是否存在 
			int count  = budgHosChargeStanService.queryBudgHosChargeStanExist(item);
			
			if(count == 0 ){
				item.put("budg_value","")   ;
				item.put("remark","")   ;
				listVo.add(item);
			}
		}
		
		String budgWorkHosExecuteJson = "";
		
		if(listVo.size() > 0 ){
			
			budgWorkHosExecuteJson = budgHosChargeStanService.addBatch(listVo);
			
		}else{
			
			budgWorkHosExecuteJson = "{\"error\":\"数据已全部生成,无需生成!\"}";
		}
	    
	    return JSONObject.parseObject(budgWorkHosExecuteJson);	
	}
	
	/**
	 * 主页面 换行添加  费用标准下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/queryBudgChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String chargeStan = budgHosChargeStanService.queryBudgChargeStan(mapVo);
		return chargeStan;

	}
	
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImportNewPage", method = RequestMethod.GET)
	public String budgHosChargeStanImportNewPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImpo";

	}
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/budgHosChargeStanImport", method = RequestMethod.POST)
	@ResponseBody
	public String budgHosChargeStanImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgHosChargeStanService.budgHosChargeStanImport(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosstandard/collectBudgHosChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public String collectBudgHosChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = budgHosChargeStanService.collectBudgHosChargeStan(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
			
		}
	}
	
}

