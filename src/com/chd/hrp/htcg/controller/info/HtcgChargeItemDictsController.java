
/*
 *
 */package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.service.info.HtcgChargeItemDictsService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgChargeItemDictsController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgChargeItemDictsController.class);
	
	
	@Resource(name = "htcgChargeItemDictsService")
	private final HtcgChargeItemDictsService htcgChargeItemDictsService= null;
    

	// 维护页面跳转
	@RequestMapping(value = "hrp/htcg/info/chargeItemDict/htcgChargeItemDictsMainPage", method = RequestMethod.GET)
	public String htcgChargeItemDictsMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/chargeItemDict/htcgChargeItemDictsMain";

	}
	
	// 查询
	@RequestMapping(value = "hrp/htcg/info/chargeItemDict/queryHtcgChargeItemDicts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgChargeItemDicts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String clpStepDict = htcgChargeItemDictsService.queryHtcgChargeItemDicts(getPage(mapVo));

		return JSONObject.parseObject(clpStepDict);
		
	}
	
}

