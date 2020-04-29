<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
$(function() {
	autodate("#account_date", "yyyy-mm-dd", "new")
});
 function account(){
	 if($("#account_date").val() == ""){
		 $.ligerDialog.error('记账日期不能为空');
		 return;
	 }
	 var formPara = {
			 account_date:$("#account_date").val()
	 };
	 ajaxJsonObjectByUrl("accountBudgBorrApply.do", formPara,function(responseData) {
		if (responseData.state == "true") {
						
		}
	 });
	 
 }

</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<font color="red">记账后，借款初始数据将不允许修改，请确认期初借款数据无误后再记账</font>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		 <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记账日期：</td>
            <td align="left" class="l-table-edit-td"><input name="account_date" class="Wdate" type="text" id="account_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left" class="l-table-edit-td"><input class="l-button l-button-test" type="button"  value="记账" onclick="account();"/></td>
            <td align="left"></td>
        </tr> 
	
    </table>
	
</body>
</html>
