package com.chd.hrp.acc.controller.autovouch.accpubCost;

import java.io.IOException;
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
import com.chd.hrp.acc.service.autovouch.accpubCost.AccPubCostRegService;

@Controller
public class AccPubCostRegController extends BaseController {

	@Resource(name = "accPubCostRegService")
	private AccPubCostRegService accPubCostRegService;

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegMainPage", method = RequestMethod.GET)
	public String pubCostRegMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/accpubCost/pubcostreg/pubCostRegMain";
	}

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegAddPage", method = RequestMethod.GET)
	public String pubCostRegAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("page_type", "add");
		mode.addAttribute("state", 1);
		return "hrp/acc/autovouch/accpubCost/pubcostreg/pubCostRegAdd";
	}

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegDeptAddPage", method = RequestMethod.GET)
	public String pubCostRegDeptAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/accpubCost/pubcostreg/pubCostRegDeptAdd";
	}

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubcost/pubcostreg/pubCostRegUpdatePage", method = RequestMethod.GET)
	public String pubCostRegUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String, Object> map = accPubCostRegService.queryAccPubCostRegByCode(mapVo);
		mode.addAllAttributes(map);
		mode.addAttribute("page_type", "update");
		return "hrp/acc/autovouch/accpubCost/pubcostreg/pubCostRegAdd";
	}

	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubcost/pubcostreg/pubCostDeptImportPage", method = RequestMethod.GET)
	public String accPubCostDeptImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("year_month", mapVo.get("year_month"));
		mode.addAttribute("ft_my", mapVo.get("ft_my"));
		mode.addAttribute("ft_para", mapVo.get("ft_para"));
		mode.addAttribute("proj_code", mapVo.get("proj_code"));
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());

		return "hrp/acc/autovouch/accpubCost/pubcostreg/pubCostDeptImport";
	}

	/**
	 * 查询公用费用主页面数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/queryAccPubCostReg", method = RequestMethod.POST)
	public Map<String, Object> queryAccPubCostReg(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.queryAccPubCostReg(getPage(mapVo));
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询公用费用主页面数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/queryAccPubCostRegDept", method = RequestMethod.POST)
	public Map<String, Object> queryAccPubCostRegDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.queryAccPubCostRegDept(getPage(mapVo));
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	
	/**
	 * 判断是否存在数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/queryAccPubCostRegDeptCount", method = RequestMethod.POST)
	public Map<String, Object> queryAccPubCostRegDeptCount(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = accPubCostRegService.queryAccPubCostRegDeptCount(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存公用费用
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/saveAccPubCostReg", method = RequestMethod.POST)
	public Map<String, Object> saveAccPubCostReg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.saveAccPubCostReg(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	
	/**
	 * 保存公用费用部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/saveAccPubCostRegDept", method = RequestMethod.POST)
	public Map<String, Object> saveAccPubCostRegDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = accPubCostRegService.saveAccPubCostRegDept(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 删除公用费用
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/deleteAccPubCostReg", method = RequestMethod.POST)
	public Map<String, Object> deleteAccPubCostReg(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.deleteAccPubCostReg(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 删除公用费用部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/deleteAccPubCostRegDept", method = RequestMethod.POST)
	public Map<String, Object> deleteAccPubCostRegDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.deleteAccPubCostRegDept(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 审核与消审
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/auditAccPubCostReg", method = RequestMethod.POST)
	public Map<String, Object> auditAccPubCostReg(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.auditAccPubCostReg(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	/**
	 * 审核与消审
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/collectAccPubCostReg", method = RequestMethod.POST)
	public Map<String, Object> collectAccPubCostReg(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = accPubCostRegService.collectAccPubCostReg(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询部门的详细信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/queryDeptAllInfoDict", method = RequestMethod.POST)
	public String queryDeptAllInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accPubCostRegService.queryDeptAllInfoDict(mapVo);
			return reJson;
		} catch (Exception e) {
			return "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}

	}

	/**
	 * 导入部门的详细信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/acc/autovouch/accpubcost/pubcostreg/importAccPubCostRegDept", method = RequestMethod.POST)
	public String importAccPubCostRegDept(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return accPubCostRegService.importAccPubCostRegDept(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
	}

}
