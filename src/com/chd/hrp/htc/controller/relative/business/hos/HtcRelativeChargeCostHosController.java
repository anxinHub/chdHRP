package com.chd.hrp.htc.controller.relative.business.hos;

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
import com.chd.hrp.htc.service.relative.business.hos.HtcRelativeChargeCostHosService;

@Controller
public class HtcRelativeChargeCostHosController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostHosController.class);
	
	@Resource(name = "htcRelativeChargeCostHosService")
	private final HtcRelativeChargeCostHosService htcRelativeChargeCostHosService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/business/hos/htcRelativeChargeCostHosMainPage", method = RequestMethod.GET)
	public String htcIncomeChargeCostHosMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/business/hos/htcRelativeChargeCostHosMain";

	}
	
	//核算
	@RequestMapping(value = "/hrp/htc/relative/business/hos/addHtcRelativeChargeCostHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativeChargeCostHos(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostHos = "";
		
		try {
	  		
			chargeCostHos = htcRelativeChargeCostHosService.addHtcRelativeChargeCostHos(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostHos = e.getMessage();
		}
		
		return JSONObject.parseObject(chargeCostHos);

	}
	
	// 查询
		@RequestMapping(value = "/hrp/htc/relative/business/hos/queryHtcRelativeChargeCostHos", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcRelativeChargeCostHos(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			
		    mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
		  		
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String chargeCostHos = htcRelativeChargeCostHosService.queryHtcRelativeChargeCostHos(getPage(mapVo));
		
			return JSONObject.parseObject(chargeCostHos);
		
		}
		
	
	
}
