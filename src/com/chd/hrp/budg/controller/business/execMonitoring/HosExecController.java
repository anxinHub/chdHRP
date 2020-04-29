/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.execMonitoring;
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
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.service.business.budgquery.busHosBusinessQuery.BudgWorkHosMService;
import com.chd.hrp.budg.service.business.execMonitoring.DeptExecService;
import com.chd.hrp.budg.service.business.execMonitoring.HosExecService;
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
public class HosExecController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HosExecController.class);
	
	//引入Service服务
	@Resource(name = "hosExecService")
	private final HosExecService hosExecService = null;
	
	//引入Service服务
	@Resource(name = "budgWorkHosMService")
	private final BudgWorkHosMService budgWorkHosMService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/hosExecMainPage", method = RequestMethod.GET)
	public String budgWorkHosMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/business/execMonitoring/hosExecMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/budgWorkHosMonthAddPage", method = RequestMethod.GET)
	public String budgWorkHosMonthAddPage(Model mode) throws Exception {

		return "hrp/budg/budgworkhosmonth/budgWorkHosMonthAdd";

	}*/

	/**
	 * @Description 
	 * 添加数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/addBudgWorkHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgWorkHosMonthJson = hosExecService.add(mapVo);

		return JSONObject.parseObject(budgWorkHosMonthJson);
		
	}*/
/**
	 * @Description 
	 * 更新跳转页面 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/budgWorkHosMonthUpdatePage", method = RequestMethod.GET)
	public String budgWorkHosMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkHosMonth budgWorkHosMonth = new BudgWorkHosMonth();
    
		budgWorkHosMonth = hosExecService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosMonth.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosMonth.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosMonth.getCopy_code());
		mode.addAttribute("year", budgWorkHosMonth.getYear());
		mode.addAttribute("index_code", budgWorkHosMonth.getIndex_code());
		mode.addAttribute("month", budgWorkHosMonth.getMonth());
		mode.addAttribute("count_value", budgWorkHosMonth.getCount_value());
		mode.addAttribute("budg_value", budgWorkHosMonth.getBudg_value());
		mode.addAttribute("remark", budgWorkHosMonth.getRemark());
		mode.addAttribute("grow_rate", budgWorkHosMonth.getGrow_rate());
		mode.addAttribute("resolve_rate", budgWorkHosMonth.getResolve_rate());
		mode.addAttribute("last_year_workload", budgWorkHosMonth.getLast_year_workload());
		mode.addAttribute("last_month_carried", budgWorkHosMonth.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgWorkHosMonth.getCarried_next_month());
		
		return "hrp/budg/budgworkhosmonth/budgWorkHosMonthUpdate";
	}*/
		
	/**
	 * @Description 
	 * 更新数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/updateBudgWorkHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgWorkHosMonthJson = hosExecService.update(mapVo);
		
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}
	*/
	/**
	 * @Description 
	 * 更新数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/addOrUpdateBudgWorkHosMonth", method = RequestMethod.POST)
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
	  
		budgWorkHosMonthJson = hosExecService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosMonthJson);
	}*/
	
	/**
	 * @Description 
	 * 删除数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/deleteBudgWorkHosMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosMonth(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("month", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosMonthJson = hosExecService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosMonthJson);
			
	}*/
	
	/**
	 * @Description 
	 * 查询数据 医院月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/queryHosExecData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkHosMonth = hosExecService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	
	/**
	 * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/budgWorkHosExecuteLinkPage", method = RequestMethod.GET)
	public String budgWorkCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
	mode.addAttribute("hos_id",SessionManager.getHosId());
	mode.addAttribute("copy_code",SessionManager.getCopyCode());
	mode.addAttribute("year", mapVo.get("year"));
	mode.addAttribute("index_code", mapVo.get("index_code"));
	mode.addAttribute("month", mapVo.get("month"));
	return "hrp/budg/business/execMonitoring/budgWorkHosLinkExecuteSum";
		
	}
	/**
	 * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/budgWorkHosExecuteLinkPageSum", method = RequestMethod.GET)
	public String budgWorkHosLinkPageSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("sum_year", mapVo.get("sum_year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		return "hrp/budg/business/execMonitoring/budgWorkHosExecuteLink";
		
	}
	
	
	/**
	* 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/queryBudgWorkCheckDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptMonth(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgWorkHosMonth="";
		//这一部分最好放到service层进行处理,少写两个方法
		if(!((String)mapVo.get("sum_year")==null&&(String)mapVo.get("sum_year")=="")){
			//抓取预算值BUDG_WORK_HOS_YEAR_COPY表中抓取数据	
		     budgWorkHosMonth = budgWorkHosMService.queryHosYearCopy(getPage(mapVo));
		}
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	/**
    * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/execMonitoring/queryBudgWorkCheckDeptMonthSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptMonthSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgWorkHosMonth="";
			//抓取数据从BUDG_WORK_HOS_MONTH_COPY表中抓取数据
			budgWorkHosMonth = budgWorkHosMService.queryHosMonthCopy(getPage(mapVo));
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院月份业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/budgWorkHosMonthImportPage", method = RequestMethod.GET)
	public String budgWorkHosMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/budgworkhosmonth/budgWorkHosMonthImport";

	}
	*/
	/**
	 * @Description 
	 * 下载导入模版 医院月份业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	/* @RequestMapping(value="hrp/budg/budgworkhosmonth/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","医院月份业务预算.xls");
	    
	    return null; 
	 }*/
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
	/*@RequestMapping(value="/hrp/budg/budgworkhosmonth/readBudgWorkHosMonthFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosMonthFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkHosMonth> list_err = new ArrayList<BudgWorkHosMonth>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkHosMonth budgWorkHosMonth = new BudgWorkHosMonth();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgWorkHosMonth.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgWorkHosMonth.setIndex_code(temp[4]);
		    		mapVo.put("index_code", temp[4]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgWorkHosMonth.setMonth(temp[5]);
		    		mapVo.put("month", temp[5]);
					
					} else {
						
						err_sb.append("月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgWorkHosMonth.setCount_value(Double.valueOf(temp[6]));
		    		mapVo.put("count_value", temp[6]);
					
					} else {
						
						err_sb.append("计算值为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgWorkHosMonth.setBudg_value(Double.valueOf(temp[7]));
		    		mapVo.put("budg_value", temp[7]);
					
					} else {
						
						err_sb.append("预算值为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgWorkHosMonth.setRemark(temp[8]);
		    		mapVo.put("remark", temp[8]);
					
					} else {
						
						err_sb.append("说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgWorkHosMonth.setGrow_rate(Double.valueOf(temp[9]));
		    		mapVo.put("grow_rate", temp[9]);
					
					} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgWorkHosMonth.setResolve_rate(Double.valueOf(temp[10]));
		    		mapVo.put("resolve_rate", temp[10]);
					
					} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgWorkHosMonth.setLast_year_workload(Double.valueOf(temp[11]));
		    		mapVo.put("last_year_workload", temp[11]);
					
					} else {
						
						err_sb.append("上年业务量为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgWorkHosMonth.setLast_month_carried(Double.valueOf(temp[12]));
		    		mapVo.put("last_month_carried", temp[12]);
					
					} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgWorkHosMonth.setCarried_next_month(Double.valueOf(temp[13]));
		    		mapVo.put("carried_next_month", temp[13]);
					
					} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					 
					
				BudgWorkHosMonth data_exc_extis = hosExecService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosMonth.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosMonth);
					
				} else {
			  
					String dataJson = hosExecService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosMonth data_exc = new BudgWorkHosMonth();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  */
   /**
	 * @Description 
	 * 批量添加数据 医院月份业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	/*@RequestMapping(value = "/hrp/budg/budgworkhosmonth/addBatchBudgWorkHosMonth", method = RequestMethod.POST)
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
					
					
				BudgWorkHosMonth data_exc_extis = hosExecService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosMonth.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosMonth);
					
				} else {
			  
					String dataJson = hosExecService.add(mapVo);
					
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
    */
}

