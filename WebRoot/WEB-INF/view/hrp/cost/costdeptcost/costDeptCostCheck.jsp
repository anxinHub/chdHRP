<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '消息编码',
										name : 'message_code',
										align : 'left',
										width : 60
									},
									{
										display : '消息名称',
										name : 'message_name',
										align : 'left'
									},
									{
										display : '备注',
										name : 'message_note',
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryCostDeptCostCheck.do?isCheck=false',
							width : '100%',
							height : '100%',
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '查询（<u>E</u>）',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {

	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>

	<div id="maingrid"></div>
</body>
</html>
