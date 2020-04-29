/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgquerygroup;
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
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.business.budgquery.busHosBusinessQuery.BudgWorkHosMService;
import com.chd.hrp.budg.service.business.budgquerygroup.BudgGroupBusService;
import com.chd.hrp.budg.service.business.budgquerygroup.BudgHosBusGroupService;
/**
 * 
 * @Description:
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgGroupBusController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgGroupBusController.class);
	
	//引入Service服务
	@Resource(name = "budgGroupBusService")
	private final BudgGroupBusService budgGroupBusService= null;
	//引入Service服务
	@Resource(name = "budgWorkHosMService")
	private final BudgWorkHosMService budgWorkHosMService = null;
	
	@Resource(name = "budgHosBusGroupService")
	private final BudgHosBusGroupService budgHosBusGroupService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/budgGroupBusMainPage", method = RequestMethod.GET)
	public String budgWorkHosMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/business/budgquerygroup/budgGroupBusMain";

	}

	/**
	 * @Description 
	 * 查询数据 集团业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/queryBudgGroupBus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		mapVo.put("hos_id", SessionManager.getHosId());
	        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkHosMonth = budgGroupBusService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 医院业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/queryBudgHosGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		mapVo.put("hos_id", SessionManager.getHosId());
	        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgWorkHosMonth = budgHosBusGroupService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
	
	/**
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/budgWorkGroupLinkPage", method = RequestMethod.GET)
	public String budgWorkCheckUpdatePage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		mode.addAttribute("month", mapVo.get("month"));
		return "hrp/budg/business/budgquerygroup/budgWorkGroupLinkSum";
		
	}
	
	/**
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/budgWorkGroupLinkPageSum", method = RequestMethod.GET)
	public String budgWorkHosLinkPageSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id",SessionManager.getHosId());
		mode.addAttribute("copy_code",SessionManager.getCopyCode());
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("sum_year", mapVo.get("sum_year"));
		mode.addAttribute("index_code", mapVo.get("index_code"));
		return "hrp/budg/business/budgquerygroup/budgWorkGroupLink";
		
	}

	/**
    * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/queryBudgWorkGroupMonthSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckDeptMonthSum(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgWorkHosMonth = budgWorkHosMService.queryGroupMonthCopy(getPage(mapVo));
		
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}

	/**
    * 预算值连接页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgquerygroup/queryBudgWorkCheckGroupYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryBudgWorkCheckGroupYear(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgWorkHosMonth = budgWorkHosMService.queryGroupYearCopy(getPage(mapVo));
		
		return JSONObject.parseObject(budgWorkHosMonth);
		
	}
				
}

