<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script src="<%=path%>/lib/hrpjs/acc/accLederCheck.js"
	type="text/javascript"></script>
	<script type="text/javascript">
	var liger_id = ${leger_id};
		$(function(){
			liger.get("subj_code").setValue('${subj_id}'+'.'+'${subj_code}');
	        liger.get("subj_code").setText('${subj_code}'+' '+'${subj_name}');
		})
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" ltype="text" validate="{required:true,maxlength:20}"  value="${bal_os }"  onchange="collEndOs(this)"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计借方：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_od" type="text" id="sum_od" ltype="text" validate="{required:true,maxlength:20}" value="${sum_od }"  onchange="collEndOs(this)"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计贷方：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_oc" type="text" id="sum_oc" ltype="text" validate="{required:true,maxlength:20}"  value="${sum_oc }"  onchange="collEndOs(this)"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="end_os" type="text" id="end_os" ltype="text" value="${end_os}"   validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>

	<div id="maingrid"></div>

</body>
</html>
