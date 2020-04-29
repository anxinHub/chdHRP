package com.chd.hrp.htc.controller.task.projectcost;
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
import com.chd.hrp.htc.service.task.projectcost.HtcChargeCoopService;

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
public class HtcChargeCoopController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcChargeCoopController.class);
	
	
	@Resource(name = "htcChargeCoopService")
	private final HtcChargeCoopService htcChargeCoopService = null;
    
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/htcChargeCoopDefineMainPage", method = RequestMethod.GET)
	public String chargeCoopMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/chargecoop/htcChargeCoopDefineMain";

	}
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/htcChargeCoopMainPage", method = RequestMethod.GET)
	public String htcChargeCoopMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/chargecoop/htcChargeCoopMain";

	}

	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/saveHtcChargeCoop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcChargeCoop(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcChargeCoopJson="";
		try {
			htcChargeCoopJson = htcChargeCoopService.saveHtcChargeCoop(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcChargeCoopJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcChargeCoopJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/queryHtcChargeCoop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcChargeCoop(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcChargeCoopJson = htcChargeCoopService.queryHtcChargeCoop(getPage(mapVo));

		return JSONObject.parseObject(htcChargeCoopJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/deleteHtcChargeCoop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcChargeCoop(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("charge_item_id", ids[7]);
			mapVo.put("server_dept_no", ids[8]);
			mapVo.put("server_dept_id", ids[9]);
			mapVo.put("title_code", ids[10]);
            listVo.add(mapVo);
        }
		String htcChargeCoopJson = "";
		try {
			htcChargeCoopJson = htcChargeCoopService.deleteBatchHtcChargeCoop(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcChargeCoopJson = e.getMessage();
		}
	   return JSONObject.parseObject(htcChargeCoopJson);
			
	}
	

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/queryHtcChargeCoopCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcChargeCoopCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcChargeCoopJson = htcChargeCoopService.queryHtcChargeCoopCharge(getPage(mapVo));
		
		return JSONObject.parseObject(htcChargeCoopJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/chargecoop/queryHtcChargeCoopTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcChargeCoopTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcChargeCoopJson = htcChargeCoopService.queryHtcChargeCoopTitle(getPage(mapVo));
       	 
		return JSONObject.parseObject(htcChargeCoopJson);

	}
}

