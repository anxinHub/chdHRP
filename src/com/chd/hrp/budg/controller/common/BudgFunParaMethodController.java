/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
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
import com.chd.hrp.budg.entity.BudgFunParaMethod;
import com.chd.hrp.budg.entity.BudgFunType;
import com.chd.hrp.budg.service.common.BudgFunParaMethodService;
/**
 * 
 * @Description:
 * 函数参数取值表
 * @Table:
 * FUN_PARA_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgFunParaMethodController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFunParaMethodController.class);
	
	//引入Service服务
	@Resource(name = "funParaMethodService")
	private final BudgFunParaMethodService funParaMethodService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/budgFunParaMethodMainPage", method = RequestMethod.GET)
	public String budgFunParaMethodMainPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunparamethod/budgFunParaMethodMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/budgFunParaMethodAddPage", method = RequestMethod.GET)
	public String budgFunParaMethodAddPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunparamethod/budgFunParaMethodAdd";

	}

	/**
	 * @Description 
	 * 添加数据 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/addBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));
       
		String funParaMethodJson = funParaMethodService.add(mapVo);

		return JSONObject.parseObject(funParaMethodJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/budgFunParaMethodUpdatePage", method = RequestMethod.GET)
	public String budgFunParaMethodUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgFunParaMethod funParaMethod = new BudgFunParaMethod();
    
		funParaMethod = funParaMethodService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", funParaMethod.getGroup_id());
		mode.addAttribute("hos_id", funParaMethod.getHos_id());
		mode.addAttribute("copy_code", funParaMethod.getCopy_code());
		mode.addAttribute("para_code", funParaMethod.getPara_code());
		mode.addAttribute("para_name", funParaMethod.getPara_name());
		mode.addAttribute("para_sql", funParaMethod.getPara_sql());
		mode.addAttribute("spell_code", funParaMethod.getSpell_code());
		mode.addAttribute("wbx_code", funParaMethod.getWbx_code());
		mode.addAttribute("is_stop", funParaMethod.getIs_stop());
		
		return "hrp/budg/common/budgfunparamethod/budgFunParaMethodUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/updateBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));
	  
		String funParaMethodJson = funParaMethodService.update(mapVo);
		
		return JSONObject.parseObject(funParaMethodJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/addOrUpdateBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String funParaMethodJson ="";
		
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
	  
		funParaMethodJson = funParaMethodService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(funParaMethodJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/deleteBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgFunParaMethod(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("para_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String funParaMethodJson = funParaMethodService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(funParaMethodJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 函数参数取值表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/queryBudgFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String funParaMethod = funParaMethodService.query(getPage(mapVo));

		return JSONObject.parseObject(funParaMethod);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 函数参数取值表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/budgFunParaMethodImportPage", method = RequestMethod.GET)
	public String budgFunParaMethodImportPage(Model mode) throws Exception {

		return "hrp/budg/common/budgfunparamethod/budgFunParaMethodImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 函数参数取值表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/common/budgfunparamethod/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\base","函数参数取值模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 函数参数取值表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/common/budgfunparamethod/readBudgFunParaMethodFiles",method = RequestMethod.POST)  
    public String readBudgFunParaMethodFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgFunParaMethod> list_err = new ArrayList<BudgFunParaMethod>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgFunParaMethod funParaMethod = new BudgFunParaMethod();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行参数代码重复;");
						}
						if(temp[1].equals(error[1]) ){
							err_sb.append("第"+i+"行数据与第 "+j+"行参数名称重复;");
						}
					}
					 
					if (StringTool.isNotBlank(temp[0])) {
						
						funParaMethod.setPara_code(temp[0]);
			    		mapVo.put("para_code", temp[0]);
			    		
			    		BudgFunParaMethod code  = funParaMethodService.queryByCode(mapVo);
						
						if(code != null ){
							err_sb.append("参数代码已被占用;");
						}
					
					} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						funParaMethod.setPara_name(temp[1]);
			    		mapVo.put("para_name", temp[1]);
			    		
			    		int count = funParaMethodService.queryNameExist(mapVo);
			    		
			    		if(count > 0){
			    			err_sb.append("参数名称已被占用;");
			    		}
					
					} else {
						
						err_sb.append("参数名称为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					funParaMethod.setPara_sql(temp[2]);
		    		mapVo.put("para_sql", temp[2]);
					
					} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					 
					 
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					funParaMethod.setIs_stop(Integer.valueOf(temp[3]));
		    		mapVo.put("is_stop", temp[3]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 
					
					BudgFunParaMethod data_exc_extis = funParaMethodService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					funParaMethod.setError_type(err_sb.toString());
					
					list_err.add(funParaMethod);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));
			  
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = funParaMethodService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgFunParaMethod data_exc = new BudgFunParaMethod();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 函数参数取值表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/addBatchFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchFunParaMethod(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgFunParaMethod> list_err = new ArrayList<BudgFunParaMethod>();
		
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
			
			BudgFunParaMethod funParaMethod = new BudgFunParaMethod();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("para_code"))) {
						
					funParaMethod.setPara_code((String)jsonObj.get("para_code"));
		    		mapVo.put("para_code", jsonObj.get("para_code"));
		    		} else {
						
						err_sb.append("参数代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("para_name"))) {
						
					funParaMethod.setPara_name((String)jsonObj.get("para_name"));
		    		mapVo.put("para_name", jsonObj.get("para_name"));
		    		} else {
						
						err_sb.append("参数名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("para_sql"))) {
						
					funParaMethod.setPara_sql((String)jsonObj.get("para_sql"));
		    		mapVo.put("para_sql", jsonObj.get("para_sql"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					funParaMethod.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					funParaMethod.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					funParaMethod.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					
					BudgFunParaMethod data_exc_extis = funParaMethodService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					funParaMethod.setError_type(err_sb.toString());
					
					list_err.add(funParaMethod);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = funParaMethodService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgFunParaMethod data_exc = new BudgFunParaMethod();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	// 应用模式字典
		@RequestMapping(value = "/hrp/budg/common/budgfunparamethod/queryFunParaByDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryFunParaByDict(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			if (mapVo.get("copy_code") == null) {

				mapVo.put("copy_code", SessionManager.getCopyCode());

			}
			return funParaMethodService.queryFunParaByDict(mapVo);

		}

}

