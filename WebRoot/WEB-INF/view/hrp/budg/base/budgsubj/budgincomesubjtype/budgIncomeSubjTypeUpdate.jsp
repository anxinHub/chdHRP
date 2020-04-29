<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
   <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
    	 if("${is_fixed}" == 1){
    		 $.ligerDialog.warn('系统内置数据,不允许修改');
    	 }else{
    		 var formPara={
    	        		income_subj_type_codeOld :'${income_subj_type_code}',
    	        		
    	        		income_subj_type_code:$("#income_subj_type_code").val(),
    							
    	        		income_subj_type_name:$("#income_subj_type_name").val(),
    	        		
    	        		is_stop:liger.get("is_stop").getValue(),
    	        		
    	        		is_fixed:liger.get("is_fixed").getValue()		
    			        };
    	        ajaxJsonObjectByUrl("updateBudgIncomeSubjType.do?isCheck=false",formPara,function(responseData){
    	            
    	            if(responseData.state=="true"){
    	                parent.query();
    	            }
    	        }); 
    	 }
    }
     
    function loadForm(){
    
    $.metadata.setType("attr","validate");
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
   
    function updateBudgAccSubjNature(){
    	if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true);
        liger.get("is_stop").setValue('${is_stop}');
        autoCompleteByData("#is_fixed", yes_or_no.Rows, "id", "text", true, true);
        liger.get("is_fixed").setValue('${is_fixed}');
        
        if("${is_fixed}" == 1){
        	$("#income_subj_type_code").ligerTextBox({disabled:true});
            $("#income_subj_type_name").ligerTextBox({disabled:true});
        	$("#is_stop").ligerTextBox({disabled:true});
            $("#is_fixed").ligerTextBox({disabled:true});
        }else{
        	 $("#is_stop").ligerTextBox({width:160});
             $("#is_fixed").ligerTextBox({width:160});
        }
       
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目类别编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="income_subj_type_code" type="text" id="income_subj_type_code" value="${income_subj_type_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           </tr> 
		   <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>科目类别名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="income_subj_type_name" type="text" id="income_subj_type_name" value="${income_subj_type_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
			</tr> 
			<tr>	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否内置<font color ="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="is_fixed" type="text" id="is_fixed" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
			</tr>
        </table>
    </form>
    </body>
</html>
