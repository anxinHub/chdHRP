<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc.jsp"/>
 <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
    	 
        var formPara={
            
            
           duty_code:$("#duty_code").val(),
            
           duty_name:$("#duty_name").val(),
            
           duty_note:$("#duty_note").val(),
            
           is_stop:$("#is_stop").val() 
            
            
         };
        ajaxJsonObjectByUrl("addAphiEmpDuty.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='duty_code']").val('');
				 $("input[name='duty_name']").val('');
				 $("input[name='duty_note']").val('');
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color: red">*</span>职务编码：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color: red">*</span>职务名称：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_name" type="text" id="duty_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_note" type="text" id="duty_note" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<select name="is_stop" id="is_stop">
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
