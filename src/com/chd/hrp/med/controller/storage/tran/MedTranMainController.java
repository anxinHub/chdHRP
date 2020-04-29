/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.storage.tran;
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
import com.chd.hrp.med.entity.MedTranMain;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.out.MedOutMainService;
import com.chd.hrp.med.service.storage.tran.MedTranMainService;
/**
 * 
 * @Description:
 * 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
 * @Table:
 * MED_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedTranMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedTranMainController.class);
	
	//引入Service服务
	@Resource(name = "medTranMainService")
	private final MedTranMainService medTranMainService = null;
	@Resource(name = "medOutMainService")
	private final MedOutMainService medOutMainService = null;
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainMainPage", method = RequestMethod.GET)
	public String medTranMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));

		return "hrp/med/storage/tran/medTranMainMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainAddPage", method = RequestMethod.GET)
	public String medTranMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/tran/medTranMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/addMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("maker", SessionManager.getUserId());

		String medJson;
		try {
			medJson = medTranMainService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
		调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainUpdatePage", method = RequestMethod.GET)
	public String medTranMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){mapVo.put("group_id", SessionManager.getGroupId());}
		
		if(mapVo.get("hos_id") == null){mapVo.put("hos_id", SessionManager.getHosId());}
		
		if(mapVo.get("copy_code") == null){mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}	
	
		 MedTranMain medTranMain = medTranMainService.queryMedTranMainByCode(mapVo);
		
		mode.addAttribute("group_id", medTranMain.getGroup_id());
		mode.addAttribute("hos_id", medTranMain.getHos_id());
		mode.addAttribute("copy_code", medTranMain.getCopy_code());
		mode.addAttribute("tran_id", medTranMain.getTran_id());
		mode.addAttribute("tran_no", medTranMain.getTran_no());
		mode.addAttribute("tran_method", medTranMain.getTran_method());
		mode.addAttribute("tran_type", medTranMain.getTran_type());
		mode.addAttribute("out_hos_id", medTranMain.getOut_hos_id());
		mode.addAttribute("out_hos_code", medTranMain.getOut_hos_code());
		mode.addAttribute("out_hos_name", medTranMain.getOut_hos_name());
		mode.addAttribute("out_store_id", medTranMain.getOut_store_id());
		mode.addAttribute("out_store_no", medTranMain.getOut_store_no());
		mode.addAttribute("out_store_code", medTranMain.getOut_store_code());
		mode.addAttribute("out_store_name", medTranMain.getOut_store_name());
		mode.addAttribute("in_hos_id", medTranMain.getIn_hos_id());
		mode.addAttribute("in_hos_code", medTranMain.getIn_hos_code());
		mode.addAttribute("in_hos_name", medTranMain.getIn_hos_name());
		mode.addAttribute("in_store_id", medTranMain.getIn_store_id());
		mode.addAttribute("in_store_no", medTranMain.getIn_store_no());
		mode.addAttribute("in_store_code", medTranMain.getIn_store_code());
		mode.addAttribute("in_store_name", medTranMain.getIn_store_name());
		mode.addAttribute("brief", medTranMain.getBrief());
		mode.addAttribute("tran_date", DateUtil.dateToString(medTranMain.getTran_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", medTranMain.getState());
	
		Integer is_apply  = medTranMainService.queryMedTranMainIsApply(mapVo);
		if(is_apply>0){
			mode.addAttribute("is_apply", "1");
		}else{
			mode.addAttribute("is_apply", "0");
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));

		return "hrp/med/storage/tran/medTranMainUpdate";
	}

	/**
	 * @Description 加载明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String medTranDetail = medTranMainService.queryMedTranDetailByTranID(getPage(mapVo));

		return JSONObject.parseObject(medTranDetail);
	}
		
	/**
	 * @Description 
	 * 更新数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
		调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/updateMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
			medJson = medTranMainService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		/*if(medJson.contains("true")){
			medTranMainService.updateMedTranOutRela(mapVo);
		}*/
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/addOrUpdateMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medTranMainJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
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
	  
		medTranMainJson = medTranMainService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(medTranMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/deleteMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedTranMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("tran_id", ids[3]);
				//用于删除与申请单之前的对应关系
				mapVo.put("rela_type", "2");
				mapVo.put("rela_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }

		String medJson;
		try {
			medJson = medTranMainService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
	    
	  return JSONObject.parseObject(medJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//转换日期格式
		if(mapVo.get("begin_tran_date") != null && !"".equals(mapVo.get("begin_tran_date"))){
			mapVo.put("begin_tran_date", DateUtil.stringToDate(mapVo.get("begin_tran_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_tran_date") != null && !"".equals(mapVo.get("end_tran_date"))){
			mapVo.put("end_tran_date", DateUtil.stringToDate(mapVo.get("end_tran_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_check_date") != null && !"".equals(mapVo.get("begin_check_date"))){
			mapVo.put("begin_check_date", DateUtil.stringToDate(mapVo.get("begin_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_check_date") != null && !"".equals(mapVo.get("end_check_date"))){
			mapVo.put("end_check_date", DateUtil.stringToDate(mapVo.get("end_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}
		
		String medTranJson = null; 
		
		if("0".equals(mapVo.get("show_detail"))){
			medTranJson = medTranMainService.queryMedTranMain(getPage(mapVo));
		}else{
			medTranJson = medTranMainService.queryMedTranDetails(getPage(mapVo));
		}

		return JSONObject.parseObject(medTranJson);
		
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainBySinglePage", method = RequestMethod.GET)
	public String medTranMainBySinglePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
		 
		if(paras != null && !"".equals(paras)){
			
			String[] paraArray = paras.split(","); 
			if(!"null".equals(paraArray[0])){
				mode.addAttribute("store_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
				mode.addAttribute("store_code", paraArray[1]);
			}
			
			if(!"null".equals(paraArray[2])){
				mode.addAttribute("store_name", paraArray[2]);
			}
			
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/tran/medTranMainBySingle";

	}
	
	/**
	 * @Description 查询数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedInMainBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInMainBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		StringBuffer sql = new StringBuffer();
		
		String in_date_start = String.valueOf(mapVo.get("in_date_start"));
		
		if(in_date_start!=null && !"".equals(in_date_start) && !"null".equals(in_date_start)){
			
			sql.append("and in_date > to_date('"+in_date_start+"','yyyy/MM/dd') ");
			
		}

		String in_date_end = String.valueOf(mapVo.get("in_date_end"));
		
		if(in_date_end!=null && !"".equals(in_date_end) && !"null".equals(in_date_end)){
			
			sql.append("and in_date < to_date('"+in_date_end+"','yyyy/MM/dd') ");
			
		}

		mapVo.put("sql", sql.toString());
		
		String medOutMain = medTranMainService.queryMedInMainBySingle(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);

	}
	
	/**
	 * @Description 查询数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedInDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String medOutMain = medTranMainService.queryMedInDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);

	}
	
	/**
	 * @Description 组装整单调拨药品数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String medOutMain = medTranMainService.queryMedTranDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}
	
	/**
	 * @Description 调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/outConfirmMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> outConfirmMedTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			
			mapVo.put("checker", SessionManager.getUserId());
			
			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));

			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medTranMainService.confirmOutMedTranMain(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * @Description 取消调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/unOutConfirmMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unOutConfirmMedTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("checker", "");
			
			mapVo.put("check_date", "");
			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medTranMainService.unConfirmOutMedTranMain(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * @Description 调入确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/inConfirmMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inConfirmMedTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String user_id = SessionManager.getUserId();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("state", "3");
			mapVo.put("year", year);
			mapVo.put("month", month);
			mapVo.put("confirmer", user_id);
			mapVo.put("confirm_date", date);
			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medTranMainService.confirmInMedTranMain(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * @Description 冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/balanceConfirmMedTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceConfirmMedTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			
			mapVo.put("tran_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));
			
			mapVo.put("maker", SessionManager.getUserId());
			
			listVo.add(mapVo);

		}

		String medJson;
		try {
			medJson = medTranMainService.offsetMedTranMain(listVo);
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
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranMainByMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranMainByMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String medCheckMain = medTranMainService.queryMedTranMainByMedInv(getPage(mapVo));

		return JSONObject.parseObject(medCheckMain);
		
	}

	/**
	 * @Description 查看出库单跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainUpdateOutPage", method = RequestMethod.GET)
	public String medTranMainUpdateOutPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
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

		return "hrp/med/storage/tran/medTranMainOut";
	}

	/**
	 * @Description 
	 * 查看入库单跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainUpdateInPage", method = RequestMethod.GET)
	public String medTranMainUpdateInPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/med/storage/tran/medTranMainIn";
	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranPrintSetPage", method = RequestMethod.GET)
	public String medTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		System.out.println("=============="+mapVo.get("p_num"));
		String reJson=null;
		try {
			reJson=medTranMainService.queryMedTranByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainImportPage", method = RequestMethod.GET)
	public String medTranMainImportPage(Model mode) throws Exception {

		return "hrp/med/storage/tran/medTranMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/storage/tran/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","调拨方式TRAN_METHOD：1同价调拨  2 异价调拨调拨类型：1 院内调拨  2 院外调拨.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/storage/tran/readMedTranMainFiles",method = RequestMethod.POST)  
    public String readMedTranMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedTranMain> list_err = new ArrayList<MedTranMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedTranMain medTranMain = new MedTranMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medTranMain.setTran_id(Long.valueOf(temp[3]));
		    		mapVo.put("tran_id", temp[3]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
		    		mapVo.put("tran_no", temp[4]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
		    		mapVo.put("year", temp[5]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
		    		mapVo.put("month", temp[6]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medTranMain.setTran_method(Integer.valueOf(temp[7]));
		    		mapVo.put("tran_method", temp[7]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medTranMain.setTran_type(Integer.valueOf(temp[8]));
		    		mapVo.put("tran_type", temp[8]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medTranMain.setOut_hos_id(Long.valueOf(temp[9]));
		    		mapVo.put("out_hos_id", temp[9]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medTranMain.setOut_store_id(Long.valueOf(temp[10]));
		    		mapVo.put("out_store_id", temp[10]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medTranMain.setOut_store_no(Long.valueOf(temp[11]));
		    		mapVo.put("out_store_no", temp[11]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medTranMain.setIn_hos_id(Long.valueOf(temp[12]));
		    		mapVo.put("in_hos_id", temp[12]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medTranMain.setIn_store_id(Long.valueOf(temp[13]));
		    		mapVo.put("in_store_id", temp[13]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medTranMain.setIn_store_no(Long.valueOf(temp[14]));
		    		mapVo.put("in_store_no", temp[14]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
		    		mapVo.put("brief", temp[15]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					medTranMain.setTran_date(DateUtil.stringToDate(temp[16]));
		    		mapVo.put("tran_date", temp[16]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					medTranMain.setMaker(Long.valueOf(temp[17]));
		    		mapVo.put("maker", temp[17]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					medTranMain.setChecker(Long.valueOf(temp[19]));
		    		mapVo.put("checker", temp[19]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[20])) {
						
					medTranMain.setCheck_date(DateUtil.stringToDate(temp[20]));
		    		mapVo.put("check_date", temp[20]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[21])) {
						
					medTranMain.setConfirmer(Long.valueOf(temp[21]));
		    		mapVo.put("confirmer", temp[21]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					medTranMain.setConfirm_date(DateUtil.stringToDate(temp[22]));
		    		mapVo.put("confirm_date", temp[22]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					medTranMain.setState(Integer.valueOf(temp[23]));
		    		mapVo.put("state", temp[23]);
					
					} else {
						
						err_sb.append("为空  ");
						
					}
					 
					
				MedTranMain data_exc_extis = medTranMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medTranMain.setError_type(err_sb.toString());
					
					list_err.add(medTranMain);
					
				} else {
			  
					String dataJson = medTranMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedTranMain data_exc = new MedTranMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/storage/tran/addBatchMedTranMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedTranMain> list_err = new ArrayList<MedTranMain>();
		
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
			
			MedTranMain medTranMain = new MedTranMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("tran_id"))) {
						
					medTranMain.setTran_id(Long.valueOf((String)jsonObj.get("tran_id")));
		    		mapVo.put("tran_id", jsonObj.get("tran_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_no"))) {
						
		    		mapVo.put("tran_no", jsonObj.get("tran_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_method"))) {
						
					medTranMain.setTran_method(Integer.valueOf((String)jsonObj.get("tran_method")));
		    		mapVo.put("tran_method", jsonObj.get("tran_method"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_type"))) {
						
					medTranMain.setTran_type(Integer.valueOf((String)jsonObj.get("tran_type")));
		    		mapVo.put("tran_type", jsonObj.get("tran_type"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_hos_id"))) {
						
					medTranMain.setOut_hos_id(Long.valueOf((String)jsonObj.get("out_hos_id")));
		    		mapVo.put("out_hos_id", jsonObj.get("out_hos_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_store_id"))) {
						
					medTranMain.setOut_store_id(Long.valueOf((String)jsonObj.get("out_store_id")));
		    		mapVo.put("out_store_id", jsonObj.get("out_store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("out_store_no"))) {
						
					medTranMain.setOut_store_no(Long.valueOf((String)jsonObj.get("out_store_no")));
		    		mapVo.put("out_store_no", jsonObj.get("out_store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_hos_id"))) {
						
					medTranMain.setIn_hos_id(Long.valueOf((String)jsonObj.get("in_hos_id")));
		    		mapVo.put("in_hos_id", jsonObj.get("in_hos_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_store_id"))) {
						
					medTranMain.setIn_store_id(Long.valueOf((String)jsonObj.get("in_store_id")));
		    		mapVo.put("in_store_id", jsonObj.get("in_store_id"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_store_no"))) {
						
					medTranMain.setIn_store_no(Long.valueOf((String)jsonObj.get("in_store_no")));
		    		mapVo.put("in_store_no", jsonObj.get("in_store_no"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("brief"))) {
						
		    		mapVo.put("brief", jsonObj.get("brief"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("tran_date"))) {
						
					medTranMain.setTran_date(DateUtil.stringToDate((String)jsonObj.get("tran_date")));
		    		mapVo.put("tran_date", jsonObj.get("tran_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medTranMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medTranMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("check_date"))) {
						
					medTranMain.setCheck_date(DateUtil.stringToDate((String)jsonObj.get("check_date")));
		    		mapVo.put("check_date", jsonObj.get("check_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirmer"))) {
						
					medTranMain.setConfirmer(Long.valueOf((String)jsonObj.get("confirmer")));
		    		mapVo.put("confirmer", jsonObj.get("confirmer"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_date"))) {
						
					medTranMain.setConfirm_date(DateUtil.stringToDate((String)jsonObj.get("confirm_date")));
		    		mapVo.put("confirm_date", jsonObj.get("confirm_date"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medTranMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("为空  ");
						
					}
					
					
				MedTranMain data_exc_extis = medTranMainService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medTranMain.setError_type(err_sb.toString());
					
					list_err.add(medTranMain);
					
				} else {
			  
					String dataJson = medTranMainService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedTranMain data_exc = new MedTranMain();
			
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
	 * 添加页面选择药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainChoiceInvPage", method = RequestMethod.GET)
	public String medTranMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/tran/medTranMainChoiceInv";
	}
	/**
	 * 添加页面选择条码药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/medTranMainChoiceInvBarPage", method = RequestMethod.GET)
	public String medTranMainChoiceInvBarPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/tran/medTranMainChoiceInvBar";
	}
	/**
	 * 选择药品页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryMedTranInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTranInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medOutDetail = medOutMainService.queryMedInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);
	}
	
	/**
	 * @Description 选择药品返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/tran/queryTranInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTranInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("store_id", ids[3]);
			mapVo.put("inv_id", ids[4]);
			mapVo.put("inv_code", ids[5]);
			mapVo.put("inv_name", ids[6]);
			mapVo.put("batch_no", ids[7]);
			mapVo.put("amount", ids[8]);

			listVo.add(mapVo);
		}
		
		String medOutDetail;
		try {
			medOutDetail = medOutMainService.queryOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(medOutDetail);
	}
}

