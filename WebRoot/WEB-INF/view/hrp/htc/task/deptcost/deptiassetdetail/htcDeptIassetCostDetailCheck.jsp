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

	$(function() {

		loadDict()//加载下拉框
		loadHead();

	});
	//查询
	function query() {

		grid.options.parms = [];

		grid.options.newPage = 1;

		grid.options.parms = [];
		grid.options.newPage = 1;
		
		var _check_way = "";
		$("#d1 input:radio").each(function() {
			if (this.checked) {
				_check_way = this.value;
			}
		});
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'check_way',
			value : _check_way
		});
		grid.options.parms.push({
			name : 'start_year_month',
			value : $("#start_year_month").val()
		});
		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
						{
				  columns : [{
								display: '科室编码',
								name: 'dept_code',
								align: 'left'
							}, {
								display: '科室名称',
								name: 'dept_name',
								align: 'left'
							}, {
								display: '成本项目编码',
								name: 'cost_item_code',
								align: 'left'
							}, {
								display: '成本项目名称',
								name: 'cost_item_name',
								align: 'left'
							},{
								display: '科室成本核算总额',
								name: 'tot_amount',
								align: 'left',
							    render: function(rowdata, rowindex, value) {
							        return formatNumber(rowdata.tot_amount, 2, 1);
							    }
							},
							{   display: '折旧明细数据合计',
								name: 'num_amount',
								align: 'left',
							    render: function(rowdata, rowindex, value) {
							        return formatNumber(rowdata.num_amount, 2, 1);
							    }
							},{
								display: '差额',
								name: '',
								align: 'left',
								type: 'float',
								render: function(rowdata, rowindex, value) {
									return formatNumber(parseFloat(rowdata.tot_amount) - parseFloat(rowdata.num_amount), 2, 1);
								}
							}],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'sumHtcDeptIassetCostDetail.do',
							width : '100%',
							height : '100%',
							rownumbers : true,
							delayLoad : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '数据核对',
									id : 'checkdata',
									click : query,
									icon : 'search'
								}, {
									line : true
								}]
							}

						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict(){
		autodate("#start_year_month", "YYYYmm");
		autodate("#end_year_month", "YYYYmm");
		$("#start_year_month").ligerTextBox({
			width : 70
		});
		$("#end_year_month").ligerTextBox({
			width : 70
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td>
				<table style="font-size: 12px;">
					<tr>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="start_year_month" type="text" id="start_year_month"
							style="width: 70px;" ltype="text"
							onFocus="WdatePicker({startDate: '%y%M',isShowClear:false,readOnly:true,dateFmt:'yyyyMM',maxDate:'#F{$dp.$D(\'start_acct_year\')}'})" />
						</td>
						<td align="right"><span>至&nbsp;</span></td>
						<td><input class="Wdate" name="end_year_month" type="text"
							id="end_year_month" style="width: 70px;" ltype="text"
							onFocus="WdatePicker({startDate: '%y%M',isShowClear:false,readOnly:true,dateFmt:'yyyyMM',minDate:'#F{$dp.$D(\'end_acct_year\')}'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核对方式：</td>
			<td align="left" class="l-table-edit-td" id="d1"><input
				type="radio" value="all" name="check_way" checked />汇总核对&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" value="month" name="check_way" />按月核对</td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
