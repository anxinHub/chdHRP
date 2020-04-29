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
	var first_length;
	$(function() {
		//获取第一级编码长度
		first_length=${first_length};
		loadDict();
		loadForm();
		$("#fina_type_code").ligerTextBox({
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
		if ('${is_budg}' == '0') {
			liger.get("is_budg").setValue(0);
			liger.get("is_budg").setText("否");

		}
		if ('${is_budg}' == '1') {
			liger.get("is_budg").setValue(1);
			liger.get("is_budg").setText("是");

		}
		
	
		//charge_disabled();
	});

	function save() {
		var formPara = {
			fina_type_code : $("#fina_type_code").val(),
			fina_type_name : $("#fina_type_name").val(),
			is_last : $("#is_last").val(),
			is_stop : liger.get("is_stop").getValue(),
			is_budg : liger.get("is_budg").getValue(),
			type_level:1
		};
		ajaxJsonObjectByUrl("updateAssFinaDict.do", formPara, function(
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

	function saveAssFinaDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		 if(${isDisabled}){
		   $("#is_last,#is_stop").ligerComboBox({disabled: true });
	     } 
		
		 //if(${lastIsDisabled}){
		   $("#is_last").ligerComboBox({disabled: true });
	    // }
		   $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
			liger.get("is_stop").setValue("${is_stop}");
			$('#is_budg').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
			liger.get("is_budg").setValue("${is_budg}");
	}
/* 	function charge_disabled() {
		var v_length=$("#fina_type_code").val();
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
					style="padding-left: 20px;">分类编码<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="fina_type_code" type="text" id="fina_type_code" ltype="text"
					value="${fina_type_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="fina_type_name" type="text" id="fina_type_name" ltype="text"
					value="${fina_type_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否末级分类<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><select id="is_last"
					name="is_last">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td">
				<!-- <select id="is_stop"
					name="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select> -->
				<input name="is_stop" type="text" id="is_stop"  />
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
				<input name="is_budg" type="text" id="is_budg"  />
				</td>
				<td align="left"></td>
			</tr>
		</table>
		</form>
</body>
</html>
