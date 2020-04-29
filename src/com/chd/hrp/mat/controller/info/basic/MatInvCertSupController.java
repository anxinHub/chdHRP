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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.entity.MatInvCertSup;
import com.chd.hrp.mat.service.info.basic.MatInvCertSupService;
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
public class MatInvCertSupController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvCertSupController.class);
	
	//引入Service服务
	@Resource(name = "matInvCertSupService")
	private final MatInvCertSupService matInvCertSupService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupMainPage", method = RequestMethod.GET)
	public String matInvCertSupMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupAddPage", method = RequestMethod.GET)
	public String matInvCertSupAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupAdd";

	}

	/**
	 * @Description 
	 * 添加数据 MAT_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/addMatInvCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCertSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		mapVo.put("state", "0");
		String matJson;
		try {
			matJson =matInvCertSupService.addMatInvCertSup(mapVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupCopyPage", method = RequestMethod.GET)
	public String matInvCertSupCopyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		MatInvCertSup matInvCert = new MatInvCertSup();
	    
		matInvCert = matInvCertSupService.queryMatInvCertSupByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", matInvCert.getGroup_id());
		mode.addAttribute("hos_id", matInvCert.getHos_id());
		mode.addAttribute("copy_code", matInvCert.getCopy_code());
		mode.addAttribute("sup_cert_id", matInvCert.getSup_cert_id());
		mode.addAttribute("sup_cert_code", matInvCert.getSup_cert_code());
		mode.addAttribute("sup_cert_inv_name", matInvCert.getSup_cert_inv_name());
		mode.addAttribute("type_id", matInvCert.getType_id());
		mode.addAttribute("type_code", matInvCert.getType_code());
		mode.addAttribute("type_name", matInvCert.getType_name());
		mode.addAttribute("fac_id", matInvCert.getFac_id());
		mode.addAttribute("fac_code", matInvCert.getFac_code());
		mode.addAttribute("fac_name", matInvCert.getFac_name());
		
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

		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupCopy";
	}
	
	// 获取内置数据
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatCertInvList", method = RequestMethod.POST)
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
			
		String hrpMatSelect = matInvCertSupService.queryMatCertInvList(getPage(mapVo));
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupUpdatePage", method = RequestMethod.GET)
	public String matInvCertSupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MatInvCertSup matInvCert = new MatInvCertSup();
    
		matInvCert = matInvCertSupService.queryMatInvCertSupByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", matInvCert.getGroup_id());
		mode.addAttribute("hos_id", matInvCert.getHos_id());
		mode.addAttribute("copy_code", matInvCert.getCopy_code());
		mode.addAttribute("sup_cert_id", matInvCert.getSup_cert_id());
		mode.addAttribute("sup_cert_code", matInvCert.getSup_cert_code());
		mode.addAttribute("sup_cert_inv_name", matInvCert.getSup_cert_inv_name());
		mode.addAttribute("type_id", matInvCert.getType_id());
		mode.addAttribute("type_code", matInvCert.getType_code());
		mode.addAttribute("type_name", matInvCert.getType_name());
		mode.addAttribute("fac_id", matInvCert.getFac_id());
		mode.addAttribute("fac_code", matInvCert.getFac_code());
		mode.addAttribute("fac_name", matInvCert.getFac_name());
		
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
		
		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupUpdate";
	}
	
	/**
	 * 获取对应关系明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatInvCertSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvCertSupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = matInvCertSupService.queryMatInvCertSupDetail(getPage(mapVo));
		
		return hrpMatSelect;
	}
	
	/**
	 * 获取证件供应商对应关系 数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatCerSup", method = RequestMethod.POST)
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
		String certSup = matInvCertSupService.queryMatCerSup(getPage(mapVo));
		
		return certSup;
	}
	
	
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/supAuditMatInvCertSupBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> supAuditMatInvCertSupBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("cert_code", ids[4]);
			mapVo.put("cert_inv_name", ids[5]);
			mapVo.put("type_id", ids[6]);
			mapVo.put("fac_id", ids[7]);
			mapVo.put("cert_date", ids[8]);
			mapVo.put("issuing_authority", ids[9]);
			mapVo.put("start_date", ids[10]);
			mapVo.put("end_date", ids[11]);
			mapVo.put("link_person", ids[12]);
			mapVo.put("link_tel", ids[13]);
			mapVo.put("link_mobile", ids[14]);
			mapVo.put("cert_memo", ids[15]);
			mapVo.put("file_path", ids[16]);
			mapVo.put("file_address", ids[17]);
			mapVo.put("cert_state", "1");
			mapVo.put("state", "0");
			
			
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matInvCertSupService.supAuditMatInvCertSupBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/auditMatInvCertSupBatch", method = RequestMethod.POST)
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
			mapVo.put("sup_cert_id", ids[3]);
			mapVo.put("state", 1);
			
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matInvCertSupService.auditMatInvCertSupBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/unAuditMatInvCertSupBatch", method = RequestMethod.POST)
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
			mapVo.put("sup_cert_id", ids[3]);
			mapVo.put("state", 0);
			
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matInvCertSupService.unAuditMatInvCertSupBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/deleteMatInvCertSupRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertSupRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String useStr = "";
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("sup_cert_id", ids[3]);
				mapVo.put("inv_id", ids[4]);
				listVo.add(mapVo);
			}
			
		String matJson;
		try {
			matJson =matInvCertSupService.deleteMatInvCertSupRela(listVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/updateMatInvCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvCertSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			matJson =matInvCertSupService.updateMatInvCertSup(mapVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/deleteMatInvCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertSup(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String useStr = "";
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("sup_cert_id", ids[3]);
				mapVo.put("sup_cert_code", ids[4]);
				int isUse = matInvCertSupService.queryDelDate(mapVo);
				if(isUse >0){
					useStr += mapVo.get("sup_cert_code") +",";
				}
	      listVo.add(mapVo);
	      
	    }
		if(useStr !=null && !"".equals(useStr)){
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + useStr.substring(0, useStr.length() - 1) + "】已被使用,不允许删除。\",\"state\":\"false\"}");
		}
		String matJson;
		try {
			matJson =matInvCertSupService.deleteBatchMatInvCertSup(listVo);
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatInvCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCertSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("queryDate", df.format(new Date()));
		String matInvCert = matInvCertSupService.queryMatInvCertSup(getPage(mapVo));

		return JSONObject.parseObject(matInvCert);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MAT_INV_CERT
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupImportPage", method = RequestMethod.GET)
	public String matInvCertSupImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupImport";

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
	 @RequestMapping(value="hrp/mat/info/basic/cert/invcertsup/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/mat/info/basic/cert/invcertsup/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> matInvCertSupImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=matInvCertSupService.importData(mapVo);
			
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/addBatchMatInvCertSup", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatInvCertSup(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatInvCertSup> list_err = new ArrayList<MatInvCertSup>();
		
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
			
			MatInvCertSup matInvCert = new MatInvCertSup();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("sup_cert_code"))) {
						
					matInvCert.setSup_cert_code(String.valueOf(jsonObj.get("sup_cert_code")));
		    		mapVo.put("sup_cert_code", jsonObj.get("sup_cert_code"));
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
					
				List<MatInvCertSup> list_extis = matInvCertSupService.queryMatInvCertSupByID(mapVo);
				
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
			  
					String dataJson = matInvCertSupService.addMatInvCertSup(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCertSup data_exc = new MatInvCertSup();
			
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/matInvCertSupChoiceInvPage", method = RequestMethod.GET)
	public String matInvCertSupChoiceInvPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcertsup/matInvCertSupChoiceInv";

	}
	
	// 材料选择页面材料查询
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatCertInvListObject", method = RequestMethod.POST)
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
				
		String matCertInvList = matInvCertSupService.queryMatCertInvList(getPage(mapVo));
		
		return JSONObject.parseObject(matCertInvList);
	}
	
	// 查询 选择的材料
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcertsup/queryMatCertInvChoiceInvList", method = RequestMethod.POST)
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
				
		String invDataList = matInvCertSupService.queryMatCertInvChoiceInvList(mapVo);
		
		return JSONObject.parseObject(invDataList);
	}
}

