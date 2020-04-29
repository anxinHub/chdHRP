/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.protocol;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedProtocolFile;
import com.chd.hrp.med.entity.MedProtocolMain;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolDetailServiceImpl;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolFileServiceImpl;
import com.chd.hrp.med.serviceImpl.protocol.MedProtocolMainServiceImpl;
/**
 * 
 * @Description:
 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedProtocolMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedProtocolMainController.class);
	
	//引入Service服务
	@Resource(name = "medProtocolMainService")
	private final MedProtocolMainServiceImpl medProtocolMainService = null;
	
	@Resource(name = "medProtocolDetailService")
	private final MedProtocolDetailServiceImpl medProtocolDetailService = null;
	
	@Resource(name = "medProtocolFileService")
	private final MedProtocolFileServiceImpl medProtocolFileService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medProtocolMainMainPage", method = RequestMethod.GET)
	public String medProtocolMainMainPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolmain/medProtocolMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medProtocolMainAddPage", method = RequestMethod.GET)
	public String medProtocolMainAddPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolmain/medProtocolMainAdd";
	}
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medInvDetailPage", method = RequestMethod.GET)
	public String medInvDetailPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolmain/medInvDetail";
	}
	//上传页面跳转
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/upLodePage", method = RequestMethod.GET)
	public String upLodePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("protocol_id") == null || "undefined".equals(mapVo.get("protocol_id"))){
			mode.addAttribute("protocol_id", "00");
		}else{
			mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		}
		if(mapVo.get("file_id") == null || "undefined".equals(mapVo.get("file_id"))){
			mode.addAttribute("file_id", "00");
		}else{
			mode.addAttribute("file_id", mapVo.get("file_id"));
		}
		mode.addAttribute("data", mapVo.get("data"));
		return "hrp/med/protocol/medprotocolmain/upLode";
	}

	/**
	 * @Description 
	 * 添加数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/addMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("is_init", "0");
		String medProtocolMainJson = medProtocolMainService.addMedProtocolMain(mapVo);

		return JSONObject.parseObject(medProtocolMainJson);
		
	}
	/**
	 * 添加协议明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/addMedProtocolDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedProtocolDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("inv_id", ids[3]);
			mapVo.put("inv_no", ids[4]);
			mapVo.put("price", ids[5]);
			if(ids[6] == null || ids[6] == "" || "undefined".equals(ids[6]) ){
				mapVo.put("note", "");
			}else{
				mapVo.put("note", ids[6]);
			}
			if("0".equals(ids[7])){
				mapVo.put("detail_id",null);
				addList.add(mapVo);
			}else{
				mapVo.put("detail_id",ids[7]);
				updateList.add(mapVo);
			}
			
			if(Integer.valueOf(ids[8]) == 0){
				Long protocol_id = medProtocolMainService.qureyCurrval();
				mapVo.put("protocol_id", protocol_id);
			}else{
				mapVo.put("protocol_id", ids[8]);
			}
			
		}
		
		String medProtocolMainJson =null;
		
		if(updateList.size()>0){
			medProtocolMainJson = medProtocolDetailService.updateBatchMedProtocolDetail(updateList);
		}
		
		if(addList.size()>0){
			medProtocolMainJson = medProtocolDetailService.addBatchMedProtocolDetail(addList);
		}
		

		return JSONObject.parseObject(medProtocolMainJson);
		
	}
	
	/**
	 * 添加协议文档信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/addMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedProtocolFile(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("type_code", ids[0])   ;
			mapVo.put("file_code", ids[1])   ;
			mapVo.put("file_name", ids[2])   ;
			mapVo.put("at_local", ids[3])   ;
			mapVo.put("dept_id", ids[4])   ;
			mapVo.put("mana_emp", ids[5].split(":")[0])   ;
			if("0".equals(ids[6])){
				mapVo.put("file_id",null);
				addList.add(mapVo);
			}else{
				mapVo.put("file_id",ids[6]);
				updateList.add(mapVo);
			}
			
			if(Integer.valueOf(ids[7]) == 0){
				Long protocol_id = medProtocolMainService.qureyCurrval();
				mapVo.put("protocol_id", protocol_id);
			}else{
				mapVo.put("protocol_id", ids[7]);
			}
			mapVo.put("file_path",ids[8]) ;
			
		}
		String medProtocolMainJson =null;
		
		if(updateList.size()>0){
			medProtocolMainJson = medProtocolFileService.updateBatchMedProtocolFile(updateList);
		}
		
		if(addList.size()>0){
			medProtocolMainJson = medProtocolFileService.addBatchMedProtocolFile(addList);
		}
		
		return JSONObject.parseObject(medProtocolMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medProtocolMainUpdatePage", method = RequestMethod.GET)
	public String medProtocolMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		resultMap = medProtocolMainService.queryMedProtocolMainUnit(mapVo);
		//查询  该采购协议的协议类型的开始年月 
		Map<String,Object> preMap = medProtocolMainService.queryTypePre(resultMap);
		mode.addAttribute("year", preMap.get("start_year"));
		mode.addAttribute("month", preMap.get("start_month"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		mode.addAttribute("group_id", resultMap.get("group_id"));
		mode.addAttribute("hos_id", resultMap.get("hos_id"));
		mode.addAttribute("copy_code", resultMap.get("copy_code"));
		mode.addAttribute("protocol_id", resultMap.get("protocol_id"));
		mode.addAttribute("protocol_code", resultMap.get("protocol_code"));
		mode.addAttribute("original_no", resultMap.get("original_no"));
		mode.addAttribute("type_id", resultMap.get("type_id"));
		mode.addAttribute("type_code", resultMap.get("type_code"));
		mode.addAttribute("type_name", resultMap.get("type_name"));
		mode.addAttribute("protocol_name", resultMap.get("protocol_name"));
		mode.addAttribute("sign_date", df.format(resultMap.get("sign_date")));
		mode.addAttribute("sup_no", resultMap.get("sup_no"));
		mode.addAttribute("sup_id", resultMap.get("sup_id"));
		mode.addAttribute("sup_code", resultMap.get("sup_code"));
		mode.addAttribute("sup_name", resultMap.get("sup_name"));
		mode.addAttribute("is_bid", resultMap.get("is_bid"));
		mode.addAttribute("start_date", df.format(resultMap.get("start_date")));
		mode.addAttribute("end_date", df.format(resultMap.get("end_date")));
		mode.addAttribute("dept_no", resultMap.get("dept_no"));
		mode.addAttribute("dept_id", resultMap.get("dept_id"));
		mode.addAttribute("dept_code", resultMap.get("dept_code"));
		mode.addAttribute("dept_name", resultMap.get("dept_name"));
		mode.addAttribute("addr", resultMap.get("addr"));
		mode.addAttribute("first_man", resultMap.get("first_man"));
		mode.addAttribute("emp_no", resultMap.get("emp_no"));
		mode.addAttribute("emp_code", resultMap.get("emp_code"));
		mode.addAttribute("emp_name", resultMap.get("emp_name"));
		mode.addAttribute("second_man", resultMap.get("second_man"));
		mode.addAttribute("second_phone", resultMap.get("second_phone"));
		mode.addAttribute("contract_detail", resultMap.get("contract_detail"));
		mode.addAttribute("service_detail", resultMap.get("service_detail"));
		mode.addAttribute("create_emp", resultMap.get("create_emp"));
		mode.addAttribute("check_emp", resultMap.get("check_emp"));
		mode.addAttribute("confirm_emp", resultMap.get("confirm_emp"));
		mode.addAttribute("state", resultMap.get("state"));
		mode.addAttribute("spell", resultMap.get("spell"));
		mode.addAttribute("is_init", resultMap.get("is_init"));
		
		return "hrp/med/protocol/medprotocolmain/medProtocolMainUpdate";
	}
	/**
	 * 查看协议文档
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medProtocolFilePage", method = RequestMethod.GET)
	public String medProtocolFilePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> fileMap = new HashMap<String, Object>();
		
		fileMap = medProtocolFileService.queryMedProtocolFileByID(mapVo);
		
		mode.addAttribute("group_id", fileMap.get("group_id"));
		mode.addAttribute("hos_id", fileMap.get("hos_id"));
		mode.addAttribute("copy_code", fileMap.get("copy_code"));
		mode.addAttribute("protocol_id", fileMap.get("protocol_id"));
		mode.addAttribute("file_id", fileMap.get("file_id"));
		mode.addAttribute("type_code", fileMap.get("type_code"));
		mode.addAttribute("type_name", fileMap.get("type_name"));
		mode.addAttribute("file_code", fileMap.get("file_code"));
		mode.addAttribute("file_name", fileMap.get("file_name"));
		mode.addAttribute("at_local", fileMap.get("at_local"));
		mode.addAttribute("dept_id", fileMap.get("dept_id"));
		mode.addAttribute("dept_name", fileMap.get("dept_name"));
		mode.addAttribute("mana_emp", fileMap.get("mana_emp"));
		mode.addAttribute("emp_name", fileMap.get("emp_name"));
		mode.addAttribute("file_path", fileMap.get("file_path"));
		
		return "hrp/med/protocol/medprotocolmain/medProtocolFile";
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/updateMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medProtocolMainJson = medProtocolMainService.updateMedProtocolMain(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 * 审核、消审、确认、取消确认、终止、取消终止
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/updateState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("protocol_id", ids[3]);
				mapVo.put("state", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medProtocolMainJson = medProtocolMainService.updateState(listVo);
			
	    return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/addOrUpdateMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medProtocolMainJson ="";
		
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
	  
		medProtocolMainJson = medProtocolMainService.addOrUpdateMedProtocolMain(detailVo);
		
		}
		return JSONObject.parseObject(medProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/deleteMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedProtocolMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("protocol_id", ids[3]);
				mapVo.put("detail_id", null);
				mapVo.put("file_id", null);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medProtocolMainJson = medProtocolMainService.deleteBatchMedProtocolMain(listVo);
			
	  return JSONObject.parseObject(medProtocolMainJson);
			
	}
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/deleteDetail_File", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDetail_File(@RequestParam(value="delData") String delData, Model mode) throws Exception {
		
		List<Map<String, Object>> fileList= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
		
			for ( String id: delData.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				
				if("file".equals(ids[5])){
					mapVo.put("group_id", ids[0])   ;
					mapVo.put("hos_id", ids[1])   ;
					mapVo.put("copy_code", ids[2])   ;
					mapVo.put("protocol_id", ids[3]);
					mapVo.put("file_id", ids[4]);
					
					fileList.add(mapVo);
				}
				if("detail".equals(ids[5])){
					mapVo.put("group_id", ids[0])   ;
					mapVo.put("hos_id", ids[1])   ;
					mapVo.put("copy_code", ids[2])   ;
					mapVo.put("protocol_id", ids[3]);
					mapVo.put("detail_id", ids[4]);
					
					detailList.add(mapVo);
				}
				
			}
		String str =null;
	    if(detailList.size()>0){
	    	str = medProtocolDetailService.deleteBatchMedProtocolDetail(detailList);
	    }
	    if(fileList.size()>0){
	    	str = medProtocolFileService.deleteBatchMedProtocolFile(fileList);
	    }
			
	  return JSONObject.parseObject(str);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		//mapVo.put("is_init", "0");
		String medProtocolMain = medProtocolMainService.queryMedProtocolMainInfo(getPage(mapVo));

		return JSONObject.parseObject(medProtocolMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 state：0：新建 1：审核 2：确认
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/medProtocolMainImportPage", method = RequestMethod.GET)
	public String medProtocolMainImportPage(Model mode) throws Exception {

		return "hrp/med/protocol/medprotocolmain/medProtocolMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 state：0：新建 1：审核 2：确认
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/protocol/medprotocolmain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","state：0：新建 1：审核 2：确认 .xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 state：0：新建 1：审核 2：确认
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/protocol/medprotocolmain/readMedProtocolMainFiles",method = RequestMethod.POST)  
    public String readMedProtocolMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedProtocolMain> list_err = new ArrayList<MedProtocolMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedProtocolMain medProtocolMain = new MedProtocolMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medProtocolMain.setProtocol_id(Long.valueOf(temp[3]));
		    		mapVo.put("protocol_id", temp[3]);
					
					} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medProtocolMain.setProtocol_code(temp[4]);
		    		mapVo.put("protocol_code", temp[4]);
					
					} else {
						
						err_sb.append("协议编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medProtocolMain.setOriginal_no(temp[5]);
		    		mapVo.put("original_no", temp[5]);
					
					} else {
						
						err_sb.append("原始编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medProtocolMain.setType_id(Long.valueOf(temp[6]));
		    		mapVo.put("type_id", temp[6]);
					
					} else {
						
						err_sb.append("类别ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medProtocolMain.setProtocol_name(temp[7]);
		    		mapVo.put("protocol_name", temp[7]);
					
					} else {
						
						err_sb.append("协议名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medProtocolMain.setSign_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("sign_date", temp[8]);
					
					} else {
						
						err_sb.append("签订日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medProtocolMain.setSup_no(Long.valueOf(temp[9]));
		    		mapVo.put("sup_no", temp[9]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medProtocolMain.setSup_id(Long.valueOf(temp[10]));
		    		mapVo.put("sup_id", temp[10]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medProtocolMain.setIs_bid(Integer.valueOf(temp[11]));
		    		mapVo.put("is_bid", temp[11]);
					
					} else {
						
						err_sb.append("是否招标为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medProtocolMain.setStart_date(DateUtil.stringToDate(temp[12]));
		    		mapVo.put("start_date", temp[12]);
					
					} else {
						
						err_sb.append("开始日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medProtocolMain.setEnd_date(DateUtil.stringToDate(temp[13]));
		    		mapVo.put("end_date", temp[13]);
					
					} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medProtocolMain.setDept_no(Long.valueOf(temp[14]));
		    		mapVo.put("dept_no", temp[14]);
					
					} else {
						
						err_sb.append("签订科室变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					medProtocolMain.setDept_id(Long.valueOf(temp[15]));
		    		mapVo.put("dept_id", temp[15]);
					
					} else {
						
						err_sb.append("签订科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					medProtocolMain.setAddr(temp[16]);
		    		mapVo.put("addr", temp[16]);
					
					} else {
						
						err_sb.append("存放位置为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					medProtocolMain.setFirst_man(temp[17]);
		    		mapVo.put("first_man", temp[17]);
					
					} else {
						
						err_sb.append("甲方负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					medProtocolMain.setSecond_man(temp[18]);
		    		mapVo.put("second_man", temp[18]);
					
					} else {
						
						err_sb.append("乙方负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					medProtocolMain.setSecond_phone(temp[19]);
		    		mapVo.put("second_phone", temp[19]);
					
					} else {
						
						err_sb.append("乙方电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					medProtocolMain.setContract_detail(temp[20]);
		    		mapVo.put("contract_detail", temp[20]);
					
					} else {
						
						err_sb.append("协议简介为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					medProtocolMain.setService_detail(temp[21]);
		    		mapVo.put("service_detail", temp[21]);
					
					} else {
						
						err_sb.append("售后服务为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					medProtocolMain.setCreate_emp(Long.valueOf(temp[22]));
		    		mapVo.put("create_emp", temp[22]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					medProtocolMain.setCheck_emp(Long.valueOf(temp[23]));
		    		mapVo.put("check_emp", temp[23]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					medProtocolMain.setConfirm_emp(Long.valueOf(temp[24]));
		    		mapVo.put("confirm_emp", temp[24]);
					
					} else {
						
						err_sb.append("确认人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[25])) {
						
					medProtocolMain.setState(Integer.valueOf(temp[25]));
		    		mapVo.put("state", temp[25]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[26])) {
						
					medProtocolMain.setSpell(temp[26]);
		    		mapVo.put("spell", temp[26]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[27])) {
						
					medProtocolMain.setIs_init(Integer.valueOf(temp[27]));
		    		mapVo.put("is_init", temp[27]);
					
					} else {
						
						err_sb.append("是否期初为空  ");
						
					}
					 
					
				MedProtocolMain data_exc_extis = medProtocolMainService.queryMedProtocolMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medProtocolMain.setError_type(err_sb.toString());
					
					list_err.add(medProtocolMain);
					
				} else {
			  
					String dataJson = medProtocolMainService.addMedProtocolMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedProtocolMain data_exc = new MedProtocolMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 state：0：新建 1：审核 2：确认
s	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/addBatchMedProtocolMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedProtocolMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedProtocolMain> list_err = new ArrayList<MedProtocolMain>();
		
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
			
			MedProtocolMain medProtocolMain = new MedProtocolMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_id"))) {
						
					medProtocolMain.setProtocol_id(Long.valueOf((String)jsonObj.get("protocol_id")));
		    		mapVo.put("protocol_id", jsonObj.get("protocol_id"));
		    		} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_code"))) {
						
					medProtocolMain.setProtocol_code((String)jsonObj.get("protocol_code"));
		    		mapVo.put("protocol_code", jsonObj.get("protocol_code"));
		    		} else {
						
						err_sb.append("协议编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("original_no"))) {
						
					medProtocolMain.setOriginal_no((String)jsonObj.get("original_no"));
		    		mapVo.put("original_no", jsonObj.get("original_no"));
		    		} else {
						
						err_sb.append("原始编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					medProtocolMain.setType_id(Long.valueOf((String)jsonObj.get("type_id")));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("类别ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_name"))) {
						
					medProtocolMain.setProtocol_name((String)jsonObj.get("protocol_name"));
		    		mapVo.put("protocol_name", jsonObj.get("protocol_name"));
		    		} else {
						
						err_sb.append("协议名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sign_date"))) {
						
					medProtocolMain.setSign_date(DateUtil.stringToDate((String)jsonObj.get("sign_date")));
		    		mapVo.put("sign_date", jsonObj.get("sign_date"));
		    		} else {
						
						err_sb.append("签订日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					medProtocolMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					medProtocolMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_bid"))) {
						
					medProtocolMain.setIs_bid(Integer.valueOf((String)jsonObj.get("is_bid")));
		    		mapVo.put("is_bid", jsonObj.get("is_bid"));
		    		} else {
						
						err_sb.append("是否招标为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					medProtocolMain.setStart_date(DateUtil.stringToDate((String)jsonObj.get("start_date")));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("开始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					medProtocolMain.setEnd_date(DateUtil.stringToDate((String)jsonObj.get("end_date")));
		    		mapVo.put("end_date", jsonObj.get("end_date"));
		    		} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					medProtocolMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("签订科室变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					medProtocolMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("签订科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("addr"))) {
						
					medProtocolMain.setAddr((String)jsonObj.get("addr"));
		    		mapVo.put("addr", jsonObj.get("addr"));
		    		} else {
						
						err_sb.append("存放位置为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("first_man"))) {
						
					medProtocolMain.setFirst_man((String)jsonObj.get("first_man"));
		    		mapVo.put("first_man", jsonObj.get("first_man"));
		    		} else {
						
						err_sb.append("甲方负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("second_man"))) {
						
					medProtocolMain.setSecond_man((String)jsonObj.get("second_man"));
		    		mapVo.put("second_man", jsonObj.get("second_man"));
		    		} else {
						
						err_sb.append("乙方负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("second_phone"))) {
						
					medProtocolMain.setSecond_phone((String)jsonObj.get("second_phone"));
		    		mapVo.put("second_phone", jsonObj.get("second_phone"));
		    		} else {
						
						err_sb.append("乙方电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("contract_detail"))) {
						
					medProtocolMain.setContract_detail((String)jsonObj.get("contract_detail"));
		    		mapVo.put("contract_detail", jsonObj.get("contract_detail"));
		    		} else {
						
						err_sb.append("协议简介为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("service_detail"))) {
						
					medProtocolMain.setService_detail((String)jsonObj.get("service_detail"));
		    		mapVo.put("service_detail", jsonObj.get("service_detail"));
		    		} else {
						
						err_sb.append("售后服务为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					medProtocolMain.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_emp"))) {
						
					medProtocolMain.setCheck_emp(Long.valueOf((String)jsonObj.get("check_emp")));
		    		mapVo.put("check_emp", jsonObj.get("check_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {
						
					medProtocolMain.setConfirm_emp(Long.valueOf((String)jsonObj.get("confirm_emp")));
		    		mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
		    		} else {
						
						err_sb.append("确认人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medProtocolMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell"))) {
						
					medProtocolMain.setSpell((String)jsonObj.get("spell"));
		    		mapVo.put("spell", jsonObj.get("spell"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					medProtocolMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("是否期初为空  ");
						
					}
					
					
				MedProtocolMain data_exc_extis = medProtocolMainService.queryMedProtocolMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medProtocolMain.setError_type(err_sb.toString());
					
					list_err.add(medProtocolMain);
					
				} else {
			  
					String dataJson = medProtocolMainService.addMedProtocolMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedProtocolMain data_exc = new MedProtocolMain();
			
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
	 * 根据协议类型Id 查询其开始年月 、协议前缀 ,同时生成 协议编号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedProtocolTypePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedProtocolTypePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medProtocolMainJson = medProtocolMainService.queryMedProtocolTypePre(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 * grid明细编辑  文档类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedFileType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryMedFileType(mapVo);
			
		return str;
	}
	/**
	 * grid明细编辑  管理科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryManaDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryManaDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryManaDept(mapVo);
			
		return str;
	}
	/**
	 * grid明细编辑  管理员下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryManaEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryManaEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryManaEmp(mapVo);
			
		return str;
	}
	/**
	 * 物资药品明细列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryMedInvDetail(getPage(mapVo));
			
		return str;
	}
	/**
	 * 协议明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedProtocolDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedProtocolDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryMedProtocolDetail(getPage(mapVo));
			
		return str;
	}
	/**
	 * 协议文档信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = medProtocolMainService.queryMedProtocolFile(getPage(mapVo));
			
		return str;
	}

	
	@RequestMapping(value = "/hrp/med/protocol/medprotocolmain/queryMedProtocolMainPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedProtocolMainPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		  mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}

		String medProtocolMain = medProtocolMainService.queryMedProtocolMainPur(getPage(mapVo));

		return JSONObject.parseObject(medProtocolMain);
		
	}

	

	//上传文件
	@RequestMapping(value="/hrp/med/protocol/medprotocolmain/upLodeFile", method = RequestMethod.POST)  
	 public String upLodeFile(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo) throws IOException { 
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
		if(mapVo.get("file_id") == null || mapVo.get("file_id") == "" ||("00").equals(mapVo.get("file_id").toString())){
			List<File> fileList = UploadUtil.upload(plupload, "medFile\\protocolFile",request, response);
			
			result = "{\"file_path\":\""+fileList.get(0).getPath()+"\",\"state\":\"false\"}";
			
			System.out.println("result = "+result);

			//result = "{\"msg\":\"数据未保存.请先保存数据再上传文件.\",\"state\":\"false\"}";
		}else{
			MedProtocolFile medProtocolFile =  medProtocolFileService.queryMedProtocolFileByCode(mapVo);
			
			//logger.warn("test:"+request.getSession().getServletContext().getRealPath("\\")+assBidFile.getFile_url()+"\\"+assBidFile.getFile_name());
			
			//FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+assBidFile.getFile_url()+"\\"+assBidFile.getFile_name());
			List<File> fileList = UploadUtil.upload(plupload, "medFile\\protocolFile",request, response);
			for(File file : fileList){
				//String str = file.getPath();
				mapVo.put("file_id", medProtocolFile.getFile_id());
				mapVo.put("protocol_id", medProtocolFile.getProtocol_id());
				mapVo.put("type_code", medProtocolFile.getType_code());
				mapVo.put("file_code", medProtocolFile.getFile_code());
				mapVo.put("file_name", medProtocolFile.getFile_name());
				mapVo.put("at_local", medProtocolFile.getAt_local());
				mapVo.put("dept_id", medProtocolFile.getDept_id());
				mapVo.put("mana_emp", medProtocolFile.getMana_emp());
				
				mapVo.put("file_path", file.getPath());
				
				result = medProtocolFileService.updateMedProtocolFile(mapVo);
			}
			
			result = "{\"msg\":\"上传成功.\",\"state\":\"true\"}";	
		}
		response.getWriter().print(result);
	    return null; 
	 }

}

