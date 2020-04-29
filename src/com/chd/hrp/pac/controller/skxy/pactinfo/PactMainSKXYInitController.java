package com.chd.hrp.pac.controller.skxy.pactinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactDetSKXYService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactMainSKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/pactinfo/pactinit")
public class PactMainSKXYInitController extends BaseController {

	@Resource(name = "pactMainSKXYService")
	private PactMainSKXYService pactMainSKXYService;

	@Resource(name = "pactDetSKXYService")
	private PactDetSKXYService pactDetSKXYService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/pactInitSKXYMainPage", method = RequestMethod.GET)
	public String toPactInitSKXYMainPage() {
		return "hrp/pac/skxy/pactinfo/pactinit/pactInitSKXYMain";
	}

	@RequestMapping(value = "/pactInitSKXYAddPage", method = RequestMethod.GET)
	public String toPactInitSKXYAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/skxy/pactinfo/pactinit/pactInitSKXYAdd";
	}

	@RequestMapping(value = "/pactSKXYEditPage", method = RequestMethod.GET)
	public String toPactSKXYEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKXYEntity entity = pactMainSKXYService.queryPactSKXYByPactCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("is_init", entity.getIs_init());
		}
		return "hrp/pac/skxy/pactinfo/pactinit/pactInitSKXYEdit";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactSKXYForMaster", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKXYForMaster(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			PactMainSKXYEntity query = pactMainSKXYService.queryPactSKXYByPactCode(mapVo);
			return JSONObject.parseObject(ChdJson.toJson(query));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactSKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainSKXYService.queryPactSKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactDetSKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDetSKXYService.queryPactDetSKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactSKXY", method = RequestMethod.POST)
	public Map<String, Object> addPactSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "1");
			String pact_code = pactNoRuleService.getNo("PACT_MAIN_SKXY", mapVo);
			mapVo.put("pact_code", pact_code);
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));

			String result = pactMainSKXYService.addPactSKXY(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactSKXY", method = RequestMethod.POST)
	public Map<String, Object> updatePactInitSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_exe", "0");
			String updatePactSKXY = pactMainSKXYService.updatePactSKXY(mapVo);
			return JSONObject.parseObject(updatePactSKXY);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactDetSKXY", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetSKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetSKXYEntity> listVo = JSONArray.parseArray(mapVo, PactDetSKXYEntity.class);
			if (!listVo.isEmpty()) {
				pactDetSKXYService.deletePactDetSKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactSKXY", method = RequestMethod.POST)
	public Map<String, Object> deletePactSKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactMainSKXYEntity> listVo = JSONArray.parseArray(mapVo, PactMainSKXYEntity.class);
			if (!listVo.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (PactMainSKXYEntity entity : listVo) {
					buffer.append(entity.getPact_code()).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_SKXY", buffer.toString());
				if (result != null) {
					result = result.substring(0, result.length() - 1);
					return JSONObject.parseObject("{\"error\":\"该协议已在" + result + "中使用，不可删除\"}");
				}
				pactMainSKXYService.deletePactSKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactMainSKXY", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainSKXY(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);
			if (!"unuse,recover,file,unfile,stop,unstop".contains(check)) {
				StringBuffer buffer = new StringBuffer();
				for (String string : listVo) {
					buffer.append(string).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_SKXY", buffer.toString());
				if (result != null) {
					return JSONObject.parseObject("{\"error\":\"此协议已被使用，不能更改状态\"}");
				}
			}

			String result = pactMainSKXYService.checkPactMainSKXY(listVo, check, is_init);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
