package com.chd.hrp.med.controller.purchase.purquery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

/**
 * 
 * @Description:
 * 08113 采购计划统计查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedPurMainReqInquireController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPurMainReqInquireController.class);
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/med/purchase/purquery/medPurMainReqInquireMainPage", method = RequestMethod.GET)
	public String medPurMainReqInquireMainPage(Model mode) throws Exception {

		return "hrp/med/purchase/purquery/medPurMainReqInquireMain";

	}
}
