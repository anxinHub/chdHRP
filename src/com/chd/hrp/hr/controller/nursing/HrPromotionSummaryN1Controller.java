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
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN1;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN1Service;

/**
 * 护理晋级汇总审批表(N1)
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrPromotionSummaryN1Controller extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN1Controller.class);

	// 引入Service服务
	@Resource(name = "hrPromotionSummaryN1Service")
	private final HrPromotionSummaryN1Service hrPromotionSummaryN1Service = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPromotionSummaryN1MainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/promotionsummaryn1/promotionSummaryN1MainPage";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPromotionSummaryN1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPromotionSummaryN1(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("dept_code") != null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[1]);
		}
		String stationTransef = hrPromotionSummaryN1Service.queryPromotionSummaryN1(getPage(mapVo));

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
	@RequestMapping(value = "/auditHnurseAuditN1", method = RequestMethod.POST)
	@ResponseBody
	public String auditHnurseAuditN1(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN1> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN1.class);
			List<HrPromotionSummaryN1> saveList = new ArrayList<HrPromotionSummaryN1>();
			for (HrPromotionSummaryN1 hrPromotionSummaryN1 : listVo) {
				if (hrPromotionSummaryN1.getHnurse_audit() == null) {
					stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
					return stationTransef;
				}else{
					hrPromotionSummaryN1.setHnurse(Long.parseLong(SessionManager.getUserId()));
					hrPromotionSummaryN1.setHnurse_date(new Date());
					saveList.add(hrPromotionSummaryN1);
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN1Service.auditHnurseAuditN1(listVo);
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
	@RequestMapping(value = "/reAuditHnurseAuditN1", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditHnurseAuditN1(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN1> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN1.class);
			List<HrPromotionSummaryN1> saveList = new ArrayList<HrPromotionSummaryN1>();
			for (HrPromotionSummaryN1 hrPromotionSummaryN1 : listVo) {
				if (hrPromotionSummaryN1.getDhnurse() != null) {
					stationTransef = "{\"error\":\"销审失败！请先进行科护士长销审！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN1.getHnurse_audit() != null) {
					hrPromotionSummaryN1.setHnurse(null);
					hrPromotionSummaryN1.setHnurse_date(null);
					hrPromotionSummaryN1.setHnurse_audit(null);
					saveList.add(hrPromotionSummaryN1);
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN1Service.auditHnurseAuditN1(listVo);
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
	@RequestMapping(value = "/auditDhnurseAuditN1", method = RequestMethod.POST)
	@ResponseBody
	public String auditDhnurseAuditN1(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN1> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN1.class);
			List<HrPromotionSummaryN1> saveList = new ArrayList<HrPromotionSummaryN1>();
			for (HrPromotionSummaryN1 hrPromotionSummaryN1 : listVo) {
				if(hrPromotionSummaryN1.getHnurse() == null){
					stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if(hrPromotionSummaryN1.getHnurse_audit() == 0){
					stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN1.getDhnurse_audit() != null) {
					hrPromotionSummaryN1.setDhnurse(Long.parseLong(SessionManager.getUserId()));
					hrPromotionSummaryN1.setDhnurse_date(new Date());
					saveList.add(hrPromotionSummaryN1);
				} else {
					stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
					return stationTransef;
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN1Service.auditDhnurseAuditN1(saveList);
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
	 * 科护士长销审
	 * 
	 * @param mapVo
	 * @param mode
	 *            reAudit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reAuditDhnurseAuditN1", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditDhnurseAuditN1(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN1> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN1.class);
			List<HrPromotionSummaryN1> saveList = new ArrayList<HrPromotionSummaryN1>();
			for (HrPromotionSummaryN1 hrPromotionSummaryN1 : listVo) {
				if (hrPromotionSummaryN1.getPromotion() != null) {
					stationTransef = "{\"error\":\"销审失败！晋级小组已经审核！\",\"state\":\"false\"}";
					return stationTransef;
				}

				if (hrPromotionSummaryN1.getDhnurse_audit() != null) {
					hrPromotionSummaryN1.setDhnurse(null);
					hrPromotionSummaryN1.setDhnurse_date(null);
					hrPromotionSummaryN1.setDhnurse_audit(null);
					saveList.add(hrPromotionSummaryN1);
				}
				if (saveList.size() > 0) {
					stationTransef = hrPromotionSummaryN1Service.auditDhnurseAuditN1(saveList);
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
	@RequestMapping(value = "/auditPromotionAuditN1", method = RequestMethod.POST)
	@ResponseBody
	public String auditPromotionAuditN1(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			return hrPromotionSummaryN1Service.auditPromotionAuditN1(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
