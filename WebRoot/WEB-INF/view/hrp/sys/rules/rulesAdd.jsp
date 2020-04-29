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
            
           mod_code:$("#mod_code").val(),
            
           proj_code:$("#proj_code").val(),
            
           proj_name:$("#proj_name").val(),
            
           max_level:$("#max_level").val(),
            
           max_length:$("#max_length").val(),
            
           level1:$("#level1").val(),
            
           level2:$("#level2").val(),
            
           level3:$("#level3").val(),
            
           level4:$("#level4").val(),
            
           level5:$("#level5").val(),
            
           level6:$("#level6").val(),
            
           level7:$("#level7").val(),
            
           level8:$("#level8").val(),
            
           level9:$("#level9").val(),
            
           level10:$("#level10").val(),
            
           is_auto:$("#is_auto").val()
            
         };
        
        ajaxJsonObjectByUrl("addRules.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='group_id']").val('');
				 $("input[name='hos_id']").val('');
				 $("input[name='mod_code']").val('');
				 $("input[name='proj_code']").val('');
				 $("input[name='proj_name']").val('');
				 $("input[name='max_level']").val('');
				 $("input[name='max_length']").val('');
				 $("input[name='level1']").val('');
				 $("input[name='level2']").val('');
				 $("input[name='level3']").val('');
				 $("input[name='level4']").val('');
				 $("input[name='level5']").val('');
				 $("input[name='level6']").val('');
				 $("input[name='level7']").val('');
				 $("input[name='level8']").val('');
				 $("input[name='level9']").val('');
				 $("input[name='level10']").val('');
				 $("input[name='is_auto']").val('');
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
   
    function saveRules(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">mod_code：</td>
                <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">proj_code：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">proj_name：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">max_level：</td>
                <td align="left" class="l-table-edit-td"><input name="max_level" type="text" id="max_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">max_length：</td>
                <td align="left" class="l-table-edit-td"><input name="max_length" type="text" id="max_length" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level1：</td>
                <td align="left" class="l-table-edit-td"><input name="level1" type="text" id="level1" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level2：</td>
                <td align="left" class="l-table-edit-td"><input name="level2" type="text" id="level2" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level3：</td>
                <td align="left" class="l-table-edit-td"><input name="level3" type="text" id="level3" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level4：</td>
                <td align="left" class="l-table-edit-td"><input name="level4" type="text" id="level4" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level5：</td>
                <td align="left" class="l-table-edit-td"><input name="level5" type="text" id="level5" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level6：</td>
                <td align="left" class="l-table-edit-td"><input name="level6" type="text" id="level6" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level7：</td>
                <td align="left" class="l-table-edit-td"><input name="level7" type="text" id="level7" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level8：</td>
                <td align="left" class="l-table-edit-td"><input name="level8" type="text" id="level8" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level9：</td>
                <td align="left" class="l-table-edit-td"><input name="level9" type="text" id="level9" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">level10：</td>
                <td align="left" class="l-table-edit-td"><input name="level10" type="text" id="level10" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">is_auto：</td>
                <td align="left" class="l-table-edit-td"><input name="is_auto" type="text" id="is_auto" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
