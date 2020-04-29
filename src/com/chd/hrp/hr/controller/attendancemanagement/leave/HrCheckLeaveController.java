package com.chd.hrp.hr.controller.attendancemanagement.leave;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrCheckLeaveService;

/**
 * 职工休假审核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrCheckLeaveController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCheckLeaveController.class);

	// 引入Service服务
	@Resource(name = "hrCheckLeaveService")
	private final HrCheckLeaveService hrCheckLeaveService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrApplyingLeavesAuditMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/leave/checkleave/checkLeaveAuditMainPage";
	}

	/**
	 *  审核
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditHrApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHrApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		String hrjson;
		try {
			hrjson = hrCheckLeaveService.auditHrApplyingLeaves(mapVo);
		} catch (Exception e) {
			e.printStackTrace();
			hrjson = e.getMessage();
		}
		
		return JSONObject.parseObject(hrjson);
	}

	/**
	 *  销审
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/unauditHrHrApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrHrApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String hrjson;
		try {
			hrjson = hrCheckLeaveService.unAuditHrApplyingLeaves(mapVo);
		} catch (Exception e) {
			hrjson = e.getMessage();
		}

		return JSONObject.parseObject(hrjson);
	}

	/**
	 *  审核未通过
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditUnHrApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditUnHrApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String hrjson;
		try {
			hrjson = hrCheckLeaveService.backHrApplyingLeaves(mapVo);
		} catch (Exception e) {
			hrjson = e.getMessage();
		}

		return JSONObject.parseObject(hrjson);
	}

	/**
	 *  核定
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkHrApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkHrApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String hrjson;
		try {
			hrjson = hrCheckLeaveService.checkHrApplyingLeaves(mapVo);
		} catch (Exception e) {
			hrjson = e.getMessage();
		}

		return JSONObject.parseObject(hrjson);
	}

	/**
	 * 取消核定
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uncheckHrHrApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uncheckHrHrApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hrjson;
		try {
			hrjson = hrCheckLeaveService.uncheckHrHrApplyingLeaves(mapVo);
		} catch (Exception e) {
			hrjson = e.getMessage();
		}

		return JSONObject.parseObject(hrjson);
	}
	
	/**
	 * 作废
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelFXJApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancelFXJApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hrjson;
		try {
			hrjson = hrCheckLeaveService.cancelFXJApplyingLeaves(mapVo);
		} catch (Exception e) {
			hrjson = e.getMessage();
		}

		return JSONObject.parseObject(hrjson);
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryApplyingLeavesAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryApplyingLeavesAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			   mapVo.put("user_id", SessionManager.getUserId());
			   mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String stationTransef = hrCheckLeaveService.queryCheckLeave(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

}
