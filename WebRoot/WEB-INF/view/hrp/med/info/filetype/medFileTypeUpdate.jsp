<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#type_code").ligerComboBox({disabled:true,cancelable: false});

        liger.get("isStopSelect").setValue('${is_stop}');
   	 	
        if('${is_stop}'=="0"){
     		
        	liger.get("isStopSelect").setText("否");
     	}else{
     		
     		liger.get("isStopSelect").setText("是");
     	}
        
     });  
     
     
     function  save(){
        var formPara={
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val(),
           is_stop:$('#isStopSelect option:selected').val(),
           note:$("#note").val()
         };
        ajaxJsonObjectByUrl("updateMedFileType.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 /* $("input[name='type_code']").val('');
				 $("input[name='type_name']").val('');
				 $("input[name='note']").val(''); */
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
   
    function saveMedFileType(){
    	
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" value="${type_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">文档分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" value="${type_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="isStopSelect">
                		<option value="1">是</option>
                		<option value="0">否</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr>
            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" value="${note}" ltype="text"/></td>
                <td align="left"></td>
            </tr> 
           
        </table>
    </form>
   
    </body>
</html>
