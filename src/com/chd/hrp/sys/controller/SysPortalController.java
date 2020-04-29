/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sys.controller;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.sys.service.portal.PortalService;
import com.chd.hrp.sys.service.portal.SysPortalTitleSetService;
import com.chd.hrp.sys.service.portal.SysPortalTitleUserService;

/**
 * 
 * @Description:  门户
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SysPortalController extends BaseController {

	private static Logger logger = Logger.getLogger(SysPortalController.class);
	
	
	@Resource(name = "portalService" )
	private final PortalService portalService = null ;
	
	@Resource(name = "sysPortalTitleSetService" )
	private final SysPortalTitleSetService sysPortalTitleSetService = null ;
	
	@Resource(name = "sysPortalTitleUserService" )
	private final SysPortalTitleUserService sysPortalTitleUserService = null ;

	//系统平台首页
	@RequestMapping(value = "/sysHomePage", method = RequestMethod.GET)
	public String sysHomePage(Model mode) throws Exception {
		return "../../sysHome";
	}
	
	//财务管理首页
	@RequestMapping(value = "/accHomePage", method = RequestMethod.GET)
	public String accHomePage(Model mode) throws Exception {
		return "../../accHome";
	}
	
	//资产管理首页
	@RequestMapping(value = "/assHomePage", method = RequestMethod.GET)
	public String assHomePage(Model mode) throws Exception {
		return "../../assHome";
	}
	
	//科室成本首页
	@RequestMapping(value = "/costHomePage", method = RequestMethod.GET)
	public String costHomePage(Model mode) throws Exception {
		return "../../costHome";
	}
	
	//其他模块系统首页
	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public String homePage(Model mode) throws Exception {
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_name", SessionManager.getUserName());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("copy_name", SessionManager.getCopyName());
		mode.addAttribute("acct_year", SessionManager.getAcctYear());
		mode.addAttribute("hospital", SessionManager.getHosName());
		mode.addAttribute("group_name", SessionManager.getGroupName());
		String userType="";
		if(SessionManager.getTypeCode().equals("0"))userType="医院用户：";
		if(SessionManager.getTypeCode().equals("1"))userType="集团用户：";
		//if(SessionManager.getTypeCode().equals("2"))userType="超级管理员：";
		//if(SessionManager.getTypeCode().equals("3"))userType="集团管理员：";
		//if(SessionManager.getTypeCode().equals("4"))userType="医院管理员：";
		mode.addAttribute("user_type", userType);
		return "../../home";
	}
	
	
	@RequestMapping(value = "/hrp/portal/queryMatPortalSetInfo", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMatPortalSetInfo(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("mod_code", SessionManager.getModCode()); 
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String  portalInfo =  portalService.querySysPortalInfo(mapVo);
		return portalInfo;
	}
	
	@RequestMapping(value = "/hrp/portal/queryMedPortalSetInfo", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMedPortalSetInfo(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("mod_code", SessionManager.getModCode()); 
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String  portalInfo =  portalService.querySysPortalInfo(mapVo);
		return portalInfo;
	}
	
	
	@RequestMapping(value = "/hrp/portal/querySysShowPortalData", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySysShowPortalData(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		 
			mapVo.put("group_id",SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("mod_code", SessionManager.getModCode());
			
			mapVo.put("user_id", SessionManager.getUserId());
		
		
		
		
		
		
		String  portalInfo =  portalService.querySysShowPortalData(mapVo);
		return portalInfo;
	}
	
	@RequestMapping(value = "/hrp/portal/querySysShowPortalInfo", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySysShowPortalInfo(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("mod_code", SessionManager.getModCode()); 
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String  portalInfo =  portalService.querySysShowPortalInfo(mapVo);
		return portalInfo;
	}
	
	@RequestMapping(value = "/hrp/portal/queryMatPortalTitle", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMatPortalTitle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("mod_code", SessionManager.getModCode()); 
		
		String  portalTitle =  portalService.querySysPortalTitle(mapVo);
		return portalTitle;
	}
	
	@RequestMapping(value = "/hrp/portal/queryMedPortalTitle", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryMedPortalTitle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("mod_code", SessionManager.getModCode()); 
		
		String  portalTitle =  portalService.querySysPortalTitle(mapVo);
		return portalTitle;
	}
	
	
	/**
	 * 用户 门户栏目设置
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/portal/saveSysPortalTitleUser", method = RequestMethod.POST)
	@ResponseBody
	public String saveSysPortalTitleUser(@RequestParam Map<String, Object> mapVo,Model mode){
		
		List<Map<String,Object>> detail = JsonListMapUtil.getListMap(mapVo.get("ParamVo").toString());
		
		Map<String,Object> deleteMap = new HashMap<String,Object>();
		
		deleteMap.put("group_id", SessionManager.getGroupId());
		deleteMap.put("hos_id", SessionManager.getHosId());
		deleteMap.put("copy_code", SessionManager.getCopyCode());
		deleteMap.put("mod_code", SessionManager.getModCode());
		deleteMap.put("user_id", SessionManager.getUserId());
		
		for(Map<String, Object> map : detail){
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			map.put("para_json", "");
		}
		String info  = null ;
		try {
			sysPortalTitleUserService.deleteSysPortalTitleUser(deleteMap);
			if(detail.size() > 0){
				
				info = sysPortalTitleUserService.addBatchSysPortalTitleUser(detail);
			}else{
				
				info = "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			}
			
			
			
		} catch (Exception e) {
			info = e.getMessage();
		}
		return info ;
	}
	
	/**
	 * 系统门户栏目设置
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/portal/saveSysPortalTitleSet", method = RequestMethod.POST)
	@ResponseBody
	public String saveSysPortalTitleSet(@RequestParam Map<String, Object> mapVo,Model mode){
		
		
		List<Map<String,Object>> detail = JsonListMapUtil.getListMap(mapVo.get("ParamVo").toString());
		
		Map<String,Object> deleteMap = new HashMap<String,Object>();
		
		deleteMap.put("group_id", SessionManager.getGroupId());
		deleteMap.put("hos_id", SessionManager.getHosId());
		deleteMap.put("copy_code", SessionManager.getCopyCode());
		deleteMap.put("mod_code", SessionManager.getModCode());
		
		for(Map<String, Object> map : detail){
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("para_json", "");
		}
		
		String info  = null ;
		
		try {
			
			sysPortalTitleSetService.deleteSysPortalTitleSet(deleteMap);
			
			if(detail.size() > 0){
				
				info = sysPortalTitleSetService.addBatchSysPortalTitleSet(detail);
			}else{
				
				info = "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			}
			
			
			
		} catch (Exception e) {
			info = e.getMessage();
		}
		return info ;
	}
	
	
	// 公告设置页面跳转
	@RequestMapping(value = "/hrp/portal/sysNoticePage", method = RequestMethod.GET)
	public String supTypeMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", SessionManager.getModCode());
		
		Map<String,Object> map = sysPortalTitleUserService.querySysPortalNoticeByCode(mapVo);
		
		if(map != null ){
			mode.addAttribute("group_id", map.get("group_id"));
			mode.addAttribute("hos_id", map.get("hos_id"));
			mode.addAttribute("mod_code", map.get("mod_code"));
			mode.addAttribute("content", map.get("content"));
			mode.addAttribute("user_id", map.get("user_id"));
			mode.addAttribute("user_name", map.get("user_name"));
			mode.addAttribute("create_date", map.get("create_date"));
			mode.addAttribute("state", map.get("state"));
		}
		
		
		return "hrp/mat/portal/sysNotice";

	}
	
	// 公告保存
	@RequestMapping(value = "/hrp/portal/saveSysNotice", method = RequestMethod.POST)
	@ResponseBody
	public String saveSysNotice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("mod_code", SessionManager.getModCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("create_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
		mapVo.put("state", "0");
		
		
		String notice  = null ;
		
		if(mapVo.get("content") == null || mapVo.get("content") == ""){
			
			return "{\"error\":\"内容不能为空.\",\"state\":\"false\"}";
		}
		
		try {
			
			notice = sysPortalTitleUserService.addOrUpdateSysNotice(mapVo);
			
		} catch (Exception e) {
			notice = e.getMessage();
		}
		return notice ;

	}
	
	// 发布、取消发布 更改状态
	@RequestMapping(value = "/hrp/portal/updateSysNoticeState", method = RequestMethod.POST)
	@ResponseBody
	public String updateSysNoticeState(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("mod_code", SessionManager.getModCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		if(mapVo.get("content") != ""){
			
			Map<String,Object> map = sysPortalTitleUserService.querySysPortalNoticeByCode(mapVo);
			
			if(map != null  && !String.valueOf(mapVo.get("content")).equals(map.get("content"))){
				
				return "{\"error\":\"数据未保存,操作失败.请先保存数据.\",\"state\":\"false\"}";
			}
			
		}
		
		int count = sysPortalTitleUserService.querySysNoticeExist(mapVo);
		
		String notice  = null ;
		
		if(count == 0){
			
			return "{\"error\":\"数据未保存,操作失败.请先保存数据.\",\"state\":\"false\"}";
		}else{
			try {
				
				notice = sysPortalTitleUserService.updateSysNoticeState(mapVo);
				
			} catch (Exception e) {
				notice = e.getMessage();
			}
		}
		
		
		return notice ;

	}
	
	
	@RequestMapping(value = "/hrp/portal/queryOrgChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryOrgChart(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String reJson=null;
		try{
			reJson=portalService.queryOrgChart(mapVo);
		}catch(Exception e){
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(reJson);
		
	}
}
