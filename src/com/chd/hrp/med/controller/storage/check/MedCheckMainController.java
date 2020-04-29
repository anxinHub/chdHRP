/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.storage.check;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedCheckMain;
import com.chd.hrp.med.entity.MedOutMain;
import com.chd.hrp.med.service.storage.check.MedCheckMainService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.out.MedOutMainService;
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
public class MedCheckMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedCheckMainController.class);
	
	//引入Service服务
	@Resource(name = "medCheckMainService")
	private final MedCheckMainService medCheckMainService = null;
   
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	// 引入Service服务
	@Resource(name = "medOutMainService")
	private final MedOutMainService medOutMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/medCheckMainMainPage", method = RequestMethod.GET)
	public String medCheckMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08027", MyConfig.getSysPara("08027"));
		mode.addAttribute("p08046", MyConfig.getSysPara("08046"));

		return "hrp/med/storage/check/medCheckMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/medCheckMainAddPage", method = RequestMethod.GET)
	public String medCheckMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/check/medCheckMainAdd";

	}
	
	/**
	 * @Description 仓库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/medCheckMainByStorePage", method = RequestMethod.GET)
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
		
		return "hrp/med/storage/check/medCheckMainByStore";
	}

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/addMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			medJson = medCheckMainService.add(mapVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/medCheckMainUpdatePage", method = RequestMethod.GET)
	public String medCheckMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	    if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		if(mapVo.get("copy_code") == null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		MedCheckMain medCheckMain = medCheckMainService.queryMedCheckMainByCode(mapVo);
		
		mode.addAttribute("hos_id", medCheckMain.getHos_id());
		mode.addAttribute("group_id", medCheckMain.getGroup_id());
		mode.addAttribute("copy_code", medCheckMain.getCopy_code());
		mode.addAttribute("check_id", medCheckMain.getCheck_id());
		mode.addAttribute("check_code", medCheckMain.getCheck_code());
		mode.addAttribute("store_id", medCheckMain.getStore_id());
		mode.addAttribute("store_no", medCheckMain.getStore_no());
		mode.addAttribute("store_code", medCheckMain.getStore_code());
		mode.addAttribute("store_name", medCheckMain.getStore_name());
		mode.addAttribute("dept_id", medCheckMain.getDept_id());
		mode.addAttribute("dept_no", medCheckMain.getDept_no());
		mode.addAttribute("dept_code", medCheckMain.getDept_code());
		mode.addAttribute("dept_name", medCheckMain.getDept_name());
		mode.addAttribute("create_date", DateUtil.dateToString(medCheckMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("check_date", DateUtil.dateToString(medCheckMain.getCheck_date(), "yyyy-MM-dd"));
		mode.addAttribute("emp_id", medCheckMain.getEmp_id());
		mode.addAttribute("emp_no", medCheckMain.getEmp_no());
		mode.addAttribute("emp_code", medCheckMain.getEmp_code());
		mode.addAttribute("emp_name", medCheckMain.getEmp_name());
		mode.addAttribute("maker", medCheckMain.getMaker());
		mode.addAttribute("checker", medCheckMain.getChecker());
		mode.addAttribute("state", medCheckMain.getState());
		mode.addAttribute("brif", medCheckMain.getBrif());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08046", MyConfig.getSysPara("08046"));
		
		return "hrp/med/storage/check/medCheckMainUpdate";
	}
	
	/**
	 * @Description 修改页面查明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/queryMedCheckDetailByCheckID", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCheckDetailByCheckID(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medCheckDetail = medCheckMainService.queryMedCheckDetailByCheckID(mapVo);
		
		return JSONObject.parseObject(medCheckDetail);
	}
	
	/**
	 * @Description 
	 * 更新数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/updateMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
			medJson = medCheckMainService.update(mapVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/addOrUpdateMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medJson ="";

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
	
			try {
				medJson = medCheckMainService.addOrUpdate(detailVo);
			} catch (Exception e) {
				medJson = e.getMessage();
			}
		}
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/deleteMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			medJson = medCheckMainService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/unAuditMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			medJson = medCheckMainService.unAuditMedCheckMain(listVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/auditMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			medJson = medCheckMainService.auditMedCheckMain(listVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/queryMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		//时间格式转换
		if(mapVo.get("create_date_start") != null && !"".equals(mapVo.get("create_date_start").toString())){
			mapVo.put("create_date_start", DateUtil.stringToDate(mapVo.get("create_date_start").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("create_date_end") != null && !"".equals(mapVo.get("create_date_end").toString())){
			mapVo.put("create_date_end", DateUtil.stringToDate(mapVo.get("create_date_end").toString(), "yyyy-MM-dd"));
		}

		String medCheckMain = medCheckMainService.queryMedCheckMain(getPage(mapVo));

		return JSONObject.parseObject(medCheckMain);
		
	}
	
	/**
	 * @Description 生成出入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/createInOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createInOut(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			medJson = medCheckMainService.addInOut(listVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/queryMedCheckMainByMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCheckMainByMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medCheckMain = medCheckMainService.queryMedCheckMainByMedInv(getPage(mapVo));

		return JSONObject.parseObject(medCheckMain);
		
	}
	
	/**
	 * 盘盈单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/medStorageInPage", method = RequestMethod.GET)
	public String medStorageInPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		Map<String, Object> medInMain = medStorageInService.queryByCode(mapVo);
		if(medInMain.get("in_date") != null && !"".equals(medInMain.get("in_date"))){
			medInMain.put("in_date", DateUtil.dateToString((Date)medInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("check_date") != null && !"".equals(medInMain.get("check_date"))){
			medInMain.put("check_date", DateUtil.dateToString((Date)medInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("confirm_date") != null && !"".equals(medInMain.get("confirm_date"))){
			medInMain.put("confirm_date", DateUtil.dateToString((Date)medInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medInMain", medInMain);
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		
		return "hrp/med/storage/check/medStorageInPage";
	}
	
	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/queryMedStorageInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = medStorageInService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * 盘亏单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/check/medStorageOutPage", method = RequestMethod.GET)
	public String medStorageOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}

		 Map<String, Object> medOutMain = medOutMainService.queryMedOutMainByCode(mapVo);
		 
		 if(medOutMain.get("out_date") != null && !"".equals(medOutMain.get("out_date").toString())){
			 medOutMain.put("out_date", DateUtil.dateToString((Date)medOutMain.get("out_date"), "yyyy-MM-dd"));
		 }
		 
		 mode.addAttribute("medOutMain", medOutMain);
		
		 String medOutDetail = medOutMainService.queryMedOutDetailByOutId(getPage(mapVo));
		 mode.addAttribute("medOutDetail", medOutDetail);
		 
		 mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		 mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		 mode.addAttribute("p08015", MyConfig.getSysPara("08015"));

		 return "hrp/med/storage/check/medStorageOutPage";
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/medCheckMainImportPage", method = RequestMethod.GET)
	public String medCheckMainImportPage(Model mode) throws Exception {

		return "hrp/med/storage/check/medCheckMainImport";

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
	 @RequestMapping(value="hrp/med/storage/check/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/med/storage/check/readMedCheckMainFiles",method = RequestMethod.POST)  
    public String readMedCheckMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedCheckMain> list_err = new ArrayList<MedCheckMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedCheckMain medCheckMain = new MedCheckMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medCheckMain.setCheck_id(Long.valueOf(temp[3]));
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
						
					medCheckMain.setStore_id(Long.valueOf(temp[5]));
		    		mapVo.put("store_id", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medCheckMain.setStore_no(Long.valueOf(temp[6]));
		    		mapVo.put("store_no", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medCheckMain.setDept_id(Long.valueOf(temp[7]));
		    		mapVo.put("dept_id", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medCheckMain.setDept_no(Long.valueOf(temp[8]));
		    		mapVo.put("dept_no", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medCheckMain.setCheck_date(DateUtil.stringToDate(temp[9]));
		    		mapVo.put("check_date", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medCheckMain.setEmp_id(Long.valueOf(temp[10]));
		    		mapVo.put("emp_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medCheckMain.setMaker(Long.valueOf(temp[11]));
		    		mapVo.put("maker", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medCheckMain.setChecker(Long.valueOf(temp[12]));
		    		mapVo.put("checker", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medCheckMain.setState(Integer.valueOf(temp[13]));
		    		mapVo.put("state", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
		    		mapVo.put("brif", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MedCheckMain data_exc_extis = medCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medCheckMain.setError_type(err_sb.toString());
					
					list_err.add(medCheckMain);
					
				} else {
			  
					String dataJson = medCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedCheckMain data_exc = new MedCheckMain();
			
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
	@RequestMapping(value = "/hrp/med/storage/check/addBatchMedCheckMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedCheckMain> list_err = new ArrayList<MedCheckMain>();
		
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
			
			MedCheckMain medCheckMain = new MedCheckMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("check_id"))) {
						
					medCheckMain.setCheck_id(Long.valueOf((String)jsonObj.get("check_id")));
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
						
					medCheckMain.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					medCheckMain.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					medCheckMain.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					medCheckMain.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					medCheckMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("emp_id"))) {
						
					medCheckMain.setEmp_id(Long.valueOf((String)jsonObj.get("emp_id")));
		    		mapVo.put("emp_id", jsonObj.get("emp_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medCheckMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medCheckMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medCheckMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brif"))) {
						
		    		mapVo.put("brif", jsonObj.get("brif"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MedCheckMain data_exc_extis = medCheckMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medCheckMain.setError_type(err_sb.toString());
					
					list_err.add(medCheckMain);
					
				} else {
			  
					String dataJson = medCheckMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedCheckMain data_exc = new MedCheckMain();
			
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
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/check/medCheckPrintSetPage", method = RequestMethod.GET)
	public String medCheckPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/storage/check/queryMedCheckByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCheckByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medCheckMainService.queryMedCheckByPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/med/storage/check/queryMedStoreInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStoreInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = medCheckMainService.queryMedStoreInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	
}

