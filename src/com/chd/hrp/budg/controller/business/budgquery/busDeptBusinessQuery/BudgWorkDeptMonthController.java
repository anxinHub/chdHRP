/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgquery.busDeptBusinessQuery;
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
import com.chd.hrp.budg.entity.BudgWorkDeptMonth;
import com.chd.hrp.budg.service.business.budgquery.busDeptBusinessQuery.BudgWorkDeptMService;
/**
 * 
 * @Description:
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkDeptMonthController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkDeptMonthController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptMService")
	private final BudgWorkDeptMService budgWorkDeptMService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/busdeptwholeBusinessQueryMainPage", method = RequestMethod.GET)
	public String budgWorkDeptMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/business/budgquery/busDeptBusinessQuery/budgWorkDeptMonthMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/budgWorkDeptMonthAddPage", method = RequestMethod.GET)
	public String budgWorkDeptMonthAddPage(Model mode) throws Exception {

		return "hrp/budg/budgworkdeptmonth/budgWorkDeptMonthAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/addBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgWorkDeptMonthJson = budgWorkDeptMService.add(mapVo);

		return JSONObject.parseObject(budgWorkDeptMonthJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/budgWorkDeptMonthUpdatePage", method = RequestMethod.GET)
	public String budgWorkDeptMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptMonth budgWorkDeptMonth = new BudgWorkDeptMonth();
    
		budgWorkDeptMonth = budgWorkDeptMService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptMonth.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptMonth.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptMonth.getCopy_code());
		mode.addAttribute("year", budgWorkDeptMonth.getYear());
		mode.addAttribute("index_code", budgWorkDeptMonth.getIndex_code());
		mode.addAttribute("dept_id", budgWorkDeptMonth.getDept_id());
		mode.addAttribute("month", budgWorkDeptMonth.getMonth());
		mode.addAttribute("count_value", budgWorkDeptMonth.getCount_value());
		mode.addAttribute("budg_value", budgWorkDeptMonth.getBudg_value());
		mode.addAttribute("remark", budgWorkDeptMonth.getRemark());
		mode.addAttribute("grow_rate", budgWorkDeptMonth.getGrow_rate());
		mode.addAttribute("resolve_rate", budgWorkDeptMonth.getResolve_rate());
		mode.addAttribute("last_year_workload", budgWorkDeptMonth.getLast_year_workload());
		mode.addAttribute("last_month_carried", budgWorkDeptMonth.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgWorkDeptMonth.getCarried_next_month());
		
		return "hrp/budg/budgworkdeptmonth/budgWorkDeptMonthUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/updateBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgWorkDeptMonthJson = budgWorkDeptMService.update(mapVo);
		
		return JSONObject.parseObject(budgWorkDeptMonthJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/addOrUpdateBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptMonthJson ="";
		

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
	  
		budgWorkDeptMonthJson = budgWorkDeptMService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptMonthJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/deleteBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkDeptMonth(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("month", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptMonthJson = budgWorkDeptMService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptMonthJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室月份业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/queryBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkDeptMonth = budgWorkDeptMService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室月份业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/budgWorkDeptMonthImportPage", method = RequestMethod.GET)
	public String budgWorkDeptMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/budgworkdeptmonth/budgWorkDeptMonthImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室月份业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgworkdeptmonth/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","科室月份业务预算.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室月份业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgworkdeptmonth/readBudgWorkDeptMonthFiles",method = RequestMethod.POST)  
    public String readBudgWorkDeptMonthFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkDeptMonth> list_err = new ArrayList<BudgWorkDeptMonth>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkDeptMonth budgWorkDeptMonth = new BudgWorkDeptMonth();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgWorkDeptMonth.setYear(temp[3]);
		    		mapVo.put("year", temp[3]);
					
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgWorkDeptMonth.setIndex_code(temp[4]);
		    		mapVo.put("index_code", temp[4]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgWorkDeptMonth.setDept_id(Long.valueOf(temp[5]));
		    		mapVo.put("dept_id", temp[5]);
					
					} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgWorkDeptMonth.setMonth(temp[6]);
		    		mapVo.put("month", temp[6]);
					
					} else {
						
						err_sb.append("月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgWorkDeptMonth.setCount_value(Double.valueOf(temp[7]));
		    		mapVo.put("count_value", temp[7]);
					
					} else {
						
						err_sb.append("计算值为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgWorkDeptMonth.setBudg_value(Double.valueOf(temp[8]));
		    		mapVo.put("budg_value", temp[8]);
					
					} else {
						
						err_sb.append("预算值为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgWorkDeptMonth.setRemark(temp[9]);
		    		mapVo.put("remark", temp[9]);
					
					} else {
						
						err_sb.append("说明为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgWorkDeptMonth.setGrow_rate(Double.valueOf(temp[10]));
		    		mapVo.put("grow_rate", temp[10]);
					
					} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgWorkDeptMonth.setResolve_rate(Double.valueOf(temp[11]));
		    		mapVo.put("resolve_rate", temp[11]);
					
					} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgWorkDeptMonth.setLast_year_workload(Double.valueOf(temp[12]));
		    		mapVo.put("last_year_workload", temp[12]);
					
					} else {
						
						err_sb.append("上年业务量为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgWorkDeptMonth.setLast_month_carried(Double.valueOf(temp[13]));
		    		mapVo.put("last_month_carried", temp[13]);
					
					} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					budgWorkDeptMonth.setCarried_next_month(Double.valueOf(temp[14]));
		    		mapVo.put("carried_next_month", temp[14]);
					
					} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					 
					
				BudgWorkDeptMonth data_exc_extis = budgWorkDeptMService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptMonth.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptMonth);
					
				} else {
			  
					String dataJson = budgWorkDeptMService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptMonth data_exc = new BudgWorkDeptMonth();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室月份业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgworkdeptmonth/addBatchBudgWorkDeptMonth", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkDeptMonth(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptMonth> list_err = new ArrayList<BudgWorkDeptMonth>();
		
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
			
			BudgWorkDeptMonth budgWorkDeptMonth = new BudgWorkDeptMonth();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptMonth.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkDeptMonth.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptMonth.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgWorkDeptMonth.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgWorkDeptMonth.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgWorkDeptMonth.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkDeptMonth.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgWorkDeptMonth.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgWorkDeptMonth.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_workload"))) {
						
					budgWorkDeptMonth.setLast_year_workload(Double.valueOf((String)jsonObj.get("last_year_workload")));
		    		mapVo.put("last_year_workload", jsonObj.get("last_year_workload"));
		    		} else {
						
						err_sb.append("上年业务量为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgWorkDeptMonth.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgWorkDeptMonth.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgWorkDeptMonth data_exc_extis = budgWorkDeptMService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptMonth.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptMonth);
					
				} else {
			  
					String dataJson = budgWorkDeptMService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptMonth data_exc = new BudgWorkDeptMonth();
			
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
	 * 科室预算值连接页面跳转查看,针对月份
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/budgWorkDeptMonthUpdPage", method = RequestMethod.GET)
	public String budgWorkDeptMonthLinkPage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		mode.addAttribute("month", mapVo.get("month"));
		return "hrp/budg/business/budgquery/busDeptBusinessQuery/budgWorkDeptLinkSum";
		
	}
	
	/**
	 * 科室预算值连接页面跳转查看,针对年度
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/budgWorkDeptYearUpdPage", method = RequestMethod.GET)
	public String budgWorkDeptYearUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("sum_year", mapVo.get("sum_year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		return "hrp/budg/business/budgquery/busDeptBusinessQuery/budgWorkDeptLink";			
	}

	/**
    * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/queryBudgWorkCheckDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDept(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
			mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			
			String budgWorkHosMonth="";
			//这一部分最好放到service层进行处理,少写两个方法
			if(!((String)mapVo.get("sum_year")==null&&(String)mapVo.get("sum_year")=="")){
				//抓取预算值BUDG_WORK_DEPT_YEAR_COPY表中抓取数据	
			     budgWorkHosMonth = budgWorkDeptMService.queryDeptYearCopy(getPage(mapVo));
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
	@RequestMapping(value = "/hrp/budg/business/budgquery/busDeptBusinessQuery/queryBudgWorkCheckDeptSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgWorkHosMonth="";
			//抓取数据从BUDG_WORK_DEPT_MONTH_COPY表中抓取数据
			budgWorkHosMonth = budgWorkDeptMService.queryDeptMonthCopy(getPage(mapVo));
		
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
    
}

