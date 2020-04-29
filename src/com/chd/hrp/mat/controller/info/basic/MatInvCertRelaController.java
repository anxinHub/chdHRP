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
import com.chd.hrp.mat.entity.MatInvCertRela;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvCertRelaServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreInvServiceImpl;
/**
 * 
 * @Description:
 * 

 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatInvCertRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvCertRelaController.class);
	
	//引入Service服务
	@Resource(name = "matInvCertRelaService")
	private final MatInvCertRelaServiceImpl matInvCertRelaService = null;
	
	//引入Service服务
	@Resource(name = "matStoreInvService")
	private final MatStoreInvServiceImpl matStoreInvService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/matInvCertRelaMainPage", method = RequestMethod.GET)
	public String matInvCertRelaMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertrela/matInvCertRelaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/matInvCertRelaAddPage", method = RequestMethod.GET)
	public String matInvCertRelaAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertrela/matInvCertRelaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 

	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/addMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCertRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> delList= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			Map<String, Object> delMap=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("inv_id", ids[4]);
			mapVo.put("cert_id", ids[5].split(" ")[0]);
			mapVo.put("cert_code", ids[5].split(" ")[1]);
			
			listVo.add(mapVo);
			
			delMap.put("group_id", ids[0]);
			delMap.put("hos_id", ids[1])   ;
			delMap.put("copy_code", ids[2])   ;
			delMap.put("cert_id", ids[5].split(" ")[0]);
			delList.add(delMap);
		}
		
		String matInvCertRelaJson = null;
		if(delList.size()>0){
			matInvCertRelaJson = matInvCertRelaService.deleteBatchMatInvCertRela(delList);
		}
		if(listVo.size()>0){
			matInvCertRelaJson = matInvCertRelaService.addBatchMatInvCertRela(listVo);
		}
		
		return JSONObject.parseObject(matInvCertRelaJson);
		
	}
	
	/**
	 * @Description 
	 * 证件信息管理  添加材料保存
	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/addMatInvCertRelaAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCertRelaAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			matJson = matInvCertRelaService.addCertInv(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/addMatInvCertRelaAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCertRelaAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
		}
		
		//删除已存在的数据
		matInvCertRelaService.deleteMatInvCertRela(mapVo);
		
		//查询所有 在用材料的 group_id hos_id copy_code inv_id 
		List<Map<String,Object>> list = matInvCertRelaService.queryInvId(mapVo);
		
		List<Map<String,Object>>  listAdd  = new ArrayList<Map<String,Object>>();
		
		int count = 1 ;
		for(Map<String,Object> item : list ){
			
			Map<String,Object> map  = new HashMap<String,Object>();

			map.put("group_id", String.valueOf(item.get("group_id")));
			map.put("hos_id", String.valueOf(item.get("hos_id")));
			map.put("copy_code", String.valueOf(item.get("copy_code")));
			map.put("cert_id", String.valueOf(mapVo.get("cert_id")));
			map.put("inv_id", String.valueOf(item.get("inv_id")));
			map.put("cert_code", String.valueOf(mapVo.get("cert_code")));
			
			listAdd.add(map);
			count++;
			if(count%1000 == 0){
				matInvCertRelaService.addBatchMatInvCertRela(listAdd);
				listAdd.clear();
			}

		}
		
		String 	matInvCertRelaJson = matInvCertRelaService.addBatchMatInvCertRela(listAdd);
		
		return JSONObject.parseObject(matInvCertRelaJson);
		
	}
	
	/**
	 * 材料证件信息添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/matInvCertRelaAddInvPage", method = RequestMethod.GET)
	public String matInvCertRelaAddInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("cert_id", mapVo.get("cert_id"));
		mode.addAttribute("cert_code", mapVo.get("cert_code"));
		mode.addAttribute("cert_name", mapVo.get("cert_name"));
		
		
		return "hrp/mat/info/basic/cert/invcertrela/matInvCertRelaAddInv";
	}
	
	/**
	 * 材料是否存在于仓库中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/existsMatInvInCert", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatInvInCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matInvCertRelaService.existsMatInvInCert(mapVo);
		return matMsg;
	}
	/**
	 * @Description 
	 * 更新跳转页面 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/matInvCertRelaUpdatePage", method = RequestMethod.GET)
	public String matInvCertRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MatInvCertRela matInvCertRela = new MatInvCertRela();
    
		matInvCertRela = matInvCertRelaService.queryMatInvCertRelaByCode(mapVo);
		
		mode.addAttribute("group_id", matInvCertRela.getGroup_id());
		mode.addAttribute("hos_id", matInvCertRela.getHos_id());
		mode.addAttribute("copy_code", matInvCertRela.getCopy_code());
		mode.addAttribute("cert_id", matInvCertRela.getCert_id());
		mode.addAttribute("inv_id", matInvCertRela.getInv_id());
		
		return "hrp/mat/info/basic/cert/invcertrela/matInvCertRelaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/updateMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matInvCertRelaJson = matInvCertRelaService.updateMatInvCertRela(mapVo);
		
		return JSONObject.parseObject(matInvCertRelaJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/addOrUpdateMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matInvCertRelaJson ="";
		
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
	  
		matInvCertRelaJson = matInvCertRelaService.addOrUpdateMatInvCertRela(detailVo);
		
		}
		return JSONObject.parseObject(matInvCertRelaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/deleteMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String matInvCertRelaJson = matInvCertRelaService.deleteMatInvCertRela(mapVo);
			
	  return JSONObject.parseObject(matInvCertRelaJson);
	}		
	
	
	/**
	 * @Description 
	 * 查询数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/queryMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matInvCertRela = matInvCertRelaService.queryMatInvCertRela(getPage(mapVo));

		return JSONObject.parseObject(matInvCertRela);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 

	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/matInvCertRelaImportPage", method = RequestMethod.GET)
	public String matInvCertRelaImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertrela/matInvCertRelaImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 

	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/cert/invcertrela/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate",".xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 

	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/cert/invcertrela/readMatInvCertRelaFiles",method = RequestMethod.POST)  
    public String readMatInvCertRelaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatInvCertRela> list_err = new ArrayList<MatInvCertRela>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatInvCertRela matInvCertRela = new MatInvCertRela();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matInvCertRela.setCert_id(Long.valueOf(temp[3]));
		    		mapVo.put("cert_id", temp[3]);
					
					} else {
						
						err_sb.append("证件ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matInvCertRela.setInv_id(Long.valueOf(temp[4]));
		    		mapVo.put("inv_id", temp[4]);
					
					} else {
						
						err_sb.append("材料ID为空  ");
						
					}
					 
					
				MatInvCertRela data_exc_extis = matInvCertRelaService.queryMatInvCertRelaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matInvCertRela.setError_type(err_sb.toString());
					
					list_err.add(matInvCertRela);
					
				} else {
			  
					String dataJson = matInvCertRelaService.addMatInvCertRela(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCertRela data_exc = new MatInvCertRela();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 

	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/addBatchMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatInvCertRela(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatInvCertRela> list_err = new ArrayList<MatInvCertRela>();
		
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
			
			MatInvCertRela matInvCertRela = new MatInvCertRela();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("cert_id"))) {
						
					matInvCertRela.setCert_id(Long.valueOf((String)jsonObj.get("cert_id")));
		    		mapVo.put("cert_id", jsonObj.get("cert_id"));
		    		} else {
						
						err_sb.append("证件ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					matInvCertRela.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("材料ID为空  ");
						
					}
					
					
				MatInvCertRela data_exc_extis = matInvCertRelaService.queryMatInvCertRelaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matInvCertRela.setError_type(err_sb.toString());
					
					list_err.add(matInvCertRela);
					
				} else {
			  
					String dataJson = matInvCertRelaService.addMatInvCertRela(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCertRela data_exc = new MatInvCertRela();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	
	//材料列表（全部）
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/queryMatInvCertList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvCertList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hrpMatSelect = matStoreInvService.queryMatInvAllList(getPage(mapVo));
		return hrpMatSelect;
	}
	
	//删除
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertrela/deleteMatInvCertRelaAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertRelaAdd(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("inv_id", ids[0]);
			mapVo.put("cert_id", ids[1]);
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matInvCertRelaService.deleteBatchCertInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	}
}

