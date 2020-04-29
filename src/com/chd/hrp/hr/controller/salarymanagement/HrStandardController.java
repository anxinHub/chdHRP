package com.chd.hrp.hr.controller.salarymanagement;

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
import com.chd.hrp.hr.service.salarymanagement.HrStandardService;

@Controller
public class HrStandardController extends BaseController{

	private static Logger logger = Logger.getLogger(HrStandardController.class);
	
	@Resource(name = "hrStandardService")
	private final HrStandardService hrStandardService = null;

	/**
	 * 薪资标准主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/standardMainPage", method = RequestMethod.GET)
	public String StandardPage() throws Exception {
		return "hrp/hr/salarymanagement/standard/standardMain";
	}
	
	/**
	 * 薪资标准添加页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/standardAddPage", method = RequestMethod.GET)
	public String standardAddPage() throws Exception {
		return "hrp/hr/salarymanagement/standard/standardAdd";
	}
	
	/**
	 * 薪资标准维护页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/standardMaintainPage", method = RequestMethod.GET)
	public String standardMaintainPage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		model.addAttribute("vo",mapVo);
		return "hrp/hr/salarymanagement/standard/standardMaintain";
	}
	
	/**
	 * 数据表下拉加载--当前模块停止使用，供其他模块使用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardTabCodeOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardTabCodeOption(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardTabCodeOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 数据表下拉加载--只查询人员基本情况
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardTabCodeOptionEditable", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardTabCodeOptionEditable(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardTabCodeOptionEditable(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 关联字段下拉加载--当前模块停止使用，供其他模块使用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardColCodeOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardColCodeOption(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardColCodeOption(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 关联字段下拉加载--只查询人员基本情况的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardColCodeOptionEditable", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardColCodeOptionEditable(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardColCodeOptionEditable(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 薪资标准添加
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/addStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStandard(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.addStandard(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryStandard(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.queryStandard(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/deleteStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteStandard(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.deleteStandard(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准修改页面跳转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/standardUpdatePage", method = RequestMethod.GET)
	public String standardUpdatePage(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = hrStandardService.queryStandardUpdate(mapVo);
		
		model.addAttribute("vo",map);
		
		return "hrp/hr/salarymanagement/standard/standardUpdate";
	}
	
	/**
	 * 薪资标准修改
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/updateStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateStandard(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.updateStandard(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准修改回显明细
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardCtd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryStandardCtd(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.queryStandardCtd(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准复制
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/addCopyStandard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addCopyStandard(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.addCopyStandard(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准维护下拉查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardMaintain", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardMaintain(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardMaintain(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准维护下拉查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryStandardRankodeOption", method = RequestMethod.POST)
	@ResponseBody
	public String queryStandardRankodeOption(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(hrStandardService.queryStandardRankodeOption(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	
	/**
	 * 薪资标准维护添加
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/addMaintain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMaintain(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.addMaintain(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 薪资标准维护查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/standard/queryMaintain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMaintain(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(hrStandardService.queryMaintain(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
}
