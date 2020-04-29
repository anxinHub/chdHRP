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
	       $.post("getRules.do?isCheck=false",null,function(responseData){
		  		 $("#rules").val(responseData)
		  		 $("#font2").text(responseData);
        });
     });  
     
     function  save(){
        var formPara={
            
           dept_code:$("#dept_code").val(),
            
           kind_code:liger.get("kind_code").getValue(),
            
           dept_name:$("#dept_name").val(),
            
           udefine_code:$("#udefine_code").val(),
            
           sort_code:$("#sort_code").val(),
            
           is_stop:$("#is_stop").val(),
            
           is_last:'1',
            
           note:$("#note").val(),
           
           rules:$("#rules").val(),
         };
        
        ajaxJsonObjectByUrl("addDept.do",formPara,function(responseData){
        	var dept_code = $("#dept_code").val();
            if(responseData.state=="true"){
				 $("input[name='dept_code']").val('');
				 $("input[name='kind_code']").val('');
				 $("input[name='dept_name']").val('');
				 $("input[name='udefine_code']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='spell_cde']").val('');

				 parentFrameUse().query();
	             dialog.close();
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
   
    function saveDept(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#kind_code","../deptKind/queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	 $("#sort_code").ligerTextBox({width:180,disabled:true});
    	 $("#is_last").ligerTextBox({width:180,disabled:true});
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
   <input type="hidden" id="rules" name="rules" />
   <form name="form1" method="post"  id="form1" >
   <font id="font1">编码规则：<font id="font2" color="red"></font></font><hr/>
   <input type="hidden" id="rules" name="rules" />
   <div id="panel1-1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>类别编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_name" type="text" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>自定义编码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="udefine_code" type="text" id="udefine_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
                  </tr>
                  <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>排序号<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" id="sort_code" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
           
           
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
                </td>
                <td align="left"></td>
           </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否末级<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last" style="width: 135px;">
			                		<!-- <option value="0">否</option> -->
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            
           
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>备注:</b></td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>
              <!--  <span class="wordsNum">0/100</span> -->
                </td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
