/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.wagedata.AccDeptWageItemService;

/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccDeptWageItemController extends BaseController{
	private static Logger logger = Logger.getLogger(AccDeptWageItemController.class);
	
	
	@Resource(name = "accDeptWageItemService")
	private final AccDeptWageItemService accDeptWageItemService = null;
   
    
	/**
	 * 【工资管理-工资数据-部门工资查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accdeptwageitem/accDeptWageItemMainPage", method = RequestMethod.GET)
	public String accDeptWageItemMainPage(Model mode,Map<String, Object> mapVo) throws Exception {
		String wage_year_month = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage();
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accdeptwageitem/accDeptWageItemMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accdeptwageitem/accDeptWageSumMainPage", method = RequestMethod.GET)
	public String accDeptWageSumMainPage(Model mode,Map<String, Object> mapVo) throws Exception {
		String wage_year_month = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage();
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		
		return "hrp/acc/accdeptwageitem/accDeptWageSumMain";

	}
	
	/**
	*工资数据<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accdeptwageitem/queryAccDeptWageItem", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccDeptWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWagePay = accDeptWageItemService.queryAccDeptWageItem(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accdeptwageitem/queryAccDeptWageSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccDeptWageSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWagePay = accDeptWageItemService.queryAccDeptWageSum(mapVo);

		return JSONObject.parseObject(accWagePay);
		
	}
	
	@RequestMapping(value={"/hrp/acc/accdeptwageitem/accDeptWageItemSumMainPage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String accDeptWageItemSumMainPage(Model mode) throws Exception
	  {
	    return "hrp/acc/accdeptwageitem/accDeptWageItemSumMain";
	  }

	  @RequestMapping(value={"/hrp/acc/accdeptwageitem/queryAccDeptWageItemSum"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public Map<String, Object> queryAccDeptWageItemSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    mapVo.put("group_id", SessionManager.getGroupId());

	    mapVo.put("hos_id", SessionManager.getHosId());

	    mapVo.put("copy_code", SessionManager.getCopyCode());

	    mapVo.put("acc_year", SessionManager.getAcctYear());

	    String accWagePay = this.accDeptWageItemService.queryAccDeptWageItemSum(getPage(mapVo));

	    return JSONObject.parseObject(accWagePay);
	  }
}
