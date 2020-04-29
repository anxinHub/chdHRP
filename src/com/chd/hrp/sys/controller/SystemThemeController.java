package com.chd.hrp.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.startup.LoadMenuFile;
import com.chd.hrp.sys.service.LoginService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

@Controller
public class SystemThemeController {

	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	// 工作环境页面跳转
	@RequestMapping(value = "/systemThemePage", method = RequestMethod.GET)
	public String systemThemePage(Model mode) throws Exception {
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("hos_name", SessionManager.getHosSimple());
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("group_name", SessionManager.getGroupName());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("copy_name", SessionManager.getCopyName());
		mode.addAttribute("mod_code", SessionManager.getModCode());
		mode.addAttribute("mod_name", SessionManager.getModName());
		mode.addAttribute("type_code", SessionManager.getTypeCode());
		mode.addAttribute("myAccYearMonth", MyConfig.getAccYearMonth());
		return "../../systemTheme";
	}

	// 退出系统
	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logOut(Model mode) throws Exception {
		String resultJson = null;
		if (SessionManager.getSession() != null)
			SessionManager.getSession().invalidate();
		else
			resultJson = "{\"state\":\"true\"}";
		return JSONObject.parseObject(resultJson);
	}

	// 工作环境切换
	@RequestMapping(value = "/systemThemeJump", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> systemThemeJump(@RequestParam Map<String, String> mapVo) {
		if (LoadMenuFile.getModMap().get(mapVo.get("mod_code")) == null) {
			return JSONObject.parseObject("{\"state\":\"false\"}");
		}
		SessionManager.getSession().setAttribute("hos_id", (mapVo.get("hos_id")==null || mapVo.get("hos_id")=="")?0:mapVo.get("hos_id"));
		SessionManager.getSession().setAttribute("acct_year", mapVo.get("acct_year"));
		SessionManager.getSession().setAttribute("copy_code", mapVo.get("copy_code"));
		SessionManager.getSession().setAttribute("copy_name", mapVo.get("copy_name"));
		// 切换系统
		SessionManager.getSession().setAttribute("mod_code", mapVo.get("mod_code"));
		SessionManager.getSession().setAttribute("mod_name", LoadMenuFile.getModMap().get(mapVo.get("mod_code")));
		
		//添加根据更改账套改变session中日期
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("group_id", SessionManager.getGroupId());
//		map.put("hos_id", SessionManager.getHosId());
//		map.put("copy_code", mapVo.get("copy_code"));
//		Map<String, Object> reMap = sysBaseService.queryAccYearMonthMap(map);
//		SessionManager.getSession().setAttribute("acc_year_month_map",reMap);
//		SessionManager.getSession().setAttribute("acc_year_month", SessionManager.getSysYearMonth("acc_flag"));
//		SessionManager.getSession().setAttribute("cash_year_month", SessionManager.getSysYearMonth("cash_flag"));
//		SessionManager.getSession().setAttribute("wage_year_month", SessionManager.getSysYearMonth("wage_flag"));
//		SessionManager.getSession().setAttribute("ass_year_month", SessionManager.getSysYearMonth("ass_flag"));
//		SessionManager.getSession().setAttribute("mat_year_month", SessionManager.getSysYearMonth("mat_flag"));
//		SessionManager.getSession().setAttribute("med_year_month", SessionManager.getSysYearMonth("med_flag"));
//		SessionManager.getSession().setAttribute("cost_year_month", SessionManager.getSysYearMonth("cost_flag"));
		
		// 保存用户最后登录的模块和账套
		Map<String, Object> userMod = new HashMap<String, Object>();
		
		userMod.put("mod_code", mapVo.get("mod_code"));
		userMod.put("user_id", SessionManager.getUserId());
		userMod.put("copy_code", mapVo.get("copy_code"));
		userService.updateUserMod(userMod);
		
		String user_name = SessionManager.getUserName();
		String group_name = SessionManager.getGroupName();
		String hospital = SessionManager.getHosName();
		String copy_name = SessionManager.getCopyName();
		String resultJson = "{\"state\":\"true\",\"user_name\":\""+user_name+"\",\"group_name\":\""+group_name+"\",\"hospital\":\""+hospital+"\",\"copy_name\":\""+copy_name+"\"}";
		return JSONObject.parseObject(resultJson);
	}
}
