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
import com.chd.hrp.htcg.service.calculation.HtcgDeptDdrgsCostService;

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
public class HtcgDeptDdrgsCostController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDeptDdrgsCostController.class);
	
	@Resource(name = "htcgDeptDdrgsCostService")
	private final HtcgDeptDdrgsCostService htcgDeptDdrgsCostService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/deptddrgscost/htcgDeptDdrgsCostMainPage", method = RequestMethod.GET)
	public String htcgDeptDdrgsCostMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/deptddrgscost/htcgDeptDdrgsCostMain";
	}

	@RequestMapping(value = "/hrp/htcg/calculation/deptddrgscost/initHtcgDeptDdrgsCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDeptDdrgsCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcgDeptDdrgsCostJson = "";
		try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			  mapVo.put("hos_id", SessionManager.getHosId());
			  mapVo.put("copy_code", SessionManager.getCopyCode()); 
			htcgDeptDdrgsCostJson =  htcgDeptDdrgsCostService.initHtcgDeptDdrgsCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgDeptDdrgsCostJson = e.getMessage();
		}
		 return JSONObject.parseObject(htcgDeptDdrgsCostJson);
		
	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/deptddrgscost/queryHtcgDeptDdrgsCost", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgDeptDdrgsCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		  mapVo.put("hos_id", SessionManager.getHosId());
		  mapVo.put("copy_code", SessionManager.getCopyCode());
		String deptDdrgsCost = htcgDeptDdrgsCostService.queryHtcgDeptDdrgsCost(getPage(mapVo));
		return JSONObject.parseObject(deptDdrgsCost);
	}
	//删除
	@RequestMapping(value = "/hrp/htcg/calculation/deptddrgscost/deleteHtcgDeptDdrgsCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDeptDdrgsCost(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("scheme_code", ids[6]);
			mapVo.put("drgs_code", ids[7]);
			mapVo.put("out_dept_id", ids[8]);
			mapVo.put("clp_d_step", ids[9]);
			mapVo.put("item_code", ids[10]);
			mapVo.put("charge_nature_code", ids[11]);
			mapVo.put("price", ids[12]);
			mapVo.put("recipe_type_code", ids[13]);
			listVo.add(mapVo);
        }
		String deptDdrgsCostJson =null;
		try {
			deptDdrgsCostJson = htcgDeptDdrgsCostService.deleteBatchHtcgDeptDdrgsCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			deptDdrgsCostJson = e.getMessage();
		}
	   return JSONObject.parseObject(deptDdrgsCostJson);
			
	}
}
