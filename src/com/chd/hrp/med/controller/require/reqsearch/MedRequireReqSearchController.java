/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.require.reqsearch;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.reqsearch.MedRequireReqSearchService;
/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedRequireReqSearchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedRequireReqSearchController.class);
	
	//引入Service服务
	@Resource(name = "medRequireReqSearchService")
	private final MedRequireReqSearchService medRequireReqSearchService = null;
   
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	/**
	 * 科室需求计划汇总查询(科室)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/reqsearch/medDeptStatQToDeptPage", method = RequestMethod.GET)
	public String medDeptStatQToDeptPage(Model mode) throws Exception {
		return "hrp/med/require/reqsearch/medDeptStatQToDeptPage";

	}
	
	/**
	 * 科室需求计划汇总查询(材料)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/reqsearch/medDeptStatQToInvPage", method = RequestMethod.GET)
	public String medDeptStatQToInvPage(Model mode) throws Exception {
		return "hrp/med/require/reqsearch/medDeptStatQToInvPage";

	}
	
	/**
	 * 科室需求计划明细查询主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/reqsearch/medDeptStatPlanDetailPage", method = RequestMethod.GET)
	public String medDeptStatPlanDetailPage(Model mode) throws Exception {
		return "hrp/med/require/reqsearch/medDeptStatPlanDetailPage";

	}
	
	/**
	 * 科室需求计划汇总表(科室及类别)主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/reqsearch/medDeptStatQDetpToTypePage", method = RequestMethod.GET)
	public String medDeptStatQDetpToTypePage(Model mode) throws Exception {
		return "hrp/med/require/reqsearch/medDeptStatQDetpToTypePage";

	}
	
	
}

