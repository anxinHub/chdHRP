<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>

<script type="text/javascript">
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)

	$(function(){
		var list = ${list }.Rows;
		if(list.length == 0){
			$("#paraSet").append("<h3 style=\"margin-left: 10px;margin-top: 3px\">当前报表无参数</h3>")
		}
		$.each(list,function(){
			var data = this;
			if(!data.para_value){
				data.para_value = "";
			}
    		$("#paraSet").append("<tr><td class=\"label\">"+data.para_name + "</td><td class=\"ipt\"><input style=\"margin-left: 20px;margin-top: 10px;width:180px;height:18px\" type=\"text\" id=\""+data.para_id +"\" value=\""+data.para_value+"\"></td></tr>");
    	});
	});
	
	var save = function (){
		var param = [];
		var list = ${list};
		$.each(list.Rows,function(){
			var data = this;
			data.para_value = $("#" + data.para_id).val();
			data.rep_code = "${rep_code}";
			param.push(data);
    	});
		if(list.length == 0){
			frameElement.dialog.close();
		}
		$.ajax({
            type: 'POST',
            url : 'make/insertRepRepDSPara.do?isCheck=false',
            dataType:'json',
            data:{
            	mapVo : JSON.stringify(param),
            },
            success:function(result){
           		if (result.error) {
					$.ligerDialog.error(result.error);
				}else if (result.msg) {
           			$.ligerDialog.success(result.msg,result.msg,function(a){
	            		frameElement.dialog.close();
           			});
				}
            },
            error:function(textStatus, errorThrown){
           		 $.ligerDialog.error('操作失败.');
            }
       }); 
	};
</script>
</head>
<body>
	<div style="margin-left: 20px;margin-top: 30px">
		<table id="paraSet" ></table>
	</div>
</body>
</html>