<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/htc.js" type="text/javascript"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        if(${is_stop}==true){
     		liger.get("is_stop").setValue(1);
     		liger.get("is_stop").setText("是");	
     		
     	}
     	
     	if(${is_stop}==false){
     		liger.get("is_stop").setValue(0);
     		liger.get("is_stop").setText("否");	
     		
     	}
    });  
     
    function save(){
        var formPara={
        duty_code:$("#duty_code").val(),
        duty_name:$("#duty_name").val(),
        duty_note:$("#duty_note").val(),
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateEmpDuty.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	
                //$("input[name='duty_code']").val('');
                
                //$("input[name='duty_name']").val('');
                
                //$("input[name='duty_note']").val('');
                
                //$("input[name='is_stop']").val('');
                
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
   
    function saveEmpDuty(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
       
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务编码：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_code" disabled="disabled" type="text" id="duty_code" ltype="text"  value="${duty_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务名称：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_name" type="text" id="duty_name" ltype="text"  value="${duty_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_note" type="text" id="duty_note" ltype="text"  value="${duty_note}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<select name="is_stop" id="is_stop">
						<option value="">..请选择..</option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
