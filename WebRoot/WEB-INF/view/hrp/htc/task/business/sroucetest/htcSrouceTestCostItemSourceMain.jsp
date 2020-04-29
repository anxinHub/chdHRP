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
	   + "&" + "proj_dept_id=" + '${proj_dept_id}'
	   + "&" + "cost_item_id=" + '${cost_item_id}';
	$(function() {
		//加载数据
		loadHead(null);
	});
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '资金来源编码',
				name : 'source_code',
				align : 'left'
			}, {
				display : '资金来源名称',
				name : 'source_name',
				align : 'left'
			}, {
				display : '医院全成本',
				name : 'total_cost',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.total_cost, 2, 1);
				}
			}, {
				display : '直接记入项目成本',
				name : 'charge_direct_cost',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.charge_direct_cost, 2, 1);
				}
			}, {
				display : '单独收费材料成本',
				name : 'mate_charge_cost',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.mate_charge_cost, 2, 1);
				}
			}, {
				display : '作业总成本',
				name : 'work_cost',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.work_cost, 2, 1);
				}
			}, {
				display : '差额',
				name : 'chae',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.total_cost - rowdata.charge_direct_cost - rowdata.mate_charge_cost - rowdata.work_cost,2, 1);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcSrouceTestCostItemSource.do?isCheck=false&' + parm,
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="maingrid"></div>
</body>
</html>
