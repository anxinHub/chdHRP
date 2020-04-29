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

	$(function() {
		loadDict()//加载下拉框
		loadForm();
	});

	function save() {
		var formPara = {
				table_type_code : liger.get("table_type_code").getValue(),
				page_code : $("#page_code").val(),
				page_name : $("#page_name").val(),
				is_stop : liger.get("is_stop").getValue(),
				is_innr:1,
				note : $("#note").val()
		};
		ajaxJsonObjectByUrl("updateHrPageDesign.do?isCheck=false", formPara, function(responseData) {
			if (responseData.state == "true") {
				parentFrameUse().tree.reload();
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

	function savePageDesign() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		
		autocomplete("#table_type_code","../../queryHrTableType.do?ischeck=false","id","text",true,true);
		liger.get("table_type_code").setValue("${hrPageDesign.table_type_code}");
		liger.get("table_type_code").setText("${hrPageDesign.table_type_name}");
		
		$("#is_stop").ligerComboBox({
			data: [
                { text: '否', id: '0' },
                { text: '是', id: '1' }
            ],
			valueField : 'id',
			textField : 'text',
			selectBoxWidth : 160,
			autocomplete : true,
			value : "${hrPageDesign.is_stop}",
			width : 160,
		});
		
	    $("#table_type_code").ligerComboBox({disabled:true});
	    
		$("#page_code").ligerTextBox({
			width : 160,
			disabled:true
		});
		$("#page_name").ligerTextBox({
			width : 160
		});

		$("#is_stop").ligerTextBox({
			width : 160
		});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">数据表类型：</td>
				<td align="left" class="l-table-edit-td">
					<input name="table_type_code" type="text" id="table_type_code" ltype="text" validate="{required:true}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">数据表编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="page_code" type="text" id="page_code" ltype="text" required="true" validate="{required:true}" value="${hrPageDesign.page_code}"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">数据表名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="page_name" type="text" id="page_name" ltype="text" required="true" validate="{required:true}" value="${hrPageDesign.page_name}"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">备注：</td>
				<td align="left" class="l-table-edit-td" colspan="5">
					<textarea name="note" id="note" rows="4" cols="5" style="width:100%">${hrPageDesign.note}</textarea>
				</td>

			</tr>
		</table>
	</form>

</body>
</html>
