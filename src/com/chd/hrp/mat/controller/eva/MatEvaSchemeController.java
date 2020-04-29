
package com.chd.hrp.mat.controller.eva;

import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.eva.MatEvaSchemeService;
import com.chd.hrp.mat.service.eva.MatEvaTargetService;

@Controller
@RequestMapping(value="/hrp/mat/eva/scheme")
public class MatEvaSchemeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatEvaSchemeController.class);
	
	//引入Service服务
	@Resource(name = "matEvaSchemeService")
	private final MatEvaSchemeService matEvaSchemeService = null;
	
	//主页跳转
	@RequestMapping(value = "/matEvaSchemePage", method = RequestMethod.GET)
	public String matEvaSchemePage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		Map<String, Object> mainInfo = matEvaSchemeService.queryMatEvaScheme(mapVo);
		if(mainInfo == null){
			mode.addAttribute("full_score", "");
		}else{
			mode.addAttribute("full_score", mainInfo.get("full_score"));
		}
		
		return "hrp/mat/eva/scheme/matEvaSchemeMain";
	}
	
	//指标标度跳转
	@RequestMapping(value = "/matEvaTargetScaleBatchPage", method = RequestMethod.GET)
	public String matEvaTargetScaleBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mode.addAttribute("target_code", mapVo.get("target_code"));
			
		return "hrp/mat/eva/scheme/matEvaTargetScaleBatch";
	}
	
	//指标标度跳转
	@RequestMapping(value = "/matEvaTargetScalePage", method = RequestMethod.GET)
	public String matEvaTargetScalePage(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mode.addAttribute("matEvaScale", mapVo.get("matEvaScale"));
			
		return "hrp/mat/eva/scheme/matEvaTargetScale";
	}
	
	/**
	 * 评价方案-查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaScheme(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		//String retJson = matEvaSchemeService.queryMatEvaScheme(mapVo);

		return JSONObject.parseObject("");
	}
	
	/**
	 * 评价方案-明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaSchemeDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaSchemeDetail(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaSchemeService.queryMatEvaSchemeDetail(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	@RequestMapping(value = "/saveMatEvaScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatEvaScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		
		String matJson = "";
		try {
			matJson = matEvaSchemeService.saveMatEvaScheme(mapVo);
		} catch (Exception e) {
			matJson = "{\"error\":\"保存失败\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	// 评价方案 - 引入指标
	@RequestMapping(value = "/addMatEvaSchemeDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatEvaSchemeDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		mapVo.put("scheme_name", "默认供应商评价方案");
		mapVo.put("create_user", SessionManager.getUserId());
		
		String returnJson = "";
		try {
			returnJson = matEvaSchemeService.addMatEvaSchemeDetail(mapVo);
		} catch (Exception e) {
			returnJson = "{\"error\":\"保存失败\"}";
		}
		
		return JSONObject.parseObject(returnJson);
	}
}

