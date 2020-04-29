/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.matprepay;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.mat.entity.MatPrePayMain;
import com.chd.hrp.mat.service.matprepay.MatPrePayDetailService;
import com.chd.hrp.mat.service.matprepay.MatPrePayMainService;
/**
 * 
 * @Description:
 * 保存预付款单的主要信息
 * @Table:
 * MAT_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatPrePayMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPrePayMainController.class);
	
	//引入Service服务
	@Resource(name = "matPrePayMainService")
	private final MatPrePayMainService matPrePayMainService = null;
	
	@Resource(name = "matPrePayDetailService")
	private final MatPrePayDetailService matPrePayDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/matPrePayMainMainPage", method = RequestMethod.GET)
	public String matPrePayMainMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		return "hrp/mat/matprepay/matprepaymain/matPrePayMainMain";

	}
	
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/inMainPage", method = RequestMethod.GET)
	public String inMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/matprepay/matprepaymain/inMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/matPrePayMainAddPage", method = RequestMethod.GET)
	public String matPrePayMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/matprepay/matprepaymain/matPrePayMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/addMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String matPrePayMainJson  = "" ;
		try{
			matPrePayMainJson = matPrePayMainService.addMatPrePayMain(mapVo);
		}catch (Exception e){
			matPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matPrePayMainJson);
		
	}
	/**
	 * 添加、修改发票明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/addMatPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPrePayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加预付款单明细 用List
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		
		//新增时查询预付款单明细对应的预付款单主表的Id
		Long pre_pay_id = matPrePayMainService.queryMatPrePayMainMaxId();
	
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
		String matPrePayMainJson ="";
		try{
			if(listVo.size()>0){
				 matPrePayMainJson = matPrePayDetailService.addBatchMatPrePayDetail(listVo);
			}
		}catch (Exception e){
			matPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matPrePayMainJson);
		
	}
	/**
	 * 更新预付款单明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/updateMatPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPrePayDetail(@RequestParam(value="ParamVo") String updatePara, Model mode) throws Exception {
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
			matPrePayDetailService.deleteMatPrePayDetail(mapVo);
			
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
		String matPrePayMainJson ="";
		try{
			matPrePayMainJson = matPrePayDetailService.addBatchMatPrePayDetail(listVo);
		}catch (Exception e){
			matPrePayMainJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(matPrePayMainJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/matPrePayMainUpdatePage", method = RequestMethod.GET)
	public String matPrePayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		updateMap = matPrePayMainService.queryMatPrePayMainReturnUpdate(mapVo);
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
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		return "hrp/mat/matprepay/matprepaymain/matPrePayMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/updateMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
		
		String matPrePayMainJson ="";
		try{
			matPrePayMainJson = matPrePayMainService.updateMatPrePayMain(mapVo);
		}catch (Exception e){
			matPrePayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matPrePayMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/addOrUpdateMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matPrePayMainJson ="";
		
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
	  
		matPrePayMainJson = matPrePayMainService.addOrUpdateMatPrePayMain(detailVo);
		
		}
		return JSONObject.parseObject(matPrePayMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/deleteMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPrePayMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matPrePayMainJson ="";
		try{
			 String matPrePayDetail = matPrePayDetailService.deleteBatchMatPrePayDetail(listVo);
			 matPrePayMainJson = matPrePayMainService.deleteBatchMatPrePayMain(listVo);
		}catch(Exception e){
			matPrePayMainJson = e.getMessage();
		}
			
	  return JSONObject.parseObject(matPrePayMainJson);
			
	}
	/**
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/deleteMatPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPrePayDetail(@RequestParam(value="delData") String delData, Model mode) throws Exception {
		
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
	    
		String matPrePayMainJson ="";
		try{
			matPrePayMainJson = matPrePayDetailService.deleteBatchMatPrePayDetail(listVo);
		}catch (Exception e){
			matPrePayMainJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(matPrePayMainJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 保存预付款单的主要信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/queryMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matPrePayMain = matPrePayMainService.queryMatPrePayMain(getPage(mapVo));

		return JSONObject.parseObject(matPrePayMain);
		
	}
	/**
	 * 修改页面  查询预付款单明细信息（多表联合查询）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/queryMatPrePayIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPrePayIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if("1".equals(mapVo.get("init"))){
			mapVo.put("table", "MAT_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MAT_IN_MAIN");
		}
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		//根据 预付款单ID 查询其明细中的 入库单ID
		List<Map<String,Object>> list =  matPrePayDetailService.queryMatPrePayIn_id(mapVo);
		String in_id=""; 
		for(Map<String,Object> item : list){
			in_id += item.get("in_id")+",";
		}
		mapVo.put("in_id", in_id.substring(0, in_id.length()-1));
		String matPrePayMain = matPrePayDetailService.queryMatPrePayIn(getPage(mapVo));

		return JSONObject.parseObject(matPrePayMain);
		
	}
	/**
	 * 入库单查询 （没有被预付款单引用过的采购入库单）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/queryMatCommonIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCommonIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("table", "MAT_NOPAY_DELIVER");
		}else{
			mapVo.put("table", "MAT_IN_MAIN");
		}
		
		mapVo.put("state", 3);
		String inMainPrePay = matPrePayMainService.queryMatCommonIn(getPage(mapVo));

		return JSONObject.parseObject(inMainPrePay);
		
	}
	/**
	 * 入库单明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/queryMatInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("table", "MAT_NOPAY_DELIVER_D");
		}else{
			mapVo.put("table", "MAT_IN_DETAIL");
		}
		String inDetailPrePay = matPrePayMainService.queryMatInDetail(getPage(mapVo));

		return JSONObject.parseObject(inDetailPrePay);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 保存预付款单的主要信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/matPrePayMainImportPage", method = RequestMethod.GET)
	public String matPrePayMainImportPage(Model mode) throws Exception {

		return "hrp/mat/matprepay/matprepaymain/matPrePayMainImport";

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
	 @RequestMapping(value="hrp/mat/matprepay/matprepaymain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","保存预付款单的主要信息.xls");
	    
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
	@RequestMapping(value="/hrp/mat/matprepay/matprepaymain/readMatPrePayMainFiles",method = RequestMethod.POST)  
    public String readMatPrePayMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatPrePayMain> list_err = new ArrayList<MatPrePayMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatPrePayMain matPrePayMain = new MatPrePayMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matPrePayMain.setPre_pay_id(Long.valueOf(temp[3]));
		    		mapVo.put("pre_pay_id", temp[3]);
					
					} else {
						
						err_sb.append("预付款单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matPrePayMain.setPre_pay_no(temp[4]);
		    		mapVo.put("pre_pay_no", temp[4]);
					
					} else {
						
						err_sb.append("预付款单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matPrePayMain.setPay_date(DateUtil.stringToDate(temp[7]));
		    		mapVo.put("pay_date", temp[7]);
					
					} else {
						
						err_sb.append("开票日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matPrePayMain.setSup_id(Long.valueOf(temp[8]));
		    		mapVo.put("sup_id", temp[8]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matPrePayMain.setSup_no(Long.valueOf(temp[9]));
		    		mapVo.put("sup_no", temp[9]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matPrePayMain.setPay_code(temp[10]);
		    		mapVo.put("pay_code", temp[10]);
					
					} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					matPrePayMain.setNote(temp[11]);
		    		mapVo.put("note", temp[11]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					matPrePayMain.setMaker(Long.valueOf(temp[12]));
		    		mapVo.put("maker", temp[12]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					matPrePayMain.setChecker(Long.valueOf(temp[13]));
		    		mapVo.put("checker", temp[13]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					matPrePayMain.setChk_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("chk_date", temp[14]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					matPrePayMain.setIs_init(Integer.valueOf(temp[15]));
		    		mapVo.put("is_init", temp[15]);
					
					} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					matPrePayMain.setState(Integer.valueOf(temp[16]));
		    		mapVo.put("state", temp[16]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[18])) {
						
					matPrePayMain.setPayable_money(Double.valueOf(temp[18]));
		    		mapVo.put("payable_money", temp[18]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					
				MatPrePayMain data_exc_extis = matPrePayMainService.queryMatPrePayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPrePayMain.setError_type(err_sb.toString());
					
					list_err.add(matPrePayMain);
					
				} else {
			  
					String dataJson = matPrePayMainService.addMatPrePayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPrePayMain data_exc = new MatPrePayMain();
			
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
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/addBatchMatPrePayMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatPrePayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatPrePayMain> list_err = new ArrayList<MatPrePayMain>();
		
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
			
			MatPrePayMain matPrePayMain = new MatPrePayMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("pre_pay_id"))) {
						
					matPrePayMain.setPre_pay_id(Long.valueOf((String)jsonObj.get("pre_pay_id")));
		    		mapVo.put("pre_pay_id", jsonObj.get("pre_pay_id"));
		    		} else {
						
						err_sb.append("预付款单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pre_pay_no"))) {
						
					matPrePayMain.setPre_pay_no((String)jsonObj.get("pre_pay_no"));
		    		mapVo.put("pre_pay_no", jsonObj.get("pre_pay_no"));
		    		} else {
						
						err_sb.append("预付款单号为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_date"))) {
						
					matPrePayMain.setPay_date(DateUtil.stringToDate((String)jsonObj.get("pay_date")));
		    		mapVo.put("pay_date", jsonObj.get("pay_date"));
		    		} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					matPrePayMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					matPrePayMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_code"))) {
						
					matPrePayMain.setPay_code((String)jsonObj.get("pay_code"));
		    		mapVo.put("pay_code", jsonObj.get("pay_code"));
		    		} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					matPrePayMain.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matPrePayMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matPrePayMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("chk_date"))) {
						
					matPrePayMain.setChk_date(DateUtil.stringToDate((String)jsonObj.get("chk_date")));
		    		mapVo.put("chk_date", jsonObj.get("chk_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					matPrePayMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matPrePayMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("payable_money"))) {
						
					matPrePayMain.setPayable_money(Double.valueOf((String)jsonObj.get("payable_money")));
		    		mapVo.put("payable_money", jsonObj.get("payable_money"));
		    		} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					
				MatPrePayMain data_exc_extis = matPrePayMainService.queryMatPrePayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPrePayMain.setError_type(err_sb.toString());
					
					list_err.add(matPrePayMain);
					
				} else {
			  
					String dataJson = matPrePayMainService.addMatPrePayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPrePayMain data_exc = new MatPrePayMain();
			
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
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/updateMatPrePayMainState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPrePayMainState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
	    
		String matProtocolMainJson = matPrePayMainService.updateMatPrePayMainState(listVo);
			
	    return JSONObject.parseObject(matProtocolMainJson);
	}
	/**
	 *生成付款单流水号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/setMatPrePayMainNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setMatPrePayMainNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
		String matProtocolMainJson = matPrePayMainService.setMatPrePayMainNo(mapVo);
		
		return JSONObject.parseObject(matProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprepay/matprepaymain/prePayPrintSetPage", method = RequestMethod.GET)
	public String prePayPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
}

