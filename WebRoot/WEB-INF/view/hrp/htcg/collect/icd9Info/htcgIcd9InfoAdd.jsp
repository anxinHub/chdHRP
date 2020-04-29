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
				mr_no : $("#mr_no").val(),
				in_hos_no : $("#in_hos_no").val(),
				icd9_code : liger.get("icd9_code").getValue(),
				icd9_seq : $("#icd9_seq").val(),
				icd9_date : $("#icd9_date").val(),
				icd9_time : $("#icd9_time").val(),
				icd9_oper : $("#icd9_oper").val(),
				anest_type_code : liger.get("anest_type_code").getValue(),
				anest_oper : $("#anest_oper").val()
		};
		ajaxJsonObjectByUrl("addIcdHtcg9Info.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				$("input[name='mr_no']").val('');
				$("input[name='in_hos_no']").val('');
				$("input[name='icd9_date']").val('');
				$("input[name='icd9_code']").val('');
				$("input[name='icd9_seq']").val('');
				$("input[name='icd9_time']").val('');
				$("input[name='icd9_oper']").val('');
				$("input[name='anest_type_code']").val('');
				$("input[name='anest_oper']").val('');
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

	function saveIcd9Info() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
    	autocomplete("#icd9_code","../../base/queryHtcgIcd9Dict.do?isCheck=false","id","text",true,true);
    	autocomplete("#anest_type_code","../../base/queryHtcgAnestTypeDict.do?isCheck=false","id","text",true,true);
        $("#icd9_seq").ligerComboBox({
            data: [
                { text: '1', id: '1' },
                { text: '2', id: '2' },
                { text: '3', id: '3' },
            ]
        }); 
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">病案号：</td>
				<td align="left" class="l-table-edit-td"><input name="mr_no" type="text" id="mr_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">住院号：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_no" type="text" id="in_hos_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">手术编码：</td>
				<td align="left" class="l-table-edit-td"><input name="icd9_code" type="text" id="icd9_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">手术序列：</td>
				<td align="left" class="l-table-edit-td" validate="{required:true}">
				<input name="icd9_seq" type="text" id="icd9_seq" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">手术时间：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" 
					name="icd9_date" type="text" id="icd9_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" 
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">手术时长：</td>
				<td align="left" class="l-table-edit-td"><input name="icd9_time" type="text" id="icd9_time" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">术者：</td>
				<td align="left" class="l-table-edit-td"><input name="icd9_oper" type="text" id="icd9_oper" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">麻醉种类：</td>
				<td align="left" class="l-table-edit-td"><input name="anest_type_code" type="text" id="anest_type_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">麻醉师：</td>
			<td align="left" class="l-table-edit-td"><input name="anest_oper" type="text" id="anest_oper" ltype="text" validate="{required:true}" /></td>
			<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
