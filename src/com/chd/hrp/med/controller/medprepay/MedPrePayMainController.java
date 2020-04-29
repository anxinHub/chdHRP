/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.medprepay;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedPrePayMain;
import com.chd.hrp.med.service.medprepay.MedPrePayDetailService;
import com.chd.hrp.med.service.medprepay.MedPrePayMainService;
/**
 * 
 * @Description:
 * 保存预付款单的主要信息
 * @Table:
 * MED_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedPrePayMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPrePayMainController.class);
	
	//引入Service服务
	@Resource(name = "medPrePayMainService")
	private final MedPrePayMainService medPrePayMainService = null;
	
	@Resource(name = "medPrePayDetailService")
	private final MedPrePayDetailService medPrePayDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/medPrePayMainMainPage", method = RequestMethod.GET)
	public String medPrePayMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));

		
		return "hrp/med/medprepay/medprepaymain/medPrePayMainMain";

	}
	
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/inMainPage", method = RequestMethod.GET)
	public String inMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/medprepay/medprepaymain/inMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/medPrePayMainAddPage", method = RequestMethod.GET)
	public String medPrePayMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/medprepay/medprepaymain/medPrePayMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/addMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("cheq_no", "");
		mapVo.put("pay_type_code", "");
		mapVo.put("acct_code", "");
		mapVo.put("maker", SessionManager.getUserId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("pay_date", df.parse(String.valueOf(mapVo.get("pay_date"))));
		mapVo.put("make_date", df.parse(df.format(new Date())));
		mapVo.put("checker", "");
		mapVo.put("chk_date", null);
		mapVo.put("is_init",mapVo.get("init"));
		mapVo.put("state", "1");
		String medPrePayMainJson  = "" ;
		try{
			medPrePayMainJson = medPrePayMainService.addMedPrePayMain(mapVo);
		}catch (Exception e){
			medPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medPrePayMainJson);
		
	}
	/**
	 * 添加、修改发票明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/addMedPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPrePayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加预付款单明细 用List
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		
		//新增时查询预付款单明细对应的预付款单主表的Id
		Long pre_pay_id = medPrePayMainService.queryMedPrePayMainMaxId();
	
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			if("undefined".equals(ids[9])){
				//添加预付款单明细  数据
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("group_id", ids[0])   ;
				map.put("hos_id", ids[1])   ;
				map.put("copy_code", ids[2])  ;
				if("undefined".equals(ids[3])){
					map.put("pre_pay_id", pre_pay_id);
				}else{
					map.put("pre_pay_id", ids[3]);
				}
				
				map.put("in_id", ids[4]);
				map.put("in_detail_id",ids[5]);
				map.put("payable_money", ids[6]);
				map.put("pre_pay_money", ids[7]);
				map.put("pre_pay_no", ids[8]);
				map.put("pre_detail_id",null);
				listVo.add(map);
			}
		}
		String medPrePayMainJson ="";
		try{
			if(listVo.size()>0){
				 medPrePayMainJson = medPrePayDetailService.addBatchMedPrePayDetail(listVo);
			}
		}catch (Exception e){
			medPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medPrePayMainJson);
		
	}
	/**
	 * 更新预付款单明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/updateMedPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPrePayDetail(@RequestParam(value="ParamVo") String updatePara, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
	
		for ( String id: updatePara.split(",")) {
			
			String[] ids=id.split("@");
			Map<String,Object> mapVo = new HashMap<String,Object>();
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])  ;
			mapVo.put("pre_pay_id", ids[3]);
			mapVo.put("pre_pay_no", ids[8]);
			
			//删除 修改预付款单对应的所有预付款单明细
			medPrePayDetailService.deleteMedPrePayDetail(mapVo);
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("group_id", ids[0])   ;
			map.put("hos_id", ids[1])   ;
			map.put("copy_code", ids[2])  ;
			map.put("pre_pay_id", ids[3]);
			map.put("in_id", ids[4]);
			map.put("in_detail_id",ids[5]);
			map.put("payable_money", ids[6]);
			map.put("pre_pay_money", ids[7]);
			map.put("pre_pay_no", ids[8]);
			map.put("bill_datail_id",null);
			listVo.add(map);
		}
		String medPrePayMainJson ="";
		try{
			medPrePayMainJson = medPrePayDetailService.addBatchMedPrePayDetail(listVo);
		}catch (Exception e){
			medPrePayMainJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(medPrePayMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/medPrePayMainUpdatePage", method = RequestMethod.GET)
	public String medPrePayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		updateMap = medPrePayMainService.queryMedPrePayMainReturnUpdate(mapVo);
		mode.addAttribute("group_id", updateMap.get("group_id"));
		mode.addAttribute("hos_id", updateMap.get("hos_id"));
		mode.addAttribute("copy_code",updateMap.get("copy_code"));
		mode.addAttribute("pre_pay_id",updateMap.get("pre_pay_id"));
		mode.addAttribute("pre_pay_no", updateMap.get("pre_pay_no"));
		mode.addAttribute("pay_bill_type",updateMap.get("pay_bill_type"));
		mode.addAttribute("pay_date",df.format(df.parse(String.valueOf(updateMap.get("pay_date")))));
		mode.addAttribute("sup_id", updateMap.get("sup_id"));
		mode.addAttribute("sup_no",updateMap.get("sup_no"));
		mode.addAttribute("sup_code", updateMap.get("sup_code"));
		mode.addAttribute("sup_name",updateMap.get("sup_name"));
		mode.addAttribute("pay_code",updateMap.get("pay_code"));
		mode.addAttribute("pay_term_code",updateMap.get("pay_term_code"));
		mode.addAttribute("pay_term_name",updateMap.get("pay_term_name"));
		mode.addAttribute("cheq_no", updateMap.get("cheq_no"));
		mode.addAttribute("pay_type_code", updateMap.get("pay_type_code"));
		mode.addAttribute("acct_code", updateMap.get("acct_code"));
		mode.addAttribute("payable_money",updateMap.get("payable_money"));
		mode.addAttribute("pre_pay_money",updateMap.get("pre_pay_money"));
		mode.addAttribute("is_init",updateMap.get("is_init"));
		mode.addAttribute("maker", updateMap.get("maker"));
		mode.addAttribute("make_date",updateMap.get("make_date"));
		mode.addAttribute("checker", updateMap.get("checker"));
		mode.addAttribute("chk_date",updateMap.get("chk_date"));
		mode.addAttribute("state",updateMap.get("state"));
		mode.addAttribute("note", updateMap.get("note"));
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));
		
		return "hrp/med/medprepay/medprepaymain/medPrePayMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/updateMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
		mapVo.put("pay_date", df.parse(String.valueOf(mapVo.get("pay_date"))));
		mapVo.put("make_date", df.parse(String.valueOf(mapVo.get("make_date"))));
		
		String medPrePayMainJson ="";
		try{
			medPrePayMainJson = medPrePayMainService.updateMedPrePayMain(mapVo);
		}catch (Exception e){
			medPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medPrePayMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/addOrUpdateMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medPrePayMainJson ="";
		
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
	  
		medPrePayMainJson = medPrePayMainService.addOrUpdateMedPrePayMain(detailVo);
		
		}
		return JSONObject.parseObject(medPrePayMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/deleteMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPrePayMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pre_pay_id", ids[3]);
				mapVo.put("pre_pay_no", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
		String medPrePayMainJson ="";
		try{
			 String medPrePayDetail = medPrePayDetailService.deleteBatchMedPrePayDetail(listVo);
			 medPrePayMainJson = medPrePayMainService.deleteBatchMedPrePayMain(listVo);
		}catch(Exception e){
			medPrePayMainJson = e.getMessage();
		}
			
	  return JSONObject.parseObject(medPrePayMainJson);
			
	}
	/**
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/deleteMedPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPrePayDetail(@RequestParam(value="delData") String delData, Model mode) throws Exception {
		
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
	    
		String medPrePayMainJson ="";
		try{
			medPrePayMainJson = medPrePayDetailService.deleteBatchMedPrePayDetail(listVo);
		}catch (Exception e){
			medPrePayMainJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(medPrePayMainJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/queryMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medPrePayMain = medPrePayMainService.queryMedPrePayMain(getPage(mapVo));

		return JSONObject.parseObject(medPrePayMain);
		
	}
	/**
	 * 修改页面  查询预付款单明细信息（多表联合查询）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/queryMedPrePayIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPrePayIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MED_IN_MAIN");
		}
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		//根据 预付款单ID 查询其明细中的 入库单ID
		List<Map<String,Object>> list =  medPrePayDetailService.queryMedPrePayIn_id(mapVo);
		String in_id=""; 
		for(Map<String,Object> item : list){
			in_id += item.get("in_id")+",";
		}
		mapVo.put("in_id", in_id.substring(0, in_id.length()-1));
		String medPrePayMain = medPrePayDetailService.queryMedPrePayIn(getPage(mapVo));

		return JSONObject.parseObject(medPrePayMain);
		
	}
	/**
	 * 入库单查询 （没有被预付款单引用过的采购入库单）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/queryMedCommonIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCommonIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
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
		String inMainPrePay = medPrePayMainService.queryMedCommonIn(getPage(mapVo));

		return JSONObject.parseObject(inMainPrePay);
		
	}
	/**
	 * 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/queryMedInDetail", method = RequestMethod.POST)
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
		
		if("undefined".equals(mapVo.get("pre_pay_id"))){
			
			mapVo.put("pre_pay_id","");
	        
		}
		
		if("undefined".equals(mapVo.get("pre_detail_id"))){
			
			mapVo.put("pre_detail_id","");
	        
		}
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MED_NOPAY_DELIVER_D");
		}else{
			mapVo.put("table", "MED_IN_DETAIL");
		}
		String inDetailPrePay = medPrePayMainService.queryMedInDetail(getPage(mapVo));

		return JSONObject.parseObject(inDetailPrePay);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 保存预付款单的主要信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/medPrePayMainImportPage", method = RequestMethod.GET)
	public String medPrePayMainImportPage(Model mode) throws Exception {

		return "hrp/med/medprepay/medprepaymain/medPrePayMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 保存预付款单的主要信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medprepay/medprepaymain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","保存预付款单的主要信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 保存预付款单的主要信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/medprepay/medprepaymain/readMedPrePayMainFiles",method = RequestMethod.POST)  
    public String readMedPrePayMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedPrePayMain> list_err = new ArrayList<MedPrePayMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedPrePayMain medPrePayMain = new MedPrePayMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medPrePayMain.setPre_pay_id(Long.valueOf(temp[3]));
		    		mapVo.put("pre_pay_id", temp[3]);
					
					} else {
						
						err_sb.append("预付款单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medPrePayMain.setPre_pay_no(temp[4]);
		    		mapVo.put("pre_pay_no", temp[4]);
					
					} else {
						
						err_sb.append("预付款单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medPrePayMain.setPay_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("pay_date", temp[7]);
					
					} else {
						
						err_sb.append("开票日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medPrePayMain.setSup_id(Long.valueOf(temp[8]));
		    		mapVo.put("sup_id", temp[8]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medPrePayMain.setSup_no(Long.valueOf(temp[9]));
		    		mapVo.put("sup_no", temp[9]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medPrePayMain.setPay_code(temp[10]);
		    		mapVo.put("pay_code", temp[10]);
					
					} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					medPrePayMain.setNote(temp[11]);
		    		mapVo.put("note", temp[11]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					medPrePayMain.setMaker(Long.valueOf(temp[12]));
		    		mapVo.put("maker", temp[12]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					medPrePayMain.setChecker(Long.valueOf(temp[13]));
		    		mapVo.put("checker", temp[13]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medPrePayMain.setChk_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("chk_date", temp[14]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					medPrePayMain.setIs_init(Integer.valueOf(temp[15]));
		    		mapVo.put("is_init", temp[15]);
					
					} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					medPrePayMain.setState(Integer.valueOf(temp[16]));
		    		mapVo.put("state", temp[16]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					medPrePayMain.setPayable_money(Double.valueOf(temp[18]));
		    		mapVo.put("payable_money", temp[18]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					
				MedPrePayMain data_exc_extis = medPrePayMainService.queryMedPrePayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPrePayMain.setError_type(err_sb.toString());
					
					list_err.add(medPrePayMain);
					
				} else {
			  
					String dataJson = medPrePayMainService.addMedPrePayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPrePayMain data_exc = new MedPrePayMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 保存预付款单的主要信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/addBatchMedPrePayMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedPrePayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedPrePayMain> list_err = new ArrayList<MedPrePayMain>();
		
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
			
			MedPrePayMain medPrePayMain = new MedPrePayMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("pre_pay_id"))) {
						
					medPrePayMain.setPre_pay_id(Long.valueOf((String)jsonObj.get("pre_pay_id")));
		    		mapVo.put("pre_pay_id", jsonObj.get("pre_pay_id"));
		    		} else {
						
						err_sb.append("预付款单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pre_pay_no"))) {
						
					medPrePayMain.setPre_pay_no((String)jsonObj.get("pre_pay_no"));
		    		mapVo.put("pre_pay_no", jsonObj.get("pre_pay_no"));
		    		} else {
						
						err_sb.append("预付款单号为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_date"))) {
						
					medPrePayMain.setPay_date(DateUtil.stringToDate((String)jsonObj.get("pay_date")));
		    		mapVo.put("pay_date", jsonObj.get("pay_date"));
		    		} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					medPrePayMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					medPrePayMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_code"))) {
						
					medPrePayMain.setPay_code((String)jsonObj.get("pay_code"));
		    		mapVo.put("pay_code", jsonObj.get("pay_code"));
		    		} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					medPrePayMain.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medPrePayMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medPrePayMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("chk_date"))) {
						
					medPrePayMain.setChk_date(DateUtil.stringToDate((String)jsonObj.get("chk_date")));
		    		mapVo.put("chk_date", jsonObj.get("chk_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					medPrePayMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medPrePayMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("payable_money"))) {
						
					medPrePayMain.setPayable_money(Double.valueOf((String)jsonObj.get("payable_money")));
		    		mapVo.put("payable_money", jsonObj.get("payable_money"));
		    		} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					
				MedPrePayMain data_exc_extis = medPrePayMainService.queryMedPrePayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPrePayMain.setError_type(err_sb.toString());
					
					list_err.add(medPrePayMain);
					
				} else {
			  
					String dataJson = medPrePayMainService.addMedPrePayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPrePayMain data_exc = new MedPrePayMain();
			
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
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/updateMedPrePayMainState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPrePayMainState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pre_pay_id", ids[3]);
				mapVo.put("pre_pay_no", ids[4]);
				mapVo.put("pay_date", ids[5]);
				mapVo.put("state", ids[6]);
				if(Integer.valueOf(String.valueOf(ids[6])) ==2){
					mapVo.put("checker", SessionManager.getUserId());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					mapVo.put("chk_date", df.parse(df.format(new Date())));
				}
				if(Integer.valueOf(String.valueOf(ids[6])) ==1){
					mapVo.put("checker", "");
					mapVo.put("chk_date", "");
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medProtocolMainJson = medPrePayMainService.updateMedPrePayMainState(listVo);
			
	    return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 *生成付款单流水号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/setMedPrePayMainNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setMedPrePayMainNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
		String medProtocolMainJson = medPrePayMainService.setMedPrePayMainNo(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medprepay/medprepaymain/prePayPrintSetPage", method = RequestMethod.GET)
	public String prePayPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
}

