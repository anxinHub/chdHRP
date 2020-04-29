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
        
        $("#cash_item_code").ligerTextBox({ disabled: true});
        
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        cash_item_id:'${cash_item_id}',
        cash_type_id:'${cash_type_id}',
        group_id:'${group_id}',		
		hos_id:'${hos_id}',		
		copy_code:'${copy_code}',		
		cash_dire:$("#cash_dire").val(),
        cash_item_code:$("#cash_item_code").val(),
        cash_item_name:$("#cash_item_name").val(),
				
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateAccCashItem.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query('${cash_type_id}');
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
   
    function saveAccCashItem(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	$("#is_stop").val('${is_stop}');
        $("#cash_dire").val('${cash_dire}');
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">现金流量项目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_item_code" disabled="disabled" type="text" id="cash_item_code" ltype="text"  value="${cash_item_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">现金流量项目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_item_name" type="text" id="cash_item_name" ltype="text"  value="${cash_item_name}" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方向：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="cash_dire" name="cash_dire" style="width: 135px;">
			                		<option value="0">流入</option>
			                		<option value="1">流出</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
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
