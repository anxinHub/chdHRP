package com.chd.hrp.pac.controller.skxy.doc;

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
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKXYService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactFileSKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/pactdoc")
public class PactDocFileSKXYController extends BaseController {

	@Resource(name = "pactFileTypeService")
	private PactFileTypeService pactFileTypeService;

	@Resource(name = "pactFileSKXYService")
	private PactFileSKXYService pactFileSKXYService;

	@Resource(name = "pactDocSKXYService")
	private PactDocSKXYService pactDocSKXYService;

	@RequestMapping(value = "/pactFileSKXYMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skxy/pactdoc/pactFileSKXYMain";
	}

	/**
	 * 查询归档项目与文件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFileTypeSKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactFileTypeDocSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactFileSKXYService.queryPactFileTypeSKXY(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}


	@ResponseBody
	@RequestMapping(value = "/saveFileDocSKXY", method = RequestMethod.POST)
	public Map<String, Object> saveFileDocSKXY(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> newList = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			String query = pactFileSKXYService.saveFileDocSKXY(newList);
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
	@RequestMapping(value = "/deletePactExecSKXYFile", method = RequestMethod.POST)
	public Map<String, Object> deletePactExecSKXYFile(@RequestParam String listVo, Model mode) {
		try {
			List<Map<String, Object>> list = JSON.parseObject(listVo, new TypeReference<List<Map<String, Object>>>() {});
			if (!list.isEmpty()) {
				pactFileSKXYService.deletePactExecSKXYFile(list);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
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
	@RequestMapping(value = "/toPactSKXYSelectDocPage", method = RequestMethod.GET)
	public String toPactSKXYSelectDocPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		mode.addAttribute("file_type", mapVo.get("file_type"));
		mode.addAttribute("_rowIndx", mapVo.get("_rowIndx"));
		mode.addAttribute("type_code", mapVo.get("type_code"));
		return "hrp/pac/skxy/pactdoc/pactSKXYSelectDoc";
	}


	/**
	 * 归档管理
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/toPactSKXYFileManagerPage", method = RequestMethod.GET)
	public String toPactSKXYFileManagerPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_name", mapVo.get("pact_name"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/skxy/pactdoc/pactSKXYFileManager";
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
			mapVo.put("table_name", "PACT_FILE_SKXY");
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
	@RequestMapping(value = "/queryPactDocSKXYForExec", method = RequestMethod.POST)
	public Map<String, Object> queryPactDocSKXYForExec(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactDocSKXYService.queryPactDocSKXYForExec(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
