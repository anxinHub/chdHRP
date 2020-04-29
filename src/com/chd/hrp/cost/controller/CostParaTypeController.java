/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostParaType;
import com.chd.hrp.cost.service.CostParaTypeService;
/**
 * 
 * @Description:
 * 成本_分摊类别
 * @Table:
 * COST_PARA_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class CostParaTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(CostParaTypeController.class);
	
	//引入Service服务
	@Resource(name = "costParaTypeService")
	private final CostParaTypeService costParaTypeService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/costParaTypeMainPage", method = RequestMethod.GET)
	public String costParaTypeMainPage(Model mode) throws Exception {

		return "hrp/cost/costparatype/costParaTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/costParaTypeAddPage", method = RequestMethod.GET)
	public String costParaTypeAddPage(Model mode) throws Exception {

		return "hrp/cost/costparatype/costParaTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/addCostParaType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String costParaTypeJson = costParaTypeService.add(mapVo);

		return JSONObject.parseObject(costParaTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/costParaTypeUpdatePage", method = RequestMethod.GET)
	public String costParaTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		CostParaType costParaType = new CostParaType();
    
		costParaType = costParaTypeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", costParaType.getGroup_id());
		mode.addAttribute("hos_id", costParaType.getHos_id());
		mode.addAttribute("copy_code", costParaType.getCopy_code());
		mode.addAttribute("type_code", costParaType.getType_code());
		mode.addAttribute("type_name", costParaType.getType_name());
		mode.addAttribute("note", costParaType.getNote());
		
		return "hrp/cost/costparatype/costParaTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/updateCostParaType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String costParaTypeJson = costParaTypeService.update(mapVo);
		
		return JSONObject.parseObject(costParaTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/addOrUpdateCostParaType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateCostParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costParaTypeJson ="";
		

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
	  
		costParaTypeJson = costParaTypeService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(costParaTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/deleteCostParaType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				String str = assBaseService.isExistsDataByTable("cost_para_type", ids[3]);
				if(Strings.isNotBlank(str)){
					retErrot = "{\"error\":\"删除失败，选择的分摊类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String costParaTypeJson = costParaTypeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(costParaTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 成本_分摊类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/queryCostParaType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String costParaType = costParaTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(costParaType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 成本_分摊类别
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/cost/costparatype/costParaTypeImportPage", method = RequestMethod.GET)
	public String costParaTypeImportPage(Model mode) throws Exception {

		return "hrp/cost/costparatype/costParaTypeImport";

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
	 @RequestMapping(value="hrp/cost/costparatype/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/cost/costparatype/readCostParaTypeFiles",method = RequestMethod.POST)  
    public String readCostParaTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<CostParaType> list_err = new ArrayList<CostParaType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostParaType costParaType = new CostParaType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					costParaType.setType_code(temp[0]);
		    		mapVo.put("type_code", temp[0]);
					
					} else {
						
						err_sb.append("分摊类别编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					costParaType.setType_name(temp[1]);
		    		mapVo.put("type_name", temp[1]);
					
					} else {
						
						err_sb.append("分摊类别名称为空  ");
						
					}
					
				CostParaType data_exc_extis = costParaTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costParaType.setError_type(err_sb.toString());
					
					list_err.add(costParaType);
					
				} else {
			  
					String dataJson = costParaTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			CostParaType data_exc = new CostParaType();
			
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
	@RequestMapping(value = "/hrp/cost/costparatype/addBatchCostParaType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostParaType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<CostParaType> list_err = new ArrayList<CostParaType>();
		
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
			
			CostParaType costParaType = new CostParaType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					costParaType.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("分摊类别编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					costParaType.setType_name((String)jsonObj.get("type_name"));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("分摊类别名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					costParaType.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				CostParaType data_exc_extis = costParaTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costParaType.setError_type(err_sb.toString());
					
					list_err.add(costParaType);
					
				} else {
			  
					String dataJson = costParaTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			CostParaType data_exc = new CostParaType();
			
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

