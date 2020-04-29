/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgawardsitem;
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
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemDictService;
/**
 * 
 * @Description:
 * @Table:
 * BUDG_AWARDS_ITEM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAwardsItemDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsItemDictController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsItemDictService")
	private final BudgAwardsItemDictService budgAwardsItemDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictMainPage", method = RequestMethod.GET)
	public String budgAwardsItemDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictAddPage", method = RequestMethod.GET)
	public String budgAwardsItemDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/addBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("awards_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("awards_item_name").toString()));
       
		String budgAwardsItemDictJson = budgAwardsItemDictService.add(mapVo);

		return JSONObject.parseObject(budgAwardsItemDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictUpdatePage", method = RequestMethod.GET)
	public String budgAwardsItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgAwardsItemDict budgAwardsItemDict = new BudgAwardsItemDict();
    
		budgAwardsItemDict = budgAwardsItemDictService.queryByCode(mapVo);
		

		mode.addAttribute("hos_id", budgAwardsItemDict.getHos_id());
		mode.addAttribute("copy_code", budgAwardsItemDict.getCopy_code());
		mode.addAttribute("awards_item_code", budgAwardsItemDict.getAwards_item_code());
		mode.addAttribute("awards_item_name", budgAwardsItemDict.getAwards_item_name());
		mode.addAttribute("period_nature", budgAwardsItemDict.getPeriod_nature());
		mode.addAttribute("is_stop", budgAwardsItemDict.getIs_stop());
		mode.addAttribute("spell_code", budgAwardsItemDict.getSpell_code());
		mode.addAttribute("wbx_code", budgAwardsItemDict.getWbx_code());
		
		return "hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/updateBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("awards_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("awards_item_name").toString()));
	  
		String budgAwardsItemDictJson = budgAwardsItemDictService.update(mapVo);
		
		return JSONObject.parseObject(budgAwardsItemDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/addOrUpdateBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsItemDictJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("awards_item_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("awards_item_name").toString()));
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("awards_item_name").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("awards_item_name").toString()));
	  
		budgAwardsItemDictJson = budgAwardsItemDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsItemDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/deleteBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("awards_item_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgAwardsItemDictJson = budgAwardsItemDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsItemDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/queryBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String budgAwardsItemDict = budgAwardsItemDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsItemDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictImportPage", method = RequestMethod.GET)
	public String budgAwardsItemDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsitemdict/budgAwardsItemDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgawardsitem/awardsitemdict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","奖金项目字典模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgawardsitem/awardsitemdict/readBudgAwardsItemDictFiles",method = RequestMethod.POST)  
    public String readBudgAwardsItemDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAwardsItemDict> list_err = new ArrayList<BudgAwardsItemDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAwardsItemDict budgAwardsItemDict = new BudgAwardsItemDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		            
   		    		for(int j = i + 1 ; j < list.size(); j++){
   						String error[] = list.get(j);
   						if(temp[0].equals(error[0]) ){
   							err_sb.append("第"+i+"行数据与第 "+j+"行奖金项目编码重复;");
   						}
   						
   						if(temp[1].equals(error[1])){
   							err_sb.append("第"+i+"行数据与第 "+j+"行奖金项目名称重复;");
   						}
   					}
		    		         
					if (StringTool.isNotBlank(temp[0])) {
						
						budgAwardsItemDict.setAwards_item_code(temp[0]);
						
			    		mapVo.put("awards_item_code", temp[0]);
			    		
			    		BudgAwardsItemDict code = budgAwardsItemDictService.queryByCode(mapVo);

			    		if (code != null) {
			    			
			    			err_sb.append("奖金项目编码已被占用;");
			    			
			    		}
					
					} else {
						
						err_sb.append("奖金项目编码为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgAwardsItemDict.setAwards_item_name(temp[1]);
			    		mapVo.put("awards_item_name", temp[1]);
			    		
			    		int count  = budgAwardsItemDictService.queryNameExist(mapVo);
			    		
			    		if( count > 0){
			    			
			    			err_sb.append("奖金项目名称已被占用");
			    		}
					
					} else {
						
						err_sb.append("奖金项目名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
						budgAwardsItemDict.setPeriod_nature(temp[2]);
						
			    		mapVo.put("period_nature", temp[2]);
			    		
			    		int count = budgAwardsItemDictService.queryPeriodNatureExist(mapVo);
			    		
			    		if(count  == 0){
			    			err_sb.append("期间属性编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("期间属性为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgAwardsItemDict.setIs_stop(Integer.valueOf(temp[3]));
		    		mapVo.put("is_stop", temp[3]);
					
					} else {
						
						err_sb.append("是否停用为空;");
						
					}
					 
					
				BudgAwardsItemDict data_exc_extis = budgAwardsItemDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsItemDict.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsItemDict);
					
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("awards_item_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("awards_item_name").toString()));
			  
					addList.add(mapVo);
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgAwardsItemDictService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgAwardsItemDict data_exc = new BudgAwardsItemDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description (暂时不用  代码未调)
	 * 批量添加数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsitemdict/addBatchBudgAwardsItemDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAwardsItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAwardsItemDict> list_err = new ArrayList<BudgAwardsItemDict>();
		
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
			
			BudgAwardsItemDict budgAwardsItemDict = new BudgAwardsItemDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgAwardsItemDict.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_name"))) {
						
					budgAwardsItemDict.setAwards_item_name((String)jsonObj.get("awards_item_name"));
		    		mapVo.put("awards_item_name", jsonObj.get("awards_item_name"));
		    		} else {
						
						err_sb.append("奖金项目名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("period_nature"))) {
						
					budgAwardsItemDict.setPeriod_nature((String)jsonObj.get("period_nature"));
		    		mapVo.put("period_nature", jsonObj.get("period_nature"));
		    		} else {
						
						err_sb.append("期间属性为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgAwardsItemDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgAwardsItemDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgAwardsItemDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
				BudgAwardsItemDict data_exc_extis = budgAwardsItemDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsItemDict.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsItemDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgAwardsItemDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsItemDict data_exc = new BudgAwardsItemDict();
			
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

