<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	$(function(){
		//加载部门下拉
		loadDict();
		//加载表单，渲染界面
		loadForm();
	});
	
	//加载 部门下拉
	function loadDict(){
/*  		$("#dept_name").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&field_tab_code=SYS_DEPT',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180
		 });  */
 		autocomplete("#dept_name", "../../baseSelect.do?isCheck=false&field_tab_code=SYS_DEPT", "id", "text", true, true,"",false, "", 180);
	}
	
	//加载表单，渲染界面
	function loadForm(){
		// 控制必填
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
		// 加载界面
		$("form").ligerForm();
	}
	
 	function saveDate() {
 		// 界面数据都有效后，执行保存
		if ($("form").valid()) {
			save();
		}
	} 
	
	//保存
	function save(){
		var formPara={
			/* emp_id:liger.get("emp_id").getValue(),
			emp_name:liger.get("emp_name").getValue(),
			unti_name:liger.get("unti_name").getValue(),
			entry_date:liger.get("entry_date").getValue(),
			effective_date:liger.get("effective_date").getValue(),
			dept_name:liger.get("dept_name").getValue(),
			position_name:liger.get("position_name").getValue(),
			level_code:liger.get("level_code").getValue(),
			trial_salary:liger.get("trial_salary").getValue(),
			formal_employ_salary:liger.get("formal_employ_salary").getValue(),
			upload_contract_file:liger.get("upload_contract_file").getValue() */
			emp_id:liger.get("emp_id").getValue(),
			emp_name:$("#emp_name").val(),
			unit_name:$("#unit_name").val(),
			entry_date:$("#entry_date").val(),
			effective_date:$("#effective_date").val(),
			//dept_name:$("#dept_name").val(),
			dept_id:liger.get("dept_name").getValue().split('@')[0],
			dept_no:liger.get("dept_name").getValue().split('@')[1],
			position_name:$("#position_name").val(),
			level_code:$("#level_code").val(),
			trial_salary:$("#trial_salary").val(),
			formal_employ_salary:$("#formal_employ_salary").val(),
			upload_contract_file:$("#upload_contract_file").val()
		};
		/* console.log(formPara);//控制台输出参数日志 */
		
		//保存新增
		ajaxJsonObjectByUrl("addNewpersonentryAudit.do?isCheck=false&tab_code=HR_NEWPERSONENTRY_AUDIT",formPara, function(responseData){
			if(responseData.state=="true"){
				parent.$.ligerDialog.success("添加成功");
				//$("input").val();
				parent.query();
				frameElement.dialog.close();
			}
			else{
				parent.$.ligerDialog.warn(responseData.msg);
			}
		});
	}
	
	//取消
	function this_close(){
		frameElement.dialog.close();
	}
	
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display:none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>工号:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="emp_id" id="emp_id" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>姓名:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="emp_name" id="emp_name" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>单位:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="unit_name" id="unit_name" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>进公司日期:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="entry_date" id="entry_date" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>生效日期:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="effective_date" id="effective_date" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>部门:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="dept_name" id="dept_name" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>职位:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="position_name" id="position_name" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>级别:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="level_code" id="level_code" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>试用期薪资:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="trial_salary" id="trial_salary" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>转正后薪资:
				</td>
				<td align="right" class="l-table-edit-td">
					<input name="formal_employ_salary" id="formal_employ_salary" type="text" ltype="text" validate="{required:true,maxlength:100}"  />
				</td>
				<td align="left"></td>
			</tr>
<!-- 			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<span style="color: red">*</span>上传合同附件:
				</td>
				<td align="left" class="l-table-edit-td">
					<input name="upload_contract_file" id="upload_contract_file" type="text" ltype="text" validate="{required:true, maxlength: 100}" />
				</td>
				<td align="left"></td>
			</tr> -->
		</table>
		<table align="center" cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="center" class="l-table-edit-td" style="padding-left: 20px;">
					<input type="button" class="l-button l-button-test" style="float: right;" value="保存" onclick="saveDate();" />
				</td>
				<td align="center" class="l-table-edit-td" stye="padding-left: 20px;">
					<input type="button" class="l-button l-button-test" style="float: right;" value="取消" onclick="this_close();" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>