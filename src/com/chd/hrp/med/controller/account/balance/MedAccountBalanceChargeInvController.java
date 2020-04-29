/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.account.balance;

import java.util.*;

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
import com.chd.hrp.med.service.account.balance.MedAccountBalanceChargeInvService;

/**
 * 
 * @Description: 计费药品使用查询 
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountBalanceChargeInvController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAccountBalanceChargeInvController.class);

	// 引入Service服务
	@Resource(name = "medAccountBalanceChargeInvService")
	private final MedAccountBalanceChargeInvService medAccountBalanceChargeInvService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/medChargeInvPage", method = RequestMethod.GET)
	public String medChargeInvPage(Model mode) throws Exception {
		
		mode.addAttribute("08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/balance/medChargeInv";
	}

	/**
	 * @Description 计费药品使用查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/queryMedAccountBalanceChargeInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountBalanceChargeInv(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(mapVo.get("begin_out_date") != null && !"".equals(mapVo.get("begin_out_date"))){
			mapVo.put("begin_out_date", DateUtil.stringToDate(mapVo.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_out_date") != null && !"".equals(mapVo.get("end_out_date"))){
			mapVo.put("end_out_date", DateUtil.stringToDate(mapVo.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		String medJson = medAccountBalanceChargeInvService.queryMedAccountBalanceChargeInv(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/medChargeInvCollectPage", method = RequestMethod.GET)
	public String medChargeInvCollectPage(Model mode) throws Exception {
		
		mode.addAttribute("08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/balance/medChargeInvCollect";
	}

	/**
	 * @Description 计费药品汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/queryMedAccountBalanceChargeInvCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountBalanceChargeInvCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(mapVo.get("begin_out_date") != null && !"".equals(mapVo.get("begin_out_date"))){
			mapVo.put("begin_out_date", DateUtil.stringToDate(mapVo.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_out_date") != null && !"".equals(mapVo.get("end_out_date"))){
			mapVo.put("end_out_date", DateUtil.stringToDate(mapVo.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		String medJson = medAccountBalanceChargeInvService.queryMedAccountBalanceChargeInvCollect(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/medChargeInvCollectByHospitalPage", method = RequestMethod.GET)
	public String medChargeInvCollectByHospitalPage(Model mode) throws Exception {
		
		mode.addAttribute("08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/balance/medChargeInvCollectByHospital";
	}
	
	/**
	 * @Description 计费药品汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/balance/queryMedAccountBalanceChargeInvCollectByHospital", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountBalanceChargeInvCollectByHospital(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(mapVo.get("begin_out_date") != null && !"".equals(mapVo.get("begin_out_date"))){
			mapVo.put("begin_out_date", DateUtil.stringToDate(mapVo.get("begin_out_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if(mapVo.get("end_out_date") != null && !"".equals(mapVo.get("end_out_date"))){
			mapVo.put("end_out_date", DateUtil.stringToDate(mapVo.get("end_out_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		
		String medJson = medAccountBalanceChargeInvService.queryMedAccountBalanceChargeInvCollectByHospital(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}
}
