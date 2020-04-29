package com.chd.hrp.hr.controller.nursing;

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
import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrNursingPromotionService;

/**
 * 护理晋级申请
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrNursingPromotionController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrNursingPromotionController.class);

	// 引入Service服务
	@Resource(name = "hrNursingPromotionService")
	private final HrNursingPromotionService hrNursingPromotionService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrNursingPromotionMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/nursingpromotion/nursingPromotionMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNursingPromotionPage", method = RequestMethod.GET)
	public String stationAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("year", mapVo.get("year"));
		return "hrp/hr/nursing/nursingpromotion/nursingPromotionAdd";

	}

	/**
	 * 查询人员详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpDetail", method = RequestMethod.POST)
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

		String hrdeptnursing = hrNursingPromotionService.queryHosEmpDetail(mapVo);

		return hrdeptnursing;

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNursingPromotion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addNursingPromotion(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("dept_code") != null) {
				String dept_id_no = mapVo.get("dept_code").toString();

				mapVo.put("dept_no", dept_id_no.split("@")[0]);
				mapVo.put("dept_id", dept_id_no.split("@")[1]);
			}
			mapVo.put("state", 0);


		

		
		String hrJson;
		try {
			hrJson = hrNursingPromotionService.addNursingPromotion(mapVo);
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
	@RequestMapping(value = "/updateNursingPromotionPage", method = RequestMethod.GET)
	public String updateHrDeptnursingPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrNursingPromotion hrPromotionApply = new HrNursingPromotion();
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		hrPromotionApply = hrNursingPromotionService.queryByCodeNursingPromotion(mapVo);
		mode.addAttribute("group_id", hrPromotionApply.getGroup_id());
		mode.addAttribute("hos_id", hrPromotionApply.getHos_id());
		mode.addAttribute("year", hrPromotionApply.getYear());
		mode.addAttribute("emp_id", hrPromotionApply.getEmp_id());
		mode.addAttribute("emp_name", hrPromotionApply.getEmp_name());
		mode.addAttribute("dept_id", hrPromotionApply.getDept_id());
		mode.addAttribute("dept_no", hrPromotionApply.getDept_no());
		mode.addAttribute("dept_name", hrPromotionApply.getDept_name());
		mode.addAttribute("degree_code", hrPromotionApply.getDegree_code());
		mode.addAttribute("degree_name", hrPromotionApply.getDegree_name());
		if (hrPromotionApply.getGraduation_date() == null) {
			mode.addAttribute("graduation_date", hrPromotionApply.getGraduation_date());
		} else {
			mode.addAttribute("graduation_date", a.format(hrPromotionApply.getGraduation_date()));
		}
		mode.addAttribute("max_degree_code", hrPromotionApply.getMax_degree_code());
		mode.addAttribute("max_degree_name", hrPromotionApply.getMax_degree_name());
		if (hrPromotionApply.getMax_graduation_date() == null) {
			mode.addAttribute("max_graduation_date", hrPromotionApply.getMax_graduation_date());
		} else {
			mode.addAttribute("max_graduation_date", a.format(hrPromotionApply.getMax_graduation_date()));
		}
		mode.addAttribute("birthday", hrPromotionApply.getBirthday());
		mode.addAttribute("cur_level_code", hrPromotionApply.getCur_level_code());
		mode.addAttribute("cur_level_name", hrPromotionApply.getCur_level_name());
		if (hrPromotionApply.getCur_get_date() == null) {
			mode.addAttribute("cur_get_date", hrPromotionApply.getCur_get_date());
		} else {
			mode.addAttribute("cur_get_date", a.format(hrPromotionApply.getCur_get_date()));
		}
		mode.addAttribute("cur_title_code", hrPromotionApply.getCur_title_code());
		mode.addAttribute("cur_title_name", hrPromotionApply.getCur_title_name());
		if (hrPromotionApply.getCur_title_date() == null) {
			mode.addAttribute("cur_title_date", hrPromotionApply.getCur_title_date());
		} else {
			mode.addAttribute("cur_title_date", a.format(hrPromotionApply.getCur_title_date()));
		}
		mode.addAttribute("apply_level_code", hrPromotionApply.getApply_level_code());
		mode.addAttribute("apply_level_name", hrPromotionApply.getApply_level_name());
		mode.addAttribute("worktime", hrPromotionApply.getWorktime());
		if (hrPromotionApply.getApply_date() == null) {
			mode.addAttribute("apply_date", hrPromotionApply.getApply_date());
		} else {
			mode.addAttribute("apply_date", a.format(hrPromotionApply.getApply_date()));
		}
		if (hrPromotionApply.getAudit_date() == null) {
			mode.addAttribute("audit_date", hrPromotionApply.getAudit_date());
		} else {
			mode.addAttribute("audit_date", a.format(hrPromotionApply.getAudit_date()));
		}
		mode.addAttribute("state", hrPromotionApply.getState());
		mode.addAttribute("note", hrPromotionApply.getNote());

		return "hrp/hr/nursing/nursingpromotion/nursingPromotionUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateNursingPromotion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNursingPromotion(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrNursingPromotionService.updateNursingPromotion(mapVo);

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
	@RequestMapping(value = "/deleteNursingPromotion", method = RequestMethod.POST)
	@ResponseBody

	public String deleteNursingPromotion(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrNursingPromotion> listVo = JSONArray.parseArray(paramVo, HrNursingPromotion.class);
		try {
			for (HrNursingPromotion hrNursingPromotion : listVo) {
				/*
				 * str = str + hrBaseService.isExistsDataByTable("HR_DUTY",
				 * hrDuty.getKind_code())== null ? "" :
				 * hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());
				 */
				if (hrNursingPromotion.getState()!=0) {
					falg = false;
					continue;
				}
              
			}
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			hrNursingPromotionService.deletePromotionLeave(listVo);
			return hrNursingPromotionService.deleteNursingPromotion(listVo);

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
	@RequestMapping(value = "/queryNursingPromotion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryNursingPromotion(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("dept_code") !=null) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}
		String stationTransef = hrNursingPromotionService.queryNursingPromotion(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmNursingPromotion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmNursingPromotion(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrNursingPromotion> listVo = JSONArray.parseArray(paramVo, HrNursingPromotion.class);

			for (HrNursingPromotion hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getState() != 1) {
					hrNursingPromotion.setState(1);
					hrNursingPromotion.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Long.parseLong(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrNursingPromotionService.confirmNursingPromotionBatch(listVo);
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
	@RequestMapping(value = "/reConfirmNursingPromotion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmNursingPromotion(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg = "";
		try {
			List<HrNursingPromotion> listVo = JSONArray.parseArray(paramVo, HrNursingPromotion.class);

			for (HrNursingPromotion hrNursingPromotion : listVo) {
				if (hrNursingPromotion.getState() != 0) {
					hrNursingPromotion.setState(0);
					hrNursingPromotion.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrNursingPromotion.setHos_id(Long.parseLong(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrNursingPromotionService.reConfirmNursingPromotionBatch(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 查询级别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrNursingPromotionService.queryLevel(mapVo);
		return json;

	}

	/**
	 * 查询三年资料
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("year", SessionManager.getAcctYear());
		String json = hrNursingPromotionService.queryAttend(mapVo);
		return JSONObject.parseObject(json);

	}
}
