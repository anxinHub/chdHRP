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
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdvicePmergeService;

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
public class HtcgMedicalAdvicePmergeController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcgMedicalAdvicePmergeController.class);

	@Resource(name = "htcgMedicalAdvicePmergeService")
	private final HtcgMedicalAdvicePmergeService htcgMedicalAdvicePmergeService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepmerge/htcgMedicalAdvicePmergeMainPage", method = RequestMethod.GET)
	public String htcgMedicalAdvicePmergeMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/medicaladvicepmerge/htcgMedicalAdvicePmergeMain";

	}
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepmerge/initHtcgMedicalAdvicePmerge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMedicalAdvicePmerge(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		String medicalAdvicePmergeJson;
		
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 medicalAdvicePmergeJson = htcgMedicalAdvicePmergeService.initHtcgMedicalAdvicePmerge(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePmergeJson = e.getMessage();
		}
		 return JSONObject.parseObject(medicalAdvicePmergeJson);
	
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepmerge/queryHtcgMedicalAdvicePmerge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMedicalAdvicePmerge(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdvicePmergeJson = htcgMedicalAdvicePmergeService.queryHtcgMedicalAdvicePmerge(getPage(mapVo));
		return JSONObject.parseObject(medicalAdvicePmergeJson);

	}
	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepmerge/deleteHtcgMedicalAdvicePmerge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMedicalAdvicePmerge(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
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
			mapVo.put("charge_item_code", ids[8]);
			mapVo.put("price", ids[9]);
			mapVo.put("recipe_type_code", ids[10]);
			mapVo.put("clp_p_step", ids[11]);
			listVo.add(mapVo);
		}
		String medicalAdvicePmergeJson = null;
		try {
			medicalAdvicePmergeJson = htcgMedicalAdvicePmergeService.deleteBathcHtcgMedicalAdvicePmerge(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePmergeJson = e.getMessage();
		}
		return JSONObject.parseObject(medicalAdvicePmergeJson);

	}
}
