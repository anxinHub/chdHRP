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
		
		$("#beg_date").ligerTextBox({
			width : 160
		});
		$("#end_date").ligerTextBox({
			width : 160
		});

	});

	function save() {
		var formPara = {

			summary : $("#summary").val(),

			beg_date : $("#beg_date").val(),

			end_date : $("#end_date").val()

		};
		ajaxJsonObjectByUrl("addAssCheckPlanGeneral.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='summary']").val('');
				$("input[name='beg_date']").val('');
				$("input[name='end_date']").val('');
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

	function saveAssCheckPlanGeneral() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">


			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>盘点开始日期：</td>
				<td align="left" class="l-table-edit-td"><input name="beg_date"
					type="text" id="beg_date" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>盘点结束日期：</td>
				<td align="left" class="l-table-edit-td"><input name="end_date"
					type="text" id="end_date" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>

			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">任务说明：</td>
				<td align="left" class="l-table-edit-td">
				<textarea
					rows="3" cols="40" id="summary" name="summary"></textarea>
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
