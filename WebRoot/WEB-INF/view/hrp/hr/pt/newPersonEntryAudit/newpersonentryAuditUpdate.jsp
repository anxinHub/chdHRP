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
	var effective_date;
	var entry_date;
	
	$(function(){
		//加载部门 下拉
		loadDict();
		// 渲染加载表单界面
		loadForm();
	});
	
	//加载部门下拉框,同时初始化原始填入值
	function loadDict(){
		$("#dept_name").ligerComboBox({
			url: '../../baseSelect.do?isCheck=false&field_tab_code=SYS_DEPT',
			valueField: 'id',
			textField: 'text',
			selectBoxWidth:180,
			autocomplete: true,
			width: 180,
			onSuccess: function(data){
				this.setValue("${dept_id}" +  "@" + "${dept_no}");
			}
		});
		// 下面不知哪个对应设置初始值
		//autocomplete("#dept_name", "../../baseSelect.do?isCheck=false&field_tab_code=SYS_DEPT", "id", "text", true, true,"",false, "", 180);
	}
	//渲染加载界面
	function loadForm(){
		//关于 非空 为空的提示  有效输入控制
		$.metadata.setType('attr','validate');
		// 每个空 点击、输入后，输入执行一次
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				// 通过 类标签 来定位 控制必填提示
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
		//渲染界面
		$("form").ligerForm();
	}
	
	// 修改保存
	function saveDate(){
		// 先判断必填项 是不是都有效
		if(!$("form").valid()){
			return; //无效 退出
		}
		var formPara = {
				emp_id:$("#emp_id").val(),
				emp_name:$("#emp_name").val(),
				unit_name:$("#unit_name").val(),
				entry_date:$("#entry_date").val().split(" ")[0],
				effective_date:$("#effective_date").val().split(" ")[0],
				//dept_name:$("#dept_name").val(),
				dept_id:liger.get("dept_name").getValue().split('@')[0],
				dept_no:liger.get("dept_name").getValue().split('@')[1],
				position_name:$("#position_name").val(),
				level_code:$("#level_code").val(),
				trial_salary:$("#trial_salary").val(),
				formal_employ_salary:$("#formal_employ_salary").val(),
				upload_contract_file:$("#upload_contract_file").val(),
				tab_code:"HR_NEWPERSONENTRY_AUDIT"
		};
		
		ajaxJsonObjectByUrl("updateNewpersonentryAudit.do?isCheck=false", formPara, function(responseData){
			if(responseData.state == "true"){
				parent.query();
			}
			else{
				
			}
		});
	}
</script>
</head>
<body>
<div id="pageloading" class="1-loading" style="display:none"></div>
	<form name="form1" method="post" id="form1" style="display:  blok">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>工号:
				</td>
				<td align="left" class="l-table-edit-td">
					<input value="${emp_id}" name="emp_id" id="emp_id" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>姓名:
				</td>
				<td align="right" class="l-table-edit-td">
					<input value="${emp_name}" name="emp_name" id="emp_name" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>单位:
				</td>
				<td align="left" class="l-table-edit-td">
					<input value="${unit_name}" name="unit_name" id="unit_name" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>进公司日期:
				</td>
				<td align="right" class="l-table-edit-td">
					<input value="${entry_date}" name="entry_date" id="entry_date" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}"  />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>生效日期:
				</td>
				<td align="right" class="l-table-edit-td">
					<input value="${effective_date}" name="effective_date" id="effective_date" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>部门:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="dept_name" id="dept_name" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>职位:
				</td>
				<td align="left" class="l-table-edit-td">
					<input value="${position_name}" name="position_name" id="position_name" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>级别:
				</td>
				<td align="right" class="l-table-edit-td">
					<input value="${level_code}" name="level_code" id="level_code" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>试用期薪资:
				</td>
				<td align="left" class="l-table-edit-td">
					<input value="${trial_salary}" name="trial_salary" id="trial_salary" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>转正后薪资:
				</td>
				<td align="right" class="l-table-edit-td">
					<input value="${formal_employ_salary}" name="formal_employ_salary" id="formal_employ_salary" type="text" ltype="text" validate = "{required: true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr>
<%-- 			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>上传合同附件:
				</td>
				<td align="left" class="l-table-edit-td">
					<input value="${upload_contract_file}" name="upload_contract_file" id="upload_contract_file" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr> --%>
		</table>
	</form>
</body>
</html>