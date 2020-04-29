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
import com.chd.hrp.pac.service.basicset.doc.temp.PactFileTempFKXYService;

/**
 * 付款协议归档模版
 * 
 * @author haotong
 * @date 2018年8月23日 下午2:58:39
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/filetemp/fkxy")
public class PactFileTempFKXYController extends BaseController {

	@Resource(name = "pactFileTempFKXYService")
	private PactFileTempFKXYService pactFileTempFKXYService;

	@RequestMapping(value = "/PactFileTempFKXYMainPage")
	public String toPactFileTempFKXYMainPage() {
		return "hrp/pac/basicset/doc/filetemp/fkxy/pactFileTempFKXYMain";
	}

	@RequestMapping(value = "/PactFileTempFKXYAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/filetemp/fkxy/pactFileTempFKXYAdd";
	}
	@RequestMapping(value = "/pactFileTempFKXYInsert")
	public String toPactNatureInsert() {
		return "hrp/pac/basicset/doc/filetemp/fkxy/pactFileTempFKXYInsert";
	}
	@RequestMapping(value = "/queryPactFileTempFKXY", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactFileTempFKXYService.queryPactFileTempFKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}


	/**
	 * 查询页面中中的树形结构
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactTypeFKXY", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPactTypeFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactFileTempFKXYService.queryPactTypeFKXYTree(getPage(mapVo));
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/addPactFileTempFKXY", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactFileTempFKXY(@RequestParam String paramVo, Model mode) {
		try {
			List<PactFileTempEntity> listVo = JSONArray.parseArray(paramVo, PactFileTempEntity.class);
			String query = pactFileTempFKXYService.addPactFileTempFKXY(listVo);
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
	@RequestMapping(value="/queryPactFileTempFKXYfile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempFKXYfile(@RequestParam Map<String, Object> mapVo, Model mode) {
		
	  try{
		  mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//String query = pactFileTempFKHTService.queryPactFileTempFKHTfile(mapVo);
			String query =pactFileTempFKXYService.queryPactFileTempFKXYfile(mapVo);
			return JSONObject.parseObject(query);
	  }catch(Exception e){
		  
		  return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		  
	  }
	}
	
}
