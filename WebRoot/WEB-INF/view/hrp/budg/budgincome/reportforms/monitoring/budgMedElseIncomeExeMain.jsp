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
		<jsp:param value="select,datepicker,grid,ligerUI" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		var budg_year;
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			init();
			loadSelect();
		});
		
		var year_input, subj_name_select, subj_level_select;

		function init(){
			year_input = $("#year_input").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                clearButton: false,
                onChange: function () {
                    setTimeout(function () {
                    	query();
                    	reloadSubjName();
                    }, 10);
                },
                defaultDate: true
            });

			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../queryBudgIncomeSubj.do?isCheck=false&subj_type=4&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
			function reloadSubjName(year){
				subj_name_select.reload({
					url:'../../../queryBudgSubj.do?isCheck=false',
					para:{
						subj_type:'04',
						budg_year:year_input.getValue(),
						key : '4301'
					}
				})
			}
			subj_level_select = $("#subj_level_select").etSelect({
				url: "../../../queryBudgSubjLevel.do?isCheck=false",
				defaultValue: "none",
				onChange: query
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
			})
		};
		function loadSelect(){
			
			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../queryBudgIncomeSubj.do?isCheck=false&subj_type=4&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
		}
		function query(){
			var search = [
				{ name: 'year', value: year_input.getValue() },
				{ name: 'subj_code', value:subj_name_select.getValue().split(",")[0] },
				{ name: 'subj_level', value: subj_level_select.val() }
			];
			//加载查询条件
			grid.loadData(search,'queryBudgMedElseIncomExe.do?isCheck=false');
		}
		
		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{display: '年度', name: 'year', align: 'left', width: 70},
					{display: '科目编码', name: 'subj_code', align: 'left', width: 100},
					{display: '科目名称', name: 'subj_name', align: 'left', width: 100},
					{display: '项目', name: 'item', align: 'left', width: 100},
					{display: '本年合计', name: 'year_sum', align: 'right', width: 110,
						render:function(ui) {
							if (ui.rowData.item == '上月结转' || ui.rowData.item == '结转下月') {
								return "";
							} else if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(ui.rowData.year_sum, 2, 1) + "%";
							} else {
								return formatNumber(ui.rowData.year_sum, 2, 1);
							}
						}
					},
					{display: '1月', name: 'month_1', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度') {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '2月', name: 'month_2', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度'  ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '3月', name: 'month_3', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度'  ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '4月', name: 'month_4', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '5月', name: 'month_5', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度'  ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '6月', name: 'month_6', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '7月', name: 'month_7', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度'  ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '8月', name: 'month_8', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度'  ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '9月', name: 'month_9', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '10月', name: 'month_10', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '11月', name: 'month_11', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{display: '12月', name: 'month_12', align: 'right', width: 110,
						render:function(ui) {
							var value = ui.cellData;
							if (ui.rowData.item == '进度' || ui.rowData.item == '总进度' ) {
								return formatNumber(value, 2, 1) + "%";
							} else {
								return formatNumber(value, 2, 1);
							}
						}
					}
				],
				dataModel:{
					method: 'POST',
					location: 'remote',
					url: '',
					recInx: 'year'
				},
				pageModel:{
					type: 'remote',
				},
				summaryRowIndx :[0,1,2],
				usePager: true,
				width: '100%',
				height: '100%',
				checkBox: false,
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
			grid.options.parms = [];
			grid.options.newPage = 1;
			grid.options.parms.push({ name: 'year', value: year_input.getValue() });
			grid.options.parms.push({ name: 'subj_code', value: subj_name_select.getValue() });
			grid.options.parms.push({ name: 'subj_level', value: subj_level_select.getValue() });
			var selPara = {};
			$.each(grid.options.parms, function (i, obj) {
				selPara[obj.name] = obj.value;
			});
			var printPara = {
				headCount: 2,
				title: "医院医疗收入预算执行监控数据表",
				type: 3,
				columns: grid.getColumns(1)
			};
			ajaxJsonObjectByUrl("queryBudgMedInHosYearMon.do?isCheck=false", selPara, function (responseData) {
				printGridView(responseData, printPara);
			});
		}

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('P', printDate);
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
					<select name="" id="subj_name_select" style="width:180px"></select>
				</td>
				<td class="label">科目级次：</td>
				<td class="ipt">
					<select name="" id="subj_level_select" style="width:180px"></select>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="maingrid"></div>
	
</body>
</html>