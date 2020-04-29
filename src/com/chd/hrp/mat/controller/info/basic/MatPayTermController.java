/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.hrp.mat.entity.MatPayTerm;
import com.chd.hrp.mat.serviceImpl.info.basic.MatPayTermServiceImpl;
/**
 * 
 * @Description:
 * MAT_PAY_TERM
 * @Table:
 * MAT_PAY_TERM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatPayTermController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPayTermController.class);
	
	//引入Service服务
	@Resource(name = "matPayTermService")
	private final MatPayTermServiceImpl matPayTermService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/matPayTermMainPage", method = RequestMethod.GET)
	public String matPayTermMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matpayterm/matPayTermMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/matPayTermAddPage", method = RequestMethod.GET)
	public String matPayTermAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matpayterm/matPayTermAdd";

	}

	/**
	 * @Description 
	 * 添加数据 MAT_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/addMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_term_name").toString()));
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_term_name").toString()));
       
		String matPayTermJson = matPayTermService.add(mapVo);

		return JSONObject.parseObject(matPayTermJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 MAT_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/matPayTermUpdatePage", method = RequestMethod.GET)
	public String matPayTermUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatPayTerm matPayTerm = new MatPayTerm();
    
		matPayTerm = matPayTermService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", matPayTerm.getGroup_id());
		mode.addAttribute("hos_id", matPayTerm.getHos_id());
		mode.addAttribute("copy_code", matPayTerm.getCopy_code());
		mode.addAttribute("pay_term_id", matPayTerm.getPay_term_id());
		mode.addAttribute("pay_term_code", matPayTerm.getPay_term_code());
		mode.addAttribute("pay_term_name", matPayTerm.getPay_term_name());
		mode.addAttribute("wbx_code", matPayTerm.getWbx_code());
		mode.addAttribute("spell_code", matPayTerm.getSpell_code());
		mode.addAttribute("is_stop", matPayTerm.getIs_stop());
		mode.addAttribute("note", matPayTerm.getNote());
		
		return "hrp/mat/info/basic/matpayterm/matPayTermUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 MAT_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/updateMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_term_name").toString()));
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_term_name").toString()));
		String matPayTermJson = matPayTermService.update(mapVo);
		
		return JSONObject.parseObject(matPayTermJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 MAT_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/deleteMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPayTerm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("pay_term_code", ids[3])   ;
				
				listVo.add(mapVo);
	      
	    }
	    
			String matPayTermJson = matPayTermService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(matPayTermJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 MAT_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/queryMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String matPayTerm = matPayTermService.query(getPage(mapVo));

		return JSONObject.parseObject(matPayTerm);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MAT_PAY_TERM
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/matPayTermImportPage", method = RequestMethod.GET)
	public String matPayTermImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matpayterm/matPayTermImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MAT_PAY_TERM
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/matpayterm/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04119 付款条件.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 MAT_PAY_TERM
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/matpayterm/readMatPayTermFiles",method = RequestMethod.POST)  
    public String readMatPayTermFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatPayTerm> list_err = new ArrayList<MatPayTerm>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatPayTerm matPayTerm = new MatPayTerm();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					matPayTerm.setPay_term_code(temp[0]);
		    		mapVo.put("pay_term_code", temp[0]);
					
					} else {
						
						err_sb.append("付款条件编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matPayTerm.setPay_term_name(temp[1]);
		    		mapVo.put("pay_term_name", temp[1]);
					
					} else {
						
						err_sb.append("付款条件名称为空  ");
						
					}
					 
					
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matPayTerm.setIs_stop(Integer.parseInt(temp[2].toString()));
		    		mapVo.put("is_stop", Integer.parseInt(temp[2].toString()));
					
					} else {
						
						err_sb.append("是否停用是为空  ");
						
					}
					 
				
					 
					
				MatPayTerm data_exc_extis = matPayTermService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPayTerm.setError_type(err_sb.toString());
					
					list_err.add(matPayTerm);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_term_name").toString()));
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_term_name").toString()));
			  
					String dataJson = matPayTermService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPayTerm data_exc = new MatPayTerm();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 MAT_PAY_TERM
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matpayterm/addBatchMatPayTerm", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatPayTerm(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatPayTerm> list_err = new ArrayList<MatPayTerm>();
		
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
			
			MatPayTerm matPayTerm = new MatPayTerm();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_term_code"))) {
						
					matPayTerm.setPay_term_code((String)jsonObj.get("pay_term_code"));
		    		mapVo.put("pay_term_code", jsonObj.get("pay_term_code"));
		    		} else {
						
						err_sb.append("付款条件编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_term_name"))) {
						
					matPayTerm.setPay_term_name((String)jsonObj.get("pay_term_name"));
		    		mapVo.put("pay_term_name", jsonObj.get("pay_term_name"));
		    		} else {
						
						err_sb.append("付款条件名称为空  ");
						
					}
			
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					matPayTerm.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		} else {
						
						err_sb.append("是否停用是为空  ");
						
					}
					

					
				MatPayTerm data_exc_extis = matPayTermService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPayTerm.setError_type(err_sb.toString());
					
					list_err.add(matPayTerm);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_term_name").toString()));
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_term_name").toString()));
			  
					String dataJson = matPayTermService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPayTerm data_exc = new MatPayTerm();
			
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

