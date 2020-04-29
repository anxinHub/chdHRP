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
     
     function  save(obj,dialog){
    	var content_code = liger.get("content_code").getValue();
    	var content_code_name = liger.get("content_code").getText();
    	if(content_code == '' || content_code == undefined || content_code == null){
    		$.ligerDialog.error('请选择财政补助内容');
    		return;
    	}
    	
        var formPara={
        		subj_code:'${subj_code}',
        		group_id:'${group_id}',
        		hos_id:'${hos_id}',
        		copy_code:'${copy_code}',
        		acc_year:'${acc_year}',
        		content_code:liger.get("content_code").getValue()
         };
        
        ajaxJsonObjectByUrl("addAccSubjContent.do?isCheck=false",formPara,function(responseData){
            if(responseData.state == "true"){
                var selected = parent.grid.getSelected();
			    parent.grid.updateRow(obj,
			    		{content_name: content_code_name,
			    		content_code: content_code
			    		});
			    dialog.close();
            }
        });
    }
     
 
   
    function saveAccSubj(obj,dialog){
        if($("form").valid()){
            save(obj,dialog);
        }
   }
    function loadDict(){
        //字典下拉框
    	autocomplete("#content_code","../queryAccFinaContent.do?isCheck=false","id","text",true,true);
    } 
    
    </script>
  </head>
   <body>
	   <div id="pageloading" class="l-loading" style="display: none"></div>
	   <form name="form1" method="post"  id="form1" >
	         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	            <tr>
	                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;">财政补助内容：</td>
	                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="content_code" type="text" id="content_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
	                <td align="left"></td>
	            </tr> 
	         </table>
	    </form>
    </body>
</html>
