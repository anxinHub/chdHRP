package com.chd.hrp.mat.controller.account.report.outCategoryCount;

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
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptTypeCountService;

/**
 * @Description: 出库分类统计:科室类型统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAccountReportDeptTypeCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportDeptTypeCountController.class);
	
	@Resource(name = "matAccountReportDeptTypeCountService")
	private final MatAccountReportDeptTypeCountService matAccountReportDeptTypeCountService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/matDeptTypeCountPage", method = RequestMethod.GET)
	public String matDeptTypeCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/outCategoryCount/matDeptTypeCount";
	}
	
	/**
	 * @Description 
	 * @param mode科室类型统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryMatDeptTypeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatDeptTypeCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAccountReportDeptTypeCountJson = matAccountReportDeptTypeCountService.queryMatAccountReportDeptTypeCount(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportDeptTypeCountJson);
	}
	
	//
	/**
	 * @Description 
	 * 科室类型统计:按物资类别查询动态表头
	 * @param mapVo
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryDeptTypeCountHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTypeCountHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson=null;
		
		try {
			reJson=matAccountReportDeptTypeCountService.queryDeptTypeCountHead(mapVo);
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}


/**
 * @Description 
 * 科室类型统计:按物资类别查询动态表头
 * @param mapVo
 * @return map
 * @throws Exception
 */
@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryDeptTypeCountHeadNew", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryDeptTypeCountHeadNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
	mapVo.put("group_id", SessionManager.getGroupId());
	
	mapVo.put("hos_id", SessionManager.getHosId());
	
	mapVo.put("copy_code", SessionManager.getCopyCode());
		
	String reJson=null;
	
	try {
		reJson=matAccountReportDeptTypeCountService.queryDeptTypeCountHeadNew(mapVo);
		
	} catch (Exception e) {
			// TODO Auto-generated catch block
		reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
	}
	return JSONObject.parseObject(reJson);

}

	/**
	 * @Description 
	 * 科室类型统计:查询发生过业务的物资类别
	 * @param mapVo
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryOccurDeptTypeHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOccurDeptTypeHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		return JSONObject.parseObject(matAccountReportDeptTypeCountService.queryOccurDeptTypeHead(mapVo));
	}

}
