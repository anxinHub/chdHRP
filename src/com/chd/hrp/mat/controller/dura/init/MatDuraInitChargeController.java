/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.init;

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
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.init.MatDuraInitChargeService;

/**
 * @Description:  耐用品期初记账
 * @Table: MAT_DURA_(STORE/DEPT)_REG
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDuraInitChargeController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraInitChargeController.class);

	// 引入Service服务
	@Resource(name = "matDuraInitChargeService")
	private final MatDuraInitChargeService matDuraInitChargeService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/charge/mainPage", method = RequestMethod.GET)
	public String MatDuraInitChargeMainPage(Model mode) throws Exception {
		
		return "hrp/mat/dura/init/charge/main";
	}

	/**
	 * @Description 查询数据  耐用品期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/charge/queryMatDuraInitChargeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraInitChargeTree(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String matInitStatus = matDuraInitChargeService.queryTree(getPage(mapVo));
		
		return JSONObject.parseObject(matInitStatus);
	}

	/**
	 * @Description 记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/charge/saveMatDuraInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatDuraInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String matJson;
		try {
			matJson = matDuraInitChargeService.save(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 删除数据  耐用品期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/charge/deleteMatDuraInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraInitCharge(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		return JSONObject.parseObject("");
	}
}
