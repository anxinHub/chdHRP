<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        $("#wage_code").ligerTextBox({ disabled: true });
        
    });  
     
    function save(){
    	
    	var formPara={
    			 group_id:'${group_id}',
    			 
    			 hos_id:'${hos_id}',
    			 
    			 copy_code:'${copy_code}',
        		
    	           wage_code:$("#wage_code").val(),
    	            
    	           wage_name:$("#wage_name").val(),
    	            
    	           note:$("#note").val()
    	            
    	         };
        ajaxJsonObjectByUrl("../accwage/updateAccWage.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query("update");
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
   
    function saveAccWage(){
        
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套编码：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" value="${wage_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
                <td align="left" class="l-table-edit-td"><input name="wage_name" type="text" id="wage_name" ltype="text"  value="${wage_name }" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
          
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note }"  validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
    </body>
</html>
