<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript"></script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <form name="form1" method="post"  id="form1" >
	     <table cellpadding="0" cellspacing="0" class="l-table-edit" style=" background:#F0F0F0 ;width:100%;font-size:16px" >
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>文档类别编码<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" disabled="disabled" id="type_code" value="${type_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>文档编号<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="file_code" type="text" id="file_code" disabled="disabled" value="${file_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>文档名称<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="file_name" type="text" id="file_name" disabled="disabled" ltype="text" value="${file_name}" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>存放位置<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="at_local" type="text" id="at_local" value="${at_local}" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" />
	            </td>
	         </tr> 
			<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>管理科室ID:<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" disabled="disabled" value="${dept_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>管理员:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="mana_emp" type="text" id="mana_emp" disabled="disabled" value="${emp_name}" ltype="text" validate="{maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>文件路径:</b></td>
	            <td align="left" class="l-table-edit-td" colspan="4">
	            	<input name="file_path" type="text" id="file_path" value="${file_path}" disabled="disabled" ltype="text" validate="{maxlength:100}" />
	            </td>
	        </tr>
	     </table>
    </form>
</body>
</html>
