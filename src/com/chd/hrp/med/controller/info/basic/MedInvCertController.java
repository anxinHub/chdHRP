/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
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
import com.chd.hrp.med.entity.MedInvCert;
import com.chd.hrp.med.serviceImpl.info.basic.MedInvCertServiceImpl;
/**
 * 
 * @Description:
 * MED_INV_CERT
 * @Table:
 * MED_INV_CERT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedInvCertController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInvCertController.class);
	
	//引入Service服务
	@Resource(name = "medInvCertService")
	private final MedInvCertServiceImpl medInvCertService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertMainPage", method = RequestMethod.GET)
	public String medInvCertMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcert/medInvCertMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertAddPage", method = RequestMethod.GET)
	public String medInvCertAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcert/medInvCertAdd";

	}

	/**
	 * @Description 
	 * 添加数据 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/addMedInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String medJson;
		try {
			medJson =medInvCertService.addMedInvCert(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 复制证件信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertCopyPage", method = RequestMethod.GET)
	public String medInvCertCopyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		MedInvCert medInvCert = new MedInvCert();
	    
		medInvCert = medInvCertService.queryMedInvCertByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", medInvCert.getGroup_id());
		mode.addAttribute("hos_id", medInvCert.getHos_id());
		mode.addAttribute("copy_code", medInvCert.getCopy_code());
		mode.addAttribute("cert_id", medInvCert.getCert_id());
		mode.addAttribute("cert_code", medInvCert.getCert_code());
		mode.addAttribute("cert_inv_name", medInvCert.getCert_inv_name());
		mode.addAttribute("type_id", medInvCert.getType_id());
		mode.addAttribute("type_code", medInvCert.getType_code());
		mode.addAttribute("type_name", medInvCert.getType_name());
		mode.addAttribute("fac_id", medInvCert.getFac_id());
		mode.addAttribute("fac_code", medInvCert.getFac_code());
		mode.addAttribute("fac_name", medInvCert.getFac_name());
		
		if(!"".equals(medInvCert.getCert_date()) && medInvCert.getCert_date() != null ){
			mode.addAttribute("cert_date", df.format(medInvCert.getCert_date()));
		}else{
			mode.addAttribute("cert_date", medInvCert.getCert_date());
		}
		
		if(!"".equals(medInvCert.getStart_date()) && medInvCert.getStart_date() != null ){
			mode.addAttribute("start_date", df.format(medInvCert.getStart_date()));
		}else{
			mode.addAttribute("start_date", medInvCert.getStart_date());
		}
		
		if(!"".equals(medInvCert.getEnd_date()) && medInvCert.getEnd_date() != null ){
			mode.addAttribute("end_date", df.format(medInvCert.getEnd_date()));
		}else{
			mode.addAttribute("end_date", medInvCert.getEnd_date());
		}
		
		mode.addAttribute("issuing_authority", medInvCert.getIssuing_authority());
		mode.addAttribute("link_person", medInvCert.getLink_person());
		mode.addAttribute("link_tel", medInvCert.getLink_tel());
		mode.addAttribute("link_mobile", medInvCert.getLink_mobile());
		mode.addAttribute("cert_memo", medInvCert.getCert_memo());
		mode.addAttribute("file_path", medInvCert.getFile_path());
		mode.addAttribute("file_address", medInvCert.getFile_address());
		mode.addAttribute("cert_state", medInvCert.getCert_state());

		return "hrp/med/info/basic/cert/invcert/medInvCertCopy";
	}
	
	// 获取内置数据
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedCertInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedCertInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String hrpMedSelect = medInvCertService.queryMedCertInvList(getPage(mapVo));
		return hrpMedSelect;
	}
		
/**
	 * @Description 
	 * 更新跳转页面 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertUpdatePage", method = RequestMethod.GET)
	public String medInvCertUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MedInvCert medInvCert = new MedInvCert();
    
		medInvCert = medInvCertService.queryMedInvCertByCodeNew(mapVo);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("group_id", medInvCert.getGroup_id());
		mode.addAttribute("hos_id", medInvCert.getHos_id());
		mode.addAttribute("copy_code", medInvCert.getCopy_code());
		mode.addAttribute("cert_id", medInvCert.getCert_id());
		mode.addAttribute("cert_code", medInvCert.getCert_code());
		mode.addAttribute("cert_inv_name", medInvCert.getCert_inv_name());
		mode.addAttribute("type_id", medInvCert.getType_id());
		mode.addAttribute("type_code", medInvCert.getType_code());
		mode.addAttribute("type_name", medInvCert.getType_name());
		mode.addAttribute("fac_id", medInvCert.getFac_id());
		mode.addAttribute("fac_code", medInvCert.getFac_code());
		mode.addAttribute("sup_id", medInvCert.getSup_id());
		mode.addAttribute("sup_code", medInvCert.getSup_code());
		mode.addAttribute("sup_name", medInvCert.getSup_name());
		
		if(!"".equals(medInvCert.getCert_date()) && medInvCert.getCert_date() != null ){
			mode.addAttribute("cert_date", df.format(medInvCert.getCert_date()));
		}else{
			mode.addAttribute("cert_date", medInvCert.getCert_date());
		}
		
		if(!"".equals(medInvCert.getStart_date()) && medInvCert.getStart_date() != null ){
			mode.addAttribute("start_date", df.format(medInvCert.getStart_date()));
		}else{
			mode.addAttribute("start_date", medInvCert.getStart_date());
		}
		
		if(!"".equals(medInvCert.getEnd_date()) && medInvCert.getEnd_date() != null ){
			mode.addAttribute("end_date", df.format(medInvCert.getEnd_date()));
		}else{
			mode.addAttribute("end_date", medInvCert.getEnd_date());
		}
		
		mode.addAttribute("issuing_authority", medInvCert.getIssuing_authority());
		mode.addAttribute("link_person", medInvCert.getLink_person());
		mode.addAttribute("link_tel", medInvCert.getLink_tel());
		mode.addAttribute("link_mobile", medInvCert.getLink_mobile());
		mode.addAttribute("cert_memo", medInvCert.getCert_memo());
		mode.addAttribute("file_path", medInvCert.getFile_path());
		mode.addAttribute("file_address", medInvCert.getFile_address());
		mode.addAttribute("cert_state", medInvCert.getCert_state());
		
		return "hrp/med/info/basic/cert/invcert/medInvCertUpdate";
	}
	
	/**
	 * 获取对应关系明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedInvCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = medInvCertService.queryMedInvCertDetail(getPage(mapVo));
		
		return hrpMedSelect;
	}
	
	/**
	 * 获取证件供应商对应关系 数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedCerSup", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedCerSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String certSup = medInvCertService.queryMedCerSup(getPage(mapVo));
		
		return certSup;
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/auditMedInvCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medInvCertService.auditMedInvCertBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/unAuditMedInvCertBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medInvCertService.unAuditMedInvCertBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	
	/**
	 * @Description 
	 * 删除数据 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/deleteMedInvCertRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvCertRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
		String medJson;
		try {
			medJson =medInvCertService.deleteMedInvCertRela(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);	
			
	}
	
	/**
	 * @Description 
	 * 更新数据 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/updateMedInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			medJson =medInvCertService.updateMedInvCert(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);	
		
	}
	
	/**
	 * @Description 
	 * 删除数据 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/deleteMedInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvCert(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				int isUse = medInvCertService.queryDelDate(mapVo);
				if(isUse >0){
					useStr += mapVo.get("cert_code") +",";
				}
	      listVo.add(mapVo);
	      
	    }
		if(useStr !=null && !"".equals(useStr)){
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + useStr.substring(0, useStr.length() - 1) + "】已被使用,不允许删除。\",\"state\":\"false\"}");
		}
		String medJson;
		try {
			medJson =medInvCertService.deleteBatchMedInvCert(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 MED_INV_CERT
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedInvCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medInvCert = medInvCertService.queryMedInvCert(getPage(mapVo));

		return JSONObject.parseObject(medInvCert);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 MED_INV_CERT
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertImportPage", method = RequestMethod.GET)
	public String medInvCertImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcert/medInvCertImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 MED_INV_CERT
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/cert/invcert/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08601 药品证件信息.xls");
	    
	    return null; 
	 }
	
	 /**
	 * @Description 
	 * 导入数据 MED_INV_CERT
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 * @throws ParseException 
	*/
	@RequestMapping(value="/hrp/med/info/basic/cert/invcert/importMedInvCert",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importMedInvCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=medInvCertService.importMedInvCert(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	 
	 
   /**
	 * @Description 
	 * 批量添加数据 MED_INV_CERT
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/addBatchMedInvCert", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInvCert(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedInvCert> list_err = new ArrayList<MedInvCert>();
		
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
			
			MedInvCert medInvCert = new MedInvCert();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("cert_code"))) {
						
					medInvCert.setCert_code(String.valueOf(jsonObj.get("cert_code")));
		    		mapVo.put("cert_code", jsonObj.get("cert_code"));
		    		} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					medInvCert.setType_id(Long.valueOf(String.valueOf(jsonObj.get("type_id"))));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {
						
					medInvCert.setFac_id(Long.valueOf(String.valueOf(jsonObj.get("fac_id"))));
		    		mapVo.put("fac_id", jsonObj.get("fac_id"));
		    		} else {
						
						err_sb.append("生产厂商为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					medInvCert.setStart_date(df.parse(String.valueOf(jsonObj.get("start_date"))));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					medInvCert.setEnd_date(df.parse(String.valueOf(jsonObj.get("end_date"))));
		    		mapVo.put("end_date", jsonObj.get("end_date"));
		    		} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("cert_date"))) {
						
					medInvCert.setCert_date(df.parse(String.valueOf(jsonObj.get("cert_date"))));
		    		mapVo.put("cert_date", jsonObj.get("cert_date"));
		    		} else {
						
						err_sb.append("发证日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issuing_authority"))) {
						
					medInvCert.setIssuing_authority(String.valueOf(jsonObj.get("issuing_authority")));
		    		mapVo.put("issuing_authority", jsonObj.get("issuing_authority"));
		    		} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					
				List<MedInvCert> list_extis = medInvCertService.queryMedInvCertByID(mapVo);
				
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
					
					medInvCert.setError_type(err_sb.toString());
					
					list_err.add(medInvCert);
					
				} else {
			  
					String dataJson = medInvCertService.addMedInvCert(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedInvCert data_exc = new MedInvCert();
			
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
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/medInvCertChoiceInvPage", method = RequestMethod.GET)
	public String medInvCertChoiceInvPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/invcert/medInvCertChoiceInv";

	}
	
	// 材料选择页面材料查询
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedCertInvListObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedCertInvListObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String medCertInvList = medInvCertService.queryMedCertInvList(getPage(mapVo));
		
		return JSONObject.parseObject(medCertInvList);
	}
	
	// 查询 选择的材料
	@RequestMapping(value = "/hrp/med/info/basic/cert/invcert/queryMedCertInvChoiceInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedCertInvChoiceInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String invDataList = medInvCertService.queryMedCertInvChoiceInvList(mapVo);
		
		return JSONObject.parseObject(invDataList);
	}
}

