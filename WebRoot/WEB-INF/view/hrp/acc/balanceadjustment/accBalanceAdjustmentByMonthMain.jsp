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
 	
	$("#acc_month").ligerTextBox({ disabled:true });
	$("#now_acc_time").ligerTextBox({ disabled:true });
	
	$("#acc_month").ligerTextBox({width:180,height:30});
	$("#now_acc_time").ligerTextBox({width:180,height:30});
	
	$("#daily").ligerButton({width:80});
	$("#fanjie").ligerButton({width:80});
    
 });  
 
	function fanjieClick(){
		 var pre_month = $("#acc_month").val();
		 
		 if(pre_month == '' || pre_month == null || pre_month == undefined){
			 
			 $.ligerDialog.error('结账日期不能为空');
			 return ; 
		 }
		 var acc_year = pre_month.substring(0,4);
		 var acc_month =pre_month.substring(5,7);
			
		 ajaxJsonObjectByUrl("undailyAccBalanceAdjustmentByMonth.do",{"acc_year":acc_year,"acc_month":acc_month},function (responseData){
					if(responseData.state=="true"){
						$('#acc_month').val(responseData.pre_month);
						$('#now_acc_time').val(pre_month);
					}
				}
		 
	);
	
	}
	
	function btnClick(){
	
	var now_acc_time = $('#now_acc_time').val();
	if(now_acc_time == '' || now_acc_time == null || now_acc_time == undefined){
		 $.ligerDialog.error('结账日期不能为空');
		 return ;
	}
	var acc_year = now_acc_time.substring(0,4);
	var acc_month = now_acc_time.substring(5,7);
	
	ajaxJsonObjectByUrl("dailyAccBalanceAdjustmentByMonth.do",{"acc_year":acc_year,"acc_month":acc_month},function (responseData){
		if(responseData.state=="true"){
			$('#now_acc_time').val(responseData.now_month);
			$('#acc_month').val(now_acc_time);
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
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">当前出纳月：</td>
            <td align="left" class="l-table-edit-td"><input name="now_acc_time" type="text" id="now_acc_time" ltype="text" value="${now_time}"  disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button"   type="button" id="daily" value="月结" onclick="btnClick()"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上次结账月：</td>
             <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" ltype="text"  value="${pre_time }"  disabled="disabled"/></td>
             <td align="left" class="l-table-edit-td"><input class="liger-button"   type="button" id="fanjie" value="反结" onclick="fanjieClick()"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>

</body>
</html>
