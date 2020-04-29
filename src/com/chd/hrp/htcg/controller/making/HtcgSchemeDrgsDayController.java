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
import com.chd.hrp.htcg.service.making.HtcgSchemeDrgsDayService;

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
public class HtcgSchemeDrgsDayController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeDrgsDayController.class);

	@Resource(name = "htcgSchemeDrgsDayService")
	private final HtcgSchemeDrgsDayService htcgSchemeDrgsDayService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemedrgsday/htcgSchemeDrgsDayMainPage", method = RequestMethod.GET)
	public String htcgSchemeDrgsDayMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemedrgsday/htcgSchemeDrgsDayMain";

	}
	
	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schemedrgsday/initHtcgSchemeDrgsDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemeDrgsDay(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemePathConfJson = "";
			try {
				schemePathConfJson = htcgSchemeDrgsDayService.initHtcgSchemeDrgsDay(mapVo);
			} catch (Exception e) {
				// TODO: handle exception
				schemePathConfJson = e.getMessage();
			}
		return JSONObject.parseObject(schemePathConfJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemedrgsday/queryHtcgSchemeDrgsDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySchemeDrgsDay(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeDrgsDay = htcgSchemeDrgsDayService.queryHtcgSchemeDrgsDay(getPage(mapVo));
		return JSONObject.parseObject(schemeDrgsDay);

	}

	// 批量修改保存
	@RequestMapping(value = "/hrp/htcg/making/schemedrgsday/saveHtcgSchemeDrgsDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgSchemeDrgsDay(
			@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);
			mapVo.put("drgs_code", ids[4]);
			mapVo.put("drgs_day", ids[5].equals("-")?"":ids[5]);
			listVo.add(mapVo);
		}
		String schemeMrRuleJson = "";
		try {
			schemeMrRuleJson = htcgSchemeDrgsDayService.updateBatchHtcgSchemeDrgsDay(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeMrRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeMrRuleJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schemedrgsday/deleteHtcgSchemeDrgsDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeDrgsDay(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("drgs_code", ids[4]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String schemeDrgsDayJson = "";
		try {
			 schemeDrgsDayJson = htcgSchemeDrgsDayService.deleteBatchHtcgSchemeDrgsDay(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeDrgsDayJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeDrgsDayJson);

	}
}
