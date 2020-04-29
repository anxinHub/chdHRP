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
	var first_length=0;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
		//获取第一级编码长度
		first_length=${first_length};
		
	});

	function save() {
		var v_length=$("#ass_type_code").val();
		if(v_length.length==first_length){
			if(liger.get("ass_naturs").getValue()==""){
				$.ligerDialog.error('资产性质不能为空');
		    	 return ;
			}
	     }

		var formPara = {

			ass_type_id : "",
			ass_type_code : $("#ass_type_code").val(),

			ass_type_name : $("#ass_type_name").val(),

			is_last : $("#is_last").val(),

			ass_naturs : liger.get("ass_naturs").getValue(),

			is_stop : $("#is_stop").val()

		};

		ajaxJsonObjectByUrl("addBudgCostFassetType.do?isCheck=false", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='ass_type_code']").val('');
				$("input[name='ass_type_name']").val('');
				$("input[name='is_last']").val('');
				$("input[name='ass_naturs']").val('');
				$("input[name='is_stop']").val('');
				parent.query();
				parent.loadTree();
		    	 $("#ass_naturs").ligerGetComboBoxManager().setEnabled();
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

	function saveBudgCostFassetType() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		autocomplete("#ass_naturs",
				"../../../../ass/queryAssNaturs.do?isCheck=false", "id", "text",
				true, true, "", true);

	}
	function charge_disabled() {
		var v_length=$("#ass_type_code").val();
	     if(v_length.length>first_length){
	    	 $("input[name='ass_naturs']").val('');
	         $("#ass_naturs").ligerComboBox({disabled:true,cancelable: false});
	    	 
	     }else{
	    	 
	        	
	         $("#ass_naturs").ligerComboBox({disabled:false,cancelable: true});
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
					style="padding-left: 20px;">分类编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_code" type="text" id="ass_type_code" ltype="text"
					validate="{required:true,maxlength:20}" onchange="charge_disabled()" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_name" type="text" id="ass_type_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性质编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_naturs" type="text" id="ass_naturs" ltype="text"
					validate="{maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否末级分类：</td>
				<td align="left" class="l-table-edit-td"><select id="is_last"
					name="is_last">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select id="is_stop"
					name="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
