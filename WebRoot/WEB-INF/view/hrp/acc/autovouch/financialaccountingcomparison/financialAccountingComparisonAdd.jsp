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
     
     function  save(subj_code_c,dialog){
    	var subj_code_k = liger.get("subj_code_k").getValue();
    	var subj_code_name = liger.get("subj_code_k").getText();
    	if(subj_code_k == '' || subj_code_k == undefined || subj_code_k == null){
    		$.ligerDialog.error('请选择财政补助内容');
    		return;
    	}
    	
    	var code = {'subj_code_c':subj_code_c,'subj_code_k':subj_code_k}
    	
        ajaxJsonObjectByUrl("addFinancialAccountingComparison.do",code,function(responseData){
                //parent.query();
                if(responseData.state == true){
                var selected = parent.grid.getSelected();
		            if (!selected) { alert('请选择行'); return; }
			            parent.grid.updateRow(selected,{
			            	SUBJ_CODE_K: subj_code_k,
			            	YUSUAN: subj_code_name,
		            });
                }
                dialog.close();
        });
    }
   
    function loadDict(){
        //字典下拉框
    	$("#subj_code_k").ligerComboBox({
    		url: "queryFinancialAccountingComparisonSubjK.do?isCheck=false",
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
        liger.get("subj_code_k").setValue('${SUBJ_CODE_K}');
    } 
    
    </script>
  </head>
   <body>
	   <div id="pageloading" class="l-loading" style="display: none"></div>
	         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="center" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code_k" type="text" id="subj_code_k" ltype="text" validate="{required:true,maxlength:20}" /></td>
	            </tr> 
	         </table>
    </body>
</html>
