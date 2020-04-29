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
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var med_type_code = $("#med_type_code").val();
    	 if(med_type_code == ''){
    		 $.ligerDialog.warn('药品类别编码不能为空');
    		 return ; 
    	 }
    	 
    	 var med_type_name = $("#med_type_name").val();
    	 if(med_type_name == ''){
    		 $.ligerDialog.warn('药品类别名称不能为空');
    		 return ; 
    	 }
    	 
    	 var super_code = $("#super_code").val();
    	 if(super_code == ''){
    		 $.ligerDialog.warn('上级编码不能为空');
    		 return ; 
    	 }
    	 
    	 var type_level = $("#type_level").val();
    	 if(type_level == ''){
    		 $.ligerDialog.warn('类别级次不能为空');
    		 return ; 
    	 }
    	 
        var formPara={
           med_type_code:med_type_code,
           med_type_name:med_type_name,
           super_code:super_code,
           type_level:type_level,
           is_last:$("#is_last").val()
         };
        
        ajaxJsonObjectByUrl("addHisAccMedType.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='pay_type_cpde']").val('');
				 $("input[name='med_type_name']").val('');
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
   
    function saveCostChargeKindArrt(){
    	save();
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>药品类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>药品类别名称：</td>
                <td align="left" class="l-table-edit-td"><input name="med_type_name" type="text" id="med_type_name" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>上级编码：</td>
                <td align="left" class="l-table-edit-td"><input name="super_code" type="text" id="super_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>类别级次：</td>
                <td align="left" class="l-table-edit-td"><input name="type_level" type="text" id="type_level" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级：</td>
                <td align="left" class="l-table-edit-td">
                <select name="is_last" id="is_last">
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
