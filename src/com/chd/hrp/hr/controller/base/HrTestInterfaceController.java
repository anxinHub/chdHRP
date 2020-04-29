package com.chd.hrp.hr.controller.base;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

/**
 * 
 * @ClassName: HrTestInterfaceController 
 * @Description: 接口测试
 * @author zn 
 * @date 2017年10月24日 上午10:31:35 
 * 
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/base")
public class HrTestInterfaceController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTestInterfaceController.class);

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/testInterfacePage", method = RequestMethod.GET)
	public String testInterfacePage(Model mode) throws Exception {
		return "hrp/hr/base/testInterface";
	}
	
}
