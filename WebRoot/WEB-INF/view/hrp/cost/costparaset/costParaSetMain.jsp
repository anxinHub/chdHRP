<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var menu;
	var actionNodeID;
	var v_bill_type;
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 235
		});
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#acc_year").ligerTextBox({
			width : 160
		});
		$("#acc_month").ligerTextBox({
			width : 160
		});
		$("#natur_code").ligerTextBox({
			width : 160
		});
		$("#type_code").ligerTextBox({
			width : 160
		});
		$("#para_value").ligerTextBox({
			width : 160
		});
		loadTree();
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 120,
			items : [ {
				text : '添加',
				click : tree_open,
				icon : 'add'
			}, {
				line : true
			}, {
				text : '删除',
				click : tree_del,
				icon : 'add'
			}, {
				line : true
			} ]
		});

		$("#toptoolbar").ligerToolBar({
			items : [ {
				text : '查询（<u>E</u>）',
				id : 'search',
				click : query_load,
				icon : 'search'
			}, {
				line : true
			}, 
			{
				text : '添加（<u>A</u>）',
				id : 'add',
				click : add_open,
				icon : 'add'
			}, 
			{ text: '继承', id:'extend', click: add_extend, icon:'add' },
            { line:true },
            {
				text : '生成（<u>G</u>）',
				id : 'add',
				click : generate,
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
			}
			/* , {
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
			}, {
				line : true
			}, {
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
			}  */]
		});
		query();
	});
	function update(item) {
		openUpdate(actionNodeID);
	}
	function query_load() {
		query();
		loadTree();
	}

	/* 设置树形菜单 */
	function onSelect(note) {
		query(note.data.natur_code);
	}
	//是否存在指定变量 
	function isExitsVariable(variableName) {
		try {
			if (typeof (variableName) == "object") {
				return false;
			} else if (typeof (variableName) == "undefined") {
				return false;
			} else {
				return true;
			}
		} catch (e) {
		}
		return false;
	}
	function loadTree() {
		var formPara = {

				acc_year : $("#acc_year").val(),

				acc_month : $("#acc_month").val()
			};
		ajaxJsonObjectByUrl("queryByTree.do?isCheck=false", formPara, function(
				responseData) {
			if (responseData != null) {
				tree = $("#tree").height($(window).height()-60).ligerTree(
						{
							data : responseData.Rows,
							parentIcon : null,
							childIcon : null,
							checkbox : false,
							idFieldName : 'code',
							parentIDFieldName : 'pcode',
							textFieldName : 'text',
							onSelect : onSelect,
							isExpand : true,
							nodeWidth : 250,
							slide : false,
							onContextmenu : function(node, e) {
								actionNodeID = node.data.natur_code;
								v_bill_type =node.data.bill_type;
								menu.show({
									top : e.pageY,
									left : e.pageX
								});
								return false;
							}
						});
				treeManager = $("#tree").ligerGetTreeManager();
				//treeManager.collapseAll(); //全部收起
			}
		});
	}
	//查询
	function query(code) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		if (isExitsVariable(code)) {
			grid.options.parms.push({
				name : 'natur_code',
				value : code
			});
		}
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'acc_month',
			value : $("#acc_month").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {
			if ($("#acc_year").val() != "") {
				return rowdata.acc_year.indexOf($("#acc_year").val()) > -1;
			}
			if ($("#acc_month").val() != "") {
				return rowdata.acc_month.indexOf($("#acc_month").val()) > -1;
			}
			if ($("#natur_code").val() != "") {
				return rowdata.natur_code.indexOf($("#natur_code").val()) > -1;
			}
			if ($("#type_code").val() != "") {
				return rowdata.type_code.indexOf($("#type_code").val()) > -1;
			}
			if ($("#para_value").val() != "") {
				return rowdata.para_value.indexOf($("#para_value").val()) > -1;
			}
		};
		return clause;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '统计年份',
						name : 'acc_year',
						align : 'left'
					}, {
						display : '统计月份',
						name : 'acc_month',
						align : 'left'
					}, {
						display : '分摊性质',
						name : 'natur_name',
						align : 'left'
					}, {
						display : '分摊类型',
						name : 'type_name',
						align : 'left'
					}, {
						display : '分摊参数',
						name : 'para_name',
						align : 'left'
					} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryCostParaSet.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					scrollToPage : true,
					scrollToAppend : true,
					selectRowButtonOnly : true,

					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.acc_year + "|" + rowdata.acc_month
								+ "|" + rowdata.natur_code + "|"
								+ rowdata.type_code+ "|"
								+ rowdata.bill_type);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function tree_open() {
		var parm = "natur_code=" + actionNodeID + "&" + "bill_type=" + v_bill_type + "&" + "acc_year="
		+ $("#acc_year").val() + "&" + "acc_month="
		+ $("#acc_month").val();
		
		$.ligerDialog.open({
			url : 'costParaSetAddPage.do?isCheck=false' + parm,
			height : 400,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaSet();
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
	function add_open() {
		var parm = "natur_code=" + actionNodeID + "&" + "bill_type=" + v_bill_type + "&" + "acc_year="
		+ $("#acc_year").val() + "&" + "acc_month="
		+ $("#acc_month").val();
		$.ligerDialog.open({
			url : 'costParaSetAddPage.do?isCheck=false' + parm,
			height : 400,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaSet();
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
	function add_extend(){
		$.ligerDialog.open({url: 'costParaSetExtendPage.do?isCheck=false', height: 270,width: 500, title:'继承数据',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveCostWageCostRela(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
  		
	}
	function tree_update() {
		var parm = "natur_code=" + actionNodeID + "&" + "acc_year="
				+ $("#acc_year").val() + "&" + "acc_month="
				+ $("#acc_month").val();
		$.ligerDialog.open({
			url : 'costParaSetUpdatePage.do?isCheck=false' + parm,
			data : {},
			height : 400,
			width : 500,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaSet();
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
	function tree_del() {
		var parm = "natur_code=" + actionNodeID + "&" + "acc_year="
				+ $("#acc_year").val() + "&" + "acc_month="
				+ $("#acc_month").val();
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("delete.do?isCheck=false&" + parm,
						{}, function(
						responseData) {
					if (responseData.state == "true") {
						query();
						loadTree();
					}
				});
			}
		});
	}

	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.natur_code + "@"
								+ this.type_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteCostParaSet.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
							loadTree();
						}
					});
				}
			});
		}
	}
	function generate() {
		if ($("#acc_year").val() == "") {

			$.ligerDialog.error('统计年份不能为空');

			return;
		}
		if ($("#acc_month").val() == "") {

			$.ligerDialog.error('统计月份不能为空');

			return;
		}
		var formPara = {

			acc_year : $("#acc_year").val(),

			acc_month : $("#acc_month").val()
		};

		ajaxJsonObjectByUrl("generate.do?isCheck=false", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				query();
				loadTree();
			}
		});
	}
	function imp() {

		var index = layer.open({
			type : 2,
			title : '导入',
			shadeClose : false,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ '893px', '500px' ],
			content : 'costParaSetImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "acc_year=" + vo[3] + "&"
				+ "acc_month=" + vo[4] + "&" + "natur_code=" + vo[5] + "&"
				+ "type_code=" + vo[6]+ "&"
				+ "bill_type=" + vo[7]
		$.ligerDialog.open({
			url : 'costParaSetUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 400,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveCostParaSet();
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
	function loadDict() {
		//字典下拉框
		//默认年
		autodate("#acc_year", "YYYY");
		//默认月
		autodate("#acc_month", "mm");
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('G', generate);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);

	}
	//打印数据
	function printDate() {
		//有数据直接打印
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopPrinterTable("resultPrint", "开始打印", "成本_分摊参数设置", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara = {
			usePager : false,
			acc_year : $("#acc_year").val(),
			acc_month : $("#acc_month").val(),
			natur_code : $("#natur_code").val(),
			type_code : $("#type_code").val(),
			para_value : $("#para_value").val()
		};
		ajaxJsonObjectByUrl("queryCostParaSet.do", printPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.acc_year + "</td>";
				trHtml += "<td>" + item.acc_month + "</td>";
				trHtml += "<td>" + item.natur_code + "</td>";
				trHtml += "<td>" + item.type_code + "</td>";
				trHtml += "<td>" + item.para_value + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint", "开始打印", "成本_分摊参数设置", true);
		}, true, manager);
		return;
	}

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "成本_分摊参数设置.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			acc_year : $("#acc_year").val(),
			acc_month : $("#acc_month").val(),
			natur_code : $("#natur_code").val(),
			type_code : $("#type_code").val(),
			para_value : $("#para_value").val()
		};
		ajaxJsonObjectByUrl("queryCostParaSet.do", exportPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.acc_year + "</td>";
				trHtml += "<td>" + item.acc_month + "</td>";
				trHtml += "<td>" + item.natur_code + "</td>";
				trHtml += "<td>" + item.type_code + "</td>";
				trHtml += "<td>" + item.para_value + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint", "导出Excel", "成本_分摊参数设置.xls", true);
		}, true, manager);
		return;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>


	<div id="layout1">
		<div position="left" title="按分摊性质划分">
			<div class="tree">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="top">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">统计年份：</td>
					<td align="left" class="l-table-edit-td"><input
						name="acc_year" type="text" id="acc_year" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">统计月份：</td>
					<td align="left" class="l-table-edit-td"><input
						name="acc_month" type="text" id="acc_month" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>

					<td align="left"></td>
				</tr>
			</table>
			<div id="toptoolbar"></div>
		</div>
		<div position="center">


			<div id="maingrid"></div>
		</div>
	</div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">统计年份</th>
					<th width="200">统计月份</th>
					<th width="200">科室性质</th>
					<th width="200">分摊类型</th>
					<th width="200">分摊参数</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
