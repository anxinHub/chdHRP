<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           cost_item_id:$("#cost_item_id").val(),
            
           cost_type_id:$("#cost_type_id").val(),
            
           cost_type_no:$("#cost_type_no").val(),
            
           cost_item_no:$("#cost_item_no").val(),
            
           cost_item_code:$("#cost_item_code").val(),
            
           cost_item_name:$("#cost_item_name").val(),
            
           user_code:$("#user_code").val(),
            
           create_date:$("#create_date").val(),
            
           note:$("#note").val(),
            
           is_stop:$("#is_stop").val(),
            
            
            
         };
        
        ajaxJsonObjectByUrl("addCostItemDictNo.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				
				
				 $("input[name='cost_item_id']").val('');
				 $("input[name='cost_type_id']").val('');
				 $("input[name='cost_type_no']").val('');
				 $("input[name='cost_item_no']").val('');
				 $("input[name='cost_item_code']").val('');
				 $("input[name='cost_item_name']").val('');
				 $("input[name='user_code']").val('');
				 $("input[name='create_date']").val('');
				 $("input[name='note']").val('');
				 $("input[name='is_stop']").val('');
				
				
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
   
    function saveCostItemDictNo(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目ID：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类ID：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_type_id" type="text" id="cost_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本分类变更ID：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_type_no" type="text" id="cost_type_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目变更id：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_no" type="text" id="cost_item_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_name" type="text" id="cost_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更人：</td>
                <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更时间：</td>
                <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更原因：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">0启用，1停用：</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
