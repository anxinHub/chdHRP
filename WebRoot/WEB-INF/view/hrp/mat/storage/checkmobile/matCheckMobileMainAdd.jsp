<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
 </head>
 <body>

	<form name="form" method="post"  id="form" class="liger-form">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
	        <tr>
	            <td align="right" class="l-table-edit-td"  ><span style="color:red">*</span>盘点日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="add_check_date" type="text" id="add_check_date" ltype="text" validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
			</tr>
			<tr>
	            <td align="right" class="l-table-edit-td" >仓库：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="add_store_id" type="text" id="add_store_id" ltype="text" validate="{required:true,maxlength:20}" />
	            </td>
			</tr>
		</table>
	</form>

	
	
	<script>
		$("#add_check_date").ligerTextBox({width : 180});
		autocompleteAsync(".add_store_id", "../../queryMatStoreDictDate.do?isCheck=false", "id","text", true, true, {read_or_write:1}, false, false, '180');
	</script>
 </body>
</html>

 
 
 
 
 