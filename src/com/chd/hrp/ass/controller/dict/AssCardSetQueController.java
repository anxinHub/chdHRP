/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.dict;

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
import com.chd.hrp.ass.service.dict.AssCardSetQueService;



@Controller
public class AssCardSetQueController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCardSetQueController.class);

	// 引入Service服务
	@Resource(name = "assCardSetQueService")
	private final AssCardSetQueService assCardSetQueService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardsetque/assCardSetQueMainPage", method = RequestMethod.GET)
	public String assCardSetViewMainPage(Model mode) throws Exception {

		return "hrp/ass/asscardsetque/assCardSetQueMain";

	}


	/**
	 * @Description 更新数据 050117 资产卡片显示表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardsetque/updateAssCardSetQue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardSetQue(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assCardSetViewJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_naturs", ids[3]);
			mapVo.put("table_name", ids[4]);
			mapVo.put("col_code", ids[5]);
			mapVo.put("col_name", ids[6]);
			mapVo.put("seq_no", ids[7]);
			mapVo.put("is_view", ids[8]);
			mapVo.put("col_name", ids[9]);
			mapVo.put("field_width", ids[10]);
			mapVo.put("is_section", ids[11]);
			mapVo.put("is_verify", ids[12]);
			mapVo.put("is_init", ids[13]);
			mapVo.put("is_default", ids[14]);
			mapVo.put("default_value", ids[15].replaceAll(" ", ""));
			mapVo.put("default_text", ids[16].replaceAll(" ", ""));
			listVo.add(mapVo);

		}

		try {

			assCardSetViewJson = assCardSetQueService.updateBatch(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardSetViewJson);
	}


	/**
	 * @Description 查询数据 050117 资产卡片显示表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardsetque/queryAssCardSetQue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCardSetQue(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCardSetView = assCardSetQueService.query(getPage(mapVo));

		return JSONObject.parseObject(assCardSetView);

	}


}
