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
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.chd.hrp.pac.service.basicset.type.PactTypeSKXYService;
import com.chd.hrp.pac.serviceImpl.pacsysset.PactModStartServiceImpl;

/**
 * 收款协议类型
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:09:03
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/type/SKXY")
public class PactTypeSKXYController extends BaseController {

	@Resource(name = "pactTypeSKXYService")
	private PactTypeSKXYService pactTypeSKXYService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@Resource(name = "pactTypeFKHTService")
	private PactTypeFKHTService pactTypeFKHTService;

	@Resource(name = "pactModStartService")
	private PactModStartServiceImpl modStartService = null;

	@RequestMapping(value = "/PactTypeSKXYMainPage")
	public String toPactTypeSKXYMainPage() {
		return "hrp/pac/basicset/type/SKXY/pactTypeSKXYMain";
	}

	@RequestMapping(value = "/toPactTypeSKXYAddMain")
	public String toPactTypeSKXYAddMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("mod_code", "11");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String modStart = modStartService.queryModStartByModeCode(mapVo);
		mode.addAttribute("modStart", modStart);
		return "hrp/pac/basicset/type/SKXY/pactTypeSKXYAdd";
	}

	@RequestMapping(value = "/toPactTypeSKXYEditMain")
	public String toPactTypeSKXYEditMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("type_code", mapVo.get("type_code"));

		int is_user = pactTypeSKXYService.qeuryPactTypeCode(mapVo);
		PactTypeSKXYEntity entity = pactTypeSKXYService.queryPactTypeSKXYByCode(mapVo);
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
		return "hrp/pac/basicset/type/SKXY/pactTypeSKXYEdit";
	}

	@RequestMapping("/queryPactTypeSKXY")
	@ResponseBody
	public Map<String, Object> queryPactTypeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactTypeSKXYService.queryPactTypeSKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/deletePactTypeSKXY")
	@ResponseBody
	public Map<String, Object> deletePactTypeSKXY(@RequestParam String paramVo, Model mode) {
		try {
			List<PactTypeSKXYEntity> listVo = JSONArray.parseArray(paramVo, PactTypeSKXYEntity.class);

			StringBuffer buffer = new StringBuffer();
			for (PactTypeSKXYEntity pactTypeSKXYEntity : listVo) {
				buffer.append(pactTypeSKXYEntity.getType_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String existsDataByTable = pactDeleteService.isExistsDataByTable("PACT_TYPE_SKXY", buffer);
			if (existsDataByTable != null) {
				existsDataByTable = existsDataByTable.substring(0, existsDataByTable.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + existsDataByTable + "中使用，不可删除\"}");
			}

			String query = pactTypeSKXYService.deletePactTypeSKXY(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/addPactTypeSKXY")
	@ResponseBody
	public Map<String, Object> addPactTypeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeSKXYService.addPactTypeSKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/updatePactTypeSKXY")
	@ResponseBody
	public Map<String, Object> updatePactTypeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactTypeSKXYService.updatePactTypeSKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
