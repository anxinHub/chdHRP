package com.chd.hrp.pac.controller.fkxy.change;

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
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactChangeAfterFKXYService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactDetFKXYService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/changeafter")
public class PactChangeAftFKXYController extends BaseController {

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactDetFKXYService")
	private PactDetFKXYService pactDetFKXYService;
	
	@Resource(name = "pactChangeAfterFKXYService")
	private PactChangeAfterFKXYService pactChangeAfterFKXYService;

	@RequestMapping(value = "/pactChangeAftFKXYMainPage")
	public String toPactChangeAftMainFKXYPage() {
		return "hrp/pac/fkxy/change/pactChangeAftFKXYMain";
	}

	@RequestMapping(value = "/pactChangeAftFKXYAddPage")
	public String toPactMainChangeAftFKXYAddPage(Model mode) {
		mode.addAttribute("user_id", SessionManager.getUserId());
		return "hrp/pac/fkxy/change/pactChangeAftFKXYAdd";
	}

	/**
	 * 查询合同变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactChangeAftFKXYMain", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_exe", "1");
			String string = pactChangeService.queryPactMainChangeFKXY(mapVo);
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
	@RequestMapping(value = "/addPactFKXYC", method = RequestMethod.POST)
	public Map<String, Object> addPactFKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "1");
			mapVo.put("is_exe", "1");
			String result = pactChangeAfterFKXYService.addPactFKXYC(mapVo, "FKXY");
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
	@RequestMapping(value = "/deletePactFKXYC", method = RequestMethod.POST)
	public Map<String, Object> deletePactFKXYC(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSONArray.parseObject(mapVo,List.class);
			if (!listVo.isEmpty()) {
				pactChangeAfterFKXYService.deletePactFKXYC(listVo);
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
	@RequestMapping(value = "/checkPactMainFKXYC", method = RequestMethod.POST)
	public Map<String, Object> checkPactMainFKXYC(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "check") String check, @RequestParam(name = "is_init") String is_init, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSONArray.parseObject(mapVo, List.class);
			String result = pactChangeAfterFKXYService.checkPactMainFKXYC(listVo, check, is_init);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 跳转签订后协议修改页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/pactChangeAfterFKXYEdit", method = RequestMethod.GET)
	public String pactExecFKXYEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainFKXYEntity entity = pactChangeAfterFKXYService.queryPactFKXYCByChangeCode(mapVo);
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
		return "hrp/pac/fkxy/change/pactChangeAfterFKXYEdit";
	}
	
	/**
	 * 查询备份表明细
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDetFKXYC", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetFKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
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
	
	/**
	 * 签订后协议变动-更新
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePactFKXYC", method = RequestMethod.POST)
	public Map<String, Object> updatePactInitFKXYC(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String result = pactChangeAfterFKXYService.updatePactFKXYC(mapVo);
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
	@RequestMapping(value = "/deletePactDetFKXYC", method = RequestMethod.POST)
	public Map<String, Object> deletePactDetFKXYC(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDetFKXYEntity> listVo = JSONArray.parseArray(mapVo, PactDetFKXYEntity.class);
			if (!listVo.isEmpty()) {
				pactChangeAfterFKXYService.deletePactDetFKXYC(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
