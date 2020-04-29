package com.chd.hrp.acc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.AccVouchCashierServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchCashierController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchCashierController.class);

	@Resource(name = "accVouchCashierService")
	private final AccVouchCashierServiceImpl accVouchCashierService = null;
	
	@Resource(name = "accVouchService")
	private final AccVouchServiceImpl accVouchService = null;
	/**
	 * 凭证审核<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/cashier/accVouchCashierMainPage", method = RequestMethod.GET)
	public String accVouchCashierMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();  //SessionManager.getSysYearMonth("acc_flag");
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		return "hrp/acc/accvouch/cashier/accVouchCashierMain";

	}
	
	/**
	 * 凭证主表<BR>
	 * 高级查询页面跳转
	 */
	// 高级查询页面
	@RequestMapping(value = "/hrp/acc/accvouch/cashier/accVouchCashierSearchPage", method = RequestMethod.GET)
	public String accVouchCashierSearchPage(Model mode) throws Exception {

		return "hrp/acc/accvouch/cashier/accVouchCashierSearch";

	}

	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/cashier/queryAccVouchCashier", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchCashier(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accVouch = null;
		
		try{
			if(	"1".equals(mapVo.get("pay_type_code"))){
				
				accVouch = accVouchService.queryAccVouch(getPage(mapVo));
			}else{
				
				accVouch = accVouchService.queryAccVouchPayTypeCode(getPage(mapVo));
			}
	
			return JSONObject.parseObject(accVouch);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
	
	//出纳签字
	@RequestMapping(value = "/hrp/acc/accvouch/cashier/signAccVouchCashire", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> signAccVouchCashire(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String vouch_id="";
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String [] res = id.split("@");
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//mapVo.put("acc_year", SessionManager.getAcctYear());
			mapVo.put("state", "2");
			mapVo.put("vouch_state", res[1]);
			mapVo.put("cash", "0");
			mapVo.put("cash_user", SessionManager.getUserId());
			mapVo.put("cashe_date", new Date());
			vouch_id = vouch_id + res[0]+",";
			listVo.add(mapVo);
		}
		
		try {
			
			//如果不包含现金银行
			Map<String, Object> mapCheck = new HashMap<String, Object>();
			vouch_id = vouch_id.substring(0,vouch_id.lastIndexOf(","));
			mapCheck.put("group_id", SessionManager.getGroupId());
			mapCheck.put("hos_id", SessionManager.getHosId());
			mapCheck.put("copy_code", SessionManager.getCopyCode());
			mapCheck.put("vouchid", vouch_id);
			List<String> list2=accVouchService.queryAccVouchListByBank(mapCheck);
			if(list2!=null && list2.size()>0){
				
				return JSONObject.parseObject("{\"error\":\"非现金银行凭证："+list2.toString()+"\",\"state\":\"false\"}");
			}
			
			String accVouchJson = accVouchCashierService.updateBatchAccVouchCashier(listVo);
			return JSONObject.parseObject(accVouchJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
	
	//取消签字
	@RequestMapping(value = "/hrp/acc/accvouch/cashier/unsignAccVouchCashire", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unsignAccVouchCashire(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String [] res = id.split("@");
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//mapVo.put("acc_year", SessionManager.getAcctYear());
			mapVo.put("state", res[2]);
			mapVo.put("vouch_state",res[1]);
			mapVo.put("cash", "0");
			mapVo.put("cash_user", "");
			mapVo.put("cashe_date","");
			listVo.add(mapVo);
		}
		String accVouchJson = accVouchCashierService.updateBatchAccVouchCashier(listVo);
		return JSONObject.parseObject(accVouchJson);

	}
}
