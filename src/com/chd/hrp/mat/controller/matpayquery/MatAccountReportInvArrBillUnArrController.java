package com.chd.hrp.mat.controller.matpayquery;

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
import com.chd.hrp.mat.service.matpayquery.MatAccountReportInvArrBillUnArrService;

/**
 * @Description: 货到票未到明细表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountReportInvArrBillUnArrController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportInvArrBillUnArrController.class);
	
	@Resource(name = "matAccountReportInvArrBillUnArrService")
	private final MatAccountReportInvArrBillUnArrService matAccountReportInvArrBillUnArrService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matInvArrBillUnArrPage", method = RequestMethod.GET)
	public String matInvArrBillUnArrPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/matpayquery/matInvArrBillUnArr";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatInvArrBillUnArr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvArrBillUnArr(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAccountReportInvArrBillUnArrJson;
		if(mapVo.get("show_detail").equals("1")){
			matAccountReportInvArrBillUnArrJson = matAccountReportInvArrBillUnArrService.queryMatAccountReportInvArrBillUnArr(getPage(mapVo));
		}else{
			matAccountReportInvArrBillUnArrJson = matAccountReportInvArrBillUnArrService.queryMatAccountReportInvArrBillUnArrSup(getPage(mapVo));
		}
		 
		return JSONObject.parseObject(matAccountReportInvArrBillUnArrJson) ;  
	}
}
