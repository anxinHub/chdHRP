<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc.jsp"/>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
    	
        var pack_name;
		
		if($("#pack_name").val() == '${pack_name}'){
			
			pack_name = '';
			
		}else{
			pack_name = $("#pack_name").val();
			
		}
    	
    	
        var formPara={
        pack_code:$("#pack_code").val(),
        pack_name:pack_name,
        is_stop:$("#is_stop").val(),
        note:$("#note").val()
        };
        ajaxJsonObjectByUrl("updateHosPackage.do",formPara,function(responseData){
            
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
   
    function saveHosPackage(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        $("#is_stop").val("${is_stop}");
        $("#pack_code").ligerTextBox({width:160,disabled:true});
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr >
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>包装单位编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="pack_code" type="text" id="pack_code" disabled="disabled" ltype="text" value="${pack_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
       
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>包装单位名称<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="pack_name" type="text" id="pack_name" ltype="text" value="${pack_name}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td">
            <select name="is_stop" id="is_stop"style="width: 135px;" >
                		<option value="0">否</option>
                		<option value="1">是</option>
            	</select>
            </td>
            <td align="left"></td>
           
        </tr> 
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note}" validate="{maxlength:50}" /></td>
            <td align="left"></td>
        </tr>
			
        </table>
    </form>
    </body>
</html>
