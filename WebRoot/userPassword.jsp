<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/hrp.js"></script>
    <script src="<%=path %>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
	<script src="<%=path %>/lib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
	<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
	<script type="text/javascript">
	
	$(function (){
		$("#password").ligerTextBox({width:180});
		$("#newPassword").ligerTextBox({width:180});
		$("#comNewPassword").ligerTextBox({width:180});
	});

     function  save(){
    
     	if($("#password").val()==""){
     		$.ligerDialog.error("请输入旧密码.");
     		return false;
     	}
     	if($("#newPassword").val()==""){
     		$.ligerDialog.error("请输入新密码.");
     		return false;
     	}
     	if($("#comNewPassword").val()!=$("#newPassword").val()){
     		$.ligerDialog.error("两次输入的密码不一致.");
     		return false;
     	}
     	if($("#password").val()==$("#newPassword").val()){
     		$.ligerDialog.error("旧密码与新密码一致.");
     		return false;
     	}
     	
     	if($("#newPassword").val().indexOf(" ") != -1){
			$.ligerDialog.error("新密码中不能包含空格.");
			return false;
    	}
		
		if($("#newPassword").val().length > 16){
			$.ligerDialog.error("新密码长度不能大于16个字符.");
			return false;
    	}

     	var formPara={user_id:'${user_id}',user_code:'${user_code}',oldPassword:$("#password").val(),newPassword:$("#newPassword").val()};
     	ajaxJsonObjectByUrl("hrp/sys/user/updateUserPassword.do?isCheck=false",formPara);
     	
	}
     

    </script>

  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
           
            <tr>
                <td align="right" class="l-table-edit-td" >旧密码：</td>
                <td align="left" class="l-table-edit-td"><input name="password" type="password" id="password" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td" >新密码：</td>
                <td align="left" class="l-table-edit-td"><input name="newPassword" type="password" id="newPassword" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td" >确认新密码：</td>
                <td align="left" class="l-table-edit-td"><input name="comNewPassword" type="password" id="comNewPassword" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   </body>
</html>
