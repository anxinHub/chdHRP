/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.controller;

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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hip.entity.HipDblinkSource;
import com.chd.hrp.hip.service.HipDblinkSourceService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipDblinkSourceController extends BaseController {
	private static Logger logger = Logger.getLogger(HipDblinkSourceController.class);

	@Resource(name = "hipDblinkSourceService")
	private final HipDblinkSourceService hipDblinkSourceService = null;

	@RequestMapping(value = "/hrp/hip/hipdblinksource/hipDblinkSourceMainPage", method = RequestMethod.GET)
	public String HipDblinkSourceMainPage(Model mode) throws Exception {
		
		return "hrp/hip/hipdblinksource/hipDblinkSourceMain";
	}

	@RequestMapping(value = "/hrp/hip/hipdblinksource/queryHipDblinkSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDblinkSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipDblinkSourceService.queryHipDblinkSource(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdblinksource/addHipDblinkSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipDblinkSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str;
		try {
			
			str = hipDblinkSourceService.addHipDblinkSource(mapVo);
		} catch (Exception e) {

			str = e.getMessage();
		}

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdblinksource/updateHipDblinkSourcePage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipDblinkSourcePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HipDblinkSource hdls = hipDblinkSourceService.queryHipDblinkSourceByCode(mapVo);

		return JSONObject.parseObject(JsonListBeanUtil.beanToJson(hdls));
	}

	@RequestMapping(value = "/hrp/hip/hipdblinksource/deleteHipDblinkSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipDblinkSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String apt;
		try {
			
			apt = hipDblinkSourceService.delHipDblinkSource(mapVo);
		} catch (Exception e) {

			apt = e.getMessage();
		}

		return JSONObject.parseObject(apt);
	}
}
