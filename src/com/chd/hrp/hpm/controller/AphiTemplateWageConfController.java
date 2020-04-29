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
import com.chd.hrp.hpm.service.AphiTemplateWageConfService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiTemplateWageConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiTemplateWageConfController.class);
	
	@Resource(name = "aphiTemplateWageConfService")
	private final AphiTemplateWageConfService aphiTemplateWageConfService = null;
	

	//工资与奖金项目配置  主页面跳转
	@RequestMapping(value = "/hrp/hpm/templatewageconf/hpmTemplateWageConfMainPage", method = RequestMethod.GET)
	public String hpmTemplateWageConfMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> map = aphiTemplateWageConfService.queryAphiTemplateWageConfByCode(mapVo);
		
		if(map !=null){
			
			mode.addAttribute("is_grant", map.get("is_grant"));
			
			mapVo.put("wage_code", map.get("wage_code"));
			
			Map<String,Object> mapWage = aphiTemplateWageConfService.queryWage(mapVo);
			
			mode.addAttribute("wage_code", map.get("wage_code"));
			
			mode.addAttribute("wage_name", mapWage.get("wage_name"));
			//map.put("", value)
		}else{
			mode.addAttribute("is_grant", "");
			//map.put("", value)
		}
       
		return "hrp/hpm/templatewageconf/hpmTemplateWageConfMain";
	}
	
	//工资与奖金项目配置 保存
	@RequestMapping(value = "/hrp/hpm/templatewageconf/addHpmTemplateWageConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmTemplateWageConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String aphiTemplateWageConfJson = null;
		
		try{
			aphiTemplateWageConfJson = aphiTemplateWageConfService.addAphiTemplateWageConf(mapVo);
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			aphiTemplateWageConfJson = e.getMessage();
		}
		
		return JSONObject.parseObject(aphiTemplateWageConfJson);

	}
	
	
	/**
	 * @Description 工资与奖金项目配置  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatewageconf/queryHpmTemplateWageConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTemplateWageConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String aphiTemplateWageConfJson = aphiTemplateWageConfService.queryAphiTemplateWageConfDetail(getPage(mapVo));

		return JSONObject.parseObject(aphiTemplateWageConfJson);

	}
	
	
	/**
	 * @Description
	 * 工资与奖金项目配置  查询工资项
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatewageconf/queryWageItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWageItem(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String aphiTemplateWageConfJson  = null;
		try {
			
			aphiTemplateWageConfJson = aphiTemplateWageConfService.queryWageItem(getPage(mapVo));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			aphiTemplateWageConfJson = e.getMessage();
		}

		return JSONObject.parseObject(aphiTemplateWageConfJson);

	}
	
	
	/**
	 * 删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/templatewageconf/deleteWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
		
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("wage_code", ids[3]);
			mapVo.put("emp_item", ids[4]);
			listVo.add(mapVo);
		}

		String	tempWageJson = aphiTemplateWageConfService.deleteBatchWage(listVo);
		
		return JSONObject.parseObject(tempWageJson);
	}
	
}
