package com.chd.hrp.hr.controller.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 积休查询
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAccRestController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAccRestController.class);

	// 引入Service服务 
	@Resource(name = "hrAccRestService")
	private final HrAccRestService hrAccRestService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAccRestMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/accrest/accrest/accRestMainPage";

	}
	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccRest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccRest(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String stationTransef = hrAccRestService.queryAccRest(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
}
