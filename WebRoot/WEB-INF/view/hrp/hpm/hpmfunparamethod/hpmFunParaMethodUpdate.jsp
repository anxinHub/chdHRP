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
        var formPara={
        para_code:$("#para_code").val(),
        para_name:$("#para_name").val(),
        para_sql:$("#para_sql").val()
        };
        ajaxJsonObjectByUrl("updateHpmFunParaMethod.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                //$("input[name='para_code']").val('');
                //$("input[name='para_name']").val('');
                //$("input[name='para_sql']").val('');
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
   
    function saveHpmFunParaMethod(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
    	
    	$("#para_code").ligerTextBox({ disabled: true});
 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;">参数代码：</td>
                <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" value="${para_code}" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;">参数名称：</td>
                <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" value="${para_name}" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>        
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">取值方法：</td>
	            <td align="left" class="l-table-edit-td" colspan="4">
	                	<textarea rows="8" class="liger-textarea" id="para_sql" name="para_sql" style="width:440px" validate="{required:true}" >${para_sql}</textarea>
	            </td>
	            <td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><font color="red">举例</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">select
					dept_id as <font color="red">id</font>, dept_name as <font
					color="red">text</font> from aphi_dept <br /> where is_stop=0 AND
					group_id = {group_id} AND hos_id = {hos_id} AND copy_code = {copy_code} <br /> order by
					dept_code 
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 10px;"><font color="red">说明</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">1)id text
					必写<br /> 2)变量放在{}中间 常用变量{group_id} {hos_id} {copy_code}
				</td>
				<td align="left"></td>
			</tr>
        </table>
    </form>
    </body>
</html>
