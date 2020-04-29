<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="grid, select, datepicker, dialog, hr" name="plugins" />
</jsp:include>
<script>
	var grid, attend_xjreg_date, dept_code, emp_id, attend_code, attend_xjstate;
	$(function () {
		init();
	});

	function init() {
		initSelect();
		initGrid();
	}

	function initSelect() {
	}

	function initGrid() {
		// 基础表格参数
		var main_toolbar = {};
		var main_columns = [
			{display : '职工名称', align : "center", name : 'emp_name', width : 120 },
			{display : '部门', align : "center", name : 'dept_name', width : 120 },
			{display : '考勤月度', align : "center", name : 'attend_month', width : 120 },
			{display : '变更类型', align : "center", name : 'file_type', width : 120 },
			{display : '申请变更天数', align : "center", name : 'attend_xjdays', width : 120 },
			{display : '实际变更天数', align : "center", name : 'attend_xxjdays', width : 120 },
			{display : '变更日期', align : "center", name : 'attend_xjreg_operdate', width : 120 },
		];
		var main_obj = {
			height : '100%',
			inWindowHeight : true,
			toolbar : main_toolbar,
			columns : main_columns,
			dataModel : {
				url : 'applyingLeavesQuery.do?isCheck=false&emp_id='+'${emp_id}'
			},
			rowDblClick : function(event, ui) {
				var rowData = ui.rowData;
				openUpdate(rowData);
			},
		};
		grid = $("#mainGrid").etGrid(main_obj);
	}

	function query() {
		var param = [];
		grid.loadData(param);
	}
</script>
</head>
<body>
	<div id="mainGrid"></div>
</body>
</html>