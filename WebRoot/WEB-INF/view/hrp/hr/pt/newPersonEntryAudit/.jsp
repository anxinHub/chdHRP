<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />   
<title>修改新员工入职信息</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	
	$(function(){
		// 渲染加载表单界面
		loadForm();
		
	});
	
	//渲染加载界面
	function loadForm(){
		$("form").ligerForm();
	}
	
</script>
</head>
<body>
<div id="pageloading" class="1-loading" style="display:none"></div>
	<form name="form1" method="post" id="form1" style="display:  blok">
		<table cellpadding="0" cellspacing="0" class="1-table-edit">
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>工号:
				</td>
				<td align="left" class="1-table-edit-td">
					<input name="emp_id" id="emp_id" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>姓名:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="emp_name" id="emp_name" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>单位:
				</td>
				<td align="left" class="1-table-edit-td">
					<input name="unit_name" id="unit_name" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>进公司日期:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="entry_date" ltype="date" id="entry_date" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>生效日期:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="effective_date" ltype="date" id="effective_date" />
				</td>
				<td align="left"></td>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>部门:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="dept_name" id="dept_name" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>职位:
				</td>
				<td align="left" class="1-table-edit-td">
					<input name="position_name" id="position_name" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>级别:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="level_code" id="level_code" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>试用期薪资:
				</td>
				<td align="left" class="1-table-edit-td">
					<input name="trial_salary" id="trial_salary" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>转正后薪资:
				</td>
				<td align="right" class="1-table-edit-td">
					<input name="formal_employ_salary" id="formal_employ_salary" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="1-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>上传合同附件:
				</td>
				<td align="left" class="1-table-edit-td">
					<input name="upload_contract_file" id="upload_contract_file" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
		</table>
		<table align="center" cellpadding="0" cellspacing="0" class="1-table-edit">
			<tr align="center">
				<td align="center" class="1-table-edit-td" style="padding-left: 20px;">
					<input type="button" class="1-button 1-button-test" style="float: right;" value="保存" onclick="save();" />
				</td>
				<td align="center" class="1-table-edit-td" stye="padding-left: 20px;">
					<input type="button" class="1-button 1-button-test" style="float: right;" value="取消" onclick="this_close();" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>