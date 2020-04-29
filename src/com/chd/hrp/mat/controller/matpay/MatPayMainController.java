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
import com.chd.hrp.mat.entity.MatPayMain;
import com.chd.hrp.mat.service.matpay.MatPayDetailService;
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
public class MatPayMainController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPayMainController.class);
	
	//引入Service服务
	@Resource(name = "matPayMainService")
	private final MatPayMainService matPayMainService = null;
	
	
	@Resource(name = "matPayDetailService")
	private final MatPayDetailService matPayDetailService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/matPayMainMainPage", method = RequestMethod.GET)
	public String matPayMainMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04028", MyConfig.getSysPara("04028"));
		mode.addAttribute("p04048", MyConfig.getSysPara("04048"));

		return "hrp/mat/matpay/matpaymain/matPayMainMain";

	}
	/**
	 * 添加发票页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/selectBillPage", method = RequestMethod.GET)
	public String selectBillPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name",mapVo.get("sup_name"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/matpay/matpaymain/selectBill";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/matPayMainAddPage", method = RequestMethod.GET)
	public String matPayMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/matpay/matpaymain/matPayMainAdd";

	}
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatMainlYesNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatMainlYesNo(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
				 matBillDetailJson = matPayMainService.queryMatMainlYesNo(listVo);
			}
		}catch(Exception e){
			matBillDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matBillDetailJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/addMatPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Long pay_id  = matPayMainService.queryPayID();
		
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
		
		String matPayMainJson ="";
		try{
			matPayMainJson = matPayMainService.addMatPayMain(mapVo);
		}catch(Exception e){
			matPayMainJson = e.getMessage() ;
		}

		return JSONObject.parseObject(matPayMainJson);
		
	}
	
	
	/**
	 * 添加付款单明细信息
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/addMatPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatPayDetail(@RequestParam(value="ParamVo") String paramVo, @RequestParam(value="pay_id") String pay_id, 
			@RequestParam(value="pay_bill_no") String pay_bill_no, Model mode) throws Exception {
		//添加 list
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//查询 付款单主表当前最大的 ID
		//Long pay_id  = matPayMainService.queryPayID();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();		
			String[] ids=id.split("@");
    	 
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("pay_id",pay_id);
			mapVo.put("pay_bill_no",pay_bill_no);
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
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/updateMatPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/matPayMainUpdatePage", method = RequestMethod.GET)
	public String matPayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		mode.addAttribute("bill_id", "".equals(bill_id) ? "" : bill_id.substring(0, bill_id.length()-1));
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
		mode.addAttribute("acc_check_state", detailMap.get("acc_check_state"));
		if(detailMap.get("pay_dept_id") != null && !"".equals(detailMap.get("pay_dept_id"))){
			mode.addAttribute("pay_dept_code", detailMap.get("pay_dept_id") + "," + detailMap.get("dept_no"));
		}else{
			mode.addAttribute("pay_dept_code","");
		}
		mode.addAttribute("bank_name", detailMap.get("bank_name"));
		mode.addAttribute("bank_no", detailMap.get("bank_no"));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04028", MyConfig.getSysPara("04028"));
		mode.addAttribute("p04048", MyConfig.getSysPara("04048"));
		
		return "hrp/mat/matpay/matpaymain/matPayMainUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/updateMatPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		String matPayMainJson = "";
		try{
			matPayMainJson = matPayMainService.updateMatPayMain(mapVo);
		}catch(Exception e){
			matPayMainJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matPayMainJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/addOrUpdateMatPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matPayMainJson ="";
		
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
	  
		matPayMainJson = matPayMainService.addOrUpdateMatPayMain(detailVo);
		
		}
		return JSONObject.parseObject(matPayMainJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/deleteMatPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPayMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("checker", SessionManager.getUserId());
				
	      listVo.add(mapVo);
	      
	    }
		String matPayMainJson  = "";
		try{
			 matPayDetailService.deleteBatchMatPayDetail(listVo);
			 matPayMainJson = matPayMainService.deleteBatchMatPayMain(listVo);
		}catch(Exception e ){
			matPayMainJson = e.getMessage();
		}
	   
	  return JSONObject.parseObject(matPayMainJson);
			
	}
	/**
	 * 审核、消审、确认、取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/updatePayState", method = RequestMethod.POST)
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
	    
		String matProtocolMainJson = "";
		try{
			matProtocolMainJson = matPayMainService.updatePayState(listVo);
		}catch(Exception e){
			
			matProtocolMainJson= e.getMessage();
		}
				
			
	    return JSONObject.parseObject(matProtocolMainJson);
	}
	/**
	 * 删除 付款单明细
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/deleteMatPayDetail", method = RequestMethod.POST)
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
		String matPayMainJson  = "";
		try{
			matPayMainJson = matPayDetailService.deleteBatchMatPayDetail(listVo);
		}catch(Exception e ){
			matPayMainJson = e.getMessage();
		}
		
			
	  return JSONObject.parseObject(matPayMainJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 state 1:未审核；2审核；3:记帐  PAY_BILL_TYPE: 0付款 1 退款
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matPayMain = matPayMainService.queryMatPayMain(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);
		
	}
	/**
	 * 根据 付款单Id 查询对应的付款单明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatPayDetailNew", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/matPayMainImportPage", method = RequestMethod.GET)
	public String matPayMainImportPage(Model mode) throws Exception {

		return "hrp/mat/matpay/matpaymain/matPayMainImport";

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
	 @RequestMapping(value="hrp/mat/matpay/matpaymain/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","付款单模版.xls");
	    
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
	@RequestMapping(value="/hrp/mat/matpay/matpaymain/readMatPayMainFiles",method = RequestMethod.POST)  
    public String readMatPayMainFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatPayMain> list_err = new ArrayList<MatPayMain>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatPayMain matPayMain = new MatPayMain();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matPayMain.setPay_id(Long.valueOf(temp[3]));
		    		mapVo.put("pay_id", temp[3]);
					
					} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matPayMain.setPay_bill_no(temp[4]);
		    		mapVo.put("pay_bill_no", temp[4]);
					
					} else {
						
						err_sb.append("付款单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					matPayMain.setPay_date(DateUtil.stringToDate(temp[5]));
		    		mapVo.put("pay_date", temp[5]);
					
					} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					matPayMain.setPay_bill_type(temp[6]);
		    		mapVo.put("pay_bill_type", temp[6]);
					
					} else {
						
						err_sb.append("单据类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					matPayMain.setPay_type_code(temp[7]);
		    		mapVo.put("pay_type_code", temp[7]);
					
					} else {
						
						err_sb.append("付款方式为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					matPayMain.setCheq_no(temp[8]);
		    		mapVo.put("cheq_no", temp[8]);
					
					} else {
						
						err_sb.append("票据号码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					matPayMain.setAcct_code(temp[9]);
		    		mapVo.put("acct_code", temp[9]);
					
					} else {
						
						err_sb.append("付款账号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					matPayMain.setPay_money(Double.valueOf(temp[10]));
		    		mapVo.put("pay_money", temp[10]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					matPayMain.setSup_id(Long.valueOf(temp[14]));
		    		mapVo.put("sup_id", temp[14]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					matPayMain.setSup_no(Long.valueOf(temp[15]));
		    		mapVo.put("sup_no", temp[15]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[19])) {
						
					matPayMain.setPay_code(temp[19]);
		    		mapVo.put("pay_code", temp[19]);
					
					} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					 
					 
					if (StringTool.isNotBlank(temp[22])) {
						
					matPayMain.setNote(temp[22]);
		    		mapVo.put("note", temp[22]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[23])) {
						
					matPayMain.setMaker(Long.valueOf(temp[23]));
		    		mapVo.put("maker", temp[23]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[24])) {
						
					matPayMain.setChecker(Long.valueOf(temp[24]));
		    		mapVo.put("checker", temp[24]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[25])) {
						
					matPayMain.setChk_date(DateUtil.stringToDate(temp[25]));
		    		mapVo.put("chk_date", temp[25]);
					
					} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[26])) {
						
					matPayMain.setIs_init(Integer.valueOf(temp[26]));
		    		mapVo.put("is_init", temp[26]);
					
					} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[27])) {
						
					matPayMain.setState(Integer.valueOf(temp[27]));
		    		mapVo.put("state", temp[27]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					
				MatPayMain data_exc_extis = matPayMainService.queryMatPayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPayMain.setError_type(err_sb.toString());
					
					list_err.add(matPayMain);
					
				} else {
			  
					String dataJson = matPayMainService.addMatPayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPayMain data_exc = new MatPayMain();
			
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
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/addBatchMatPayMain", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatPayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatPayMain> list_err = new ArrayList<MatPayMain>();
		
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
			
			MatPayMain matPayMain = new MatPayMain();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_id"))) {
						
					matPayMain.setPay_id(Long.valueOf((String)jsonObj.get("pay_id")));
		    		mapVo.put("pay_id", jsonObj.get("pay_id"));
		    		} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_bill_no"))) {
						
					matPayMain.setPay_bill_no((String)jsonObj.get("pay_bill_no"));
		    		mapVo.put("pay_bill_no", jsonObj.get("pay_bill_no"));
		    		} else {
						
						err_sb.append("付款单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_date"))) {
						
					matPayMain.setPay_date(DateUtil.stringToDate((String)jsonObj.get("pay_date")));
		    		mapVo.put("pay_date", jsonObj.get("pay_date"));
		    		} else {
						
						err_sb.append("付款日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_bill_type"))) {
						
					matPayMain.setPay_bill_type((String)jsonObj.get("pay_bill_type"));
		    		mapVo.put("pay_bill_type", jsonObj.get("pay_bill_type"));
		    		} else {
						
						err_sb.append("单据类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_type_code"))) {
						
					matPayMain.setPay_type_code((String)jsonObj.get("pay_type_code"));
		    		mapVo.put("pay_type_code", jsonObj.get("pay_type_code"));
		    		} else {
						
						err_sb.append("付款方式为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("cheq_no"))) {
						
					matPayMain.setCheq_no((String)jsonObj.get("cheq_no"));
		    		mapVo.put("cheq_no", jsonObj.get("cheq_no"));
		    		} else {
						
						err_sb.append("票据号码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("acct_code"))) {
						
					matPayMain.setAcct_code((String)jsonObj.get("acct_code"));
		    		mapVo.put("acct_code", jsonObj.get("acct_code"));
		    		} else {
						
						err_sb.append("付款账号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {
						
					matPayMain.setPay_money(Double.valueOf((String)jsonObj.get("pay_money")));
		    		mapVo.put("pay_money", jsonObj.get("pay_money"));
		    		} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {
						
					matPayMain.setSup_id(Long.valueOf((String)jsonObj.get("sup_id")));
		    		mapVo.put("sup_id", jsonObj.get("sup_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {
						
					matPayMain.setSup_no(Long.valueOf((String)jsonObj.get("sup_no")));
		    		mapVo.put("sup_no", jsonObj.get("sup_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_code"))) {
						
					matPayMain.setPay_code((String)jsonObj.get("pay_code"));
		    		mapVo.put("pay_code", jsonObj.get("pay_code"));
		    		} else {
						
						err_sb.append("付款条件为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					matPayMain.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("maker"))) {
						
					matPayMain.setMaker(Long.valueOf((String)jsonObj.get("maker")));
		    		mapVo.put("maker", jsonObj.get("maker"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("checker"))) {
						
					matPayMain.setChecker(Long.valueOf((String)jsonObj.get("checker")));
		    		mapVo.put("checker", jsonObj.get("checker"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("chk_date"))) {
						
					matPayMain.setChk_date(DateUtil.stringToDate((String)jsonObj.get("chk_date")));
		    		mapVo.put("chk_date", jsonObj.get("chk_date"));
		    		} else {
						
						err_sb.append("审核日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_init"))) {
						
					matPayMain.setIs_init(Integer.valueOf((String)jsonObj.get("is_init")));
		    		mapVo.put("is_init", jsonObj.get("is_init"));
		    		} else {
						
						err_sb.append("期初标志为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					matPayMain.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					
				MatPayMain data_exc_extis = matPayMainService.queryMatPayMainByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matPayMain.setError_type(err_sb.toString());
					
					list_err.add(matPayMain);
					
				} else {
			  
					String dataJson = matPayMainService.addMatPayMain(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatPayMain data_exc = new MatPayMain();
			
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
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatBillMain_Pay", method = RequestMethod.POST)
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
     * 根修改页面的添加按钮的查询
     * @param mapVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatBillMain_PayAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillMain_PayAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String matBillMain = matPayMainService.queryMatBillMain_PayAdd(getPage(mapVo));

		return JSONObject.parseObject(matBillMain);
		
	}
	/**
	 * 根据发票ID 查询发票明细信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatBillDetail_Pay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillDetail_Pay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String matBillDetail = matPayMainService.queryMatBillDetail_Pay(getPage(mapVo));

		return JSONObject.parseObject(matBillDetail);
		
	}
	/**
	 * 生成付款单号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/setPayBillNo", method = RequestMethod.POST)
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
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/matPayMainPrintSetPage", method = RequestMethod.GET)
	public String matPayMainPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	

	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/queryMatPayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = matPayMainService.queryMatPayState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"付款单不是审核状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
}

