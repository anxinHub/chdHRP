<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
    	 
        loadDict()//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
         
        var formPara={

           wage_item_code:$("#wage_item_code").val(),
            
           wage_item_name:$("#wage_item_name").val(),
            
           is_stop:liger.get("is_stop").getValue(),
            
           remark:$("#remark").val()
         };
        
		ajaxJsonObjectByUrl("addHtcWageItemDict.do",formPara,function(responseData){
            
			if(responseData.state=="true"){
				$("input[name='wage_item_code']").val('');
				$("input[name='wage_item_name']").val('');
				/* liger.get("is_stop").setValue(''); */
				$("input[name='remark']").val('');
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
   
    function saveWageItemDict(){
    	
        if($("form").valid()){
        	
            save();
            
        }
        
   	}
	function loadDict(){
    	//字典下拉框
		 autocomplete("#is_stop", "../../../info/base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true,"",true,'0');       
	} 
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"  style="margin-left: 30px"> 

			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工资项编码：</td>
				<td align="left" class="l-table-edit-td"><input
					name="wage_item_code" type="text" id="wage_item_code" ltype="text"
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工资项名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="wage_item_name" type="text" id="wage_item_name" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td">
					<input name="is_stop" type="text" id="is_stop" ltype="text"/>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding: 0px 0px 80px 20px">备注：</td>
				<td align="left" class="l-table-edit-td" colspan="2"> 
                <textarea cols="100" rows="4" class="l-textarea" id="remark" name="remark" style="width:300px;resize:none" validate="{maxlength:200}"></textarea>
                </td> 
				<td align="left"></td>
			</tr>
			

		</table>
	</form>

</body>
</html>
