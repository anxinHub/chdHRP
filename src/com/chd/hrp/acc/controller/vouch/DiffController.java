package com.chd.hrp.acc.controller.vouch;

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
import com.chd.base.exception.SysException;
import com.chd.hrp.acc.service.vouch.AccDifferService;

@Controller
@RequestMapping(value = "/hrp/acc/accvouch/diff")
public class DiffController extends BaseController {

	private static Logger logger = Logger.getLogger(DiffController.class);

	@Resource(name = "accDifferService")
	private AccDifferService accDifferService;

	@RequestMapping(value = "/accDifferItemMainPage", method = RequestMethod.GET)
	public String AccDifferItemMainPage(Model mode) throws Exception {
		return "hrp/acc/accvouch/diff/differitem/accDifferItemMain";

	}

	// 项目添加页面
	@RequestMapping(value = "/accDifferItemAddPage", method = RequestMethod.GET)
	public String accDifferItemAddPage(Model mode, String type_code) throws Exception {
		mode.addAttribute("type_code", type_code);
		return "hrp/acc/accvouch/diff/differitem/accDifferItemAdd";

	}

	// 类别添加页面
	@RequestMapping(value = "/accDifferTypeAddPage", method = RequestMethod.GET)
	public String accDifferTypeAddPage(Model mode, String type_code) throws Exception {
		mode.addAttribute("type_code", type_code);
		return "hrp/acc/accvouch/diff/differitem/accDifferTypeAdd";

	}

	// 项目修改页面
	@RequestMapping(value = "/accDifferItemEditPage", method = RequestMethod.GET)
	public String accDifferItemEditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> result = accDifferService.queryAccDifferItemByCode(mapVo);
		mode.addAttribute("entity", result);

		return "hrp/acc/accvouch/diff/differitem/accDifferItemUpdate";

	}
	
	// 类别修改页面
	@RequestMapping(value = "/accDifferTypeEditPage", method = RequestMethod.GET)
	public String accDifferTypeEditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> result = accDifferService.queryAccDifferTypeByCode(mapVo);
		mode.addAttribute("entity", result);
		
		return "hrp/acc/accvouch/diff/differitem/accDifferTypeUpdate";
		
	}

	/**
	 * 查询差异标注项目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccDifferItem", method = RequestMethod.POST)
	public Map<String, Object> queryAccDifferItem(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String result = accDifferService.queryAccDifferItem(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

	/**
	 * 保存差异标注项目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAccDifferItem", method = RequestMethod.POST)
	public Map<String, Object> addAccDifferItem(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.addAccDifferItem(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 更新差异标注项目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAccDifferItem", method = RequestMethod.POST)
	public Map<String, Object> updateAccDifferItem(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.updateAccDifferItem(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 删除差异标注项目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAccDifferItem", method = RequestMethod.POST)
	public Map<String, Object> deleteAccDifferItem(@RequestParam String mapVo, Model mode) {
		
		String result = accDifferService.deleteAccDifferItem(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 查询差异项目类别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccDifferType", method = RequestMethod.POST)
	public Map<String, Object> queryAccDifferType(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.queryAccDifferType(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 新增差异项目类别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAccDifferType", method = RequestMethod.POST)
	public Map<String, Object> addAccDifferType(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.addAccDifferType(mapVo);
		return JSONObject.parseObject(result);
	}
	/**
	 * 修改差异项目类别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAccDifferType", method = RequestMethod.POST)
	public Map<String, Object> updateAccDifferType(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String result = accDifferService.updateAccDifferType(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 查询差异项目类别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAccDifferType", method = RequestMethod.POST)
	public Map<String, Object> deleteAccDifferType(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.deleteAccDifferType(mapVo);
		return JSONObject.parseObject(result);
	}
}
