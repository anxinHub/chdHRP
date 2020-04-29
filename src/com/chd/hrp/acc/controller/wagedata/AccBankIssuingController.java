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
import com.chd.hrp.acc.service.wagedata.AccBankIssuingService;

/**
* @Title. @Description.
* 工资数据
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

 
@Controller
public class AccBankIssuingController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankIssuingController.class);
	
	
	@Resource(name = "accBankIssuingService")
	private final AccBankIssuingService accBankIssuingService = null;
   
	/**
	 * 【工资管理-工资发放-银行代发】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/accbankissuing/accBankIssuingMainPage", method = RequestMethod.GET)
	public String accBankIssuingMainPage(Model mode) throws Exception {
//		String wage_year_month = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage();
		mode.addAttribute("wage_year_month", DateUtil.dateToString(new Date(), "yyyyMM"));
		return "hrp/acc/accbankissuing/accBankIssuingMain";
	}
	
	/**
	*工资数据<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbankissuing/accBankIssuingSchemeUpdate", method = RequestMethod.GET)
	public String accBankIssuingSchemeUpdate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accbankissuing/accBankIssuingSchemeUpdate";

	}
	
	@RequestMapping(value = "/hrp/acc/accbankissuing/accBankIssuingAddPage", method = RequestMethod.GET)
	public String accBankIssuingAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/accbankissuing/accBankIssuingAdd";

	}
	
	/**
	*工资数据<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accbankissuing/queryAccBankIssuing", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccBankIssuing(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		
        String accWagePay = accBankIssuingService.queryAccBankIssuing(getPage(mapVo));

		return JSONObject.parseObject(accWagePay);
		
	}
	
	/**
	*工资数据<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accbankissuing/accBankIssuingUpdatePage", method = RequestMethod.GET)
	
	public String accBankIssuingUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       // AccWagePay accWagePay = new AccWagePay();
		/*AccWagePay = AccWagePayService.queryAccWagePayByCode(mapVo);
		mode.addAttribute("para_code", AccWagePay.getPara_code());
		mode.addAttribute("para_name", AccWagePay.getPara_name());
		mode.addAttribute("group_id", AccWagePay.getGroup_id());
		mode.addAttribute("hos_id", AccWagePay.getHos_id());
		mode.addAttribute("copy_code", AccWagePay.getCopy_code());
		mode.addAttribute("mod_code", AccWagePay.getMod_code());
		mode.addAttribute("para_type", AccWagePay.getPara_type());
		mode.addAttribute("para_json", AccWagePay.getPara_json());
		mode.addAttribute("para_value", AccWagePay.getPara_value());
		mode.addAttribute("note", AccWagePay.getNote());
		mode.addAttribute("is_stop", AccWagePay.getIs_stop());*/
		return "hrp/acc/accbankissuing/accBankIssuingUpdate";
	}
	
	@RequestMapping(value = "/hrp/acc/accbankissuing/accPeopleWageSchemeUpdate", method = RequestMethod.GET)
	public String accPeopleWageSchemeUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        //AccWageScheme accWageScheme = new AccWageScheme();
		/*AccWageScheme = AccWageSchemeService.queryAccWageSchemeByCode(mapVo);
		mode.addAttribute("para_code", AccWageScheme.getPara_code());
		mode.addAttribute("para_name", AccWageScheme.getPara_name());
		mode.addAttribute("group_id", AccWageScheme.getGroup_id());
		mode.addAttribute("hos_id", AccWageScheme.getHos_id());
		mode.addAttribute("copy_code", AccWageScheme.getCopy_code());
		mode.addAttribute("mod_code", AccWageScheme.getMod_code());
		mode.addAttribute("para_type", AccWageScheme.getPara_type());
		mode.addAttribute("para_json", AccWageScheme.getPara_json());
		mode.addAttribute("para_value", AccWageScheme.getPara_value());
		mode.addAttribute("note", AccWageScheme.getNote());
		mode.addAttribute("is_stop", AccWageScheme.getIs_stop());*/
		return "hrp/acc/accbankissuing/accPeopleWageSchemeUpdate";
	}
	/**
	*工资数据<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accbankissuing/updatePeopleAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePeopleAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String accWagePayJson = accBankIssuingService.updateAccBankIssuing(mapVo);
		
		return JSONObject.parseObject(accWagePayJson);
	}
	
}

