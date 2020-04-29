/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.budg.entity.BudgComType;
import com.chd.hrp.budg.serviceImpl.common.BudgComTypeServiceImpl;
/**
 * 
 * @Description:
 * 部件类型表
 * @Table:
 * COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgComTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgComTypeController.class);
	
	//引入Service服务
	@Resource(name = "budgComTypeService")
	private final BudgComTypeServiceImpl budgComTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgcomtype/budgComTypeMainPage", method = RequestMethod.GET)
	public String comTypeMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgcomtype/budgComTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgcomtype/budgComTypeAddPage", method = RequestMethod.GET)
	public String comTypeAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgcomtype/budgComTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/comtype/addComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String comTypeJson = budgComTypeService.add(mapVo);

		return JSONObject.parseObject(comTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgcomtype/budgComTypeUpdatePage", method = RequestMethod.GET)
	public String comTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgComType comType = new BudgComType();
    
		comType = budgComTypeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", comType.getGroup_id());
		mode.addAttribute("hos_id", comType.getHos_id());
		mode.addAttribute("copy_code", comType.getCopy_code());
		mode.addAttribute("com_type_code", comType.getCom_type_code());
		mode.addAttribute("com_type_name", comType.getCom_type_name());
		mode.addAttribute("com_type_nature", comType.getCom_type_nature());
		mode.addAttribute("com_type_note", comType.getCom_type_note());
		
		return "hrp/budg/common/budgcomtype/budgComTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/comtype/updateComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String comTypeJson = budgComTypeService.update(mapVo);
		
		return JSONObject.parseObject(comTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/comtype/addOrUpdateComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String comTypeJson ="";
		
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
	  
		comTypeJson = budgComTypeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(comTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/comtype/deleteComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteComType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("com_type_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String comTypeJson = budgComTypeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(comTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/comtype/queryComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String comType = budgComTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(comType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 部件类型表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgcomtype/budgComTypeImportPage", method = RequestMethod.GET)
	public String comTypeImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgcomtype/budgComTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 部件类型表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/comtype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","部件类型表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 部件类型表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/comtype/readComTypeFiles",method = RequestMethod.POST)  
    public String readComTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgComType> list_err = new ArrayList<BudgComType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgComType comType = new BudgComType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					comType.setCom_type_code(temp[3]);
		    		mapVo.put("com_type_code", temp[3]);
					
					} else {
						
						err_sb.append("部件代码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					comType.setCom_type_name(temp[4]);
		    		mapVo.put("com_type_name", temp[4]);
					
					} else {
						
						err_sb.append("部件名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					comType.setCom_type_nature(temp[5]);
		    		mapVo.put("com_type_nature", temp[5]);
					
					} else {
						
						err_sb.append("input:下拉框 text:文本框 date:日期框为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					comType.setCom_type_note(temp[6]);
		    		mapVo.put("com_type_note", temp[6]);
					
					} else {
						
						err_sb.append("部件说明为空  ");
						
					}
					 
					
					BudgComType data_exc_extis = budgComTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					comType.setError_type(err_sb.toString());
					
					list_err.add(comType);
					
				} else {
			  
					String dataJson = budgComTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgComType data_exc = new BudgComType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 部件类型表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/comtype/addBatchComType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchComType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgComType> list_err = new ArrayList<BudgComType>();
		
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
			
			BudgComType comType = new BudgComType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("com_type_code"))) {
						
					comType.setCom_type_code((String)jsonObj.get("com_type_code"));
		    		mapVo.put("com_type_code", jsonObj.get("com_type_code"));
		    		} else {
						
						err_sb.append("部件代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("com_type_name"))) {
						
					comType.setCom_type_name((String)jsonObj.get("com_type_name"));
		    		mapVo.put("com_type_name", jsonObj.get("com_type_name"));
		    		} else {
						
						err_sb.append("部件名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("com_type_nature"))) {
						
					comType.setCom_type_nature((String)jsonObj.get("com_type_nature"));
		    		mapVo.put("com_type_nature", jsonObj.get("com_type_nature"));
		    		} else {
						
						err_sb.append("input:下拉框 text:文本框 date:日期框为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("com_type_note"))) {
						
					comType.setCom_type_note((String)jsonObj.get("com_type_note"));
		    		mapVo.put("com_type_note", jsonObj.get("com_type_note"));
		    		} else {
						
						err_sb.append("部件说明为空  ");
						
					}
					
					
					BudgComType data_exc_extis = budgComTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					comType.setError_type(err_sb.toString());
					
					list_err.add(comType);
					
				} else {
			  
					String dataJson = budgComTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgComType data_exc = new BudgComType();
			
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

