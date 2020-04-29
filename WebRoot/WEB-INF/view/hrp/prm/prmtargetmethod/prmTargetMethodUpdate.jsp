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
	var fun_para_value;
	$(function() {
		
		$("#target_code").ligerTextBox({
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
			width : 160
		});

		$("#fun_code").ligerTextBox({
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
						disabled : false
					});

					$("#formulaButton").ligerButton({
						disabled : true
					});
					$("#funButton").ligerButton({
						disabled : false
					});

					$("input[name='formula_code']").val('');

					$("input[name='formula_method_chs']").val('');

					$("input[name='formula_name']").val('');

				} else if (selectValue == '02') {

					$("#formula_code").ligerTextBox({
						disabled : false
					});

					$("#fun_code").ligerTextBox({
						disabled : true
					});

					$("#funButton").ligerButton({
						disabled : true
					});
					$("#formulaButton").ligerButton({
						disabled : false
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

					$("#funButton").ligerButton({
						disabled : true
					});
					$("#formulaButton").ligerButton({
						disabled : true
					});

					$("input[name='fun_note']").val('');

					$("input[name='fun_name']").val('');

					$("input[name='formula_method_chs']").val('');

					$("input[name='formula_name']").val('');

				}
			}
		});
		liger.get("method_code").setValue("${method_code}");
		liger.get("method_code").setText("${method_code} ${method_name}");
		loadForm();

	});

	function save() {
		var formPara = {

			target_code : $("#target_code").val(),

			method_code : liger.get("method_code").getValue(),

			formula_code : $("#formula_code").val(),

			fun_code : $("#fun_code").val(),

			fun_stack_data : JSON.stringify(fun_para_value)

		};

		ajaxJsonObjectByUrl("updatePrmTargetMethod.do", formPara, function(
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
	}

	function savePrmTargetMethod() {
		if ($("form").valid()) {
			save();
		}
	}

	function openFun() {
		
		var target_code = $("#target_code").val();
		
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmfun/prmFunPage.do?isCheck=false'+$("#fun_code").val()+"&target_code="+target_code,
			height : 630,
			width : 1200,
			data : {
			},
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,
			title : '奖金函数',
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					var obj = {};
					obj.target_code = target_code;
					dialog.frame.saveFunStack(obj);
					$("#fun_code").val(obj.fun_code);
					$("#fun_note").val(obj.fun_note);
					$("#fun_name").val(obj.fun_name);
					fun_para_value = obj.fun_para_value;
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]

		});

	}

	function openFormula() {
		parent.$.ligerDialog.open({
			url : 'hrp/prm/prmformula/prmFormulaPage.do?isCheck=false',
			height : 630,
			width : 1200,
			title : '计算公式',
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					var obj = {};//js对象实现引用传递
					dialog.frame.saveFormula(obj);
					$("#formula_code").val(obj.formula_code);
					$("#formula_name").val(obj.formula_name);
					$("#formula_method_chs").val(obj.formula_method_chs);
					dialog.close();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}
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
				<td align="right" class="l-table-edit-td" style="padding-left: 100px; padding-top: 20px;">指标编码：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px;">
					<input name="kpi_code" type="text" id="target_code" ltype="text" value="${target_code}"  validate="{required:true}" /></td>
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
					<font style="color: red">选择取值方法</font>
				</td>

				<td align="left" style="padding-left: 10px; padding-top: 20px;">
					<input class="liger-button" id="formulaButton" name="formulaButton" onClick="openFormula()" value="选择公式" /> 
					<input class="liger-button" id="funButton" name="funButton" onClick="openFun()" value="选择函数" />
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
