/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.in;

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
import com.chd.base.ChdToken;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description:  常备药品入库
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedStorageInController extends BaseController {

	private static Logger logger = Logger.getLogger(MedStorageInController.class);

	// 引入Service服务
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	// 引入Service服务
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/mainPage", method = RequestMethod.GET)
	public String MedStorageInMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08011", MyConfig.getSysPara("08011"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));
		mode.addAttribute("p08045", MyConfig.getSysPara("08045"));
		
		mode.addAttribute("p08047", MyConfig.getSysPara("08047"));
		
		return "hrp/med/storage/in/main";
	}
	
	/**
	 * @Description 根据订单生成入库单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/medOrderImpPage", method = RequestMethod.GET)
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
			mapVo.put("mod_code", "08");
		}
		start_date = medStorageInService.queryModeStartDate(mapVo);
		mode.addAttribute("start_date", start_date);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		mode.addAttribute("p08040", MyConfig.getSysPara("08040"));
		
		return "hrp/med/storage/in/medOrderImp";

	}
	
	/**
	 * @Description 送货单生成面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/deliveryOrderImpPage", method = RequestMethod.GET)
	public String deliveryOrderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		
		return "hrp/med/storage/in/deliveryOrderImp";

	}
	
	/**
	 * @Description 送货单主表生成面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/deliveryOrderImpMainPage", method = RequestMethod.GET)
	public String deliveryOrderImpMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		return "hrp/med/storage/in/deliveryOrderImpMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/addPage", method = RequestMethod.GET)
	public String medStorageInAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));
		
		return "hrp/med/storage/in/add";
	}

	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_confirm_date") != null && !"".equals(mapVo.get("begin_confirm_date"))){
			mapVo.put("begin_confirm_date", DateUtil.stringToDate(mapVo.get("begin_confirm_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_confirm_date") != null && !"".equals(mapVo.get("end_confirm_date"))){
			mapVo.put("end_confirm_date", DateUtil.stringToDate(mapVo.get("end_confirm_date").toString(), "yyyy-MM-dd"));
		}
		
		String medIn = "";
		if(mapVo.get("show_detail").equals("1")){
			medIn =  medStorageInService.queryDetails(getPage(mapVo));
		}else{
			medIn =  medStorageInService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 查询数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInDetail", method = RequestMethod.POST)
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
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String detail = medStorageInService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	/**
	 * @Description 合并打印
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/querymergePrintDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querymergePrintDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String queryMedInSupDetail = medStorageInService.querymergePrintTemlate(getPage(mapVo));

		return JSONObject.parseObject(queryMedInSupDetail);
	}
	
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return medStorageInService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/med/storage/in/addMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			medJson = medStorageInService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);

	}
	/**
	 * 订单导入添加入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/addMedStorageInOrder", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedStorageInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		//mapVo.put("brief", "根据订单生成入库");
		
		String medJson;
		try {
			medJson = medStorageInService.addInByOrder(mapVo);
			
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		if(medJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.order_amount = it.in_amount ");
			
			medStorageInService.updateMedOrderState(mapVo);
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
	@RequestMapping(value = "/hrp/med/storage/in/updatePage", method = RequestMethod.GET)
	public String medStorageInUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if(medInMain.get("bill_date") != null && !"".equals(medInMain.get("bill_date"))){
			medInMain.put("bill_date", DateUtil.dateToString((Date)medInMain.get("bill_date"), "yyyy-MM-dd"));
		}
			medInMain.put("bill_no", medInMain.get("bill_no"));
		
		mode.addAttribute("medInMain", medInMain);
		
		List<String> exists=supDeliveryMainService.queryDeliveryOrderRelaExists(mapVo);
		
		List<Map<String,Object>> orderRelaList = medStorageInService.queryOrderRelaExists(mapVo);
		if(orderRelaList.size()>0){
			mode.addAttribute("flag", "1");
		}else{
			mode.addAttribute("flag", "0");
		}
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));
		
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		mode.addAttribute("p08042", MyConfig.getSysPara("08042"));
		mode.addAttribute("p08044", MyConfig.getSysPara("08044"));
		mode.addAttribute("p08045", MyConfig.getSysPara("08045"));
		
		mode.addAttribute("p08047", MyConfig.getSysPara("08047"));
		
		
		/**
		 * 20170812 应温州要求送货单的数量是可以编辑的所以暂时注释分开不同页面显示的效果
		 * */
		/*if(exists.size()>0){
		  	
			 return "hrp/med/storage/in/updateDelivery";
		}else{
			return "hrp/med/storage/in/update";
		}*/
		return "hrp/med/storage/in/update";
	}

	/**
	 * @Description 更新数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/updateMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			medJson = medStorageInService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		if(medJson.contains("true")){
			mapVo.put("state", "5");
			mapVo.put("flag", "1");
			mapVo.put("whereSql"," and ot.order_amount != it.in_amount ");
			medStorageInService.updateMedOrderState(mapVo);
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  常备药品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/deleteMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			medJson = medStorageInService.deleteBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 复制入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/copyMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMedStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String medJson;
		try {
			medJson = medStorageInService.copyMedStorageInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库单冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/offsetMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	//public Map<String, Object> offsetMedStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
	public Map<String, Object> addMedOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {	
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		mapVo.put("maker", SessionManager.getUserId());
		String medJson;
		try {
			medJson = medStorageInService.add(mapVo);
		}catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
		/*List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
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
		
		String medJson;
		try {
			medJson = medStorageInService.offsetMedStorageInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);*/
	}
	
	/**
	 * @Description 入库单审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/auditMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> auditMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medStorageInService.auditMedStorageIn(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库单消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/unAuditMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medStorageInService.unAuditMedStorageIn(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/confirmMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("confirm_date",mapVo.get("confirm_date"));

		String medJson;
		try {
			medJson = medStorageInService.confirmMedStorageIn(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库单批量审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/auditMedStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("state", 2);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", date);
			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medStorageInService.auditMedStorageInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 入库单批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/unAuditMedStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMedStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String medJson;
		try {
			medJson = medStorageInService.unAuditMedStorageInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 验证结账日期
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/verifyMedClosingDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyMedClosingDate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("confirm_date", ids[4]);
			mapVo.put("is_store", ids[5]);
			mapVo.put("in_no", ids[7]);
			if("1".equals(ids[5])){
				mapVo.put("store_id", ids[6]);
			}
			listVo.add(mapVo);
		}
		
		String medJson;
		try {
			medJson = medStorageInService.verifyMedClosingDate(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * @Description 批量入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/confirmMedStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("confirm_date", ids[4]);
			mapVo.put("is_store", ids[5]);
			mapVo.put("in_no", ids[7]);
			if("1".equals(ids[5])){
				mapVo.put("store_id", ids[6]);
			}
			listVo.add(mapVo);
		}

		String medJson;
		try {
			medJson = medStorageInService.confirmMedStorageInBatch(listVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 订单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/orderImpPage", method = RequestMethod.GET)
	public String medStorageInorderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		//mode.addAttribute("sup_text", new String(mapVo.get("sup_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		
		return "hrp/med/storage/in/orderImp";
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_order_date") != null && !"".equals(mapVo.get("begin_order_date"))){
			mapVo.put("begin_order_date", DateUtil.stringToDate(mapVo.get("begin_order_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_order_date") != null && !"".equals(mapVo.get("end_order_date"))){
			mapVo.put("end_order_date", DateUtil.stringToDate(mapVo.get("end_order_date").toString(), "yyyy-MM-dd"));
		}
		
		String medIn = medStorageInService.queryOrder(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInOrderN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInOrderN(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medStorageInService.queryOrderDetailNew(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	/**
	 * @Description 订单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInOrderDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("order_detail_id", ids[4]);
			listVo.add(mapVo);
		}
		
		String medIn = medStorageInService.queryOrderDetail((listVo));
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 
	 * 配套导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/matchImpPage", method = RequestMethod.GET)
	public String medStorageInmatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		//mode.addAttribute("store_text", new String(mapVo.get("store_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/in/matchImp";
	}
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInMatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medStorageInService.queryMatch(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 
	 * 协议导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/protocolImpPage", method = RequestMethod.GET)
	public String medStorageInprotocolImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		//mode.addAttribute("sup_text", new String(mapVo.get("sup_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		//mode.addAttribute("protocol_text", new String(mapVo.get("protocol_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/in/protocolImp";
	}
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageInProtocol", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInProtocol(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medStorageInService.queryProtocol(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 
	 * 维护发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/invoicePage", method = RequestMethod.GET)
	public String medStorageInInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("money_sum", mapVo.get("money_sum"));
		
		mode.addAttribute("medInMainList", medStorageInService.queryMedInMainByInvoice(mapVo));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/in/invoice";
	}
	/**
	 * @Description 明细数据是否已经全部维护发票信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/existsMedInDetailByInvoice", method = RequestMethod.POST)
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
		
		String medIn = medStorageInService.existsMedInDetailByInvoice(mapVo);
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 
	 * 更新发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/in/updateInvoicePage", method = RequestMethod.GET)
	public String medStorageInUpdateInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("in_id", mapVo.get("in_id"));
		
		
		return "hrp/med/storage/in/updateInvoice";
	}
	
	/**
	 * @Description 发票补登
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/updateMedInMainInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInMainInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//转换日期格式
		if(mapVo.get("bill_date") != null && !"".equals(mapVo.get("bill_date"))){
			mapVo.put("bill_date", DateUtil.stringToDate(mapVo.get("bill_date").toString(), "yyyy-MM-dd"));
		}
		
		String medIn = "";
		try {
			medIn = medStorageInService.updateMedStorageInInvoice(mapVo);
		} catch (Exception e) {
			
			medIn = e.getMessage();
		}
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 生成发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/addMedBillByInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedBillByInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medIn = "";
		try {
			medIn = medStorageInService.addMedBillByInvoice(mapVo);
		} catch (Exception e) {
			
			medIn = e.getMessage();
		}
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medStorageInService.queryMedStorageInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInNextNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String medJson = medStorageInService.queryMedStorageInBeforeOrNext(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * @Description 导入跳转页面  常备药品入库
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/importPage", method = RequestMethod.GET)
	public String medStorageInImportPage(Model mode) throws Exception {

		return "hrp/med/storage/in/Import";

	}

	/**
	 * @Description 下载导入模版  常备药品入库
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/storage/in/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate",
				"常备药品入库.xls");

		return null;
	}

	/**
	 * @Description 导入数据 常备药品入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/in/readFiles", method = RequestMethod.POST)
	public String readMedStorageInFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

		} catch (DataAccessException e) {

			MedInMain data_exc = new MedInMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 常备药品入库
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/in/addBatchMedStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedStorageIn(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MedInMain> list_err = new ArrayList<MedInMain>();

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
			MedInMain data_exc = new MedInMain();
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
	@RequestMapping(value = "/hrp/med/storage/in/storageInPrintSetPage", method = RequestMethod.GET)
	public String storageInPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/storage/in/queryMedInByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medStorageInService.queryMedInByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 合并冲账数据 MED_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/mergeBalanceMedInMain", method = RequestMethod.GET)
	public String mergeBalanceMedInMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String in_id = "";
		String in_no = "";
        
		String in_ids = mapVo.get("in_ids").toString();
		Map<String, Object> medInMain = new HashMap<String, Object>();
		
		for (String id : in_ids.split(",")) {
			String[] v=id.split("@");
			// 表的主键
			in_id = in_id + v[0] + ",";
			in_no = in_no + v[1] + ",";
			mapVo.put("in_id", v[0]);
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
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}

		mapVo.put("in_ids", "(" + in_id.substring(0, in_id.length() - 1) + ")");
		//String medInDetail = medStorageInService.mergeOffsetMedInMain(getPage(mapVo));
		
		medInMain = medStorageInService.queryMedInOffsetMainByCode(mapVo);
		//处理冲账的业务类型
		if("10".equals(medInMain.get("bus_type_code").toString()) || "16".equals(medInMain.get("bus_type_code").toString()) || "22".equals(medInMain.get("bus_type_code").toString())){
			medInMain.put("bus_type_code",medInMain.get("bus_type_code") );
		}else{
			medInMain.put("bus_type_code", "12");
		}
		
		//medInMain.put("bus_type_code", "12");
		medInMain.put("brief", "冲账"+in_no.substring(0, in_no.length() - 1));
		mode.addAttribute("medInMain", medInMain);
		mode.addAttribute("in_ids", mapVo.get("in_ids"));
		//mode.addAttribute("medInDetail", medInDetail);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/in/medInMainOffset";

	}
	
	
	@RequestMapping(value = "/hrp/med/storage/in/queryMedStorageMegerBackDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageMegerBackDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = medStorageInService.mergeOffsetMedInMain(getPage(mapVo));;
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/updateMedStorageInCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStorageInCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/storage/in/medOrderImpCloseInvPage", method = RequestMethod.GET)
	public String medOrderImpCloseInvPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08033", MyConfig.getSysPara("08033"));
		
		
		//String start_date = medStorageInService.queryModeStartDate(mapVo);
		//mode.addAttribute("start_date", start_date);
		
		return "hrp/med/storage/in/medOrderImpCloseInv";
	}
	
	/**
	 * 关闭药品
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/in/updateMedStorageInCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStorageInCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
