package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN3Service;

/**
 *护理晋级汇总审批表(N3)
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrPromotionSummaryN3Controller extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN3Controller.class);

	// 引入Service服务
	@Resource(name = "hrPromotionSummaryN3Service")
	private final HrPromotionSummaryN3Service hrPromotionSummaryN3Service = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPromotionSummaryN3MainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/promotionsummaryn3/promotionSummaryN3MainPage";
	}


	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPromotionSummaryN3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPromotionSummaryN3(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("dept_code") !=null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[1]);
		}
		String stationTransef = hrPromotionSummaryN3Service.queryPromotionSummaryN3(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 护士长审核
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditHnurseAuditN3", method = RequestMethod.POST)
	@ResponseBody
	public String auditHnurseAuditN3(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN3> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN3.class);
			List<HrPromotionSummaryN3> saveList = new ArrayList<HrPromotionSummaryN3>();
			for (HrPromotionSummaryN3 hrPromotionSummaryN3 : listVo) {
				if (hrPromotionSummaryN3.getHnurse_audit() == null) {
					stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN3.getHnurse() == null) {
					hrPromotionSummaryN3.setHnurse(Long.parseLong(SessionManager.getUserId()));
					hrPromotionSummaryN3.setHnurse_date(new Date());
					saveList.add(hrPromotionSummaryN3);
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN3Service.auditHnurseAuditN3(listVo);
			}else{
				stationTransef="{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
			}
			return stationTransef;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 护士长销审
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditHnurseAuditN3", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditHnurseAuditN3(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN3> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN3.class);
			List<HrPromotionSummaryN3> saveList = new ArrayList<HrPromotionSummaryN3>();
			for (HrPromotionSummaryN3 hrPromotionSummaryN3 : listVo) {
				if (hrPromotionSummaryN3.getDhnurse() != null) {
					stationTransef = "{\"error\":\"销审失败！请先进行科护士长销审！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN3.getHnurse_audit() != null) {
					hrPromotionSummaryN3.setHnurse(null);
					hrPromotionSummaryN3.setHnurse_date(null);
					hrPromotionSummaryN3.setHnurse_audit(null);
					saveList.add(hrPromotionSummaryN3);
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN3Service.auditHnurseAuditN3(listVo);
			} else {
				stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
			}
			return stationTransef;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 科护士长审核
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditDhnurseAuditN3", method = RequestMethod.POST)
	@ResponseBody
	public String auditDhnurseAuditN3(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN3> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN3.class);
			List<HrPromotionSummaryN3> saveList = new ArrayList<HrPromotionSummaryN3>();
			for (HrPromotionSummaryN3 hrPromotionSummaryN3 : listVo) {
				if (hrPromotionSummaryN3.getHnurse() == null) {
					stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN3.getHnurse_audit() == 0) {
					stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN3.getDhnurse_audit() != null) {
					hrPromotionSummaryN3.setDhnurse(Long.parseLong(SessionManager.getUserId()));
					hrPromotionSummaryN3.setDhnurse_date(new Date());
					saveList.add(hrPromotionSummaryN3);
				} else {
					stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
					return stationTransef;
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN3Service.auditDhnurseAuditN3(saveList);
			}
			return stationTransef;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 科护士长销审
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditDhnurseAuditN3", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditDhnurseAuditN3(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN3> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN3.class);
			List<HrPromotionSummaryN3> saveList = new ArrayList<HrPromotionSummaryN3>();
			for (HrPromotionSummaryN3 hrPromotionSummaryN3 : listVo) {
				if (hrPromotionSummaryN3.getPromotion() != null) {
					stationTransef = "{\"error\":\"销审失败！晋级小组已经审核！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN3.getDhnurse_audit() != null) {

					hrPromotionSummaryN3.setDhnurse(null);
					hrPromotionSummaryN3.setDhnurse_date(null);
					hrPromotionSummaryN3.setDhnurse_audit(null);
					saveList.add(hrPromotionSummaryN3);
				}
				if (saveList.size() > 0) {
					stationTransef = hrPromotionSummaryN3Service.auditDhnurseAuditN3(saveList);
				} else {
					stationTransef = "{\"error\":\"审核失败！请先科护士长审核！\",\"state\":\"false\"}";

				}
			}
			return stationTransef;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 晋级小组审核
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/auditPromotionAuditN3", method = RequestMethod.POST)
	@ResponseBody
	public String auditPromotionAuditN3(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			return hrPromotionSummaryN3Service.auditPromotionAuditN3(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
