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
	var para = "";
	$(function() {
		loadDict();
		loadForm();
	});

	function save() {
		var formPara = {
			paramVo:'${paramVo}',
			dir_res_code : liger.get("dir_res_code").getValue(),
			pub_res_code : liger.get("pub_res_code").getValue(),
			man_res_code : liger.get("man_res_code").getValue(),
			ass_res_code : liger.get("ass_res_code").getValue(),
		};
		ajaxJsonObjectByUrl("updateBatchHtcResCauseSet.do", formPara, function(
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

	function saveResCauseSet() {
		if ($("form").valid()) {
			save();
		}
	}

	
	function loadDict() {
		//直接成本资源动因
		autocomplete("#dir_res_code","../../../info/base/queryHtcResCauseDict.do?isCheck=false", "id", "text", true,true);
		//公用成本资源动因
		autocomplete("#pub_res_code","../../../info/base/queryHtcResCauseDict.do?isCheck=false", "id", "text", true,true);
		//管理成本资源动因
		autocomplete("#man_res_code","../../../info/base/queryHtcResCauseDict.do?isCheck=false", "id", "text", true,true);
		//医辅成本资源动因
		autocomplete("#ass_res_code","../../../info/base/queryHtcResCauseDict.do?isCheck=false.do?isCheck=false", "id", "text", true,true);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">直接成本资源动因：</td>
				<td align="left" class="l-table-edit-td"><input name="dir_res_code" type="text" id="dir_res_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公用成本资源动因：</td>
				<td align="left" class="l-table-edit-td"><input name="pub_res_code" type="text" id="pub_res_code" ltype="text"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">管理成本资源动因：</td>
				<td align="left" class="l-table-edit-td"><input name="man_res_code" type="text" id="man_res_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医辅成本资源动因：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_res_code" type="text" id="ass_res_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
