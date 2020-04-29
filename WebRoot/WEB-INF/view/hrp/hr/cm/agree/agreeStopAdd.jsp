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
	  var empdata = parentFrameUse().grid.getCheckedRows();
	 //console.log(2,dialogData);
	$(function() {
		loadDict()//加载下拉框
		loadForm();
		 $("#trial_date").ligerTextBox({width:180});
	});
	 
	function save() {
		
		   var formPara={
				   force_date:$("#force_date").val(),
				   force_note:$("#force_note").val(),
				   agree_id: empdata[0].agree_id,
				   agree_code: empdata[0].agree_code,
				   agree_type: empdata[0].agree_type,
				   agree_content: empdata[0].agree_content,
				   agree_penalty: empdata[0].agree_penalty,
				   start_date:empdata[0].start_date,
				   end_date: empdata[0].end_date,
				   status: empdata[0].status,
				   agree_file: empdata[0].agree_file,
				   agree_image: empdata[0].agree_image,
				   status:'4',
	            	 tab_code: 'HR_AGREE'
            };
    	   ajaxJsonObjectByUrl("updateAgree.do?isCheck=false", formPara, function(
					responseData) {
    		   if(responseData.state=="true"){
    				
    			   parentFrameUse().query();
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
	
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
             <tr>
               
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">终止时间：</td>
                <td align="left" class="l-table-edit-td"><input name="force_date" type="text" id="force_date" 
                onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" class="Wdate" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">终止原因：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="force_note" name="force_note"></textarea>
                </td>
                <td align="left"></td>
            </tr> 

		</table>
	</form>

</body>
</html>
