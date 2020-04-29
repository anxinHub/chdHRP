package com.chd.hrp.hr.controller.attendancemanagement.leave;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrTerminateleaveService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 职工销假申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrTerminateleaveController extends BaseController {


	private static Logger logger = Logger.getLogger(HrTerminateleaveController.class);

	// 引入Service服务
	@Resource(name = "hrTerminateleaveService")
	private final HrTerminateleaveService hrTerminateleaveService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTerminateleaveMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/leave/terminateleave/terminateLeaveMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTerminateleavePage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/attendancemanagement/leave/terminateleave/terminateLeaveAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTerminateleave", method = RequestMethod.POST)
	@ResponseBody
	public String  addTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			  
			String hosEmpKindJson = hrTerminateleaveService.addTerminateleave(mapVo);

			return hosEmpKindJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTerminateleavePage", method = RequestMethod.GET)
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
		Map hrdeptleave = new HashMap<String, Object>();

		hrdeptleave = hrTerminateleaveService.queryByCodeTerminateleave(mapVo);
		//mode.addAttribute(arg0, arg1);
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("attend_xjapply_code", hrdeptleave.get("attend_xjapply_code"));
		mode.addAttribute("attend_xjreg_date", hrdeptleave.get("attend_xjreg_date"));
		mode.addAttribute("emp_name", hrdeptleave.get("emp_name"));
		mode.addAttribute("emp_id", hrdeptleave.get("emp_id"));
		mode.addAttribute("dept_name", hrdeptleave.get("dept_name"));
		mode.addAttribute("duty_name", hrdeptleave.get("duty_name"));
		mode.addAttribute("birthday", hrdeptleave.get("birthday"));
		mode.addAttribute("sex_name", hrdeptleave.get("sex_name"));
		mode.addAttribute("worktime", hrdeptleave.get("worktime"));
		mode.addAttribute("workage", hrdeptleave.get("workage"));
		mode.addAttribute("birthplace", hrdeptleave.get("birthplace"));
		mode.addAttribute("photo", hrdeptleave.get("photo"));
		mode.addAttribute("attend_xjyear", hrdeptleave.get("attend_year"));
		mode.addAttribute("attend_name", hrdeptleave.get("attend_name"));
		mode.addAttribute("attend_code", hrdeptleave.get("attend_code"));
		mode.addAttribute("attend_ed", hrdeptleave.get("attend_ed"));
		mode.addAttribute("attend_ed_is", hrdeptleave.get("attend_ed_is"));
		mode.addAttribute("xjdays",hrdeptleave.get("xjdays"));
		mode.addAttribute("residue_days", hrdeptleave.get("residue_days"));
		mode.addAttribute("attend_xjbegdate", hrdeptleave.get("attend_xjbegdate"));
		mode.addAttribute("attend_xjenddate",hrdeptleave.get("attend_xjenddate"));
		mode.addAttribute("attend_xjdays", hrdeptleave.get("attend_xjdays"));
		mode.addAttribute("attend_xj_reason", hrdeptleave.get("attend_xj_reason"));
		mode.addAttribute("attend_xjbegdate_ampm",hrdeptleave.get("attend_xjbegdate_ampm"));
		mode.addAttribute("attend_xjenddate_ampm", hrdeptleave.get("attend_xjenddate_ampm"));
		mode.addAttribute("attend_xjbegdate_ampm_name",hrdeptleave.get("attend_xjbegdate_ampm_name"));
		mode.addAttribute("attend_xjenddate_ampm_name", hrdeptleave.get("attend_xjenddate_ampm_name"));
		mode.addAttribute("attend_xxjapply_code", hrdeptleave.get("attend_xxjapply_code"));
		mode.addAttribute("attend_xxj_backtime", hrdeptleave.get("attend_xxj_backtime"));
		mode.addAttribute("attend_xxjbegdate_ampm", hrdeptleave.get("attend_xxjbegdate_ampm"));
		mode.addAttribute("attend_xxj_note", hrdeptleave.get("attend_xxj_note"));
		mode.addAttribute("attend_xjreg_date", hrdeptleave.get("attend_xjreg_date"));
		mode.addAttribute("attend_xxjdays", hrdeptleave.get("attend_xxjdays"));
		mode.addAttribute("attend_xjstate", hrdeptleave.get("attend_xjstate"));
		
		  if(mapVo.get("xj_type").toString()!=null && !mapVo.get("xj_type").toString().equals("undefined")){
	        	 mode.addAttribute("xj_type", mapVo.get("xj_type").toString());
	         }
		return "hrp/hr/attendancemanagement/leave/terminateleave/terminateLeaveUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTerminateleave", method = RequestMethod.POST)
	@ResponseBody
	public String  updateTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
try{
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		

		String hosEmpKindJson = hrTerminateleaveService.updateTerminateleave(mapVo);

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
	@RequestMapping(value = "/deleteTerminateleave", method = RequestMethod.POST)
	@ResponseBody

	public String deleteTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		try {
			return hrTerminateleaveService.deleteTerminateleave(mapVo);
		}  catch (Exception e) {
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
	@RequestMapping(value = "/queryTerminateleave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String stationTransef = hrTerminateleaveService.queryTerminateleave(getPage(mapVo));

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
	@RequestMapping(value = "/confirmTerminateleave", method = RequestMethod.POST)
	@ResponseBody
	public String  confirmTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrTerminateleaveService.confirmTerminateleave(mapVo);
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
	@RequestMapping(value = "/reConfirmTerminateleave", method = RequestMethod.POST)
	@ResponseBody
	public String  reConfirmTerminateleave(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = hrTerminateleaveService.reConfirmTerminateleave(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * 查询休假申请编号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
		@RequestMapping(value = "/queryApplyCode", method = RequestMethod.POST)
		@ResponseBody
	public String queryApplyCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String json = hrTerminateleaveService.queryApplyCode(mapVo);
			return json;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * 查询人员详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryApplying", method = RequestMethod.POST)
	@ResponseBody
	public String queryApplying(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hrStationTransfer = hrTerminateleaveService.queryApplying(mapVo);
		return hrStationTransfer;

	}
}
