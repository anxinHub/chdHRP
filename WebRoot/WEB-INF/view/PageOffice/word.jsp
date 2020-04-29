<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="UTF-8"%>
<%
	String file_type =(String)request.getAttribute("file_type");
	String out1;
	if("pdf".equals(file_type)){
		PDFCtrl poCtrl=(PDFCtrl)request.getAttribute("poCtrl");
		out1 = poCtrl.getHtmlCode("PDFCtrl1");
	}else{
		PageOfficeCtrl poCtrl=(PageOfficeCtrl)request.getAttribute("poCtrl");
		out1 = poCtrl.getHtmlCode("PageOfficeCtrl1");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title></title>
</head>
<body>
    <div style="width:100%; height:100%;">
    	
       <%=out1 %>
    </div>
</body>
</html>