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
import com.chd.hrp.budg.entity.BudgWorkHosYear;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
/**
 * 
 * @Description:
 * 医院年度业务预算零基预算 
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class ZeroHYbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ZeroHYbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosYearService")
	private final BudgWorkHosYearService budgWorkHosYearService = null;
	
	@Resource(name = "budgSelectService")
	private final BudgSelectService budgSelectService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgMainPage", method = RequestMethod.GET)
	public String zeroHYBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgAddPage", method = RequestMethod.GET)
	public String zeroHYBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgAdd";

	}

	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/saveZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("budg_value", ids[2]);
			mapVo.put("remark", ids[3]);
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0])-1)   ;
			map.put("index_code", ids[1]);
			
			Map<String,Object> incomeMap = JSONObject.parseObject(budgWorkHosYearService.queryLastYearWork(map)) ;
			
			if(incomeMap != null){
				if(incomeMap.get("last_year_workload") == null){
					mapVo.put("last_year_workload", "");
				}else{
					mapVo.put("last_year_workload", String.valueOf(incomeMap.get("last_year_workload")));
				}
			}else{
				mapVo.put("last_year_workload", "");
			}
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
			listVo.add(mapVo);
		}
       
		String budgHosIndependentSubjJson = null;
		
		try {
			
			budgHosIndependentSubjJson = budgWorkHosYearService.save(listVo);
			
		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage();
		}

		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/addZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addZeroHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("budg_value", ids[2]);
			mapVo.put("remark", ids[3]);
			
			//构建 查询上年业务量  参数 Map
			Map<String,Object> paraMap =  new HashMap<String,Object>();
			
			paraMap.put("group_id", SessionManager.getGroupId())   ;
			paraMap.put("hos_id", SessionManager.getHosId())   ;
			paraMap.put("copy_code", SessionManager.getCopyCode())   ;
			paraMap.put("budg_year", ids[0]);
			paraMap.put("index_code", ids[1]);
			paraMap.put("year", Integer.parseInt(ids[0])-1);
			
			paraMap.put("budg_level", "01");
			paraMap.put("edit_method", "01");
			
			//查询上年业务量
			Map<String,Object> map = JSONObject.parseObject(budgWorkHosYearService.queryLastYearWork(paraMap));
			
			if(map != null){
				if(map.get("last_year_workload") == null){
					mapVo.put("last_year_workload", "");
				}else{
					mapVo.put("last_year_workload", map.get("last_year_workload"));
				}
			}else{
				mapVo.put("last_year_workload", "");
			}
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
			listVo.add(mapVo);
		}
		
		String budgWorkHosYearJson = null ;
		
		budgWorkHosYearJson = budgWorkHosYearService.addBatch(listVo);
			
		return JSONObject.parseObject(budgWorkHosYearJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgUpdatePage", method = RequestMethod.GET)
	public String zeroHYBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgWorkHosYear budgWorkHosYear = new BudgWorkHosYear();
    
		budgWorkHosYear = budgWorkHosYearService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosYear.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosYear.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosYear.getCopy_code());
		mode.addAttribute("year", budgWorkHosYear.getYear());
		mode.addAttribute("index_code", budgWorkHosYear.getIndex_code());
		mode.addAttribute("count_value", budgWorkHosYear.getCount_value());
		mode.addAttribute("budg_value", budgWorkHosYear.getBudg_value());
		mode.addAttribute("remark", budgWorkHosYear.getRemark());
		mode.addAttribute("grow_rate", budgWorkHosYear.getGrow_rate());
		mode.addAttribute("grow_value", budgWorkHosYear.getGrow_value());
		mode.addAttribute("last_year_workload", budgWorkHosYear.getLast_year_workload());
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/zeroHYBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/updateZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateZeroHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("last_year_workload", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("remark", ids[4]);
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
			listVo.add(mapVo);
		}
		String budgWorkHosYearJson = budgWorkHosYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/addOrUpdateZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateZeroHYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosYearJson ="";

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
			budgWorkHosYearJson = budgWorkHosYearService.addOrUpdate(detailVo);
		}
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/deleteZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteZeroHYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("index_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosYearJson = budgWorkHosYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度业务预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/queryZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryZeroHYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosIndependentSubj = budgWorkHosYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度业务预算零基预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgImportPage", method = RequestMethod.GET)
	public String zeroHYBudgImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/zeroHYBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度业务预算零基预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\toptodown","医院年度业务预算零基预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度业务预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/readZeroHYBudgFiles",method = RequestMethod.POST)  
    public String readZeroHYBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkHosYear> list_err = new ArrayList<BudgWorkHosYear>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkHosYear budgWorkHosYear = new BudgWorkHosYear();
				
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
						
						budgWorkHosYear.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgWorkHosYear.setIndex_code(temp[1]);
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("budg_level", "01");
			    		
			    		mapVo.put("edit_method", "01");
			    		
			    		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
						if(index.length() == 2 ){
							err_sb.append("该指标编码在所填预算年度业务预算编制方案中编制方法不是零基预算;");
						}
					
					} else {
						
						err_sb.append("指标编码为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgWorkHosYear.setBudg_value(Double.valueOf(temp[3]));
			    		mapVo.put("budg_value", temp[3]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgWorkHosYear.setRemark(temp[4]);
			    		mapVo.put("remark", temp[4]);
					
					}else{
						mapVo.put("remark", "");
					}
					/*
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgWorkHosYear.setDept_suggest_sum(Double.valueOf(temp[4]));
			    		mapVo.put("dept_suggest_sum", temp[4]);
					
					}else{
						mapVo.put("dept_suggest_sum", "");
					}
					*/
					
					BudgWorkHosYear data_exc_extis = budgWorkHosYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYear);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("grow_value", "");
					mapVo.put("last_year_workload", "");
					mapVo.put("hos_suggest", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgWorkHosYearService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYear data_exc = new BudgWorkHosYear();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院年度业务预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/addBatchZeroHYBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchZeroHYBudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosYear> list_err = new ArrayList<BudgWorkHosYear>();
		
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
			
			BudgWorkHosYear budgWorkHosYear = new BudgWorkHosYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
						budgWorkHosYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
						budgWorkHosYear.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
						budgWorkHosYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
						budgWorkHosYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
						mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
						budgWorkHosYear.setRemark((String)jsonObj.get("remark"));
						mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
						budgWorkHosYear.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
						mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
						
						budgWorkHosYear.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
						mapVo.put("grow_value", jsonObj.get("grow_value"));
		    		} else {
						
						err_sb.append("增加额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_workload"))) {
						
						budgWorkHosYear.setLast_year_workload(Double.valueOf((String)jsonObj.get("last_year_workload")));
						mapVo.put("last_year_workload", jsonObj.get("last_year_workload"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
						budgWorkHosYear.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
						mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
		    		} else {
						
						err_sb.append("医院意见为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
						
						budgWorkHosYear.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
						mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
		    		} else {
						
						err_sb.append("科室意见汇总为空  ");
						
					}
					
					
					BudgWorkHosYear data_exc_extis = budgWorkHosYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYear);
					
				} else {
			  
					String dataJson = budgWorkHosYearService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYear data_exc = new BudgWorkHosYear();
			
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
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "01");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份


			mapVo.put("edit_method", "01");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算

			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkHosYearService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据.\"}");
			}
			
			for(Map<String, Object> item : list ){
				
				//根据主键  查询数据是否存在
				int count  = budgWorkHosYearService.queryDataExist(item);
				
				//不存在  组装 生成数据
				if(count == 0){
					
					//构建 查询上年业务量  参数 Map
					Map<String,Object> paraMap =  new HashMap<String,Object>();
					
					paraMap.put("group_id", SessionManager.getGroupId())   ;
					paraMap.put("hos_id", SessionManager.getHosId())   ;
					paraMap.put("copy_code", SessionManager.getCopyCode())   ;
					paraMap.put("budg_year", item.get("year"));
					paraMap.put("index_code", item.get("index_code"));
					paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
					
					paraMap.put("budg_level", "01");
					paraMap.put("edit_method", "01");
					
					//查询上年业务量
					Map<String,Object> map = JSONObject.parseObject(budgWorkHosYearService.queryLastYearWork(paraMap));
					
					if(map != null){
						if(map.get("last_year_workload") == null){
							item.put("last_year_workload","");
						}else{
							item.put("last_year_workload", map.get("last_year_workload"));
						}
					}else{
						item.put("last_year_workload","");
					}
					
					
					
					item.put("count_value", "");
					item.put("budg_value", "");
					item.put("remark", "");
					item.put("grow_rate", "");
					item.put("grow_value", "");
					item.put("hos_suggest", "");
					item.put("dept_suggest_sum", "");
			        listVo.add(item); 
				}
			}
			
			String budgWorkHosYearJson = null ;
				
			if(listVo.size() > 0 ){
				
				  budgWorkHosYearJson =budgWorkHosYearService.addBatch(listVo);
		         
			}else{
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			}
				
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 	
    
	/**
	 *  预算指标下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/zero/queryBudgIndex", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String index = budgWorkHosYearService.queryBudgIndex(mapVo);
		return index;

	}
	
}

