<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
    
    $(function ()
    {
    	
    });
    function  save(){
    	var obj = parent.liger.get("mod_code").getValue();
    	var mod_code = obj.split(".")[1];
    	var table_code = obj.split(".")[0];
    	
    	var data = parent.gridManager.getData();
    	var param = [];
    	
    	if($("#is_read").attr("checked")==false && $("#is_write").attr("checked")==false){
   		 $.ligerDialog.confirm('确定全部取消?', function (yes){
            	if(yes){
	            	if(parent.tip==1){
	            		param.push(parent.user_id+"@"+table_code+"@"+0+"@"+mod_code+"@"+$("#is_read").attr("checked")+"@"+$("#is_write").attr("checked"));
	       			}else{
	       				param.push(parent.role_id+"@"+table_code+"@"+0+"@"+mod_code+"@"+$("#is_read").attr("checked")+"@"+$("#is_write").attr("checked"));
	       			}
	           		
	           		if(parent.tip==1){
	           			$.post("deleteAccUserPermData.do?isCheck=false",{ParamVo : param},function(data){
	           				if(data != null){
	           					$.ligerDialog.success('保存成功');
	           					parent.query();
	           				}
	           			},"json");
	           		}else{
	           			$.post("deleteAccRolePermData.do?isCheck=false",{ParamVo : param},function(data){
	           				if(data != null){
	           					$.ligerDialog.success('保存成功');
	           					parent.query();
	           				}
	           			},"json");
	           		}
            	}
   		 });	
   		}
    	$.each(data,function(i,v){
			if($("#is_read").attr("checked")==false && $("#is_write").attr("checked")==false){
				
    		}else{
    			if(parent.tip==1){
    				param.push(parent.user_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read").attr("checked")+"@"+$("#is_write").attr("checked"));
    			}else{
    				param.push(parent.role_id+"@"+table_code+"@"+v.obj_id+"@"+mod_code+"@"+$("#is_read").attr("checked")+"@"+$("#is_write").attr("checked"));
    			}
    		}
    		
    	});
    	if(parent.tip==1){
			$.post("addAccUserPermData.do?isCheck=false",{ParamVo : param},function(data){
				if(data != null){
					$.ligerDialog.success('保存成功');
					parent.query();
				}
			},"json");
		}else{
			$.post("addAccRolePermData.do?isCheck=false",{ParamVo : param},function(data){
				if(data != null){
					$.ligerDialog.success('保存成功');
					parent.query();
				}
			},"json");
		}
    }
 

    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
		<center>
	    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="center" class="l-table-edit-td"  style="padding-left:20px;"><input name="is_read" type="checkbox" id="is_read"  /></td>
	            <td align="center" class="l-table-edit-td">全部读取权限</td>
	        </tr> 
	        <tr>
	            <td align="center" class="l-table-edit-td"  style="padding-left:20px;"><input name="is_write" type="checkbox" id="is_write"  /></td>
	            <td align="center" class="l-table-edit-td">全部写入权限</td>
	        </tr> 
	    </table>
	    </center>
	</form>

</body>
</html>
