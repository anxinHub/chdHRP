package com.chd.hrp.hr.controller.salarymanagement.accumulationfund;

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
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrFundBaseCalService;
/**
 * 【薪资管理-公积金等】：缴费基数设置
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/accumulationfund/fundBaseCal")
public class HrFundBaseCalController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFundBaseCalController.class);
	
	// 引入service
	@Resource(name="hrFundBaseCalService")
	private final HrFundBaseCalService hrFundBaseCalService = null;
	
	// 主页面
	@RequestMapping(value = "/fundBaseCalSetMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception{
		return "hrp/hr/salarymanagement/accumulationfund/fundBaseCal/hrFundBaseCalSetMain";
	}
	
	// 公式编辑页面
	@RequestMapping(value = "/fundBaseCalEditPage", method = RequestMethod.GET)
	public String fundBaseCalEditPage() throws Exception{
		return "hrp/hr/salarymanagement/accumulationfund/fundBaseCal/hrFundBaseCalEdit";
	}
	
	// 查询
	@RequestMapping(value = "/queryHrFundBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrFundBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String jsonStr = hrFundBaseCalService.queryHrFundBaseCal(getPage(paramMap));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 删除
	@RequestMapping(value = "/deleteHrFundBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrFundBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrFundBaseCalService.deleteHrFundBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 添加
	@RequestMapping(value = "/addHrFundBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String addHrFundBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrFundBaseCalService.addHrFundBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 保存（更新）公式
	@RequestMapping(value = "/updateHrFundBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrFundBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrFundBaseCalService.updateHrFundBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 公式设置 左侧树
	@RequestMapping(value = "/queryFundBaseSetFunTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFundBaseSetFunTree(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		String json = hrFundBaseCalService.queryFundBaseSetFunTree(paramMap);
		return JSONObject.parseObject(json);
	}

}
