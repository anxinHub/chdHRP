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
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvImpowerRelaServiceImpl;
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
public class MatInvImpowerRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvImpowerRelaController.class);
	
	//引入Service服务
	@Resource(name = "matInvImpowerRelaService")
	private final MatInvImpowerRelaServiceImpl matInvImpowerRelaService = null;
	
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
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaMainPage", method = RequestMethod.GET)
	public String matInvImpowerRelaMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaAddPage", method = RequestMethod.GET)
	public String matInvImpowerRelaAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 

	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/addMatInvImpowerRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvImpowerRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("impower_id", ids[5].split(" ")[0]);
			mapVo.put("impower_code", ids[5].split(" ")[1]);
			
			listVo.add(mapVo);
			
			delMap.put("group_id", ids[0]);
			delMap.put("hos_id", ids[1])   ;
			delMap.put("copy_code", ids[2])   ;
			delMap.put("impower_id", ids[5].split(" ")[0]);
			delList.add(delMap);
		}
		
		String matInvImpowerRelaJson = null;
		if(delList.size()>0){
			matInvImpowerRelaJson = matInvImpowerRelaService.deleteBatchMatInvImpowerRela(delList);
		}
		if(listVo.size()>0){
			matInvImpowerRelaJson = matInvImpowerRelaService.addBatchMatInvImpowerRela(listVo);
		}
		
		return JSONObject.parseObject(matInvImpowerRelaJson);
		
	}
	
	/**
	 * @Description 
	 * 证件信息管理  添加材料保存
	 * @param  paramVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/addMatInvImpowerRelaAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvImpowerRelaAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			matJson = matInvImpowerRelaService.addCertInv(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/addMatInvImpowerRelaAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvImpowerRelaAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		matInvImpowerRelaService.deleteMatInvCertRela(mapVo);
		
		//查询所有 在用材料的 group_id hos_id copy_code inv_id 
		List<Map<String,Object>> list = matInvImpowerRelaService.queryInvId(mapVo);
		
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
				matInvImpowerRelaService.addBatchMatInvImpowerRela(listAdd);
				listAdd.clear();
			}

		}
		
		String 	matInvCertRelaJson = matInvImpowerRelaService.addBatchMatInvImpowerRela(listAdd);
		
		return JSONObject.parseObject(matInvCertRelaJson);
		
	}
	
	/**
	 * 材料证件信息添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaAddInvPage", method = RequestMethod.GET)
	public String matInvImpowerRelaAddInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		
		return "hrp/mat/info/basic/invimpowerrela/matInvImpowerRelaAddInv";
	}
	
	/**
	 * 材料是否存在于仓库中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/existsMatInvInImpower", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatInvInImpower(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
	 * 删除数据 

	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/deleteMatInvImpowerRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvImpowerRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/queryMatInvImpowerRela", method = RequestMethod.POST)
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
	
  
	//材料列表（全部）
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/queryMatInvImpowerList", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/mat/info/basic/invimpowerrela/deleteMatInvImpowerRelaAdd", method = RequestMethod.POST)
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

