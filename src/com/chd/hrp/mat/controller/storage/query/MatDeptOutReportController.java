/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

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
import com.chd.hrp.mat.service.storage.query.MatDeptOutReportService;

/**
 * 
 * @Description: 常州三院 个性化报表
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDeptOutReportController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDeptOutReportController.class);
	

	//引入Service服务
	@Resource(name = "matDeptOutReportService")
	private final MatDeptOutReportService matDeptOutReportService = null; 
	
	/**
	 * @Description 库存明细查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matDeptOutReportPage", method = RequestMethod.GET)
	public String matDeptOutReportPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/query/matDeptOutReport";
	} 
	//科室领用耗材分类汇总表
	@RequestMapping(value = "/hrp/mat/storage/query/matStoreOutFimTypePage", method = RequestMethod.GET)
	public String matStoreOutFimType(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/query/matStoreOutFimType";
	} 
	
	/**
	 * @Description 库存明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatDeptOutReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptOutReport(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String queryMatDeptOutReportJson = matDeptOutReportService.queryMatDeptOutReport(getPage(mapVo));

		return JSONObject.parseObject(queryMatDeptOutReportJson);

	}
	 
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStoreOutFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreOutFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
					throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String queryMatDeptOutReportJson = matDeptOutReportService.queryMatStoreOutFimType(getPage(mapVo));
		
		return JSONObject.parseObject(queryMatDeptOutReportJson);
		
	}
	
	
	/**
	 * @Description 跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInvFinancePage", method = RequestMethod.GET)
	public String matInvFinancePage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "/hrp/mat/storage/query/matInvFinance";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatFinance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFinance(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {


			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String matInSupCount = matDeptOutReportService.queryMatFinance(getPage(mapVo));

		return JSONObject.parseObject(matInSupCount);

	}
	 
}
