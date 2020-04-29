<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/static_resource.jsp">
		 <jsp:param value="select,datepicker,grid,dialog" name="plugins"/>
	</jsp:include>
	<script>
		var date_input, dept_name_select, emp_type_select;
		var gridManager, grid;
		var columnArray = [
			{ display: '年度', name: 'year', width: 80 },
			{ display: '科室', name: 'dept_name', width: 120 },
			{ display: '职工类别', name: 'type_name', minWidth: 120, maxWidth: 120 }
		];
		$(function () {
			init();
			loadGrid();
			// loadHotkeys();
		});

		function init() {
			ajaxPostData({
				url:"../../queryBudgYear.do?isCheck=false",
				success: function (data) {
					date_input = $("#date_input").etDatepicker({
						defaultDate: data[0].text,
						view: "years",
						minView: "years",
						dateFormat: "yyyy",
						minDate: data[data.length - 1].text,
						maxDate: data[0].text,
						todayButton: false,
						onChanged: queryNew
					});
				}
			});

			dept_name_select = $("#dept_name_select").etSelect({
				url: "../../queryBudgHosDept.do?isCheck=false",
				defaultValue: "none",
				onChange: queryNew
			});

			emp_type_select = $("#emp_type_select").etSelect({
				url: "../../queryBudgEmpType.do?isCheck=false",
				defaultValue: "none",
				onChange: queryNew
			});
		}

		//查询
		function queryNew() {
			// 首先动态加载列头，然后query
			loadGridColumn(date_input.getValue())
			var params = [
				{ name: 'year', value: date_input.getValue() },
				{ name: 'dept_id', value: dept_name_select.val() },
				{ name: 'emp_type_code', value: emp_type_select.val() }
			]
			grid.loadData(params, 'queryBudgEmpTypeWage.do');
		}

		//加载列
		function loadGridColumn(value) {
			var columns = columnArray.slice();
			$.ajax({
				type: "POST",
				url: "queryBudgWageItem.do?isCheck=false",
				data: { "year": value },
				dataType: "json",
				async: false,
				success: function (data) {
					if (data.Rows.length > 0) {
						$.each(data.Rows, function (i, v) {
							columns.push({
								display: v.wage_item_name,
								name: v.wage_item_code,
								width: 120,
								render: function (ui) {
									var value = formatNumber(ui.cellData, 2, 1);
									return value;
								}
							})
						});
					}
					grid.option('columns', columns);
				}
			});
		}

		//加载表格
		function loadGrid() {
			var gridParam = {
	            height: "100%",
	            numberCell: {
	                show: true
	            },
	            checkbox: true,
	            freezeCols: 3
	        }
	        gridParam.columns = columnArray;
	        gridParam.dataModel = {
	            location: "remote",
	            recIndx: "type_name",
	            url: ''
	        };
	        gridParam.toolbar = {
	            items: [
	                { type: 'button', label: '查询（<u>Q</u>）', listeners: [{ click: queryNew }], icon: 'search' },
	                { type: 'button', label: '计算（<u>A</u>）', listeners: [{ click: count }], icon: 'add' },
	                { type: 'button', label: '删除（<u>D</u>）', listeners: [{ click: remove }], icon: 'delete' },
	                // { label: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
	            ]
	        }
			grid = $("#maingrid").etGrid(gridParam);
		}

		//键盘事件
		function loadHotkeys() {
			hotkeys('Q', queryNew);
			hotkeys('A', add);
			hotkeys('D', remove);
		}

		function count() {
			var param = {
				year: date_input.getValue()
			}
			ajaxPostData({
				url: 'collectBudgEmpWageTotal.do?isCheck=false',
				data: param,
				success: function (responseData) {
					queryNew();
				}
			})
		}

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
						this.rowData.year + "@" +
						this.rowData.dept_id
					)
				});
				$.etDialog.confirm('确定删除?', function (index) {
					ajaxPostData({
						url: 'deleteBudgEmpTypeWage.do',
						data: { ParamVo: ParamVo.toString() },
						success: function (responseData) {
							queryNew();
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
				content: 'budgEmpTypeWageImportPage.do?isCheck=false'
			});
			layer.full(index);
		}
	</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label no-empty">年度：</td>
				<td class="ipt">
					<input type="text" id="date_input" />
				</td>
				<td class="label">科室名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">职工类别：</td>
				<td class="ipt">
					<select name="" id="emp_type_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
</body>

</html>