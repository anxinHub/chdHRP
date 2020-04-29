package com.chd.hrp.htc.controller.task.basic;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.basic.HtcResCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcResCauseDictService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcResCauseDictController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcResCauseDictController.class);

	@Resource(name = "htcResCauseDictService")
	private final HtcResCauseDictService htcResCauseDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/htcResCauseDictMainPage", method = RequestMethod.GET)
	public String htcResCauseDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/rescausedict/htcResCauseDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/htcResCauseDictAddPage", method = RequestMethod.GET)
	public String htcResCauseDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/rescausedict/htcResCauseDictAdd";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/addHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcResCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	
		String resCauseDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("res_cause_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("res_cause_name").toString()));
		mapVo.put("fun_value", 0);//fun_value没有使用,暂时给一个默认值0
		
		try {
			resCauseDictJson = htcResCauseDictService.addHtcResCauseDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resCauseDictJson = e.getMessage();
		}

		return JSONObject.parseObject(resCauseDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/queryHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcResCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String resCauseDict = htcResCauseDictService.queryHtcResCauseDict(getPage(mapVo));

		return JSONObject.parseObject(resCauseDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/deleteHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcResCauseDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

		String resCauseDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("res_cause_code", ids[3]);
			listVo.add(mapVo);
		}
		
		try {
			resCauseDictJson = htcResCauseDictService.deleteBatchHtcResCauseDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resCauseDictJson = e.getMessage();
		}

		return JSONObject.parseObject(resCauseDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/htcResCauseDictUpdatePage", method = RequestMethod.GET)
	public String htcResCauseDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		HtcResCauseDict resCauseDict = htcResCauseDictService.queryHtcResCauseDictByCode(mapVo);
		mode.addAttribute("group_id", resCauseDict.getGroup_id());
		mode.addAttribute("hos_id", resCauseDict.getHos_id());
		mode.addAttribute("copy_code", resCauseDict.getCopy_code());
		mode.addAttribute("res_cause_code", resCauseDict.getRes_cause_code());
		mode.addAttribute("res_cause_name", resCauseDict.getRes_cause_name());
		mode.addAttribute("res_remark", resCauseDict.getRes_remark());
		mode.addAttribute("spell_code", resCauseDict.getSpell_code());
		mode.addAttribute("wbx_code", resCauseDict.getWbx_code());
		mode.addAttribute("fun_value", resCauseDict.getFun_value());
		return "hrp/htc/task/basic/rescausedict/htcResCauseDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/rescausedict/updateHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcResCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String resCauseDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("res_cause_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("res_cause_name").toString()));
		mapVo.put("fun_value", 0);//fun_value没有使用,暂时给一个默认值0
		
		try {
			resCauseDictJson = htcResCauseDictService.updateHtcResCauseDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resCauseDictJson = e.getMessage();
		}

		return JSONObject.parseObject(resCauseDictJson);
	}
}
