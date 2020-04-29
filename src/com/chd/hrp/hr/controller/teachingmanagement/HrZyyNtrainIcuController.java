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
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainIcu;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainIcuService;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainIcuSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_ZYY_NTRAIN_ICU
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value="/hrp/hr/teachingmanagement")
public class HrZyyNtrainIcuController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainIcuController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainIcuService")
	private final HrZyyNtrainIcuService hrZyyNtrainIcuService = null;
	
	@Resource(name = "hrZyyNtrainIcuSetService")
	private final HrZyyNtrainIcuSetService hrZyyNtrainIcuSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainIcuMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainIcuMainPage(Model mode) throws Exception {
		
		
		return "hrp/hr/teachingmanagement/hrzyyntrainicu/hrZyyNtrainIcuMain";

	}

	
	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainIcu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainIcu(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainIcuJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainIcuJson = hrZyyNtrainIcuService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainIcuJson);
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
	@RequestMapping(value = "/deleteHrZyyNtrainIcu", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainIcu(@RequestParam String paramVo, Model mode)
			throws Exception {
		
		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainIcu> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainIcu.class);
			for (HrZyyNtrainIcu hrZyyNtrainIcu : listVo) {
				hrZyyNtrainIcu.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrZyyNtrainIcu.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}


			String hrZyyNtrainIcuJson = hrZyyNtrainIcuService.deleteHrZyyNtrainIcu(listVo);

			return hrZyyNtrainIcuJson;
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
	@RequestMapping(value = "/queryHrZyyNtrainIcu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainIcu(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("year") == null) {
			
			mapVo.put("year", SessionManager.getAcctYear());
			
		}
	
		String hrZyyNtrainIcu = hrZyyNtrainIcuService.query(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainIcu);

	}
	/**
	 * 查询专业信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryProfessionalComboBoxIcu", method = RequestMethod.POST)
	@ResponseBody
	public String queryProfessionalComboBoxIcu(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainIcuService.queryProfessionalComboBox(mapVo);
		
	}
	/**
	 * 查询学历信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEducationComboBoxIcu", method = RequestMethod.POST)
	@ResponseBody
	public String queryEducationComboBoxIcu(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainIcuService.queryEducationComboBox(mapVo);
		
	}
	/**
	 * 查询轮转科室信息  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeptComboBoxIcu", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptComboBoxIcu(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainIcuService.queryDeptComboBox(mapVo);
		
	}
	/**
	 * 查询  双击工号出现下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComboBoxIcu", method = RequestMethod.POST)
	@ResponseBody
	public String queryComboBoxIcu(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		return hrZyyNtrainIcuService.queryComboBox(mapVo);
		
	}
	/**
	 * 动态表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainIcuSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}
		HrZyyNtrainIcuSet fullScore = hrZyyNtrainIcuSetService.queryIcuSet(mapVo);
		reMap.put("fullScore", fullScore == null ? new HrZyyNtrainIcuSet() : fullScore);

		return reMap;

	}
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mathHrZyyNtrainIcu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainIcu(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainIcuJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainIcuJson = hrZyyNtrainIcuService.math(mapVo);

			return JSONObject.parseObject(hrZyyNtrainIcuJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainIcu", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainIcu(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainIcuService.importZyyNtrainIcu(mapVo);
		return reJson;
	}

}
