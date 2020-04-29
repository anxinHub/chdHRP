<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var kind_codes = ${kind_codes};
	
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
			checkbox: true,
			usePager : false, 
			isChecked : function(rowData){
				var flag = false;
				$(kind_codes).each(function (index, item){
					if(rowData.kind_code == item){
						flag = true;
						return true;
					}
		 		});
				return flag;
			},
			url:'../../hr/record/queryHosEmpKink.do?isCheck=false&is_stop=0'
		});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	// 获取选择行
	function f_select(){
		var rowData = grid.getCheckedRows();
		return rowData;
	}
</script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div id="maingrid"></div>
</body>
</html>