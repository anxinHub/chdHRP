/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.medinjx;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_MED_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedInJXController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedInJXController.class);
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/outside/budgMedInJXMainPage", method = RequestMethod.GET)
	public String budgMedInJXMainPage(Model mode) throws Exception {

		return "hrp/budg/project/outside/budgOutsideFundGetMain";

	}


    
}

