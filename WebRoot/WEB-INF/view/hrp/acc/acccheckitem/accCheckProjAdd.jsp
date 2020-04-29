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
        		
           proj_id:'',
           
           proj_code:$("#proj_code").val(),
            
           proj_name:$("#proj_name").val(),
            
           type_code:liger.get("proj_type").getValue(),
            
           proj_simple:$("#proj_simple").val(),
            
           sort_code:$("#sort_code").val(),
            
           is_stop:$("#is_stop").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("../../sys/proj/addProj.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 
				 $("input[name='proj_code']").val('');
				 $("input[name='proj_type']").val('');
				 $("input[name='proj_name']").val('');
				 $("input[name='proj_simple']").val('');
				 $("input[name='spel_code']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='note']").val('');
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
   
    function saveProj(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#proj_type","../../sys/queryProjTypeDict.do?isCheck=false","id","text",true,true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目编码<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                  <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目名称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_name" type="text" id="proj_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目简称<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_simple" type="text" id="proj_simple" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目类型<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="proj_type" type="text" id="proj_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color ="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td" colspan="4">
                <textarea rows="6" cols="70" id="note" name="note" ltype="text" validate="{maxlength:50}"></textarea>
				</td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
