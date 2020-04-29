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
        
        loadForm();
        
    });
    //检查编码名称是否重复
    function checkItemCodeOrNameRepeat(){
     	var returnMsg; 
        var formPara={
            
           check_item_id:'${check_item_id}', 
        		
           check_type_id:liger.get("check_type_id").getValue(),
            
           check_item_code:$("#check_item_code").val(),
            
           check_item_name:$("#check_item_name").val()
            
         };
        $.ajax({
       	url:"checkItemCodeOrNameRepeat.do?isCheck=false",
           data:formPara,
           type:"post",
           dataType:"json",
           success:function(responseData){
          	 returnMsg=responseData;
           },
           async:false
        });
        return returnMsg;
    }
     
    function save(){
    	//检查编码或者名称是否重复
    	var repeatJson=checkItemCodeOrNameRepeat();
    	//state有三种状态  0 代表没有数据重复 1代表code重复 2代表name重复 3代表code,name都重复
    	if(repeatJson.state!=0){
    		$.ligerDialog.error(repeatJson.note);
    		return;
    	}
        var formPara={
        check_item_id:'${check_item_id}',
        check_type_id:'${check_type_id}',
        group_id:'${group_id}',
		hos_id:'${hos_id}',
		copy_code:'${copy_code}',
        check_item_code:$("#check_item_code").val(),
        check_item_name:$("#check_item_name").val(),
        sort_code:$("#sort_code").val(),
        spell_code:$("#spell_code").val(),
        wbx_code:$("#wbx_code").val(),
        is_stop:$("#is_stop").val(),
        is_auto:liger.get("is_auto").getValue(),
        type_code :liger.get("type_code").getValue()
        };
        ajaxJsonObjectByUrl("updateAccCheckItem.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parentFrameUse().query();
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
   
    function saveAccCheckItem(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	$("#is_stop").val('${is_stop}');
    	$("#check_type_id").ligerComboBox({disabled:true});
    	liger.get("check_type_id").setValue('${check_type_id}');
    	liger.get("check_type_id").setText('${check_type_name}');
    	
    	autocomplete("#type_code","../queryCheckItemType.do?isCheck=false","id","text",true,true,{check_type_id : '${check_type_id}'});
    	liger.get("type_code").setValue('${type_code}');
    	liger.get("type_code").setText('${type_name}');
    	
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        	<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类型<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="check_type_id" type="text"  id="check_type_id" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项编码<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="check_item_code" type="text" id="check_item_code" ltype="text"  value="${check_item_code}" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算项名称<font color="red">*</font>:</td>
                <td align="left" class="l-table-edit-td"><input name="check_item_name" type="text" id="check_item_name" ltype="text"  value="${check_item_name}" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算分类:</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:false}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">排序号:</td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code"  ltype="text"  value="${sort_code}" validate="{required:true,maxlength:10}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">拼音码:</td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" id="spell_code" ltype="text"  value="${spell_code}" validate="{required:false,maxlength:100}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">五笔码:</td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" type="text" id="wbx_code" ltype="text"  value="${wbx_code}" validate="{required:false,maxlength:100}" /></td>
                <td align="left"><input type="checkbox" id="is_auto" />重新生成</td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用:</td>
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
