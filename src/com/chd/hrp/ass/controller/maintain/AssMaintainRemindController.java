/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanService;

/**
 * 
 * @Description: 051202 保养提醒
 * @Table: ASS_MAINTAIN_PLAN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainRemindController extends BaseController {

	private static Logger logger = Logger.getLogger(AssMaintainRemindController.class);

	// 引入Service服务
	@Resource(name = "assMaintainPlanService")
	private final AssMaintainPlanService assMaintainPlanService = null;

	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/assMaintainRemindMainPage", method = RequestMethod.GET)
	public String assMaintainRemindMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainremind/assMaintainRemindMain";

	}

	/**
	 * @Description 查询数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/queryAssMaintainRemind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainRemind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String newDate = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
		
		mapVo.put("newDate", newDate);
		

		String assMaintainRemind = assMaintainPlanService.queryAssMainRemindPlan(getPage(mapVo));

		return JSONObject.parseObject(assMaintainRemind);

	}

}
