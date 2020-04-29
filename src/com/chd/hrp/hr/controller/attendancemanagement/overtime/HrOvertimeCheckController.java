package com.chd.hrp.hr.controller.attendancemanagement.overtime;

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
import com.chd.hrp.hr.service.attendancemanagement.overtime.HrOvertimeCheckService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 加班审核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrOvertimeCheckController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrOvertimeCheckController.class);

	// 引入Service服务
	@Resource(name = "hrOvertimeCheckService")
	private final HrOvertimeCheckService hrOvertimeCheckService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrOvertimeCheckMainPage", method = RequestMethod.GET)
	public String hrOvertimeCheckMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/overtime/overtimecheck/overtimeCheckMainPage";
	}
	
	/**
	 * 审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditOvertimeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditOvertimeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String msg = null;
		try {
			msg=hrOvertimeCheckService.auditOvertimeCheck(mapVo);
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error(e.getMessage(), e);
		}	
		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 退回
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditOvertimeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditOvertimeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String msg = null;
		try {
			msg=hrOvertimeCheckService.reAuditOvertimeCheck(mapVo);
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 销审
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unAuditOvertimeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditOvertimeCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String msg="";
		try {
			msg=hrOvertimeCheckService.unAuditOvertimeCheck(mapVo);
		} catch (Exception e) {
			msg = e.getMessage();
			logger.error(e.getMessage(), e);
		}
		return JSONObject.parseObject(msg);
	}
	
	/**
	* @Description 查询数据
	* @param mapVo
	* @param mode
	* @return Map<String, Object>
	* @throws Exception
	*/
	@RequestMapping(value = "/queryOvertimeCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOvertimeCheck(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String stationTransef = hrOvertimeCheckService.queryCheckLeave(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
}
