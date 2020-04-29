/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.base;
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
import com.chd.hrp.med.entity.MedNoManage;
import com.chd.hrp.med.serviceImpl.base.MedNoManageServiceImpl;
/**
 * 
 * @Description:
 * 08199 单据号管理表
 * @Table:
 * MED_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedNoManageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedNoManageController.class);
	
	//引入Service服务
	@Resource(name = "medNoManageService")
	private final MedNoManageServiceImpl medNoManageService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/medNoManageMainPage", method = RequestMethod.GET)
	public String medNoManageMainPage(Model mode) throws Exception {

		return "hrp/med/mednomanage/medNoManageMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/medNoManageAddPage", method = RequestMethod.GET)
	public String medNoManageAddPage(Model mode) throws Exception {

		return "hrp/med/mednomanage/medNoManageAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/addMedNoManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedNoManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String medNoManageJson = medNoManageService.add(mapVo);

		return JSONObject.parseObject(medNoManageJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/medNoManageUpdatePage", method = RequestMethod.GET)
	public String medNoManageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedNoManage medNoManage = new MedNoManage();
    
		medNoManage = medNoManageService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", medNoManage.getGroup_id());
		mode.addAttribute("hos_id", medNoManage.getHos_id());
		mode.addAttribute("copy_code", medNoManage.getCopy_code());
		mode.addAttribute("table_code", medNoManage.getTable_code());
		mode.addAttribute("prefixe", medNoManage.getPrefixe());
		mode.addAttribute("year_month", medNoManage.getYear_month());
		mode.addAttribute("max_no", medNoManage.getMax_no());
		
		return "hrp/med/mednomanage/medNoManageUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/updateMedNoManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedNoManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medNoManageJson = medNoManageService.update(mapVo);
		
		return JSONObject.parseObject(medNoManageJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/addOrUpdateMedNoManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedNoManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medNoManageJson ="";
		
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
	  
		medNoManageJson = medNoManageService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(medNoManageJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/deleteMedNoManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedNoManage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("table_code", ids[3])   ;
				mapVo.put("year_month", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medNoManageJson = medNoManageService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(medNoManageJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08199 单据号管理表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/queryMedNoManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedNoManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medNoManage = medNoManageService.query(getPage(mapVo));

		return JSONObject.parseObject(medNoManage);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08199 单据号管理表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/medNoManageImportPage", method = RequestMethod.GET)
	public String medNoManageImportPage(Model mode) throws Exception {

		return "hrp/med/mednomanage/medNoManageImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08199 单据号管理表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/mednomanage/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08199 单据号管理表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08199 单据号管理表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/mednomanage/readMedNoManageFiles",method = RequestMethod.POST)  
    public String readMedNoManageFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedNoManage> list_err = new ArrayList<MedNoManage>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedNoManage medNoManage = new MedNoManage();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medNoManage.setTable_code(temp[3]);
		    		mapVo.put("table_code", temp[3]);
					
					} else {
						
						err_sb.append("单据表编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medNoManage.setPrefixe(temp[4]);
		    		mapVo.put("prefixe", temp[4]);
					
					} else {
						
						err_sb.append("单据前缀为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medNoManage.setYear_month(temp[5]);
		    		mapVo.put("year_month", temp[5]);
					
					} else {
						
						err_sb.append("年月为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medNoManage.setMax_no(Long.valueOf(temp[6]));
		    		mapVo.put("max_no", temp[6]);
					
					} else {
						
						err_sb.append("最大流水号为空  ");
						
					}
					 
					
					 
					
				MedNoManage data_exc_extis = medNoManageService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medNoManage.setError_type(err_sb.toString());
					
					list_err.add(medNoManage);
					
				} else {
			  
					String dataJson = medNoManageService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedNoManage data_exc = new MedNoManage();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08199 单据号管理表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/mednomanage/addBatchMedNoManage", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedNoManage(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedNoManage> list_err = new ArrayList<MedNoManage>();
		
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
			
			MedNoManage medNoManage = new MedNoManage();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("table_code"))) {
						
					medNoManage.setTable_code((String)jsonObj.get("table_code"));
		    		mapVo.put("table_code", jsonObj.get("table_code"));
		    		} else {
						
						err_sb.append("单据表编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("prefixe"))) {
						
					medNoManage.setPrefixe((String)jsonObj.get("prefixe"));
		    		mapVo.put("prefixe", jsonObj.get("prefixe"));
		    		} else {
						
						err_sb.append("单据前缀为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("year_month"))) {
						
					medNoManage.setYear_month((String)jsonObj.get("year_month"));
		    		mapVo.put("year_month", jsonObj.get("year_month"));
		    		} else {
						
						err_sb.append("年月为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("max_no"))) {
						
					medNoManage.setMax_no(Long.valueOf((String)jsonObj.get("max_no")));
		    		mapVo.put("max_no", jsonObj.get("max_no"));
		    		} else {
						
						err_sb.append("最大流水号为空  ");
						
					}
					
					
					
				MedNoManage data_exc_extis = medNoManageService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medNoManage.setError_type(err_sb.toString());
					
					list_err.add(medNoManage);
					
				} else {
			  
					String dataJson = medNoManageService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedNoManage data_exc = new MedNoManage();
			
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

