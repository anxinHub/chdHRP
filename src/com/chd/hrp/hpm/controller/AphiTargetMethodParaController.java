
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
import com.chd.hrp.hpm.entity.AphiTargetMethodPara;
import com.chd.hrp.hpm.service.AphiTargetMethodParaService;

/**
 * 
 * @Description:
 * 9903 指标取值方法参数表
 * @Table:
 * PRM_TARGET_METHOD_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AphiTargetMethodParaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AphiTargetMethodParaController.class);
	
	//引入Service服务
	@Resource(name = "aphiTargetMethodParaService")
	private final AphiTargetMethodParaService aphiTargetMethodParaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaMainPage", method = RequestMethod.GET)
	public String hpmTargetMethodParaMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaAddPage", method = RequestMethod.GET)
	public String hpmTargetMethodParaAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/addHpmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
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
	  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("method_name").toString()));
	  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("method_name").toString())); 
		String hpmTargetMethodParaJson = aphiTargetMethodParaService.addPrmTargetMethodPara(mapVo);

		return JSONObject.parseObject(hpmTargetMethodParaJson);
		
	}
	
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/addAphiTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAphiTargetMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
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
		String hpmTargetMethodJson = aphiTargetMethodParaService.addPrmTargetMethod(mapVo);

		return JSONObject.parseObject(hpmTargetMethodJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaUpdatePage", method = RequestMethod.GET)
	public String hpmTargetMethodParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AphiTargetMethodPara hpmTargetMethodPara = new AphiTargetMethodPara();
    
		hpmTargetMethodPara = aphiTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
		
		mode.addAttribute("group_id", hpmTargetMethodPara.getGroup_id());
		mode.addAttribute("hos_id", hpmTargetMethodPara.getHos_id());
		mode.addAttribute("copy_code", hpmTargetMethodPara.getCopy_code());
		mode.addAttribute("method_code", hpmTargetMethodPara.getMethod_code());
		mode.addAttribute("method_name", hpmTargetMethodPara.getMethod_name());
		mode.addAttribute("spell_code", hpmTargetMethodPara.getSpell_code());
		mode.addAttribute("wbx_code", hpmTargetMethodPara.getWbx_code());
		mode.addAttribute("is_stop", hpmTargetMethodPara.getIs_stop());
		
		return "hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/updateHpmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		String hpmTargetMethodParaJson = aphiTargetMethodParaService.updatePrmTargetMethodPara(mapVo);
		
		return JSONObject.parseObject(hpmTargetMethodParaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/deleteHpmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTargetMethodPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("method_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String hpmTargetMethodParaJson = aphiTargetMethodParaService.deleteBatchPrmTargetMethodPara(listVo);
			
	  return JSONObject.parseObject(hpmTargetMethodParaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/queryHpmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hpmTargetMethodPara = aphiTargetMethodParaService.queryPrmTargetMethodPara(getPage(mapVo));

		return JSONObject.parseObject(hpmTargetMethodPara);
		
	}
	
	
	
  /**
	 * @Description 
	 * 导入跳转页面 9903 指标取值方法参数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaImportPage", method = RequestMethod.GET)
	public String hpmTargetMethodParaImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethodpara/hpmTargetMethodParaImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 9903 指标取值方法参数表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/hpm/hpmtargetmethodpara/downTargetMethodParaTemplate", method = RequestMethod.GET)  
	 public String downTargetMethodParaTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"hpm\\基础设置","指标取值方法参数表模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 9903 指标取值方法参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/cost/hpmtargetmethodpara/readHpmTargetMethodParaFiles",method = RequestMethod.POST)  
    public String readHpmTargetMethodParaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AphiTargetMethodPara> list_err = new ArrayList<AphiTargetMethodPara>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AphiTargetMethodPara hpmTargetMethodPara = new AphiTargetMethodPara();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					hpmTargetMethodPara.setGroup_id(Long.valueOf(temp[0]));
								
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					hpmTargetMethodPara.setHos_id(Long.valueOf(temp[1]));
								
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					hpmTargetMethodPara.setCopy_code(temp[2]);
								
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					hpmTargetMethodPara.setMethod_code(temp[3]);
								
		    		mapVo.put("method_code", temp[3]);
		    		
					} else {
						
						err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					hpmTargetMethodPara.setMethod_name(temp[4]);
								
		    		mapVo.put("method_name", temp[4]);
		    		
					} else {
						
						err_sb.append("取值方法名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
					hpmTargetMethodPara.setSpell_code(temp[5]);
								
		    		mapVo.put("spell_code", temp[5]);
		    		
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[6])) {
						
					hpmTargetMethodPara.setWbx_code(temp[6]);
								
		    		mapVo.put("wbx_code", temp[6]);
		    		
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[7])) {
						
					hpmTargetMethodPara.setIs_stop(Integer.valueOf(temp[7]));
								
		    		mapVo.put("is_stop", temp[7]);
		    		
					} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					AphiTargetMethodPara data_exc_extis = aphiTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hpmTargetMethodPara.setError_type(err_sb.toString());
					
					list_err.add(hpmTargetMethodPara);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = aphiTargetMethodParaService.addPrmTargetMethodPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AphiTargetMethodPara data_exc = new AphiTargetMethodPara();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 9903 指标取值方法参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/cost/hpmtargetmethodpara/addBatchHpmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchHpmTargetMethodPara(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AphiTargetMethodPara> list_err = new ArrayList<AphiTargetMethodPara>();
		
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
			
			AphiTargetMethodPara hpmTargetMethodPara = new AphiTargetMethodPara();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					hpmTargetMethodPara.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					hpmTargetMethodPara.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					hpmTargetMethodPara.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("method_code"))) {
						
					hpmTargetMethodPara.setMethod_code((String)jsonObj.get("method_code"));
											
		    		mapVo.put("method_code", jsonObj.get("method_code"));
		    		
					} else {
						
						err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("method_name"))) {
						
					hpmTargetMethodPara.setMethod_name((String)jsonObj.get("method_name"));
											
		    		mapVo.put("method_name", jsonObj.get("method_name"));
		    		
					} else {
						
						err_sb.append("取值方法名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					hpmTargetMethodPara.setSpell_code((String)jsonObj.get("spell_code"));
											
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					hpmTargetMethodPara.setWbx_code((String)jsonObj.get("wbx_code"));
											
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					hpmTargetMethodPara.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
											
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					AphiTargetMethodPara data_exc_extis = aphiTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hpmTargetMethodPara.setError_type(err_sb.toString());
					
					list_err.add(hpmTargetMethodPara);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = aphiTargetMethodParaService.addPrmTargetMethodPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AphiTargetMethodPara data_exc = new AphiTargetMethodPara();
			
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

