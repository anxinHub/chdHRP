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
	$(function() {
		loadForm(); 
	});
	
	function save() {
		var data = frameElement.dialog.get("data");
		var ParamVo  = [],theme_id;
		 $(data).each(function (i){	
			    theme_id = data[0].demand_id;
				ParamVo.push({
					app_id : data[i].app_id,
		 			group_id : data[i].group_id,
		 		    hos_id: data[i].hos_id,
		 		    app_label : $("#app_label").val(),
					app_review : $("#app_review").val(),
					app_note : $("#app_note").val()
				});
     	});
		
		$.post("updateAppLabelBatch.do?isCheck=false&&tab_code=HR_RECRUIT_APP", {ParamVo : JSON.stringify(ParamVo)}, function(responseData) {
			
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("保存成功");
				$("input[name='app_label']").val('');
				$("input[name='app_review']").val('');
				$("input[name='app_note']").val('');
				
				parent.queryCard(theme_id);
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

	function saveResumeLabel() {
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
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">简历标签：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="app_label" name="app_label" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>       
                </td>
                <td align="left"></td>
			</tr>
			 <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">简历评语：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="app_review" name="app_review" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
                 
                </td>
                <td align="left"></td>
             </tr>
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">简历备注：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="app_note" name="app_note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
                 
                </td>
                <td align="left"></td>
               </tr>
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="saveResumeLabel();" /></td>
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
