/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.reportforms.monitoring;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMedIncomeExecute;
import com.chd.hrp.budg.service.budgincome.reportforms.monitoring.BudgMedInDeptYearMonService;
/**
 * 
 * @Description:
 * 科室医疗收入预算执行监控
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedInDeptYearMonController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedInDeptYearMonController.class);
	
	//引入Service服务
	@Resource(name = "budgMedInDeptYearMonService")
	private final BudgMedInDeptYearMonService budgMedInDeptYearMonService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonMainPage", method = RequestMethod.GET)
	public String budgMedInDeptYearMonMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonAddPage", method = RequestMethod.GET)
	public String budgMedInDeptYearMonAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/addBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedInDeptYearMon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgMedInDeptYearMonJson = budgMedInDeptYearMonService.add(mapVo);

		return JSONObject.parseObject(budgMedInDeptYearMonJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonUpdatePage", method = RequestMethod.GET)
	public String budgMedInDeptYearMonUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeExecute budgMedInDeptYearMon = new BudgMedIncomeExecute();
    
		budgMedInDeptYearMon = budgMedInDeptYearMonService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedInDeptYearMon.getGroup_id());
		mode.addAttribute("hos_id", budgMedInDeptYearMon.getHos_id());
		mode.addAttribute("copy_code", budgMedInDeptYearMon.getCopy_code());
		mode.addAttribute("year", budgMedInDeptYearMon.getYear());
		mode.addAttribute("dept_id", budgMedInDeptYearMon.getDept_id());
		mode.addAttribute("month", budgMedInDeptYearMon.getMonth());
		mode.addAttribute("subj_code", budgMedInDeptYearMon.getSubj_code());
		mode.addAttribute("amount", budgMedInDeptYearMon.getAmount());
		mode.addAttribute("remark", budgMedInDeptYearMon.getRemark());
		
		return "hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/updateBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedInDeptYearMon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgMedInDeptYearMonJson = budgMedInDeptYearMonService.update(mapVo);
		
		return JSONObject.parseObject(budgMedInDeptYearMonJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/addOrUpdateBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedInDeptYearMon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedInDeptYearMonJson ="";
		

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
	  
		budgMedInDeptYearMonJson = budgMedInDeptYearMonService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedInDeptYearMonJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/deleteBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedInDeptYearMon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("month", ids[5])   ;
				mapVo.put("subj_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedInDeptYearMonJson = budgMedInDeptYearMonService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedInDeptYearMonJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/queryBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedInDeptYearMon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedInDeptYearMon = budgMedInDeptYearMonService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedInDeptYearMon);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医疗收入执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonImportPage", method = RequestMethod.GET)
	public String budgMedInDeptYearMonImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/monitoring/budgMedInDeptYearMonImport";

	}
	
	 /**
	 * @Description 
	 * 导入数据 医疗收入执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/reportforms/monitoring/readBudgMedInDeptYearMonFiles",method = RequestMethod.POST)  
    public String readBudgMedInDeptYearMonFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeExecute> list_err = new ArrayList<BudgMedIncomeExecute>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeExecute budgMedInDeptYearMon = new BudgMedIncomeExecute();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgMedInDeptYearMon.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgMedInDeptYearMon.setDept_id(Long.valueOf(temp[4]));
		    		mapVo.put("dept_id", temp[4]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgMedInDeptYearMon.setMonth(temp[5]);
		    		mapVo.put("month", temp[5]);
					
					} else {
						
						err_sb.append("月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgMedInDeptYearMon.setSubj_code(temp[6]);
		    		mapVo.put("subj_code", temp[6]);
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgMedInDeptYearMon.setAmount(Double.valueOf(temp[7]));
		    		mapVo.put("amount", temp[7]);
					
					} else {
						
						err_sb.append("金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgMedInDeptYearMon.setRemark(temp[8]);
		    		mapVo.put("remark", temp[8]);
					
					} else {
						
						err_sb.append("说明为空  ");
						
					}
					 
					
					BudgMedIncomeExecute data_exc_extis = budgMedInDeptYearMonService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedInDeptYearMon.setError_type(err_sb.toString());
					
					list_err.add(budgMedInDeptYearMon);
					
				} else {
			  
					String dataJson = budgMedInDeptYearMonService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeExecute data_exc = new BudgMedIncomeExecute();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医疗收入执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/addBatchBudgMedInDeptYearMon", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMedInDeptYearMon(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeExecute> list_err = new ArrayList<BudgMedIncomeExecute>();
		
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
			
			BudgMedIncomeExecute budgMedInDeptYearMon = new BudgMedIncomeExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedInDeptYearMon.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedInDeptYearMon.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedInDeptYearMon.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedInDeptYearMon.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgMedInDeptYearMon.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedInDeptYearMon.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
					BudgMedIncomeExecute data_exc_extis = budgMedInDeptYearMonService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedInDeptYearMon.setError_type(err_sb.toString());
					
					list_err.add(budgMedInDeptYearMon);
					
				} else {
			  
					String dataJson = budgMedInDeptYearMonService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeExecute data_exc = new BudgMedIncomeExecute();
			
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

