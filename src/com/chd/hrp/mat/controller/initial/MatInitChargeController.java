/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.initial;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.initial.MatInitChargeService;

/**
 * 
 * @Description:  期初记账
 * @Table: MAT_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatInitChargeController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInitChargeController.class);

	// 引入Service服务
	@Resource(name = "matInitChargeService")
	private final MatInitChargeService matInitChargeService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/mainPage", method = RequestMethod.GET)
	public String MatInitChargeMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/initial/charge/main";
	}

	/**
	 * @Description 查询数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/queryMatInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matInitStatus = matInitChargeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matInitStatus);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/queryMatInitChargeById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInitChargeById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return matInitChargeService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/addMatInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		if(mapVo.get("acc_date") == null || "".equals(mapVo.get("acc_date"))){
			return JSONObject.parseObject("{\"error\":\"记账日期不能为空\",\"state\":\"false\"}");
		}
		//截取期间
		String[] date = mapVo.get("acc_date").toString().split("-");
		mapVo.put("year", date[0]);
		mapVo.put("month", date[1]);
		//转换日期格式
		if(mapVo.get("acc_date") != null && !"".equals(mapVo.get("acc_date"))){
			mapVo.put("acc_date", DateUtil.stringToDate(mapVo.get("acc_date").toString(), "yyyy-MM-dd"));
		}

		String matJson;
		try {
			matJson = matInitChargeService.add(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/initial/charge/updatePage", method = RequestMethod.GET)
	public String matInitChargeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/mat/initial/charge/update";
	}

	/**
	 * @Description 更新数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/updateMatInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		return JSONObject.parseObject("");
	}

	/**
	 * @Description 删除数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/initial/charge/deleteMatInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInitCharge(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		return JSONObject.parseObject("");
	}
}
