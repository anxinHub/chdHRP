package com.chd.hrp.hip.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hip.entity.HipDataJob;
import com.chd.hrp.hip.service.HipDataJobService;

@Controller
@RequestMapping(value = "/hrp/hip/dataJob")
public class HipDataJobController  extends BaseController {
	
	private static Logger logger = Logger.getLogger(HipDataJobController.class);
	@Autowired
	private HipDataJobService dataJobService;

	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String dataJobMainPage(Model mode) throws Exception {
		return "hrp/hip/dataJob/main";
	}
	@RequestMapping(value = "/setCron", method = RequestMethod.GET)
	public String hrCaltransMainPage(Model mode,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("ptype",mapVo.get("ptype"));
		mode.addAttribute("cron",mapVo.get("cron"));
		return "hrp/hip/dataJob/setCron";
	}
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addDataJobPage(Model mode) throws Exception {
		
		return "hrp/hip/dataJob/add";
	}
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public String updateDataJobPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		HipDataJob job=null;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("mod_code", SessionManager.getModCode());
		if(mapVo.get("id")!=null){
			job=dataJobService.queryDataJobById(mapVo);
			mode.addAttribute("job",job);
		}
		return "hrp/hip/dataJob/update";
	}
	@RequestMapping(value = "/toUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String toAadd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("mod_code", SessionManager.getModCode());
		return dataJobService.updateDataJob(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrCaltranss(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("mod_code", SessionManager.getModCode());

		String hrFun =dataJobService.queryDataJob(getPage(mapVo));
		return JSONObject.parseObject(hrFun);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("mod_code", SessionManager.getModCode());
			mapVo.put("state", 0);
			return dataJobService.addDataJob(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipAssRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo=null;
		for (String id : paramVo.split(",")) {
			mapVo = new HashMap<String, Object>();
			mapVo.put("id",id);
			listVo.add(mapVo);
		}
		String apt = dataJobService.deleteDataJob(listVo);
		return JSONObject.parseObject(apt);
		

	}
	
	@RequestMapping(value = "/startDataJob", method = RequestMethod.POST)
	@ResponseBody
	public String startDataJob(HttpServletRequest request,@RequestParam String ParamVo, Model mode) throws Exception {

		try {
			String uri=request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			uri+="/hrp/hip/dataType/syncData.do?isCheck=false";
			String[] a=ParamVo.split(",");
			if(a.length<1)
				return "{\"error\":\" 没有数据 \"}";
			List<String> listVo = Arrays.asList(a);
			return dataJobService.startDataJob(listVo,uri);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@RequestMapping(value = "/stopDataJob", method = RequestMethod.POST)
	@ResponseBody
	public String stopDataJob(@RequestParam String ParamVo, Model mode) throws Exception {

		try {
			String[] a=ParamVo.split(",");
			if(a.length<1)
				return "{\"error\":\" 没有数据 \"}";
			List<String> listVo = Arrays.asList(a);
			return dataJobService.stopDataJob(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@RequestMapping(value = "/queryDataType", method = RequestMethod.POST)
	@ResponseBody
	public String queryModDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (!SessionManager.getModCode().equals("00")) {
			mapVo.put("mod_code", SessionManager.getModCode());
		}
		
		String mod = dataJobService.queryDataType(mapVo);
		return mod;

	}
}
