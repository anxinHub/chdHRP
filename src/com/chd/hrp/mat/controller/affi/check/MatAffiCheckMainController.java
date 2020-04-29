/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.affi.check;
import java.io.IOException;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatAffiCheckMain;
import com.chd.hrp.mat.service.affi.check.MatAffiCheckMainService;
import com.chd.hrp.mat.service.affi.in.MatAffiInCommonService;
import com.chd.hrp.mat.service.affi.out.MatAffiOutCommonService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatAffiCheckMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatAffiCheckMainController.class);
	
	//引入Service服务
	@Resource(name = "matAffiCheckMainService")
	private final MatAffiCheckMainService matAffiCheckMainService = null;
	
	@Resource(name = "matAffiInCommonService")
	private final MatAffiInCommonService matAffiInCommonService = null;
	
	@Resource(name = "matAffiOutCommonService")
	private final MatAffiOutCommonService matAffiOutCommonService = null;
    
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckMainPage", method = RequestMethod.GET)
	public String matAffiCheckMainPage(Model mode) throws Exception {
		
		return "hrp/mat/affi/check/matAffiCheckMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckMainAddPage", method = RequestMethod.GET)
	public String matAffiCheckMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/check/matAffiCheckMainAdd";

	}
	
	/**
	 * @Description 仓库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckMainByStorePage", method = RequestMethod.GET)
	public String matCheckByStorePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
				
		if(paras != null && !"null".equals(paras)){			
			mode.addAttribute("store_id", paras);
		}
	
		return "hrp/mat/affi/check/matAffiCheckMainByStore";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/addMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	    mapVo.put("maker", SessionManager.getUserId());		
		mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

		String matJson;
		try {
			matJson = matAffiCheckMainService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckMainUpdatePage", method = RequestMethod.GET)
	public String matAffiCheckMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
				
		MatAffiCheckMain matAffiCheckMain = new MatAffiCheckMain();
    
		matAffiCheckMain = matAffiCheckMainService.queryMatAffiCheckMainByCode(mapVo);
		
		mode.addAttribute("hos_id", matAffiCheckMain.getHos_id());
		mode.addAttribute("group_id", matAffiCheckMain.getGroup_id());
		mode.addAttribute("copy_code", matAffiCheckMain.getCopy_code());
		mode.addAttribute("check_id", matAffiCheckMain.getCheck_id());
		mode.addAttribute("check_code", matAffiCheckMain.getCheck_code());
		mode.addAttribute("store_id", matAffiCheckMain.getStore_id());
		mode.addAttribute("store_no", matAffiCheckMain.getStore_no());
		mode.addAttribute("store_code", matAffiCheckMain.getStore_code());
		mode.addAttribute("store_name", matAffiCheckMain.getStore_name());
		mode.addAttribute("dept_id", matAffiCheckMain.getDept_id());
		mode.addAttribute("dept_no", matAffiCheckMain.getDept_no());
		mode.addAttribute("dept_code", matAffiCheckMain.getDept_code());
		mode.addAttribute("dept_name", matAffiCheckMain.getDept_name());
		mode.addAttribute("create_date", DateUtil.dateToString(matAffiCheckMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("check_date", DateUtil.dateToString(matAffiCheckMain.getCheck_date(), "yyyy-MM-dd"));
		mode.addAttribute("emp_id", matAffiCheckMain.getEmp_id());
		mode.addAttribute("emp_code", matAffiCheckMain.getEmp_code());
		mode.addAttribute("emp_name", matAffiCheckMain.getEmp_name());
		mode.addAttribute("maker", matAffiCheckMain.getMaker());
		mode.addAttribute("checker", matAffiCheckMain.getChecker());
		mode.addAttribute("state", matAffiCheckMain.getState());
		mode.addAttribute("brif", matAffiCheckMain.getBrif());
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04028", MyConfig.getSysPara("04028"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/check/matAffiCheckMainUpdate";
	}
	
	/**
	 * @Description 更新页面查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCheckDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiCheckMainService.queryMatAffiCheckDetailByCheckID(getPage(mapVo));

		return JSONObject.parseObject(matJson);

	}
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/updateMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatematAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String matJson;
		try {
			matJson = matAffiCheckMainService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/addOrUpdateMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matAffiCheckMainJson ="";
		

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		matAffiCheckMainJson = matAffiCheckMainService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(matAffiCheckMainJson);
	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/deleteMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiCheckMainService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
	  
		return JSONObject.parseObject(matJson);
		

	}
	
	/**
	 * @Description 消审数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/unAuditMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			
			mapVo.put("checker", "");
			mapVo.put("check_date","");
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matAffiCheckMainService.unAuditMatAffiCheckMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 审核数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/auditMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
						
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}
		String matJson;
		try {
			matJson = matAffiCheckMainService.auditMatAffiCheckMain(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//时间格式转换
//		if(mapVo.get("create_date_start") != null && !"".equals(mapVo.get("create_date_start").toString())){
//			mapVo.put("create_date_start", DateUtil.stringToDate(mapVo.get("create_date_start").toString(), "yyyy-MM-dd"));
//		}
//		
//		if(mapVo.get("create_date_end") != null && !"".equals(mapVo.get("create_date_end").toString())){
//			mapVo.put("create_date_end", DateUtil.stringToDate(mapVo.get("create_date_end").toString(), "yyyy-MM-dd"));
//		}
				
		String matAffiCheckMain = matAffiCheckMainService.queryMatAffiCheckMain(getPage(mapVo));

		return JSONObject.parseObject(matAffiCheckMain);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatAffiCheckMainByMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiCheckMainByMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());       
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		if(mapVo.get("acct_year") == null){			
			mapVo.put("acct_year", SessionManager.getAcctYear());       
		}
		String matAffiCheckMain = matAffiCheckMainService.queryMatAffiCheckMainByMatInv(mapVo);
		//String matAffiCheckMain = matAffiCheckMainService.queryMatAffiCheckMainByMatInv(getPage(mapVo));

		return JSONObject.parseObject(matAffiCheckMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckMainImportPage", method = RequestMethod.GET)
	public String matAffiCheckMainImportPage(Model mode) throws Exception {

		return "hrp/mat/affi/check/matAffiCheckMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/affi/check/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate",".xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/affi/check/readMatAffiCheckMainFiles",method = RequestMethod.POST)  
    public String readmatAffiCheckMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatAffiCheckMain> list_err = new ArrayList<MatAffiCheckMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatAffiCheckMain matAffiCheckMain = new MatAffiCheckMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matAffiCheckMain.setCheck_id(Long.valueOf(temp[3]));
		    		mapVo.put("check_id", temp[3]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("check_code", temp[4]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matAffiCheckMain.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matAffiCheckMain.setStore_no(Long.valueOf(temp[6]));
		    		mapVo.put("store_no", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matAffiCheckMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matAffiCheckMain.setDept_no(Long.valueOf(temp[8]));
		    		mapVo.put("dept_no", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matAffiCheckMain.setCheck_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("check_date", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matAffiCheckMain.setEmp_id(Long.valueOf(temp[10]));
		    		mapVo.put("emp_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matAffiCheckMain.setMaker(Long.valueOf(temp[11]));
		    		mapVo.put("maker", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matAffiCheckMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matAffiCheckMain.setState(Integer.valueOf(temp[13]));
		    		mapVo.put("state", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
		    		mapVo.put("brif", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MatAffiCheckMain data_exc_extis = matAffiCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matAffiCheckMain.setError_type(err_sb.toString());
					
					list_err.add(matAffiCheckMain);
					
				} else {
			  
					String dataJson = matAffiCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatAffiCheckMain data_exc = new MatAffiCheckMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/addBatchmatAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchmatAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatAffiCheckMain> list_err = new ArrayList<MatAffiCheckMain>();
		
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
			
			MatAffiCheckMain matAffiCheckMain = new MatAffiCheckMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_id"))) {
						
					matAffiCheckMain.setCheck_id(Long.valueOf((String)jsonObj.get("check_id")));
		    		mapVo.put("check_id", jsonObj.get("check_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_code"))) {
						
		    		mapVo.put("check_code", jsonObj.get("check_code"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matAffiCheckMain.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					matAffiCheckMain.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					matAffiCheckMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					matAffiCheckMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					matAffiCheckMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
						
					matAffiCheckMain.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
		    		mapVo.put("emp_id", jsonObj.get("emp_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matAffiCheckMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matAffiCheckMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matAffiCheckMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MatAffiCheckMain data_exc_extis = matAffiCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matAffiCheckMain.setError_type(err_sb.toString());
					
					list_err.add(matAffiCheckMain);
					
				} else {
			  
					String dataJson = matAffiCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatAffiCheckMain data_exc = new MatAffiCheckMain();
			
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
	 * @Description 生成出入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/createMatAffiInOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createMatAffiInOut(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String user_id = SessionManager.getUserId();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			mapVo.put("user_id", user_id);
			mapVo.put("state", 3);//已生成
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matAffiCheckMainService.createInOut(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	}
    
	/**
	 * 盘盈单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiInPage", method = RequestMethod.GET)
	public String matAffiInPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matAffiIn = matAffiInCommonService.queryByCode(mapVo);
		
		if(matAffiIn.get("in_date") != null && !"".equals(matAffiIn.get("in_date"))){
			matAffiIn.put("in_date", DateUtil.dateToString((Date)matAffiIn.get("in_date"), "yyyy-MM-dd"));
		}
		if(matAffiIn.get("make_date") != null && !"".equals(matAffiIn.get("make_date"))){
			matAffiIn.put("make_date", DateUtil.dateToString((Date)matAffiIn.get("make_date"), "yyyy-MM-dd"));
		}
		if(matAffiIn.get("check_date") != null && !"".equals(matAffiIn.get("check_date"))){
			matAffiIn.put("check_date", DateUtil.dateToString((Date)matAffiIn.get("check_date"), "yyyy-MM-dd"));
		}
		if(matAffiIn.get("confirm_date") != null && !"".equals(matAffiIn.get("confirm_date"))){
			matAffiIn.put("confirm_date", DateUtil.dateToString((Date)matAffiIn.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matAffiIn", matAffiIn);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/affi/check/matAffiInPage";
	}
	
	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatAffiInCommonDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInCommonDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAffiInCommonService.queryDetailByCode(mapVo);

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 盘亏单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiOutPage", method = RequestMethod.GET)
	public String matAffiOutPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matAffiOutMain = matAffiOutCommonService.queryByCode(mapVo);
		
		Map<String, Object> matAffiOutId = matAffiOutCommonService.queryAffiOutIds(mapVo);
		
		String matOutDetail = matAffiOutCommonService.queryAffiOutDetailById(getPage(mapVo));
		
		mode.addAttribute("store_id", matAffiOutMain.get("store_id").toString()+","+matAffiOutMain.get("store_no").toString());
		mode.addAttribute("store_code", matAffiOutMain.get("store_code").toString()+" "+matAffiOutMain.get("store_name").toString());
		mode.addAttribute("dept_id", matAffiOutMain.get("dept_id").toString()+","+matAffiOutMain.get("dept_no").toString());
		mode.addAttribute("dept_code", matAffiOutMain.get("dept_code").toString()+" "+matAffiOutMain.get("dept_name").toString());
		mode.addAttribute("dept_emp", matAffiOutMain.get("dept_emp").toString()+","+matAffiOutMain.get("emp_no").toString());
		mode.addAttribute("dept_emp_code", matAffiOutMain.get("dept_emp_code").toString()+" "+matAffiOutMain.get("dept_emp_name").toString());
		mode.addAttribute("out_id", matAffiOutMain.get("out_id"));
		mode.addAttribute("out_no", matAffiOutMain.get("out_no"));
		mode.addAttribute("state", matAffiOutMain.get("state"));
		mode.addAttribute("group_id", matAffiOutMain.get("group_id"));
		mode.addAttribute("hos_id", matAffiOutMain.get("hos_id"));
		mode.addAttribute("copy_code", matAffiOutMain.get("copy_code"));
		mode.addAttribute("is_dir", matAffiOutMain.get("is_dir"));
		mode.addAttribute("out_date", matAffiOutMain.get("out_date"));
		mode.addAttribute("brief", matAffiOutMain.get("brief"));
		mode.addAttribute("out_idUp", matAffiOutId.get("out_idUp"));
		mode.addAttribute("out_idNext", matAffiOutId.get("out_idNext"));
		mode.addAttribute("use_code", matAffiOutMain.get("use_code"));
		mode.addAttribute("proj_id", matAffiOutMain.get("proj_id"));
		mode.addAttribute("bus_type_code", matAffiOutMain.get("bus_type_code"));
		
		mode.addAttribute("matOutDetail", matOutDetail);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/check/matAffiOutPage";

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/check/matAffiCheckPrintSetPage", method = RequestMethod.GET)
	public String matAffiCheckPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	/*
	*//**
	 * @Description 
	 * 盘点单模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*//*
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatAffiCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		//System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=matAffiCheckMainService.queryMatAffiCheckByPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}*/
	
	/**
	 * 引入仓库材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/check/queryMatAffiStoreInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiStoreInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matAffiCheckMainService.queryMatAffiStoreInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
}

