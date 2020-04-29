/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedStoreInv;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreInvServiceImpl;
/**
 *  
 * @Description:
 * 08110 仓库材料信息
 * @Table:
 * MED_STORE_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStoreInvController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStoreInvController.class);
	
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
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medStoreInvMainPage", method = RequestMethod.GET)
	public String medStoreInvMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08035", MyConfig.getSysPara("08035"));

		return "hrp/med/info/basic/storeinv/medstoreinv/medStoreInvMain";

	}
	/**
	 * 库存材料维护页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medStoreInvPage", method = RequestMethod.GET)
	public String medStoreInvPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeinv/medstoreinv/medStoreInv";

	}
	/**
	 * 添加材料页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medAddInvPage", method = RequestMethod.GET)
	public String medAddInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("p08035", MyConfig.getSysPara("08035"));
		
		return "hrp/med/info/basic/storeinv/medstoreinv/medAddInv";

	}
	
	//材料列表（全部）
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/queryMedInvAllList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvAllList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
	
	/**
	 * 材料是否存在于仓库中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/existsMedInvInStore", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedInvInStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medStoreInvService.existsMedInvInStore(mapVo);
		return medMsg;
	}
	/**
	 * 保存材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addMedStoreInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medInvCertJson = null ;
		
		try {
			
			medInvCertJson = medStoreInvService.addMedStoreInvCert(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medInvCertJson = e.getMessage();
		}
			
		return JSONObject.parseObject(medInvCertJson);
		
	}
	/**
	 * 选择材料页面 跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medInvPage", method = RequestMethod.GET)
	public String medInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("p08035", MyConfig.getSysPara("08035"));
		
		return "hrp/med/info/basic/storeinv/medstoreinv/medInv";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medStoreInvAddPage", method = RequestMethod.GET)
	public String medStoreInvAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeinv/medstoreinv/medStoreInvAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String medStoreInvJson = null ;
		
		try {
			
			medStoreInvJson = medStoreInvService.addBatchMedStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medStoreInvJson);
		
	}
	/**
	 * 添加数据 08110 仓库材料定义
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addMedStoreInvNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStoreInvNew(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String medStoreInvJson =null ;
		
		try {
			
			if(listVo.size()>0){
				
				medStoreInvJson = medStoreInvService.deleteBatchMedStoreInv(listVo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}
		

		return JSONObject.parseObject(medStoreInvJson);
		
	}
	/**
	 * 全部保存
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addMedStoreInvAll", method = RequestMethod.POST)
	@ResponseBody
	public String addMedStoreInvAll(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		
			Map<String, Object> mapVo=new HashMap<String, Object>();

				//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code",SessionManager.getCopyCode())   ;
			mapVo.put("store_id", paramVo);

			mapVo.put("oper", SessionManager.getUserId());
			mapVo.put("oper_date", new Date());
			
			String medStoreInvJson = null;
			try {
				
				medStoreInvJson = medStoreInvService.addMedStoreInvAll(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				medStoreInvJson = e.getMessage();
				
			}
	    	return medStoreInvJson;
	   
	}
/**
	 * @Description 
	 * 更新跳转页面 08110 仓库材料信息   编辑货位 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medStoreInvUpdatePage", method = RequestMethod.GET)
	public String medStoreInvUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MedStoreInv medStoreInv   = medStoreInvService.queryMedStoreInvByCode(mapVo);
		mode.addAttribute("group_id", medStoreInv.getGroup_id());
		mode.addAttribute("hos_id", medStoreInv.getHos_id());
		mode.addAttribute("copy_code", medStoreInv.getCopy_code());
		mode.addAttribute("store_id", medStoreInv.getStore_id()); 
		mode.addAttribute("inv_id", medStoreInv.getInv_id());
		mode.addAttribute("low_limit", medStoreInv.getLow_limit());
		mode.addAttribute("high_limit", medStoreInv.getHigh_limit());
		mode.addAttribute("location_id", medStoreInv.getLocation_id());
		mode.addAttribute("location_code", medStoreInv.getLocation_code());
		
		mode.addAttribute("p08035", MyConfig.getSysPara("08035"));
		
		return "hrp/med/info/basic/storeinv/medstoreinv/medStoreInvUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/updateMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medStoreInvJson = null;
		
		try {
			
			medStoreInvJson = medStoreInvService.updateMedStoreInv(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medStoreInvJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08110 仓库材料信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addOrUpdateMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medStoreInvJson ="";
		
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
			
			medStoreInvJson = medStoreInvService.addOrUpdateMedStoreInv(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			medStoreInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medStoreInvJson);
	}
	
	
	/**
	 * @Description 
	 * 仓库材料定义--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/deleteMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medStoreInvJson = null;
		
		try {
			
			medStoreInvJson = medStoreInvService.deleteBatchMedStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}
    	return JSONObject.parseObject(medStoreInvJson);
	}
	
	/**
	 * 清除限额
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/deleteLimit", method = RequestMethod.POST)
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
		
		String medStoreInvJson = null;
		
		try {
			
			medStoreInvJson = medStoreInvService.updateBatchMedStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}
    	
    	return JSONObject.parseObject(medStoreInvJson);
	}
	
	/**
	 * 根据条件查询 （联合） 药品材料记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/queryMedStoreInvNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreInvNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String medStoreInv = medStoreInvService.queryMedStoreInvNew(getPage(mapVo));

		return JSONObject.parseObject(medStoreInv);
		
	}
	
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/queryMedInv", method = RequestMethod.POST)
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
		
		String medStoreInv = medStoreInvService.queryMedInv(getPage(mapVo));

		return JSONObject.parseObject(medStoreInv);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 08110 仓库材料信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/medStoreInvImportPage", method = RequestMethod.GET)
	public String medStoreInvImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/storeinv/medstoreinv/medStoreInvImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08110 仓库材料信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/storeinv/medstoreinv/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08110 仓库材料信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08110 仓库材料信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/storeinv/medstoreinv/readMedStoreInvFiles",method = RequestMethod.POST)  
    public String readMedStoreInvFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedStoreInv> list_err = new ArrayList<MedStoreInv>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedStoreInv medStoreInv = new MedStoreInv();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medStoreInv.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medStoreInv.setInv_id(Long.valueOf(temp[4]));
		    		mapVo.put("inv_id", temp[4]);
					
					} else {
						
						err_sb.append("药品材料ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medStoreInv.setLow_limit(Double.valueOf(temp[5]));
		    		mapVo.put("low_limit", temp[5]);
					
					} else {
						
						err_sb.append("最低库存为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medStoreInv.setStock_secu(Double.valueOf(temp[6]));
		    		mapVo.put("stock_secu", temp[6]);
					
					} else {
						
						err_sb.append("库存基数为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medStoreInv.setHigh_limit(Double.valueOf(temp[7]));
		    		mapVo.put("high_limit", temp[7]);
					
					} else {
						
						err_sb.append("最高库存为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medStoreInv.setIs_auto_supply(Integer.valueOf(temp[8]));
		    		mapVo.put("is_auto_supply", temp[8]);
					
					} else {
						
						err_sb.append("是否自动补货为空  ");
						
					}
					 
					
				MedStoreInv data_exc_extis = medStoreInvService.queryMedStoreInvByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreInv.setError_type(err_sb.toString());
					
					list_err.add(medStoreInv);
					
				} else {
			  
					String dataJson = medStoreInvService.addMedStoreInv(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreInv data_exc = new MedStoreInv();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08110 仓库材料信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addBatchMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedStoreInv(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedStoreInv> list_err = new ArrayList<MedStoreInv>();
		
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
					
					MedStoreInv medStoreInv = new MedStoreInv();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					medStoreInv.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					medStoreInv.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("药品材料ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("low_limit"))) {
						
					medStoreInv.setLow_limit(Double.valueOf((String)jsonObj.get("low_limit")));
		    		mapVo.put("low_limit", jsonObj.get("low_limit"));
		    		} else {
						
						err_sb.append("最低库存为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_secu"))) {
						
					medStoreInv.setStock_secu(Double.valueOf((String)jsonObj.get("stock_secu")));
		    		mapVo.put("stock_secu", jsonObj.get("stock_secu"));
		    		} else {
						
						err_sb.append("库存基数为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("high_limit"))) {
						
					medStoreInv.setHigh_limit(Double.valueOf((String)jsonObj.get("high_limit")));
		    		mapVo.put("high_limit", jsonObj.get("high_limit"));
		    		} else {
						
						err_sb.append("最高库存为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_auto_supply"))) {
						
					medStoreInv.setIs_auto_supply(Integer.valueOf((String)jsonObj.get("is_auto_supply")));
		    		mapVo.put("is_auto_supply", jsonObj.get("is_auto_supply"));
		    		} else {
						
						err_sb.append("是否自动补货为空  ");
						
					}
					
					
				MedStoreInv data_exc_extis = medStoreInvService.queryMedStoreInvByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medStoreInv.setError_type(err_sb.toString());
					
					list_err.add(medStoreInv);
					
				} else {
			  
					String dataJson = medStoreInvService.addMedStoreInv(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedStoreInv data_exc = new MedStoreInv();
			
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
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/querySafeMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySafeMedStoreInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());       
		}
		
		String medStoreInv = medStoreInvService.querySafeMedStoreInv(getPage(mapVo));
		return JSONObject.parseObject(medStoreInv);
		
	}
	
	/**
	 * @Description 
	 * 库存安全设置--保存
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/addSafeMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSafeMedStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("low_limit", ids[8]);
			mapVo.put("secu_limit", ids[9]);
			mapVo.put("high_limit", ids[10]);
			mapVo.put("pack_amount", ids[11]);
			mapVo.put("warn_amount", ids[12]);
			mapVo.put("period", ids[13]);
			
			mapVo.put("oper", user_id);
			mapVo.put("oper_date", date);
			
			listVo.add(mapVo);
		}
	  
		String medVenCertDetailJson = medStoreInvService.addSafeMedStoreInv(listVo);
		
		return JSONObject.parseObject(medVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 安全库存设置--删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/deleteSafeMedStoreInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSafeMedStoreInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			listVo.add(mapVo);
	    }
		
		String medStoreInvJson = null;
		try {
			
			medStoreInvJson = medStoreInvService.deleteSafeMedStoreInv(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			medStoreInvJson = e.getMessage();
		}
    	return JSONObject.parseObject(medStoreInvJson);
	}
	
	/**
	 * @Description 
	 * 查询材料申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/queryMedInvApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvApplyStore(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

			if(mapVo.get("group_id") == null){
				
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){	
				
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){		
				
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			String medInvApplyStoreJson = medStoreInvService.queryMedInvApplyStore(mapVo);
			
			return JSONObject.parseObject(medInvApplyStoreJson);
	}
	
	/**
	 * @Description 
	 * 设置申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/setApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setApplyStore(@RequestParam(value="ParamVo") String[] paramVo, Model mode) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
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
			
			mapVo.put("is_apply", 1);
			
			mapVo.put("store_id", s.split("@")[0]);
			
			mapVo.put("inv_id", s.split("@")[1]);
			
			mapVo.put("oper", user_id);
			mapVo.put("oper_date", date);
			
			list.add(mapVo);
		}
		
		String medInvApplyStoreJson = null;
		try {
			
			medInvApplyStoreJson = medStoreInvService.updateMedInvApplyStore(list);
			
		} catch (Exception e) {

			medInvApplyStoreJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medInvApplyStoreJson);
	}
	
	/**
	 * @Description 
	 * 取消设置申领库房
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/storeinv/medstoreinv/cancelSetApplyStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelSetApplyStore(@RequestParam(value="ParamVo") String[] paramVo, Model mode) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String user_id = SessionManager.getUserId();
		
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
		}
		
		String medInvApplyStoreJson = null;
		try {
			
			medInvApplyStoreJson = medStoreInvService.updateCancelMedInvApplyStore(list);
			
		} catch (Exception e) {

			medInvApplyStoreJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medInvApplyStoreJson);
	}
}

