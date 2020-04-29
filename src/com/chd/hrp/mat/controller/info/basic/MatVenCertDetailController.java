/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import org.jasypt.commons.CommonUtils;
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
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatProtocolFile;
import com.chd.hrp.mat.entity.MatVenCertDetail;
import com.chd.hrp.mat.serviceImpl.info.basic.MatVenCertDetailServiceImpl;
/**
 * 
 * @Description:
 * cert_state
1：在用
0：停用



 * @Table:
 * MAT_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatVenCertDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatVenCertDetailController.class);
	
	//引入Service服务
	@Resource(name = "matVenCertDetailService")
	private final MatVenCertDetailServiceImpl matVenCertDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailMainPage", method = RequestMethod.GET)
	public String matVenCertDetailMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailMain";

	}
	/**
	 * 编辑跳转 供应商证件明细页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailPage", method = RequestMethod.GET)
	public String matVenCertDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("group_id",mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("cert_code", mapVo.get("cert_code"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));

		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetail";

	}
	
	/**
	 *  供应商证件 审核页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailCheckPage", method = RequestMethod.GET)
	public String matVenCertDetailCheckPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailCheck";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailAddPage", method = RequestMethod.GET)
	public String matVenCertDetailAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailAdd";

	}
	
	//上传页面跳转
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/upLodePage", method = RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("sup_id") == null || "undefined".equals(mapVo.get("sup_id"))){
			mode.addAttribute("sup_id", "00");
		}else{
			mode.addAttribute("sup_id", mapVo.get("sup_id"));
		}
		mode.addAttribute("cert_code", mapVo.get("cert_code"));
		return "hrp/mat/info/basic/cert/vencertdetail/upLode";
	}

	/**
	 * @Description 
	 * 添加数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/addMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matVenCertDetailJson = matVenCertDetailService.addMatVenCertDetail(mapVo);

		return JSONObject.parseObject(matVenCertDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailUpdatePage", method = RequestMethod.GET)
	public String matVenCertDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		MatVenCertDetail matVenCertDetail = new MatVenCertDetail();
    
		matVenCertDetail = matVenCertDetailService.queryMatVenCertDetailByID(mapVo);
		
		mode.addAttribute("group_id", matVenCertDetail.getGroup_id());
		mode.addAttribute("hos_id", matVenCertDetail.getHos_id());
		mode.addAttribute("copy_code", matVenCertDetail.getCopy_code());
		mode.addAttribute("cert_id", matVenCertDetail.getCert_id());
		mode.addAttribute("cert_code", matVenCertDetail.getCert_code());
		mode.addAttribute("type_id", matVenCertDetail.getType_id());
		mode.addAttribute("type_code", matVenCertDetail.getType_code());
		mode.addAttribute("type_name", matVenCertDetail.getType_name());
		mode.addAttribute("sup_id", matVenCertDetail.getSup_id());
		mode.addAttribute("sup_code", matVenCertDetail.getSup_code());
		mode.addAttribute("sup_name", matVenCertDetail.getSup_name());
		mode.addAttribute("issuing_authority", matVenCertDetail.getIssuing_authority());
		mode.addAttribute("start_date", df.format(matVenCertDetail.getStart_date()));
		mode.addAttribute("end_date", df.format(matVenCertDetail.getEnd_date()));
		mode.addAttribute("cert_memo", matVenCertDetail.getCert_memo());
		mode.addAttribute("file_path", matVenCertDetail.getFile_path());
		mode.addAttribute("cert_state", matVenCertDetail.getCert_state());
		mode.addAttribute("validity_type", matVenCertDetail.getValidity_type());
		
		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/updateMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matVenCertDetailJson = matVenCertDetailService.updateMatVenCertDetail(mapVo);
		
		return JSONObject.parseObject(matVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 cert_state 1：在用 0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/addOrUpdateMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matVenCertDetailJson ="";
		
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
	  
		matVenCertDetailJson = matVenCertDetailService.addOrUpdateMatVenCertDetail(detailVo);
		
		}
		return JSONObject.parseObject(matVenCertDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 cert_state 1：在用  0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/deleteMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatVenCertDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String matVenCertDetailJson = matVenCertDetailService.deleteBatchMatVenCertDetail(listVo);
			
	  return JSONObject.parseObject(matVenCertDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 cert_state  1：在用  0：停用
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/queryMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatVenCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matVenCertDetail = matVenCertDetailService.queryMatVenCertDetail(getPage(mapVo));

		return JSONObject.parseObject(matVenCertDetail);
		
	}
	/**
	 * 查询 供应商证件信息明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/queryMatVenCertDetailCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatVenCertDetailCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matVenCertDetail = matVenCertDetailService.queryMatVenCertDetailCert(getPage(mapVo));

		return JSONObject.parseObject(matVenCertDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 cert_state 1：在用 0：停用
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailImportPage", method = RequestMethod.GET)
	public String matVenCertDetailImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencertdetail/matVenCertDetailImport";

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
	 @RequestMapping(value="hrp/mat/info/basic/cert/vencertdetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04605 供应商证件信息.xls");
	    
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
	@RequestMapping(value="/hrp/mat/info/basic/cert/vencertdetail/readMatVenCertDetailFiles",method = RequestMethod.POST)  
    public String readMatVenCertDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException, ParseException { 
			 
		List<MatVenCertDetail> list_err = new ArrayList<MatVenCertDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatVenCertDetail matVenCertDetail = new MatVenCertDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					matVenCertDetail.setCert_code(temp[0]);
		    		mapVo.put("cert_code", temp[0]);
					
					} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matVenCertDetail.setSup_id(Long.valueOf(temp[1]));
		    		mapVo.put("sup_id", temp[1]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matVenCertDetail.setType_id(Long.valueOf(temp[2]));
		    		mapVo.put("type_id", temp[2]);
					
					} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					if (StringTool.isNotBlank(temp[3])) {
						
					matVenCertDetail.setIssuing_authority(temp[3]);
		    		mapVo.put("issuing_authority", temp[3]);
					
					} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matVenCertDetail.setStart_date(df.parse(String.valueOf(temp[4])));
		    		mapVo.put("start_date", temp[4]);
					
					} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matVenCertDetail.setEnd_date(df.parse(String.valueOf(temp[5])));
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
				MatVenCertDetail data_exc_extis = matVenCertDetailService.queryMatVenCertDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matVenCertDetail.setError_type(err_sb.toString());
					
					list_err.add(matVenCertDetail);
					
				} else {
			  
					String dataJson = matVenCertDetailService.addMatVenCertDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatVenCertDetail data_exc = new MatVenCertDetail();
			
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
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/addBatchMatVenCertDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatVenCertDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatVenCertDetail> list_err = new ArrayList<MatVenCertDetail>();
		
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
			
			MatVenCertDetail matVenCertDetail = new MatVenCertDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("cert_code"))) {
						
					matVenCertDetail.setCert_code(String.valueOf(jsonObj.get("cert_code")));
		    		mapVo.put("cert_code", jsonObj.get("cert_code"));
		    		} else {
						
						err_sb.append("证件编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					matVenCertDetail.setType_id(Long.valueOf(String.valueOf(jsonObj.get("type_id"))));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("证件类型ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					matVenCertDetail.setSup_id(Long.valueOf(String.valueOf(jsonObj.get("sup_id"))));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("issuing_authority"))) {
						
					matVenCertDetail.setIssuing_authority(String.valueOf(jsonObj.get("issuing_authority")));
		    		mapVo.put("issuing_authority", jsonObj.get("issuing_authority"));
		    		} else {
						
						err_sb.append("发证机关为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					matVenCertDetail.setStart_date(df.parse(String.valueOf(jsonObj.get("start_date"))));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("起始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					matVenCertDetail.setEnd_date(df.parse(String.valueOf(jsonObj.get("end_date"))));
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
					
				MatVenCertDetail data_exc_extis = matVenCertDetailService.queryMatVenCertDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matVenCertDetail.setError_type(err_sb.toString());
					
					list_err.add(matVenCertDetail);
					
				} else {
			  
					String dataJson = matVenCertDetailService.addMatVenCertDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatVenCertDetail data_exc = new MatVenCertDetail();
			
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
	@RequestMapping(value="/hrp/mat/info/basic/cert/vencertdetail/upLodeFile", method = RequestMethod.POST)  
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
		
		MatVenCertDetail matVenCertDetail =  matVenCertDetailService.queryMatVenCertDetailByCode(mapVo);
		
		String basePath = "matFile/venFile/"+mapVo.get("sup_id") +"/"+mapVo.get("cert_code")+"/";
		
		String fileName = plupload.getName();// 文件原名称
		
		// 文件保存目录url
		String saveUrl = request.getContextPath() + "/" + basePath;

		String url = saveUrl + fileName;
		
		if(matVenCertDetail == null ){
			List<File> fileList = UploadUtil.upload(plupload, basePath,request, response);
			
			result = "{\"file_path\":\""+url.replaceAll("\\\\","\\\\\\\\")+"\",\"state\":\"true\"}";

		}else{
			
			String str = matVenCertDetail.getFile_path();
			if(CommonUtils.isNotEmpty(str)){
				FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+basePath+str.substring(str.lastIndexOf("/")));
			}
			
			List<File> fileList = UploadUtil.upload(plupload, basePath,request, response);
			
			result = "{\"file_path\":\""+url.replaceAll("\\\\","\\\\\\\\")+"\",\"state\":\"true\"}";	
		}
		response.getWriter().print(result);
	    return null; 
	 }
	
	/**
	 *  供应商证件审核 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/queryMatVenCertDetailCertCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatVenCertDetailCertCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matVenCertDetail = matVenCertDetailService.queryMatVenCertDetailCert(getPage(mapVo));

		return JSONObject.parseObject(matVenCertDetail);
		
	}
	
		
	/**
	 * @Description 
	 * 审核/消审/驳回  state 0：未审核 1：已审核   2：驳回
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/updateVenCertDetailState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateVenCertDetailState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("sup_id", ids[0]);
			mapVo.put("cert_code", ids[1])   ;
			mapVo.put("state", ids[2])   ;
			listVo.add(mapVo);
	      
	    }
	    
		String matVenCertDetailJson = matVenCertDetailService.updateVenCertDetailState(listVo);
			
	  return JSONObject.parseObject(matVenCertDetailJson);
			
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencertdetail/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		String basePath = "matFile/venFile/"+mapVo.get("sup_id") +"/"+mapVo.get("cert_code")+"/";
		
		String str = mapVo.get("file_path").toString();
		
		FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+basePath+str.substring(str.lastIndexOf("/")));
		
		MatVenCertDetail matVenCertDetail =  matVenCertDetailService.queryMatVenCertDetailByCode(mapVo);
		
		if(matVenCertDetail != null){
			// 清空  文件路径
			matVenCertDetailService.updateVenCertFilePath(mapVo);
		}
		
		return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
	}
}

