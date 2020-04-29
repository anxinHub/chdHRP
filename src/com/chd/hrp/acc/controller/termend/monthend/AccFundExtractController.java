package com.chd.hrp.acc.controller.termend.monthend;

import java.util.ArrayList;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccFundExtractService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0
 */
@Controller
public class AccFundExtractController extends BaseController {
	private static Logger logger = Logger.getLogger(AccFundExtractController.class);

	@Resource(name = "accFundExtractService")
	private final AccFundExtractService accFundExtractService = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;

	/**
	 * 医疗风险基金提取<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/accFundExtractPage", method = RequestMethod.GET)
	public String accFundExtractMainPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/termend/monthend/fundextract/accFundExtractMain";
	}

	/**
	 * 科室比例设置<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/accFundExtractDeptPage", method = RequestMethod.GET)
	public String accFundExtractDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("template_id", mapVo.get("template_id"));
		mode.addAttribute("year_month", mapVo.get("year_month"));
		return "hrp/acc/termend/monthend/fundextract/accFundExtractDept";
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 模板查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/queryAccFundExtract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccFundExtract(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z003");
		}
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 模板保存
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/saveAccFundExtract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccFundExtract(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z003");
		}
		if (mapVo.get("template_type_name") == null) {
			mapVo.put("template_type_name", "医疗风险基金提取");
		}

		String msg = "";
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			msg = accTermendTemplateService.addAccTermendTemplate(getPage(mapVo));
		} else {
			msg = accTermendTemplateService.updateAccTermendTemplate(getPage(mapVo));
		}

		return JSONObject.parseObject(msg);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 模板删除
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/deleteAccFundExtract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccFundExtract(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("template_id", id);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", MyConfig.getCurAccYear());
			listVo.add(mapVo);
		}
		String msg = accTermendTemplateService.deleteBatchAccTermendTemplate(listVo);

		return JSONObject.parseObject(msg);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 生成凭证
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/addAccFundExtractVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccFundExtractVouch(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}

		String accVouch = accFundExtractService.addAccFundExtractVouch(mapVo);

		return JSONObject.parseObject(accVouch);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 科室比例查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/queryAccFundExtractDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccFundExtractDept(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		// System.out.println("group_id: "+mapVo.get("group_id"));
		// System.out.println("hos_id: "+mapVo.get("hos_id"));
		// System.out.println("copy_code: "+mapVo.get("copy_code"));
		// System.out.println("template_id: "+mapVo.get("template_id"));
		// System.out.println("dept_code: "+mapVo.get("dept_code"));
		String templateDept = accFundExtractService.queryAccFundExtractDept(getPage(mapVo));
		return JSONObject.parseObject(templateDept);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 科室比例保存
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/saveAccFundExtractDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccFundExtractDept(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		String accVouch = accFundExtractService.saveAccFundExtractDept(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}

	/**
	 * 医疗风险基金提取<BR>
	 * 提取科室收入
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/saveAccFundExtractGetDeptIncom", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccFundExtractGetDeptIncom(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		// System.out.println(mapVo.get("year_month"));
		String accVouch = accFundExtractService.saveAccFundExtractGetDeptIncom(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}

	/**
	 * 医疗风险基金提取(财务)<BR>
	 * 提取科室收入
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/fundextract/saveAccFundExtractGetDeptIncomAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccFundExtractGetDeptIncomAcc(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if ((mapVo.get("template_id") == null) || ("".equals(mapVo.get("template_id")))) {
			return JSONObject.parseObject("{\"error\":\"请先保存模板\"}");
		}
		String accVouch = this.accFundExtractService.saveAccFundExtractGetDeptIncomAcc(getPage(mapVo));

		return JSONObject.parseObject(accVouch);
	}
}
