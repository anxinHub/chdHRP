package com.chd.hrp.pac.controller.cmitype;

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
import com.chd.hrp.pac.entity.cmitype.PACTTemplateDataSource;
import com.chd.hrp.pac.service.cmitype.PACTTemplateDataSourceService;



@Controller
@RequestMapping(value = "/hrp/pac/cmitype")
public class PACTTemplateDataSourceController extends BaseController{
	@Resource(name = "PACTTemplateDataSourceService")
	private  PACTTemplateDataSourceService pactService ;
	
	

	@RequestMapping(value = "/PACTTemplateDataSourceMaintPage", method = RequestMethod.GET)
	public String toPACTTemplateDataSourceMaintPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("IFB_GROUPID",SessionManager.getGroupId());
		mode.addAttribute("IFB_PrjName",SessionManager.getHosId());
		mode.addAttribute("COPY_CODE",SessionManager.getCopyCode());
		return "hrp/pac/cmitype/PACTTemplateDataSource";
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPACTTemplateDataSource", method = RequestMethod.POST)
	public Map<String, Object> queryPACTTemplateDataSource(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("IFB_GROUPID", SessionManager.getGroupId());
			mapVo.put("IFB_PrjName", SessionManager.getHosId());
			mapVo.put("COPY_CODE", SessionManager.getCopyCode());
			String query = pactService.queryPACTTemplateDataSource(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addPACTTemplateDataSource", method = RequestMethod.POST)
	public Map<String, Object> addPACTTemplateDataSource(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTTemplateDataSource> listVo = JSONArray.parseArray(mapVo, PACTTemplateDataSource.class);
			String query = pactService.addPACTTemplateDataSource(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletePACTTemplateDataSource",method = RequestMethod.POST)
	public Map<String, Object> deletePACTTemplateDataSource(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTTemplateDataSource> listVo = JSONArray.parseArray(mapVo, PACTTemplateDataSource.class);
			String query = pactService.deletePACTTemplateDataSource(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	@ResponseBody
	@RequestMapping(value = "/updatePACTTemplateDataSource",method = RequestMethod.POST)
	public Map<String, Object> updatePACTTemplateDataSource(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTTemplateDataSource> listVo = JSONArray.parseArray(mapVo, PACTTemplateDataSource.class);
			String query = pactService.updatePACTTemplateDataSource(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
