package com.chd.hrp.pac.controller.basicset.type;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.chd.hrp.pac.service.basicset.type.PactTypeSKHTService;
import com.chd.hrp.pac.serviceImpl.pacsysset.PactModStartServiceImpl;

/**
 * 收款合同类型
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:08:18
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/type/SKHT")
public class PactTypeSKHTController extends BaseController {

	@Resource(name = "pactTypeSKHTService")
	private PactTypeSKHTService pactTypeSKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@Resource(name = "pactTypeFKHTService")
	private PactTypeFKHTService pactTypeFKHTService;

	@Resource(name = "pactModStartService")
	private PactModStartServiceImpl modStartService = null;

	@RequestMapping(value = "/PactTypeSKHTMainPage")
	public String toPactTypeSKHTMainPage() {
		return "hrp/pac/basicset/type/SKHT/pactTypeSKHTMain";
	}

	@RequestMapping(value = "/toPactTypeSKHTAddMain")
	public String toPactTypeSKHTAddMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("mod_code", "11");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String modStart = modStartService.queryModStartByModeCode(mapVo);
		mode.addAttribute("modStart", modStart);
		return "hrp/pac/basicset/type/SKHT/pactTypeSKHTAdd";
	}

	@RequestMapping(value = "/toPactTypeSKHTEditMain")
	public String toPactTypeSKHTEditMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("type_code", mapVo.get("type_code"));

		int is_user = pactTypeSKHTService.qeuryPactTypeCode(mapVo);
		PactTypeSKHTEntity entity = pactTypeSKHTService.queryPactTypesSKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("is_user", is_user);
			mapVo.put("mod_code", "11");
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			String modStart = modStartService.queryModStartByModeCode(mapVo);
			mode.addAttribute("modStart", modStart);
		}
		return "hrp/pac/basicset/type/SKHT/pactTypeSKHTEdit";
	}

	@RequestMapping("/queryPactTypeSKHT")
	@ResponseBody
	public Map<String, Object> queryPactTypeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeSKHTService.queryPactTypeSKHT(getPage(mapVo));

			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/deletePactTypeSKHT")
	@ResponseBody
	public Map<String, Object> deletePactTypeSKHT(@RequestParam String paramVo, Model mode) {
		try {
			List<PactTypeSKHTEntity> listVo = JSONArray.parseArray(paramVo, PactTypeSKHTEntity.class);

			StringBuffer buffer = new StringBuffer();
			for (PactTypeSKHTEntity pactTypeSKHTEntity : listVo) {
				buffer.append(pactTypeSKHTEntity.getType_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String existsDataByTable = pactDeleteService.isExistsDataByTable("PACT_TYPE_SKHT", buffer);
			if (existsDataByTable != null) {
				existsDataByTable = existsDataByTable.substring(0, existsDataByTable.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + existsDataByTable + "中使用，不可删除\"}");
			}

			String query = pactTypeSKHTService.deletePactTypeSKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/addPactTypeSKHT")
	@ResponseBody
	public Map<String, Object> addPactTypeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeSKHTService.addPactTypeSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/updatePactTypeSKHT")
	@ResponseBody
	public Map<String, Object> updatePactTypeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeSKHTService.updatePactTypeSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
