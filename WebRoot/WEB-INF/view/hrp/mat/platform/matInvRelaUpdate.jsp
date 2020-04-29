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
       		inv_id : liger.get("inv_code").getValue().split(',')[0],
            goodsid : liger.get("goodsid").getValue() ,
            old_inv_id : $("#old_inv_code").val() ,
            old_goodsid : $("#old_goodsid").val()
        };
        ajaxJsonObjectByUrl("updateMatInvRela.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }else{
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
   
    function saveMatinvRela(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#inv_code","queryMatInvHrpBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',380);
        liger.get("inv_code").setValue("${matInvRela.inv_id}");
        liger.get("inv_code").setText("${matInvRela.inv_name}");
        
        autocomplete("#goodsid", "queryMatInvSptBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',380);
        liger.get("goodsid").setValue("${matInvRela.goodsid}");
        liger.get("goodsid").setText("${matInvRela.goodsname}");
       
   }   
 </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
  			<tr>
  			 
        <input name="old_inv_code" type="hidden" id="old_inv_code"   value="${matInvRela.inv_id }" />
        <input name="old_goodsid" type="hidden" id="old_goodsid"   value="${matInvRela.goodsid }" />
	        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">HRP材料：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="inv_code" type="text" id="inv_code" ltype="text"   />
	            </td>
	       </tr>
	       <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">省平台材料：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="goodsid" type="text" id="goodsid" ltype="text"  />
	            </td>
	            <td align="left"></td>
        	</tr> 
        </table>
    </form>
    </body>
</html>
