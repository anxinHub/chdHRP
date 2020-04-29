/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.dailymanagement;

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
import com.chd.hrp.ass.service.dailymanagement.AssRepairTroubleService;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
import com.chd.hrp.ass.service.repair.AssRepairRecService;

/**
 * 
 * @Description: 051201 维修故障统计报表
 * @Table: ASS_REPAIR_APPLY
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssRepairTroubleController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRepairTroubleController.class);

	
		//引入Service服务
		@Resource(name = "assRepairTroubleService")
		private final AssRepairTroubleService assRepairTroubleService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/dailymanagement/assRepairTroubleMainPage", method = RequestMethod.GET)
	public String assRepairTroubleMainPage(Model mode) throws Exception {

		return "hrp/ass/assdailymanagement/assRepairTroubleMain";

	}

	/**
	 * @Description 查询数据 051201 资产维修
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/dailymanagement/queryAssRepairTrouble", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairTrouble(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRepairtrouble =assRepairTroubleService.querytrouble(getPage(mapVo));
		return JSONObject.parseObject(assRepairtrouble);

	}

}
