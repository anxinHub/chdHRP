<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
	var save = function() {
		formValidate = $.etValidate({
			items : [{el : $("#change_reason"), required : true}, ]
		});
		if(!formValidate.test()){return;};
		
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex); 
        var parentFrameName = parent.$.etDialog.getFrameName('change');
        var parentWindow = parent.window[parentFrameName];
        parentWindow.change_reason = $("#change_reason").val();
        parentWindow.save();
	}

	$(function(){
		$("#save").on("click", function () {
			save();
		})
	})
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label" style="width: 100px;">变更原因：</td>
			<td class="ipt" style="width: 85%;">
				<textarea rows="5" style="width: 90%;height: 50%" id="change_reason"></textarea>
			</td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	</div>
	
</body>

</html>

