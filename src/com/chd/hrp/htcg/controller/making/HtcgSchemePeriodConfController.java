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
import com.chd.hrp.htcg.service.making.HtcgSchemePeriodConfService;

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
public class HtcgSchemePeriodConfController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemePeriodConfController.class);

	@Resource(name = "htcgSchemePeriodConfService")
	private final HtcgSchemePeriodConfService htcgSchemePeriodConfService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemeperiodconf/htcgSchemePeriodConfMainPage", method = RequestMethod.GET)
	public String schemePeriodConfMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemeperiodconf/htcgSchemePeriodConfMain";
	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schemeperiodconf/initHtcgSchemePathConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemePathConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String schemePeriodConfJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			schemePeriodConfJson = htcgSchemePeriodConfService.initHtcgSchemePeriodConf(mapVo);
			
		}catch(Exception e){
			
			schemePeriodConfJson= e.getMessage();
			
		}
		return JSONObject.parseObject(schemePeriodConfJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemeperiodconf/queryHtcgSchemePeriodConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemePathConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String schemePeriodConf = htcgSchemePeriodConfService.queryHtcgSchemePeriodConf(getPage(mapVo));

		return JSONObject.parseObject(schemePeriodConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schemeperiodconf/deleteHtcgSchemePeriodConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemePathConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String schemePeriodConfJson ;
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			listVo.add(mapVo);
		}
		try{
			schemePeriodConfJson = htcgSchemePeriodConfService.deleteBatchHtcgSchemePeriodConf(listVo);
		}catch(Exception e){
			schemePeriodConfJson= e.getMessage();
		}
		return JSONObject.parseObject(schemePeriodConfJson);

	}


	@RequestMapping(value = "/hrp/htcg/making/schemeperiodconf/saveHtcgSchemePeriodConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgSchemePathConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String schemePeriodConfJson ;
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("month_flag", ids[4]);// 实际实体类变量
			mapVo.put("quarter_flag", ids[5]);// 实际实体类变量
			mapVo.put("half_year_flag", ids[6]);// 实际实体类变量
			mapVo.put("year_flag", ids[7]);// 实际实体类变量
			listVo.add(mapVo);
		}
		try{
			schemePeriodConfJson = htcgSchemePeriodConfService.saveHtcgSchemePeriodConf(listVo);
		}catch(Exception e){
			schemePeriodConfJson= e.getMessage();
		}
		return JSONObject.parseObject(schemePeriodConfJson);
	}
}
