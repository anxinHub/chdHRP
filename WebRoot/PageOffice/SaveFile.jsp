<%@page import="java.io.FileNotFoundException"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%
FileSaver fs=new FileSaver(request,response);
String docPath=request.getParameter("docPath");
fs.saveToFile(request.getSession().getServletContext().getRealPath(docPath)+"/"+fs.getFileName());
fs.setCustomSaveResult("ok");
fs.close();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body>
		
	</body>
</html>