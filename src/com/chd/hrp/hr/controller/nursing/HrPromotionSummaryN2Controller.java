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
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN2Service;

/**
 *护理晋级汇总审批表(N2)
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrPromotionSummaryN2Controller extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN2Controller.class);

	// 引入Service服务
	@Resource(name = "hrPromotionSummaryN2Service")
	private final HrPromotionSummaryN2Service hrPromotionSummaryN2Service = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPromotionSummaryN2MainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/promotionsummaryn2/promotionSummaryN2MainPage";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPromotionSummaryN2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPromotionSummaryN2(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrPromotionSummaryN2Service.queryPromotionSummaryN2(getPage(mapVo));

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
	@RequestMapping(value = "/auditHnurseAuditN2", method = RequestMethod.POST)
	@ResponseBody
	public String auditHnurseAuditN2(@RequestParam String paramVo, Model mode) throws Exception {
		try {
		String stationTransef ="";
		List<HrPromotionSummaryN2> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN2.class);
		List<HrPromotionSummaryN2> saveList = new ArrayList<HrPromotionSummaryN2>();
		for (HrPromotionSummaryN2 hrPromotionSummaryN2 : listVo) {
			 if(hrPromotionSummaryN2.getHnurse()==null){
				hrPromotionSummaryN2.setHnurse(Long.parseLong(SessionManager.getUserId()));
				hrPromotionSummaryN2.setHnurse_date(new Date());
				saveList.add(hrPromotionSummaryN2);
			}
		}
		if(saveList.size()>0){
			stationTransef= hrPromotionSummaryN2Service.auditHnurseAuditN2(listVo);
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
	@RequestMapping(value = "/reAuditHnurseAuditN2", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditHnurseAuditN2(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN2> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN2.class);
			List<HrPromotionSummaryN2> saveList = new ArrayList<HrPromotionSummaryN2>();
			for (HrPromotionSummaryN2 hrPromotionSummaryN2 : listVo) {
				if (hrPromotionSummaryN2.getDhnurse() != null) {
					stationTransef = "{\"error\":\"销审失败！请先进行科护士长销审！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN2.getHnurse_audit() != null) {
					hrPromotionSummaryN2.setHnurse(null);
					hrPromotionSummaryN2.setHnurse_date(null);
					hrPromotionSummaryN2.setHnurse_audit(null);
					saveList.add(hrPromotionSummaryN2);
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN2Service.auditHnurseAuditN2(listVo);
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
	@RequestMapping(value = "/auditDhnurseAuditN2", method = RequestMethod.POST)
	@ResponseBody
	public String auditDhnurseAuditN2(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN2> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN2.class);
			List<HrPromotionSummaryN2> saveList = new ArrayList<HrPromotionSummaryN2>();
			for (HrPromotionSummaryN2 hrPromotionSummaryN2 : listVo) {
				if(hrPromotionSummaryN2.getHnurse() == null){
					stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if(hrPromotionSummaryN2.getHnurse_audit() == 0){
					stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN2.getDhnurse_audit() != null) {
					hrPromotionSummaryN2.setDhnurse(Long.parseLong(SessionManager.getUserId()));
					hrPromotionSummaryN2.setDhnurse_date(new Date());
					saveList.add(hrPromotionSummaryN2);
				} else {
					stationTransef = "{\"error\":\"审核失败！请检查状态！\",\"state\":\"false\"}";
					return stationTransef;
				}
			}
			if (saveList.size() > 0) {
				stationTransef = hrPromotionSummaryN2Service.auditDhnurseAuditN2(saveList);
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
	@RequestMapping(value = "/reAuditDhnurseAuditN2", method = RequestMethod.POST)
	@ResponseBody
	public String reAuditDhnurseAuditN2(@RequestParam String paramVo, Model mode) throws Exception {
		try {
			String stationTransef = "";
			List<HrPromotionSummaryN2> listVo = JSONArray.parseArray(paramVo, HrPromotionSummaryN2.class);
			List<HrPromotionSummaryN2> saveList = new ArrayList<HrPromotionSummaryN2>();
			for (HrPromotionSummaryN2 hrPromotionSummaryN2 : listVo) {
				if (hrPromotionSummaryN2.getPromotion() != null) {
					stationTransef = "{\"error\":\"销审失败！晋级小组已经审核！\",\"state\":\"false\"}";
					return stationTransef;
				}
				if (hrPromotionSummaryN2.getDhnurse_audit() != null) {

					hrPromotionSummaryN2.setDhnurse(null);
					hrPromotionSummaryN2.setDhnurse_date(null);
					hrPromotionSummaryN2.setDhnurse_audit(null);
					saveList.add(hrPromotionSummaryN2);
				}
				if (saveList.size() > 0) {
					stationTransef = hrPromotionSummaryN2Service.auditDhnurseAuditN2(saveList);
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
	@RequestMapping(value = "/auditPromotionAuditN2", method = RequestMethod.POST)
	@ResponseBody
	public String auditPromotionAuditN2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			return hrPromotionSummaryN2Service.auditPromotionAuditN2(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
