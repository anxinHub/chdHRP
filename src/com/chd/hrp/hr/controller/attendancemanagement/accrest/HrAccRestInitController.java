package com.chd.hrp.hr.controller.attendancemanagement.accrest;

import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestInitService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 积休设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAccRestInitController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAccRestInitController.class);

	// 引入Service服务 
	@Resource(name = "hrAccRestInitService")
	private final HrAccRestInitService hrAccRestInitService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAccRestInitMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("attend_year", SessionManager.getAcctYear());
		map.put("dept_source", MyConfig.getSysPara("06103"));
		mode.addAttribute("attend_acctop", hrAccRestInitService.queryAttendAcctop(map));
		return "hrp/hr/attendancemanagement/accrest/accrestinit/accRestInitMainPage";
	}

	@RequestMapping(value = "/importRestInit", method = RequestMethod.POST)
	@ResponseBody
	public String importRestInit(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrAccRestInitService.importRestInit(mapVo);
		return reJson;
	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAccRestInit", method = RequestMethod.POST)
	@ResponseBody
	public String addAccRestInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hosEmpKindJson = hrAccRestInitService.addAccRestInit(mapVo);

			return hosEmpKindJson;

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
	}
	@RequestMapping(value = "/setAccJxMax", method = RequestMethod.POST)
	@ResponseBody
	public String setAccJxMax(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hosEmpKindJson = hrAccRestInitService.setAccJxMax(mapVo);

			return hosEmpKindJson;

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
	}
	


	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccRestInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccRestInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String stationTransef = hrAccRestInitService.queryAccRestInit(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
