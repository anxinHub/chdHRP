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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
   
    	 if($("#target_code").val()==""){
    		 
             $.ligerDialog.error("基本指标编码不能为空!");
    		 
    		 return;
    	 }
    	 
         if($("#target_name").val()==""){
    		 
             $.ligerDialog.error("基本指标名称不能为空!");
    		 
    		 return;
    	 }
    	 
    	 if(liger.get("unit_code").getValue()==""){
    		 
    		 $.ligerDialog.error("计量单位不能为空!");
    		 
    		 return;
    	 }
    	
         if(liger.get("is_stop").getValue()==""){
    		 
            $.ligerDialog.error("是否停用不能为空!");
    		 
    		 return;
    	 }
        var formPara={
        		
        		target_code:$("#target_code").val(),
        		
        		target_name:$("#target_name").val(),
        		
        		unit_code:liger.get("unit_code").getValue(),
        		
        		is_stop:liger.get("is_stop").getValue(),
        		
        		note:$("#note").val()
            
            
         };
        
        ajaxJsonObjectByUrl("updateAccTarget.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccSubjType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#unit_code","../../sys/queryUnitDict.do?isCheck=false","id","text",true,true);
		liger.get("unit_code").setValue("${unit_code}");
		liger.get("unit_code").setText("${unit_name}");
    	 $("#is_stop").ligerComboBox({  
             data: [
                 { text: '否', id: '0' },
                 { text: '是', id: '1' },
             ]
         }); 
    	liger.get("is_stop").setValue("${is_stop}");
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">基本指标编码:</td>
                <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" value="${target_code}" disabled="disabled" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">基本指标编码:</td>
                <td align="left" class="l-table-edit-td"><input name="target_name" type="text" id="target_name" ltype="text" value="${target_name}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位:</td>
                <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号:</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text" value="${sort_code}" disabled="disabled"  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用:</td>
                <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注:</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
