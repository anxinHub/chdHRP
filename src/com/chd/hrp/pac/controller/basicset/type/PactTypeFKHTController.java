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
import com.chd.hrp.pac.entity.basicset.type.PactTypeFKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.chd.hrp.pac.serviceImpl.pacsysset.PactModStartServiceImpl;

/**
 * 付款合同类型
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:06:18
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/type/FKHT")
public class PactTypeFKHTController extends BaseController {

	@Resource(name = "pactTypeFKHTService")
	private PactTypeFKHTService pactTypeFKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@Resource(name = "pactModStartService")
	private PactModStartServiceImpl modStartService = null;

	@RequestMapping(value = "/PactTypeFKHTMainPage")
	public String toPactTypeFKHTMainPage() {
		return "hrp/pac/basicset/type/FKHT/pactTypeFKHTMain";
	}

	@RequestMapping(value = "/toPactTypeFKHTAddMain")
	public String toPactTypeFKHTAddMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("mod_code", "11");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String modStart = modStartService.queryModStartByModeCode(mapVo);
		mode.addAttribute("modStart", modStart);
		return "hrp/pac/basicset/type/FKHT/pactTypeFKHTAdd";
	}

	@RequestMapping(value = "/toPactTypeFKHTEditMain")
	public String toPactTypeFKHTEditMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("type_code", mapVo.get("type_code"));

		int is_user = pactTypeFKHTService.qeuryPactTypeCode(mapVo);
		PactTypeFKHTEntity entity = pactTypeFKHTService.queryPactTypeFKHTByCode(mapVo);
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
		return "hrp/pac/basicset/type/FKHT/pactTypeFKHTEdit";
	}

	@RequestMapping("/queryPactTypeFKHT")
	@ResponseBody
	public Map<String, Object> queryPactTypeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactTypeFKHTService.queryPactTypeFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/deletePactTypeFKHT")
	@ResponseBody
	public Map<String, Object> deletePactTypeFKHT(@RequestParam String paramVo, Model mode) {
		try {
			List<PactTypeFKHTEntity> listVo = JSONArray.parseArray(paramVo, PactTypeFKHTEntity.class);
			StringBuffer buffer = new StringBuffer();
			for (PactTypeFKHTEntity pactTypeFKHTEntity : listVo) {
				buffer.append(pactTypeFKHTEntity.getType_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String existsDataByTable = pactDeleteService.isExistsDataByTable("PACT_TYPE_FKHT", buffer);
			if (existsDataByTable != null) {
				existsDataByTable = existsDataByTable.substring(0, existsDataByTable.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + existsDataByTable + "中使用，不可删除\"}");
			}
			String query = pactTypeFKHTService.deletePactTypeFKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/addPactTypeFKHT")
	@ResponseBody
	public Map<String, Object> addPactTypeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeFKHTService.addPactTypeFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/updatePactTypeFKHT")
	@ResponseBody
	public Map<String, Object> updatePactTypeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeFKHTService.updatePactTypeFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
