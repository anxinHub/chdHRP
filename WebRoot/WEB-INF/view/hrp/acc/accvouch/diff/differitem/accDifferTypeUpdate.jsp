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
	
     $(function (){
    	 $("#diff_type_code").val('${entity.diff_type_code}');
     	$("#diff_type_name").val('${entity.diff_type_name}');
     	$("#diff_dire").val('${entity.diff_dire}');
     	$("#diff_obj").val('${entity.diff_obj}');
     	$("#is_stop").val('${entity.is_stop}');
     	$("#note").val('${entity.note}');
        loadForm();
     });  
     
     function  save(){
        var formPara={
           diff_type_code:$("#diff_type_code").val(),
           diff_type_name:$("#diff_type_name").val(),
           diff_dire:$("#diff_dire").val(),
           diff_obj:$("#diff_obj").val(),
           note:$("#note").val(),
           is_stop:$("#is_stop").val()
         };
        
        ajaxJsonObjectByUrl("updateAccDifferType.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
			     parent.loadTree();
            }
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({ errorPlacement: function (lable, element) {
             if (element.hasClass("l-textarea")){
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }else if (element.hasClass("l-text-field")){
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }else{
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable){
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function (){
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
     $("#diff_type_code").attr("disabled","disabled")
 }       
   
    function saveAccDifferType(){
        if($("form").valid()){
            save();
        }
   }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">差异标注类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="diff_type_code" type="text" id="diff_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">差异标注类别名称：</td>
                <td align="left" class="l-table-edit-td"><input name="diff_type_name" type="text" id="diff_type_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">标注方向：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="diff_dire" name="diff_dire" style="width: 135px;">
                		<option value="0">正向</option>
                		<option value="1">负向</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分析对象：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="diff_obj" name="diff_obj" style="width: 135px;">
                		<option value="04">收入</option>
                		<option value="05">费用</option>
                		<option value="06">预算收入</option>
                		<option value="07">预算支出</option>
                	</select>
                </td>
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
