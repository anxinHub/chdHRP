<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	$(function() {
		$("#layout1").ligerLayout({ leftWidth: 150});

		$("#begin_date").ligerTextBox({width:60});
		
		$("#but_start").ligerButton({click: start, width:120});
	});
	
	function start(){
		$.ligerDialog.confirm('确定继承?', function (yes){
			if(yes){
					//不需要判断出纳和工资已结账 
					var paras={
							acc_year : $("#begin_date").val()
					};
					ajaxJsonObjectByUrl("extendAccTemplate.do",paras,function (responseData){
						if(responseData.state == "true"){ 
							
						}
					}); 
			}
		});
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 100px;margin-left: 400px">
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">当前年度：</td>
            <td align="left" class="l-table-edit-td"><input class="Wdate"
				name="begin_date" type="text" id="begin_date"
				ltype="text" validate="{required:true,maxlength:8}"  value="${acc_year}"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
				</td>
				<td align="left" class="l-table-edit-td" >
					<input type="button" id="but_start" value="继承模板（J）"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" colspan="10">
					<span style="color: red;font-size: 25px">备注：继承期末处理模板以及自动凭证模板</span>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
