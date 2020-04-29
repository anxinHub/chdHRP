package com.chd.hrp.hr.controller.nursingtraining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentStatistical;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursingtraining.HrAssessmentStatisticalService;

/**
 * 考核统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursingtraining")
public class HrAssessmentStatisticalController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAssessmentStatisticalController.class);

	// 引入Service服务
	@Resource(name = "hrAssessmentStatisticalService")
	private final HrAssessmentStatisticalService hrAssessmentStatisticalService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAssessmentStatisticalMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursingtraining/assessmentstatistical/AssessmentStatisticalMainPage";
	}



	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssessmentStatistical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssessmentStatistical(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrAssessmentStatisticalService.queryAssessmentStatistical(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 查询考核名称
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEvalCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrAssessmentStatisticalService.queryEvalCode(mapVo);
		return json;
		
	}
	
}
