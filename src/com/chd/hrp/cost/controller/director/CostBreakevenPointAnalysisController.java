package com.chd.hrp.cost.controller.director;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.director.CostBreakevenPointAnalysisService;

/**
 * 保本点分析
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/hrp/cost/director/breakevenpointanalysis")
public class CostBreakevenPointAnalysisController extends BaseController {

	private static Logger logger = Logger.getLogger(CostBreakevenPointAnalysisController.class);

	@Resource(name = "costBreakevenPointAnalysisService")
	private final CostBreakevenPointAnalysisService costBreakevenPointAnalysisService = null;

	/**
	 * 
	 * @Title: costDepartmentOperationMainPage
	 * @Description: 门诊保本点
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointClinicMainPage", method = RequestMethod.GET)
	public String costBreakevenPointClinicMainPage(Model mode) throws DataAccessException {
		return "hrp/cost/costdirector/breakevenpointanalysis/clinic/costBreakevenPointClinicMain";
	}

	/**
	 * 
	 * @Title: queryCostBreakevenPointClinic
	 * @Description: 门诊保本点-查询
	 * @param @param  mapVo
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return Map<String,Object>
	 * @author sjy
	 */
	@RequestMapping(value = "/queryCostBreakevenPointClinic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDepartmentOperation(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costBreakevenPointAnalysisService.queryCostBreakevenPointClinic(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 
	 * @Title: costDepartmentOperationMainPage
	 * @Description: 门诊保本点-本量利分析图
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointClinicChartMainPage", method = RequestMethod.GET)
	public String costBreakevenPointClinicChartMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/clinic/costBreakevenPointClinicChartMain";
	}

	/**
	 * 
	 * @Title: costBreakevenPointClinicCalculationMainPage
	 * @Description: 门诊保本点-测算
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointClinicCalculationMainPage", method = RequestMethod.GET)
	public String costBreakevenPointClinicCalculationMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/clinic/costBreakevenPointClinicCalculationMain";
	}

	/**
	 * 
	 * @Title: queryCostBreakevenPointClinicCalculation
	 * @Description: 门诊保本点-测算查询
	 * @param @param  mapVo
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return Map<String,Object>
	 * @author sjy
	 */
	@RequestMapping(value = "/queryCostBreakevenPointClinicCalculation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenPointClinicCalculation(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costBreakevenPointAnalysisService
				.queryCostBreakevenPointClinicCalculation(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 
	 * @Title: costDepartmentOperationMainPage
	 * @Description: 住院保本点
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointInhosMainPage", method = RequestMethod.GET)
	public String costBreakevenPointInhosMainPage(Model mode) throws DataAccessException {
		return "hrp/cost/costdirector/breakevenpointanalysis/inhos/costBreakevenPointInhosMain";
	}

	/**
	 * 
	 * @Title: queryCostBreakevenPointInhos
	 * @Description: 住院保本点-查询
	 * @param @param  mapVo
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return Map<String,Object>
	 * @author sjy
	 */
	@RequestMapping(value = "/queryCostBreakevenPointInhos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenPointInhos(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costBreakevenPointAnalysisService.queryCostBreakevenPointInhos(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 医技保本点
	 * 
	 * @param mode
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/costBreakevenPointMedicalMainPage", method = RequestMethod.GET)
	public String costBreakevenPointMedicalMainPage(Model mode) throws DataAccessException {
		return "hrp/cost/costdirector/breakevenpointanalysis/medical/costBreakevenPointMedicalMain";
	}

	/**
	 * 医技保本点-查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCostBreakevenPointMedical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenPointMedical(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costBreakevenPointAnalysisService.queryCostBreakevenPointMedical(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 医技保本点-本利量分析图
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/costBreakevenPointMedicalChartMainPage", method = RequestMethod.GET)
	public String costBreakevenPointMedicalChartMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/medical/costBreakevenPointMedicalChartMain";
	}

	/**
	 * 医技保本点-测算
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/costBreakevenPointMedicalCalculationMainPage", method = RequestMethod.GET)
	public String costBreakevenPointMedicalCalculationMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/medical/costBreakevenPointMedicalCalculationMain";
	}
	
	/**
	  * 医技保本点-测算查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCostBreakevenPointMedicalCalculation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenPointMedicalCalculation(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costBreakevenPointAnalysisService.queryCostBreakevenPointMedicalCalculation(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 
	 * @Title: costDepartmentOperationMainPage
	 * @Description: 住院保本点-本量利分析图
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointInhosChartMainPage", method = RequestMethod.GET)
	public String costBreakevenPointInhosChartMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/inhos/costBreakevenPointInhosChartMain";
	}

	/**
	 * 
	 * @Title: costBreakevenPointClinicCalculationMainPage
	 * @Description: 住院保本点-测算
	 * @param @param  mode
	 * @param @return
	 * @param @throws DataAccessException
	 * @return String
	 * @author sjy
	 */
	@RequestMapping(value = "/costBreakevenPointInhosCalculationMainPage", method = RequestMethod.GET)
	public String costBreakevenPointInhosCalculationMainPage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws DataAccessException {
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/breakevenpointanalysis/inhos/costBreakevenPointInhosCalculationMain";
	}

	/**
	 * 
	 * @Title: queryCostBreakevenPointInhosCalculation
	 * @Description: 住院保本点-测算查询
	 * @param @param  mapVo
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return Map<String,Object>
	 * @author sjy
	 */
	@RequestMapping(value = "/queryCostBreakevenPointInhosCalculation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenPointInhosCalculation(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costBreakevenPointAnalysisService.queryCostBreakevenPointInhosCalculation(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
}
