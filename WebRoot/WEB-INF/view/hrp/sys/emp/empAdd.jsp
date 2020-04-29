<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            emp_id:'',
           emp_code:$("#emp_code").val(),
            
           emp_name:$("#emp_name").val(),
            
           dept_no:liger.get("dept").getValue().split(".")[1],
            
           dept_id:liger.get("dept").getValue().split(".")[0],
            
           kind_code:liger.get("kind_code").getValue(),
            
           sort_code:$("#sort_code").val(),
            
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addEmp.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='emp_code']").val('');
				 $("input[name='emp_name']").val('');
				 $("input[name='dept']").val('');
				 $("input[name='kind_code']").val('');
				// $("input[name='sort_code']").val('');
				 $("input[name='is_stop']").val('');
				 $("#note").val('');
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
   
    function saveEmp(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#dept","../queryDeptDictLast.do?isCheck=false","id","text",true,true);
            
         autocomplete("#kind_code","../queryEmpKindDict.do?isCheck=false","id","text",true,true);
         
         $("#sort_code").ligerTextBox({width:180,disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		<input type="hidden" id="dept_id" name="dept_id"/>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="emp_name" type="text" id="emp_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color ="red">*</font>:</b></td>
			    <td align="left" class="l-table-edit-td">
			                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
			    </td>
			    <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>职工分类:</b></td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:false}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept" type="text" id="dept" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
			    <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
			    <td align="left" class="l-table-edit-td" colspan="4">
			          <textarea rows="3" cols="60" id="note" name="note" ltype="text" validate="{maxlength:50}"></textarea>
			    </td>
			    <td align="left"></td>
			</tr> 

        </table>
    </form>
   
    </body>
</html>
