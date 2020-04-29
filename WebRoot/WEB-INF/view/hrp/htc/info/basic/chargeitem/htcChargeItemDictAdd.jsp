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
         
        var formPara={
            
           charge_kind_id:liger.get("charge_kind_id").getValue(),
            
           charge_item_code:$("#charge_item_code").val(),
            
           charge_item_name:$("#charge_item_name").val(),
            
           price:$("#price").val(),
            
           is_stop:liger.get("is_stop").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addHtcChargeItemDict.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='charge_kind_id']").val('');
				 $("input[name='charge_item_code']").val('');
				 $("input[name='charge_item_name']").val('');
				 $("input[name='price']").val('');
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
   
    function saveCostChargeItemArrt(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#charge_kind_id","../../base/queryHtcChargeKindArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#is_stop", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,"0");
    	
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_item_code" type="text" id="charge_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费项目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_item_name" type="text" id="charge_item_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_kind_id" type="text" id="charge_kind_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
                <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">停用标志：</td>
                <td align="left" class="l-table-edit-td">
                		<input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true}"/>

                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
