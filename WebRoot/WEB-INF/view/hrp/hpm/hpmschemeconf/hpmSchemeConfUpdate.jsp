<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
$(function(){
	
	autocomplete("#scheme_seq_no","../querySchemeSeq.do?isCheck=false", "id", "text", true,true);
	
	loadForm();

	liger.get("scheme_seq_no").setValue("${scheme_seq_no}");
	liger.get("scheme_seq_no").setText("${scheme_seq_no}");	
	
	$("#acct_year").ligerTextBox({ disabled: true });
	$("#acct_month").ligerTextBox({ disabled: true });
	
});
function save(){
	var param={
			acct_year:$("#acct_year").val(),
			acct_month:$("#acct_month").val(),
			scheme_seq_no:$("#scheme_seq_no").val()
	};
	ajaxJsonObjectByUrl("updateHpmSchemeConf.do",param,function(responseData){
 		if(responseData.state=="true"){
            parent.query();
          
        }
 		
 	});
}
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }  
	function updateItem(){
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
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_year" class="Wdate" type="text" id="acct_year" ltype="text" value="${acct_year }"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" class="Wdate" type="text" id="acct_month" ltype="text" value="${acct_month }"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案序号：</td>
				<td align="left" class="l-table-edit-td"><input name="scheme_seq_no" type="text" id="scheme_seq_no" ltype="text" /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>