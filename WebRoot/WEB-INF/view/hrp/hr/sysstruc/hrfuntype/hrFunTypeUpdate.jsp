<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var dataFormat;
	var is_change;
	$(function() {
		loadForm();
		liger.get("is_stop").setValue("${is_stop}");
		$("#type_code").ligerTextBox({disabled : true});
	});

	function save() {
		if($("#type_name").val()=='${type_name}'){
			is_change = 0;
		}else{
			is_change = 1;
		}
		
		var formPara = {
			type_code : $("#type_code").val(),
			type_name : $("#type_name").val(),
			is_stop : $("#is_stop").val(),
			is_change : is_change
		};
		
		ajaxJsonObjectByUrl("hrFunTypeSetupdate.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				parent.query();
			}
		});
	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}

	function saveHpmFunType() {
		if ($("form").valid()) {
			save();
		}
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">分类编码：</td>
				<td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" value="${type_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">分类名称：</td>
				<td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" value="${type_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
