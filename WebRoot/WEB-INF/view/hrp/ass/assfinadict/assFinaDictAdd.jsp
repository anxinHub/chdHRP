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
	var dataFormat;
	var first_length=0;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
		//获取第一级编码长度
		first_length=${first_length};
		
	});

	function save() {
		
		var formPara = {

			fina_type_code : $("#fina_type_code").val(),

			fina_type_name : $("#fina_type_name").val(),

			is_last : $("#is_last").val(),

		
			is_stop : liger.get("is_stop").getValue(),
			is_budg : liger.get("is_budg").getValue(),

		};

		ajaxJsonObjectByUrl("addAssFinaDict.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='fina_type_code']").val('');
				$("input[name='fina_type_name']").val('');
				$("input[name='is_last']").val('');
				$("input[name='is_stop']").val('');
				$("input[name='is_budg']").val('');
				parent.query();
				parent.loadTree();
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

	function saveAssFinaDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		$('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField:'id',
			textField:'text',
			cancelable:true,
			width:180
			
		});
		$('#is_budg').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField:'id',
			textField:'text',
			cancelable:true,
			width:180
			
		})
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类编码<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="fina_type_code" type="text" id="fina_type_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="fina_type_name" type="text" id="fina_type_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			
			
			<!-- <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否末级分类<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><select id="is_last"
					name="is_last">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr> -->
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_stop"y
					name="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select> -->
				<input name="is_stop" type="text" id="is_stop" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否预算：</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_budg"
					name="is_budg">
						<option value="0">否</option>
						<option value="1">是</option>
				</select> -->
				<input name="is_budg type="text" id="is_budg"/>
				</td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
