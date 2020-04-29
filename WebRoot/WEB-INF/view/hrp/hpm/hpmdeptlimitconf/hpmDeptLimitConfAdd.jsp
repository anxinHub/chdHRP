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
		
		autocomplete("#dept_id","../../hpm/queryDeptDictByPerm.do?isCheck=false","id","text",true,true);

		loadForm();

	});

	function save() {
		var formPara = {

			dept_id : liger.get("dept_id").getValue().split(",")[0],
			dept_no : liger.get("dept_id").getValue().split(",")[1],
			is_limit : $("#is_limit").val(),

			lower_money : $("#lower_money").val(),

			upper_money : $("#upper_money").val()

		};

		ajaxJsonObjectByUrl("addHpmDeptLimitConf"+app_mod_code+".do?isCheck=false", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				
				$("input[name='dept_id']").val('');
				
				$("input[name='is_limit']").val('');
				
				$("input[name='lower_money']").val('');
				
				$("input[name='upper_money']").val('');
				
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

	function saveDeptLimitConf() {
		
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
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">科室名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_id" type="text" id="dept_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否控制：</td>
				<td align="left" class="l-table-edit-td">
					<select name="is_limit" id="is_limit" style="width:160px;">
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">保底线：</td>
				<td align="left" class="l-table-edit-td"><input
					name="lower_money" type="text" id="lower_money" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">封顶线：</td>
				<td align="left" class="l-table-edit-td"><input
					name="upper_money" type="text" id="upper_money" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
