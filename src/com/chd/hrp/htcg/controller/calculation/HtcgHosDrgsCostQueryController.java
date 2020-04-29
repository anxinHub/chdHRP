package com.chd.hrp.htcg.controller.calculation;

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
import com.chd.hrp.htcg.service.calculation.HtcgHosDrgsCostQueryService;

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
public class HtcgHosDrgsCostQueryController extends BaseController{ 
	private static Logger logger = Logger.getLogger(HtcgHosDrgsCostQueryController.class);
	@Resource(name = "htcgHosDrgsCostQueryService")
	private final HtcgHosDrgsCostQueryService htcgHosDrgsCostQueryService = null;
	// 维护页面跳转                                                                               
	@RequestMapping(value = "/hrp/htcg/calculation/hosdrgscostquery/htcgHosDrgsCostQueryMainPage", method = RequestMethod.GET)
	public String htcgHosDrgsCostQueryMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/hosdrgscostquery/htcgHosDrgsCostQueryMain";

	}
	
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/hosdrgscostquery/queryHtcgHosDrgsCostQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgHosDrgsCostQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String hospPdrgsCost = htcgHosDrgsCostQueryService.queryHtcgHosDrgsCostQuery(getPage(mapVo));
		return JSONObject.parseObject(hospPdrgsCost);
	}
	
	
}

