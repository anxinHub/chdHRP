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
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
        	
            //dept_id:'${dept_id}',
            type_code:liger.get("type_code").getValue(),
            natur_code:liger.get("natur_code").getValue(),
            para_code:liger.get("para_code").getValue(),
            paramVo:'${paramVo}'
         };
        
        ajaxJsonObjectByUrl("../costdeptdict/costDeptDictManage.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
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
   
    function saveDeptDictManage(){
       //if($("form").valid()){
            save();
        //}
   }
    function loadDict(){
    		
            autocomplete("#type_code","../../acc/queryDeptType.do?isCheck=false","id","text",true,true);
            autocomplete("#natur_code","../../acc/queryDeptNatur.do?isCheck=false","id","text",true,true);
        	autocomplete("#para_code", "../queryParaNaturLast.do?isCheck=false", "id",
    				"text", true, true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text"  id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        <tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none">部门性质：</td> 
                <td align="left" class="l-table-edit-td" ><input name="natur_code" type="text"  id="natur_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        
        </tr>
        <tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none">分摊性质：</td> 
                <td align="left" class="l-table-edit-td" ><input name="para_code" type="text"  id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        
        </tr>
        </table>
    </form>
   
    </body>
</html>