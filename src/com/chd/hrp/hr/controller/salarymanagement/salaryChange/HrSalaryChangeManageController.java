package com.chd.hrp.hr.controller.salarymanagement.salaryChange;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.salarymanagement.salaryChange.HrSalaryChangeManageService;
/**
 * 薪资管理-薪资变动-薪资变动管理
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/salaryChange")
public class HrSalaryChangeManageController extends BaseController {

	private static Logger logger = Logger.getLogger(HrSalaryChangeManageController.class);
	
	@Resource(name = "hrSalaryChangeManageService")
	private final HrSalaryChangeManageService hrSalaryChangeManageService = null;
	
	//薪资变动管理主页面跳转
	@RequestMapping(value = "/salaryChangeManageMainPage", method = RequestMethod.GET)
	public String salaryChangeManageMainPage() throws Exception{
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeManage/salaryChangeManageMain";
	}
	
	//薪资变动管理添加页面跳转
	@RequestMapping(value = "/salaryChangeManageAddPage", method = RequestMethod.GET)
	public String salaryChangeManageAddPage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		//model.addAttribute("change_code",hrSalaryChangeManageService.querySalaryChangeManageChangecode());
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeManage/salaryChangeManageAdd";
	}
	
	//薪资变动管理修改页面跳转
	@RequestMapping(value = "/salaryChangeManageUpdatePage", method = RequestMethod.GET)
	public String salaryChangeManageUpdatePage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		model.addAttribute("vo", hrSalaryChangeManageService.queryUpdateSalaryChangeManage(mapVo));
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeManage/salaryChangeManageUpdate";
	}
	
	//薪资变动管理变动类型下拉框加载
	@RequestMapping(value = "/querySalaryChangeTypeOption", method = RequestMethod.POST)
	@ResponseBody
	public String querySalaryChangeTypeOption(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeManageService.querySalaryChangeTypeOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	//薪资变动管理变动项目下拉框加载
	@RequestMapping(value = "/querySalaryChangeTypeChangeProjectOption", method = RequestMethod.POST)
	@ResponseBody
	public String querySalaryChangeTypeChangeProjectOption(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeManageService.querySalaryChangeTypeChangeProjectOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	//薪资变动管理变动项目级联变动后
	@RequestMapping(value = "/queryValueaftOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryValueaftOption(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeManageService.queryValueaftOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	//薪资变动管理职工下拉加载
	@RequestMapping(value = "/querySalaryManageEmpOption", method = RequestMethod.POST)
	@ResponseBody
	public String querySalaryManageEmpOption(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeManageService.querySalaryManageEmpOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	//薪资变动管理添加
	@RequestMapping(value = "/addSalaryManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSalaryManage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.addSalaryManage(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理查询
	@RequestMapping(value = "/querySalaryChangeManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySalaryChangeManage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.querySalaryChangeManage(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理删除
	@RequestMapping(value = "/deleteSalaryChangeManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSalaryChangeManage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.deleteSalaryChangeManage(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理审核
	@RequestMapping(value = "/updateSalaryChangeManageSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSalaryChangeManageSubmit(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("state", 1);
			return JSON.parseObject(hrSalaryChangeManageService.updateSalaryChangeManageSubmit(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理取消审核
	@RequestMapping(value = "/updateSalaryChangeManageCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSalaryChangeManageCancel(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("state", 0);
			return JSON.parseObject(hrSalaryChangeManageService.updateSalaryChangeManageSubmit(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理修改回显变动项目
	@RequestMapping(value = "/queryUpdateSalaryChangeTypeSalaryProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUpdateSalaryChangeTypeSalaryProject(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.queryUpdateSalaryChangeTypeSalaryProject(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理修改
	@RequestMapping(value = "/updateSalaryManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSalaryManage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.updateSalaryManage(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//薪资变动管理修改回显工资项目数据
	@RequestMapping(value = "/queryUpdateSalaryChangeTypeSalaryManageProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUpdateSalaryChangeTypeSalaryManageProject(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("user_id", SessionManager.getUserId());
			
			return JSON.parseObject(hrSalaryChangeManageService.queryUpdateSalaryChangeTypeSalaryManageProject(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
}
