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
		var v_length=$("#ass_type_code").val();
		if(v_length.length==first_length){
			if(liger.get("ass_naturs").getValue()==""){
				$.ligerDialog.error('资产性质不能为空');
		    	 return ;
			}
	     }
		 var fac_type =  liger.get("is_stop").getValue();
    	 if(fac_type == null || fac_type == '' || fac_type == undefined ){
    		 $.ligerDialog.error("是否停用不能为空");
    		 return ; 
    	 }
		var formPara = {

			ass_type_id : "",
			ass_type_code : $("#ass_type_code").val(),

			ass_type_name : $("#ass_type_name").val(),
			
			manage_depre_amount:$("#manage_depre_amount").val(),

			is_last : $("#is_last").val(),

			ass_naturs : liger.get("ass_naturs").getValue(),
			
			acc_type_code:liger.get("acc_type_code").getValue(),

			is_stop : liger.get("is_stop").getValue()
			

		};

		ajaxJsonObjectByUrl("addAssTypeDict.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='ass_type_code']").val('');
				$("input[name='ass_type_name']").val('');
				$("input[name='is_last']").val('');
				$("input[name='ass_naturs']").val('');
				$("input[name='acc_type_code']").val('');
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

	function saveAssTypeDict() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		autocomplete("#ass_naturs",
				"../queryAssNaturs.do?isCheck=false", "id", "text",
				false, true, "", false);
		$("ass_naturs").ligerComboBox({
			cancelable:true
			
		})
		
		autocomplete("#acc_type_code",
				"../queryMatFinaType.do?isCheck=false", "id", "text",
				true, true, "", false);
		  $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width:180
			})

	}
	function charge_disabled() {
		var ass_type_code = $("#ass_type_code").val();
    	ajaxJsonObjectByUrl("getFirstNaturs.do?isCheck=false", {ass_type_code:ass_type_code,first_length:first_length}, function(
 				responseData) {
    		 if (responseData.state == "true" && responseData.naturs_code != '') {
    		 	 liger.get("ass_naturs").selectValue(responseData.naturs_code)
    		     $("#ass_naturs").ligerComboBox({disabled:true,cancelable: false});
    		 	 //liger.get("ass_naturs").setDisabled(true);
    		 	 $("#ass_type_name").focus();
    		 }else{
    			 $("#ass_naturs").ligerComboBox({disabled:false,cancelable: true});
    			 $("#ass_type_name").focus();
    		 }
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
					name="ass_type_code" type="text" id="ass_type_code" ltype="text"
					validate="{required:true,maxlength:20}" onchange="charge_disabled()"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">分类名称<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_type_name" type="text" id="ass_type_name" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
					<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">折旧年限：</td>
				<td align="left" class="l-table-edit-td"><input
					name="manage_depre_amount" type="text" id="manage_depre_amount" ltype="text"
					 /></td>
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
				<td align="right" 
					style="padding-left: 20px;">资产性质<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_naturs" type="text" id="ass_naturs" ltype="text"/></td>
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
					<input name="is_stop" type="text" id="is_stop"  />
				</td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
