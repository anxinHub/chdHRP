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
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockToFimService;

/**
 * 
 * @Description: 库存收发查询  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountReportInvStockToFimController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportInvStockToFimController.class);
	
	
	@Resource(name="matAccountReportInvStockToFimService")
	private final MatAccountReportInvStockToFimService matAccountReportInvStockToFimService = null;
	
	@RequestMapping(value="hrp/mat/account/report/matInvStockToFimPage")
	public String  matInvStockToFimPage(Model mode){

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		mode.addAttribute("user_name", SessionManager.getUserName());
		
		return "hrp/mat/account/report/matInvStockToFim";
	}
	/**
	 * 跳转到库房收支月报表界面
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="hrp/mat/account/report/matStoreInvStockPage")
	public String  matStoreInvStockPage(Model mode){
		
		return "hrp/mat/account/report/matStoreInvStock";
	}
	
	@RequestMapping(value = "/hrp/mat/account/report/queryMatAccountReportInvStockToFim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatAccountReportInvStockToFim(@RequestParam Map<String, Object> mapVo, Model mode) {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
			
		String matJson=matAccountReportInvStockToFimService.collectMatAccountReportInvStockTofim(mapVo);
		return JSONObject.parseObject(matJson);
		
	}
	
	@RequestMapping(value = "/hrp/mat/account/report/queryMatStoreInvStock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatStoreInvStock(@RequestParam Map<String, Object> mapVo, Model mode) {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
			
		String matJson=matAccountReportInvStockToFimService.collectMatStoreInvStock(mapVo);
		return JSONObject.parseObject(matJson);
		
	}
	/**
	 * @Description 查询本期增加减少字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatAccountReportInvStockToFimColumns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportInvStockToFimColumns(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matAccountReportInvStockToFimService.queryMatAccountReportInvStockToFimColumns(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	
}
