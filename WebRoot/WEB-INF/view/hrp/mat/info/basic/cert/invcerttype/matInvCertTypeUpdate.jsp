<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
   <jsp:include page="${path}/inc.jsp"/>
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
        
    });  
     
    function save(){
		if(!liger.get("validity_type").getValue()){
			$.ligerDialog.warn("效期类型不能为空！");  
			return false;
		}
		
		if($("#is_alarm").val() == 1 && $("#war_days").val() == ""){
			$.ligerDialog.warn("预警天数不能为空！");  
			return false;
		}
		if($("#is_alarm").val() == 1 && $("#war_days").val() <= 0){
			$.ligerDialog.warn("预警天数不能小于0！");  
			return false;
		}
        var formPara={
       		type_id : '${type_id}',
	        type_code:$("#type_code").val(),
	        type_name:$("#type_name").val(),
			validity_type :  liger.get("validity_type").getValue(), 
	        is_alarm:$("#is_alarm").val(),
	        war_days:$("#war_days").val()
        };
        ajaxJsonObjectByUrl("updateMatInvCertType.do",formPara,function(responseData){
            
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
   
    function saveMatInvCertType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        $("#is_alarm").val("${is_alarm}");
		autoCompleteByData("#validity_type", matInvCertType_validityType.Rows, "id", "text", true, true, "", false, "${validity_type}");
	
		$("#type_code").ligerTextBox({width:180, disabled:true});

    }   
    
	function changeState(){
		
		if($("#is_alarm").val() == 1){
			$("#war_days").ligerTextBox({width:180, disabled:false});
		}else if($("#is_alarm").val() == 0){
			$("#war_days").ligerTextBox({width:180, disabled:true});
			$("#war_days").val(""); 
		}
	}
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
	        <tr>   
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件类型编码<font color ="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" value="${type_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件类型名称<font color ="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" value="${type_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>效期类型<font color ="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="validity_type" type="text" id="validity_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr>
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否预警<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td">
	            	<select name="is_alarm" id="is_alarm"style="width: 135px;" onchange="changeState()">
	                		<option value="0">否</option>
	                		<option value="1">是</option>
	            	</select>
	            </td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预警天数:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="war_days" type="text" id="war_days" value="${war_days}" ltype="text" validate="{digits:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
        </table>
    </form>
    </body>
</html>