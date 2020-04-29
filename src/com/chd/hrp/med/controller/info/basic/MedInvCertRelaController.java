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
import com.chd.hrp.med.entity.MedInvCertRela;
import com.chd.hrp.med.serviceImpl.info.basic.MedInvCertRelaServiceImpl;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreInvServiceImpl;
/**
 * 
 * @Description:
 * 

 * @Table:
 * MED_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedInvCertRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInvCertRelaController.class);
	
	//引入Service服务
	@Resource(name = "medInvCertRelaService")
	private final MedInvCertRelaServiceImpl medInvCertRelaService = null;
	
	//引入Service服务
	@Resource(name = "medStoreInvService")
	private final MedStoreInvServiceImpl medStoreInvService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/medInvCertRelaMainPage", method = RequestMethod.GET)
	public String medInvCertRelaMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcertrela/medInvCertRelaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/medInvCertRelaAddPage", method = RequestMethod.GET)
	public String medInvCertRelaAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcertrela/medInvCertRelaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 

	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/addMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvCertRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
		
		String medInvCertRelaJson = null;
		if(delList.size()>0){
			medInvCertRelaJson = medInvCertRelaService.deleteBatchMedInvCertRela(delList);
		}
		if(listVo.size()>0){
			medInvCertRelaJson = medInvCertRelaService.addBatchMedInvCertRela(listVo);
		}
		
		return JSONObject.parseObject(medInvCertRelaJson);
		
	}
	
	/**
	 * @Description 
	 * 证件信息管理  添加材料保存
	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/addMedInvCertRelaAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvCertRelaAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medInvCertRelaService.addCertInv(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/addMedInvCertRelaAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvCertRelaAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		medInvCertRelaService.deleteMedInvCertRela(mapVo);
		
		//查询所有 在用材料的 group_id hos_id copy_code inv_id 
		List<Map<String,Object>> list = medInvCertRelaService.queryInvId(mapVo);
		
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
				medInvCertRelaService.addBatchMedInvCertRela(listAdd);
				listAdd.clear();
			}

		}
		
		String 	medInvCertRelaJson = medInvCertRelaService.addBatchMedInvCertRela(listAdd);
		
		return JSONObject.parseObject(medInvCertRelaJson);
		
	}
	
	/**
	 * 材料证件信息添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/medInvCertRelaAddInvPage", method = RequestMethod.GET)
	public String medInvCertRelaAddInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		
		return "hrp/med/info/basic/cert/invcertrela/medInvCertRelaAddInv";
	}
	
	/**
	 * 材料是否存在于仓库中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/existsMedInvInCert", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedInvInCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medInvCertRelaService.existsMedInvInCert(mapVo);
		return medMsg;
	}
	/**
	 * @Description 
	 * 更新跳转页面 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/medInvCertRelaUpdatePage", method = RequestMethod.GET)
	public String medInvCertRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MedInvCertRela medInvCertRela = new MedInvCertRela();
    
		medInvCertRela = medInvCertRelaService.queryMedInvCertRelaByCode(mapVo);
		
		mode.addAttribute("group_id", medInvCertRela.getGroup_id());
		mode.addAttribute("hos_id", medInvCertRela.getHos_id());
		mode.addAttribute("copy_code", medInvCertRela.getCopy_code());
		mode.addAttribute("cert_id", medInvCertRela.getCert_id());
		mode.addAttribute("inv_id", medInvCertRela.getInv_id());
		
		return "hrp/med/info/basic/cert/invcertrela/medInvCertRelaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/updateMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medInvCertRelaJson = medInvCertRelaService.updateMedInvCertRela(mapVo);
		
		return JSONObject.parseObject(medInvCertRelaJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/addOrUpdateMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medInvCertRelaJson ="";
		
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
	  
		medInvCertRelaJson = medInvCertRelaService.addOrUpdateMedInvCertRela(detailVo);
		
		}
		return JSONObject.parseObject(medInvCertRelaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/deleteMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String medInvCertRelaJson = medInvCertRelaService.deleteMedInvCertRela(mapVo);
			
	  return JSONObject.parseObject(medInvCertRelaJson);
	}		
	
	
	/**
	 * @Description 
	 * 查询数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/queryMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvCertRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medInvCertRela = medInvCertRelaService.queryMedInvCertRela(getPage(mapVo));

		return JSONObject.parseObject(medInvCertRela);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 

	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/medInvCertRelaImportPage", method = RequestMethod.GET)
	public String medInvCertRelaImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcertrela/medInvCertRelaImport";

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
	 @RequestMapping(value="hrp/med/info/basic/cert/invcertrela/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate",".xls");
	    
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
	@RequestMapping(value="/hrp/med/info/basic/cert/invcertrela/readMedInvCertRelaFiles",method = RequestMethod.POST)  
    public String readMedInvCertRelaFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedInvCertRela> list_err = new ArrayList<MedInvCertRela>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedInvCertRela medInvCertRela = new MedInvCertRela();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medInvCertRela.setCert_id(Long.valueOf(temp[3]));
		    		mapVo.put("cert_id", temp[3]);
					
					} else {
						
						err_sb.append("证件ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medInvCertRela.setInv_id(Long.valueOf(temp[4]));
		    		mapVo.put("inv_id", temp[4]);
					
					} else {
						
						err_sb.append("材料ID为空  ");
						
					}
					 
					
				MedInvCertRela data_exc_extis = medInvCertRelaService.queryMedInvCertRelaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medInvCertRela.setError_type(err_sb.toString());
					
					list_err.add(medInvCertRela);
					
				} else {
			  
					String dataJson = medInvCertRelaService.addMedInvCertRela(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedInvCertRela data_exc = new MedInvCertRela();
			
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
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/addBatchMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInvCertRela(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedInvCertRela> list_err = new ArrayList<MedInvCertRela>();
		
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
			
			MedInvCertRela medInvCertRela = new MedInvCertRela();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("cert_id"))) {
						
					medInvCertRela.setCert_id(Long.valueOf((String)jsonObj.get("cert_id")));
		    		mapVo.put("cert_id", jsonObj.get("cert_id"));
		    		} else {
						
						err_sb.append("证件ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					medInvCertRela.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("材料ID为空  ");
						
					}
					
					
				MedInvCertRela data_exc_extis = medInvCertRelaService.queryMedInvCertRelaByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medInvCertRela.setError_type(err_sb.toString());
					
					list_err.add(medInvCertRela);
					
				} else {
			  
					String dataJson = medInvCertRelaService.addMedInvCertRela(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedInvCertRela data_exc = new MedInvCertRela();
			
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
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/queryMedInvCertList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvCertList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String hrpMedSelect = medStoreInvService.queryMedInvAllList(getPage(mapVo));
		return hrpMedSelect;
	}
	
	//删除
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcertrela/deleteMedInvCertRelaAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvCertRelaAdd(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		String medJson;
		try {
			medJson = medInvCertRelaService.deleteBatchCertInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	}
}

