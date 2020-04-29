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
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdvicePgroupService;

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
public class HtcgMedicalAdvicePgroupController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcgMedicalAdvicePgroupController.class);

	@Resource(name = "htcgMedicalAdvicePgroupService")
	private final HtcgMedicalAdvicePgroupService htcgMedicalAdvicePgroupService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/htcgMedicalAdvicePgroupMainPage", method = RequestMethod.GET)
	public String htcgMedicalAdvicePgroupMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/medicaladvicepgroup/htcgMedicalAdvicePgroupMain";

	}

	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/initHtcgMedicalAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMedicalAdvicePgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	     
		String medicalAdvicePgroupJson;
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 medicalAdvicePgroupJson = htcgMedicalAdvicePgroupService.initHtcgMedicalAdvicePgroup(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePgroupJson = e.getMessage();
		}
		 return JSONObject.parseObject(medicalAdvicePgroupJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/queryHtcgMedicalAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMedicalAdvicePgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdvicePgroupJson = htcgMedicalAdvicePgroupService.queryHtcgMedicalAdvicePgroup(getPage(mapVo));
	
		return JSONObject.parseObject(medicalAdvicePgroupJson);
	
	}

	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/calculateHtcgMedicalAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculateHtcgMedicalAdvicePgroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
	   
		String medicalAdvicePgroupJson;
		
		 try {
				
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 
			 medicalAdvicePgroupJson = htcgMedicalAdvicePgroupService.calculateHtcgMedicalAdvicePgroup(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePgroupJson = e.getMessage();
		}
		 return JSONObject.parseObject(medicalAdvicePgroupJson);

	}
	
	/**
	 * 准入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/admittanceHtcgMedicalAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> admittanceHtcgMedicalAdvicePgroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		String medicalAdvicePgroupJson;
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 
			 medicalAdvicePgroupJson = htcgMedicalAdvicePgroupService.admittanceHtcgMedicalAdvicePgroup(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePgroupJson = e.getMessage();
		}
		 return JSONObject.parseObject(medicalAdvicePgroupJson);

	}
	
	// 删除                                              
	@RequestMapping(value = "/hrp/htcg/setout/medicaladvicepgroup/deleteHtcgMedicalAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMedicalAdvicePgroup(
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
		
		String medicalAdvicePgroupJson;
		try {
			medicalAdvicePgroupJson =  htcgMedicalAdvicePgroupService.deleteBatchHtcgMedicalAdvicePgroup(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdvicePgroupJson = e.getMessage();
			
		}
		return JSONObject.parseObject(medicalAdvicePgroupJson);

	}
}
