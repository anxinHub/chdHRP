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

	function save() {

		var obj = parent.liger.get("mod_code").getValue();

		var mod_code = obj.split(".")[1];

		var table_code = obj.split(".")[0];

		var is_acc_year = obj.split(".")[8];

		var copy_code;
		var is_copy = obj.split(".")[7];

		if (is_copy == 0) {
			copy_code = '0';
		}else {
			copy_code = parent.liger.get("copy_code").getValue();
		}

		var acc_year;
		var is_acc_year = obj.split(".")[8];

		if (is_acc_year == 0) {
			acc_year = '0';
		}

		else {
			acc_year = parent.$("#acc_year").val()
		}

		var perm_id

		if (parent.tip == 1) {

			perm_id = parent.user_id;

		} else {

			perm_id = parent.user_id;

		}
		
		var perm_id;
		if (parent.user_id != '' && parent.user_id != null) {
			perm_id = parent.user_id;
		} else {
			perm_id = parent.role_id;
		}

		
		var hos_id='0';
        hos_id=parent.liger.get("hos_code").getValue();
        
        if(hos_id==''){
        	hos_id='';
        }
		var formPara = {

			perm_id : perm_id,

			table_code : table_code,

			mod_code : mod_code,

			is_read : $("#is_read").attr("checked") == true ? 1 : 0,

			is_write : $("#is_write").attr("checked") == true ? 1 : 0,

			copy_code : copy_code,
			hos_id:hos_id,

			acc_year : acc_year,

			tip : parent.tip,

			obj : obj

		};

		ajaxJsonObjectByUrl("addBatchPerm.do", formPara,
				function(responseData) {

					if (responseData.state == "true") {

						parent.query();

					}

				});

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
