<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	var dataFormat;
	$(function() {
		
		$("#kpi_code").ligerTextBox({
			disabled : true,
			width : 160
		});
		
		$("#target_note").ligerTextBox({
			disabled : true,
			width : 520
		});

		$("#nature_name").ligerTextBox({
			disabled : true,
			width : 160
		});

		$("#formula_method_chs").ligerTextBox({
			disabled : true,
			width : 520
		});

		$("#formula_name").ligerTextBox({
			disabled : true,
			width : 160
		});

		$("#fun_note").ligerTextBox({
			disabled : true,
			width : 520
		});

		$("#fun_name").ligerTextBox({
			disabled : true,
			width : 160
		});

		$("#formula_code").ligerTextBox({
			disabled : true,
			width : 160
		});

		$("#fun_code").ligerTextBox({
			disabled : true,
			width : 160
		});
		
		$("#formula_code").val("${formula_code}" == 'null'?"":"${formula_code}");

		$("#formula_method_chs").val("${formula_method_chs}" == 'null'?"":"${formula_method_chs}");
		
		$("#formula_name").val("${formula_name}" == 'null'?"":"${formula_name}");
		
		$("#fun_code").val("${fun_code}" == 'null'?"":"${fun_code}");

		$("#fun_note").val("${fun_method_chs}" == 'null'?"":"${fun_method_chs}");
		
		$("#fun_name").val("${fun_name}" == 'null'?"":"${fun_name}");

		//--取值方法
		$("#method_code").ligerComboBox({
			url : '../queryPrmTargetMethodPara.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			width : 160,
			autocomplete : autocomplete,
			onSelected : function(selectValue) {
				if (selectValue == '03') {

					$("#formula_code").ligerTextBox({
						disabled : true
					});

					$("#fun_code").ligerTextBox({
						disabled : true
					});

					$("#formulaButton").ligerButton({
						disabled : true
					});

					$("input[name='formula_code']").val('');

					$("input[name='formula_method_chs']").val('');

					$("input[name='formula_name']").val('');

				} else if (selectValue == '02') {

					$("#formula_code").ligerTextBox({
						disabled : true
					});

					$("#fun_code").ligerTextBox({
						disabled : true
					});


					$("input[name='fun_code']").val('');

					$("input[name='fun_note']").val('');

					$("input[name='fun_name']").val('');

				} else {

					$("#formula_code").ligerTextBox({
						disabled : true
					});

					$("#fun_code").ligerTextBox({
						disabled : true
					});

					$("input[name='formula_code']").val('');

					$("input[name='fun_code']").val('');

					$("input[name='fun_note']").val('');

					$("input[name='fun_name']").val('');

					$("input[name='formula_method_chs']").val('');

					$("input[name='formula_name']").val('');

				}
			}
		});
		$("#method_code").ligerButton({
			disabled : true
		});
		
		liger.get("method_code").setValue("${method_code}");
		liger.get("method_code").setText("${method_name}");
		loadForm();

	});

</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" colspan="6" style="padding-left: 100px;"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">kpi指标：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="kpi_code" type="text" id="kpi_code" ltype="text" value="${kpi_name}"  validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">指标性质：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="nature_name" type="text" id="nature_name" value="${nature_name}" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">指标描述：</td>
				<td align="left" class="l-table-edit-td" colspan="4" style="padding-top: 20px;">
					<input name="target_note" type="text" ltype="text" id="target_note" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">取值方法：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="method_code" type="text" id="method_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="left" style="padding-left: 100px; padding-top: 20px;">
				</td>

				<td align="left" style="padding-left: 10px; padding-top: 20px;">
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px; padding-top: 20px;">公式代码：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="formula_code" type="text" id="formula_code" ltype="text"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">公式名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="formula_name" type="text" id="formula_name" ltype="text"   /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">取值公式：</td>
				<td align="left" class="l-table-edit-td" colspan="4" style="padding-top: 20px;">
					<input name="formula_method_chs" type="text" ltype="text" id="formula_method_chs"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">函数代码：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="fun_code" type="text" id="fun_code" ltype="text"  /> 
					<input name="f_p_v" type="hidden" id="f_p_v" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">函数名称：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="fun_name" type="text" id="fun_name" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">取值函数：</td>
				<td align="left" class="l-table-edit-td" colspan="4" style="padding-top: 20px;">
					<input name="fun_note" type="text" ltype="text" id="fun_note" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
