package com.chd.hrp.pac.controller.skht.doc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.doc.PactFileTypeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKHTService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactFileSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/pactdoc")
public class PactDocFileSKHTController extends BaseController {

	@Resource(name = "pactFileTypeService")
	private PactFileTypeService pactFileTypeService;

	@Resource(name = "pactFileSKHTService")
	private PactFileSKHTService pactFileSKHTService;

	@Resource(name = "pactDocSKHTService")
	private PactDocSKHTService pactDocSKHTService;

	@RequestMapping(value = "/pactFileSKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skht/pactdoc/pactFileSKHTMain";
	}

	/**
	 * 查询归档项目与文件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFileTypeSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactFileTypeDocSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactFileSKHTService.queryPactFileTypeSKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveFileDocSKHT", method = RequestMethod.POST)
	public Map<String, Object> saveFileDocSKHT(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> newList = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String query = pactFileSKHTService.saveFileDocSKHT(newList);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除归档
	 * 
	 * @param listVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactExecSKHTFile", method = RequestMethod.POST)
	public Map<String, Object> deletePactExecSKHTFile(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> list = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String string = pactFileSKHTService.deletePactExecSKHTFile(list);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 跳转选择文档页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactSKHTSelectDocPage", method = RequestMethod.GET)
	public String toPactSKHTSelectDocPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		mode.addAttribute("file_type", mapVo.get("file_type"));
		mode.addAttribute("_rowIndx", mapVo.get("_rowIndx"));
		mode.addAttribute("type_code", mapVo.get("type_code"));

		return "hrp/pac/skht/pactdoc/pactSKHTSelectDoc";
	}

	/**
	 * 归档管理
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactSKHTFileManagerPage", method = RequestMethod.GET)
	public String toPactSKHTFileManagerPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_name", mapVo.get("pact_name"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/skht/pactdoc/pactSKHTFileManager";
	}

	/**
	 * 查询归档
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFileType", method = RequestMethod.POST)
	public Map<String, Object> queryPactFileType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("table_name", "PACT_FILE_SKHT");
			String string = pactFileTypeService.queryPactFileTypeSimple(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询文档
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDocSKHTForExec", method = RequestMethod.POST)
	public Map<String, Object> queryPactDocSKHTForExec(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactDocSKHTService.queryPactDocSKHTForExec(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
