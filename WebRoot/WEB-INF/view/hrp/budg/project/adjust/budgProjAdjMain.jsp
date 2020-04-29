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
			loadHead();
			loadHotkeys();
		});
		//查询
		function query() {
			grid.options.parms = [
				{ name: 'budg_year', value: year_input.getValue() },
				{ name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
				{ name: 'emp_id', value: emp_select.getValue() },
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
						display: '调整单号', name: 'adj_code', align: 'left', width: 150,
						render: function (rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('" + rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "|"
								+ rowdata.budg_year + "|"
								+ rowdata.adj_code + "|"
								+ rowdata.proj_id + "')>"
								+ rowdata.adj_code + "</a>";
						}
					},
					{
						display: '项目名称', name: 'proj_name', align: 'left', width: 250
					},
					{
						display: '项目负责人', name: 'con_emp_name', align: 'left', width: 80
					},
					{
						display: '相关文件', name: 'file_url', align: 'left', width: 150
					},
					{
						display: '制单人', name: 'emp_name', align: 'left', width: 80
					},
					{
						display: '制单日期', name: 'make_date', align: 'left', width: 80
					},
					{
						display: '审核人', name: 'che_emp_name', align: 'left', width: 80
					},
					{
						display: '审核日期', name: 'check_date', align: 'left', width: 80
					},
					{
						display: '状态', name: 'state_name', align: 'left', width: 80
					},
					{
						display: '调整说明', name: 'remark', align: 'left', width: 150
					}
				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgProjAdj.do',
				width: '100%', height: '100%', checkbox: true, rownumbers: true,
				delayLoad: true,
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: {
					items: [
						{ text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true },
						{ text: '提交（<u>S</u>）', id: 'submit', click: submit, icon: 'audit' },
						{ line: true },
						{ text: '撤回（<u>C</u>）', id: 'recall', click: recall, icon: 'uncashier' },
						{ line: true },
						{ text: '审核（<u>R</u>）', id: 'review', click: review, icon: 'bluebook' },
						{ line: true },
						{ text: '销审（<u>R</u>）', id: 'cancel', click: cancel, icon: 'outbox' },
						{ line: true }
					]
				},
				onDblClickRow: function (rowdata, rowindex, value) {
					openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.budg_year + "|" +
						rowdata.adj_code + "|" +
						rowdata.proj_id
					);
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function add_open() {

			parent.$.ligerDialog.open({
				url: 'hrp/budg/project/adjust/budgProjAdjAddPage.do?isCheck=false', data: {},
				height: 300, width: 800, title: '项目预算调整添加', modal: true, showMax: true,
				showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});

		}

		function openUpdate(obj) {
			var vo = obj.split("|");
			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"budg_year=" + vo[3] + "&" +
				"adj_code=" + vo[4] + "&" +
				"proj_id=" + vo[5]

			parent.$.ligerDialog.open({
				url: 'hrp/budg/project/adjust/budgProjAdjUpdatePage.do?isCheck=false&' + parm, data: {},
				height: 300, width: 800, title: '项目预算调整查看', modal: true, showMax: true,
				showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});

		}

		//提交
		function submit() {
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return false;
			}

			var ParamVo = [];
			$(data).each(function () {
				ParamVo.push(
					this.group_id + "@" +
					this.hos_id + "@" +
					this.copy_code + "@" +
					this.budg_year + "@" +
					this.adj_code + "@" +
					this.proj_id

				)
			});
			$.ligerDialog.confirm('确定提交?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("submitbudgProjAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

		//撤回
		function recall() {
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return false;
			}
			var ParamVo = [];
			$(data).each(function () {
				ParamVo.push(
					this.group_id + "@" +
					this.hos_id + "@" +
					this.copy_code + "@" +
					this.budg_year + "@" +
					this.adj_code + "@" +
					this.proj_id
				)
			});
			$.ligerDialog.confirm('确定撤回?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("recallbudgProjAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

		//审核
		function review() {
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return false;
			}
			var ParamVo = [];
			$(data).each(function () {
				ParamVo.push(
					this.group_id + "@" +
					this.hos_id + "@" +
					this.copy_code + "@" +
					this.budg_year + "@" +
					this.adj_code + "@" +
					this.proj_id
				)
			});
			$.ligerDialog.confirm('确定审核?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("reviewbudgProjAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

		//销审
		function cancel() {
			var data = gridManager.getCheckedRows();
			if (data.length === 0) {
				$.ligerDialog.error('请选择行');
				return false;
			}
			var ParamVo = [];
			$(data).each(function () {
				ParamVo.push(
					this.group_id + "@" +
					this.hos_id + "@" +
					this.copy_code + "@" +
					this.budg_year + "@" +
					this.adj_code + "@" +
					this.proj_id
				)
			});
			$.ligerDialog.confirm('确定销审?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("cancelbudgProjAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}

		//删除
		function remove() {
			var msg = "";
			var data = gridManager.getCheckedRows();

			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				return false;
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
						this.budg_year + "@" +
						this.adj_code + "@" +
						this.proj_id
					)
				});

				if (msg) {
					$.ligerDialog.error('单号为' + msg + '的数据，不是新建状态，无法执行删除操作！');
					return false;
				}
				$.ligerDialog.confirm('确定删除?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("deleteBudgProjAdj.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				});
			}
		}
		function imp() {

			var index = layer.open({
				type: 2,
				title: '导入',
				shadeClose: false,
				shade: false,
				maxmin: true, //开启最大化最小化按钮
				area: ['893px', '500px'],
				content: 'budgProjAdjImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {

			location.href = "downTemplate.do?isCheck=false";
		}

		//键盘事件
		function loadHotkeys() {

			hotkeys('Q', query);

			hotkeys('A', add);
			hotkeys('D', remove);
		}
	</script>
	<script>
		var year_input, proj_name_select, emp_select, state_select;
		$(function () {
			init();
		});

		function init() {
			getData("../../../budg/queryBudgYear.do?isCheck=false", function (data) {
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
				url: "../../../budg/queryProjName.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});

			emp_select = $("#emp_select").etSelect({
				url: "../../../budg/queryConEmpId.do?isCheck=false",
				defaultValue: "none",
				onChange: query
			});

			state_select = $("#state_select").etSelect({
				url: "../../../budg/queryBudgApplyState.do?isCheck=false",
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
				<td class="label">负责人：</td>
				<td class="ipt">
					<select name="" id="emp_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name="" id="state_select" style="width:180px;"></select>
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