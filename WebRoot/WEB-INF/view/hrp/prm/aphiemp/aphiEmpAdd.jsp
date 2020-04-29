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

	});

	function save() {
		var formPara = {
			emp_code : $("#emp_code").val(),
			emp_name : $("#emp_name").val(),
			dept_id : liger.get("dept_code").getValue(),
			duty_code : liger.get("duty_code").getValue(),
			is_stop : $("#is_stop").val(),
			is_acc : $("#is_acc").val()
		};

		ajaxJsonObjectByUrl("addAphiEmp.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				$("input[name='emp_code']").val('');
				$("input[name='emp_name']").val('');
				$("input[name='dept_code']").val('');
				$("input[name='duty_code']").val('');
				$("input[name='is_stop']").val('');
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

	function saveEmp() {
		if ($("form").valid()) {

			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#dept_code", "../queryPrmDeptDict.do?isCheck=false","id", "text", true, true);
		
		autocomplete("#duty_code", "../queryPrmEmpDuty.do?isCheck=false", "id","text", true, true);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="emp_name" type="text" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否发奖金：</td>
				<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
