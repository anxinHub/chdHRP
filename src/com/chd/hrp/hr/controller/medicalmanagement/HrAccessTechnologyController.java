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
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.service.medicalmanagement.HrAccessTechnologyService;

/**
 * 技术准入
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/technologicalmanagement")
public class HrAccessTechnologyController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAccessTechnologyController.class);

	// 引入Service服务
	@Resource(name = "hrAccessTechnologyService")
	private final HrAccessTechnologyService hrAccessTechnologyService = null;


	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAccessTechnologyMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/accesstechnology/accessTechnologyMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAccessTechnologyPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {	
	

		return "hrp/hr/medicalmanagement/accesstechnology/accessTechnologyAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccessTechnology(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("state", 0);

	
		String hrJson;
		try {
			hrJson =hrAccessTechnologyService.addAccessTechnology(mapVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);
        
	}
	

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAccessTechnologyPage", method = RequestMethod.GET)
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
		HrAccessTechnology hrAccessTechnology = new HrAccessTechnology();

		hrAccessTechnology = hrAccessTechnologyService.queryByCodeAccessTechnology(mapVo);
		SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
		mode.addAttribute("emp_id",hrAccessTechnology.getEmp_id() );
		mode.addAttribute("emp_name",hrAccessTechnology.getEmp_name() );
		mode.addAttribute("app_no",hrAccessTechnology.getApp_no());
		mode.addAttribute("oper_name",hrAccessTechnology.getOper_name() );
		mode.addAttribute("app_type",hrAccessTechnology.getApp_type());
		mode.addAttribute("state",hrAccessTechnology.getState());
		mode.addAttribute("app_date",a.format(hrAccessTechnology.getApp_date()) );
		return "hrp/hr/medicalmanagement/accesstechnology/accessTechnologyUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccessTechnology(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrAccessTechnologyService.updateAccessTechnology(mapVo);

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
	@RequestMapping(value = "/deleteAccessTechnology", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAccessTechnology(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);
		try {
		/*	for (HrAccessTechnology hrAccessTechnology : listVo) {
				hrAccessTechnology.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrAccessTechnology.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
	
			}*/
			return	 hrAccessTechnologyService.deleteAccessTechnology(listVo);

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
	@RequestMapping(value = "/queryAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccessTechnology(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrAccessTechnologyService.queryAccessTechnology(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据(左侧菜单) 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccessTechnologyTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAccessTechnologyTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrAccessTechnologyService.queryAccessTechnologyTree(mapVo);

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrAccessTechnology(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);

			for (HrAccessTechnology hrNursingPromotion : listVo) {
				hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				HrAccessTechnology accessTechnology = hrAccessTechnologyService.queryByCode(hrNursingPromotion);
				if (accessTechnology != null) {
					if (accessTechnology.getState() == 0) {
						hrNursingPromotion.setState(1);
						msg = hrAccessTechnologyService.confirmHrTechRec(hrNursingPromotion);
					} else {
						msg = "{\"error\":\"提交失败！请检查状态！\",\"state\":\"false\"}";
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
	@RequestMapping(value = "/reConfirmHrHrAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrHrAccessTechnology(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);

			for (HrAccessTechnology hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getState() == 1) {
					hrNursingPromotion.setState(0);
					hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrAccessTechnologyService.reConfirmHrHrTechRec(hrNursingPromotion);
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
	//审核
	@RequestMapping(value = "/auditHrAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHrAccessTechnology(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);

			for (HrAccessTechnology hrNursingPromotion : listVo) {
				hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				HrAccessTechnology accessTechnology = hrAccessTechnologyService.queryByCode(hrNursingPromotion);
				if (accessTechnology != null) {
					if (accessTechnology.getState() == 1) {
						hrNursingPromotion.setState(2);
						msg = hrAccessTechnologyService.auditHrTechRec(hrNursingPromotion);
					} else {
						msg = "{\"error\":\"审批失败！请检查状态！\",\"state\":\"false\"}";
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
	@RequestMapping(value = "/unauditHrHrAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditHrHrAccessTechnology(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);

			for (HrAccessTechnology hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getState() == 2) {
					hrNursingPromotion.setState(1);
					hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrAccessTechnologyService.unauditHrHrTechRec(hrNursingPromotion);
				} else {
					msg = "{\"error\":\"销审失败！请检查状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	//未通过
	@RequestMapping(value = "/dispassHrHrAccessTechnology", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dispassHrHrAccessTechnology(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrAccessTechnology> listVo = JSONArray.parseArray(paramVo, HrAccessTechnology.class);

			for (HrAccessTechnology hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getState() == 2) {
					hrNursingPromotion.setState(3);
					hrNursingPromotion.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					msg = hrAccessTechnologyService.dispassHrHrTechRec(hrNursingPromotion);
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
	@RequestMapping(value = "/confirmHrAccessTechnologyAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrAccessTechnologyAdd(@RequestParam Map<String, Object> mapVo, Model mode)
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
			HrAccessTechnology accessTechnology = hrAccessTechnologyService.queryByCodeAdd(mapVo);

			if (accessTechnology != null) {
				if (accessTechnology.getState() == 0) {
					accessTechnology.setState(1);
					msg = hrAccessTechnologyService.confirmHrTechRecAdd(accessTechnology);
				} else {
					msg = "{\"error\":\"提交失败！请检查状态！\",\"state\":\"false\"}";
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
	@RequestMapping(value = "/reConfirmHrHrAccessTechnologyAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrHrAccessTechnologyAdd(@RequestParam Map<String, Object> mapVo, Model mode)
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
			HrAccessTechnology accessTechnology = hrAccessTechnologyService.queryByCodeAdd(mapVo);

			if (accessTechnology != null && accessTechnology.getState() == 1) {
				accessTechnology.setState(0);
				msg = hrAccessTechnologyService.reConfirmHrHrTechRecAdd(accessTechnology);
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
