<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
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
            
           is_stop:$("#is_stop").val(),
           
            
           note:$("#note").val(),
           
           spell_code:$("#spell_code").val(),
           
           wbx_code:$("#wbx_code").val()
           
         };
        ajaxJsonObjectByUrl("updateCostCustomDept.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				/* $("input[name='dept_code']").val('');
				 $("input[name='kind_code']").val('');
				 $("input[name='dept_name']").val('');
				 $("input[name='super_code']").val('');
				 $("input[name='udefine_code']").val('');
				 $("input[name='sort_code']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='is_last']").val('');
				 $("input[name='spell_cde']").val('');
				 $("input[name='dept_level']").val('');*/
                //parent.loadTree();
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
   
    function saveGroup(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	 autocomplete("#kind_code","../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true,'',false,'${kind_code}',180);
    	 /* liger.get("kind_code").setValue('${kind_code}');
	     liger.get("kind_code").setText('${kind_name}'); */
         $("#is_stop").val('${is_stop}');
	     
	     $("#spell_code").ligerTextBox({ disabled: true });
	     $("#wbx_code").ligerTextBox({ disabled: true });
     }   
    
    </script>
  
  </head>
  
   <body>
   <div align="left" >
	   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门编码<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" value = "${dept_code}" id="dept_code" ltype="text"  validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>部门名称<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_name" type="text" value = "${dept_name}" id="dept_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                 <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>类别编码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text"  id="kind_code"  validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop"  style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>拼音码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="spell_code" type="text" value = "${spell_code}" id="spell_code" ltype="text" validate="{maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>五笔码:</b></td>
                <td align="left" class="l-table-edit-td"><input name="wbx_code" value = "${wbx_code}" type="text" id="wbx_code"  ltype="text" validate="{maxlength:100}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>备注</b></td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note">${note}</textarea>
                </td>
			</tr>
   		</table>
    </form>
    </div>
    </body>
</html>