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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
           group_id:$("#group_id").val(),
            
           hos_id:$("#hos_id").val(),
            
           hos_code:$("#hos_code").val(),
            
           hos_name:$("#hos_name").val(),
            
           hos_simple:$("#hos_simple").val(),
            
           super_code:$("#super_code").val(),
            
           hos_level:$("#hos_level").val(),
            
           hos_city:$("#hos_city").val(),
            
           hos_sort:$("#hos_sort").val(),
            
           hos_contact:$("#hos_contact").val(),
            
           hos_phone:$("#hos_phone").val(),
            
           hos_email:$("#hos_email").val(),
            
           hos_zipcode:$("#hos_zipcode").val(),
            
           hos_type:$("#hos_type").val(),
            
           hos_address:$("#hos_address").val(),
            
           is_last:$("#is_last").val(),
            
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val(),
            
            
            
         };
        
        ajaxJsonObjectByUrl("addInfo.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='group_id']").val('');
				 $("input[name='hos_id']").val('');
				 $("input[name='hos_code']").val('');
				 $("input[name='hos_name']").val('');
				 $("input[name='hos_simple']").val('');
				 $("input[name='super_code']").val('');
				 $("input[name='hos_level']").val('');
				 $("input[name='hos_city']").val('');
				 $("input[name='hos_sort']").val('');
				 $("input[name='hos_contact']").val('');
				 $("input[name='hos_phone']").val('');
				 $("input[name='hos_email']").val('');
				 $("input[name='hos_zipcode']").val('');
				 $("input[name='hos_type']").val('');
				 $("input[name='hos_address']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='note']").val('');
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
   
    function saveInfo(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">group_id：</td>
                <td align="left" class="l-table-edit-td"><input name="group_id" type="text" id="group_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_id：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_code：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_code" type="text" id="hos_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_name：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_name" type="text" id="hos_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_simple：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_simple" type="text" id="hos_simple" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">super_code：</td>
                <td align="left" class="l-table-edit-td"><input name="super_code" type="text" id="super_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_level：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_level" type="text" id="hos_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_city：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_city" type="text" id="hos_city" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_sort：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_sort" type="text" id="hos_sort" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_contact：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_contact" type="text" id="hos_contact" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_phone：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_phone" type="text" id="hos_phone" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_email：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_email" type="text" id="hos_email" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_zipcode：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_zipcode" type="text" id="hos_zipcode" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_type：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_type" type="text" id="hos_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">hos_address：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_address" type="text" id="hos_address" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">is_last：</td>
                <td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">is_stop：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">note：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
