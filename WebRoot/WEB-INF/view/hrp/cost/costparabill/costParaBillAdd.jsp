<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadDict()//加载下拉框
		loadForm();

	});

	function save() {
		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val(),

			bill_name : $("#bill_name").val(),

			cost_type_code : liger.get("cost_type_code").getValue()

		};

		ajaxJsonObjectByUrl("addCostParaBill.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='acc_year']").val('');
				$("input[name='acc_month']").val('');
				$("input[name='bill_name']").val('');
				$("input[name='cost_type_code']").val('');
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

	function saveCostParaBill() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autoCompleteByData("#cost_type_code", cost_bill_type_state.Rows, "id",
				"text", true, true, "", true);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">统计年份：</td>
				<td align="left" class="l-table-edit-td"><input name="acc_year"
					type="text" id="acc_year" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">统计月份：</td>
				<td align="left" class="l-table-edit-td"><input
					name="acc_month" type="text" id="acc_month" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>

				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">单据类型：</td>
				<td align="left" class="l-table-edit-td"><input
					name="cost_type_code" type="text" id="cost_type_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td"><input
					name="bill_name" type="text" id="bill_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
