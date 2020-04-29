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
 	
	$("#budg_year").ligerTextBox({ disabled:true });
	$("#wait_year").ligerTextBox({ disabled:true });
	
	$("#budg_year").ligerTextBox({width:180,height:30});
	$("#wait_year").ligerTextBox({width:180,height:30});
	
	$("#daily").ligerButton({width:80});
	$("#fanjie").ligerButton({width:80});
    
 });  
 function query(){
	 
 }
function fanjieClick(){
	
	 var budg_year = $("#budg_year").val();
	 
	 if(budg_year==""){
		 
		 $.ligerDialog.error('没有结转不能反结');
		 
		 return;
		 
	 }
	 
	 var formPara={
			 
			 year:budg_year,
			 is_carried:0
	 };
	
	 ajaxJsonObjectByUrl("reCarryBudgProjCarry.do",formPara,function (responseData){
		 if(responseData.state=="true"){
			
			 
			 $('#budg_year').val(responseData.date.split(",")[0]);
			 $('#wait_year').val(responseData.date.split(",")[1]);
			 
		 
		}
 	});
	
}
 
 function btnClick(){
	 //待结转年度
	 var awaitYear = $("#wait_year").val();
	 
	 var formPara={
				 
				 year:awaitYear,	
				 is_carried:1
				 
		 };
	 ajaxJsonObjectByUrl("carryBudgProjCarry.do",formPara,function (responseData){
 		
		 if(responseData.state=="true"){
				
				 $('#budg_year').val(responseData.date);
				 $('#wait_year').val(Number(responseData.date)+1);
			 
			 
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">待结转年度：</td>
            <td align="left" class="l-table-edit-td"><input name="wait_year" type="text" id="wait_year" ltype="text"  value="${year}" disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button" type="button"  id="fanjie" value="结转" onclick="btnClick()"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">已结转年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" value="${yet_year }" ltype="text" disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button" type="button" id="daily" value="反结" onclick="fanjieClick()"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div style="margin-left: 220px;font-weight: bold;color: red;font-size:small; ">说明：结转后本年度数据不允许修改；反结取消上次结转。</div>
	<div id="maingrid"></div>

</body>
</html>
