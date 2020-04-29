package com.chd.hrp.ass.controller.tongJiReports;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssSupplierService;


@Controller
public class AssSupplierController extends BaseController{    
	
	private static Logger logger = Logger.getLogger(AssSupplierController.class);

	
	@Resource(name="assSupplierService")
	private final AssSupplierService assSupplierService = null;
	
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/assSupplierPage", method = RequestMethod.GET)
	public String assSupplierPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assSupplierMain";

	}
	
	
	//查询数据  
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSupplier(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assSupplier = assSupplierService.queryAssSupplier(getPage(mapVo));

		return JSONObject.parseObject(assSupplier);

	}

}
