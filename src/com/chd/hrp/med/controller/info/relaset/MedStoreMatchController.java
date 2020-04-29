/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.relaset;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.med.entity.MedStoreMatch;
import com.chd.hrp.med.service.info.relaset.MedStoreMatchService;
import com.chd.hrp.med.serviceImpl.info.relaset.MedStoreMatchServiceImpl;
/**
 * 
 * @Description:
 * 08113 药品仓库配套表
 * @Table:
 * MED_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStoreMatchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreMatchController.class);
	
	//引入Service服务
	@Resource(name = "medStoreMatchService")
	private final MedStoreMatchServiceImpl medStoreMatchService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medStoreMatchMainPage", method = RequestMethod.GET)
	public String medStoreMatchMainPage(Model mode) throws Exception {

		return "hrp/med/info/relaset/storematch/medStoreMatchMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medStoreMatchAddPage", method = RequestMethod.GET)
	public String medStoreMatchAddPage(Model mode) throws Exception {

		return "hrp/med/info/relaset/storematch/medStoreMatchAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/addMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if("系统生成".equals(mapVo.get("store_match_code"))){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String date = df.format(new Date());
			String year = date.split("-")[0];
			String month = date.split("-")[1];
			mapVo.put("table_code", "MED_STORE_MATCH");
			mapVo.put("prefixe", "");
			mapVo.put("year", year);
			mapVo.put("month", month);
			String dept_match_code= medStoreMatchService.getMedNextNo(mapVo);
			mapVo.put("store_match_code", dept_match_code);
		}
       
		String medStoreMatchJson = medStoreMatchService.add(mapVo);

		return JSONObject.parseObject(medStoreMatchJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medStoreMatchUpdatePage", method = RequestMethod.GET)
	public String medStoreMatchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("para") != null){
			
			String[] ids = mapVo.get("para").toString().split(",");
			
			mapVo.put("store_match_id", ids[0]);
			
			mapVo.put("store_id", ids[1]);
		}
		
		MedStoreMatch medStoreMatch = medStoreMatchService.queryByCode(mapVo);
		
		mode.addAttribute("store_match_id", medStoreMatch.getStore_match_id());
		
		mode.addAttribute("store_match_code", medStoreMatch.getStore_match_code());
		
		mode.addAttribute("store_match_name", medStoreMatch.getStore_match_name());
		
		mode.addAttribute("store_code", medStoreMatch.getStore_code());
		
		mode.addAttribute("store_no", medStoreMatch.getStore_no());
		
		mode.addAttribute("store_id",  medStoreMatch.getStore_id());
		
		mode.addAttribute("store_name",medStoreMatch.getStore_name());
		
		
		
		return "hrp/med/info/relaset/storematch/medStoreMatchUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/updateMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		String medStoreMatchJson = medStoreMatchService.updateMsmDetail(mapVo);
		
		return JSONObject.parseObject(medStoreMatchJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/addOrUpdateMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medStoreMatchJson ="";
		
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
	  
		medStoreMatchJson = medStoreMatchService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(medStoreMatchJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/deleteMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		if(mapVo.get("paramVo").toString() != null && !"".equals(mapVo.get("paramVo").toString())){
			
			String paramVo = mapVo.get("paramVo").toString();
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])   ;
				map.put("store_match_id", ids[3]);
				
				listVo.add(map);
				
			}
		}
	    
		String medStoreMatchJson = medStoreMatchService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(medStoreMatchJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08113 药品仓库配套表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/queryMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreMatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}
		String medStoreMatch = medStoreMatchService.query(mapVo);

		return JSONObject.parseObject(medStoreMatch);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08113 药品仓库配套表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/medStoreMatchImportPage", method = RequestMethod.GET)
	public String medStoreMatchImportPage(Model mode) throws Exception {

		return "hrp/med/info/relaset/storematch/medStoreMatchImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08113 药品仓库配套表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/relaset/storematch/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08113 药品仓库配套表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08113 药品仓库配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/relaset/readMedStoreMatchFiles",method = RequestMethod.POST)  
    public String readMedStoreMatchFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedStoreMatch> list_err = new ArrayList<MedStoreMatch>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedStoreMatch medStoreMatch = new MedStoreMatch();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medStoreMatch.setStore_match_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_match_id", temp[3]);
					
					} else {
						
						err_sb.append("配套表ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("store_match_name", temp[4]);
					
					} else {
						
						err_sb.append("配套表名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medStoreMatch.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					
				MedStoreMatch data_exc_extis = medStoreMatchService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreMatch.setError_type(err_sb.toString());
					
					list_err.add(medStoreMatch);
					
				} else {
			  
					String dataJson = medStoreMatchService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreMatch data_exc = new MedStoreMatch();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08113 药品仓库配套表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/addBatchMedStoreMatch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedStoreMatch(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedStoreMatch> list_err = new ArrayList<MedStoreMatch>();
		
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
				
				MedStoreMatch medStoreMatch = new MedStoreMatch();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				if (StringTool.isNotBlank(jsonObj.get("store_match_id"))) {
							
					medStoreMatch.setStore_match_id(Long.valueOf((String)jsonObj.get("store_match_id")));
				    		
					mapVo.put("store_match_id", jsonObj.get("store_match_id"));
			    } else {
							
					err_sb.append("配套表ID为空  ");
							
				}
						
				if (StringTool.isNotBlank(jsonObj.get("store_match_name"))) {
							
			    	mapVo.put("store_match_name", jsonObj.get("store_match_name"));
			    } else {
							
					err_sb.append("配套表名称为空  ");
							
				}
						
				if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
							
					medStoreMatch.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
			    	
					mapVo.put("store_id", jsonObj.get("store_id"));
			    } else {
							
					err_sb.append("仓库ID为空  ");
							
				}
						
				MedStoreMatch data_exc_extis = medStoreMatchService.queryByCode(mapVo);
					
				if (data_exc_extis != null) {
						
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
						
					medStoreMatch.setError_type(err_sb.toString());
						
					list_err.add(medStoreMatch);
						
				} else {
				  
					String dataJson = medStoreMatchService.add(mapVo);
						
				}
					
			}
			
		} catch (DataAccessException e) {
			
			MedStoreMatch data_exc = new MedStoreMatch();
			
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
	 * @Description 
	 * 药品仓库配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	@RequestMapping(value = "/hrp/med/info/relaset/queryMedSmmDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedSmmDetailByCode(@RequestParam Map<String,Object> mapVo){
		

	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String medStoreMatchJson = medStoreMatchService.querySmmDetailByCode(getPage(mapVo));
		
		return JSONObject.parseObject(medStoreMatchJson);
	}
}

