package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.service.info.HtcgMaterialDictsService;
import com.chd.hrp.htcg.serviceImpl.info.HtcgMaterialDictsServiceImpl;

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
public class HtcgMatetialDictsController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgMatetialDictsController.class);
	
	
	@Resource(name = "htcgMaterialDictsService")
	private final HtcgMaterialDictsService htcgMaterialDictsService= null;
	                                       

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/info/materialDict/htcgMaterialDictsMainPage", method = RequestMethod.GET)
	public String htcgMaterialDictsMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/materialDict/htcgMaterialDictsMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/info/materialDict/queryHtcgMaterialDicts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMaterialDicts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcgMaterialDicts = htcgMaterialDictsService.queryHtcgMaterialDicts(getPage(mapVo));

		return JSONObject.parseObject(htcgMaterialDicts);
		
	}
}

