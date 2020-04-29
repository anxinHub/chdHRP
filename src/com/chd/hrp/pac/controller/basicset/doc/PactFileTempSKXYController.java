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
import com.chd.hrp.pac.service.basicset.doc.temp.PactFileTempSKXYService;

/**
 * 收款协议归档模版
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:01:02
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/doc/filetemp/skxy")
public class PactFileTempSKXYController extends BaseController {

	@Resource(name = "pactFileTempSKXYService")
	private PactFileTempSKXYService pactFileTempSKXYService;

	@RequestMapping(value = "/PactFileTempSKXYMainPage")
	public String toPactFileTempSKXYMainPage() {
		return "hrp/pac/basicset/doc/filetemp/skxy/pactFileTempSKXYMain";
	}

	@RequestMapping(value = "/PactFileTempSKXYAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/doc/filetemp/skxy/pactFileTempSKXYAdd";
	}
    @RequestMapping(value = "/PactFileTempSKXYInsert")
    public String toPactNatureInsert(){
    	
    	return "hrp/pac/basicset/doc/filetemp/skxy/pactFileTempSKXYInsert";
    }
	@RequestMapping(value = "/queryPactFileTempSKXY", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactFileTempSKXYService.queryPactFileTempSKXY(mapVo);
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
	@RequestMapping(value = "/queryPactTypeSKXY", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPactTypeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactFileTempSKXYService.queryPactTypeSKXYTree(getPage(mapVo));
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/addPactFileTempSKXY", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactFileTempSKXY(@RequestParam String paramVo, Model mode) {
		try {
			List<PactFileTempEntity> listVo = JSONArray.parseArray(paramVo, PactFileTempEntity.class);
			String query = pactFileTempSKXYService.addPactFileTempSKXY(listVo);
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
	@RequestMapping(value="/queryPactFileTempSKXYfile",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactFileTempSKXYfile(@RequestParam Map<String, Object> mapVo, Model mode) {
		
	  try{
		  mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
			String query =pactFileTempSKXYService.queryPactFileTempSKXYfile(mapVo);
			return JSONObject.parseObject(query);
	  }catch(Exception e){
		  
		  return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		  
	  }
	}
	
}
