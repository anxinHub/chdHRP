<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">

	$(function() {
		$("#year_month").ligerTextBox({width:180});//autodate("#year_month","yyyyMM");
	});
	
	function save(){
		var param={
			sub_scheme_note:$("#sub_scheme_note").val(),
			year_month:$("#year_month").val()
		};
		
		ajaxJsonObjectByUrl("auditHpmEmpSchemeSeq.do",param,function(responseData){
	 		if(responseData.state=="true"){
	 			parent.query();
	 		}
	 	});
	}

 	function loadForm(){
    	$.metadata.setType("attr", "validate");
    	var v = $("form").validate({
			errorPlacement: function (lable, element){
             	if (element.hasClass("l-textarea")){
                 	element.ligerTip({ content: lable.html(), target: element[0] }); 
             	}else if (element.hasClass("l-text-field")){
                	element.parent().ligerTip({ content: lable.html(), target: element[0] });
             	}else{
                 	lable.appendTo(element.parents("td:first").next("td"));
             	}
         	},
         	success: function (lable){
	       		lable.ligerHideTip();
	        	lable.remove();
         	},
         	submitHandler: function (){
             	$("form .l-text,.l-textarea").ligerHideTip();
         	}
     });
    	
		$("form").ligerForm();
    }  
	function auditAphiEmpSchemeSeq(){
		if($("form").valid()){
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">应用年月：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="year_month" type="text" id="year_month" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案说明：</td>
				<td align="left" class="l-table-edit-td"><textarea rows="7" cols="40" id="sub_scheme_note" name="sub_scheme_note"></textarea></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>
</body>
</html>