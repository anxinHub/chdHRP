/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.in;

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
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
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
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.invlocation.MatInvLocationService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.platform.dao.ZjMapper;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**  
 * 
 * @Description:  常备材料入库
 * @Table: MAT_IN_MAIN 
 * @Author: bell
 * @email: bell@s-chd.com 
 * @Version: 1.0  
 */
 
@Controller
public class MatStorageInController extends BaseController {      

	private static Logger logger = Logger.getLogger(MatStorageInController.class);

	// 引入Service服务
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	// 引入Service服务
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;
	// 引入Service服务
	@Resource(name = "matInvLocationService")
	private final MatInvLocationService matInvLocationService = null;
	
	@Resource(name = "zjMapper")
	private final ZjMapper zjMapper = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/mainPage", method = RequestMethod.GET)
	public String MatStorageInMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04011", MyConfig.getSysPara("04011"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		return "hrp/mat/storage/in/main";
	}
	
	/**
	 * @Description 根据订单生成入库单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/matOrderImpPage", method = RequestMethod.GET)
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
		
		return "hrp/mat/storage/in/matOrderImp";

	}
	
	/**
	 * @Description 送货单生成面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/deliveryOrderImpPage", method = RequestMethod.GET)
	public String deliveryOrderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/storage/in/deliveryOrderImp";

	}
	
	/**
	 * @Description 送货单主表生成面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/deliveryOrderImpMainPage", method = RequestMethod.GET)
	public String deliveryOrderImpMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//mode.addAttribute("sup_id", mapVo.get("sup_id"));
		//mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/storage/in/deliveryOrderImpMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/addPage", method = RequestMethod.GET)
	public String matStorageInAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p04082", MyConfig.getSysPara("04082"));
		return "hrp/mat/storage/in/add";
	}

	/**
	 * @Description 查询数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		//由于xml中已经to_char （之前我查询的日期是2017-08-11 的日期，查询不到2017-08-11 的数据，必须日期选择为2017-08-12才会查询到）
		//转换日期格式   
		/*if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
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
		*/
		String matIn = "";
		if(mapVo.get("show_detail").equals("1")){
			matIn =  matStorageInService.queryDetails(getPage(mapVo));
		}else{
			matIn =  matStorageInService.query(getPage(mapVo));
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
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matStorageInService.queryDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	/**
	 * @Description 合并打印
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/querymergePrintDetail", method = RequestMethod.POST)
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
		
		String queryMatInSupDetail = matStorageInService.querymergePrintTemlate(getPage(mapVo));

		return JSONObject.parseObject(queryMatInSupDetail);
	}
	
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return matStorageInService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/storage/in/addMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
			matJson = matStorageInService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);

	}
	/**
	 * 订单导入添加入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/hrp/mat/storage/in/addMatStorageInOrder", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatStorageInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String matJson = null;
		//mapVo.put("brief", "根据订单生成入库");
		try {
			
			if(mapVo.get("detailData") != null && !"[]".equals(String.valueOf(mapVo.get("detailData")))){
				JSONArray json = JSONArray.parseArray((String)mapVo.get("detailData"));
				Iterator it = json.iterator();
				JSONArray jsonArray = new JSONArray();
				JSONArray jsonArray1 = new JSONArray();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					//List<Map<String, String>> platformOrderDetailList=zjMapper.getPlatformOrderDetailsByHrpOrderId(jsonObj);//系统现在在用,以前的省平台订单is_good也空
					if( jsonObj.get("is_good") != null && !"".equals(jsonObj.get("is_good")) && "1".equals(jsonObj.get("is_good").toString()) ){
						
						jsonArray.add(jsonObj);
						mapVo.put("jsonArray", jsonArray);
						
					}else{
						
						jsonArray1.add(jsonObj); 
						
						mapVo.put("jsonArray", jsonArray1);
					}
					
				}
				//if(jsonArray.size()>0){
					//matJson = matStorageInService.addInByOrderIsGood(mapVo);//订单如果是省平台的走这个方法
				//}if(jsonArray1.size()>0){
					matJson = matStorageInService.addInByOrder(mapVo);//普通的
				//}
				
			}
		
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "2");
			mapVo.put("flag", "0");
			mapVo.put("whereSql"," and ot.order_amount = it.in_amount ");
			
			matStorageInService.updateMatOrderState(mapVo);
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
	@RequestMapping(value = "/hrp/mat/storage/in/updatePage", method = RequestMethod.GET)
	public String matStorageInUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		Map<String, Object> matInMain = matStorageInService.queryByCode(mapVo);
		// 判断是否置灰发票框
		Integer count = matStorageInService.queryBillCountByInId(mapVo);
		matInMain.put("count", count);
		
		if(matInMain.get("in_date") != null && !"".equals(matInMain.get("in_date"))){
			matInMain.put("in_date", DateUtil.dateToString((Date)matInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("bill_date") != null && !"".equals(matInMain.get("bill_date"))){
			matInMain.put("bill_date", DateUtil.dateToString((Date)matInMain.get("bill_date"), "yyyy-MM-dd"));
		}
			matInMain.put("bill_no", matInMain.get("bill_no"));
			matInMain.put("temperature", matInMain.get("temperature"));
		
		mode.addAttribute("matInMain", matInMain);
		
		if("1".equals(matInMain.get("come_from").toString())){
			mode.addAttribute("flag", "0");
		}else{
			mode.addAttribute("flag", "1");
		}

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04021", MyConfig.getSysPara("04021"));
		mode.addAttribute("p04030", MyConfig.getSysPara("04030"));
		mode.addAttribute("p04042", MyConfig.getSysPara("04042"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04073", MyConfig.getSysPara("04073"));
		mode.addAttribute("p03001", MyConfig.getSysPara("03001"));
		mode.addAttribute("p04076", MyConfig.getSysPara("04076"));
		mode.addAttribute("p04082", MyConfig.getSysPara("04082"));
		/*List<String> exists=supDeliveryMainService.queryDeliveryOrderRelaExists(mapVo);
		List<Map<String,Object>> orderRelaList = matStorageInService.queryOrderRelaExists(mapVo);
		List<Map<String,Object>> DeliveryRelaList = matStorageInService.queryDeliveryRelaExists(mapVo);
		
		if(orderRelaList.size() > 0 || DeliveryRelaList.size() > 0){
			mode.addAttribute("flag", "1");
		}else{
			mode.addAttribute("flag", "0");
		}*/
		
		/**
		 * 20170412 应温州要求送货单的数量是可以编辑的所以暂时注释分开不同页面显示的效果
		 * */
		/*if(exists.size()>0){
			 return "hrp/mat/storage/in/updateDelivery";
		}else{
			return "hrp/mat/storage/in/update";
		}*/
		
		return "hrp/mat/storage/in/update";
	}

	/**
	 * @Description 更新数据  常备材料入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/updateMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			matJson = matStorageInService.update(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		if(matJson.contains("true")){
			mapVo.put("state", "5");
			mapVo.put("flag", "1");
			mapVo.put("whereSql"," and ot.order_amount != it.in_amount ");
			matStorageInService.updateMatOrderState(mapVo);
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
	@RequestMapping(value = "/hrp/mat/storage/in/deleteMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("is_com", "0");
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matStorageInService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 复制入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/copyMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			matJson = matStorageInService.copyMatStorageInBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 入库单冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/offsetMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	//public Map<String, Object> offsetMatStorageIn(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
	public Map<String, Object> addMatOutMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {	
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
		String matJson;
		try {
			matJson = matStorageInService.add(mapVo);
		}catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
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
		
		String matJson;
		try {
			matJson = matStorageInService.offsetMatStorageInBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);*/
	}
	
	/**
	 * @Description 入库单审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/auditMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> auditMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			matJson = matStorageInService.auditMatStorageIn(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 入库单消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/unAuditMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			matJson = matStorageInService.unAuditMatStorageIn(mapVo);
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
	@RequestMapping(value = "/hrp/mat/storage/in/confirmMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatStorageIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String matJson;
		try {
			matJson = matStorageInService.confirmMatStorageIn(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 入库单批量审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/auditMatStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matStorageInService.auditMatStorageInBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 入库单批量消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/unAuditMatStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			matJson = matStorageInService.unAuditMatStorageInBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 验证结账日期
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/verifyMatClosingDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyMatClosingDate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String matJson;
		try {
			matJson = matStorageInService.verifyMatClosingDate(listVo);
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
	@RequestMapping(value = "/hrp/mat/storage/in/confirmMatStorageInBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatStorageInBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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

		String matJson;
		try {
			matJson = matStorageInService.confirmMatStorageInBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 订单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/orderImpPage", method = RequestMethod.GET)
	public String matStorageInorderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		//mode.addAttribute("sup_text", new String(mapVo.get("sup_text").toString().getBytes("ISO-8859-1"),"UTF-8"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04033", MyConfig.getSysPara("04033"));
		
		return "hrp/mat/storage/in/orderImp";
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matStorageInService.queryOrder(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInOrderN", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInOrderN(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		/*if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}*/
		
		String matIn = matStorageInService.queryOrderDetailNew(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}
	/**
	 * @Description 订单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInOrderDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
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
		
		String matIn = matStorageInService.queryOrderDetail((listVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 
	 * 配套导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/matchImpPage", method = RequestMethod.GET)
	public String matStorageInmatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		//mode.addAttribute("store_text", new String(mapVo.get("store_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/in/matchImp";
	}
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInMatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInMatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matStorageInService.queryMatch(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 
	 * 协议导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/protocolImpPage", method = RequestMethod.GET)
	public String matStorageInprotocolImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		//mode.addAttribute("sup_text", new String(mapVo.get("sup_text").toString().getBytes("ISO-8859-1"),"UTF-8"));
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		//mode.addAttribute("protocol_text", new String(mapVo.get("protocol_text").toString().getBytes("ISO-8859-1"),"UTF-8"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/in/protocolImp";
	}
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageInProtocol", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInProtocol(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = matStorageInService.queryProtocol(getPage(mapVo));
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 
	 * 维护发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/invoicePage", method = RequestMethod.GET)
	public String matStorageInInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("money_sum", mapVo.get("money_sum"));
		
		mode.addAttribute("matInMainList", matStorageInService.queryMatInMainByInvoice(mapVo));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04079", MyConfig.getSysPara("04079"));
		
		return "hrp/mat/storage/in/invoice";
	}
	/**
	 * @Description 明细数据是否已经全部维护发票信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/existsMatInDetailByInvoice", method = RequestMethod.POST)
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
		
		String matIn = matStorageInService.existsMatInDetailByInvoice(mapVo);
		
		return JSONObject.parseObject(matIn);
	}

	/**
	 * @Description 
	 * 更新发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/updateInvoicePage", method = RequestMethod.GET)
	public String matStorageInUpdateInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("in_id", mapVo.get("in_id"));
		mode.addAttribute("in_date", mapVo.get("in_date"));
		
		// 保留已存在发票号
		Map<String, Object> matInMain = matStorageInService.queryByCode(mapVo); 
		mode.addAttribute("exist_bill_no", matInMain.get("bill_no"));
		
		// 用发票记录来判断是否 置灰 已存在发票号 文本框
		Integer count = matStorageInService.queryBillCountByInId(mapVo);
		mode.addAttribute("count", count);
		mode.addAttribute("p04079", MyConfig.getSysPara("04079"));
		
		return "hrp/mat/storage/in/updateInvoice";
	}
	
	/**
	 * @Description 发票补登
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/updateMatInMainInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInMainInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matIn = "";
		try {
			matIn = matStorageInService.updateMatStorageInInvoice(mapVo);
		} catch (Exception e) {
			
			matIn = e.getMessage();
		}
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 生成发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/addMatBillByInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatBillByInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matIn = "";
		try {
			matIn = matStorageInService.addMatBillByInvoice(mapVo);
		} catch (Exception e) {
			
			matIn = e.getMessage();
		}
		
		return JSONObject.parseObject(matIn);
	}
	
	/**
	 * @Description 获取上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matStorageInService.queryMatStorageInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 获取下一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInNextNo(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matStorageInService.queryMatStorageInBeforeOrNext(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 导入跳转页面  常备材料入库
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/importPage", method = RequestMethod.GET)
	public String matStorageInImportPage(Model mode) throws Exception {

		return "hrp/mat/storage/in/Import";

	}

	/**
	 * @Description 下载导入模版  常备材料入库
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/storage/in/downTemplate", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/mat/storage/in/readFiles", method = RequestMethod.POST)
	public String readMatStorageInFiles(Plupload plupload,
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
	@RequestMapping(value = "/hrp/mat/storage/in/addBatchMatStorageIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatStorageIn(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
	@RequestMapping(value = "/hrp/mat/storage/in/storageInPrintSetPage", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatInByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=matStorageInService.queryMatInByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 合并冲账数据 MAT_IN_MAIN
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/mergeBalanceMatInMain", method = RequestMethod.GET)
	public String mergeBalanceMatInMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String in_id = "";
		String in_no = "";
        
		String in_ids = mapVo.get("in_ids").toString();
		Map<String, Object> matInMain = new HashMap<String, Object>();
		
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
		//String matInDetail = matStorageInService.mergeOffsetMatInMain(getPage(mapVo));
		
		matInMain = matStorageInService.queryMatInOffsetMainByCode(mapVo);
		//处理冲账的业务类型
		if("10".equals(matInMain.get("bus_type_code").toString()) || "16".equals(matInMain.get("bus_type_code").toString()) || "22".equals(matInMain.get("bus_type_code").toString())){
			matInMain.put("bus_type_code",matInMain.get("bus_type_code") );
		}else{
			matInMain.put("bus_type_code", "12");
		}
		
		//matInMain.put("bus_type_code", "12");
		matInMain.put("brief", "冲账"+in_no.substring(0, in_no.length() - 1));
		mode.addAttribute("matInMain", matInMain);
		mode.addAttribute("in_ids", mapVo.get("in_ids"));
		//mode.addAttribute("matInDetail", matInDetail);

		return "hrp/mat/storage/in/matInMainOffset";

	}
	
	
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatStorageMegerBackDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageMegerBackDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		 
		String detail = matStorageInService.queryMatStorageMegerBackDetail(getPage(mapVo));;
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/updateMatStorageInCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStorageInCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/storage/in/matOrderImpCloseInvPage", method = RequestMethod.GET)
	public String matOrderImpCloseInvPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

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
		
		return "hrp/mat/storage/in/matOrderImpCloseInv";
	}
	
	/**
	 * 关闭材料
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/updateMatStorageInCancleCloseInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStorageInCancleCloseInv(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	
	/**
	 * @Description 
	 * 入出库查询-材料入库库存数量明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/in/invInDetailPage", method = RequestMethod.GET)
	public String invInDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String invInDetail = matStorageInService.queryInvInDetail(mapVo);
		
		
		mode.addAttribute("invInDetail", JSONObject.parseObject(invInDetail));
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/in/invInDetail";
	}
	/**
	 * 选择材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/choiceInvBySupPage", method = RequestMethod.GET)
	public String choiceInvBySupPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/in/choiceInvBySup";
	}
	
	/**
	 * 根据供应商查材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/queryMatChoiceInvBySup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatChoiceInvBySup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matStorageInService.queryMatChoiceInvBySup(getPage(mapVo));;
		
		return JSONObject.parseObject(detail);
	}
	
	@RequestMapping(value = "/hrp/mat/storage/in/queryChoiceInvBySupData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChoiceInvBySupData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
					
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
					
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
					
		String detailData = matStorageInService.queryChoiceInvBySupData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}
	
	/**
	 * 批量生成发票
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/in/updateBatchMatStorageInInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchMatStorageInInvoice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("sup_id", ids[4]);
			mapVo.put("bill_date", ids[5]);
			mapVo.put("bill_no", ids[6]);
			listVo.add(mapVo);
		}
		
		String matJson;
		try {
			matJson = matStorageInService.updateBatchMatStorageInInvoice(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value={"/hrp/mat/storage/in/sendInDataOne"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public Map<String, Object> sendInDataOne(@RequestParam Map<String, Object> mapVo, Model mode)
	    throws Exception
	  {
	    String matJson = "{\"msg1\":\"操作成功.\",\"state\":\"true\"}";
	    try {
	      String in_ids = mapVo.get("in_id").toString();

	      if ("1".equals(MyConfig.getSysPara("04076")))
	      {
	        Map map = new HashMap();
	        map.put("group_id", SessionManager.getGroupId());
	        map.put("hos_id", SessionManager.getHosId());
	        map.put("copy_code", SessionManager.getCopyCode());
	        map.put("in_ids", Character.valueOf('0'));
	        if (!(Strings.isBlank(in_ids))) {
	          map.put("in_ids", in_ids);
	        }

	        Map mapshandeng = map;
	        mapshandeng.put("flag", "01");
	        List list = matInvLocationService.querySendData(mapshandeng);

	        if (!(Lang.isEmpty(list))) {
	          matInvLocationService.matInvlocation(list, "03");
	        }

	      }

	      matJson = "{\"msg\":\"电子标签发送成功.\",\"state\":\"true\"}";
	    }
	    catch (Exception e) {
	      matJson = e.getMessage();
	    }

	    return JSONObject.parseObject(matJson);
	  }
}
