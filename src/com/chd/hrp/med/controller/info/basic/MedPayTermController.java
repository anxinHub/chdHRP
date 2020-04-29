/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.hrp.med.entity.MedPayTerm;
import com.chd.hrp.med.serviceImpl.info.basic.MedPayTermServiceImpl;
/**
 * 
 * @Description:
 * MED_PAY_TERM
 * @Table:
 * MED_PAY_TERM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedPayTermController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPayTermController.class);
	
	//引入Service服务
	@Resource(name = "medPayTermService")
	private final MedPayTermServiceImpl medPayTermService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/medPayTermMainPage", method = RequestMethod.GET)
	public String medPayTermMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medpayterm/medPayTermMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/medPayTermAddPage", method = RequestMethod.GET)
	public String medPayTermAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medpayterm/medPayTermAdd";

	}

	/**
	 * @Description 
	 * 添加数据 MED_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/addMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String medPayTermJson = medPayTermService.add(mapVo);

		return JSONObject.parseObject(medPayTermJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 MED_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/medPayTermUpdatePage", method = RequestMethod.GET)
	public String medPayTermUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedPayTerm medPayTerm = new MedPayTerm();
    
		medPayTerm = medPayTermService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", medPayTerm.getGroup_id());
		mode.addAttribute("hos_id", medPayTerm.getHos_id());
		mode.addAttribute("copy_code", medPayTerm.getCopy_code());
		mode.addAttribute("pay_term_id", medPayTerm.getPay_term_id());
		mode.addAttribute("pay_term_code", medPayTerm.getPay_term_code());
		mode.addAttribute("pay_term_name", medPayTerm.getPay_term_name());
		mode.addAttribute("wbx_code", medPayTerm.getWbx_code());
		mode.addAttribute("spell_code", medPayTerm.getSpell_code());
		mode.addAttribute("is_stop", medPayTerm.getIs_stop());
		mode.addAttribute("note", medPayTerm.getNote());
		
		return "hrp/med/info/basic/medpayterm/medPayTermUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 MED_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/updateMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String medPayTermJson = medPayTermService.update(mapVo);
		
		return JSONObject.parseObject(medPayTermJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 MED_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/deleteMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPayTerm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String medPayTermJson = medPayTermService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(medPayTermJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 MED_PAY_TERM
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/queryMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String medPayTerm = medPayTermService.query(getPage(mapVo));

		return JSONObject.parseObject(medPayTerm);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MED_PAY_TERM
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/medPayTermImportPage", method = RequestMethod.GET)
	public String medPayTermImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medpayterm/medPayTermImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MED_PAY_TERM
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/medpayterm/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08119 付款条件.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 MED_PAY_TERM
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/med/info/basic/medpayterm/importMedPayTerm",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importMedPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			if(mapVo.get("group_id") == null){			
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){			
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){			
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			String reJson=medPayTermService.importMedPayTerm(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
		
	 
	 
	 
	 
   /**
	 * @Description 
	 * 批量添加数据 MED_PAY_TERM
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medpayterm/addBatchMedPayTerm", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedPayTerm(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedPayTerm> list_err = new ArrayList<MedPayTerm>();
		
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
			
			MedPayTerm medPayTerm = new MedPayTerm();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_term_code"))) {
						
					medPayTerm.setPay_term_code((String)jsonObj.get("pay_term_code"));
		    		mapVo.put("pay_term_code", jsonObj.get("pay_term_code"));
		    		} else {
						
						err_sb.append("付款条件编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_term_name"))) {
						
					medPayTerm.setPay_term_name((String)jsonObj.get("pay_term_name"));
		    		mapVo.put("pay_term_name", jsonObj.get("pay_term_name"));
		    		} else {
						
						err_sb.append("付款条件名称为空  ");
						
					}
			
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					medPayTerm.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		} else {
						
						err_sb.append("是否停用是为空  ");
						
					}
					

					
				MedPayTerm data_exc_extis = medPayTermService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPayTerm.setError_type(err_sb.toString());
					
					list_err.add(medPayTerm);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_term_name").toString()));
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_term_name").toString()));
			  
					String dataJson = medPayTermService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPayTerm data_exc = new MedPayTerm();
			
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

