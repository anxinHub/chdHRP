/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.tran;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.tran.MatDuraTranStoreStoreService;

/**
 * 
 * @Description: 耐用品库房到库房转移 
 * @Table: MAT_DURA_STORE_(STORE/STORE_D)
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatDuraTranStoreStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraTranStoreStoreController.class);

	// 引入Service服务
	@Resource(name = "matDuraTranStoreStoreService")
	private final MatDuraTranStoreStoreService matDuraTranStoreStoreService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/mainPage", method = RequestMethod.GET)
	public String matDuraTranStoreStoreMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/tran/storeStore/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/addPage", method = RequestMethod.GET)
	public String matDuraTranStoreStoreAddPage(Model mode) throws Exception {
       
		
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
		
		return "hrp/mat/dura/tran/storeStore/add";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/queryMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
//		if(mapVo.get("begin_make_date") != null && !"".equals(mapVo.get("begin_make_date"))){
//			mapVo.put("begin_make_date", DateUtil.stringToDate(mapVo.get("begin_make_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("end_make_date") != null && !"".equals(mapVo.get("end_make_date"))){
//			mapVo.put("end_make_date", DateUtil.stringToDate(mapVo.get("end_make_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
//			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
//			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
//		}
		
		String matIn = matDuraTranStoreStoreService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/queryMatDuraTranStoreStoreDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranStoreStoreDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matDuraTranStoreStoreService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/queryMatDuraTranStoreStoreById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraTranStoreStoreById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matDuraTranStoreStoreService.queryMainByCode(mapVo);
	}

	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/addMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDuraTranStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			
			matJson = matDuraTranStoreStoreService.add(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/updatePage", method = RequestMethod.GET)
	public String matDuraTranStoreStoreUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matDuraStoreStore = matDuraTranStoreStoreService.queryMainByCode(mapVo);
		
		//编制日期
		if(matDuraStoreStore.get("make_date") != null && !"".equals(matDuraStoreStore.get("make_date"))){
			matDuraStoreStore.put("make_date", DateUtil.dateToString((Date)matDuraStoreStore.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matDuraStoreStore.get("check_date") != null && !"".equals(matDuraStoreStore.get("check_date"))){
			matDuraStoreStore.put("check_date", DateUtil.dateToString((Date)matDuraStoreStore.get("check_date"), "yyyy-MM-dd"));
		}
		//确认日期
		if(matDuraStoreStore.get("confirm_date") != null && !"".equals(matDuraStoreStore.get("confirm_date"))){
			matDuraStoreStore.put("confirm_date", DateUtil.dateToString((Date)matDuraStoreStore.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matDuraStoreStore", matDuraStoreStore);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/tran/storeStore/update";
	}

	/**
	 * @Description 更新数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/updateMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraTranStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			
			matJson = matDuraTranStoreStoreService.update(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/deleteMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraTranStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dura_id", ids[3]);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranStoreStoreService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/auditMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraTranStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 2);
				mapVo.put("checker", user_id);
				mapVo.put("check_date", date);
				mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranStoreStoreService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/unAuditMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraTranStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 1);
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
				mapVo.put("check_state", 2);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraTranStoreStoreService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/confirmMatDuraTranStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatDuraTranStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dura_id", ids[3]);
				mapVo.put("state", 3);
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("check_state", 2);  //用于判断状态
				mapVo.put("year", year);  //用于修改单据期间
				mapVo.put("month", month);  //用于修改单据期间
				listVo.add(mapVo);
			}
		
			matJson = matDuraTranStoreStoreService.confirmBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/choiceInvPage", method = RequestMethod.GET)
	public String matDuraTranStoreStoreChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/tran/storeStore/choiceInv";
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/printSetPage", method = RequestMethod.GET)
	public String printSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	/*@RequestMapping(value = "/hrp/mat/dura/tran/storeStore/queryDataByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDataByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		String reJson=null;
		try {
			
			reJson=matDuraTranStoreStoreService.queryDataByPrintTemlate(mapVo);
		} catch (Exception e) {
			
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}*/
}
