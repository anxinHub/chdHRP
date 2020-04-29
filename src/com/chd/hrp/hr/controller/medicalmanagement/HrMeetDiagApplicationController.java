package com.chd.hrp.hr.controller.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagApplication;
import com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagApplicationService;

/**
 * 全院大会诊申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/consultation")
public class HrMeetDiagApplicationController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrMeetDiagApplicationController.class);

	// 引入Service服务
	@Resource(name = "hrMeetDiagApplicationService")
	private final HrMeetDiagApplicationService hrMeetDiagApplicationService = null;

	
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrMeetDiagApplicationMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/meetdiagapplication/meetDiagApplicationMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMeetDiagApplicationPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {	
		Map<String, Object> mapVo = new HashMap<String, Object>();


		return "hrp/hr/medicalmanagement/meetdiagapplication/meetDiagApplicationAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addMeetDiagApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMeetDiagApplication(@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("is_commit", 0);
		try {
			String hosEmpKindJson = hrMeetDiagApplicationService.addMeetDiagApplication(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMeetDiagApplicationPage", method = RequestMethod.GET)
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
		HrMeetDiagApplication hrMeetDiagApplication = new HrMeetDiagApplication();

		hrMeetDiagApplication = hrMeetDiagApplicationService.queryByCodeMeetDiagApplication(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
		mode.addAttribute("bill_no",hrMeetDiagApplication.getBill_no());
		mode.addAttribute("dept_id",hrMeetDiagApplication.getDept_id());
		mode.addAttribute("dept_name",hrMeetDiagApplication.getDept_name());
		mode.addAttribute("case_no",hrMeetDiagApplication.getCase_no());
		mode.addAttribute("patient",hrMeetDiagApplication.getPatient());
		mode.addAttribute("bed_no",hrMeetDiagApplication.getBed_no());
		mode.addAttribute("diagnose",hrMeetDiagApplication.getDiagnose());
		mode.addAttribute("reason",hrMeetDiagApplication.getReason());
		mode.addAttribute("app_date",a.format(hrMeetDiagApplication.getApp_date()) );
		return "hrp/hr/medicalmanagement/meetdiagapplication/meetDiagApplicationUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateMeetDiagApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMeetDiagApplication(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrMeetDiagApplicationService.updateMeetDiagApplication(mapVo);

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
	@RequestMapping(value = "/deleteMeetDiagApplication", method = RequestMethod.POST)
	@ResponseBody

	public String deleteMeetDiagApplication(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);
		try {
		/*	for (HrMeetDiagApplication hrMeetDiagApplication : listVo) {
				hrMeetDiagApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
				hrMeetDiagApplication.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
	
			}*/
			return	 hrMeetDiagApplicationService.deleteMeetDiagApplication(listVo);

		} catch (Exception e) {
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
	@RequestMapping(value = "/queryMeetDiagApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMeetDiagApplication(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrMeetDiagApplicationService.queryMeetDiagApplication(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	
	/**
	 * 
	 */
	/**
	 * @Description 查询历史数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHistroy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHistroy(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrMeetDiagApplicationService.queryHistroy(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询明细数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMeetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMeetDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrMeetDiagApplicationService.queryMeetDetail(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
/*	*//**
	 * @Description 查询数据(左侧菜单) 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/queryMeetDiagApplicationTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryMeetDiagApplicationTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrMeetDiagApplicationService.queryMeetDiagApplicationTree(mapVo);

	}*/
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmMeetDiag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMeetDiag(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);

			for (HrMeetDiagApplication hrMeetDiagApplication : listVo) {
				hrMeetDiagApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrMeetDiagApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				HrMeetDiagApplication MeetDiagApplication = hrMeetDiagApplicationService
						.queryByCode(hrMeetDiagApplication);
				if (MeetDiagApplication != null) {
					if (MeetDiagApplication.getIs_commit() == 0) {
						hrMeetDiagApplication.setIs_commit(1);
						msg = hrMeetDiagApplicationService.confirmMeetDiag(hrMeetDiagApplication);
					} else {
						msg = "{\"error\":\"提交失败！请勿重复提交！\",\"Is_commit\":\"false\"}";
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
	@RequestMapping(value = "/reConfirmHrMeetDiag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrMeetDiag(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);

			for (HrMeetDiagApplication hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getIs_commit() != 0) {
					hrNursingPromotion.setIs_commit(0);
					hrNursingPromotion.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrMeetDiagApplicationService.reConfirmMeetDiag(hrNursingPromotion);
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
	@RequestMapping(value = "/auditMeetDiag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMeetDiag(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);

			for (HrMeetDiagApplication hrNursingPromotion : listVo) {
				hrNursingPromotion.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrNursingPromotion.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				HrMeetDiagApplication MeetDiagApplication = hrMeetDiagApplicationService
						.queryByCode(hrNursingPromotion);
				if (MeetDiagApplication != null) {
					if (MeetDiagApplication.getIs_commit() == 1) {
						hrNursingPromotion.setIs_commit(2);
						msg = hrMeetDiagApplicationService.auditMeetDiag(hrNursingPromotion);
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
	@RequestMapping(value = "/unauditHrMeetDiag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrMeetDiag(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);

			for (HrMeetDiagApplication hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getIs_commit() == 2) {
					hrNursingPromotion.setIs_commit(1);
					hrNursingPromotion.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrMeetDiagApplicationService.unauditMeetDiag(hrNursingPromotion);
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
	@RequestMapping(value = "/dispassHrMeetDiag", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dispassHrMeetDiag(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrMeetDiagApplication> listVo = JSONArray.parseArray(paramVo, HrMeetDiagApplication.class);

			for (HrMeetDiagApplication hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getIs_commit() == 2) {
					hrNursingPromotion.setIs_commit(3);
					hrNursingPromotion.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrMeetDiagApplicationService.dispassMeetDiag(hrNursingPromotion);
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
	
//@RequestParam Map<String, Object> mapVo, Model mode
	/**
	 * @Description 添加页面提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmMeetDiagAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMeetDiagAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg = "";
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
			HrMeetDiagApplication MeetDiagApplication = hrMeetDiagApplicationService.queryByCodeAdd(mapVo);

			if (MeetDiagApplication != null) {
				if (MeetDiagApplication.getIs_commit() == 0) {
					MeetDiagApplication.setIs_commit(1);
					msg = hrMeetDiagApplicationService.confirmMeetDiagAdd(MeetDiagApplication);
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
	@RequestMapping(value = "/reConfirmMeetDiagAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmMeetDiagAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String msg = "";
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
			HrMeetDiagApplication MeetDiagApplication = hrMeetDiagApplicationService.queryByCodeAdd(mapVo);

			if (MeetDiagApplication != null) {
				if (MeetDiagApplication.getIs_commit() != 0) {
					MeetDiagApplication.setIs_commit(0);
					msg = hrMeetDiagApplicationService.reConfirmMeetDiagAdd(MeetDiagApplication);
				} else {
					msg = "{\"error\":\"撤回失败！请检查状态！\",\"state\":\"false\"}";
				}
			} else {
				msg = "{\"error\":\"撤回失败！请检查状态！\",\"state\":\"false\"}";
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	

}
