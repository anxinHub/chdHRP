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
	$(function() {
		
		loadDict();
		
		loadForm();
		
		liger.get("is_avg").setValue("${is_avg}");
		
		liger.get("is_stop").setValue("${is_stop}");
		
		
	});

	function save() { 
		
		var formPara = {
				
			dept_id : $("#dept_id").val(),
			
			dept_code : $("#dept_code").val(),
			
			dept_name : $("#dept_name").val(),
			
			dept_note : $("#dept_note").val(),
			
			dept_kind_code : '',
			
			nature_code : '',
			
			is_avg : $("#is_avg").val(),
			
			is_stop : $("#is_stop").val()
		
		};
		ajaxJsonObjectByUrl("updatePrmDeptHip.do", formPara, function(
				responseData) {

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

	function saveHpmDept() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
/* 
		autocomplete("#dept_kind_code", "../queryDeptKindDict.do?isCheck=false","id", "text", true, true);

		autocomplete("#nature_code", "../queryDeptNatureDict.do?isCheck=false","id", "text", true, true); */

		$("#dept_id").ligerTextBox({
			width : 160,
			disabled : true
		});
		$("#dept_code").ligerTextBox({
			width : 160,
			disabled : true
		});

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td" style="display: none">
					<input name="dept_id" disabled="true" type="text" id="dept_id" ltype="text" value="${dept_id}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_code" disabled="true" type="text" id="dept_code" ltype="text" value="${dept_code}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_name" type="text" id="dept_name" ltype="text" value="${dept_name}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室描述：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_note" type="text" id="dept_note" ltype="text" value="${dept_note}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与人均奖</td>
				<td align="left" class="l-table-edit-td"><select name="is_avg" id="is_avg">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用</td>
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
