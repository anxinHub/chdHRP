package com.chd.hrp.ass.controller.guanLiReports;

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
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationUseTimeService;
/**
 * 
 * @author 资产月报表
 *
 */
@Controller
public class AssDepreciationUseTimeController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AssDepreciationUseTimeController.class);
	
	//引入Service服务
	@Resource(name = "assDepreciationUseTimeService")
	private final AssDepreciationUseTimeService assDepreciationUseTimeService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDepreciationUseTimePage", method = RequestMethod.GET)
	public String assPlanDeptMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDepreciationUseTime";

	}

	/**
	 * @Description 
	 * 查询数据 050301 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDepreciationUseTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationUseTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assPlanDept = assDepreciationUseTimeService.query(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);
		
	}
	
}
