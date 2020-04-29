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

    	 if(liger.get("type_code").getValue()==""){
    		 
    		 $.ligerDialog.error("部门类型不能为空");
    		 
    		 return false;
    	 }
        var formPara={
        	dept_id:'${dept_id}',
        	dept_code:$("#dept_code").val(),
        	dept_name:$("#dept_name").val(),
        	kind_code:liger.get("kind_code").getValue(),
            type_code:liger.get("type_code").getValue(),
            natur_code:liger.get("natur_code").getValue(),         
         };
        ajaxJsonObjectByUrl("addHtcDeptAttrDict.do?isCheck=false",formPara,function(responseData){
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
   
    function saveAccDeptAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    		
    		autocomplete("#kind_code","../../base/queryHtcDeptKind.do?isCheck=false","id","text",true,true);
    		liger.get("kind_code").setValue("${kind_code}");
    		liger.get("kind_code").setText("${kind_name}");
            autocomplete("#type_code","../../base/queryHtcDeptType.do?isCheck=false","id","text",true,true);
            autocomplete("#natur_code","../../base/queryHtcDeptNatur.do?isCheck=false","id","text",true,true);
            $("#dept_code").ligerTextBox({disabled:true});
            
            $("#dept_name").ligerTextBox({disabled:true});
            
             $("#kind_code").ligerTextBox({disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门编码：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" value="${dept_code }" type="text" disabled="disabled" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_name" value="${dept_name }" type="text" disabled="disabled" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text"  id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
       		<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none">部门性质：</td> 
                <td align="left" class="l-table-edit-td"><input name="natur_code" type="text"  id="natur_code" ltype="text"/></td>
                <td align="left"></td>
        	</tr>
        </table>
    </form>
   
    </body>
</html>
