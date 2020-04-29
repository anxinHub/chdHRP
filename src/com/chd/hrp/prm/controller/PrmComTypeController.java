
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;
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
import com.chd.hrp.prm.entity.PrmComType;
import com.chd.hrp.prm.service.PrmComTypeService;

/**
 * 
 * @Description:
 * 9913 绩效部件类型表
 * @Table:
 * PRM_COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmComTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmComTypeController.class);
	
	//引入Service服务
	@Resource(name = "prmComTypeService")
	private final PrmComTypeService prmComTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/prmComTypeMainPage", method = RequestMethod.GET)
	public String prmComTypeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmcomtype/prmComTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/prmComTypeAddPage", method = RequestMethod.GET)
	public String prmComTypeAddPage(Model mode) throws Exception {

		return "hrp/prm/prmcomtype/prmComTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9913 绩效部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/addPrmComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmComTypeJson = prmComTypeService.addPrmComType(mapVo);

		return JSONObject.parseObject(prmComTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9913 绩效部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/prmComTypeUpdatePage", method = RequestMethod.GET)
	public String prmComTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmComType prmComType = new PrmComType();
    
		prmComType = prmComTypeService.queryPrmComTypeByCode(mapVo);
		
		mode.addAttribute("group_id", prmComType.getGroup_id());
		mode.addAttribute("hos_id", prmComType.getHos_id());
		mode.addAttribute("copy_code", prmComType.getCopy_code());
		mode.addAttribute("com_type_code", prmComType.getCom_type_code());
		mode.addAttribute("com_type_name", prmComType.getCom_type_name());
		mode.addAttribute("com_type_nature", prmComType.getCom_type_nature());
		mode.addAttribute("com_type_note", prmComType.getCom_type_note());
		
		return "hrp/prm/prmcomtype/prmComTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9913 绩效部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/updatePrmComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmComTypeJson = prmComTypeService.updatePrmComType(mapVo);
		
		return JSONObject.parseObject(prmComTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9913 绩效部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/deletePrmComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmComType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String prmComTypeJson = prmComTypeService.deleteBatchPrmComType(listVo);
			
	  return JSONObject.parseObject(prmComTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9913 绩效部件类型表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/queryPrmComType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmComType = prmComTypeService.queryPrmComType(getPage(mapVo));

		return JSONObject.parseObject(prmComType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 9913 绩效部件类型表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmcomtype/prmComTypeImportPage", method = RequestMethod.GET)
	public String prmComTypeImportPage(Model mode) throws Exception {

		return "hrp/prm/prmcomtype/prmComTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 9913 绩效部件类型表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmcomtype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"base\\目录","模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 9913 绩效部件类型表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/cost/prmcomtype/readPrmComTypeFiles",method = RequestMethod.POST)  
    public String readPrmComTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmComType> list_err = new ArrayList<PrmComType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				PrmComType prmComType = new PrmComType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmComType.setGroup_id(Long.valueOf(temp[0]));
								
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					prmComType.setHos_id(Long.valueOf(temp[1]));
								
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmComType.setCopy_code(temp[2]);
								
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					prmComType.setCom_type_code(temp[3]);
								
		    		mapVo.put("com_type_code", temp[3]);
		    		
					} else {
						
						err_sb.append("部件代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					prmComType.setCom_type_name(temp[4]);
								
		    		mapVo.put("com_type_name", temp[4]);
		    		
					} else {
						
						err_sb.append("部件名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
					prmComType.setCom_type_nature(temp[5]);
								
		    		mapVo.put("com_type_nature", temp[5]);
		    		
					} else {
						
						err_sb.append("input:下拉框 text:文本框 date:日期框为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[6])) {
						
					prmComType.setCom_type_note(temp[6]);
								
		    		mapVo.put("com_type_note", temp[6]);
		    		
					} else {
						
						err_sb.append("部件说明为空  ");
						
					}
					
				PrmComType data_exc_extis = prmComTypeService.queryPrmComTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmComType.setError_type(err_sb.toString());
					
					list_err.add(prmComType);
					
				} else {
			  
					String dataJson = prmComTypeService.addPrmComType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmComType data_exc = new PrmComType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 9913 绩效部件类型表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/cost/prmcomtype/addBatchPrmComType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchPrmComType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<PrmComType> list_err = new ArrayList<PrmComType>();
		
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
			
			PrmComType prmComType = new PrmComType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					prmComType.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					prmComType.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					prmComType.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_code"))) {
						
					prmComType.setCom_type_code((String)jsonObj.get("com_type_code"));
											
		    		mapVo.put("com_type_code", jsonObj.get("com_type_code"));
		    		
					} else {
						
						err_sb.append("部件代码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_name"))) {
						
					prmComType.setCom_type_name((String)jsonObj.get("com_type_name"));
											
		    		mapVo.put("com_type_name", jsonObj.get("com_type_name"));
		    		
					} else {
						
						err_sb.append("部件名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_nature"))) {
						
					prmComType.setCom_type_nature((String)jsonObj.get("com_type_nature"));
											
		    		mapVo.put("com_type_nature", jsonObj.get("com_type_nature"));
		    		
					} else {
						
						err_sb.append("input:下拉框 text:文本框 date:日期框为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_note"))) {
						
					prmComType.setCom_type_note((String)jsonObj.get("com_type_note"));
											
		    		mapVo.put("com_type_note", jsonObj.get("com_type_note"));
		    		
					} else {
						
						err_sb.append("部件说明为空  ");
						
					}
					
				PrmComType data_exc_extis = prmComTypeService.queryPrmComTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmComType.setError_type(err_sb.toString());
					
					list_err.add(prmComType);
					
				} else {
			  
					String dataJson = prmComTypeService.addPrmComType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmComType data_exc = new PrmComType();
			
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

