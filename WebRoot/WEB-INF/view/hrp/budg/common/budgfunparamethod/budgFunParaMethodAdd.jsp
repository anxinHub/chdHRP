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
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           para_code:$("#para_code").val(),
            
           para_name:$("#para_name").val(),
            
           para_sql:$("#para_sql").val(),
            
           is_stop:$("#is_stop").val()
            
         };
        
        ajaxJsonObjectByUrl("addBudgFunParaMethod.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='para_code']").val('');
				 $("input[name='para_name']").val('');
				 $("input[name='para_sql']").val('');
				 $("input[name='is_stop']").val('');
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
   
    function saveFunParaMethod(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>参数代码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="para_code" type="text" id="para_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>参数名称:</b></td>
            <td align="left" class="l-table-edit-td"><input name="para_name" type="text" id="para_name" ltype="text" validate="{maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用:</b></td>
            <td align="left" class="l-table-edit-td">
            	<select id="is_stop" name="is_stop" style="width: 135px;" >
	                	<option value="0">否</option>
	                	<option value="1">是</option>	
              	</select>
            </td>
            <td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>取值方法:</b></td>
            <td align="left" class="l-table-edit-td" colspan="4">
            	<textarea rows="15" class="liger-textarea" id="para_sql" name="para_sql"  style="width:440px" validate="{required:true}" ></textarea>
            </td>
            <td align="left"></td>
        </tr>
        <tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><font color="red">举例</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">select
					dept_code as <font color="red">id</font>, dept_name as <font
					color="red">text</font> from aphi_dept <br /> where is_stop=0 AND
					comp_code = {comp_code} AND copy_code = '001' <br /> order by
					dept_code
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 10px;"><font color="red">说明</font>：</td>
				<td align="left" class="l-table-edit-td" colspan="4">1)id text
					必写<br /> 2)变量放在{}中间 常用变量{comp_code} {copy_code}
				</td>
				<td align="left"></td>
			</tr>
    </table>
    </form>
   
    </body>
</html>
