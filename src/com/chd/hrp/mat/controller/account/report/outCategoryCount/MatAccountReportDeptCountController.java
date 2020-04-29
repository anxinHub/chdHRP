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

/**
 * @Description: 出库分类统计:科室统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAccountReportDeptCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportDeptCountController.class);
	
	@Resource(name = "matAccountReportDeptCountService")
	private final MatAccountReportDeptCountService matAccountReportDeptCountService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/matDeptCountPage", method = RequestMethod.GET)
	public String matDeptCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/outCategoryCount/matDeptCount";
	}
	
	/**
	 * @Description 
	 * @param mode科室统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryMatDeptCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatDeptCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAccountReportDeptCountJson = matAccountReportDeptCountService.queryMatAccountReportDeptCount(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportDeptCountJson);
	}
	
	//
	/**
	 * @Description 
	 * 查询物资收费类别-表头
	 * @param mapVo
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryDeptCountHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptCountHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson=null;
		
		try {
			reJson=matAccountReportDeptCountService.queryDeptCountHead(mapVo);
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * @Description 移库汇总表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/matTransferCountPage", method = RequestMethod.GET)
	public String matTransferCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		
		return "hrp/mat/account/report/outCategoryCount/matTransferCount";
	}
	
	/**
	 * 移库汇总表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryMatTransferCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatTransferCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matAccountReportDeptCountJson = matAccountReportDeptCountService.queryMatTransferCount(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportDeptCountJson);
	}
}
