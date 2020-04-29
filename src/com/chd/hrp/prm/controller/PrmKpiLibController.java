
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
import com.chd.hrp.prm.entity.PrmKpiDim;
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.service.PrmKpiDimService;
import com.chd.hrp.prm.service.PrmKpiLibService;

/**
 * 
 * @Description:
 * 0502 KPI指标库表
 * @Table:
 * PRM_KPI_LIB
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmKpiLibController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmKpiLibController.class);
	
	//引入Service服务
	@Resource(name = "prmKpiLibService")
	private final PrmKpiLibService prmKpiLibService = null;
	
	@Resource(name = "prmKpiDimService")
	
	private final PrmKpiDimService prmKpiDimService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/prmKpiLibMainPage", method = RequestMethod.GET)
	public String prmKpiLibMainPage(Model mode) throws Exception {

		return "hrp/prm/prmkpilib/prmKpiLibMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/prmKpiLibAddPage", method = RequestMethod.GET)
	public String prmKpiLibAddPage(Model mode) throws Exception {

		return "hrp/prm/prmkpilib/prmKpiLibAdd";

	}

	/**
	 * @Description 
	 * 添加数据 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/addPrmKpiLib", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmKpiLib(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
	  mapVo.put("is_stop", 0);
	  
	  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
	 
	  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
	  
		String prmKpiLibJson = prmKpiLibService.addPrmKpiLib(mapVo);

		return JSONObject.parseObject(prmKpiLibJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/prmKpiLibUpdatePage", method = RequestMethod.GET)
	public String prmKpiLibUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmKpiLib prmKpiLib = new PrmKpiLib();
    
		prmKpiLib = prmKpiLibService.queryPrmKpiLibByCode(mapVo);
		
		mode.addAttribute("group_id", prmKpiLib.getGroup_id());
		
		mode.addAttribute("hos_id", prmKpiLib.getHos_id());
		
		mode.addAttribute("copy_code", prmKpiLib.getCopy_code());
		
		mode.addAttribute("kpi_code", prmKpiLib.getKpi_code());
		
		mode.addAttribute("kpi_name", prmKpiLib.getKpi_name());
		
		mode.addAttribute("nature_code", prmKpiLib.getNature_code());
		
		mode.addAttribute("dim_code", prmKpiLib.getDim_code());
		
		mode.addAttribute("spell_code", prmKpiLib.getSpell_code());
		
		mode.addAttribute("wbx_code", prmKpiLib.getWbx_code());
		
		mode.addAttribute("kpi_note", prmKpiLib.getKpi_note());
		
		mode.addAttribute("unit_code", prmKpiLib.getUnit_code());
		
		mode.addAttribute("kpi_set_note", prmKpiLib.getKpi_set_note());
		
		mode.addAttribute("kpi_act_note", prmKpiLib.getKpi_act_note());
		
		mode.addAttribute("kpi_hos_type", prmKpiLib.getKpi_hos_type());
		
		mode.addAttribute("kpi_dept_type", prmKpiLib.getKpi_dept_type());
		
		mode.addAttribute("kpi_emp_type", prmKpiLib.getKpi_emp_type());
		
		mode.addAttribute("is_stop", prmKpiLib.getIs_stop());
		
		return "hrp/prm/prmkpilib/prmKpiLibUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/updatePrmKpiLib", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmKpiLib(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		 mapVo.put("is_stop", 0);
		 
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
		 
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
	
		  String prmKpiLibJson = prmKpiLibService.updatePrmKpiLib(mapVo);
		
		return JSONObject.parseObject(prmKpiLibJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/deletePrmKpiLib", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmKpiLib(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				
				mapVo.put("hos_id", ids[1])   ;
				
				mapVo.put("copy_code", ids[2])   ;
				
				mapVo.put("kpi_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String prmKpiLibJson = prmKpiLibService.deleteBatchPrmKpiLib(listVo);
			
	  return JSONObject.parseObject(prmKpiLibJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/queryPrmKpiLib", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiLib(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmKpiLib = prmKpiLibService.queryPrmKpiLib(getPage(mapVo));

		return JSONObject.parseObject(prmKpiLib);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 0502 KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/queryPrmKpiLibDimPkn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiLibDimPkn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String prmKpiLib = prmKpiLibService.queryPrmKpiLibDimPkn(getPage(mapVo));

		return JSONObject.parseObject(prmKpiLib);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 0502 KPI指标库表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/prmKpiLibImportPage", method = RequestMethod.GET)
	public String prmKpiLibImportPage(Model mode) throws Exception {

		return "hrp/prm/prmkpilib/prmKpiLibImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 0502 KPI指标库表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmkpilib/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm//基础设置","kpi指标库.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 0502 KPI指标库表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/prm/prmkpilib/readPrmKpiLibFiles",method = RequestMethod.POST)  
    public String readPrmKpiLibFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmKpiLib> list_err = new ArrayList<PrmKpiLib>();
		
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
				
				PrmKpiLib prmKpiLib = new PrmKpiLib();
				
				String temp[] = list.get(i);// 行
					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmKpiLib.setKpi_code(temp[0]);
								
		    		mapVo.put("kpi_code", temp[0]);
		    		
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					prmKpiLib.setKpi_name(temp[1]);
								
		    		mapVo.put("kpi_name", temp[1]);
		    		
					} else {
						
						err_sb.append("指标名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmKpiLib.setNature_code(temp[2]);
								
		    		mapVo.put("nature_code", temp[2]);
		    		
					} else {
						
						err_sb.append("指标性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					prmKpiLib.setDim_code(temp[3]);
								
		    		mapVo.put("dim_code", temp[3]);
		    		
					} else {
						
						err_sb.append("维度编码为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(temp[4])) {
						
					prmKpiLib.setKpi_note(temp[4]);
								
		    		mapVo.put("dim_note", temp[4]);
		    		
					} else {
						
						err_sb.append("维度描述为空  ");
						
					}
					
								
		    	mapVo.put("is_stop", 0);
		    		
                
		    	PrmKpiDim prmKpidim = prmKpiDimService.queryPrmKpiDimByCode(mapVo);
		    	
				if(prmKpidim == null){
					
					err_sb.append("维度编码不存在！ ");
				}	
		    	
				PrmKpiLib data_exc_extis = prmKpiLibService.queryPrmKpiLibByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmKpiLib.setError_type(err_sb.toString());
					
					list_err.add(prmKpiLib);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
				  
				  mapVo.put("kpi_note", "");
				  mapVo.put("kpi_set_note","");
				  mapVo.put("kpi_act_note","");
			  
				  mapVo.put("kpi_hos_type", "");
				  mapVo.put("kpi_dept_type","");
				  mapVo.put("kpi_emp_type","");
				  mapVo.put("unit_code","");
				  
					String dataJson = prmKpiLibService.addPrmKpiLib(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmKpiLib data_exc = new PrmKpiLib();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
 
	@RequestMapping(value = "/hrp/prm/prmkpilib/queryPrmKpiLibByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiLibByMenu(@RequestParam Map<String, Object> mapVo, Model mode
			,HttpServletResponse response
			) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
	    
       
		String targetTree = prmKpiLibService.queryPrmKpiLibByMenu(mapVo);
		
		return JSONObject.parseObject(targetTree);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 0502 按属性结构展示KPI指标库表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpilib/queryPrmKpiLibDimPkns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiLibDimPkns(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String prmKpiLib = prmKpiLibService.queryPrmKpiLibDimPkns(getPage(mapVo));

		return JSONObject.parseObject(prmKpiLib);
		
	}
    
}

