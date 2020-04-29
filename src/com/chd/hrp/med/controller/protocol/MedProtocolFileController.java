/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.protocol;
import java.io.IOException;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedProtocolFile;
import com.chd.hrp.med.service.protocol.MedProtocolFileService;
/**
 * 
 * @Description:
 * 协议文档信息
 * @Table:
 * MED_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedProtocolFileController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedProtocolFileController.class);
	
	//引入Service服务
	@Resource(name = "medProtocolFileService")
	private final MedProtocolFileService medProtocolFileService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/MedProtocolFileMainPage", method = RequestMethod.GET)
	public String MedProtocolFileMainPage(Model mode) throws Exception {

		return "hrp/med/MedProtocolFile/MedProtocolFileMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/MedProtocolFileAddPage", method = RequestMethod.GET)
	public String MedProtocolFileAddPage(Model mode) throws Exception {

		return "hrp/med/MedProtocolFile/MedProtocolFileAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/addMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		String MedProtocolFileJson = medProtocolFileService.addMedProtocolFile(mapVo);

		return JSONObject.parseObject(MedProtocolFileJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/MedProtocolFileUpdatePage", method = RequestMethod.GET)
	public String MedProtocolFileUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		MedProtocolFile MedProtocolFile = new MedProtocolFile();
    
		MedProtocolFile = medProtocolFileService.queryMedProtocolFileByCode(mapVo);
		
		mode.addAttribute("group_id", MedProtocolFile.getGroup_id());
		mode.addAttribute("hos_id", MedProtocolFile.getHos_id());
		mode.addAttribute("copy_code", MedProtocolFile.getCopy_code());
		mode.addAttribute("protocol_id", MedProtocolFile.getProtocol_id());
		
		return "hrp/med/MedProtocolFile/MedProtocolFileUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/updateMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String MedProtocolFileJson = medProtocolFileService.updateMedProtocolFile(mapVo);
		
		return JSONObject.parseObject(MedProtocolFileJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/addOrUpdateMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String MedProtocolFileJson ="";
		
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
	  
		MedProtocolFileJson = medProtocolFileService.addOrUpdateMedProtocolFile(detailVo);
		
		}
		return JSONObject.parseObject(MedProtocolFileJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/deleteMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedProtocolFile(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("protocol_id", ids[3])   ;
				mapVo.put("detail_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String MedProtocolFileJson = medProtocolFileService.deleteBatchMedProtocolFile(listVo);
			
	  return JSONObject.parseObject(MedProtocolFileJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08503 付款协议明细表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/queryMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String MedProtocolFile = medProtocolFileService.queryMedProtocolFile(getPage(mapVo));

		return JSONObject.parseObject(MedProtocolFile);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08503 付款协议明细表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/MedProtocolFileImportPage", method = RequestMethod.GET)
	public String MedProtocolFileImportPage(Model mode) throws Exception {

		return "hrp/med/MedProtocolFile/MedProtocolFileImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08503 付款协议明细表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/MedProtocolFile/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08503 付款协议明细表.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08503 付款协议明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/MedProtocolFile/readMedProtocolFileFiles",method = RequestMethod.POST)  
    public String readMedProtocolFileFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedProtocolFile> list_err = new ArrayList<MedProtocolFile>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedProtocolFile MedProtocolFile = new MedProtocolFile();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					MedProtocolFile.setProtocol_id(Long.valueOf(temp[3]));
		    		mapVo.put("protocol_id", temp[3]);
					
					} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					//MedProtocolFile.setDetail_id(Long.valueOf(temp[4]));
		    		mapVo.put("detail_id", temp[4]);
					
					} else {
						
						err_sb.append("协议明细ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					//MedProtocolFile.setInv_no(Long.valueOf(temp[5]));
		    		mapVo.put("inv_no", temp[5]);
					
					} else {
						
						err_sb.append("物资药品变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					//MedProtocolFile.setInv_id(Long.valueOf(temp[6]));
		    		mapVo.put("inv_id", temp[6]);
					
					} else {
						
						err_sb.append("物资药品ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					//MedProtocolFile.setPrice(Double.valueOf(temp[7]));
		    		mapVo.put("price", temp[7]);
					
					} else {
						
						err_sb.append("单价为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					//MedProtocolFile.setNote(temp[9]);
		    		mapVo.put("note", temp[8]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					
				MedProtocolFile data_exc_extis = medProtocolFileService.queryMedProtocolFileByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					MedProtocolFile.setError_type(err_sb.toString());
					
					list_err.add(MedProtocolFile);
					
				} else {
			  
					String dataJson = medProtocolFileService.addMedProtocolFile(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedProtocolFile data_exc = new MedProtocolFile();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08503 付款协议明细表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/MedProtocolFile/addBatchMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedProtocolFile(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedProtocolFile> list_err = new ArrayList<MedProtocolFile>();
		
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
			
			MedProtocolFile MedProtocolFile = new MedProtocolFile();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_id"))) {
						
					MedProtocolFile.setProtocol_id(Long.valueOf((String)jsonObj.get("protocol_id")));
		    		mapVo.put("protocol_id", jsonObj.get("protocol_id"));
		    		} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("detail_id"))) {
						
					//MedProtocolFile.setDetail_id(Long.valueOf((String)jsonObj.get("detail_id")));
		    		mapVo.put("detail_id", jsonObj.get("detail_id"));
		    		} else {
						
						err_sb.append("协议明细ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_no"))) {
						
					//MedProtocolFile.setInv_no(Long.valueOf((String)jsonObj.get("inv_no")));
		    		mapVo.put("inv_no", jsonObj.get("inv_no"));
		    		} else {
						
						err_sb.append("物资药品变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("inv_id"))) {
						
					//MedProtocolFile.setInv_id(Long.valueOf((String)jsonObj.get("inv_id")));
		    		mapVo.put("inv_id", jsonObj.get("inv_id"));
		    		} else {
						
						err_sb.append("物资药品ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("price"))) {
						
					//MedProtocolFile.setPrice(Double.valueOf((String)jsonObj.get("price")));
		    		mapVo.put("price", jsonObj.get("price"));
		    		} else {
						
						err_sb.append("单价为空  ");
						
					}
					
				
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					//MedProtocolFile.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					
				MedProtocolFile data_exc_extis = medProtocolFileService.queryMedProtocolFileByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					MedProtocolFile.setError_type(err_sb.toString());
					
					list_err.add(MedProtocolFile);
					
				} else {
			  
					String dataJson = medProtocolFileService.addMedProtocolFile(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedProtocolFile data_exc = new MedProtocolFile();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

