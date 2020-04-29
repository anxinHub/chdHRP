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
import com.chd.hrp.med.entity.MedInvUnitMap;
import com.chd.hrp.med.serviceImpl.info.basic.MedInvUnitMapServiceImpl;
/**
 * 
 * @Description:
 * 08116 材料包装单位关系表
 * @Table:
 * MED_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedInvUnitMapController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInvUnitMapController.class);
	
	//引入Service服务
	@Resource(name = "medInvUnitMapService")
	private final MedInvUnitMapServiceImpl medInvUnitMapService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/medInvUnitMapMainPage", method = RequestMethod.GET)
	public String medInvUnitMapMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medinvunitmap/medInvUnitMapMain";

	}
	/**
	 * 选择材料页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/medInvPage", method = RequestMethod.GET)
	public String medInvPage(Model mode) throws Exception {
		
		return "hrp/med/info/basic/medinvunitmap/medInv";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/medInvUnitMapAddPage", method = RequestMethod.GET)
	public String medInvUnitMapAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medinvunitmap/medInvUnitMapAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/addMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String medInvUnitMapJson = medInvUnitMapService.addMedInvUnitMap(mapVo);

		return JSONObject.parseObject(medInvUnitMapJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/medInvUnitMapUpdatePage", method = RequestMethod.GET)
	public String medInvUnitMapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedInvUnitMap medInvUnitMap = new MedInvUnitMap();
    
		medInvUnitMap = medInvUnitMapService.queryMedInvUnitMapByCode(mapVo);
		
		mode.addAttribute("group_id", medInvUnitMap.getGroup_id());
		mode.addAttribute("hos_id", medInvUnitMap.getHos_id());
		mode.addAttribute("copy_code", medInvUnitMap.getCopy_code());
		mode.addAttribute("inv_id", medInvUnitMap.getInv_id());
		mode.addAttribute("pack_code", medInvUnitMap.getPack_code());
		mode.addAttribute("unit_code", medInvUnitMap.getUnit_code());
		mode.addAttribute("map_amount", medInvUnitMap.getMap_amount());
		
		return "hrp/med/info/basic/medinvunitmap/medInvUnitMapUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/updateMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medInvUnitMapJson = medInvUnitMapService.updateMedInvUnitMap(mapVo);
		
		return JSONObject.parseObject(medInvUnitMapJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/saveBatchMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchMedInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("inv_id", ids[3])   ;
			mapVo.put("pack_code", ids[4]);
			mapVo.put("unit_code", ids[5]);
			mapVo.put("map_amount", ids[6]);
			mapVo.put("pack_codeNew", ids[7]);
			
			MedInvUnitMap count = medInvUnitMapService.queryMedInvUnitMapByCode(mapVo);
			if(count != null){
				updateList.add(mapVo);
			}else{
				listVo.add(mapVo);
			}
			
		}
		String medInvUnitMapJson = null;
		if(updateList.size()>0){
			medInvUnitMapJson = medInvUnitMapService.updateBatchMedInvUnitMap(updateList);
		}
		if(listVo.size()>0){
			medInvUnitMapJson = medInvUnitMapService.addBatchMedInvUnitMap(listVo);
		}
		
		return JSONObject.parseObject(medInvUnitMapJson);
	}
	/**
	 * 批量更新08116 材料包装单位关系表<BR> 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/updateBatchMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchMedInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("inv_id", ids[3])   ;
			mapVo.put("pack_code", ids[4]);
			mapVo.put("unit_code", ids[5]);
			mapVo.put("map_amount", ids[6]);
			mapVo.put("pack_codeOld", ids[7]);
			
			listVo.add(mapVo);
      
		}
	  
		String medInvUnitMapJson = medInvUnitMapService.updateBatchMedInvUnitMap(listVo);
		
		return JSONObject.parseObject(medInvUnitMapJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/deleteMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("inv_id", ids[3])   ;
				mapVo.put("pack_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medInvUnitMapJson = medInvUnitMapService.deleteBatchMedInvUnitMap(listVo);
			
	  return JSONObject.parseObject(medInvUnitMapJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/queryMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medInvUnitMap = medInvUnitMapService.queryMedInvUnitMap(getPage(mapVo));

		return JSONObject.parseObject(medInvUnitMap);
		
	}
	/**
	 * 弹出选择材料页面，根据查询条件查询出药品材料结果集（MED_INV中IS_STOP=0）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/queryMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medInvUnitMap = medInvUnitMapService.queryMedInv(getPage(mapVo));

		return JSONObject.parseObject(medInvUnitMap);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08116 材料包装单位关系表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/medInvUnitMapImportPage", method = RequestMethod.GET)
	public String medInvUnitMapImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medinvunitmap/medInvUnitMapImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08116 材料包装单位关系表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/medinvunitmap/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08116 材料包装单位关系表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08116 材料包装单位关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/medinvunitmap/readMedInvUnitMapFiles",method = RequestMethod.POST)  
    public String readMedInvUnitMapFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedInvUnitMap> list_err = new ArrayList<MedInvUnitMap>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedInvUnitMap medInvUnitMap = new MedInvUnitMap();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medInvUnitMap.setInv_id(Long.valueOf(temp[3]));
		    		mapVo.put("inv_id", temp[3]);
					
					} else {
						
						err_sb.append("药品材料ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medInvUnitMap.setPack_code(temp[4]);
		    		mapVo.put("pack_code", temp[4]);
					
					} else {
						
						err_sb.append("包装单位为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medInvUnitMap.setUnit_code(temp[5]);
		    		mapVo.put("unit_code", temp[5]);
					
					} else {
						
						err_sb.append("计量单位为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medInvUnitMap.setMap_amount(Long.valueOf(temp[6]));
		    		mapVo.put("map_amount", temp[6]);
					
					} else {
						
						err_sb.append("转换数量为空  ");
						
					}
					 
					
				MedInvUnitMap data_exc_extis = medInvUnitMapService.queryMedInvUnitMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medInvUnitMap.setError_type(err_sb.toString());
					
					list_err.add(medInvUnitMap);
					
				} else {
			  
					String dataJson = medInvUnitMapService.addMedInvUnitMap(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedInvUnitMap data_exc = new MedInvUnitMap();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08116 材料包装单位关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medinvunitmap/addBatchMedInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInvUnitMap(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedInvUnitMap> list_err = new ArrayList<MedInvUnitMap>();
		
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
			
			MedInvUnitMap medInvUnitMap = new MedInvUnitMap();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					medInvUnitMap.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("药品材料ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pack_code"))) {
						
					medInvUnitMap.setPack_code((String)jsonObj.get("pack_code"));
		    		mapVo.put("pack_code", jsonObj.get("pack_code"));
		    		} else {
						
						err_sb.append("包装单位为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("unit_code"))) {
						
					medInvUnitMap.setUnit_code((String)jsonObj.get("unit_code"));
		    		mapVo.put("unit_code", jsonObj.get("unit_code"));
		    		} else {
						
						err_sb.append("计量单位为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("map_amount"))) {
						
					medInvUnitMap.setMap_amount(Long.valueOf((String)jsonObj.get("map_amount")));
		    		mapVo.put("map_amount", jsonObj.get("map_amount"));
		    		} else {
						
						err_sb.append("转换数量为空  ");
						
					}
					
					
				MedInvUnitMap data_exc_extis = medInvUnitMapService.queryMedInvUnitMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medInvUnitMap.setError_type(err_sb.toString());
					
					list_err.add(medInvUnitMap);
					
				} else {
			  
					String dataJson = medInvUnitMapService.addMedInvUnitMap(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedInvUnitMap data_exc = new MedInvUnitMap();
			
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

