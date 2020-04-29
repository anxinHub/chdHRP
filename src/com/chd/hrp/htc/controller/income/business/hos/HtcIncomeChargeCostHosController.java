package com.chd.hrp.htc.controller.income.business.hos;

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
import com.chd.hrp.htc.service.income.business.hos.HtcIncomeChargeCostHosService;

@Controller
public class HtcIncomeChargeCostHosController extends BaseController{  

	private static Logger logger = Logger.getLogger(HtcIncomeChargeCostHosController.class);
	
	@Resource(name = "htcIncomeChargeCostHosService")
	private final HtcIncomeChargeCostHosService htcIncomeChargeCostHosService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/business/hos/htcIncomeChargeCostHosMainPage", method = RequestMethod.GET)
	public String htcIncomeChargeCostHosMainPage(Model mode) throws Exception {

		return "hrp/htc/income/business/hos/htcIncomeChargeCostHosMain";

	}
	
	//核算
	@RequestMapping(value = "/hrp/htc/income/business/hos/addHtcIncomeChargeCostHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomeChargeCostHos(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostHos = "";
		
		try {
	  		
			chargeCostHos = htcIncomeChargeCostHosService.addHtcIncomeChargeCostHos(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostHos = e.getMessage();
		}
		
		return JSONObject.parseObject(chargeCostHos);

	}
	
	// 查询
		@RequestMapping(value = "/hrp/htc/income/business/hos/queryHtcIncomeChargeCostHos", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcIncomeChargeCostHos(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			
		    mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
		  		
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String chargeCostHos = htcIncomeChargeCostHosService.queryHtcIncomeChargeCostHos(getPage(mapVo));
		
			return JSONObject.parseObject(chargeCostHos);
		
		}
		
	
	
}
