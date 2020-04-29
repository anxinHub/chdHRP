package com.chd.hrp.pac.controller.skht.pactInfo;

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
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.service.skht.pactinfo.PactMainSKHTService;

/**
 * 合同执行
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/pactinfo/pactexec")
public class PacExecSKHTController extends BaseController {

	@Resource(name = "pactMainSKHTService")
	private PactMainSKHTService service;

	@RequestMapping(value = "/pactExecSKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/skht/pactinfo/pactexec/pactExecSKHTMain";
	}

	/**
	 * 查询执行合同
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactExecSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactExecSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
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
			String query = service.queryPactMainSKHTByStateCode(getPage(mapVo));
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
	@RequestMapping(value = "/pactExecSKHTEdit", method = RequestMethod.GET)
	public String pactExecSKHTEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
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
		}
		return "hrp/pac/skht/pactinfo/pactexec/pactExecSKHTEdit";
	}

	/**
	 * 执行合同归档文档管理
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactExecSKHTFilePage", method = RequestMethod.GET)
	public String toPactExecSKHTFilePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactMainSKHTEntity entity = service.queryPactMainSKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("pact_code", entity.getPact_code());
			mode.addAttribute("pact_type_code", entity.getPact_type_code());
			mode.addAttribute("pact_name", entity.getPact_name());
		}
		return "hrp/pac/skht/pactinfo/pactexec/pactExecSKHTFile";
	}

}
