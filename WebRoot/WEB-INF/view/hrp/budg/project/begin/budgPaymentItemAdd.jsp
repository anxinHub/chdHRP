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
           payment_item_id:$("#payment_item_id").val(),
            
           payment_item_code:$("#payment_item_code").val(),
            
           payment_item_name:$("#payment_item_name").val(),
            
           item_name_all:$("#item_name_all").val(),
            
           super_code:$("#super_code").val(),
            
           item_level:$("#item_level").val(),
            
           is_last:$("#is_last").val(),
            
           payment_item_nature:$("#payment_item_nature").val(),
            
           is_manage:$("#is_manage").val(),
            
           control_way:$("#control_way").val(),
            
           is_stop:$("#is_stop").val(),
           acc_subj_manage:$("#acc_subj_manage").val(),
            
           acc_subj_medical:$("#acc_subj_medical").val(),
            
           acc_subj_education:$("#acc_subj_education").val(),
            
           acc_subj_scientific:$("#acc_subj_scientific").val(),
            
           acc_subj_financial:$("#acc_subj_financial").val()
            
         };
        
        ajaxJsonObjectByUrl("addBudgPaymentItem.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='payment_item_id']").val('');
				 $("input[name='payment_item_code']").val('');
				 $("input[name='payment_item_name']").val('');
				 $("input[name='item_name_all']").val('');
				 $("input[name='super_code']").val('');
				 $("input[name='item_level']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='payment_item_nature']").val('');
				 $("input[name='is_manage']").val('');
				 $("input[name='control_way']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='acc_subj_manage']").val('');
				 $("input[name='acc_subj_medical']").val('');
				 $("input[name='acc_subj_education']").val('');
				 $("input[name='acc_subj_scientific']").val('');
				 $("input[name='acc_subj_financial']").val('');
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
   
    function savebudgPaymentItemDict(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目ID：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_id" type="text" id="payment_item_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目编码：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_code" type="text" id="payment_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_name" type="text" id="payment_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目全称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_name_all" type="text" id="item_name_all" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级项目：</td>
            <td align="left" class="l-table-edit-td"><input name="super_code" type="text" id="super_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目级次：</td>
            <td align="left" class="l-table-edit-td"><input name="item_level" type="text" id="item_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级：</td>
            <td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出项目性质：</td>
            <td align="left" class="l-table-edit-td"><input name="payment_item_nature" type="text" id="payment_item_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否管理费：</td>
            <td align="left" class="l-table-edit-td"><input name="is_manage" type="text" id="is_manage" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">控制方式：</td>
            <td align="left" class="l-table-edit-td"><input name="control_way" type="text" id="control_way" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">管理支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_subj_manage" type="text" id="acc_subj_manage" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医疗支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_subj_medical" type="text" id="acc_subj_medical" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">教学支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_subj_education" type="text" id="acc_subj_education" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科研支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_subj_scientific" type="text" id="acc_subj_scientific" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财政支出对应会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_subj_financial" type="text" id="acc_subj_financial" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
