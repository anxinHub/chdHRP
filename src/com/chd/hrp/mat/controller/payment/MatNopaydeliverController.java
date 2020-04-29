/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.payment;

import java.io.IOException;
import java.util.*;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.mat.entity.MatInMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.payment.MatNopayDeliverService;

/**
 * 
 * @Description:  期初未付款送货单
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value="/hrp/mat/payment/deliver")
public class MatNopaydeliverController extends BaseController {

	private static Logger logger = Logger.getLogger(MatNopaydeliverController.class);

	// 引入Service服务
	@Resource(name = "matNopayDeliverService")
	private final MatNopayDeliverService matNopayDeliverService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/matNopayDeliverMainPage", method = RequestMethod.GET)
	public String MatCommonInMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/payment/deliver/matNopayDeliverMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matNopayDeliverAddPage", method = RequestMethod.GET)
	public String matCommonInAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/payment/deliver/matNopayDeliverAdd";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/matAffiImpPage", method = RequestMethod.GET)
	public String matAffiImpPage(Model mode) throws Exception {

		mode.addAttribute("paras", matCommonService.queryMatParas());
		return "hrp/mat/payment/deliver/matAffiImp";
	}

	/**
	 * @Description 查询数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_in_date") != null && !"".equals(mapVo.get("begin_in_date"))){
			mapVo.put("begin_in_date", DateUtil.stringToDate(mapVo.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_in_date") != null && !"".equals(mapVo.get("end_in_date"))){
			mapVo.put("end_in_date", DateUtil.stringToDate(mapVo.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("begin_check_date") != null && !"".equals(mapVo.get("begin_check_date"))){
			mapVo.put("begin_check_date", DateUtil.stringToDate(mapVo.get("begin_check_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_check_date") != null && !"".equals(mapVo.get("end_check_date"))){
			mapVo.put("end_check_date", DateUtil.stringToDate(mapVo.get("end_check_date").toString(), "yyyy-MM-dd"));
		}
		String matNopayDeliver = matNopayDeliverService.queryMatNopayDeliver(getPage(mapVo));
		
		return JSONObject.parseObject(matNopayDeliver);
	}
	
	/**
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatNopayDeliverDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatNopayDeliverDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detail = matNopayDeliverService.queryMatNopayDeliverDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatCommonInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return matNopayDeliverService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  期初未付款送货单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson="";
		try{
			matJson = matNopayDeliverService.addMatNopayDeliver(mapVo);
		}catch (Exception e) {
			matJson = "{\"error\":\"操作失败\"}";
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
	@RequestMapping(value = "/matNopayDeliverUpdatePage", method = RequestMethod.GET)
	public String matNopayDeliverUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> matInMain = matNopayDeliverService.queryMatNopayDeliverMainUpdate(mapVo);
		
		if(matInMain.get("in_date") != null && !"".equals(matInMain.get("in_date"))){
			matInMain.put("in_date", DateUtil.dateToString((Date)matInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("bill_date") != null && !"".equals(matInMain.get("bill_date"))){
			matInMain.put("bill_date", DateUtil.dateToString((Date)matInMain.get("bill_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("check_date") != null && !"".equals(matInMain.get("check_date"))){
			matInMain.put("check_date", DateUtil.dateToString((Date)matInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(matInMain.get("confirm_date") != null && !"".equals(matInMain.get("confirm_date"))){
			matInMain.put("confirm_date", DateUtil.dateToString((Date)matInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("matInMain", matInMain);
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04009", MyConfig.getSysPara("04009"));
		
		return "hrp/mat/payment/deliver/matNopayDeliverUpdate";
	}
	
	/**
	 * 上一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatNopayDeliverBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatNopayDeliverBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("flag", "-1");//-1 标识  上一张
		
		String dateMap = matNopayDeliverService.queryMatNopayDeliverBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * 下一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatNopayDeliverNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatNopayDeliverNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("flag", "1");//-1 标识  上一张
		
		String dateMap = matNopayDeliverService.queryMatNopayDeliverBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * @Description 更新数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson ="";
		try{
			matJson = matNopayDeliverService.updateMatNopayDeliver(mapVo);
		}catch (Exception e) {
			matJson = "{\"error\":\"操作失败！\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("deliver_id", ids[3]);
			listVo.add(mapVo);
		}
		String matInvJson ="";
		try{
			matInvJson = matNopayDeliverService.deleteMatNopayDeliverBatch(listVo);
		}catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * 期初未付款送货单出入库单审核、消审
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStateMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStateMatNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String info = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("deliver_id", ids[3]);
			mapVo.put("deliver_no", ids[4]);
			mapVo.put("state", ids[6]);
			if(Integer.parseInt(ids[5].toString()) == 1){
				mapVo.put("checker", SessionManager.getUserId());
				mapVo.put("check_date", date);
			}
			if(Integer.parseInt(ids[5].toString()) == 3){
				// 查询期初未付款送货单  是否已生成发票  
				int count = matNopayDeliverService.queryBillOrNot(mapVo);
				if(count > 0){
					info += mapVo.get("deliver_no"); 
				}
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
			}
			listVo.add(mapVo);
		}
		if( info != ""){
			return JSONObject.parseObject("{\"error\":\"消审失败！【期初未付款送货单："+info.substring(0,info.length()-1)+"已生成发票,不能消审】.\",\"state\":\"true\"}");
		}else{
			// 期初未付款送货单  审核、消审
			String matInvJson ="";
			try{
				matInvJson = matNopayDeliverService.updateStateMatNopayDeliver(listVo);
			}catch (Exception e) {
				matInvJson = e.getMessage();
			}
			return JSONObject.parseObject(matInvJson);
		}
		
	}
	/**
	 * 复制
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyMatNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMatNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("deliver_id", ids[3]);
			mapVo.put("deliver_no", ids[4]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		String matInvJson = "";
		try{
			matInvJson =matNopayDeliverService.copyMatNopayDeliverBatch(listVo);
		}catch (Exception e){
			matInvJson =e.getMessage();
		}
		return JSONObject.parseObject(matInvJson);
	}
	
	/**
	 * @Description 导入跳转页面  期初未付款送货单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPage", method = RequestMethod.GET)
	public String matCommonInImportPage(Model mode) throws Exception {

		return "hrp/mat/payment/deliver/Import";

	}

	/**
	 * @Description 下载导入模版  期初未付款送货单
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/payment/deliver/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate","期初未付款送货单入库.xls");

		return null;
	}

	/**
	 * @Description 导入数据 期初未付款送货单入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/impData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> impData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			retMap = matNopayDeliverService.impData(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		return retMap;
	}
	
	/**
	 * @Description 生成发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMatBillByBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatBillByBill(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> retMap;
		try {
			retMap = matNopayDeliverService.addMatBillByBill(mapVo);
		} catch (Exception e) {

			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * 批量生成发票
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMatBillByBillBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatBillByBillBatch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("deliver_id", ids[3]);
			mapVo.put("sup_id", ids[4]);
			mapVo.put("bill_date", ids[5]);
			mapVo.put("bill_no", ids[6]);
			listVo.add(mapVo);
		}
		
		Map<String, Object> retMap;
		try {
			retMap = matNopayDeliverService.addMatBillByBillBatch(listVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}

		return retMap;
	}
}
