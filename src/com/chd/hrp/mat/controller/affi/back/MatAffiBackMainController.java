/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.affi.back;

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

import com.alibaba.fastjson.JSON;
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
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.service.affi.back.MatAffiBackService;
import com.chd.hrp.mat.service.affi.in.MatAffiInCommonService;
import com.chd.hrp.mat.service.base.MatCommonService;

/**
 * 
 * @Description:  常备材料退货 
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */ 

@Controller
public class MatAffiBackMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAffiBackMainController.class);

	// 引入Service服务
	@Resource(name = "matAffiBackService")
	private final MatAffiBackService matAffiBackService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	// 引入Service服务
	@Resource(name = "matAffiInCommonService")
	private final MatAffiInCommonService matAffiInCommonService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/mainPage", method = RequestMethod.GET)
	public String MatAffiBackMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04023", MyConfig.getSysPara("04023"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("user_id", SessionManager.getUserId());

		return "hrp/mat/affi/back/matAffiBackMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/back/matAffiBackAddPage", method = RequestMethod.GET)
	public String matAffiBackAddPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/back/matAffiBackAdd";
	}

	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//转换日期格式    
//		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
//			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
//			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
//			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
//		}
//		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
//			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
//		}
		//只能查询退库
		mapVo.put("bus_type_code", 29);
		String matIn = "";
		if("0".equals(mapVo.get("show_detail").toString())){
			matIn = matAffiBackService.query(getPage(mapVo));
		}else{
			matIn = matAffiBackService.queryDetails(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackDetailById(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matAffiBackService.queryAffiDetailById(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matAffiBackService.queryAffiDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matAffiBackService.queryByCode(mapVo);
	}

	/**
	 * @Description 选择材料页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/matAffiBackInvListPage", method = RequestMethod.GET)
	public String matAffiBackInvListPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		mapVo.put("store_id", paras.split(",")[0]);
		
		//String invList = matAffiBackService.queryMatStorageBackInvHold(getPage(mapVo));
		
		mode.addAttribute("store_id", paras);
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/back/matAffiBackInvList";

	}

	/**
	 * @Description 添加数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/addMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("maker") == null) {
			mapVo.put("maker", SessionManager.getUserId());
		}
		
		String matJson;
		try {
			matJson = matAffiBackService.add(mapVo);
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
	@RequestMapping(value = "/hrp/mat/affi/back/matAffiBackUpdatePage", method = RequestMethod.GET)
	public String matAffiBackUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matAffiInMain = matAffiBackService.queryByCode(mapVo);
		
		if(matAffiInMain.get("in_date") != null && !"".equals(matAffiInMain.get("in_date"))){
			matAffiInMain.put("in_date", DateUtil.dateToString((Date)matAffiInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matAffiInMain.get("check_date") != null && !"".equals(matAffiInMain.get("check_date"))){
			matAffiInMain.put("check_date", DateUtil.dateToString((Date)matAffiInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matAffiInMain.get("confirm_date") != null && !"".equals(matAffiInMain.get("confirm_date"))){
			matAffiInMain.put("confirm_date", DateUtil.dateToString((Date)matAffiInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		
		mapVo.put("bus_type_code", matAffiInMain.get("bus_type_code"));
		
		Map<String, Object> matAffiInId = matAffiInCommonService.queryMatAffiInIds(mapVo);
		mode.addAttribute("up", matAffiInId.get("in_idUp"));
		mode.addAttribute("next", matAffiInId.get("in_idNext"));
		
		mode.addAttribute("matAffiInMain", matAffiInMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04023", MyConfig.getSysPara("04023"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/back/matAffiBackUpdate";
	}

	/**
	 * @Description 更新数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/updateMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matAffiBackService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 删除数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/deleteMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatAffiBack(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matAffiBackService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 复制退库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/copyMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatAffiBack(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiBackService.copyMatAffiBackBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 退库单审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/auditMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//状态：2 审核
		mapVo.put("state", 2);
		//审核人：当前用户
		mapVo.put("checker", SessionManager.getUserId());
		//审核时间：当前系统时间
		mapVo.put("check_date", new Date());
		
		String matJson;
		try {
			matJson = matAffiBackService.auditMatAffiBack(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 退库单消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/unAuditMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//状态：1 未审核
		mapVo.put("state", 1);
		//审核人：置空
		mapVo.put("checker", "");
		//审核时间：置空
		mapVo.put("check_date", "");
		
		String matJson;
		try {
			matJson = matAffiBackService.unAuditMatAffiBack(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/confirmMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatAffiBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期
		if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString()) 
		   && mapVo.get("confirm_date")!=null && "".equals(mapVo.get("confirm_date").toString())){
				confirm_date = mapVo.get("confirm_date").toString();
		}

		
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
		//状态：3 已确认
		mapVo.put("state", 1); 
		//确认人：当前系统用户
		mapVo.put("confirmer", SessionManager.getUserId());
		//确认时间：当前系统时间
		mapVo.put("confirm_date", confirm_date);
		
		String matJson  = matAffiBackService.confirmMatAffiBack(mapVo);
		return JSONObject.parseObject(matJson);
		/*try { 
			matJson = matAffiBackService.confirmMatAffiBack(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);*/
		
	}

	/**
	 * @Description 退库单批量审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/auditMatAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			//System.out.println(mapVo.get("in_id"));
			mapVo.put("state", 2);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", date);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiBackService.auditMatAffiBackBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 退库单批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/unAuditMatAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("state", 1);
			mapVo.put("checker", "");
			mapVo.put("check_date", "");
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiBackService.unAuditMatAffiBackBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}

	/**
	 * @Description 批量入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/confirmMatAffiBackBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatAffiBackBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("state", 3);
			mapVo.put("confirmer", SessionManager.getUserId());


			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				confirm_date = ids[4];
			}


			mapVo.put("confirm_date", confirm_date);
			listVo.add(mapVo);
		}
		
		String matJson = matAffiBackService.confirmMatAffiBackBatch(listVo);
		
		return JSONObject.parseObject(matJson);
		/*String matJson;
		try {
			matJson = matAffiBackService.confirmMatAffiBackBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);*/
		
	}

	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiInBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("type", "before");
		
		String matJson = matAffiBackService.queryMatAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiInNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInNextNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("type", "next");
		
		String matJson = matAffiBackService.queryMatAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	/**
	 * @Description 
	 * 入库单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/back/matAffiBackInImpPage", method = RequestMethod.GET)
	public String matAffiBackInImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text").toString());
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/back/matAffiBackInImp";
	}
	
	/**
	 * @Description 
	 * 入库单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/back/matAffiBackAddSupPage", method = RequestMethod.GET)
	public String matAffiBackAddSupPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text").toString());
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/back/matAffiBackAddSup";
	}
	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackIn", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> queryMatAffiBackIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//转换日期格式
		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matAffiBackService.queryAffiIn(mapVo);
		
		return JSONObject.parseObject(matIn);
	}
	
	
	/**
	 * @Description 入库单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackInSup", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> queryMatAffiBackInSup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//转换日期格式
		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matAffiBackService.queryMatAffiBackInSup(mapVo);
		
		return JSONObject.parseObject(matIn);
	}
	/**
	 * @Description 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		
		String matIn = matAffiBackService.queryAffiInDetail(mapVo);
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 入库单导入组装退货明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackDetailByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackDetailByImp(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		String matIn = matAffiBackService.queryBackDetailByImp(mapVo);
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 导入跳转页面  常备材料入库
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/importPage", method = RequestMethod.GET)
	public String matAffiBackImportPage(Model mode) throws Exception {

		return "hrp/mat/affi/back/Import";

	}

	/**
	 * @Description 下载导入模版  常备材料入库
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/affi/back/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate",
				"常备材料入库.xls");

		return null;
	}

	/**
	 * @Description 导入数据 常备材料入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/readFiles", method = RequestMethod.POST)
	public String readMatAffiBackFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MatInMain data_exc = new MatInMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 常备材料入库
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/affi/back/addBatchMatAffiBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatAffiBack( @RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MatInMain> list_err = new ArrayList<MatInMain>();

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

		} catch (DataAccessException e) {
			MatInMain data_exc = new MatInMain();
			list_err.add(data_exc);
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));
		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/back/affiBackPrintSetPage", method = RequestMethod.GET)
	public String affiBackPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/affi/back/queryMatAffiBackByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiBackByPrintTsemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=matAffiInCommonService.queryMatAffiInByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	@RequestMapping(value="/hrp/mat/affi/back/queryAllMatbySupId", method = RequestMethod.POST)
	@ResponseBody
	public JSON queryAllMatbySupId(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AllMat =matAffiInCommonService.queryAllMatbySupId(mapVo);
		return JSONObject.parseObject(AllMat);
		
	}
}
