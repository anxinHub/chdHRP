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
         loadDict();//加载下拉框
         loadForm();
     });   
     
     function  save(){
        var formPara={
           set_id : $("#set_id").val(),
           set_code : $("#set_code").val(),
           set_name : $("#set_name").val()
         };
        
        ajaxJsonObjectByUrl("addMedStoreSet.do?isCheck=true",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("input[name='set_code']").val('');
				 $("input[name='set_name']").val('');
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
   
    function saveMedStoreSet(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	//字典下拉框
    	$("#set_id").ligerTextBox({width:160});
    	 $("#set_code").ligerTextBox({width:160}); 
        $("#set_name").ligerTextBox({width:160});    
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr style="display: none">
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">设置ID：</td>
            <td align="left" class="l-table-edit-td"><input name="set_id" type="text" id="set_id" disabled="disabled" value="系统生成" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>编码<font color ="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="set_code" type="text" id="set_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>名称<font color ="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="set_name" type="text" id="set_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
