package com.chd.hrp.acc.controller.vouch;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.vouch.AccDifferService;
import com.chd.hrp.acc.service.vouch.AccDifferSubjService;

@Controller
@RequestMapping(value = "/hrp/acc/accvouch/diff")
public class DifferSubjController extends BaseController {

	@Resource(name = "accDifferSubjService")
	private AccDifferSubjService accDifferSubjService;

	@Resource(name = "accDifferService")
	private AccDifferService accDifferService;

	@RequestMapping(value = "/accDifferSubjMainPage")
	public String accDifferSubjMainPage(Model mode) throws Exception {
		return "hrp/acc/accvouch/diff/differsubj/accDifferSubjMain";
	}

	@RequestMapping(value = "/accDifferSubjAddPage")
	public String accDifferSubjAddPage(Model mode, String diff_item_code, String diff_type_code) throws Exception {
		mode.addAttribute("diff_item_code", diff_item_code);
		mode.addAttribute("diff_type_code", diff_type_code);
		return "hrp/acc/accvouch/diff/differsubj/accDifferSubjAdd";
	}

	/**
	 * 左侧树形结构
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDifferItemTree")
	public Map<String, Object> queryDifferItemTree(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = accDifferService.queryDifferItemTree(mapVo);
		return JSONObject.parseObject(result);
	}

	/**
	 * 右侧科目
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccDifferSubj")
	public Map<String, Object> queryAccDifferSubj(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String result = accDifferSubjService.queryAccDifferSubj(mapVo);
		return JSONObject.parseObject(result);
	}

	@ResponseBody
	@RequestMapping(value = "/queryAccDifferSubjForAdd")
	public Map<String, Object> queryAccDifferSubjForAdd(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String result = accDifferSubjService.queryAccDifferSubjForAdd(getPage(mapVo));
		return JSONObject.parseObject(result);
	}

	@ResponseBody
	@RequestMapping(value = "/addAccDifferSubj")
	public Map<String, Object> addAccDifferSubj(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String result = accDifferSubjService.addAccDifferSubj(mapVo);
		return JSONObject.parseObject(result);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteAccDifferSubj")
	public Map<String, Object> deleteAccDifferSubj(@RequestParam String mapVo, Model mode) {
		String result = accDifferSubjService.deleteAccDifferSubj(mapVo);
		return JSONObject.parseObject(result);
	}

}
