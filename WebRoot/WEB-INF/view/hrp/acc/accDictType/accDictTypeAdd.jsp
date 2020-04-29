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
<style>
element.style {
	width: 300px;
}
</style>
<script type="text/javascript">
	var addDictDialog = frameElement.dialog;
	$(function() {
		loadCss();
		$("#table_code").val(addDictDialog!=null?addDictDialog.get('data').ywtype_name:"")
		$("#table_code").ligerGetTextBoxManager().setDisabled();
	});

	function loadCss() {
		$("#dict_code").ligerTextBox({ width : 400 }); 
		$("#dict_name").ligerTextBox({ width : 400 });
		$("#table_code").ligerTextBox({ width : 400 });
		$("#type_code").ligerTextBox({ width : 400 });
		$("#type_name").ligerTextBox({ width : 400 });
		$("#is_stop").ligerTextBox({ width : 400 });
	}
	
	function save(forms,dialog) {
		var formPara = {
				dict_code : $("#dict_code").val(),
				dict_name : $("#dict_name").val(),
				table_code_name : $("#table_code").val(),
				table_code : addDictDialog!=null?addDictDialog.get('data').ywtype_code:"" ,
				type_code : $("#type_code").val(),
				type_name : $("#type_name").val(),
				note : $("#note").val(),
				is_stop : $("#is_stop").val(),
				sort_code : '系统生成',
		};
		
		
		if (formPara.dict_code == "") {
			$.ligerDialog.error("字典编码不能为空!");
			return;
		}else if(formPara.dict_code.length>20){
			$.ligerDialog.error("字典编码过长!");
			return;
		}
		
		if (formPara.dict_name == "") {
			$.ligerDialog.error("字典名称不能为空!");
			return;
		}else if(formPara.dict_name.length>50){
			$.ligerDialog.error("字典名称过长!");
			return;
		}
		
		if (!(formPara.table_code == "") && formPara.table_code.length>50) {
			$.ligerDialog.error("类型编码过长!");
			return;
		}
		
		if (!(formPara.type_name == "") && formPara.type_name.length>50) {
			$.ligerDialog.error("类型名称过长!");
			return;
		}
		
		if (!(formPara.note == "") && formPara.note.length>100) {
			$.ligerDialog.error("备注过长!");
		}
		
		ajaxJsonObjectByUrl("addDictType.do", formPara,
			function(resData) {
				if(resData.status){
					$("#dict_code").val("");
					$("#dict_name").val("");
					$("#type_code").val("");
			    	$("#type_name").val("");
					$("#note").val("");
					$("#is_stop").val("");
				}	
		},false);
		
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

</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<label style="color: red;">*</label>字典编码:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="dict_code" type="text" id="dict_code" ltype="text" maxlength="20" validate="{required:true}" />
				</td>
				<td align="left"></td>
			</tr>


			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><label style="color: red;">*</label>字典名称:
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="dict_name" type="text" id="dict_name" ltype="text"
					maxlength="50" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">业务类型:</td>
				<td align="left" class="l-table-edit-td"><input name="table_code" style="width: 400px;height: 22px;"
					type="text" id="table_code" ltype="text" maxlength="20"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">类型编码:</td>
				<td align="left" class="l-table-edit-td"><input
					name="type_code" type="text" id="type_code" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">类型名称:</td>
				<td align="left" class="l-table-edit-td"><input
					name="type_name" type="text" id="type_name" ltype="text"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td" style="display:none;"
					style="padding-left: 20px;">排序号:</td>
				<td align="left" class="l-table-edit-td"><input
					name="sort_code" type="text" id="sort_code" ltype="text" style="display:none;"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">备注:</td>
				<td align="left" class="l-table-edit-td"><textarea name="note"
						id="note" ltype="textarea" maxlength="100"
						style="border: 1px solid #aecaf0; height: 50px; line-height: 20px; width: 400px; background: #fff;"></textarea>
				</td>
				<td align="left"></td>
			</tr>

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">状态:</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop"
					id="is_stop" style="width: 180px;">
						<option value="0">启用</option>
						<option value="1">停用</option>
				</select></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
