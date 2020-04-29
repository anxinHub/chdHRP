/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcost.wage;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;
/**
 * 
 * @Description:
 * 医疗收入执行数据
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WageReduceBudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WageReduceBudgController.class);
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/accwagepaymodi/accWagePayModiMainPage2", method = RequestMethod.GET)
	public String budgMedInExecuteMainPage(Model mode) throws Exception {

		return "hrp/acc/accwagepaymodi/accWagePayModiMain";

	}

    
}

