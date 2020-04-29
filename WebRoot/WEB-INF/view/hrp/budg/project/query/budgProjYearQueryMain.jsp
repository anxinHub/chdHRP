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
			<script type="text/javascript">
				var grid;
				var gridManager = null;
				var userUpdateStr;

				//打印 单元格格式化 用
				var renderFunc = {

					b_remain_amoun: function (value) { //期初余额  合计
						return formatNumber(value, 2, 1);
					},
					budg_amount: function (value) { //本期增加  合计
						return formatNumber(value, 2, 1);
					},
					cost_amount: function (value) { //本期支出  合计
						return formatNumber(value, 2, 1);
					},
					t_budg_amount: function (value) { //期末余额  合计
						return formatNumber(value, 2, 1);
					},
					b_remain_amounO: function (value) { //期初余额  外拨资金
						return formatNumber(value, 2, 1);
					},
					budg_amountO: function (value) { //本期增加  外拨资金
						return formatNumber(value, 2, 1);
					},
					cost_amountO: function (value) { //本期支出  外拨资金
						return formatNumber(value, 2, 1);
					},
					t_budg_amountO: function (value) { //期末余额  外拨资金
						return formatNumber(value, 2, 1);
					},
					b_remain_amounM: function (value) { //期初余额  配套资金
						return formatNumber(value, 2, 1);
					},
					budg_amountM: function (value) { //本期增加  配套资金
						return formatNumber(value, 2, 1);
					},
					cost_amountM: function (value) { //本期支出  配套资金
						return formatNumber(value, 2, 1);
					},
					t_budg_amountM: function (value) { //期末余额  配套资金
						return formatNumber(value, 2, 1);
					}

				};
				$(function () {
					//加载数据
					loadHead(null);
					loadHotkeys();
					init();
				});
				
				var year_input, proj_name_select, type_code_select, level_code_select, proj_state_select, con_emp_select;

				function init() {
					getData("../../queryBudgYear.do?isCheck=false", function (data) {
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

					proj_name_select = $("#proj_name_select").etSelect({
						url:"../../queryProjName.do?isCheck=false",
						defaultValue:"none",
						onChange:query
					});

					type_code_select = $("#type_code_select").etSelect({
						url:"../../queryBudgProjType.do?isCheck=false",
						defaultValue:"none",
						onChange:query
					});

					level_code_select = $("#level_code_select").etSelect({
						url:"../../queryBudgProjLevel.do?isCheck=false",
						defaultValue:"none",
						onChange:query
					});

					proj_state_select = $("#proj_state_select").etSelect({
						url:"../../qureyProjStateSelect.do?isCheck=false",
						defaultValue:"none",
						onChange:query
					});

					con_emp_select = $("#con_emp_select").etSelect({
						url:"../../queryConEmpId.do?isCheck=false",
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
						{ name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
						{ name: 'type_code', value: type_code_select.getValue() },
						{ name: 'level_code', value: level_code_select.getValue() },
						{ name: 'proj_state', value: proj_state_select.getValue() },
						{ name: 'con_emp_id', value: con_emp_select.getValue() }
					];
					//加载查询条件
					grid.loadData(parms,"queryBudgProjYearQuery.do");
				}

				function loadHead() {
					grid = $("#maingrid").etGrid({
						columns: [
							{
								display: '项目状态', name: 'proj_state', align: 'left', width: 80,
							},
							{
								display: '项目名称', name: 'proj_name', align: 'left', width: 200
							},
							{
								display: '合计', columns:
								[
									{
										display: '期初余额', name: 'b_remain_amoun', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期增加', name: 'budg_amount', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期支出', name: 'cost_amount', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '期末余额', name: 't_budg_amount', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									}
								]

							},
							{
								display: '外拨资金', columns: [
									{
										display: '期初余额', name: 'b_remain_amounO', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期增加', name: 'budg_amountO', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期支出', name: 'cost_amountO', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '期末余额', name: 't_budg_amountO', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									}
								]

							},
							{
								display: '配套资金', columns:
								[
									{
										display: '期初余额', name: 'b_remain_amounM', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期增加', name: 'budg_amountM', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '本期支出', name: 'cost_amountM', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									},
									{
										display: '期末余额', name: 't_budg_amountM', align: 'right', width: 120,
										render:function(ui){
											var value = ui.cellData;
											return formatNumber(value, 2, 1);
										}
									}
								]

							}
						],
						dataModel:{
			              	 method:'POST',
			              	 location:'remote',
			              	 url:'',
			              	 recIndx: 'proj_name' //必填 且该列不能为空  
			             },
			             usePager:false,width: '100%', height: '100%',checkbox: true,
			             toolbar: {
			               items: [
			                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
			             ]},
					});
				}

				//打印回调方法
				function lodopPrint() {
					var head = "";
					grid.options.lodop.head = head;
					grid.options.lodop.fn = renderFunc;
					grid.options.lodop.title = "项目预算查询(一)";
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
							<input type="text" id="year_input">
						</td>
						<td class="label">项目名称：</td>
						<td class="ipt">
							<select name="" id="proj_name_select" style="width:180px;"></select>
						</td>
						<td class="label">项目类型：</td>
						<td class="ipt">
							<select name="" id="type_code_select" style="width:180px;"></select>
						</td>
					</tr>
					<tr>
						<td class="label">项目级别：</td>
						<td class="ipt">
							<select name="" id="level_code_select" style="width:180px;"></select>
						</td>
						<td class="label">项目状态：</td>
						<td class="ipt">
							<select name="" id="proj_state_select" style="width:180px;"></select>
						</td>
						<td class="label">负责人：</td>
						<td class="ipt">
							<select name="" id="con_emp_select" style="width:180px;"></select>
						</td>
					</tr>
				</table>
			</div>

			<div id="maingrid"></div>

		</body>

		</html>