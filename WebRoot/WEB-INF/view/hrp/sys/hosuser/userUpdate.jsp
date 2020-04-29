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
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<link href="<%=path%>/lib/htc/style/select/chosen.css" rel="stylesheet">
<script src="<%=path%>/lib/htc/style/select/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        $("#user_code").ligerTextBox({ disabled: true});
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        user_id:'${user_id}',
        user_code:$("#user_code").val(),
        user_name:$("#user_name").val(),
        emp_code:liger.get("emp_code").getValue(),
        type_code:'0',
        is_stop:$("#is_stop").val(),
        note:$("#note").val(),
        phone:$("#phone").val()
        };
        ajaxJsonObjectByUrl("updateHosUser.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
   
    function saveUser(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	autocomplete("#emp_code","../queryEmpNew.do?isCheck=false","id","text",true,true);	
    	liger.get("emp_code").setValue('${emp_id}');
    	liger.get("emp_code").setText('${emp_code} ${emp_name}');
    	$("#is_stop").val('${is_stop}');
    	
     }   
    function rePassword(){
      	 $.ligerDialog.confirm('确定要重置密码为123456?', function(yes) {
   				if (yes) {
   					var formPara={user_id:'${user_id}',user_code:'${user_code}',newPassword:'123456'};
   			     	ajaxJsonObjectByUrl("../user/reUserPassword.do?isCheck=false",formPara);
   				}
      	 });		
       }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户编码：</td>
                <td align="left" class="l-table-edit-td"><input name="user_code" value="${user_code }" type="text" id="user_code" ltype="text" disabled="disabled" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户名称：</td>
                <td align="left" class="l-table-edit-td"><input name="user_name" type="text" id="user_name" value="${user_name }" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">电话号码：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" value="${phone }" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop">
                		<option value="0">启用</option>
                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note">${note }</textarea>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
