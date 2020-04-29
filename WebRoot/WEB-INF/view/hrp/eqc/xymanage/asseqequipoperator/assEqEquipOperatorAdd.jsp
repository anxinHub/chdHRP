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
            
            
            
           analysis_code:$("#analysis_code").val(),
            
          // eo_eq_group:$("#eo_eq_group").val(),
            
           user_id:$("#user_id").val(),
            
           percent:$("#percent").val(),
            
           remark:$("#remark").val(),
            
           from_date:$("#from_date").val(),
            
           to_date:$("#to_date").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssEqequipoperator.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='analysis_code']").val('');
				 //$("input[name='eo_eq_group']").val('');
				 $("input[name='user_id']").val('');
				 $("input[name='percent']").val('');
				 $("input[name='remark']").val('');
				 $("input[name='from_date']").val('');
				 $("input[name='to_date']").val('');
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
   
    function saveAssEqequipoperator(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分析项</td>
            <td align="left" class="l-table-edit-td"><input name="analysis_code" type="text" id="analysis_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>        
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户ID（SYS_USER）：</td>
            <td align="left" class="l-table-edit-td"><input name="user_id" type="text" id="user_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">占比：</td>
            <td align="left" class="l-table-edit-td"><input name="percent" type="text" id="percent" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">起始日期：</td>
            <td align="left" class="l-table-edit-td"><input name="from_date" type="text" id="from_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结束日期：</td>
            <td align="left" class="l-table-edit-td"><input name="to_date" type="text" id="to_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
