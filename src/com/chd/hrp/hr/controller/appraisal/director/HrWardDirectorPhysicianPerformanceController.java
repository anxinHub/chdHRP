package com.chd.hrp.hr.controller.appraisal.director;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.hrp.hr.controller.organize.HosDutyLevelController;


/**
 * 病区首席医生考核
 * @author 18645098540
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/appraisal/director")
public class HrWardDirectorPhysicianPerformanceController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosDutyLevelController.class);
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrWardDirectorPhysicianPerformanceMainPage", method = RequestMethod.GET)
	public String hrWardDirectorPhysicianPerformanceMainPage(Model mode) throws Exception {

		return "hrp/hr/appraisal/director/hrWardDirectorPhysicianPerformanceMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrWardDirectorPhysicianPerformanceAddPage", method = RequestMethod.GET)
	public String addHosDutyLevelPage(Model mode) throws Exception {

		return "hrp/hr/appraisal/director/hrWardDirectorPhysicianPerformanceAdd";

	}
	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrWardDirectorPhysicianPerformanceUpdatePage", method = RequestMethod.GET)
	public String updateHosDutyLevelPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/hr/appraisal/director/hrWardDirectorPhysicianPerformanceUpdate";
	}
}
