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
	var first_length;
	$(function() {
		//获取第一级编码长度
		first_length=${first_length};
		loadDict();
		loadForm();
		$("#ass_type_code").ligerTextBox({
			disabled : true
		});

		if ('${is_last}' == '1') {
			liger.get("is_last").setValue(1);
			liger.get("is_last").setText("是");

		}

		if ('${is_last}' == '0') {
			liger.get("is_last").setValue(0);
			liger.get("is_last").setText("否");

		}
		if ('${is_stop}' == '1') {
			liger.get("is_stop").setValue(1);
			liger.get("is_stop").setText("是");

		}

		if ('${is_stop}' == '0') {
			liger.get("is_stop").setValue(0);
			liger.get("is_stop").setText("否");

		}
		
		if ('${ass_naturs}' == '01') {
			liger.get("ass_naturs").setValue('01');
			liger.get("ass_naturs").setText("专用设备");

		}
		if ('${ass_naturs}' == '02') {
			liger.get("ass_naturs").setValue('02');
			liger.get("ass_naturs").setText("一般设备");

		}
		if ('${ass_naturs}' == '03') {
			liger.get("ass_naturs").setValue('03');
			liger.get("ass_naturs").setText("房屋及建筑物");

		}
		if ('${ass_naturs}' == '04') {
			liger.get("ass_naturs").setValue('04');
			liger.get("ass_naturs").setText("其它固定资产");

		}
		if ('${ass_naturs}' == '05') {
			liger.get("ass_naturs").setValue('05');
			liger.get("ass_naturs").setText("无形资产");

		}
		//charge_disabled();
	});

	function save() {
		var formPara = {
			ass_type_id : '${ass_type_id}',
			ass_type_code : $("#ass_type_code").val(),
			ass_type_name : $("#ass_type_name").val(),
			is_last : $("#is_last").val(),
			ass_naturs : liger.get("ass_naturs").getValue(),
			is_stop : $("#is_stop").val(),
			type_level:1
		};
		ajaxJsonObjectByUrl("updateBudgCostFassetType.do?isCheck=false", formPara, function(
				responseData) {

			if (responseData.state == "true") {
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

	function saveBudgCostFassetType() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		
		var v_length=$("#ass_type_code").val();
		
		   if(v_length.length>first_length){

			   $("#ass_naturs").ligerComboBox({disabled:true,cancelable: false});
			   
		   }else {
				autocomplete("#ass_naturs",
						"../../../../ass/queryAssNaturs.do?isCheck=false", "id", "text",
						true, true, "","","${ass_naturs}");
			   
		   }

	}
/* 	function charge_disabled() {
		var v_length=$("#ass_type_code").val();
	     if(v_length.length>first_length){

	    	 $("#super_code").ligerGetComboBoxManager().setDisabled(true);
	    	// $("#super_code").ligerComboBox({disabled: true });  // 禁用 false 是启用  

	    	 
	    	 $("#ass_naturs").ligerGetComboBoxManager().setDisabled();
	    	 
	    	 
	     }else{
	    	 $("#super_code").ligerGetComboBoxManager().setEnabled();
	    	 $("#ass_naturs").ligerGetComboBoxManager().setEnabled();
	     }
	} */
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"></td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_code" type="hidden" id="ass_type_id" ltype="text"
					value="${ass_type_id}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_code" type="text" id="ass_type_code" ltype="text"
					value="${ass_type_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_name" type="text" id="ass_type_name" ltype="text"
					value="${ass_type_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">性质编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_naturs" type="text" id="ass_naturs" ltype="text"
					 /></td>
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
