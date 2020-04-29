<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var is_auto=${sessionScope.hos_rules['HOS_SUP'].is_auto };
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        if(is_auto=="1"){
        	$("#sup_code").ligerTextBox({disabled: true});
        }
        
     });  
     
     function  save(){
    	 if(is_auto=="0"){
    		 	mat=$("#is_mat").is(":checked") ? 1 : 0;
    	    	med=$("#is_med").is(":checked") ? 1 : 0;
    	    	ass=$("#is_ass").is(":checked") ? 1 : 0;
    	    	sup=$("#is_sup").is(":checked") ? 1 : 0 ;		    
    	    	if(mat=='0' && med=='0' && ass=='0' && sup=='0'){
    	    		 $.ligerDialog.error("系统模块不能为空");
    	    		 return ; 
    	    	}
    		 
    		 if($("#sup_code").val()==""){
        		 
        		 $.ligerDialog.error("供应商编码不能为空");
        		 
        		 return false;
        	 }else{
        		 
        		 var formPara={
     	        		sup_id:'',
     	           sup_code:$("#sup_code").val(),
     	            
     	           type_code:liger.get("type_code").getValue(),
     	            
     	           sup_name:$("#sup_name").val(),
     	            
     	           sort_code:$("#sort_code").val(),
     	              
     	           is_stop:liger.get("is_stop").getValue(),
     	            
     	           note:$("#note").val(),
     	          is_mat:$("#is_mat").is(":checked") ? 1 : 0,
     	          is_med:$("#is_med").is(":checked") ? 1 : 0,
     	          is_ass:$("#is_ass").is(":checked") ? 1 : 0 ,
     	          is_sup:$("#is_sup").is(":checked") ? 1 : 0 
     	         };
     	        
     	        ajaxJsonObjectByUrl("assaddSup.do",formPara,function(responseData){
     	            
     	            if(responseData.state=="true"){
     					 $("input[name='sup_code']").val('');
     					 $("input[name='type_code']").val('');
     					 $("input[name='sup_name']").val('');
     					// $("input[name='sort_code']").val('');
     					 $("input[name='is_stop']").val('');
     					 $("input[name='note']").val('');
     	                parent.query();
     	            }
     	        });
        	 }
    	 }else{
    		 var formPara={
 	        		sup_id:'',
 	           sup_code:$("#sup_code").val(),
 	            
 	           type_code:liger.get("type_code").getValue(),
 	            
 	           sup_name:$("#sup_name").val(),
 	            
 	           sort_code:$("#sort_code").val(),
 	              
 	          is_stop:liger.get("is_stop").getValue(),
 	            
 	           note:$("#note").val(), 
				is_mat:$("#is_mat").is(":checked") ? 1 : 0,
				is_med:$("#is_med").is(":checked") ? 1 : 0,
				is_sup:$("#is_sup").is(":checked") ? 1 : 0 ,
				is_ass:$("#is_ass").is(":checked") ? 1 : 0 
 	            
 	         };
 	        ajaxJsonObjectByUrl("assaddSup.do",formPara,function(responseData){
 	            
 	            if(responseData.state=="true"){
 					 $("input[name='sup_code']").val('');
 					 $("input[name='type_code']").val('');
 					 $("input[name='sup_name']").val('');
 					// $("input[name='sort_code']").val('');
 					 $("input[name='is_stop']").val('');
 					 $("input[name='note']").val('');
 	                parent.query();
 	            }
 	     });
    }
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
   
    function saveSup(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	autocomplete("#type_code","../queryassSupTypeDict.do?isCheck=false","id","text",true,true);
    	$("#sort_code").ligerTextBox({width:160,disabled:true});
    	 $('#is_stop').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		})
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sup_code" type="text" id="sup_code"  ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sup_name" type="text" id="sup_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>供应商类别<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,digits:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td" >
              		<!-- <select name="is_stop" id="is_stop">
		              	<option value="0">否</option>
		              	<option value="1">是</option>
              		</select> -->
              		 <input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:20}" />
               </td>
               
               <td align="right" class="l-table-edit-td" colspan="2">
                
                	<input name="is_mat"  id ="is_mat" type="hidden" />
                	<input name="is_med"  id ="is_med"  type="hidden" />
                	<input name="is_ass"  id ="is_ass" type="checkbox"  checked="checked"/>固定资产&nbsp;&nbsp;
                	<input name="is_sup"  id ="is_sup" type="hidden" />
                </td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                	<textarea rows="3" cols="75" id="note" name="note" ltype="text" validate="{maxlength:20}"></textarea>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
