package com.chd.hrp.acc.controller.books.check;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.books.check.AccBooksCheckService;
@Controller
public class AccBooksCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBooksCheckController.class);
	
	@Resource(name = "accBooksCheckService")
	private final AccBooksCheckService accBooksCheckService = null;
	
	/**
	* 科目选择页面跳转<BR>
	*/
	@RequestMapping(value = "/hrp/acc/books/subjTreePage", method = RequestMethod.GET)
	public String subjTreePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/acc/books/subjTree"; 
	}
	
	/**
	* 总账<BR>
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/books/check/checkzzPage", method = RequestMethod.GET)
	public String checkzzPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("template_code", mapVo.get("template_code"));
		mode.addAttribute("type", mapVo.get("type"));
		if(mapVo.get("type") != null && !"zcheck".equals(mapVo.get("type"))){
			//查询对应核算类ID
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("column_check", mapVo.get("type").toString().toUpperCase());
			
			Map<String, Object> checkType =accBooksCheckService.queryCheckTypeByCheck(mapVo);
			mode.addAttribute("check_type_id", checkType.get("CHECK_TYPE_ID"));
			mode.addAttribute("check_type_name", checkType.get("CHECK_TYPE_NAME"));
			mode.addAttribute("is_zcheck", 0);
		}else{
			mode.addAttribute("is_zcheck", 1);
		}
		
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		return "hrp/acc/books/check/checkzz";
	}
	/**
	* 总账<BR>
	* 查询数据
	*/
	@RequestMapping(value = "/hrp/acc/books/check/queryAccBooksCheckZZ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBooksCheckZZ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result=accBooksCheckService.collectAccBooksCheckZZ(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	* 明细账<BR>
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/books/check/checkmxzPage", method = RequestMethod.GET)
	public String checkmxzPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("template_code", mapVo.get("template_code"));
		mode.addAttribute("type", mapVo.get("type"));
		if(mapVo.get("type") != null && !"zcheck".equals(mapVo.get("type"))){
			//查询对应核算类ID
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("column_check", mapVo.get("type").toString().toUpperCase());
			
			Map<String, Object> checkType =accBooksCheckService.queryCheckTypeByCheck(mapVo);
			mode.addAttribute("check_type_id", checkType.get("CHECK_TYPE_ID"));
			mode.addAttribute("check_type_name", checkType.get("CHECK_TYPE_NAME"));
			mode.addAttribute("is_zcheck", 0);
		}else{
			mode.addAttribute("is_zcheck", 1);
		}
		
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		return "/hrp/acc/books/check/checkmxz";
	}
	/**
	* 明细账<BR>
	* 查询数据
	*/
	@RequestMapping(value = "/hrp/acc/books/check/queryAccBooksCheckMXZ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBooksCheckMXZ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accBooksCheckService.collectAccBooksCheckMXZ(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	* 余额表<BR>
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/books/check/checkyebPage", method = RequestMethod.GET)
	public String checkyebPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("template_code", mapVo.get("template_code"));
		mode.addAttribute("type", mapVo.get("type"));
		if(mapVo.get("type") != null && !"zcheck".equals(mapVo.get("type"))){
			//查询对应核算类ID
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("column_check", mapVo.get("type").toString().toUpperCase());
			
			Map<String, Object> checkType =accBooksCheckService.queryCheckTypeByCheck(mapVo);
			mode.addAttribute("check_type_id", checkType.get("CHECK_TYPE_ID"));
			mode.addAttribute("check_type_name", checkType.get("CHECK_TYPE_NAME"));
			mode.addAttribute("is_zcheck", 0);
		}else{
			mode.addAttribute("is_zcheck", 1);
		}
		
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		return "hrp/acc/books/check/checkyeb";
	}
	/**
	* 余额表<BR>
	* 查询数据
	*/
	@RequestMapping(value = "/hrp/acc/books/check/queryAccBooksCheckYEB", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBooksCheckYEB(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accBooksCheckService.collectAccBooksCheckYEB(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	/**
	* 余额表明细账<BR>
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/books/check/checkyebmxzPage", method = RequestMethod.GET)
	public String checkyebmxzPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/acc/books/check/checkyebmxz";
	}
	
	/**
	* 交叉表<BR>
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/books/check/checkjcbPage", method = RequestMethod.GET)
	public String checkjcbPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("template_code", mapVo.get("template_code"));
		mode.addAttribute("type", mapVo.get("type"));
		if(mapVo.get("type") != null && !"zcheck".equals(mapVo.get("type"))){
			//查询对应核算类ID
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("column_check", mapVo.get("type").toString().toUpperCase());
			
			Map<String, Object> checkType =accBooksCheckService.queryCheckTypeByCheck(mapVo);
			mode.addAttribute("check_type_id", checkType.get("CHECK_TYPE_ID"));
			mode.addAttribute("check_type_name", checkType.get("CHECK_TYPE_NAME"));
			mode.addAttribute("is_zcheck", 0);
		}else{
			mode.addAttribute("is_zcheck", 1);
		}
		
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		//mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		
		return "hrp/acc/books/check/checkjcb";
	}
	/**
	* 交叉表<BR>
	* 查询数据
	*/
	@RequestMapping(value = "/hrp/acc/books/check/queryAccBooksCheckJCB", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBooksCheckJCB(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accBooksCheckService.collectAccBooksCheckJCB(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	/**
	* 交叉表<BR>
	* 查询表头
	*/
	@RequestMapping(value = "/hrp/acc/books/check/querySubjHeadByJCB", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjHeadByJCB(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
    	try{
			return JSON.toJSONString(accBooksCheckService.querySubjHeadByJCB(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
}
