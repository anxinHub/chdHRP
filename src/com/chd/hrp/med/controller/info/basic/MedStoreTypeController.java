/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedStoreType;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreTypeServiceImpl;
/**
 * 
 * @Description:
 * 08111 仓库类别信息
 * @Table:
 * MED_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStoreTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreTypeController.class);
	
	//引入Service服务
	@Resource(name = "medStoreTypeService")
	private final MedStoreTypeServiceImpl medStoreTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/medStoreTypeMainPage", method = RequestMethod.GET)
	public String medStoreTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeinv/medstoretype/medStoreTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/medStoreTypeAddPage", method = RequestMethod.GET)
	public String medStoreTypeAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		return "hrp/med/info/basic/storeinv/medstoretype/medStoreTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/addMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> deleteList= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			Map<String, Object> deleteMap=new HashMap<String, Object>();
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("med_type_id", ids[3]);
			mapVo.put("store_id", ids[4])   ;
			listVo.add(mapVo);
			
			deleteMap.put("group_id", ids[0])   ;
			deleteMap.put("hos_id", ids[1])   ;
			deleteMap.put("copy_code", ids[2])   ;
			deleteMap.put("store_id", ids[4])   ;
			deleteList.add(deleteMap);
		}
		String medStoreTypeJson =null;
		
		if(deleteList.size()>0){
        	medStoreTypeJson = medStoreTypeService.deleteBatchMedStoreType(deleteList);
        }
		
        if(listVo.size()>0){
        	medStoreTypeJson = medStoreTypeService.addBatchMedStoreType(listVo);
        }
		

		return JSONObject.parseObject(medStoreTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/medStoreTypeUpdatePage", method = RequestMethod.GET)
	public String medStoreTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedStoreType medStoreType = new MedStoreType();
    
		medStoreType = medStoreTypeService.queryMedStoreTypeByCode(mapVo);
		
		mode.addAttribute("group_id", medStoreType.getGroup_id());
		mode.addAttribute("hos_id", medStoreType.getHos_id());
		mode.addAttribute("copy_code", medStoreType.getCopy_code());
		mode.addAttribute("store_id", medStoreType.getStore_id());
		mode.addAttribute("med_type_id", medStoreType.getMed_type_id());
		
		return "hrp/med/info/basic/storeinv/medstoretype/medStoreTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/updateMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medStoreTypeJson = medStoreTypeService.updateMedStoreType(mapVo);
		
		return JSONObject.parseObject(medStoreTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/addOrUpdateMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medStoreTypeJson ="";
		
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
	  
		medStoreTypeJson = medStoreTypeService.addOrUpdateMedStoreType(detailVo);
		
		}
		return JSONObject.parseObject(medStoreTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/deleteMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("store_id", ids[3])   ;
				mapVo.put("med_type_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medStoreTypeJson = medStoreTypeService.deleteBatchMedStoreType(listVo);
			
	  return JSONObject.parseObject(medStoreTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 根据仓库ID 删除其对应的所有对应关系数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/saveMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		String[] id=paramVo.split(",");
		String[] ids = id[0].split("@");
		
		//表的主键
		mapVo.put("group_id",SessionManager.getGroupId() );
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("store_id", ids[0]);
				
	      
	    Long count = medStoreTypeService.queryExist(mapVo);
	    String medStoreTypeJson = null;
	    if(count > 0){
	    	 medStoreTypeJson = medStoreTypeService.deleteMedStoreType(mapVo);
	    }else{
	    	medStoreTypeJson = "{\"error\":\"无操作数据.\",\"state\":\"true\"}";
	    }
		
	  return JSONObject.parseObject(medStoreTypeJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 08111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/queryMedStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medStoreType = medStoreTypeService.queryMedStoreType(getPage(mapVo));

		return JSONObject.parseObject(medStoreType);
		
	}
	/**
	 * 查询出药品类别字典表 MED_TYPE中IS_STOP=0的所有药品类别记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/queryStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medStoreType = medStoreTypeService.queryStoreType(mapVo);

		return JSONObject.parseObject(medStoreType);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 08111 仓库类别信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/medStoreTypeImportPage", method = RequestMethod.GET)
	public String medStoreTypeImportPage(Model mode) throws Exception {

		return "hrp/med/medstoretype/medStoreTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08111 仓库类别信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medstoretype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08111 仓库类别信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08111 仓库类别信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/storeinv/medstoretype/readMedStoreTypeFiles",method = RequestMethod.POST)  
    public String readMedStoreTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedStoreType> list_err = new ArrayList<MedStoreType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedStoreType medStoreType = new MedStoreType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medStoreType.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medStoreType.setMed_type_id(Long.valueOf(temp[4]));
		    		mapVo.put("med_type_id", temp[4]);
					
					} else {
						
						err_sb.append("药品类别ID为空  ");
						
					}
					 
					
				MedStoreType data_exc_extis = medStoreTypeService.queryMedStoreTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreType.setError_type(err_sb.toString());
					
					list_err.add(medStoreType);
					
				} else {
			  
					String dataJson = medStoreTypeService.addMedStoreType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreType data_exc = new MedStoreType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08111 仓库类别信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/addBatchMedStoreType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedStoreType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedStoreType> list_err = new ArrayList<MedStoreType>();
		
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
			
			MedStoreType medStoreType = new MedStoreType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					medStoreType.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("med_type_id"))) {
						
					medStoreType.setMed_type_id(Long.valueOf((String)jsonObj.get("med_type_id")));
		    		mapVo.put("med_type_id", jsonObj.get("med_type_id"));
		    		} else {
						
						err_sb.append("药品类别ID为空  ");
						
					}
					
					
				MedStoreType data_exc_extis = medStoreTypeService.queryMedStoreTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreType.setError_type(err_sb.toString());
					
					list_err.add(medStoreType);
					
				} else {
			  
					String dataJson = medStoreTypeService.addMedStoreType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreType data_exc = new MedStoreType();
			
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
	 * 查询出药品类别字典表去除已经做出对应关系
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/queryMedTypeByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTypeByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medTypeByStore = medStoreTypeService.queryMedTypeByStore(mapVo);

		return JSONObject.parseObject(medTypeByStore);
		
	}
	
	
	/**
	 * @Description 
	 * 添加数据 08110 仓库药品类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoretype/addMedTypeByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedTypeByStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		System.out.println("paramVo====="+paramVo);
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
				Map<String, Object> mapVo=new HashMap<String, Object>();
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("med_type_id", ids[0]);
				mapVo.put("store_id", ids[1]) ;
				listVo.add(mapVo);
		}
		
		String medStoreTypeJson = null ;
		
		try {
			
			medStoreTypeJson = medStoreTypeService.addMedTypeByStore(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(medStoreTypeJson);
		
	}
	
    
}

