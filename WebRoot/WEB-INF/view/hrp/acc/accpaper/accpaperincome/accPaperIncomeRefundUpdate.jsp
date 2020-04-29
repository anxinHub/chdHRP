<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">

	function updateRefund(str){
		
		ajaxJsonObjectByUrl("updateRefundAccPaperIncome.do",str+"&tp_note="+$("#tp_note").val(),function(responseData){
			if(responseData.state == "true"){
				parentFrameUse().query();
				frameElement.dialog.close();
            }
        });
		
	}

</script>
</head>
<body>
<form id="acc_paper_sf">
<table cellpadding="0" align="center" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*签发日期：</td>
            <td align="left" class="l-table-edit-td"><input style="width: 158;height: 27" disabled value="${date}" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
        </tr>
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">*票面金额：</td>
            <td align="left" class="l-table-edit-td"><input class="l-table-edit-td" disabled value="${money}" ltype="text"   /></td>
        </tr>
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
            <td align="left" class="l-table-edit-td"><input class="l-table-edit-td"  id="tp_note" ltype="text"   /></td>
        </tr>
    </table>
</form>
</body>
</html>