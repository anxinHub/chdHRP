/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedLocationDict;
import com.chd.hrp.med.entity.MedLocationType;
import com.chd.hrp.med.serviceImpl.info.basic.MedLocationDictServiceImpl;
import com.chd.hrp.med.serviceImpl.info.basic.MedLocationTypeServiceImpl;
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.EmpDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;


/**
 * 
 * @Description:
 * 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
 * @Table:
 * MED_LOCATION_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedLocationDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedLocationDictController.class);
	
	//引入Service服务
	@Resource(name = "medLocationDictService")
	private final MedLocationDictServiceImpl medLocationDictService = null;
	
	@Resource(name = "medLocationTypeService")
	private final MedLocationTypeServiceImpl medLocationTypeService = null;
	
	
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;
	
	@Resource(name = "empDictService")
	private final EmpDictServiceImpl empDictService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/medLocationDictMainPage", method = RequestMethod.GET)
	public String medLocationDictMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/dict/medLocationDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/medLocationDictAddPage", method = RequestMethod.GET)
	public String medLocationDictAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/dict/medLocationDictAdd";

	}

	/**
	 * @Description 
	 * 添加数据 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/addMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("location_name").toString()));
       
		String medLocationDictJson = medLocationDictService.add(mapVo);

		return JSONObject.parseObject(medLocationDictJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/medLocationDictUpdatePage", method = RequestMethod.GET)
	public String medLocationDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedLocationDict medLocationDict = new MedLocationDict();
    
		medLocationDict = medLocationDictService.queryByCode(mapVo);
		
		
		mode.addAttribute("location_id", medLocationDict.getLocation_id());
		mode.addAttribute("location_code", medLocationDict.getLocation_code());
		mode.addAttribute("location_name", medLocationDict.getLocation_name());
		
		mode.addAttribute("grid_no", medLocationDict.getGrid_no());
		mode.addAttribute("location_type_id", medLocationDict.getLocation_type_id());
		mode.addAttribute("location_type_code", medLocationDict.getLocation_type_code());
		mode.addAttribute("store_id", medLocationDict.getStore_id());
		mode.addAttribute("store_name", medLocationDict.getStore_name());
		
		mode.addAttribute("location_nature", medLocationDict.getLocation_nature());
		mode.addAttribute("ctrl_type", medLocationDict.getCtrl_type());
		mode.addAttribute("picker", medLocationDict.getPicker());
		mode.addAttribute("picker_name", medLocationDict.getPicker_name());
		mode.addAttribute("is_stop", medLocationDict.getIs_stop());
		
		System.out.println("***********88888888111111"+medLocationDict.getNote());
		mode.addAttribute("note", medLocationDict.getNote());
		
		return "hrp/med/info/basic/location/dict/medLocationDictUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/updateMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("location_name").toString()));
	  
		String medLocationDictJson = medLocationDictService.update(mapVo);
		
		return JSONObject.parseObject(medLocationDictJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/addOrUpdateMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medLocationDictJson ="";
		
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
	  
		medLocationDictJson = medLocationDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(medLocationDictJson);
	}
	
	/**
	 * 删除数据 货位性质 LOCATION_NATURE
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/deleteMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedLocationDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("location_id", ids[3]);
				System.out.println("********** :"+mapVo.get("location_id"));
	      listVo.add(mapVo);
	      
	    }
	    
		String medLocationDictJson = medLocationDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(medLocationDictJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/queryMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medLocationDict = medLocationDictService.query(getPage(mapVo));

		return JSONObject.parseObject(medLocationDict);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/medLocationDictImportPage", method = RequestMethod.GET)
	public String medLocationDictImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/dict/medLocationDictImport";

	}
	
	/**
	 * @Description 
	 * 下载导入模版 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/location/dict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","货位字典.xlsx");
	    
	    return null; 
	 }
	 
	 
	 /**
	 * @Description 导入
	 * @param  mapVo
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/importMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importMedLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		String locationDictJson = null;
		
		try {
			
			if(mapVo.get("group_id") == null){			
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){			
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){	
				mapVo.put("copy_code", SessionManager.getCopyCode());      
			}
			
			locationDictJson = medLocationDictService.importMedLocationDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			locationDictJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(locationDictJson);
	}
	 
	 
	 
   /**
	 * @Description 
	 * 批量添加数据 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/location/dict/addBatchMedLocationDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedLocationDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<MedLocationDict> list_err = new ArrayList<MedLocationDict>();
		
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
			
			MedLocationDict medLocationDict = new MedLocationDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			

					if (StringTool.isNotBlank(jsonObj.get("location_code"))) {
						
					medLocationDict.setLocation_code((jsonObj.get("location_code").toString()));
					
		    		mapVo.put("location_code", jsonObj.get("location_code").toString());
		    		
		    		} else {
						
						err_sb.append("货位编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("location_name"))) {
						
					medLocationDict.setLocation_name(jsonObj.get("location_name").toString());
					
		    		mapVo.put("location_name", jsonObj.get("location_name").toString());
		    		
		    		} else {
						
						err_sb.append("货位名称为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("location_type_code"))) {
						
                        Map<String, Object> typeMapVo = new HashMap<String, Object>();
						
						typeMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
						typeMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
						typeMapVo.put("copy_code", SessionManager.getCopyCode());  
						
						typeMapVo.put("location_type_code", jsonObj.get("location_type_code").toString());
						
						MedLocationType medLocationType =  medLocationTypeService.queryByCode(typeMapVo);
						
						if(medLocationType != null){
							
							mapVo.put("location_type_id", medLocationType.getLocation_type_id());
							
						}else {
							
							err_sb.append("货位分类编码不存在");
						 }
						
					medLocationDict.setLocation_type_code(jsonObj.get("location_type_code").toString());
					
		    		
		    		} else {
						
						err_sb.append("货位分类编码为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("grid_no"))) {
						
					medLocationDict.setGrid_no(jsonObj.get("grid_no").toString());
					
		    		mapVo.put("grid_no", jsonObj.get("grid_no"));
		    		
		    		} else {
						
						err_sb.append("排位编号为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("store_code"))) {
						
					      Map<String, Object> storeMapVo = new HashMap<String, Object>();
							
	                        storeMapVo.put("group_id", SessionManager.getGroupId());   
		    		         
	                        storeMapVo.put("hos_id", SessionManager.getHosId());   
				    		         					 
	                        storeMapVo.put("copy_code", SessionManager.getCopyCode());  
							
	                        storeMapVo.put("store_code", jsonObj.get("store_code"));
	                        
	                        storeMapVo.put("is_stop", "0");
							
							StoreDict storeDict =  storeDictService.queryStoreDictByCode(storeMapVo);
							
							if(storeDict != null){
								
								mapVo.put("store_id", storeDict.getStore_id());
								
							}else {
								
								err_sb.append("仓库编码不存在");
							}
						
							medLocationDict.setStore_code(jsonObj.get("store_code").toString());

		    		} else {
						
						err_sb.append("所属库房为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("location_nature"))) {
						
					medLocationDict.setLocation_nature(jsonObj.get("location_nature").toString());
					
		    		mapVo.put("location_nature", jsonObj.get("location_nature").toString());
		    		
		    		} else {
						
						err_sb.append("货位性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ctrl_type"))) {
						
					medLocationDict.setCtrl_type(jsonObj.get("ctrl_type").toString());
					
		    		mapVo.put("ctrl_type", jsonObj.get("ctrl_type"));
		    		
		    		} else {
						
						err_sb.append("控制方式为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("picker_code"))) {
						

						  Map<String, Object> empMapVo = new HashMap<String, Object>();
							
						  empMapVo.put("group_id", SessionManager.getGroupId());   
		    		         
						  empMapVo.put("hos_id", SessionManager.getHosId());   
				    		         					 
						  empMapVo.put("emp_code", jsonObj.get("picker_code").toString());  
	                        
						  empMapVo.put("is_stop", "0");
						
						  EmpDict empdict =  empDictService.queryEmpDictByCode(empMapVo);
						  
						  if(empdict != null){
							  
							  mapVo.put("picker", empdict.getEmp_id());
							  
						  }else {
							  
							  err_sb.append("拣货员编码不存在 ");
						  }
						
					      medLocationDict.setPicker_code(jsonObj.get("picker_code").toString());

		    		} else {
						
						err_sb.append("拣货员为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
							
							medLocationDict.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
							
				    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
							
		    		
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					medLocationDict.setNote(jsonObj.get("note").toString());
					
		    		mapVo.put("note", jsonObj.get("note").toString());
		    		
		    		} else {
						
		    			medLocationDict.setNote(null);
						
			    		mapVo.put("note", null);
						
					}

						
					if(mapVo.get("location_code") !=null){
						
                        Map<String, Object> locaMapVo = new HashMap<String, Object>();
						
                        locaMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
                        locaMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
                        locaMapVo.put("copy_code", SessionManager.getCopyCode());  
						
                        locaMapVo.put("location_code", mapVo.get("location_code").toString());
                        
                        locaMapVo.put("is_stop", "0");
						
                        MedLocationDict ex = medLocationDictService.queryByUniqueness(locaMapVo);
                        
                        if(ex != null){
                        	
                        	err_sb.append("货位字典编码重复");
                         }
						}
					
					
				if (err_sb.toString().length() > 0) {
					
					medLocationDict.setError_type(err_sb.toString());
					
					list_err.add(medLocationDict);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("location_name").toString()));
					
				  addlistMaps.add(mapVo);
					
				}
				
			}
			
			if(addlistMaps.size() > 0 ){
				
				medLocationDictService.addBatch(addlistMaps);
			}
			
		} catch (DataAccessException e) {
			
			MedLocationDict data_exc = new MedLocationDict();
			
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

