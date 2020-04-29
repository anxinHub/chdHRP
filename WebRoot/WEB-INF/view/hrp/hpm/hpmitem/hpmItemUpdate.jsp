<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	 var dataFormat;
     $(function (){

		loadDict();
		
     	loadForm();

	
		if('${is_avg}'=='1'){
    	   liger.get("is_avg").setValue(1);
     	liger.get("is_avg").setText("是");	
     		
     	}
     	
     	if('${is_avg}'=='0'){
     		liger.get("is_avg").setValue(0);
     		liger.get("is_avg").setText("否");	
     			
     	}
     	
     	if('${is_two_audit}'=='1'){
     	   	liger.get("is_two_audit").setValue(1);
      		liger.get("is_two_audit").setText("是");	
      		
      	}
      	
      	if('${is_two_audit}'=='0'){
      		liger.get("is_two_audit").setValue(0);
      		liger.get("is_two_audit").setText("否");	
      			
      	}
     	
     	
     	if('${is_sum}'=='1'){
     	   liger.get("is_sum").setValue(1);
      		liger.get("is_sum").setText("是");	
      		
      	}
      	
      	if('${is_sum}'=='0'){
      		liger.get("is_sum").setValue(0);
      		liger.get("is_sum").setText("否");	
      			
      	}
     	
     	var is_stop = ${is_stop};
     	if(is_stop==1){
     		liger.get("is_stop").setValue(1);
     		liger.get("is_stop").setText("是");	
     			
     	}
     	
     	if(is_stop==0){
     		liger.get("is_stop").setValue(0);
     		liger.get("is_stop").setText("否");	
     			
     	}
     	
     });  
     
     function  save(){
    	 
     	var formPara={
     			item_code:$("#item_code").val(),
     			item_name:$("#item_name").val(),
     			item_note:$("#item_note").val(),
     			app_mod_code:liger.get("app_mod_code").getValue(),
     			is_avg:$("#is_avg").val(),
     			is_two_audit:$("#is_two_audit").val(),
     			is_sum:$("#is_sum").val(),
     			is_stop:$("#is_stop").val()
     	};
     	
     	ajaxJsonObjectByUrl("updateHpmItem.do",formPara,function(responseData){
            
     		if(responseData.state=="true"){
     			
     			//$("input[name='item_code']").val('');
     			
            	//$("input[name='item_name']").val('');
            	
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
   
	function saveItem(){
		if($("form").valid()){
     		save();
     	}
   }
	function loadDict(){
		autocomplete("#app_mod_code","../queryAppModDict.do?isCheck=false","id","text",true,true);
		liger.get("app_mod_code").setValue("${app_mod_code}");	
		liger.get("app_mod_code").setText("${app_mod_name}");	
		$("#item_code").ligerTextBox({disabled:true});
	}

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">奖金项目编码：</td>
				<td align="left" class="l-table-edit-td"><input name="item_code" type="text" disabled="disabled" id="item_code" ltype="text" value="${item_code}"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">奖金项目名称：</td>
				<td align="left" class="l-table-edit-td"><input name="iitem_name" type="text" id="item_name" ltype="text" value="${item_name}"
					validate="{required:true,maxlength:50}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">应用模式：</td>
				<td align="left" class="l-table-edit-td"><input name="app_mod_code" type="text" id="app_mod_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">是否参与人均奖核算：</td>
				<td align="left" class="l-table-edit-td"><select name="is_avg" id="is_avg">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否领导审核：</td>
				<td align="left" class="l-table-edit-td"><select name="is_two_audit" id="is_two_audit">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与合计：</td>
				<td align="left" class="l-table-edit-td"><select name="is_sum" id="is_sum">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 40px;">项目说明：</td>
				<td align="left" class="l-table-edit-td"><input name="item_note" type="text" id="item_note" ltype="text" value="${item_note}"/></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
