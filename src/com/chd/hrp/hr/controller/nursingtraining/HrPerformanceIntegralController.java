package com.chd.hrp.hr.controller.nursingtraining;

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
import com.chd.hrp.hr.entity.nursingtraining.HrPerformanceIntegral;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursingtraining.HrPerformanceIntegralService;

/**
 * 员工年终绩效积分考核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursingtraining")
public class HrPerformanceIntegralController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPerformanceIntegralController.class);

	// 引入Service服务
	@Resource(name = "hrPerformanceIntegralService")
	private final HrPerformanceIntegralService hrPerformanceIntegralService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPerformanceIntegralMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursingtraining/performanceintegral/PerformanceIntegralMainPage";
	}

	

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPerformanceIntegral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPerformanceIntegral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String hosEmpKindJson = hrPerformanceIntegralService.addPerformanceIntegral(mapVo);

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
	@RequestMapping(value = "/deletePerformanceIntegral", method = RequestMethod.POST)
	@ResponseBody

	public String deletePerformanceIntegral(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrPerformanceIntegral> listVo = JSONArray.parseArray(paramVo, HrPerformanceIntegral.class);
		try {
			for (HrPerformanceIntegral hrAcademicAbility : listVo) {
				hrAcademicAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAcademicAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
				/*if (StringUtils.isBlank(hrAcademicAbility.getYear()) && hrAcademicAbility.getEmp_id() == null || StringUtils.isBlank(hrAcademicAbility.getEmp_id().toString())) {
					listVo.remove(hrAcademicAbility);
				}*/
			
			}
			return  hrPerformanceIntegralService.deletePerformanceIntegral(listVo);

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
	@RequestMapping(value = "/queryPerformanceIntegral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPerformanceIntegral(@RequestParam Map<String, Object> mapVo, Model mode)
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

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}
		String stationTransef = hrPerformanceIntegralService.queryPerformanceIntegral(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
}
