package com.chd.hrp.hr.controller.salarymanagement.empWage;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.hr.service.salarymanagement.empWage.HrWageCheckComputeService;

/**
 * 薪资管理-职工薪资-薪资核算
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/empWage")
public class HrWageCheckComputeController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrWageCheckComputeController.class);
	
	// 引入Service
	@Resource(name = "hrWageCheckComputeService")
	private final HrWageCheckComputeService hrWageCheckComputeService = null;
	
	// 进入薪资核算主页面
	@RequestMapping(value = "/wageCheckComputeMainPage", method = RequestMethod.GET)
	public String wageCheckComputeMainPage() throws Exception{
		return "hrp/hr/salarymanagement/empWage/wageCheckComputeMainPage";
	}
	
	// 薪资核算 生成
	@RequestMapping(value = "/generateEmpWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateEmpWage(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception{
		try{
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			
			String jsonStr = hrWageCheckComputeService.generateEmpWage(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 薪资核算返回前台grid表格表头
	@RequestMapping(value = "/queryWageCheckComputeHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageCheckComputeHead(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			String jsonStr = hrWageCheckComputeService.queryWageCheckComputeHead(mapVo);
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 薪资核算返回前台grid表格内容
	@RequestMapping(value = "/queryWageCheckComputeGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageCheckComputeGrid(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			String jsonStr = hrWageCheckComputeService.queryWageCheckComputeGrid(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 保存职工薪资
	@RequestMapping(value = "/saveEmpHrWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveEmpHrWage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			String jsonStr = hrWageCheckComputeService.saveEmpHrWage(mapVo);
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 删除职工薪资
	@RequestMapping(value = "/deleteEmpHrWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpHrWage(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception{
		try{
			String jsonStr = hrWageCheckComputeService.deleteEmpHrWage(paramMap);
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 职工薪资计算
	@RequestMapping(value = "/collectEmpHrWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectEmpHrWage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			
			String jsonStr = hrWageCheckComputeService.collectEmpHrWage(getPage(mapVo));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 查符合薪资方案有关联的职工分类的职工
	@RequestMapping(value = "queryEmpByWagePlanEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpByWagePlanEmpKind(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		return hrWageCheckComputeService.queryEmpByWagePlanEmpKind(paramMap);
	}
	
	/**
	 * 导入数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importData", method = RequestMethod.POST)
	@ResponseBody
	public String importCheckCompute(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrWageCheckComputeService.importCheckCompute(mapVo);
		return reJson;
	}
	//
	/**
	 * 查工资项目
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wageItemSelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String wageItemSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = hrWageCheckComputeService.wageItemSelect(mapVo);
		return json;
	}
	
	// 批量修改
	@RequestMapping(value = "/updateItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String jsonStr = hrWageCheckComputeService.updateItem(mapVo);
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
