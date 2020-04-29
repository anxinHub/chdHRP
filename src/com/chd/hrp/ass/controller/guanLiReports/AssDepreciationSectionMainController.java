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
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationSectionMainService;
/**
 * 
 * @author 资产月报表
 *
 */
@Controller
public class AssDepreciationSectionMainController extends BaseController {
	

	
	private static Logger logger = Logger.getLogger(AssDepreciationSectionMainController.class);
	
	//引入Service服务
	@Resource(name = "assDepreciationSectionMainService")
	private final AssDepreciationSectionMainService assDepreciationSectionMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDepreciationSectionMainPage", method = RequestMethod.GET)
	public String assPlanDeptMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDepreciationSectionMain";

	}


	/**
	 * @Description 
	 * 查询数据 050301 购置计划单
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDepreciationSectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationSectionMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		/*String assPlanDept = assDepreciationSectionMainService.query(getPage(mapVo));*/
		String assPlanDept = assDepreciationSectionMainService.queryAssDepreciationSectionMain(getPage(mapVo));
		return JSONObject.parseObject(assPlanDept);
		
	}
	
}
