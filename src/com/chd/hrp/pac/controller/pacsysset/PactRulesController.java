package com.chd.hrp.pac.controller.pacsysset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
 * 编码规则
 * 
 * @author Hao Tong
 * @date 2018年8月10日 下午4:52:48
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/pacsysset")
public class PactRulesController extends BaseController {

	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;

	@RequestMapping(value = "/pacRulesMainPage", method = RequestMethod.GET)
	public String pacRulesMainPage() {
		return "hrp/pac/pacsysset/pacRulesMain";
	}

	/**
	 * 主页面查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPacRules", method = RequestMethod.POST)
	public Map<String, Object> queryPacRules(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("mod_code", "11");
			String rules = rulesService.queryRules(getPage(mapVo));

			return JSONObject.parseObject(rules);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 添加
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPacRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPacRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String rulesJson = rulesService.addRules(mapVo);
			return JSONObject.parseObject(rulesJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 更新
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePacRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("mod_code", "11");
			String rulesJson = rulesService.updateRules(mapVo);

			return JSONObject.parseObject(rulesJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePacRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRules(@RequestParam(value = "ParamVo") String paramVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("temp", id);// 实际实体类变量
				listVo.add(mapVo);
			}
			String rulesJson = rulesService.deleteBatchRules(listVo);
			return JSONObject.parseObject(rulesJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
