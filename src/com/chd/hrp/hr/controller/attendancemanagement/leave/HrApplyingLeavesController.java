package com.chd.hrp.hr.controller.attendancemanagement.leave;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.s;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrApplyingLeavesService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 职工休假申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrApplyingLeavesController extends BaseController {

	private static Logger logger = Logger.getLogger(HrApplyingLeavesController.class);

	// 引入Service服务
	@Resource(name = "hrApplyingLeavesService")
	private final HrApplyingLeavesService hrApplyingLeavesService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrApplyingLeavesMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/leave/applyingleaves/applyingLeavesMainPage";
	}

	/**
	 * 添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addApplyingLeavesPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {
		mode.addAttribute("attend_xj_add", 0);
		return "hrp/hr/attendancemanagement/leave/applyingleaves/applyingLeavesAdd";
	}

	/**
	 * 补登记添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mendApp2lyingLeavesPage", method = RequestMethod.GET)
	public String mendApplyingLeavesPage(Model mode) throws Exception {
		mode.addAttribute("attend_xj_add", 1);
		return "hrp/hr/attendancemanagement/leave/applyingleaves/applyingLeavesAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String addApplyingLeaves(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrJson;
		try {
			hrJson = hrApplyingLeavesService.addApplyingLeaves(mapVo);
			return hrJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * 修改页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateApplyingLeavesPage", method = RequestMethod.GET)
	public String updateHrDeptleavePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		HrApplyingLeaves hrdeptleave = new HrApplyingLeaves();
		hrdeptleave = hrApplyingLeavesService.queryByCodeApplyingLeaves(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("attend_xjapply_code",hrdeptleave.getAttend_xjapply_code());
		mode.addAttribute("attend_xjreg_date",hrdeptleave.getAttend_xjreg_date());
		mode.addAttribute("emp_id", hrdeptleave.getEmp_id());
		mode.addAttribute("emp_name", hrdeptleave.getEmp_name());
		mode.addAttribute("dept_name", hrdeptleave.getDept_name());
		mode.addAttribute("duty_name", hrdeptleave.getDuty_name());
		mode.addAttribute("birthday", hrdeptleave.getBirthday());
		mode.addAttribute("sex_name", hrdeptleave.getSex_name());
		mode.addAttribute("worktime", hrdeptleave.getWorktime());
		mode.addAttribute("workage", hrdeptleave.getWorkage());
		mode.addAttribute("birthplace", hrdeptleave.getBirthplace());
		mode.addAttribute("photo", hrdeptleave.getPhoto());
		mode.addAttribute("attend_year", hrdeptleave.getAttend_year());
		mode.addAttribute("attend_code", hrdeptleave.getAttend_code());
		mode.addAttribute("attend_ed", hrdeptleave.getAttend_ed());
		mode.addAttribute("attend_ed_is", hrdeptleave.getAttend_ed_is());
		mode.addAttribute("xjdays", hrdeptleave.getXjdays());
		mode.addAttribute("residue_days", Double.parseDouble(hrdeptleave.getResidue_days()));
		mode.addAttribute("attend_xjbegdate",a.format(hrdeptleave.getAttend_xjbegdate()));
		mode.addAttribute("attend_xjenddate",a.format(hrdeptleave.getAttend_xjenddate()));
		mode.addAttribute("attend_xjdays", hrdeptleave.getAttend_xjdays());
		mode.addAttribute("attend_xj_reason", hrdeptleave.getAttend_xj_reason());
		mode.addAttribute("attend_xjbegdate_ampm",hrdeptleave.getAttend_xjbegdate_ampm());
		mode.addAttribute("attend_xjenddate_ampm",hrdeptleave.getAttend_xjenddate_ampm());
		mode.addAttribute("attend_xjstate",hrdeptleave.getAttend_xjstate());
        
		if(mapVo.get("xj_type").toString()!=null && !mapVo.get("xj_type").toString().equals("undefined")){
        	mode.addAttribute("xj_type", mapVo.get("xj_type").toString());
        }
		return "hrp/hr/attendancemanagement/leave/applyingleaves/applyingLeavesUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String updateApplyingLeaves(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrApplyingLeavesService.updateApplyingLeaves(mapVo);

			return hosEmpKindJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String deleteApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrApplyingLeavesService.deleteApplyingLeaves(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * 作废
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelFApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String cancelFApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrApplyingLeavesService.cancelFApplyingLeaves(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	@RequestMapping(value = "/queryApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode)
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
             mapVo.put("user_id", SessionManager.getUserId());
             mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String stationTransef = hrApplyingLeavesService.queryApplyingLeaves(getPage(mapVo));
			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String confirmApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrApplyingLeavesService.confirmApplyingLeaves(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmApplyingLeaves", method = RequestMethod.POST)
	@ResponseBody
	public String reConfirmApplyingLeaves(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrApplyingLeavesService.reConfirmApplyingLeaves(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * 查询休假额度
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendSum", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendSum(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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

			String hrStationTransfer = hrApplyingLeavesService.queryAttendSum(mapVo);
			return hrStationTransfer;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 核算休假天数
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/countXJDays", method = RequestMethod.POST)
	@ResponseBody
	public String countXJDays(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("year", SessionManager.getAcctYear());
			String days = hrApplyingLeavesService.countXJDays(mapVo);
			
			return  days;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	

	/**
	 * @Description 查询休假历史
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHistroy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHistroy(
			@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrApplyingLeavesService.queryHistroy(getPage(mapVo));
		return JSONObject.parseObject(stationTransef);
	}
}
