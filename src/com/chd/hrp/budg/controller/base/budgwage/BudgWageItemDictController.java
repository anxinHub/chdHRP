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
import com.chd.hrp.budg.entity.BudgWageItemDict;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemDictService;
/**
 * 
 * @Description:
 * 工资项目字典
 * @Table:
 * BUDG_WAGE_ITEM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWageItemDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWageItemDictController.class);
	
	//引入Service服务
	@Resource(name = "budgWageItemDictService")
	private final BudgWageItemDictService budgWageItemDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictMainPage", method = RequestMethod.GET)
	public String budgWageItemDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictAddPage", method = RequestMethod.GET)
	public String budgWageItemDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/addBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
       
		String budgWageItemDictJson = budgWageItemDictService.add(mapVo);

		return JSONObject.parseObject(budgWageItemDictJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictUpdatePage", method = RequestMethod.GET)
	public String budgWageItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgWageItemDict budgWageItemDict = new BudgWageItemDict();
    
		budgWageItemDict = budgWageItemDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWageItemDict.getGroup_id());
		mode.addAttribute("hos_id", budgWageItemDict.getHos_id());
		mode.addAttribute("copy_code", budgWageItemDict.getCopy_code());
		mode.addAttribute("wage_item_code", budgWageItemDict.getWage_item_code());
		mode.addAttribute("wage_item_name", budgWageItemDict.getWage_item_name());
		mode.addAttribute("is_stop", budgWageItemDict.getIs_stop());
		mode.addAttribute("spell_code", budgWageItemDict.getSpell_code());
		mode.addAttribute("wbx_code", budgWageItemDict.getWbx_code());
		
		return "hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/updateBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
	  
		String budgWageItemDictJson = budgWageItemDictService.update(mapVo);
		
		return JSONObject.parseObject(budgWageItemDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/addOrUpdateBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWageItemDictJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("wage_item_name").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("wage_item_name").toString()));
	  
		budgWageItemDictJson = budgWageItemDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWageItemDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/deleteBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWageItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("wage_item_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWageItemDictJson = budgWageItemDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWageItemDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 工资项目字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/queryBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWageItemDict = budgWageItemDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWageItemDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 工资项目字典
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictImportPage", method = RequestMethod.GET)
	public String budgWageItemDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgwageitemdict/budgWageItemDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 工资项目字典
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgwage/budgwageitemdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","工资项目字典.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 工资项目字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgwage/budgwageitemdict/readBudgWageItemDictFiles",method = RequestMethod.POST)  
    public String readBudgWageItemDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWageItemDict> list_err = new ArrayList<BudgWageItemDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWageItemDict budgWageItemDict = new BudgWageItemDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行工资项目编码重复;");
						}
						
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行工资项目名称重复;");
						}
					}
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgWageItemDict.setWage_item_code(temp[0]);
			    		mapVo.put("wage_item_code", temp[0]);
			    		
			    		BudgWageItemDict code = budgWageItemDictService.queryByCode(mapVo);
			    		
			    		if( code != null ){
			    			err_sb.append("工资项目编码已被占用;");
			    		}
					
					} else {
						
						err_sb.append("工资项目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgWageItemDict.setWage_item_name(temp[1]);
			    		mapVo.put("wage_item_name", temp[1]);
			    		
			    		int count = budgWageItemDictService.queryNameExist(mapVo);
			    		
			    		if(count > 0 ){
			    			
			    			err_sb.append("职称名称已被占用;");
			    		}
					
					} else {
						
						err_sb.append("工资项目名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgWageItemDict.setIs_stop(Integer.valueOf(temp[2]));
			    		mapVo.put("is_stop", temp[2]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
				BudgWageItemDict data_exc_extis = budgWageItemDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWageItemDict.setError_type(err_sb.toString());
					
					list_err.add(budgWageItemDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
				  
				  addList.add(mapVo) ;
					
				}
				
			}
			
			if( list_err.size() == 0 ){
				
				String dataJson = budgWageItemDictService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgWageItemDict data_exc = new BudgWageItemDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 工资项目字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgwageitemdict/addBatchBudgWageItemDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWageItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWageItemDict> list_err = new ArrayList<BudgWageItemDict>();
		
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
			
			BudgWageItemDict budgWageItemDict = new BudgWageItemDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("wage_item_code"))) {
						
					budgWageItemDict.setWage_item_code((String)jsonObj.get("wage_item_code"));
		    		mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
		    		} else {
						
						err_sb.append("工资项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wage_item_name"))) {
						
					budgWageItemDict.setWage_item_name((String)jsonObj.get("wage_item_name"));
		    		mapVo.put("wage_item_name", jsonObj.get("wage_item_name"));
		    		} else {
						
						err_sb.append("工资项目名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgWageItemDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgWageItemDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgWageItemDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
				BudgWageItemDict data_exc_extis = budgWageItemDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWageItemDict.setError_type(err_sb.toString());
					
					list_err.add(budgWageItemDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgWageItemDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWageItemDict data_exc = new BudgWageItemDict();
			
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

