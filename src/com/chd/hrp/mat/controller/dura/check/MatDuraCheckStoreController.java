/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.check;

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
import com.chd.hrp.mat.service.dura.check.MatDuraCheckStoreService;

/**
 * 
 * @Description: 耐用品库房盘点 
 * @Table: MAT_DURA_STORE_(CHECK/CHECK_D) 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDuraCheckStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraCheckStoreController.class);

	// 引入Service服务
	@Resource(name = "matDuraCheckStoreService")
	private final MatDuraCheckStoreService matDuraCheckStoreService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/mainPage", method = RequestMethod.GET)
	public String matDuraCheckStoreMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));

		return "hrp/mat/dura/check/store/main";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/check/store/addPage", method = RequestMethod.GET)
	public String matDuraCheckStoreAddPage(Model mode) throws Exception {
       
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
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/check/store/add";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/queryMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraCheckStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_check_date") != null && !"".equals(mapVo.get("begin_check_date"))){
			mapVo.put("begin_check_date", DateUtil.stringToDate(mapVo.get("begin_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_check_date") != null && !"".equals(mapVo.get("end_check_date"))){
			mapVo.put("end_check_date", DateUtil.stringToDate(mapVo.get("end_check_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matDuraCheckStoreService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/queryMatDuraCheckStoreDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraCheckStoreDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matDuraCheckStoreService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/queryMatDuraCheckStoreById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraCheckStoreById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matDuraCheckStoreService.queryMainByCode(mapVo);
	}

	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/addMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDuraCheckStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			
			matJson = matDuraCheckStoreService.add(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/updatePage", method = RequestMethod.GET)
	public String matDuraCheckStoreUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matDuraDeptCheck = matDuraCheckStoreService.queryMainByCode(mapVo);
		
		//编制日期
		if(matDuraDeptCheck.get("make_date") != null && !"".equals(matDuraDeptCheck.get("make_date"))){
			matDuraDeptCheck.put("make_date", DateUtil.dateToString((Date)matDuraDeptCheck.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matDuraDeptCheck.get("check_date") != null && !"".equals(matDuraDeptCheck.get("check_date"))){
			matDuraDeptCheck.put("check_date", DateUtil.dateToString((Date)matDuraDeptCheck.get("check_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matDuraStoreCheck", matDuraDeptCheck);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/check/store/update";
	}

	/**
	 * @Description 更新数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/updateMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraCheckStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			
			matJson = matDuraCheckStoreService.update(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/deleteMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraCheckStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_id", ids[3]);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraCheckStoreService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/auditMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraCheckStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_id", ids[3]);
				mapVo.put("state", 2);
				mapVo.put("checker", user_id);
				mapVo.put("check_date", date);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraCheckStoreService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/unAuditMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraCheckStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("check_id", ids[3]);
				mapVo.put("state", 1);
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
				mapVo.put("check_state", 2);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraCheckStoreService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/confirmMatDuraCheckStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatDuraCheckStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String operdate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		String year = operdate.substring(0, 4);
		String month = operdate.substring(5, 7);
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
				mapVo.put("check_id", ids[3]);
				mapVo.put("state", 3);
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("check_state", 2);  //用于判断状态
				mapVo.put("year", year);  
				mapVo.put("month", month); 
				
				listVo.add(mapVo);
			}
		
			matJson = matDuraCheckStoreService.confirmBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/check/store/choiceInvPage", method = RequestMethod.GET)
	public String matDuraCheckStoreChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/check/store/choiceInv";
	}
	
	/**
	 * 查询当前账表材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/queryMatInvByBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvByBalance(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson;
		try {
		
			matJson = matDuraCheckStoreService.queryMatInvByBalance(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 选择材料进行盘点
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/check/store/queryMatDuraInvChoiceByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraInvChoiceByStore(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson;
		try {
		
			matJson = matDuraCheckStoreService.queryMatDuraInvChoiceByStore(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(matJson);
	}
}
