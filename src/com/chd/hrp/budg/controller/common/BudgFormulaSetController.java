/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.hrp.budg.entity.BudgFormulaSet;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
/**
 * 
 * @Description:
 * 计算公式
 * @Table:
 * BUDG_FORMULA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFormulaSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFormulaSetController.class);
	
	//引入Service服务
	@Resource(name = "budgFormulaSetService")
	private final BudgFormulaSetService budgFormulaSetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaMainPage", method = RequestMethod.GET)
	public String budgFormulaMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgformula/budgFormulaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaAddPage", method = RequestMethod.GET)
	public String budgFormulaAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgformula/budgFormulaAdd";

	}
	
	/**
	 * @Description 
	 *  选择计算公式  页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaPage", method = RequestMethod.GET)
	public String budgFormulaPage(Model mode) throws Exception {

		return "hrp/budg/common/budgformula/budgFormula";

	}
	
	/**
	 * @Description 
	 *  业务预算  选择计算公式  页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaBusinessPage", method = RequestMethod.GET)
	public String budgFormulaBusinessPage(Model mode) throws Exception {

		return "hrp/budg/common/budgformula/budgFormulaBusiness";

	}
	
	/**
	 * @Description 
	 * 添加数据 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/addBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		String budgFormulaSetJson = null;
		try {
			budgFormulaSetJson = budgFormulaSetService.add(mapVo);
		} catch (Exception e) {
			budgFormulaSetJson = e.getMessage();
		}
				

		return JSONObject.parseObject(budgFormulaSetJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaUpdatePage", method = RequestMethod.GET)
	public String budgFormulaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> budgFormulaSet  = budgFormulaSetService.queryByCode(mapVo);
		
		Map<String,Object> map = budgFormulaSetService.queryFormulaStack(mapVo);
		
		mode.addAttribute("group_id", budgFormulaSet.get("group_id"));
		mode.addAttribute("hos_id", budgFormulaSet.get("hos_id"));
		mode.addAttribute("copy_code", budgFormulaSet.get("copy_code"));
		mode.addAttribute("formula_id", budgFormulaSet.get("formula_id"));
		mode.addAttribute("formula_name", budgFormulaSet.get("formula_name"));
		mode.addAttribute("formula_ca", budgFormulaSet.get("formula_ca"));
		mode.addAttribute("formula_en", budgFormulaSet.get("formula_en"));
		mode.addAttribute("formula_stack", map.get("formula_stack"));
		
		return "hrp/budg/common/budgformula/budgFormulaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/updateBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgFormulaSetJson = null;
		try {
			budgFormulaSetJson = budgFormulaSetService.update(mapVo);
		} catch (Exception e) {
			budgFormulaSetJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(budgFormulaSetJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/addOrUpdateBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgFormulaSetJson ="";
		

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
	  
		budgFormulaSetJson = budgFormulaSetService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgFormulaSetJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/deleteBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFormula(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("formula_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgFormulaSetJson = null ;
		try {
			budgFormulaSetJson = budgFormulaSetService.deleteBatch(listVo);
		} catch (Exception e) {
			budgFormulaSetJson = e.getMessage();
		}
				
			
	  return JSONObject.parseObject(budgFormulaSetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 计算公式
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/queryBudgFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		String budgFormulaSet = budgFormulaSetService.query(getPage(mapVo));

		return JSONObject.parseObject(budgFormulaSet);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 计算公式
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaImportPage", method = RequestMethod.GET)
	public String budgFormulaImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgformula/budgFormulaImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 计算公式
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgformula/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","计算公式.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 计算公式
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgformula/readBudgFormulaFiles",method = RequestMethod.POST)  
    public String readBudgFormulaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgFormulaSet> list_err = new ArrayList<BudgFormulaSet>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgFormulaSet budgFormulaSet = new BudgFormulaSet();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
		    		mapVo.put("formula_id", temp[3]);
					
					} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("formula_ca", temp[4]);
					
					} else {
						
						err_sb.append("公式中文为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
		    		mapVo.put("formula_en", temp[5]);
					
					} else {
						
						err_sb.append("公式英文为空  ");
						
					}
					 
					
				Map<String,Object> data_exc_extis = budgFormulaSetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgFormulaSet.setError_type(err_sb.toString());
					
					list_err.add(budgFormulaSet);
					
				} else {
			  
					String dataJson = budgFormulaSetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFormulaSet data_exc = new BudgFormulaSet();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 计算公式
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/addBatchBudgFormula", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgFormula(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFormulaSet> list_err = new ArrayList<BudgFormulaSet>();
		
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
			
			BudgFormulaSet budgFormulaSet = new BudgFormulaSet();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_ca"))) {
						
		    		mapVo.put("formula_ca", jsonObj.get("formula_ca"));
		    		} else {
						
						err_sb.append("公式中文为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_en"))) {
						
		    		mapVo.put("formula_en", jsonObj.get("formula_en"));
		    		} else {
						
						err_sb.append("公式英文为空  ");
						
					}
					
					
					Map<String,Object> data_exc_extis = budgFormulaSetService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgFormulaSet.setError_type(err_sb.toString());
					
					list_err.add(budgFormulaSet);
					
				} else {
			  
					String dataJson = budgFormulaSetService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFormulaSet data_exc = new BudgFormulaSet();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	/**
	 * 计算元素 树加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/common/budgformula/queryElementTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIndexTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String assSupAttr = budgFormulaSetService.queryElementTree(getPage(mapVo));

		return JSONObject.parseObject(assSupAttr);
	}
    
}

