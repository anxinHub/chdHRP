/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.teachingmanagement.standardset;

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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainIcuSetService;

/**
 * 
 * @Description:
 * 住院医师规培轮转成绩标准设置（急诊ICU）
 * @Table: HR_ZYY_NTRAIN_ICU_SET
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/teachingmanagement/standardset/hrzyyntrainicuset")
public class HrZyyNtrainIcuSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainIcuSetController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainIcuSetService")
	private final HrZyyNtrainIcuSetService hrZyyNtrainIcuSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainIcuSetMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainIcuSetMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/standardset/hrzyyntrainicuset/hrZyyNtrainIcuSetMain";

	}

	
	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainIcuSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainIcuSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainIcuSetJson = hrZyyNtrainIcuSetService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainIcuSetJson);
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
	@RequestMapping(value = "/deleteHrZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainIcuSet(@RequestParam String paramVo, Model mode)
			throws Exception {

		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainIcuSet> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainIcuSet.class);
			for (HrZyyNtrainIcuSet hrZyyNtrainIcuSet : listVo) {
				hrZyyNtrainIcuSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrZyyNtrainIcuSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			
			String hrZyyNtrainIcuSetJson = hrZyyNtrainIcuSetService.deleteHrZyyNtrainIcuSet(listVo);


		return hrZyyNtrainIcuSetJson;
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
	@RequestMapping(value = "/queryHrZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainIcuSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String hrZyyNtrainIcuSet = hrZyyNtrainIcuSetService.queryHrZyyNtrainIcuSet(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainIcuSet);

	}
	/**
	 * 计算
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mathHrZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainIcuSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainIcuSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainIcuSetJson = hrZyyNtrainIcuSetService.math(mapVo);

			return JSONObject.parseObject(hrZyyNtrainIcuSetJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainIcuSet", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainIcuSet(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainIcuSetService.importZyyNtrainIcuSet(mapVo);
		return reJson;
	}

}
