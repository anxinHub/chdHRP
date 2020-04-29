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
	<jsp:param value="select,datepicker,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	var budg_year;
	var year;
	var index_code;
	var rowidex;

	//打印 单元格格式化 用
	var renderFunc = {
		count_value: function (value) { //预算值
			return formatNumber(value, 2, 1);
		},
		budg_value: function (value) { //预算值
			return formatNumber(value, 2, 1);
		},
		dept_suggest_sum: function (value) { //科室意见汇总
			return formatNumber(value, 2, 1);
		},
	};
	$(function () {
		loadHead(null);
		loadHeadDetail(null);
		//加载数据
		loadHotkeys();

		$("#layout1").ligerLayout({ rightWidth: 600 });
	});
	//查询
	function query() {
		parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'budg_level', value: "01" },
			{ name: 'edit_method', value: "04" }
		];

		//加载查询条件
		grid.loadData(parms, "queryProbHYBudgUp.do?isCheck=false&budg_level=01&edit_method=04");
	}

	function queryRight() {
		parms = [
			{ name: 'year', value: year },
			{ name: 'index_code', value: index_code}
		];
		//加载查询条件
		gridRight.loadData(parms, "queryBudgWorkHosRate.do?isCheck=false");
	}
	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{
					display: '预算年度', name: 'year', align: 'center', width: 80, editable: setEdit,
					editor: {
						keyField: 'year',
						type: 'select',  //编辑框为下拉框时
						//source:[],   //  静态数据接口  也可以是回调函数返回值
						url: '../../../../../queryBudgYear.do?isCheck=false',      //  动态数据接口
						change: function (rowdata, celldata) {

							grid.updateRow(celldata.rowIndx, { index_code: "", index_name: "" })

						}
					}
				},
				{
					display: '指标编码', name: 'index_code', align: 'left', width: 100, editable: false,
				},
				{
					display: '指标名称', name: 'index_name', align: 'left', editable: setEdit, width: 150,
					editor: {
						type: 'select',
						keyField: 'index_code',
						url: 'queryBudgIndex.do?isCheck=false&budg_level=01&edit_method=04',
						change: function (rowdata, celldata) {
							grid.updateRow(celldata.rowIndx, { index_code: rowdata.index_code })
						},
						//与年度联动查询
						create: function (rowdata, celldata, setting) {
							if (rowdata.year) {
								setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=01&edit_method=04&budg_year=' + rowdata.year;
							} else {

								$.ligerDialog.error('请先填写年度');

								return false;
							}
						}
					}
				},
				{display: '计算值', name: 'count_value', align: 'right', dataType: "float", width: 120,editable: false,
					render:function(ui) {
						if (ui.rowData.count_value) {
							return formatNumber(ui.rowData.count_value, 2, 1);
						}
					}
				},
				{
					display: '预算值(E)', name: 'budg_value', align: 'right', dataType: "float", width: 120,
					render:function(ui) {
						if (ui.rowData.budg_value) {
							return formatNumber(ui.rowData.budg_value, 2, 1);
						}
					}
				},
				{
					display: '说明(E)', name: 'remark', align: 'left', dataType: "string", width: 200,
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'year'
			},
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: true, inWindowHeight: true,
			rowSelect: queryRightDate,
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: add_Row }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
				]
			}
		});
	}

	function loadHeadDetail() {

		gridRight = $("#maingridRight").etGrid({
			columns: [
				{
					display: '运营尺度(E)', name: 'measure_name', align: 'center', dataType: "string", width: "25%"
				},
				{
					display: '运营预期(E)', name: 'measure_value', align: 'right', dataType: "float", width: 100,
					render:function(ui) {
						if (ui.rowData.measure_value) {
							return formatNumber(ui.rowData.measure_value, 2, 1);
						}
					},
				},
				{
					display: '概率(E)', name: 'rate', align: 'right', dataType: "float", width: 80,
					render:function(ui) {
						if (ui.rowData.rate) {
							return formatNumber(ui.rowData.rate, 2, 0) + "%";
						}
					}
				},
				{
					display: '计算值', name: 'count_value', align: 'right', minWidth: "100",
					render:function(ui) {
						return formatNumber(ui.rowData.count_value == null ? 0 : ui.rowData.count_value, 2, 1);
					}
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'measure_name'
			},
			usePager: false, width: '100%', height: '100%', checkbox: false, editable: true,
			addRowByKey: true, inWindowHeight: true,
			toolbar: {
				items: [
					{ type: "button", label: '生成', icon: 'plus', listeners: [{ click: generateRight }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: addRow }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: removeRows }] },
					{ type: "button", label: '计算', icon: 'calculator', listeners: [{ click: count }] },
				]
			},
			summary: { //  前台渲染摘要行    摘要行集合    
				totalColumns: ['rate', 'count_value'], //合计冻结行 
				keyWordCol: 'measure_name', //关键字所在列的列名
			},

			load: function () {
				gridRight.refreshSummary();
			}
		});
	}

	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "医院年度业务预算-概率预算";
	}

	// 根据 group_id 是否存在 返回 true 或 false  控制单元格可否编辑 用 
	function setEdit(ui) {
		if (ui.rowData && ui.rowData.group_id) {
			return false;
		} else {
			return true;
		}
	}

	//添加行
	function add_Row() {
		grid.addRow();
	}
	//添加行
	function addRow() {
		gridRight.addRow();
	}

	// 选中 左侧行数据 加载右侧概率数据
	function queryRightDate(event, ui) {
		year = ui.rowData.year;
		index_code = ui.rowData.index_code;
		queryRight();
	}

	function add_open() {
		$.ligerDialog.open({
			url: 'probHYBudgAddPage.do?isCheck=false', data: {}, height: 500, width: 800,
			title: '医院年度业务预算-概率预算添加', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true
		});
	}

	//换行  保存数据
	function save() {

		var data = grid.getChanges();

		var ParamVo = [];

		if (data.addList.length > 0 || data.updateList.length > 0) {

			if (data.addList.length > 0) {

				var addData = data.addList;

				if (!validateGrid(addData)) {
					return false;
				}
				$(addData).each(function () {

					ParamVo.push(
						this.year + "@" +
						this.index_code + "@" +
						(this.count_value ? this.count_value : "") + "@" +
						(this.budg_value ? this.budg_value : "") + "@" +
						(this.remark ? this.remark : "") + "@" +
						this._rowIndx + "@" +
						//添加数据标识
						'1' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1") + "@"
					)
				});
			}

			if (data.updateList.length > 0) {
				var updateData = data.updateList;
				$(updateData).each(function () {
					ParamVo.push(
						this.year + "@" +
						this.index_code + "@" +
						(this.count_value ? this.count_value : "") + "@" +
						(this.budg_value ? this.budg_value : "") + "@" +
						(this.remark ? this.remark : "") + "@" +
						this._rowIndx + "@" +
						//修改数据标识
						'2' + "@" +
						(this.detail?JSON.stringify(this.detail.Rows):"-1") + "@"
					)
				});
			}
			ajaxJsonObjectByUrl("saveProbHYBudgUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
				if (responseData.state == "true") {
					query();
					year = "";
					index_code = "";
					queryRight()
				}
			});
		} else {
			$.ligerDialog.warn('没有需要保存的数据!');
		}
	}

	//删除
	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];//后台删除数据
			var deletePageRow = [];// 页面删除数据
			$(data).each(function () {
				if (this.rowData.group_id) {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.index_code
					)
				} else {
					deletePageRow.push(this);
				}
			});

			$.ligerDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxJsonObjectByUrl("deleteProbHYBudgUp.do?isCheck=false", { ParamVo: ParamVo.toString() }, function (responseData) {
							if (responseData.state == "true") {
								query();
								year = "";
								index_code = "";
								queryRight();
							}
						});
					} else if (deletePageRow.length > 0) {

						grid.deleteRows(deletePageRow);

						year = "";
						index_code = "";

						queryRight();

						$.ligerDialog.success("删除成功!");
					}

				}
			});
		}
	}

	//增量生成
	function generate() {
		var year = year_input.getValue();
		if (year) {
			ajaxJsonObjectByUrl("generate.do?isCheck=false&year=" + year, {}, function (responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		} else {
			$.ligerDialog.error("预算年度不能为空");
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
			content: 'probHYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	//右侧grid 生成
	function generateRight() {
		var data = grid.selectGet();

		var rightData = gridRight.getAllData();

		if (rightData != null) {
			$.ligerDialog.error('有概率数据,不能生成');
		} else {
			if (data.length == 1) {
				ajaxJsonObjectByUrl("setProbBudgRate.do?isCheck=false", {}, function (responseData) {
					gridRight.deleteRows(gridRight.getAllData());
					gridRight.addRows(responseData.Rows);
					gridRight.refreshSummary();
				});

			} else {
				$.ligerDialog.error('请选择一行数据进行生成');
			}
		}

	}

	//右侧grid 删除
	function removeRows() {
		var data = gridRight.selectGet();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			gridRight.deleteRows(data);
		}

		gridRight.refreshSummary();
	}

	//右侧grid 计算
	function count() {

		var data = gridRight.getAllData();

		var dataL = grid.selectGet();

		if (data.length == 0) {
			$.ligerDialog.error('没有需要计算的数据');
		} else {
			if (dataL.length != 1) {
				$.ligerDialog.error('请在左侧选择一行数据再操作');
			} else {
				var count_value = 0; //存储 总计算值
				//var countValue = 0;// 存储 右侧表格每行 计算值
				var falg = 0; // 记录 总概率
				$(data).each(function () {
					falg = falg + Number(this.rate);
				})
				if (falg == 100) {
					$(data).each(function () {
						this.count_value = Number(this.measure_value) * Number(this.rate) / 100;//计算右侧每行 计算值

						count_value = count_value + this.count_value;
					})

					grid.updateRow(dataL[0].rowData._rowIndx, { "count_value": count_value, "budg_value": count_value, detail: { "Rows": data, "Total": data.length } });
					gridRight.refreshView();
					gridRight.refreshSummary();
				} else {
					$.ligerDialog.error('总概率不等于100%,不能计算');
				}
			}
		}
	}

	function validateGrid(data) {
		var msg = "";
		var rowm = "";
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data, function (i, v) {
			rowm = "";
			if (!v.year) {
				rowm += "[年度]、";
			}
			if (!v.index_name) {
				rowm += "[指标名称]、";
			}
			if (!v.budg_value) {
				rowm += "[预算值]、";
			}

			if (rowm != "") {
				rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key = v.year + v.month + v.index_code
			var value = "第" + (Number(v._rowIndx) + 1) + "行";
			if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
				targetMap.put(key, value);
			} else {
				msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msg);
			return false;
		} else {
			return true;
		}
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query)
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
		hotkeys('G', generate);
		hotkeys('T', generateRight);
		hotkeys('R', removeRows);
		hotkeys('C', count)
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
			url: "../../../../../queryBudgIndexDict.do?isCheck=false",
			defaultValue: "none",
			onChange: query
		});
		function reloadIndexCode(year) {
			index_code_select.reload({
				url: "../../../../../qureyBudgIndexFromPlan.do?isCheck=false",
				para: {
					budg_level: "01",
					edit_method: "04",
					budg_year: year
				}
			});
		}
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

<div class="main">
	<table class="table-layout">
		<tr>
			<td class="label">预算年度：</td>
			<td class="ipt">
				<input type="text" id="year_input" />
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
<div id="toptoolbar"></div>
<div>
	<div style="float: left; width: 65%;">
		<div id="maingrid"></div>
	</div>
	<div style="float: left; width: 35%;">

			<div id="maingridRight"></div>
		</div>
	</div>


</body>

</html>