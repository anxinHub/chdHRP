/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.protocol;
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
import com.chd.hrp.mat.entity.MatProtocolFile;
import com.chd.hrp.mat.entity.MatProtocolMain;
import com.chd.hrp.mat.serviceImpl.protocol.MatProtocolDetailServiceImpl;
import com.chd.hrp.mat.serviceImpl.protocol.MatProtocolFileServiceImpl;
import com.chd.hrp.mat.serviceImpl.protocol.MatProtocolMainServiceImpl;
/**
 * 
 * @Description:
 * @Table:
 * MAT_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatProtocolMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatProtocolMainController.class);
	
	//引入Service服务
	@Resource(name = "matProtocolMainService")
	private final MatProtocolMainServiceImpl matProtocolMainService = null;
	
	@Resource(name = "matProtocolDetailService")
	private final MatProtocolDetailServiceImpl matProtocolDetailService = null;
	
	@Resource(name = "matProtocolFileService")
	private final MatProtocolFileServiceImpl matProtocolFileService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matProtocolMainMainPage", method = RequestMethod.GET)
	public String matProtocolMainMainPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocolmain/matProtocolMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matProtocolMainAddPage", method = RequestMethod.GET)
	public String matProtocolMainAddPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocolmain/matProtocolMainAdd";
	}
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matInvDetailPage", method = RequestMethod.GET)
	public String matInvDetailPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocolmain/matInvDetail";
	}
	//上传页面跳转
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/upLodePage", method = RequestMethod.GET)
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
		return "hrp/mat/protocol/matprotocolmain/upLode";
	}

	/**
	 * @Description 
	 * 添加数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/addMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String matProtocolMainJson = matProtocolMainService.addMatProtocolMain(mapVo);

		return JSONObject.parseObject(matProtocolMainJson);
		
	}
	/**
	 * 添加协议明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/addMatProtocolDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProtocolDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				Long protocol_id = matProtocolMainService.qureyCurrval();
				mapVo.put("protocol_id", protocol_id);
			}else{
				mapVo.put("protocol_id", ids[8]);
			}
			
		}
		
		String matProtocolMainJson =null;
		
		if(updateList.size()>0){
			matProtocolMainJson = matProtocolDetailService.updateBatchMatProtocolDetail(updateList);
		}
		
		if(addList.size()>0){
			matProtocolMainJson = matProtocolDetailService.addBatchMatProtocolDetail(addList);
		}
		

		return JSONObject.parseObject(matProtocolMainJson);
		
	}
	
	/**
	 * 添加协议文档信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/addMatProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProtocolFile(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				Long protocol_id = matProtocolMainService.qureyCurrval();
				mapVo.put("protocol_id", protocol_id);
			}else{
				mapVo.put("protocol_id", ids[7]);
			}
			mapVo.put("file_path",ids[8]) ;
			
		}
		String matProtocolMainJson =null;
		
		if(updateList.size()>0){
			matProtocolMainJson = matProtocolFileService.updateBatchMatProtocolFile(updateList);
		}
		
		if(addList.size()>0){
			matProtocolMainJson = matProtocolFileService.addBatchMatProtocolFile(addList);
		}
		
		return JSONObject.parseObject(matProtocolMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matProtocolMainUpdatePage", method = RequestMethod.GET)
	public String matProtocolMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		resultMap = matProtocolMainService.queryMatProtocolMainUnit(mapVo);
		//查询  该采购协议的协议类型的开始年月 
		Map<String,Object> preMap = matProtocolMainService.queryTypePre(resultMap);
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
		
		return "hrp/mat/protocol/matprotocolmain/matProtocolMainUpdate";
	}
	/**
	 * 查看协议文档
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matProtocolFilePage", method = RequestMethod.GET)
	public String matProtocolFilePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		fileMap = matProtocolFileService.queryMatProtocolFileByID(mapVo);
		
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
		
		return "hrp/mat/protocol/matprotocolmain/matProtocolFile";
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/updateMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matProtocolMainJson = matProtocolMainService.updateMatProtocolMain(mapVo);
		
		return JSONObject.parseObject(matProtocolMainJson);
	}
	/**
	 * 审核、消审、确认、取消确认、终止、取消终止
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/updateState", method = RequestMethod.POST)
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
	    
		String matProtocolMainJson = matProtocolMainService.updateState(listVo);
			
	    return JSONObject.parseObject(matProtocolMainJson);
	}
	/**
	 * @Description 
	 * 更新数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/addOrUpdateMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matProtocolMainJson ="";
		
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
	  
		matProtocolMainJson = matProtocolMainService.addOrUpdateMatProtocolMain(detailVo);
		
		}
		return JSONObject.parseObject(matProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 state：0：新建 1：审核 2：确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/deleteMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatProtocolMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String matProtocolMainJson = matProtocolMainService.deleteBatchMatProtocolMain(listVo);
			
	  return JSONObject.parseObject(matProtocolMainJson);
			
	}
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/deleteDetail_File", method = RequestMethod.POST)
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
	    	str = matProtocolDetailService.deleteBatchMatProtocolDetail(detailList);
	    }
	    if(fileList.size()>0){
	    	str = matProtocolFileService.deleteBatchMatProtocolFile(fileList);
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
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProtocolMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matProtocolMain = matProtocolMainService.queryMatProtocolMainInfo(getPage(mapVo));

		return JSONObject.parseObject(matProtocolMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 state：0：新建 1：审核 2：确认
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/matProtocolMainImportPage", method = RequestMethod.GET)
	public String matProtocolMainImportPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocolmain/matProtocolMainImport";

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
	 @RequestMapping(value="hrp/mat/protocol/matprotocolmain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","state：0：新建 1：审核 2：确认 .xls");
	    
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
	@RequestMapping(value="/hrp/mat/protocol/matprotocolmain/readMatProtocolMainFiles",method = RequestMethod.POST)  
    public String readMatProtocolMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatProtocolMain> list_err = new ArrayList<MatProtocolMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatProtocolMain matProtocolMain = new MatProtocolMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matProtocolMain.setProtocol_id(Long.valueOf(temp[3]));
		    		mapVo.put("protocol_id", temp[3]);
					
					} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matProtocolMain.setProtocol_code(temp[4]);
		    		mapVo.put("protocol_code", temp[4]);
					
					} else {
						
						err_sb.append("协议编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matProtocolMain.setOriginal_no(temp[5]);
		    		mapVo.put("original_no", temp[5]);
					
					} else {
						
						err_sb.append("原始编号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matProtocolMain.setType_id(Long.valueOf(temp[6]));
		    		mapVo.put("type_id", temp[6]);
					
					} else {
						
						err_sb.append("类别ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matProtocolMain.setProtocol_name(temp[7]);
		    		mapVo.put("protocol_name", temp[7]);
					
					} else {
						
						err_sb.append("协议名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matProtocolMain.setSign_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("sign_date", temp[8]);
					
					} else {
						
						err_sb.append("签订日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matProtocolMain.setSup_no(Long.valueOf(temp[9]));
		    		mapVo.put("sup_no", temp[9]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matProtocolMain.setSup_id(Long.valueOf(temp[10]));
		    		mapVo.put("sup_id", temp[10]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matProtocolMain.setIs_bid(Integer.valueOf(temp[11]));
		    		mapVo.put("is_bid", temp[11]);
					
					} else {
						
						err_sb.append("是否招标为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matProtocolMain.setStart_date(DateUtil.stringToDate(temp[12]));
		    		mapVo.put("start_date", temp[12]);
					
					} else {
						
						err_sb.append("开始日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matProtocolMain.setEnd_date(DateUtil.stringToDate(temp[13]));
		    		mapVo.put("end_date", temp[13]);
					
					} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					matProtocolMain.setDept_no(Long.valueOf(temp[14]));
		    		mapVo.put("dept_no", temp[14]);
					
					} else {
						
						err_sb.append("签订科室变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					matProtocolMain.setDept_id(Long.valueOf(temp[15]));
		    		mapVo.put("dept_id", temp[15]);
					
					} else {
						
						err_sb.append("签订科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					matProtocolMain.setAddr(temp[16]);
		    		mapVo.put("addr", temp[16]);
					
					} else {
						
						err_sb.append("存放位置为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					matProtocolMain.setFirst_man(temp[17]);
		    		mapVo.put("first_man", temp[17]);
					
					} else {
						
						err_sb.append("甲方负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					matProtocolMain.setSecond_man(temp[18]);
		    		mapVo.put("second_man", temp[18]);
					
					} else {
						
						err_sb.append("乙方负责人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					matProtocolMain.setSecond_phone(temp[19]);
		    		mapVo.put("second_phone", temp[19]);
					
					} else {
						
						err_sb.append("乙方电话为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					matProtocolMain.setContract_detail(temp[20]);
		    		mapVo.put("contract_detail", temp[20]);
					
					} else {
						
						err_sb.append("协议简介为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					matProtocolMain.setService_detail(temp[21]);
		    		mapVo.put("service_detail", temp[21]);
					
					} else {
						
						err_sb.append("售后服务为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					matProtocolMain.setCreate_emp(Long.valueOf(temp[22]));
		    		mapVo.put("create_emp", temp[22]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					matProtocolMain.setCheck_emp(Long.valueOf(temp[23]));
		    		mapVo.put("check_emp", temp[23]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					matProtocolMain.setConfirm_emp(Long.valueOf(temp[24]));
		    		mapVo.put("confirm_emp", temp[24]);
					
					} else {
						
						err_sb.append("确认人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[25])) {
						
					matProtocolMain.setState(Integer.valueOf(temp[25]));
		    		mapVo.put("state", temp[25]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[26])) {
						
					matProtocolMain.setSpell(temp[26]);
		    		mapVo.put("spell", temp[26]);
					
					} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[27])) {
						
					matProtocolMain.setIs_init(Integer.valueOf(temp[27]));
		    		mapVo.put("is_init", temp[27]);
					
					} else {
						
						err_sb.append("是否期初为空  ");
						
					}
					 
					
				MatProtocolMain data_exc_extis = matProtocolMainService.queryMatProtocolMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matProtocolMain.setError_type(err_sb.toString());
					
					list_err.add(matProtocolMain);
					
				} else {
			  
					String dataJson = matProtocolMainService.addMatProtocolMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatProtocolMain data_exc = new MatProtocolMain();
			
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
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/addBatchMatProtocolMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatProtocolMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatProtocolMain> list_err = new ArrayList<MatProtocolMain>();
		
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
			
			MatProtocolMain matProtocolMain = new MatProtocolMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_id"))) {
						
					matProtocolMain.setProtocol_id(Long.valueOf((String)jsonObj.get("protocol_id")));
		    		mapVo.put("protocol_id", jsonObj.get("protocol_id"));
		    		} else {
						
						err_sb.append("协议ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_code"))) {
						
					matProtocolMain.setProtocol_code((String)jsonObj.get("protocol_code"));
		    		mapVo.put("protocol_code", jsonObj.get("protocol_code"));
		    		} else {
						
						err_sb.append("协议编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("original_no"))) {
						
					matProtocolMain.setOriginal_no((String)jsonObj.get("original_no"));
		    		mapVo.put("original_no", jsonObj.get("original_no"));
		    		} else {
						
						err_sb.append("原始编号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_id"))) {
						
					matProtocolMain.setType_id(Long.valueOf((String)jsonObj.get("type_id")));
		    		mapVo.put("type_id", jsonObj.get("type_id"));
		    		} else {
						
						err_sb.append("类别ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("protocol_name"))) {
						
					matProtocolMain.setProtocol_name((String)jsonObj.get("protocol_name"));
		    		mapVo.put("protocol_name", jsonObj.get("protocol_name"));
		    		} else {
						
						err_sb.append("协议名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sign_date"))) {
						
					matProtocolMain.setSign_date(DateUtil.stringToDate((String)jsonObj.get("sign_date")));
		    		mapVo.put("sign_date", jsonObj.get("sign_date"));
		    		} else {
						
						err_sb.append("签订日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					matProtocolMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					matProtocolMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_bid"))) {
						
					matProtocolMain.setIs_bid(Integer.valueOf((String)jsonObj.get("is_bid")));
		    		mapVo.put("is_bid", jsonObj.get("is_bid"));
		    		} else {
						
						err_sb.append("是否招标为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("start_date"))) {
						
					matProtocolMain.setStart_date(DateUtil.stringToDate((String)jsonObj.get("start_date")));
		    		mapVo.put("start_date", jsonObj.get("start_date"));
		    		} else {
						
						err_sb.append("开始日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("end_date"))) {
						
					matProtocolMain.setEnd_date(DateUtil.stringToDate((String)jsonObj.get("end_date")));
		    		mapVo.put("end_date", jsonObj.get("end_date"));
		    		} else {
						
						err_sb.append("截止日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					matProtocolMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("签订科室变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					matProtocolMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("签订科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("addr"))) {
						
					matProtocolMain.setAddr((String)jsonObj.get("addr"));
		    		mapVo.put("addr", jsonObj.get("addr"));
		    		} else {
						
						err_sb.append("存放位置为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("first_man"))) {
						
					matProtocolMain.setFirst_man((String)jsonObj.get("first_man"));
		    		mapVo.put("first_man", jsonObj.get("first_man"));
		    		} else {
						
						err_sb.append("甲方负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("second_man"))) {
						
					matProtocolMain.setSecond_man((String)jsonObj.get("second_man"));
		    		mapVo.put("second_man", jsonObj.get("second_man"));
		    		} else {
						
						err_sb.append("乙方负责人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("second_phone"))) {
						
					matProtocolMain.setSecond_phone((String)jsonObj.get("second_phone"));
		    		mapVo.put("second_phone", jsonObj.get("second_phone"));
		    		} else {
						
						err_sb.append("乙方电话为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("contract_detail"))) {
						
					matProtocolMain.setContract_detail((String)jsonObj.get("contract_detail"));
		    		mapVo.put("contract_detail", jsonObj.get("contract_detail"));
		    		} else {
						
						err_sb.append("协议简介为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("service_detail"))) {
						
					matProtocolMain.setService_detail((String)jsonObj.get("service_detail"));
		    		mapVo.put("service_detail", jsonObj.get("service_detail"));
		    		} else {
						
						err_sb.append("售后服务为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					matProtocolMain.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_emp"))) {
						
					matProtocolMain.setCheck_emp(Long.valueOf((String)jsonObj.get("check_emp")));
		    		mapVo.put("check_emp", jsonObj.get("check_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {
						
					matProtocolMain.setConfirm_emp(Long.valueOf((String)jsonObj.get("confirm_emp")));
		    		mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
		    		} else {
						
						err_sb.append("确认人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matProtocolMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("spell"))) {
						
					matProtocolMain.setSpell((String)jsonObj.get("spell"));
		    		mapVo.put("spell", jsonObj.get("spell"));
		    		} else {
						
						err_sb.append("拼音码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					matProtocolMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("是否期初为空  ");
						
					}
					
					
				MatProtocolMain data_exc_extis = matProtocolMainService.queryMatProtocolMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matProtocolMain.setError_type(err_sb.toString());
					
					list_err.add(matProtocolMain);
					
				} else {
			  
					String dataJson = matProtocolMainService.addMatProtocolMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatProtocolMain data_exc = new MatProtocolMain();
			
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
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatProtocolTypePre", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProtocolTypePre(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matProtocolMainJson = matProtocolMainService.queryMatProtocolTypePre(mapVo);
		
		return JSONObject.parseObject(matProtocolMainJson);
	}
	/**
	 * grid明细编辑  文档类别下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatFileType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = matProtocolMainService.queryMatFileType(mapVo);
			
		return str;
	}
	/**
	 * grid明细编辑  管理科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryManaDept", method = RequestMethod.POST)
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
		String str = matProtocolMainService.queryManaDept(mapVo);
			
		return str;
	}
	/**
	 * grid明细编辑  管理员下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryManaEmp", method = RequestMethod.POST)
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
		String str = matProtocolMainService.queryManaEmp(mapVo);
			
		return str;
	}
	/**
	 * 物资材料明细列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = matProtocolMainService.queryMatInvDetail(getPage(mapVo));
			
		return str;
	}
	/**
	 * 协议明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatProtocolDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatProtocolDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = matProtocolMainService.queryMatProtocolDetail(getPage(mapVo));
			
		return str;
	}
	/**
	 * 协议文档信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatProtocolFile", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatProtocolFile(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
				
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
				
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String str = matProtocolMainService.queryMatProtocolFile(getPage(mapVo));
			
		return str;
	}

	
	@RequestMapping(value = "/hrp/mat/protocol/matprotocolmain/queryMatProtocolMainPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProtocolMainPur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		  mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}

		String matProtocolMain = matProtocolMainService.queryMatProtocolMainPur(getPage(mapVo));

		return JSONObject.parseObject(matProtocolMain);
		
	}

	

	//上传文件
	@RequestMapping(value="/hrp/mat/protocol/matprotocolmain/upLodeFile", method = RequestMethod.POST)  
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
			List<File> fileList = UploadUtil.upload(plupload, "matFile\\protocolFile",request, response);
			
			result = "{\"file_path\":\""+fileList.get(0).getPath()+"\",\"state\":\"false\"}";
			
			//System.out.println("result = "+result);

			//result = "{\"msg\":\"数据未保存.请先保存数据再上传文件.\",\"state\":\"false\"}";
		}else{
			MatProtocolFile matProtocolFile =  matProtocolFileService.queryMatProtocolFileByCode(mapVo);
			
			//logger.warn("test:"+request.getSession().getServletContext().getRealPath("\\")+assBidFile.getFile_url()+"\\"+assBidFile.getFile_name());
			
			//FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+assBidFile.getFile_url()+"\\"+assBidFile.getFile_name());
			List<File> fileList = UploadUtil.upload(plupload, "matFile\\protocolFile",request, response);
			for(File file : fileList){
				//String str = file.getPath();
				mapVo.put("file_id", matProtocolFile.getFile_id());
				mapVo.put("protocol_id", matProtocolFile.getProtocol_id());
				mapVo.put("type_code", matProtocolFile.getType_code());
				mapVo.put("file_code", matProtocolFile.getFile_code());
				mapVo.put("file_name", matProtocolFile.getFile_name());
				mapVo.put("at_local", matProtocolFile.getAt_local());
				mapVo.put("dept_id", matProtocolFile.getDept_id());
				mapVo.put("mana_emp", matProtocolFile.getMana_emp());
				
				mapVo.put("file_path", file.getPath());
				
				result = matProtocolFileService.updateMatProtocolFile(mapVo);
			}
			
			result = "{\"msg\":\"上传成功.\",\"state\":\"true\"}";	
		}
		response.getWriter().print(result);
	    return null; 
	 }

}

