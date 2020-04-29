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
import com.chd.hrp.htcg.service.calculation.HtcgChargeCostDeptService;

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
public class HtcgChargeCostDeptController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgChargeCostDeptController.class);
	
	@Resource(name = "htcgChargeCostDeptService")
	private final HtcgChargeCostDeptService htcgChargeCostDeptService = null;
    
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/htcgChargeCostDeptMainPage", method = RequestMethod.GET)
	public String htcgChargeCostDeptMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/chargecostdept/htcgChargeCostDeptMain";

	}

	// 医疗项目成本核算（科室）  生成 弹出方案页面
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/htcgChargeCostSchemeRelaPage", method = RequestMethod.GET)
	public String htcgChargeCostSchemeRelaPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/chargecostdept/htcgChargeCostSchemeRela";

	}
		
	// 保存 项目成本方案和病种成本方案对应关系
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/addHtcgChargeCostSchemeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgChargeCostDeptSchemeRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String chargeCostDeptJson = htcgChargeCostDeptService.addHtcgChargeCostSchemeRela(mapVo);
		return JSONObject.parseObject(chargeCostDeptJson);
		
	}

	// 查询对应关系
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/queryHtcgChargeCostSchemeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgChargeCostSchemeRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String chargeCostDept = htcgChargeCostDeptService.queryHtcgChargeCostSchemeRela(getPage(mapVo));
		return JSONObject.parseObject(chargeCostDept);
		
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/queryHtcgChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String chargeCostDept = htcgChargeCostDeptService.queryHtcgChargeCostDept(getPage(mapVo));
		return JSONObject.parseObject(chargeCostDept);
		
	}
	
	// 生成
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/initHtcgChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson =  htcgChargeCostDeptService.initHtcgChargeCostDept(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/deleteHtcgChargeCostSchemeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgChargeCostSchemeRela(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id : paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code",ids[2]);
			mapVo.put("acc_year",ids[3]);
			mapVo.put("scheme_code", ids[4]);//实际实体类变量
			mapVo.put("period_type_code", ids[5]);//实际实体类变量
			mapVo.put("period_code", ids[6]);//实际实体类变量
			mapVo.put("plan_code", ids[7]);//实际实体类变量
			listVo.add(mapVo);
		}
		
		String chargeCostDeptJson  = null;
		try {
			chargeCostDeptJson = htcgChargeCostDeptService.deleteBatchHtcgChargeCostSchemeRela(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostDeptJson = e.getMessage();
		}
		return JSONObject.parseObject(chargeCostDeptJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/calculation/chargecostdept/deleteHtcgChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgChargeCostDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code",ids[2]);
			mapVo.put("period_type_code",ids[3]);
			mapVo.put("acc_year", ids[4]);//实际实体类变量
			mapVo.put("acc_month", ids[5]);//实际实体类变量
			mapVo.put("proj_dept_id", ids[6]);//实际实体类变量
			mapVo.put("charge_item_code", ids[7]);//实际实体类变量
			mapVo.put("source_id", ids[8]);//实际实体类变量
			listVo.add(mapVo);
        }
		String chargeCostDeptJson = null;
		try {
			chargeCostDeptJson = htcgChargeCostDeptService.deleteBathcHtcgChargeCostDept(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostDeptJson = e.getMessage();
		}
	   return JSONObject.parseObject(chargeCostDeptJson);
			
	}
}

