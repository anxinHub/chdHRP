package com.chd.hrp.hr.controller.medicalmanagement;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdApplication;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrFurstdApplicationService;

/**
 * 进修申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/furstd")
public class HrFurstdApplicationController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFurstdApplicationController.class);

	// 引入Service服务
	@Resource(name = "hrFurstdApplicationService")
	private final HrFurstdApplicationService hrFurstdApplicationService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFurstdApplicationMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/furstdapplication/furstdApplicationMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFurstdApplicationPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {	

		return "hrp/hr/medicalmanagement/furstdapplication/furstdApplicationAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFurstdApplication(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String hosEmpKindJson = hrFurstdApplicationService.addFurstdApplication(mapVo);

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
	@RequestMapping(value = "/updateFurstdApplicationPage", method = RequestMethod.GET)
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
		HrFurstdApplication hrFurstdApplication = new HrFurstdApplication();

		hrFurstdApplication = hrFurstdApplicationService.queryByCodeFurstdApplication(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
		mode.addAttribute("emp_id",hrFurstdApplication.getEmp_id() );
		mode.addAttribute("emp_name",hrFurstdApplication.getEmp_name() );
		mode.addAttribute("app_no",hrFurstdApplication.getApp_no());
		mode.addAttribute("app_date",a.format(hrFurstdApplication.getApp_date()) );
		mode.addAttribute("profession",hrFurstdApplication.getProfession());
		mode.addAttribute("sex_code",hrFurstdApplication.getSex_code());
		mode.addAttribute("age",hrFurstdApplication.getAge());
		mode.addAttribute("hostime",hrFurstdApplication.getHostime());
		mode.addAttribute("workage",hrFurstdApplication.getWorkage() );
		mode.addAttribute("political_code",hrFurstdApplication.getPolitical_code() );
		mode.addAttribute("graduation_school",hrFurstdApplication.getGraduation_school() );
		mode.addAttribute("english_level",hrFurstdApplication.getEnglish_level());
		if(hrFurstdApplication.getBeg_date()!=null  ){
			mode.addAttribute("beg_date",a.format(hrFurstdApplication.getBeg_date()));
		}
		mode.addAttribute("duration",hrFurstdApplication.getDuration() );
		mode.addAttribute("sec_profession",hrFurstdApplication.getSec_profession());
		mode.addAttribute("furstd_hos",hrFurstdApplication.getFurstd_hos() );
		mode.addAttribute("hostel_charge",hrFurstdApplication.getHostel_charge());
		mode.addAttribute("hostel",hrFurstdApplication.getHostel());
		mode.addAttribute("goal",hrFurstdApplication.getGoal());
		mode.addAttribute("plan1",hrFurstdApplication.getPlan1());
		mode.addAttribute("plan3",hrFurstdApplication.getPlan3());
		mode.addAttribute("plan6",hrFurstdApplication.getPlan6());
		mode.addAttribute("is_commit",hrFurstdApplication.getIs_commit());
		return "hrp/hr/medicalmanagement/furstdapplication/furstdApplicationUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFurstdApplication(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrFurstdApplicationService.updateFurstdApplication(mapVo);

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
	@RequestMapping(value = "/deleteFurstdApplication", method = RequestMethod.POST)
	@ResponseBody

	public String deleteFurstdApplication(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);
		try {
	/*		for (HrFurstdApplication hrFurstdApplication : listVo) {
				if (hrFurstdApplication.getIs_commit()==0) {
					hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
					hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
		
				}else{
					return "{\"error\":\"删除失败！请选择新建状态删除！\",\"state\":\"false\"}";
				}
			}*/

			return	 hrFurstdApplicationService.deleteFurstdApplication(listVo);

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
	@RequestMapping(value = "/queryFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFurstdApplication(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrFurstdApplicationService.queryFurstdApplication(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrFurstdApplication(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);

			for (HrFurstdApplication hrFurstdApplication : listVo) {
				hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				hrFurstdApplication.setAudit_emp(SessionManager.getUserName());
				hrFurstdApplication.setAudit_date(new Date());
				if (hrFurstdApplication.getIs_commit() == 0) {
					hrFurstdApplication.setIs_commit(1);
					msg = hrFurstdApplicationService.confirmHrFurstdApplication(hrFurstdApplication);
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
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrFurstdApplication(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);

			for (HrFurstdApplication hrFurstdApplication : listVo) {
				if (hrFurstdApplication.getIs_commit() == 1) {
					hrFurstdApplication.setIs_commit(0);
					hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrFurstdApplication.setAudit_emp(SessionManager.getUserName());
					hrFurstdApplication.setAudit_date(new Date());
					msg = hrFurstdApplicationService.reConfirmHrHrFurstdApplication(hrFurstdApplication);
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
	@RequestMapping(value = "/auditHrFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHrFurstdApplication(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);

			for (HrFurstdApplication hrFurstdApplication : listVo) {
				hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				hrFurstdApplication.setAudit_emp(SessionManager.getUserName());
				hrFurstdApplication.setAudit_date(new Date());
				if (hrFurstdApplication.getIs_commit() != null) {
					if (hrFurstdApplication.getIs_commit() == 1) {
						hrFurstdApplication.setIs_commit(2);
						msg = hrFurstdApplicationService.auditHrFurstdApplication(hrFurstdApplication);
					} else if (hrFurstdApplication.getIs_commit() == 0) {
						msg = "{\"error\":\"审批失败！请勿先提交！\",\"state\":\"false\"}";
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
	@RequestMapping(value = "/unauditHrHrFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrHrFurstdApplication(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);

			for (HrFurstdApplication hrFurstdApplication : listVo) {
				if (hrFurstdApplication.getIs_commit() == 2) {
					hrFurstdApplication.setIs_commit(1);
					hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrFurstdApplication.setAudit_emp(SessionManager.getUserName());
					hrFurstdApplication.setAudit_date(new Date());
					msg = hrFurstdApplicationService.unauditHrHrFurstdApplication(hrFurstdApplication);
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
	@RequestMapping(value = "/dispassHrHrFurstdApplication", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dispassHrHrFurstdApplication(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdApplication> listVo = JSONArray.parseArray(paramVo, HrFurstdApplication.class);

			for (HrFurstdApplication hrFurstdApplication : listVo) {
				if (hrFurstdApplication.getIs_commit() == 2) {
					hrFurstdApplication.setIs_commit(3);
					hrFurstdApplication.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstdApplication.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrFurstdApplication.setAudit_emp(SessionManager.getUserName());
					hrFurstdApplication.setAudit_date(new Date());
					msg = hrFurstdApplicationService.dispassHrHrFurstdApplication(hrFurstdApplication);
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
	 * 查询人员详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosEmpDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrdeptnursing = hrFurstdApplicationService.queryHosEmp(mapVo);

		return hrdeptnursing;

	}
	

}
