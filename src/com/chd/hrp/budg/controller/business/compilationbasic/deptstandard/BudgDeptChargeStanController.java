/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.deptstandard;
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
import com.chd.hrp.budg.entity.BudgDeptChargeStan;
import com.chd.hrp.budg.entity.BudgHosChargeStan;
import com.chd.hrp.budg.service.business.compilationbasic.deptstandard.BudgDeptChargeStanService;
/**
 * 
 * @Description:
 * 科室费用标准维护
 * @Table:
 * BUDG_DEPT_CHARGE_STAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptChargeStanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptChargeStanController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptChargeStanService")
	private final BudgDeptChargeStanService budgDeptChargeStanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/

	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanMainPage", method = RequestMethod.GET)
	public String budgDeptChargeStanMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanAddPage", method = RequestMethod.GET)
	public String budgDeptChargeStanAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/saveBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDeptChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[4]);
			}
			
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgDeptChargeStanJson = null ;
		
		try {
			
			budgDeptChargeStanJson = budgDeptChargeStanService.save(listVo);
			
		} catch (Exception e) {
			
			budgDeptChargeStanJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgDeptChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/addBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("charge_stan_code", ids[1]);
			mapVo.put("dept_id", ids[2])   ;
			mapVo.put("dept_name", ids[3])   ;
			mapVo.put("budg_value", ids[4])   ;
			if("-1".equals(ids[5])){
				
				mapVo.put("remark", "")   ;
				
			}else{
				
				mapVo.put("remark", ids[5])   ;
			}
			
	        listVo.add(mapVo);      
	    }       
		String budgDeptChargeStanJson = budgDeptChargeStanService.addBatch(listVo);

		return JSONObject.parseObject(budgDeptChargeStanJson);		
			
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanUpdatePage", method = RequestMethod.GET)
	public String budgDeptChargeStanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptChargeStan budgDeptChargeStan = new BudgDeptChargeStan();
    
		budgDeptChargeStan = budgDeptChargeStanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptChargeStan.getGroup_id());
		mode.addAttribute("hos_id", budgDeptChargeStan.getHos_id());
		mode.addAttribute("copy_code", budgDeptChargeStan.getCopy_code());
		mode.addAttribute("year", budgDeptChargeStan.getYear());
		mode.addAttribute("dept_id", budgDeptChargeStan.getDept_id());
		mode.addAttribute("charge_stan_code", budgDeptChargeStan.getCharge_stan_code());
		mode.addAttribute("budg_value", budgDeptChargeStan.getBudg_value());
		mode.addAttribute("remark", budgDeptChargeStan.getRemark());
		
		return "hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/updateBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("charge_stan_code", ids[1]);
			mapVo.put("dept_id", ids[2])   ;
			mapVo.put("budg_value", ids[3])   ;
			if("-1".equals(ids[4])){
				
				mapVo.put("remark", "")   ;
				
			}else{
				
				mapVo.put("remark", ids[4])   ;
			}
			
	        listVo.add(mapVo);      
	    }       
	  
		String budgDeptChargeStanJson = budgDeptChargeStanService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/addOrUpdateBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptChargeStanJson ="";
		

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
	  
		budgDeptChargeStanJson = budgDeptChargeStanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptChargeStanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/deleteBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptChargeStan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("charge_stan_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptChargeStanJson = budgDeptChargeStanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptChargeStanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室费用标准维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/queryBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDeptChargeStan = budgDeptChargeStanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptChargeStan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室费用标准维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanImportPage", method = RequestMethod.GET)
	public String budgDeptChargeStanImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室费用标准维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/deptstandard/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","科室费用标准维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室费用标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/deptstandard/readBudgDeptChargeStanFiles",method = RequestMethod.POST)  
    public String readBudgDeptChargeStanFiles(Plupload plupload,HttpServletRequest request,
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
						err_sb.append("第"+i+"行数据与第 "+j+"行数据】重复;");
					}
				}
	
				if (StringTool.isNotBlank(temp[0])) {
					
					errMap.put("year", temp[0]);
					
		    		mapVo.put("year", temp[0]);
				} else {
					
					err_sb.append("年度为空;");
					
				}
				
				if (StringTool.isNotBlank(temp[1])) {
					
					errMap.put("charge_stan_code", temp[1]);
					
		    		mapVo.put("charge_stan_code", temp[1]);
		    		
		    		mapVo.put("charge_stan_nature", "02");
	    		
		    		int count = budgDeptChargeStanService.queryChargeStanCodeExist(mapVo);
		    		
		    		if(count == 0 ){
		    			err_sb.append("填写费用标准不存在或其费用标准性质不是科室;");
		    		}
				
				} else {
					
					err_sb.append("费用标准编码为空;");
					
				}
				if (StringTool.isNotBlank(temp[2])) {					
					
					errMap.put("dept_code", temp[2]);
					
		    		mapVo.put("dept_code", temp[2]);
		    		
		    		//根据 费用标准编码 科室编码 查询  预算科室ID
		    		Map<String,Object> map = budgDeptChargeStanService.queryDeptID(mapVo);
		    		
		    		if(map != null){
		    			mapVo.put("dept_id", map.get("dept_id"));
		    			
		    		}else{
		    			
		    			err_sb.append("填写费用标准编码与科室编码不存对应关系;");
		    		}
		    		
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				
				if (StringTool.isNotBlank(temp[3])) {
					
					errMap.put("budg_value", Double.parseDouble(temp[3]));
					
		    		mapVo.put("budg_value", temp[3]);
					
				}else{
					err_sb.append("预算值数据为空;");
				}
				if (ExcelReader.validExceLColunm(temp, 4)) {
					
					errMap.put("remark", temp[4]);
					
		    		mapVo.put("remark", temp[4]);
					
				}else{
					
		    		mapVo.put("remark", "");
				}
				
				if(mapVo.get("dept_id") != null){
					//查询数据是否存在
					int count = budgDeptChargeStanService.queryDeptChargeStanExist(mapVo);
					
					if(count > 0 ){
						
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
				
				String dataJson = budgDeptChargeStanService.addBatch(addList);
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
	 * 批量添加数据 科室费用标准维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/addBatchBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptChargeStan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptChargeStan> list_err = new ArrayList<BudgDeptChargeStan>();
		
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
			
			BudgDeptChargeStan budgDeptChargeStan = new BudgDeptChargeStan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDeptChargeStan.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgDeptChargeStan.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("charge_stan_code"))) {
						
					budgDeptChargeStan.setCharge_stan_code((String)jsonObj.get("charge_stan_code"));
		    		mapVo.put("charge_stan_code", jsonObj.get("charge_stan_code"));
		    		} else {
						
						err_sb.append("费用标准编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgDeptChargeStan.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgDeptChargeStan.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgDeptChargeStan data_exc_extis = budgDeptChargeStanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptChargeStan.setError_type(err_sb.toString());
					
					list_err.add(budgDeptChargeStan);
					
				} else {
			  
					String dataJson = budgDeptChargeStanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptChargeStan data_exc = new BudgDeptChargeStan();
			
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		mapVo.put("charge_stan_nature","02")   ;
		
		List<Map<String,Object>> list = budgDeptChargeStanService.queryBudgDeptChargeStanData(mapVo);
		
		for(Map<String,Object> item : list){
			
			item.put("year", mapVo.get("year"));
			
			int  count = budgDeptChargeStanService.queryDeptChargeStanExist(item);
			
			if (count == 0){
				
				item.put("budg_value","")   ;
				
				item.put("remark","")   ;
				
		        listVo.add(item); 
			}
		}
		
		String budgWorkHosExecuteJson = null ;
		 
		if(listVo.size() > 0 ){
			
			  budgWorkHosExecuteJson = budgDeptChargeStanService.addBatch(listVo);
			  
		}else{
			
			budgWorkHosExecuteJson = "{\"error\":\"数据已全部生成,无需生成!\"}";
		}
	   
      return JSONObject.parseObject(budgWorkHosExecuteJson);	
	}
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanImportNewPage", method = RequestMethod.GET)
	public String budgHosChargeStanImportNewPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanImpo";

	}
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/budgDeptChargeStanImport", method = RequestMethod.POST)
	@ResponseBody
	public String budgDeptChargeStanImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgDeptChargeStanService.budgDeptChargeStanImport(mapVo);
			
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptstandard/collectBudgDeptChargeStan", method = RequestMethod.POST)
	@ResponseBody
	public String collectBudgDeptChargeStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = budgDeptChargeStanService.collectBudgDeptChargeStan(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
			
		}
	}
	
	

}

