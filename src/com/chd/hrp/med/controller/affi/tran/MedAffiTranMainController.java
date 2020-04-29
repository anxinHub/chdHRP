/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.affi.tran;
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
import org.jsoup.helper.DataUtil;
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
import com.chd.hrp.med.entity.MedAffiTranMain;
import com.chd.hrp.med.entity.MedTranMain;
import com.chd.hrp.med.service.affi.out.MedAffiOutCommonService;
import com.chd.hrp.med.service.affi.tran.MedAffiTranMainService;
/**
 * 
 * @Description:
 * 代销调拨单
 * @Table:
 * MED_AFFI_TRAN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedAffiTranMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedAffiTranMainController.class);
	
	//引入Service服务
	@Resource(name = "medAffiTranMainService")
	private final MedAffiTranMainService medAffiTranMainService = null;
   
	@Resource(name = "medAffiOutCommonService")
	private final MedAffiOutCommonService medAffiOutCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainPage", method = RequestMethod.GET)
	public String medAffiTranMainPage(Model mode) throws Exception {	
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08025", MyConfig.getSysPara("08025"));
		
		return "hrp/med/affi/tran/medAffiTranMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainAddPage", method = RequestMethod.GET)
	public String medAffiTranMainAddPage(Model mode) throws Exception {
		mode.addAttribute("hos_id", SessionManager.getHosId());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/tran/medAffiTranMainAdd";

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
	@RequestMapping(value = "/hrp/med/affi/tran/addMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("make_date", DateUtil.dateToString(new Date(), "yyyy/MM/dd"));      
		
		String medJson = medAffiTranMainService.add(mapVo);
		
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
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainUpdatePage", method = RequestMethod.GET)
	public String MedAffiTranMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		 Map<String,Object> medAffiTranMain = medAffiTranMainService.queryMainByCode(mapVo);
		 mode.addAttribute("medAffiTranMain", medAffiTranMain);	
		 
		 
		 mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		 mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		 mode.addAttribute("p08020", MyConfig.getSysPara("08020"));
		 mode.addAttribute("p08025", MyConfig.getSysPara("08025"));
		
		return "hrp/med/affi/tran/medAffiTranMainUpdate";
	}
		
	/**
	 * 修改页面查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedAffiTranDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAffiTranMainService.queryMedAffiTranDetailByTranID(getPage(mapVo));

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
	@RequestMapping(value = "/hrp/med/affi/tran/updateMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		String medJson = medAffiTranMainService.update(mapVo);
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
	@RequestMapping(value = "/hrp/med/affi/tran/addOrUpdateMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medAffiTranMainJson ="";
		

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
			
			try {
				medAffiTranMainJson = medAffiTranMainService.addOrUpdate(detailVo);
			} catch (Exception e) {
				medAffiTranMainJson = e.getMessage();
				e.printStackTrace();
			}
			
		}
		return JSONObject.parseObject(medAffiTranMainJson);
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
	@RequestMapping(value = "/hrp/med/affi/tran/deleteMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedAffiTranMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("rela_type", "4");
				mapVo.put("rela_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String medJson;
			try {
				medJson = medAffiTranMainService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		StringBuffer sql = new StringBuffer();		
		String tran_date_start = String.valueOf(mapVo.get("tran_date_start"));		
		if(tran_date_start!=null && !"".equals(tran_date_start) && !"null".equals(tran_date_start)){			
			sql.append("and mtm.tran_date >= to_date('"+tran_date_start+"','yyyy/MM/dd') ");			
		}

		String tran_date_end = String.valueOf(mapVo.get("tran_date_end"));		
		if(tran_date_end!=null && !"".equals(tran_date_end) && !"null".equals(tran_date_end)){			
			sql.append("and mtm.tran_date <= to_date('"+tran_date_end+"','yyyy/MM/dd') ");			
		}
		
		String check_date_start = String.valueOf(mapVo.get("check_date_start"));		
		if(check_date_start!=null && !"".equals(check_date_start) && !"null".equals(check_date_start)){			
			sql.append("and mtm.check_date >= to_date('"+check_date_start+"','yyyy/MM/dd') ");			
		}

		String check_date_end = String.valueOf(mapVo.get("check_date_end"));		
		if(check_date_end!=null && !"".equals(check_date_end) && !"null".equals(check_date_end)){			
			sql.append("and mtm.check_date <= to_date('"+check_date_end+"','yyyy/MM/dd') ");			
		}
		
		String confirm_date_start = String.valueOf(mapVo.get("confirm_date_start"));		
		if(confirm_date_start!=null && !"".equals(confirm_date_start) && !"null".equals(confirm_date_start)){			
			sql.append("and mtm.confirm_date >= to_date('"+confirm_date_start+"','yyyy/MM/dd') ");			
		}

		String confirm_date_end = String.valueOf(mapVo.get("confirm_date_end"));		
		if(confirm_date_end!=null && !"".equals(confirm_date_end) && !"null".equals(confirm_date_end)){			
			sql.append("and mtm.confirm_date <= to_date('"+confirm_date_end+"','yyyy/MM/dd') ");			
		}
			
		mapVo.put("sql", sql.toString());
		String medAffiTranMain = medAffiTranMainService.queryMedAffiTranMain(getPage(mapVo));

		return JSONObject.parseObject(medAffiTranMain);
		
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainBySinglePage", method = RequestMethod.GET)
	public String medAffiTranMainBySinglePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			String[] paraArray = paras.split("@");
			if(!"null".equals(paraArray[0])){
				mode.addAttribute("store_id", paraArray[0]);
			}
		}

		return "hrp/med/affi/tran/medAffiTranMainBySingle";

	}
	
	/**
	 * @Description 整单出库主表查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedInMainBySingle", method = RequestMethod.POST)
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
		if (mapVo.get("in_date_start") != null) {
			mapVo.put("in_date_start", DateUtil.stringToDate(mapVo.get("in_date_start").toString(), "yyyy-MM-dd"));
			
		}
		if (mapVo.get("in_date_end") != null) {
			mapVo.put("in_date_end",  DateUtil.stringToDate(mapVo.get("in_date_end").toString(), "yyyy-MM-dd"));
			
		}
	
		String medOutMain = medAffiTranMainService.queryMedInMainBySingle(getPage(mapVo));
		return JSONObject.parseObject(medOutMain);

	}
	
	/**
	 * @Description 整单调拨明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedInDetailBySingle", method = RequestMethod.POST)
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
		String medOutMain = medAffiTranMainService.queryMedInDetailBySingle(getPage(mapVo));
		return JSONObject.parseObject(medOutMain);

	}
	/**
	 * @Description 组装整单调拨药品数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedAffiTranDetailBySingle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranDetailBySingle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medOutMain = medAffiTranMainService.queryMedAffiTranDetailBySingle(getPage(mapVo));

		return JSONObject.parseObject(medOutMain);
	}
	/**
	 * @Description 调出确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/outConfirmMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> outConfirmMedAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Date  date = new Date();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiTranMainService.outConfirmMedAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/med/affi/tran/unOutConfirmMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unOutConfirmMedAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_id", ids[3]);			
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiTranMainService.unOutConfirmMedAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/med/affi/tran/inConfirmMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inConfirmMedAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			medJson = medAffiTranMainService.inConfirmMedAffiTranMain(listVo);
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
	@RequestMapping(value = "/hrp/med/affi/tran/balanceConfirmMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> balanceConfirmMedAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		
		String medJson = medAffiTranMainService.balanceConfirmMedAffiTranMain(listVo);
		
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
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedAffiTranMainByMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranMainByMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medCheckMain = medAffiTranMainService.queryMedAffiTranMainByMedInv(getPage(mapVo));

		return JSONObject.parseObject(medCheckMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 调拨方式TRAN_METHOD：1同价调拨  2 异价调拨
调拨类型：1 院内调拨  2 院外调拨
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainImportPage", method = RequestMethod.GET)
	public String medAffiTranMainImportPage(Model mode) throws Exception {

		return "hrp/med/affi/tran/medAffiTranMainImport";

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
	 @RequestMapping(value="hrp/med/affi/tran/downTemplate", method = RequestMethod.GET)  
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
	@RequestMapping(value="/hrp/med/affi/tran/readMedAffiTranMainFiles",method = RequestMethod.POST)  
    public String readMedAffiTranMainFiles(Plupload plupload,HttpServletRequest request, HttpServletResponse response,Model mode) throws IOException { 
			 
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
	@RequestMapping(value = "/hrp/med/affi/tran/addBatchMedAffiTranMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedAffiTranMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedAffiTranMain> list_err = new ArrayList<MedAffiTranMain>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiOutMainDirPage", method = RequestMethod.GET)
	public String medAffiOutMainDirPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("store_id", paraArray[0]);
				
			}
		}

		return "hrp/med/affi/tran/medAffiOutMainDir";

	}
	
	/**
	 * @Description 配套页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiOutMainMatchedPage", method = RequestMethod.GET)
	public String medAffiOutMainMatchedPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("dept_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
				
				
				mode.addAttribute("store_id", paraArray[1]);
				
			}
		}
		
		return "hrp/med/affi/tran/medAffiOutMainMatched";

	}
	
	/**
	 * @Description 历史导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiOutMainHistoryPage", method = RequestMethod.GET)
	public String medAffiOutMainHistoryPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
			
			String[] paraArray = paras.split("@");
			
			if(!"null".equals(paraArray[0])){
				
				mode.addAttribute("dept_id", paraArray[0]);
				
			}
			
			if(!"null".equals(paraArray[1])){
				
				
				mode.addAttribute("store_id", paraArray[1]);
				
			}
		}

		return "hrp/med/affi/tran/medAffiOutMainHistory";

	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranPrintSetPage", method = RequestMethod.GET)
	public String medAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/affi/tran/queryMedAffiTranByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medAffiTranMainService.queryMedAffiTranByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
	/**
	 * 添加页面选择药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/medAffiTranMainChoiceInvPage", method = RequestMethod.GET)
	public String medAffiTranMainChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/affi/tran/medAffiTranMainChoiceInv";
	}
	/**
	 * 选择药品页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryAffiTranInvBatchList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiTranInvBatchList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medOutDetail = medAffiOutCommonService.queryMedAffiInvBatchList(getPage(mapVo));

		return JSONObject.parseObject(medOutDetail);
	}
	
	/**
	 * @Description 选择药品返回List
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/tran/queryAffiTranInvListByChoiceInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiTranInvListByChoiceInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			medOutDetail = medAffiOutCommonService.queryAffiOutInvListByChoiceInv(listVo);
		} catch (Exception e) {
			medOutDetail = e.getMessage();
		}

		return JSONObject.parseObject(medOutDetail);
	}

}

