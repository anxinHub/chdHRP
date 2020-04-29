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
import com.chd.hrp.hr.service.medicalmanagement.HrFurtherStatisticsService;
/**
 * 进修统计表
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/further")
public class HrFurtherStatisticsController extends BaseController{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFurtherStatisticsController.class);

	// 引入Service服务
	@Resource(name = "hrFurtherStatisticsService")
	private final HrFurtherStatisticsService hrFurtherStatisticsService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFurtherStatisticsMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/furtherstatistics/furtherStatisticsMainPage";
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryFurtherStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFurtherStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrFurtherStatisticsService.query(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
}
