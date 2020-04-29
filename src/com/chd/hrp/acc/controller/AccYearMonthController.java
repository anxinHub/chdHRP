/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccYearServiceImpl;

/**
 * @Title. @Description. 会计期间
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */

@Controller
public class AccYearMonthController extends BaseController {

	private static Logger logger = Logger.getLogger(AccYearMonthController.class);

	@Resource(name = "accYearMonthService")
	private final AccYearMonthServiceImpl accYearMonthService = null;
	
	@Resource(name = "accYearService")
	private final AccYearServiceImpl accYearService = null;
	

	/**
	 * 会计期间<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accyearmonth/accYearMonthMainPage", method = RequestMethod.GET)
	public String accYearMonthMainPage(Model mode) throws Exception {

		return "hrp/acc/accyearmonth/accYearMonthMain";

	}

	/**
	 * 会计期间<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accyearmonth/accYearMonthAddPage", method = RequestMethod.GET)
	public String accYearMonthAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		return "hrp/acc/accyearmonth/accYearMonthAdd";

	}

	/**
	 * 会计期间<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accyearmonth/addAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String accYearMonthJson = "";
		
		try {
			
			accYearMonthJson = accYearMonthService.addAccYearMonth(mapVo);
			return JSONObject.parseObject(accYearMonthJson);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
			
		}

		

	}

	/**
	 * 会计期间<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accyearmonth/queryAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String accYearMonth = accYearMonthService.queryAccYearMonth(mapVo);

		return JSONObject.parseObject(accYearMonth);

	}

	/**
	 * 会计期间<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accyearmonth/deleteAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String accYearMonthJson = accYearMonthService.deleteBatchAccYearMonth(mapVo);

		return JSONObject.parseObject(accYearMonthJson);

	}

	/**
	 * 会计期间<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accyearmonth/accYearMonthUpdatePage", method = RequestMethod.GET)
	public String accYearMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		/*
		 * AccYearMonth accYearMonth = new AccYearMonth(); accYearMonth =
		 * accYearMonthService.queryAccYearMonthByCode(mapVo);
		 * mode.addAttribute("group_id", accYearMonth.getGroup_id());
		 * mode.addAttribute("hos_id", accYearMonth.getHos_id());
		 * mode.addAttribute("copy_code", accYearMonth.getCopy_code());
		 * mode.addAttribute("acc_year", accYearMonth.getAcc_year());
		 * mode.addAttribute("acct_month", accYearMonth.getAcc_month());
		 * mode.addAttribute("begin_date", accYearMonth.getBegin_date());
		 * mode.addAttribute("end_date", accYearMonth.getEnd_date());
		 * mode.addAttribute("acc_flag", accYearMonth.getAcc_flag());
		 * mode.addAttribute("acc_user", accYearMonth.getAcc_user());
		 * mode.addAttribute("acc_date", accYearMonth.getAcc_date());
		 * mode.addAttribute("cash_flag", accYearMonth.getCash_flag());
		 * mode.addAttribute("casg_user", accYearMonth.getCasg_user());
		 */
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		return "hrp/acc/accyearmonth/accYearMonthUpdate";
	}

	/**
	 * 会计期间<BR>
	 * 修改保存
	 */
	@RequestMapping(value = "/hrp/acc/accyearmonth/updateAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String accYearMonthJson = accYearMonthService.updateAccYearMonth(mapVo);

		return JSONObject.parseObject(accYearMonthJson);
	}

	/**
	 * 会计期间<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accyearmonth/importAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accYearMonthJson = accYearMonthService.importAccYearMonth(mapVo);

		return JSONObject.parseObject(accYearMonthJson);
	}

	@RequestMapping(value = "/hrp/acc/accyearmonth/queryAccYearMonthByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccYearMonthByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		String group = accYearMonthService.queryAccYearMonthByMenu(mapVo);

		return JSONObject.parseObject(group);

	}

	@RequestMapping(value = "/hrp/acc/accyearmonth/queryAccYearMonthNow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccYearMonthNow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String group = accYearMonthService.queryAccYearMonthNow(mapVo);
		return JSONObject.parseObject(group);
	}
}
