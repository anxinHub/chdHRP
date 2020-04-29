<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var dataFormat;

	$(function() {

		loadForm();
		if('${is_stop}'=='1'){
     		liger.get("is_stop").setValue(1);
     		liger.get("is_stop").setText("是");	
     		
     	}
     	
     	if('${is_stop}'=='0'){
     		liger.get("is_stop").setValue(0);
     		liger.get("is_stop").setText("否");	
     		
     	}
     	
     	$("#work_item_code").ligerTextBox({ disabled: true });

	});

	function save() {
		var formPara = {
			work_item_code : $("#work_item_code").val(),

			work_item_name : $("#work_item_name").val(),

			is_stop : $("#is_stop").val()

		};
		ajaxJsonObjectByUrl("updateHpmWorkitem.do", formPara, function(
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

	function saveWorkitem() {
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标编码：</td>
				<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text" value="${work_item_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标名称：</td>
				<td align="left" class="l-table-edit-td"><input name="work_item_name" type="text" id="work_item_name" ltype="text" value="${work_item_name}"
					validate="{required:true,maxlength:20}" /></td>
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
		</table>
	</form>
</body>
</html>
