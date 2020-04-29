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
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        group_id:'${group_id}',
        hos_id:'${hos_id}',
        source_id:'${source_id}',
        source_code:$("#source_code").val(),
        source_name:$("#source_name").val(),
        source_attr:liger.get("source_attr").getValue(),
        is_stop:liger.get("is_stop").getValue(),
        note:$("#note").val()
        };
        ajaxJsonObjectByUrl("updateAssSource.do",formPara,function(responseData){
            
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
   
    function saveSource(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#source_attr","../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true); 
    	liger.get("source_attr").setValue("${source_attr}");
        liger.get("source_attr").setText('${source_attr} ${nature_name}');
        
        $('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 180
	});
		liger.get("is_stop").setValue("${is_stop}");
    	
    	$("#note").val('${note}');
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源编码：</td>
                <td align="left" class="l-table-edit-td"><input name="source_code" type="text" id="source_code" disabled="disabled" ltype="text"  value="${source_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源名称：</td>
                <td align="left" class="l-table-edit-td"><input name="source_name" type="text" id="source_name" ltype="text"  value="${source_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金性质：</td>
                <td align="left" class="l-table-edit-td"><input name="source_attr" type="text"  id="source_attr" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                </tr>
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:20}"/>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note"></textarea>
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
