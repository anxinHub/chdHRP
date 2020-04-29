/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedProtocolFile;
import com.chd.hrp.med.entity.MedVenCertDetail;
import com.chd.hrp.med.serviceImpl.info.basic.MedVenCertDetailServiceImpl;
/**
 * 
 * @Description:
 * cert_state
1：在用
0：停用



 * @Table:
 * MED_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedVenCertDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedVenCertDetailController.class);
	
	//引入Service服务
	@Resource(name = "medVenCertDetailService")
	private final MedVenCertDetailServiceImpl medVenCertDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/medVenCertDetailMainPage", method = RequestMethod.GET)
	public String medVenCertDetailMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/vencertdetail/medVenCertDetailMain";

	}
	/**
	 * 编辑跳转 供应商证件明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/medVenCertDetailPage", method = RequestMethod.GET)
	public String medVenCertDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("group_id",mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("cert_code", mapVo.get("cert_code"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));

		return "hrp/med/info/basic/cert/vencertdetail/medVenCertDetail";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/medVenCertDetailAddPage", method = RequestMethod.GET)
	public String medVenCertDetailAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		return "hrp/med/info/basic/cert/vencertdetail/medVenCertDetailAdd";

	}
	
	//上传页面跳转
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/upLodePage", method = RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("sup_id") == null || "undefined".equals(mapVo.get("sup_id"))){
			mode.addAttribute("sup_id", "00");
		}else{
			mode.addAttribute("sup_id", mapVo.get("sup_id"));
		}
		mode.addAttribute("cert_code", mapVo.get("cert_code"));
		return "hrp/med/info/basic/cert/vencertdetail/upLode";
	}

	/**
	 * @Description 
	 * 添加数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/addMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medVenCertDetailJson = medVenCertDetailService.addMedVenCertDetail(mapVo);

		return JSONObject.parseObject(medVenCertDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/medVenCertDetailUpdatePage", method = RequestMethod.GET)
	public String medVenCertDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("cert_code", mapVo.get("cert_code"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		MedVenCertDetail medVenCertDetail = new MedVenCertDetail();
    
		medVenCertDetail = medVenCertDetailService.queryMedVenCertDetailByID(mapVo);
		
		mode.addAttribute("group_id", medVenCertDetail.getGroup_id());
		mode.addAttribute("hos_id", medVenCertDetail.getHos_id());
		mode.addAttribute("copy_code", medVenCertDetail.getCopy_code());
		mode.addAttribute("cert_id", medVenCertDetail.getCert_id());
		mode.addAttribute("cert_code", medVenCertDetail.getCert_code());
		mode.addAttribute("type_id", medVenCertDetail.getType_id());
		mode.addAttribute("type_code", medVenCertDetail.getType_code());
		mode.addAttribute("type_name", medVenCertDetail.getType_name());
		mode.addAttribute("sup_id", medVenCertDetail.getSup_id());
		mode.addAttribute("sup_code", medVenCertDetail.getSup_code());
		mode.addAttribute("sup_name", medVenCertDetail.getSup_name());
		mode.addAttribute("issuing_authority", medVenCertDetail.getIssuing_authority());
		mode.addAttribute("start_date", df.format(medVenCertDetail.getStart_date()));
		mode.addAttribute("end_date", df.format(medVenCertDetail.getEnd_date()));
		mode.addAttribute("cert_memo", medVenCertDetail.getCert_memo());
		mode.addAttribute("file_path", medVenCertDetail.getFile_path());
		mode.addAttribute("cert_state", medVenCertDetail.getCert_state());
		mode.addAttribute("validity_type", medVenCertDetail.getValidity_type());
		
		return "hrp/med/info/basic/cert/vencertdetail/medVenCertDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/updateMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medVenCertDetailJson = medVenCertDetailService.updateMedVenCertDetail(mapVo);
		
		return JSONObject.parseObject(medVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/addOrUpdateMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medVenCertDetailJson ="";
		
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
	  
		medVenCertDetailJson = medVenCertDetailService.addOrUpdateMedVenCertDetail(detailVo);
		
		}
		return JSONObject.parseObject(medVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 cert_state 1：在用  0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/deleteMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedVenCertDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("cert_code", ids[3])   ;
				mapVo.put("sup_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medVenCertDetailJson = medVenCertDetailService.deleteBatchMedVenCertDetail(listVo);
			
	  return JSONObject.parseObject(medVenCertDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 cert_state  1：在用  0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/queryMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medVenCertDetail = medVenCertDetailService.queryMedVenCertDetail(getPage(mapVo));

		return JSONObject.parseObject(medVenCertDetail);
		
	}
	/**
	 * 查询 供应商证件信息明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/queryMedVenCertDetailCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedVenCertDetailCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medVenCertDetail = medVenCertDetailService.queryMedVenCertDetailCert(getPage(mapVo));

		return JSONObject.parseObject(medVenCertDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 cert_state 1：在用 0：停用
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/medVenCertDetailImportPage", method = RequestMethod.GET)
	public String medVenCertDetailImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/vencertdetail/medVenCertDetailImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 cert_state 1：在用 0：停用
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/cert/vencertdetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08605 供应商证件信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 cert_state 1：在用 0：停用 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 * @throws ParseException 
	*/
	@RequestMapping(value="/hrp/med/info/basic/cert/vencertdetail/readMedVenCertDetailFiles",method = RequestMethod.POST)  
    public String readMedVenCertDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException, ParseException { 
			 
		List<MedVenCertDetail> list_err = new ArrayList<MedVenCertDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedVenCertDetail medVenCertDetail = new MedVenCertDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					medVenCertDetail.setCert_code(temp[0]);
		    		mapVo.put("cert_code", temp[0]);
					
					} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					medVenCertDetail.setSup_id(Long.valueOf(temp[1]));
		    		mapVo.put("sup_id", temp[1]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					medVenCertDetail.setType_id(Long.valueOf(temp[2]));
		    		mapVo.put("type_id", temp[2]);
					
					} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					if (StringTool.isNotBlank(temp[3])) {
						
					medVenCertDetail.setIssuing_authority(temp[3]);
		    		mapVo.put("issuing_authority", temp[3]);
					
					} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medVenCertDetail.setStart_date(df.parse(String.valueOf(temp[4])));
		    		mapVo.put("start_date", temp[4]);
					
					} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medVenCertDetail.setEnd_date(df.parse(String.valueOf(temp[5])));
		    		mapVo.put("end_date", temp[5]);
					
					} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					 
					Date date = df.parse((df.format(new Date())));
					Date  enddate =df.parse(String.valueOf(mapVo.get("end_date")));;
					if(date.before(enddate)){
						mapVo.put("cert_state", 0);
					}else{
						mapVo.put("cert_state", 1);
					}
					
					mapVo.put("cert_id", null);
					mapVo.put("cert_memo", "");
					mapVo.put("file_path", "");
				MedVenCertDetail data_exc_extis = medVenCertDetailService.queryMedVenCertDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medVenCertDetail.setError_type(err_sb.toString());
					
					list_err.add(medVenCertDetail);
					
				} else {
			  
					String dataJson = medVenCertDetailService.addMedVenCertDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedVenCertDetail data_exc = new MedVenCertDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 cert_state 1：在用 0：停用 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencertdetail/addBatchMedVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedVenCertDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedVenCertDetail> list_err = new ArrayList<MedVenCertDetail>();
		
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
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			MedVenCertDetail medVenCertDetail = new MedVenCertDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("cert_code"))) {
						
					medVenCertDetail.setCert_code(String.valueOf(jsonObj.get("cert_code")));
		    		mapVo.put("cert_code", jsonObj.get("cert_code"));
		    		} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					medVenCertDetail.setType_id(Long.valueOf(String.valueOf(jsonObj.get("type_id"))));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					medVenCertDetail.setSup_id(Long.valueOf(String.valueOf(jsonObj.get("sup_id"))));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issuing_authority"))) {
						
					medVenCertDetail.setIssuing_authority(String.valueOf(jsonObj.get("issuing_authority")));
		    		mapVo.put("issuing_authority", jsonObj.get("issuing_authority"));
		    		} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					medVenCertDetail.setStart_date(df.parse(String.valueOf(jsonObj.get("start_date"))));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					medVenCertDetail.setEnd_date(df.parse(String.valueOf(jsonObj.get("end_date"))));
		    		mapVo.put("end_date", jsonObj.get("end_date"));
		    		} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					
					Date date = df.parse((df.format(new Date())));
					Date  enddate =df.parse(String.valueOf(mapVo.get("end_date")));;
					if(date.before(enddate)){
						mapVo.put("cert_state", 0);
					}else{
						mapVo.put("cert_state", 1);
					}
					
					mapVo.put("cert_id", null);
					mapVo.put("cert_memo", "");
					mapVo.put("file_path", "");
					
				MedVenCertDetail data_exc_extis = medVenCertDetailService.queryMedVenCertDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medVenCertDetail.setError_type(err_sb.toString());
					
					list_err.add(medVenCertDetail);
					
				} else {
			  
					String dataJson = medVenCertDetailService.addMedVenCertDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedVenCertDetail data_exc = new MedVenCertDetail();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	//上传文件
	@RequestMapping(value="/hrp/med/info/basic/cert/vencertdetail/upLodeFile", method = RequestMethod.POST)  
	 public String upLodeFile(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo) throws IOException, DataAccessException, ParseException { 
		String result = "";
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MedVenCertDetail medVenCertDetail =  medVenCertDetailService.queryMedVenCertDetailByCode(mapVo);
		
		if(medVenCertDetail == null ){
			List<File> fileList = UploadUtil.upload(plupload, "medFile\\venFile",request, response);
			
			result = "{\"file_path\":\""+fileList.get(0).getPath()+"\",\"state\":\"false\"}";

		}else{
			
			List<File> fileList = UploadUtil.upload(plupload, "medFile\\venFile",request, response);
			
			result = "{\"file_path\":\""+fileList.get(0).getPath()+"\",\"state\":\"true\"}";	
		}
		response.getWriter().print(result);
	    return null; 
	 }
}

