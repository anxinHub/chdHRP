<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        $("#sort_code").ligerSpinner({ height: 28, type: 'int',isNegative:false }).setValue(${sort_code});
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        check_type_id:'${check_type_id}',
        group_id:'${group_id}',
		hos_id:'${hos_id}',
		type_codeOld : '${type_code}',
		copy_code:'${copy_code}',		
        type_code:$("#type_code").val(),
        type_name:$("#type_name").val(),
        sort_code:$("#sort_code").val(),
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateAccCheckItemType.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parentFrameUse().loadHead("typegrid");
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
   
    function saveAccCheckItemType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#check_type_id","../queryCheckType.do?isCheck=false","id","text",true,true,{is_sys:0});
    	$("#check_type_id").ligerComboBox({disabled:true});
    	liger.get("check_type_id").setValue('${check_type_id}');
    	liger.get("check_type_id").setText('${check_type_name}');
    	
    	$("#is_stop").val('${is_stop}');
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" value="${type_code}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" value="${type_name}" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类型：</td>
                <td align="left" class="l-table-edit-td"><input name="check_type_id" type="text" id="check_type_id" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号：</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" ltype="text" disabled="disabled"  value="系统生成" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		   <select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
