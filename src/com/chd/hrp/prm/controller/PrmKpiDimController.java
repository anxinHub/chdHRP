
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
import com.chd.hrp.prm.entity.PrmKpiDim;
import com.chd.hrp.prm.service.PrmKpiDimService;

/**
 * 
 * @Description:
 * 0501 KPI指标维度表
 * @Table:
 * PRM_KPI_DIM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmKpiDimController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmKpiDimController.class);
	
	//引入Service服务
	@Resource(name = "prmKpiDimService")
	private final PrmKpiDimService prmKpiDimService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/prmKpiDimMainPage", method = RequestMethod.GET)
	public String prmKpiDimMainPage(Model mode) throws Exception {

		return "hrp/prm/prmkpidim/prmKpiDimMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/prmKpiDimAddPage", method = RequestMethod.GET)
	public String prmKpiDimAddPage(Model mode) throws Exception {

		return "hrp/prm/prmkpidim/prmKpiDimAdd";

	}

	/**
	 * @Description 
	 * 添加数据 0501 KPI指标维度表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/addPrmKpiDim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmKpiDim(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
	  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dim_name").toString()));
	  
	  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dim_name").toString()));
       
		String prmKpiDimJson = prmKpiDimService.addPrmKpiDim(mapVo);

		return JSONObject.parseObject(prmKpiDimJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 0501 KPI指标维度表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/prmKpiDimUpdatePage", method = RequestMethod.GET)
	public String prmKpiDimUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmKpiDim prmKpiDim = new PrmKpiDim();
    
		prmKpiDim = prmKpiDimService.queryPrmKpiDimByCode(mapVo);
		
		mode.addAttribute("group_id", prmKpiDim.getGroup_id());
		
		mode.addAttribute("hos_id", prmKpiDim.getHos_id());
		
		mode.addAttribute("copy_code", prmKpiDim.getCopy_code());
		
		mode.addAttribute("dim_code", prmKpiDim.getDim_code());
		
		mode.addAttribute("dim_name", prmKpiDim.getDim_name());
		
		mode.addAttribute("spell_code", prmKpiDim.getSpell_code());
		
		mode.addAttribute("wbx_code", prmKpiDim.getWbx_code());
		
		mode.addAttribute("dim_note", prmKpiDim.getDim_note());
		
		mode.addAttribute("is_stop", prmKpiDim.getIs_stop());
		
		return "hrp/prm/prmkpidim/prmKpiDimUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 0501 KPI指标维度表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/updatePrmKpiDim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmKpiDim(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dim_name").toString()));
		 
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dim_name").toString()));
		
		String prmKpiDimJson = prmKpiDimService.updatePrmKpiDim(mapVo);
		
		return JSONObject.parseObject(prmKpiDimJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 0501 KPI指标维度表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/deletePrmKpiDim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmKpiDim(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				
				mapVo.put("hos_id", ids[1])   ;
				
				mapVo.put("copy_code", ids[2])   ;
				
				mapVo.put("dim_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String prmKpiDimJson = prmKpiDimService.deleteBatchPrmKpiDim(listVo);
			
	  return JSONObject.parseObject(prmKpiDimJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 0501 KPI指标维度表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/queryPrmKpiDim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiDim(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String prmKpiDim = prmKpiDimService.queryPrmKpiDim(getPage(mapVo));

		return JSONObject.parseObject(prmKpiDim);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 0501 KPI指标维度表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmkpidim/prmKpiDimImportPage", method = RequestMethod.GET)
	public String prmKpiDimImportPage(Model mode) throws Exception {

		return "hrp/prm/prmkpidim/prmKpiDimImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 0501 KPI指标维度表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/prm/prmkpidim/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"prm\\基础设置","kpi指标维度.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 0501 KPI指标维度表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/prm/prmkpidim/readPrmKpiDimFiles",method = RequestMethod.POST)  
    public String readPrmKpiDimFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmKpiDim> list_err = new ArrayList<PrmKpiDim>();
		
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
				
				PrmKpiDim prmKpiDim = new PrmKpiDim();
				
				String temp[] = list.get(i);// 行
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmKpiDim.setDim_code(temp[0]);
								
		    		mapVo.put("dim_code", temp[0]);
		    		
					} else {
						
						err_sb.append("维度编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					prmKpiDim.setDim_name(temp[1]);
								
		    		mapVo.put("dim_name", temp[1]);
		    		
					} else {
						
						err_sb.append("维度名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmKpiDim.setDim_note(temp[2]);
								
		    		mapVo.put("dim_note", temp[2]);
		    		
					} else {
						
						err_sb.append("维度描述为空  ");
						
					}
									
				/*	是否停用默认 0 */
		    		mapVo.put("is_stop", 0);
		    		
				PrmKpiDim data_exc_extis = prmKpiDimService.queryPrmKpiDimByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmKpiDim.setError_type(err_sb.toString());
					
					list_err.add(prmKpiDim);
					
				} else {
				 
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dim_name").toString()));
				 
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dim_name").toString()));
			  
					String dataJson = prmKpiDimService.addPrmKpiDim(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmKpiDim data_exc = new PrmKpiDim();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   
}

