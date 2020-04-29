package com.chd.hrp.mat.controller.storage.traceability;

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
import com.chd.hrp.mat.service.storage.traceability.MatInvTraceabilityService;


@Controller
public class MatInvTraceabilityController extends BaseController {

	
	private static Logger logger = Logger.getLogger(MatInvTraceabilityController.class);
	
	
	@Resource(name = "matInvTraceabilityService")
	private final MatInvTraceabilityService matInvTraceabilityService = null;   
	
	/**
	 * @Description 主页面跳转
	 * @param 耗材追溯查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/traceability/matInvTraceabilityPage", method = RequestMethod.GET)
	public String matInvTraceabilityPage(Model mode) throws Exception {

		return "hrp/mat/storage/traceability/matInvTraceability";
	}
	
	
	/**
	 * 左边页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/traceability/matInvTraceabilityAddPage", method = RequestMethod.GET)
	public String matInvTraceabilityAddPage(Model mode) throws Exception {

		return "hrp/mat/storage/traceability/matInvTraceabilityAdd";
	}
	
	/**
	 * 左边页面查询数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/traceability/queryMatInvTraceability", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryMatInvTraceability(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());  
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String inv = matInvTraceabilityService.queryMatInvTraceability(getPage(mapVo));

		return JSONObject.parseObject(inv);
		
	}
	
	
	/**
	 * 查询右边数据
	 */
	@RequestMapping(value = "/hrp/mat/storage/traceability/queryMatInvTraceabilityBar_code", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvTraceabilityBar_code(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if("-".equals(  mapVo.get("bar_code"))){
			mapVo.put("bar_code", "******");
		}
		String matInvTraceabilityBar = matInvTraceabilityService.queryMatInvTraceabilityBar_code(mapVo);
		return matInvTraceabilityBar;
	}
	
}
