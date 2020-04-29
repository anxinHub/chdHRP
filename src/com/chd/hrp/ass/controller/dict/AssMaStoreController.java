/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.dict;
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
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.serviceImpl.dict.AssMatStoreServiceImpl;
import com.chd.hrp.mat.entity.MatStore;
/**
 * 
 * @Description:
 * 04107 库房附属表
 * @Table:
 * MAT_STORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class AssMaStoreController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssMaStoreController.class);
	
	//引入Service服务
	@Resource(name = "assMatStoreService")
	private final AssMatStoreServiceImpl matStoreService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/matStoreMainPage", method = RequestMethod.GET)
	public String matStoreMainPage(Model mode) throws Exception {

		return "hrp/ass/assstore/matStoreMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/matStoreAddPage", method = RequestMethod.GET)
	public String matStoreAddPage(Model mode) throws Exception {

		return "hrp/ass/assstore/matStoreAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/addMatStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String matStoreJson = matStoreService.add(mapVo);

		return JSONObject.parseObject(matStoreJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/matStoreUpdatePage", method = RequestMethod.GET)
	public String matStoreUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
//		System.out.println("*********:"+mapVo.get("dept_id"));
		
		
		 Map<String, Object> mapVo1 = matStoreService.queryByCode(mapVo);
		if(mapVo1.get("flag").toString().equals("0")){
			
			mode.addAttribute("hosStore", mapVo1);
			
			return "hrp/ass/assstore/matStoreAdd";
			
		}else{
			
			mode.addAttribute("group_id", mapVo1.get("group_id"));
			mode.addAttribute("hos_id", mapVo1.get("hos_id"));
			mode.addAttribute("store_id", mapVo1.get("store_id"));
			mode.addAttribute("store_code", mapVo1.get("store_code"));
			mode.addAttribute("store_name", mapVo1.get("store_name"));
			
			mode.addAttribute("is_stop", mapVo1.get("is_stop"));
			mode.addAttribute("type_code", mapVo1.get("type_code"));
			mode.addAttribute("type_name", mapVo1.get("type_name"));
			mode.addAttribute("spell_code", mapVo1.get("spell_code"));
			mode.addAttribute("wbx_code", mapVo1.get("wbx_code"));
			
			mode.addAttribute("sort_code", mapVo1.get("sort_code"));
			mode.addAttribute("note", mapVo1.get("note"));
			
			mode.addAttribute("is_purchase", mapVo1.get("is_purchase"));
			mode.addAttribute("is_location", mapVo1.get("is_location"));
			mode.addAttribute("is_com", mapVo1.get("is_com"));
			mode.addAttribute("alias", mapVo1.get("alias"));
			
			mode.addAttribute("dept_id", mapVo1.get("dept_id"));
			mode.addAttribute("dept_name", mapVo1.get("dept_name"));
			
			mode.addAttribute("acc_emp", mapVo1.get("acc_emp"));
			mode.addAttribute("acc_emp_name", mapVo1.get("acc_emp_name"));
			
			mode.addAttribute("manager", mapVo1.get("manager"));
			mode.addAttribute("manager_name", mapVo1.get("manager_name"));
			
			mode.addAttribute("stock_emp", mapVo1.get("stock_emp"));
			mode.addAttribute("stock_emp_name", mapVo1.get("stock_emp_name"));
			
			mode.addAttribute("telephone", mapVo1.get("telephone"));
			
			mode.addAttribute("is_control", mapVo1.get("is_control"));
			
			return "hrp/ass/assstore/matStoreUpdate";
		}
		
		
		
	}
		
	/**
	 * @Description 
	 * 更新数据 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/updateMatStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		String matStoreJson = matStoreService.update(mapVo);
		
		return JSONObject.parseObject(matStoreJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/addOrUpdateMatStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matStoreJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
	  
		matStoreJson = matStoreService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(matStoreJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/deleteMatStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("store_id", ids[2]);
				
				str = str + assBaseService.isExistsDataByTable("MAT_STORE", id.split("@")[2])== null ? ""
						: assBaseService.isExistsDataByTable("MAT_STORE", id.split("@")[2]);
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
				
				listVo.add(mapVo);
		      
		    }
			
			if (!falg) {
				return JSONObject.parseObject("{\"error\":\"删除失败，选择的仓库被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
	    
		String matStoreJson = matStoreService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(matStoreJson);
			
	}
	/**
	 * 变更记录页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/matstore/matStoreUpdateDictMainPage", method = RequestMethod.GET)
	public String matStoreUpdateDictMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/ass/assstore/matStoreUpdateDictMain";

	}
	
	/**
	 * 查看仓库变更记录
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/matstore/matStoreUpdateDictPage", method = RequestMethod.GET)
	public String matStoreUpdateDictPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		return "hrp/ass/assstore/matStoreUpdateDict";

	}
	
	/**
	 * 查看变更记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/matstore/queryMatStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());       
		}
		mapVo.put("is_ass", "1");
		String matStore = matStoreService.queryMatStoreDict(getPage(mapVo));

		return JSONObject.parseObject(matStore);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 04107 库房附属表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/queryMatStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());       
		}
		mapVo.put("is_ass", "1");   
		String matStore = matStoreService.query(getPage(mapVo));

		return JSONObject.parseObject(matStore);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04107 库房附属表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/matstore/matStoreImportPage", method = RequestMethod.GET)
	public String matStoreImportPage(Model mode) throws Exception {

		return "hrp/ass/assstore/matStoreImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04107 库房附属表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/ass/matstore/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04107 库房附属表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04107 库房附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/matstore/readMatStoreFiles",method = RequestMethod.POST)  
    public String readMatStoreFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStore> list_err = new ArrayList<MatStore>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStore matStore = new MatStore();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matStore.setStore_id(Long.valueOf(temp[2]));
		    		mapVo.put("store_id", temp[2]);
					
					} else {
						
						err_sb.append("库房ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matStore.setIs_location(Integer.valueOf(temp[3]));
		    		mapVo.put("is_location", temp[3]);
					
					} else {
						
						err_sb.append("是否货位管理为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matStore.setIs_purchase(Integer.valueOf(temp[4]));
		    		mapVo.put("is_purchase", temp[4]);
					
					} else {
						
						err_sb.append("是否采购库房为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matStore.setDept_id(Long.valueOf(temp[5]));
		    		mapVo.put("dept_id", temp[5]);
					
					} else {
						
						err_sb.append("主管部门为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matStore.setManager(Long.valueOf(temp[6]));
		    		mapVo.put("manager", temp[6]);
					
					} else {
						
						err_sb.append("保管员为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matStore.setAcc_emp(Long.valueOf(temp[7]));
		    		mapVo.put("acc_emp", temp[7]);
					
					} else {
						
						err_sb.append("会计为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matStore.setStock_emp(Long.valueOf(temp[8]));
		    		mapVo.put("stock_emp", temp[8]);
					
					} else {
						
						err_sb.append("采购员为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matStore.setTelephone(temp[9]);
		    		mapVo.put("telephone", temp[9]);
					
					} else {
						
						err_sb.append("电话为空  ");
						
					}
					 
					
				MatStore data_exc_extis = matStoreService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStore.setError_type(err_sb.toString());
					
					list_err.add(matStore);
					
				} else {
			  
					String dataJson = matStoreService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStore data_exc = new MatStore();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04107 库房附属表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/matstore/addBatchMatStore", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStore(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStore> list_err = new ArrayList<MatStore>();
		
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
			
			MatStore matStore = new MatStore();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matStore.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("库房ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_location"))) {
						
					matStore.setIs_location(Integer.valueOf((String)jsonObj.get("is_location")));
		    		mapVo.put("is_location", jsonObj.get("is_location"));
		    		} else {
						
						err_sb.append("是否货位管理为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_purchase"))) {
						
					matStore.setIs_purchase(Integer.valueOf((String)jsonObj.get("is_purchase")));
		    		mapVo.put("is_purchase", jsonObj.get("is_purchase"));
		    		} else {
						
						err_sb.append("是否采购库房为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					matStore.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("主管部门为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("manager"))) {
						
					matStore.setManager(Long.valueOf((String)jsonObj.get("manager")));
		    		mapVo.put("manager", jsonObj.get("manager"));
		    		} else {
						
						err_sb.append("保管员为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acc_emp"))) {
						
					matStore.setAcc_emp(Long.valueOf((String)jsonObj.get("acc_emp")));
		    		mapVo.put("acc_emp", jsonObj.get("acc_emp"));
		    		} else {
						
						err_sb.append("会计为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_emp"))) {
						
					matStore.setStock_emp(Long.valueOf((String)jsonObj.get("stock_emp")));
		    		mapVo.put("stock_emp", jsonObj.get("stock_emp"));
		    		} else {
						
						err_sb.append("采购员为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("telephone"))) {
						
					matStore.setTelephone((String)jsonObj.get("telephone"));
		    		mapVo.put("telephone", jsonObj.get("telephone"));
		    		} else {
						
						err_sb.append("电话为空  ");
						
					}
					
					
				MatStore data_exc_extis = matStoreService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStore.setError_type(err_sb.toString());
					
					list_err.add(matStore);
					
				} else {
			  
					String dataJson = matStoreService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStore data_exc = new MatStore();
			
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

