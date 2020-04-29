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
import com.chd.hrp.hr.service.nursingtraining.HrEmpMonthNurseService;

/**
 * 
 * @ClassName: HrEmpMonthNurseController 
 * @Description: 护理绩效月考核记录
 * @author zn 
 * @date 2018年1月29日 下午4:46:22 
 * 
 *
 */
@Controller
@RequestMapping(value = "hrp/hr/nursingtraining/hrempmonthnurse")
public class HrEmpMonthNurseController extends BaseController {

	private static Logger logger = Logger.getLogger(HrEmpMonthNurseController.class);

	// 引入Service服务
	@Resource(name = "hrEmpMonthNurseService")
	private final HrEmpMonthNurseService hrEmpMonthNurseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpMonthNurseMainPage", method = RequestMethod.GET)
	public String hrEmpMonthNurseMainPage(Model mode) throws Exception {
//		logger.debug("-----------------------进入护理绩效月考核记录页面");
		return "hrp/hr/nursingtraining/hrempmonthnurse/hrEmpMonthNurseMain";

	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrEmpMonthNurse", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrEmpMonthNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		try {
			return hrEmpMonthNurseService.saveHrEmpMonthNurse(mapVo);
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
	@RequestMapping(value = "/deleteHrEmpMonthNurse", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEmpMonthNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			return hrEmpMonthNurseService.deleteHrEmpMonthNurse(mapVo);
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
	@RequestMapping(value = "/queryHrEmpMonthNurse", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpMonthNurse(@RequestParam Map<String, Object> mapVo)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrEmpMonthNurseService.queryHrEmpMonthNurse(getPage(mapVo));

	}
	/*
* 导入数据
*/
@RequestMapping(value = "/importMonthNurse", method = RequestMethod.POST)
@ResponseBody
public String importMonthNurse(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
String reJson = hrEmpMonthNurseService.importMonthNurse(mapVo);
return reJson;
}
}