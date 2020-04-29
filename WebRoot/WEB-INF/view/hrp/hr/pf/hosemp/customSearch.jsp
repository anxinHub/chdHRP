<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>自定义查询</title>
	<jsp:include page="${path}/resource.jsp">
		<jsp:param value="hr,grid,dialog" name="plugins" />
	</jsp:include>
	<script>
		var grid;
		var tab_code = '${tab_code}';
		var tab_name = '${tab_name}'
		$(function () {
			init();
		});

		function init() {
			initGrid();
		}

		function initGrid() {
			// 从父级获取暂存数据
			var storageKey = parent.custom_query_storage.curKey;
			var storageData = parent.custom_query_storage.data[storageKey];

			var columns = [
				{
					display: '左括号',
					align: 'center',
					width: 120,
					name: 'l_bracket'
				},
				{
					display: '数据项编码',
					align: 'center',
					name: 'col_name',
					width: 200,
					editor: {
						type: 'select',
						keyField: 'col_code',
						url: '../queryHrColStruc.do?isCheck=false&tab_code=' + tab_code + '&store_type_code=${store_type_code}',
						change: function (rowwData, cellData) {
							grid.updateRow(cellData.rowIndx, {
								field_col_code: '',
								field_col_name: '',
								field_col_name_type: cellData.selected.type
							})
						}
					},
					render: function (ui) {
						if (ui.cellData) {
							return tab_name + '.' + ui.cellData;
						}
					}
				},
				{
					display: '条件',
					align: "center",
					width: 120,
					name: "con_sign_name",
					editor: {
						type: 'select',
						keyField: 'con_sign_code',
						url: '../queryHrConSignSelect.do?isCheck=false'
					}
				},
				{
					display: '数据项值',
					align: "center",
					width: 120,
					name: "field_col_name",
					editable: function (ui) {
						if (ui.rowData && ui.rowData.col_name) {
							return true;
						}
						return false;
					},
					relyOn: [{
						field: 'col_code',
						key: 'col_code'
					}],
					editor: {
						// 用于动态切换改编辑框类型的属性
						dynamic: true,
						keyField: 'field_col_code',
						columns: [{
							display: '代码项编码',
							align: 'center',
							width: 120,
							name: 'field_col_code'
						}, {
							display: '代码项名称',
							align: 'center',
							width: 120,
							name: 'field_col_name'
						}],
						dataModel: {
							url: '../queryHrFiiedDataDicByTabCol.do?isCheck=false&tab_code='+tab_code,
						},
						width: 500,
						height: 200
					}
				},
				{
					display: "右括号",
					align: "center",
					width: 120,
					name: "r_bracket",
				},
				{
					display: "连接符",
					align: "center",
					width: 120,
					name: "join_sign_name",
					editor: {
						type: 'select',
						keyField: 'join_sign_code',
						url: '../queryHrJoinSignSelect.do?isCheck=false'
					}
				}
			];
			var obj = {
				// height: '100%',
				inWindowHeight: true,
				editable: true,
				checkbox: true,
				columns: columns,
				addRowByKey: true,
				usePager: false,
				dataModel: {
					location: 'local',
					data: storageData
				},
				cellSelect: function (event, ui) {
					if (ui.dataIndx === 'field_col_name') {
						var type = ui.rowData.field_col_name_type || 'textbox';
						if (type === 'select') {
							type = 'grid'
						}
						ui.column.editor.dynamic = type;
					}
				},
			};

			obj.toolbar = {
				items: [{
					type: "button",
					label: '添加',
					icon: 'add',
					listeners: [{
						click: add
					}]
				}, {
					type: "button",
					label: '删除',
					icon: 'delete',
					listeners: [{
						click: deleteData
					}]
				}]
			};
			grid = $("#grid").etGrid(obj);

			if (!storageData) {
				grid.addRow();
			}
		}

		function add() {
			grid.addRow()
		}

		function deleteData() {
			grid.deleteSelectedRows();
		}

		function query() {
			var allData = grid.getAllData();
			var dataString = '';
			
			//11:=  12:> 13: >= 14:<  15:<= 16:<> 17:Like 18:NOT LIKE 19:IN 20:NOT IN
			var con = new Map([['11', '='], ['12', '>'], ['13', '>='], ['14', '<'], ['15', '<='], ['16', '<>'],
				['17', 'Like'], ['18', 'NOT LIKE'], ['19', 'IN'], ['20', 'NOT IN']]);
			//11:AND 12:OR 13:NOT
			var join = new Map([['11', 'AND'], ['12', 'OR'], ['13', 'NOT']]);
			var errorMsg = '';//错误信息提示
			
			allData.forEach(function (item, index) {
				
				//验证括号,避免数据库异常
				if(item.l_bracket != "("){
					errorMsg += "第" + (index+1) + "行" + "左括号必须为:<span style='color:red'>(</span>" +"<br/>";
				}
				
				if(item.r_bracket != ")"){
					errorMsg += "第" + (index+1) + "行" + "右括号必须为:<span style='color:red'>)</span>" +"<br/>";
				}
				
				dataString += item.l_bracket + " " +
					item.col_code + " " +
					con.get(item.con_sign_code) + " ";
				
				// 数据项值，分解
				var filed_col = {
					value: '',
					center: item.field_col_code || item.field_col_name,
					leftSym: "'",
					rightSym: "'",
				};
				if (item.con_sign_code === '17' || item.con_sign_code === '18') {
					filed_col.leftSym = "'%";
					filed_col.rightSym = "%'";
				} else if (item.con_sign_code === "19" || item.con_sign_code === "20") {
					var valueInArr = filed_col.center.split(',');
					valueInArr = valueInArr.map(function (item) {
						return "'" + item + "'";
					})
					filed_col.center = valueInArr.join(',');
					filed_col.leftSym = "(";
					filed_col.rightSym = ")";
				}
				filed_col.value = filed_col.leftSym + filed_col.center + filed_col.rightSym;

				dataString += filed_col.value + " " + item.r_bracket;

				if (index !== allData.length - 1) {
					dataString += " " + join.get(item.join_sign_code) + " ";
				}
				
			});
			
			if(errorMsg != ''){
				$.etDialog.error(errorMsg);
				return ; 
			}
			
			// 暂存数据
			parent.custom_query_storage.saveData(allData);
			//customSearchMainGrid(dataString);
			customSearchMainGrid(JSON.stringify(allData));
		}
		//自定义查询
		function customSearchMainGrid(param) {
			var value = parent.main_select.getValue();
			var text = parent.main_select.getText();
			var url = 'customSearchEmp.do?isCheck=false';
			var data = {
				tab_code: value,
				store_type_code: '${store_type_code}',
				param: param,
			};
			// 获取数据
			ajaxPostData({
				url: url,
				data: data,
				success: function (res) {
					// 添加编辑列
					/* res.columns.unshift({
						display: '操作', name: 'a', width: 50, render: function (ui) {
							var index = ui.rowIndx;
							return "<a onclick='update(" + index + ")'>编辑</a>";
						}
					}); */
					parent.main_grid.option('title', text);
					//main_grid.option('columns', res.columns);
					parent.main_grid.option('dataModel.data', res.Rows);
					parent.main_grid.refreshView();

					var curIndex = parent.$.etDialog.getFrameIndex(window.name);
					parent.$.etDialog.close(curIndex);
				},
				delayCallback: true
			});
		}
	</script>
</head>

<body>
	<div id="grid"></div>
</body>

</html>