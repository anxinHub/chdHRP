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
		
	});

	function save() {
		
		   var formPara={
				  
	                  trial_dept: liger.get("trial_dept").getValue().split('@')[0],
	                  trial_dept_no: liger.get("trial_dept").getValue().split('@')[1],
	                  emp_id: liger.get("emp_id").getValue(),
	                  trial_beg_date: $("#trial_beg_date").val(),
					   trial_end_date : $("#trial_end_date").val(),
	                 theory_achievement: $("#theory_achievement").val(),
	                 vocational_vocational: $("#vocational_vocational").val(),
	                 subjective_attitude: $("#subjective_attitude").val(),
	                 comprehensive: $("#comprehensive").val(),
	                 teacher: liger.get("teacher").getValue(),
	                 note:$("#note").val(),
	                 state:'0',
	            	tab_code: 'HR_REGULAR'
            };
    	   ajaxJsonObjectByUrl("addRegular.do", formPara, function(
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
		 autocomplete("#trial_dept","../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT","id","text",true,true);  
		 autocomplete("#emp_id","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true);    
		 autocomplete("#teacher","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true);    
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
					style="padding-left: 20px;">试用科室<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="trial_dept" type="text" id="trial_dept" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">员工姓名<span style="color:red">*</span>：</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_id" type="text" id="emp_id" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">试用期开始时间：</td>
                <td align="left" class="l-table-edit-td"><input name="trial_beg_date" type="text" class="Wdate"  id="trial_beg_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
                <td align="left"></td>
                </tr>
                   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">试用期结束时间：</td>
                <td align="left" class="l-table-edit-td"><input name="trial_end_date" type="text"  class="Wdate" id="trial_end_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
                <td align="left"></td>
                </tr>
                   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">理论考核：</td>
                <td align="left" class="l-table-edit-td"><input name="theory_achievement" type="text"  id="theory_achievement" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务考核：</td>
                <td align="left" class="l-table-edit-td"><input name="vocational_vocational" type="text"  id="vocational_vocational" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主观态度：</td>
                <td align="left" class="l-table-edit-td"><input name="subjective_attitude" type="text"  id="subjective_attitude" ltype="text"  /></td>
                <td align="left"></td>
                </tr>
			 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">综合能力：</td>
                <td align="left" class="l-table-edit-td"><input name="comprehensive" type="text"  id="comprehensive" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">带教老师：</td>
                <td align="left" class="l-table-edit-td"><input name="teacher" type="text"  id="teacher" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
                	 <textarea rows="3" cols="30" id="note" name="note"></textarea> 
                </td>
                <td align="left"></td>
            </tr> 

		</table>
	</form>

</body>
</html>
