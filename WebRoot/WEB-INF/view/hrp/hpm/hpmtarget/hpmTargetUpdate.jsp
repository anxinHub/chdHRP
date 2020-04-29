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
        $("#target_code").ligerTextBox({width:160,disabled: true});
        $("#target_name").ligerTextBox({width:240});
        $("#target_nature").ligerTextBox({width:160});
        $("#target_note").ligerTextBox({width:240});
        $("#is_stop").ligerComboBox({width:160 });
        
        liger.get("target_nature").setValue("${target_nature_code}");
        liger.get("target_nature").setText("${target_nature}");
        liger.get("is_stop").setValue("${is_stop}");
        
    });  
     
    function save(){
    	
        var formPara={
        target_code:$("#target_code").val(),
        target_name:$("#target_name").val(),
        target_nature:liger.get("target_nature").getValue(),
        target_note:$("#target_note").val(),
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateHpmTarget.do",formPara,function(responseData){
            
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
   
    function saveHpmTarget(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#target_nature","../queryTargetNatureDict.do?isCheck=false","id","text",true,true);
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text"  value="${target_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left" style="padding-left:20px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
                <td align="left" class="l-table-edit-td"><input name="target_name" type="text" id="target_name" ltype="text"  value="${target_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"></td>
                <td align="left" style="padding-left:20px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标性质：</td>
                <td align="left" class="l-table-edit-td"><input name="target_nature" type="text" id="target_nature" ltype="text"  value="${target_nature}" validate="{required:true,maxlength:20}" /></td>
                <td align="left" style="padding-left:20px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标描述：</td>
                <td align="left" class="l-table-edit-td"><input name="target_note" type="text" id="target_note" ltype="text"  value="${target_note}"  /></td>
                <td align="left"></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"></td>
                <td align="left" style="padding-left:20px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
                <td align="left" class="l-table-edit-td"></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                    	<select name="is_stop" id="is_stop">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
