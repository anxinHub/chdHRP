package com.chd.hrp.pac.controller.skxy.change;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactChangeAfterSKXYService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactDetSKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/changeafter")
public class PactChangeAftSKXYController extends BaseController {

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactDetSKXYService")
	private PactDetSKXYService pactDetSKXYService;
	
	@Resource(name = "pactChangeAfterSKXYService")
	private PactChangeAfterSKXYService pactChangeAfterSKXYService;

	@RequestMapping(value = "/pactChangeAftSKXYMainPage")
	public String toPactChangeAftMainSKXYPage() {
		return "hrp/pac/skxy/change/pactChangeAftSKXYMain";
	}

	@RequestMapping(value = "/pactChangeAftSKXYAddPage")
	public String toPactMainChangeAftSKXYAddPage() {
		return "hrp/pac/skxy/change/pactChangeAftSKXYAdd";
	}

	/**
	 * 查询收款协议变更后记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactChangeAftSKXYMain", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_exe", "1");
			String string = pactChangeService.queryPactMainChangeSKXY(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 添加备份表
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPactSKXYC", method = RequestMethod.POST)
	public Map<String, Object> addPactSKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "1");
			mapVo.put("is_exe", "1");
			String result = pactChangeAfterSKXYService.addPactSKXYC(mapVo, "SKXY");
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 删除变更记录
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactSKXYC", method = RequestMethod.POST)
	public Map<String, Object> deletePactSKXYC(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSONArray.parseObject(mapVo,List.class);
			if (!listVo.isEmpty()) {
				pactChangeAfterSKXYService.deletePactSKXYC(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 提交、撤回、确认
	 * @param mapVo
	 * @param check
	 * @param is_init
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPactMainSKXYC", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainSKXYC(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSONArray.parseObject(mapVo, List.class);
			String result = pactChangeAfterSKXYService.checkPactMainSKXYC(listVo, check, is_init);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 修改页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/pactChangeAfterSKXYEdit", method = RequestMethod.GET)
	public String pactExecSKXYEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKXYEntity entity = pactChangeAfterSKXYService.queryPactSKXYCByChangeCode(mapVo);
		if(mapVo.get("state")!=null && mapVo.get("state")!="") {
			entity.setState(Integer.parseInt(mapVo.get("state").toString()));
		}
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
		return "hrp/pac/skxy/change/pactChangeAfterSKXYEdit"; 
	}
	
	/**
	 * 查询备份表明细
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDetSKXYC", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetSKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
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
	
	/**
	 * 签订后协议变动-更新
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePactSKXYC", method = RequestMethod.POST)
	public Map<String, Object> updatePactInitSKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String result = pactChangeAfterSKXYService.updatePactSKXYC(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 签订后变动修改页面删除标的物
	 * @param mapVo 
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactDetSKXYC", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetSKXYC(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetSKXYEntity> listVo = JSONArray.parseArray(mapVo, PactDetSKXYEntity.class);
			if (!listVo.isEmpty()) {
				pactChangeAfterSKXYService.deletePactDetSKXYC(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
