package com.chd.hrp.pac.controller.basicset.doc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
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
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.doc.PactDocTypeService;

/**
 * 文档类别
 * 
 * @author haotong
 * @date 2018年8月23日 下午2:53:46
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/doctype")
public class PactDocTypeController extends BaseController {

	@Resource(name = "pactDocTypeService")
	private PactDocTypeService PactDocTypeService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactDocTypeMainPage")
	public String toPactDocTypeMainPage() {
		return "hrp/pac/basicset/doc/doctype/pactDocTypeMain";
	}

	@RequestMapping(value = "/PactDocTypeAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/doctype/pactDocTypeAdd";
	}

	/**
	 * 主查询方法
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactDocType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactDocType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = PactDocTypeService.queryPactDocType(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/PactDocTypeEdit", method = RequestMethod.GET)
	public String toPactNatureEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactDocTypeEntity entity = PactDocTypeService.queryPactDocTypeByCode(mapVo);

		if (entity != null) {
			mode.addAttribute("type_code", entity.getType_code());
			mode.addAttribute("type_name", entity.getType_name());
			mode.addAttribute("spell_code", entity.getSpell_code());
			mode.addAttribute("wbx_code", entity.getWbx_code());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		
		return "hrp/pac/basicset/doc/doctype/pactDocTypeEdit";
	}

	/**
	 * 新增
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/addPactDocType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactDocType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = PactDocTypeService.addPactDocType(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 更新
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/updatePactDocType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactDocType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 通过页面中传递过来的check关键字判断是否校验过，在更新式首先进行教养
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_DOC_TYPE", mapVo.get("type_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"msg\":\"数据存在.\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = PactDocTypeService.updatePactDocType(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/deletePactDocType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactDocType(@RequestParam String paramVo, Model mode) {
		try {
			List<PactDocTypeEntity> listVo = JSONArray.parseArray(paramVo, PactDocTypeEntity.class);
			for (PactDocTypeEntity entity : listVo) {
				String queryNatureExists = pactDeleteService.isExistsDataByTable("PACT_DOC_TYPE", entity.getType_code());
				if (queryNatureExists != null) {
					queryNatureExists = queryNatureExists.substring(0, queryNatureExists.length() - 1);
					return JSONObject.parseObject("{\"error\":\"该数据已在" + queryNatureExists + "中使用，不可删除\"}");
				}
			}
			String query = PactDocTypeService.deletePactDocType(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
