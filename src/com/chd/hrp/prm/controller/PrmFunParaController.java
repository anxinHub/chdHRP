
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
import com.chd.hrp.prm.entity.PrmFunPara;
import com.chd.hrp.prm.service.PrmFunParaService;

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
public class PrmFunParaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmFunParaController.class);
	
	//引入Service服务
	@Resource(name = "prmFunParaService")
	private final PrmFunParaService prmFunParaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/prmFunParaMainPage", method = RequestMethod.GET)
	public String prmFunParaMainPage(Model mode) throws Exception {

		return "hrp/prm/prmfunpara/prmFunParaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/prmFunParaAddPage", method = RequestMethod.GET)
	public String prmFunParaAddPage(Model mode) throws Exception {

		return "hrp/prm/prmfunpara/prmFunParaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/addPrmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String prmFunParaJson = prmFunParaService.addPrmFunPara(mapVo);

		return JSONObject.parseObject(prmFunParaJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/prmFunParaUpdatePage", method = RequestMethod.GET)
	public String prmFunParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    PrmFunPara prmFunPara = new PrmFunPara();
    
		prmFunPara = prmFunParaService.queryPrmFunParaByCode(mapVo);
		
		mode.addAttribute("group_id", prmFunPara.getGroup_id());
		mode.addAttribute("hos_id", prmFunPara.getHos_id());
		mode.addAttribute("copy_code", prmFunPara.getCopy_code());
		mode.addAttribute("fun_code", prmFunPara.getFun_code());
		mode.addAttribute("para_code", prmFunPara.getPara_code());
		mode.addAttribute("para_name", prmFunPara.getPara_name());
		mode.addAttribute("stack_seq_no", prmFunPara.getStack_seq_no());
		mode.addAttribute("com_type_code", prmFunPara.getCom_type_code());
		
		return "hrp/prm/prmfunpara/prmFunParaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/updatePrmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String prmFunParaJson = prmFunParaService.updatePrmFunPara(mapVo);
		
		return JSONObject.parseObject(prmFunParaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/deletePrmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFunPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String prmFunParaJson = prmFunParaService.deleteBatchPrmFunPara(listVo);
			
	  return JSONObject.parseObject(prmFunParaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 9911 绩效函数参数表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/queryPrmFunPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFunPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmFunPara = prmFunParaService.queryPrmFunPara(getPage(mapVo));

		return JSONObject.parseObject(prmFunPara);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 9911 绩效函数参数表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmfunpara/prmFunParaImportPage", method = RequestMethod.GET)
	public String prmFunParaImportPage(Model mode) throws Exception {

		return "hrp/prm/prmfunpara/prmFunParaImport";

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
	@RequestMapping(value="hrp/prm/prmfunpara/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
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
	 @RequestMapping(value="/hrp/cost/prmfunpara/readPrmFunParaFiles",method = RequestMethod.POST)  
    public String readPrmFunParaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<PrmFunPara> list_err = new ArrayList<PrmFunPara>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				PrmFunPara prmFunPara = new PrmFunPara();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					prmFunPara.setGroup_id(Long.valueOf(temp[0]));
								
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					prmFunPara.setHos_id(Long.valueOf(temp[1]));
								
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					prmFunPara.setCopy_code(temp[2]);
								
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						
					prmFunPara.setFun_code(temp[3]);
								
		    		mapVo.put("fun_code", temp[3]);
		    		
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						
					prmFunPara.setPara_code(temp[4]);
								
		    		mapVo.put("para_code", temp[4]);
		    		
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[5])) {
						
					prmFunPara.setPara_name(temp[5]);
								
		    		mapVo.put("para_name", temp[5]);
		    		
					} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[6])) {
						
					prmFunPara.setStack_seq_no(Integer.valueOf(temp[6]));
								
		    		mapVo.put("stack_seq_no", temp[6]);
		    		
					} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[7])) {
						
					prmFunPara.setCom_type_code(temp[7]);
								
		    		mapVo.put("com_type_code", temp[7]);
		    		
					} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					
				PrmFunPara data_exc_extis = prmFunParaService.queryPrmFunParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmFunPara.setError_type(err_sb.toString());
					
					list_err.add(prmFunPara);
					
				} else {
			  
					String dataJson = prmFunParaService.addPrmFunPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmFunPara data_exc = new PrmFunPara();
			
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
	@RequestMapping(value = "/hrp/cost/prmfunpara/addBatchPrmFunPara", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchPrmFunPara(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<PrmFunPara> list_err = new ArrayList<PrmFunPara>();
		
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
			
			PrmFunPara prmFunPara = new PrmFunPara();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					prmFunPara.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集团ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					prmFunPara.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					prmFunPara.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("fun_code"))) {
						
					prmFunPara.setFun_code((String)jsonObj.get("fun_code"));
											
		    		mapVo.put("fun_code", jsonObj.get("fun_code"));
		    		
					} else {
						
						err_sb.append("函数编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("para_code"))) {
						
					prmFunPara.setPara_code((String)jsonObj.get("para_code"));
											
		    		mapVo.put("para_code", jsonObj.get("para_code"));
		    		
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("para_name"))) {
						
					prmFunPara.setPara_name((String)jsonObj.get("para_name"));
											
		    		mapVo.put("para_name", jsonObj.get("para_name"));
		    		
					} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("stack_seq_no"))) {
						
					prmFunPara.setStack_seq_no(Integer.valueOf((String)jsonObj.get("stack_seq_no")));
											
		    		mapVo.put("stack_seq_no", jsonObj.get("stack_seq_no"));
		    		
					} else {
						
						err_sb.append("参数栈序列为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("com_type_code"))) {
						
					prmFunPara.setCom_type_code((String)jsonObj.get("com_type_code"));
											
		    		mapVo.put("com_type_code", jsonObj.get("com_type_code"));
		    		
					} else {
						
						err_sb.append("部件类型为空  ");
						
					}
					
				PrmFunPara data_exc_extis = prmFunParaService.queryPrmFunParaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					prmFunPara.setError_type(err_sb.toString());
					
					list_err.add(prmFunPara);
					
				} else {
			  
					String dataJson = prmFunParaService.addPrmFunPara(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			PrmFunPara data_exc = new PrmFunPara();
			
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
		@RequestMapping(value = "/hrp/prm/prmfunpara/queryComTypePara", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypePara(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = prmFunParaService.queryComTypePara(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/prm/prmfunpara/queryComTypeParaByDept", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByDept(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = prmFunParaService.queryComTypeParaByDept(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/prm/prmfunpara/queryComTypeParaByEmp", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByEmp(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = prmFunParaService.queryComTypeParaByEmp(mapVo);

			return JSONObject.parseObject(comTypePara);

		}
		
		@RequestMapping(value = "/hrp/prm/prmfunpara/queryComTypeParaByHos", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryComTypeParaByHos(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode()); 

			String comTypePara = prmFunParaService.queryComTypeParaByHos(mapVo);

			return JSONObject.parseObject(comTypePara);

		}

    
}

