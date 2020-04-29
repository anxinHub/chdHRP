/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.controller.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetMdt;
import com.chd.hrp.hr.service.medicalmanagement.HrMDTTeamMeetingService;

/**
 * 
 * @Description:
 * MDT团队会议记录
 * @Table:
 * HR_MEET_MDT MDT团队会议
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "hrp/hr/healthadministration/MDT")
public class HrMDTTeamMeetingController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrMDTTeamMeetingController.class);
	// 引入Service服务
	@Resource(name = "hrMDTTeamMeetingService")
	private final HrMDTTeamMeetingService hrMDTTeamMeetingService = null;
	
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrMDTTeamMeetingMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/mdt/hrMDTTeamMeetingMainPage";
	}
	
	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrMDTTeamMeetingpage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/mdt/hrMDTTeamMeetingAdd";
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addhrMDTTeamMeeting(@RequestParam Map<String, Object> mapVo, Model mode)
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

			mapVo.put("is_commit", 0);

			String hosEmpKindJson = hrMDTTeamMeetingService.addhrMDTTeamMeeting(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrMDTTeamMeeting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrMDTTeamMeetingService.queryHrMDTTeamMeeting(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrMDTTeamMeetingPage", method = RequestMethod.GET)
	public String updateHrDepttechnologicalmanagementPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrMeetMdt hrMeetMdt = new HrMeetMdt();

		hrMeetMdt = hrMDTTeamMeetingService.queryByCodeHrMDTTeamMeeting(mapVo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("rc_no", hrMeetMdt.getRc_no());
		mode.addAttribute("rc_date", sdf.format(hrMeetMdt.getRc_date()));
		mode.addAttribute("team_name", hrMeetMdt.getTeam_name());
		mode.addAttribute("title", hrMeetMdt.getTitle());
		mode.addAttribute("recorder", hrMeetMdt.getRecorder());
		mode.addAttribute("compere", hrMeetMdt.getCompere());
		Map<String, Object> emp_map=hrMDTTeamMeetingService.queryEmp(mapVo);
		mode.addAttribute("emp_id", emp_map.get("EMP_ID"));
		mode.addAttribute("emp_name", emp_map.get("EMP_NAME"));
		mode.addAttribute("content", hrMeetMdt.getContent());
		mode.addAttribute("site", hrMeetMdt.getSite());
		mode.addAttribute("is_commit", hrMeetMdt.getIs_commit());

		return "hrp/hr/medicalmanagement/mdt/hrMDTTeamMeetingUpdate";

	}
	
	/**
	 * 修改页面MDT团队会议明细回显
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMeetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMeetDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrMDTTeamMeetingService.queryMeetDetail(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrMDTTeamMeeting(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {
			String hosEmpKindJson = hrMDTTeamMeetingService.updateHrMDTTeamMeeting(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody

	public String deleteHrMDTTeamMeeting(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);
		try {
			/*for (HrMeetMdt hrMeetMdt : listVo) {
				hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
			}*/
			return hrMDTTeamMeetingService.deleteHrMDTTeamMeeting(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	//提交
	@RequestMapping(value = "/confirmHrMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrMDTTeamMeeting(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);
		try {
			for (HrMeetMdt hrMeetMdt : listVo) {
				hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				HrMeetMdt hmt = hrMDTTeamMeetingService.queryByCodeDate(hrMeetMdt);
				if (hmt != null) {
					if (hmt.getIs_commit() == 0) {
						hrMeetMdt.setIs_commit(1);
						msg = hrMDTTeamMeetingService.confirmHrMDTTeamMeeting(hrMeetMdt);
					} else {
						msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					}
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrMDTTeamMeeting(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg = "";
		List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);
		try {
			for (HrMeetMdt hrMeetMdt : listVo) {
				if (hrMeetMdt.getIs_commit() == 1) {
					hrMeetMdt.setIs_commit(0);
					hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrMDTTeamMeetingService.reConfirmHrMDTTeamMeeting(hrMeetMdt);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	//审核
	@RequestMapping(value = "/auditHrMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHrMDTTeamMeeting(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);

			for (HrMeetMdt hrMeetMdt : listVo) {
				hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				HrMeetMdt hmt = hrMDTTeamMeetingService.queryByCodeDate(hrMeetMdt);
				if (hmt != null) {
					if (hmt.getIs_commit() == 1) {
						hrMeetMdt.setIs_commit(2);
						msg = hrMDTTeamMeetingService.auditHrMDTTeamMeeting(hrMeetMdt);
					} else {
						msg = "{\"error\":\"审批失败！请勿重复审批！\",\"state\":\"false\"}";
					}
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	//销审
	@RequestMapping(value = "/unauditHrMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrMDTTeamMeeting(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);

			for (HrMeetMdt hrMeetMdt : listVo) {
				if (hrMeetMdt.getIs_commit() == 2) {
					hrMeetMdt.setIs_commit(1);
					hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrMDTTeamMeetingService.unauditHrMDTTeamMeeting(hrMeetMdt);
				} else {
					msg = "{\"error\":\"销审失败！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	//未通过
	@RequestMapping(value = "/dispassHrMDTTeamMeeting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dispassHrMDTTeamMeeting(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetMdt> listVo = JSONArray.parseArray(paramVo, HrMeetMdt.class);

			for (HrMeetMdt hrMeetMdt : listVo) {
				if (hrMeetMdt.getIs_commit() == 2) {
					hrMeetMdt.setIs_commit(3);
					hrMeetMdt.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrMeetMdt.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrMDTTeamMeetingService.dispassHrMDTTeamMeeting(hrMeetMdt);
				} else {
					msg = "{\"error\":\"未通过失败！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * @Description 添加页面提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrMDTTeamMeetingAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrMDTTeamMeetingAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		try {
			HrMeetMdt hrMeetMdt = hrMDTTeamMeetingService.queryByCodeAdd(mapVo);

			if (hrMeetMdt != null) {
				if (hrMeetMdt.getIs_commit() == 0) {
					hrMeetMdt.setIs_commit(1);
					msg = hrMDTTeamMeetingService.confirmHrMDTTeamMeetingAdd(hrMeetMdt);
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * @Description 添加页面取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrMDTTeamMeetingAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrMDTTeamMeetingAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String msg = "";

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			HrMeetMdt hrMeetMdt = hrMDTTeamMeetingService.queryByCodeAdd(mapVo);

			if (hrMeetMdt != null) {
				if (hrMeetMdt.getIs_commit() != 0) {
					hrMeetMdt.setIs_commit(0);
					msg = hrMDTTeamMeetingService.reConfirmHrMDTTeamMeetingAdd(hrMeetMdt);
				} else {
					msg = "{\"error\":\"撤回失败！请检查状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
}
