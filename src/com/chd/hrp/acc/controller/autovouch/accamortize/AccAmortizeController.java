package com.chd.hrp.acc.controller.autovouch.accamortize;

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
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeDeptService;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeInfoService;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeSourceService;

@Controller
@RequestMapping(value = "/hrp/acc/autovouch/amortization")
public class AccAmortizeController extends BaseController {

	@Resource(name = "accAmortizeInfoService")
	private AccAmortizeInfoService accAmortizeInfoService;

	@Resource(name = "accAmortizeSourceService")
	private AccAmortizeSourceService accAmortizeSourceService;

	@Resource(name = "accAmortizeDeptService")
	private AccAmortizeDeptService accAmortizeDeptService;

	@RequestMapping(value = "/amortizeMainPage", method = RequestMethod.GET)
	public String amortizeMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/amortization/amortizeMain";
	}

	@RequestMapping(value = "/amortizeAddPage", method = RequestMethod.GET)
	public String amortizeAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/amortization/amortizeAdd";
	}

	@RequestMapping(value = "/amortizeReportPage", method = RequestMethod.GET)
	public String amortizeReportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/amortization/amortizeReport";
	}

	@RequestMapping(value = "/amortizeUpdatePage", method = RequestMethod.GET)
	public String amortizeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> map = accAmortizeInfoService.queryAmortizeByCode(mapVo);
		mode.addAllAttributes(map);
		return "hrp/acc/autovouch/amortization/amortizeUpdate";
	}

	@ResponseBody
	@RequestMapping(value = "/queryAmortizeInfo", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = accAmortizeInfoService.queryAmortizeInfo(getPage(mapVo));
		return JSONObject.parseObject(reJson);

	}

	/**
	 * 保存卡片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAmortizeInfo", method = RequestMethod.POST)
	public Map<String, Object> saveAmortizeInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson;
			if (mapVo.get("apply_code") == null || "".equals(mapVo.get("apply_code").toString())) {
				reJson = accAmortizeInfoService.saveAmortizeInfo(mapVo);
			} else {
				reJson = accAmortizeInfoService.updateAmortizeInfo(mapVo);
			}
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 删除卡片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAmortizeList", method = RequestMethod.POST)
	public Map<String, Object> deleteAmortizeList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String reJson = accAmortizeInfoService.deleteAmortizeList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存资金来源列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAmortizeSourceList", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeSourceList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeSourceService.queryAmortizeSourceList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存资金来源列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAmortizeSourceList", method = RequestMethod.POST)
	public Map<String, Object> saveAmortizeSourceList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeSourceService.saveAmortizeSourceList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存资金来源列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAmortizeSourceList", method = RequestMethod.POST)
	public Map<String, Object> deleteAmortizeSourceList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String reJson = accAmortizeSourceService.deleteAmortizeSourceList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询代摊历史
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAmortizeHistoryList", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeHistoryList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeInfoService.queryAmortizeHistoryList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryAmortizeHistoryCount", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeHistoryCount(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = accAmortizeInfoService.queryAmortizeHistoryCount(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询部门列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAmortizeDeptList", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeDeptList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeDeptService.queryAmortizeDeptList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存部门列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveAmortizeDeptList", method = RequestMethod.POST)
	public Map<String, Object> saveAmortizeDeptList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {

			String reJson = accAmortizeDeptService.saveAmortizeDeptList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 保存部门列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAmortizeDeptList", method = RequestMethod.POST)
	public Map<String, Object> deleteAmortizeDeptList(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String reJson = accAmortizeDeptService.deleteAmortizeDeptList(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/setAmortize", method = RequestMethod.POST)
	public Map<String, Object> setAmortize(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String reJson = accAmortizeInfoService.setAmortize(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryAmortizeReport", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeReport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String reJson = accAmortizeInfoService.queryAmortizeReport(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkAmortizeList", method = RequestMethod.POST)
	public Map<String, Object> checkAmortizeList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String reJson = accAmortizeInfoService.checkAmortizeState(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

}
