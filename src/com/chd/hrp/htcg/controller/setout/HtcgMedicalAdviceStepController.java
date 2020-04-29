package com.chd.hrp.htcg.controller.setout;
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
import com.chd.base.SessionManager;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdviceStepService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgMedicalAdviceStepController extends BaseController {
	private static Logger logger = Logger.getLogger(HtcgMedicalAdviceStepController.class);

	@Resource(name = "htcgMedicalAdviceStepService")
	private final HtcgMedicalAdviceStepService htcgMedicalAdviceStepService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicestep/htcgMedicalAdviceStepMainPage", method = RequestMethod.GET)
	public String htcgMedicalAdviceStepMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/medicaladvicestep/htcgMedicalAdviceStepMain";
	}
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicestep/initHtcgMedicalAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMedicalAdviceStep(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		String medicalAdviceStepJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			medicalAdviceStepJson = htcgMedicalAdviceStepService.initHtcgMedicalAdviceStep(mapVo);
		} catch (Exception e) {
			medicalAdviceStepJson=e.getMessage();
		}
		return JSONObject.parseObject(medicalAdviceStepJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicestep/queryHtcgMedicalAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMedicalAdviceStep(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdviceStep = htcgMedicalAdviceStepService.queryHtcgMedicalAdviceStep(getPage(mapVo));
		return JSONObject.parseObject(medicalAdviceStep);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicestep/deleteHtcgMedicalAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMedicalAdviceStep(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception {
		String medicalAdviceStepJson ;
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("scheme_code", ids[6]);
			mapVo.put("drgs_code", ids[7]);
			mapVo.put("mr_no", ids[8]);
			mapVo.put("in_hos_no", ids[9]);
			mapVo.put("advice_date", ids[10]);
			mapVo.put("order_by_no", ids[11]);
			mapVo.put("order_by_id", ids[12]);
			mapVo.put("perform_by_no", ids[13]);
			mapVo.put("perform_by_id", ids[14]);
			mapVo.put("charge_item_code", ids[15]);
			listVo.add(mapVo);
		}
		try{
			medicalAdviceStepJson = htcgMedicalAdviceStepService.deleteBatchHtcgMedicalAdviceStep(listVo);
		} catch (Exception e) {
			medicalAdviceStepJson=e.getMessage();
		}
		return JSONObject.parseObject(medicalAdviceStepJson);

	}
}
