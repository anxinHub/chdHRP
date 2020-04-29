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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccInitialBalanceCalibrationServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccInitialBalanceCalibrationController extends BaseController{
	private static Logger logger = Logger.getLogger(AccInitialBalanceCalibrationController.class);
   
	@Resource(name = "accInitialBalanceCalibrationService")
	private final AccInitialBalanceCalibrationServiceImpl accInitialBalanceCalibrationService = null;
	
	
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/account/initialBalanceCalibrationMainPage", method = RequestMethod.GET)
	public String initialBalanceCalibrationMainPage(Model mode) throws Exception {

		return "hrp/acc/account/initialBalanceCalibrationMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/account/initialBalanceCalibrationAddPage", method = RequestMethod.GET)
	public String accBankAccountAddPage(Model mode) throws Exception {

		return "hrp/acc/account/initialBalanceCalibrationAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/account/initialBalanceCalibrationInstallPage", method = RequestMethod.GET)
	public String initialBalanceCalibrationInstallPage(Model mode) throws Exception {

		return "hrp/acc/account/initialBalanceCalibrationInstall";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 查询页面
	@RequestMapping(value = "/hrp/acc/account/queryInitialBalanceCalibration", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInitialBalanceCalibration(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		mapVo.put("mod_code", "01");
		
		mapVo.put("para_code", "018");
		
		String initialBalanceCalibration = accInitialBalanceCalibrationService.collectInitialBalanceCalibration(mapVo);

		return JSONObject.parseObject(initialBalanceCalibration);
	}

}

