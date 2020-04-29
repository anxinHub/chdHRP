package com.chd.hrp.pac.controller.fkht.pactInfo;

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
import com.chd.hrp.pac.entity.fkht.pactinfo.PactDetFKHTEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.elsesub.PactElseSubService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactDetFKHTService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactPlanFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

/**
 * 期初合同
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/pactinfo/pactinit")
public class PactInitFKHTController extends BaseController {

	@Resource(name = "pactMainFKHTService")
	private PactMainFKHTService pactMainFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactElseSubService")
	private PactElseSubService pactElseSubService;

	@Resource(name = "assNoDictService")
	private AssNoDictService assNoDictService;

	@Resource(name = "pactPlanFKHTService")
	private PactPlanFKHTService pactPlanFKHTService;

	@Resource(name = "pactDetFKHTService")
	private PactDetFKHTService pactDetFKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/pactInitFKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/fkht/pactinfo/pactinit/pactInitFKHTMain";
	}

	@RequestMapping(value = "/pactMainFKHTAdd", method = RequestMethod.GET)
	public String toPactMainFKHTAdd(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTAdd";
	}
	
	@RequestMapping(value = "/impBidInfo", method = RequestMethod.GET)
	public String impBidInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("bid_code", mapVo.get("bid_code"));
		return "hrp/pac/fkht/pactinfo/pactinit/impBidInfo";
	}
	
	@RequestMapping(value = "/impAssApplyInfo", method = RequestMethod.GET)
	public String impAssApplyInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		return "hrp/pac/fkht/pactinfo/pactinit/impAssApplyInfo";
	}

	@RequestMapping(value = "/pactMainFKHTChangePage", method = RequestMethod.GET)
	public String pactMainFKHTChangePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		return "hrp/pac/basicset/common/pactMainChangePage";
	}

	@RequestMapping(value = "/pactMainFKHTEdit", method = RequestMethod.GET)
	public String pactMainFKHTEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactMainFKHTEntity entity = new PactMainFKHTEntity();
		if(mapVo.get("change_code") == null){//正常 修改页面
			entity= pactMainFKHTService.queryPactMainFKHTByCode(mapVo);
			mode.addAttribute("change_code", null);
		}else{//变更页面 链接合同查看 数据查询（查询备份表数据)
			entity = pactMainFKHTService.queryPactMainFKHTByCodeCopy(mapVo);
			
			mode.addAttribute("change_code", mapVo.get("change_code"));
		}
		
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}

		return "hrp/pac/fkht/pactinfo/pactinit/pactMainFKHTEdit";
	}
	
	/**
	 * 资产购置申请信息查询（引入资产购置申请用）
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssApplyFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactAssApplyFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainFKHTService.queryPactAssApplyFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 定标信息查询（引入定标用）
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactBidFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactBidFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainFKHTService.queryPactBidFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPactMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainFKHTService.queryPactMainFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryPactMainFKHTForMaster", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainFKHTForMaster(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			PactMainFKHTEntity query = pactMainFKHTService.queryPactMainFKHTByCode(mapVo);
			return JSONObject.parseObject(ChdJson.toJson(query));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPactSourceFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactSourceFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainFKHTService.queryPactSourceFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactMainFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactMainFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactMainFKHTEntity.class);
			if (!listVo.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (PactMainFKHTEntity entity : listVo) {
					buffer.append(entity.getPact_code()).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_FKHT", buffer.toString());
				if (result != null) {
					result = result.substring(0, result.length() - 1);
					return JSONObject.parseObject("{\"error\":\"该数据已在" + result + "中使用，不可删除\"}");
				}
				pactMainFKHTService.deletePactMainFKHT(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("state", "01");
		mapVo.put("maker", SessionManager.getUserId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mapVo.put("make_date", format.format(new Date()));

		String no = pactNoRuleService.getNo("PACT_MAIN_FKHT", mapVo);
		mapVo.put("pact_code", no);
		try {
			String json = pactMainFKHTService.addPactMainFKHT(mapVo);
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String string = pactMainFKHTService.updatePactMainFKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainFKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);
			if (!"unuse,recover,file,unfile,stop,unstop".contains(check)) {
				StringBuffer buffer = new StringBuffer();
				for (String string : listVo) {
					buffer.append(string).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_FKHT", buffer.toString());
				if (result != null) {
					return JSONObject.parseObject("{\"error\":\"此合同已被" + result + "使用，不能更改状态\"}");
				}
			}

			String query = pactMainFKHTService.updatePactMainFKHTState(listVo, check, is_init);
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
	@RequestMapping(value = "/queryPactDetFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDetFKHTService.queryPactDetFKHT(mapVo);
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
	@RequestMapping(value = "/deletePactDetFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDetFKHTEntity.class);
			String query = pactDetFKHTService.deletePactDetFKHT(listVo);
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
	@RequestMapping(value = "/queryPactPlanFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPlanFKHTService.queryPactPlanFKHT(mapVo);
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
	@RequestMapping(value = "/deletePactPlanFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactPlanFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactPlanFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactPlanFKHTEntity.class);
			String query = pactPlanFKHTService.deletePactPlanFKHT(listVo);
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
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		try {
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
			mapVo.put("is_init", 0);

			String query = pactElseSubService.addPactElseSub(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
