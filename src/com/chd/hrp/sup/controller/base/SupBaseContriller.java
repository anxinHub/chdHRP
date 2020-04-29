package com.chd.hrp.sup.controller.base;

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
import com.chd.hrp.sup.service.base.SupBaseService;

@Controller
public class SupBaseContriller extends BaseController {

	private static Logger logger = Logger.getLogger(SupBaseContriller.class);

	@Resource(name = "supBaseService")
	private final SupBaseService supBaseService = null;

	// 材料字典列表（有材料库存）
	@RequestMapping(value = "/hrp/sup/queryMatInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("sup_id", SessionManager.getCopyCode());
		
		mapVo.put("sup_no", SessionManager.getCopyCode());
		
		mapVo.put("by_sup_inv", "1");
		String matInvList = supBaseService.queryMatInvList(getPage(mapVo));
		return matInvList;
	}
	
	/**
	 * @Description 判断批号有效日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/queryMatInvBatchInva", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchInva(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = supBaseService.queryMatInvBatchInva(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 判断批号灭菌日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/queryMatInvBatchDisinfect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchDisinfect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = supBaseService.queryMatInvBatchDisinfect(mapVo);

		return JSONObject.parseObject(matJson);
	}
	/**
	 * @Description 判断批号单价是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/queryMatInvBatchPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchPrice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = supBaseService.queryMatInvBatchPrice(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
}
