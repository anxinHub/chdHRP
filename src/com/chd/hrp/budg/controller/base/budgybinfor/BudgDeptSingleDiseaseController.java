/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgybinfor;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDeptSingleDisease;
import com.chd.hrp.budg.service.base.budgybinfor.BudgDeptSingleDiseaseService;
/**
 * 
 * @Description:
 * 科室单病种维护
 * @Table:
 * BUDG_DEPT_SINGLE_DISEASE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptSingleDiseaseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptSingleDiseaseController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptSingleDiseaseService")
	private final BudgDeptSingleDiseaseService budgDeptSingleDiseaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDisMainPage", method = RequestMethod.GET)
	public String budgDeptSingleDisMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseAddPage", method = RequestMethod.GET)
	public String budgDeptSingleDiseaseAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/addBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgDeptSingleDiseaseJson = budgDeptSingleDiseaseService.add(mapVo);

		return JSONObject.parseObject(budgDeptSingleDiseaseJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseUpdatePage", method = RequestMethod.GET)
	public String budgDeptSingleDiseaseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptSingleDisease budgDeptSingleDisease = new BudgDeptSingleDisease();
    
		budgDeptSingleDisease = budgDeptSingleDiseaseService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptSingleDisease.getGroup_id());
		mode.addAttribute("hos_id", budgDeptSingleDisease.getHos_id());
		mode.addAttribute("copy_code", budgDeptSingleDisease.getCopy_code());
		mode.addAttribute("dept_id", budgDeptSingleDisease.getDept_id());
		mode.addAttribute("disease_code", budgDeptSingleDisease.getDisease_code());
		
		return "hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/updateBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgDeptSingleDiseaseJson = budgDeptSingleDiseaseService.update(mapVo);
		
		return JSONObject.parseObject(budgDeptSingleDiseaseJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/addOrUpdateBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptSingleDiseaseJson ="";
		

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
	  
		budgDeptSingleDiseaseJson = budgDeptSingleDiseaseService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptSingleDiseaseJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/deleteBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptSingleDisease(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("dept_id", ids[3])   ;
				mapVo.put("disease_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptSingleDiseaseJson = budgDeptSingleDiseaseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptSingleDiseaseJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室单病种维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/queryBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptSingleDisease(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDeptSingleDisease = budgDeptSingleDiseaseService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptSingleDisease);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室单病种维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseImportPage", method = RequestMethod.GET)
	public String budgDeptSingleDiseaseImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgybinfor/budgdeptsingledisease/budgDeptSingleDiseaseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室单病种维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgybinfor/budgdeptsingledisease/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		 printTemplate(request,response,"budg\\base","科室单病种维护模板.xlsx");
	 
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室单病种维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgybinfor/budgdeptsingledisease/readBudgDeptSingleDiseaseFiles",method = RequestMethod.POST)  
    public String readBudgDeptSingleDiseaseFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDeptSingleDisease> list_err = new ArrayList<BudgDeptSingleDisease>();
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		Map<String,Object> paraMap = new  HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		paraMap.put("hos_id", SessionManager.getHosId());  						 
		paraMap.put("copy_code", SessionManager.getCopyCode());  
		
		// 预算科室基本信息（根据code 匹配ID）
		List<Map<String,Object>> deptData = budgDeptSingleDiseaseService.queryDeptData(paraMap);
		
		//病种信息(校验数据是否存在)
		List<Map<String,Object>> diseaseData = budgDeptSingleDiseaseService.queryDiseaseData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDeptSingleDisease budgDeptSingleDisease = new BudgDeptSingleDisease();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for (int j = i + 1; j < list.size(); j++) {
						String error[] = list.get(j);
						if (temp[0].equals(error[0]) && temp[1].equals(error[1]) ) {
							err_sb.append("第" + i + "行数据与第 " + j + "行【科室编码、病种编码】重复;");
						}
					}
		    		
		    		if (StringTool.isNotBlank(temp[0])) {
						int flag = 0 ;
						for(Map<String,Object> item : deptData){							
							if(temp[0].equals(item.get("dept_code"))){		
								mapVo.put("dept_code", temp[0]);
								mapVo.put("dept_id", item.get("dept_id"));
								flag = 1;		
								break ;
							}
						}
						budgDeptSingleDisease.setDept_id(Long.valueOf(temp[0]));
						if(flag == 0 ){
							err_sb.append("科室编码不存在或已停用;");
						}	
					}else{		
						err_sb.append("科室编码为空;");
					}
		    		if (StringTool.isNotBlank(temp[1])) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : diseaseData){
							
							if(temp[1].equals(item.get("disease_code"))){	
								mapVo.put("disease_code", temp[1]);
								flag = 1 ;
								break ;
								
							}
						}
						budgDeptSingleDisease.setDisease_code(temp[1]);
						if(flag == 0){
							
							err_sb.append("病种编码不存在或已停用;");
						}
						
					} else {
						
						err_sb.append("病种编码为空;");
						
					}
					
					if (mapVo.get("dept_id") != null && mapVo.get("disease_code") != null ){
						BudgDeptSingleDisease  budgDeptSingleDisease2 =budgDeptSingleDiseaseService.queryByCode(mapVo);
			    		if(budgDeptSingleDisease2!=null){
							err_sb.append("科室编码 病种编码已被占用;");
						}
					}
				if (err_sb.toString().length() > 0) {
					
					budgDeptSingleDisease.setError_type(err_sb.toString());
					
					list_err.add(budgDeptSingleDisease);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			if(list_err.size() == 0){
				String dataJson = budgDeptSingleDiseaseService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptSingleDisease data_exc = new BudgDeptSingleDisease();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室单病种维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgybinfor/budgdeptsingledisease/addBatchBudgDeptSingleDisease", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptSingleDisease(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptSingleDisease> list_err = new ArrayList<BudgDeptSingleDisease>();
		
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
			
			BudgDeptSingleDisease budgDeptSingleDisease = new BudgDeptSingleDisease();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgDeptSingleDisease.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("disease_code"))) {
						
					budgDeptSingleDisease.setDisease_code((String)jsonObj.get("disease_code"));
		    		mapVo.put("disease_code", jsonObj.get("disease_code"));
		    		} else {
						
						err_sb.append("病种编码为空  ");
						
					}
					
					
				BudgDeptSingleDisease data_exc_extis = budgDeptSingleDiseaseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptSingleDisease.setError_type(err_sb.toString());
					
					list_err.add(budgDeptSingleDisease);
					
				} else {
			  
					String dataJson = budgDeptSingleDiseaseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptSingleDisease data_exc = new BudgDeptSingleDisease();
			
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

