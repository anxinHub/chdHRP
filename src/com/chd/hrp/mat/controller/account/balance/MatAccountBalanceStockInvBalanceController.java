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
import com.chd.hrp.mat.service.account.balance.MatAccountBalanceStockInvBalanceService;

/**
 * 
 * @Description: 库存材料收发帐表  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountBalanceStockInvBalanceController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAccountBalanceStockInvBalanceController.class);

	// 引入Service服务
	@Resource(name = "matAccountBalanceStockInvBalanceService")
	private final MatAccountBalanceStockInvBalanceService matAccountBalanceStockInvBalanceService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/matStockInvBalancePage", method = RequestMethod.GET)
	public String MatInvTranPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/balance/matStockInvBalance";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/balance/queryMatAccountBalanceStockInvBalance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountBalanceStockInvBalance(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAccountBalanceStockInvBalanceJson = matAccountBalanceStockInvBalanceService.queryMatAccountBalanceStockInvBalance(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountBalanceStockInvBalanceJson);
	}
}
