/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.account.report;

import java.util.*;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutService;

/**
 * 
 * @Description: 科室出库查询表  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountReportDeptOutController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAccountReportDeptOutController.class);

	// 引入Service服务
	@Resource(name = "matAccountReportDeptOutService")
	private final MatAccountReportDeptOutService matAccountReportDeptOutService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matDeptOutPage", method = RequestMethod.GET)
	public String MatDeptOutPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/matDeptOut";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatAccountReportDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportDeptOut(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date").toString())){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date").toString())){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String matJson = matAccountReportDeptOutService.queryMatAccountReportDeptOut(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
	
	/**
	 * 其他入库查询
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInByTypePage", method = RequestMethod.GET)
	public String matInByTypePage(Model mode) throws Exception {
		
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/matInByType";
	}
	
	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatInByType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInByType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matJson = matAccountReportDeptOutService.queryMatInByType(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * 其他入库明细页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matOtherInDetailPage", method = RequestMethod.GET)
	public String matOtherInDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/matOtherInDetail";
	}
	
	/**
	 * @Description 其他入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatOtherInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOtherInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matJson = matAccountReportDeptOutService.queryMatOtherInDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 其他出库明细页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matOtherOutDetailPage", method = RequestMethod.GET)
	public String matOtherOutDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/matOtherOutDetail";
	}
	
	/**
	 * @Description 其他入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatOtherOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOtherOutDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matJson = matAccountReportDeptOutService.queryMatOtherOutDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
}
