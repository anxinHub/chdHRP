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
				plan_code : liger.get("plan_code").getValue(),
				dept_id : liger.get("dept_id").getValue().split(".")[0],
				proj_dept_id : liger.get("proj_dept_id").getValue().split(".")[0]
		};
		ajaxJsonObjectByUrl("addHtcRelativeDeptRela.do", formPara, function(responseData) {
			if (responseData.state == "true") {
			 $("input[name='dept_id']").val('');
			 $("input[name='proj_dept_id']").val('');
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

	function saveDeptRela() {
		if ($("form").valid()) {
			save();
		}
	}
	
	function loadDict() {

		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);
		
		autocomplete("#dept_id","../../../info/base/queryHtcDeptDict.do?isCheck=false", "id","text", true, true);

		autocomplete("#proj_dept_id","../../../info/base/queryHtcProjDeptDict.do?isCheck=false","id", "text", true, true);
		

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
				<td align="left" class="l-table-edit-td"><input name="proj_dept_id" type="text" id="proj_dept_id" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
