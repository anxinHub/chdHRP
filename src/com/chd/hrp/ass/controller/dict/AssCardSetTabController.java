/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.dict;

import java.util.*;

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
import com.chd.hrp.ass.service.dict.AssCardSetTabService;


@Controller
public class AssCardSetTabController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCardSetTabController.class);

	// 引入Service服务
	@Resource(name = "assCardSetTabService")
	private final AssCardSetTabService assCardSetTabService = null;

	@RequestMapping(value = "/hrp/ass/asscardsettab/assCardSetTabMainPage", method = RequestMethod.GET)
	public String assCardSetTabMainPage(Model mode) throws Exception {

		return "hrp/ass/asscardsettab/assCardSetTabMain";

	}


	@RequestMapping(value = "/hrp/ass/asscardsettab/addAssCardSetTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCardSetTab(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCardSetTabJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			assCardSetTabJson = assCardSetTabService.add(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardSetTabJson);

	}



	/**
	 * 批量保存
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardsettab/updateAssCardSetTabAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardSetTabAll(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String assCardSetTabJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_naturs", ids[3]);
			mapVo.put("tab_id", ids[4]);
			mapVo.put("tab_name", ids[5]);
			mapVo.put("seq_no", ids[6]);
			mapVo.put("is_view", ids[7]);
			listVo.add(mapVo);
		}	
		try {

			assCardSetTabJson = assCardSetTabService.updateBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardSetTabJson);
	}
	@RequestMapping(value = "/hrp/ass/asscardsettab/updateAssCardSetTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardSetTab(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assCardSetTabJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_naturs", ids[3]);
			mapVo.put("tab_id", ids[4]);
			mapVo.put("tab_name", ids[5]);
			mapVo.put("seq_no", ids[6]);
			mapVo.put("is_view", ids[7]);
			listVo.add(mapVo);
		}

		try {

			assCardSetTabJson = assCardSetTabService.updateBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardSetTabJson);
	}

	
	@RequestMapping(value = "/hrp/ass/asscardsettab/deleteAssCardSetTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCardSetTab(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assCardSetTabJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_naturs", ids[3]);
			mapVo.put("tab_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			assCardSetTabJson = assCardSetTabService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardSetTabJson);

	}

	
	@RequestMapping(value = "/hrp/ass/asscardsettab/queryAssCardSetTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCardSetTab(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCardSetTab = assCardSetTabService.query(getPage(mapVo));

		return JSONObject.parseObject(assCardSetTab);

	}


}
