<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     $(function (){  	
        
    	loadDict();//加载下拉框     
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
           pay_code:$("#pay_code").val(),
          
           pay_name:$("#pay_name").val(),  
           pay_type:$("#pay_type").val(), 
           is_stop:$("#is_stop").val()
            
         };
        
        ajaxJsonObjectByUrl("addMedPayType.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='pay_code']").val('');
				 
				 $("input[name='pay_name']").val('');
				 $("input[name='pay_type']").val('');
				
				 $("input[name='is_stop']").val('');
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
   
   function saveMedPayType(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	 $("#subj_id").ligerComboBox({
 	      	url: '../../../../acc/querySubj.do?isCheck=false',
 	      	valueField: 'id',
 	       	textField: 'text', 
 	       	selectBoxWidth: 180,
 	      	autocomplete: true,
 	      	width: 180
 		 });
    	$("#use_code").ligerTextBox({width:160});
      	$("#use_name").ligerTextBox({width:160});
      	$("#is_stop").ligerTextBox({width:160});
      	$("#note").ligerTextBox({width:160});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付方式编码：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_code" type="text" id="pay_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付方式名称：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_name" type="text" id="pay_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支付属性：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="pay_type" name="pay_type">
                		<option value="0">现金</option>
                		<option value="1">银行</option>
                		<option value="2">应付</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
