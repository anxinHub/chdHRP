package com.chd.hrp.mat.controller.matpur;

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
import com.chd.hrp.mat.service.matpur.MatPurCollectService;

/**
 * @Description:采购汇总查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatPurCollectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatPurCollectController.class);
	
	@Resource(name = "matPurCollectService")
	private final MatPurCollectService matPurCollectService = null;
	
	 
	/**
	 * @Description 材料采购查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/matInvPurMainPage", method = RequestMethod.GET)
	public String matInvPurMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/matpur/matInvPurMain";
	}
	
	/**
	 * @Description 材料采购查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/queryMatInvPurMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvPurMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("user_id", SessionManager.getUserId()); 
		
		    String matPurCollectJson = matPurCollectService.queryMatInvPurMain(getPage(mapVo));
		
		   return JSONObject.parseObject(matPurCollectJson);
	}
	
	
	/**
	 * @Description 采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/matPurCollectMainPage", method = RequestMethod.GET)
	public String matPurCollectMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/matpur/matPurCollectMain";
	}
	/**
	 * @Description 采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/matPurCollectTypeMainPage", method = RequestMethod.GET)
	public String matPurCollectTypeMainPage(Model mode) throws Exception {
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
		Map<String, Object> map= matPurCollectService.ViewSqlMatType(mapVo);
		mode.addAttribute("view_columns", map.get("sbviewTable"));
		//System.out.println(map.get("sbviewTable"));
		return "hrp/mat/matpur/matPurCollectTypeMain";
	}
	
	/**
	 * @Description 采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/queryMatPurCollectMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurCollectMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String matPurCollectJson = matPurCollectService.queryMatPurCollect(getPage(mapVo));
		
		return JSONObject.parseObject(matPurCollectJson);
	}
	/**
	 * @Description 采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpur/queryMatPurCollectTypeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPurCollectTypeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		Map<String, Object> mapView = matPurCollectService.ViewSqlMatType(mapVo);
		
		String matPurCollectJson = matPurCollectService.queryMatPurCollectType(getPage(mapView));
		
		return JSONObject.parseObject(matPurCollectJson);
	}
	
	
}
