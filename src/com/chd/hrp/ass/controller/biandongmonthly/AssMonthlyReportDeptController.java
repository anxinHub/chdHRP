package com.chd.hrp.ass.controller.biandongmonthly;

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
import com.chd.hrp.ass.service.biandongmonthly.AssMonthlyReportDeptService;



/**
 * 资产月报明细表
 * @author Administrator
 *
 */
@Controller
public class AssMonthlyReportDeptController extends BaseController{
	private static Logger logger = Logger.getLogger(AssMonthlyReportDeptController.class);
	
	@Resource(name = "assMonthlyReportDeptService")
	private final AssMonthlyReportDeptService assMonthlyReportDeptService = null;
	
	/**
	 * 资产月报明细表查询跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbiandongmonthly/assMonthlyReportDept", method = RequestMethod.GET)
	public String assMonthlyReportDept(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbiandongmonthly/assMonthlyReportDeptMain";
	}
	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assbiandongmonthly/queryAssMonthlyReportDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMonthlyReportDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
	    
		String result = assMonthlyReportDeptService.queryAssMonthlyReportDept(getPage(mapVo));

		return JSONObject.parseObject(result);
		
	}
}
