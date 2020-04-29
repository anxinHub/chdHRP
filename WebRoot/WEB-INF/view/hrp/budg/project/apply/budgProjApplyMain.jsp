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
		var budg_year;
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();
			
		});
		//查询
		function query() {
			grid.options.parms = [
				{ name: 'budg_year', value: year_input.getValue() },
				{ name: 'apply_type', value: app_type_select.getValue() },
				{ name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
				{ name: 'proj_no', value: proj_name_select.getValue().split(",")[1] },
				{ name: 'apply_code', value: app_code_select.getValue() },
				{ name: 'con_emp_id', value: con_emp_select.getValue() },
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
						display: '申报单号', name: 'apply_code', align: 'left', width: 120,
						render: function (rowdata, rowindex, value) {
							return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" + rowdata.hos_id + "|"
								+ rowdata.copy_code + "|" + rowdata.budg_year +
								"|" + rowdata.apply_code + "|" + rowdata.proj_id + "')>" + rowdata.apply_code + "</a>";
						}

					},
					{
						display: '预算年度', name: 'budg_year', align: 'left', width: 80,
					},
					{
						display: '项目名称', name: 'proj_name', align: 'left'
					},
					{
						display: '申报类型', name: 'apply_type_name', align: 'left', width: 100
					},
					{
						display: '申报金额', name: 'apply_amount', align: 'right', width: 120,
						render: function (rowdata, rowindex, value) {
							if (value) {
								return formatNumber(value, 2, 1);
							}
						}
					},
					{
						display: '制单人', name: 'maker_name', align: 'left', width: 80,
					},
					{
						display: '制单日期', name: 'make_date', align: 'left', width: 80,
					},
					{
						display: '审核人', name: 'checker_name', align: 'left', width: 80
					},
					{
						display: '审核日期', name: 'check_date', align: 'left', width: 80
					},
					{
						display: '状态', name: 'state', align: 'left', width: 50
					}
				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgProjApply.do',
				width: '100%', height: '100%', checkbox: true, rownumbers: true,
				delayLoad: true,
				selectRowButtonOnly: true,//heightDiff: -10,
				toolbar: {
					items: [
						{ text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						{ text: '添加（<u>S</u>）', id: 'add', click: add_open, icon: 'add' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true },
						{ text: '提交（<u>T</u>）', id: 'up', click: submitBudgProjApply, icon: 'up' },
						{ line: true },
						{ text: '撤回（<u>R</u>）', id: 'back', click: recallBudgProjApply, icon: 'back' },
						{ line: true },
						{ text: '审核（<u>A</u>）', id: 'audit', click: auditBudgProjApply, icon: 'audit' },
						{ line: true },
						{ text: '消审（<u>U</u>）', id: 'bcancle', click: reAuditBudgProjApply, icon: 'bcancle' },
						{ line: true },
						{ text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
						{ line: true },
						{ text: '导入（<u>I</u>）', id: 'import', click: imp, icon: 'up' },
						{ line: true },
						{ text: '打印（<u>P</u>）', id: 'print', click: printDate, icon: 'print' }
					]
				},
				onDblClickRow: function (rowdata, rowindex, value) {
					openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.budg_year + "|" +
						rowdata.apply_code + "|" +
						rowdata.proj_id
					);
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}

		function add_open() {
			if ('${mark}' == '1') {
				parent.$.ligerDialog.open({
					url: 'hrp/budg/project/apply/budgProjApplyAddPage.do?isCheck=false', data: {}, height: 300, width: 800,
					title: '项目预算申报添加', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});
			} else {
				$.ligerDialog.error('期初项目预算未记账,不允许申报!');
			}

		}

		function remove() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = '';
				var ParamVo = [];
				$(data).each(function () {
					if (this.state != '新建') {
						msg += this.apply_code + "、";
					}

					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.budg_year + "@" +
						this.apply_code + "@" +
						this.proj_id  + "@" +
						this.file_url
					)
				});

				if (msg != '') {

					$.ligerDialog.error('以下数据:【' + msg.substring(0, msg.length - 1) + '】不是新建状态,不允许删除!');

				} else {
					$.ligerDialog.confirm('确定删除?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteBudgProjApply.do", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
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
				content: 'budgProjApplyImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {

			location.href = "downTemplate.do?isCheck=false";
		}

		function openUpdate(obj) {

			var vo = obj.split("|");

			var parm =
				"group_id=" + vo[0] + "&" +
				"hos_id=" + vo[1] + "&" +
				"copy_code=" + vo[2] + "&" +
				"budg_year=" + vo[3] + "&" +
				"apply_code=" + vo[4] + "&" +
				"proj_id=" + vo[5]


			parent.$.ligerDialog.open({
				url: 'hrp/budg/project/apply/budgProjApplyUpdatePage.do?isCheck=false&' + parm,
				data: {}, height: 300, width: 450, title: '项目预算申报修改', modal: true, showToggle: false, showMax: true,
				showMin: false, isResize: true, parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			});
		}

		//打印
		function printDate() {
			if (grid.getData().length == 0) {
				$.ligerDialog.error("无打印数据！");
				return;
			}

			grid.options.parms = [
				{ name: 'budg_year', value: year_input.getValue() },
				{ name: 'apply_type', value: app_type_select.getValue() },
				{ name: 'proj_id', value: proj_name_select.getValue().split(",")[0] },
				{ name: 'proj_no', value: proj_name_select.getValue().split(",")[1] },
				{ name: 'apply_code', value: app_code_select.getValue() },
				{ name: 'con_emp_id', value: con_emp_select.getValue() },
				{ name: 'state', value: state_select.getValue() }
			];

			
			var selPara = {};
			$.each(grid.options.parms, function (i, obj) {
				selPara[obj.name] = obj.value;
			});
			var printPara = {
				headCount: 2,
				title: "项目预算申报",
				type: 3,
				columns: grid.getColumns(1)
			};
			ajaxJsonObjectByUrl("queryBudgProjApply.do?isCheck=false", selPara, function (responseData) {
				printGridView(responseData, printPara);
			});
		}

		//提交
		function submitBudgProjApply() {
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = '';
				var ParamVo = [];
				$(data).each(function () {
					if (this.state != '新建') {
						msg += this.apply_code + "、";
					}

					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.budg_year + "@" +
						this.apply_code + "@" +
						this.proj_id + "@" + '02'

					)
				});

				if (msg != '') {

					$.ligerDialog.error('以下数据:【' + msg.substring(0, msg.length - 1) + '】不是新建状态,不允许提交!');

				} else {
					$.ligerDialog.confirm('确定提交选中数据吗?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("updateBudgProjApplyState.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
			}
		}
		//撤回
		function recallBudgProjApply() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = '';
				var ParamVo = [];
				$(data).each(function () {
					if (this.state != '已提交') {
						msg += this.apply_code + "、";
					}

					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.budg_year + "@" +
						this.apply_code + "@" +
						this.proj_id + "@" + '01'

					)
				});

				if (msg != '') {

					$.ligerDialog.error('以下数据:【' + msg.substring(0, msg.length - 1) + '】不是已提交状态,不允许撤回!');

				} else {
					$.ligerDialog.confirm('确定撤回选中数据吗?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("updateBudgProjApplyState.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
			}
		}
		//审核
		function auditBudgProjApply() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = '';
				var ParamVo = [];
				$(data).each(function () {
					if (this.state != '已提交') {
						msg += this.apply_code + "、";
					}

					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.budg_year + "@" +
						this.apply_code + "@" +
						this.proj_id + "@" + '03'

					)
				});

				if (msg != '') {

					$.ligerDialog.error('以下数据:【' + msg.substring(0, msg.length - 1) + '】不是已提交状态,不允许审核!');

				} else {
					$.ligerDialog.confirm('确定审核选中数据吗?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("auditBudgProjApply.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
			}
		}
		//消审
		function reAuditBudgProjApply() {

			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var msg = '';
				var ParamVo = [];
				$(data).each(function () {
					if (this.state != '已审核') {
						msg += this.apply_code + "、";
					}

					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.budg_year + "@" +
						this.apply_code + "@" +
						this.proj_id + "@" + '02'

					)
				});

				if (msg != '') {

					$.ligerDialog.error('以下数据:【' + msg.substring(0, msg.length - 1) + '】不是已审核状态,不允许消审!');

				} else {
					$.ligerDialog.confirm('确定消审选中数据吗?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("reAuditBudgProjApply.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
			}
		}

		//键盘事件
		function loadHotkeys() {

			hotkeys('Q', query);

			hotkeys('S', add_open);
			hotkeys('D', remove);
			hotkeys('T', submitBudgProjApply);
			hotkeys('R', recallBudgProjApply);
			hotkeys('A', auditBudgProjApply);
			hotkeys('U', reAuditBudgProjApply);
			hotkeys('B', downTemplate);

			hotkeys('P', printDate);
			hotkeys('I', imp);

		}
	</script>
	<script>
		var year_input, app_type_select, proj_name_select, app_code_select, con_emp_select, state_select;
		$(function () {
			init();
		});

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
					onChanged: function (value) {
						reloadAppCode(value);
						query()
					}
				});
			});

			app_type_select = $("#app_type_select").etSelect({
				url:"../../queryBudgApplyType.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			proj_name_select = $("#proj_name_select").etSelect({
				url:"../../queryProjName.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			app_code_select = $("#app_code_select").etSelect({
				defaultValue:"none",
				onChange:query
			});
			function reloadAppCode(year){
				app_code_select.reload({
					url:"../../queryBudgApplyCode.do?isCheck=false",
					para:{
						budg_year:year
					}
				})
			}


			con_emp_select = $("#con_emp_select").etSelect({
				url:"../../queryConEmpId.do?isCheck=false",
				defaultValue:"none",
				onChange:query
			});

			state_select = $("#state_select").etSelect({
				url:"../../queryBudgApplyState.do?isCheck=false",
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
			})
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
				<td class="label">申报类型：</td>
				<td class="ipt">
					<select name="" id="app_type_select" style="width:180px;"></select>
				</td>
				<td class="label">项目：</td>
				<td class="ipt">
					<select name="" id="proj_name_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">申报单号：</td>
				<td class="ipt">
					<select name="" id="app_code_select" style="width:180px;"></select>
				</td>
				<td class="label">负责人：</td>
				<td class="ipt">
					<select name="" id="con_emp_select" style="width:180px;"></select>
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