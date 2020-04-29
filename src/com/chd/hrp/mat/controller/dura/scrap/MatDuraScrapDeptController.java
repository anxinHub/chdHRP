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
import com.chd.hrp.mat.service.dura.scrap.MatDuraScrapDeptService;

/**
 * 
 * @Description: 耐用品科室报废 
 * @Table: MAT_DURA_DEPT_(SCRAP/SCRAP_D) 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */

@Controller
public class MatDuraScrapDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraScrapDeptController.class);

	// 引入Service服务
	@Resource(name = "matDuraScrapDeptService")
	private final MatDuraScrapDeptService matDuraScrapDeptService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/mainPage", method = RequestMethod.GET)
	public String matDuraScrapDeptMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));

		return "hrp/mat/dura/scrap/dept/main";
	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/addPage", method = RequestMethod.GET)
	public String matDuraScrapDeptAddPage(Model mode) throws Exception {
       
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
		
		return "hrp/mat/dura/scrap/dept/add";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/queryMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matDuraScrapDeptService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/queryMatDuraScrapDeptDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapDeptDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matDuraScrapDeptService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/queryMatDuraScrapDeptById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraScrapDeptById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matDuraScrapDeptService.queryMainByCode(mapVo);
	}

	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/addMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatDuraScrapDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			
			matJson = matDuraScrapDeptService.add(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/updatePage", method = RequestMethod.GET)
	public String matDuraScrapDeptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matDuraDeptScrap = matDuraScrapDeptService.queryMainByCode(mapVo);
		
		//编制日期
		if(matDuraDeptScrap.get("make_date") != null && !"".equals(matDuraDeptScrap.get("make_date"))){
			matDuraDeptScrap.put("make_date", DateUtil.dateToString((Date)matDuraDeptScrap.get("make_date"), "yyyy-MM-dd"));
		}
		//审核日期
		if(matDuraDeptScrap.get("check_date") != null && !"".equals(matDuraDeptScrap.get("check_date"))){
			matDuraDeptScrap.put("check_date", DateUtil.dateToString((Date)matDuraDeptScrap.get("check_date"), "yyyy-MM-dd"));
		}
		//确认日期
		if(matDuraDeptScrap.get("confirm_date") != null && !"".equals(matDuraDeptScrap.get("confirm_date"))){
			matDuraDeptScrap.put("confirm_date", DateUtil.dateToString((Date)matDuraDeptScrap.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matDuraDeptScrap", matDuraDeptScrap);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04020", MyConfig.getSysPara("04020"));
		
		return "hrp/mat/dura/scrap/dept/update";
	}

	/**
	 * @Description 更新数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/updateMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatDuraScrapDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			
			matJson = matDuraScrapDeptService.update(mapVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/deleteMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			matJson = matDuraScrapDeptService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/auditMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			matJson = matDuraScrapDeptService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/unAuditMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			matJson = matDuraScrapDeptService.auditOrUnAuditBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/confirmMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("state", 4);
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("year", year);
				mapVo.put("month", month);
				mapVo.put("check_state", 3);  //用于判断状态
				listVo.add(mapVo);
			}
		
			matJson = matDuraScrapDeptService.confirmBatch(listVo);
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
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/choiceInvPage", method = RequestMethod.GET)
	public String matDuraScrapDeptChoiceInvPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));

		return "hrp/mat/dura/scrap/dept/choiceInv";
	}
	
	/**
	 * 审批
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/approveMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("state", 3);
				mapVo.put("approve_emp", user_id);
				mapVo.put("approve_date", date);
				//mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraScrapDeptService.auditOrUnApproveBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 销审批
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/scrap/dept/unApproveMatDuraScrapDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unApproveMatDuraScrapDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("approve_emp", "");
				mapVo.put("approve_date", "");
				//mapVo.put("check_state", 1);  //用于判断状态
				listVo.add(mapVo);
			}
			
			matJson = matDuraScrapDeptService.auditOrUnApproveBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
}
