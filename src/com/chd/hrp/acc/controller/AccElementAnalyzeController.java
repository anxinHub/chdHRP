package com.chd.hrp.acc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.chd.hrp.acc.service.AccElementAnalyzeService;
import org.apache.log4j.Logger;
import org.logicalcobwebs.concurrent.FJTask.Par;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccBank;
import com.chd.hrp.acc.service.AccBadDebtsService;

@Controller
@RequestMapping(value="/hrp/acc/accanalyze/elementAnalyze")
public class AccElementAnalyzeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccElementAnalyzeController.class);
	
	@Resource(name = "accElementAnalyzeService")
	private final AccElementAnalyzeService accElementAnalyzeService = null;
	
	
	//因素分析主页面accElementAnalyzeController
	@RequestMapping(value = "/elementMainPage", method = RequestMethod.GET)
	public String mainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accanalyze/elementAnalyze";
	}
	
	
	//添加-页面
	@RequestMapping(value = "/elementAddPage", method = RequestMethod.GET)
	public String addPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accanalyze/addUpdataElementPage";
	}
	//公式-页面
	@RequestMapping(value = "/elementCellsetPage", method = RequestMethod.GET)
	public String cellsetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/accanalyze/cellSet";
	}
	//修改-页面
	@RequestMapping(value = "/elementUpdataPage", method = RequestMethod.GET)
	public String updataPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("GROUP_ID", SessionManager.getGroupId());
		mapVo.put("HOS_ID", SessionManager.getHosId());
		mapVo.put("COPY_CODE", SessionManager.getCopyCode());
		
		List<Map<String, Object>> map = accElementAnalyzeService.queryElementForUpdata(mapVo);
		mode.addAttribute("COPY_CODE", map.get(0).get("COPY_CODE"));
		mode.addAttribute("FAC_CODE", map.get(0).get("FAC_CODE"));
		mode.addAttribute("FAC_NAME", map.get(0).get("FAC_NAME"));
		mode.addAttribute("ZB_UNIT", map.get(0).get("ZB_UNIT"));
		mode.addAttribute("FMA_CN", map.get(0).get("FMA_CN"));
		mode.addAttribute("NOTE", map.get(0).get("NOTE"));
		mode.addAttribute("IS_STOP", map.get(0).get("IS_STOP"));
		
		return "hrp/acc/accanalyze/updataElementPage";
	}
	
	
	//facCode，facName模糊查询
	@RequestMapping(value = "/queryElementAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryElementAnalyze(@RequestParam Map<String, Object> mapVo,Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("into query");
		//FAC_CODE FAC_NAME
		mapVo.put("GROUP_ID", SessionManager.getGroupId());
		mapVo.put("HOS_ID", SessionManager.getHosId());
		mapVo.put("COPY_CODE", SessionManager.getCopyCode());
		return JSON.parseObject(accElementAnalyzeService.queryElements(mapVo));
	}
	
	//facCode精确查询
	@RequestMapping(value = "/queryElementForUpdata", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryElementForUpdata(@RequestParam Map<String, Object> mapVo,Model mode,HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("into query");
		mapVo.put("GROUP_ID", SessionManager.getGroupId());
		mapVo.put("HOS_ID", SessionManager.getHosId());
		mapVo.put("COPY_CODE", SessionManager.getCopyCode());
		return JSON.parseObject(ChdJson.toJson(accElementAnalyzeService.queryElementForUpdata(mapVo)));
	}

	//添加
	@RequestMapping(value = "/addElementAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public String addElementAnalyze(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return accElementAnalyzeService.addElements(mapVo);
	}
	

	//删除
	@RequestMapping(value = "/deleteElementAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public String deleteElementAnalyze(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("GROUP_ID", SessionManager.getGroupId());
		mapVo.put("HOS_ID", SessionManager.getHosId());
		mapVo.put("COPY_CODE", SessionManager.getCopyCode());
		System.out.println("into query");
		return accElementAnalyzeService.deleteElements(mapVo);
	}

	//修改
	@RequestMapping(value = "/updataElementAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public String updataElementAnalyze(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("GROUP_ID", SessionManager.getGroupId());
		mapVo.put("HOS_ID", SessionManager.getHosId());
		mapVo.put("COPY_CODE", SessionManager.getCopyCode());
		return accElementAnalyzeService.updataElements(mapVo);
	}
	
}
