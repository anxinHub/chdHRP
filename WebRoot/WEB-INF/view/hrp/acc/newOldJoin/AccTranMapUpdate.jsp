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
     });  
     
     function  save(subj_code_old_s,subj_code_new_s,subj_code_new_b_s){
    	var subj_code_old = liger.get("subj_code_old").getValue();
    	var subj_code_new = liger.get("subj_code_new").getValue();
    	var subj_code_new_b = liger.get("subj_code_new_b").getValue();
    	if(subj_code_old == '' || subj_code_old == undefined || subj_code_old == null){
    		$.ligerDialog.error('请选择原科目!');
    		return;
    	}
    	if(subj_code_new == '' || subj_code_new == undefined || subj_code_new == null){
    		$.ligerDialog.error('请选择新财务科目!');
    		return;
    	}
    	if(subj_code_new_b == '' || subj_code_new_b == undefined || subj_code_new_b == null){
    		$.ligerDialog.error('请选择新预算科目!');
    		return;
    	}
    	
    	var code = {
    		'subj_code_old':subj_code_old,
			'subj_code_new':subj_code_new,
			'subj_code_new_b':subj_code_new_b,
			'subj_code_old_s':subj_code_old_s,
			'subj_code_new_s':subj_code_new_s,
			'subj_code_new_b_s':subj_code_new_b_s,
    	};
    	
        ajaxJsonObjectByUrl("updateTranMap.do?isCheck=false",code,function(responseData){
                parent.queryClick();
                dialog.close();
        });
    }
   
    function loadDict(){
        //字典下拉框
    	$("#subj_code_old").ligerComboBox({
    		url: "querySubjcodeoldSelect.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: 600,
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: false,
    		alwayShowInDown: true,
    	});
        //字典下拉框
    	$("#subj_code_new").ligerComboBox({
    		url: "querySubjcodenewSelect.do?isCheck=false&code=1",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: 600,
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: false,
    		alwayShowInDown: true,
    	});
        //字典下拉框
    	$("#subj_code_new_b").ligerComboBox({
    		url: "querySubjcodenewSelect.do?isCheck=false&code=2",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: subjWidth,
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: 600,
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: false,
    		alwayShowInDown: true,
    	});
        liger.get("subj_code_old").setValue('${subj_code_old}');
        liger.get("subj_code_new").setValue('${subj_code_new}');
        liger.get("subj_code_new_b").setValue('${subj_code_new_b}');
    } 
    
    </script>
  </head>
   <body>
	   <div id="pageloading" class="l-loading" style="display: none"></div>
	         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	            	<td align="center">原会计科目</td>
	            </tr> 
	            <tr>
	                <td align="center" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code_old" type="text" id="subj_code_old" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            </tr> 
	            <tr>
	            	<td align="center">财务会计科目</td>
	            </tr>
	            <tr>
	                <td align="center" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code_new" type="text" id="subj_code_new" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            </tr>
	            <tr>
	           	 	<td align="center">预算会计科目</td>
	            </tr>
	            <tr>
	                <td align="center" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code_new_b" type="text" id="subj_code_new_b" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            </tr>
	         </table>
    </body>
</html>
