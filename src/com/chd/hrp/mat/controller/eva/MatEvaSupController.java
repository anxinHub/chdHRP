
package com.chd.hrp.mat.controller.eva;

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
import com.chd.base.ChdToken;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.eva.MatEvaSchemeService;
import com.chd.hrp.mat.service.eva.MatEvaSupService;

@Controller
@RequestMapping(value="/hrp/mat/eva/sup")
public class MatEvaSupController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatEvaSupController.class);
	
	//引入Service服务
	@Resource(name = "matEvaSupService")
	private final MatEvaSupService matEvaSupService = null;
	
	@Resource(name = "matEvaSchemeService")
	private final MatEvaSchemeService matEvaSchemeService = null;
	
	//主页跳转
	@RequestMapping(value = "/matEvaSupMainPage", method = RequestMethod.GET)
	public String matEvaSupMainPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/sup/matEvaSupMain";
	}
	
	//评价维护页面跳转
	@RequestMapping(value = "/matEvaSupUpdatePage", method = RequestMethod.GET)
	public String matEvaSupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		Map<String, Object> schemeMap = matEvaSchemeService.queryMatEvaScheme(mapVo);
		
		if(mapVo.get("isAdd").equals("0")){
			List<Map<String, Object>> retList = matEvaSupService.queryMatEvaSupMainRightByCode(mapVo);
			mode.addAttribute("evaInfo", retList.get(0));
		}
		mode.addAttribute("isAdd", mapVo.get("isAdd"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_name", mapVo.get("sup_code") + " " +mapVo.get("sup_name"));
		mode.addAttribute("order_ids", mapVo.get("order_ids"));
		if(!(schemeMap == null)){
			mode.addAttribute("full_score", schemeMap.get("full_score"));
		}
		 
		return "hrp/mat/eva/sup/matEvaSupUpdate";
	}
	
	//扣分页面跳转
	@RequestMapping(value = "/matEvaSupAbbreviationPage", method = RequestMethod.GET)
	public String matEvaSupAbbreviationPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("eva_code", mapVo.get("eva_code"));
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("get_score", mapVo.get("get_score"));
		mode.addAttribute("state", mapVo.get("state"));
		
		return "hrp/mat/eva/sup/matEvaSupAbbreviation";
	}
	
	/**
	 * 供应商评价-供应商信息查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaSupInfoLeft", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSupInfoLeft(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matEvaSupService.queryMatEvaSupInfoLeft(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * 供应商评价-评价查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaSupMainRight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSupMainRight(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaSupService.queryMatEvaSupMainRight(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * 供应商评价-评价指标添加查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaSupTargetForAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSupTargetForAdd(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaSupService.queryMatEvaSupTargetForAdd(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * 供应商评价-评价指标查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaSupTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSupTarget(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaSupService.queryMatEvaSupTarget(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	@RequestMapping(value = "/queryMatEvaSupTargetDeduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSupTargetDeduct(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matEvaSupService.queryMatEvaSupTargetDeduct(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	// 供应商评价 - 删除
	@RequestMapping(value = "/deleteMatEvaSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatEvaSup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String returnJson = "";
		try {
			returnJson = matEvaSupService.deleteMatEvaSup(mapVo);
		} catch (Exception e) {
			returnJson = "{\"error\":\"删除失败\"}";
		}
		
		return JSONObject.parseObject(returnJson);
	}
	
	// 供应商评价 - 提交
	@RequestMapping(value = "/submitMatEvaSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitMatEvaSup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		mapVo.put("state", 2);
		mapVo.put("submit_emp", SessionManager.getUserId());
		mapVo.put("submit_date", DateUtil.getCurrenDate());
		
		String returnJson = "";
		try {
			returnJson = matEvaSupService.changeMatEvaSupState(mapVo);
		} catch (Exception e) {
			returnJson = "{\"error\":\"提交失败\"}";
		}
			
		return JSONObject.parseObject(returnJson);
	}
	
	// 供应商评价 - 作废
	@RequestMapping(value = "/invalidMatEvaSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> invalidMatEvaSup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		mapVo.put("state", 9);
		
		String returnJson = "";
		try {
			returnJson = matEvaSupService.changeMatEvaSupState(mapVo);
		} catch (Exception e) {
			returnJson = "{\"error\":\"作废失败\"}";
		}
				
		return JSONObject.parseObject(returnJson);
	}
	
	@RequestMapping(value = "/addOrUpdateMatEvaSup", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addOrUpdateMatEvaSup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		mapVo.put("eva_emp", SessionManager.getUserId());
		mapVo.put("eva_date", DateUtil.getCurrenDate());
		
		String retJson;
		try {
			retJson = matEvaSupService.addOrUpdateMatEvaSup(mapVo);
		} catch (Exception e) {
			retJson = e.getMessage();
		}
		
		return JSONObject.parseObject(retJson);
	}
	
	@RequestMapping(value = "/addMatEvaTargetDudect", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatEvaTargetDudect(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson;
		try {
			retJson = matEvaSupService.addMatEvaTargetDudect(mapVo);
		} catch (Exception e) {
			retJson = e.getMessage();
		}
		
		return JSONObject.parseObject(retJson);
	}
	
	// 标度查询
	@RequestMapping(value = "/queryMatEvaTargetScale", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEvaTargetScale(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retMatSelect = matEvaSupService.queryMatEvaTargetScale(mapVo);
		return retMatSelect;
	}
	
	// 扣分项查询
	@RequestMapping(value = "/queryMatEvaTargetDeduct", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEvaTargetDeduct(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retMatSelect = matEvaSupService.queryMatEvaTargetDeduct(mapVo);
		return retMatSelect;
	}
}

