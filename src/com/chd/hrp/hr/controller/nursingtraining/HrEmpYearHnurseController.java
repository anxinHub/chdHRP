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
import com.chd.hrp.hr.service.nursingtraining.HrEmpYearHnurseService;

/**
 * 
 * @ClassName: HrEmpYearHnurseController 
 * @Description: 护理长绩效年考核记录
 * @author zn 
 * @date 2018年1月29日 下午4:45:32 
 * 
 *
 */
@Controller
@RequestMapping(value = "hrp/hr/nursingtraining/hrempyearhnurse")
public class HrEmpYearHnurseController extends BaseController {

	private static Logger logger = Logger.getLogger(HrEmpYearHnurseController.class);

	// 引入Service服务
	@Resource(name = "hrEmpYearHnurseService")
	private final HrEmpYearHnurseService hrEmpYearHnurseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpYearHnurseMainPage", method = RequestMethod.GET)
	public String hrEmpYearHnurseMainPage(Model mode) throws Exception {
		logger.debug("-----------------------进入护理长绩效年考核记录页面");
		return "hrp/hr/nursingtraining/hrempyearhnurse/hrEmpYearHnurseMain";

	}


	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrEmpYearHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrEmpYearHnurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		try {
			return hrEmpYearHnurseService.saveHrEmpYearHnurse(mapVo);
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
	@RequestMapping(value = "/deleteHrEmpYearHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEmpYearHnurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		try {
			return hrEmpYearHnurseService.deleteHrEmpYearHnurse(mapVo);
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
	@RequestMapping(value = "/queryHrEmpYearHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpYearHnurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrEmpYearHnurseService.queryHrEmpYearHnurse(getPage(mapVo));

	}
	/*
* 导入数据
*/
@RequestMapping(value = "/importYearHNurse", method = RequestMethod.POST)
@ResponseBody
public String importYearHNurse(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
String reJson = hrEmpYearHnurseService.importYearHNurse(mapVo);
return reJson;
}
}