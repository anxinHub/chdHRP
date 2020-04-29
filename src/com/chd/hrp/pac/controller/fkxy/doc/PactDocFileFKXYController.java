package com.chd.hrp.pac.controller.fkxy.doc;

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
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKXYService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactFileFKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/pactdoc")
public class PactDocFileFKXYController extends BaseController {

	@Resource(name = "pactFileTypeService")
	private PactFileTypeService pactFileTypeService;

	@Resource(name = "pactFileFKXYService")
	private PactFileFKXYService pactFileFKXYService;

	@Resource(name = "pactDocFKXYService")
	private PactDocFKXYService pactDocFKXYService;

	@RequestMapping(value = "/pactFileFKXYMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkxy/pactdoc/pactFileFKXYMain";
	}

	/**
	 * 查询归档项目与文件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFileTypeFKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactFileTypeDocFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactFileFKXYService.queryPactFileTypeFKXY(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "/saveFileDocFKXY", method = RequestMethod.POST)
	public Map<String, Object> saveFileDocFKXY(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> newList = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String result = pactFileFKXYService.saveFileDocFKXY(newList);
			return JSONObject.parseObject(result);
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
	@RequestMapping(value = "/deletePactExecFKXYFile", method = RequestMethod.POST)
	public Map<String, Object> deletePactExecFKXYFile(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> list = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String string = pactFileFKXYService.deletePactExecFKXYFile(list);
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
	@RequestMapping(value = "/toPactFKXYSelectDocPage", method = RequestMethod.GET)
	public String toPactFKXYSelectDocPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		mode.addAttribute("file_type", mapVo.get("file_type"));
		mode.addAttribute("_rowIndx", mapVo.get("_rowIndx"));
		mode.addAttribute("type_code", mapVo.get("type_code"));
		
		return "hrp/pac/fkxy/pactdoc/pactFKXYSelectDoc";
	}


	/**
	 * 归档管理
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactFKXYFileManagerPage", method = RequestMethod.GET)
	public String toPactFKXYFileManagerPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_name", mapVo.get("pact_name"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/fkxy/pactdoc/pactFKXYFileManager";
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
			mapVo.put("table_name", "PACT_FILE_FKXY");
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
	@RequestMapping(value = "/queryPactDocFKXYForExec", method = RequestMethod.POST)
	public Map<String, Object> queryPactDocFKXYForExec(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactDocFKXYService.queryPactDocFKXYForExec(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
