<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           charge_item_id:$("#charge_item_id").val(),
            
           consum_code:$("#consum_code").val(),
            
           quantity:$("#quantity").val(),
            
           quantity_type:$("#quantity_type").val(),
            
           month_stat_flag:$("#month_stat_flag").val(),
            
           cycle_num:$("#cycle_num").val(),
            
           cycle_nuit:$("#cycle_nuit").val(),
            
           type_name:$("#type_name").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssEqcservicedetitemconsumabl.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='charge_item_id']").val('');
				 $("input[name='consum_code']").val('');
				 $("input[name='quantity']").val('');
				 $("input[name='quantity_type']").val('');
				 $("input[name='month_stat_flag']").val('');
				 $("input[name='cycle_num']").val('');
				 $("input[name='cycle_nuit']").val('');
				 $("input[name='type_name']").val('');
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
   
    function saveAssEqcservicedetitemconsumabl(){
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
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">chargeItemId：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_item_id" type="text" id="charge_item_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码：</td>
            <td align="left" class="l-table-edit-td"><input name="consum_code" type="text" id="consum_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">消耗数量：</td>
            <td align="left" class="l-table-edit-td"><input name="quantity" type="text" id="quantity" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">数量类型（绝对量、相对量、相对量已计算绝对量）：</td>
            <td align="left" class="l-table-edit-td"><input name="quantity_type" type="text" id="quantity_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否是按月来统计：</td>
            <td align="left" class="l-table-edit-td"><input name="month_stat_flag" type="text" id="month_stat_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">维护保养/检查周期：</td>
            <td align="left" class="l-table-edit-td"><input name="cycle_num" type="text" id="cycle_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">周期单位（年、月、天、时）：</td>
            <td align="left" class="l-table-edit-td"><input name="cycle_nuit" type="text" id="cycle_nuit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">类型（使用、质控）：</td>
            <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
