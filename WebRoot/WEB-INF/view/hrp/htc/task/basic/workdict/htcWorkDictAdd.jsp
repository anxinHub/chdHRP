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
			work_code : $("#work_code").val(),
			work_name : $("#work_name").val(),
			work_type_code : liger.get("work_type_code").getValue(),
			work_remark : $("#work_remark").val(),
			is_people : liger.get("is_people").getValue(),
			is_material : liger.get("is_material").getValue(),
			is_asset : liger.get("is_asset").getValue()
		};
		ajaxJsonObjectByUrl("addHtcWorkDict.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				$("input[name='work_code']").val('');
				$("input[name='work_name']").val('');
				$("input[name='work_type_code']").val('');
				$("input[name='work_remark']").val('');
				/* liger.get("is_people").setValue('');
				liger.get("is_material").setValue('');
				liger.get("is_asset").setValue(''); */
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

	function saveWorkDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#work_type_code","../../../info/base/queryHtcWorkTypeDict.do?isCheck=false", "id","text", true, true);
		autocomplete("#is_people","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true,"",false,"0");
		autocomplete("#is_material","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true,"",false,"0");
		autocomplete("#is_asset","../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text",true, true,"",false,"0");
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业编码：</td>
				<td align="left" class="l-table-edit-td"><input name="work_code" type="text" id="work_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业名称：</td>
				<td align="left" class="l-table-edit-td"><input name="work_name" type="text" id="work_name" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业分类编码：</td>
				<td align="left" class="l-table-edit-td"><input name="work_type_code" type="text" id="work_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否体现人工：</td>
				<td align="left" class="l-table-edit-td"><input name="is_people" type="text" id="is_people" ltype="text" validate="{required:true}"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否体现材料：</td>
				<td align="left" class="l-table-edit-td"><input name="is_material" type="text" id="is_material" ltype="text" validate="{required:true}"/></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否体现折旧：</td>
				<td align="left" class="l-table-edit-td"><input name="is_asset" type="text" id="is_asset" ltype="text" validate="{required:true}"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业说明：</td>
				<td align="left" class="l-table-edit-td" colspan="2"><textarea cols="100" rows="4" class="l-textarea" id="work_remark" name="work_remark" style="width: 300px;height: 140px;resize:none" validate="{maxlength:200}"></textarea></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
