/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.service.AphiTemplateDataService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiTemplateDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiTemplateDataController.class);
	
	@Resource(name = "aphiTemplateDataService")
	private final AphiTemplateDataService aphiTemplateDataService = null;

	//主页面跳转
	@RequestMapping(value = "/hrp/hpm/templatedata/hpmTemplateDataMainPage", method = RequestMethod.GET)
	public String accBusiTemplateMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
       
		return "hrp/hpm/templatedata/hpmTemplateDataMain";
	}
	
	//保存
	@RequestMapping(value = "/hrp/hpm/templatedata/saveHpmTemplateData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHpmTemplateData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String aphiTemplateDataJson = null;
		
		try{
			aphiTemplateDataJson = aphiTemplateDataService.addTemplateData(mapVo);
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			aphiTemplateDataJson = e.getMessage();
		}
		
		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	/**
	 * @Description 查询 主表数据  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/queryAphiTemplateDataTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAphiTemplateDataTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String aphiTemplateDataJson = aphiTemplateDataService.queryAphiTemplateDataTree(mapVo);

		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	/**
	 * @Description 按编码查询 主表数据  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/queryHpmTemplateData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTemplateData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String aphiTemplateDataJson = aphiTemplateDataService.queryAphiTemplateDataByCode(mapVo);

		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	/**
	 * @Description 按编码查询 明细数据 AphiTemplateDetailData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/queryAphiTemplateDetailDataByTemplateCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAphiTemplateDetailDataByTemplateCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String aphiTemplateDataJson = aphiTemplateDataService.queryAphiTemplateDetailDataByTemplateCode(mapVo);

		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	/**
	 * @Description 删除模板 TemplateDetailData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/deleteHpmTemplateData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTemplateData(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String aphiTemplateDataJson  = null;
		try {
			
			aphiTemplateDataJson = aphiTemplateDataService.deleteAphiTemplateData(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			aphiTemplateDataJson = e.getMessage();
		}

		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	/**
	 * @Description 删除明细 TemplateDetailData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/deleteHpmTemplateDataDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTemplateDataDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for(String codes : paramVo.split(",")){
			String [] code = codes.split("@");
			
			Map<String,Object> mapVo = new HashMap<String,Object>();
			
			mapVo.put("group_id", code[0]);
			mapVo.put("hos_id", code[1]);
			mapVo.put("copy_code",code[2]);
			mapVo.put("template_code",code[3]);
			mapVo.put("template_detail_code",code[4]);
			
			list.add(mapVo);
		}
		
		String aphiTemplateDataJson  = null;
		try {
			
			aphiTemplateDataJson = aphiTemplateDataService.deleteBatchAphiTemplateDetailData(list);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			aphiTemplateDataJson = e.getMessage();
		}

		return JSONObject.parseObject(aphiTemplateDataJson);

	}
	
	//自定义页面跳转
	@RequestMapping(value = {
			"/hrp/hpm/templatedata/hpmTemplateDataForParseDataMainPage01",	//自定义收入数据准备
			"/hrp/hpm/templatedata/hpmTemplateDataForParseDataMainPage02",	//自定义工作量数据准备
			"/hrp/hpm/templatedata/hpmTemplateDataForParseDataMainPage03",	//自定义支出数据准备
			"/hrp/hpm/templatedata/hpmTemplateDataForParseDataMainPage04"	//自定义核算项目点数准备
			
	}, method = RequestMethod.GET)
	public String hpmTemplateDataForParseDataMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("page_code", mapVo.get("page_code"));
		mode.addAttribute("template_table", mapVo.get("template_table"));
			
		return "hrp/hpm/templatedata/hpmTemplateDataForParseDataMain";
	}
	
	/**
	 * @Description 查询动态表头  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatedata/queryHpmTemplateDataColumnHeads", method = RequestMethod.POST)
	@ResponseBody
	public  String queryHpmTemplateDataColumnHeads(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String aphiTemplateDataJson = aphiTemplateDataService.queryAphiTemplateDataColumnHeads(mapVo);

		return aphiTemplateDataJson;

	}
	
	/**
	 * @Description 自定义页面 查询  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = {
			"/hrp/hpm/templatedata/queryHpmTemplateDataForParseData01",		//自定义收入数据准备
			"/hrp/hpm/templatedata/queryHpmTemplateDataForParseData02",		//自定义工作量数据准备
			"/hrp/hpm/templatedata/queryHpmTemplateDataForParseData03",		//自定义支出数据准备
			"/hrp/hpm/templatedata/queryHpmTemplateDataForParseData04"		//自定义核算项目点数准备
	}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTemplateDataForParseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String dataJson = aphiTemplateDataService.queryHpmTemplateDataForParseData(mapVo);

		return JSONObject.parseObject(dataJson);

	}
	
	/**
	 * @Description 自定义页面 修改  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = {
			"/hrp/hpm/templatedata/updateHpmTemplateDataForParseData01",		//自定义收入数据准备
			"/hrp/hpm/templatedata/updateHpmTemplateDataForParseData02",		//自定义工作量数据准备
			"/hrp/hpm/templatedata/updateHpmTemplateDataForParseData03",			//自定义支出数据准备
			"/hrp/hpm/templatedata/updateHpmTemplateDataForParseData04"			//自定义核算项目点数准备
	}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmTemplateDataForParseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String dataJson = null;
		try {
			
			dataJson = aphiTemplateDataService.updateHpmTemplateDataForParseData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			dataJson = e.getMessage();
		}

		return JSONObject.parseObject(dataJson);

	}
	
	/**
	 * @Description 自定义页面 删除  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = {
			"/hrp/hpm/templatedata/deleteHpmTemplateDataForParseData01", 		//自定义收入数据准备
			"/hrp/hpm/templatedata/deleteHpmTemplateDataForParseData02",		//自定义工作量数据准备
			"/hrp/hpm/templatedata/deleteHpmTemplateDataForParseData03",			//自定义支出数据准备
			"/hrp/hpm/templatedata/deleteHpmTemplateDataForParseData04"			//自定义核算项目点数准备
	},method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTemplateDataForParseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String dataJson = null;
		try {
			
			dataJson = aphiTemplateDataService.deleteHpmTemplateDataForParseData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			dataJson = e.getMessage();
		}

		return JSONObject.parseObject(dataJson);

	}
	
	
	/**
	 * @Description 自定义页面 生成  AphiTemplateData
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = {
			"/hrp/hpm/templatedata/initHpmTemplateDataForParseData01",		//自定义收入数据准备
			"/hrp/hpm/templatedata/initHpmTemplateDataForParseData02",		//自定义工作量数据准备
			"/hrp/hpm/templatedata/initHpmTemplateDataForParseData03",		//自定义支出数据准备
			"/hrp/hpm/templatedata/initHpmTemplateDataForParseData04"		//自定义核算项目点数准备
	}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmTemplateDataForParseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String dataJson = null;
		
		try {
			
			dataJson = aphiTemplateDataService.initHpmTemplateDataForParseData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			dataJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(dataJson);
		
	}
}
