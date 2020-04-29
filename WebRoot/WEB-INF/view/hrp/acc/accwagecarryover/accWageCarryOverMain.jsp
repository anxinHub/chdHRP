<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

$(function (){
 	
	$("#acc_year").ligerTextBox({ disabled:true });
	$("#acc_time").ligerTextBox({ disabled:true });
	
	$("#acc_year").ligerTextBox({width:180,height:30});
	$("#acc_time").ligerTextBox({width:180,height:30});
	
	$("#daily").ligerButton({width:80});
	$("#fanjie").ligerButton({width:80});
    
 });  
 
function fanjieClick(){
	
	 var acc_year = $("#acc_year").val();
	 
	 if(acc_year==""){
		 
		 $.ligerDialog.error('没有结转不能反结');
		 
		 return;
		 
	 }
	 
	 var formPara={
			 
			 year_month:acc_year,
			 
			 wage_flag:0
	 };
	
	 ajaxJsonObjectByUrl("upAccWageCarryOver.do",formPara,function (responseData){
 		
		 if(responseData.state=="true"){
			 
 			if(responseData.state=="true"){
 				
 				if(responseData.date=="null"){
 					 
 					 $('#acc_year').val("");
 					 
 				 }else{
 					 
 					 $('#acc_year').val(responseData.date);
 					 
 				 }
 	 			
 	 			$('#acc_time').val(acc_year);
 	 			
 	 		}
 		}
 	});
	
}
 
 function btnClick(){
	 
	 var acc_time = $("#acc_time").val();
	 
	 var formPara={
				 
				 year_month:acc_time,
				 
				 wage_flag:1
				 
		 };
	 ajaxJsonObjectByUrl("updateAccWageCarryOver.do",formPara,function (responseData){
 		
		 if(responseData.state=="true"){
 			
			 if(responseData.date=="null"){
				 
				 $('#acc_time').val("");
				 
			 }else{
				 
				 $('#acc_time').val(responseData.date);
				 
			 }
			 
			 $('#acc_year').val(acc_time);
			 
 		}
 	});
 }
 
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin: 100px 100px 20px 200px;font-size:large;font-weight: bold;">
		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">上次年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text" value="${acc_year}"  disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button" type="button" id="daily" value="反结" onclick="fanjieClick()"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">当前年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text"  value="${acc_time }" disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button" type="button"  id="fanjie" value="结转" onclick="btnClick()"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div style="margin-left: 220px;font-weight: bold;color: red;font-size:small; ">说明：结转后本月工资数据不允许修改；反结取消上次结转。</div>
	<div id="maingrid"></div>

</body>
</html>
