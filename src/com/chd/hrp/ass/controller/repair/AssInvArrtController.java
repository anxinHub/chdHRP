/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
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
import com.chd.hrp.ass.entity.repair.AssInvArrt;
import com.chd.hrp.ass.service.repair.AssInvArrtService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_INV_ARRT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssInvArrtController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssInvArrtController.class);
	
	//引入Service服务
	@Resource(name = "assInvArrtService")
	private final AssInvArrtService assInvArrtService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/assInvArrtMainPage", method = RequestMethod.GET)
	public String assInvArrtMainPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assinvarrt/assInvArrtMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/assInvArrtAddPage", method = RequestMethod.GET)
	public String assInvArrtAddPage(Model mode) throws Exception {

		return "hrp/ass/assrepairapply/assinvarrt/assInvArrtAdd";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/addAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInvArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("inv_name").toString()));
       
		String assInvArrtJson = assInvArrtService.add(mapVo);

		return JSONObject.parseObject(assInvArrtJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/assInvArrtUpdatePage", method = RequestMethod.GET)
	public String assInvArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssInvArrt assInvArrt = new AssInvArrt();
    
		assInvArrt = assInvArrtService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assInvArrt.getGroup_id());
		mode.addAttribute("hos_id", assInvArrt.getHos_id());
		mode.addAttribute("copy_code", assInvArrt.getCopy_code());
		mode.addAttribute("inv_code", assInvArrt.getInv_code());
		mode.addAttribute("inv_name", assInvArrt.getInv_name());
		mode.addAttribute("spell_code", assInvArrt.getSpell_code());
		mode.addAttribute("wbx_code", assInvArrt.getWbx_code());
		mode.addAttribute("is_stop", assInvArrt.getIs_stop());
		
		return "hrp/ass/assrepairapply/assinvarrt/assInvArrtUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/updateAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInvArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("inv_name").toString()));
	  
		String assInvArrtJson = assInvArrtService.update(mapVo);
		
		return JSONObject.parseObject(assInvArrtJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/addOrUpdateAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssInvArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assInvArrtJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		
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
	  
		assInvArrtJson = assInvArrtService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assInvArrtJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/deleteAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInvArrt(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("inv_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assInvArrtJson = assInvArrtService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assInvArrtJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/queryAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInvArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assInvArrt = assInvArrtService.query(getPage(mapVo));

		return JSONObject.parseObject(assInvArrt);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 tabledesc
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/assInvArrtImportPage", method = RequestMethod.GET)
	public String assInvArrtImportPage(Model mode) throws Exception {

		return "hrp/ass/repair/assinvarrt/assInvArrtImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 tabledesc
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/repair/assinvarrt/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","tabledesc.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/repair/assinvarrt/readAssInvArrtFiles",method = RequestMethod.POST)  
    public String readAssInvArrtFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssInvArrt> list_err = new ArrayList<AssInvArrt>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssInvArrt assInvArrt = new AssInvArrt();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assInvArrt.setInv_code(temp[3]);
		    		mapVo.put("inv_code", temp[3]);
					
					} else {
						
						err_sb.append("材料编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assInvArrt.setInv_name(temp[4]);
		    		mapVo.put("inv_name", temp[4]);
					
					} else {
						
						err_sb.append("材料名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assInvArrt.setSpell_code(temp[5]);
		    		mapVo.put("spell_code", temp[5]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assInvArrt.setWbx_code(temp[6]);
		    		mapVo.put("wbx_code", temp[6]);
					
					} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assInvArrt.setIs_stop(Integer.valueOf(temp[7]));
		    		mapVo.put("is_stop", temp[7]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 
					
				AssInvArrt data_exc_extis = assInvArrtService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assInvArrt.setError_type(err_sb.toString());
					
					list_err.add(assInvArrt);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = assInvArrtService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssInvArrt data_exc = new AssInvArrt();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 tabledesc
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/repair/assinvarrt/addBatchAssInvArrt", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssInvArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssInvArrt> list_err = new ArrayList<AssInvArrt>();
		
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
			
			AssInvArrt assInvArrt = new AssInvArrt();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("inv_code"))) {
						
					assInvArrt.setInv_code((String)jsonObj.get("inv_code"));
		    		mapVo.put("inv_code", jsonObj.get("inv_code"));
		    		} else {
						
						err_sb.append("材料编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {
						
					assInvArrt.setInv_name((String)jsonObj.get("inv_name"));
		    		mapVo.put("inv_name", jsonObj.get("inv_name"));
		    		} else {
						
						err_sb.append("材料名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {
						
					assInvArrt.setSpell_code((String)jsonObj.get("spell_code"));
		    		mapVo.put("spell_code", jsonObj.get("spell_code"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {
						
					assInvArrt.setWbx_code((String)jsonObj.get("wbx_code"));
		    		mapVo.put("wbx_code", jsonObj.get("wbx_code"));
		    		} else {
						
						err_sb.append("五笔码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					assInvArrt.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					
				AssInvArrt data_exc_extis = assInvArrtService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assInvArrt.setError_type(err_sb.toString());
					
					list_err.add(assInvArrt);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
			  
					String dataJson = assInvArrtService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssInvArrt data_exc = new AssInvArrt();
			
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

