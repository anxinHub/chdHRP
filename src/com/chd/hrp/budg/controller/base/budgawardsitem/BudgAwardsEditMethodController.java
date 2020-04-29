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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgAwardsEditMethod;
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.entity.BudgFormulaSet;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsEditMethodService;
import com.chd.hrp.budg.service.base.budgawardsitem.BudgAwardsItemDictService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
import com.chd.hrp.budg.service.common.BudgFunService;
/**
 * 
 * @Description:
 * BUDG_AWARDS_EDIT_METHOD
 * @Table:
 * BUDG_AWARDS_EDIT_METHOD
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgAwardsEditMethodController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgAwardsEditMethodController.class);
	
	//引入Service服务
	@Resource(name = "budgAwardsEditMethodService")
	private final BudgAwardsEditMethodService budgAwardsEditMethodService = null;
	
	@Resource(name = "budgAwardsItemDictService")
	private final BudgAwardsItemDictService budgAwardsItemDictService = null;
	
	@Resource(name = "budgFormulaSetService")
	private final BudgFormulaSetService budgFormulaSetService = null;
	
	@Resource(name = "budgFunService")
	private final BudgFunService budgFunService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodMainPage", method = RequestMethod.GET)
	public String budgAwardsEditMethodMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodAddPage", method = RequestMethod.GET)
	public String budgAwardsEditMethodAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodAdd";

	}

	/**
	 * @Description 
	 * 添加数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/addBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAwardsEditMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		String budgAwardsEditMethodJson = budgAwardsEditMethodService.add(mapVo);

		return JSONObject.parseObject(budgAwardsEditMethodJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodUpdatePage", method = RequestMethod.GET)
	public String budgAwardsEditMethodUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgAwardsEditMethod budgAwardsEditMethod = new BudgAwardsEditMethod();
    
		budgAwardsEditMethod = budgAwardsEditMethodService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgAwardsEditMethod.getGroup_id());
		mode.addAttribute("hos_id", budgAwardsEditMethod.getHos_id());
		mode.addAttribute("copy_code", budgAwardsEditMethod.getCopy_code());
		mode.addAttribute("awards_item_code", budgAwardsEditMethod.getAwards_item_code());
		mode.addAttribute("edit_method", budgAwardsEditMethod.getEdit_method());
		mode.addAttribute("get_way", budgAwardsEditMethod.getGet_way());
		mode.addAttribute("formula_id", budgAwardsEditMethod.getFormula_id());
		mode.addAttribute("fun_id", budgAwardsEditMethod.getFun_id());
		
		return "hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/updateBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAwardsEditMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgAwardsEditMethodJson = budgAwardsEditMethodService.update(mapVo);
		
		return JSONObject.parseObject(budgAwardsEditMethodJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/addOrUpdateBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgAwardsEditMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgAwardsEditMethodJson ="";
		

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
	  
		budgAwardsEditMethodJson = budgAwardsEditMethodService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgAwardsEditMethodJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/deleteBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAwardsEditMethod(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgAwardsEditMethodJson = budgAwardsEditMethodService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgAwardsEditMethodJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/queryBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAwardsEditMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgAwardsEditMethod = budgAwardsEditMethodService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAwardsEditMethod);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_AWARDS_EDIT_METHOD
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodImportPage", method = RequestMethod.GET)
	public String budgAwardsEditMethodImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgawardsitem/awardseditmethod/budgAwardsEditMethodImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_AWARDS_EDIT_METHOD
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/base/budgawardsitem/awardseditmethod/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","奖金项目预算编制方法维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgawardsitem/awardseditmethod/readBudgAwardsEditMethodFiles",method = RequestMethod.POST)  
    public String readBudgAwardsEditMethodFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgAwardsEditMethod> list_err = new ArrayList<BudgAwardsEditMethod>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		
		String[] editMethod = {"01","02","03","04"} ;
		
		String[] getWay = {"01","02","03","04","05"} ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgAwardsEditMethod budgAwardsEditMethod = new BudgAwardsEditMethod();
				
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
						
						budgAwardsEditMethod.setAwards_item_code(temp[0]);
			    		mapVo.put("awards_item_code", temp[0]);
		    		
			    		BudgAwardsItemDict code = budgAwardsItemDictService.queryByCode(mapVo);
			    		
			    		if( code == null ){
			    			
			    			err_sb.append("奖金项目编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						budgAwardsEditMethod.setEdit_method(temp[1]);
			    		mapVo.put("edit_method", temp[1]);
			    		
			    		if(!Arrays.asList(editMethod).contains(temp[1])){
							err_sb.append("编制方法输入格式不正确(01-04或不填);");
						}
					
					} else {
						
						mapVo.put("edit_method", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						budgAwardsEditMethod.setGet_way(temp[2]);
			    		mapVo.put("get_way", temp[2]);
			    		
			    		if(!Arrays.asList(getWay).contains(temp[2])){
							err_sb.append("取值方法输入格式不正确(01-05或不填);");
						}else{
							if(StringTool.isNotBlank(mapVo.get("edit_method"))){
								
								if(Arrays.asList(editMethod).contains(temp[1])){
									// 查询  编制方法与取值方法对应关系 是否存在
					    			int count = budgAwardsEditMethodService.queryEditGetShip(mapVo) ;
					    			
					    			if(count == 0 ){
					    				
					    				err_sb.append("填写的编制方法与取值方法不对应！");
						    			
						    		}
								}
				    			
				    		}else{
				    			err_sb.append("填写取值方法则必须填写编制方法！");
				    		}
						}
			    		
					} else {
						
						mapVo.put("get_way", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgAwardsEditMethod.setFormula_id(temp[3]);
			    		mapVo.put("formula_id", temp[3]);
			    		
			    		if("03".equals(mapVo.get("get_way"))){
			    			
			    			BudgFormulaSet formula = budgFormulaSetService.queryByCode(mapVo);
			    			
			    			if(formula == null ){
			    				err_sb.append("计算公式不存在！");
			    			}
			    			
			    		}else{
			    			err_sb.append("取值方法不是计算公式,不能填写计算公式！");
			    		}
					
					} else {
						
						mapVo.put("formula_id", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgAwardsEditMethod.setFun_id(temp[4]);
			    		mapVo.put("fun_id", temp[4]);
			    		
			    		if("04".equals(mapVo.get("get_way"))){
			    			
			    			Map<String,Object> fun = budgFunService.queryByCode(mapVo);
			    			
			    			if(fun == null ){
			    				err_sb.append("取值函数不存在！");
			    			}
			    			
			    		}else{
			    			err_sb.append("取值方法不是取值函数,不能填写取值函数！");
			    		}
					
					} else {
						
						mapVo.put("fun_id", "");
						
					}
					 
					
				BudgAwardsEditMethod data_exc_extis = budgAwardsEditMethodService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsEditMethod.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsEditMethod);
					
				} else {
						
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgAwardsEditMethodService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsEditMethod data_exc = new BudgAwardsEditMethod();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_AWARDS_EDIT_METHOD
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgawardsitem/awardseditmethod/addBatchBudgAwardsEditMethod", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgAwardsEditMethod(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgAwardsEditMethod> list_err = new ArrayList<BudgAwardsEditMethod>();
		
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
			
			BudgAwardsEditMethod budgAwardsEditMethod = new BudgAwardsEditMethod();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("awards_item_code"))) {
						
					budgAwardsEditMethod.setAwards_item_code((String)jsonObj.get("awards_item_code"));
		    		mapVo.put("awards_item_code", jsonObj.get("awards_item_code"));
		    		} else {
						
						err_sb.append("奖金项目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("edit_method"))) {
						
					budgAwardsEditMethod.setEdit_method((String)jsonObj.get("edit_method"));
		    		mapVo.put("edit_method", jsonObj.get("edit_method"));
		    		} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_way"))) {
						
					budgAwardsEditMethod.setGet_way((String)jsonObj.get("get_way"));
		    		mapVo.put("get_way", jsonObj.get("get_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgAwardsEditMethod.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgAwardsEditMethod.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					
				BudgAwardsEditMethod data_exc_extis = budgAwardsEditMethodService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgAwardsEditMethod.setError_type(err_sb.toString());
					
					list_err.add(budgAwardsEditMethod);
					
				} else {
			  
					String dataJson = budgAwardsEditMethodService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgAwardsEditMethod data_exc = new BudgAwardsEditMethod();
			
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

