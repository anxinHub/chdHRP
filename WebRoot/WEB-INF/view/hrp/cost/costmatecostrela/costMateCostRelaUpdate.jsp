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
        
        $("#mate_type_code").ligerTextBox({disabled: true});
        
    });  
     
    function save(){
    	
    	var cost_item_code = liger.get("cost_item_code").getValue();
    	
        var formPara={

        id:$("#id").val(),
        
        mate_type_id:liger.get("mate_type_id").getValue(),
        
        cost_item_code:cost_item_code
        
        };
        ajaxJsonObjectByUrl("updateCostMateCostRela.do",formPara,function(responseData){
            
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
   
    function saveCostMateCostRela(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#mate_type_code","../queryMateTypeArrt.do?isCheck=false","id","text",true,true);
        
    	autocomplete("#cost_item_code","../queryItemDictCodeLast.do?isCheck=false","id","text",true,true);
    	
    	liger.get("mate_type_code").setValue('${mate_type_code}');	
    	
		liger.get("mate_type_code").setText('${mate_type_name}');	
		
		liger.get("cost_item_code").setValue('${cost_item_code}');	
    	
		liger.get("cost_item_code").setText('${cost_item_name}');	

     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <input name="id" type="hidden" id="id" value="${id}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料分类编码：</td>
                <td align="left" class="l-table-edit-td"><input disabled="disabled" name="mate_type_code" type="text" id="mate_type_code" ltype="text"  value="${mate_type_code}" validate="{required:true}"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">成本项目：</td>
                <td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text"  value="${cost_item_code}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            
        </table>
    </form>
    </body>
</html>
