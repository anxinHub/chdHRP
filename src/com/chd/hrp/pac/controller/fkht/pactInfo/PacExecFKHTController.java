package com.chd.hrp.pac.controller.fkht.pactInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService;

/**
 * 合同执行
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/pactinfo/pactexec")
public class PacExecFKHTController extends BaseController {

	@Resource(name = "pactMainFKHTService")
	private PactMainFKHTService pactMainFKHTService;

	@RequestMapping(value = "/pactExecFKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/fkht/pactinfo/pactexec/pactExecFKHTMain";
	}

	/**
	 * 查询执行合同
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactExecFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactExecFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "3");
			String state_code = (String) mapVo.get("state_code");
			List<String> list = new ArrayList<String>();
			if (state_code == null || "".equals(state_code)) {
				list.add("12");
				list.add("13");
				list.add("14");
				list.add("15");
			} else {
				list.add(state_code);
			}
			mapVo.put("list", list);
			String query = pactMainFKHTService.queryPactMainFKHTByStateCode(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查看页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactExecFKHTEditPage", method = RequestMethod.GET)
	public String toPactExecFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainFKHTEntity entity = pactMainFKHTService.queryPactMainFKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
		return "hrp/pac/fkht/pactinfo/pactexec/pactExecFKHTEdit";
	}

	/**
	 * 执行合同主页面中合同档案管理页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactExecFKHTFilePage", method = RequestMethod.GET)
	public String toPactExecFKHTFilePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactMainFKHTEntity entity = pactMainFKHTService.queryPactMainFKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("pact_code", entity.getPact_code());
			mode.addAttribute("pact_type_code", entity.getPact_type_code());
			mode.addAttribute("pact_name", entity.getPact_name());
		}
		return "hrp/pac/fkht/pactinfo/pactexec/pactExecFKHTFile";
	}

}
