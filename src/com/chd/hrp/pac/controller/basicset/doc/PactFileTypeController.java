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
import com.chd.hrp.pac.entity.basicset.doc.PactFileTypeEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.doc.PactFileTypeService;

/**
 * 归档项目
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:01:46
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/filetype")
public class PactFileTypeController extends BaseController {

	@Resource(name = "pactFileTypeService")
	private PactFileTypeService PactFileTypeService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactFileTypeMainPage")
	public String toPactFileTypeMainPage() {
		return "hrp/pac/basicset/doc/filetype/pactFileTypeMain";
	}

	@RequestMapping(value = "/PactFileTypeAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/filetype/pactFileTypeAdd";
	}

	@RequestMapping(value = "/queryPactFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = PactFileTypeService.queryPactFileType(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/PactFileTypeEdit", method = RequestMethod.GET)
	public String toPactNatureEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactFileTypeEntity entity = PactFileTypeService.queryPactFileTypeByCode(mapVo);
		mode.addAttribute("entity", entity);

		return "hrp/pac/basicset/doc/filetype/pactFileTypeEdit";
	}

	@RequestMapping(value = "/addPactFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactFileType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = PactFileTypeService.addPactFileType(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updatePactFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactFileType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 通过页面中传递过来的check关键字判断是否校验过，在更新时首先进行校验
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_File_TYPE", mapVo.get("type_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"msg\":\"数据存在.\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = PactFileTypeService.updatePactFileType(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deletePactFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactFileType(@RequestParam String paramVo, Model mode) {
		try {
			List<PactFileTypeEntity> listVo = JSONArray.parseArray(paramVo, PactFileTypeEntity.class);

			StringBuffer buffer = new StringBuffer();
			for (PactFileTypeEntity entity : listVo) {
				buffer.append(entity.getType_code()).append("','");
			}
			buffer.delete(buffer.length() - 3, buffer.length());
			String queryNatureExists = pactDeleteService.isExistsDataByTable("PACT_File_TYPE", buffer);
			if (queryNatureExists != null) {
				queryNatureExists = queryNatureExists.substring(0, queryNatureExists.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + queryNatureExists + "中使用，不可删除\"}");
			}

			String query = PactFileTypeService.deletePactFileType(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
