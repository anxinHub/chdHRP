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
	<jsp:param value="grid,select,datepicker,ligerUI" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input,subj_code_select,subj_level_select;

	function init(){
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
		var parms=[];
        //根据表字段进行添加查询条件
   	    parms.push({name:'budg_year',value:year_input.getValue()}); 
   	    parms.push({name:'subj_code',value:subj_code_select.getValue()}); 
   	    parms.push({name:'subj_level',value:subj_level_select.getValue()}); 
    	//加载查询条件
    	grid.loadData(parms,'');
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{
					display: '预算年度', name: 'budg_year', align: 'left', width: 80
				},
				{
					display: '科目编码', name: 'subj_code', align: 'left', width: 100
				},
				{
					display: '科目名称', name: 'subj_name', align: 'left', width: 120
				},
				{
					display: '项目', name: 'summary', align: 'left', width: 100
				},
				{
					display: '本年合计', name: 'year_sum', align: 'right', width: 110,
				    render:function(ui) {
						if (ui.rowData.summary == '上月结转' || ui.rowData.summary == '结转下月') {
							return "";
						} else if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {

							return formatNumber(ui.rowData.year_sum, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.year_sum, 2, 1);
						}
					}
				},
				{
					display: '1月', name: 'month_data1', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {
							return formatNumber(ui.rowData.month_data1 , 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data1, 2, 1);
						}
					}
				},
				{
					display: '2月', name: 'month_data2', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {
							return formatNumber(ui.rowData.month_data2, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data2, 2, 1);
						}
					}
				},
				{
					display: '3月', name: 'month_data3', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {
							return formatNumber(ui.rowData.month_data3, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data3, 2, 1);
						}
					}
				},
				{
					display: '4月', name: 'month_data4', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data4, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data4, 2, 1);
						}
					}
				},
				{
					display: '5月', name: 'month_data5', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data5, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data5, 2, 1);
						}
					}
				},
				{
					display: '6月', name: 'month_data6', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {
							return formatNumber(ui.rowData.month_data6, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data6, 2, 1);
						}
					}
				},
				{
					display: '7月', name: 'month_data7', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度') {
							return formatNumber(ui.rowData.month_data7, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data7, 2, 1);
						}
					}
				},
				{
					display: '8月', name: 'month_data8', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data8, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data8, 2, 1);
						}
					}
				},
				{
					display: '9月', name: 'month_data9', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data9, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data9, 2, 1);
						}
					}
				},
				{
					display: '10月', name: 'month_data10', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data10, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data10, 2, 1);
						}
					}
				},
				{
					display: '11月', name: 'month_data11', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data11, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data11, 2, 1);
						}
					}
				},
				{
					display: '12月', name: 'month_data12', align: 'right', width: 110,
					 render:function(ui) {
						if (ui.rowData.summary == '进度' || ui.rowData.summary == '总进度' ) {
							return formatNumber(ui.rowData.month_data12, 2, 1) + "%";
						} else {
							return formatNumber(ui.rowData.month_data12, 2, 1);
						}
					}
				},
			],
			
			dataModel: {
                method: 'POST',
                location: 'remote',
                url: 'queryReportMedAllMonitor.do',
                recIndx: 'budg_year' //必填 且该列不能为空  
            },
            usePager: true,
            width: '100%',
            inWindowHeight: true,
            height: '100%',
            checkbox: true,
            pageModel: {
				type: 'remote',
			}, 
			summaryRowIndx :[0,1,2],
            toolbar: {
                items: [
                    { type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                    { type: "button", label: '打印', icon: 'search', listeners: [{ click: printDate }] },
                ]
            },
		});
	}

	function printDate() {
		if (grid.getAllData().length == 0) {
			$.ligerDialog.error("无打印数据！");
			return;
		}
		grid.options.parms = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_code_select.getValue() },
			{ name: 'subj_level', value: subj_level_select.getValue() }
		];
		grid.options.newPage = 1;
	
		var selPara = {};
		$.each(grid.options.parms, function (i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount: 2,
			title: "医院医疗支出预算执行监控表",
			type: 3,
			columns: grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryReportMedAllMonitor.do?isCheck=false", selPara, function (responseData) {
			printGridView(responseData, printPara);
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
				<td class="label">科目名称：</td>
				<td class="ipt">
					<select name="" id="subj_code_select" style="width:180px;"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>
</html>