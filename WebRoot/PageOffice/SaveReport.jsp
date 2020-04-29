<%@page import="java.io.FileNotFoundException"%>
<%@ page language="java"
	import="java.util.*, java.text.*,com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.excelreader.*"
	pageEncoding="utf-8"%>
<%
FileSaver fs=new FileSaver(request,response);
String docPath=request.getParameter("docPath");
String fileName=request.getParameter("fileName");
fs.saveToFile(request.getSession().getServletContext().getRealPath(docPath)+"/"+fileName);
fs.setCustomSaveResult("ok");//返回值
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