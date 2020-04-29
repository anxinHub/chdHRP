/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.affi.check;
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
import com.chd.hrp.med.entity.MedAffiCheckMain;
import com.chd.hrp.med.entity.MedOutMain;
import com.chd.hrp.med.service.affi.check.MedAffiCheckMainService;
import com.chd.hrp.med.service.affi.in.MedAffiInCommonService;
import com.chd.hrp.med.service.affi.out.MedAffiOutCommonService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedAffiCheckMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedAffiCheckMainController.class);
	
	//引入Service服务
	@Resource(name = "medAffiCheckMainService")
	private final MedAffiCheckMainService medAffiCheckMainService = null;
	
	@Resource(name = "medAffiInCommonService")
	private final MedAffiInCommonService medAffiInCommonService = null;
	
	@Resource(name = "medAffiOutCommonService")
	private final MedAffiOutCommonService medAffiOutCommonService = null;
   
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckMainPage", method = RequestMethod.GET)
	public String medAffiCheckMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/check/medAffiCheckMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckMainAddPage", method = RequestMethod.GET)
	public String medAffiCheckMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/affi/check/medAffiCheckMainAdd";

	}
	
	/**
	 * @Description 仓库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckMainByStorePage", method = RequestMethod.GET)
	public String medCheckByStorePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
	
		return "hrp/med/affi/check/medAffiCheckMainByStore";

	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/addMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String medJson;
		try {
			medJson = medAffiCheckMainService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckMainUpdatePage", method = RequestMethod.GET)
	public String medAffiCheckMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
				
		MedAffiCheckMain medAffiCheckMain = new MedAffiCheckMain();
    
		medAffiCheckMain = medAffiCheckMainService.queryMedAffiCheckMainByCode(mapVo);
		
		mode.addAttribute("hos_id", medAffiCheckMain.getHos_id());
		mode.addAttribute("group_id", medAffiCheckMain.getGroup_id());
		mode.addAttribute("copy_code", medAffiCheckMain.getCopy_code());
		mode.addAttribute("check_id", medAffiCheckMain.getCheck_id());
		mode.addAttribute("check_code", medAffiCheckMain.getCheck_code());
		mode.addAttribute("store_id", medAffiCheckMain.getStore_id());
		mode.addAttribute("store_no", medAffiCheckMain.getStore_no());
		mode.addAttribute("store_code", medAffiCheckMain.getStore_code());
		mode.addAttribute("store_name", medAffiCheckMain.getStore_name());
		mode.addAttribute("dept_id", medAffiCheckMain.getDept_id());
		mode.addAttribute("dept_no", medAffiCheckMain.getDept_no());
		mode.addAttribute("dept_code", medAffiCheckMain.getDept_code());
		mode.addAttribute("dept_name", medAffiCheckMain.getDept_name());
		mode.addAttribute("create_date", DateUtil.dateToString(medAffiCheckMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("check_date", DateUtil.dateToString(medAffiCheckMain.getCheck_date(), "yyyy-MM-dd"));
		mode.addAttribute("emp_id", medAffiCheckMain.getEmp_id());
		mode.addAttribute("emp_code", medAffiCheckMain.getEmp_code());
		mode.addAttribute("emp_name", medAffiCheckMain.getEmp_name());
		mode.addAttribute("maker", medAffiCheckMain.getMaker());
		mode.addAttribute("checker", medAffiCheckMain.getChecker());
		mode.addAttribute("state", medAffiCheckMain.getState());
		mode.addAttribute("brif", medAffiCheckMain.getBrif());
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/check/medAffiCheckMainUpdate";
	}
	
	/**
	 * @Description 更新页面查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/queryMedCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCheckDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiCheckMainService.queryMedAffiCheckDetailByCheckID(getPage(mapVo));

		return JSONObject.parseObject(medJson);

	}
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/updateMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatemedAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String medJson;
		try {
			medJson = medAffiCheckMainService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/addOrUpdateMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medAffiCheckMainJson ="";
		

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
	  
		medAffiCheckMainJson = medAffiCheckMainService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(medAffiCheckMainJson);
	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/deleteMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String medJson;
		try {
			medJson = medAffiCheckMainService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
	  
		return JSONObject.parseObject(medJson);
		

	}
	
	/**
	 * @Description 消审数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/unAuditMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		String medJson;
		try {
			medJson = medAffiCheckMainService.unAuditMedAffiCheckMain(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * @Description 审核数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/auditMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String medJson;
		try {
			medJson = medAffiCheckMainService.auditMedAffiCheckMain(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/queryMedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(mapVo.get("create_date_start") != null && !"".equals(mapVo.get("create_date_start").toString())){
			mapVo.put("create_date_start", DateUtil.stringToDate(mapVo.get("create_date_start").toString(), "yyyy-MM-dd"));
		}
		
		if(mapVo.get("create_date_end") != null && !"".equals(mapVo.get("create_date_end").toString())){
			mapVo.put("create_date_end", DateUtil.stringToDate(mapVo.get("create_date_end").toString(), "yyyy-MM-dd"));
		}
				
		String medAffiCheckMain = medAffiCheckMainService.queryMedAffiCheckMain(getPage(mapVo));

		return JSONObject.parseObject(medAffiCheckMain);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/queryMedAffiCheckMainByMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiCheckMainByMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medAffiCheckMain = medAffiCheckMainService.queryMedAffiCheckMainByMedInv(mapVo);
		//String medAffiCheckMain = medAffiCheckMainService.queryMedAffiCheckMainByMedInv(getPage(mapVo));

		return JSONObject.parseObject(medAffiCheckMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckMainImportPage", method = RequestMethod.GET)
	public String medAffiCheckMainImportPage(Model mode) throws Exception {

		return "hrp/med/affi/check/medAffiCheckMainImport";

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
	 @RequestMapping(value="hrp/med/affi/check/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate",".xls");
	    
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
	@RequestMapping(value="/hrp/med/affi/check/readMedAffiCheckMainFiles",method = RequestMethod.POST)  
    public String readmedAffiCheckMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedAffiCheckMain> list_err = new ArrayList<MedAffiCheckMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedAffiCheckMain medAffiCheckMain = new MedAffiCheckMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medAffiCheckMain.setCheck_id(Long.valueOf(temp[3]));
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
						
					medAffiCheckMain.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medAffiCheckMain.setStore_no(Long.valueOf(temp[6]));
		    		mapVo.put("store_no", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medAffiCheckMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medAffiCheckMain.setDept_no(Long.valueOf(temp[8]));
		    		mapVo.put("dept_no", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medAffiCheckMain.setCheck_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("check_date", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medAffiCheckMain.setEmp_id(Long.valueOf(temp[10]));
		    		mapVo.put("emp_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medAffiCheckMain.setMaker(Long.valueOf(temp[11]));
		    		mapVo.put("maker", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medAffiCheckMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medAffiCheckMain.setState(Integer.valueOf(temp[13]));
		    		mapVo.put("state", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
		    		mapVo.put("brif", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MedAffiCheckMain data_exc_extis = medAffiCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medAffiCheckMain.setError_type(err_sb.toString());
					
					list_err.add(medAffiCheckMain);
					
				} else {
			  
					String dataJson = medAffiCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedAffiCheckMain data_exc = new MedAffiCheckMain();
			
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
	@RequestMapping(value = "/hrp/med/affi/check/addBatchmedAffiCheckMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchmedAffiCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedAffiCheckMain> list_err = new ArrayList<MedAffiCheckMain>();
		
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
			
			MedAffiCheckMain medAffiCheckMain = new MedAffiCheckMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_id"))) {
						
					medAffiCheckMain.setCheck_id(Long.valueOf((String)jsonObj.get("check_id")));
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
						
					medAffiCheckMain.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					medAffiCheckMain.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					medAffiCheckMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					medAffiCheckMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					medAffiCheckMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
						
					medAffiCheckMain.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
		    		mapVo.put("emp_id", jsonObj.get("emp_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medAffiCheckMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medAffiCheckMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medAffiCheckMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MedAffiCheckMain data_exc_extis = medAffiCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medAffiCheckMain.setError_type(err_sb.toString());
					
					list_err.add(medAffiCheckMain);
					
				} else {
			  
					String dataJson = medAffiCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedAffiCheckMain data_exc = new MedAffiCheckMain();
			
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
	@RequestMapping(value = "/hrp/med/affi/check/createMedAffiInOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createMedAffiInOut(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String medJson;
		try {
			medJson = medAffiCheckMainService.createInOut(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	}
    
	/**
	 * 盘盈单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/med/affi/check/medAffiInPage", method = RequestMethod.GET)
	public String medAffiInPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> medAffiIn = medAffiInCommonService.queryByCode(mapVo);
		
		if(medAffiIn.get("in_date") != null && !"".equals(medAffiIn.get("in_date"))){
			medAffiIn.put("in_date", DateUtil.dateToString((Date)medAffiIn.get("in_date"), "yyyy-MM-dd"));
		}
		if(medAffiIn.get("make_date") != null && !"".equals(medAffiIn.get("make_date"))){
			medAffiIn.put("make_date", DateUtil.dateToString((Date)medAffiIn.get("make_date"), "yyyy-MM-dd"));
		}
		if(medAffiIn.get("check_date") != null && !"".equals(medAffiIn.get("check_date"))){
			medAffiIn.put("check_date", DateUtil.dateToString((Date)medAffiIn.get("check_date"), "yyyy-MM-dd"));
		}
		if(medAffiIn.get("confirm_date") != null && !"".equals(medAffiIn.get("confirm_date"))){
			medAffiIn.put("confirm_date", DateUtil.dateToString((Date)medAffiIn.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medAffiIn", medAffiIn);
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		return "hrp/med/affi/check/medAffiInPage";
	}
	
	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/queryMedAffiInCommonDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInCommonDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiInCommonService.queryDetailByCode(mapVo);

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 盘亏单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/medAffiOutPage", method = RequestMethod.GET)
	public String medAffiOutPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> medAffiOutMain = medAffiOutCommonService.queryByCode(mapVo);
		
		Map<String, Object> medAffiOutId = medAffiOutCommonService.queryAffiOutIds(mapVo);
		
		String medOutDetail = medAffiOutCommonService.queryAffiOutDetailById(getPage(mapVo));
		
		mode.addAttribute("store_id", medAffiOutMain.get("store_id").toString()+","+medAffiOutMain.get("store_no").toString());
		mode.addAttribute("store_code", medAffiOutMain.get("store_code").toString()+" "+medAffiOutMain.get("store_name").toString());
		mode.addAttribute("dept_id", medAffiOutMain.get("dept_id").toString()+","+medAffiOutMain.get("dept_no").toString());
		mode.addAttribute("dept_code", medAffiOutMain.get("dept_code").toString()+" "+medAffiOutMain.get("dept_name").toString());
		mode.addAttribute("dept_emp", medAffiOutMain.get("dept_emp").toString()+","+medAffiOutMain.get("emp_no").toString());
		mode.addAttribute("dept_emp_code", medAffiOutMain.get("dept_emp_code").toString()+" "+medAffiOutMain.get("dept_emp_name").toString());
		mode.addAttribute("out_id", medAffiOutMain.get("out_id"));
		mode.addAttribute("out_no", medAffiOutMain.get("out_no"));
		mode.addAttribute("state", medAffiOutMain.get("state"));
		mode.addAttribute("group_id", medAffiOutMain.get("group_id"));
		mode.addAttribute("hos_id", medAffiOutMain.get("hos_id"));
		mode.addAttribute("copy_code", medAffiOutMain.get("copy_code"));
		mode.addAttribute("is_dir", medAffiOutMain.get("is_dir"));
		mode.addAttribute("out_date", medAffiOutMain.get("out_date"));
		mode.addAttribute("brief", medAffiOutMain.get("brief"));
		mode.addAttribute("out_idUp", medAffiOutId.get("out_idUp"));
		mode.addAttribute("out_idNext", medAffiOutId.get("out_idNext"));
		mode.addAttribute("use_code", medAffiOutMain.get("use_code"));
		mode.addAttribute("proj_id", medAffiOutMain.get("proj_id"));
		mode.addAttribute("bus_type_code", medAffiOutMain.get("bus_type_code"));
		
		mode.addAttribute("medOutDetail", medOutDetail);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/affi/check/medAffiOutPage";

	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/medAffiCheckPrintSetPage", method = RequestMethod.GET)
	public String medAffiCheckPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 盘点单模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/check/queryMedAffiCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medAffiCheckMainService.queryMedAffiCheckByPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * 引入仓库药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/check/queryMedAffiStoreInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiStoreInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = medAffiCheckMainService.queryMedAffiStoreInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
}

