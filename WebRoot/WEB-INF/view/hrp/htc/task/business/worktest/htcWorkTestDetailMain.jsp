<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var parm = "group_id=" + '${group_id}' 
	   + "&" + "hos_id=" + '${hos_id}'
	   + "&" + "copy_code=" + '${copy_code}'
	   + "&" + "acc_year=" + '${acc_year}'
	   + "&" + "plan_code=" + '${plan_code}'
	   + "&" + "proj_dept_id=" + '${proj_dept_id}';
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

	});

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
							columns : [
									{
										display : '科室项目编码',
										name : 'proj_dept_code',
										align : 'left'
									},
									{
										display : '科室项目名称',
										name : 'proj_dept_name',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return "<a href='#' onclick=\"queryItemDetailWork('"
									        + rowdata.group_id + "|"
									        + rowdata.hos_id + "|"
									        + rowdata.copy_code + "|"
									        + rowdata.acc_year + "|"
									        + rowdata.plan_code + "|"
									        + rowdata.proj_dept_id + "|"
											+ rowdata.work_code
											+ "');\" >"
										 	+ rowdata.proj_dept_name + "</a>";
										}
									},
									{
										display : '作业编码',
										name : 'work_code',
										align : 'left'
									},
									{
										display : '作业名称',
										name : 'work_name',
										align : 'left'
									},
									{
										display : '作业总成本',
										name : 'total_cost',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.total_cost, 2, 1);
										}
									},
									{
										display : '项目总成本',
										name : 'charge_cost',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.charge_cost, 2, 1);
										}
									},
									{
										display : '差额',
										name : 'chae',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.total_cost - rowdata.charge_cost,2, 1);
										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcWorkDetailTest.do?isCheck=false&'+ parm,
							width : '100%',
							height : '100%',
							checkbox : false,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function queryItemDetailWork(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] 
		   + "&" + "hos_id=" + vo[1]
		   + "&" + "copy_code=" + vo[2]
		   + "&" + "acc_year=" + vo[3]
		   + "&" + "plan_code=" + vo[4]
		   + "&" + "proj_dept_id=" + vo[5]
		   + "&" + "work_code=" + vo[6];
		$.ligerDialog.open({
			url : 'htcWorkTestItemDetailMainPage.do?isCheck==false&' + parm,
			data : {},
			height : 500,
			width : 1000,
			title : '',
			modal : false,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true
		});

	}
	
	function loadDict() {
		//字典下拉框
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="maingrid"></div>
</body>
</html>
