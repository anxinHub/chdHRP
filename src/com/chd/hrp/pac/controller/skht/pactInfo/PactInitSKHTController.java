package com.chd.hrp.pac.controller.skht.pactInfo;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSKHTEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactPlanSKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.elsesub.PactElseSubService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.pactinfo.PactDetSKHTService;
import com.chd.hrp.pac.service.skht.pactinfo.PactMainSKHTService;
import com.chd.hrp.pac.service.skht.pactinfo.PactPlanSKHTService;

/**
 * 期初合同
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/pactinfo/pactinit")
public class PactInitSKHTController extends BaseController {

	@Resource(name = "pactMainSKHTService")
	private PactMainSKHTService service;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactElseSubService")
	private PactElseSubService pactElseSubService;

	@Resource(name = "assNoDictService")
	private AssNoDictService assNoDictService;

	@Resource(name = "pactPlanSKHTService")
	private PactPlanSKHTService pactPlanSKHTService;

	@Resource(name = "pactDetSKHTService")
	private PactDetSKHTService pactDetSKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/pactInitSKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/skht/pactinfo/pactinit/pactInitSKHTMain";
	}

	@RequestMapping(value = "/pactInitSKHTAdd", method = RequestMethod.GET)
	public String toPactInitSKHTAdd(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/skht/pactinfo/pactinit/pactInitSKHTAdd";
	}

	@RequestMapping(value = "/pactInitSKHTEdit", method = RequestMethod.GET)
	public String pactInitSKHTEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKHTEntity entity = service.queryPactMainSKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("state", mapVo.get("state"));
		}
		return "hrp/pac/skht/pactinfo/pactinit/pactInitSKHTEdit";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = service.queryPactMainSKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactMainSKHTForMaster", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainSKHTForMaster(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			PactMainSKHTEntity query = service.queryPactMainSKHTByCode(mapVo);
			return JSONObject.parseObject(ChdJson.toJson(query));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactMainSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactMainSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactMainSKHTEntity.class);
			if (!listVo.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (PactMainSKHTEntity entity : listVo) {
					buffer.append(entity.getPact_code()).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_SKHT", buffer.toString());
				if (result != null) {
					result = result.substring(0, result.length() - 1);
					return JSONObject.parseObject("{\"error\":\"该合同已在" + result + "中使用，不可删除\"}");
				}
				service.deletePactMainSKHT(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
            
			String no = pactNoRuleService.getNo("PACT_MAIN_SKHT", mapVo);
			mapVo.put("pact_code", no);
			List<Map<String, Object>> li = service.queryPactSKHTMainByTypeAndName(mapVo);
			if(li!=null && !li.isEmpty()) {
				return JSONObject.parseObject("{\"error\":\"该条数据已经存在，请重新选择合同类型或重新输入合同名称\",\"state\":\"false\"}");
			}
			service.addPactMainSKHT(mapVo);
			return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\",\"pact_code\":\"" + no + "\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			List<Map<String, Object>> li = service.queryPactSKHTMainByTypeAndName(mapVo);
			int i=0;
			for (Map<String, Object> map : li) {
				if(!map.get("pact_code").toString().equals(mapVo.get("pact_code").toString()) && map.get("pact_type_code").toString().equals( mapVo.get("pact_type_code").toString())) {
					i+=1;
				}
			}
			if(i>0) {
				return JSONObject.parseObject("{\"error\":\"该条数据已经存在，请重新输入合同名称\",\"state\":\"false\"}");
			}
			String string = service.updatePactMainSKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainSKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);

			if (!"unuse,recover,file,unfile,stop,unstop".contains(check)) {
				StringBuffer buffer = new StringBuffer();
				for (String string : listVo) {
					buffer.append(string).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_SKHT", buffer.toString());
				if (result != null) {
					return JSONObject.parseObject("{\"error\":\"此合同已被" + result + "使用，不能更改状态\"}");
				}
			}

			String query = service.updatePactMainSKHTState(listVo, check, is_init);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询付款合同明细
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDetSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDetSKHTService.queryPactDetSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除付款合同明细
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactDetSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDetSKHTEntity.class);
			String query = pactDetSKHTService.deletePactDetSKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 查询付款计划
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactPlanSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPlanSKHTService.queryPactPlanSKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除付款计划
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactPlanSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactPlanSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactPlanSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactPlanSKHTEntity.class);
			String query = pactPlanSKHTService.deletePactPlanSKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 添加其他标的物
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAssNoDict", method = RequestMethod.POST)
	public Map<String, Object> addAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
			String addAssNoDict = assNoDictService.addAssNoDict(mapVo);
			return JSONObject.parseObject(addAssNoDict);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * 添加其他标的物
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPactElseSub", method = RequestMethod.POST)
	public Map<String, Object> addPactElseSub(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactElseSubService.addPactElseSub(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
