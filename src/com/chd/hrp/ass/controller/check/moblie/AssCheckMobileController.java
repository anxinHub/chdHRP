package com.chd.hrp.ass.controller.check.moblie;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.check.mobile.AssCheckMobileService;

@Controller
public class AssCheckMobileController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCheckMobileController.class);

	// 引入Service服务
	@Resource(name = "assCheckMobileService")
	private final AssCheckMobileService assCheckMobileService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/assCheckMoblieMainPage", method = RequestMethod.GET)
	public String assCheckMoblieMainPage(Model mode) throws Exception {

		return "hrp/ass/moblie/main";

	}
	/**
	 * @Description 移动盘点与账面对账主页
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/checkQueryMainPage", method = RequestMethod.GET)
	public String checkQueryMainPage(Model mode) throws Exception {
		
		return "hrp/ass/moblie/checkQueryMain";
		
	}
	/**
	 * @Description 移动盘点与账面对账主页
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/checkContrastMainPage", method = RequestMethod.GET)
	public String checkContrastMainPage(Model mode) throws Exception {
		
		return "hrp/ass/moblie/checkContrastMain";
		
	}

	/**
	 * @Description 删除数据 移动盘点数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/assdeleteMoblie", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assdeleteMoblie(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("bar_code", ids[0]);

			listVo.add(mapVo);

		}

		String assCheckApLandJson = assCheckMobileService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckApLandJson);

	}

	/**
	 * @Description 查询数据 051101 盘盈处置申请单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/queryAssCheckMoblie", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckMoblie(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String moblie = assCheckMobileService.query(getPage(mapVo));

		return JSONObject.parseObject(moblie);

	}
	/**
	 * @Description 查询数据 051101 盘盈处置申请单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String moblie = assCheckMobileService.generate(mapVo);
		
		return JSONObject.parseObject(moblie);
		
	}
	/**
	 * @Description 查询数据 移动盘点数据与系统账面数据核对
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/queryAssCheckMoblieContrast", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckMoblieContrast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String moblie = assCheckMobileService.queryMoblieContrast(getPage(mapVo));

		return JSONObject.parseObject(moblie);

	}
	/**
	 * @Description 查询数据 移动盘点数据与系统账面数据核对
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/moblie/queryAssCheckContrast", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckContrast(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		String moblie = assCheckMobileService.queryCheckContrast(getPage(mapVo));
		
		return JSONObject.parseObject(moblie);
		
	}
}
