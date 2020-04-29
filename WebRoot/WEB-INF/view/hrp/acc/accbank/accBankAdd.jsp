<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    	 
    	 /* //手机电话验证
         var re1 = /^[1][358][0-9]{9}$/; 
         if($("#phone").val()!=null && ($("#phone").val()!="")){
           	 
         	if(re1.test($("#phone").val())==false){
     		  $.ligerDialog.error("联系电话输入不合法!");
 				
     		  return;
     	  }
      } */
        var formPara={
        		
           bank_number:$("#bank_number").val(),
            
           bank_name:$("#bank_name").val(),
           
           simple_name:$("#simple_name").val(),
           bank_zh:$("#bank_zh").val(),
           bank_address:$("#bank_address").val(),
           
           bank_account:$("#bank_account").val(),
           
           phone:$("#phone").val(),
           
           spell_code:$("#spell_code").val(),
           
           wbx_code:$("#wbx_code").val(),
           
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()/* ,
           
           is_auto:liger.get("is_auto").getValue() */
            
         };
        
        ajaxJsonObjectByUrl("addAccBank.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				/*  $("input[name='para_code']").val('');
				 $("input[name='mod_code']").val('');
				 $("input[name='para_name']").val('');
				 $("input[name='para_value']").val('');
				 $("input[name='note']").val('');
				 $("input[name='is_stop']").val(''); */
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
   
    function saveAccBank(){
    	
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行编码：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_number" type="text" id="bank_number" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行名称：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_name" type="text" id="bank_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行简称：</td>
                <td align="left" class="l-table-edit-td"><input name="simple_name" type="text" id="simple_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
          
          	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行账号：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_zh" type="text" id="bank_zh" ltype="text" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户行：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_address" type="text" id="bank_address" ltype="text" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户人：</td>
                <td align="left" class="l-table-edit-td"><input name="bank_account" type="text" id="bank_account" ltype="text" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系电话：</td>
                <td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" ltype="text"  /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">拼音码：</td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" id="spell_code" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五笔码：</td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code" ltype="text" /></td>
                <!-- <td align="left"><input type="checkbox" id="is_auto" />重新生成</td> -->
            </tr> 
            
            <!-- <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号：</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text"  validate="{required:false,maxlength:10}" /></td>
                <td align="left"></td>
            </tr> -->
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
					<option value="0">否</option>
					<option value="1">是</option>
				</select></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" maxlength="50"/></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
   
    </body>
</html>
