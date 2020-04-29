<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	var grid;
	$(function(){
		initGrid();
	});
	
	// 初始化grid
	function initGrid(){
		grid = $("#maingrid").ligerGrid({
			columns:[
				{ display: '职工分类编码', name: 'kind_code', align: 'left', width:'45%' },
				{ display: '职工分类名称', name: 'kind_name', align: 'left', width:'45%' }
			],
			width : '100%',
			height : '100%',
			dataAction: 'server',
			dataType: 'server', 
			isScroll: true,
			checkbox: true,
			usePager : false, 
			url:'../accwageempkind/queryAccWageEmpKind.do?isCheck=false&wage_code='+"${wage_code}"
		});
	}
	
	// 获取选择行
	function f_select(){
		var rowData = grid.getCheckedRows();
		return rowData;
	}
</script>
</head>
<body style="padding:4px">
	<div id="maingrid" style="margin-top: 0px"></div>
</body>
</html>