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
	//设置单元格打印格式
	var printData = {
		execute_value: function (value) { //月份指标值
			return formatNumber(value, 2, 1);
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
		init();
	});
	
	var year_input, index_code_select;

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
			url: "../../../queryBudgIndexDict.do?isCheck=false",
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
	
	//查询
	function query() {
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgWorkHosYearExecute.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left', width: "15%",editable:false ,},
				{display: '指标编码', name: 'index_code', align: 'left', width: "20%",editable:false ,},
				{display: '指标名称', name: 'index_name', align: 'left', width:"25%",editable:false ,},
				{display: '指标值(E)', name: 'execute_value', align: 'right', dataType:"float", width:"15%",
					render:function(ui){
						if (ui.rowData.execute_value) {
							return formatNumber(ui.rowData.execute_value, 2, 1);
						}
					}
				},
				{display: '说明(E)', name: 'remark', align: 'left', dataType:"string",width:"22%"}
			],
			 dataModel:{
            	 method:'POST',
            	 location:'remote',
            	 url:'',
            	 recIndx: 'year'
            },
            usePager:true,width: '100%', height: '100%',checkbox: true,editable: true,
            addRowByKey:true,
			toolbar: {
                items: [
                { type: "button", label: '查询',icon:'search',listeners: [{ click: query}] },
				{ type: "button", label: '添加',icon:'plus',listeners: [{ click: add_open}] },
				{ type: "button", label: '保存',icon:'disk',listeners: [{ click: save}] },
				{ type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
				/* { type: "button", label: '下载导入模板', icon:'arrowthickstop-1-s',listeners: [{ click: downTemplate}]}, */
				{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: impNew}] },

				]
			},
		});

	}
	
	function add_open() {
        $.etDialog.open({
            url: 'budgWorkHosYearExecuteAddPage.do?isCheck=false',
            height: 300,
            width: 700,
            title: '医院年度历史指标数据采集',
            btn: ['确定', '取消'],
            btn1: function(index, el) {
                var iframeWindow = window[el.find('iframe').get(0).name];
                iframeWindow.saveBudgWorkHosExecute()
            }
        });
	}

	function remove() {

		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error("请选择行")
		} else {
			var ParamVo = [];
			data.forEach(function(item) {
				ParamVo.push(
					item.rowData.group_id + "@" +
					item.rowData.hos_id + "@" +
					item.rowData.copy_code + "@" +
					item.rowData.year + "@" +
					item.rowData.index_code
				)
			});
			$.etDialog.confirm('确定删除?', function (){
				ajaxPostData({
                    url: "deleteBudgWorkHosYearExecute.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                    	query();
                    }
                });
			});
		}
	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = printData;
		grid.options.lodop.title = "医院年度历史指标数据采集";
	}

	//导入数据
	function impNew() {
		parent.$.etDialog.open({
			url: 'hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImportNewPage.do?isCheck=false',
			height: 300, width: 450, title: '医院年度指标历史数据采集', 
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
	}
	function imp() {
		var index = layer.open({
			type: 2,
			title: '导入',
			shadeClose: false,
			shade: false,
			maxmin: true, //开启最大化最小化按钮
			area: ['893px', '500px'],
			content: 'budgWorkHosYearExecuteImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	
	function downTemplate() {
		location.href = "downTemplate.do?isCheck=false";
	}

	function save() {
		var data = grid.getChanges().updateList;
		
		if (data.length == 0) {
			$.etDialog.error("没有需要保存的数据");
		} else {
			var ParamVo = [];
			data.forEach(function(item) {
				ParamVo.push(item.year + "@" + item.index_code + "@" + item.execute_value + "@" + (item.remark ? item.remark : "-1"));
			})
            ajaxPostData({
                url: "updateBudgWorkHosYearExecute.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('I', imp);
	}
	//打印  
	function printDate() {

		if (grid.getData().length == 0) {
			$.etDialog.error("无打印数据！");
			return;
		}

		grid.options.parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() }
		];

		var selPara = {};
		$.each(grid.options.parms, function (i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount: 2,
			title: "医院年度历史指标数据采集",
			type: 3,
			columns: grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgWorkHosYearExecute.do?isCheck=false", selPara, function (responseData) {
			printGridView(responseData, printPara);
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
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">指标名称：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class=""></td>
				<td class=""></td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>

</html>