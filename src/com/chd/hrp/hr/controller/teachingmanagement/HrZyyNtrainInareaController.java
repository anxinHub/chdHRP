/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.teachingmanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainInarea;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
import com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainInareaService;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainInareaSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_ZYY_NTRAIN_INAREA
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value="/hrp/hr/teachingmanagement")
public class HrZyyNtrainInareaController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainInareaController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainInareaService")
	private final HrZyyNtrainInareaService hrZyyNtrainInareaService = null;
	@Resource(name = "hrZyyNtrainInareaSetService")
	private final HrZyyNtrainInareaSetService hrZyyNtrainInareaSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainInareaMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainInareaMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/hrzyyntraininarea/hrZyyNtrainInareaMain";

	}

	
	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainInarea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainInarea(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainInareaJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainInareaJson = hrZyyNtrainInareaService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainInareaJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrZyyNtrainInarea", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainInarea(@RequestParam String paramVo, Model mode)
			throws Exception {

		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainInarea> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainInarea.class);
			for (HrZyyNtrainInarea hrZyyNtrainInarea : listVo) {
						hrZyyNtrainInarea.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
						hrZyyNtrainInarea.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			
			String hrZyyNtrainInareaJson = hrZyyNtrainInareaService.deleteHrZyyNtrainInarea(listVo);

			return hrZyyNtrainInareaJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\""+e.getMessage()+"\"}";
		}

	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrZyyNtrainInarea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainInarea(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		

		/*if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}*/
		String hrZyyNtrainInarea = hrZyyNtrainInareaService.query(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainInarea);

	}
	/**
	 * 查询专业信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryProfessionalComboBox", method = RequestMethod.POST)
	@ResponseBody
	public String queryProfessionalComboBox(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainInareaService.queryProfessionalComboBox(mapVo);
		
	}
	/**
	 * 查询学历信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEducationComboBox", method = RequestMethod.POST)
	@ResponseBody
	public String queryEducationComboBox(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainInareaService.queryEducationComboBox(mapVo);
		
	}
	/**
	 * 查询轮转科室信息  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeptComboBox", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptComboBox(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainInareaService.queryDeptComboBox(mapVo);
		
	}
	/**
	 * 查询  双击工号出现下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComboBox", method = RequestMethod.POST)
	@ResponseBody
	public String queryComboBox(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		return hrZyyNtrainInareaService.queryComboBox(mapVo);
		
	}
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mathHrZyyNtrainInarea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainInarea(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainInareaJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainInareaJson = hrZyyNtrainInareaService.math(mapVo);

			return JSONObject.parseObject(hrZyyNtrainInareaJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * 查询住院医满分标准(病区)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainInareaSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}
		HrZyyNtrainInareaSet fullScore = hrZyyNtrainInareaSetService.queryInareaSet(mapVo);
		reMap.put("fullScore", fullScore == null ? new HrZyyNtrainIcuSet() : fullScore);

		return reMap;

	}

	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainInarea", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainInarea(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainInareaService.importZyyNtrainInarea(mapVo);
		return reJson;
	}

}
