
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.AphiTargetService;

/**
 * 
 * @Description:
 * 9901 绩效指标字典表
 * @Table:
 * PRM_TARGET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AphiTargetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AphiTargetController.class);
	
	//引入Service服务
	@Resource(name = "aphiTargetService")
	private final AphiTargetService aphiTargetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/hpmTargetMainPage", method = RequestMethod.GET)
	public String hpmTargetMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtarget/hpmTargetMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/hpmTargetAddPage", method = RequestMethod.GET)
	public String hpmTargetAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtarget/hpmTargetAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/addHpmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
	  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));
	  
	  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("target_name").toString()));
       
		String hpmTargetJson = aphiTargetService.addPrmTarget(mapVo);

		return JSONObject.parseObject(hpmTargetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/hpmTargetUpdatePage", method = RequestMethod.GET)
	public String hpmTargetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
         AphiTarget hpmTarget = new AphiTarget();
      
         hpmTarget = aphiTargetService.queryPrmTargetNatureGetByCode(mapVo);
		
		mode.addAttribute("group_id", hpmTarget.getGroup_id());
		
		mode.addAttribute("hos_id", hpmTarget.getHos_id());
		
		mode.addAttribute("copy_code", hpmTarget.getCopy_code());
		
		mode.addAttribute("target_code", hpmTarget.getTarget_code());
		
		//mode.addAttribute("nature_code", hpmTarget.getNature_code());
		
		//mode.addAttribute("nature_name", hpmTarget.getNature_name());
		
		mode.addAttribute("target_name", hpmTarget.getTarget_name());
		
		mode.addAttribute("target_nature_code", hpmTarget.getTarget_nature_code());
		
		mode.addAttribute("target_nature", hpmTarget.getTarget_nature());
		
		mode.addAttribute("target_note", hpmTarget.getTarget_note());
		
		mode.addAttribute("spell_code", hpmTarget.getSpell_code());
		
		mode.addAttribute("wbx_code", hpmTarget.getWbx_code());
		
		mode.addAttribute("is_stop", hpmTarget.getIs_stop());
		
		return "hrp/hpm/hpmtarget/hpmTargetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/updateHpmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));
		  
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("target_name").toString()));
		  
		String hpmTargetJson = aphiTargetService.updatePrmTarget(mapVo);
		
		return JSONObject.parseObject(hpmTargetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/deleteHpmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTarget(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				
				mapVo.put("hos_id", ids[1])   ;
				
				mapVo.put("copy_code", ids[2])   ;
				
				mapVo.put("target_code", ids[3]);
				
				listVo.add(mapVo);
	      
	    }
	    
			String hpmTargetJson = aphiTargetService.deleteBatchPrmTarget(listVo);
			
	  return JSONObject.parseObject(hpmTargetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/queryHpmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hpmTarget = aphiTargetService.queryPrmTarget(getPage(mapVo));

		return JSONObject.parseObject(hpmTarget);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 9901 绩效指标字典表Lift
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/queryHpmTargetNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTargetNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String hpmTarget = aphiTargetService.queryPrmTargetNature(getPage(mapVo));

		return JSONObject.parseObject(hpmTarget);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 9901 绩效指标字典表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtarget/hpmTargetImportPage", method = RequestMethod.GET)
	public String hpmTargetImportPage(Model mode) throws Exception {
		
		return "hrp/hpm/hpmtarget/hpmTargetImport";
	}
	
	
	@RequestMapping(value = "/hrp/hpm/hpmtarget/hpmTargetImport",method = RequestMethod.POST)
	@ResponseBody
	public String hpmTargetImport(@RequestParam Map<String, Object> mapVo){
		try {
			String impJson = aphiTargetService.hpmTargetImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	} 
	
	/**
	 * @Description 
	 * 下载导入模版 9901 绩效指标字典表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/hpm/hpmtarget/downTemplateHpmTarget", method = RequestMethod.GET)  
	 public String downTargetTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		
	    printTemplate(request,response,"hpm\\downTemplate","绩效指标维护模板.xlsx");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 9901 绩效指标字典表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/hpm/hpmtarget/readPrmTargetFiles",method = RequestMethod.POST)  
    public String readPrmTargetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AphiTarget> list_err = new ArrayList<AphiTarget>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
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
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AphiTarget hpmTarget = new AphiTarget();
				
				String temp[] = list.get(i);// 行

					
					if (StringTool.isNotBlank(temp[0])) {
						
					hpmTarget.setTarget_code(temp[0]);
								
		    		mapVo.put("target_code", temp[0]);
		    		
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					hpmTarget.setTarget_name(temp[2]);
								
		    		mapVo.put("target_name", temp[2]);
		    		
					} else {
						
						err_sb.append("指标名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					hpmTarget.setTarget_nature(temp[3]);
								
		    		mapVo.put("target_nature", temp[3]);
		    		
					} else {
						
						err_sb.append("指标性质为空");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					hpmTarget.setTarget_note(temp[4]);
								
		    		mapVo.put("target_note", temp[4]);
		    		
					} else {
						
						err_sb.append("指标描述为空  ");
						
					}
					
					mapVo.put("is_stop", 0);
					
					AphiTarget data_exc_extis = aphiTargetService.queryPrmTargetByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hpmTarget.setError_type(err_sb.toString());
					
					list_err.add(hpmTarget);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("target_name").toString()));
			  
					String dataJson = aphiTargetService.addPrmTarget(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AphiTarget data_exc = new AphiTarget();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
 
	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmtarget/queryTargetTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTargetTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		 

		String targetTree = aphiTargetService.queryTargetTree(mapVo);

		return JSONObject.parseObject(targetTree);

	}

    
}

