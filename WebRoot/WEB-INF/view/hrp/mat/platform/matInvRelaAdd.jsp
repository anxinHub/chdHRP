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
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
	
     var dataFormat;
     $(function (){  	
        
    	loadDict();//加载下拉框     
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
          inv_id : liger.get("inv_code").getValue().split(',')[0],
          goodsid : liger.get("goodsid").getValue() 
            
         }; 
        
        ajaxJsonObjectByUrl("addMatInvRela.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='inv_code']").val('');
				 $("input[name='goodsid']").val(''); 
                parent.query();
            }else{
            	//$.ligerDialog.warn(responseData.msg);
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
    	autocomplete("#inv_code","queryMatInvHrpBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',380);
    	autocomplete("#goodsid", "queryMatInvSptBySelect.do?isCheck=false", "id", "text", true, true,'',false,'',380);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
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
