/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.medpay;
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
import com.chd.hrp.med.entity.MedPayMain;
import com.chd.hrp.med.service.bill.MedBillDetailService;
import com.chd.hrp.med.service.bill.MedBillMainService;
import com.chd.hrp.med.service.medpay.MedPayDetailService;
import com.chd.hrp.med.service.medpay.MedPayMainService;
/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MED_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedPayMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPayMainController.class);
	
	//引入Service服务
	@Resource(name = "medPayMainService")
	private final MedPayMainService medPayMainService = null;
	
	
	@Resource(name = "medPayDetailService")
	private final MedPayDetailService medPayDetailService = null;
	
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
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/medPayMainMainPage", method = RequestMethod.GET)
	public String medPayMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08028", MyConfig.getSysPara("08028"));

		return "hrp/med/medpay/medpaymain/medPayMainMain";

	}
	/**
	 * 添加发票页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/selectBillPage", method = RequestMethod.GET)
	public String selectBillPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name",mapVo.get("sup_name"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/medpay/medpaymain/selectBill";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/medPayMainAddPage", method = RequestMethod.GET)
	public String medPayMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/medpay/medpaymain/medPayMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/addMedPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("acct_code", "");
		mapVo.put("maker", SessionManager.getUserId());
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("make_date", df.format(new Date()));
		
		mapVo.put("checker", "");
		mapVo.put("chk_date", "");
		mapVo.put("state", 1);
		
		String medPayMainJson ="";
		try{
			medPayMainJson = medPayMainService.addMedPayMain(mapVo);
		}catch(Exception e){
			medPayMainJson = e.getMessage() ;
		}

		return JSONObject.parseObject(medPayMainJson);
		
	}
	
	
	/**
	 * 添加付款单明细信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/addMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加 list
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//查询 付款单主表当前最大的 ID
		Long pay_id  = medPayMainService.queryPayID();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();		
			String[] ids=id.split("@");
    	
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("pay_id",pay_id);
			mapVo.put("pay_bill_no",ids[4]);
			mapVo.put("pay_detail_id","");
			mapVo.put("bill_id", ids[6]);
			mapVo.put("bill_detail_id", ids[7]);
			mapVo.put("bill_money", ids[8]);
			mapVo.put("payed_money", ids[9]);
			mapVo.put("pay_money", ids[10]);
    		mapVo.put("dis_money", ids[11]);
    
			
			listVo.add(mapVo);
		}
		String medBillDetailJson = null;
		try{
			if(listVo.size()>0){
				 medBillDetailJson = medPayDetailService.addBatchMedPayDetail(listVo);
			}
		}catch(Exception e){
			medBillDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medBillDetailJson);
	}
	
	
	/**
	 * 修改付款单明细信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/updateMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加 付款明细用 list
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//查询 付款单主表当前最大的 ID
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("pay_id",ids[3]);
			mapVo.put("pay_bill_no",ids[4]);
			//删除 修改付款单的明细信息
			medPayDetailService.deleteMedPayDetail(mapVo);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("group_id", ids[0])   ;
			map.put("hos_id", ids[1])   ;
			map.put("copy_code", ids[2])   ;
			map.put("pay_id",ids[3]);
			map.put("pay_bill_no",ids[4]);
			map.put("pay_detail_id","");
			map.put("bill_id", ids[6]);
			map.put("bill_detail_id", ids[7]);
			map.put("bill_money", ids[8]);
			map.put("payed_money", ids[9]);
			map.put("pay_money", ids[10]);
			map.put("dis_money", ids[11]);
			
			listVo.add(map);
		}
		String medBillDetailJson = null;
		try{
			if(listVo.size()>0){
				 medBillDetailJson = medPayDetailService.addBatchMedPayDetail(listVo);
			}
		}catch(Exception e){
			medBillDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medBillDetailJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/medPayMainUpdatePage", method = RequestMethod.GET)
	public String medPayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据 付款单ID 查询 其对应的发票ID
		List<Map<String,Object>> list = medPayDetailService.queryMedBillId(mapVo);
		String bill_id ="";
		for(Map<String,Object> item : list ){
			bill_id  += item.get("bill_id")+",";
		}
		
		Map<String,Object> detailMap = medPayMainService.queryMedPayMainUnit(mapVo);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("bill_id", bill_id.substring(0, bill_id.length()-1));
		mode.addAttribute("group_id", detailMap.get("group_id"));
		mode.addAttribute("hos_id", detailMap.get("hos_id"));
		mode.addAttribute("copy_code", detailMap.get("copy_code"));
		mode.addAttribute("pay_id", detailMap.get("pay_id"));
		mode.addAttribute("pay_bill_no", detailMap.get("pay_bill_no"));
		mode.addAttribute("pay_date", df.format(df.parse(String.valueOf(detailMap.get("pay_date")))));
		mode.addAttribute("pay_bill_type", detailMap.get("pay_bill_type"));
		mode.addAttribute("pay_type_code", detailMap.get("pay_type_code"));
		mode.addAttribute("pay_name", detailMap.get("pay_name"));
		mode.addAttribute("cheq_no", detailMap.get("cheq_no"));
		mode.addAttribute("acct_code", detailMap.get("acct_code"));
		mode.addAttribute("payable_money", detailMap.get("payable_money"));
		mode.addAttribute("payed_money", detailMap.get("payed_money"));
		mode.addAttribute("pay_money", detailMap.get("pay_money"));
	/*	mode.addAttribute("dis_money", detailMap.get("dis_money"));*/
		mode.addAttribute("sup_id", detailMap.get("sup_id"));
		mode.addAttribute("sup_no", detailMap.get("sup_no"));
		mode.addAttribute("sup_code", detailMap.get("sup_code"));
		mode.addAttribute("sup_name", detailMap.get("sup_name"));
		mode.addAttribute("pay_code", detailMap.get("pay_code"));
		mode.addAttribute("pay_term_code", detailMap.get("pay_term_code"));
		mode.addAttribute("pay_term_name", detailMap.get("pay_term_name"));
		mode.addAttribute("note", detailMap.get("note"));
		mode.addAttribute("maker", detailMap.get("maker"));
		mode.addAttribute("make_date", df.format(df.parse(String.valueOf(detailMap.get("make_date")))));
		mode.addAttribute("checker", detailMap.get("checker"));
		mode.addAttribute("chk_date", detailMap.get("chk_date"));
		mode.addAttribute("is_init", detailMap.get("is_init"));
		mode.addAttribute("state", detailMap.get("state"));
		if(detailMap.get("pay_dept_id") != null && !"".equals(detailMap.get("pay_dept_id"))){
			mode.addAttribute("pay_dept_code", detailMap.get("pay_dept_id") + "," + detailMap.get("dept_no"));
		}else{
			mode.addAttribute("pay_dept_code","");
		}
		mode.addAttribute("bank_name", detailMap.get("bank_name"));
		mode.addAttribute("bank_no", detailMap.get("bank_no"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		mode.addAttribute("p08026", MyConfig.getSysPara("08026"));
		mode.addAttribute("p08028", MyConfig.getSysPara("08028"));
		
		return "hrp/med/medpay/medpaymain/medPayMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/updateMedPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String medPayMainJson = "";
		try{
			medPayMainJson = medPayMainService.updateMedPayMain(mapVo);
		}catch(Exception e){
			medPayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medPayMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/addOrUpdateMedPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medPayMainJson ="";
		
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
	  
		medPayMainJson = medPayMainService.addOrUpdateMedPayMain(detailVo);
		
		}
		return JSONObject.parseObject(medPayMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/deleteMedPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPayMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pay_id", ids[3]);
				mapVo.put("pay_bill_no", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
		String medPayMainJson  = "";
		try{
			 medPayDetailService.deleteBatchMedPayDetail(listVo);
			 medPayMainJson = medPayMainService.deleteBatchMedPayMain(listVo);
		}catch(Exception e ){
			medPayMainJson = e.getMessage();
		}
	   
	  return JSONObject.parseObject(medPayMainJson);
			
	}
	/**
	 * 审核、消审、确认、取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/updatePayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePayState(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pay_id", ids[3]);
				mapVo.put("pay_bill_no", ids[4]);
				mapVo.put("state", ids[6]);
				if(Integer.valueOf(String.valueOf(ids[5])) ==1 && Integer.valueOf(String.valueOf(ids[6])) ==2 ){
					mapVo.put("checker", SessionManager.getUserId());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					mapVo.put("chk_date", df.format(new Date()));
				}
				if(Integer.valueOf(String.valueOf(ids[5])) ==2 && Integer.valueOf(String.valueOf(ids[6])) ==1){
					mapVo.put("checker", "");
					mapVo.put("chk_date", "");
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medProtocolMainJson = "";
		try{
			medProtocolMainJson = medPayMainService.updatePayState(listVo);
		}catch(Exception e){
			
			medProtocolMainJson= e.getMessage();
		}
				
			
	    return JSONObject.parseObject(medProtocolMainJson);
	}
	/**
	 * 删除 付款单明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/deleteMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pay_id", ids[3]);
				mapVo.put("pay_detail_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
		String medPayMainJson  = "";
		try{
			medPayMainJson = medPayDetailService.deleteBatchMedPayDetail(listVo);
		}catch(Exception e ){
			medPayMainJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(medPayMainJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/queryMedPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medPayMain = medPayMainService.queryMedPayMain(getPage(mapVo));

		return JSONObject.parseObject(medPayMain);
		
	}
	/**
	 * 根据 付款单Id 查询对应的付款单明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/queryMedPayDetailNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayDetailNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String medDetailMain = medPayDetailService.queryMedPayDetailNew(getPage(mapVo));

		return JSONObject.parseObject(medDetailMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/medPayMainImportPage", method = RequestMethod.GET)
	public String medPayMainImportPage(Model mode) throws Exception {

		return "hrp/med/medpay/medpaymain/medPayMainImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medpay/medpaymain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","付款单模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/medpay/medpaymain/readMedPayMainFiles",method = RequestMethod.POST)  
    public String readMedPayMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedPayMain> list_err = new ArrayList<MedPayMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedPayMain medPayMain = new MedPayMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medPayMain.setPay_id(Long.valueOf(temp[3]));
		    		mapVo.put("pay_id", temp[3]);
					
					} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medPayMain.setPay_bill_no(temp[4]);
		    		mapVo.put("pay_bill_no", temp[4]);
					
					} else {
						
						err_sb.append("付款单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medPayMain.setPay_date(DateUtil.stringToDate(temp[5]));
		    		mapVo.put("pay_date", temp[5]);
					
					} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medPayMain.setPay_bill_type(temp[6]);
		    		mapVo.put("pay_bill_type", temp[6]);
					
					} else {
						
						err_sb.append("单据类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medPayMain.setPay_type_code(temp[7]);
		    		mapVo.put("pay_type_code", temp[7]);
					
					} else {
						
						err_sb.append("付款方式为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medPayMain.setCheq_no(temp[8]);
		    		mapVo.put("cheq_no", temp[8]);
					
					} else {
						
						err_sb.append("票据号码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medPayMain.setAcct_code(temp[9]);
		    		mapVo.put("acct_code", temp[9]);
					
					} else {
						
						err_sb.append("付款账号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					medPayMain.setPay_money(Double.valueOf(temp[10]));
		    		mapVo.put("pay_money", temp[10]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					medPayMain.setSup_id(Long.valueOf(temp[14]));
		    		mapVo.put("sup_id", temp[14]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					medPayMain.setSup_no(Long.valueOf(temp[15]));
		    		mapVo.put("sup_no", temp[15]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					medPayMain.setPay_code(temp[19]);
		    		mapVo.put("pay_code", temp[19]);
					
					} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					medPayMain.setNote(temp[22]);
		    		mapVo.put("note", temp[22]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					medPayMain.setMaker(Long.valueOf(temp[23]));
		    		mapVo.put("maker", temp[23]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					medPayMain.setChecker(Long.valueOf(temp[24]));
		    		mapVo.put("checker", temp[24]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[25])) {
						
					medPayMain.setChk_date(DateUtil.stringToDate(temp[25]));
		    		mapVo.put("chk_date", temp[25]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[26])) {
						
					medPayMain.setIs_init(Integer.valueOf(temp[26]));
		    		mapVo.put("is_init", temp[26]);
					
					} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[27])) {
						
					medPayMain.setState(Integer.valueOf(temp[27]));
		    		mapVo.put("state", temp[27]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				MedPayMain data_exc_extis = medPayMainService.queryMedPayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPayMain.setError_type(err_sb.toString());
					
					list_err.add(medPayMain);
					
				} else {
			  
					String dataJson = medPayMainService.addMedPayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPayMain data_exc = new MedPayMain();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/addBatchMedPayMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedPayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedPayMain> list_err = new ArrayList<MedPayMain>();
		
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
			
			MedPayMain medPayMain = new MedPayMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_id"))) {
						
					medPayMain.setPay_id(Long.valueOf((String)jsonObj.get("pay_id")));
		    		mapVo.put("pay_id", jsonObj.get("pay_id"));
		    		} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_bill_no"))) {
						
					medPayMain.setPay_bill_no((String)jsonObj.get("pay_bill_no"));
		    		mapVo.put("pay_bill_no", jsonObj.get("pay_bill_no"));
		    		} else {
						
						err_sb.append("付款单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_date"))) {
						
					medPayMain.setPay_date(DateUtil.stringToDate((String)jsonObj.get("pay_date")));
		    		mapVo.put("pay_date", jsonObj.get("pay_date"));
		    		} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_bill_type"))) {
						
					medPayMain.setPay_bill_type((String)jsonObj.get("pay_bill_type"));
		    		mapVo.put("pay_bill_type", jsonObj.get("pay_bill_type"));
		    		} else {
						
						err_sb.append("单据类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_type_code"))) {
						
					medPayMain.setPay_type_code((String)jsonObj.get("pay_type_code"));
		    		mapVo.put("pay_type_code", jsonObj.get("pay_type_code"));
		    		} else {
						
						err_sb.append("付款方式为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cheq_no"))) {
						
					medPayMain.setCheq_no((String)jsonObj.get("cheq_no"));
		    		mapVo.put("cheq_no", jsonObj.get("cheq_no"));
		    		} else {
						
						err_sb.append("票据号码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acct_code"))) {
						
					medPayMain.setAcct_code((String)jsonObj.get("acct_code"));
		    		mapVo.put("acct_code", jsonObj.get("acct_code"));
		    		} else {
						
						err_sb.append("付款账号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {
						
					medPayMain.setPay_money(Double.valueOf((String)jsonObj.get("pay_money")));
		    		mapVo.put("pay_money", jsonObj.get("pay_money"));
		    		} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					medPayMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					medPayMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_code"))) {
						
					medPayMain.setPay_code((String)jsonObj.get("pay_code"));
		    		mapVo.put("pay_code", jsonObj.get("pay_code"));
		    		} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					medPayMain.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					medPayMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					medPayMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("chk_date"))) {
						
					medPayMain.setChk_date(DateUtil.stringToDate((String)jsonObj.get("chk_date")));
		    		mapVo.put("chk_date", jsonObj.get("chk_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					medPayMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					medPayMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				MedPayMain data_exc_extis = medPayMainService.queryMedPayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPayMain.setError_type(err_sb.toString());
					
					list_err.add(medPayMain);
					
				} else {
			  
					String dataJson = medPayMainService.addMedPayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPayMain data_exc = new MedPayMain();
			
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
     * 根据查询条件查询出没有被付款单引用过的发票
     * @param mapVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/queryMedBillMain_Pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillMain_Pay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medBillMain = medPayMainService.queryMedBillMain_Pay(getPage(mapVo));

		return JSONObject.parseObject(medBillMain);
		
	}
	
	/**
	 * 根据发票ID 查询发票明细信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/queryMedBillDetail_Pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillDetail_Pay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if(mapVo.get("pay_id") == null){
			
			mapVo.put("pay_id", "");
	        
		}
		
		String medBillDetail = medPayMainService.queryMedBillDetail_Pay(getPage(mapVo));

		return JSONObject.parseObject(medBillDetail);
		
	}
	/**
	 * 生成付款单号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/setPayBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setPayBillNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
		String medProtocolMainJson = medPayMainService.setPayBillNo(mapVo);
		
		return JSONObject.parseObject(medProtocolMainJson);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpay/medpaymain/medPayMainPrintSetPage", method = RequestMethod.GET)
	public String medPayMainPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
}

