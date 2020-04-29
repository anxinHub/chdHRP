/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.matpay;
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
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.mat.entity.MatPayMain;
import com.chd.hrp.mat.service.matpay.MatPayDetailService;
import com.chd.hrp.mat.service.matpay.MatPayForAccMainService;
import com.chd.hrp.mat.service.matpay.MatPayMainService;
/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
 * @Table:
 * MAT_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatPayForAccMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPayForAccMainController.class);
	
	//引入Service服务
	@Resource(name = "matPayForAccMainService")
	private final MatPayForAccMainService matPayForAccMainService = null;
	
	//引入Service服务
	@Resource(name = "matPayMainService")
	private final MatPayMainService matPayMainService = null;

	@Resource(name = "matPayDetailService")
	private final MatPayDetailService matPayDetailService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	//---------------------以下两个方法调用matPayForAccMainService 为财务使用 其他方法都是调用原来付款单的方法 如有修改请配套修改-----------------------------------
	/**
	 * @Description 
	 * 查询数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/queryMatPayForAccMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayForAccMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matPayForAccMain = matPayForAccMainService.queryMatPayForAccMain(getPage(mapVo));

		return JSONObject.parseObject(matPayForAccMain);
		
	}
   
	/**
	 * 审核、消审、确认、取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/updatePayStateForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePayStateForAcc(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
				mapVo.put("vouch_id", ids[7]);
				if(Integer.valueOf(String.valueOf(ids[5])) ==1 && Integer.valueOf(String.valueOf(ids[6])) ==2 ){
					mapVo.put("checker", SessionManager.getUserId());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					mapVo.put("chk_date", df.format(new Date()));
				}
				if(Integer.valueOf(String.valueOf(ids[5])) ==2 && Integer.valueOf(String.valueOf(ids[6])) ==1){
					mapVo.put("checker", "");
					mapVo.put("chk_date", "");
				}
				mapVo.put("user_id", SessionManager.getUserId());
				mapVo.put("log_table", "ACC_BUSI_LOG_0408");
				

	      listVo.add(mapVo);
	      
	    }
	    
		String matProtocolMainJson = "";
		try{
			matProtocolMainJson = matPayForAccMainService.updatePayState(listVo);
		}catch(Exception e){
			
			matProtocolMainJson= e.getMessage();
		}
				
			
	    return JSONObject.parseObject(matProtocolMainJson);
	}
	
	//---------------------以上两个方法调用matPayForAccMainService 为财务使用 其他方法都是调用原来付款单的方法 如有修改请配套修改-----------------------------------
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/matPayForAccMainMainPage", method = RequestMethod.GET)
	public String matPayForAccMainMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		

		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		
		mode.addAttribute("vouch_date", reMaxVouchDate);

		mode.addAttribute("p04028", MyConfig.getSysPara("04028"));
		
		return "hrp/mat/matpay/matpayforaccmain/matPayForAccMainMain";

	}
	/**
	 * 添加发票页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/selectBillPage", method = RequestMethod.GET)
	public String selectBillPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name",mapVo.get("sup_name"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/matpay/matpayforaccmain/selectBill";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/matPayForAccMainAddPage", method = RequestMethod.GET)
	public String matPayForAccMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/matpay/matpayforaccmain/matPayForAccMainAdd";

	}

	/**
	 * @Description 
	 * 添加数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/addMatPayForAccMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayForAccMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matPayForAccMainJson ="";
		try{
			matPayForAccMainJson = matPayMainService.addMatPayMain(mapVo);
		}catch(Exception e){
			matPayForAccMainJson = e.getMessage() ;
		}

		return JSONObject.parseObject(matPayForAccMainJson);
		
	}
	
	
	/**
	 * 添加付款单明细信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/addMatPayForAccDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayForAccDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		//添加 list
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//查询 付款单主表当前最大的 ID
		Long pay_id  = matPayMainService.queryPayID();
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
		String matBillDetailJson = null;
		try{
			if(listVo.size()>0){
				 matBillDetailJson = matPayDetailService.addBatchMatPayDetail(listVo);
			}
		}catch(Exception e){
			matBillDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matBillDetailJson);
	}
	
	
	/**
	 * 修改付款单明细信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/updateMatPayForAccDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayForAccDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			matPayDetailService.deleteMatPayDetail(mapVo);
			
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
		String matBillDetailJson = null;
		try{
			if(listVo.size()>0){
				 matBillDetailJson = matPayDetailService.addBatchMatPayDetail(listVo);
			}
		}catch(Exception e){
			matBillDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matBillDetailJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/matPayForAccMainUpdatePage", method = RequestMethod.GET)
	public String matPayForAccMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		List<Map<String,Object>> list = matPayDetailService.queryMatBillId(mapVo);
		String bill_id ="";
		for(Map<String,Object> item : list ){
			bill_id  += item.get("bill_id")+",";
		}
		
		Map<String,Object> detailMap = matPayMainService.queryMatPayMainUnit(mapVo);
		
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
		
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		
		mode.addAttribute("vouch_date", reMaxVouchDate);
		mode.addAttribute("vouch_id", mapVo.get("vouch_id"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04028", MyConfig.getSysPara("04028"));
		
		return "hrp/mat/matpay/matpayforaccmain/matPayForAccMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/updateMatPayForAccMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayForAccMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String matPayForAccMainJson = "";
		try{
			matPayForAccMainJson = matPayMainService.updateMatPayMain(mapVo);
		}catch(Exception e){
			matPayForAccMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matPayForAccMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/addOrUpdateMatPayForAccMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatPayForAccMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matPayForAccMainJson ="";
		
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
	  
		matPayForAccMainJson = matPayMainService.addOrUpdateMatPayMain(detailVo);
		
		}
		return JSONObject.parseObject(matPayForAccMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/deleteMatPayForAccMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPayForAccMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matPayForAccMainJson  = "";
		try{
			 matPayDetailService.deleteBatchMatPayDetail(listVo);
			 matPayForAccMainJson = matPayMainService.deleteBatchMatPayMain(listVo);
		}catch(Exception e ){
			matPayForAccMainJson = e.getMessage();
		}
	   
	  return JSONObject.parseObject(matPayForAccMainJson);
			
	}
	
	/**
	 * 删除 付款单明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/deleteMatPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matPayForAccMainJson  = "";
		try{
			matPayForAccMainJson = matPayDetailService.deleteBatchMatPayDetail(listVo);
		}catch(Exception e ){
			matPayForAccMainJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(matPayForAccMainJson);
			
	}
	
	/**
	 * 根据 付款单Id 查询对应的付款单明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/queryMatPayDetailNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayDetailNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String matDetailMain = matPayDetailService.queryMatPayDetailNew(getPage(mapVo));

		return JSONObject.parseObject(matDetailMain);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/matPayForAccMainImportPage", method = RequestMethod.GET)
	public String matPayForAccMainImportPage(Model mode) throws Exception {

		return "hrp/mat/matpay/matpayforaccmain/matPayMainImport";

	}
	/**
     * 根据查询条件查询出没有被付款单引用过的发票
     * @param mapVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/queryMatBillMain_Pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillMain_Pay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matBillMain = matPayMainService.queryMatBillMain_Pay(getPage(mapVo));

		return JSONObject.parseObject(matBillMain);
		
	}
	
	/**
	 * 生成付款单号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/setPayBillNo", method = RequestMethod.POST)
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
		String matProtocolMainJson = matPayMainService.setPayBillNo(mapVo);
		
		return JSONObject.parseObject(matProtocolMainJson);
	}
	
	@RequestMapping(value = "/hrp/mat/matpay/matpayforaccmain/matPayForAccMainPrintSetPage", method = RequestMethod.GET)
	public String matPayForAccMainPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
}

