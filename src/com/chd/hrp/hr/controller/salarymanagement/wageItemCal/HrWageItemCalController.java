package com.chd.hrp.hr.controller.salarymanagement.wageItemCal;

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
import com.chd.hrp.hr.service.salarymanagement.wageItemCal.HrWageItemCalService;

/**
 * 【薪资管理-薪资方案】：工资项取值方法
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/wageItemCal")
public class HrWageItemCalController extends BaseController {

	private static Logger logger = Logger.getLogger(HrWageItemCalController.class);
	
	@Resource(name = "hrWageItemCalService")
	private final HrWageItemCalService hrWageItemCalService = null;
	
	// 工资项取值方法主页面
	@RequestMapping(value = "/hrWageItemCalMainPage", method = RequestMethod.GET)
	public String hrWageItemCalMainPage() throws Exception{
		return "hrp/hr/salarymanagement/wageItemCal/hrWageItemCalMain";
	}
	
	// 工资项取值方法添加（进入编辑页）
	@RequestMapping(value = "/hrWageItemCalAddPage", method = RequestMethod.GET)
	public String hrWageItemCalEditPage() throws Exception{
		return "hrp/hr/salarymanagement/wageItemCal/hrWageItemCalEdit";
	}
	
	// 工资项取值方法修改（进入编辑页）
	@RequestMapping(value = "/hrWageItemCalUpdatePage", method = RequestMethod.GET)
	public String hrWageItemCalUpdatePage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		mode.addAttribute("hrWageItemCal", hrWageItemCalService.getHrWageItemCalByPK(paramMap));
		return "hrp/hr/salarymanagement/wageItemCal/hrWageItemCalEdit";
	}
	
	// 页面主查询
	@RequestMapping(value = "/queryHrWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrWageItemCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String json = hrWageItemCalService.queryHrWageItemCal(getPage(paramMap));
			return JSONObject.parseObject(json);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 添加
	@RequestMapping(value = "/addHrWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public String addHrWageItemCal(@RequestParam Map<String, Object> paramMap, Model mode) 
			throws Exception{
		try {
			return hrWageItemCalService.addHrWageItemCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 删除
	@RequestMapping(value = "/deleteHrWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrWageItemCal(@RequestParam Map<String, Object> paramMap, Model mode) 
			throws Exception{
		try {
			return hrWageItemCalService.deleteHrWageItemCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 更新
	@RequestMapping(value = "/updateHrWageItemCal", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrWageItemCal(@RequestParam Map<String, Object> paramMap, Model mode) 
			throws Exception{
		try {
			return hrWageItemCalService.updateHrWageItemCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 提供职工分类下拉选（带“全部”选项）
	@RequestMapping(value = "/empKindSelect", method = RequestMethod.POST)
	@ResponseBody
	public String empKindSelect(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		return hrWageItemCalService.empKindSelect(paramMap);
	}
	
	// 取值方法是计算公式时，左侧树
	@RequestMapping(value = "/queryHrWageItemCalTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrWageItemCalTree(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		String json = hrWageItemCalService.queryHrWageItemCalTree(paramMap);
		return json;
	}
	
	// 取值函数参数选择页
	@RequestMapping(value = "/hrWageTaxSetPage", method = RequestMethod.GET)
	public String hrWageTaxSetPage(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		mode.addAttribute("ele_code", paramMap.get("ele_code").toString());
		return "hrp/hr/salarymanagement/wageItemCal/hrWageTaxSet";
	}
}
