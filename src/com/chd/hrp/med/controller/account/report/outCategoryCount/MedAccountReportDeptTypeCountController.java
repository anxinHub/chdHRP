package com.chd.hrp.med.controller.account.report.outCategoryCount;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountService;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptTypeCountService;

/**
 * @Description: 出库分类统计:科室类型统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAccountReportDeptTypeCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportDeptTypeCountController.class);
	
	@Resource(name = "medAccountReportDeptTypeCountService")
	private final MedAccountReportDeptTypeCountService medAccountReportDeptTypeCountService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/medDeptTypeCountPage", method = RequestMethod.GET)
	public String medDeptTypeCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/account/report/outCategoryCount/medDeptTypeCount";
	}
	
	/**
	 * @Description 
	 * @param mode科室类型统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryMedDeptTypeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedDeptTypeCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAccountReportDeptTypeCountJson = medAccountReportDeptTypeCountService.queryMedAccountReportDeptTypeCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportDeptTypeCountJson);
	}
	
	//
	/**
	 * @Description 
	 * 科室类型统计:按药品类别查询动态表头
	 * @param mapVo
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryDeptTypeCountHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTypeCountHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson=null;
		
		try {
			reJson=medAccountReportDeptTypeCountService.queryDeptTypeCountHead(mapVo);
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}


/**
 * @Description 
 * 科室类型统计:按药品类别查询动态表头
 * @param mapVo
 * @return map
 * @throws Exception
 */
@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryDeptTypeCountHeadNew", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryDeptTypeCountHeadNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
	mapVo.put("group_id", SessionManager.getGroupId());
	
	mapVo.put("hos_id", SessionManager.getHosId());
	
	mapVo.put("copy_code", SessionManager.getCopyCode());
		
	String reJson=null;
	
	try {
		reJson=medAccountReportDeptTypeCountService.queryDeptTypeCountHeadNew(mapVo);
		
	} catch (Exception e) {
			// TODO Auto-generated catch block
		reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
	}
	return JSONObject.parseObject(reJson);

}
}
