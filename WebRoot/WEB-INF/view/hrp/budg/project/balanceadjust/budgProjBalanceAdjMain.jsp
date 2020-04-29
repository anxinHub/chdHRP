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
				<jsp:param value="select,datepicker,ligerUI" name="plugins" />
			</jsp:include>
			<script type="text/javascript">
				var grid;
				var gridManager = null;
				var userUpdateStr;
				$(function () {
					//加载数据
					loadHead(null);
					loadHotkeys();
					// query();

					// $("#begin_date").change(function () {
					// 	query();
					// });

					// $("#end_date").change(function () {
					// 	query();
					// });

					// $("#remark").change(function () {
					// 	query();
					// });

					// $("#state").change(function () {
					// 	query();
					// });

					// console.log(grid)
				});
				//查询
				function query() {
					var date = date_input.getValue();
					var start_date = "";
					var end_date = "";
					if (typeof date === "string") {
						start_date = date;
					} else {
						start_date = date[0];
						end_date = date[1];
					}

					grid.options.parms = [
						{ name: 'begin_date', value: start_date},
						{ name: 'end_date', value: end_date },
						{ name: 'remark', value: $("#remark_input").val() },
						{ name: 'state', value: state_select.getValue() }
					];
					grid.options.newPage = 1;
					
					//加载查询条件
					grid.loadData(grid.where);
				}

				function loadHead() {
					grid = $("#maingrid").ligerGrid({
						columns: [

							{
								display: '调整单号', name: 'adj_code', align: 'left',
								render: function (rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('" + rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.adj_code + "')>"
										+ rowdata.adj_code + "</a>";
								}
							},
							{
								display: '摘要', name: 'remark', align: 'left'
							},
							{
								display: '调整金额合计', name: 'remain_adj_sum', align: 'right'
							},
							{
								display: '制单人', name: 'emp_name', align: 'left'
							},
							{
								display: '制单日期', name: 'make_date', align: 'left'
							},
							{
								display: '审核人', name: 'che_emp_name', align: 'left'
							},
							{
								display: '审核日期', name: 'check_date', align: 'left'
							},
							{
								display: '状态', name: 'state_name', align: 'left'
							}
						],
						dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgProjRemainAdj.do?isCheck=false',
						width: '100%', height: '100%', checkbox: true, rownumbers: true,
						selectRowButtonOnly: true,//heightDiff: -10,
						toolbar: {
							items: [
								{ text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' },
								{ line: true },
								{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
								{ line: true },
								{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
								{ line: true },
								{ text: '审核（<u>H</u>）', id: 'review', click: review, icon: 'bluebook' },
								{ line: true },
								{ text: '销审（<u>R</u>）', id: 'cancel', click: cancel, icon: 'candle' },
								{ line: true }
							]
						},
						onDblClickRow: function (rowdata, rowindex, value) {
							openUpdate(
								rowdata.group_id + "|" +
								rowdata.hos_id + "|" +
								rowdata.copy_code + "|" +
								rowdata.adj_code + "|" +
								rowdata.proj_id
							);
						}
					});

					gridManager = $("#maingrid").ligerGetGridManager();
				}

				function add_open() {

					parent.$.ligerDialog.open({
						url: 'hrp/budg/project/balanceadjust/budgProjRemainAdjAddPage.do?isCheck=false', data: {},
						height: 300, width: 800, title: '经费余额调整添加', modal: true, showMax: true,
						showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量

					});
				}

				function openUpdate(obj) {
					var vo = obj.split("|");
					var parm =
						"group_id=" + vo[0] + "&" +
						"hos_id=" + vo[1] + "&" +
						"copy_code=" + vo[2] + "&" +
						"adj_code=" + vo[3]

					parent.$.ligerDialog.open({
						url: 'hrp/budg/project/balanceadjust/budgProjRemainAdjUpdatePage.do?isCheck=false&' + parm, data: {},
						height: 300, width: 800, title: '经费余额调整查看', modal: true, showMax: true,
						showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
					});

				}

				//审核
				function review() {
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
						return false;
					}
					$(data).each(function () {

						var ParamVo = [];
						$(data).each(function () {
							ParamVo.push(
								this.group_id + "@" +
								this.hos_id + "@" +
								this.copy_code + "@" +
								this.adj_code
							)
						});

						$.ligerDialog.confirm('确定审核?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("reviewbudgProjRemainAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					})
				}

				//销审
				function cancel() {
					var data = gridManager.getCheckedRows();
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
						return false;
					}
					$(data).each(function () {

						var ParamVo = [];
						$(data).each(function () {
							ParamVo.push(
								this.group_id + "@" +
								this.hos_id + "@" +
								this.copy_code + "@" +
								this.adj_code
							)
						});
						$.ligerDialog.confirm('确定销审?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("cancelbudgProjRemainAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					})
				}

				//删除
				function remove() {
					var msg = "";
					var data = gridManager.getCheckedRows();
					console.log(data)
					if (data.length == 0) {
						$.ligerDialog.error('请选择行');
						return;
					} else {
						var ParamVo = [];
						$(data).each(function () {
							if (this.state != '01') {
								msg += this.adj_code + " ";
							}
							ParamVo.push(
								this.group_id + "@" +
								this.hos_id + "@" +
								this.copy_code + "@" +
								this.adj_code
							)
						});

						if (msg) {
							$.ligerDialog.error('单号为' + msg + '的数据，不是新建状态，无法执行删除操作！');
							return false;
						}

						$.ligerDialog.confirm('确定删除?', function (yes) {
							if (yes) {
								ajaxJsonObjectByUrl("deleteBudgProjRemainAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
									if (responseData.state == "true") {
										query();
									}
								});
							}
						});
					}
				}

				//键盘事件
				function loadHotkeys() {
					hotkeys('Q', query);
					hotkeys('A', add);
					hotkeys('D', remove);
				}
			</script>
			<script>
				var date_input,state_select;
				$(function () {
					init();
				});

				function init() {
					date_input = $("#date_input").etDatepicker({
						range: true,
						onChanged:query
					});

					state_select =$("#state_select").etSelect({
						url:"../../../budg/queryBudgState.do?isCheck=false",
						defaultValue:"none",
						onChange:query
					});
				}
			</script>
		</head>

		<body style="padding: 0px; overflow: hidden;">
			<div id="pageloading" class="l-loading" style="display: none"></div>
			<div id="toptoolbar"></div>
			
			<div class="main">
				<table class="table-layout">
					<tr>
						<td class="label">调整日期：</td>
						<td class="ipt">
							<input type="text" id="date_input">
						</td>
						<td class="label">摘要：</td>
						<td class="ipt">
							<input type="text" id="remark_input">
						</td>
						<td class="label">状态：</td>
						<td class="ipt">
							<select name="" id="state_select" style="width:180px;"></select>
						</td>
					</tr>
				</table>
			</div>

			<div id="maingrid"></div>

		</body>

		</html>