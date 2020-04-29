<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="${path}/resource.jsp">
		<jsp:param value="hr,select,grid,dialog" name="plugins" />
	</jsp:include>
	<style>
		.etgrid-col-checkbox {
			vertical-align: middle;
		}
	</style>
	<script>
		var leftGrid, mainGrid;
		var showFlag = 'user';
		
		var user_id="";
		var user_name="";
		var role_name="";
		var role_id="";
		var leftParam;
		
		var leftGridOps = {
			userColumns: [{
				display: '用户ID',
				name: 'user_id',
				width: 80
			},
			{
				display: '用户编码',
				name: 'user_code',
				width: 80
			},
			{
				display: '用户名称',
				name: 'user_name',
				width: 80
			},
			{
				display: '状态',
				name: 'is_stop',
				width: 80,
				render: function (ui) {
					var cellData = ui.cellData;
					return cellData == 0 ? "启用" : "停用";
				}
			}
			],
			roleColumns: [{
				display: '角色ID',
				name: 'role_id',
				width: 80
			},
			{
				display: '用户名称',
				name: 'role_name',
				width: 80/* ,
					render: function (ui) {
						var updateHtml =
							'<a class="openUpdateRole" row-index="' + ui.rowIndx + '">' +
							ui.cellData +
							'</a>'
						return updateHtml;
					} */
			},
			{
				display: '状态',
				name: 'is_stop',
				width: 80,
				render: function (ui) {
					var cellData = ui.cellData;
					return cellData == 0 ? "启用" : "停用";
				}
			}
			],
			userUrl: '../sysset/queryAllUser.do?isCheck=false',
			roleUrl: '../sysset/queryHosRole.do?isCheck=false',
			openUpdate: function (role_id) {
				$.etDialog.open({
					url: 'roleUserMainPage.do?isCheck=false&role_id=' + role_id,
					title: '修改',
					width: 600,
					height: 400,
					btn: ['保存', '取消'],
					btn1: function (index, el) {
						var iframeWindow = window[el.find('iframe').get(0).name];
						iframeWindow.save();
					}
				})
			}
		};

		var initFrom = function () {
			// select = $("#select").etSelect({
			// 	url: "http://192.168.1.107:9090/hr/select",
			// 	defaultValue: "none"
			// })
			$("#byUser").click(function () {
				showFlag = 'user';
				$('.leftText').text('用户：');
				var userColumns = $.extend(true, [], leftGridOps.userColumns);
				leftGrid.option('columns', userColumns);
				leftGrid.option('dataModel.url', leftGridOps.userUrl);
				queryLeftGrid();
			})
			$("#byRole").click(function () {
				showFlag = 'role';
				$('.leftText').text('角色：');
				var roleColumns = $.extend(true, [], leftGridOps.roleColumns);
				leftGrid.option('columns', roleColumns);
				leftGrid.option('dataModel.url', leftGridOps.roleUrl);
				queryLeftGrid();
			})
		};
		var queryLeftGrid = function () {
			var queryKey;

			if (showFlag === 'user') {
				queryKey = 'user_code';
			} else {
				queryKey = 'role_id';
			}
			var params = [{
				name: queryKey,
				value: $('#leftQuery').val()
			}];
			leftGrid.loadData(params);
		};
		var initLeftGrid = function () {
			var columns = $.extend(true, [], leftGridOps.userColumns);
			var paramObj = {
				height: '100%',
				inWindowHeight: true,
				rowClick: function (event, ui) {
					var rowData = ui.rowData;
					
					if (showFlag === 'user') {
						user_name='user_id',
						user_id=rowData.user_id,
						leftParam = {
							name: 'user_id',
							value: rowData.user_id
						}
					} else {
						user_name='role_id',
						user_id=rowData.role_id,
						leftParam = {
							name: 'role_id',
							value: rowData.role_id
						}
					}
					queryMainGrid();
					//queryMainGrid(leftParam);
				},
				selectionModel: {
					type: 'row',
					mode: 'single'
				},
				dataModel: {
					url: leftGridOps.userUrl
				},
				columns: columns,
				toolbar: {
					items: [{
						type: 'button',
						label: '查询',
						listeners: [{
							click: queryLeftGrid
						}],
						icon: 'search'
					}]
				}
			};
			leftGrid = $("#leftGrid").etGrid(paramObj);

			$("#leftGrid").on('click', '.openUpdateRole', function () {
				var rowIndex = $(this).attr('row-index');
				var currentRowData = leftGrid.getAllData() [rowIndex];

				var openParam = {
					role_id: currentRowData.role_id
				};
				leftGridOps.openUpdate(openParam);
			})
		};
		
		function queryMainGrid(){
			var params = [];
			params.push({
				name : user_name,
				value : user_id,
			});
			params.push({
                name: 'tab_code',
                value: $('#tab_code').val()
            });
			params.push({
            	name:"tab_name",
            	value: $("#tab_name").val()
            });
            
			//alert(JSON.stringify(params));
			mainGrid.loadData(params, 'queryPermData.do?isCheck=false');
		}
		/*var queryMainGrid = function (leftParam) {
			var params = [];
			alert(JSON.stringify(leftParam));
			if (leftParam) {
				params.push(leftParam);
			}
			
			mainGrid.loadData(params, 'queryPermData.do?isCheck=false');
		};*/
		var save = function () {
			if (leftGrid.selectGet().length === 0) {
				$.etDialog.error('请选择左侧用户或角色');
				return;
			}
			saveData();
		};
		function saveData() {
			var mainGridData = mainGrid.getAllData();
			var url = '';
			var saveParam = [];
			if (showFlag === 'user') {
				var user_id = leftGrid.selectGet() [0].rowData["user_id"];
				url = 'addUserPermData.do?isCheck=false';
				$(mainGridData).each(function (index, item) {
					saveParam.push({
						table_code: item.obj_code,
						obj_name: item.obj_name,
						is_read: item.is_read,
						is_write: item.is_write,
						user_id: user_id,
						perm_code: item.obj_code
					});
				});
			} else if (showFlag === 'role') {
				var role_id = leftGrid.selectGet() [0].rowData["role_id"];
				url = 'addRolePermData.do?isCheck=false';
				$(mainGridData).each(function (index, item) {
					saveParam.push({
						table_code: item.obj_code,
						obj_name: item.obj_name,
						is_read: item.is_read,
						is_write: item.is_write,
						role_id: role_id,
						perm_code: item.obj_code
					});
				});
			}
			//console.log(saveParam);
			ajaxPostData({
				url: url,
				data: { paramVo: JSON.stringify(saveParam) },
				success: function () {
					query();
				}
			});
		}
		var batchSave = function () {
			var user_id = leftGrid.selectGet() [0].rowData["user_id"];
			var role_id = leftGrid.selectGet() [0].rowData["role_id"];
			var saveParam = [];
			if (showFlag === 'user') {
				var user_id = leftGrid.selectGet() [0].rowData["user_id"];
				$(mainGridData).each(function (index, item) {
					saveParam.push({
						user_id: user_id
					});
				});
			} else if (showFlag === 'role') {
				var role_id = leftGrid.selectGet() [0].rowData["role_id"];
				$(mainGridData).each(function (index, item) {
					saveParam.push({
						role_id: role_id
					});
				});
			}
			$.etDialog.open({
				url: 'batchAddPermPage.do?isCheck=false',
				//data: { paramVo: JSON.stringify(saveParam) },
				height: 300,
				width: 350,
				title: '批量保存',
				btn: ['确定', '取消'],
				btn1: function (index, el) {
					var iframeWindow = window[el.find('iframe').get(0).name];

					iframeWindow.save()
				}
			});
		};
		var initMainGrid = function () {
			var columns = [{
				display: '编码',
				name: 'obj_code',
				width: 100
			},
			{
				display: '名称',
				name: 'obj_name',
				width: 100
			},
			{
				display: '<input id="write_check_all" class="etgrid-col-checkbox" type="checkbox">写入权限',
				name: 'is_write',
				sortable: false,
				width: 100,
				render: function (ui) {
					if (!ui.cellData) {
						ui.rowData.is_write = '0';
					}
					var isChecked = ui.cellData == 1 ? 'checked' : '';
					var rowIndex = ui.rowIndx;
					var checkBoxHtml = '<input class="isWriteInGrid" rowIndex="' + rowIndex + '" type="checkbox" ' + isChecked + ' />';
					return checkBoxHtml;
				}
			},
			{
				display: '<input id="read_check_all" class="etgrid-col-checkbox" type="checkbox">读取权限',
				name: 'is_read',
				sortable: false,
				width: 100,
				render: function (ui) {
					if (!ui.cellData) {
						ui.rowData.is_read = '0';
					}
					var isChecked = ui.cellData == 1 ? 'checked' : '';
					var rowIndex = ui.rowIndx;
					var checkBoxHtml = '<input class="isReadInGrid" rowIndex="' + rowIndex + '" type="checkbox" ' + isChecked + ' />';
					return checkBoxHtml;
				}
			}
			];
			var paramObj = {
				height: '100%',
				usePager : false,
				inWindowHeight: true,
				columns: columns,
				toolbar: {
					items: [{
						type: 'button',
						label: '查询',
						listeners: [{
							click: queryMainGrid
						}],
						icon: 'search'
					},
					{
						type: 'button',
						label: '保存',
						listeners: [{
							click: save
						}],
						icon: 'save'
					}/* ,
						{
							type: 'button',
							label: '批量保存',
							listeners: [{
								click: batchSave
							}],
							icon: 'search'
						} */
					]
				}
			};
			mainGrid = $("#mainGrid").etGrid(paramObj);

			// 给表格中的checkbox，绑定change事件，并修改数据
			$("#mainGrid").on('change', '.isReadInGrid', function () {
				var $slef = $(this);
				var isChecked = $slef.prop('checked')
				var rowIndex = $slef.attr('rowIndex');

				mainGrid.getAllData()[rowIndex].is_read = isChecked ? '1' : '0';
			})
			$("#mainGrid").on('change', '.isWriteInGrid', function () {
				var $slef = $(this);
				var isChecked = $slef.prop('checked')
				var rowIndex = $slef.attr('rowIndex');

				mainGrid.getAllData()[rowIndex].is_write = isChecked ? '1' : '0';
			})
			
			$("#mainGrid").on('change', '#read_check_all', function () {
				var isChecked = this.checked;
				var allData = mainGrid.getAllData();

				if (allData) {
					allData.forEach(function (rowData) {
						rowData.is_read = isChecked ? '1' : '0'
					})
					mainGrid.refreshColumn('is_read');
				}
			});
			$("#mainGrid").on('change', '#write_check_all', function () {
				var isChecked = this.checked;
				var allData = mainGrid.getAllData();
				if (allData) {
					allData.forEach(function (rowData) {
						rowData.is_write = isChecked ? '1' : '0'
					})
					mainGrid.refreshColumn('is_write');
				}
			});
		};

		$(function () {
			initFrom();
			initLeftGrid();
			initMainGrid();
		})
	</script>
</head>

<body>
	<div class="container">
		<div class="right border-right" style="max-width: 450px; min-width: 450px;">
			<table class="table-layout">
				<tr>
					<td class="label leftText">用户：</td>
					<td class="ipt">
						<input id="leftQuery" type="text">
					</td>

					<td class="label"></td>
					<td class="ipt">
						<button id="byUser">按用户</button>
						<p>
						<button id="byRole">按角色</button>
					</td>
				</tr>
			</table>
			<div id="leftGrid"></div>
		</div>
		<div class="center">
			<table class="table-layout">
				<tr>
					<td class="label">编码：</td>
					<td class="ipt">
						<input id="tab_code" type="text">
					</td>

					<td class="label">名称：</td>
					<td class="ipt">
						<input id="tab_name" type="text">
					</td>
				</tr>
			</table>
			<div id="mainGrid"></div>
		</div>
	</div>
</body>

</html>