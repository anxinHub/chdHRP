<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title></title>
	<jsp:include page="${path}/resource.jsp">
		<jsp:param value="dialog,grid,select,pageOffice,datepicker" name="plugins" />
	</jsp:include>
	<script>
		var grid, begin_date, end_date, store_code;
		var matSafeChange; //子页面所用误动
		//页面初始话
		$(function () {
			initDict();//初始化查询条件
			initGrid();//初始化grid
			query();
		})
        
		//加载查询条件
		function initDict() {
			//期间
			begin_date = $("#begin_date").etDatepicker({
				defaultDate : "yyyy-MM-fd"
			});
			end_date = $("#end_date").etDatepicker({
				defaultDate : "yyyy-MM-ed"
			});
			//库房
			store_code = $("#store_code").etSelect({
				url: "../../../queryMatStore.do?isCheck=false", 
				boxWidth: 240,
				defaultValue: "none"
			});
		}
		
		//查询
		function query() {
			var param = [
				{ name: 'begin_date', value: begin_date.getValue() },
				{ name: 'end_date', value: end_date.getValue() },
				{ name: 'store_id',  value: store_code.getValue() },
				{ name: 'change_no', value: $("#change_no").val() },
			];
			grid.loadData(param, "queryMatSafeChange.do");
		}
		
		//加载表格
        function initGrid() {
			// 按钮
			var main_toolbar = {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
					{ type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
					{ type: "button", label: '审核', icon: 'audit', listeners: [{ click: audit }] },
					{ type: "button", label: '消审', icon: 'cancel', listeners: [{ click: unAudit }] },
					{ type: "button", label: '打印', icon: 'print', listeners: [{ click: print }] },
				]
			};
			
			//grid列
			var main_columns = [ { 
				display: "调整单号", align: "left", name: "change_no", width: 120, 
				render: function (ui) { // 修改页打开
					return '<a data-item=' + ui.rowIndx + ' class="openUpdate">' + ui.cellData + '</a>'
				}
			}, {
				display: '调整日期', align: 'left', name: 'change_date', width: 90
			}, {
				display: "仓库", align: "left", name: "store_name", width: 120
			}, {
				display: "状态", align: "left", name: "state_name", width: 90
			}, {
				display: "制单人", align: "left", name: "maker_name", width: 90
			}, {
				display: "制单日期", align: "left", name: "make_date", width: 90
			}, {
				display: "审核人", align: "left", name: "checker_name", width: 90
			}, {
				display: "审核日期", align: "left", name: "check_date", width: 90
			}, {
				display: '备注', align: 'left', name: 'brief', width: 300 
			} ];
			
			//grid属性
			var main_obj = {
				height: '100%',
				inWindowHeight: true,
				checkbox: true,
				toolbar: main_toolbar,
				columns: main_columns,
				pageModel: {
					type: 'remote',//local前台分页
				},
				rowDblClick: function (event, ui) {
					var rowData = ui.rowData;
					openUpdate(rowData);
				},
			};
			
			grid = $("#mainGrid").etGrid(main_obj);
			$("#mainGrid").on('click', '.openUpdate', function () {
				var rowIndex = $(this).attr('data-item');
				var currentRowData = grid.getDataInPage()[rowIndex];
				openUpdate(currentRowData);
			})
		}

		//添加
		function add() {
			matSafeChange = {};
			openPage();
		}
		
		//修改
		function openUpdate(rowData) {
			matSafeChange = rowData;
			openPage();
		}
		
		//打开调整单
		function openPage(){
			parent.$.etDialog.open({
				url: 'hrp/mat/info/basic/safechange/matSafeChangeDetailPage.do?isCheck=false',
				frameName : window.name,
				width: $(parent.window).width(),
				height: $(parent.window).height(),
				title: '安全库存调整单',
				showMax: true,
				shade: 0,
				maxmin: true,
				restore: function(layero){
				  	//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				  	setTimeout(function() {
			  			iframeWin.budgGrid.refreshView();
			  			iframeWin.accGrid.refreshView();
				  	}, 100);
			  	}
			});
		}
		
		//删除
		function del() {
			var selectData = grid.selectGet();
			if (selectData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var ids = "";
			var str = "";
			$(selectData).each(function () {
				if(this.rowData.state != 0){
					str += this.rowData.change_no + ",";
				}
				ids += this.rowData.change_id + ",";
			});
			
			if(str.length > 0){
				$.etDialog.error("单据【" + str.substr(0, str.length - 1) + "】已审核不能删除");
			}
			
			$.etDialog.confirm('确定删除?', function () {
				ajaxPostData({
					url: 'deleteMatSafeChange.do',
					data: {
						'ids' : ids.substr(0, ids.length - 1)
					},
					success: function () {
						grid.deleteSelectedRows();
						//query();
					}
				})
			});
		}

		//审核
		function audit(){
			var selectData = grid.selectGet();
			if (selectData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var ids = "";
			var str = "";
			$(selectData).each(function () {
				if(this.rowData.state != 0){
					str += this.rowData.change_no + ",";
				}
				ids += this.rowData.change_id + ",";
			});

			if(str.length > 0){
				$.etDialog.error("单据【" + str.substr(0, str.length - 1) + "】已审核不能审核");
			}
			
			$.etDialog.confirm('确定审核?', function () {
				ajaxPostData({
					url: 'auditMatSafeChange.do',
					data: {
						'change_nos' : change_nos.substr(0, change_nos.length - 1)
					},
					success: function (res) {
						if(res.state == "true"){
							$.each(data, function (){
								grid.updateRow(this.rowIndx, {state: 1, state_name: '已审核'});
							})
						}
					}
				})
			});
		}
		
		//消审
		function unAudit(){
			var selectData = grid.selectGet();
			if (selectData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			var ids = "";
			var str = "";
			$(selectData).each(function () {
				if(this.rowData.state != 1){
					str += this.rowData.change_no + ",";
				}
				ids += this.rowData.change_id + ",";
			});

			if(str.length > 0){
				$.etDialog.error("单据【" + str.substr(0, str.length - 1) + "】未审核不能消审");
			}
			
			$.etDialog.confirm('确定消审?', function () {
				ajaxPostData({
					url: 'auditMatSafeChange.do',
					data: {
						'change_nos' : change_nos.substr(0, change_nos.length - 1)
					},
					success: function (res) {
						if(res.state == "true"){
							$.each(data, function (){
								grid.updateRow(this.rowIndx, {state: 0, state_name: '未审核'});
							})
						}
					}
				})
			});
		}
    </script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">调整日期：</td>
				<td class="ipt">
					<table>
						<tr>
							<td><input id="begin_date" type="text" style="width: 110px;"/></td>
							<td>&nbsp;至&nbsp;</td>
							<td><input id="end_date" type="text" style="width: 110px;"/></td>
						</tr>
					</table>
				</td>
				
				<td class="label">库房：</td>
				<td class="ipt">
					<select id="store_code" type="text" style="width:180px"></select>
				</td>
				
				<td class="label">调整单号：</td>
				<td class="ipt">
					<input id="change_no" type="text" />
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>