/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.affi.in;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.service.affi.in.MatAffiInCommonService;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.initial.MatInitAffiInService;
import com.chd.hrp.mat.service.order.init.MatOrderInitService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;

/**
 * 
 * @Description:  代销入库 
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAffiInCommonController extends BaseController{
	private static Logger logger = Logger.getLogger(MatAffiInCommonController.class);

	// 引入Service服务
	@Resource(name = "matAffiInCommonService")
	private final MatAffiInCommonService matAffiInCommonService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matOrderInitService")
	private final MatOrderInitService matOrderInitService = null;
	
	@Resource(name = "matInitAffiInService")
	private final MatInitAffiInService matInitAffiInService = null;
	
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonMainPage", method = RequestMethod.GET)
	public String matAffiInCommonMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04018", MyConfig.getSysPara("04018"));
		mode.addAttribute("p04022", MyConfig.getSysPara("04022"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/in/matAffiInCommonMain";
	}
	
	/**
	 * 查询  代销 入库单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String matAffiInCommon = "";
		if(mapVo.get("show_detail").equals("1")){
			matAffiInCommon =  matAffiInCommonService.queryDetails(getPage(mapVo));
		}else{
			matAffiInCommon =  matAffiInCommonService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matAffiInCommon);
	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonAddPage", method = RequestMethod.GET)
	public String matAffiInCommonAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04078", MyConfig.getSysPara("04078"));
		
		return "hrp/mat/affi/in/matAffiInCommonAdd";
	}
	
	/**
	 * @Description 添加数据  代销材料期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/addMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			matJson = matAffiInCommonService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 删除入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/deleteMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("is_com", "1");
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matAffiInCommonService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 审核 代销入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/auditMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("in_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiInCommonService.auditMatAffiInCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 取消审核 代销入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/unAuditMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("in_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiInCommonService.unAuditMatAffiInCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	
	}
	
	/**
	 * 复制  入库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/copyMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date  = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("in_id", ids[3]);
			
			
			listVo.add(mapVo);
		}
		String matJson;
		try {
			matJson = matAffiInCommonService.copyMatAffiInCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
	
	}
	
	/**
	 * 冲销  入库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/offsetMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date  = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("in_id", ids[3]);
			/*mapVo.put("make_date", date);
			
			if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
				mapVo.put("make_date", DateUtil.DateToString(mapVo.get("make_date").toString(), "yyyy-MM-dd"));
			}*/
			
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiInCommonService.offsetMatAffiInCommon(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * @Description 修改页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonUpdatePage", method = RequestMethod.GET)
	public String matAffiInCommonUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		mapVo.put("bus_type_code", matAffiIn.get("bus_type_code"));
		
		Map<String, Object> matAffiInId = matAffiInCommonService.queryMatAffiInIds(mapVo);
		mode.addAttribute("up", matAffiInId.get("in_idUp"));
		mode.addAttribute("next", matAffiInId.get("in_idNext"));
		
		if("1".equals(matAffiIn.get("come_from"))){
			mode.addAttribute("flag", "0");
		}else{
			mode.addAttribute("flag", "1");
		}
		//List<Map<String,Object>> orderRelaList = matAffiInCommonService.queryOrderRelaExists(mapVo);
		/*if(orderRelaList.size() > 0 ){
			mode.addAttribute("flag", "1");
		}else{
			mode.addAttribute("flag", "0");
		}*/
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04022", MyConfig.getSysPara("04022"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04042", MyConfig.getSysPara("04042"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		return "hrp/mat/affi/in/matAffiInCommonUpdate";
	}
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInCommonDetail", method = RequestMethod.POST)
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
	 * @Description 入库单修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/updateMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			matJson = matAffiInCommonService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "5");
			mapVo.put("flag", "1");
			mapVo.put("whereSql"," and ot.order_amount != it.in_amount ");
			matAffiInCommonService.updateMatAffiOrderState(mapVo);
		}
		
		return JSONObject.parseObject(matJson);
		
	}
	
	/**
	 * 订单导入页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonOrderImpPage", method = RequestMethod.GET)
	public String matAffiInCommonOrderImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("paras", matCommonService.queryMatParas());
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/affi/in/matAffiInCommonOrderImp";
	}
	/**
	 * 订单导入  查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = matAffiInCommonService.queryMatOrder(mapVo);

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 订单导入 查看明细主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonOrderImpDetail", method = RequestMethod.GET)
	public String matAffiInCommonOrderImpDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		Map<String, Object> matOrderMain = matOrderInitService.queryByCode(mapVo);
		
		
		mode.addAttribute("matOrderMain", matOrderMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/affi/in/matAffiInCommonOrderImpDetail";

	}
	
	/**
	 * 订单导入 查看明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInOrderImpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatAffiInOrderImpDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

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
		
		String matJson = matAffiInCommonService.queryMatOrderDetail(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 订单导入 查询导入的明细材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/affi/in/queryAffiInOrderDetailImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiInOrderDetailImp(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("order_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String matAffiIn = matAffiInCommonService.queryOrderDetailImp((listVo));
		
		return JSONObject.parseObject(matAffiIn);
	}
	/**
	 * 配套导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonMatchImpPage", method = RequestMethod.GET)
	public String matAffiInCommonMatchImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/in/matAffiInCommonMatchImp";
	
	}
	/**
	 * 配套入库 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInMatchDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = matAffiInCommonService.queryMatAffiInMatchDetail(mapVo);

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * 维护发票页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonVoice", method = RequestMethod.GET)
	public String matAffiInCommonVoice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("money_sum", mapVo.get("money_sum"));
		
		mode.addAttribute("matAffiInMain", matAffiInCommonService.queryMatAffiInMainByInvoice(mapVo));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/in/matAffiInCommonVoice";
	
	}
	
	/**
	 * 维护发票页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/existsMatInDetailByInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMatInDetailByInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matIn = matAffiInCommonService.existsMatAffiInDetailByInvoice(mapVo);
		
		return JSONObject.parseObject(matIn);

	}
	
	/**
	 * 协议导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInCommonProImpPage", method = RequestMethod.GET)
	public String matAffiInCommonProImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));	
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/in/matAffiInCommonProImp";
	
	}
	
	/**
	 * 协议导入页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiCommonPro", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiCommonPro(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = matAffiInCommonService.queryMatAffiInPro(mapVo);

		return JSONObject.parseObject(matJson);

	}
	
	
	/**
	 * 入库确认
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/confirmMatAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
		Object changeConfirmDate = MyConfig.getSysPara("04047");//是否按照客户端提交确认时间确认
		String confirm_date=DateUtil.getSysDate();//获取服务器当前日期
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键

			if(changeConfirmDate!=null && "1".equals(changeConfirmDate.toString())){
				confirm_date = ids[4];
			}

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("in_id", ids[3]);
			mapVo.put("confirm_date", confirm_date);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matAffiInCommonService.confirmMatAffiInCommon((listVo));
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		return JSONObject.parseObject(matJson);
		
		
	}
	
	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInBeforeNo", method = RequestMethod.POST)
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
		
		String matJson = matAffiInCommonService.queryMatAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInNextNo", method = RequestMethod.POST)
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
		
		String matJson = matAffiInCommonService.queryMatAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/affi/in/affiInPrintSetPage", method = RequestMethod.GET)
	public String affiInPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	
	/**
	 * @Description 退货单导入跳转页面 
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInBackImpPage", method = RequestMethod.GET)
	public String matAffiInBackImpPage(Model mode) throws Exception {
		return "hrp/mat/affi/in/matAffiInBackImp";

	}
	/**
	 * @Description 导入数据
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/readMatAffiInBackFiles", method = RequestMethod.POST)
	public String readMatAffiInBackFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		//取出所有材料信息并转换成Map<材料编码, 材料信息>
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//获取数据
			List<Map<String, Object>> invList = matInitAffiInService.queryInvListForImport(mapVo);
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> inv : invList){
				if(ChdJson.validateJSON(String.valueOf(inv.get("inv_id"))) 
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_no")))
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_code")))){
					
					invMap.put(inv.get("inv_code").toString(), inv);
				}
			}
			
			//取出所有材料信息并转换成Map<货位编码, 货位信息>
			List<Map<String, Object>> locationList = matInitAffiInService.queryLocationListForImport(mapVo);
			Map<String, Map<String, Object>> locationMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> location : locationList){
				if(ChdJson.validateJSON(String.valueOf(location.get("location_id")))
						&& ChdJson.validateJSON(String.valueOf(location.get("location_no")))
						&& ChdJson.validateJSON(String.valueOf(location.get("location_code")))){
					
					locationMap.put(location.get("location_code").toString(), location);
				}
			}
			boolean flag = true;
			int len = 0;
			String[] data = null;
			//组装数据
	        for (String[] tmp : list) {
	        	if(flag){
	        		flag = false;
	        		len = tmp.length;
	        		data = new String[len];
	        		continue;
	        	}
	        	 
	        	for(int i = 0; i< tmp.length; i++){
	        		data[i] = tmp[i];
	        	}
	        	Map<String, Object> detailMap = new HashMap<String, Object>();
	        	if(data[0] != null && !"".equals(data[0])){
		        	if(invMap.get(data[0]) != null){
			            detailMap.put("inv_id", invMap.get(data[0]).get("inv_id").toString());//材料ID
			            detailMap.put("inv_no", invMap.get(data[0]).get("inv_no").toString());//材料变更号
			            detailMap.put("is_bar", invMap.get(data[0]).get("is_bar").toString());//是否条码
			            detailMap.put("is_quality", invMap.get(data[0]).get("is_quality").toString());//是否有效期
			            detailMap.put("is_disinfect", invMap.get(data[0]).get("is_disinfect").toString());//是否保质期
		        	}
		        	detailMap.put("inv_code", data[0]);
		        	detailMap.put("inv_name", data[1]);
		        	detailMap.put("inv_model", data[2]);
		        	detailMap.put("unit_name", data[3]);
		        	detailMap.put("amount", data[4]);
		        	detailMap.put("price", data[5]);
		        	detailMap.put("amount_money", data[6]);
		        	detailMap.put("batch_no", data[7]);
		        	detailMap.put("batch_sn", data[8]);
		        	detailMap.put("inva_date", data[9] == null ? null : data[9].replace("/", "-"));
		        	detailMap.put("disinfect_date", data[10] == null ? null : data[10].replace("/", "-"));
		        	detailMap.put("sn", data[11]);
		        	detailMap.put("location_code", data[12]);
		        	if(invMap.get(data[12]) != null){
			            detailMap.put("location_id", locationMap.get(data[12]).get("location_id").toString());//货位ID
			            detailMap.put("location_name", locationMap.get(data[12]).get("location_code").toString() + " " + locationMap.get("location_code").get("location_name").toString());//货位显示内容
		        	}
		        	detailList.add(detailMap);
	        	}
            }
        }
        catch (Exception e) {
        	logger.error(e.getMessage(), e);
        }

		response.getWriter().print(JSONObject.parseObject(ChdJson.toJson(detailList)));
		return null;
	}
	
	/**
	 * 历史引入
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiHistoryImpPage", method = RequestMethod.GET)
	public String matAffiHistoryImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text").toString());
		mode.addAttribute("paras", matCommonService.queryMatParas());
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/in/matAffiHistoryImp";
	}
	/**
	 * 查询历史引入明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInHistoryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInHistoryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String matAffiInCommon = matAffiInCommonService.queryMatAffiInHistoryDetail(mapVo);
		
		return JSONObject.parseObject(matAffiInCommon);
	}
	//
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInHisDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInHisDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matAffiInCommonService.queryMatAffiInHisDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 订单页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiOrderImpPage", method = RequestMethod.GET)
	public String matOrderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String start_date = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "04");
		}
		
		start_date = matStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04033", MyConfig.getSysPara("04033"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/in/matAffiOrderImp";
	}
	
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInOrderN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInOrderN(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String matIn = matAffiInCommonService.queryMatAffiInOrderDetailN(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	
	/**
	 * 订单导入添加入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/addMatAffiInByOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatAffiInByOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			matJson = matAffiInCommonService.addAffiInByOrder(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.order_amount = it.in_amount ");
			matAffiInCommonService.updateMatAffiOrderState(mapVo);
		}
		return JSONObject.parseObject(matJson);
	}
	//送货单生成 hrp/mat/affi/in/matAffiDeliveryOrderImpMainPage
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiDeliveryOrderImpMainPage", method = RequestMethod.GET)
	public String matAffiDeliveryOrderImpMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		return "hrp/mat/affi/in/matAffiDeliveryOrderImp";

	}
	
	/**
	 * 批量添加材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiInvBatchImpPage", method = RequestMethod.GET)
	public String matAffiInvBatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/in/matAffiInvBatchImp";

	}
	
	/**
	 * 批量添加查询材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInInvBatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matAffiInCommonService.queryMatAffiInInvBatch(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * 批量选择材料生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/queryMatAffiInInvBatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInInvBatchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = matAffiInCommonService.queryMatAffiInInvBatchDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/updateMatStorageAffiInCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStorageAffiInCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("order_id", ids[3]);
			map.put("order_detail_id", ids[4]);
			listVo.add(map);
		}

		String matJson;
		try {
			matJson = matStorageInService.updateMatStorageInCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * 查看已经关闭的材料列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/matAffiOrderImpCloseInvPage", method = RequestMethod.GET)
	public String matAffiOrderImpCloseInvPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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

		if (paras != null && !"".equals(paras)) {
			String[] paraArray = paras.split("@");
			if (!"null".equals(paraArray[0])) {
				mode.addAttribute("sup_id", paraArray[0]);
			}

			if (!"null".equals(paraArray[1])) {
				mode.addAttribute("sup_text", paraArray[1]);
			}
		}
		
		//String start_date = matStorageInService.queryModeStartDate(mapVo);
		//mode.addAttribute("start_date", start_date);
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04033", MyConfig.getSysPara("04033"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		
		return "hrp/mat/affi/in/matAffiOrderImpCloseInv";
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/in/updateMatAffiInCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatAffiInCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> map=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("order_id", ids[3]);
			map.put("order_detail_id", ids[4]);
			listVo.add(map);
		}

		String matJson;
		try {
			matJson = matStorageInService.updateMatStorageInCancleCloseInv(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
}
