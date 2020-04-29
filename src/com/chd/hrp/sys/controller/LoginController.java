package com.chd.hrp.sys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.sys.service.LoginService;
import com.chd.hrp.sys.service.ModStartService;
import com.chd.hrp.sys.service.base.SysTableStyleService;
import com.chd.base.startup.LoadChdInfo;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;

@Controller
public class LoginController extends BaseController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Resource(name = "loginService")
	private LoginService loginService = null;
	
	@Resource(name = "sysTableStyleService")
	private SysTableStyleService sysTableStyleService = null;
	@Resource(name = "modStartService")
	private final ModStartService modStartService = null; 

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> main(@RequestParam Map<String, Object> mapVo) {
		if (!LoadChdInfo.$chd().equals("0$")) {
			return JSONObject.parseObject("{\"loginMsg\":\"" + LoadChdInfo.$chd() + "\"}");
		}

		try {
			
			String resultJson = loginService.login(mapVo);
			return JSONObject.parseObject(resultJson);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			String resultJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
			return JSONObject.parseObject(resultJson);
		}
		
		
	}
	
	// 工资条登录
	@RequestMapping(value = "/singleLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> singleLogin(@RequestParam Map<String, Object> mapVo) {
		if (!LoadChdInfo.$chd().equals("0$")) {
			return JSONObject.parseObject("{\"loginMsg\":\"" + LoadChdInfo.$chd() + "\"}");
		}

		try {
			
			String resultJson = loginService.singleLogin(mapVo);
			return JSONObject.parseObject(resultJson);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			String resultJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
			return JSONObject.parseObject(resultJson);
		}
	}

	// 加载系统模块菜单
	@RequestMapping(value = "/loadSystemModTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadSystemModTree(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		String resultJson = "";
		
		try {
			
			//防止url注入访问
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("mod_code",SessionManager.getModCode());
			mapVo.put("copy_code",SessionManager.getCopyCode());
			
			resultJson = loginService.loadSystemModTree(mapVo);
			
			logger.debug(resultJson);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			resultJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
			
		}
		
		return JSONObject.parseObject(resultJson);
	}

	// 用户解锁
	@RequestMapping(value = "/userUnLock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userUnLock(@RequestParam Map<String, Object> mapVo, Model mode) {
		String resultJson = "";
		try {
			
			resultJson = loginService.userUnLock(mapVo);
			
			logger.debug(resultJson);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			resultJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
			
		}
		return JSONObject.parseObject(resultJson);
	}


	// portalPage页面跳转
	@RequestMapping(value = "/portalPage", method = RequestMethod.GET)
	public String portalPage(Model mode) throws Exception {
		return "../../portal";
	}
	
	//版本信息
	@RequestMapping(value = "/about", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> about(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		String time=LoadChdInfo.getCt("time");
		String name=LoadChdInfo.getCt("name");
		String vertype=LoadChdInfo.getCt("vertype");
		
		StringBuffer resultJson = new StringBuffer();
		resultJson.append("{\"tip\":\""+DateUtil.isDiffFromToday(time,9)+"\"");
		resultJson.append(",\"time\":\""+time+"\"");
		String timeStr="";
		if(!time.equals("2099-12-31")){
			timeStr=" 有效期："+time;
		}
		String vertypeStr="正式版";
		if(vertype.equals("true")){
			vertypeStr="开发版";
		}
		
		resultJson.append(",\"version\":\""+name+" "+vertypeStr+timeStr+"\"");
		String fwtime=LoadChdInfo.getCt("fwtime");
		if(fwtime!=null && !fwtime.equals("")){
			resultJson.append(",\"fwtime\":\"免费售后服务有效期："+fwtime+"\"");
			if(DateUtil.isDiffFromToday(fwtime,0)){
				String fwtimeStr="免费售后服务已过期";
				resultJson.append(",\"fwtimeStr\":\""+fwtimeStr+"\"");
			}
		}
		
		resultJson.append("}");
		
		return JSONObject.parseObject(resultJson.toString());
	}
	
	//查询用户列表打印格式
	@RequestMapping(value = "/queryGridByUserID", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGridByUserID(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("user_id", SessionManager.getUserId());
		try{
			String resultJson=sysTableStyleService.queryGridByUserID(mapVo);
			return JSONObject.parseObject(resultJson);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{}");
		}
		
	}
	
	//判断系统启用
	@RequestMapping(value = "/querySysModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysModStart(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		try{
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("mod_code",SessionManager.getModCode());
			mapVo.put("copy_code",SessionManager.getCopyCode());
			//模块系统启用
			String resJson=modStartService.querySysModStart(mapVo);
			return JSONObject.parseObject("{"+resJson+"}");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{}");
		}
		
	}
	
	// 单点登录
	@RequestMapping(value = "/dhcHrpLogin", method = RequestMethod.GET)
	public String dhcHrpLogin(Model mode,@RequestParam Map<String, Object> mapVo,HttpServletRequest request,HttpServletResponse response) {
		
		if (!LoadChdInfo.$chd().equals("0$")) {
			printOut(request,response,LoadChdInfo.$chd());
			return "redirect:main.html";
		}

		try {
			
			Map<String, Object> reMap = loginService.dhcHrpLogin(mapVo);
			if(reMap.get("loginMsg")!=null && !"".equals(reMap.get("loginMsg").toString())){
				printOut(request,response,reMap.get("loginMsg").toString());
			}
			
		}catch (Exception e) {
			printOut(request,response,"请求被拒绝");
		}
		return "redirect:main.html";
	}
	
	/*处理页面跳转异常*/
	private void printOut(HttpServletRequest request,HttpServletResponse response,String ex){
		
		PrintWriter out = null;
        try {
        	out = response.getWriter();
        	response.setCharacterEncoding("UTF-8"); //设置编码格式
			response.setContentType("text/html");   //设置数据格式
			//out.print(getMsg("会话超时，请重新登录."));
			out.println("<!DOCTYPE html>"); 
            out.println("<HTML>"); 
            out.println("<HEAD>");
            out.println("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/lib/font-awesome/css/font-awesome.min.css\"/>");
            out.println("<style type=\"text/css\">");
            out.println("</style>");
            out.println("</HEAD>");
            out.println("<BODY>"); 
            out.print("<div style=\"text-align: center;\">"); 
            out.println("<i class=\"fa fa-frown-o fa-5x\" ></i>");//user-times
            out.println("<p style=\"font-size: 20px; font-weight: 300; color: #999;\">"+ex+"</p>"); 
            out.print("</div>"); 
            out.println("</BODY>"); 
            out.println("</HTML>");
            out.flush();
            out.close();
        	        	
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        	//e.printStackTrace();
        } finally {
            if (out != null)out.close();
        }
	}
	
}
