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
import com.chd.hrp.pac.entity.basicset.type.PactTypeFKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKXYService;
import com.chd.hrp.pac.serviceImpl.pacsysset.PactModStartServiceImpl;

/**
 * 付款协议类型
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:07:33
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/type/FKXY")
public class PactTypeFKXYController extends BaseController {

	@Resource(name = "pactTypeFKXYService")
	private PactTypeFKXYService PactTypeFKXYService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@Resource(name = "pactTypeFKHTService")
	private PactTypeFKHTService pactTypeFKHTService;

	@Resource(name = "pactModStartService")
	private PactModStartServiceImpl modStartService = null;

	@RequestMapping(value = "/PactTypeFKXYMainPage")
	public String toPactTypeFKXYMainPage() {
		return "hrp/pac/basicset/type/FKXY/pactTypeFKXYMain";
	}

	@RequestMapping(value = "/toPactTypeFKXYAddMain")
	public String toPactTypeFKXYAddMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("mod_code", "11");
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String modStart = modStartService.queryModStartByModeCode(mapVo);
		mode.addAttribute("modStart", modStart);
		return "hrp/pac/basicset/type/FKXY/pactTypeFKXYAdd";
	}

	@RequestMapping(value = "/toPactTypeFKXYEditMain")
	public String toPactTypeFKXYEditMain(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("type_code", mapVo.get("type_code"));

		int is_user = PactTypeFKXYService.qeuryPactTypeCode(mapVo);
		PactTypeFKXYEntity entity = PactTypeFKXYService.queryPactTypeFKXYByCode(mapVo);
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
		return "hrp/pac/basicset/type/FKXY/pactTypeFKXYEdit";
	}

	@RequestMapping("/queryPactTypeFKXY")
	@ResponseBody
	public Map<String, Object> queryPactTypeFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = PactTypeFKXYService.queryPactTypeFKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/deletePactTypeFKXY")
	@ResponseBody
	public Map<String, Object> deletePactTypeFKXY(@RequestParam String paramVo, Model mode) {
		try {
			List<PactTypeFKXYEntity> listVo = JSONArray.parseArray(paramVo, PactTypeFKXYEntity.class);
			StringBuffer buffer = new StringBuffer();
			for (PactTypeFKXYEntity pactTypeFKXYEntity : listVo) {
				buffer.append(pactTypeFKXYEntity.getType_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String existsDataByTable = pactDeleteService.isExistsDataByTable("PACT_TYPE_FKXY", buffer);
			if (existsDataByTable != null) {
				existsDataByTable = existsDataByTable.substring(0, existsDataByTable.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + existsDataByTable + "中使用，不可删除\"}");
			}
			String query = PactTypeFKXYService.deletePactTypeFKXY(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/addPactTypeFKXY")
	@ResponseBody
	public Map<String, Object> addPactTypeFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = PactTypeFKXYService.addPactTypeFKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping("/updatePactTypeFKXY")
	@ResponseBody
	public Map<String, Object> updatePactTypeFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = PactTypeFKXYService.updatePactTypeFKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
