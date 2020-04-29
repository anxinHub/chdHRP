/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.purchase.query;
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
import com.chd.hrp.mat.service.purchase.query.MatPurchaseQueryService;
/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatPurchaseQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPurchaseQueryController.class);
	
	//引入Service服务
	@Resource(name = "matPurchaseQueryService")
	private final MatPurchaseQueryService matPurchaseQueryService = null;
	
	/**
	 * 采购计划汇总查询(材料)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/query/matPurInvReportPage", method = RequestMethod.GET)
	public String matDeptStatQToDeptPage(Model mode) throws Exception {
		return "hrp/mat/purchase/query/matPurInvReport";
	}
	
	/**
	 * 科室需求计划汇总查询(科室)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/purchase/query/queryMatPurInvReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurInvReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matJson = matPurchaseQueryService.queryMatPurInvReport(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
}

