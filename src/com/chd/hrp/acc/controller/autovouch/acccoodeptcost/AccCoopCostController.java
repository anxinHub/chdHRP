package com.chd.hrp.acc.controller.autovouch.acccoodeptcost;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCoopCostService;

@Controller
@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccoopcost")
public class AccCoopCostController extends BaseController {

	@Resource(name = "accCoopCostService")
	private AccCoopCostService accCoopCostService;

	@RequestMapping(value = "/accCoopCostMainPage", method = RequestMethod.GET)
	public String accCoopCostMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/acccoodepcost/acccoopcost/accCoopCostMain";
	}

	@RequestMapping(value = "/accCoopCostAddPage", method = RequestMethod.GET)
	public String accCoopCostAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("page_type", "add");
		mode.addAttribute("state", 1);
		return "hrp/acc/autovouch/acccoodepcost/acccoopcost/accCoopCostAdd";
	}

	@RequestMapping(value = "/accCoopCostUpdatePage", method = RequestMethod.GET)
	public String accCoopCostUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String, Object> map = accCoopCostService.queryAccCoopCostByCode(mapVo);
		mode.addAllAttributes(map);
		mode.addAttribute("page_type", "update");
		return "hrp/acc/autovouch/acccoodepcost/acccoopcost/accCoopCostAdd";
	}

	/**
	 * 查询主页面数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/queryAccCoopCost", method = RequestMethod.POST)
	public Map<String, Object> queryAccCoopCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.queryAccCoopCost(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询添加修改页面的科室列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccCoopCostDept", method = RequestMethod.POST)
	public Map<String, Object> queryAccCoopCostDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.queryAccCoopCostDetail(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询添加修改页面的外来单位列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccCoopObj", method = RequestMethod.POST)
	public Map<String, Object> queryAccCoopObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.queryAccCoopObj(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAccCoopCost", method = RequestMethod.POST)
	public Map<String, Object> saveAccCoopCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.saveAccCoopCost(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 审核与取消审核
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/auditAccCoopCost", method = RequestMethod.POST)
	public Map<String, Object> auditAccCoopCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.auditAccCoopCost(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 删除
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAccCoopCost", method = RequestMethod.POST)
	public Map<String, Object> deleteAccCoopCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.deleteAccCoopCost(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 计算
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccCoopCostDetailCount", method = RequestMethod.POST)
	public Map<String, Object> queryAccCoopCostDetailCount(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.queryAccCoopCostDetailCount(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 计算
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/collectAccCoopCost", method = RequestMethod.POST)
	public Map<String, Object> collectAccCoopCost(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCoopCostService.collectAccCoopCost(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

//	/**
//	 * 删除
//	 * 
//	 * @param mapVo
//	 * @param mode
//	 * @return
//	 * @throws Exception
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/deleteAccCoopCostDetail", method = RequestMethod.POST)
//	public Map<String, Object> deleteAccCoopCostDetail(@RequestParam Map<String, Object> mapVo, Model mode)
//			throws Exception {
//		try {
//			mapVo.put("group_id", SessionManager.getGroupId());
//			mapVo.put("hos_id", SessionManager.getHosId());
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//
//			String reJson = accCoopCostService.deleteAccCoopCostDetail(mapVo);
//			return JSONObject.parseObject(reJson);
//		} catch (Exception e) {
//			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
//		}
//	}

	/**
	 * 查询制单人
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryCoopCostMaker", method = RequestMethod.POST)
	@ResponseBody
	public String queryCoopCostMaker(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return accCoopCostService.queryCoopCostMaker(mapVo);
	}

	/**
	 * 查询合作项目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryAccCoopProjDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccCoopProjDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return accCoopCostService.queryAccCoopProjDict(mapVo);
	}

}
