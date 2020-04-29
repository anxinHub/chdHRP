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
    
    var tell_Data = [{ id: '01', text: '收入' }, { id: '02', text: '支出'}];
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        		group_id:'${group_id}',
        		hos_id:'${hos_id}',
        		copy_code:'${copy_code}',
		        tell_type_code:$("#tell_type_code").val(),
		        tell_type_name:$("#tell_type_name").val(),
		        is_stop:$("#is_stop").val(),
		        kind_code:liger.get("kind_code").getValue(),
		        vouch_type_code:liger.get("vouch_type_code").getValue()
        };
        ajaxJsonObjectByUrl("updateAccCashierType.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccCashierType(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
        $("#kind_code").ligerComboBox({
             selectBoxWidth: 180,
             autocomplete: true,
             width: 160,
             data:tell_Data
         });
    	$("#is_stop").val('${is_stop}');
    	
    	if('${kind_code}'=='01'){
    		liger.get("kind_code").setValue('${kind_code}');
    		liger.get("kind_code").setText('收入');
    	}else if('${kind_code}'=='02'){
    		liger.get("kind_code").setValue('${kind_code}');
    		liger.get("kind_code").setText('支出');
    	}

   	 autocomplete("#vouch_type_code", "../queryVouchType.do?isCheck=false","id", "text", true, true);
   	 
   	liger.get("vouch_type_code").setValue('${vouch_type_code}');
	liger.get("vouch_type_code").setText('${vouch_type_name}');
	
	$("#tell_type_code").ligerTextBox({disabled:true});

     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出纳类型编码：</td>
                <td align="left" class="l-table-edit-td"><input name="tell_type_code" disabled="disabled" type="text" id="tell_type_code" ltype="text"  value="${tell_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出纳类型名称：</td>
                <td align="left" class="l-table-edit-td"><input name="tell_type_name" type="text" id="tell_type_name" ltype="text"  value="${tell_type_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出纳收支类型：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="kind_code" type="text" id="kind_code" ltype="text"  />
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证类型：</td>
                <td align="left" class="l-table-edit-td">
                	<input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"  />
                </td>
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
