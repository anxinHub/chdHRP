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
			liger.get("ass_naturs").setText("房屋及构筑物");

		}
		if ('${ass_naturs}' == '03') {
			liger.get("ass_naturs").setValue('03');
			liger.get("ass_naturs").setText("专用设备");

		}
		if ('${ass_naturs}' == '02') {
			liger.get("ass_naturs").setValue('02');
			liger.get("ass_naturs").setText("通用设备");

		}
		if ('${ass_naturs}' == '04') {
			liger.get("ass_naturs").setValue('04');
			liger.get("ass_naturs").setText("其它固定资产");

		}
		if ('${ass_naturs}' == '05') {
			liger.get("ass_naturs").setValue('05');
			liger.get("ass_naturs").setText("无形资产");

		}
		if ('${ass_naturs}' == '06') {
			liger.get("ass_naturs").setValue('06');
			liger.get("ass_naturs").setText("土地使用权");

		}
		//charge_disabled();
	});
	
	function save() {
		var formPara = {
			ass_type_id : '${ass_type_id}',
			ass_type_code : $("#ass_type_code").val(),
			ass_type_name : $("#ass_type_name").val(),
			manage_depre_amount:$("#manage_depre_amount").val(),
			is_last : $("#is_last").val(),
			ass_naturs : liger.get("ass_naturs").getValue(),
			acc_type_code:liger.get("acc_type_code").getValue(),
			is_stop : liger.get("is_stop").getValue(),
			type_level:1
		};
		ajaxJsonObjectByUrl("updateAssTypeDict.do", formPara, function(
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

	function saveAssTypeDict() {
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
						"../queryAssNaturs.do?isCheck=false", "id", "text",
						true, true, "","","${ass_naturs}");
			   
		   }
		   
		   autocomplete("#acc_type_code",
					"../queryMatFinaType.do?isCheck=false", "id", "text",
					true, true, "", false);
		   liger.get("acc_type_code").setValue("${acc_type_code}");
		   liger.get("acc_type_code").setText("${acc_type_code} ${acc_type_name}");
		   
		   if(${isDisabled}){
			   $("#is_last,#is_stop").ligerComboBox({disabled: true });
		   }
		   $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		});
			liger.get("is_stop").setValue("${is_stop}");
		   //if(${lastIsDisabled}){
			   $("#is_last").ligerComboBox({disabled: true });
		   //}

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
					style="padding-left: 20px;">分类编码<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_code" type="text" id="ass_type_code" ltype="text"
					value="${ass_type_code}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_name" type="text" id="ass_type_name" ltype="text"
					value="${ass_type_name}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
				<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">折旧年限：</td>
				<td align="left" class="l-table-edit-td"><input
					name="manage_depre_amount" type="text" id="manage_depre_amount" ltype="text"
					 value="${manage_depre_amount}" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">财务分类：</td>
				<td align="left" class="l-table-edit-td"><input
					name="acc_type_code" type="text" id="acc_type_code" ltype="text"
					 /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资产性质：<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_naturs" type="text" id="ass_naturs" ltype="text"
					 /></td>
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
				<input  name="is_stop" type="text" id="is_stop"/>
				</td>
				<td align="left"></td>
			</tr>
		</table>
		</form>
</body>
</html>
