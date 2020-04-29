<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() { 
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'set_code',
			value : $("#set_code").val()
		});
		grid.options.parms.push({
			name : 'set_name',
			value : $("#set_name").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '编码',
								name : 'set_code',
								align : 'left',
								render : function(rowdata, index, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.set_id + "')>"
											+ rowdata.set_code + "</a>";
								}
							},
							{
								display : '名称',
								name : 'set_name',
								align : 'left'
							},
							{
								display : '设置',
								name : 'set_name',
								align : 'center',
								render : function(rowdata, index, value) {
									return "<a href=javascript:openDetail('"
											+ rowdata.set_id + "|"
											+ rowdata.set_name
											+ "')>设置对应仓库</a>";
								}
							},
							{
								display : '是否结账',
								name : '',
								align : 'center',
								render : function(rowdata, index, value) {
									return "<a href=javascript:openAccount('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.set_id + "|"
											+ rowdata.set_code + "|"
											+ rowdata.set_name
											+ "')>设置库房是否结账</a>";
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryMatStoreSet.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,
					heightDiff : -10,
					toolbar : {
						items : [ {
							text : '查询（<u>E</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						}, 
						/*{
							text : '导出Excel（<u>E</u>）',
							id : 'export',
							click : exportExcel,
							icon : 'pager'
						}, {
							line : true
						}, {
							text : '打印（<u>P</u>）',
							id : 'print',
							click : printDate,
							icon : 'print'
						}, 
						{
							line : true
						}, 
						*/{
							text : '下载导入模板（<u>B</u>）',
							id : 'downTemplate',
							click : downTemplate,
							icon : 'down'
						}, {
							line : true
						}, {
							text : '导入（<u>I</u>）',
							id : 'import',
							click : imp,
							icon : 'up'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.set_id);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {
		$.ligerDialog.open({
			url : 'matStoreSetAddPage.do?isCheck=false',
			height : 200,
			width : 300,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveMatStoreSet();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.set_id)
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatStoreSet.do?isCheck=true", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function imp() {

		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'matStoreSetImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {
		var parm = "set_id=" + obj;

		$.ligerDialog.open({
			url : 'matStoreSetUpdatePage.do?isCheck=false&' + parm,
			height : 200,
			width : 300,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveMatStoreSet();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function openDetail(obj) {
		var vo = obj.split("|");
		var parm = "set_id=" + vo[0] + "&" + "set_name=" + vo[1]

		$.ligerDialog.open({
			url : 'matStoreDetailPage.do?isCheck=false&' + parm,
			height : 600,
			width : 860,
			title : '设置仓库对应关系',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
		})
	}

	function openAccount(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" 
			+ "hos_id=" + vo[1] + "&" 
			+ "set_id=" + vo[2] + "&" 
			+ "set_code=" + vo[3] + "&" 
			+ "set_name=" + vo[4];

		$.ligerDialog.open({
			url : 'matStoreSetAccountPage.do?isCheck=false&' + parm,
			height : 600,
			width : 860,
			title : '设置仓库是否结账',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
		})
	}
	
	function loadDict() {
		//字典下拉框
		$("#set_code").ligerTextBox({
			width : 160
		});
		$("#set_name").ligerTextBox({
			width : 160
		});
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('B', downTemplate);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
		hotkeys('I', imp);
	}
	//打印数据
	function printDate() {
		//有数据直接打印
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopPrinterTable("resultPrint", "开始打印", "04108 虚仓仓库设置", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara = {
			usePager : false,
			set_id : $("#set_code").val(),
			set_name : $("#set_name").val()
		};
		ajaxJsonObjectByUrl("queryMatStoreSet.do", printPara, function(
				responseData) {
			var trHtml = '';
			$.each(responseData.Rows, function(idx, item) {
				trHtml += "<tr>";
				trHtml += "<td>" + item.set_code + "</td>";
				trHtml += "<td>" + item.set_name + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint", "开始打印", "04108 虚仓仓库设置", true);
		}, true, manager);
		return;
	}

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "04108 虚仓仓库设置.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			set_id : $("#set_code").val(),
			set_name : $("#set_name").val()
		};
		ajaxJsonObjectByUrl("queryMatStoreSet.do", exportPara,
				function(responseData) {
					var trHtml = '';
					$.each(responseData.Rows, function(idx, item) {
						trHtml += "<tr>";
						trHtml += "<td>" + item.set_code + "</td>";
						trHtml += "<td>" + item.set_name + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").empty();
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					lodopExportExcel("resultPrint", "导出Excel",
							"04108 虚仓仓库设置.xls", true);
				}, true, manager);
		return;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">代码：</td>
			<td align="left" class="l-table-edit-td">
				<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">名称:</td>
			<td align="left" class="l-table-edit-td">
				<input name="set_name" type="text" id="set_name" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr>
					<th width="200">代码</th>
					<th width="200">名称</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
