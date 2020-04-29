<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	var grid;
	
	var gridManager = null;
	
	var userUpdateStr;
	
	$(function() {
		loadHead(null); //加载数据
		query();
	});
	
	//获取动态辅助核算列
	function getColumn(){
		var columns = "[{display : '会计期间',name : 'year_month',align : 'left',width : '10%'},"; 
		ajaxJsonObjectByUrl("getAccClosingCheckNamesBySubj.do?isCheck=false","",function (responseData){
			var checkNames = responseData.checkNames.split(",");
			for(var i = 0; i<checkNames.length(); i++){
				columns = columns + "{display : '会计期间',name : 'year_month',align : 'left',width : '10%'}";
			}
		});
	}
	
	//模板Grid
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '会计期间',
				name : 'year_month',
				align : 'left',
				width : '10%'
			}],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			pageSize : 14,
			url : 'queryAccClosingCheckDetail.do?isCheck=false',
			width : '100%',
			height : '90%',
			checkbox : false,
			rownumbers : false,
			delayLoad: true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});
	
		gridManager = $("#maingrid").ligerGetGridManager();
		
		/* g.set('columns', columns); 
        g.reRender(); */
	}
	
	function query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'group_id',
			value : '${group_id}'
		});
		grid.options.parms.push({
			name : 'hos_id',
			value : '${hos_id}'
		});
		grid.options.parms.push({
			name : 'copy_code',
			value : '${copy_code}'
		});
		grid.options.parms.push({
			name : 'subj_code',
			value : '${subj_code}'
		});
		grid.options.parms.push({
			name : 'acc_year',
			value : '${acc_year}'
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : '${acc_month}'
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div style="width: 90%; height: 100%;">
		<div style="width: 100%; height: 88%;margin-top:10px; margin-left: 20px;">
			<h2 align="center">未记账凭证列表</h2>
			<div id="maingrid"></div>
		</div>
		<div align="center" style="padding-top:10px;">
			<hr/>
			<input type="button" id="but_before" value="上一步（S）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_next" value="下一步（N）"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="but_print" value="打印（P）"/>
		</div>
	</div>
</body>
</html>