<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>

<style type="text/css">
     p{
         margin: 0;
         padding: 0;
         padding-left: 20px;
         line-height: 22px;
         font-size: 14px;
     }
    
</style> 

<script type="text/javascript">
var grid;
var gridManager = null;
var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

	$(function() {
		loadNote();
	});
	

	
	function loadNote(){
		var rows=0;
		if(parent.checkJson.r1.length>0){
			rows++;
			$("#note").append("<p>"+numToC(rows)+"、生成的数据必须要满足以下条件：</p>");
			$.each(parent.checkJson.r1,function(i,o){
				$("#note").append("<p>"+(i+1)+"、"+o.meta_name+"，"+o.note+"</p>");
			});
			$("#note").append("<br/>");
		}
		
		rows++;
		if(parent.checkJson.r2.length>0){
			$("#note").append("<p>"+numToC(rows)+"、以下数据没有科目对应关系：</p>");
			var count=0;
			$.each(parent.checkJson.r2,function(i,o){
				if(o.flag==1){
					//字典表名称
					$("#note").append("<p><i class=\"fa fa-file-text\" ></i>&nbsp;&nbsp;"+o.note+"</p>");
					count=0;
				}else{
					//字典数据
					count++;
					$("#note").append("<p>"+(count)+"、"+o.note+"</p>");
				}
				
			});
		}else{
			$("#note").append("<p>"+numToC(rows)+"、科目对应关系正常。</p>");
		}
			
	}
	
   
	//关闭
	function this_close(){
		parent.layer.close(index); 
	}
	

</script>
</head>

<body style="padding: 0px;"  >
 	<div id="note">
 		
 	</div> 
	
</body>
</html>
