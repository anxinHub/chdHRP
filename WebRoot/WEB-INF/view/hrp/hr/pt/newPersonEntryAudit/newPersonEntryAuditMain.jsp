<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
	$(function(){
		$("#audit_options").ligerTextBox({width:150,height:80});
		// 加载 下拉结果
		loadSelect();
	});
	
	// 结果
	function loadSelect(){
		$("#audit_result").ligerComboBox({
			data:[
				{ id: '0', text: '拒绝' },
				{ id: '1', text: '同意' }
			],
			valueField: 'id',
			textField: 'text',
			cancelable: true,
			width: 150
		});
	}
	
	// 保存审核
	function saveAudit(){
		if(liger.get("audit_result").getValue()=="" ||liger.get("audit_result").getValue()==null){
			$.ligerDialog.warn("您必须选择审核的结果！");
			return;
		}
		var url="";
		var ParamVo = [];
		var data = frameElement.dialog.get("data");
		var flag = data[0].audit_flag;
		if(flag == "emp_audit"){
			$(data).each(function(i){
				ParamVo.push({
					emp_id: data[i].emp_id,
					emp_name: data[i].emp_name,
					employ_dept_audit: liger.get("audit_result").getValue(),
					employ_dept_audit_options: $("#audit_options").val(),
					employ_dept_audit_date: data[i].employ_dept_audit_date,
					tab_code: "HR_NEWPERSONENTRY_AUDIT"
				});
			});
			url="updateNewpersonentrAuditEmpDep";
		}
		else if(flag == "Vice_Dean_audit"){
			$(data).each(function(i){
				ParamVo.push({
					emp_id: data[i].emp_id,
					emp_name: data[i].emp_name,
					vice_dean_audit: liger.get("audit_result").getValue(),
					vice_dean_audit_options: $("#audit_options").val(),
					vice_dean_audit_date: data[i].vice_dean_audit_date,
					tab_code: "HR_NEWPERSONENTRY_AUDIT"
				});
			});
			url="updateNewpersonentryAuditViceDean";
		}
		else if(flag == "HR_audit"){
			$(data).each(function(i){
				ParamVo.push({
					emp_id: data[i].emp_id,
					emp_name: data[i].emp_name,
					hr_audit:liger.get("audit_result").getValue(),
					hr_audit_options: $("#audit_options").val(),
					hr_audit_date: data[i].hr_audit_date,
					tab_code: "HR_NEWPERSONENTRY_AUDIT"
				});
			});
			url="updateNerpersonentryAuditHR";
		}
		else if(flag == "Dean_audit"){
			$(data).each(function(i){
				ParamVo.push({
					emp_id: data[i].emp_id,
					emp_name: data[i].emp_name,
					dean_audit: liger.get("audit_result").getValue(),
					dean_audit_options: $("#audit_options").val(),
					dean_audit_date: data[i].dean_audit_date,
					tab_code: "HR_NEWPERSONENTRY_AUDIT"
				});
			});
			url="updateNewpersonentryAuditDean";
		}
		if(url!=""){
			ajaxJsonObjectByUrl(url + "Batch.do?isCheck=false",{ParamVo: JSON.stringify(ParamVo)},
				function(responseData){
					if(responseData.state == "true"){
						parent.$.ligerDialog.success("审核成功");
						parent.query();
						frameElement.dialog.close();
					}
					else{
						parent.$.ligerDialog.warn(responseData.msg);
					}
				}	
			);
		}
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div id="audit_table" align="center">
		<table>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>审核结果：
				</td>
				<td align="left" class="l-table-edit-td" >
					<input name="audit_result" id="audit_result" type="text" ltype="text" validate="{ required: true, maxlength: 20}" /> 
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					审核意见：
				</td>
				<td align="left" class="l-table-edit-td">
					<textarea name="audit_options" id="audit_options" rows="3" cols="30" class="wishContent" placeholder="允许100字以内意见" maxlength="100" style="resize: none"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>