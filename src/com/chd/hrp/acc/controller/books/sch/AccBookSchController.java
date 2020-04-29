package com.chd.hrp.acc.controller.books.sch;

import java.util.HashMap;
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
import com.chd.base.MyConfig;
import com.chd.hrp.acc.service.books.sch.AccBookSchService;
/**
 * 账簿方案
 * @author 
 *
 */
@Controller 
public class AccBookSchController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccBookSchController.class);
	
	@Resource(name = "accBookSchService")
	private final AccBookSchService accBookSchService = null; 
	
	/**
	 * 方案主页面跳转
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/accBookSchMainPage", method = RequestMethod.GET)
	public String accBookSchMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("yearMonth", MyConfig.getAccYearMonth().getCurYearMonthAcc());
		mode.addAttribute("is_check", mapVo.get("is_check") == null ? "0" : mapVo.get("is_check").toString());
		mode.addAttribute("page", mapVo.get("page"));
		return "hrp/acc/accbooksch/accBookSchMain";
	}
	
	/**
	 * 方案添加页面跳转
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/accBookSchAddPage", method = RequestMethod.GET)
	public String accBookSchAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("is_check", mapVo.get("is_check") == null ? "0" : mapVo.get("is_check").toString());
		mode.addAttribute("page", mapVo.get("page"));
		return "hrp/acc/accbooksch/accBookSchAdd";
	}
	
	/**
	 * 方案树形列表查询
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/queryAccBookSchListForTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBookSchListForTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String str = accBookSchService.queryAccBookSchListForTree(mapVo);
		
		return JSONObject.parseObject(str) ;
	}
	
	/**
	 * 方案保存
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/saveAccBookSch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBookSch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		Map<String, Object> retMap = null;
		try {
			
			retMap = accBookSchService.saveAccBookSch(mapVo);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 方案删除
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/deleteAccBookSch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBookSch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		Map<String, Object> retMap = null;
		try {
			
			retMap = accBookSchService.deleteAccBookSch(mapVo);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 方案条件添加
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/saveAccBookSchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBookSchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		Map<String, Object> retMap = null;
		try {
			
			retMap = accBookSchService.saveAccBookSchDetail(mapVo);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 方案加载
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/queryAccBookSchById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBookSchById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		Map<String, Object> retMap = null;
		try {
			
			retMap = accBookSchService.queryAccBookSchById(mapVo);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 方案条件加载
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/queryAccBookSchDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBookSchDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		Map<String, Object> retMap = null;
		try {
			
			retMap = accBookSchService.queryAccBookSchDetailById(mapVo);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * 科目树形列表查询 
	 * */
	@RequestMapping(value = "hrp/acc/accbooksch/queryAccSubjListForTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjListForTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String str = accBookSchService.queryAccSubjListForTree(mapVo);
		
		return JSONObject.parseObject(str) ;
	}
	
	@RequestMapping(value = "hrp/acc/accbooksch/queryAccBookSchCheckNameBySchId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBookSchCheckNameBySchId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String retMap=null;
		
		try {
			
			retMap = accBookSchService.queryAccBookSchCheckNameBySchId(mapVo);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
		}
		
		return JSONObject.parseObject(retMap);
	}
}
