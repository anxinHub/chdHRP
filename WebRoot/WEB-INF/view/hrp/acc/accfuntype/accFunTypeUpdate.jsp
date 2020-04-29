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
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        fun_id:'${fun_id}',
        group_id:'${group_id}',
		hos_id:'${hos_id}',
		copy_code:'${copy_code}',
        acc_year:'${acc_year}',
        fun_code:$("#fun_code").val(),
        fun_name:$("#fun_name").val(),
        super_code:liger.get("super_code").getValue(),
        is_stop:$("#is_stop").val(),
        is_last:$("#is_last").val(),
        note:$("#note").val()
        };
        ajaxJsonObjectByUrl("updateAccFunType.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccFunType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
       autocomplete("#super_code","../queryFunType.do?isCheck=false","id","text",true,true); 
       
        $.post("../queryFunType.do?isCheck=false&fun_code=${super_code}",null,function(data){
        	$.each(data,function(index,value){
        		liger.get("super_code").setText(value.text);
        	});
        },"json");
        liger.get("super_code").setValue("${super_code}");
    	$("#is_stop").val('${is_stop}');
    	
    	$("#is_last").val('${is_last}');
    	
    	$("#fun_code").ligerTextBox({disabled: true });
    	
    	$("#super_code").ligerTextBox({disabled: true });
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" disabled="disabled" id="fun_code" ltype="text"  value="${fun_code}" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_name" type="text" id="fun_name" ltype="text"  value="${fun_name}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级分类：</td>
                <td align="left" class="l-table-edit-td"><input name="super_code" type="text" id="super_code" ltype="text" disabled="disabled" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><textarea rows="3" cols="60" id="note" name="note" ltype="text" validate="{required:false,maxlength:50}" style="resize: none;">${note }</textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
