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
import com.chd.hrp.htcg.service.setout.HtcgMaterialAdvicePgroupService;

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
public class HtcgMaterialAdvicePgroupController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcgMaterialAdvicePgroupController.class);

	@Resource(name = "htcgMaterialAdvicePgroupService")
	private final HtcgMaterialAdvicePgroupService htcgMaterialAdvicePgroupService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/htcgMaterialAdvicePgroupMainPage", method = RequestMethod.GET)
	public String htcgMaterialAdvicePgroupMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/materialadvicepgroup/htcgMaterialAdvicePgroupMain";

	}

	

	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/initHtcgMaterialAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMaterialAdvicePgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String htcgMaterialAdvicePgroupJson;
		
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 htcgMaterialAdvicePgroupJson =htcgMaterialAdvicePgroupService.initHtcgMaterialAdvicePgroup(mapVo);
				
		} catch (Exception e) {
			// TODO: handle exception
			htcgMaterialAdvicePgroupJson = e.getMessage();
		}
		 return JSONObject.parseObject(htcgMaterialAdvicePgroupJson);
		 
	
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/queryHtcgMaterialAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMaterialAdvicePgroup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialAdvicePgroup = htcgMaterialAdvicePgroupService.queryHtcgMaterialAdvicePgroup(getPage(mapVo));

		return JSONObject.parseObject(materialAdvicePgroup);

	}
	
	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/calculateHtcgMaterialAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculateHtcgMaterialAdvicePgroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
	
		String materialAdvicePgroup;
		
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 materialAdvicePgroup =htcgMaterialAdvicePgroupService.calculateHtcgMaterialAdvicePgroup(mapVo);
				
		} catch (Exception e) {
			// TODO: handle exception
			materialAdvicePgroup = e.getMessage();
		}
		 return JSONObject.parseObject(materialAdvicePgroup);
		 
		
	}
	
	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/admittanceHtcgMaterialAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> admittanceHtcgMaterialAdvicePgroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
	
		String materialAdvicePgroup;
		
		 try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 materialAdvicePgroup =htcgMaterialAdvicePgroupService.admittanceHtcgMaterialAdvicePgroup(mapVo);
				
		} catch (Exception e) {
			// TODO: handle exception
			materialAdvicePgroup = e.getMessage();
		}
		 return JSONObject.parseObject(materialAdvicePgroup);
		 
		
	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/materialadvicepgroup/deleteHtcgMaterialAdvicePgroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMaterialAdvicePgroup(
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
			mapVo.put("mate_code", ids[8]);
			mapVo.put("price", ids[9]);
			mapVo.put("recipe_type_code", ids[10]);
			mapVo.put("clp_p_step", ids[11]);
			listVo.add(mapVo);
		}
		String materialAdvicePgroupJson =  null;
		try {
			materialAdvicePgroupJson = htcgMaterialAdvicePgroupService.deleteBatchHtcgMaterialAdvicePgroup(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			materialAdvicePgroupJson = e.getMessage();
		}
		return JSONObject.parseObject(materialAdvicePgroupJson);

	}
}
