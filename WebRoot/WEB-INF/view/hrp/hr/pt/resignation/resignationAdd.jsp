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
	 var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function() {
		loadDict()//加载下拉框
		loadForm();
		 $("#emp_code").ligerTextBox({width:180,disabled:true});
		 $("#title").ligerTextBox({width:180,disabled:true});
		 $("#hostime").ligerTextBox({width:180,disabled:true});
		 $("#dept_id").ligerTextBox({width:180,disabled:true});
	});

	function save() {
		
		   var formPara={
				  
	                  emp_id: liger.get("emp_id").getValue(),
	                  resignation_date: $("#resignation_date").val(),
	                  resignation_reason: liger.get("resignation_reason").getValue(),
	                 handover_user: liger.get("handover_user").getValue(),
	                 handover_note:$("#handover_note").val(),
	                 state:'0',
	            	tab_code: 'HR_RESIGNATION'
            };
    	   ajaxJsonObjectByUrl("addResignation.do", formPara, function(
					responseData) {
    		   if(responseData.state=="true"){
    				
                    parent.query();
                    dialog.close();
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

	function saveData() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		   //字典下拉框
		 autocomplete("#resignation_reason","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_RES_REASON","id","text",true,true);  
		   //字典下拉框
		 $("#emp_id").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 160,
		      	onSelected: function (value) {
		      		$.post("queryEmpInfo.do?isCheck=false", {
						'tab_code' : 'HOS_EMP',
						'emp_id':value,
						 'rjt':'json'
					
					},function(responseData){
		       		 $.each(responseData.Rows,function(i,v){
						
							$("#emp_code").val(v.EMP_CODE);
				   			$("#title").val(v.TITLE);
				   			$("#hostime").val(v.HOSTIME);
				   			$("#dept_id").val(v.DEPT_ID_NAME);
				   			$("#permit").val(v.PERMIT);
				   			$("#technique").val(v.TECHNIQUE);
				   			$("#age").val(v.AGE);
				   			$("#kind_code").val(v.KIND_CODE_NAME);
				   			$("#interpersonal").val(v.INTERPERSONAL);
				   		 user_hide_data = {
                               
                                 dept_id: v.DEPT_ID || '',
                                 dept_no: v.DEPT_NO || ''
                             };
					 })
		       		})
		      	}
			 });	
		   autocomplete("#handover_user","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true);    
		 /*  $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		})  */
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">员工姓名<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_id" type="text" id="emp_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_code" type="text" id="emp_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			
			</tr>
			  <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_id" type="text"  id="dept_id" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
			 <tr>
			 <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">职位：</td>
				<td align="left" class="l-table-edit-td"><input
					name="title" type="text" id="title" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
				</tr>
				<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">入司时间：</td>
                <td align="left" class="l-table-edit-td"><input name="hostime" type="text"   id="hostime" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">离职日期：</td>
                <td align="left" class="l-table-edit-td"><input name="resignation_date" type="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
                <td align="left"></td>
                </tr>
                
                   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">离职原因：</td>
                <td align="left" class="l-table-edit-td"><input name="resignation_reason" type="text"  id="resignation_reason" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
		  <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">交接人：</td>
                <td align="left" class="l-table-edit-td"><input name="handover_user" type="text"  id="handover_user" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
                	 <textarea rows="3" cols="30" id="handover_note" name="handover_note"></textarea> 
                </td>
                <td align="left"></td>
            </tr> 

		</table>
	</form>

</body>
</html>
