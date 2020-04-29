/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.scrap;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.scrap.MatDuraScrapStoreService;

/**
 * 
 * @Description: 耐用品库房报废 
 * @Table: MAT_DURA_STORE_(SCRAP/SCRAP_D) 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDuraScrapStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraScrapStoreController.class);

	// 引入Service服务
	@Resource(name = "matDuraScrapStoreService")
	private final MatDuraScrapStoreService matDuraScrapStoreService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/mainPage", method = RequestMethod.GET)
	public String matDuraScrapStoreMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/scrap/store/main";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/addPage", method = RequestMethod.GET)
	public String matDuraScrapStoreAddPage(Model mode) throws Exception {
       
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("user_id", SessionManager.getUserId());
		mode.addAttribute("user_msg", matCommonService.getLoginUserMsg(map));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/dura/scrap/store/add";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/queryMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//转换日期格式
		if(mapVo.get("begin_make_date") != null && !"".equals(mapVo.get("begin_make_date"))){
			mapVo.put("begin_make_date", DateUtil.stringToDate(mapVo.get("begin_make_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_make_date") != null && !"".equals(mapVo.get("end_make_date"))){
			mapVo.put("end_make_date", DateUtil.stringToDate(mapVo.get("end_make_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matDuraScrapStoreService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/queryMatDuraScrapStoreDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapStoreDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matDuraScrapStoreService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/queryMatDuraScrapStoreById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapStoreById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matDuraScrapStoreService.queryMainByCode(mapVo);
	}

	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/addMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDuraScrapStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			
			matJson = matDuraScrapStoreService.add(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/updatePage", method = RequestMethod.GET)
	public String matDuraScrapStoreUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matDuraStoreScrap = matDuraScrapStoreService.queryMainByCode(mapVo);
		
		//编制日期
		if(matDuraStoreScrap.get("make_date") != null && !"".equals(matDuraStoreScrap.get("make_date"))){
			matDuraStoreScrap.put("make_date", DateUtil.dateToString((Date)matDuraStoreScrap.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matDuraStoreScrap.get("check_date") != null && !"".equals(matDuraStoreScrap.get("check_date"))){
			matDuraStoreScrap.put("check_date", DateUtil.dateToString((Date)matDuraStoreScrap.get("check_date"), "yyyy-MM-dd"));
		}
		//确认日期
		if(matDuraStoreScrap.get("confirm_date") != null && !"".equals(matDuraStoreScrap.get("confirm_date"))){
			matDuraStoreScrap.put("confirm_date", DateUtil.dateToString((Date)matDuraStoreScrap.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matDuraStoreScrap", matDuraStoreScrap);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/scrap/store/update";
	}

	/**
	 * @Description 更新数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/updateMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraScrapStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//编制日期
		if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
			mapVo.put("make_date", DateUtil.stringToDate(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
		}
		
		String matJson;
		try {
			
			matJson = matDuraScrapStoreService.update(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/deleteMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraScrapStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("scrap_id", ids[3]);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraScrapStoreService.deleteBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/auditMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraScrapStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("scrap_id", ids[3]);
				mapVo.put("state", 2);
				mapVo.put("checker", user_id);
				mapVo.put("check_date", date);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraScrapStoreService.auditOrUnAuditBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/unAuditMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraScrapStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("scrap_id", ids[3]);
				mapVo.put("state", 1);
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
				mapVo.put("check_state", 2);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraScrapStoreService.auditOrUnAuditBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/confirmMatDuraScrapStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatDuraScrapStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String dates = DateUtil.dateToString(date, "yyyy-MM-dd");
		String year = dates.substring(0, 4);
		String month = dates.substring(5, 7);
		String user_id = SessionManager.getUserId();
		String matJson;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("scrap_id", ids[3]);
				mapVo.put("state", 3);
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("year", year);
				mapVo.put("month", month);
				mapVo.put("check_state", 2);  //用于判断状态
				listVo.add(mapVo);
			}
		
			matJson = matDuraScrapStoreService.confirmBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/store/choiceInvPage", method = RequestMethod.GET)
	public String matDuraScrapStoreChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));

		return "hrp/mat/dura/scrap/store/choiceInv";
	}
}
