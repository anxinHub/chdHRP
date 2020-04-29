package com.chd.hrp.pac.controller.basicset.doc;

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
import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;
import com.chd.hrp.pac.service.basicset.doc.temp.PactFileTempFKHTService;

/**
 * 付款合同归档模版
 * 
 * @author haotong
 * @date 2018年8月23日 下午2:56:48
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/filetemp/fkht")
public class PactFileTempFKHTController extends BaseController {

	@Resource(name = "pactFileTempFKHTService")
	private PactFileTempFKHTService pactFileTempFKHTService;

	@RequestMapping(value = "/PactFileTempFKHTMainPage")
	public String toPactFileTempFKHTMainPage() {
		return "hrp/pac/basicset/doc/filetemp/fkht/pactFileTempFKHTMain";
	}

	@RequestMapping(value = "/PactFileTempFKHTAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/filetemp/fkht/pactFileTempFKHTAdd";
	}

	@RequestMapping(value = "/PactFileTempFKHTInsert")
	public String toPactNatureInsert() {
		return "hrp/pac/basicset/doc/filetemp/fkht/pactFileTempFKHTInsert";
	}

	 
	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactFileTempFKHT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactFileTempFKHTService.queryPactFileTempFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询页面中的树形结构
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactTypeFKHT", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPactTypeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactFileTempFKHTService.queryPactTypeFKHTTree(getPage(mapVo));
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/addPactFileTempFKHT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactFileTempFKHT(@RequestParam String paramVo, Model mode) {
		try {
			List<PactFileTempEntity> listVo = JSONArray.parseArray(paramVo, PactFileTempEntity.class);
			String query = pactFileTempFKHTService.addPactFileTempFKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 勾选查询
	 * @author lh0225
	 * @param mapvo
	 * 
	 * 
	 */
	@RequestMapping(value="/queryPactFileTempFKHTfile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempFKHTfile(@RequestParam Map<String, Object> mapVo, Model mode) {
		
	  try{
		  mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactFileTempFKHTService.queryPactFileTempFKHTfile(mapVo);
			return JSONObject.parseObject(query);
	  }catch(Exception e){
		  
		  return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		  
	  }
	}
	
}
