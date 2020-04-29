<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title></title>
	<jsp:include page="${path}/resource.jsp">
		<jsp:param value="dialog,grid,select,pageOffice" name="plugins" />
	</jsp:include>
	<script>
		var grid, vouch_type_code, user_id;
		var accBudgTp, is_update; //子页面所用误动
		//页面初始话
		$(function () {
			initDict();//初始化查询条件
			initGrid();//初始化grid
			query();
		})
        
		//加载查询条件
		function initDict() {
			//用户
			user_id = $("#user_id").etSelect({
				url : "queryUserSelect.do?isCheck=false",
				defaultValue : "none",
			});
			
			vouch_type_code = $("#vouch_type_code").etSelect({
				url: "../../queryVouchType.do?isCheck=false", 
				defaultValue: "none"
			});
		}
		
		//查询
		function query() {
			var param = [
				{ name: 'tp_code', value: $("#tp_code").val() },
				{ name: 'tp_name', value: $("#tp_name").val() },
				{ name: 'vouch_type_code',  value: vouch_type_code.getValue() },
				{ name: 'user_id',  value: user_id.getValue() },
			];
			grid.loadData(param, "queryAccBudgTp.do");
		}
		
		//加载表格
        function initGrid() {
			// 按钮
			var main_toolbar = {
				items: [
					{ type: "button", label: '查询', icon: 'search', listeners: [{ click: query }] },
					{ type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
					{ type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
					{ type: "button", label: '打印', icon: 'print', listeners: [{ click: print }] },
				]
			};
			
			//grid列
			var main_columns = [ { 
				display: "模板编码", align: "left", name: "tp_code", width: 100, 
				render: function (ui) { // 修改页打开
					return '<a data-item=' + ui.rowIndx + ' class="openUpdate">' + ui.cellData + '</a>'
				}
			}, {
				display: '模板名称', align: 'left', name: 'tp_name', width: 140
			}, {
				display: "凭证类型", align: "left", name: "vouch_type_name", width: 120, hidden: true
			}, {
				display: "创建用户", align: "left", name: "user_name", width: 90
			}, {
				display: '备注', align: 'left', name: 'note', width: 300 
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

		//添加页面跳转
		function add() {
			is_update = 0;
			parent.$.etDialog.open({
				url: 'hrp/acc/autovouch/accbudgtp/accBudgTpDetailPage.do?isCheck=false',
				frameName : window.name,
				width: $(parent.window).width(),
				height: $(parent.window).height(),
				title: '平行记账模板添加',
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
		
		//修改页面跳转
		function openUpdate(rowData) {
			is_update = 1;
			accBudgTp = rowData;
			parent.$.etDialog.open({
				url: 'hrp/acc/autovouch/accbudgtp/accBudgTpDetailPage.do?isCheck=false',
				frameName : window.name,
				width: $(parent.window).width(),
				height: $(parent.window).height(),
				title: '平行记账模板修改',
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
			var ParamVo = [];//存储选择的数据
			var tp_codes = "";
			$(selectData).each(function () {
				var rowdata = this.rowData;
				tp_codes = tp_codes + '\'' + rowdata.tp_code + "\',";
			});
			
			$.etDialog.confirm('确定删除?', function () {
				ajaxPostData({
					url: 'deleteAccBudgTp.do',
					data: {
						'tp_codes' : tp_codes.substr(0, tp_codes.length - 1)
					},
					success: function () {
						query();
					}
				})
			});
		}
		
		//打印
		function print(){
			if(grid.getAllData()==null){
				$.etDialog.error("请先查询数据！");
				return;
			}
			var heads = {};
			var printPara={
				title: "平行记账模板",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.acc.service.autovouch.AccBudgTpService",
				bean_name: "accBudgTpService",
				method_name: "queryAccBudgTpPrint",
				heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
				foots: ''//表尾需要打印的查询条件,可以为空 
			};
        	
			$.each(grid.getUrlParms(),function(i,obj){
				printPara[obj.name]=obj.value;
			}); 
			officeGridPrint(printPara);
		}
    </script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">模板编码：</td>
				<td class="ipt">
					<input id="tp_code" type="text" />
				</td>
				
				<td class="label">模板名称：</td>
				<td class="ipt">
					<input id="tp_name" type="text" />
				</td>
				                
				<td class="label">创建用户：</td>
				<td class="ipt" >
					<select id="user_id" type="text" style="width:180px"></select>
				</td>
			</tr>
			<tr style="display: none">
				<td class="label">凭证类型：</td>
				<td class="ipt">
					<select id="vouch_type_code" type="text" style="width:180px"></select>
				</td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>