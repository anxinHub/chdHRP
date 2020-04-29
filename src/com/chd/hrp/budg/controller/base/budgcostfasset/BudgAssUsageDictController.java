
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgcostfasset;
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
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.budg.entity.BudgAssUsageDict;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssUsageDictService;

/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class BudgAssUsageDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAssUsageDictController.class);
	
	//引入Service服务
	@Resource(name = "budgAssUsageDictService")
	private final BudgAssUsageDictService dudgAssUsageDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictMainPage", method = RequestMethod.GET)
	public String assUsageDictMainPage(Model mode) throws Exception {
		
		mode.addAttribute("hos_name", SessionManager.getHosName());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		
		return "hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictAddPage", method = RequestMethod.GET)
	public String assUsageDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/addBudgAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssUsageDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assUsageDictJson = "" ;
		
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
	  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
	 
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));
	
		try{
	
		  assUsageDictJson = dudgAssUsageDictService.addAssUsageDict(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		return JSONObject.parseObject(assUsageDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictUpdatePage", method = RequestMethod.GET)
	public String assUsageDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgAssUsageDict assUsageDict = new BudgAssUsageDict();
    
		assUsageDict = dudgAssUsageDictService.queryAssUsageDictByCode(mapVo);
		
		mode.addAttribute("group_id", assUsageDict.getGroup_id());
		mode.addAttribute("hos_id", assUsageDict.getHos_id());
		mode.addAttribute("copy_code", assUsageDict.getCopy_code());
		mode.addAttribute("equi_usage_code", assUsageDict.getEqui_usage_code());
		mode.addAttribute("equi_usage_name", assUsageDict.getEqui_usage_name());
		mode.addAttribute("spell_code", assUsageDict.getSpell_code());
		mode.addAttribute("wbx_code", assUsageDict.getWbx_code());
		mode.addAttribute("is_stop", assUsageDict.getIs_stop());
		
		return "hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/updateBudgAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssUsageDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assUsageDictJson = "" ;
		
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
		 
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
		 
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));
		
		try{
		 
			assUsageDictJson = dudgAssUsageDictService.updateAssUsageDict(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assUsageDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/deleteBudgAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssUsageDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String assUsageDictJson =  "" ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("equi_usage_code", ids[3]);
				
				listVo.add(mapVo);
	      
			}
	   
			try{
			 
				assUsageDictJson = dudgAssUsageDictService.deleteBatchAssUsageDict(listVo);
		  
			}catch(Exception e){
			
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		
			}
	 
			return JSONObject.parseObject(assUsageDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/queryBudgAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssUsageDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assUsageDict = dudgAssUsageDictService.queryAssUsageDict(getPage(mapVo));

		return JSONObject.parseObject(assUsageDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050104 资产用途
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictImportPage", method = RequestMethod.GET)
	public String assUsageDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/budgassusagedict/budgAssUsageDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050104 资产用途
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgcostfasset/budgassusagedict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"base\\目录","资产用途字典.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050104 资产用途
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/       
	 @RequestMapping(value="/hrp/budg/base/budgcostfasset/budgassusagedict/readCostBonusCostRelaFiles",method = RequestMethod.POST)  
    public String readCostBonusCostRelaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAssUsageDict> list_err = new ArrayList<BudgAssUsageDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAssUsageDict assUsageDict = new BudgAssUsageDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		mapVo.put("hos_id", SessionManager.getHosId());  
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		  
					if (StringTool.isNotBlank(temp[0])) {
						
					assUsageDict.setEqui_usage_code(temp[0]);
								
		    		mapVo.put("equi_usage_code", temp[0]);
		    		
					} else {
						
						err_sb.append("用途编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					assUsageDict.setEqui_usage_name(temp[1]);
								
		    		mapVo.put("equi_usage_name", temp[1]);
		    		
					} else {
						
						err_sb.append("用途名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					assUsageDict.setSpell_code(temp[2]);
								
		    		mapVo.put("is_stop", temp[2]);
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					BudgAssUsageDict data_exc_extis = dudgAssUsageDictService.queryAssUsageDictByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assUsageDict.setError_type(err_sb.toString());
					
					list_err.add(assUsageDict);
					
				} else {
				
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
				
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));
					
					try{
					
						String dataJson = dudgAssUsageDictService.addAssUsageDict(mapVo);
					
					}catch(Exception e){
						
						return  "{\"error\":\""+e.getMessage()+" \"}" ;
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAssUsageDict data_exc = new BudgAssUsageDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050104 资产用途
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassusagedict/addBatchAssUsageDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssUsageDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAssUsageDict> list_err = new ArrayList<BudgAssUsageDict>();
		
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
			
			BudgAssUsageDict assUsageDict = new BudgAssUsageDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					assUsageDict.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集体ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					assUsageDict.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					assUsageDict.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("equi_usage_code"))) {
						
					assUsageDict.setEqui_usage_code((String)jsonObj.get("equi_usage_code"));
											
		    		mapVo.put("equi_usage_code", jsonObj.get("equi_usage_code"));
		    		
					} else {
						
						err_sb.append("用途编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("equi_usage_name"))) {
						
					assUsageDict.setEqui_usage_name((String)jsonObj.get("equi_usage_name"));
											
		    		mapVo.put("equi_usage_name", jsonObj.get("equi_usage_name"));
		    		
					} else {
						
						err_sb.append("用途名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					assUsageDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
											
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					BudgAssUsageDict data_exc_extis = dudgAssUsageDictService.queryAssUsageDictByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assUsageDict.setError_type(err_sb.toString());
					
					list_err.add(assUsageDict);
					
				} else {
				 
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
				 
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));
					
					try{
					
						String dataJson = dudgAssUsageDictService.addAssUsageDict(mapVo);
					
					}catch(Exception e){
					
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAssUsageDict data_exc = new BudgAssUsageDict();
			
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

