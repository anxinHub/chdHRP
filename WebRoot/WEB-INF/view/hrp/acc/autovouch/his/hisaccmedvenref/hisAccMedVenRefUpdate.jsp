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
    	
        loadDict();
        
        loadForm();
        
        $("#ven_code").ligerTextBox({ disabled: true});
        
    });  
     
    function save(){
        var formPara={
				
        		group_id:'${group_id}',
                
                hos_id:'${hos_id}',
                
                copy_code:'${copy_code}',
                 
                ven_code:'${ven_code}', 
                
                ven_name:$("#ven_name").val()
                
        };
        ajaxJsonObjectByUrl("updateHisAccMedVen.do",formPara,function(responseData){
            
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
   
    function saveCostChargeKindArrt(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房编码：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_code" type="text" id="ven_code" ltype="text" value="${ven_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房名称：</td>
                <td align="left" class="l-table-edit-td"><input name="ven_name" type="text" id="ven_name" ltype="text" value="${ven_name }" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
              
        </table>
    </form>
    </body>
</html>
