package com.chd.hrp.med.controller.medpur;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.med.service.medpur.MedPurCollectService;

/**
 * @Description:采购汇总查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedPurCollectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedPurCollectController.class);
	
	@Resource(name = "medPurCollectService")
	private final MedPurCollectService medPurCollectService = null;
	
	
	/**
	 * @Description 采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpur/medPurCollectMainPage", method = RequestMethod.GET)
	public String medPurCollectMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/medpur/medPurCollectMain";
	}
	/**
	 * @Description 采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpur/medPurCollectTypeMainPage", method = RequestMethod.GET)
	public String medPurCollectTypeMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo =new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		Map<String, Object> map= medPurCollectService.ViewSqlMedType(mapVo);
		mode.addAttribute("view_columns", map.get("sbviewTable"));
		System.out.println(map.get("sbviewTable"));
		return "hrp/med/medpur/medPurCollectTypeMain";
	}
	
	/**
	 * @Description 采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpur/queryMedPurCollectMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurCollectMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String medPurCollectJson = medPurCollectService.queryMedPurCollect(getPage(mapVo));
		
		return JSONObject.parseObject(medPurCollectJson);
	}
	/**
	 * @Description 采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpur/queryMedPurCollectTypeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPurCollectTypeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		Map<String, Object> mapView = medPurCollectService.ViewSqlMedType(mapVo);
		
		String medPurCollectJson = medPurCollectService.queryMedPurCollectType(getPage(mapView));
		
		return JSONObject.parseObject(medPurCollectJson);
	}
}
