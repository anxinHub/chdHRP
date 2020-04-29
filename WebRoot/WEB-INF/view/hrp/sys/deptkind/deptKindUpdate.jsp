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
        $("#kind_code").ligerTextBox({width:180,disabled:true});
    });  
     
    function save(){
        var formPara={
        		group_id:'${group_id}',
                hos_id:'${hos_id}',
        kind_code:$("#kind_code").val(),
        kind_name:$("#kind_name").val(),
        is_stop:$("#is_stop").val(),
        spell_cde:$("#spell_cde").val(),
        note:$("#note").val()
        };
        ajaxJsonObjectByUrl("updateDeptKind.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
           	 //frameElement.dialog.close();
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
   
    function saveDeptKind(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	$("#is_stop").val('${is_stop}');
     }   
    //封装一个限制字数方法
    var checkStrLengths = function (str, maxLength) {
        var maxLength = maxLength;
        var result = 0;
        if (str && str.length > maxLength) {
            result = maxLength;
        } else {
            result = str.length;
        }
        return result;
    }

    //监听输入
    $(".wishContent").on('input propertychange', function () {

        //获取输入内容
        var userDesc = $(this).val();

        //判断字数
        var len;
        if (userDesc) {
            len = checkStrLengths(userDesc, 100);
        } else {
            len = 0
        }

        //显示字数
        $(".wordsNum").html(len + '/100');
    });
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" disabled="disabled" ltype="text"  value="${kind_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>类别名称：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_name" type="text" id="kind_name" ltype="text"  value="${kind_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100">${note }</textarea>
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
