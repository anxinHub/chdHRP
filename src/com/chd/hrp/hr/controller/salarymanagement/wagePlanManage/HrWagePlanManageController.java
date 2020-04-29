package com.chd.hrp.hr.controller.salarymanagement.wagePlanManage;

import java.util.List;
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.report.SuperReportDesignService;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlan;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlanKind;
import com.chd.hrp.hr.service.record.HosEmpKindService;
import com.chd.hrp.hr.service.salarymanagement.wagePlanManage.HrWagePlanManageService;
import com.chd.hrp.hr.service.sysstruc.HrStoreTypeService;

/**
 * 薪资管理-薪资方案管理
 * 
 * @author yangyunfei
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/wagePlanManage")
public class HrWagePlanManageController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrWagePlanManageController.class);
	
	// 引入service
	@Resource(name = "hrWagePlanManageService")
	private final HrWagePlanManageService hrWagePlanManageService = null;
	
	@Resource(name = "hosEmpKindService")
	private final HosEmpKindService hosEmpKindService = null;
	
	@Resource(name = "hrStoreTypeService")
	private final HrStoreTypeService hrStoreTypeService = null;
	
	@Resource(name = "superReportDesignService")
	private final SuperReportDesignService superReportDesignService = null;

	// 进入薪资方案管理主页面
	@RequestMapping(value = "/wagePlanManageMainPage", method = RequestMethod.GET)
	public String wagePlanManageMainPage() throws Exception {
		return "hrp/hr/salarymanagement/wagePlanManage/wagePlanManageMainPage";
	}

	// 薪资方案主查询
	@RequestMapping(value = "/queryWagePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWagePlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try{
			String jsonStr = hrWagePlanManageService.queryWagePlan(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 进入薪资方案编辑页面（添加）
	@RequestMapping(value = "/wagePlanAddPage", method = RequestMethod.GET)
	public String wagePlanAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		// 取职工分类
		mode.addAttribute("emp_kinds", hosEmpKindService.queryEmpKind(mapVo));
		return "hrp/hr/salarymanagement/wagePlanManage/hrWagePlanEditPage";
	}
	
	// 添加薪资方案
	@RequestMapping(value = "addWagePlan", method = RequestMethod.POST)
	@ResponseBody
	public String addWagePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			return hrWagePlanManageService.addWagePlan(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 删除薪资方案
	@RequestMapping(value = "/deleteWagePlan", method = RequestMethod.POST)
	@ResponseBody
	public String deleteWagePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			String msg = hrWagePlanManageService.deleteWagePlan(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 打开薪资方案编辑页（更新）
	@RequestMapping(value = "/wagePlanUpdatePage", method = RequestMethod.GET)
	public String wagePlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		// 取职工分类
		mode.addAttribute("emp_kinds", hosEmpKindService.queryEmpKind(mapVo));
		HrWagePlan wagePlan = hrWagePlanManageService.findHrWagePlan(mapVo);
		List<HrWagePlanKind> kinds = hrWagePlanManageService.findHrWagePlanKindByFK(mapVo);
		if(kinds.size() > 0){
			wagePlan.setEmpKindList(kinds);
		}
		mode.addAttribute("hrWagePlan", wagePlan);
		return "hrp/hr/salarymanagement/wagePlanManage/hrWagePlanEditPage";
	}
	
	// 更新薪资方案
	@RequestMapping(value = "updateWagePlan", method = RequestMethod.POST)
	@ResponseBody
	public String updateWagePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			return hrWagePlanManageService.updateWagePlan(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	
	// 工资项主查询
	@RequestMapping(value = "/queryWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try{
			String jsonStr = hrWagePlanManageService.queryWageItem(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 进入工资项编辑页面（添加）
	@RequestMapping(value = "/wageItemAddPage", method = RequestMethod.GET)
	public String wageItemAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		return "hrp/hr/salarymanagement/wagePlanManage/hrWageItemEditPage";
	}
	
	// 进入工资项编辑页面（更新）
	@RequestMapping(value = "/wageItemUpdatePage", method = RequestMethod.GET)
	public String wageItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("hrWageItem", hrWagePlanManageService.findHrWageItem(mapVo));
		return "hrp/hr/salarymanagement/wagePlanManage/hrWageItemEditPage";
	}
	
	// 添加工资项
	@RequestMapping(value = "addWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			String msg = hrWagePlanManageService.addWageItem(mapVo);
			return JSONObject.parseObject(msg);
			} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 删除工资项
	@RequestMapping(value = "deleteWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			String msg = hrWagePlanManageService.deleteWageItem(mapVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}

	// 更新工资项
	@RequestMapping(value = "updateHrWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			String msg = hrWagePlanManageService.updateHrWageItem(mapVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}
	
	// 下拉选薪资标准表取值
	@RequestMapping(value = "/selectHrWageStan", method = RequestMethod.POST)
	@ResponseBody
	public String selectHrWageStan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		return hrWagePlanManageService.selectHrWageStan(mapVo);
	}

	// 提供薪资方案下拉选数据
	@RequestMapping(value = "/wagePlanSelect", method = RequestMethod.POST)
	@ResponseBody
	public String wagePlanSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		return hrWagePlanManageService.wagePlanSelect(mapVo);
	}
	
	// 提供薪资方案下拉选数据
	@RequestMapping(value = "/wageItemSelect", method = RequestMethod.POST)
	@ResponseBody
	public String wageItemSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		return hrWagePlanManageService.wageItemSelect(mapVo);
	}
	
	// 提供工资项类型下拉选数据
	@RequestMapping(value = "/accWageItemTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String accWageItemTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		return hrWagePlanManageService.accWageItemTypeSelect(mapVo);
	}
	
	// 复制薪资方案
	@RequestMapping(value = "copyWagePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyWagePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		try {
			String msg = hrWagePlanManageService.copyWagePlan(mapVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}
	
	// 导入工资项
	@RequestMapping(value = "importHrWageItemData", method = RequestMethod.POST)
	@ResponseBody
	public String importHrWageItemData(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		return hrWagePlanManageService.importHrWageItemData(paramMap);
	}
	
}
