/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.workhosdbz;
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
import com.chd.hrp.budg.entity.BudgWorkHosDbz;
import com.chd.hrp.budg.service.business.compilationbasic.workdeptdbz.BudgWorkDeptDbzService;
import com.chd.hrp.budg.service.business.compilationbasic.workhosdbz.BudgWorkHosDbzService;
/**
 * 
 * @Description:
 * 医院单病种业务预算
 * @Table:
 * BUDG_WORK_HOS_DBZ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkHosDbzController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkHosDbzController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosDbzService")
	private final BudgWorkHosDbzService budgWorkHosDbzService = null;
	
	@Resource(name = "budgWorkDeptDbzService")
	private final BudgWorkDeptDbzService budgWorkDeptDbzService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzMainPage", method = RequestMethod.GET)
	public String budgWorkHosDbzMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzAddPage", method = RequestMethod.GET)
	public String budgWorkHosDbzAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 科室单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/saveBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgWorkHosDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("disease_code", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("budg_workload", "");
			}else{
				mapVo.put("budg_workload", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgWorkDeptDbzJson = null ;
		
		try {
			
			budgWorkDeptDbzJson = budgWorkHosDbzService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkDeptDbzJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkDeptDbzJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/addBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkHosDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());

   
		String budgWorkHosDbzJson = budgWorkHosDbzService.add(mapVo);

		return JSONObject.parseObject(budgWorkHosDbzJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzUpdatePage", method = RequestMethod.GET)
	public String budgWorkHosDbzUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkHosDbz budgWorkHosDbz = new BudgWorkHosDbz();
    
		budgWorkHosDbz = budgWorkHosDbzService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosDbz.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosDbz.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosDbz.getCopy_code());
		mode.addAttribute("year", budgWorkHosDbz.getYear());
		mode.addAttribute("disease_code", budgWorkHosDbz.getDisease_code());
		mode.addAttribute("insurance_code", budgWorkHosDbz.getInsurance_code());
		mode.addAttribute("budg_workload", budgWorkHosDbz.getBudg_workload());
		
		return "hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/updateBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();			
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("disease_code", ids[1]);
			mapVo.put("insurance_code", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("budg_workload", "");
			}else{
				mapVo.put("budg_workload", ids[3]);
			}
			
	      listVo.add(mapVo);	      
	    }            
	  
		String budgWorkHosDbzJson = budgWorkHosDbzService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkHosDbzJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/addOrUpdateBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkHosDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosDbzJson ="";
		

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
	  
		budgWorkHosDbzJson = budgWorkHosDbzService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosDbzJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/deleteBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosDbz(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("disease_code", ids[4])   ;
				mapVo.put("insurance_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosDbzJson = budgWorkHosDbzService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosDbzJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院单病种业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/queryBudgWorkHosDbz", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosDbz = budgWorkHosDbzService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosDbz);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院单病种业务预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzImportPage", method = RequestMethod.GET)
	public String budgWorkHosDbzImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院单病种业务预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/workhosdbz/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","全院单病种业务预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院单病种业务预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/workhosdbz/readBudgWorkHosDbzFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosDbzFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkHosDbz> list_err = new ArrayList<BudgWorkHosDbz>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkHosDbz budgWorkHosDbz = new BudgWorkHosDbz();
				
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
					 
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						budgWorkHosDbz.setYear(temp[0]);
			    		
						mapVo.put("year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgWorkHosDbz.setDisease_code(temp[1]);
						
			    		mapVo.put("disease_code", temp[1]);
			    		
			    		int count  = budgWorkDeptDbzService.queryDiseaseCodeExist(mapVo);
						
						if(count == 0){
							
							err_sb.append("病种编码不存在;");
						}
						
					} else {
						
						err_sb.append("病种编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgWorkHosDbz.setInsurance_code(temp[2]);
						
			    		mapVo.put("insurance_code", temp[2]);
		    		
			    		//根据病种编码、医保类型编码 查询 对应关系是否存在（查 医保单病种维护表 BUDG_YB_SINGLE_DISEASE）
						int count = budgWorkDeptDbzService.queryInsuranceCodeExist(mapVo);
						
						if(count == 0){
							
							err_sb.append("病种编码与医保类型对应关系未维护;");
						}
					
					} else {
						
						err_sb.append("医保类型编码为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgWorkHosDbz.setBudg_workload(Long.valueOf(temp[3]));
						
						mapVo.put("budg_workload", temp[3]);
					
					} else {
						
						err_sb.append("业务预算为空;");
						
					}
					 
				//根据主键查询 医院单病种业务预算是否已存在
				int count = budgWorkHosDbzService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosDbz.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosDbz);
					
				} else {
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgWorkHosDbzService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosDbz data_exc = new BudgWorkHosDbz();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//生成时 查询生成数据
		List<Map<String,Object>> list =  budgWorkHosDbzService.queryData(mapVo);
		
		for(Map<String,Object> item : list){
		
			item.put("budg_workload","");
			
			listVo.add(item);
		}
		 String budgWorkHosDbzJson  = null ;
		 
		if(listVo.size() > 0){
			
			budgWorkHosDbzJson =budgWorkHosDbzService.addBatch(listVo);
			
		}else{
			
			budgWorkHosDbzJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			
		}
		return JSONObject.parseObject(budgWorkHosDbzJson);	
	} 
	
	
	/**
	 * @Description 
	 * 汇总   汇总科室单病种业务预算至医院
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/collectBudgWorkHosDbz", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkHosDbz(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		//汇总时 查询 已存在的 科室单病种业务预算数据
		List<Map<String,Object>> updateList =  budgWorkHosDbzService.queryDeptDataExist(mapVo);
		
		//汇总时 查询 不存在的 科室单病种业务预算数据
		List<Map<String,Object>> addList = budgWorkHosDbzService.queryDeptDataNotExist(mapVo);
		
		
		if(updateList.size()> 0){
			
			budgWorkHosDbzService.updateBatch(updateList);
		}
		
		if(addList.size()> 0){
			
			budgWorkHosDbzService.addBatch(addList);
		}
			
		return JSONObject.parseObject("{\"msg\":\"操作成功!\",\"state\":\"true\"}");	
	} 
	
	/**
	 * @Description 
	 * 最新导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/workhosdbz/budgWorkHosDbzImportNewPage", method = RequestMethod.POST)
	@ResponseBody
	public String budgWorkHosDbzImportNewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgWorkHosDbzService.budgWorkHosDbzImportNewPage(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
}

