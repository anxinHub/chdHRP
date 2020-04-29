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
	<jsp:param value="select,datepicker,ligerUI,dialog,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid;
	//设置单元格打印格式
	var printData = {

		execute_value: function (value) { //一月份的预算值
			return formatNumber(value, 2, 1);
		}
	};
	$(function () {
		//加载数据
		loadHead(null);
		loadHotkeys();
	});
	//查询
	function query() {
		var parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		];
		//加载查询条件
		grid.loadData(parms,"queryBudgWorkDeptYearExecute.do?isCheck=false");
	}

	function loadHead() {
		grid = $("#maingrid").etGrid({
			columns: [
				{display: '年度', name: 'year', align: 'left', width: "10%",editable:false ,},
				{display: '科室编码', name: 'dept_code', align: 'left', width: "10%",editable:false ,},
				{display: '科室名称', name: 'dept_name', align: 'left', width: "15%",editable:false ,},
				{display: '指标编码', name: 'index_code', align: 'left', width: "10%",editable:false ,},
				{display: '指标名称', name: 'index_name', align: 'left', width:"15%",editable:false ,},
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
			url: 'budgWorkDeptYearExecuteAddPage.do?isCheck=false', height: 320, width: 700,
			title: '科室年度历史指标数据采集', 
			btn:["确定","取消"],
			btn1:function(index,el){
				var iframeWindow = window[el.find("iframe").get(0).name]
				iframeWindow.saveBudgWorkDeptYearExecute();
			}
		});

	}
	//打印回调方法
	function lodopPrint() {
		var head = "";
		grid.options.lodop.head = head;
		grid.options.lodop.fn = printData;
		grid.options.lodop.title = "医院年度历史指标数据采集";
	}
	//修改保存
	function save() {
		var data = grid.getChanges().updateList;

		if (data.length == 0) {

			$.etDialog.error('没有需要保存的数据');

		} else {
			var ParamVo = [];
			data.forEach(function(item) {
				ParamVo.push(item.year + "@" + item.dept_id + "@" + item.index_code + "@" + item.execute_value + "@" + (item.remark ? item.remark : "-1"));
			})
			
			ajaxPostData({
                url: "updateBudgWorkDeptYearExecute.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	query();
                }
            });
		}
	}

	function remove() {

		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {
			var ParamVo = [];
			data.forEach(function(item) {
				ParamVo.push(
						item.rowData.group_id + "@" +
						item.rowData.hos_id + "@" +
						item.rowData.copy_code + "@" +
						item.rowData.year + "@" +
						item.rowData.dept_id + "@" +
						item.rowData.index_code
				)
			});
			$.etDialog.confirm('确定删除?', function (){
				ajaxPostData({
                    url: "deleteBudgWorkDeptYearExecute.do?isCheck=false",
                    data: { ParamVo: ParamVo.toString() },
                    success: function(responseData) {
                    	query();
                    }
                });
				
			});
		}
	}
	function impNew() {
		parent.$.etDialog.open({
			url: 'hrp/budg/business/compilationbasic/dpetyearexecute/budgWorkDeptYearExecuteImportNewPage.do?isCheck=false',
			height: 300, width: 450, title: '科室年度历史指标数据采集', 
			isMax: true, frameName: window.name//用于parent弹出层调用本页面的方法或变量
		});
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
			"year=" + vo[3] + "&" +
			"dept_id=" + vo[4] + "&" +
			"index_code=" + vo[6]


		$.ligerDialog.open({
			url: 'budgWorkDeptYearExecuteUpdatePage.do?isCheck=false&' + parm, data: {}, height: 300, width: 700,
			title: '科室年度历史指标数据采集修改', modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			buttons: [{ text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgWorkDeptExecute(); }, cls: 'l-dialog-btn-highlight' },
			{ text: '取消', onclick: function (item, dialog) { dialog.close(); } }
			]
		});

	}

	//打印
	function printData() {
		if (grid.getData().length == 0) {
			$.etDialog.error("无打印数据！");
			return;
		}

		grid.options.parms = [
			{ name: 'year', value: year_input.getValue() },
			{ name: 'index_code', value: index_code_select.getValue() },
			{ name: 'dept_id', value: dept_name_select.getValue() }
		];
		var selPara = {};
		$.each(grid.options.parms, function (i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount: 2,
			title: "科室年度历史指标数据采集",
			type: 3,
			columns: grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgWorkDeptYearExecute.do?isCheck=false", selPara, function (responseData) {
			printGridView(responseData, printPara);
		});
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('S', save);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('P', printData);
	}
</script>
<script>
	var year_input,index_code_select
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


		index_code_select= $("#index_code_select").etSelect({
			url:"../../../queryBudgIndexDict.do?isCheck=false",
			defaultValue:"none",
			onChange:query
		});

		dept_name_select= $("#dept_name_select").etSelect({
			url:"../../../queryBudgDeptDict.do?isCheck=false",
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
				<td class="label">指标编码：</td>
				<td class="ipt">
					<select name="" id="index_code_select" style="width:180px;"></select>
				</td>
				<td class="label">科室：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
			</tr>
		</table>
	</div>

	<div id="maingrid"></div>

</body>

</html>