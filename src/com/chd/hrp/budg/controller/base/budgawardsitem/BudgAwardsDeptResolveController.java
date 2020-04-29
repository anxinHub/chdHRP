/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgawardsitem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.chd.hrp.budg.entity.BudgAwardsDeptResolve;
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsDeptResolveService;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemDictService;
/**
 * 
 * @Description:
 * 奖金项目科室分解方法
 * @Table:
 * BUDG_AWARDS_DEPT_RESOLVE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAwardsDeptResolveController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsDeptResolveController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsDeptResolveService")
	private final BudgAwardsDeptResolveService budgAwardsDeptResolveService = null;
	
	@Resource(name = "budgAwardsItemDictService")
	private final BudgAwardsItemDictService budgAwardsItemDictService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveMainPage", method = RequestMethod.GET)
	public String budgAwardsDeptResolveMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveAddPage", method = RequestMethod.GET)
	public String budgAwardsDeptResolveAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveAdd";

	}

	/**
	 * @Description 
	 * 添加数据 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/addBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsDeptResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
       
		String budgAwardsDeptResolveJson = budgAwardsDeptResolveService.add(mapVo);

		return JSONObject.parseObject(budgAwardsDeptResolveJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveUpdatePage", method = RequestMethod.GET)
	public String budgAwardsDeptResolveUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		BudgAwardsDeptResolve budgAwardsDeptResolve = new BudgAwardsDeptResolve();
    
		budgAwardsDeptResolve = budgAwardsDeptResolveService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsDeptResolve.getGroup_id());
		mode.addAttribute("hos_id", budgAwardsDeptResolve.getHos_id());
		mode.addAttribute("copy_code", budgAwardsDeptResolve.getCopy_code());
		mode.addAttribute("awards_item_code", budgAwardsDeptResolve.getAwards_item_code());
		mode.addAttribute("resolve_way", budgAwardsDeptResolve.getResolve_way());
		
		return "hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/updateBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsDeptResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAwardsDeptResolveJson = budgAwardsDeptResolveService.update(mapVo);
		
		return JSONObject.parseObject(budgAwardsDeptResolveJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/addOrUpdateBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsDeptResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsDeptResolveJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
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
	  
		budgAwardsDeptResolveJson = budgAwardsDeptResolveService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsDeptResolveJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/deleteBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsDeptResolve(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgAwardsDeptResolveJson = budgAwardsDeptResolveService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsDeptResolveJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 奖金项目科室分解方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/queryBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsDeptResolve(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgAwardsDeptResolve = budgAwardsDeptResolveService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsDeptResolve);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 奖金项目科室分解方法
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveImportPage", method = RequestMethod.GET)
	public String budgAwardsDeptResolveImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardsdeptresolve/budgAwardsDeptResolveImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 奖金项目科室分解方法
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgawardsitem/awardsdeptresolve/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","奖金项目科室分解方法维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 奖金项目科室分解方法
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgawardsitem/awardsdeptresolve/readBudgAwardsDeptResolveFiles",method = RequestMethod.POST)  
    public String readBudgAwardsDeptResolveFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAwardsDeptResolve> list_err = new ArrayList<BudgAwardsDeptResolve>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		String[] resolveMethod = {"01","02","03","04","05"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAwardsDeptResolve budgAwardsDeptResolve = new BudgAwardsDeptResolve();
				
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
						
					}
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						budgAwardsDeptResolve.setAwards_item_code(temp[0]);
			    		mapVo.put("awards_item_code", temp[0]);
						
			    		BudgAwardsItemDict code = budgAwardsItemDictService.queryByCode(mapVo);
			    		
			    		if( code == null ){
			    			
			    			err_sb.append("奖金项目编码不存在;");
			    		}
			    		
					} else {
						
						err_sb.append("奖金项目编码为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgAwardsDeptResolve.setResolve_way(temp[1]);
			    		mapVo.put("resolve_way", temp[1]);
			    		
			    		if(!Arrays.asList(resolveMethod).contains(temp[1])){
							err_sb.append("分解方法输入格式不正确(01-05);");
						}
			    		
					
					} else {
						
						err_sb.append("分解方法为空;");
						
					}
					 
					
				BudgAwardsDeptResolve data_exc_extis = budgAwardsDeptResolveService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsDeptResolve.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsDeptResolve);
					
				} else {
			  
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				String dataJson = budgAwardsDeptResolveService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsDeptResolve data_exc = new BudgAwardsDeptResolve();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 奖金项目科室分解方法
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardsdeptresolve/addBatchBudgAwardsDeptResolve", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAwardsDeptResolve(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAwardsDeptResolve> list_err = new ArrayList<BudgAwardsDeptResolve>();
		
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
			
			BudgAwardsDeptResolve budgAwardsDeptResolve = new BudgAwardsDeptResolve();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgAwardsDeptResolve.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空;");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
						budgAwardsDeptResolve.setResolve_way((String)jsonObj.get("resolve_way"));
			    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空 ;");
						
					}
					
					
				BudgAwardsDeptResolve data_exc_extis = budgAwardsDeptResolveService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsDeptResolve.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsDeptResolve);
					
				} else {
			  
					String dataJson = budgAwardsDeptResolveService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsDeptResolve data_exc = new BudgAwardsDeptResolve();
			
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

