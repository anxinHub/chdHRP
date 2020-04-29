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
	var index_code;
	var rowidex;

	//打印 单元格格式化 用
	var renderFunc = {
		last_year_workload: function (value) { //上年业务量
			return formatNumber(value, 2, 1);
		},

		budg_value: function (value) { //预算值
			return formatNumber(value, 2, 1);
		},
		hos_suggest_resolve: function (value) { //医院意见分解
			return formatNumber(value, 2, 1);
		},
	};
	$(function () {
		loadHead(null);
		//加载数据
		loadHotkeys();
		init();
	});
	
	var year_input,index_code_select,dept_name_select;

	function init() {
		year_input = $("#year_input").etDatepicker({
            view: "years",
            minView: "years",
            dateFormat: "yyyy",
            clearButton: false,
            onChange: function (year) {
            	index_code_select.setValue('');
            	reloadIndexCode(year);
            },
            defaultDate: true
        });
		
		index_code_select = $("#index_code_select").etSelect({
			url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false",
			para:{
				budg_level:"03",
				edit_method:"01",
				budg_year:year_input.getValue()
			},
			defaultValue: "none",
			onChange: function(value){
				dept_name_select.setValue('');
				reloadDeptName(value);
			}
		});
		reloadIndexCode(year_input.getValue());
		
		function reloadIndexCode(year){
			index_code_select.reload({
				url:"../../../../../qureyBudgIndexFromPlan.do?isCheck=false",
				para:{
					budg_level:"03",
					edit_method:"01",
					budg_year:year
				}
				
			});
		}
		dept_name_select = $("#dept_name_select").etSelect({
			url:"../../../../../queryBudgIndexDeptSet.do?isCheck=false",
			para:{
				index_code:index_code_select.getValue()
			},
			defaultValue: "none"
		});
		reloadDeptName(index_code_select.getValue());
		
		function reloadDeptName(indexCode){
			dept_name_select.reload({
				url:"../../../../../queryBudgIndexDeptSet.do?isCheck=false",
				para:{
					index_code:indexCode
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
	
	//查询
	function query() {
		
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() },
			{ name: 'budg_level', value: "03" },
			{ name: 'edit_method', value: "01" }
		];
		
		//加载查询条件
		grid.loadData(parms, "queryZeroDYBudgDown.do?isCheck=false&budg_level=03&edit_method=01");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{
					display: '预算年度', name: 'year', align: 'center', width: "10%", editable: setEdit,
					editor: {
						type: 'select',
						keyField: 'year',
						url: '../../../../../queryBudgYear.do?isCheck=false',
						change: function (rowdata, celldata) {
							grid.updateRow(celldata.rowIndx, { index_code: "", index_name: "" })
						}
					}
				},
				{
					display: '指标编码', name: 'index_code', align: 'left', width: "10%", editable: false
				},
				{
					display: '指标名称', name: 'index_name', align: 'left', width: "15%", editable: setEdit,
					valueField: 'code', textField: 'name',
					editor: {
						keyField: 'index_code',
						type: 'select',  //编辑框为下拉框时
						//source:[],   //  静态数据接口  也可以是回调函数返回值
						url: 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=01&budg_year=' + budg_year,      //  动态数据接口
						change: function (rowdata, celldata) {
							grid.updateRow(celldata.rowIndx, { index_code: rowdata.index_code, dept_id: "", dept_code: "", dept_name: "" })
						},
						//与年度联动查询
						create: function (rowdata, celldata, setting) {
							if (rowdata.year) {
								setting.url = 'queryBudgIndex.do?isCheck=false&budg_level=03&edit_method=01&budg_year=' + rowdata.year;
							} else {
								$.etDialog.error('请先填写年度');
								return false;
							}
						}
					}
				},
				{
					display: '科室编码', name: 'dept_code', align: 'left', width: "10%", editable: false
				},
				{
					display: '科室名称', name: 'dept_name', align: 'left', width: "15%", editable: setEdit,
					editor: {
						type: 'select',
						keyField: 'deptCode',
						url: 'queryBudgIndexDeptSet.do?isCheck=false&index_code=' + index_code,
						change: function (rowdata, celldata) {
							if (rowdata.deptCode) {
								grid.updateRow(celldata.rowIndx, { dept_id: rowdata.deptCode.split(",")[0], dept_code: rowdata.deptCode.split(",")[1] })
							}
						},
						//与年度联动查询
						create: function (rowdata, celldata, setting) {
							if (rowdata.index_code) {
								setting.url = 'queryBudgIndexDeptSet.do?isCheck=false&index_code=' + rowdata.index_code;
							} else {
								$.etDialog.error('请先填写指标名称');
								return false;
							}
						}
					}
				},
				{
					display: '预算值(E)', name: 'budg_value', align: 'right', width: 120, dataType: 'float',
					render:function(ui) {
						if (ui.rowData.budg_value) {
							return formatNumber(ui.rowData.budg_value, 2, 1);
						}
					}
				},
				{
					display: '说明(E)', name: 'remark', align: 'left', dataType: 'string', minWidth: '15%',
				}
			],
			dataModel: {
				method: 'POST',
				location: 'remote',
				url: '',
				recIndx: 'year'
			},
			usePager: true, width: '100%', height: '100%', checkbox: true, editable: true,
			addRowByKey: true,
			toolbar: {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '增量生成', icon: 'plus', listeners: [{ click: generate }] },
					{ type: "button", label: '添加行', icon: 'plus', listeners: [{ click: addRow }] },
					{ type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
					{ type: "button", label: '删除', icon: 'minus', listeners: [{ click: remove }] },
					{ type: "button", label: '下载导入模板', icon: 'arrowthickstop-1-s', listeners: [{ click: downTemplate }] },
					{ type: "button", label: '导入', icon: 'arrowthick-1-n', listeners: [{ click: imp }] },
				]
			}
		});
	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = renderFunc;
		grid.options.lodop.title = "科室年度业务预算-零基预算";
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
	function addRow() {
		grid.addRow();
	}

	function add_open() {
		$.ligerDialog.open({
			url: 'zeroDYBudgAddPage.do?isCheck=false', data: {}, height: 500, width: 800,
			title: '科室年度业务预算零基预算添加', modal: true, showToggle: false, showMax: true, showMin: false, isResize: true
		});
	}

	//修改保存
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
						this.dept_id + "@" +
						this.dept_code + "@" +
						this.index_code + "@" +
						(this.budg_value ? this.budg_value : "") + "@" +
						(this.remark ? this.remark : "") + "@" +
						this._rowIndx + "@" +
						'1' //添加数据标识
					)
				});
			}

			if (data.updateList.length > 0) {
				var updateData = data.updateList;
				$(updateData).each(function () {
					ParamVo.push(
						this.year + "@" +
						this.dept_id + "@" +
						this.dept_code + "@" +
						this.index_code + "@" +
						(this.budg_value ? this.budg_value : "") + "@" +
						(this.remark ? this.remark : "") + "@" +
						this._rowIndx + "@" +
						'2' //修改数据标识
					)
				});
			}
			ajaxPostData({
                url: "saveZeroDYBudgDown.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		} else {
			$.etDialog.warn('没有需要保存的数据!');
		}
	}
	
	function remove() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var deleteDate = [];
			$(data).each(function () {
				if (!this.rowData.group_id) {
					deleteDate.push(this);
				} else {
					ParamVo.push(
						this.rowData.group_id + "@" +
						this.rowData.hos_id + "@" +
						this.rowData.copy_code + "@" +
						this.rowData.year + "@" +
						this.rowData.dept_id + "@" +
						this.rowData.index_code
					)
				}
			});
			$.etDialog.confirm('确定删除?', function (yes) {
				if (yes) {
					if (ParamVo.length > 0) {
						ajaxPostData({
			                url: "deleteZeroDYBudgDown.do?isCheck=false",
			                data: { ParamVo: ParamVo.toString() },
			                success: function(responseData) {
			                	query();
			                }
			            });
					} else {
						grid.deleteRows(deleteDate);
						$.etDialog.success('删除成功');
					}
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
			content: 'zeroDYBudgImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
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
			if (!v.dept_name) {
				rowm += "[科室名称]、";
			}
			if (!v.budg_value) {
				rowm += "[预算值]、";
			}

			if (rowm != "") {
				rowm = "第" + (Number(v._rowIndx) + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空" + "\n\r";
			}
			msg += rowm;
			var key = v.year + v.dept_id + v.index_code
			var value = "第" + (Number(v._rowIndx) + 1) + "行";
			if (targetMap.get(key) == null || targetMap.get(key) == 'undefined' || targetMap.get(key) == "") {
				targetMap.put(key, value);
			} else {
				msg += targetMap.get(key) + "与" + value + "数据重复!!" + "\n\r";
			}
		});
		if (msg != "") {
			$.etDialog.warn(msg);
			return false;
		} else {
			return true;
		}
	}
	//增量生成
	function generate() {
		var year = year_input.getValue();
		if (year) {
			ajaxPostData({
                url: "generate.do?isCheck=false&year=" + year,
                data: {},
                success: function(responseData) {
                	query();
                }
            });
		} else {
			$.etDialog.error("预算年度不能为空");
		}
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('G', generate);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
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
			<td class="label">预算年度：</td>
			<td class="ipt">
				<input type="text" id="year_input" />
			</td>
			<td class="label">预算指标：</td>
			<td class="ipt">
				<select name="" id="index_code_select" style="width:180px;"></select>
			</td>
			<td class="label">预算科室：</td>
			<td class="ipt">
				<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>
</body>

</html>