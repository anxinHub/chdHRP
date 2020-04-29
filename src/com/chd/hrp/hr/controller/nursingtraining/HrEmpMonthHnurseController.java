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
import com.chd.hrp.hr.service.nursingtraining.HrEmpMonthHnurseService;

/**
 * 
 * @ClassName: HrEmpMonthHnurseController 
 * @Description: 护理长绩效季度考核记录
 * @author zn 
 * @date 2018年1月29日 下午4:45:13 
 * 
 *
 */
@Controller
@RequestMapping(value = "hrp/hr/nursingtraining/hrempmonthhnurse")
public class HrEmpMonthHnurseController extends BaseController {

	private static Logger logger = Logger.getLogger(HrEmpMonthHnurseController.class);

	// 引入Service服务
	@Resource(name = "hrEmpMonthHnurseService")
	private final HrEmpMonthHnurseService hrEmpMonthHnurseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpMonthHnurseMainPage", method = RequestMethod.GET)
	public String hrEmpMonthHnurseMainPage(Model mode) throws Exception {
		logger.debug("-----------------------进入护理长绩效季度考核记录页面");
		return "hrp/hr/nursingtraining/hrempmonthhnurse/hrEmpMonthHnurseMain";

	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrEmpMonthHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrEmpMonthHnurse(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			return hrEmpMonthHnurseService.saveHrEmpMonthHnurse(mapVo);
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
	@RequestMapping(value = "/deleteHrEmpMonthHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEmpMonthHnurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		try {
			return hrEmpMonthHnurseService.deleteHrEmpMonthHnurse(mapVo);
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
	@RequestMapping(value = "/queryHrEmpMonthHnurse", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpMonthHnurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrEmpMonthHnurseService.queryHrEmpMonthHnurse(getPage(mapVo));

	}
	/*
* 导入数据
*/
@RequestMapping(value = "/importMonthHNurse", method = RequestMethod.POST)
@ResponseBody
public String importMonthHNurse(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
String reJson = hrEmpMonthHnurseService.importMonthHNurse(mapVo);
return reJson;
}
}