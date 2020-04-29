/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.controller;
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
import org.nutz.lang.Strings;
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
import com.chd.hrp.cost.entity.CostParaNatur;
import com.chd.hrp.cost.entity.CostParaType;
import com.chd.hrp.cost.service.CostParaNaturService;
/**
 * 
 * @Description:
 * 成本_分摊类别
 * @Table:
 * COST_PARA_natur
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class CostParaNaturController extends BaseController{
	
	private static Logger logger = Logger.getLogger(CostParaNaturController.class);
	
	//引入Service服务
	@Resource(name = "costParaNaturService")
	private final CostParaNaturService costParaNaturService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/costParaNaturMainPage", method = RequestMethod.GET)
	public String costParaNaturMainPage(Model mode) throws Exception {

		return "hrp/cost/costparanatur/costParaNaturMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/costParaNaturAddPage", method = RequestMethod.GET)
	public String costParaNaturAddPage(Model mode) throws Exception {

		return "hrp/cost/costparanatur/costParaNaturAdd";

	}

	/**
	 * @Description 
	 * 添加数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/addCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String costParaNaturJson = costParaNaturService.add(mapVo);

		return JSONObject.parseObject(costParaNaturJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/costParaNaturUpdatePage", method = RequestMethod.GET)
	public String costParaNaturUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		CostParaNatur costParaNatur = new CostParaNatur();
    
		costParaNatur = costParaNaturService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", costParaNatur.getGroup_id());
		mode.addAttribute("hos_id", costParaNatur.getHos_id());
		mode.addAttribute("para_code", costParaNatur.getPara_code());
		mode.addAttribute("para_name", costParaNatur.getPara_name());
		mode.addAttribute("para_value", costParaNatur.getPara_value());
		mode.addAttribute("bill_type", costParaNatur.getBill_type());
		mode.addAttribute("note", costParaNatur.getNote());
		
		return "hrp/cost/costparanatur/costParaNaturUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/updateCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String costParaNaturJson = costParaNaturService.update(mapVo);
		
		return JSONObject.parseObject(costParaNaturJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/addOrUpdateCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateCostParaNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costParaNaturJson ="";
		

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
	  
		costParaNaturJson = costParaNaturService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(costParaNaturJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/deleteCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaNatur(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				String str = assBaseService.isExistsDataByTable("cost_para_natur", ids[2]);
				if(Strings.isNotBlank(str)){
					retErrot = "{\"error\":\"删除失败，选择的分摊类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("para_code", ids[2]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String costParaNaturJson = costParaNaturService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(costParaNaturJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/queryCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaNatur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String costParaNatur = costParaNaturService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaNatur);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 成本_分摊类别
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/costParaNaturImportPage", method = RequestMethod.GET)
	public String costParaNaturImportPage(Model mode) throws Exception {

		return "hrp/cost/costparanatur/costParaNaturImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 成本_分摊类别
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/cost/costparanatur/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"cost\\基础设置","分摊类别.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 成本_分摊类别
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/cost/costparanatur/readCostParaNaturFiles",method = RequestMethod.POST)  
    public String readCostParaNaturFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<CostParaNatur> list_err = new ArrayList<CostParaNatur>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostParaNatur costParaNatur = new CostParaNatur();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					costParaNatur.setPara_code(temp[0]);
		    		mapVo.put("para_code", temp[0]);
					
					} else {
						
						err_sb.append("分摊类别编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					costParaNatur.setPara_name(temp[1]);
		    		mapVo.put("para_name", temp[1]);
					
					} else {
						
						err_sb.append("分摊类别名称为空  ");
						
					}
					
				CostParaType data_exc_extis = costParaNaturService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costParaNatur.setError_type(err_sb.toString());
					
					list_err.add(costParaNatur);
					
				} else {
			  
					String dataJson = costParaNaturService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			CostParaNatur data_exc = new CostParaNatur();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 成本_分摊类别
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/cost/costparanatur/addBatchCostParaNatur", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostParaNatur(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<CostParaNatur> list_err = new ArrayList<CostParaNatur>();
		
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
			
			CostParaNatur costParaNatur = new CostParaNatur();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("para_code"))) {
						
					costParaNatur.setPara_code((String)jsonObj.get("para_code"));
		    		mapVo.put("para_code", jsonObj.get("para_code"));
		    		} else {
						
						err_sb.append("分摊类别编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("para_name"))) {
						
					costParaNatur.setPara_name((String)jsonObj.get("para_name"));
		    		mapVo.put("para_name", jsonObj.get("para_name"));
		    		} else {
						
						err_sb.append("分摊类别名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					costParaNatur.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				CostParaType data_exc_extis = costParaNaturService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costParaNatur.setError_type(err_sb.toString());
					
					list_err.add(costParaNatur);
					
				} else {
			  
					String dataJson = costParaNaturService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			CostParaNatur data_exc = new CostParaNatur();
			
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

