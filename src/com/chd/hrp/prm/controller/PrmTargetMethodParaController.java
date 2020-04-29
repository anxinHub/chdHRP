
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
import com.chd.hrp.prm.entity.PrmTargetMethodPara;
import com.chd.hrp.prm.service.PrmTargetMethodParaService;

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
public class PrmTargetMethodParaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmTargetMethodParaController.class);
	
	//引入Service服务
	@Resource(name = "prmTargetMethodParaService")
	private final PrmTargetMethodParaService prmTargetMethodParaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/prmTargetMethodParaMainPage", method = RequestMethod.GET)
	public String prmTargetMethodParaMainPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethodpara/prmTargetMethodParaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/prmTargetMethodParaAddPage", method = RequestMethod.GET)
	public String prmTargetMethodParaAddPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethodpara/prmTargetMethodParaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/addPrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
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
		String prmTargetMethodParaJson = prmTargetMethodParaService.addPrmTargetMethodPara(mapVo);

		return JSONObject.parseObject(prmTargetMethodParaJson);
		
	}
	
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/addPrmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmTargetMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
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
		String prmTargetMethodJson = prmTargetMethodParaService.addPrmTargetMethod(mapVo);

		return JSONObject.parseObject(prmTargetMethodJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/prmTargetMethodParaUpdatePage", method = RequestMethod.GET)
	public String prmTargetMethodParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmTargetMethodPara prmTargetMethodPara = new PrmTargetMethodPara();
    
		prmTargetMethodPara = prmTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
		
		mode.addAttribute("group_id", prmTargetMethodPara.getGroup_id());
		mode.addAttribute("hos_id", prmTargetMethodPara.getHos_id());
		mode.addAttribute("copy_code", prmTargetMethodPara.getCopy_code());
		mode.addAttribute("method_code", prmTargetMethodPara.getMethod_code());
		mode.addAttribute("method_name", prmTargetMethodPara.getMethod_name());
		mode.addAttribute("spell_code", prmTargetMethodPara.getSpell_code());
		mode.addAttribute("wbx_code", prmTargetMethodPara.getWbx_code());
		mode.addAttribute("is_stop", prmTargetMethodPara.getIs_stop());
		
		return "hrp/prm/prmtargetmethodpara/prmTargetMethodParaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/updatePrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmTargetMethodParaJson = prmTargetMethodParaService.updatePrmTargetMethodPara(mapVo);
		
		return JSONObject.parseObject(prmTargetMethodParaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/deletePrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmTargetMethodPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String prmTargetMethodParaJson = prmTargetMethodParaService.deleteBatchPrmTargetMethodPara(listVo);
			
	  return JSONObject.parseObject(prmTargetMethodParaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9903 指标取值方法参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/queryPrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmTargetMethodPara = prmTargetMethodParaService.queryPrmTargetMethodPara(getPage(mapVo));

		return JSONObject.parseObject(prmTargetMethodPara);
		
	}
	
	
	
  /**
	 * @Description 
	 * 导入跳转页面 9903 指标取值方法参数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmtargetmethodpara/prmTargetMethodParaImportPage", method = RequestMethod.GET)
	public String prmTargetMethodParaImportPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethodpara/prmTargetMethodParaImport";

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
	@RequestMapping(value="hrp/prm/prmtargetmethodpara/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\基础设置","指标取值方法参数表模板.xls");
	    
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
	 @RequestMapping(value="/hrp/cost/prmtargetmethodpara/readPrmTargetMethodParaFiles",method = RequestMethod.POST)  
    public String readPrmTargetMethodParaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmTargetMethodPara> list_err = new ArrayList<PrmTargetMethodPara>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				PrmTargetMethodPara prmTargetMethodPara = new PrmTargetMethodPara();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmTargetMethodPara.setGroup_id(Long.valueOf(temp[0]));
								
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					prmTargetMethodPara.setHos_id(Long.valueOf(temp[1]));
								
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmTargetMethodPara.setCopy_code(temp[2]);
								
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					prmTargetMethodPara.setMethod_code(temp[3]);
								
		    		mapVo.put("method_code", temp[3]);
		    		
					} else {
						
						err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					prmTargetMethodPara.setMethod_name(temp[4]);
								
		    		mapVo.put("method_name", temp[4]);
		    		
					} else {
						
						err_sb.append("取值方法名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
					prmTargetMethodPara.setSpell_code(temp[5]);
								
		    		mapVo.put("spell_code", temp[5]);
		    		
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[6])) {
						
					prmTargetMethodPara.setWbx_code(temp[6]);
								
		    		mapVo.put("wbx_code", temp[6]);
		    		
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[7])) {
						
					prmTargetMethodPara.setIs_stop(Integer.valueOf(temp[7]));
								
		    		mapVo.put("is_stop", temp[7]);
		    		
					} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
				PrmTargetMethodPara data_exc_extis = prmTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmTargetMethodPara.setError_type(err_sb.toString());
					
					list_err.add(prmTargetMethodPara);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = prmTargetMethodParaService.addPrmTargetMethodPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmTargetMethodPara data_exc = new PrmTargetMethodPara();
			
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
	@RequestMapping(value = "/hrp/cost/prmtargetmethodpara/addBatchPrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchPrmTargetMethodPara(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<PrmTargetMethodPara> list_err = new ArrayList<PrmTargetMethodPara>();
		
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
			
			PrmTargetMethodPara prmTargetMethodPara = new PrmTargetMethodPara();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					prmTargetMethodPara.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					prmTargetMethodPara.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					prmTargetMethodPara.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("method_code"))) {
						
					prmTargetMethodPara.setMethod_code((String)jsonObj.get("method_code"));
											
		    		mapVo.put("method_code", jsonObj.get("method_code"));
		    		
					} else {
						
						err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("method_name"))) {
						
					prmTargetMethodPara.setMethod_name((String)jsonObj.get("method_name"));
											
		    		mapVo.put("method_name", jsonObj.get("method_name"));
		    		
					} else {
						
						err_sb.append("取值方法名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					prmTargetMethodPara.setSpell_code((String)jsonObj.get("spell_code"));
											
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					prmTargetMethodPara.setWbx_code((String)jsonObj.get("wbx_code"));
											
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					prmTargetMethodPara.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
											
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
				PrmTargetMethodPara data_exc_extis = prmTargetMethodParaService.queryPrmTargetMethodParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmTargetMethodPara.setError_type(err_sb.toString());
					
					list_err.add(prmTargetMethodPara);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = prmTargetMethodParaService.addPrmTargetMethodPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmTargetMethodPara data_exc = new PrmTargetMethodPara();
			
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

