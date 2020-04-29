package com.chd.hrp.htc.controller.task.business.hos;

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
import com.chd.hrp.htc.service.task.business.hos.HtcTaskChargeCostHosService;

@Controller
public class HtcTaskChargeCostHosController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcTaskChargeCostHosController.class);
	
	@Resource(name = "htcTaskChargeCostHosService")
	private final HtcTaskChargeCostHosService htcTaskChargeCostHosService = null;
	
	@RequestMapping(value = "/hrp/htc/task/business/hos/htcTaskChargeCostHosMainPage", method = RequestMethod.GET)
	public String htcTaskChargeCostDeptMainPage(Model mode) throws Exception {
		return "hrp/htc/task/business/hos/htcTaskChargeCostHosMain";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/business/hos/queryHtcTaskChargeCostHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskChargeCostHos(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcTaskChargeCostHosJson = htcTaskChargeCostHosService.queryHtcTaskChargeCostHos(getPage(mapVo));

		return JSONObject.parseObject(htcTaskChargeCostHosJson);

	}
		
	//核算
	@RequestMapping(value = "/hrp/htc/task/business/hos/collectHtcTaskChargeCostHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcTaskChargeCostHos(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcTaskChargeCostHosJson = "";
		
		try {
	  		
			htcTaskChargeCostHosJson = htcTaskChargeCostHosService.collectHtcTaskChargeCostHos(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcTaskChargeCostHosJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcTaskChargeCostHosJson);

	}
}
