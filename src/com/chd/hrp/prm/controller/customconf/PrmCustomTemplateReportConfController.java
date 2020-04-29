package com.chd.hrp.prm.controller.customconf;

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
import com.chd.hrp.prm.service.customconf.PrmCustomTemplateReportConfService;


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
public class PrmCustomTemplateReportConfController extends BaseController {
	
	private static Logger logger = Logger.getLogger(PrmCustomTemplateReportConfController.class);
	
	@Resource(name = "prmCustomTemplateReportConfService")
	private final PrmCustomTemplateReportConfService prmCustomTemplateReportConfService = null;
	
	@RequestMapping(value="hrp/prm/customconf/prmCustomTemplateReportConfMainPage",method = RequestMethod.GET)
	public String hpmCustomTemplateReportConfMainPage(Model mode) throws Exception {
		
		return "hrp/prm/customconf/prmCustomTemplateReportConfMain";
	}
	
	
	//查询
	@RequestMapping(value="hrp/prm/customconf/queryPrmCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryPrmCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = prmCustomTemplateReportConfService.queryPrmCustomTemplateReportConf(mapVo);
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//查询菜单
	@RequestMapping(value="hrp/prm/customconf/queryPrmCustomTemplateReportConfTree",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryPrmCustomTemplateReportConfTree(@RequestParam Map<String,Object> mapVo,Model mode){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String resultJson = prmCustomTemplateReportConfService.queryPrmCustomTemplateReportTree(mapVo);
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	
	//添加
	@RequestMapping(value="hrp/prm/customconf/addPrmCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPrmCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
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
			
			resultJson = prmCustomTemplateReportConfService.addPrmCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//修改
	@RequestMapping(value="hrp/prm/customconf/updatePrmCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePrmCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
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
			
			resultJson = prmCustomTemplateReportConfService.updatePrmCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
	
	
	//删除
	@RequestMapping(value="hrp/prm/customconf/deletePrmCustomTemplateReportConf",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deletePrmCustomTemplateReportConf(@RequestParam Map<String,Object> mapVo,Model mode){
		
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
			
			resultJson = prmCustomTemplateReportConfService.deletePrmCustomTemplateReportConf(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			resultJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		
		return JSONObject.parseObject(resultJson);
	}
	
	@RequestMapping(value = "/hrp/prm/customconf/queryPrmTemplateKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmTemplateKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmTemplateKind = prmCustomTemplateReportConfService.queryPrmTemplateKind(mapVo);

		return prmTemplateKind;
	}
	
}
