/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.account.balance;

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
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceChargeInvService;

/**
 * 
 * @Description: 计费材料使用查询 
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountBalanceChargeInvController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAccountBalanceChargeInvController.class);

	// 引入Service服务
	@Resource(name = "matAccountBalanceChargeInvService")
	private final MatAccountBalanceChargeInvService matAccountBalanceChargeInvService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/matChargeInvPage", method = RequestMethod.GET)
	public String matChargeInvPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		return "hrp/mat/account/balance/matChargeInv";
	}

	/**
	 * @Description 计费材料使用查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/queryMatAccountBalanceChargeInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountBalanceChargeInv(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matAccountBalanceChargeInvService.queryMatAccountBalanceChargeInv(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/matChargeInvCollectPage", method = RequestMethod.GET)
	public String matChargeInvCollectPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		return "hrp/mat/account/balance/matChargeInvCollect";
	}

	/**
	 * @Description 计费材料汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/queryMatAccountBalanceChargeInvCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountBalanceChargeInvCollect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matAccountBalanceChargeInvService.queryMatAccountBalanceChargeInvCollect(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/matChargeInvCollectByHospitalPage", method = RequestMethod.GET)
	public String matChargeInvCollectByHospitalPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		return "hrp/mat/account/balance/matChargeInvCollectByHospital";
	}
	
	/**
	 * @Description 计费材料汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/queryMatAccountBalanceChargeInvCollectByHospital", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountBalanceChargeInvCollectByHospital(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
		
		String matJson = matAccountBalanceChargeInvService.queryMatAccountBalanceChargeInvCollectByHospital(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
}
