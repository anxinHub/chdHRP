<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="grid,select,datepicker" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var budg_year;
	$(function () {
		//加载数据
		loadHead();
		loadHotkeys();
		init();
	});
	
	var year_input, dept_name_select, subj_code_select, subj_level_select;

	function init() {
		getData("../../../../queryBudgYear.do?isCheck=false", function (data) {
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged: function (value) {
					reloadSubjCode(value);
					query();
				}
			});
		});

		dept_name_select = $("#dept_name_select").etSelect({
			url:"../../../../queryBudgDeptDict.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});

		subj_code_select = $("#subj_code_select").etSelect({
			defaultValue:"none",
			onChange:query
		});
		function reloadSubjCode(year){
			subj_code_select.reload({
				url:"../../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:"05",
					budg_year:year
				}
			})
		}

		subj_level_select = $("#subj_level_select").etSelect({
			options:subj_level.Rows,
			defaultValue:"none",
			onChange:query
		});
	}

	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		});
	};
	
	//查询
	function query() {
		var parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() },
			{ name: 'subj_code', value: subj_code_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryReportMedDeptMonitor.do");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{
					display: '预算年度', name: 'budg_year',width: 80
				},
				{
					display: '科室编码', name: 'dept_code', width: 100
				},
				{
					display: '科室名称', name: 'dept_name', width: 120
				},
				{
					display: '科目编码', name: 'subj_code',  width: 100
				},
				{
					display: '科目名称', name: 'subj_name',  width: 120
				},
				{
					display: '项目', name: 'summary', width: 100
				},
				{
					display: '本年合计', name: 'year_sum', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						if (rowdata.summary == '上月结转' || rowdata.summary == '结转下月') {
							return "";
						} else if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(rowdata.year_sum, 2, 1) + "%";
						} else {
							return formatNumber(rowdata.year_sum, 2, 1); 
						}
					}
				},
				{
					display: '1月', name: 'month_data1', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '2月', name: 'month_data2', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '3月', name: 'month_data3', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '4月', name: 'month_data4', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '5月', name: 'month_data5', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '6月', name: 'month_data6', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '7月', name: 'month_data7', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '8月', name: 'month_data8', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度'  ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '9月', name: 'month_data9', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '10月', name: 'month_data10', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '11月', name: 'month_data11', align: 'right', width: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				},
				{
					display: '12月', name: 'month_data12', align: 'right', maxWidth: 110,
					render: function (ui) {
						var rowdata = ui.rowData;
						var value = ui.cellData;
						if (rowdata.summary == '进度' || rowdata.summary == '总进度' ) {
							return formatNumber(value, 2, 1) + "%";
						} else {
							return formatNumber(value, 2, 1);
						}
					}
				}
			],
			height: '100%', checkbox: true, numberCell:{show:true} ,
			dataModel: {
                method: 'POST',
                location: 'remote',
                url: '',
                recIndx: 'budg_year' //必填 且该列不能为空  
            },
			pageModel: {
				type: 'remote',
			}, 
			summaryRowIndx :[0,1,2],
			toolbar: {
				items: [
					{ type:"button" ,label: '查询（Q）', listeners:[{click: query}] , icon: 'search' },
					// { type:"button",label: '打印（P）', listeners:[{click: printDate}], icon: 'print' }
				]
			}
		});
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

	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">预算年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_code_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="label"></td>
				<td class="label"></td>
				<td class="label"></td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>
</html>