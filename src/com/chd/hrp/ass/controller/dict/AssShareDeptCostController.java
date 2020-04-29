/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.ass.entity.dict.AssShareDeptCost;
import com.chd.hrp.ass.service.dict.AssShareDeptCostService;
/**
 * 
 * @Description:
 * 050807 分摊科室设置
 * @Table:
 * ASS_SHARE_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class AssShareDeptCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssShareDeptCostController.class);
	
	//引入Service服务
	@Resource(name = "assShareDeptCostService")
	private final AssShareDeptCostService assShareDeptCostService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/assShareDeptCostMainPage", method = RequestMethod.GET)
	public String assShareDeptCostMainPage(Model mode) throws Exception {

		return "hrp/ass/asssharedeptcost/assShareDeptCostMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/assShareDeptCostAddPage", method = RequestMethod.GET)
	public String assShareDeptCostAddPage(Model mode) throws Exception {

		return "hrp/ass/asssharedeptcost/assShareDeptCostAdd";

	}

	/**
	 * @Description 
	 * 添加数据 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/addAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssShareDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assShareDeptCostJson = "" ;
		
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
		
       try{
		 
    	   assShareDeptCostJson = assShareDeptCostService.addAssShareDeptCost(mapVo);
		
       }catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		
       }
		
       return JSONObject.parseObject(assShareDeptCostJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/assShareDeptCostUpdatePage", method = RequestMethod.GET)
	public String assShareDeptCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssShareDeptCost assShareDeptCost = new AssShareDeptCost();
    
		assShareDeptCost = assShareDeptCostService.queryAssShareDeptCostByCode(mapVo);
		
		mode.addAttribute("group_id", assShareDeptCost.getGroup_id());
		mode.addAttribute("hos_id", assShareDeptCost.getHos_id());
		mode.addAttribute("copy_code", assShareDeptCost.getCopy_code());
		mode.addAttribute("ass_nature", assShareDeptCost.getAss_nature());
		mode.addAttribute("ass_card_no", assShareDeptCost.getAss_card_no());
		mode.addAttribute("dept_id", assShareDeptCost.getDept_id());
		mode.addAttribute("dept_no", assShareDeptCost.getDept_no());
		mode.addAttribute("percent", assShareDeptCost.getPercent());
		
		return "hrp/ass/asssharedeptcost/assShareDeptCostUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/updateAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssShareDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assShareDeptCostJson = "" ;

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		try{
		 
			assShareDeptCostJson = assShareDeptCostService.updateAssShareDeptCost(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assShareDeptCostJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/addOrUpdateAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssShareDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assShareDeptCostJson ="";
		
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
		detailVo.put("ass_nature", mapVo.get("ass_nature"));
		detailVo.put("ass_card_no", mapVo.get("ass_card_no"));
		String dept_id = detailVo.get("dept_id") == null ? "" : detailVo.get("dept_id").toString();
		detailVo.put("dept_id", Integer.parseInt(dept_id.split("@")[0]));
		detailVo.put("dept_no", Integer.parseInt(dept_id.split("@")[1]));
		try{
		assShareDeptCostJson = assShareDeptCostService.addOrUpdateAssShareDeptCost(detailVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		}
		return JSONObject.parseObject(assShareDeptCostJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/deleteAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssShareDeptCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String assShareDeptCostJson = "" ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_nature", ids[3])   ;
				mapVo.put("ass_card_no", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				mapVo.put("dept_no", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    try{
		 
	    	assShareDeptCostJson = assShareDeptCostService.deleteBatchAssShareDeptCost(listVo);
		
	    }catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	 
	    return JSONObject.parseObject(assShareDeptCostJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050807 分摊科室设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/queryAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssShareDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assShareDeptCost = assShareDeptCostService.queryAssShareDeptCost(getPage(mapVo));

		return JSONObject.parseObject(assShareDeptCost);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050807 分摊科室设置
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/assShareDeptCostImportPage", method = RequestMethod.GET)
	public String assShareDeptCostImportPage(Model mode) throws Exception {

		return "hrp/ass/asssharedeptcost/assShareDeptCostImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050807 分摊科室设置
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/asssharedeptcost/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"ass\\downTemplate","050807 分摊科室设置.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050807 分摊科室设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/asssharedeptcost/readAssShareDeptCostFiles",method = RequestMethod.POST)  
    public String readAssShareDeptCostFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssShareDeptCost> list_err = new ArrayList<AssShareDeptCost>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssShareDeptCost assShareDeptCost = new AssShareDeptCost();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assShareDeptCost.setAss_nature(temp[3]);
		    		mapVo.put("ass_nature", temp[3]);
					
					} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assShareDeptCost.setAss_card_no(temp[4]);
		    		mapVo.put("ass_card_no", temp[4]);
					
					} else {
						
						err_sb.append("卡片编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assShareDeptCost.setDept_id(Long.valueOf(temp[5]));
		    		mapVo.put("dept_id", temp[5]);
					
					} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assShareDeptCost.setDept_no(Long.valueOf(temp[6]));
		    		mapVo.put("dept_no", temp[6]);
					
					} else {
						
						err_sb.append("科室变更NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assShareDeptCost.setPercent(Double.valueOf(temp[7]));
		    		mapVo.put("percent", temp[7]);
					
					} else {
						
						err_sb.append("分摊比例为空  ");
						
					}
					 
					
				AssShareDeptCost data_exc_extis = assShareDeptCostService.queryAssShareDeptCostByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assShareDeptCost.setError_type(err_sb.toString());
					
					list_err.add(assShareDeptCost);
					
				} else {
			  
					try{
					
						String dataJson = assShareDeptCostService.addAssShareDeptCost(mapVo);
					
					}catch(Exception e){
						
						return  "{\"error\":\""+e.getMessage()+" \"}" ;
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssShareDeptCost data_exc = new AssShareDeptCost();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050807 分摊科室设置
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/asssharedeptcost/addBatchAssShareDeptCost", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssShareDeptCost(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssShareDeptCost> list_err = new ArrayList<AssShareDeptCost>();
		
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
			
			AssShareDeptCost assShareDeptCost = new AssShareDeptCost();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_nature"))) {
						
					assShareDeptCost.setAss_nature((String)jsonObj.get("ass_nature"));
		    		mapVo.put("ass_nature", jsonObj.get("ass_nature"));
		    		} else {
						
						err_sb.append("资产性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {
						
					assShareDeptCost.setAss_card_no((String)jsonObj.get("ass_card_no"));
		    		mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
		    		} else {
						
						err_sb.append("卡片编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					assShareDeptCost.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					assShareDeptCost.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("科室变更NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("percent"))) {
						
					assShareDeptCost.setPercent(Double.valueOf((String)jsonObj.get("percent")));
		    		mapVo.put("percent", jsonObj.get("percent"));
		    		} else {
						
						err_sb.append("分摊比例为空  ");
						
					}
					
					
				AssShareDeptCost data_exc_extis = assShareDeptCostService.queryAssShareDeptCostByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assShareDeptCost.setError_type(err_sb.toString());
					
					list_err.add(assShareDeptCost);
					
				} else {
			  
					try{
					
						String dataJson = assShareDeptCostService.addAssShareDeptCost(mapVo);
					
					}catch(Exception e){
					
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssShareDeptCost data_exc = new AssShareDeptCost();
			
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

