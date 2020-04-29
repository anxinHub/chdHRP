/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatInvCert;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatInvCertService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvCertServiceImpl;
/**
 * 
 * @Description:
 * MAT_INV_CERT
 * @Table:
 * MAT_INV_CERT 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatInvCertController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvCertController.class);
	
	//引入Service服务
	@Resource(name = "matInvCertService")
	private final MatInvCertService matInvCertService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertMainPage", method = RequestMethod.GET)
	public String matInvCertMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcert/matInvCertMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertAddPage", method = RequestMethod.GET)
	public String matInvCertAddPage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("user_id", SessionManager.getUserId());
		mode.addAttribute("user_msg", matCommonService.getLoginUserMsg(map));

		return "hrp/mat/info/basic/cert/invcert/matInvCertAdd";

	}

	/**
	 * @Description 
	 * 添加数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/addMatInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("state", "0");
		String matJson;
		try {
			matJson =matInvCertService.addMatInvCert(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 复制证件信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertCopyPage", method = RequestMethod.GET)
	public String matInvCertCopyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		MatInvCert matInvCert = new MatInvCert();
	    
		matInvCert = matInvCertService.queryMatInvCertByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", matInvCert.getGroup_id());
		mode.addAttribute("hos_id", matInvCert.getHos_id());
		mode.addAttribute("copy_code", matInvCert.getCopy_code());
		mode.addAttribute("cert_id", matInvCert.getCert_id());
		mode.addAttribute("cert_code", matInvCert.getCert_code());
		mode.addAttribute("cert_inv_name", matInvCert.getCert_inv_name());
		mode.addAttribute("type_id", matInvCert.getType_id());
		mode.addAttribute("type_code", matInvCert.getType_code());
		mode.addAttribute("type_name", matInvCert.getType_name());
		mode.addAttribute("fac_id", matInvCert.getFac_id());
		mode.addAttribute("fac_code", matInvCert.getFac_code());
		mode.addAttribute("fac_name", matInvCert.getFac_name());
		mode.addAttribute("sup_id", matInvCert.getSup_id());
		mode.addAttribute("sup_code", matInvCert.getSup_code());
		mode.addAttribute("sup_name", matInvCert.getSup_name());
		
		if(!"".equals(matInvCert.getCert_date()) && matInvCert.getCert_date() != null ){
			mode.addAttribute("cert_date", df.format(matInvCert.getCert_date()));
		}else{
			mode.addAttribute("cert_date", matInvCert.getCert_date());
		}
		
		if(!"".equals(matInvCert.getStart_date()) && matInvCert.getStart_date() != null ){
			mode.addAttribute("start_date", df.format(matInvCert.getStart_date()));
		}else{
			mode.addAttribute("start_date", matInvCert.getStart_date());
		}
		
		if(!"".equals(matInvCert.getEnd_date()) && matInvCert.getEnd_date() != null ){
			mode.addAttribute("end_date", df.format(matInvCert.getEnd_date()));
		}else{
			mode.addAttribute("end_date", matInvCert.getEnd_date());
		}
		
		mode.addAttribute("issuing_authority", matInvCert.getIssuing_authority());
		mode.addAttribute("link_person", matInvCert.getLink_person());
		mode.addAttribute("link_tel", matInvCert.getLink_tel());
		mode.addAttribute("link_mobile", matInvCert.getLink_mobile());
		mode.addAttribute("cert_memo", matInvCert.getCert_memo());
		mode.addAttribute("file_path", matInvCert.getFile_path());
		mode.addAttribute("file_address", matInvCert.getFile_address());
		mode.addAttribute("cert_state", matInvCert.getCert_state());

		return "hrp/mat/info/basic/cert/invcert/matInvCertCopy";
	}
	
	// 获取内置数据
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatCertInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatCertInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String hrpMatSelect = matInvCertService.queryMatCertInvList(getPage(mapVo));
		return hrpMatSelect;
	}
		
/**
	 * @Description 
	 * 更新跳转页面 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertUpdatePage", method = RequestMethod.GET)
	public String matInvCertUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MatInvCert matInvCert = new MatInvCert();
    
		matInvCert = matInvCertService.queryMatInvCertByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", matInvCert.getGroup_id());
		mode.addAttribute("hos_id", matInvCert.getHos_id());
		mode.addAttribute("copy_code", matInvCert.getCopy_code());
		mode.addAttribute("cert_id", matInvCert.getCert_id());
		mode.addAttribute("cert_code", matInvCert.getCert_code());
		mode.addAttribute("cert_inv_name", matInvCert.getCert_inv_name());
		mode.addAttribute("type_id", matInvCert.getType_id());
		mode.addAttribute("type_code", matInvCert.getType_code());
		mode.addAttribute("type_name", matInvCert.getType_name());
		mode.addAttribute("fac_id", matInvCert.getFac_id());
		mode.addAttribute("fac_code", matInvCert.getFac_code());
		mode.addAttribute("fac_name", matInvCert.getFac_name());
		mode.addAttribute("sup_id", matInvCert.getSup_id());
		mode.addAttribute("sup_code", matInvCert.getSup_code());
		mode.addAttribute("sup_name", matInvCert.getSup_name());
		
		if(!"".equals(matInvCert.getCert_date()) && matInvCert.getCert_date() != null ){
			mode.addAttribute("cert_date", df.format(matInvCert.getCert_date()));
		}else{
			mode.addAttribute("cert_date", matInvCert.getCert_date());
		}
		
		if(!"".equals(matInvCert.getStart_date()) && matInvCert.getStart_date() != null ){
			mode.addAttribute("start_date", df.format(matInvCert.getStart_date()));
		}else{
			mode.addAttribute("start_date", matInvCert.getStart_date());
		}
		
		if(!"".equals(matInvCert.getEnd_date()) && matInvCert.getEnd_date() != null ){
			mode.addAttribute("end_date", df.format(matInvCert.getEnd_date()));
		}else{
			mode.addAttribute("end_date", matInvCert.getEnd_date());
		}
		
		mode.addAttribute("issuing_authority", matInvCert.getIssuing_authority());
		mode.addAttribute("link_person", matInvCert.getLink_person());
		mode.addAttribute("link_tel", matInvCert.getLink_tel());
		mode.addAttribute("link_mobile", matInvCert.getLink_mobile());
		mode.addAttribute("cert_memo", matInvCert.getCert_memo());
		mode.addAttribute("file_path", matInvCert.getFile_path());
		mode.addAttribute("file_address", matInvCert.getFile_address());
		mode.addAttribute("cert_state", matInvCert.getCert_state());
		
		return "hrp/mat/info/basic/cert/invcert/matInvCertUpdate";
	}
	
	/**
	 * 获取对应关系明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatInvCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = matInvCertService.queryMatInvCertDetail(getPage(mapVo));
		
		return hrpMatSelect;
	}
	
	/**
	 * 获取证件供应商对应关系 数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatCerSup", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatCerSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String certSup = matInvCertService.queryMatCerSup(getPage(mapVo));
		
		return certSup;
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/auditMatInvCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("cert_id", ids[3]);
			mapVo.put("state", 1);
			
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matInvCertService.auditMatInvCertBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/unAuditMatInvCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("cert_id", ids[3]);
			mapVo.put("state", 0);
			
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matInvCertService.unAuditMatInvCertBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	
	/**
	 * @Description 
	 * 删除数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/deleteMatInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String useStr = "";
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("cert_id", ids[3]);
				mapVo.put("inv_id", ids[4]);
				listVo.add(mapVo);
			}
			
		String matJson;
		try {
			matJson =matInvCertService.deleteMatInvCertRela(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);	
			
	}
	
	/**
	 * @Description 
	 * 更新数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/updateMatInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			matJson =matInvCertService.updateMatInvCert(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);	
		
	}
	
	/**
	 * @Description 
	 * 删除数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/deleteMatInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCert(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String useStr = "";
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("cert_id", ids[3]);
				mapVo.put("cert_code", ids[4]);
				int isUse = matInvCertService.queryDelDate(mapVo);
				if(isUse >0){
					useStr += mapVo.get("cert_code") +",";
				}
	      listVo.add(mapVo);
	      
	    }
		if(useStr !=null && !"".equals(useStr)){
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + useStr.substring(0, useStr.length() - 1) + "】已被使用,不允许删除。\",\"state\":\"false\"}");
		}
		String matJson;
		try {
			matJson =matInvCertService.deleteBatchMatInvCert(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
	        
			}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("queryDate", df.format(new Date()));
		String matInvCert = matInvCertService.queryMatInvCert(getPage(mapVo));

		return JSONObject.parseObject(matInvCert);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MAT_INV_CERT
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertImportPage", method = RequestMethod.GET)
	public String matInvCertImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcert/matInvCertImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MAT_INV_CERT
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/cert/invcert/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04601 材料证件信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 MAT_INV_CERT
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 * @throws ParseException 
	*/
	@RequestMapping(value="/hrp/mat/info/basic/cert/invcert/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> matInvCertImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=matInvCertService.importData(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
   /**
	 * @Description 
	 * 批量添加数据 MAT_INV_CERT
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/addBatchMatInvCert", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatInvCert(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatInvCert> list_err = new ArrayList<MatInvCert>();
		
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
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			MatInvCert matInvCert = new MatInvCert();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("cert_code"))) {
						
					matInvCert.setCert_code(String.valueOf(jsonObj.get("cert_code")));
		    		mapVo.put("cert_code", jsonObj.get("cert_code"));
		    		} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					matInvCert.setType_id(Long.valueOf(String.valueOf(jsonObj.get("type_id"))));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {
						
					matInvCert.setFac_id(Long.valueOf(String.valueOf(jsonObj.get("fac_id"))));
		    		mapVo.put("fac_id", jsonObj.get("fac_id"));
		    		} else {
						
						err_sb.append("生产厂商为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					matInvCert.setStart_date(df.parse(String.valueOf(jsonObj.get("start_date"))));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					matInvCert.setEnd_date(df.parse(String.valueOf(jsonObj.get("end_date"))));
		    		mapVo.put("end_date", jsonObj.get("end_date"));
		    		} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("cert_date"))) {
						
					matInvCert.setCert_date(df.parse(String.valueOf(jsonObj.get("cert_date"))));
		    		mapVo.put("cert_date", jsonObj.get("cert_date"));
		    		} else {
						
						err_sb.append("发证日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issuing_authority"))) {
						
					matInvCert.setIssuing_authority(String.valueOf(jsonObj.get("issuing_authority")));
		    		mapVo.put("issuing_authority", jsonObj.get("issuing_authority"));
		    		} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					
				List<MatInvCert> list_extis = matInvCertService.queryMatInvCertByID(mapVo);
				
				if (list_extis.size() > 0) {
					
					err_sb.append("证件编码已经存在！ ");
					
				}
				Date date = df.parse((df.format(new Date())));
				Date  enddate =df.parse(String.valueOf(mapVo.get("end_date")));;
				if(date.before(enddate)){
					mapVo.put("cert_state", 0);
				}else{
					mapVo.put("cert_state", 1);
				}
				mapVo.put("link_person", "");
				mapVo.put("link_tel", "");
				mapVo.put("link_mobile", "");
				mapVo.put("file_address", "");
				mapVo.put("file_path", "");
				mapVo.put("cert_memo", "");
				mapVo.put("state", "0");
				if (err_sb.toString().length() > 0) {
					
					matInvCert.setError_type(err_sb.toString());
					
					list_err.add(matInvCert);
					
				} else {
			  
					String dataJson = matInvCertService.addMatInvCert(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCert data_exc = new MatInvCert();
			
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
	 * 选择材料页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matInvCertChoiceInvPage", method = RequestMethod.GET)
	public String matInvCertChoiceInvPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcert/matInvCertChoiceInv";

	}
	
	// 材料选择页面材料查询
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatCertInvListObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatCertInvListObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String matCertInvList = matInvCertService.queryMatCertInvList(getPage(mapVo));
		
		return JSONObject.parseObject(matCertInvList);
	}
	
	// 查询 选择的材料
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/queryMatCertInvChoiceInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatCertInvChoiceInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String invDataList = matInvCertService.queryMatCertInvChoiceInvList(mapVo);
		
		return JSONObject.parseObject(invDataList);
	}
	/**
	 * 批量修改材料证件信息
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/matCertInvUpdateBatchPage", method = RequestMethod.GET)
	public String matCertInvUpdateBatchPage(@RequestParam Map<String, Object> mapVo,	Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("cert_ids", mapVo.get("cert_ids"));

		return "hrp/mat/info/basic/cert/invcert/matCertInvUpdateBatch";

	}
	
	/**
	 * 批量修改材料证件
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcert/updateMatCertInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatCertInvBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 处理日期
		if (mapVo.get("sdate") != null && !"".equals(mapVo.get("sdate"))) {
			mapVo.put("start_date", mapVo.get("sdate").toString());
		}
		if (mapVo.get("edate") != null && !"".equals(mapVo.get("edate"))) {
			mapVo.put("end_date", mapVo.get("edate").toString());
		}
		
		
		String matInvJson;
		try {
			matInvJson = matInvCertService.updateMatCertInvBatch(mapVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}
}

