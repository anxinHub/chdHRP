package com.chd.hrp.pac.controller.fkxy.pactinfo;

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
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactDetFKXYService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactMainFKXYService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/pactinfo/pactinit")
public class PactMainFKXYInitController extends BaseController {

	@Resource(name = "pactMainFKXYService")
	private PactMainFKXYService pactMainFKXYService;

	@Resource(name = "pactDetFKXYService")
	private PactDetFKXYService pactDetFKXYService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/pactInitFKXYMainPage", method = RequestMethod.GET)
	public String toPactInitFKXYMainPage() {
		return "hrp/pac/fkxy/pactinfo/pactinit/pactInitFKXYMain";
	}

	@RequestMapping(value = "/pactInitFKXYAddPage", method = RequestMethod.GET)
	public String toPactInitFKXYAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/fkxy/pactinfo/pactinit/pactInitFKXYAdd";
	}
   
	@RequestMapping(value = "/pactaddBatchinsert", method = RequestMethod.GET)
	public String toPactaddBatchinsert(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));

		return "hrp/pac/fkxy/pactinfo/pactinit/pactInitFKXYBatchAdd";
	}
	@RequestMapping(value = "/pactFKXYEditPage", method = RequestMethod.GET)
	public String toPactFKXYEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainFKXYEntity entity = pactMainFKXYService.queryPactFKXYByPactCode(mapVo);
		mode.addAttribute("entity", entity);
		if (entity != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
		return "hrp/pac/fkxy/pactinfo/pactinit/pactInitFKXYEdit";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactFKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactMainFKXYService.queryPactFKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactMainFKXYForMaster", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainFKXYForMaster(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			PactMainFKXYEntity query = pactMainFKXYService.queryPactFKXYByPactCode(mapVo);
			return JSONObject.parseObject(ChdJson.toJson(query));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactDetFKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDetFKXYService.queryPactDetFKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactFKXY", method = RequestMethod.POST)
	public Map<String, Object> addPactFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "1");
			String pact_code = pactNoRuleService.getNo("PACT_MAIN_FKXY", mapVo);
			mapVo.put("pact_code", pact_code);
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));

			String result = pactMainFKXYService.addPactFKXY(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactFKXY", method = RequestMethod.POST)
	public Map<String, Object> updatePactInitFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_exe", "0");
			String result = pactMainFKXYService.updatePactFKXY(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactDetFKXY", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetFKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetFKXYEntity> listVo = JSONArray.parseArray(mapVo, PactDetFKXYEntity.class);
			if (!listVo.isEmpty()) {
				pactDetFKXYService.deletePactDetFKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactFKXY", method = RequestMethod.POST)
	public Map<String, Object> deletePactFKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactMainFKXYEntity> listVo = JSONArray.parseArray(mapVo, PactMainFKXYEntity.class);
			if (!listVo.isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				for (PactMainFKXYEntity entity : listVo) {
					buffer.append(entity.getPact_code()).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_FKXY", buffer.toString());
				if (result != null) {
					result = result.substring(0, result.length() - 1);
					return JSONObject.parseObject("{\"error\":\"该协议已在" + result + "中使用，不可删除\"}");
				}
				pactMainFKXYService.deletePactFKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactMainFKXY", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainFKXY(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);
			if (!"unuse,recover,file,unfile,stop,unstop".contains(check)) {
				StringBuffer buffer = new StringBuffer();
				for (String string : listVo) {
					buffer.append(string).append("','");
				}
				buffer.delete(buffer.length() - 3, buffer.length());
				String result = pactDeleteService.isExistsDataByTable("PACT_MAIN_FKXY", buffer.toString());
				if (result != null) {
					return JSONObject.parseObject("{\"error\":\"此合同已被" + result + "使用，不能更改状态\"}");
				}
			}

			String result = pactMainFKXYService.checkPactMainFKXY(listVo, check, is_init);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value ="/queryPactFKXYMaterial" ,method = RequestMethod.POST)
	
	public Map<String ,Object> queryPactFKXYMaterial(@RequestParam Map<String, Object> mapVo, Model mode){
		
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			/*if(mapVo.get("mat_store")!=null && mapVo.get("mat_store")!=""){
				String store = mapVo.get("mat_store").toString().substring(0, 1);
				mapVo.put("mat_store", store);
				
			}*/
			String query = pactDetFKXYService.queryPactFKXYMaterial(getPage(mapVo));
			return JSONObject.parseObject(query);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
		
	}
}
