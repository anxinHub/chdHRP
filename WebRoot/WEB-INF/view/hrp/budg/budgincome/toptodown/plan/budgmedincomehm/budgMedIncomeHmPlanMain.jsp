<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,ligerUI" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var budg_year ;
	var year_input,subj_name_select,subj_level_select;
	$(function () {
		//加载数据
		init();
		loadHead(null);
		loadHotkeys();

	});
	//查询
	function query() {
		if(!year_input.getValue()){
			$.ligerDialog.warn("年度不能为空");
			return false;
		}
		var search = [
			{ name: 'budg_year', value: year_input.getValue() },
			{ name: 'subj_code', value: subj_name_select.getValue().split(",")[0] },
			{ name: 'subj_level', value: subj_level_select.getValue() }
		];

		grid.options.parms = search;
		grid.options.newPage = 1;
		
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns: [
				{display: '科目编码', name: 'subj_code', align: 'left',width:150,
					render: function (rowdata, rowindex, value) {
						return "<a href=javascript:openUpdate('" + rowdata.group_id + "|" +
							rowdata.hos_id + "|" + rowdata.copy_code + "|" + rowdata.budg_year + "|"
							+ rowdata.subj_code + "')>"
							+ rowdata.subj_code + "</a>";
					}
				},
				{display: '科目全称', name: 'subj_name_all', align: 'left',width:500},
				{display: '科目级次', name: 'subj_level', align: 'center',width:70},
				{display: '分解方法', name: 'resolve_way_name', align: 'left',},
			],
			dataAction: 'server', dataType: 'server', usePager: true, url: 'queryBudgMedIncomeHmPlan.do?isCheck=false',
			width: '100%', height: '100%', checkbox: true, rownumbers: true,
			delayLoad:true,
			selectRowButtonOnly: true,//heightDiff: -10,
			toolbar: {
				items: [
					{ text: '查询（<u>E</u>）', id: 'search', click: query, icon: 'search' },
					{ line: true },
					{ text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' },
					{ line: true },
					{ text: '生成（<u>G</u>）', id: 'generate', click: generate, icon: 'generate' },
					{ line: true },
					{ text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' },
					{ line: true },
					{ text: '批量设置分解方法（<u>S</u>）', id: 'setWay', click: setWay, icon: 'setting' },
					{ line: true }/* ,
					{ text: '打印（<u>P</u>）', id: 'print', click: printDate, icon: 'print' },
					{ line: true },
					{ text: '导入（<u>I</u>）', id: 'import', click: imp, icon: 'up' },
					{ line: true },
					{ text: '下载导入模板（<u>B</u>）', id: 'downTemplate', click: downTemplate, icon: 'down' },
					{ line: true } */
				]
			},
			onDblClickRow: function (rowdata, rowindex, value) {
				openUpdate(
					rowdata.group_id + "|" +
					rowdata.hos_id + "|" +
					rowdata.copy_code + "|" +
					rowdata.budg_year + "|" +
					rowdata.subj_code
				);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		$.ligerDialog.open({
			url: 'budgMedIncomeHmPlanAddPage.do?isCheck=false&', data: {}, height: 300, width: 450, title: '医院年度至科室年度医疗收入预算分解方案', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgMedIncomeHmPlanAdd(); }, cls: 'l-dialog-btn-highlight' },
			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
		});
	}
	//生成
	function generate() {
		if (!year_input.getValue()) {
			$.ligerDialog.warn("年度不能为空");
			return false;
		} else {
			var formPara = {
				budg_year: year_input.getValue()
			};
			$.ligerDialog.confirm('确定生成?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("generate.do?isCheck=false", formPara, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});

				}
			});
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
					this.subj_code
				)
			});
			$.ligerDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBudgMedIncomeHmPlan.do", { ParamVo: ParamVo.toString() }, function (responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//打印数据
	function printDate() {
		if (grid.getData().length == 0) {
			$.ligerDialog.error("无打印数据！");
			return;
		}

		grid.options.parms = [];

		grid.options.parms.push({ name: 'budg_year', value: year_input.getValue() });
		grid.options.parms.push({ name: 'subj_code', value: subj_name_select.getValue() });
		grid.options.parms.push({ name: 'subj_level', value: subj_level_select.getValue() });
		var selPara = {};
		$.each(grid.options.parms, function (i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount: 2,
			title: "医院年度至医院月份医疗收入预算分解方案",
			type: 3,
			columns: grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgMedIncomeHmPlan.do?isCheck=false", selPara, function (responseData) {
			printGridView(responseData, printPara);
		});
	}

	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function imp() {

		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgMedIncomeHmPlanImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	// 批量设置
	function setWay() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$.ligerDialog.open({
				url: 'budgMedIncomeHmPlanSetPage.do?isCheck=false', data: {}, height: 250, width: 450, title: '医院月份医疗收入预算编制方案批量设置', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
				buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgMedIncomeHmPlan(); }, cls: 'l-dialog-btn-highlight' },
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
				]
			});
		}

	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm =
			"group_id=" + vo[0] + "&" +
			"hos_id=" + vo[1] + "&" +
			"copy_code=" + vo[2] + "&" +
			"budg_year=" + vo[3] + "&" +
			"subj_code=" + vo[4]


		$.ligerDialog.open({
			url: 'budgMedIncomeHmPlanUpdatePage.do?isCheck=false&' + parm, data: {}, height: 300, width: 450, title: '医院月份医疗收入预算编制方案修改', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveBudgMedIncomeHmPlan(); }, cls: 'l-dialog-btn-highlight' },
			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
		});


	}
	function init(){
		year_input = $("#year_input").etDatepicker({
			defaultDate:true,
			view: "years",
			minView: "years",
			dateFormat: "yyyy",
			todayButton: false,
			onSelect: function (value) {
				reloadSubjCode(value);
				query();
					
			}
		});
		
		setTimeout(function () {
			subj_name_select = $("#subj_name_select").etSelect({
				url:"../../../../queryBudgSubj.do?isCheck=false&subj_type=04&type_code=01&is_last=1&budg_year="+year_input.getValue(),
				defaultValue: "none",
				onChange: query
			});
		}, 10);
		function reloadSubjCode(year){
			subj_name_select.reload({
				url:"../../../../queryBudgSubj.do?isCheck=false",
				para:{
					subj_type:"04",
					budg_year:year,
					type_code:"01",
					is_last:1
				}
			})
		}

		subj_level_select = $("#subj_level_select").etSelect({
			url:"../../../../queryBudgSubjLevel.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});
		
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		
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
				<select name="" id="subj_name_select" style="width:180px;"></select>
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