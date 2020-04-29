/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.nursingtraining;

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

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.nursingtraining.HrEmpYearNurseService;

/**
 * 
 * @Description: 护理绩效年考核记录
 * @Table: HR_EMP_YEAR_NURSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "hrp/hr/nursingtraining/hrempyearnurse")
public class HrEmpYearNurseController extends BaseController {

	private static Logger logger = Logger.getLogger(HrEmpYearNurseController.class);

	// 引入Service服务
	@Resource(name = "hrEmpYearNurseService")
	private final HrEmpYearNurseService hrEmpYearNurseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpYearNurseMainPage", method = RequestMethod.GET)
	public String hrEmpYearNurseMainPage(Model mode) throws Exception {
		logger.debug("-----------------------进入护理绩效年考核记录页面");
		return "hrp/hr/nursingtraining/hrempyearnurse/hrEmpYearNurseMain";

	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrEmpYearNurse", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrEmpYearNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		try {
			return hrEmpYearNurseService.saveHrEmpYearNurse(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":" + e.getMessage() + "}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrEmpYearNurse", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEmpYearNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		try {
			return hrEmpYearNurseService.deleteHrEmpYearNurse(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":"+e.getMessage()+"}";
		}
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEmpYearNurse", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpYearNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrEmpYearNurseService.queryHrEmpYearNurse(getPage(mapVo));
	}
	/*
* 导入数据
*/
@RequestMapping(value = "/importYearNurse", method = RequestMethod.POST)
@ResponseBody
public String importYearNurse(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
String reJson = hrEmpYearNurseService.importYearNurse(mapVo);
return reJson;
}
}