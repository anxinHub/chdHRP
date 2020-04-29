<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/static_resource.jsp">
		<jsp:param value="select,datepicker,grid,dialog,ligerUI" name="plugins" />
	</jsp:include>
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
		
		var year_input, source_id_select, ass_type_id_select;

		function init() {
			getData("../../../queryBudgYear.do?isCheck=false", function (data) {
				year_input = $("#year_input").etDatepicker({
					defaultDate: data[0].text,
					view: "years",
					minView: "years",
					dateFormat: "yyyy",
					minDate: data[data.length - 1].text,
					maxDate: data[0].text,
					todayButton: false,
					onChanged: query
				});
			});

			source_id_select = $("#source_id_select").etSelect({
				url:"../../../queryBudgSource.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			ass_type_id_select = $("#ass_type_id_select").etSelect({
				url:"../../../../ass/queryAssTypeDict.do?isCheck=false",
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
		}
		//查询
		function query() {
		 	var parms = [
				{ name: 'budg_year', value: year_input.getValue() },
				{ name: 'source_id', value: source_id_select.getValue() },
				{ name: 'ass_type_id', value: ass_type_id_select.getValue() }
			];
			//加载查询条件
			grid.loadData(parms,'queryBugetMain.do');
		}
		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [
					{ display: '预算年度', name: 'budg_year', width:'10%'},
					{ display: '月份', name: 'month',width:'7%'},
					{ display: '资金来源', name: 'source_name',width:'15%'},
					{ display: '资产分类', name: 'ass_type_name',width:'15%'},
					{ display: '年度计划金额', name: 'year_plan_amount', align: 'right',width:'15%',
						render:function(ui) {
							var value = ui.cellData;
							return formatNumber(value, 2, 1);
						}
					},
					{ display: '追加计划金额', name: 'add_plan_amount', align: 'right',width:'15%',
						render:function(ui) {
							var value = ui.cellData;
							return formatNumber(value, 2, 1);
						}
					},
					{ display: '预算总额', name: 'pur_budg', align: 'right', minWidth:'20%',
						render:function(ui) {
							var value = ui.cellData;
							return formatNumber(value, 2, 1);
						}
					}
				],
				dataMedol: {
     	         	method: 'POST',
     	         	location: 'remote',
     	         	url: '',
     	         	recIndx: 'budg_year'
                },
                pageModel:{
                	type: 'remote',
                },
                usePager: true, width: '100%',height: '100%',checkbox: true,
                toolbar: { 
                   	items: [
                   		{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
                   		{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
                   		{ type: "button", label: '生成', icon: 'plus', listeners: [{ click: generate }] },
           		    ]
                },
                summaryRowIndx: [0] 
			});
		}

		function generate() {
			$.etDialog.confirm('确定执行生成操作？', function () {
				var formPara = {
					budg_year: year_input.getValue()
				};
				ajaxPostData({
					url: "generateBuget.do?isCheck=false",
					data: formPara,
					success: function (res) {
						if (res.state == "true") {
							query();
						}
					}
				})
			});
		}

		//删除;
		function remove() {
			var data = grid.selectGet();
			if (data.length == 0) {
				$.etDialog.error('请选择行');
			} else {
				var ParamVo = [];
				$(data).each(function () {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.budg_year + "@" +
						this.rowData.source_id + "@" +
						this.rowData.ass_type_id
					)
				});
				$.etDialog.confirm('确定删除?', function () {
					ajaxPostData({
						url: "deleteBudgWorkCheck.do?isCheck=false",
						data: { ParamVo: ParamVo.toString() },
						success: function (res) {
							if (res.state == "true") {
								query();
							}
						}
					})
				});
			}
		}

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add);
			hotkeys('D', remove);
			hotkeys('G', generate);
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
				<td class="label">资金来源：</td>
				<td class="ipt">
					<select name="" id="source_id_select" style="width:180px;"></select>
				</td>
				<td class="label">资产分类：</td>
				<td class="ipt">
					<select name="" id="ass_type_id_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
</body>

</html>