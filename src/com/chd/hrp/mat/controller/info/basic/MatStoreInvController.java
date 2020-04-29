/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatStoreInv;
import com.chd.hrp.mat.service.info.basic.MatStoreInvService;
/**
 *  
 * @Description:
 * 04110 仓库材料信息
 * @Table:
 * MAT_STORE_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatStoreInvController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStoreInvController.class);
	
	//引入Service服务
	@Resource(name = "matStoreInvService")
	private final MatStoreInvService matStoreInvService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvMainPage", method = RequestMethod.GET)
	public String matStoreInvMainPage(Model mode) throws Exception {

		mode.addAttribute("p04035", MyConfig.getSysPara("04035"));

		return "hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvMain";

	}
	/**
	 * 库存材料维护页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvPage", method = RequestMethod.GET)
	public String matStoreInvPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeinv/matstoreinv/matStoreInv";

	}
	/**
	 * 添加材料页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matAddInvPage", method = RequestMethod.GET)
	public String matAddInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));

		mode.addAttribute("p04035", MyConfig.getSysPara("04035"));
		
		return "hrp/mat/info/basic/storeinv/matstoreinv/matAddInv";

	}
	
	//材料列表（全部）
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/queryMatInvAllList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvAllList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		mapVo.put("inv_state", MyConfig.getSysPara("04050"));
		String hrpMatSelect = matStoreInvService.queryMatInvAllList(getPage(mapVo));
		return hrpMatSelect;
	}
	
	/**
	 * 材料是否存在于仓库中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/existsMatInvInStore", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatInvInStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matStoreInvService.existsMatInvInStore(mapVo);
		return matMsg;
	}
	/**
	 * 保存材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addMatStoreInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvCertJson = null ;
		
		try {
			
			matInvCertJson = matStoreInvService.addMatStoreInvCert(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matInvCertJson = e.getMessage();
		}
			
		return JSONObject.parseObject(matInvCertJson);
		
	}
	/**
	 * 选择材料页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matInvPage", method = RequestMethod.GET)
	public String matInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));

		mode.addAttribute("p04035", MyConfig.getSysPara("04035"));
		
		return "hrp/mat/info/basic/storeinv/matstoreinv/matInv";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvAddPage", method = RequestMethod.GET)
	public String matStoreInvAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		//System.out.println("paramVo====="+paramVo);
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
				Map<String, Object> mapVo=new HashMap<String, Object>();
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("inv_id", ids[0]);
				mapVo.put("store_id", ids[1]) ;
				if("-1".equals(ids[2])){
					mapVo.put("location_id", "");
				}else{
					mapVo.put("location_id", ids[2]);
				}
				mapVo.put("is_apply", 0) ;
				mapVo.put("oper", user_id);
				mapVo.put("oper_date", date);
				listVo.add(mapVo);
		}
		
		String matStoreInvJson = null ;
		
		try {
			
			matStoreInvJson = matStoreInvService.addBatchMatStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matStoreInvJson);
		
	}
	/**
	 * 添加数据 04110 仓库材料定义
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addMatStoreInvNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreInvNew(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();

			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("store_id", ids[1]);
			mapVo.put("inv_id", ids[0]);
			if("-1".equals(ids[2])){
				mapVo.put("location_id", "-1");
			}else{
				mapVo.put("location_id", ids[2]);
			}
			
			mapVo.put("oper", user_id);
			mapVo.put("oper_date", date);
			
			listVo.add(mapVo);
		}
		String matStoreInvJson =null ;
		
		try {
			
			if(listVo.size()>0){
				
				matStoreInvJson = matStoreInvService.deleteBatchMatStoreInv(listVo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}
		

		return JSONObject.parseObject(matStoreInvJson);
		
	}
	/**
	 * 全部保存
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addMatStoreInvAll", method = RequestMethod.POST)
	@ResponseBody
	public String addMatStoreInvAll(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
			Map<String, Object> mapVo=new HashMap<String, Object>();

				//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code",SessionManager.getCopyCode())   ;
			mapVo.put("store_id", paramVo);

			mapVo.put("oper", SessionManager.getUserId());
			mapVo.put("oper_date", new Date());
			
			String matStoreInvJson = null;
			try {
				
				matStoreInvJson = matStoreInvService.addMatStoreInvAll(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				matStoreInvJson = e.getMessage();
				
			}
	    	return matStoreInvJson;
	   
	}
/**
	 * @Description 
	 * 更新跳转页面 04110 仓库材料信息   编辑货位 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvUpdatePage", method = RequestMethod.GET)
	public String matStoreInvUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MatStoreInv matStoreInv   = matStoreInvService.queryMatStoreInvByCode(mapVo);
		mode.addAttribute("group_id", matStoreInv.getGroup_id());
		mode.addAttribute("hos_id", matStoreInv.getHos_id());
		mode.addAttribute("copy_code", matStoreInv.getCopy_code());
		mode.addAttribute("store_id", matStoreInv.getStore_id()); 
		mode.addAttribute("inv_id", matStoreInv.getInv_id());
		mode.addAttribute("low_limit", matStoreInv.getLow_limit());
		mode.addAttribute("high_limit", matStoreInv.getHigh_limit());
		mode.addAttribute("location_id", matStoreInv.getLocation_id());
		mode.addAttribute("location_code", matStoreInv.getLocation_code());
		mode.addAttribute("inv_code", matStoreInv.getInv_code());
		mode.addAttribute("inv_name", matStoreInv.getInv_name());
		mode.addAttribute("inv_model", matStoreInv.getInv_model());
		mode.addAttribute("fac_code", matStoreInv.getFac_code());
		mode.addAttribute("fac_name", matStoreInv.getFac_name());

		mode.addAttribute("p04035", MyConfig.getSysPara("04035"));
		
		return "hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/updateMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}

		mapVo.put("oper", SessionManager.getUserId());
		mapVo.put("oper_date", new Date());
		
		String matStoreInvJson = null;
		
		try {
			
			matStoreInvJson = matStoreInvService.updateMatStoreInv(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matStoreInvJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addOrUpdateMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matStoreInvJson ="";
		
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		mapVo.put("store_id", mapVo.get("store_id"));
		mapVo.put("inv_id", mapVo.get("inv_id"));
		mapVo.put("location_id", mapVo.get("location_id"));
		
		try {
			
			matStoreInvJson = matStoreInvService.addOrUpdateMatStoreInv(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			matStoreInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matStoreInvJson);
	}
	
	
	/**
	 * @Description 
	 * 仓库材料定义--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/deleteMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();

			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("store_id", ids[3]);
			if(!"null".equals(ids[4])){
				mapVo.put("inv_id", ids[4]);
			}
			
			listVo.add(mapVo);
	    }
		
		String matStoreInvJson = null;
		
		try {
			
			matStoreInvJson = matStoreInvService.deleteBatchMatStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}
    	return JSONObject.parseObject(matStoreInvJson);
	}
	
	/**
	 * 清除限额
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/deleteLimit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteLimit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();

			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("is_auto_supply", ids[5]);
			mapVo.put("low_limit", null);
			mapVo.put("stock_secu", null);
			mapVo.put("high_limit", null);
			listVo.add(mapVo);
	    }
		
		String matStoreInvJson = null;
		
		try {
			
			matStoreInvJson = matStoreInvService.updateBatchMatStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}
    	
    	return JSONObject.parseObject(matStoreInvJson);
	}
	
	/**
	 * 根据条件查询 （联合） 物资材料记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/queryMatStoreInvNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreInvNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String matStoreInv = matStoreInvService.queryMatStoreInvNew(getPage(mapVo));

		return JSONObject.parseObject(matStoreInv);
		
	}
	
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/queryMatInv", method = RequestMethod.POST)
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
		
		mapVo.put("inv_state", MyConfig.getSysPara("04050"));
		
		String matStoreInv = matStoreInvService.queryMatInv(getPage(mapVo));

		return JSONObject.parseObject(matStoreInv);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 04110 仓库材料信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvImportPage", method = RequestMethod.GET)
	public String matStoreInvImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeinv/matstoreinv/matStoreInvImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04110 仓库材料信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/storeinv/matstoreinv/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04110 仓库材料信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04110 仓库材料信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/storeinv/matstoreinv/readMatStoreInvFiles",method = RequestMethod.POST)  
    public String readMatStoreInvFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStoreInv> list_err = new ArrayList<MatStoreInv>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStoreInv matStoreInv = new MatStoreInv();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matStoreInv.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matStoreInv.setInv_id(Long.valueOf(temp[4]));
		    		mapVo.put("inv_id", temp[4]);
					
					} else {
						
						err_sb.append("物资材料ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matStoreInv.setLow_limit(Double.valueOf(temp[5]));
		    		mapVo.put("low_limit", temp[5]);
					
					} else {
						
						err_sb.append("最低库存为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matStoreInv.setStock_secu(Double.valueOf(temp[6]));
		    		mapVo.put("stock_secu", temp[6]);
					
					} else {
						
						err_sb.append("库存基数为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matStoreInv.setHigh_limit(Double.valueOf(temp[7]));
		    		mapVo.put("high_limit", temp[7]);
					
					} else {
						
						err_sb.append("最高库存为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matStoreInv.setIs_auto_supply(Integer.valueOf(temp[8]));
		    		mapVo.put("is_auto_supply", temp[8]);
					
					} else {
						
						err_sb.append("是否自动补货为空  ");
						
					}
					 
					
				MatStoreInv data_exc_extis = matStoreInvService.queryMatStoreInvByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreInv.setError_type(err_sb.toString());
					
					list_err.add(matStoreInv);
					
				} else {
			  
					String dataJson = matStoreInvService.addMatStoreInv(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreInv data_exc = new MatStoreInv();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04110 仓库材料信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addBatchMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStoreInv(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStoreInv> list_err = new ArrayList<MatStoreInv>();
		
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
			
			mapVo.put("oper", SessionManager.getUserId());
			mapVo.put("oper_date", new Date());
		
			Iterator it = json.iterator();
		
			try {
			
				while (it.hasNext()) {
					
					StringBuffer err_sb = new StringBuffer();
					
					MatStoreInv matStoreInv = new MatStoreInv();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matStoreInv.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					matStoreInv.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("物资材料ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("low_limit"))) {
						
					matStoreInv.setLow_limit(Double.valueOf((String)jsonObj.get("low_limit")));
		    		mapVo.put("low_limit", jsonObj.get("low_limit"));
		    		} else {
						
						err_sb.append("最低库存为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_secu"))) {
						
					matStoreInv.setStock_secu(Double.valueOf((String)jsonObj.get("stock_secu")));
		    		mapVo.put("stock_secu", jsonObj.get("stock_secu"));
		    		} else {
						
						err_sb.append("库存基数为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("high_limit"))) {
						
					matStoreInv.setHigh_limit(Double.valueOf((String)jsonObj.get("high_limit")));
		    		mapVo.put("high_limit", jsonObj.get("high_limit"));
		    		} else {
						
						err_sb.append("最高库存为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_auto_supply"))) {
						
					matStoreInv.setIs_auto_supply(Integer.valueOf((String)jsonObj.get("is_auto_supply")));
		    		mapVo.put("is_auto_supply", jsonObj.get("is_auto_supply"));
		    		} else {
						
						err_sb.append("是否自动补货为空  ");
						
					}
					
					
				MatStoreInv data_exc_extis = matStoreInvService.queryMatStoreInvByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreInv.setError_type(err_sb.toString());
					
					list_err.add(matStoreInv);
					
				} else {
			  
					String dataJson = matStoreInvService.addMatStoreInv(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreInv data_exc = new MatStoreInv();
			
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
	 * 仓库安全设置 -- 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/querySafeMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySafeMatStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());       
		}
		
		String matStoreInv = matStoreInvService.querySafeMatStoreInv(getPage(mapVo));
		return JSONObject.parseObject(matStoreInv);
		
	}
	
	/**
	 * @Description 
	 * 库存安全设置--保存
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/addSafeMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSafeMatStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("package_code", ids[5]);
			mapVo.put("num_exchange", ids[6]);
			mapVo.put("period_num", ids[7]);
			mapVo.put("low_limit",StringUtils.isBlank(ids[8])?"0":ids[8]);
			mapVo.put("secu_limit",StringUtils.isBlank(ids[9])?"0":ids[9]);
			mapVo.put("high_limit",StringUtils.isBlank(ids[10])?"0":ids[10]);
			mapVo.put("pack_amount", ids[11]);
			mapVo.put("warn_amount",StringUtils.isBlank(ids[12])?"0":ids[12]);
			mapVo.put("period",ids.length<14?"0":ids[13]);
			mapVo.put("ps_period",StringUtils.isBlank(ids[14])?"0":ids[14]);
			mapVo.put("cg_period",StringUtils.isBlank(ids[15])?"0":ids[15]);
			mapVo.put("min_pur",StringUtils.isBlank(ids[16])?"0":ids[16]);
			mapVo.put("rxhl_period",StringUtils.isBlank(ids[17])?"0":ids[17]);

			mapVo.put("oper", user_id);
			mapVo.put("oper_date", date);
			listVo.add(mapVo);
		}
	  
		String matVenCertDetailJson = matStoreInvService.addSafeMatStoreInv(listVo);
		
		return JSONObject.parseObject(matVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 安全库存设置--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/deleteSafeMatStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSafeMatStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();

			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("period", "0");
			mapVo.put("period_num", "0");
			mapVo.put("high_limit", "0");
			mapVo.put("secu_limit", "0");
			mapVo.put("low_limit", "0");
			mapVo.put("package_code", "null");
			mapVo.put("num_exchange", "0");
			mapVo.put("pack_amount", "0");
			mapVo.put("warn_amount", "0");
			mapVo.put("ps_period", "0");
			mapVo.put("cg_period", "0");
			mapVo.put("min_pur", "0");
			mapVo.put("rxhl_period", "0");
			
			listVo.add(mapVo);
	    }
		
		String matStoreInvJson = null;
		try {
			
			matStoreInvJson = matStoreInvService.deleteSafeMatStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreInvJson = e.getMessage();
		}
    	return JSONObject.parseObject(matStoreInvJson);
	}
	
	/**
	 * @Description 
	 * 查询材料申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/queryMatInvApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvApplyStore(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

			if(mapVo.get("group_id") == null){
				
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){	
				
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){		
				
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			String matInvApplyStoreJson = matStoreInvService.queryMatInvApplyStore(mapVo);
			
			return JSONObject.parseObject(matInvApplyStoreJson);
	}
	
	/**
	 * @Description 
	 * 设置申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/setApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setApplyStore(@RequestParam(value="ParamVo") String[] paramVo, Model mode) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		String inv_ids="";  //用于HIS信息同步 由参数 04075 控制是否传递
		String store_id="";//用于HIS信息同步 由参数 04075 控制是否传递
		for(String s:paramVo){
			
			Map<String,Object> mapVo = new HashMap<String,Object>();

			if(mapVo.get("group_id") == null){
				
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){	
				
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){		
				
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			mapVo.put("is_apply", 1);
			
			mapVo.put("store_id", s.split("@")[0]);
			
			mapVo.put("inv_id", s.split("@")[1]);
			
			mapVo.put("oper", user_id);
			mapVo.put("oper_date", date);
			
			list.add(mapVo);
			
			//用于HIS信息同步 由参数 04075 控制是否传递
			inv_ids=inv_ids+s.split("@")[1]+",";
			store_id=s.split("@")[0];
			
		}
		
		String matInvApplyStoreJson = null;
		try {
			
			matInvApplyStoreJson = matStoreInvService.updateMatInvApplyStore(list);
			
			//用于his同步申请仓库与材料对应关系  由系统参数 04075 控制  
			/////////////////////////////////////////////////////
			matStoreInvService.wbPass(inv_ids, store_id, 1);
			/////////////////////////////////////////////////////
		
			
			
			
		} catch (Exception e) {

			matInvApplyStoreJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matInvApplyStoreJson);
	}
	
	/**
	 * @Description 
	 * 取消设置申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/cancelSetApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelSetApplyStore(@RequestParam(value="ParamVo") String[] paramVo, Model mode) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String user_id = SessionManager.getUserId();
		
		String inv_ids="";  //用于HIS信息同步 由参数 04075 控制是否传递
		String store_id="";//用于HIS信息同步 由参数 04075 控制是否传递
		
		//String date = sdf.format(new Date());
		
		Date date = new Date();
		
		for(String s:paramVo){
			
			Map<String,Object> mapVo = new HashMap<String,Object>();

			if(mapVo.get("group_id") == null){
				
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){	
				
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){		
				
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			mapVo.put("is_apply", 0);
			
			mapVo.put("store_id", s.split("@")[0]);
			
			mapVo.put("inv_id", s.split("@")[1]);
			
			mapVo.put("oper",user_id);
			
			mapVo.put("oper_date",date);  
			
			list.add(mapVo);
			
			//用于HIS信息同步 由参数 04075 控制是否传递
			inv_ids=inv_ids+s.split("@")[1]+",";
			store_id=s.split("@")[0];
			
		}
		
		String matInvApplyStoreJson = null;
		try {
			
			matInvApplyStoreJson = matStoreInvService.updateCancelMatInvApplyStore(list);
			
			//用于his同步申请仓库与材料对应关系  由系统参数 04075 控制  
			/////////////////////////////////////////////////////
			matStoreInvService.wbPass(inv_ids, store_id, 2);
			/////////////////////////////////////////////////////
			
			
		} catch (Exception e) {

			matInvApplyStoreJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matInvApplyStoreJson);
	}

	/**
	 * 安全库存批量修改页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/matSafeStoreInvUpdateBatchPage", method = RequestMethod.GET)
	public String matSafeStoreInvUpdateBatch(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeinv/matstoreinv/matSafeStoreInvUpdateBatch";
	}
	
	/**
	 * @Description 批量修改
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoreinv/updateSafeMatStoreInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSafeMatStoreInvBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matStoreInvService.updateSafeMatStoreInvBatch(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

