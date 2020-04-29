<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        loadForm();
   
    });  
     
    function save(){
        var formPara={
	        pay_code:$("#pay_code").val(),
	        pay_name:$("#pay_name").val(),
	        pay_type:$("#pay_type").val(),	
	        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateMedPayType.do",formPara,function(responseData){
            
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
   
    function saveMedPayType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	$("#pay_code").ligerTextBox({width:160,disabled:true});
        
        $("#pay_type").val('${medPayType.pay_type}');
        $("#is_stop").val('${medPayType.is_stop}');
       
   }   
 </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式编码：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="pay_code" type="text" id="pay_code" disabled="disabled" ltype="text"  value="${medPayType.pay_code}" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式名称：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="pay_name" type="text" id="pay_name" ltype="text"  value="${medPayType.pay_name}" validate="{required:true,maxlength:20}" />
                </td>
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
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
