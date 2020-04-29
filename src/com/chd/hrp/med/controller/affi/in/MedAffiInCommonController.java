/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.affi.in;

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
import com.chd.hrp.med.service.affi.in.MedAffiInCommonService;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.initial.MedInitAffiInService;
import com.chd.hrp.med.service.order.init.MedOrderInitService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;

/**
 * 
 * @Description:  代销入库
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAffiInCommonController extends BaseController{
	private static Logger logger = Logger.getLogger(MedAffiInCommonController.class);

	// 引入Service服务
	@Resource(name = "medAffiInCommonService")
	private final MedAffiInCommonService medAffiInCommonService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "medOrderInitService")
	private final MedOrderInitService medOrderInitService = null;
	
	@Resource(name = "medInitAffiInService")
	private final MedInitAffiInService medInitAffiInService = null;
	
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonMainPage", method = RequestMethod.GET)
	public String medAffiInCommonMainPage(Model mode) throws Exception {
		
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08022", MyConfig.getSysPara("08022"));
		
		return "hrp/med/affi/in/medAffiInCommonMain";
	}
	
	/**
	 * 查询  代销 入库单
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String medAffiInCommon = "";
		if(mapVo.get("show_detail").equals("1")){
			medAffiInCommon =  medAffiInCommonService.queryDetails(getPage(mapVo));
		}else{
			medAffiInCommon =  medAffiInCommonService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medAffiInCommon);
	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonAddPage", method = RequestMethod.GET)
	public String medAffiInCommonAddPage(Model mode) throws Exception {
		
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		return "hrp/med/affi/in/medAffiInCommonAdd";
	}
	
	/**
	 * @Description 添加数据  代销药品期初入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/addMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson;
		try {
			medJson = medAffiInCommonService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 删除入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/deleteMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		String medJson;
		try {
			medJson = medAffiInCommonService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 审核 代销入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/auditMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiInCommonService.auditMedAffiInCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 取消审核 代销入库单据
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/unAuditMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiInCommonService.unAuditMedAffiInCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	
	}
	
	/**
	 * 复制  入库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/copyMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> copyMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String medJson;
		try {
			medJson = medAffiInCommonService.copyMedAffiInCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
	
	}
	
	/**
	 * 冲销  入库单据 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/offsetMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medAffiInCommonService.offsetMedAffiInCommon(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * @Description 修改页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonUpdatePage", method = RequestMethod.GET)
	public String medAffiInCommonUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		mapVo.put("bus_type_code", medAffiIn.get("bus_type_code"));
		
		Map<String, Object> medAffiInId = medAffiInCommonService.queryMedAffiInIds(mapVo);
		mode.addAttribute("up", medAffiInId.get("in_idUp"));
		mode.addAttribute("next", medAffiInId.get("in_idNext"));
		
		List<Map<String,Object>> orderRelaList = medAffiInCommonService.queryOrderRelaExists(mapVo);
		if(orderRelaList.size()>0){
			mode.addAttribute("flag", "1");
		}else{
			mode.addAttribute("flag", "0");
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08022", MyConfig.getSysPara("08022"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		mode.addAttribute("p08042", MyConfig.getSysPara("08042"));
		
		return "hrp/med/affi/in/medAffiInCommonUpdate";
	}
	
	/**
	 * @Description 修改页面：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInCommonDetail", method = RequestMethod.POST)
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
	 * @Description 入库单修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/updateMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedAffiInCommon(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			medJson = medAffiInCommonService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		if(medJson.contains("true")){
			mapVo.put("state", "5");
			mapVo.put("flag", "1");
			mapVo.put("whereSql"," and ot.order_amount != it.in_amount ");
			medAffiInCommonService.updateMedAffiOrderState(mapVo);
		}
		
		return JSONObject.parseObject(medJson);
		
	}
	
	/**
	 * 订单导入页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonOrderImpPage", method = RequestMethod.GET)
	public String medAffiInCommonOrderImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("paras", medCommonService.queryMedParas());
		return "hrp/med/affi/in/medAffiInCommonOrderImp";
	}
	/**
	 * 订单导入  查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson = medAffiInCommonService.queryMedOrder(mapVo);

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 订单导入 查看明细主表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonOrderImpDetail", method = RequestMethod.GET)
	public String medAffiInCommonOrderImpDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		Map<String, Object> medOrderMain = medOrderInitService.queryByCode(mapVo);
		
		
		mode.addAttribute("medOrderMain", medOrderMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/affi/in/medAffiInCommonOrderImpDetail";

	}
	
	/**
	 * 订单导入 查看明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInOrderImpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedAffiInOrderImpDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

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
		
		String medJson = medAffiInCommonService.queryMedOrderDetail(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 订单导入 查询导入的明细药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/med/affi/in/queryAffiInOrderDetailImp", method = RequestMethod.POST)
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
		
		String medAffiIn = medAffiInCommonService.queryOrderDetailImp((listVo));
		
		return JSONObject.parseObject(medAffiIn);
	}
	/**
	 * 配套导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonMatchImpPage", method = RequestMethod.GET)
	public String medAffiInCommonMatchImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		//mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/in/medAffiInCommonMatchImp";
	
	}
	/**
	 * 配套入库 查询明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInMatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInMatchDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson = medAffiInCommonService.queryMedAffiInMatchDetail(mapVo);

		return JSONObject.parseObject(medJson);

	}
	
	/**
	 * 维护发票页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonVoice", method = RequestMethod.GET)
	public String medAffiInCommonVoice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("money_sum", mapVo.get("money_sum"));
		
		mode.addAttribute("medAffiInMain", medAffiInCommonService.queryMedAffiInMainByInvoice(mapVo));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/in/medAffiInCommonVoice";
	
	}
	
	/**
	 * 维护发票页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/existsMedInDetailByInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existsMedInDetailByInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medIn = medAffiInCommonService.existsMedAffiInDetailByInvoice(mapVo);
		
		return JSONObject.parseObject(medIn);

	}
	
	/**
	 * 协议导入页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInCommonProImpPage", method = RequestMethod.GET)
	public String medAffiInCommonProImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));	
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/in/medAffiInCommonProImp";
	
	}
	
	/**
	 * 协议导入页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiCommonPro", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiCommonPro(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson = medAffiInCommonService.queryMedAffiInPro(mapVo);

		return JSONObject.parseObject(medJson);

	}
	
	
	/**
	 * 入库确认
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/confirmMedAffiInCommon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedAffiInCommon(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("in_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medAffiInCommonService.confirmMedAffiInCommon((listVo));
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		return JSONObject.parseObject(medJson);
		
		
	}
	
	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medAffiInCommonService.queryMedAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInNextNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medAffiInCommonService.queryMedAffiInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/affi/in/affiInPrintSetPage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=medAffiInCommonService.queryMedAffiInByPrintTemlate(mapVo);
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
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInBackImpPage", method = RequestMethod.GET)
	public String medAffiInBackImpPage(Model mode) throws Exception {
		return "hrp/med/affi/in/medAffiInBackImp";

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
	@RequestMapping(value = "/hrp/med/affi/in/readMedAffiInBackFiles", method = RequestMethod.POST)
	public String readMedAffiInBackFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		//取出所有药品信息并转换成Map<药品编码, 药品信息>
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//获取数据
			List<Map<String, Object>> invList = medInitAffiInService.queryInvListForImport(mapVo);
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> inv : invList){
				if(ChdJson.validateJSON(String.valueOf(inv.get("inv_id"))) 
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_no")))
						&& ChdJson.validateJSON(String.valueOf(inv.get("inv_code")))){
					
					invMap.put(inv.get("inv_code").toString(), inv);
				}
			}
			
			//取出所有药品信息并转换成Map<货位编码, 货位信息>
			List<Map<String, Object>> locationList = medInitAffiInService.queryLocationListForImport(mapVo);
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
			            detailMap.put("inv_id", invMap.get(data[0]).get("inv_id").toString());//药品ID
			            detailMap.put("inv_no", invMap.get(data[0]).get("inv_no").toString());//药品变更号
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
	@RequestMapping(value = "/hrp/med/affi/in/medAffiHistoryImpPage", method = RequestMethod.GET)
	public String medAffiHistoryImpPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text").toString());
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text").toString());
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/in/medAffiHistoryImp";
	}
	/**
	 * 查询历史引入明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInHistoryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInHistoryDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String medAffiInCommon = medAffiInCommonService.queryMedAffiInHistoryDetail(mapVo);
		
		return JSONObject.parseObject(medAffiInCommon);
	}
	//
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInHisDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInHisDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = medAffiInCommonService.queryMedAffiInHisDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 订单页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiOrderImpPage", method = RequestMethod.GET)
	public String medOrderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		start_date = medStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		
		return "hrp/med/affi/in/medAffiOrderImp";
	}
	
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInOrderN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInOrderN(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medAffiInCommonService.queryMedAffiInOrderDetailN(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	
	/**
	 * 订单导入添加入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/addMedAffiInByOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedAffiInByOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String medJson;
		try {
			medJson = medAffiInCommonService.addAffiInByOrder(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		if(medJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.order_amount = it.in_amount ");
			medAffiInCommonService.updateMedAffiOrderState(mapVo);
		}
		return JSONObject.parseObject(medJson);
	}
	//送货单生成 hrp/med/affi/in/medAffiDeliveryOrderImpMainPage
	@RequestMapping(value = "/hrp/med/affi/in/medAffiDeliveryOrderImpMainPage", method = RequestMethod.GET)
	public String medAffiDeliveryOrderImpMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		
		return "hrp/med/affi/in/medAffiDeliveryOrderImp";

	}
	
	/**
	 * 批量添加药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiInvBatchImpPage", method = RequestMethod.GET)
	public String medAffiInvBatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/in/medAffiInvBatchImp";

	}
	
	/**
	 * 批量添加查询药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInInvBatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medAffiInCommonService.queryMedAffiInInvBatch(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * 批量选择药品生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/queryMedAffiInInvBatchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInInvBatchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = medAffiInCommonService.queryMedAffiInInvBatchDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/updateMedStorageAffiInCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStorageAffiInCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medStorageInService.updateMedStorageInCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * 查看已经关闭的药品列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/medAffiOrderImpCloseInvPage", method = RequestMethod.GET)
	public String medAffiOrderImpCloseInvPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
		
		//String start_date = medStorageInService.queryModeStartDate(mapVo);
		//mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		
		
		return "hrp/med/affi/in/medAffiOrderImpCloseInv";
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/in/updateMedAffiInCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedAffiInCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medStorageInService.updateMedStorageInCancleCloseInv(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
}
