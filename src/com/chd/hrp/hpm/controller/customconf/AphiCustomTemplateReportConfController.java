package com.chd.hrp.hpm.controller.customconf;

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
import com.chd.hrp.hpm.service.customconf.AphiCustomTemplateReportConfService;


/**
 * 
 * @Title.
 * 自定义模板配置
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiCustomTemplateReportConfController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AphiCustomTemplateReportConfController.class);
	
	@Resource(name = "aphiCustomTemplateReportConfService")
	private final AphiCustomTemplateReportConfService aphiCustomTemplateReportConfService = null;
	
	@RequestMapping(value="hrp/hpm/customconf/hpmCustomTemplateReportConfMainPage",method = RequestMethod.GET)
	public String hpmCustomTemplateReportConfMainPage(Model mode) throws Exception {
		
		return "hrp/hpm/customconf/hpmCustomTemplateReportConfMain";
	}
	
	
	//查询
	@RequestMapping(value="hrp/hpm/customconf/queryAphiCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAphiCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = aphiCustomTemplateReportConfService.queryAphiCustomTemplateReportConf(mapVo);
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//查询菜单
	@RequestMapping(value="hrp/hpm/customconf/queryAphiCustomTemplateReportConfTree",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAphiCustomTemplateReportConfTree(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = aphiCustomTemplateReportConfService.queryAphiCustomTemplateReportTree(mapVo);
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	
	//添加
	@RequestMapping(value="hrp/hpm/customconf/addAphiCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addAphiCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = null;
		
		try {
			
			resultJson = aphiCustomTemplateReportConfService.addAphiCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//修改
	@RequestMapping(value="hrp/hpm/customconf/updateAphiCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAphiCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = null;
		
		try {
			
			resultJson = aphiCustomTemplateReportConfService.updateAphiCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//删除
	@RequestMapping(value="hrp/hpm/customconf/deleteAphiCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteAphiCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = null;
		
		try {
			
			resultJson = aphiCustomTemplateReportConfService.deleteAphiCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
}
