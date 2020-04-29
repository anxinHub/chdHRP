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
			work_type_code : $("#work_type_code").val(),
			work_type_name : $("#work_type_name").val(),
			work_type_remark : $("#work_type_remark").val(),
			is_last : liger.get("is_last").getValue(),
			is_stop : liger.get("is_stop").getValue()
		};
		ajaxJsonObjectByUrl("addHtcWorkType.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				$("input[name='work_type_code']").val('');
				$("input[name='work_type_name']").val('');
				$("input[name='work_type_remark']").val('');
				$("input[name='is_last']").val('');
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

	function saveWorkType() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		
	    autocomplete("#is_stop","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true);
		
		autocomplete("#is_last","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业分类编码：</td>
				<td align="left" class="l-table-edit-td"><input name="work_type_code" type="text" id="work_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业分类名称：</td>
				<td align="left" class="l-table-edit-td"><input name="work_type_name" type="text" id="work_type_name" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否末级：</td>
				<td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding: 0px 0px 50px 20px;">分类说明：</td>
				<td align="left" class="l-table-edit-td" colspan="2"><textarea cols="100" rows="4" class="l-textarea" id="work_type_remark" name="work_type_remark" style="width: 250px;resize:none"></textarea></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
