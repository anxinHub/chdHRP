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
import com.chd.hrp.pac.service.basicset.doc.temp.PactFileTempSKHTService;

/**
 * 收款合同归档模版
 * 
 * @author haotong
 * @date 2018年8月23日 下午2:59:43
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/filetemp/skht")
public class PactFileTempSKHTController extends BaseController {

	@Resource(name = "pactFileTempSKHTService")
	private PactFileTempSKHTService pactFileTempSKHTService;

	@RequestMapping(value = "/PactFileTempSKHTMainPage")
	public String toPactFileTempSKHTMainPage() {
		return "hrp/pac/basicset/doc/filetemp/skht/pactFileTempSKHTMain";
	}

	@RequestMapping(value = "/PactFileTempSKHTAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/filetemp/skht/pactFileTempSKHTAdd";
	}
	@RequestMapping(value = "/PactFileTempSKHTInsert")
    
	public String toPactNatureInsert() {
		
		return "hrp/pac/basicset/doc/filetemp/skht/pactFileTempSKHTInsert";
	}
	
	@RequestMapping(value = "/queryPactFileTempSKHT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactFileTempSKHTService.queryPactFileTempSKHT(mapVo);
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
	@RequestMapping(value = "/queryPactTypeSKHT", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPactTypeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactFileTempSKHTService.queryPactTypeSKHTTree(getPage(mapVo));
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/addPactFileTempSKHT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactFileTempSKHT(@RequestParam String paramVo, Model mode) {
		try {
			List<PactFileTempEntity> listVo = JSONArray.parseArray(paramVo, PactFileTempEntity.class);
			String query = pactFileTempSKHTService.addPactFileTempSKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	

	/**
	 * 勾选查询
	 * @author lh0227
	 * @param mapvo
	 * 
	 * 
	 */
	@RequestMapping(value="/queryPactFileTempSKHTfile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempSKHTfile(@RequestParam Map<String, Object> mapVo, Model mode) {
		
	  try{
		  mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
			String query =pactFileTempSKHTService.queryPactFileTempSKHTfile(mapVo);
			return JSONObject.parseObject(query);
	  }catch(Exception e){
		  
		  return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		  
	  }
	}
	
}
