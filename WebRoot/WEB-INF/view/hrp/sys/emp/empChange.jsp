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
<link href="<%=path%>/lib/htc/style/select/chosen.css" rel="stylesheet">
<script src="<%=path%>/lib/htc/style/select/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
    });  
     
    function save(){
        if($("#codeTable").is(':hidden') && $("#nameTable").is(':show')){//名称变更
        	var formPara2 = {
        			emp_no:'0',
        			emp_id:'${emp_id}',
        			group_id:'${group_id}',
        			hos_id:'${hos_id}',
        			emp_code:'${emp_code}',
        			emp_name:$("#new_emp_name").val(),
                    is_stop:0,
                    note:$("#note2").val(),
                    dictType:1
            };
        	ajaxJsonObjectByUrl("addEmpDict.do",formPara2,function (responseData){
				if(responseData.state=="true"){
        			parent.query();
        		}
     		});
        }else{//编码变更
        	var formPara1 = {
        			emp_no:'0',
        			emp_id:'${emp_id}',
    		        group_id:'${group_id}',
    		        hos_id:'${hos_id}',
    		        emp_code:$("#new_emp_code").val(),
    		        emp_name:'${emp_name}',
    		        is_stop:0,
    		        note:$("#note1").val(),
    		        dictType:0
            };
        	ajaxJsonObjectByUrl("addEmpDict.do",formPara1,function (responseData){
        		if(responseData.state=="true"){
        			parent.query();
        		}
     		});
        }
       
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
   
    function saveGroup(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        
     }   
    
    function update(state){
    	if(state == 0){
    		$("#codeTable").show();
    		$("#nameTable").hide();
    	}else{
    		$("#codeTable").hide();
    		$("#nameTable").show();
    	}
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div align="center" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
                	<div class="l-button" style="width: 60px;  margin-right: 40px;margin-left: 18px;" ligeruiid="Button1000" onclick="update(0);">
   					<span>编码变更</span></div>
                </td>
                <td align="left" class="l-table-edit-td">
                	<div class="l-button" style="width: 60px; " ligeruiid="Button1001" onclick="update(1);">
   					<span>名称变更</span></div>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
   </div>
   <div align="center" >
   <form name="form" method="post"  id="form" >
        <table cellpadding="0" cellspacing="0" id="codeTable" class="l-table-edit" style="display: inline;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">旧编码：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" disabled="disabled" id="emp_code" ltype="text"  value="${emp_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新编码：</td>
                <td align="left" class="l-table-edit-td"><input name="new_emp_code" type="text" id="new_emp_code" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更原因：</td>
                <td align="left" class="l-table-edit-td"><textarea rows="3" cols="30" id="note1" name="note1"></textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
        
        
        <table cellpadding="0" cellspacing="0" id="nameTable" class="l-table-edit" style="display: none;">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">旧名称：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_name" disabled="disabled" type="text" id="emp_name" ltype="text"  value="${emp_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新名称：</td>
                <td align="left" class="l-table-edit-td"><input name="new_emp_name"  type="text" id="new_emp_name" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">变更原因：</td>
                <td align="left" class="l-table-edit-td"><textarea rows="3" cols="30" id="note2" name="note2"></textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </div>
    </body>
</html>
