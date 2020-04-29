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
var dialog = frameElement.dialog;
    var dataFormat;
    
    $(function (){
    
    	
    	   $.post("getRules.do?isCheck=false",null,function(responseData){
    	   		 $("#rules").val(responseData)
    	   		 $("#font2").text(responseData);
    	        }); 
         $("#dept_code").ligerTextBox({width:180,disabled:true});
    	$("#super_code").ligerTextBox({width:180,disabled:true});
    	$("#is_last").ligerTextBox({width:180,disabled:true});
    	$("#dept_level").ligerTextBox({width:180,disabled:true});
        loadDict();
        
        loadForm();
        
        $("#dept_code").blur(function(){
        	var map ={
        			dept_code:$("#dept_code").val(),
        			rules:$("#rules").val()
        		};
       /*  	ajaxJsonObjectByUrl("getSuperCode.do",map,function(responseData){
   	    		$("#super_code").val(responseData.super_code);
   	    		$("#dept_level").val(responseData.dept_level);
        	}); */
        })
    });  
    
    function  save(){
    	var dept_id = ${dept_id};
    	var kind_code ;
    	if($("#kind_code").val() != null && $("#kind_code").val() != ''){
    		kind_code = liger.get("kind_code").getValue();
    	}else{
    		kind_code = '';
    	}
    	  var formPara={
    	           dept_id : dept_id ,
    	           dept_codeOld:'${dept_code}',
    	           dept_code:$("#dept_code").val(),
    	            
    	           kind_code:liger.get("kind_code").getValue(),
    	            
    	           dept_name:$("#dept_name").val(),
    	            
    	           super_code:$("#super_code").val(),
    	            
    	           udefine_code:$("#udefine_code").val(),
    	            
    	           sort_code:$("#sort_code").val(),
    	            
    	           is_disable:$("#is_disable").val(),
    	            
    	           is_last:$("#is_last").val(),
    	            
    	           note:$("#note").val(),
    	            
    	           dept_level:$("#dept_level").val(),
    	           
    	           spell_code:$("#spell_code").val(),
    	           
    	           wbx_code:$("#wbx_code").val(),
    	           
    	           history: liger.get("history").getValue(),
    	           
    	           is_auto: liger.get("is_auto").getValue(),
    	           ol_kind_code :'${kind_code}'
    	         };
    	if(liger.get("kind_code").getValue()!='${kind_code}'&&'${super_code}'!=0){
    		$.ligerDialog.error('与上级类别不一致，不允许修改！');
    		/*  $.ligerDialog.confirm('末级不允许修改类别?', function (yes){
             	if(yes){
             		
             	} */
    	}else if(liger.get("kind_code").getValue()!='${kind_code}'&&'${super_code}'==0){
    		$.ligerDialog.confirm('修改类别会修改下级类别是否修改?', function (yes){
             	if(yes){
                    ajaxJsonObjectByUrl("updateDept.do",formPara,function(responseData){
                        
                        if(responseData.state=="true"){
            				/* $("input[name='dept_code']").val('');
            				 $("input[name='kind_code']").val('');
            				 $("input[name='dept_name']").val('');
            				 $("input[name='super_code']").val('');
            				 $("input[name='udefine_code']").val('');
            				 $("input[name='sort_code']").val('');
            				 $("input[name='is_disable']").val('');
            				 $("input[name='is_last']").val('');
            				 $("input[name='spell_cde']").val('');
            				 $("input[name='dept_level']").val('');*/
                           // parent.loadTree();
            			     
	                            var parentFrameName = parent.$.etDialog.parentFrameName;
	                            var parentWindow = parent.window[parentFrameName];
	                            parentWindow.query(); 
	                            dialog.close();
	                           
                            
                        }
                    });
             	} 
    		 }); 
    	}else{
    	       ajaxJsonObjectByUrl("updateDept.do",formPara,function(responseData){
                   
                   if(responseData.state=="true"){
       				/* $("input[name='dept_code']").val('');
       				 $("input[name='kind_code']").val('');
       				 $("input[name='dept_name']").val('');
       				 $("input[name='super_code']").val('');
       				 $("input[name='udefine_code']").val('');
       				 $("input[name='sort_code']").val('');
       				 $("input[name='is_disable']").val('');
       				 $("input[name='is_last']").val('');
       				 $("input[name='spell_cde']").val('');
       				 $("input[name='dept_level']").val('');*/
                       //parent.loadTree();
       				parentFrameUse().query();
       				 dialog.close();
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
    	 autocomplete("#kind_code","../deptKind/queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	 liger.get("kind_code").setValue('${kind_code}');
	     liger.get("kind_code").setText('${kind_name}');
        // autocomplete("#super_code","../queryDept.do?isCheck=false","id","text",true,true);
        // liger.get("super_code").setValue('${super_code}');
        // liger.get("super_code").setText('${dept_name}');
          $("#is_disable").val('${is_disable}');
         $("#is_last").val('${is_last}'); 
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
   <div align="left" >
	   <form name="form1" method="post"  id="form1" >
	   	<input type="hidden" id="dept_id" name="dept_id"/>
	
	   	   <font id="font1">编码规则：<font id="font2" color="red"></font></font><hr/>
   <input type="hidden" id="rules" name="rules" />
   <div id="panel1-1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" value = "${dept_code}" id="dept_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>类别编码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text"  id="kind_code"  validate="{maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>部门名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_name" type="text" value = "${dept_name}" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>上级编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="super_code"  value = "${super_code}" type="text" id="super_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>部门级次<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_level" type="text"  value = "${dept_level}" id="dept_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>自定义编码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="udefine_code" type="text" value = "${udefine_code}" id="udefine_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>排序号<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="sort_code" type="text" value = "${sort_code}" id="sort_code" ltype="text" validate="{required:true,digits:true ,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_disable" name="is_disable"  style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>是否末级<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_last" name="is_last"  style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" value = "${spell_code}" id="spell_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" value = "${wbx_code}" type="text" id="wbx_code" ltype="text" validate="{maxlength:20}"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"></td>
			<td align="left" class="l-table-edit-td"><input name="is_auto" id="is_auto" type="checkbox" />是否自动生成拼音码、五笔码</td>
            <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>备注</b></td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100">${note}</textarea>
                </td>
                <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:10px;"></td>
			<td align="left" class="l-table-edit-td"><input name="history" id="history" type="checkbox" />是否保留历史记录</td>
            <td align="left"></td>
			</tr>
   		</table>
    </form>
    </div>
    </body>
</html>