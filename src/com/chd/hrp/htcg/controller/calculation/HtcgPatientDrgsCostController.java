package com.chd.hrp.htcg.controller.calculation;
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
import com.chd.hrp.htcg.service.calculation.HtcgPatientDrgsCostService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgPatientDrgsCostController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgPatientDrgsCostController.class);
	
	
	@Resource(name = "htcgPatientDrgsCostService")
	private final HtcgPatientDrgsCostService htcgPatientDrgsCostService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/patientdrgscost/htcgPatientDrgsCostMainPage", method = RequestMethod.GET)
	public String htcgPatientDrgsCostMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/patientdrgscost/htcgPatientDrgsCostMain";
	}
	
	@RequestMapping(value = "/hrp/htcg/calculation/patientdrgscost/initHtcgPatientDrgsCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgPatientDrgsCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String patientDrgsCostJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			 patientDrgsCostJson =  htcgPatientDrgsCostService.initHtcgPatientDrgsCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			patientDrgsCostJson = e.getMessage();
		}
		 return JSONObject.parseObject(patientDrgsCostJson);
		 
	
  }

	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/patientdrgscost/queryHtcgPatientDrgsCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgPatientDrgsCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String patientDrgsCostJson = htcgPatientDrgsCostService.queryHtcgPatientDrgsCost(getPage(mapVo));
		return JSONObject.parseObject(patientDrgsCostJson);
		
	}
	//删除
	@RequestMapping(value = "/hrp/htcg/calculation/patientdrgscost/deleteHtcgPatientDrgsCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgPatientDrgsCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
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
			mapVo.put("order_by_id", ids[11]);
			mapVo.put("perform_by_id", ids[12]);
			mapVo.put("item_code", ids[13]);
			listVo.add(mapVo);
        }
		String patientDrgsCostJson = null;
		try {
			patientDrgsCostJson = htcgPatientDrgsCostService.deleteBatchHtcgPatientDrgsCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			patientDrgsCostJson = e.getMessage();
		}
	   return JSONObject.parseObject(patientDrgsCostJson);
			
	}
}

