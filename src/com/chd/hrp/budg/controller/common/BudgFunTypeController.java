/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.hrp.budg.entity.BudgFunType;
import com.chd.hrp.budg.service.common.BudgFunTypeService;
/**
 * 
 * @Description:
 * 函数分类表
 * @Table:
 * FUN_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFunTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFunTypeController.class);
	
	//引入Service服务
	@Resource(name = "funTypeService")
	private final BudgFunTypeService funTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/budgFunTypeMainPage", method = RequestMethod.GET)
	public String budgFunTypeMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfuntype/budgFunTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/budgFunTypeAddPage", method = RequestMethod.GET)
	public String budgFunTypeAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfuntype/budgFunTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/addBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
       
		String funTypeJson = funTypeService.add(mapVo);

		return JSONObject.parseObject(funTypeJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/budgFunTypeUpdatePage", method = RequestMethod.GET)
	public String budgFunTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgFunType funType = new BudgFunType();
    
		funType = funTypeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", funType.getGroup_id());
		mode.addAttribute("hos_id", funType.getHos_id());
		mode.addAttribute("copy_code", funType.getCopy_code());
		mode.addAttribute("type_code", funType.getType_code());
		mode.addAttribute("type_name", funType.getType_name());
		mode.addAttribute("spell_code", funType.getSpell_code());
		mode.addAttribute("wbx_code", funType.getWbx_code());
		mode.addAttribute("is_stop", funType.getIs_stop());
		
		return "hrp/budg/common/budgfuntype/budgFunTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/updateBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
	  
		String funTypeJson = funTypeService.update(mapVo);
		
		return JSONObject.parseObject(funTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/addOrUpdateBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String budgFunTypeJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("type_name").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("type_name").toString()));
	  
		budgFunTypeJson = funTypeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgFunTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/deleteBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFunType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String funTypeJson = funTypeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(funTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 函数分类表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/queryBudgFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String funType = funTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(funType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 函数分类表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/budgFunTypeImportPage", method = RequestMethod.GET)
	public String funTypeImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfuntype/budgFunTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 函数分类表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgfuntype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","函数分类模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 函数分类表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgfuntype/readBudgFunTypeFiles",method = RequestMethod.POST)  
    public String readFunTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgFunType> list_err = new ArrayList<BudgFunType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgFunType funType = new BudgFunType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行分类编码重复;");
						}
						if(temp[1].equals(error[1]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行分类名称重复;");
						}
					}
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					funType.setType_code(temp[0]);
		    		mapVo.put("type_code", temp[0]);
		    		
		    		BudgFunType code  = funTypeService.queryByCode(mapVo);
					
					if(code != null ){
						err_sb.append("分类编码已被占用;");
					}
					
					} else {
						
						err_sb.append("分类编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					funType.setType_name(temp[1]);
		    		mapVo.put("type_name", temp[1]);
		    		
		    		int count  = funTypeService.queryNameExist(mapVo);
					
					if(count > 0 ){
						err_sb.append("分类名称已被占用,请重新填写;");
					}
					
					} else {
						
						err_sb.append("分类名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					funType.setIs_stop(Integer.valueOf(temp[2]));
		    		mapVo.put("is_stop", temp[2]);
					
					} else {
						
						err_sb.append("是否停用为空;");
						
					}
					 
					
					BudgFunType data_exc_extis = funTypeService.queryByCode(mapVo);
				
					if (data_exc_extis != null) {
						
						err_sb.append("数据已经存在!");
						
					}
				if (err_sb.toString().length() > 0) {
					
					funType.setError_type(err_sb.toString());
					
					list_err.add(funType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
				  
				  addList.add(mapVo) ;
					
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = funTypeService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgFunType data_exc = new BudgFunType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description （不用  代码未调 ）
	 * 批量添加数据 函数分类表   
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfuntype/addBatchFunType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchFunType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFunType> list_err = new ArrayList<BudgFunType>();
		
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
			
			BudgFunType funType = new BudgFunType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					funType.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("分类编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					funType.setType_name((String)jsonObj.get("type_name"));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("分类名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					funType.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					funType.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					funType.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					
					BudgFunType data_exc_extis = funTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					funType.setError_type(err_sb.toString());
					
					list_err.add(funType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = funTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFunType data_exc = new BudgFunType();
			
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

