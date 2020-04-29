/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.workdeptdbz;
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
import com.chd.hrp.budg.entity.BudgWorkDeptDbz;
import com.chd.hrp.budg.service.business.compilationbasic.workdeptdbz.BudgWorkDeptDbzService;
/**
 * 
 * @Description:
 * 科室单病种业务预算
 * @Table:
 * BUDG_WORK_DEPT_DBZ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkDeptDbzController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkDeptDbzController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptDbzService")
	private final BudgWorkDeptDbzService budgWorkDeptDbzService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzMainPage", method = RequestMethod.GET)
	public String budgWorkDeptDbzMainPage(Model mode) throws Exception {
		return "hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzAddPage", method = RequestMethod.GET)
	public String budgWorkDeptDbzAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/saveBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgWorkDeptDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("dept_id", ids[1]);
			mapVo.put("disease_code", ids[2]);
			mapVo.put("insurance_code", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("budg_workload", "");
			}else{
				mapVo.put("budg_workload", ids[4]);
			}
			
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptDbzJson = null ;
		
		try {
			
			budgWorkDeptDbzJson = budgWorkDeptDbzService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkDeptDbzJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkDeptDbzJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/addBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkDeptDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());

			
		String budgWorkDeptDbzJson = budgWorkDeptDbzService.add(mapVo);
		return JSONObject.parseObject(budgWorkDeptDbzJson);		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzUpdatePage", method = RequestMethod.GET)
	public String budgWorkDeptDbzUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptDbz budgWorkDeptDbz = new BudgWorkDeptDbz();
    
		budgWorkDeptDbz = budgWorkDeptDbzService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptDbz.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptDbz.getHos_id());
		mode.addAttribute("dept_id", budgWorkDeptDbz.getDept_id());
		mode.addAttribute("copy_code", budgWorkDeptDbz.getCopy_code());
		mode.addAttribute("year", budgWorkDeptDbz.getYear());
		mode.addAttribute("disease_code", budgWorkDeptDbz.getDisease_code());
		mode.addAttribute("insurance_code", budgWorkDeptDbz.getInsurance_code());
		mode.addAttribute("budg_workload", budgWorkDeptDbz.getBudg_workload());
		
		return "hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/updateBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkDeptDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("dept_id", ids[1]);
			mapVo.put("disease_code", ids[2]);
			mapVo.put("insurance_code", ids[3]);
			if("-1".equals(ids[4])){
				
				mapVo.put("budg_workload", "");
				
			}else{
				
				mapVo.put("budg_workload", ids[4]);
			}
	        listVo.add(mapVo);	      
	    }    
	  
		String budgWorkDeptDbzJson = budgWorkDeptDbzService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptDbzJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/addOrUpdateBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkDeptDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptDbzJson ="";
		

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
	  
		budgWorkDeptDbzJson = budgWorkDeptDbzService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptDbzJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/deleteBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkDeptDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("dept_id", ids[2])   ;
				mapVo.put("copy_code", ids[3])   ;
				mapVo.put("year", ids[4])   ;
				mapVo.put("disease_code", ids[5])   ;
				mapVo.put("insurance_code", ids[6]);
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptDbzJson = budgWorkDeptDbzService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptDbzJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/queryBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkDeptDbz = budgWorkDeptDbzService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptDbz);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室单病种业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzImportPage", method = RequestMethod.GET)
	public String budgWorkDeptDbzImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/workdeptdbz/budgWorkDeptDbzImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室单病种业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/workdeptdbz/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","科室单病种业务预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室单病种业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/workdeptdbz/readBudgWorkDeptDbzFiles",method = RequestMethod.POST)  
    public String readBudgWorkDeptDbzFiles(Plupload plupload,HttpServletRequest request,
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
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[3].equals(error[3])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
		    		if (ExcelReader.validExceLColunm(temp, 0)) {
		    			
		    				errMap.put("year", temp[0]);
		    				
		    				mapVo.put("year", temp[0]);
						
						} else {
							
							err_sb.append("年度为空;");
							
						}
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						errMap.put("disease_code", temp[1]);
						
						mapVo.put("disease_code", temp[1]);
						
						int count  = budgWorkDeptDbzService.queryDiseaseCodeExist(mapVo);
						
						if(count == 0){
							
							err_sb.append("病种编码不存在;");
						}
					
					} else {
						
						err_sb.append("病种编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						errMap.put("dept_code", temp[2]);
						
						mapVo.put("dept_code", temp[2]);
						
						//根据病种编码、科室编码 查询 对应关系是否存在（查 科室单病种维护表  BUDG_DEPT_SINGLE_DISEASE）
						Map<String,Object> map  = budgWorkDeptDbzService.queryDeptCodeExist(mapVo);
						
						if(map != null){
							
							mapVo.put("dept_id", map.get("dept_id"));
							
						}else{
							err_sb.append("病种编码与预算科室对应关系未维护;");
						}
					
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						errMap.put("insurance_code", temp[3]);
						
						mapVo.put("insurance_code", temp[3]);
						
						//根据病种编码、医保类型编码 查询 对应关系是否存在（查 医保单病种维护表 BUDG_YB_SINGLE_DISEASE）
						int count = budgWorkDeptDbzService.queryInsuranceCodeExist(mapVo);
						
						if(count == 0){
							
							err_sb.append("病种编码与医保类型对应关系未维护;");
						}
						
					} else {
						
						err_sb.append("医保类型编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						errMap.put("budg_workload", temp[4]);
						
						mapVo.put("budg_workload", temp[4]);
					
					} else {
						
						err_sb.append("业务预算为空;");
						
					}
					 
				//根据主键查询 科室单病种业务预算数据是否存在
					
				if(mapVo.get("dept_id") != null ){
					
					int count  = budgWorkDeptDbzService.queryDataExist(mapVo);
					
					if (count > 0 ) {
						
						err_sb.append("数据已经存在！ ");
						
					}
				}
				
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgWorkDeptDbzService.addBatch(addList);
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
	 * @Description 
	 * 批量添加数据 科室单病种业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/addBatchBudgWorkDeptDbz", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkDeptDbz(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptDbz> list_err = new ArrayList<BudgWorkDeptDbz>();
		
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
			
			BudgWorkDeptDbz budgWorkDeptDbz = new BudgWorkDeptDbz();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptDbz.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptDbz.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("disease_code"))) {
						
					budgWorkDeptDbz.setDisease_code((String)jsonObj.get("disease_code"));
		    		mapVo.put("disease_code", jsonObj.get("disease_code"));
		    		} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("insurance_code"))) {
						
					budgWorkDeptDbz.setInsurance_code((String)jsonObj.get("insurance_code"));
		    		mapVo.put("insurance_code", jsonObj.get("insurance_code"));
		    		} else {
						
						err_sb.append("医保类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_workload"))) {
						
					budgWorkDeptDbz.setBudg_workload(Long.valueOf((String)jsonObj.get("budg_workload")));
		    		mapVo.put("budg_workload", jsonObj.get("budg_workload"));
		    		} else {
						
						err_sb.append("业务预算为空  ");
						
					}
					
					
				BudgWorkDeptDbz data_exc_extis = budgWorkDeptDbzService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptDbz.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptDbz);
					
				} else {
			  
					String dataJson = budgWorkDeptDbzService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptDbz data_exc = new BudgWorkDeptDbz();
			
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			//生成时 查询生成数据
			List<Map<String, Object>> list= budgWorkDeptDbzService.queryData(mapVo);
			
			for(Map<String, Object> item : list){
				
				item.put("budg_workload","");
				
				listVo.add(item);
			}
			 String budgWorkDeptDbzJson  = null ;
			 
			if(listVo.size() > 0){
				
				budgWorkDeptDbzJson =budgWorkDeptDbzService.addBatch(listVo);
				
			}else{
				budgWorkDeptDbzJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
				
			}
	      return JSONObject.parseObject(budgWorkDeptDbzJson);	
	} 	
	
	/**
	 * 预算科室 下拉框 添加页面用（根据病种编码 查询科室单病种维护表   BUDG_DEPT_SINGLE_DISEASE）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/queryBudgDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String dept = budgWorkDeptDbzService.queryBudgDeptDict(mapVo);
		return dept;

	}
	/**
	 * 医保类型下拉框 添加页面用（根据病种编码 查询医保单病种维护表  BUDG_YB_SINGLE_DISEASE）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/queryBudgYBTY", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgYBTY(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String ybType = budgWorkDeptDbzService.queryBudgYBTY(mapVo);
		
		return ybType;

	}
	
	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/queryBudgSingleDC", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSingleDC(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String typeNature = budgWorkDeptDbzService.queryBudgSingleDC(mapVo);
		return typeNature;

	}
	
	
	
	/**
	 * @Description 
	 * 最新导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workdeptdbz/budgWorkHosDbzImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgWorkHosDbzImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgWorkDeptDbzService.budgWorkHosDbzImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
	
}

