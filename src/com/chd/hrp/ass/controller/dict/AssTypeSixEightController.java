
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
import com.chd.hrp.ass.entity.dict.AssGBcode;
import com.chd.hrp.ass.entity.dict.AssTypeSixEight;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssTypeSixEightService;

/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AssTypeSixEightController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssTypeSixEightController.class);
	
	//引入Service服务
	@Resource(name = "assTypeSixEightService")
	private final AssTypeSixEightService assTypeSixEightService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/assTypeSixEightMainPage", method = RequestMethod.GET)
	public String assTypeSixEightMainPage(Model mode) throws Exception {

		return "hrp/ass/asstypesixeight/assTypeSixEightMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/assTypeSixEightAddPage", method = RequestMethod.GET)
	public String assTypeSixEightAddPage(Model mode) throws Exception {

		return "hrp/ass/asstypesixeight/assTypeSixEightAdd";

	}

	/**
	 * @Description 
	 * 添加数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/addAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTypeSixEight(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assTypeSixEightJson = "" ;
		
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
	  
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
	 
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
	
		mapVo.put("type_level", 1);
		try{
			
		  AssTypeSixEight entity =	assTypeSixEightService.queryAssTypeSixEightByCodeOrName(mapVo);
		  if(entity != null){
			  return JSONObject.parseObject("{\"error\":\"编码或名称重复 \"}");
		  }
		  assTypeSixEightJson = assTypeSixEightService.addAssTypeSixEight(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		return JSONObject.parseObject(assTypeSixEightJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/assTypeSixEightUpdatePage", method = RequestMethod.GET)
	public String assTypeSixEightUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    AssTypeSixEight assTypeSixEight = new AssTypeSixEight();
    
		assTypeSixEight = assTypeSixEightService.queryAssTypeSixEightByCode(mapVo);
		
		mode.addAttribute("group_id", assTypeSixEight.getGroup_id());
		mode.addAttribute("hos_id", assTypeSixEight.getHos_id());
		mode.addAttribute("copy_code", assTypeSixEight.getCopy_code());
		mode.addAttribute("type_code", assTypeSixEight.getType_code());
		mode.addAttribute("type_name", assTypeSixEight.getType_name());
		mode.addAttribute("supper_code", assTypeSixEight.getSupper_code());
		mode.addAttribute("is_last", assTypeSixEight.getIs_last());
		mode.addAttribute("type_level", assTypeSixEight.getType_level());
		mode.addAttribute("spell_code", assTypeSixEight.getSpell_code());
		mode.addAttribute("wbx_code", assTypeSixEight.getWbx_code());
		mode.addAttribute("is_stop", assTypeSixEight.getIs_stop());
		
		return "hrp/ass/asstypesixeight/assTypeSixEightUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/updateAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssTypeSixEight(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assTypeSixEightJson = "" ;
		
		if(mapVo.get("group_id") == null){
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
    mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		mapVo.put("type_level", 1);
		 
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		 
		mapVo.put("spell_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
		
		try{
		 
			assTypeSixEightJson = assTypeSixEightService.updateAssTypeSixEight(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assTypeSixEightJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/deleteAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTypeSixEight(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String AssGBcodeJson = "";
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			String str = assBaseService.isExistsDataByTable("ASS_GB_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("type_code", ids[3]);
//			mapVo.put("supper_code", ids[4]);
			//存在下级分类时，不允许修改 
			List<AssTypeSixEight> child = assTypeSixEightService.queryAssTypeSixEightChild(mapVo);
			if (child !=null && child.size()>0) {
				retErrot = "{\"error\":\"存在下级分类，请先删除下级\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			try{
			AssGBcodeJson = assTypeSixEightService.deleteAssTypeSixEight(mapVo);
			// listVo.add(mapVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		}
	 
		return JSONObject.parseObject(AssGBcodeJson);			
	}
	
	/**
	 * @Description 
	 * 查询数据 050104 资产用途
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/queryAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeSixEight(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assTypeSixEight = assTypeSixEightService.queryAssTypeSixEight(getPage(mapVo));

		return JSONObject.parseObject(assTypeSixEight);
		
	}
	/**
	 * 导入数据 050104 资产用途
	 * @param mapVo
	 * @param mode
	 * @return String 
	 * @throws Exception
	 */
	@RequestMapping(value="/hrp/ass/asstypesixeight/assTypeSixEightImport",method=RequestMethod.POST)
	@ResponseBody
	public String assTypeSixEightImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assTypeSixEightService.assTypeSixEightImport(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050104 资产用途
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeightasstypesixeight/assTypeSixEightImportPage", method = RequestMethod.GET)
	public String assTypeSixEightImportPage(Model mode) throws Exception {
		return "hrp/ass/asstypesixeight/assTypeSixEightImport";
	}
	/**
	 * @Description 
	 * 下载导入模版 050104 资产用途
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/ass/asstypesixeight/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"base\\目录","资产68分类.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050104 资产用途
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/       
	 @RequestMapping(value="/hrp/ass/asstypesixeight/readCostBonusCostRelaFiles",method = RequestMethod.POST)  
    public String readCostBonusCostRelaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssTypeSixEight> list_err = new ArrayList<AssTypeSixEight>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssTypeSixEight assTypeSixEight = new AssTypeSixEight();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		mapVo.put("hos_id", SessionManager.getHosId());  
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		  
					if (StringTool.isNotBlank(temp[0])) {
						
					assTypeSixEight.setType_code(temp[0]);
								
		    		mapVo.put("type_code", temp[0]);
		    		
					} else {
						
						err_sb.append("用途编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					assTypeSixEight.setType_name(temp[1]);
								
		    		mapVo.put("type_name", temp[1]);
		    		
					} else {
						
						err_sb.append("用途名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
					assTypeSixEight.setSpell_code(temp[2]);
								
		    		mapVo.put("is_stop", temp[2]);
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
				AssTypeSixEight data_exc_extis = assTypeSixEightService.queryAssTypeSixEightByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assTypeSixEight.setError_type(err_sb.toString());
					
					list_err.add(assTypeSixEight);
					
				} else {
				
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
					
					try{
					
						String dataJson = assTypeSixEightService.addAssTypeSixEight(mapVo);
					
					}catch(Exception e){
						
						return  "{\"error\":\""+e.getMessage()+" \"}" ;
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssTypeSixEight data_exc = new AssTypeSixEight();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050104 资产用途
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/asstypesixeight/addBatchAssTypeSixEight", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssTypeSixEight(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssTypeSixEight> list_err = new ArrayList<AssTypeSixEight>();
		
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
			
			AssTypeSixEight assTypeSixEight = new AssTypeSixEight();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("group_id"))) {
						
					assTypeSixEight.setGroup_id(Long.valueOf((String)jsonObj.get("group_id")));
											
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					} else {
						
						err_sb.append("集体ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {
						
					assTypeSixEight.setHos_id(Long.valueOf((String)jsonObj.get("hos_id")));
											
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					} else {
						
						err_sb.append("医院ID为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {
						
					assTypeSixEight.setCopy_code((String)jsonObj.get("copy_code"));
											
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					} else {
						
						err_sb.append("账套编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					assTypeSixEight.setType_code((String)jsonObj.get("type_code"));
											
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		
					} else {
						
						err_sb.append("用途编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					assTypeSixEight.setType_name((String)jsonObj.get("type_name"));
											
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		
					} else {
						
						err_sb.append("用途名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					assTypeSixEight.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
											
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
				AssTypeSixEight data_exc_extis = assTypeSixEightService.queryAssTypeSixEightByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assTypeSixEight.setError_type(err_sb.toString());
					
					list_err.add(assTypeSixEight);
					
				} else {
				 
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				 
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
					
					try{
					
						String dataJson = assTypeSixEightService.addAssTypeSixEight(mapVo);
					
					}catch(Exception e){
					
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssTypeSixEight data_exc = new AssTypeSixEight();
			
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

