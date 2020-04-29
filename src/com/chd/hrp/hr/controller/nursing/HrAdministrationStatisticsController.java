package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.nursing.HrAdministrationStatistics;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrSelectService;
import com.chd.hrp.hr.service.nursing.HrAdministrationStatisticsService;

/**
 * 行政能力统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrAdministrationStatisticsController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAdministrationStatisticsController.class);

	// 引入Service服务
	@Resource(name = "hrAdministrationStatisticsService")
	private final HrAdministrationStatisticsService hrAdministrationStatisticsService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Resource(name = "hrSelectService")
	private HrSelectService hrSelectService = null;
	
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAdministrationStatisticsMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/administrationstatistics/administrationStatisticsMainPage";
	}

	

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAdministrationStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAdministrationStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrAdministrationStatisticsService.queryAdministrationStatistics(getPage(mapVo));
		return JSONObject.parseObject(stationTransef);
	}
	
	/**
	 * 获得获奖情况
	 * @author yangyunfei
	 */
	@RequestMapping(value = "/queryPrize.do", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrize(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.queryPrize(mapVo);
		return json;
	}
	
}
