package com.chd.hrp.acc.controller.report;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.report.SuperReportDataSetService;

@Controller
@RequestMapping(value = "/hrp/acc/superReport")
public class SuperReportDataSetManagerController {

	@Resource(name = "superReportDataSetService")
	private SuperReportDataSetService superReportDataSetService;

	// 报表数据集页面，主页面
	@RequestMapping(value = "/ds/mainPage", method = RequestMethod.GET)
	public String elePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/superReport/ds/main";
	}
	
	// 报表数据集页面，主页面
	@RequestMapping(value = "/ds/paraSetPage", method = RequestMethod.GET)
	public String paraSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/superReport/ds/paraSet";
	}

	// 数据集查询
	@RequestMapping(value = "/ds/querySuperReportDsManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDsManager(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", SessionManager.getModCode());

		String reJson = null;
		try {
			reJson = superReportDataSetService.querySuperReportDsManager(mapVo);
		} catch (Exception e) {
			e.printStackTrace();
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}

	// 数据集添加
	@RequestMapping(value = "/ds/saveSuperReportDs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperReportDs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String s=mapVo.get("sqlcontent").toString();
		s=s.replace("\n", "\n ");
		mapVo.put("sqlcontent", s);
		String reJson = null;
		try {
			reJson = superReportDataSetService.saveSuperReportDs(mapVo);
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}

	// 数据集删除
	@RequestMapping(value = "/ds/deleteSuperReportDs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSuperReportDs(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = null;
		try {
			reJson = superReportDataSetService.deleteSuperReportDs(mapVo);
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}

	/**
	 * 查询数据集下的元素
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/ds/querySuperReportDSColoums", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDSColoums(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String reJson = null;
		try {
			reJson = superReportDataSetService.querySuperReportDSColoums(mapVo);
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}

	/**
	 * 查询数据集下的元素
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/ds/querySuperReportDSParas", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDSParas(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = null;
		try {
			reJson = superReportDataSetService.querySuperReportDSParas(mapVo);
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	//查询对应sql
	@RequestMapping(value = "/ds/querySuperReportDsSql", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySuperReportSourceAndViews(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String reJson="";
		try {
			reJson=superReportDataSetService.querySuperReportDsSql(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return reJson;
		
	}
}
