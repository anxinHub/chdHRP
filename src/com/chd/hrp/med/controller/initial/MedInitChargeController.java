/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.initial;

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
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.initial.MedInitChargeService;

/**
 * 
 * @Description:  期初记账
 * @Table: MED_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedInitChargeController extends BaseController {

	private static Logger logger = Logger.getLogger(MedInitChargeController.class);

	// 引入Service服务
	@Resource(name = "medInitChargeService")
	private final MedInitChargeService medInitChargeService = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/mainPage", method = RequestMethod.GET)
	public String MedInitChargeMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		
		return "hrp/med/initial/charge/main";
	}

	/**
	 * @Description 查询数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/queryMedInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String medInitStatus = medInitChargeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medInitStatus);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/queryMedInitChargeById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInitChargeById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return medInitChargeService.queryByCode(mapVo);
	}

	/**
	 * @Description 添加数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/addMedInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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

		String medJson;
		try {
			medJson = medInitChargeService.add(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medJson);

	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/initial/charge/updatePage", method = RequestMethod.GET)
	public String medInitChargeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/med/initial/charge/update";
	}

	/**
	 * @Description 更新数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/updateMedInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInitCharge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		return JSONObject.parseObject("");
	}

	/**
	 * @Description 删除数据  期初记账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/initial/charge/deleteMedInitCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInitCharge(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		return JSONObject.parseObject("");
	}
}
