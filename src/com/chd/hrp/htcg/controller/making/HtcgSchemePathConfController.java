package com.chd.hrp.htcg.controller.making;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.htcg.service.making.HtcgSchemePathConfService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgSchemePathConfController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemePathConfController.class);

	@Resource(name = "htcgSchemePathConfService")
	private final HtcgSchemePathConfService htcgSchemePathConfService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemepathconf/htcgSchemePathConfMainPage", method = RequestMethod.GET)
	public String htcgSchemePathConfMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemepathconf/htcgSchemePathConfMain";

	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schemepathconf/initHtcgSchemePathConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemePathConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String schemePathConfJson ="";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			schemePathConfJson = htcgSchemePathConfService.initHtcgSchemePathConf(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemePathConfJson = e.getMessage();
		}
		return JSONObject.parseObject(schemePathConfJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemepathconf/queryHtcgSchemePathConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemePathConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 

		String schemePathConf = htcgSchemePathConfService.queryHtcgSchemePathConf(getPage(mapVo));

		return JSONObject.parseObject(schemePathConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schemepathconf/deleteHtcgSchemePathConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemePathConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			listVo.add(mapVo);
		}
		
		String schemePathConfJson = "";
		try {
			schemePathConfJson = htcgSchemePathConfService.deleteBatchHtcgSchemePathConf(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemePathConfJson = e.getMessage();
		}
		return JSONObject.parseObject(schemePathConfJson);

	}
 

	@RequestMapping(value = "/hrp/htcg/making/schemepathconf/saveHtcgSchemePathConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgSchemePathConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("mr_group", ids[4]);// 实际实体类变量
			mapVo.put("mr_sample", ids[5]);// 实际实体类变量
			mapVo.put("clp_step", ids[6]);// 实际实体类变量
			mapVo.put("recipe_p_merge", ids[7]);// 实际实体类变量
			mapVo.put("recipe_d_merge", ids[8]);// 实际实体类变量
			mapVo.put("recipe_p_group", ids[9]);// 实际实体类变量
			mapVo.put("recipe_d_group", ids[10]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String schemePathConfJson = "";
		try {
			
			schemePathConfJson = htcgSchemePathConfService.saveHtcgSchemePathConf(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			schemePathConfJson = e.getMessage();
		}
		return JSONObject.parseObject(schemePathConfJson);

	}
	
	 
}
