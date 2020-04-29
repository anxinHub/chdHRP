/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.uptodown.budg;
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
import com.chd.hrp.budg.entity.BudgWorkDeptYear;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptMonthService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
/**
 * 
 * @Description:
 * 科室年度业务预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkDeptYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkDeptYearController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearService")
	private final BudgWorkDeptYearService budgWorkDeptYearService = null;
	
	@Resource(name = "budgSelectService")
	private final BudgSelectService budgSelectService = null;
	
	@Resource(name = "budgWorkDeptMonthService")
	private final BudgWorkDeptMonthService budgWorkDeptMonthService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearMainPage", method = RequestMethod.GET)
	public String budgWorkDeptYearMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearAddPage", method = RequestMethod.GET)
	public String budgWorkDeptYearAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/saveBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgWorkDeptYearUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("resolve_rate", ids[6]);
			mapVo.put("count_value", ids[7]);
			mapVo.put("budg_value", ids[8]);
			mapVo.put("remark", ids[9]);
			mapVo.put("refer_value", ids[10]);
			mapVo.put("reason", ids[11]);
			mapVo.put("state", ids[12]);
			
			mapVo.put("rowNo", ids[13]);
			mapVo.put("flag", ids[14]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptYearJson = null ;
		
		try {
			
			budgWorkDeptYearJson = budgWorkDeptYearService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkDeptYearJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/addBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkDeptYearUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String str ="";
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("dept_name", ids[3]);
			if("-1".equals(ids[4])){
				
				str += ids[3] +"、" ;
				
			}else{
				
				mapVo.put("last_year_workload", ids[4]);
			}
			
			mapVo.put("grow_rate", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("remark", ids[7]);
			if("-1".equals(ids[8])){
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest", ids[8]);
			}
			
			mapVo.put("count_value", "");
			mapVo.put("grow_value", "");
			mapVo.put("resolve_rate", "");
			mapVo.put("hos_suggest_resolve", "");
			
			
			listVo.add(mapVo);
		}
		
		if(str != ""){
			
			return JSONObject.parseObject("{\"error\":\"添加失败！所填年度、指标的以下科室:【"+str.substring(0,str.length()-1)+"】上年业务量不存在.\",\"state\":\"false\"}");
			
		}else{
			
			String budgWorkDeptYearJson = budgWorkDeptYearService.addBatch(listVo);

			return JSONObject.parseObject(budgWorkDeptYearJson);
		}
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearUpdatePage", method = RequestMethod.GET)
	public String budgWorkDeptYearUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("year", mapVo.get("year"));
		//mode.addAttribute("index_code", mapVo.get("index_code"));
		
		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/updateBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkDeptYearUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("last_year_workload", ids[3]);
			mapVo.put("grow_rate", ids[4]);
			mapVo.put("grow_value", ids[5]);
			mapVo.put("resolve_rate", ids[6]);
			mapVo.put("count_value", ids[7]);
			mapVo.put("budg_value", ids[8]);
			mapVo.put("remark", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest", ids[10]);
			}
			
			mapVo.put("hos_suggest_resolve", "");
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptYearJson = budgWorkDeptYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/addOrUpdateBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkDeptYearUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptYearJson ="";
		

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
	  
		budgWorkDeptYearJson = budgWorkDeptYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/deleteBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkDeptYearUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dept_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/queryBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptYearUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String budgWorkDeptYear = budgWorkDeptYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYear);
		
	}
	
	/**
	 * @Description 
	 * 查询数据  预算分解页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/queryBudgWorkDeptYearResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptYearResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
		
        
		String budgWorkDeptYear = budgWorkDeptYearService.queryBudgWorkDeptYearResolve(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYear);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室年度业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearImportPage", method = RequestMethod.GET)
	public String budgWorkDeptYearImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/budgWorkDeptYearImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/uptodown/deptyearbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\toptodown","科室年度业务预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/uptodown/deptyearbudg/readBudgWorkDeptYearFiles",method = RequestMethod.POST)  
    public String readBudgWorkDeptYearFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List< Map<String, Object> > list_err = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String, Object> budgWorkDeptYear = new HashMap<String, Object>();
				
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
						
						budgWorkDeptYear.put("year",temp[0]);
			    		mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgWorkDeptYear.put("index_code",temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("budg_level", "03");
			    		
			    		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
			    		
						if(index.length() == 2){
							
							err_sb.append("该指标编码在所填预算年度的业务预算编制方案中不存在;");
						}
					
					} else {
						
						err_sb.append("指标编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgWorkDeptYear.put("dept_code",temp[2]);
						
						mapVo.put("dept_code",temp[2]);
						
						Map<String,Object> dept = budgWorkDeptMonthService.queryDeptExist(mapVo);
			    		
			    		if(dept != null ){
			    			
			    			mapVo.put("dept_id", dept.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("指标与预算科室对应关系不存在;");
			    		}
					
					} else {
						
						err_sb.append("部门编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgWorkDeptYear.put("last_year_workload",Double.valueOf(temp[3]));
						
			    		mapVo.put("last_year_workload", temp[3]);
						
					} else {
						
						err_sb.append("上年业务量为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgWorkDeptYear.put("grow_rate",Double.valueOf(temp[4]));
			    		mapVo.put("grow_rate", temp[4]);
						
						} else {
							
							err_sb.append("增长比例为空 ;");
							
						}
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgWorkDeptYear.put("budg_value",Double.valueOf(temp[5]));
			    		
						mapVo.put("budg_value", temp[5]);
					
					} else {
						
						mapVo.put("budg_value", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgWorkDeptYear.put("remark",temp[6]);
						
			    		mapVo.put("remark", temp[6]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						budgWorkDeptYear.put("dept_suggest",Double.valueOf(temp[7]));
			    		mapVo.put("dept_suggest", temp[7]);
					
					} else {
						
						mapVo.put("dept_suggest", "");
						
					}
					 
					
				BudgWorkDeptYear data_exc_extis = budgWorkDeptYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptYear.put("error_type",err_sb.toString());
					
					list_err.add(budgWorkDeptYear);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("grow_value", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("hos_suggest_resolve", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgWorkDeptYearService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object> ();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室年度业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/addBatchBudgWorkDeptYear", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkDeptYear(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptYear> list_err = new ArrayList<BudgWorkDeptYear>();
		
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
			
			BudgWorkDeptYear budgWorkDeptYear = new BudgWorkDeptYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkDeptYear.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptYear.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgWorkDeptYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgWorkDeptYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkDeptYear.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgWorkDeptYear.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
						
					budgWorkDeptYear.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
		    		mapVo.put("grow_value", jsonObj.get("grow_value"));
		    		} else {
						
						err_sb.append("增加额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgWorkDeptYear.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_workload"))) {
						
					budgWorkDeptYear.setLast_year_workload(Double.valueOf((String)jsonObj.get("last_year_workload")));
		    		mapVo.put("last_year_workload", jsonObj.get("last_year_workload"));
		    		} else {
						
						err_sb.append("上年业务量为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest_resolve"))) {
						
					budgWorkDeptYear.setHos_suggest_resolve(Double.valueOf((String)jsonObj.get("hos_suggest_resolve")));
		    		mapVo.put("hos_suggest_resolve", jsonObj.get("hos_suggest_resolve"));
		    		} else {
						
						err_sb.append("医院意见分解为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest"))) {
						
					budgWorkDeptYear.setDept_suggest(Double.valueOf((String)jsonObj.get("dept_suggest")));
		    		mapVo.put("dept_suggest", jsonObj.get("dept_suggest"));
		    		} else {
						
						err_sb.append("科室意见为空  ");
						
					}
					
					
				BudgWorkDeptYear data_exc_extis = budgWorkDeptYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptYear.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptYear);
					
				} else {
			  
					String dataJson = budgWorkDeptYearService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptYear data_exc = new BudgWorkDeptYear();
			
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
	 * 查询 所传 科室 的 上年业务量  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/queryDeptYearLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		String str  = budgWorkDeptYearService.queryDeptYearLastYearWork(mapVo) ;

		return str;

	}
	
	/**
	 * 03科室年度  计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/collectBudgWorkDeptYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkDeptYearUp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份
		mapVo.put("is_single_count", "0");//是否独立核算（IS_SINGLE_COUNT）：0否，1是
	
		String collect  = budgWorkDeptYearService.collectBudgWorkDeptYearUp(mapVo) ;

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
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

			mapVo.put("is_single_count", "0");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份
			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkDeptYearService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【科室年度业务预算编制方案】数据.或数据已全部生成\"}");
			}
			
			for(Map<String, Object> item : list ){
				if(item.get("dept_id") != null ){//指标 存在 对应科室关系时  生成相应数据  否则不生成
					//构建 查询上年业务量  参数 Map
					Map<String,Object> paraMap =  new HashMap<String,Object>();
					
					paraMap.put("group_id", SessionManager.getGroupId())   ;
					paraMap.put("hos_id", SessionManager.getHosId())   ;
					paraMap.put("copy_code", SessionManager.getCopyCode())   ;
					paraMap.put("budg_year", item.get("year"));
					paraMap.put("index_code", item.get("index_code"));
					paraMap.put("dept_id", item.get("dept_id"));
					paraMap.put("dept_code", item.get("dept_code"));
					paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
					
					paraMap.put("budg_level", "03");
					
					//查询上年业务量
					Map<String,Object> map = JSONObject.parseObject(budgWorkDeptYearService.queryDeptYearLastYearWork(paraMap));
					
					if(map != null){
						if(map.get("last_year_workload") == null){
							item.put("last_year_workload", "");
						}else{
							item.put("last_year_workload", String.valueOf(map.get("last_year_workload")));
						}
					}else{
						item.put("last_year_workload", "");
					}
					
					item.put("count_value", "");
					item.put("budg_value", "");
					item.put("remark", "");
					item.put("grow_rate", "");
					item.put("grow_value", "");
					item.put("resolve_rate", "");

					item.put("refer_value", "");
					item.put("reason", "");
					item.put("state", "");

			        listVo.add(item); 
				}
			}
			
			String budgWorkHosYearJson = null ;
			
			if(listVo.size() > 0 ){
				budgWorkHosYearJson =budgWorkDeptYearService.addBatch(listVo);
			}else{
				budgWorkHosYearJson = "{\"error\":\"指标对应科室数据未维护或数据已全部生成.\"}";;
			}
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 
	
	/**
	 * @Description 
	 * 下发,撤回,取消确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/issuedOrRetract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> issuedOrRetract(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("index_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			if(!"-1".equals(ids[8])){
				mapVo.put("state", ids[8]);
			}else{
				mapVo.put("state", "");
			}
			mapVo.put("flag", ids[9]);
			
			listVo.add(mapVo);
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.issuedOrRetract(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 确认不通过页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/disPassReasonPage", method = RequestMethod.GET)
	public String disPassReasonPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/deptyearbudg/disPassReasonPage";

	}
	
	/**
	 * @Description 
	 * 确认 (通过,不通过)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/deptyearbudg/passOrDisPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> passOrDisPass(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("index_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			mapVo.put("refer_value", ids[8]);
			mapVo.put("reason", ids[9]);
			mapVo.put("state", ids[10]);
			//mapVo.put("flag", ids[11]);
			
			listVo.add(mapVo);
	    }
	    
		String budgWorkDeptYearJson = budgWorkDeptYearService.passOrDisPass(listVo);
			
	    return JSONObject.parseObject(budgWorkDeptYearJson);
			
	}
}

