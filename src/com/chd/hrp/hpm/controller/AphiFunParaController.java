
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.controller;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.hpm.entity.AphiFunPara;
import com.chd.hrp.hpm.service.AphiFunParaService;

/**
 * 
 * @Description:
 * 9911 绩效函数参数表
 * @Table:
 * PRM_FUN_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AphiFunParaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AphiFunParaController.class);
	
	//引入Service服务
	@Resource(name = "aphiFunParaService")
	private final AphiFunParaService aphiFunParaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/hpmFunParaMainPage", method = RequestMethod.GET)
	public String hpmFunParaMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunpara/hpmFunParaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/hpmFunParaAddPage", method = RequestMethod.GET)
	public String hpmFunParaAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunpara/hpmFunParaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/addHpmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String hpmFunParaJson = aphiFunParaService.addPrmFunPara(mapVo);

		return JSONObject.parseObject(hpmFunParaJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/hpmFunParaUpdatePage", method = RequestMethod.GET)
	public String hpmFunParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AphiFunPara hpmFunPara = new AphiFunPara();
    
		hpmFunPara = aphiFunParaService.queryPrmFunParaByCode(mapVo);
		
		mode.addAttribute("group_id", hpmFunPara.getGroup_id());
		mode.addAttribute("hos_id", hpmFunPara.getHos_id());
		mode.addAttribute("copy_code", hpmFunPara.getCopy_code());
		mode.addAttribute("fun_code", hpmFunPara.getFun_code());
		mode.addAttribute("para_code", hpmFunPara.getPara_code());
		mode.addAttribute("para_name", hpmFunPara.getPara_name());
		mode.addAttribute("stack_seq_no", hpmFunPara.getStack_seq_no());
		mode.addAttribute("com_type_code", hpmFunPara.getCom_type_code());
		
		return "hrp/hpm/hpmfunpara/hpmFunParaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/updateHpmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hpmFunParaJson = aphiFunParaService.updatePrmFunPara(mapVo);
		
		return JSONObject.parseObject(hpmFunParaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/deleteHpmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFunPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fun_code", ids[3])   ;
				mapVo.put("para_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String hpmFunParaJson = aphiFunParaService.deleteBatchPrmFunPara(listVo);
			
	  return JSONObject.parseObject(hpmFunParaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/queryHpmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hpmFunPara = aphiFunParaService.queryPrmFunPara(getPage(mapVo));

		return JSONObject.parseObject(hpmFunPara);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 9911 绩效函数参数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/hpm/hpmfunpara/hpmFunParaImportPage", method = RequestMethod.GET)
	public String hpmFunParaImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunpara/hpmFunParaImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 9911 绩效函数参数表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/hpm/hpmfunpara/downFunParaTemplate", method = RequestMethod.GET)  
	 public String downFunParaTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"base\\目录","模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 9911 绩效函数参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/cost/hpmfunpara/readHpmFunParaFiles",method = RequestMethod.POST)  
    public String readHpmFunParaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AphiFunPara> list_err = new ArrayList<AphiFunPara>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AphiFunPara hpmFunPara = new AphiFunPara();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					hpmFunPara.setGroup_id(Long.valueOf(temp[0]));
								
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					hpmFunPara.setHos_id(Long.valueOf(temp[1]));
								
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					hpmFunPara.setCopy_code(temp[2]);
								
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					hpmFunPara.setFun_code(temp[3]);
								
		    		mapVo.put("fun_code", temp[3]);
		    		
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					hpmFunPara.setPara_code(temp[4]);
								
		    		mapVo.put("para_code", temp[4]);
		    		
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
					hpmFunPara.setPara_name(temp[5]);
								
		    		mapVo.put("para_name", temp[5]);
		    		
					} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[6])) {
						
					hpmFunPara.setStack_seq_no(Integer.valueOf(temp[6]));
								
		    		mapVo.put("stack_seq_no", temp[6]);
		    		
					} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[7])) {
						
					hpmFunPara.setCom_type_code(temp[7]);
								
		    		mapVo.put("com_type_code", temp[7]);
		    		
					} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					
					AphiFunPara data_exc_extis = aphiFunParaService.queryPrmFunParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hpmFunPara.setError_type(err_sb.toString());
					
					list_err.add(hpmFunPara);
					
				} else {
			  
					String dataJson = aphiFunParaService.addPrmFunPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AphiFunPara data_exc = new AphiFunPara();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 9911 绩效函数参数表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/cost/hpmfunpara/addBatchHpmFunPara", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchHpmFunPara(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AphiFunPara> list_err = new ArrayList<AphiFunPara>();
		
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
			
			AphiFunPara hpmFunPara = new AphiFunPara();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					hpmFunPara.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					hpmFunPara.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					hpmFunPara.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("fun_code"))) {
						
					hpmFunPara.setFun_code((String)jsonObj.get("fun_code"));
											
		    		mapVo.put("fun_code", jsonObj.get("fun_code"));
		    		
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("para_code"))) {
						
					hpmFunPara.setPara_code((String)jsonObj.get("para_code"));
											
		    		mapVo.put("para_code", jsonObj.get("para_code"));
		    		
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("para_name"))) {
						
					hpmFunPara.setPara_name((String)jsonObj.get("para_name"));
											
		    		mapVo.put("para_name", jsonObj.get("para_name"));
		    		
					} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("stack_seq_no"))) {
						
					hpmFunPara.setStack_seq_no(Integer.valueOf((String)jsonObj.get("stack_seq_no")));
											
		    		mapVo.put("stack_seq_no", jsonObj.get("stack_seq_no"));
		    		
					} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_code"))) {
						
					hpmFunPara.setCom_type_code((String)jsonObj.get("com_type_code"));
											
		    		mapVo.put("com_type_code", jsonObj.get("com_type_code"));
		    		
					} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					
					AphiFunPara data_exc_extis = aphiFunParaService.queryPrmFunParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hpmFunPara.setError_type(err_sb.toString());
					
					list_err.add(hpmFunPara);
					
				} else {
			  
					String dataJson = aphiFunParaService.addPrmFunPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AphiFunPara data_exc = new AphiFunPara();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	
	// 查询
		@RequestMapping(value = "/hrp/hpm/hpmfunpara/queryComTypePara", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypePara(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = aphiFunParaService.queryComTypePara(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/hpm/hpmfunpara/queryComTypeParaByDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByDept(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = aphiFunParaService.queryComTypeParaByDept(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/hpm/hpmfunpara/queryComTypeParaByEmp", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByEmp(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = aphiFunParaService.queryComTypeParaByEmp(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/hpm/hpmfunpara/queryComTypeParaByHos", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByHos(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = aphiFunParaService.queryComTypeParaByHos(mapVo);

			return JSONObject.parseObject(comTypePara);

		}

    
}

