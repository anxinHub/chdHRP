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
     var dialog = frameElement.dialog;
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 if(liger.get("type_code").getValue() == ""){
    		 $.ligerDialog.error("部门类型不能为空!");
      		return false;
    	 }
    	 
         
        var formPara={
        	//is_stop:$("#is_stop").val(),
        	kind_code:liger.get("kind_code").getValue(),
        	//super_code:liger.get("super_code").getValue(),
        	dept_name:$("#dept_name").val(),
        	dept_code:$("#dept_code").val(),
            dept_id:'${dept_id}',
            type_code:liger.get("type_code").getValue(),
            natur_code:liger.get("natur_code").getValue(),
            para_code:liger.get("para_code").getValue()
//             out_code:liger.get("out_code").getValue(),
//             emp_id:liger.get("emp_code").getValue().split(".")[0],
//             is_manager:$("#is_manager").val(),
//             is_stock:$("#is_stock").val()  
            
         };
        
        ajaxJsonObjectByUrl("../costdeptdict/updateAccDeptAttr.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parent.query('${tree_code}');
            	parent.loadtree();
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
   
    function saveAccDeptAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    		//autocomplete("#super_code","../../sys/queryDept.do?isCheck=false","id","text",true,true,'',false,'${super_code}');
    		
    		autocomplete("#kind_code","../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true,'',false,'${kind_code}');
            
            autocomplete("#type_code","../../acc/queryDeptType.do?isCheck=false","id","text",true,true,'',false,'${type_code}');

            autocomplete("#natur_code","../../acc/queryDeptNatur.do?isCheck=false","id","text",true,true,'',false,'${natur_code}');
            autocomplete("#para_code", "../queryParaNaturLast.do?isCheck=false", "id","text", true,true,'', false,'${para_code}');
            //autocomplete("#out_code","../../acc/queryDeptOut.do?isCheck=false","id","text",true,true,'',false,'${out_code}');

            //autocomplete("#emp_code","../../sys/queryEmp.do?isCheck=false","id","text",true,true,'',false,'${emp_code}');

//             $("#is_stop").ligerComboBox({
//                 width : 200,
//                 data: [
//                     { text: '否', id: 0 },
//                     { text: '是', id: 1 }
//                 ],  
//                 value: 'bj',
//                 initIsTriggerEvent: false
//             });
//             liger.get("is_stop").setValue("${is_stop}");
//             if('${is_stop}' == 0){
//             	liger.get("is_stop").setText("否");
//             }else{
//             	liger.get("is_stop").setText("是");
//             }
//             $("#is_stop").ligerTextBox({ disabled: true });
//             $("#is_manager").val("${is_manager}");
//             $("#is_stock").val("${is_stock}");
            $("#dept_code").ligerTextBox({ disabled: true });
            $("#dept_name").ligerTextBox({ disabled: true });
            $("#kind_code").ligerTextBox({ disabled: true });
            var is_last = '${is_last}';
            
            if(is_last == 0){//如果不是末级 不允许修改
            	$("#type_code").ligerTextBox({ disabled: true });
                $("#natur_code").ligerTextBox({ disabled: true });
            }
//             $("#super_code").ligerTextBox({ disabled: true });
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门编码：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" value="${dept_code }" type="text" disabled="disabled" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_name" value="${dept_name }" type="text" disabled="disabled" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" disabled="disabled" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text"  id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none">部门性质：</td> 
                <td align="left" class="l-table-edit-td"><input name="natur_code" type="text"  id="natur_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        	<tr>
        		 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none">分摊性质：</td> 
                <td align="left" class="l-table-edit-td"><input name="para_code" type="text"  id="para_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
        	</tr>
        
<!--        		 <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门编码：</td> -->
<%--                 <td align="left" class="l-table-edit-td"><input name="dept_code" value="${dept_code }" type="text" disabled="disabled" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td> --%>
<!--                 <td align="left"></td> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td> -->
<%--                 <td align="left" class="l-table-edit-td"><input name="dept_name" value="${dept_name }" type="text" disabled="disabled" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td> --%>
<!--                 <td align="left"></td> -->
<!--             </tr>  -->
<!--             <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级部门：</td> -->
<!--                 <td align="left" class="l-table-edit-td"><input name="super_code" type="text"  disabled="disabled" id="super_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left"></td> -->
<!--                  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td> -->
<!--                 <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" disabled="disabled" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left"></td> -->
<!--             </tr>  -->
<!--             <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td> -->
<!--                 <td align="left" class="l-table-edit-td"> -->
<!--               		<input name="is_stop" type="text"  id="is_stop" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}"/> -->
<!--                 </td> -->
<!--                  <td align="left"></td> -->
<!--                   <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td> -->
<!--                 <td align="left" class="l-table-edit-td"><input name="type_code" type="text"  id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left"></td> -->
<!--             </tr> -->
<!--             <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none"></td> -->
<!--                 <td align="left" class="l-table-edit-td" style="display: none"><input name="nature_code" type="text"  id="nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left"></td> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td> -->
<!--                 <td align="left" class="l-table-edit-td" style="display: none"><input name="out_code" type="text"  id="out_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left" style="display: none"></td> -->
<!--             </tr>  -->
<!--             <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none"></td> -->
<!--                 <td align="left" class="l-table-edit-td" style="display: none"> -->
<!--                 	<select id="is_manager" name="is_manager" style="width: 135px;"> -->
<!-- 			                		<option value="0">是</option> -->
<!-- 			                		<option value="1">否</option> -->
<!--                 	</select> -->
<!--                 </td> -->
<!--                 <td align="left"></td> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none"></td> -->
<!--                 <td align="left" class="l-table-edit-td" style="display: none"> -->
<!--                		 <select id="is_stock" name="is_stock" style="width: 135px;"> -->
<!-- 			                		<option value="0">是</option> -->
<!-- 			                		<option value="1">否</option> -->
<!--                 	</select> -->
<!--                 </td> -->
<!--                 <td align="left"></td> -->
<!--             </tr>  -->
<!-- 			 <tr> -->
<!--                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;" style="display: none"></td> -->
<!--                 <td align="left" class="l-table-edit-td" style="display: none"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td> -->
<!--                 <td align="left" style="display: none"></td> -->
<!--             </tr>  -->
        </table>
    </form>
   
    </body>
</html>
