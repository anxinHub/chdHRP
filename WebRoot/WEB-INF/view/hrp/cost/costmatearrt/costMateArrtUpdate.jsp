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
        
    });  
     
    function save(){
        var formPara={
	
        mate_type_id:liger.get("mate_type_id").getValue(),
        mate_id:$("#mate_id").val(),
        mate_code:$("#mate_code").val(),
        mate_name:$("#mate_name").val(),
        mate_mode:$("#mate_mode").val(),
        meas_code:$("#meas_code").val(),
        price:$("#price").val(),	
				
        };
        ajaxJsonObjectByUrl("updateCostMateArrt.do",formPara,function(responseData){
            
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
   
    function saveCostMateArrt(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#mate_type_id","../queryMateTypeArrt.do?isCheck=false","id","text",true,true);
    	liger.get("mate_type_id").setValue("${mate_type_id}");	
		liger.get("mate_type_id").setText("${mate_type_name}");	
		
		//$("#mate_type_id").ligerTextBox({disabled: true});

     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <input name="mate_id" type="hidden" id="mate_id" value="${mate_id}"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="mate_type_id" type="text" id="mate_type_id" ltype="text"  value="${mate_type_id}" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料编码：</td>
                <td align="left" class="l-table-edit-td"><input name="mate_code" type="text" id="mate_code" ltype="text"  value="${mate_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">材料名称：</td>
                <td align="left" class="l-table-edit-td"><input name="mate_name" type="text" id="mate_name" ltype="text"  value="${mate_name}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">型号：</td>
                <td align="left" class="l-table-edit-td"><input name="mate_mode" type="text" id="mate_mode" ltype="text"  value="${mate_mode}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位：</td>
                <td align="left" class="l-table-edit-td"><input name="meas_code" type="text" id="meas_code" ltype="text"  value="${meas_code}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
                <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text"  value="${price}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
