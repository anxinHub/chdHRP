/**
 * 2015-1-18 ExcpetionInterceptor.java author:pengjin
 */
package com.chd.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.chd.base.util.DateUtil;
import com.chd.base.util.DhcHrpLogin;

public class ExcpetionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(ExcpetionInterceptor.class);
	
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("WatchExecuteTime");

	// 在Controller被调用前先执行
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// logger.debug("interceptor：preHandle");

		long beginTime = System.currentTimeMillis();            //开始时间
        startTimeThreadLocal.set(beginTime);
		
		String xRequestedWith = request.getHeader("X-Requested-With");

		if (!(handler instanceof HandlerMethod)) {

			logger.debug("没有找到对应的请求：" + request.getRequestURI());
			if (xRequestedWith != null
					&& xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
				// ajax请求
				response.setHeader("sessionstatus", "REQUEST_MAPPING");
				response.setStatus(415);// 请求被拒绝
			} else {
				response.setCharacterEncoding("UTF-8"); //设置编码格式
				response.setContentType("text/html");   //设置数据格式
				PrintWriter out = response.getWriter();
				out.print(getMsg("没有找到对应的请求."));
				out.close();
			}
			return false;
		}

		String servletPath=request.getServletPath();
		//String requestUrl = request.getRequestURI().toString();
		logger.debug("servletPath：" + servletPath);

		//登录、解锁不拦截
		if (servletPath.startsWith("/login.do") || servletPath.startsWith("/singleLogin.do") || servletPath.startsWith("/userUnLock.do") || servletPath.startsWith("/PageOffice/")) {
			return true;
		}
		
		//单点登录
		if (servletPath.startsWith("/dhcHrpLogin.do") ) {
			
			if(request.getParameter("tokey")==null || "".equals(request.getParameter("tokey"))){
				printOut(request, response, "单点登录无效");
				return false;
			}
			String[] tokey=request.getParameter("tokey").split(",");
			if(tokey.length<2){
				printOut(request, response, "单点登录无效");
				return false;
			}
			
			if(!tokey[1].equals(DhcHrpLogin.shaEncode(tokey[0]))){
				printOut(request, response, "单点登录无效");
				return false;
			}
			
			return true;
		}
		
		//app接口
		if (servletPath.startsWith("/hrp/app/")) {
			return true;
		}
		
		//任务调度
		if (servletPath.startsWith("/hrp/hip/dataType/syncData.do")) {
			return true;
		}
		
		Object obj = request.getSession().getAttribute("user_id");
		if (obj == null || !request.isRequestedSessionIdValid()) {
			logger.debug("会话超时，请重新登录.");
			if (xRequestedWith != null
					&& xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
				// ajax请求
				response.setHeader("sessionstatus", "MSG_TIME_OUT");
				response.setStatus(415);// 请求被拒绝
			} else {
				response.setCharacterEncoding("UTF-8"); //设置编码格式
				response.setContentType("text/html");   //设置数据格式
				PrintWriter out = response.getWriter();
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
	            out.println("<p style=\"font-size: 20px; font-weight: 300; color: #999;\">会话超时，请重新登录</p>"); 
	            out.print("</div>"); 
	            out.println("</BODY>"); 
	            out.println("</HTML>");
	            out.flush();
	            out.close();
			}
			return false;
		}
		String requestMapping = servletPath.substring(
				servletPath.lastIndexOf("/") + 1, servletPath.length() - 3);
		// 验证功能权限
		// 医院用户、集团用户、isCheck=true验证功能权限
		if ((SessionManager.getTypeCode().equals("0") || SessionManager.getTypeCode().equals("1"))
				&& (request.getParameter("isCheck") == null || request.getParameter("isCheck").equals("true"))) {
			
			logger.debug("requestMapping：" + requestMapping);
			if (!SessionManager.existsUserPerm(requestMapping)) {
				logger.debug(SessionManager.getUserId() + "，" + requestMapping
						+ "，没有该操作权限！");
				if (xRequestedWith != null
						&& xRequestedWith.equalsIgnoreCase("XMLHttpRequest")) {
					// ajax请求
					response.setHeader("sessionstatus", "NOT_PERMID");
					response.setStatus(415);// 请求被拒绝
				} else {
					PrintWriter out = response.getWriter();
					response.setCharacterEncoding("UTF-8"); //设置编码格式
					response.setContentType("text/html");   //设置数据格式
					//out.print(getMsg("没有该操作权限！"));
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
		            out.println("<p style=\"font-size: 20px; font-weight: 300; color: #999;\">没有该功能权限</p>"); 
		            out.print("</div>"); 
		            out.println("</BODY>"); 
		            out.println("</HTML>");
		            out.flush();
		            out.close();
				}
				return false;
			}

			// 减少与数据库的交互，改用session判断
			// Map<String,String> paramMap = new HashMap<String,String>();
			// paramMap.put("user_id", SessionManager.getUserId());
			// paramMap.put("perm_id", requestMapping);
			// paramMap.put("mod_code", SessionManager.getModCode());
			// paramMap.put("comp_code", SessionManager.getCompCode());
			// paramMap.put("copy_code", SessionManager.getCopyCode());
			// if(!sysPermMapper.checkUsersPermByUser(paramMap)){
			// logger.debug(SessionManager.getUserId()+"，"+requestMapping+"，没有该操作权限！");
			// if(xRequestedWith!=null &&
			// xRequestedWith.equalsIgnoreCase("XMLHttpRequest")){
			// //ajax请求
			// response.setHeader("sessionstatus", "NOT_PERMID");
			// response.setStatus(415);//请求被拒绝
			// }else{
			// PrintWriter out = response.getWriter();
			// out.print(getMsg("没有该操作权限."));
			// out.close();
			// }
			// return false;
			// }
		}
		
		if(requestMapping.startsWith("save") 
				|| requestMapping.startsWith("add") 
				|| requestMapping.startsWith("insert")  
				|| requestMapping.startsWith("init")
				|| requestMapping.startsWith("import")
				
						) {
		//防止重复提交校验
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            ChdToken annotation = method.getAnnotation(ChdToken.class);
            if (annotation != null) {
            	boolean isRpeat=repeatDataValidator(request,method.getName());
            	
            	if(isRpeat)//如果重复相同数据
            	{
            		
            		response.setHeader("sessionstatus", "TOKEN_MAPPING");
					response.setStatus(415);// 请求被拒绝
            		response.setCharacterEncoding("UTF-8"); // 设置编码格式
    				response.setContentType("text/html"); // 设置数据格式
    				PrintWriter out = response.getWriter();
    				out.print(getMsg("重复提交数据."));
    				out.close();
    				return false;
            	}
            }
        } else {
            return super.preHandle(request, response, handler);
        }
		}
		return true;
	}

	// 在Controller调用后执行
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// logger.debug("interceptor：postHandle");
	}

	// 在Controller调用全部完成后执行
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// logger.debug("interceptor：afterCompletion");
		if (ex != null) {
			logger.error(handler);
			logger.error(ex.getMessage(), ex);
		}

		long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTimeThreadLocal.get();
        logger.debug(String.format("%s execute %d ms." , request.getRequestURI() , executeTime));
        System.out.println(String.format("%s execute %d ms." , request.getRequestURI() , executeTime));
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// logger.debug("interceptor：afterConcurrentHandlingStarted");
	}

	private String getMsg(String msg) {
		// OutputStreamWriter out = new OutputStreamWriter(
		// response.getOutputStream(), "UTF-8");
		StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/javascript\" >");
		builder.append("alert(\"" + msg + "\");");
		// builder.append("window.topmenu.openLogin();");
		builder.append("</script>");
		return builder.toString();
	}
	/**
	 * 验证同一个url数据是否相同提交  ,相同返回true
	 * @param httpServletRequest
	 * @return
	 */
	public boolean repeatDataValidator(HttpServletRequest httpServletRequest,String methodName)
	{
		int timeCount=5;
		if(methodName.equals("saveSuperVouch")) {
			timeCount=30;
		}
		String params=JSON.toJSONString(httpServletRequest.getParameterMap());
		String url=httpServletRequest.getRequestURI();
		Map<String,String> map=new HashMap<String,String>();
		map.put(url, params);
		String nowUrlParams=map.toString();//
		
		Object preUrlParams=httpServletRequest.getSession().getAttribute("repeatData");
		Object preTimeStamp=httpServletRequest.getSession().getAttribute("timeStamp");
		
		if(preUrlParams==null)//如果上一个数据为null,表示还没有访问页面
		{
			httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
			httpServletRequest.getSession().setAttribute("timeStamp", DateUtil.getSysTime());
			return false;
		}
		else//否则，已经访问过页面
		{
			//如果上次url+数据和本次url+数据相同，则表示重复添加数据 session 失效时间为5秒
			if(preUrlParams.toString().equals(nowUrlParams) && DateUtil.getTimeDifference(preTimeStamp.toString(), DateUtil.getSysTime(), "s")<5)
			{
				logger.debug("重复提交数据："+url);
				return true;
			}
			else//如果上次 url+数据 和本次url加数据不同，则不是重复提交
			{
				httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
				httpServletRequest.getSession().setAttribute("timeStamp", DateUtil.getSysTime());
				return false;
			}
			
		}
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
