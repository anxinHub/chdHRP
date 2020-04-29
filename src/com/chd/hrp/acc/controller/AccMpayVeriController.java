package com.chd.hrp.acc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckTypeMapper;
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccMpayVeriServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCheckTypeServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccSubjServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccVerificationServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;

@Controller
public class AccMpayVeriController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMpayVeriController.class);
	
	@Resource(name = "accMpayVeriService")
	private final AccMpayVeriServiceImpl accMpayVeriService = null;
	
	@Resource(name = "accSubjService")
	private final AccSubjServiceImpl accSubjService = null;
	
	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;
	
	@Resource(name = "accLederCheckService")
	private final AccLederCheckServiceImpl accLederCheckService = null;
	
	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeServiceImpl accCheckTypeService = null;
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
	
	@Resource(name = "accVerificationService")
	private final AccVerificationServiceImpl accVerificationServiceImpl = null;
	
	@Resource(name = "accCheckTypeMapper")
	private final AccCheckTypeMapper accCheckTypeMapper = null;
	
	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;
	/*
	 * 往来核销页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/accMpayVeriMainPage", method = RequestMethod.GET)
	public String accMpayVeriMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String rs = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		mode.addAttribute("acc_month", SessionManager.getSysYearMonth("acc_flag"));
		return "hrp/acc/accmpayveri/accMpayVeriMain";
	}
	/*
	 * 往来核销左侧查询
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/queryAccMpayVeriL", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccMpayVeriL(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = accMpayVeriService.queryAccMpayVeri(mapVo);
		return JSONObject.parseObject(result);
	}
	
	/*
	 * 往来核销右侧查询
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/queryAccMpayVeriR", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccMpayVeriR(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = accMpayVeriService.queryAccMpayVeriR(mapVo);
		return JSONObject.parseObject(result);
	}
	/**
	 * 确定查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/queryAccMpayVeriAll", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccMpayVeriAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = accMpayVeriService.queryAccVeriAll(mapVo);
		return JSONObject.parseObject(result);
	}
	/**
	 * 往来核销--勾选进行核销
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/addAccMpayVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccMpayVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    String accVouchCheckJson = accMpayVeriService.addAccMpayVeri(mapVo);
	    
	    return JSONObject.parseObject(accVouchCheckJson);			
	    
	}
	
	/**
	 * 勾选取消核销
	 * deleteCancleAccVericaIsCheck
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/deleteCancleAccMpayVericaIsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCancleAccMpayVericaIsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        mapVo.put("user_id", SessionManager.getUserId());
        
        String accLederJson;
		try {
			accLederJson = accVerificationServiceImpl.deleteAccVeriIsChecked(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		/*List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listVo1= new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)mapVo.get("check_dataJ"));
		JSONArray json1 = JSONArray.parseArray((String)mapVo.get("check_dataD"));
		
		
		Map<String,Double> mapJ = new HashMap<String,Double>();	//要比较的对象
		Map<String,Double> mapD = new HashMap<String,Double>();	//要比较的对象
		
		Iterator it1 = json.iterator();
		try{
			while (it1.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it1.next().toString());
				Map<String, Object> mapVo1 = new HashMap<String, Object>();
				
				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("acc_year", SessionManager.getAcctYear());
				mapVo1.put("subj_id", mapVo.get("subj_id"));
				mapVo1.put("veri_check_id", jsonObj.get("veri_check_id"));
				
				mapVo1.put("debit_check_id", jsonObj.get("check_id"));
				
				if(mapJ.get(mapVo1.get("veri_check_id").toString())==null){
					mapJ.put(jsonObj.get("veri_check_id").toString(),Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				}else{
					mapJ.put(jsonObj.get("veri_check_id").toString(),Double.parseDouble(mapJ.get(jsonObj.get("veri_check_id").toString()).toString())+Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				}
				
				listVo.add(mapVo1);
			}
		}catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"核销失败！\",\"state\":\"err\"}");
		}
		
		Iterator it2 = json1.iterator();
		try{
			while (it2.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it2.next().toString());
				Map<String, Object> mapVo2 = new HashMap<String, Object>();
				
				mapVo2.put("group_id", SessionManager.getGroupId());
				mapVo2.put("hos_id", SessionManager.getHosId());
				mapVo2.put("copy_code", SessionManager.getCopyCode());
				mapVo2.put("acc_year", SessionManager.getAcctYear());
				mapVo2.put("subj_id", mapVo.get("subj_id"));
				mapVo2.put("veri_check_id", jsonObj.get("veri_check_id"));
				
				mapVo2.put("credit_check_id", jsonObj.get("check_id"));
				if(mapD.get(jsonObj.get("veri_check_id").toString())==null){
					mapD.put(jsonObj.get("veri_check_id").toString(),Double.parseDouble(jsonObj.get("ybal_amt").toString()));	
				}else{
					
					//System.out.println("*****:"+Double.parseDouble(mapD.get(jsonObj.get("veri_check_id").toString()).toString())+Double.parseDouble(jsonObj.get("ybal_amt").toString()));
					mapD.put(jsonObj.get("veri_check_id").toString(),Double.parseDouble(mapD.get(jsonObj.get("veri_check_id").toString()).toString())+Double.parseDouble(jsonObj.get("ybal_amt").toString()));
				}
				
				
				listVo.add(mapVo2);
			}
		}catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		
		
		Map<String, Object> mapVo1 = new HashMap<String,Object>();
		
		mapVo1.put("group_id", SessionManager.getGroupId());
		mapVo1.put("hos_id", SessionManager.getHosId());
		mapVo1.put("copy_code", SessionManager.getCopyCode());
		mapVo1.put("acc_year", SessionManager.getAcctYear());
		
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		mapVo1.put("create_user", SessionManager.getUserId());
		mapVo1.put("user_id",SessionManager.getUserId());
		mapVo1.put("create_date", dateFormat.format(now));
		String message = "勾选取消科目："+mapVo.get("subj_name").toString() + "的核销记录！";
		mapVo1.put("note", message);
		
		int count = 0;//查看是否勾选同一时间核销的ID
		int countC = 0;
		for (String key : mapJ.keySet()) {
			if(mapD.get(key) ==null){
				count++;
			}else{
				if(!mapJ.get(key).equals(mapD.get(key))){
					countC++;
				}
			}
			if(mapD.get(key) !=null){
				if(!mapJ.get(key).equals(mapD.get(key))){
					count++;
				}
			}else{
				count++;
			}
		}
		
		if(count != 0){
			return JSONObject.parseObject("{\"msg\":\"取消核销失败，请勾选相对应的借贷方！\",\"state\":\"err\"}");
		}else{
			if(countC!=0){
				return JSONObject.parseObject("{\"msg\":\"取消核销失败，金额不相等！\",\"state\":\"err\"}");
			}else{
				String accVouchCheckJson = accVerificationServiceImpl.deleteAccVeriIsChecked(listVo,mapVo1);
				return JSONObject.parseObject(accVouchCheckJson);
			}
			
		}*/
			
	}

	/**
	 * 取消批量核销跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/accMpayVeriBatchCancle", method = RequestMethod.GET)
	public String accMpayVeriBatchCancle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("subj_id", mapVo.get("subj_id"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_name", SessionManager.getUserName());
		return "hrp/acc/accmpayveri/accMpayVeriBatchCancle";
	}
	
	/**
	 * 批量取消 核销
	 * deleteBatchCancleAccVerica
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/deleteBatchCancleAccMpayVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchCancleAccMpayVeri(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		mapVo.put("create_date", dateFormat.format(now));
		String message = "批量取消  科目："+ mapVo.get("subj_name").toString()+ " 在  "+mapVo.get("start_days").toString()+" 至 "+mapVo.get("end_days").toString()+" 时间范围内 "+mapVo.get("user_name")+" 的核销记录！";
		mapVo.put("note", message);
		mapVo.put("user_id",SessionManager.getUserId());
        String accLederCheckJson = accVerificationServiceImpl.deleteBatchAccVeri(mapVo);
 	    return JSONObject.parseObject(accLederCheckJson);
		
	}
	
	//往来核销--核销明细账
	@RequestMapping(value = "/hrp/acc/accmpayveri/accMpayVeriDetail", method = RequestMethod.GET)
	public String accMpayVeriDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        mode.addAttribute("veri_check_id", mapVo.get("veri_check_id"));
		mode.addAttribute("check_id", mapVo.get("check_id"));
		mode.addAttribute("direct", mapVo.get("direct"));
		mode.addAttribute("check_type", mapVo.get("check_type"));
		mode.addAttribute("subj_id", mapVo.get("subj_id"));
		mode.addAttribute("check_type_id", mapVo.get("check_type_id"));
		
		return "hrp/acc/accmpayveri/accMpayVeriDetail";
	}
	/*
	 *  往来核销 核销明细数据查询
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/queryAccMpayVeriDetail", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccMpayVeriDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
       
        String result = "";
		result = accVerificationServiceImpl.queryAccVerDetail(getPage(mapVo));
		
		return JSONObject.parseObject(result);
		
	}
	/**
	 * 查询会计科目  带方向
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accmpayveri/querySubjDirect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjDirect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String subj = accVerificationServiceImpl.querySubjDirect(mapVo);
		return subj;
		
	}
	//单位
	@RequestMapping(value = "/hrp/acc/accmpayveri/queryVHosDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryVHosDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String subj = accLederService.queryHosInfo(mapVo);
		return subj;
		
	}
}
