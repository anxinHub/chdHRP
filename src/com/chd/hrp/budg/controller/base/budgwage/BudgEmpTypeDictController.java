/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgwage;
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
import com.chd.hrp.budg.entity.BudgEmpTypeDict;
import com.chd.hrp.budg.service.base.budgwage.BudgEmpTypeDictService;
/**
 * 
 * @Description:
 * 职工类别字典
 * @Table:
 * BUDG_EMP_TYPE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgEmpTypeDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgEmpTypeDictController.class);
	
	//引入Service服务
	@Resource(name = "budgEmpTypeDictService")
	private final BudgEmpTypeDictService budgEmpTypeDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictMainPage", method = RequestMethod.GET)
	public String budgEmpTypeDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictAddPage", method = RequestMethod.GET)
	public String budgEmpTypeDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/addBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgEmpTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
       
		String budgEmpTypeDictJson = budgEmpTypeDictService.add(mapVo);

		return JSONObject.parseObject(budgEmpTypeDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictUpdatePage", method = RequestMethod.GET)
	public String budgEmpTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgEmpTypeDict budgEmpTypeDict = new BudgEmpTypeDict();
    
		budgEmpTypeDict = budgEmpTypeDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgEmpTypeDict.getGroup_id());
		mode.addAttribute("hos_id", budgEmpTypeDict.getHos_id());
		mode.addAttribute("copy_code", budgEmpTypeDict.getCopy_code());
		mode.addAttribute("type_code", budgEmpTypeDict.gettype_code());
		mode.addAttribute("type_name", budgEmpTypeDict.gettype_name());
		mode.addAttribute("is_stop", budgEmpTypeDict.getIs_stop());
		mode.addAttribute("spell_code", budgEmpTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", budgEmpTypeDict.getWbx_code());
		
		return "hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/updateBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgEmpTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
	  
		String budgEmpTypeDictJson = budgEmpTypeDictService.update(mapVo);
		
		return JSONObject.parseObject(budgEmpTypeDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/addOrUpdateBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdatebudgEmpTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgEmpTypeDictJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());   
		
		mapVo.put("hos_id", SessionManager.getHosId());   
		
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());   
			
			detailVo.put("hos_id", SessionManager.getHosId());   
			
			detailVo.put("copy_code", SessionManager.getCopyCode());   
			  
			detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
			
			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
		  
			budgEmpTypeDictJson = budgEmpTypeDictService.addOrUpdate(detailVo);
			
		}
		return JSONObject.parseObject(budgEmpTypeDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/deleteBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgEmpTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgEmpTypeDictJson = budgEmpTypeDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgEmpTypeDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 职工类别字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/queryBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgEmpTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgEmpTypeDict = budgEmpTypeDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgEmpTypeDict);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 职工类别字典
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/importBudgEmpTypeDictPage", method = RequestMethod.GET)
	public String budgEmpTypeDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgemptype/budgEmpTypeDictImport";

	}
	
	 /**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/importBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgBasicIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgEmpTypeDictService.importBudgEmpTypeDict(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
		
   /**
	 * @Description 
	 * 批量添加数据 职工类别字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgemptype/addBatchBudgEmpTypeDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgEmpTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgEmpTypeDict> list_err = new ArrayList<BudgEmpTypeDict>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Iterator it = json.iterator();
		
		try {
		
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgEmpTypeDict budgEmpTypeDict = new BudgEmpTypeDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					budgEmpTypeDict.settype_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("类别编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					budgEmpTypeDict.settype_name((String)jsonObj.get("type_name"));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("类别名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgEmpTypeDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgEmpTypeDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgEmpTypeDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
				BudgEmpTypeDict data_exc_extis = budgEmpTypeDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgEmpTypeDict.setError_type(err_sb.toString());
					
					list_err.add(budgEmpTypeDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgEmpTypeDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgEmpTypeDict data_exc = new BudgEmpTypeDict();
			
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

