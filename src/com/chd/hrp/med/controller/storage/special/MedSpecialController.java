/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.special;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.chd.hrp.med.entity.MedInResource;
import com.chd.hrp.med.service.affi.out.MedAffiOutCommonService;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.special.MedSpecialService;

/**
 * 
 * @Description:  专购品
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(MedSpecialController.class);

	// 引入Service服务
	@Resource(name = "medSpecialService")
	private final MedSpecialService medSpecialService = null;
	
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "medAffiOutCommonService")
	private final MedAffiOutCommonService medAffiOutCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/medSpecialMainPage", method = RequestMethod.GET)
	public String MedCommonInMainPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		
		return "hrp/med/storage/special/medSpecialMain";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/special/medSpecialAddPage", method = RequestMethod.GET)
	public String medCommonInAddPage(Model mode) throws Exception {

		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));
		
		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		
		return "hrp/med/storage/special/medSpecialAdd";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/medAffiImpOldPage", method = RequestMethod.GET)
	public String medAffiImpOldPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/special/medAffiImpOld";
	}
	/**
	 * 代销使用导入页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/medAffiImpPage", method = RequestMethod.GET)
	public String medAffiImpPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/storage/special/medAffiImp";
	}

	/**
	 * @Description 查询数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
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
		
		if(mapVo.get("bus_type_code") != null && !"".equals(mapVo.get("bus_type_code"))){
			if("47".equals(mapVo.get("bus_type_code")) || "48".equals(mapVo.get("bus_type_code")) ){
				mapVo.put("bus_type_flag", 1);
			}
			
			if("49".equals(mapVo.get("bus_type_code")) || "50".equals(mapVo.get("bus_type_code")) ){
				mapVo.put("bus_type_flag", 2);
			}
		}
		
		String medSpecial = "";
		if(mapVo.get("show_detail").equals("1")){
			medSpecial =  medSpecialService.queryDetails(getPage(mapVo));
		}else{
			medSpecial =  medSpecialService.queryMedSpecial(getPage(mapVo));
		}
		
		return JSONObject.parseObject(medSpecial);
	}
	
	
	
	
	/**
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecialDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detail = medSpecialService.queryMedSpecialDetailByCode(mapVo);
		
		return JSONObject.parseObject(detail);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedCommonInById", method = RequestMethod.POST)
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

		return medSpecialService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  专购品
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/addMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMedSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String medJson =null ;
		try {
			medJson = medSpecialService.add(mapVo);
		} catch (Exception e) {
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
	@RequestMapping(value = "/hrp/med/storage/special/updatePage", method = RequestMethod.GET)
	public String medCommonInUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInMain = medSpecialService.queryMedSpecialMainUpdate(mapVo);
		
		Map<String,Object> dateMap = medSpecialService.queryMedInOutData(mapVo);
		
		mapVo.put("in_id", dateMap.get("in_id"));
		
		MedInResource inResource = medStorageInService.queryMedInResource(mapVo);
		
		if(medInMain.get("make_date") != null && !"".equals(medInMain.get("make_date"))){
			medInMain.put("make_date", DateUtil.dateToString((Date)medInMain.get("make_date"), "yyyy-MM-dd"));
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
		
		mode.addAttribute("money_sum", inResource.getSource_money());
		mode.addAttribute("medInMain", medInMain);
		mode.addAttribute("paras", medCommonService.queryMedParas());
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08009", MyConfig.getSysPara("08009"));

		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		mode.addAttribute("p08021", MyConfig.getSysPara("08021"));
		mode.addAttribute("p08030", MyConfig.getSysPara("08030"));

		mode.addAttribute("p08043", MyConfig.getSysPara("08043"));
		
		return "hrp/med/storage/special/medSpecialUpdate";
	}
	
	/**
	 * 上一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecialBeforeNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecialBeforeNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String dateMap = medSpecialService.queryMedSpecialBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * 下一张
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecialNextNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecialNextNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String dateMap = medSpecialService.queryMedSpecialBeforeOrNextNo(mapVo);
		
		return JSONObject.parseObject(dateMap);
	}
	/**
	 * @Description 更新数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/updateMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		if(mapVo.get("make_date") != null && !"".equals(mapVo.get("make_date"))){
			mapVo.put("make_date",df.parse(mapVo.get("make_date").toString()));
		}

		//处理发票日期
		if(mapVo.get("bill_date") != null && !"".equals(mapVo.get("bill_date"))){
			mapVo.put("bill_date", DateUtil.stringToDate(mapVo.get("bill_date").toString(), "yyyy-MM-dd"));
		}
		
		//入库编制时间
		mapVo.put("in_date",mapVo.get("make_date"));
		//即入即出  出库编制时间
		mapVo.put("out_date",mapVo.get("make_date"));
		String medJson =null ;
		try {
			medJson = medSpecialService.update(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  专购品入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/deleteMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			Map<String,Object> data = medSpecialService.queryMedInOutData(mapVo);
			mapVo.put("in_id", data.get("in_id"));
			mapVo.put("in_no", data.get("in_no"));
			mapVo.put("out_id", data.get("out_id"));
			mapVo.put("out_no", data.get("out_no"));
			listVo.add(mapVo);
		}
		String medInvJson = null ;
		try {
			medInvJson = medSpecialService.deleteBatch(listVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 专购品  冲账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/offsetMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> offsetMedSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			mapVo.put("in_id", ids[5]);
			mapVo.put("in_no", ids[6]);
			mapVo.put("out_id", ids[7]);
			mapVo.put("out_no", ids[8]);
			mapVo.put("maker", SessionManager.getUserId());
			listVo.add(mapVo);
		}
		String medInvJson = null ;
		try {
			medInvJson = medSpecialService.offsetMedSpecialBatch(listVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}
		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * 专购品出入库单审核、消审
	 * @Description 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/updateState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("special_id", ids[3]);
			mapVo.put("special_no", ids[4]);
			Map<String,Object> data = medSpecialService.queryMedInOutData(mapVo);
			mapVo.put("in_id", data.get("in_id"));
			mapVo.put("in_no", data.get("in_no"));
			mapVo.put("out_id", data.get("out_id"));
			mapVo.put("out_no", data.get("out_no"));
			mapVo.put("state", ids[6]);
			if(Integer.parseInt(ids[5].toString()) == 1){
				mapVo.put("checker", SessionManager.getUserId());
				mapVo.put("check_date", date);
			}
			if(Integer.parseInt(ids[5].toString()) == 2){
				mapVo.put("checker", "");
				mapVo.put("check_date", "");
			}
			listVo.add(mapVo);
		}
		// 专购品  审核、消审
		String specialJson = null ;
		try {
			specialJson = medSpecialService.updateState(listVo);
		} catch (Exception e) {
			specialJson = e.getMessage();
		}
		return JSONObject.parseObject(specialJson);
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/confirmMedSpecialOld", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedSpecialOld(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String medInvJson = null ;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("special_id", ids[3]);
				mapVo.put("special_no", ids[4]);
				mapVo.put("year", ids[5]);
				mapVo.put("month", ids[6]);
				mapVo.put("store_id", ids[7]);
				
				Map<String,Object> data = medSpecialService.queryMedInOutData(mapVo);
				mapVo.put("in_id", data.get("in_id"));
				mapVo.put("in_no", data.get("in_no"));
				mapVo.put("out_id", data.get("out_id"));
				mapVo.put("out_no", data.get("out_no"));
				
				//根据 专购品主表Id 查询 专购品明细
				List<Map<String,Object>> detailList = medSpecialService.querySpecialDetail(mapVo);
				for(Map<String,Object> item : detailList){
					Map<String, Object> invMap = new HashMap<String, Object>();
					invMap.putAll(mapVo);
					invMap.put("inv_id", item.get("inv_id"));
					invMap.put("batch_sn", item.get("batch_sn"));
					invMap.put("batch_no", item.get("batch_no"));
					invMap.put("bar_code", item.get("bar_code"));
					invMap.put("price", item.get("price"));
					invMap.put("sale_price", item.get("sale_price"));
					invMap.put("in_amount", item.get("amount"));
					invMap.put("in_money", item.get("amount_money"));
					invMap.put("in_sale_money", "0");
					invMap.put("out_amount", item.get("amount"));
					invMap.put("out_money", item.get("amount_money"));
					invMap.put("out_sale_money", "0");
					invMap.put("inva_date", item.get("inva_date"));
					invMap.put("disinfect_date", item.get("disinfect_date"));
					invMap.put("location_id", item.get("location_id"));
					invMap.put("left_amount", "0");
					invMap.put("left_money", "0");
					invMap.put("left_sale_money", "0");
					invMap.put("remove_zero_error", "0");
					invMap.put("sale_zero_error", "0");
					invMap.put("cur_amount", "0");
					invMap.put("cur_money", "0");
					
					invMap.put("state", 3);
					invMap.put("confirmer", SessionManager.getUserId());
					invMap.put("confirm_date", date);
					listVo.add(invMap);
				}
			}
		
			medInvJson = medSpecialService.confirmMedSpecialBatch(listVo);
		} catch (Exception e) {
			
			medInvJson = e.getMessage();
		}
		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 专购品确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/confirmMedSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMedSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		String[] dates = DateUtil.dateToString(date, "yyyy-MM-dd").split("-");
		String user_id = SessionManager.getUserId();
		String medInvJson = null;
		try {
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("special_id", ids[3]);
				mapVo.put("special_no", ids[4]);
				
				Map<String,Object> data = medSpecialService.queryMedInOutData(mapVo);
				mapVo.put("in_id", data.get("in_id"));
				mapVo.put("in_no", data.get("in_no"));
				mapVo.put("out_id", data.get("out_id"));
				mapVo.put("out_no", data.get("out_no"));
				
				mapVo.put("state", "3");
				mapVo.put("confirmer", user_id);
				mapVo.put("confirm_date", date);
				mapVo.put("year", dates[0]);
				mapVo.put("month", dates[1]);
				listVo.add(mapVo);
			}
		
			medInvJson = medSpecialService.confirmMedSpecialBatch(listVo);
		} catch (Exception e) {
			
			medInvJson = e.getMessage();
		}
		return JSONObject.parseObject(medInvJson);
	}
	
	/**
	 * 代销使用生成
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/affiDateOld", method = RequestMethod.POST)
	@ResponseBody
	public String affiDateOld(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String affiStr = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("out_id", ids[3]);
			mapVo.put("year", ids[4]);
			mapVo.put("month", ids[5]);
			mapVo.put("bus_type_code", "01");
			mapVo.put("store_id", ids[7]);
			mapVo.put("store_no", ids[8]);
			mapVo.put("brief", "代销使用生成");
			mapVo.put("dept_id", ids[10]);
			mapVo.put("dept_no", ids[11]);
			mapVo.put("dept_emp", ids[12]);
			mapVo.put("store_id", ids[13]);
			mapVo.put("out_no", ids[14]);
			mapVo.put("use_state", "1");
			String  detail = medSpecialService.queryMedAffiDetail(mapVo);
			List<Map<String,Object>> list = (List<Map<String, Object>>) JSONObject.parseObject(detail).get("Rows");
			
			String info = "" ;
			Map<String,String> supMap =new HashMap<String,String>();
			
			for(Map<String,Object> mapKey:list){
				if(mapKey.get("sup_id") != null ){
					supMap.put(String.valueOf(mapKey.get("sup_id")), String.valueOf(mapKey.get("sup_id")));
				}else{
					info += mapKey.get("inv_code") + "," ;
				}
			}
			if( info != "" ){
				return "{\"error\":\"代销使用生成失败!【出库单:"+mapVo.get("out_no")+"药品:"+info.substring(0,info.length()-1)+"】没有供应商.不能使用代销生成！\"}";
			}else{
				for(String key :supMap.keySet()){
					List<Map<String,Object>> detailData = new ArrayList<Map<String,Object>>();
					for(Map<String,Object> t : list){
						if(key.equals(String.valueOf(t.get("sup_id")))){
							detailData.add(t);
						}
					}

					mapVo.put("sup_id", key);
					mapVo.put("sup_no", detailData.get(0).get("sup_no"));
					mapVo.put("detailData", String.valueOf(detailData));
					mapVo.put("bus_type", "47");
					mapVo.put("special_no", "自动生成");
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					
					mapVo.put("make_date",df.format(new Date()));
					mapVo.put("maker", SessionManager.getUserId());
					
					try{
						affiStr = medSpecialService.add(mapVo);
						mapVo.put("affi_out_id", ids[3]);
						medSpecialService.updateAffiOutState(mapVo);
					}catch(Exception e){
						affiStr = e.getMessage();
					}
				}
			}
		}
		return affiStr;
	}
	
	/**
	 * 代销使用生成
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/affiDate", method = RequestMethod.POST)
	@ResponseBody
	public String affiDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String affiStr = "";
		
		try{
			affiStr = medSpecialService.addByAffiOut(mapVo);
		}catch(Exception e){
			affiStr = e.getMessage();
		}
		return affiStr;
	}

	/**
	 * @Description 
	 * 订单导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/special/orderImpPage", method = RequestMethod.GET)
	public String medCommonInorderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		return "hrp/med/storage/special/orderImp";
	}
	/**
	 * @Description 订单查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		if(mapVo.get("begin_order_date") != null && !"".equals(mapVo.get("begin_order_date"))){
			mapVo.put("begin_order_date", DateUtil.stringToDate(mapVo.get("begin_order_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_order_date") != null && !"".equals(mapVo.get("end_order_date"))){
			mapVo.put("end_order_date", DateUtil.stringToDate(mapVo.get("end_order_date").toString(), "yyyy-MM-dd"));
		}
		
		String medIn = medSpecialService.queryOrder(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 订单明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrderDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)throws Exception {
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
		
		String medIn = medSpecialService.queryOrderDetail((listVo));
		
		return JSONObject.parseObject(medIn);
	}

	/**
	 * @Description 代销出库单 主数据  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryAffiOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAffiOut(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medIn = medSpecialService.queryAffiOut(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 代销出库单 明细数据  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedAffiDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medIn = medSpecialService.queryMedAffiDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}


	/**
	 * @Description 
	 * 协议导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/special/protocolImpPage", method = RequestMethod.GET)
	public String medCommonInprotocolImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("protocol_id", mapVo.get("protocol_id"));
		mode.addAttribute("protocol_text", mapVo.get("protocol_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/special/protocolImp";
	}
	
	/**
	 * 专购品供应商导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/sup_ImpPage", method = RequestMethod.GET)
	public String medCommonInsupImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/med/storage/special/specialSupImp";
	}
	
	/**
	 * 请求后台解析
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/supImportToJson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> medCommonInsupImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		Map<String,Object> entityMap = new HashMap<String, Object>();
		
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		entityMap.put("by_sup_inv", MyConfig.getSysPara("08021"));
		entityMap.put("sup_id", mapVo.get("sup_id"));
		entityMap.put("store_id", mapVo.get("store_id"));
		
		String impJson = medSpecialService.importMedCommonInsup(mapVo,entityMap);
		return JSONObject.parseObject(impJson);
		
	}
	
	
	/**
	 * @Description 配套查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedCommonInProtocol", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonInProtocol(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medIn = medSpecialService.queryProtocol(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * @Description 导入跳转页面  专购品
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/importPage", method = RequestMethod.GET)
	public String medCommonInImportPage(Model mode) throws Exception {

		return "hrp/med/storage/special/Import";

	}

	/**
	 * @Description 下载导入模版  专购品
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/storage/special/downTemplateMedCommonIn", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate","专购品导入.xlsx");

		return null;
	}

	/**
	 * @Description 导入数据 专购品入库@param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/special/readFiles", method = RequestMethod.POST)
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
	 * @Description 批量添加数据 专购品
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/storage/special/addBatchMedCommonIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedCommonIn(
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
	@RequestMapping(value = "/hrp/med/storage/special/medSpecialPrintSetPage", method = RequestMethod.GET)
	public String medSpecialPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecialByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecialByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=medSpecialService.queryMedSpecialByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 代销专购品生成查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedAffiSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiSpecial(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medSpecial = medSpecialService.queryMedAffiSpecial(getPage(mapVo));
		
		return JSONObject.parseObject(medSpecial);
	}

	/**
	 * @Description 
	 * 更新发票号页面跳转 
	 * @param mapVo
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/storage/special/updateSpecialInvoicePage", method = RequestMethod.GET)
	public String updateSpecialInvoicePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("special_id", mapVo.get("special_id"));
		mode.addAttribute("in_id", mapVo.get("in_id"));
		
		return "hrp/med/storage/special/updateInvoice";
	}
	
	/**
	 * @Description 发票补登
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/updateMedSpecialInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedSpecialInvoice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medSpecial = "";
		try {
			medSpecial = medSpecialService.updateMedSpecialInvoice(mapVo);
		} catch (Exception e) {
			
			medSpecial = e.getMessage();
		}
		
		return JSONObject.parseObject(medSpecial);
	}
	
	
	/**
	 * 批量添加药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/medSpecailInvBatchImpPage", method = RequestMethod.GET)
	public String medSpecailInvBatchImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/special/medSpecailInvBatchImp";

	}
	
	/**
	 * 批量添加查询药品
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecailInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecailInvBatch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medIn = medSpecialService.queryMedSpecailInvBatch(getPage(mapVo));
		
		return JSONObject.parseObject(medIn);
	}
	
	/**
	 * 批量选择药品生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/special/queryMedSpecailInvDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedSpecailInvDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String detailData = medSpecialService.queryMedSpecailInvDetail(mapVo);
		
		return JSONObject.parseObject(detailData);
		
	}
}
