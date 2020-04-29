/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.hrp.mat.entity.MatLocationDict;
import com.chd.hrp.mat.entity.MatLocationType;
import com.chd.hrp.mat.serviceImpl.info.basic.MatLocationDictServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatLocationTypeServiceImpl;
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
 * MAT_LOCATION_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatLocationDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatLocationDictController.class);
	
	//引入Service服务
	@Resource(name = "matLocationDictService")
	private final MatLocationDictServiceImpl matLocationDictService = null;
	
	@Resource(name = "matLocationTypeService")
	private final MatLocationTypeServiceImpl matLocationTypeService = null;
	
	
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/matLocationDictMainPage", method = RequestMethod.GET)
	public String matLocationDictMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/dict/matLocationDictMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/matLocationDictAddPage", method = RequestMethod.GET)
	public String matLocationDictAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/dict/matLocationDictAdd";

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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/addMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String matLocationDictJson = matLocationDictService.add(mapVo);

		return JSONObject.parseObject(matLocationDictJson);
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/matLocationDictUpdatePage", method = RequestMethod.GET)
	public String matLocationDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatLocationDict matLocationDict = new MatLocationDict();
    
		matLocationDict = matLocationDictService.queryByCode(mapVo);
		
		
		mode.addAttribute("location_id", matLocationDict.getLocation_id());
		mode.addAttribute("location_code", matLocationDict.getLocation_code());
		mode.addAttribute("location_name", matLocationDict.getLocation_name());
		
		mode.addAttribute("grid_no", matLocationDict.getGrid_no());
		mode.addAttribute("location_type_id", matLocationDict.getLocation_type_id());
		mode.addAttribute("location_type_code", matLocationDict.getLocation_type_code());
		mode.addAttribute("store_id", matLocationDict.getStore_id());
		mode.addAttribute("store_name", matLocationDict.getStore_name());
		
		mode.addAttribute("location_nature", matLocationDict.getLocation_nature());
		mode.addAttribute("ctrl_type", matLocationDict.getCtrl_type());
		mode.addAttribute("picker", matLocationDict.getPicker());
		mode.addAttribute("picker_name", matLocationDict.getPicker_name());
		mode.addAttribute("is_stop", matLocationDict.getIs_stop());
		
		//System.out.println("***********88888888111111"+matLocationDict.getNote());
		mode.addAttribute("note", matLocationDict.getNote());
		
		return "hrp/mat/info/basic/location/dict/matLocationDictUpdate";
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/updateMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	  
		String matLocationDictJson = matLocationDictService.update(mapVo);
		
		return JSONObject.parseObject(matLocationDictJson);
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/addOrUpdateMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matLocationDictJson ="";
		
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
	  
		matLocationDictJson = matLocationDictService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(matLocationDictJson);
	}
	
	/**
	 * 删除数据 货位性质 LOCATION_NATURE
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/deleteMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatLocationDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("location_id", ids[3]);
				//System.out.println("********** :"+mapVo.get("location_id"));
	      listVo.add(mapVo);
	      
	    }
	    
		String matLocationDictJson = matLocationDictService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(matLocationDictJson);
			
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/queryMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatLocationDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matLocationDict = matLocationDictService.query(getPage(mapVo));

		return JSONObject.parseObject(matLocationDict);
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/matLocationDictImportPage", method = RequestMethod.GET)
	public String matLocationDictImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/dict/matLocationDictImport";

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
	 @RequestMapping(value="hrp/mat/info/basic/location/dict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","货位字典.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 货位性质 LOCATION_NATURE
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
	@RequestMapping(value="/hrp/mat/info/basic/location/dict/readMatLocationDictFiles",method = RequestMethod.POST)  
    public String readMatLocationDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		Map<String, Map<String,Object>> exitesMap = new HashMap<String, Map<String,Object>>();
		
		List<MatLocationDict> list_err = new ArrayList<MatLocationDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatLocationDict matLocationDict = new MatLocationDict();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   

					if (ExcelReader.validExceLColunm(temp, 0)) {
						
					matLocationDict.setLocation_code(temp[0]);
		    		
					mapVo.put("location_code", temp[0]);
					
					} else {
						
						err_sb.append("货位编码为空  ");
						
					}
					
					if(exitesMap.get(temp[0]) != null){

						err_sb.append("货位分类编码已存在!");

					}
					
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
					matLocationDict.setLocation_name(temp[1]);
					
		    		mapVo.put("location_name", temp[1]);
					
					} else {
						
						err_sb.append("货位名称为空  ");
						
					}
					 
					
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						Map<String, Object> typeMapVo = new HashMap<String, Object>();
						
						typeMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
						typeMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
						typeMapVo.put("copy_code", SessionManager.getCopyCode());  
						
						typeMapVo.put("location_type_code", temp[2]);
						
						MatLocationType matLocationType =  matLocationTypeService.queryByCode(typeMapVo);
						
						if(matLocationType != null){
							
							mapVo.put("location_type_id", matLocationType.getLocation_type_id());
							
						}else {
							
							err_sb.append("货位分类编码不存在");
						 }
						
					   matLocationDict.setLocation_type_code(temp[2].toString());
					
					} else {
						
						err_sb.append("货位分类编码为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
					matLocationDict.setGrid_no(temp[3]);
					
		    		mapVo.put("grid_no", temp[3]);
					
					} else {
						
						err_sb.append("排位编号为空  ");
						
					}
					
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
                        Map<String, Object> storeMapVo = new HashMap<String, Object>();
						
                        storeMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
                        storeMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
                        storeMapVo.put("copy_code", SessionManager.getCopyCode());  
						
                        storeMapVo.put("store_code", temp[4]);
                        
                        storeMapVo.put("is_stop", "0");
						
						StoreDict storeDict =  storeDictService.queryStoreDictByCode(storeMapVo);
						
						if(storeDict != null){
							
							mapVo.put("store_id", storeDict.getStore_id());
							
						}else {
							
							err_sb.append("仓库编码不存在");
						}
						
					       matLocationDict.setStore_code(temp[4]);
					
					
					} else {
						
						err_sb.append("所属库房编码为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,5)) {
						
					 matLocationDict.setLocation_nature(temp[5]);
					
		    		 mapVo.put("location_nature", temp[5]);
					
					} else {
						
						err_sb.append("货位性质为空  ");
						
					}

					 
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
					matLocationDict.setCtrl_type(temp[6]);
					
		    		mapVo.put("ctrl_type", temp[6]);
					
					} else {
						
						err_sb.append("控制方式为空  ");
						
					}
					

					if (ExcelReader.validExceLColunm(temp,7)) {
						
						  Map<String, Object> empMapVo = new HashMap<String, Object>();
							
						  empMapVo.put("group_id", SessionManager.getGroupId());   
		    		         
						  empMapVo.put("hos_id", SessionManager.getHosId());   
				    		         					 
						  empMapVo.put("emp_code", temp[7]);  
	                        
						  empMapVo.put("is_stop", "0");
						
						  EmpDict empdict =  empDictService.queryEmpDictByCode(empMapVo);
						  
						  if(empdict != null){
							  
							  mapVo.put("picker", empdict.getEmp_id());
							  
						  }else {
							  
							  err_sb.append("拣货员编码不存在 ");
						  }
						
					   matLocationDict.setPicker_code(temp[7]);
					
					} else {
						
						err_sb.append("拣货员为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,8)) {
						
						
						if(is_Stop_Map.containsKey(temp[8])){
							
							matLocationDict.setIs_stop(Integer.parseInt(is_Stop_Map.get(temp[8]).toString()));
							
				    		mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(temp[8]).toString()));
							
						}else {
							
							err_sb.append("是否停用输入错误  ");
						}

					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,9)) {
						
					   matLocationDict.setNote(temp[9]);
					   
		    		    mapVo.put("note", temp[9]);
					
					} else {
						
						   matLocationDict.setNote(null);
						   
			    		    mapVo.put("note",null);
						
					}


					if(mapVo.get("location_code") !=null){
                        Map<String, Object> locaMapVo = new HashMap<String, Object>();
						
                        locaMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
                        locaMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
                        locaMapVo.put("copy_code", SessionManager.getCopyCode());  
						
                        locaMapVo.put("location_code", mapVo.get("location_code").toString());
                        
                        locaMapVo.put("is_stop", "0");
						
                        MatLocationDict ex = matLocationDictService.queryByUniqueness(locaMapVo);
                        
                        if(ex != null){
                        	
                        	err_sb.append("货位字典编码重复");
                         }
						}
				if (err_sb.toString().length() > 0) {
					
					matLocationDict.setError_type(err_sb.toString());
					
					list_err.add(matLocationDict);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("location_name").toString()));
			  
			      exitesMap.put(temp[0], mapVo);
			      
				  addlistMaps.add(mapVo);

					
				}
				
			}

			if(addlistMaps.size() > 0 ){
				
				matLocationDictService.addBatch(addlistMaps);
			}
			
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			
			MatLocationDict data_exc = new MatLocationDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/location/dict/addBatchMatLocationDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatLocationDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<MatLocationDict> list_err = new ArrayList<MatLocationDict>();
		
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
			
			MatLocationDict matLocationDict = new MatLocationDict();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			

					if (StringTool.isNotBlank(jsonObj.get("location_code"))) {
						
					matLocationDict.setLocation_code((jsonObj.get("location_code").toString()));
					
		    		mapVo.put("location_code", jsonObj.get("location_code").toString());
		    		
		    		} else {
						
						err_sb.append("货位编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("location_name"))) {
						
					matLocationDict.setLocation_name(jsonObj.get("location_name").toString());
					
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
						
						MatLocationType matLocationType =  matLocationTypeService.queryByCode(typeMapVo);
						
						if(matLocationType != null){
							
							mapVo.put("location_type_id", matLocationType.getLocation_type_id());
							
						}else {
							
							err_sb.append("货位分类编码不存在");
						 }
						
					matLocationDict.setLocation_type_code(jsonObj.get("location_type_code").toString());
					
		    		
		    		} else {
						
						err_sb.append("货位分类编码为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("grid_no"))) {
						
					matLocationDict.setGrid_no(jsonObj.get("grid_no").toString());
					
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
						
							matLocationDict.setStore_code(jsonObj.get("store_code").toString());

		    		} else {
						
						err_sb.append("所属库房为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("location_nature"))) {
						
					matLocationDict.setLocation_nature(jsonObj.get("location_nature").toString());
					
		    		mapVo.put("location_nature", jsonObj.get("location_nature").toString());
		    		
		    		} else {
						
						err_sb.append("货位性质为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ctrl_type"))) {
						
					matLocationDict.setCtrl_type(jsonObj.get("ctrl_type").toString());
					
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
						
					      matLocationDict.setPicker_code(jsonObj.get("picker_code").toString());

		    		} else {
						
						err_sb.append("拣货员为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
							
							matLocationDict.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
							
				    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
							
		    		
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					matLocationDict.setNote(jsonObj.get("note").toString());
					
		    		mapVo.put("note", jsonObj.get("note").toString());
		    		
		    		} else {
						
		    			matLocationDict.setNote(null);
						
			    		mapVo.put("note", null);
						
					}

						
					if(mapVo.get("location_code") !=null){
						
                        Map<String, Object> locaMapVo = new HashMap<String, Object>();
						
                        locaMapVo.put("group_id", SessionManager.getGroupId());   
	    		         
                        locaMapVo.put("hos_id", SessionManager.getHosId());   
			    		         					 
                        locaMapVo.put("copy_code", SessionManager.getCopyCode());  
						
                        locaMapVo.put("location_code", mapVo.get("location_code").toString());
                        
                        locaMapVo.put("is_stop", "0");
						
                        MatLocationDict ex = matLocationDictService.queryByUniqueness(locaMapVo);
                        
                        if(ex != null){
                        	
                        	err_sb.append("货位字典编码重复");
                         }
						}
					
					
				if (err_sb.toString().length() > 0) {
					
					matLocationDict.setError_type(err_sb.toString());
					
					list_err.add(matLocationDict);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("location_name").toString()));
					
				  addlistMaps.add(mapVo);
					
				}
				
			}
			
			if(addlistMaps.size() > 0 ){
				
				matLocationDictService.addBatch(addlistMaps);
			}
			
		} catch (DataAccessException e) {
			
			MatLocationDict data_exc = new MatLocationDict();
			
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

