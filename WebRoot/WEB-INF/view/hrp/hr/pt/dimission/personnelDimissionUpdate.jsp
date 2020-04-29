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
		loadDict()//加载下拉框
		loadForm(); 
	
// 		$("#aft_increase").ligerTextBox({width:180,disabled:true});
	});
	
	
	function save() {
		
		if(isnull($("#station_name").val())){
			parent.$.ligerDialog.warn('岗位不能为空');
			return;
		}
		if(isnull($("#dept_name").val())){
			parent.$.ligerDialog.warn('部门不能为空');
			return;
		}
		
		var formPara = {
	
				dim_app_date :  $("#dim_app_date").val(),
				station_name :  $("#station_name").val(),
				dim_cert_date : $("#dim_cert_date").val(),
				dim_reason : $("#dim_reason").val(),
				dim_id :  ${dim_id},
		};
		
		
		$.post("updatePersonnelDimission.do?tab_code=HR_PERSONNEL_DIMISSION", formPara, function(responseData) {
			
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("修改成功");
				$("input[name='dim_emp_select']").val('');
				$("input[name='station_name']").val('');
				$("input[name='dept_name']").val('');
				$("input[name='entity_time']").val('');
				$("input[name='dim_app_date']").val('');
				$("input[name='dim_cert_date']").val('');
				$("input[name='dim_reason']").val('');
				
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

	function saveDemand() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		$("#dim_emp_select").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 160,
	      	onSuccess: function (data) {
					this.setValue("${emp_id}");
	      	}
		 });
		
		$("#dept_name").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180,
	      	disabled:true,
	      	onSuccess: function (data) {
				this.setValue("${dept_id}");
      		}
		 });
		
		 $("#station_name").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 180,
		      	disabled:true,
		      	onSuccess: function (data) {
					this.setValue("${station_code}");
	      		}

			 });

		 $("#entity_time").ligerTextBox({width : 180,disabled:true});
		 
		 $("#dim_app_date").ligerTextBox({width : 180});
		 $("#dim_cert_date").ligerTextBox({width : 180});
		
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
					style="padding-left: 20px;">姓名<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="dim_emp_select" type="text" id="dim_emp_select" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">岗位<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="station_name" type="text" id="station_name" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">所属部门：
				</td>
				<td align="left" class="l-table-edit-td"><input 
					name="dept_name" type="text" id="dept_name" ltype="text" 
					validate="{required:true,maxlength:100}"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">入职时间：
				</td>
				<td align="left" class="l-table-edit-td" ><input id="entity_time" name="entity_time" class="Wdate" value="${entity_time}"  type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">申请离职日期：
				</td>
				<td align="left" class="l-table-edit-td" ><input id="dim_app_date" name="dim_app_date" class="Wdate" value="${dim_app_date}"  type="text"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:2px!important;" width="60">确定离职日期：</td>
            	<td align="left" class="l-table-edit-td" ><input name="dim_cert_date" class="Wdate"  type="text" id="dim_cert_date"  value="${dim_cert_date}"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
		   </tr>
		   <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">离职申请及其原由：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="50" id="dim_reason" name="dim_reason" placeholder="请输入不超过100个字" maxlength="100">${dim_reason}</textarea>
                </td>
                <td align="left"></td>
              </tr> 
		</table>
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="saveDemand();" /></td>
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
