/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

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
import com.chd.hrp.mat.service.matpayquery.MatPayQueryService;

/**
 * 
 * @Description:  账表查询
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatPayQueryController extends BaseController {

	private static Logger logger = Logger.getLogger(MatPayQueryController.class);

	// 引入Service服务
	@Resource(name = "matPayQueryService")
	private final MatPayQueryService matPayQueryService = null;
	
	/**
	 * @Description 入库发票查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matInBillReportPage", method = RequestMethod.GET)
	public String matMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/matpayquery/matInBillReport";
	}
	
	/**
	 * @Description 入库发票查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatInBillReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInBillReport(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String retMsg = matPayQueryService.queryMatInBillReport(getPage(mapVo));
		
		return JSONObject.parseObject(retMsg);
	}
}
