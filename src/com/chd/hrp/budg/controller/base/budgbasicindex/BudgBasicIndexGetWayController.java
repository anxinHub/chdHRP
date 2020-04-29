/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.base.budgbasicindex;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.budg.entity.BudgBasicIndexGetWay;
import com.chd.hrp.budg.service.base.budgbasicindex.BudgBasicIndexGetWayService;

/**
 * 
 * @Description:
 *基本指标取值方法
 * @Table:
 * BUDG_BASIC_INDEX_GET_WAY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Controller
public class BudgBasicIndexGetWayController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgBasicIndexGetWayController.class);
	
	//引入Service服务
	@Resource(name = "budgBasicIndexGetWayService")
	private final BudgBasicIndexGetWayService budgBasicIndexGetWayService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayMainPage", method = RequestMethod.GET)
	public String budgBasicIndexGetWayMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayAddPage", method = RequestMethod.GET)
	public String budgBasicIndexGetWayAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayAdd";

	}

	/**
	 * @Description 
	 * 添加数据 基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/addBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBasicIndexGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgBasicIndexGetWayJson = null ;
		try {
			
			budgBasicIndexGetWayJson = budgBasicIndexGetWayService.add(mapVo);
			
		} catch (Exception e) {
			
			budgBasicIndexGetWayJson = e.getMessage();
		}
				

		return JSONObject.parseObject(budgBasicIndexGetWayJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面  基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayUpdatePage", method = RequestMethod.GET)
	public String budgBasicIndexGetWayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> budgBasicIndexGetWay = budgBasicIndexGetWayService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgBasicIndexGetWay.get("group_id"));
		mode.addAttribute("hos_id", budgBasicIndexGetWay.get("hos_id"));
		mode.addAttribute("copy_code", budgBasicIndexGetWay.get("copy_code"));
		mode.addAttribute("index_code", budgBasicIndexGetWay.get("index_code"));
		mode.addAttribute("index_name", budgBasicIndexGetWay.get("index_name"));
		mode.addAttribute("index_nature", budgBasicIndexGetWay.get("index_nature"));
		mode.addAttribute("index_nature_name", budgBasicIndexGetWay.get("index_nature_name"));
		mode.addAttribute("index_describe", budgBasicIndexGetWay.get("index_describe"));
		mode.addAttribute("get_value_way", budgBasicIndexGetWay.get("get_value_way"));
		mode.addAttribute("get_value_way_name", budgBasicIndexGetWay.get("get_value_way_name"));
		mode.addAttribute("formula_id", budgBasicIndexGetWay.get("formula_id"));
		mode.addAttribute("formula_name", budgBasicIndexGetWay.get("formula_name"));
		mode.addAttribute("formula_ca", budgBasicIndexGetWay.get("formula_ca"));
		mode.addAttribute("fun_id", budgBasicIndexGetWay.get("fun_id"));
		mode.addAttribute("fun_name", budgBasicIndexGetWay.get("fun_name"));
		mode.addAttribute("fun_method_chs", budgBasicIndexGetWay.get("fun_method_chs"));
		
		return "hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/updateBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgBasicIndexGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgBasicIndexGetWayJson = null;
		
		try {
			
			budgBasicIndexGetWayJson = budgBasicIndexGetWayService.update(mapVo);
			
		} catch (Exception e) {

			budgBasicIndexGetWayJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(budgBasicIndexGetWayJson);
	}
	
	/**
	 * @Description 
	 * 更新数据  基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/addOrUpdateBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgBasicIndexGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgBasicIndexGetWayJson ="";
		

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
	  
		budgBasicIndexGetWayJson = budgBasicIndexGetWayService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgBasicIndexGetWayJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/deleteBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBasicIndexGetWay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("index_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgBasicIndexGetWayJson = null ;
		
		try {
			
			budgBasicIndexGetWayJson = budgBasicIndexGetWayService.deleteBatch(listVo);
			
		} catch (Exception e) {
			
			budgBasicIndexGetWayJson =e.getMessage();
			
		}
		
	  return JSONObject.parseObject(budgBasicIndexGetWayJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 基本指标取值方法
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/queryBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBasicIndexGetWay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String budgBasicIndexGetWay = budgBasicIndexGetWayService.query(getPage(mapVo));

		return JSONObject.parseObject(budgBasicIndexGetWay);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 基本指标取值方法
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayImportPage", method = RequestMethod.GET)
	public String budgBasicIndexGetWayImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgbasicindex/basicindexgetway/budgBasicIndexGetWayImport";

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
	 @RequestMapping(value="hrp/budg/base/budgbasicindex/basicindexgetway/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","取值方法（GET_VALUE_WAY）取自系统字典表01手工录入02取值函数03计算公式.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 基本指标取值方法
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/base/budgbasicindex/basicindexgetway/readBudgBasicIndexGetWayFiles",method = RequestMethod.POST)  
    public String readBudgBasicIndexGetWayFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgBasicIndexGetWay> list_err = new ArrayList<BudgBasicIndexGetWay>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgBasicIndexGetWay budgBasicIndexGetWay = new BudgBasicIndexGetWay();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgBasicIndexGetWay.setIndex_code(temp[3]);
		    		mapVo.put("index_code", temp[3]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgBasicIndexGetWay.setGet_value_way(temp[4]);
		    		mapVo.put("get_value_way", temp[4]);
					
					} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgBasicIndexGetWay.setFormula_id(temp[5]);
		    		mapVo.put("formula_id", temp[5]);
					
					} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgBasicIndexGetWay.setFun_id(temp[6]);
		    		mapVo.put("fun_id", temp[6]);
					
					} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					 
					
				Map<String,Object> data_exc_extis = budgBasicIndexGetWayService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgBasicIndexGetWay.setError_type(err_sb.toString());
					
					list_err.add(budgBasicIndexGetWay);
					
				} else {
			  
					String dataJson = budgBasicIndexGetWayService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicIndexGetWay data_exc = new BudgBasicIndexGetWay();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据  基本指标取值方法
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/base/budgbasicindex/basicindexgetway/addBatchBudgBasicIndexGetWay", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgBasicIndexGetWay(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgBasicIndexGetWay> list_err = new ArrayList<BudgBasicIndexGetWay>();
		
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
			
			BudgBasicIndexGetWay budgBasicIndexGetWay = new BudgBasicIndexGetWay();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgBasicIndexGetWay.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_value_way"))) {
						
					budgBasicIndexGetWay.setGet_value_way((String)jsonObj.get("get_value_way"));
		    		mapVo.put("get_value_way", jsonObj.get("get_value_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgBasicIndexGetWay.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgBasicIndexGetWay.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					
				BudgBasicIndexGetWay data_exc_extis = budgBasicIndexGetWayService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgBasicIndexGetWay.setError_type(err_sb.toString());
					
					list_err.add(budgBasicIndexGetWay);
					
				} else {
			  
					String dataJson = budgBasicIndexGetWayService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgBasicIndexGetWay data_exc = new BudgBasicIndexGetWay();
			
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

