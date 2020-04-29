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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainTecSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainTecSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_ZYY_NTRAIN_TEC_SET
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/teachingmanagement/standardset/hrzyyntraintecset")
public class HrZyyNtrainTecSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainTecSetController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainTecSetService")
	private final HrZyyNtrainTecSetService hrZyyNtrainTecSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainTecSetMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainTecSetMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/standardset/hrzyyntraintecset/hrZyyNtrainTecSetMain";

	}

	
	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainTecSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainTecSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainTecSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			hrZyyNtrainTecSetJson = hrZyyNtrainTecSetService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainTecSetJson);
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
	@RequestMapping(value = "/deleteHrZyyNtrainTecSet", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainTecSet(@RequestParam String paramVo, Model mode)
			throws Exception {

		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainTecSet> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainTecSet.class);
			for (HrZyyNtrainTecSet hrZyyNtrainTecSet : listVo) {
				hrZyyNtrainTecSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrZyyNtrainTecSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			
			String hrZyyNtrainTecSetJson = hrZyyNtrainTecSetService.deleteHrZyyNtrainTecSet(listVo);


		return hrZyyNtrainTecSetJson;
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
	@RequestMapping(value = "/queryHrZyyNtrainTecSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainTecSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		
		String hrZyyNtrainTecSet = hrZyyNtrainTecSetService.query(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainTecSet);

	}
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mathHrZyyNtrainTecSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainTecSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainTecSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			hrZyyNtrainTecSetJson = hrZyyNtrainTecSetService.mathHrZyyNtrainTecSet(mapVo);

			return JSONObject.parseObject(hrZyyNtrainTecSetJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainTecSet", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainTecSet(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainTecSetService.importZyyNtrainTecSet(mapVo);
		return reJson;
	}
}
