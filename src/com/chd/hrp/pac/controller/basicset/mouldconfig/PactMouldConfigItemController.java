package com.chd.hrp.pac.controller.basicset.mouldconfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.mouldconfig.PactMouldConfigItemService;

/**
 * 合同模板配置项
 * 
 * @author wjt
 * @date 2020年3月9日 下午5:10:52
 */
@Controller
public class PactMouldConfigItemController extends BaseController {
	
	@Resource(name = "pactMouldConfigItemService")
	private PactMouldConfigItemService pactMouldConfigItemService;

	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/pactMouldConfigItemMainPage", method = RequestMethod.GET)
	public String pactMouldConfigItemMainPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		//mode.addAttribute("pact_code", mapVo.get("pact_code"));
		//mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/basicset/mouldconfig/pactMouldConfigItemMain";
	}
	
	/***
	 * 合同管理配置界面主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/queryPactMouldConfigItem", method = RequestMethod.POST)
	public Map<String, Object> queryPactMouldConfigItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception 
	{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String query = pactMouldConfigItemService.queryPactMouldConfig(mapVo);
			
			return JSONObject.parseObject(query);
	}
	
	/**
	 * 暂未使用
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/hrp/pac/basicset/mouldconfig/queryDataPropertySelect",method = RequestMethod.POST)
	public Map<String, Object> queryDataPropertySelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception
	{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String matBillMain = pactMouldConfigItemService.queryDataPropertySelect(getPage(mapVo));

			return JSONObject.parseObject(matBillMain);
		
	}
	
	/**
	 * 行编辑增加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/addPactMouldConfigItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactMouldConfigItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		System.out.println(mapVo);
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matfimtypeJson="";
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("mapVo").toString());
		for (Map<String,Object> map : detail) {
			 matfimtypeJson = pactMouldConfigItemService.add(map);
		}
		
		return JSONObject.parseObject(matfimtypeJson);

	}
	
	/**
	 * 行编辑删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/deletePactMouldConfigItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactMouldConfigItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("mould_id", ids[3]);

			listVo.add(mapVo);

		}

		String matLocationTypeJson = pactMouldConfigItemService.deleteBatch(listVo);

		return JSONObject.parseObject(matLocationTypeJson);
	}
	
	/**
	 * 界面编制项数据集下拉查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/queryMouldItem", method = RequestMethod.POST)
	
	public String queryMouldItem(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactMouldConfigItemService.queryMouldItem(getPage(mapVo));
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 界面行编辑更新操作
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/pac/basicset/mouldconfig/updatePactMouldConfigItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactMouldConfigItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matfimtypeJson="";
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("mapVo").toString());
		for (Map<String,Object> map : detail)
			matfimtypeJson = pactMouldConfigItemService.update(map);
		
		return JSONObject.parseObject(matfimtypeJson);

	}
	
	
}
