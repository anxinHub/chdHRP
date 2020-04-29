/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.acc.service.wagedata.AccPeopleWageItemService;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

/**
* @Title. @Description. 
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccPeopleWageItemController extends BaseController{
	private static Logger logger = Logger.getLogger(AccPeopleWageItemController.class);
	
	@Resource(name = "accPeopleWageItemService")
	private final AccPeopleWageItemService accPeopleWageItemService = null;
   
	/**
	 * 【工资管理-工资数据-个人工资查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleWageItemMainPage", method = RequestMethod.GET)
	public String accPeopleWageItemMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accpeoplewageitem/accPeopleWageItemMain";
	}
	
	/**
	*工资数据<BR>
	*维护页面跳转accWagePayAddPage
	*/
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accWageSchemeAddPage", method = RequestMethod.GET)
	public String accWageSchemeAddPage(Model mode) throws Exception {

		return "hrp/acc/accpeoplewageitem/accWageSchemeAdd";

	}
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleWageItemAddPage", method = RequestMethod.GET)
	public String accPeopleWageItemAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accpeoplewageitem/accPeopleWageItemAdd";

	}
	
	/**
	 * 工资数据<BR>
	 * 查询
	 * 【工资管理-工资数据-个人工资查询】：右侧表格查询
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/queryAccPeopleWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPeopleWageItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accWagePay = accPeopleWageItemService.queryAccPeopleWageItem(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);

	}
	
	/**
	*工资数据<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleWageItemUpdatePage", method = RequestMethod.GET)
	
	public String accPeopleWageItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accpeoplewageitem/accPeopleWageItemUpdate";
	}
	
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleWageSchemeUpdate", method = RequestMethod.GET)
	public String accPeopleWageSchemeUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accpeoplewageitem/accPeopleWageSchemeUpdate";
	}
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/updatePeopleAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePeopleAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWagePayJson = accPeopleWageItemService.updateAccPeopleWageItem(mapVo);
		
		return JSONObject.parseObject(accWagePayJson);
	}
	
	/**
	 * 【工资管理-工资数据-员工工资查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeoplePayMainPage", method = RequestMethod.GET)
	public String accPeoplePayMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accpeoplewageitem/accPeoplePayMain";
	}
	
	/**
	 * 【工资管理-工资数据-员工工资查询】：页面主查询
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/queryAccPeoplePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPeoplePay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        String accWagePay = accPeopleWageItemService.queryAccPeoplePay(mapVo);
		return JSONObject.parseObject(accWagePay);
	}
	
	/**
	 * 【工资管理-工资数据-员工薪资查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleSalaryMainPage", method = RequestMethod.GET)
	public String accPeopleSalaryMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accpeoplewageitem/accPeopleSalaryMain";

	}
	
	/**
	 * 【工资管理-工资数据-员工餐费查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleMealsMainPage", method = RequestMethod.GET)
	public String accPeopleMealsMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accpeoplewageitem/accPeopleMealsMain";

	}
	
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/accPeopleBonusMainPage", method = RequestMethod.GET)
	public String accPeopleBonusMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage());
		return "hrp/acc/accpeoplewageitem/accPeopleBonusMain";

	}
	
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/queryAccPeopleSalary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPeopleSalary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
        String accWagePay = accPeopleWageItemService.queryAccPeoplePay(mapVo);

		return JSONObject.parseObject(accWagePay);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accpeoplewageitem/queryAccPeopleMeals", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPeopleMeals(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
        String accWagePay = accPeopleWageItemService.queryAccPeoplePayMeals(mapVo);

		return JSONObject.parseObject(accWagePay);
		
	}
	
}

