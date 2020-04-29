/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.reqsearch;
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
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.reqsearch.MatRequireReqSearchService;
/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatRequireReqSearchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatRequireReqSearchController.class);
	
	//引入Service服务
	@Resource(name = "matRequireReqSearchService")
	private final MatRequireReqSearchService matRequireReqSearchService = null;
	
	/**
	 * 科室需求计划汇总查询(科室)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/reqsearch/matDeptStatQToDeptPage", method = RequestMethod.GET)
	public String matDeptStatQToDeptPage(Model mode) throws Exception {
		return "hrp/mat/require/reqsearch/matDeptStatQToDeptPage";
	}
	
	/**
	 * 科室需求计划汇总查询(科室)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/reqsearch/queryMatDeptStatQToDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptStatQToDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matRequireMainJson = matRequireReqSearchService.queryMatDeptStatQToDept(getPage(mapVo));
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * 科室需求计划汇总查询(材料)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/reqsearch/matDeptStatQToInvPage", method = RequestMethod.GET)
	public String matDeptStatQToInvPage(Model mode) throws Exception {
		return "hrp/mat/require/reqsearch/matDeptStatQToInvPage";

	}
	
	/**
	 * 科室需求计划明细查询主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/reqsearch/matDeptStatPlanDetailPage", method = RequestMethod.GET)
	public String matDeptStatPlanDetailPage(Model mode) throws Exception {
		return "hrp/mat/require/reqsearch/matDeptStatPlanDetailPage";

	}
	
	/**
	 * 科室需求计划汇总表(科室及类别)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/reqsearch/matDeptStatQDetpToTypePage", method = RequestMethod.GET)
	public String matDeptStatQDetpToTypePage(Model mode) throws Exception {
		return "hrp/mat/require/reqsearch/matDeptStatQDetpToTypePage";

	}
	
	
}

