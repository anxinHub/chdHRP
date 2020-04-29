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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
import com.chd.hrp.hr.service.teachingmanagement.standardset.HrZyyNtrainInareaSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_ZYY_NTRAIN_INAREA_SET
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/teachingmanagement/standardset/hrzyyntraininareaset")
public class HrZyyNtrainInareaSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HrZyyNtrainInareaSetController.class);

	// 引入Service服务
	@Resource(name = "hrZyyNtrainInareaSetService")
	private final HrZyyNtrainInareaSetService hrZyyNtrainInareaSetService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrZyyNtrainInareaSetMainPage", method = RequestMethod.GET)
	public String hrZyyNtrainInareaSetMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/standardset/hrzyyntraininareaset/hrZyyNtrainInareaSetMain";

	}


	


	

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOrUpdateHrZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrZyyNtrainInareaSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainInareaSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			hrZyyNtrainInareaSetJson = hrZyyNtrainInareaSetService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrZyyNtrainInareaSetJson);
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
	@RequestMapping(value = "/deleteHrZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrZyyNtrainInareaSet(@RequestParam String paramVo, Model mode)
			throws Exception {

		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrZyyNtrainInareaSet> listVo = JSONArray.parseArray(paramVo, HrZyyNtrainInareaSet.class);
			for (HrZyyNtrainInareaSet hrZyyNtrainInareaSet : listVo) {
				hrZyyNtrainInareaSet.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrZyyNtrainInareaSet.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			
			String hrZyyNtrainInareaSetJson = hrZyyNtrainInareaSetService.deleteHrZyyNtrainInareaSet(listVo);


		return hrZyyNtrainInareaSetJson;
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
	@RequestMapping(value = "/queryHrZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrZyyNtrainInareaSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		
		String hrZyyNtrainInareaSet = hrZyyNtrainInareaSetService.query(getPage(mapVo));

		return JSONObject.parseObject(hrZyyNtrainInareaSet);

	}
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mathHrZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> mathHrZyyNtrainInareaSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrZyyNtrainInareaSetJson = "";
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			hrZyyNtrainInareaSetJson = hrZyyNtrainInareaSetService.math(mapVo);

			return JSONObject.parseObject(hrZyyNtrainInareaSetJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importZyyNtrainInareaSet", method = RequestMethod.POST)
	@ResponseBody
	public String importZyyNtrainInareaSet(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrZyyNtrainInareaSetService.importZyyNtrainInareaSet(mapVo);
		return reJson;
	}

}
