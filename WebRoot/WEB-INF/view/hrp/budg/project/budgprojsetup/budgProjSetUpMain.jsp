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
		$(function () {
			//加载数据
			loadHead(null);
			loadHotkeys();

		});
		//查询
		function query() {
			var date = date_input.getValue();
			var start_date = "" ;
			var end_date = "";
			if(typeof date === "string"){
				start_date = date;
			}else{
				start_date = date[0];
				end_date = date[1];
			}

			var search = [
				{ name: 'proj_name', value: $("#proj_name_input").val().toUpperCase() },
				{ name: 'type_code', value: proj_type_select.getValue() },
				{ name: 'level_code', value: proj_level_select.getValue() },
				{ name: 'con_emp_id', value: con_emp_select.getValue() },
				{ name: 'start_app_date', value: start_date },
				{ name: 'end_app_date', value: end_date },
				{ name: 'state', value: state_select.getValue() },
				{ name: 'apply_code', value: $("#apply_code_input").val().toUpperCase() }
			]
			grid.options.parms = search;
			grid.options.newPage = 1;
			//加载查询条件
			grid.loadData(grid.where);
		}
		//获取查询条件的数值

		function loadHead() {
			grid = $("#maingrid").ligerGrid({
				columns: [
					{ display: '立项申请单号', name: 'apply_code', align: 'left',width:150 },
					{ display: '项目编码', name: 'proj_code', align: 'left',width:100 },
					{ display: '项目名称', name: 'proj_name', align: 'left',width:150 },
					{ display: '项目类型', name: 'type_name', align: 'left',width:120 },
					{ display: '项目级别', name: 'level_name', align: 'left',width:100 },
					{ display: '项目负责人', name: 'con_emp_name', align: 'left',width:80 },
					{ display: '财务负责人', name: 'acc_emp_name', align: 'left',width:80 },
					{ display: '填报部门', name: 'dept_name', align: 'left',width:100 },
					{ display: '填报人', name: 'app_emp_name', align: 'left',width:80 },
					{ display: '填报日期', name: 'app_date', align: 'left',width:80 },
					{ display: '审核人', name: 'checker_name', align: 'left',width:80 },
					{ display: '审核日期', name: 'check_date', align: 'left',width:80 },
					{ display: '立项日期', name: 'app_date', align: 'left',width:80 },
					{ display: '状态', name: 'value_name', align: 'left',width:80 },
					{
						display: '是否结转', name: 'is_carry', align: 'left',width:60,
						render: function (item) {
							if (parseInt(item.is_carry) == 1) {
								return '是';
							}
							return '否';
						}
					}
				],
				dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgProjSetUp.do',
				width: '100%', height: '100%', checkbox: true, rownumbers: true,
				selectRowButtonOnly: true,
				toolbar: {
					items: [
						{ text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
						{ line: true },
						{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
						{ line: true },
						{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
						{ line: true },
						{ text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
						{ line: true },
						//{ text: '导入（<u>I</u>）', id: 'import', click: imp, icon: 'up' },
						//{ line: true },
						{ text: '提交', id: 'submit', click: submit, icon: 'up' },
						{ line: true },
						{ text: '撤回', id: 'recall', click: recall, icon: 'up' },
						{ line: true },
						{ text: '审核', id: 'audit', click: audit, icon: 'settle' },
						{ line: true },
						{ text: '消审', id: 'cancelaudit', click: cancelaudit, icon: 'unaudit' }
					]
				},
				onDblClickRow: function (rowdata, rowindex, value) {
					openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.apply_code + "|" +
						rowdata.proj_code
					);
				}
			});

			gridManager = $("#maingrid").ligerGetGridManager();
		}
		//撤回
		function recall() {
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
						this.proj_code + "@" +
						this.apply_code
					)
				});
				$.ligerDialog.confirm('确定撤回?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("recallBudgProjSetUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				})
			}
		}
		//提交
		function submit() {
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
						this.proj_code + "@" +
						this.apply_code
					)
				});
				$.ligerDialog.confirm('确定提交?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("submitBudgProjSetUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				})
			}
		}
		//消审
		function cancelaudit() {
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
						this.proj_code + "@" +
						this.apply_code
					)
				});
				$.ligerDialog.confirm('确定消审?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("cancelauditBudgProjSetUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				})
			}

		}
		//审核
		function audit() {
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
						this.proj_code + "@" +
						this.apply_code
					)
				});
				$.ligerDialog.confirm('确定审核?', function (yes) {
					if (yes) {
						ajaxJsonObjectByUrl("auditBudgProjSetUp.do", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
							}
						});
					}
				})
			}


		}
		function add_open() {
			$.ligerDialog.open({
				url: 'budgProjSetUpAddPage.do?isCheck=false&', data: {}, height: 510, width: 700, title: '立项申请单添加', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [
					{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgProjSetUp(); }, cls: 'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});
		}

		function remove() {
			var data = gridManager.getCheckedRows();
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
			} else {
				var ParamVo = [];
				var msg = '';
				$(data).each(function () {
					if (this.state != '01') {
						msg += this.apply_code + ";"
					}
					ParamVo.push(
						this.group_id + "@" +
						this.hos_id + "@" +
						this.copy_code + "@" +
						this.apply_code
					)
				});
				if (msg != '') {
					$.ligerDialog.error("以下数据:【" + msg + "】不是新建状态，不能删除！！")
				} else {
					$.ligerDialog.confirm('确定删除?', function (yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteBudgProjSetUp.do", { ParamVo: ParamVo.toString() }, function (responseData) {
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
				content: 'budgProjSetUpImportPage.do?isCheck=false'
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
				"apply_code=" + vo[3] + "&" +
				"proj_code=" + vo[4];
			$.ligerDialog.open({
				url: 'budgProjSetUpUpdatePage.do?isCheck=false&' + parm, data: {}, height: 510, width: 700, title: '立项申请单修改页', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [
					{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgProjSetUp(); }, cls: 'l-dialog-btn-highlight' },
					{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});


		}
	
		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add);
			hotkeys('D', remove);
			hotkeys('B', downTemplate);
			hotkeys('I', imp);
		}
	</script>
	<script>
		var date_input, state_select, proj_level_select, con_emp_select;
		$(function () {
			init();
		});

		function init() {
			date_input = $("#date_input").etDatepicker({
				range: true
			});

			state_select = $("#state_select").etSelect({
				url: "../../qureyProjSetUpStateSelect.do?isCheck=false",
				defaultValue: "none"
			});

			proj_type_select = $("#proj_type_select").etSelect({
				url: "../../../sys/queryProjTypeDict.do?isCheck=false",
				defaultValue: "none"
			});

			proj_level_select = $("#proj_level_select").etSelect({
				url: "../../../sys/queryProjLevelDict.do?isCheck=false",
				defaultValue: "none"
			});

			con_emp_select = $("#con_emp_select").etSelect({
				url: "../../../sys/queryEmpDict.do?isCheck=false",
				defaultValue: "none"
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
				<td class="label">立项日期：</td>
				<td class="ipt">
					<input type="text" id="date_input">
				</td>
				<td class="label">项目名称：</td>
				<td class="ipt">
					<input type="text" id="proj_name_input">
				</td>
				<td class="label">申请单号：</td>
				<td class="ipt">
					<input type="text" id="apply_code_input">
				</td>
				<td class="label">状态：</td>
				<td class="ipt">
					<select name="" id="state_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">项目类型：</td>
				<td class="ipt">
					<select name="" id="proj_type_select" style="width:180px;"></select>
				</td>
				<td class="label">项目级别：</td>
				<td class="ipt">
					<select name="" id="proj_level_select" style="width:180px;"></select>
				</td>
				<td class="label">负责人：</td>
				<td class="ipt">
					<select name="" id="con_emp_select" style="width:180px;"></select>
				</td>
				<td class="label"></td>
				<td class="label"></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
</body>

</html>