/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.bill;
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
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedBillMain;
import com.chd.hrp.med.service.bill.MedBillDetailService;
import com.chd.hrp.med.service.bill.MedBillMainService;
import com.chd.hrp.med.serviceImpl.bill.MedBillDetailServiceImpl;
/**
 * 
 * @Description:
 * 保存采购发票的主要信息
 * @Table:
 * MED_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedBillMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedBillMainController.class);
	
	//引入Service服务
	@Resource(name = "medBillMainService")
	private final MedBillMainService medBillMainService = null;
	
	@Resource(name = "medBillDetailService")
	private final MedBillDetailService medBillDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/medBillMainMainPage", method = RequestMethod.GET)
	public String medBillMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08027", MyConfig.getSysPara("08027"));
		
		return "hrp/med/bill/medbillmain/medBillMainMain";

	}
	
	@RequestMapping(value = "/hrp/med/bill/medbillmain/inMainPage", method = RequestMethod.GET)
	public String inMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		
		return "hrp/med/bill/medbillmain/inMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/medBillMainAddPage", method = RequestMethod.GET)
	public String medBillMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/bill/medbillmain/medBillMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/addMedBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("ori_no", "0000");
		mapVo.put("stock_type_code", "");
		mapVo.put("maker", SessionManager.getUserId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("bill_date", df.parse(String.valueOf(mapVo.get("bill_date"))));
		mapVo.put("make_date", df.parse(df.format(new Date())));
		mapVo.put("checker", "");
		mapVo.put("chk_date", null);
		mapVo.put("is_init",mapVo.get("init"));
		mapVo.put("state", "1");
		String medBillMainJson ="";
		try{
			medBillMainJson = medBillMainService.addMedBillMain(mapVo);
		} catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
		return JSONObject.parseObject(medBillMainJson);
		
	}
	/**
	 * 添加、修改发票明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/addMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedBillDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加发票明细 用List
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		
		//新增时查询发票明细对应的发票主表的Id
		Long bill_id = medBillMainService.queryBillMainMaxId();
	
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			if("undefined".equals(ids[10])){
				//添加发票明细  数据
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])  ;
				if("undefined".equals(ids[3])){
					map.put("bill_id", bill_id);
				}else{
					map.put("bill_id", ids[3]);
				}
				
				map.put("in_id", ids[4]);
				map.put("in_detail_id",ids[5]);
				map.put("payable_money", ids[6]);
				map.put("bill_money", ids[7]);
				map.put("bill_no", ids[8]);
				map.put("bill_date", DateUtil.stringToDate(ids[9], "yyyy-MM-dd"));
				map.put("bill_datail_id",null);
				listVo.add(map);
			}
		}
		String medBillMainJson ="";
		try{
			if(listVo.size()>0){
				 medBillMainJson = medBillDetailService.addBatchMedBillDetail(listVo);
			}
		} catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medBillMainJson);
		
	}
	/**
	 * 更新发票明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/updateMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedBillDetail(@RequestParam(value="ParamVo") String updatePara, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
	
		for ( String id: updatePara.split(",")) {
			
			String[] ids=id.split("@");
			/*Map<String,Object> mapVo = new HashMap<String,Object>();
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])  ;
			mapVo.put("bill_id", ids[3]);
			mapVo.put("bill_no", ids[8]);
			
			//List<Long> inDetailIdList = medBillMainService.queryIn_detail_id(mapVo);
			//删除 修改发票对应的所有发票明细
			medBillDetailService.deleteMedBillDetail(mapVo);*/
			//for(Long item : inDetailIdList ){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("group_id", ids[0])   ;
			map.put("hos_id", ids[1])   ;
			map.put("copy_code", ids[2])  ;
			map.put("bill_id", ids[3]);
			map.put("in_id", ids[4]);
			map.put("in_detail_id",ids[5]);
			map.put("payable_money", ids[6]);
			map.put("bill_money", ids[7]);
			map.put("bill_no", ids[8]);
			map.put("bill_date", DateUtil.stringToDate(ids[9], "yyyy-MM-dd"));
			map.put("bill_datail_id",null);
			listVo.add(map);
		}
		String medBillMainJson ="";
		try{
			medBillMainJson = medBillDetailService.addBatchMedBillDetail(listVo);
		} catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medBillMainJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/medBillMainUpdatePage", method = RequestMethod.GET)
	public String medBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String,Object> updateMap = new HashMap<String,Object>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		updateMap = medBillMainService.queryMedBillMainReturnUpdate(mapVo);
		mode.addAttribute("group_id", updateMap.get("group_id"));
		mode.addAttribute("hos_id", updateMap.get("hos_id"));
		mode.addAttribute("copy_code",updateMap.get("copy_code"));
		mode.addAttribute("bill_id",updateMap.get("bill_id"));
		mode.addAttribute("bill_code",updateMap.get("bill_code"));
		mode.addAttribute("bill_no", updateMap.get("bill_no"));
		mode.addAttribute("in_id", updateMap.get("in_id"));
		mode.addAttribute("bill_type",updateMap.get("bill_type"));
		mode.addAttribute("ori_no",updateMap.get("ori_no"));
		mode.addAttribute("stock_type_code",updateMap.get("stock_type_code"));
		mode.addAttribute("bill_date",df.format(df.parse(String.valueOf(updateMap.get("bill_date")))));
		mode.addAttribute("sup_id", updateMap.get("sup_id"));
		mode.addAttribute("sup_no",updateMap.get("sup_no"));
		mode.addAttribute("sup_code", updateMap.get("sup_code"));
		mode.addAttribute("sup_name",updateMap.get("sup_name"));
		mode.addAttribute("dept_id",updateMap.get("dept_id"));
		mode.addAttribute("dept_no",updateMap.get("dept_no"));
		mode.addAttribute("dept_code",updateMap.get("dept_code"));
		mode.addAttribute("dept_name",updateMap.get("dept_name"));
		mode.addAttribute("pay_code",updateMap.get("pay_code"));
		mode.addAttribute("pay_term_code",updateMap.get("pay_term_code"));
		mode.addAttribute("pay_term_name",updateMap.get("pay_term_name"));
		mode.addAttribute("note", updateMap.get("note"));
		mode.addAttribute("stocker",updateMap.get("stocker"));
		mode.addAttribute("emp_code",updateMap.get("emp_code"));
		mode.addAttribute("emp_name",updateMap.get("emp_name"));
		mode.addAttribute("maker", updateMap.get("maker"));
		mode.addAttribute("make_date",df.format(df.parse(String.valueOf(updateMap.get("make_date")))));
		mode.addAttribute("checker", updateMap.get("checker"));
		mode.addAttribute("chk_date",updateMap.get("chk_date"));
		mode.addAttribute("is_init",updateMap.get("is_init"));
		mode.addAttribute("state",updateMap.get("state"));
		mode.addAttribute("bill_money",updateMap.get("bill_money"));
		mode.addAttribute("fav_money",updateMap.get("fav_money"));
		mode.addAttribute("in_money",updateMap.get("in_money"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08027", MyConfig.getSysPara("08027"));
		
		return "hrp/med/bill/medbillmain/medBillMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/updateMedBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("bill_date", df.parse(String.valueOf(mapVo.get("bill_date"))));
		mapVo.put("make_date", df.parse(String.valueOf(mapVo.get("make_date"))));
		
		String medBillMainJson ="";
		try{
			medBillMainJson = medBillMainService.updateMedBillMain(mapVo);
		} catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(medBillMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/addOrUpdateMedBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medBillMainJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		medBillMainJson = medBillMainService.addOrUpdateMedBillMain(detailVo);
		
		}
		return JSONObject.parseObject(medBillMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/deleteMedBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("bill_id", ids[3]);
				mapVo.put("bill_no", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
		String medBillMainJson = "";
		try{
			String inMain = medBillMainService.updateBatchMedInMain(listVo);
			String medBillDetail = medBillDetailService.deleteBatchMedBillDetail(listVo);
			medBillMainJson = medBillMainService.deleteBatchMedBillMain(listVo);
		} catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
	    
			
	  return JSONObject.parseObject(medBillMainJson);
			
	}
	/**
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/deleteMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedBillDetail(@RequestParam(value="delData") String delData, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: delData.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("bill_id", ids[3]);
				mapVo.put("bill_no", ids[4]);
				mapVo.put("bill_detail_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
		String medBillMainJson ="";
	    try{
	    	medBillMainJson = medBillDetailService.deleteBatchMedBillDetail(listVo);
	    } catch (Exception e) {
			medBillMainJson = e.getMessage();
		}
			
	  return JSONObject.parseObject(medBillMainJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 保存采购发票的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/queryMedBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medBillMain = medBillMainService.queryMedBillMain(getPage(mapVo));

		return JSONObject.parseObject(medBillMain);
		
	}
	/**
	 * 修改页面  查询发票明细信息（多表联合查询）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/queryMedBillIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MED_IN_MAIN");
		}
		//根据 发票ID 查询其明细中的 入库单ID
		List<Map<String,Object>> list =  medBillDetailService.queryIn_id(mapVo);
		String in_id=""; 
		for(Map<String,Object> item : list){
			in_id += item.get("in_id")+",";
		}
		mapVo.put("in_id", in_id.substring(0, in_id.length()-1));
		String medBillMain = medBillDetailService.queryMedBillIn(getPage(mapVo));

		return JSONObject.parseObject(medBillMain);
		
	}
	/**
	 * 入库单查询 （没有被发票引用过的采购入库单）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/queryMedCommonInBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonInBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
	        
		}
		if(mapVo.get("init") == null){
			mapVo.put("init", "0");
		}
		
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MED_IN_MAIN");
		}
		
		mapVo.put("state", 3);
		String inMainBill = medBillMainService.queryMedCommonInBill(getPage(mapVo));

		return JSONObject.parseObject(inMainBill);
		
	}
	/**
	 * 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/queryMedInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if("undefined".equals(mapVo.get("in_detail_id"))){
			
			mapVo.put("in_detail_id","");
	        
		}
		if("undefined".equals(mapVo.get("bill_id"))){
			
			mapVo.put("bill_id","");
	        
		}
		/*
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER_D");
		}else{
			mapVo.put("table", "MED_IN_DETAIL");
		}*/
		
		

		/*if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER_D");
		}else{
			if("1".equals(mapVo.get("flag"))){
				mapVo.put("table", "MED_AFFI_IN_DETAIL");
			}else{
				mapVo.put("table", "MED_IN_DETAIL");
			}
		}*/
		
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MED_IN_MAIN");
		}
		
		String inDetailBill = medBillMainService.queryMedInDetail(getPage(mapVo));

		return JSONObject.parseObject(inDetailBill);
	
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 保存采购发票的主要信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/medBillMainImportPage", method = RequestMethod.GET)
	public String medBillMainImportPage(Model mode) throws Exception {

		return "hrp/med/bill/medbillmain/medBillMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 保存采购发票的主要信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/bill/medbillmain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","保存采购发票的主要信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 保存采购发票的主要信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/bill/medbillmain/readMedBillMainFiles",method = RequestMethod.POST)  
    public String readMedBillMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedBillMain> list_err = new ArrayList<MedBillMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedBillMain medBillMain = new MedBillMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medBillMain.setBill_id(Long.valueOf(temp[3]));
		    		mapVo.put("bill_id", temp[3]);
					
					} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medBillMain.setBill_no(temp[4]);
		    		mapVo.put("bill_no", temp[4]);
					
					} else {
						
						err_sb.append("发票号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medBillMain.setOri_no(temp[5]);
		    		mapVo.put("ori_no", temp[5]);
					
					} else {
						
						err_sb.append("原始发票号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medBillMain.setStock_type_code(temp[6]);
		    		mapVo.put("stock_type_code", temp[6]);
					
					} else {
						
						err_sb.append("采购类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medBillMain.setBill_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("bill_date", temp[7]);
					
					} else {
						
						err_sb.append("开票日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medBillMain.setSup_id(Long.valueOf(temp[8]));
		    		mapVo.put("sup_id", temp[8]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medBillMain.setSup_no(Long.valueOf(temp[9]));
		    		mapVo.put("sup_no", temp[9]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medBillMain.setPay_code(temp[10]);
		    		mapVo.put("pay_code", temp[10]);
					
					} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medBillMain.setNote(temp[11]);
		    		mapVo.put("note", temp[11]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medBillMain.setMaker(Long.valueOf(temp[12]));
		    		mapVo.put("maker", temp[12]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medBillMain.setChecker(Long.valueOf(temp[13]));
		    		mapVo.put("checker", temp[13]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medBillMain.setChk_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("chk_date", temp[14]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					medBillMain.setIs_init(Integer.valueOf(temp[15]));
		    		mapVo.put("is_init", temp[15]);
					
					} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					medBillMain.setState(Integer.valueOf(temp[16]));
		    		mapVo.put("state", temp[16]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					medBillMain.setBill_money(Double.valueOf(temp[17]));
		    		mapVo.put("bill_money", temp[17]);
					
					} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					medBillMain.setPayable_money(Double.valueOf(temp[18]));
		    		mapVo.put("payable_money", temp[18]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					
				MedBillMain data_exc_extis = medBillMainService.queryMedBillMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medBillMain.setError_type(err_sb.toString());
					
					list_err.add(medBillMain);
					
				} else {
			  
					String dataJson = medBillMainService.addMedBillMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedBillMain data_exc = new MedBillMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 保存采购发票的主要信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/addBatchMedBillMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedBillMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedBillMain> list_err = new ArrayList<MedBillMain>();
		
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
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			MedBillMain medBillMain = new MedBillMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("bill_id"))) {
						
					medBillMain.setBill_id(Long.valueOf((String)jsonObj.get("bill_id")));
		    		mapVo.put("bill_id", jsonObj.get("bill_id"));
		    		} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_no"))) {
						
					medBillMain.setBill_no((String)jsonObj.get("bill_no"));
		    		mapVo.put("bill_no", jsonObj.get("bill_no"));
		    		} else {
						
						err_sb.append("发票号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ori_no"))) {
						
					medBillMain.setOri_no((String)jsonObj.get("ori_no"));
		    		mapVo.put("ori_no", jsonObj.get("ori_no"));
		    		} else {
						
						err_sb.append("原始发票号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_type_code"))) {
						
					medBillMain.setStock_type_code((String)jsonObj.get("stock_type_code"));
		    		mapVo.put("stock_type_code", jsonObj.get("stock_type_code"));
		    		} else {
						
						err_sb.append("采购类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_date"))) {
						
					medBillMain.setBill_date(DateUtil.stringToDate((String)jsonObj.get("bill_date")));
		    		mapVo.put("bill_date", jsonObj.get("bill_date"));
		    		} else {
						
						err_sb.append("开票日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					medBillMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					medBillMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_code"))) {
						
					medBillMain.setPay_code((String)jsonObj.get("pay_code"));
		    		mapVo.put("pay_code", jsonObj.get("pay_code"));
		    		} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					medBillMain.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medBillMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medBillMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("chk_date"))) {
						
					medBillMain.setChk_date(DateUtil.stringToDate((String)jsonObj.get("chk_date")));
		    		mapVo.put("chk_date", jsonObj.get("chk_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					medBillMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medBillMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("bill_money"))) {
						
					medBillMain.setBill_money(Double.valueOf((String)jsonObj.get("bill_money")));
		    		mapVo.put("bill_money", jsonObj.get("bill_money"));
		    		} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					
				MedBillMain data_exc_extis = medBillMainService.queryMedBillMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medBillMain.setError_type(err_sb.toString());
					
					list_err.add(medBillMain);
					
				} else {
			  
					String dataJson = medBillMainService.addMedBillMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedBillMain data_exc = new MedBillMain();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
	/**
	 * 审核、消审
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/updateBillState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBillState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String info = "";
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("bill_id", ids[3]);
				mapVo.put("bill_no", ids[4]);
				mapVo.put("bill_date", ids[5]);
				mapVo.put("state", ids[6]);
				if(Integer.valueOf(String.valueOf(ids[6])) ==2){
					mapVo.put("checker", SessionManager.getUserId());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					mapVo.put("chk_date", df.parse(df.format(new Date())));
				}
				if(Integer.valueOf(String.valueOf(ids[6])) ==1){
					//查询   发票是否已付款 
					int count = medBillMainService.queryPayOrNot(mapVo);
					if(count > 0){
						info += mapVo.get("bill_no"); 
					}
					mapVo.put("checker", "");
					mapVo.put("chk_date", "");
				}
				
	      listVo.add(mapVo);
	      
	    }
		if( info != ""){
			return JSONObject.parseObject("{\"error\":\"消审失败！【发票："+info.substring(0,info.length()-1)+"已生成付款单,不能消审】.\",\"state\":\"true\"}");
		}else{
			String medBillMainJson = "";
			try{
				medBillMainJson = medBillMainService.updateBillState(listVo);
			} catch (Exception e) {
				medBillMainJson = e.getMessage();
			}
				
		    return JSONObject.parseObject(medBillMainJson);
		}
	}
	/**
	 *生成发票流水号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/bill/medbillmain/setBillCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setBillCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		

		int num = Integer.valueOf(String.valueOf(mapVo.get("month"))) + 1 ;
		if(num < 10){
			
			String month = "0"+num;
			mapVo.put("month", month);
		}
		
		if(String.valueOf(mapVo.get("day")).length() == 1){
			String day = "0"+mapVo.get("day");
			mapVo.put("day", day);
		}
		
		String medProtocolMainJson = medBillMainService.setBillCode(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/bill/medbillmain/medBillMainPrintSetPage", method = RequestMethod.GET)
	public String medBillMainPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/med/bill/medbillmain/queryMedBillMainByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillMainByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("p_num") .equals("1") ) {
			mapVo.put("p_num", 1);
		}else{
			mapVo.put("p_num", 0);
		}
		System.out.println("=============="+mapVo);
		String reJson=null;
		try {
			reJson=medBillMainService.queryMedBillMainByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
}

