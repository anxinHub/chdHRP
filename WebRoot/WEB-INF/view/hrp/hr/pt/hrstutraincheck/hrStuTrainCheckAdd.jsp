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
	    var user_hide_data = {};
	$(function() {
		loadDict()//加载下拉框
		loadForm();		 		 
		 
	});

	function save() {
		
		   var formPara={		
				   dept_id: liger.get("dept_id").getValue().split('@')[0],
				   dept_no: liger.get("dept_id").getValue().split('@')[1],                  
	                  emp_id: liger.get("emp_id").getValue(),
	                  major_stu : $("#major_stu").val(),
	                  major_unit : $("#major_unit").val(),
	                  budget_id : $("#budget_id").val(),
	                  budget_pro : $("#budget_pro").val(),
	                  stu_time : $("#stu_time").val(),
	                  apply_amount : $("#apply_amount").val(),
	                  stu_target:$("#stu_target").val(),
	                 state:'0',
	            	tab_code: 'HR_STUTRAIN_CHECK',	            	
            };
		  
    	   ajaxJsonObjectByUrl("addStutrainCheck.do", formPara, function(
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
		 autocomplete("#dept_id","../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT","id","text",true,true);  
		 autocomplete("#emp_id","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true);    
	 
	}
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form" method="post" id="form">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">员工姓名<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_id" type="text" id="emp_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_id" type="text" id="dept_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			
			</tr>
			
			 <tr>
			 <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">进修专业：</td>
				<td align="left" class="l-table-edit-td"><input
					name="major_stu" type="text" id="major_stu" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">进修单位：</td>
                <td align="left" class="l-table-edit-td"><input name="major_unit" type="text"   id="major_unit" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
                   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算代码：</td>
                <td align="left" class="l-table-edit-td"><input name="budget_id" type="text"  id="budget_id" ltype="text"  /></td>
                <td align="left"></td>
                   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算项目：</td>
                <td align="left" class="l-table-edit-td"><input name="budget_pro" type="text"  id="budget_pro" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">进修时间：</td>
                <td align="left" class="l-table-edit-td"><input name="stu_time" type="text"  class="Wdate" id="stu_time" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
                              
            </tr>
                   <tr>			 
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">申请金额：</td>
                <td align="left" class="l-table-edit-td"><input name="apply_amount" type="text"   id="apply_amount" ltype="text"  /></td>
               
                </tr>
             
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">进修目标分析：</td>
                 <td class="ipt" class="l-table-edit-td">
                            <textarea rows="5" cols="30" id="stu_target" name="stu_target"></textarea>
                        </td>
            
            </tr> 

		</table>
	</form>

</body>
</html>
