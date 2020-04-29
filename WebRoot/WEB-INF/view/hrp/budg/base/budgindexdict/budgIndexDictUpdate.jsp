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
        		 index_code:$("#index_code").val(),
                 
                 index_name:$("#index_name").val(),
                  
                 unit:$("#unit").val(),
                  
                 data_precision:$("#data_precision").val(),
                  
                 data_nature:liger.get("data_nature").getValue(),
                  
                 is_carried:liger.get("is_carried").getValue(),
                  
                 is_stop:liger.get("is_stop").getValue(),
                  
        };
        ajaxJsonObjectByUrl("updateBudgIndexDict.do",formPara,function(responseData){
            
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
   
    function updateBudgIndexDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        //字典下拉框
        autoCompleteByData("#is_stop", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_stop}");
        autoCompleteByData("#is_carried", yes_or_no.Rows, "id", "text", true, true,"",false,"${is_carried}");
       	                                  
       	 autocomplete("#data_nature","../../queryBudgDataNature.do?isCheck=false","id","text",true,true,"",false,"${data_nature}");
       	$("#is_stop").ligerTextBox({width:180});
       	$("#is_carried").ligerTextBox({width:180});
       	  $("#data_nature").ligerTextBox({width:180});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>指标编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" disabled="disabled" value="${index_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>指标名称<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="index_name" type="text" id="index_name" ltype="text" value="${index_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>数据性质<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="data_nature" type="text" id="data_nature" ltype="text" value="${data_nature}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>数据精度<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="data_precision" type="text" id="data_precision" ltype="text" value="${data_precision}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否结转<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="is_carried" type="text" id="is_carried" ltype="text" value="${is_carried}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" value="${is_stop}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量单位：</td>
            <td align="left" class="l-table-edit-td"><input name="unit" type="text" id="unit" ltype="text" value="${unit}" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
			
        </table>
    </form>
    </body>
</html>
