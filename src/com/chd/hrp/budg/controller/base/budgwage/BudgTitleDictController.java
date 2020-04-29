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
import com.chd.hrp.budg.entity.BudgTitleDict;
import com.chd.hrp.budg.service.base.budgwage.BudgTitleDictService;
/**
 * 
 * @Description:
 * 职称字典
 * @Table:
 * BUDG_TITLE_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgTitleDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgTitleDictController.class);
	
	//引入Service服务
	@Resource(name = "budgTitleDictService")
	private final BudgTitleDictService budgTitleDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/budgTitleDictMainPage", method = RequestMethod.GET)
	public String budgTitleDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgtitle/budgTitleDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/budgTitleDictAddPage", method = RequestMethod.GET)
	public String budgTitleDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgtitle/budgTitleDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/addBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
       
		String budgTitleDictJson = budgTitleDictService.add(mapVo);

		return JSONObject.parseObject(budgTitleDictJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/budgTitleDictUpdatePage", method = RequestMethod.GET)
	public String budgTitleDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgTitleDict budgTitleDict = new BudgTitleDict();
    
		budgTitleDict = budgTitleDictService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgTitleDict.getGroup_id());
		mode.addAttribute("hos_id", budgTitleDict.getHos_id());
		mode.addAttribute("copy_code", budgTitleDict.getCopy_code());
		mode.addAttribute("title_code", budgTitleDict.getTitle_code());
		mode.addAttribute("title_name", budgTitleDict.getTitle_name());
		mode.addAttribute("is_stop", budgTitleDict.getIs_stop());
		mode.addAttribute("spell_code", budgTitleDict.getSpell_code());
		mode.addAttribute("wbx_code", budgTitleDict.getWbx_code());
		
		return "hrp/budg/base/budgwage/budgtitle/budgTitleDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/updateBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
	  
		String budgTitleDictJson = budgTitleDictService.update(mapVo);
		
		return JSONObject.parseObject(budgTitleDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/addOrUpdateBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgTitleDictJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("").toString()));
	  
		budgTitleDictJson = budgTitleDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgTitleDictJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/deleteBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgTitleDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("title_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgTitleDictJson = budgTitleDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgTitleDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 职称字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/queryBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgTitleDict = budgTitleDictService.query(getPage(mapVo));

		return JSONObject.parseObject(budgTitleDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 职称字典
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/budgTitleDictImportPage", method = RequestMethod.GET)
	public String budgTitleDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgwage/budgtitle/budgTitleDictImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 职称字典
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgwage/budgtitle/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","职称字典.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 职称字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgwage/budgtitle/readBudgTitleDictFiles",method = RequestMethod.POST)  
    public String readBudgTitleDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgTitleDict> list_err = new ArrayList<BudgTitleDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgTitleDict budgTitleDict = new BudgTitleDict();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行职称编码重复;");
						}
						
						if(temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行职称名称重复;");
						}
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						budgTitleDict.setTitle_code(temp[0]);
			    		mapVo.put("title_code", temp[0]);
		    		
			    		BudgTitleDict  code = budgTitleDictService.queryByCode(mapVo);
			    		
			    		if(code != null ){
			    			err_sb.append("职称编码已被占用;");
			    		}
					
					} else {
						
						err_sb.append("职称编码为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgTitleDict.setTitle_name(temp[1]);
			    		mapVo.put("title_name", temp[1]);
			    		
			    		int count = budgTitleDictService.queryNameExist(mapVo);
			    		
			    		if(count > 0 ){
			    			
			    			err_sb.append("职称名称已被占用;");
			    		}
			    		
					} else {
						
						err_sb.append("职称名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					budgTitleDict.setIs_stop(Integer.valueOf(temp[2]));
		    		mapVo.put("is_stop", temp[2]);
					
					} else {
						
						err_sb.append("是否停用为空;");
						
					}
					 
				BudgTitleDict data_exc_extis = budgTitleDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgTitleDict.setError_type(err_sb.toString());
					
					list_err.add(budgTitleDict);
					
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
			  
					addList.add(mapVo);
					
				}
				
			}
			if(list_err.size() == 0){
				
				String dataJson = budgTitleDictService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgTitleDict data_exc = new BudgTitleDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 职称字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgwage/budgtitle/addBatchBudgTitleDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgTitleDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgTitleDict> list_err = new ArrayList<BudgTitleDict>();
		
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
			
			BudgTitleDict budgTitleDict = new BudgTitleDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("title_code"))) {
						
					budgTitleDict.setTitle_code((String)jsonObj.get("title_code"));
		    		mapVo.put("title_code", jsonObj.get("title_code"));
		    		} else {
						
						err_sb.append("职称编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("title_name"))) {
						
					budgTitleDict.setTitle_name((String)jsonObj.get("title_name"));
		    		mapVo.put("title_name", jsonObj.get("title_name"));
		    		} else {
						
						err_sb.append("职称名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					budgTitleDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("0:不停用 1:停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					budgTitleDict.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					budgTitleDict.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					
				BudgTitleDict data_exc_extis = budgTitleDictService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgTitleDict.setError_type(err_sb.toString());
					
					list_err.add(budgTitleDict);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = budgTitleDictService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgTitleDict data_exc = new BudgTitleDict();
			
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

