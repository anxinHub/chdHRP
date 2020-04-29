package com.chd.hrp.hr.controller.training;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.base.HrUser;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.HrReportService;


/**
 * 培训方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/report")
public class HrReportController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrReportController.class);

	// 引入Service服务
	@Resource(name = "hrReportService")
	private final HrReportService hrReportService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 签到汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportSignMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/report/reportSignMainPage";

	}
	/**
	 * @Description 查询签到汇总
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportSign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportSign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String reportSign = hrReportService.queryReportSign(getPage(mapVo));

		return JSONObject.parseObject(reportSign);

	}
	/**
	 * @Description考核评价表页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportExamMainPage", method = RequestMethod.GET)
	public String reportExamMainPage(Model mode) throws Exception {

		return "hrp/hr/training/report/reportExamMainPage";

	}
	/**
	 * @Description 查询考核评价表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportExam", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportExam(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String reportExam = hrReportService.queryReportExam(getPage(mapVo));

		return JSONObject.parseObject(reportExam);

	}
	/**
	 * @Description 补考主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportBuKaoMainPage", method = RequestMethod.GET)
	public String reportBuKaoMainPage(Model mode) throws Exception {

		return "hrp/hr/training/report/reportBuKaoMainPage";

	}
	/**
	 * @Description 查询补考
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportBuKao", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportBuKao(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String reportBuKao = hrReportService.queryReportBuKao(getPage(mapVo));

		return JSONObject.parseObject(reportBuKao);

	}
	/**
	 * @Description 综合评价页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportEvaluateMainPage", method = RequestMethod.GET)
	public String reportEvaluateMainPage(Model mode) throws Exception {
		mode.addAttribute("hos_name", SessionManager.getHosName());
		return "hrp/hr/training/report/reportEvaluateMainPage";

	}
	/**
	 * @Description 生成综合评价
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportEvaluate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportEvaluate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String reportEvaluate = hrReportService.queryReportEvaluate(getPage(mapVo));

		return JSONObject.parseObject(reportEvaluate);

	}
	//
	/**
	 * @Description 查询已经生成的综合评价
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportEvaluateTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportEvaluateTable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String reportEvaluate = hrReportService.queryReportEvaluateTable(getPage(mapVo));

		return JSONObject.parseObject(reportEvaluate);

	}
	/**
	 * @Description 添加综合评价
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addReportEvaluate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReportEvaluate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String Report = hrReportService.addReportEvaluate(mapVo);

			return JSONObject.parseObject(Report);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 个人年度汇总页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportEmpYearMainPage", method = RequestMethod.GET)
	public String reportEmpYear(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		mapVo.put("emp_code", SessionManager.getEmpCode());
		Map<String, Object> user=hrReportService.queryUser(mapVo);
        if(user!=null){
        	mode.addAttribute("emp_name", user.get("EMP_CODE")+" "+user.get("EMP_NAME"));
    		mode.addAttribute("emp_id", user.get("EMP_ID"));
    		mode.addAttribute("dept_name", user.get("DEPT_NAME"));
    		mode.addAttribute("kind_name", user.get("KIND_NAME"));
    		mode.addAttribute("emp_code", user.get("EMP_CODE"));
        }
		return "hrp/hr/training/report/reportEmpYearMainPage";

	}
	/**
	 * @Description 查询个人年度汇总
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryReportEmpYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportEmpYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if(SessionManager.getEmpCode()==null||SessionManager.getEmpCode().equals(""))
			return JSONObject.parseObject("{\"error\":\"未关联职工，无法查询您的培训考核信息" + "\"}");
		mapVo.put("emp_code", SessionManager.getEmpCode());
		Map<String, Object> user=hrReportService.queryUser(mapVo);
        if(user!=null){
        	mapVo.put("emp_id",user.get("EMP_ID"));
        }
		String reportEmpYear = hrReportService.queryReportEmpYear(getPage(mapVo));

		return JSONObject.parseObject(reportEmpYear);

	}

}
