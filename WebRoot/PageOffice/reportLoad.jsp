<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="<%=path %>/lib/jquery/jquery-1.9.0.min.js"></script>
	<script src="<%=path%>/lib/json2.js"></script>
	<script type="text/javascript" src="<%=path %>/pageoffice.js" id="po_js_main"></script>
	
  </head>
  <script type="text/javascript">
  
  	function init(){
  	 	
		var para=POBrowser.getArgument();
		
		window.location.href = "printReportPre.do?isCheck=false&"+para;
  		
  	}
  </script>
  
  <body onload="init();">
   		<img src='<%=path %>/lib/ligerUI/skins/Aqua/images/common/bigloading.gif' />
    	<div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: visible;position: absolute;  left: 70px; top: 60px; margin-top: -32px">
        	<span style="color: black; font-size: 14px; text-align: center;">正在处理数据，请耐心等待...</span><br />
			<!--div style=" border:solid 1px green;">
	        	<div id="ProgressBar" style="background-color: Green; height: 16px; width: 0%; border-width: 1px;border-style: Solid;"></div>
			</div-->
	    </div>
  </body>
</html>
