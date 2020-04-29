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
			<script type="text/javascript">
				var grid;
				var gridManager = null;
				var userUpdateStr;
				var edit_method;
				//打印 单元格格式化 用
				var renderFunc = {};
				$(function () {
					//加载数据
					loadHead(null);
					loadHotkeys();
				});
				//查询
				function query() {
					grid.options.parms = [
						{ name: 'budg_year', value: year_input.getValue() },
						{ name: 'index_code', value: index_code_select.getValue() },
						{ name: 'budg_level', value: "02" }
					];
					grid.options.newPage = 1;

					//加载查询条件
					grid.loadData(grid.where);
				}

				function loadHead() {
					grid = $("#maingrid").ligerGrid({
						columns: [
							{
								display: '预算年度', name: 'budg_year', align: 'left', width: 80
							},
							{
								display: '指标编码', name: 'index_code', align: 'left', width: 100,
								render: function (rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" +
										rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.budg_year + "|"
										+ rowdata.budg_level + "|" + rowdata.index_code + "')>"
										+ rowdata.index_code + "</a>";
								}
							},
							{
								display: '指标名称', name: 'index_name', align: 'left', width: 180
							},
							{
								display: '是否独立核算', name: 'is_single_count', align: 'center', isAllowHide: false, isSort: false, width: 80,
								render: function (rowdata, rowindex, value) {
									if (rowdata.is_single_count == '√') {
										return "<input id=is_single_count" + rowdata.index_code + "  type ='checkbox' checked='checked' style='margin-top:8px;'>";
									} else {
										return "<input id=is_single_count" + rowdata.index_code + "  type ='checkbox' style='margin-top:8px;'>";
									}
								}
							},
							{
								display: '编制方法', name: 'method', align: 'left',
								columns: [
									{
										display: '编制方法', name: 'edit_method_name', align: 'left', width: 150

									},
									{
										display: '取值方法', name: 'get_way_name', align: 'left', width: 150

									},
									{
										display: '计算公式', name: 'formula_name', align: 'left', width: 150,
										render: function (rowdata, rowindex, value) {
											if (rowdata.formula_id) {
												return "<a href=javascript:openFormula('" + rowdata.group_id + "|" +
													rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.formula_id + "')>"
													+ rowdata.formula_name + "</a>";
											}
										}

									},
									{
										display: '取值函数', name: 'fun_name', align: 'left', width: 150,
										render: function (rowdata, rowindex, value) {
											if (rowdata.fun_id) {
												return "<a href=javascript:openFun('" + rowdata.group_id + "|" +
													rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.fun_id + "')>"
													+ rowdata.fun_name + "</a>";
											}
										}

									},
								]
							},

							{
								display: '分解或汇总', name: 'resolvesum', align: 'left',
								columns: [
									{
										display: '分解或汇总', name: 'resolve_or_sum_name', align: 'left', width: 100
									},
									{
										display: '分解方法', name: 'resolve_way_name', align: 'left', width: 150
									},
									{
										display: '分解层次', name: 'resolve_level_name', align: 'left', width: 100
									},
									{
										display: '汇总层次', name: 'sum_level_name', align: 'left', width: 100
									}

								]
							},
						],
						dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgHosMonthPlanUp.do?isCheck=false&budg_level=02',
						width: '100%', height: '100%', checkbox: true, rownumbers: true,
						delayLoad: true,//初始化不加载
						selectRowButtonOnly: true,//heightDiff: -10,
						toolbar: {
							items: [
								{ text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
								{ line: true },
								{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
								{ line: true },
								{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' }
							]
						},
						onDblClickRow: function (rowdata, rowindex, value) {
							openUpdate(
								rowdata.group_id + "|" +
								rowdata.hos_id + "|" +
								rowdata.copy_code + "|" +
								rowdata.budg_year + "|" +
								rowdata.budg_level + "|" +
								rowdata.index_code
							);
						}

					});

					gridManager = $("#maingrid").ligerGetGridManager();
				}

				//打印回调方法
				function lodopPrint() {
					var head = "";
					grid.options.lodop.head = head;
					grid.options.lodop.fn = renderFunc;
					grid.options.lodop.title = "医院年度业务预算编制方案";
				}

				function add_open() {

					var budg_year = year_input.getValue();
					if (budg_year) {

						parent.$.ligerDialog.open({
							url: 'hrp/budg/business/compilationplan/uptodown/hosmonthplan/budgHosMonthPlanAddPage.do?isCheck=false&budg_year=' + budg_year, data: {}, height: 400, width: 700,
							title: '医院月份业务预算编制方案添加', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
							parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
							buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgHosYearPlan(); }, cls: 'l-dialog-btn-highlight' },
							{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
							]
						});

					} else {

						$.ligerDialog.error('预算年度不能为空');

					}

				}

				function remove() {

					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
					} else {
						var ParamVo = [];
						$(data).each(function () {
							ParamVo.push(
								this.group_id + "@" +
								this.hos_id + "@" +
								this.copy_code + "@" +
								this.budg_year + "@" +
								this.budg_level + "@" +
								this.index_code
							)
						});
						$.ligerDialog.confirm('确定删除?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("deleteBudgHosMonthPlanUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					}
				}

				function openUpdate(obj) {

					var vo = obj.split("|");
					var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code=" + vo[2]
						+ "&budg_year=" + vo[3] + "&budg_level=" + vo[4] + "&index_code=" + vo[5]


					parent.$.ligerDialog.open({
						url: 'hrp/budg/business/compilationplan/uptodown/hosmonthplan/budgHosMonthPlanUpdatePage.do?isCheck=false&' + parm, data: {}, height: 400, width: 700,
						title: '医院月份业务预算编制方案修改', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
						parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
						buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgHosYearPlan(); }, cls: 'l-dialog-btn-highlight' },
						{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
						]
					});

				}

				//计算公式页面跳转
				function openFormula(obj) {

					var vo = obj.split("|");
					var parm =
						"group_id=" + vo[0] + "&" +
						"hos_id=" + vo[1] + "&" +
						"copy_code=" + vo[2] + "&" +
						"formula_id=" + vo[3]

					parent.$.ligerDialog.open({
						url: 'hrp/budg/common/budgformula/budgFormulaUpdatePage.do?isCheck=false&' + parm, data: {}, height: 500, width: 600,
						title: '计算公式', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
						parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					});
				}
				// 取值函数页面跳转
				function openFun(obj) {

					var vo = obj.split("|");
					var parm =
						"group_id=" + vo[0] + "&" +
						"hos_id=" + vo[1] + "&" +
						"copy_code=" + vo[2] + "&" +
						"fun_code=" + vo[3] + "&" +
						"index_type_code=" + '03' //指标类型 01基本指标 02费用标准 03预算指标  04收入科目

					parent.$.ligerDialog.open({
						url: 'hrp/budg/common/budgfun/budgFunUpdatePage.do?isCheck=false&' + parm,
						data: {}, height: 500, width: 600,
						title: '取值函数', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
						parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					});
				}

				//键盘事件
				function loadHotkeys() {
					hotkeys('Q', query);
					hotkeys('A', add);
					hotkeys('D', remove);
				}
			</script>
			<script>
				var year_input, index_code_select;
				$(function () {
					init();
				});

				function init() {
					year_input = $("#year_input").etDatepicker({
	                    view: "years",
	                    minView: "years",
	                    dateFormat: "yyyy",
	                    clearButton: false,
	                    onChange: function () {
	                        setTimeout(function () {
	                        	initColumns();
	                        }, 10);
	                    },
	                    defaultDate: true
	                });

					index_code_select = $("#index_code_select").etSelect({
						url: "../../../../queryBudgIndexDict.do?isCheck=false",
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
					});
				};
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
						<td class="label">预算指标：</td>
						<td class="ipt">
							<select name="" id="index_code_select" style="width:180px;"></select>
						</td>
						<td class="label"></td>
						<td class="ipt"></td>
					</tr>
				</table>
			</div>

			<div id="maingrid"></div>

		</body>

		</html>