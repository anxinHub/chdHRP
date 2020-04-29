<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
     	
     	$("#source_code").ligerTextBox({ disabled: true});
    	
     	//$("#source_name").ligerTextBox({ disabled: true});
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#is_stop").val('${is_stop}');
        
       
        
     });  
     
     function  save(){
        var formPara={
           source_id:$("#source_id").val(),

           source_code:$("#source_code").val(),
            
           source_name:$("#source_name").val(),
            
           source_attr:liger.get("source_attr").getValue(),
            
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()
            
         };
        ajaxJsonObjectByUrl("../../sys/source/updateSource.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 /* $("input[name='source_id']").val('');
				 $("input[name='source_code']").val('');
				 $("input[name='source_name']").val('');
				 $("input[name='source_attr']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='note']").val(''); */
				 parentFrameUse().query();
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
   
    function saveAccSource(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            
    	autocomplete("#source_attr","../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true); 
     
    	liger.get("source_attr").setValue("${source_attr}");
        liger.get("source_attr").setText('${source_attr} ${nature_name}');
        
        $("#note").val('${note}');
        
        $("#is_stop").val(${is_stop});
    } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
         <tr>
                <td align="left" class="l-table-edit-td"><input name="source_id" type="hidden"  id="source_id" ltype="text"  value="${source_id }" validate="{required:true,maxlength:20}" /></td>
            </tr> 
        <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源编码：</td>
                <td align="left" class="l-table-edit-td"><input name="source_code" type="text"  id="source_code" ltype="text"  value="${source_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源名称：</td>
                <td align="left" class="l-table-edit-td"><input name="source_name" type="text"  id="source_name" ltype="text"  value="${source_name }"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                </tr>
                 <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金性质：</td>
                <td align="left" class="l-table-edit-td"><input name="source_attr" type="text"  id="source_attr" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
                </tr>
                <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_stop" name="is_stop" style="width: 135px;" >
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><textarea rows="3" cols="60" name="note" id="note"  validate="{required:false,maxlength:50}" style="resize: none;"></textarea></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
