package com.chd.hrp.pac.controller.fkht.doc;

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
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKHTService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactFileFKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/pactdoc")
public class PactDocFileFKHTController extends BaseController {

	@Resource(name = "pactFileTypeService")
	private PactFileTypeService pactFileTypeService;

	@Resource(name = "pactFileFKHTService")
	private PactFileFKHTService pactFileFKHTService;

	@Resource(name = "pactDocFKHTService")
	private PactDocFKHTService pactDocFKHTService;

	@RequestMapping(value = "/pactFileFKHTMainPage", method = RequestMethod.GET)
	public String toPactFileFKHTMainPage() {
		return "hrp/pac/fkht/pactdoc/pactFileFKHTMain";
	}

	/**
	 * 查询归档项目与文件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFileTypeFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactFileTypeDocFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String string = pactFileFKHTService.queryPactFileTypeFKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveFileDocFKHT", method = RequestMethod.POST)
	public Map<String, Object> saveFileDocFKHT(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> newList = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			pactFileFKHTService.saveFileDocFKHT(newList);
			return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\"}");
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
	@RequestMapping(value = "/deletePactExecFKHTFile", method = RequestMethod.POST)
	public Map<String, Object> deletePactExecFKHTFile(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> list = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String string = pactFileFKHTService.deletePactExecFKHTFile(list);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
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
			mapVo.put("table_name", "PACT_FILE_FKHT");
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
	@RequestMapping(value = "/queryPactDocFKHTForExec", method = RequestMethod.POST)
	public Map<String, Object> queryPactDocFKHTForExec(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactDocFKHTService.queryPactDocFKHTForExec(mapVo);
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
	@RequestMapping(value = "/toPactFKHTSelectDocPage", method = RequestMethod.GET)
	public String toPactExecSelectDoc(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		mode.addAttribute("file_type", mapVo.get("file_type"));
		mode.addAttribute("_rowIndx", mapVo.get("_rowIndx"));
		mode.addAttribute("type_code", mapVo.get("type_code"));
		return "hrp/pac/fkht/pactdoc/pactFKHTSelectDoc";
	}

	/**
	 * 跳转文档管理页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactFKHTDocManagerPage", method = RequestMethod.GET)
	public String toPactFKHTDocManagerPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_name", mapVo.get("pact_name"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/fkht/pactdoc/pactFKHTDocManager";
	}

	/**
	 * 跳转归档归档管理页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactFKHTFileManagerPage", method = RequestMethod.GET)
	public String toPactFKHTFileManagerPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_name", mapVo.get("pact_name"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/fkht/pactdoc/pactFKHTFileManager";
	}
}
