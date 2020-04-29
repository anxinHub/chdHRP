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
<script src="/CHD-HRP/lib/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadForm(); 
		$("#theme_name").ligerTextBox({
			width : 200
		});
		$("#start_time").ligerTextBox({
			width : 200
		});
		$("#end_time").ligerTextBox({
			width : 200
		});
		$("#user_name").ligerTextBox({
			width : 200
		});
		$("#theme_phone").ligerTextBox({
			width : 200
		});
		$("#theme_email").ligerTextBox({
			width : 200
		});
		$("#theme_address").ligerTextBox({
			width : 200
		});
		$("#describe").ligerTextBox({
			width : 200
		});   
		$("#theme_state").ligerTextBox({
			width : 200
		}); 
	});
	
	function save() {
		if(isnull($("#theme_name").val())){
			parent.$.ligerDialog.warn('招聘主题不能为空');
			return;
		}
		
		if(isnull($("#start_time").val())){
			parent.$.ligerDialog.warn('招聘开始日期不能为空');
			return;
		}
		
		if(isnull($("#end_time").val())){
			parent.$.ligerDialog.warn('招聘结束日期不能为空');
			return;
		}
		if(isnull($("#theme_state").val())){
			parent.$.ligerDialog.warn('招聘主题状态不能为空');
			return;
		}
		
			if(!isnull($("#theme_email").val())){
			var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;		
			if(!reg.test($("#theme_email").val())){					
				parent.$.ligerDialog.warn('邮箱格式不正确');		
				return;
			}
		}
		
		var formPara = {
			
			theme_id : ${theme_id},
			theme_name : $("#theme_name").val(),
			start_time : $("#start_time").val(),
			end_time : $("#end_time").val(),
			user_name : $("#user_name").val(),
			theme_phone : $("#theme_phone").val(),
			user_name : $("#user_name").val(),
			theme_email : $("#theme_email").val(),
			theme_address : $("#theme_address").val(),
			describe :  $("#describe").val(),
			theme_address : $("#theme_address").val(),
			theme_state : $("#theme_state").val(),

		};
		
		
		$.post("updateRecruitTheme.do?tab_code=HR_RECRUIT_THEME", formPara, function(responseData) {
			
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("修改成功");
				$("input[name='theme_name']").val('');
				$("input[name='start_time']").val('');
				$("input[name='end_time']").val('');
				$("input[name='user_name']").val('');
				$("input[name='theme_phone']").val('');
				$("input[name='user_name']").val('');
				$("input[name='theme_email']").val('');
				$("input[name='theme_address']").val('');
				$("input[name='describe']").val('');
				$("input[name='theme_state']").val('');
				
// 				parentframename.query();
				parent.query();
				frameElement.dialog.close();
			}else{
				parent.$.ligerDialog.warn(responseData.msg);
			}
		},"json");
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

	function saveTheme() {
		if ($("form").valid()) {
			save();
		}
	}

	function this_close() {
		frameElement.dialog.close();
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" style="display:blok">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘主题<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input name="theme_name"
					type="text" id="theme_name" ltype="text"  value="${theme_name}"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">开始日期<span style="color: red">*</span>：</td>
            	<td align="left" class="l-table-edit-td" >
            	<input name="start_time" class="Wdate" type="text" id="start_time" value="${start_time}" validate="{required:true}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;" width="60">结束日期<span style="color: red">*</span>：</td>
            	<td align="left" class="l-table-edit-td" ><input name="end_time" class="Wdate" type="text" id="end_time" value="${end_time}" validate="{required:true}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">联系人<span style="color: red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input name="user_name" value="${user_name}"
					type="text" id="user_name" ltype="text" validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">联系电话<span style="color: red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="phone" value="${theme_phone}" type="text" id="theme_phone" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">电子邮件<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="email" type="text" id="theme_email" value="${theme_email}" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">地址：
				</td>
				<td align="left" class="l-table-edit-td"><input value="${theme_address}"
					name="theme_address" type="text" id="theme_address" ltype="text"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">描述：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="describe" type="text" id="describe" ltype="text"  value="${describe}"
					validate="{maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
		   <tr>	
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘状态<span style="color: red">*</span>：
				</td>
				 <td align="left" class="l-table-edit-td">
                	<select id="theme_state" name="theme_state" value="${theme_state}">
                		<option value="01">未发布</option>
                		<option value="02">发布中</option>
                		<option value="03">招聘结束</option>
                	</select>
                </td>
				<td align="left"></td>
				
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="saveTheme();" /></td>
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
