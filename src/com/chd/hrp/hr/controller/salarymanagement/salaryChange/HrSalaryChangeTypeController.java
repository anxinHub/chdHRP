package com.chd.hrp.hr.controller.salarymanagement.salaryChange;

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
import com.chd.hrp.hr.service.salarymanagement.salaryChange.HrSalaryChangeTypeService;

@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/salaryChange")
public class HrSalaryChangeTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(HrSalaryChangeTypeController.class);
	
	@Resource(name = "hrSalaryChangeTypeService")
	private final HrSalaryChangeTypeService hrSalaryChangeTypeService = null;
	
	//薪资变动类型主页面跳转
	@RequestMapping(value = "/salaryChangeTypeMainPage", method = RequestMethod.GET)
	public String salaryChangeTypeMainPage() throws Exception{
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeType/salaryChangeTypeMain";
	}
	
	//薪资变动类型添加页面跳转
	@RequestMapping(value = "/salaryChangeTypeAddPage", method = RequestMethod.GET)
	public String salaryChangeTypeAdd() throws Exception{
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeType/salaryChangeTypeAdd";
	}
	
	//薪资变动类型修改页面跳转
	@RequestMapping(value = "/salaryChangeTypeUpdatePage", method = RequestMethod.GET)
	public String salaryChangeTypeUpdatePage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception{
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo = hrSalaryChangeTypeService.queryUpdateSalaryChangeType(mapVo);
		
		model.addAttribute("vo",mapVo);
		
		return "hrp/hr/salarymanagement/salaryChange/salaryChangeType/salaryChangeTypeUpdate";
	}
	
	/**
	 * 工资项目下拉加载
	 */
	@RequestMapping(value = "/queryItemOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryItemOption(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeTypeService.queryItemOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 工资项编码下拉加载
	 */
	@RequestMapping(value = "/queryPlancodeOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryPlancodeOption(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrSalaryChangeTypeService.queryPlancodeOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 薪资变动类型添加
	 */
	@RequestMapping(value = "/addSalaryChangeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addSalaryChangeType(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.addSalaryChangeType(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资变动类型查询
	 */
	@RequestMapping(value = "/querySalaryChangeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> querySalaryChangeType(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.querySalaryChangeType(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资变动类型查询
	 */
	@RequestMapping(value = "/deleteSalaryChangeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteSalaryChangeType(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.deleteSalaryChangeType(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资变动类型,变动项目表格回显
	 */
	@RequestMapping(value = "/querySalaryChangeTypeChangeProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> querySalaryChangeTypeChangeProject(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.querySalaryChangeTypeChangeProject(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资变动类型,工资项目表格回显
	 */
	@RequestMapping(value = "/querySalaryChangeTypeSalaryProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> querySalaryChangeTypeSalaryProject(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.querySalaryChangeTypeSalaryProject(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资变动类型修改
	 */
	@RequestMapping(value = "/updateSalaryChangeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateSalaryChangeType(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrSalaryChangeTypeService.updateSalaryChangeType(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
}
