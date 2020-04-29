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
        		copy_code:'${copy_code}',
        		acc_year:'${acc_year}',
        cur_code:$("#cur_code").val(),
        cur_name:$("#cur_name").val(),
        cur_num:$("#cur_num").val(),
        cur_rate:$("#cur_rate").val(),
        cur_plan:$("#cur_plan").val(),
        is_self:$("#is_self").val(),
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateAccCur.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccCur(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	$("#is_stop").val('${is_stop}');
    	$("#is_self").val('${is_self}');
    	$("#cur_plan").val('${cur_plan}');
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_code" disabled="disabled" readonly="readonly" type="text" id="cur_code" ltype="text"  value="${cur_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种名称：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_name" type="text" id="cur_name" ltype="text"  value="${cur_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">汇率小数位：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_num" type="text" id="cur_num" ltype="text"  value="${cur_num}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间汇率：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_rate" type="text" id="cur_rate" ltype="text"  value="${cur_rate}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折算方式：</td>
                <td align="left" class="l-table-edit-td">
               		<select id="cur_plan" name="cur_plan" style="width: 135px;">
			                		<option value="0">原币*汇率 = 本位币</option>
			                		<option value="1">原币/汇率 = 本位币</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否本币：</td>
                <td align="left" class="l-table-edit-td">
               		<select id="is_self" name="is_self" style="width: 135px;">
			                		<option value="0">是</option>
			                		<option value="1">否</option>
                	</select>
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
