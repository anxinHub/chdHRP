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
import com.chd.hrp.htcg.service.setout.HtcgDrugAdviceStepService;

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
public class HtcgDrugAdviceStepController extends BaseController {
	private static Logger logger = Logger.getLogger(HtcgDrugAdviceStepController.class);

	@Resource(name = "htcgDrugAdviceStepService")
	private final HtcgDrugAdviceStepService htcgDrugAdviceStepService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/drugadvicestep/htcgDrugAdviceStepMainPage", method = RequestMethod.GET)
	public String htcgDrugAdviceStepMainPage(Model mode) throws Exception {

		return "hrp/htcg/setout/drugadvicestep/htcgDrugAdviceStepMain";

	}
	

 
	@RequestMapping(value = "/hrp/htcg/setout/drugadvicestep/initHtcgDrugAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDrugAdviceStep(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		String drugAdviceJson = "";
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			drugAdviceJson =  htcgDrugAdviceStepService.initHtcgDrugAdviceStep(mapVo);
			
		} catch (Exception e) {
			 
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/drugadvicestep/queryHtcgDrugAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrugAdviceStep(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		String drugAdviceStep ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
			drugAdviceStep= htcgDrugAdviceStepService.queryHtcgDrugAdviceStep(getPage(mapVo));
		} catch (Exception e) {
			 
			drugAdviceStep = e.getMessage();
		}
		return JSONObject.parseObject(drugAdviceStep);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/drugadvicestep/deleteHtcgMedicalAdviceStep", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugAdviceStep(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception {
		String drugAdviceStep ;
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
			mapVo.put("drug_code", ids[15]);
			listVo.add(mapVo);
		}
		
		try{
			drugAdviceStep = htcgDrugAdviceStepService.deleteBatchHtcgDrugAdviceStep(listVo);
		} catch (Exception e) {
			 
			drugAdviceStep = e.getMessage();
		}
		return JSONObject.parseObject(drugAdviceStep);

	}
	
}
