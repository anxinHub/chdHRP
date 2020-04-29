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
		<jsp:param value="select,datepicker,dialog,grid" name="plugins" />
	</jsp:include>
	<script type="text/javascript">
		var grid;
		var gridManager = null;
		var userUpdateStr;
		$(function () {
			//加载数据
			loadHead();
			init();
		});
		
		var year_input, dept_name_select, awards_item_select, emp_id_select, emp_type_select;

		function init() {
			getData("../../../acc/queryYearMonth.do?isCheck=false", function (data) {
				year_input = $("#year_input").etDatepicker({
					view: "months",
					minView: "months",
					dateFormat: "yyyy.mm",
					minDate: data[0].text,
					maxDate: data[data.length - 1].text,
					todayButton: false,
				});
			});

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../queryBudgDeptDictAll.do?isCheck=false",
				defaultValue: "none"
			});

			awards_item_select = $("#awards_item_select").etSelect({
				url: "../../queryBudgAwardsItemAll.do?isCheck=false",
				defaultValue: "none"
			});

			emp_id_select = $("#emp_id_select").etSelect({
				url: "../../../acc/queryAllEmpDict.do?isCheck=false",
				defaultValue: "none"
			});

			emp_type_select = $("#emp_type_select").etSelect({
				url: "../../queryEmpTypeAll.do?isCheck=false",
				defaultValue: "none"
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

		function query() {
			var search = [{name: 'year',value: year_input.getValue("yyyy")},
				{name: 'month',	value: year_input.getValue("mm")},
				{name: 'dept_id',value: dept_name_select.getValue()},
				{name: 'emp_id',value: emp_id_select.getValue().split(".")[0]},
				{name: 'emp_type_code',	value: emp_type_select.getValue()},
				{name: 'awards_item_code',value: awards_item_select.getValue()},
			];
			grid.loadData(search, 'queryBudgEmpAwardsTotal.do');
		}

		function loadHead() {
			grid = $("#maingrid").etGrid({
				columns: [{	display: '年度',	name: 'year', width: 120,align: 'left'},
					{display: '月',name: 'month',width: 120,	align: 'left'},
					{display: '科室',width: 120,name: 'dept_name',align: 'left',},
					{display: '职工',width: 120,name: 'emp_name',align: 'left',},
					{display: '职工类别',width: 120,name: 'type_name',align: 'left'},
					{display: '奖金项目',	width: 120,	align: 'left',name: 'awards_item_name'},
					{display: '金额',name: 'amount',	align: 'right',	width:100,
						render: function (ui) {
								var cellData = ui.cellData;
								return formatNumber(cellData, 2, 1);
						}
					}
				],
				dataModel: { 
					recIndx: 'year' 
				},
				usePager: true,	url: 'queryBudgEmpAwardsTotal.do',
				width: '100%',	height: '100%',	checkbox: true,
				toolbar: {
					items: [{type: "button",label: '查询',icon: 'search',listeners: [{click: query}]},
					{type: "button",label: '添加',icon: 'plus',listeners: [{click: add_open}]},
					{type: "button",label: '删除',icon: 'minus',listeners: [{click: remove}]},
					{type: "button",label: '下载导入模板',icon: 'arrowthickstop-1-s',listeners: [{click: downTemplate}]},
					{type: "button",label: '导入',icon: 'arrowstop-1-s',listeners: [{click: imp}]
					}],
				},
				rowDblClick: function (evt, ui) {
					var rowdata = ui.rowData;
					openUpdate(
						rowdata.group_id + "|" +
						rowdata.hos_id + "|" +
						rowdata.copy_code + "|" +
						rowdata.year + "|" +
						rowdata.month + "|" +
						rowdata.dept_id + "|" +
						rowdata.emp_id + "|" +
						rowdata.awards_item_code
					);
				}
			});
		}

		function add_open() {
			  var dialog = $.etDialog.open({
				type: "iframe",
				url: 'budgEmpAwardsTotalAddPage.do?isCheck=false&',
				width: 600,
				height: 400,
				title: '添加页',
				/* isMax: true, */
				btnAlign: 'right',
				btn: ['确定', '取消'],
				btn1: function (index, el) {
					var childFrame = window[el.find('iframe')[0].name]; // 获取window对象
					childFrame.saveBudgEmpAwardsTotal();
				},
				btn2: function (index, el) {
					$.etDialog.close(index); // 关闭弹窗
					return false;
				}
			})   
		}

		function remove() {
			var data = grid.selectGetChecked(); //etGrid
			if (data.length == 0) {
				// $.ligerDialog.error('请选择行');  // liger
				$.etDialog.error('请选择行');   
			} else {
				var ParamVo = [];
				$(data).each(function () {
					// var rowdata = this;    // liger
					var rowdata = this.rowData;
					ParamVo.push(
						rowdata.group_id + "@" +
						rowdata.hos_id + "@" +
						rowdata.copy_code + "@" +
						rowdata.year + "@" +
						rowdata.month + "@" +
						rowdata.dept_id + "@" +
						rowdata.emp_id + "@" +
						rowdata.emp_type_code + "@" +
						rowdata.awards_item_code
					)
				});

				$.etDialog.confirm('确定删除?',function (){
					ajaxPostData({
						url: "deleteBudgEmpAwardsTotal.do",
						data: {ParamVo: ParamVo.toString()},
						success: function (res){
							if (res.state == "true") {
								query();
							}
						}
					})
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
				content: 'budgEmpAwardsTotalImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
		function downTemplate() {
			location.href = "downTemplate.do?isCheck=false";
		}

		function openUpdate(obj) {

			var vo = obj.split("|");
			var parm = 
				"year=" + vo[3] + "&" +
				"month=" + vo[4] + "&" +
				"dept_id=" + vo[5] + "&" +
				"emp_id=" + vo[6] + "&" +
				"awards_item_code=" + vo[7];

			var dialog = $.etDialog.open({
				type: "iframe",
				url: 'budgEmpAwardsTotalUpdatePage.do?isCheck=false&' + parm,
				width: 600,
				height: 400,
				title: '修改页',
				/* isMax: true, */
				btnAlign: 'right',
				btn: ['确定', '取消'],
				btn1: function (index, el) {
					var childFrame = window[el.find('iframe')[0].name]; // 获取window对象
					childFrame.saveBudgEmpAwardsTotal();
				},
				btn2: function (index, el) {
					$.etDialog.close(index); // 关闭弹窗
					return false;
				}
			})   
		}

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', query);
			hotkeys('A', add);
			hotkeys('D', remove);
			hotkeys('B', downTemplate);
			// hotkeys('E', exportExcel);
			// hotkeys('P', printDate);
			hotkeys('I', imp);
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">科室：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">项目编码：</td>
				<td class="ipt">
					<select name="" id="awards_item_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">职工：</td>
				<td class="ipt">
					<select name="" id="emp_id_select" style="width:180px;"></select>
				</td>
				<td class="label">职工类别：</td>
				<td class="ipt">
					<select name="" id="emp_type_select" style="width:180px;"></select>
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>

</body>

</html>