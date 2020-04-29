
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;
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
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmTargetService;

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
public class PrmTargetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmTargetController.class);
	
	//引入Service服务
	@Resource(name = "prmTargetService")
	private final PrmTargetService prmTargetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/prmTargetMainPage", method = RequestMethod.GET)
	public String prmTargetMainPage(Model mode) throws Exception {

		return "hrp/prm/prmtarget/prmTargetMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/prmTargetAddPage", method = RequestMethod.GET)
	public String prmTargetAddPage(Model mode) throws Exception {

		return "hrp/prm/prmtarget/prmTargetAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/addPrmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmTargetJson = prmTargetService.addPrmTarget(mapVo);

		return JSONObject.parseObject(prmTargetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/prmTargetUpdatePage", method = RequestMethod.GET)
	public String prmTargetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
         PrmTarget prmTarget = new PrmTarget();
      
         prmTarget = prmTargetService.queryPrmTargetNatureGetByCode(mapVo);
		
		mode.addAttribute("group_id", prmTarget.getGroup_id());
		
		mode.addAttribute("hos_id", prmTarget.getHos_id());
		
		mode.addAttribute("copy_code", prmTarget.getCopy_code());
		
		mode.addAttribute("target_code", prmTarget.getTarget_code());
		
		//mode.addAttribute("nature_code", prmTarget.getNature_code());
		
		//mode.addAttribute("nature_name", prmTarget.getNature_name());
		
		mode.addAttribute("target_name", prmTarget.getTarget_name());
		
		mode.addAttribute("target_nature_code", prmTarget.getTarget_nature_code());
		
		mode.addAttribute("target_nature", prmTarget.getTarget_nature());
		
		mode.addAttribute("target_note", prmTarget.getTarget_note());
		
		mode.addAttribute("spell_code", prmTarget.getSpell_code());
		
		mode.addAttribute("wbx_code", prmTarget.getWbx_code());
		
		mode.addAttribute("is_stop", prmTarget.getIs_stop());
		
		return "hrp/prm/prmtarget/prmTargetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/updatePrmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		  
		String prmTargetJson = prmTargetService.updatePrmTarget(mapVo);
		
		return JSONObject.parseObject(prmTargetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/deletePrmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmTarget(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String prmTargetJson = prmTargetService.deleteBatchPrmTarget(listVo);
			
	  return JSONObject.parseObject(prmTargetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9901 绩效指标字典表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/queryPrmTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmTarget = prmTargetService.queryPrmTarget(getPage(mapVo));

		return JSONObject.parseObject(prmTarget);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 9901 绩效指标字典表Lift
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/queryPrmTargetNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmTargetNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String prmTarget = prmTargetService.queryPrmTargetNature(getPage(mapVo));

		return JSONObject.parseObject(prmTarget);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 9901 绩效指标字典表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtarget/prmTargetImportPage", method = RequestMethod.GET)
	public String prmTargetImportPage(Model mode) throws Exception {

		return "hrp/prm/prmtarget/prmTargetImport";

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
	@RequestMapping(value="hrp/prm/prmtarget/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\基础设置","绩效指标导入模板.xls");
	    
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
	 @RequestMapping(value="/hrp/prm/prmtarget/readPrmTargetFiles",method = RequestMethod.POST)  
    public String readPrmTargetFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmTarget> list_err = new ArrayList<PrmTarget>();
		
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
				
				PrmTarget prmTarget = new PrmTarget();
				
				String temp[] = list.get(i);// 行

					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmTarget.setTarget_code(temp[0]);
								
		    		mapVo.put("target_code", temp[0]);
		    		
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmTarget.setTarget_name(temp[2]);
								
		    		mapVo.put("target_name", temp[2]);
		    		
					} else {
						
						err_sb.append("指标名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					prmTarget.setTarget_nature(temp[3]);
								
		    		mapVo.put("target_nature", temp[3]);
		    		
					} else {
						
						err_sb.append("指标性质为空");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					prmTarget.setTarget_note(temp[4]);
								
		    		mapVo.put("target_note", temp[4]);
		    		
					} else {
						
						err_sb.append("指标描述为空  ");
						
					}
					
					mapVo.put("is_stop", 0);
					
				PrmTarget data_exc_extis = prmTargetService.queryPrmTargetByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmTarget.setError_type(err_sb.toString());
					
					list_err.add(prmTarget);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("target_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("target_name").toString()));
			  
					String dataJson = prmTargetService.addPrmTarget(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmTarget data_exc = new PrmTarget();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
 
	// 查询
	@RequestMapping(value = "/hrp/prm/prmtarget/queryTargetTree", method = RequestMethod.POST)
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
		 

		String targetTree = prmTargetService.queryTargetTree(mapVo);

		return JSONObject.parseObject(targetTree);

	}

    
}

