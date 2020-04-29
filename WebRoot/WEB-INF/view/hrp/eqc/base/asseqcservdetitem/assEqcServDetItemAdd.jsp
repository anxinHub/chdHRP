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
            
           unit_code:$("#unit_code").val(),
            
           price:$("#price").val(),
            
           remark:$("#remark").val(),
            
           import_flag:$("#import_flag").val(),
            
           min_minutes:$("#min_minutes").val(),
            
           minutes_per_times:$("#minutes_per_times").val(),
            
           minutes_per_times:$("#minutes_per_times").val(),
            
           invalid_flag:$("#invalid_flag").val(),
            
           invalid_flag:$("#invalid_flag").val(),
            
           ex_desc:$("#ex_desc").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssEqcservdetitem.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='charge_item_id']").val('');
				 $("input[name='unit_code']").val('');
				 $("input[name='price']").val('');
				 $("input[name='remark']").val('');
				 $("input[name='import_flag']").val('');
				 $("input[name='min_minutes']").val('');
				 $("input[name='minutes_per_times']").val('');
				 $("input[name='minutes_per_times']").val('');
				 $("input[name='invalid_flag']").val('');
				 $("input[name='invalid_flag']").val('');
				 $("input[name='ex_desc']").val('');
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
   
    function saveAssEqcservdetitem(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">unitCode：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">导入标志：</td>
            <td align="left" class="l-table-edit-td"><input name="import_flag" type="text" id="import_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最小分钟数：</td>
            <td align="left" class="l-table-edit-td"><input name="min_minutes" type="text" id="min_minutes" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">每次分钟数：</td>
            <td align="left" class="l-table-edit-td"><input name="minutes_per_times" type="text" id="minutes_per_times" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">最大分钟数：</td>
            <td align="left" class="l-table-edit-td"><input name="minutes_per_times" type="text" id="minutes_per_times" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">无效标志：</td>
            <td align="left" class="l-table-edit-td"><input name="invalid_flag" type="text" id="invalid_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新日期：</td>
            <td align="left" class="l-table-edit-td"><input name="invalid_flag" type="text" id="invalid_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">曾用描述：</td>
            <td align="left" class="l-table-edit-td"><input name="ex_desc" type="text" id="ex_desc" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
