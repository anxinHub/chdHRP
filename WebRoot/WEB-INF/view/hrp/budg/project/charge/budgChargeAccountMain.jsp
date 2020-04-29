<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<style>
#mark_date{font-size:16px;color:red}

</style>
<script type="text/javascript">
	var state='${stateMap.state}';
	var year_Month;
	var mark_date;
	$(function (){
	    $("#charge_money").ligerButton({click: charge_money, width:120});

	    $("#mark_date").ligerTextBox({width:160,height:25});
		var date=new Date();
		
		var year=date.getFullYear();
		 
		var month=date.getMonth()+1;
		var day= date.getDate();
		month =(month<10 ? "0"+month:month); 
		 
		year_Month = (year.toString()+"-"+month.toString()+"-"+day.toString());
		
		if (state){
			$("#mark_date").val('${stateMap.mark_date}'.split(" ")[0]);
			$("#charge_money").attr("disabled", true);
		}else{
			$("#mark_date").val(year_Month);
			$("#charge_money").attr("disabled", false);
		}
	});
	    
	//结转
 	function charge_money(){  
		$.ligerDialog.confirm('您确定要记账吗?', function (yes){	
			ajaxJsonObjectByUrl("ChargeAccount.do?isCheck=false",{"mark_date":year_Month},function (responseData){
				if(responseData.state == "true"){ 
					$("#mark_date").val(year_Month);
					$("#charge_money").attr("disabled", true);
				}
			}); 
		});
	}
</script>
</head>
<body>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="#FF6600">${stateMap.msg}</font></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">记账日期：</td>
            <td align="left" class="l-table-edit-td"><input name="mark_date" type="text" id="mark_date" ltype="text" validate="{required:true,maxlength:20}"  disabled/></td>
            <td align="left"></td>
        </tr> 
        <tr>
	        <td align="right" class="l-table-edit-td" >
				<input type="button" id="charge_money" value="记账"/>
		    </td>
        </tr>
    </table>
</body>
</html>