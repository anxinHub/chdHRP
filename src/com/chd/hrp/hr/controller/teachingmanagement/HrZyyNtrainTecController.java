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
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainTec;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
import com.chd.hrp.hr.service.teachingmanagement.HrZyyNtrainTecService;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainTecSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_ZYY_NTRAIN_TEC
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value="/hrp/hr/teachingmanagement")
public class HrZyyNtrainTecController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainTecController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainTecService")
	private final HrZyyNtrainTecService hrZyyNtrainTecService = null;
	@Resource(name = "hrZyyNtrainTecSetService")
	private final HrZyyNtrainTecSetService hrZyyNtrainTecSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainTecMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainTecMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/hrzyyntraintec/hrZyyNtrainTecMain";

	}

	
	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainTec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainTec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainTecJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainTecJson = hrZyyNtrainTecService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainTecJson);
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
	@RequestMapping(value = "/deleteHrZyyNtrainTec", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainTec(@RequestParam String paramVo, Model mode)
			throws Exception {

		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainTec> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainTec.class);
			for (HrZyyNtrainTec hrZyyNtrainTec : listVo) {
				hrZyyNtrainTec.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrZyyNtrainTec.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}

			String hrZyyNtrainTecJson = hrZyyNtrainTecService.deleteHrZyyNtrainTec(listVo);

			return hrZyyNtrainTecJson;
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
	@RequestMapping(value = "/queryHrZyyNtrainTec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainTec(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String hrZyyNtrainTec = hrZyyNtrainTecService.query(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainTec);

	}
	/**
	 * 查询专业信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryProfessionalComboBoxTec", method = RequestMethod.POST)
	@ResponseBody
	public String queryProfessionalComboBoxTec(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainTecService.queryProfessionalComboBox(mapVo);
		
	}
	/**
	 * 查询学历信息 下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEducationComboBoxTec", method = RequestMethod.POST)
	@ResponseBody
	public String queryEducationComboBoxTec(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainTecService.queryEducationComboBox(mapVo);
		
	}
	/**
	 * 查询轮转科室信息  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeptComboBoxTec", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptComboBoxTec(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		return hrZyyNtrainTecService.queryDeptComboBox(mapVo);
		
	}
	/**
	 * 查询  双击工号出现下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComboBoxTec", method = RequestMethod.POST)
	@ResponseBody
	public String queryComboBoxTec(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		return hrZyyNtrainTecService.queryComboBox(mapVo);
		
	}
	
	@RequestMapping(value = "/mathHrZyyNtrainTec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainTec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainTecJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainTecJson = hrZyyNtrainTecService.math(mapVo);

			return JSONObject.parseObject(hrZyyNtrainTecJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 动态表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrZyyNtrainSetTec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainSetTec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}
		//满分标准
		HrZyyNtrainTecSet fullScore = hrZyyNtrainTecSetService.queryTecSet(mapVo);
		
		reMap.put("fullScore", fullScore == null ? new HrZyyNtrainTecSet() : fullScore);
		
		
		return reMap;

	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainTec", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainTec(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainTecService.importZyyNtrainTec(mapVo);
		return reJson;
	}
}
