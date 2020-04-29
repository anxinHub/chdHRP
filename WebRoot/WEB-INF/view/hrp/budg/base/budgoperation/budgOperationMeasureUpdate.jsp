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
<script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
      
       
        measure_code:$("#measure_code").val(),
        measure_name:$("#measure_name").val(),
        is_stop:liger.get("is_stop").getValue(),
        };
        ajaxJsonObjectByUrl("updateBudgOperationMeasure.do",formPara,function(responseData){
            
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
   
    function updateBudgOperationMeasure(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	 autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_stop}");
    	 $("#measure_code").ligerTextBox({disabled:true});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">运营尺度编码：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_code" type="text" id="measure_code" ltype="text"  validate="{required:true,maxlength:20}"  disabled="disabled" value="${measure_code}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">运营尺度名称：</td>
            <td align="left" class="l-table-edit-td"><input name="measure_name" type="text" id="measure_name" ltype="text" value="${measure_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             </tr> 
             <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" value="${is_stop}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             </tr>
            
       
			
        </table>
    </form>
    </body>
</html>
