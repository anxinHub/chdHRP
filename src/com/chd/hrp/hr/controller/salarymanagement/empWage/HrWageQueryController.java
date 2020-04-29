package com.chd.hrp.hr.controller.salarymanagement.empWage;

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
import com.chd.hrp.hr.service.salarymanagement.empWage.HrWageCheckComputeService;
import com.chd.hrp.hr.service.salarymanagement.empWage.HrWageQueryService;
/**
 * 薪资管理-职工薪资-薪资查询
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/empWage")
public class HrWageQueryController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrWageQueryController.class);
	// 引入Service
		@Resource(name = "hrWageQueryService")
		private final HrWageQueryService hrWageQueryService = null;
	// 进入薪资核算主页面
	@RequestMapping(value = "/wageQueryMainPage", method = RequestMethod.GET)
	public String salaryQueryMainPage() throws Exception{
		return "hrp/hr/salarymanagement/empWage/wageQueryMainPage";
	}
	
	
	
	// 薪资查询返回前台grid表格内容
	@RequestMapping(value = "/queryWageCheckQueryGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageCheckQueryGrid(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			String jsonStr = hrWageQueryService.queryWageCheckQueryGrid(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	//
	/**
	 * 获取form表单
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryWageQueryForm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageQueryForm(@RequestParam Map<String, Object> mapVo) throws Exception {

	

	
		// 是否为修改页面，是则先查询数据


		int colNum = Integer.parseInt(MyConfig.getSysPara("06001"));

		String formEle = hrWageQueryService.queryWageQueryForm(mapVo, colNum);
		
		
		return JSONObject.parseObject("{\"data\":"+formEle+"}");
	}
}
