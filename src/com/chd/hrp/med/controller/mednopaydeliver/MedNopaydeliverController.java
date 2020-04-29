/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.mednopaydeliver;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedInMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.mednopaydeliver.MedNopayDeliverService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;

/**
 * 
 * @Description:  期初未付款送货单
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedNopaydeliverController extends BaseController {

	private static Logger logger = Logger.getLogger(MedNopaydeliverController.class);

	// 引入Service服务
	@Resource(name = "medNopayDeliverService")
	private final MedNopayDeliverService medNopayDeliverService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/medNopayDeliverMainPage", method = RequestMethod.GET)
	public String MedCommonInMainPage(Model mode) throws Exception {
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/mednopay/deliver/medNopayDeliverMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/mednopay/deliver/medNopayDeliverAddPage", method = RequestMethod.GET)
	public String medCommonInAddPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));

		return "hrp/med/mednopay/deliver/medNopayDeliverAdd";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/medAffiImpPage", method = RequestMethod.GET)
	public String medAffiImpPage(Model mode) throws Exception {

		mode.addAttribute("paras", medCommonService.queryMedParas());
		return "hrp/med/mednopay/deliver/medAffiImp";
	}

	/**
	 * @Description 查询数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/queryMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		String medNopayDeliver = medNopayDeliverService.queryMedNopayDeliver(getPage(mapVo));
		
		return JSONObject.parseObject(medNopayDeliver);
	}
	
	/**
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/queryMedNopayDeliverDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedNopayDeliverDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detail = medNopayDeliverService.queryMedNopayDeliverDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/queryMedCommonInById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonInById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return medNopayDeliverService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  期初未付款送货单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/addMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		mapVo.put("in_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		
		
		if(mapVo.get("confirm_date") != null && !"".equals(mapVo.get("confirm_date"))){
			mapVo.put("confirm_date", DateUtil.stringToDate(mapVo.get("confirm_date").toString(), "yyyy-MM-dd"));
		}
		String medJson="";
		try{
			medJson = medNopayDeliverService.addMedNopayDeliver(mapVo);
		}catch (Exception e) {
			medJson = e.getMessage();
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
	@RequestMapping(value = "/hrp/med/mednopay/deliver/medNopayDeliverUpdatePage", method = RequestMethod.GET)
	public String medNopayDeliverUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInMain = medNopayDeliverService.queryMedNopayDeliverMainUpdate(mapVo);
		
		if(medInMain.get("in_date") != null && !"".equals(medInMain.get("in_date"))){
			medInMain.put("in_date", DateUtil.dateToString((Date)medInMain.get("in_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("check_date") != null && !"".equals(medInMain.get("check_date"))){
			medInMain.put("check_date", DateUtil.dateToString((Date)medInMain.get("check_date"), "yyyy-MM-dd"));
		}
		if(medInMain.get("confirm_date") != null && !"".equals(medInMain.get("confirm_date"))){
			medInMain.put("confirm_date", DateUtil.dateToString((Date)medInMain.get("confirm_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("medInMain", medInMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));

		return "hrp/med/mednopay/deliver/medNopayDeliverUpdate";
	}
	
	/**
	 * 上一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/queryMedNopayDeliverBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedNopayDeliverBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String dateMap = medNopayDeliverService.queryMedNopayDeliverBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * 下一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/queryMedNopayDeliverNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedNopayDeliverNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String dateMap = medNopayDeliverService.queryMedNopayDeliverBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * @Description 更新数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/updateMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedNopayDeliver(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(mapVo.get("confirm_date") != null && !"".equals(mapVo.get("confirm_date"))){
			mapVo.put("confirm_date",df.parse(mapVo.get("confirm_date").toString()));
		}
		String medJson ="";
		try{
			medJson = medNopayDeliverService.updateMedNopayDeliver(mapVo);
		}catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  期初未付款送货单入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/deleteMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			listVo.add(mapVo);
		}
		String medInvJson ="";
		try{
			medInvJson = medNopayDeliverService.deleteMedNopayDeliverBatch(listVo);
		}catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * 期初未付款送货单出入库单审核、消审
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/updateStateMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStateMedNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				int count = medNopayDeliverService.queryBillOrNot(mapVo);
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
			String medInvJson ="";
			try{
				medInvJson = medNopayDeliverService.updateStateMedNopayDeliver(listVo);
			}catch (Exception e) {
				medInvJson = e.getMessage();
			}
			return JSONObject.parseObject(medInvJson);
		}
		
	}
	/**
	 * 复制
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/copyMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyMedNopayDeliver(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String medInvJson = "";
		try{
			medInvJson =medNopayDeliverService.copyMedNopayDeliverBatch(listVo);
		}catch (Exception e){
			medInvJson =e.getMessage();
		}
		return JSONObject.parseObject(medInvJson);
	}
	
	/**
	 * @Description 导入跳转页面  期初未付款送货单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/importPage", method = RequestMethod.GET)
	public String medCommonInImportPage(Model mode) throws Exception {

		return "hrp/med/mednopay/deliver/Import";

	}

	/**
	 * @Description 下载导入模版  期初未付款送货单
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/mednopay/deliver/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate","期初未付款送货单入库.xls");

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
	@RequestMapping(value = "/hrp/med/mednopay/deliver/readFiles", method = RequestMethod.POST)
	public String readMedCommonInFiles(Plupload plupload,
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
	 * @Description 批量添加数据 期初未付款送货单
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/mednopay/deliver/addBatchMedNopayDeliver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedNopayDeliver(
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
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));
		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
}
