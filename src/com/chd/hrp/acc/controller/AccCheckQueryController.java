/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.hrp.acc.serviceImpl.tell.AccTellServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCheckQueryController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCheckQueryController.class);
   
	@Resource(name = "accTellService")
	private final AccTellServiceImpl accTellService = null;
	   @Resource(name="modStartService")
	    private final ModStartServiceImpl modStartService = null;
	
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/checkquery/accCheckQueryMainPage", method = RequestMethod.GET)
	public String accCheckQueryMainPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCheckQueryMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/checkquery/accAllCheckQueryMainPage", method = RequestMethod.GET)
	public String accAllCheckQueryMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/acctell/accAllCheckQueryMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/checkquery/accCheckQueryAddPage", method = RequestMethod.GET)
	public String accCheckQueryAddPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCheckQueryAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/checkquery/accCheckQueryInstallPage", method = RequestMethod.GET)
	public String accCheckQueryInstallPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCheckQueryInstall";

	}
	
	@RequestMapping(value = "/hrp/acc/checkquery/queryCheckQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCheckQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("subj_code") == null || mapVo.get("subj_code").toString() == ""){
			return JSONObject.parseObject("{\"error\":\"请选择科目.\"}");
		}
		
		if(mapVo.get("begin_date") == null || mapVo.get("begin_date").toString() == ""
			|| mapVo.get("end_date") == null || mapVo.get("end_date").toString() == ""){
			return JSONObject.parseObject("{\"error\":\"请选择出纳日期开始与结束日期.\"}");
		}
		String reg = "^\\d{8}$";
		if(!mapVo.get("begin_date").toString().matches(reg) || !mapVo.get("end_date").toString().matches(reg)){
			return JSONObject.parseObject("{\"error\":\"出纳日期格式不正确.\"}");
		}
		
		mapVo.put("tell_flag", 0);
		mapVo.put("acct_flag", 0);
		String accTellJson = accTellService.collectAccCheck(mapVo);
		return JSONObject.parseObject(accTellJson);
	}
	//总账对账表查询
	@RequestMapping(value = "/hrp/acc/checkquery/queryAllCheckQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllCheckQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		if(mapVo.get("begin_date") == null || mapVo.get("begin_date").toString() == ""
			|| mapVo.get("end_date") == null || mapVo.get("end_date").toString() == ""){
			return JSONObject.parseObject("{\"error\":\"请选择出纳日期开始与结束日期.\"}");
		}
		/*String reg = "^\\d{6}$";
		if(!mapVo.get("begin_date").toString().matches(reg) || !mapVo.get("end_date").toString().matches(reg)){
			return JSONObject.parseObject("{\"error\":\"出纳日期格式不正确.\"}");
		}
		*/
		
		mapVo.put("acc_year",mapVo.get("begin_date").toString().substring(0, 4));
        mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		mapVo.put("end_yearMonth", mapVo.get("begin_date").toString());
		String accTellJson = accTellService.queryAllCheckQuery(mapVo);
		return JSONObject.parseObject(accTellJson);
	}
}

