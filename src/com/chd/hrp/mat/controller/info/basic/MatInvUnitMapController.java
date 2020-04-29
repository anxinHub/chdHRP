/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.hrp.mat.entity.MatInvUnitMap;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvUnitMapServiceImpl;
/**
 * 
 * @Description:
 * 04116 材料包装单位关系表
 * @Table:
 * MAT_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatInvUnitMapController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvUnitMapController.class);
	
	//引入Service服务
	@Resource(name = "matInvUnitMapService")
	private final MatInvUnitMapServiceImpl matInvUnitMapService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/matInvUnitMapMainPage", method = RequestMethod.GET)
	public String matInvUnitMapMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matinvunitmap/matInvUnitMapMain";

	}
	/**
	 * 选择材料页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/matInvPage", method = RequestMethod.GET)
	public String matInvPage(Model mode) throws Exception {
		
		return "hrp/mat/info/basic/matinvunitmap/matInv";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/matInvUnitMapAddPage", method = RequestMethod.GET)
	public String matInvUnitMapAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matinvunitmap/matInvUnitMapAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/addMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String matInvUnitMapJson = matInvUnitMapService.addMatInvUnitMap(mapVo);

		return JSONObject.parseObject(matInvUnitMapJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/matInvUnitMapUpdatePage", method = RequestMethod.GET)
	public String matInvUnitMapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatInvUnitMap matInvUnitMap = new MatInvUnitMap();
    
		matInvUnitMap = matInvUnitMapService.queryMatInvUnitMapByCode(mapVo);
		
		mode.addAttribute("group_id", matInvUnitMap.getGroup_id());
		mode.addAttribute("hos_id", matInvUnitMap.getHos_id());
		mode.addAttribute("copy_code", matInvUnitMap.getCopy_code());
		mode.addAttribute("inv_id", matInvUnitMap.getInv_id());
		mode.addAttribute("pack_code", matInvUnitMap.getPack_code());
		mode.addAttribute("unit_code", matInvUnitMap.getUnit_code());
		mode.addAttribute("map_amount", matInvUnitMap.getMap_amount());
		
		return "hrp/mat/info/basic/matinvunitmap/matInvUnitMapUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/updateMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matInvUnitMapJson = matInvUnitMapService.updateMatInvUnitMap(mapVo);
		
		return JSONObject.parseObject(matInvUnitMapJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/saveBatchMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchMatInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			MatInvUnitMap count = matInvUnitMapService.queryMatInvUnitMapByCode(mapVo);
			if(count != null){
				updateList.add(mapVo);
			}else{
				listVo.add(mapVo);
			}
			
		}
		String matInvUnitMapJson = null;
		if(updateList.size()>0){
			matInvUnitMapJson = matInvUnitMapService.updateBatchMatInvUnitMap(updateList);
		}
		if(listVo.size()>0){
			matInvUnitMapJson = matInvUnitMapService.addBatchMatInvUnitMap(listVo);
		}
		
		return JSONObject.parseObject(matInvUnitMapJson);
	}
	/**
	 * 批量更新04116 材料包装单位关系表<BR> 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/updateBatchMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchMatInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	  
		String matInvUnitMapJson = matInvUnitMapService.updateBatchMatInvUnitMap(listVo);
		
		return JSONObject.parseObject(matInvUnitMapJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/deleteMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvUnitMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String matInvUnitMapJson = matInvUnitMapService.deleteBatchMatInvUnitMap(listVo);
			
	  return JSONObject.parseObject(matInvUnitMapJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04116 材料包装单位关系表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/queryMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvUnitMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matInvUnitMap = matInvUnitMapService.queryMatInvUnitMap(getPage(mapVo));

		return JSONObject.parseObject(matInvUnitMap);
		
	}
	/**
	 * 弹出选择材料页面，根据查询条件查询出物资材料结果集（MAT_INV中IS_STOP=0）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/queryMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matInvUnitMap = matInvUnitMapService.queryMatInv(getPage(mapVo));

		return JSONObject.parseObject(matInvUnitMap);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04116 材料包装单位关系表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/matInvUnitMapImportPage", method = RequestMethod.GET)
	public String matInvUnitMapImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matinvunitmap/matInvUnitMapImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04116 材料包装单位关系表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/matinvunitmap/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04116 材料包装单位关系表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04116 材料包装单位关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/matinvunitmap/readMatInvUnitMapFiles",method = RequestMethod.POST)  
    public String readMatInvUnitMapFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatInvUnitMap> list_err = new ArrayList<MatInvUnitMap>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatInvUnitMap matInvUnitMap = new MatInvUnitMap();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matInvUnitMap.setInv_id(Long.valueOf(temp[3]));
		    		mapVo.put("inv_id", temp[3]);
					
					} else {
						
						err_sb.append("物资材料ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matInvUnitMap.setPack_code(temp[4]);
		    		mapVo.put("pack_code", temp[4]);
					
					} else {
						
						err_sb.append("包装单位为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matInvUnitMap.setUnit_code(temp[5]);
		    		mapVo.put("unit_code", temp[5]);
					
					} else {
						
						err_sb.append("计量单位为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matInvUnitMap.setMap_amount(Long.valueOf(temp[6]));
		    		mapVo.put("map_amount", temp[6]);
					
					} else {
						
						err_sb.append("转换数量为空  ");
						
					}
					 
					
				MatInvUnitMap data_exc_extis = matInvUnitMapService.queryMatInvUnitMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matInvUnitMap.setError_type(err_sb.toString());
					
					list_err.add(matInvUnitMap);
					
				} else {
			  
					String dataJson = matInvUnitMapService.addMatInvUnitMap(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvUnitMap data_exc = new MatInvUnitMap();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04116 材料包装单位关系表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matinvunitmap/addBatchMatInvUnitMap", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatInvUnitMap(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatInvUnitMap> list_err = new ArrayList<MatInvUnitMap>();
		
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
			
			MatInvUnitMap matInvUnitMap = new MatInvUnitMap();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					matInvUnitMap.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("物资材料ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pack_code"))) {
						
					matInvUnitMap.setPack_code((String)jsonObj.get("pack_code"));
		    		mapVo.put("pack_code", jsonObj.get("pack_code"));
		    		} else {
						
						err_sb.append("包装单位为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("unit_code"))) {
						
					matInvUnitMap.setUnit_code((String)jsonObj.get("unit_code"));
		    		mapVo.put("unit_code", jsonObj.get("unit_code"));
		    		} else {
						
						err_sb.append("计量单位为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("map_amount"))) {
						
					matInvUnitMap.setMap_amount(Long.valueOf((String)jsonObj.get("map_amount")));
		    		mapVo.put("map_amount", jsonObj.get("map_amount"));
		    		} else {
						
						err_sb.append("转换数量为空  ");
						
					}
					
					
				MatInvUnitMap data_exc_extis = matInvUnitMapService.queryMatInvUnitMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matInvUnitMap.setError_type(err_sb.toString());
					
					list_err.add(matInvUnitMap);
					
				} else {
			  
					String dataJson = matInvUnitMapService.addMatInvUnitMap(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvUnitMap data_exc = new MatInvUnitMap();
			
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

