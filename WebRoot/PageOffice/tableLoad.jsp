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
  	 	
		var para=JSON.parse(POBrowser.getArgument());
		
		var paraStr="";
  	    for(var key in para){
  	    	paraStr=paraStr+"&"+key+"="+para[key];
  	    }
		
		//预览打印
		window.location.href = "printTablePre.do?isCheck=false"+paraStr;
		
		/* $.ajax({
			type: "post",
			url: "printTableData.do?isCheck=false",
			data: JSON.parse(para),
			dataType: "json",
			async: true,
			success: function (responseData) {
				if(responseData.error){
					alert(responseData.error);
					return;
				}
				
				if(!responseData.temp_file_name){
					alert("数据处理异常！");
					return;
				}
				
				temp_file_name=responseData.temp_file_name
				var resPara="&temp_file_name="+responseData.temp_file_name;
				resPara=resPara+"&group_id="+responseData.group_id+"&hos_id="+responseData.hos_id;
				resPara=resPara+"&hos_copy="+responseData.copy_code;
				
				//预览打印
				window.location.href = "printTablePre.do?isCheck=false&"+resPara;
				
			},
			error:function(XMLHttpRequest, textStatus){
				alert("操作失败！");
			}
		}); */
  		
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
