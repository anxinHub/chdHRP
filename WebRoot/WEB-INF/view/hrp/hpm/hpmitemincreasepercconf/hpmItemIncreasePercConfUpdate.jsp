<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var dataFormat;
	var app_mod_code = '${app_mod_code}';
	$(function() {
	
		autocomplete("#item_code", "../queryItemAllDict.do?app_mod_code='03','99'&isCheck=false", "id", "text", true, true);

		$("#item_code").ligerTextBox({
			disabled : true
		});
		
		
		liger.get("item_code").setValue('${item_code}');

		liger.get("item_code").setText('${item_name}');
		
		$("#increase_percent").ligerTextBox({width:178});

		loadForm();

	});

	function save() {

		var formPara = {

			item_code : liger.get("item_code").getValue(),

			increase_percent : $("#increase_percent").val()

		};
		ajaxJsonObjectByUrl("updateHpmItemIncreasePercConf.do", formPara, function(responseData) {

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

	function saveItemIncreasePercConf() {

		if ($("form").valid()) {

			save();

		}

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 30px">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目编码：</td>
				<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">增长比例(%)：</td>
				<td align="left" class="l-table-edit-td"><input name="increase_percent" type="text" id="increase_percent" value="${increase_percent}"
					ligerui="{type:'float'}" class="required" validate="{number:true,min:0.00,max:100.00}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
