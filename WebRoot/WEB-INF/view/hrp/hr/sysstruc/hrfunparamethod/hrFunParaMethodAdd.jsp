<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />

<script type="text/javascript">
	var dataFormat;
	$(function() {

		loadDict();

		loadForm();

	});

	function save() {
		if($("#para_code").val() == null || $("#para_code").val() == ""){
  			$.ligerDialog.warn("参数代码不能为空！");  
  			return ;  
  		}
		
		if($("#para_name").val() == null || $("#para_name").val() == ""){
  			$.ligerDialog.warn("参数名称不能为空！");  
  			return ;  
  		}
		
		if($("#para_sql").val() == null || $("#para_sql").val() == ""){
  			$.ligerDialog.warn("取值方法不能为空！");  
  			return ;  
  		}
		
		var formPara = {
			para_code : $("#para_code").val(),
			para_name : $("#para_name").val(),
			para_sql : $("#para_sql").val()
		};

		ajaxJsonObjectByUrl("hrFunParaMethodSetadd.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				$("input[name='para_code']").val('');
				$("input[name='para_name']").val('');
				$("input[name='para_sql']").val('');
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

	function saveHpmFunParaMethod() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 10px;">参数代码：</td>
				<td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:200}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">参数名称：</td>
				<td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 10px;">取值方法：</td>
				<td align="left" class="l-table-edit-td" colspan="4">
					<textarea rows="8" class="liger-textarea" id="para_sql" name="para_sql" style="width: 480px" validate="{required:true}"></textarea></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><font color="red">举例</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">select
					dept_id as <font color="red">id</font>, dept_name as <font
					color="red">text</font> from hos_dept_dict <br /> where is_stop=0 AND
					group_id = {group_id} AND hos_id = {hos_id}  <br /> order by
					dept_code 
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 10px;"><font color="red">说明</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">1)id text
					必写<br /> 2)变量放在{}中间 常用变量{group_id} {hos_id} 
					
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
