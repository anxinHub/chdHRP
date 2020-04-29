<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/plugins/ligerAccVouch.js"
	type="text/javascript"></script>

<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/hrpjs/acc/accVouchDetail.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#layout1").ligerLayout({
            centerBottomHeight:180,
            allowCenterBottomResize: false
        });
		loadDict();
		loadHead(null); //加载数据
		
		
		
        
		$("#pageloading").hide();
		$("#topmenu").ligerMenuBar({
			items : [ {
				text : '打印',
				menu : menu_pring
			}, {
				text : '新增',
				menu : menu_add
			}, {
				text : '保存',
				menu : menu_save
			}, {
				text : '删除',
				menu : menu_del
			}, {
				text : '审核',
				menu : menu_audit
			}, {
				text : '显示',
				menu : menu_view
			}, {
				text : '其他',
				menu : menu_other
			}, {
				text : '字典',
				menu : menu_dict
			}
			]
		});
		
		
	});
	function is_addRow(){
		setTimeout(function () { 
			//当数据为空时 默认新增一行
			var data = grid.getData();
			if (data.length==0)
				grid.addRow();
			}, 1000);
		
	}
</script>
<style type="text/css">
body {
	padding: 5px;
	margin: 0;
	padding-bottom: 15px;
}

#layout1 {
	width: 100%;
	margin: 0;
	padding: 0;
}

.l-page-top {
	height: 40px;
	background: #f8f8f8;
	margin-bottom: 3px;
	text-align:center;
	
}
</style>
</head>

<body style="padding: 0px; overflow-x: hidden"  onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="topmenu"></div>
	<div class="l-page-top" >
		<h1>记   账   凭   证222</h1>
	</div>

	<div id="layout1">
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">

				<tr>
					<td align="left" class="l-table-edit-td"><input name="vouch_type_code"  type="text"  id="vouch_type_code"  ltype="text" /></td>
					<td align="left">凭证号：</td>
					<td align="left"><input name="vouch_no"  type="text"  id="vouch_no"  ltype="text" /></td>
				</tr>

			</table>
			<div id="maingrid"></div>
		</div>
		<div position="centerbottom" title="辅助账信息"></div>
	</div>
</body>
</html>
