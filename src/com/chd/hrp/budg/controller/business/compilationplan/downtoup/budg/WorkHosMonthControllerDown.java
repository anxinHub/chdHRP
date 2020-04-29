/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.budg;
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
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosMonthService;
/**
 * 
 * @Description:
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WorkHosMonthControllerDown extends BaseController{
	
	private static Logger logger = Logger.getLogger(WorkHosMonthControllerDown.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosMonthService")
	private final BudgWorkHosMonthService budgWorkHosMonthService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthMainPage", method = RequestMethod.GET)
	public String budgWorkHosMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthAddPage", method = RequestMethod.GET)
	public String budgWorkHosMonthAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/addBudgWorkHosMonthDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkHosMonthDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
			
		}
		
		String last_year_workload = budgWorkHosMonthService.queryHosYearLastYearWork(mapVo);
		
		if(last_year_workload == null || "".equals(last_year_workload) ){
			
			mapVo.put("last_year_workload", "");
			
		}else{
			
			mapVo.put("last_year_workload", last_year_workload);
			
		}
		String budgWorkDeptMonthJson = budgWorkHosMonthService.add(mapVo);
		
		return JSONObject.parseObject(budgWorkDeptMonthJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthUpdatePageDown", method = RequestMethod.GET)
	public String budgWorkHosMonthUpdatePageDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		
		return "hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/updateBudgWorkHosMonthDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosMonthDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1])   ;
			mapVo.put("month", ids[2])   ;
			if("-1".equals(ids[3])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[3]);
			}
			
			listVo.add(mapVo);
		}
	  
		String budgWorkHosMonthJson = budgWorkHosMonthService.updateBatchBudgValue(listVo);
		
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}
	/**
	 * 预算分解维护 修改
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/updateBudgWorkHosMonthRateDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosMonthRateDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1])   ;
			mapVo.put("month", ids[2])   ;
			
			if("-1".equals(ids[3])){
				mapVo.put("grow_rate", "");
			}else{
				mapVo.put("grow_rate", ids[3]);
			}
			
			if("-1".equals(ids[4])){
				mapVo.put("resolve_rate", "");
			}else{
				mapVo.put("resolve_rate", ids[4]);
			}
			if("-1".equals(ids[5])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[5]);
			}
			if("-1".equals(ids[6])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[6]);
			}
			if("-1".equals(ids[7])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[7]);
			}
			
			mapVo.put("last_year_workload", ids[8]);
			
			mapVo.put("last_month_carried", "");
			
			mapVo.put("carried_next_month", "");
			
			listVo.add(mapVo);
		}
	  
		String budgWorkDeptMonthJson = budgWorkHosMonthService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptMonthJson);
	}
	/**
	 * @Description 
	 * 更新数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/addOrUpdateBudgWorkHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosMonthJson ="";
		

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
	  
		budgWorkHosMonthJson = budgWorkHosMonthService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/deleteBudgWorkHosMonthDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosMonthDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosMonthJson = budgWorkHosMonthService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosMonthJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/queryBudgWorkHosMonthDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosMonthDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosMonth = budgWorkHosMonthService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院月份业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthImportPage", method = RequestMethod.GET)
	public String budgWorkHosMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/hosmonthbudg/budgWorkHosMonthImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院月份业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/downtoup/hosmonthbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\downtotop","医院月份业务预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院月份业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/readBudgWorkHosMonthFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosMonthFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object> > list_err = new ArrayList<Map<String,Object> >();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object> ();
				
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
					 
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						errMap.put("year",temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						errMap.put("index_code",temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		int index = budgWorkHosMonthService.qureyBudgIndexExist(mapVo);
			    		
						if(index == 0 ){
							
							err_sb.append("指标编码不存在;");
						}
					
					} else {
						
						err_sb.append("指标编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						errMap.put("month",temp[2]);
						
			    		mapVo.put("month", temp[2]);
			    		
			    		
						if(!Arrays.asList(monthData).contains(temp[2])){
							
							err_sb.append("月份输入不合法（两位数字01-12）;");
						}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						errMap.put("last_year_workload",Double.valueOf(temp[3]));
						
			    		mapVo.put("last_year_workload", temp[3]);
						
					}else{
						
						err_sb.append("上年业务量为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						errMap.put("budg_value",Double.valueOf(temp[4]));
						
			    		mapVo.put("budg_value", temp[4]);
						
					}else{
						
						err_sb.append("预算值为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						errMap.put("remark",temp[5]);
						
			    		mapVo.put("remark", temp[5]);
						
					}else{
						
						errMap.put("remark","");
						
						mapVo.put("remark", "");
						
					}
					
					
					
					
				int count = budgWorkHosMonthService.queryDataExist(mapVo);
				
				if (count > 0) {
					
					err_sb.append("数据已经存在!");
					
				}
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				}else{
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("last_month_carried", "");
					mapVo.put("carried_next_month", "");
					
					addList.add(mapVo);
					
				}
			  
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgWorkHosMonthService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 不用  代码未调
	 * 批量添加数据 医院月份业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/addBatchBudgWorkHosMonth", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkHosMonth(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosMonth> list_err = new ArrayList<BudgWorkHosMonth>();
		
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
			
			BudgWorkHosMonth budgWorkHosMonth = new BudgWorkHosMonth();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkHosMonth.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkHosMonth.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgWorkHosMonth.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgWorkHosMonth.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgWorkHosMonth.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkHosMonth.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgWorkHosMonth.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgWorkHosMonth.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_workload"))) {
						
					budgWorkHosMonth.setLast_year_workload(Double.valueOf((String)jsonObj.get("last_year_workload")));
		    		mapVo.put("last_year_workload", jsonObj.get("last_year_workload"));
		    		} else {
						
						err_sb.append("上年业务量为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgWorkHosMonth.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgWorkHosMonth.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgWorkHosMonth data_exc_extis = budgWorkHosMonthService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosMonth.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosMonth);
					
				} else {
			  
					String dataJson = budgWorkHosMonthService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosMonth data_exc = new BudgWorkHosMonth();
			
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
	 * 查询 所传 指标的 上年业务量
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/queryHosYearLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))  - 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgWorkHosMonthService.queryHosYearLastYearWork(mapVo) ;

		return str;

	}
	
	/**
	 * 汇总
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/collectBudgWorkHosMonthDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkHosMonthDown(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String collect  = budgWorkHosMonthService.collectBudgWorkHosMonthUp(mapVo) ;

		return JSONObject.parseObject(collect);

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "02");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkHosMonthService.queryData(mapVo);
			
			String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			
			for(Map<String, Object> item : list ){
				
				for(String month : monthData){
					
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.putAll(item);
					
					map.put("month", month);
					//根据主键  查询数据是否存在
					int count  = budgWorkHosMonthService.queryDataExist(map);
					
					//不存在  组装 生成数据
					if(count == 0){
						
						//构建 查询上年业务量  参数 Map
						Map<String,Object> paraMap =  new HashMap<String,Object>();
						
						paraMap.put("group_id", SessionManager.getGroupId())   ;
						paraMap.put("hos_id", SessionManager.getHosId())   ;
						paraMap.put("copy_code", SessionManager.getCopyCode())   ;
						paraMap.put("budg_year", map.get("year"));
						paraMap.put("index_code", map.get("index_code"));
						paraMap.put("year", map.get("year"));//年度不作处理  在service中做处理
						paraMap.put("month", map.get("month"));
						
						
						//查询上年业务量
						String  last_year_workload = budgWorkHosMonthService.queryHosYearLastYearWork(paraMap);
						
						if(last_year_workload == null || "".equals(last_year_workload)){
							
							map.put("last_year_workload", "");
							
						}else{
							
							map.put("last_year_workload", last_year_workload);
						}	
							map.put("count_value", "");
							map.put("budg_value", "");
							map.put("remark", "");
							map.put("grow_rate", "");
							map.put("grow_value", "");
							map.put("resolve_rate", "");
							map.put("last_month_carried", "");
							map.put("carried_next_month", "");
					        listVo.add(map); 
					}
				}
				
			}
			
			String budgWorkHosYearJson = null ;
			
			if(listVo.size() > 0 ){
				
				  budgWorkHosYearJson =budgWorkHosMonthService.addBatch(listVo);
		         
			}else{
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			}
				
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 
	
	/**
	 * @Description 
	 * 医院月份业务 预算分解 页面 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/queryResolveDataDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResolveDataDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		
		String budgWorkDeptMonth = budgWorkHosMonthService.queryResolveData(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptMonth);
		
	}
	
	/**
	 * 分解计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/hosmonthbudg/resolveDataDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resolveData(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String collect  = null ;
		try {
			collect = budgWorkHosMonthService.resolveData(mapVo) ;
		} catch (Exception e) {
			collect =e.getMessage() ;
		}
		

		return JSONObject.parseObject(collect);

	}
}

