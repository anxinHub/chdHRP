package com.chd.hrp.hr.controller.medicalmanagement;

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
import com.chd.hrp.hr.service.medicalmanagement.HrFurtherTrackingService;
/**
 * 进修追踪表
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/further")
public class HrFurtherTrackingController extends BaseController{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFurtherTrackingController.class);

	// 引入Service服务
	@Resource(name = "hrFurtherTrackingService")
	private final HrFurtherTrackingService hrFurtherTrackingService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFurtherTrackingMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/furthertracking/furtherTrackingMainPage";
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryFurtherTracking", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFurtherTracking(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrFurtherTrackingService.query(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
}
