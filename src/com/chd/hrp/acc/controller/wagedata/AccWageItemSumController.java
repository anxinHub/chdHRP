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
import com.chd.hrp.acc.service.wagedata.AccWageItemSumService;

/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageItemSumController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccWageItemSumController.class);
	
	@Resource(name = "accWageItemSumService")
	private final AccWageItemSumService accWageItemSumService = null;
   
	/**
	 * 【工资管理-工资数据-工资综合查询】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accwageitemsum/accWageItemSumMainPage", method = RequestMethod.GET)
	public String accWageItemSumMainPage(Model mode) throws Exception {
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accwageitemsum/accWageItemSumMain";
	}
	
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwageitemsum/accWageItemSumAddPage", method = RequestMethod.GET)
	public String accWageItemSumAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwageitemsum/accWageItemSum";

	}
	
	@RequestMapping(value = "/hrp/acc/accwageitemsum/accWageItemSumSchemeUpdate", method = RequestMethod.GET)
	public String accWageItemSumSchemeUpdate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accwageitemsum/accWageItemSumSchemeUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/accwageitemsum/accWageSchemeAddPage", method = RequestMethod.GET)
	public String accWageSchemeAddPage(Model mode) throws Exception {

		return "hrp/acc/accwageitemsum/accWageSchemeAdd";

	}
	/**
	*工资数据<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accwageitemsum/queryAccWageItemSum", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccWageItemSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String accWagePay = accWageItemSumService.queryAccWageItemSum(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);
		
	}
	
}

