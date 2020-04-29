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
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#bank_emp").ligerTextBox({
			width : 160
		});
		$("#delegate_emp").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'create_date',
			value : $("#create_date").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'bank_emp',
			value : $("#bank_emp").val()
		});
		grid.options.parms.push({
			name : 'delegate_emp',
			value : $("#delegate_emp").val()
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
								display : '保函单号',
								name : 'bond_no',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.bond_no + "')>"
											+ rowdata.bond_no + "</a>";
								}
							}, {
								display : '合同',
								name : 'contract_name',
								align : 'left'
							}, {
								display : '登记日期',
								name : 'create_date',
								align : 'left'
							}, {
								display : '担保金额',
								name : 'pay_money',
								align : 'right',
								render : function(item) {
									return formatNumber(item.pay_money, '${ass_05005}', 1);
								}
							}, {
								display : '状态',
								name : 'state',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.state == 1) {
										return "审核";
									} else if (rowdata.state == 0) {
										return "新建";
									}
								}
							}, {
								display : '保函内容',
								name : 'reason',
								align : 'left'
							}, {
								display : '银行信息',
								name : 'bank_info',
								align : 'left'
							}, {
								display : '银行负责人',
								name : 'bank_emp_name',
								align : 'left'
							}, {
								display : '银行联系电话',
								name : 'bank_tel',
								align : 'left'
							}, {
								display : '银行帐号',
								name : 'bank_account',
								align : 'left'
							}, {
								display : '委托方',
								name : 'delegate',
								align : 'left'
							}, {
								display : '委托方负责人',
								name : 'delegate_emp_name',
								align : 'left'
							}, {
								display : '委托人联系电话',
								name : 'delegate_tel',
								align : 'left'
							}, {
								display : '医院信息',
								name : 'hos_info',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssContractBank.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,//heightDiff: -10,
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
						}, {
							text : '打印（<u>P</u>）',
							id : 'print',
							click : printDate,
							icon : 'print'
						}, {
							line : true
						},
						//{ text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
						]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.bond_no);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		parent.$.ligerDialog
				.open({
					title : ' 资产合同银行保函添加',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/asscontractbank/assContractBankAddPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
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
								+ this.copy_code + "@" + this.bond_no)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssContractBank.do", {
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
			content : 'assContractBankImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "bond_no=" + vo[3];

		parent.$.ligerDialog
				.open({
					title : ' 资产合同银行保函修改',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/asscontractbank/assContractBankUpdatePage.do?isCheck=false&'
							+ parm,
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	function loadDict() {
		var param = {
			query_key : ''
		};

		autocomplete("#bank_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		autocomplete("#delegate_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

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
	function printDate() {

		if (grid.getData().length == 0) {

			$.ligerDialog.error("请先查询数据！");

			return;
		}

		var selPara = {};

		$.each(grid.options.parms, function(i, obj) {

			selPara[obj.name] = obj.value;

		});

		var dates = getCurrentDate();

		var cur_date = dates.split(";")[2];
		//跨所有列:计算列数
		var colspan_num = grid.getColumns(1).length - 1;

		var printPara = {
			title : '银行保函',
			head : [ {
				"cell" : 0,
				"value" : "单位: ${hos_name}",
				"colspan" : colspan_num,
				"br" : true
			} ],
			foot : [ {
				"cell" : 0,
				"value" : "主管:",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "复核人:",
				"colspan" : colspan_num - 2,
				"br" : true
			}, {
				"cell" : 0,
				"value" : "制单人： ${user_name}",
				"colspan" : 2,
				"br" : false
			}, {
				"cell" : 2,
				"value" : "打印日期: " + cur_date,
				"colspan" : colspan_num - 2,
				"br" : true
			} ],
			columns : grid.getColumns(1),
			headCount : 2,//列头行数
			autoFile : true,
			type : 3
		};
		ajaxJsonObjectByUrl("queryAssContractBank.do?isCheck=false", selPara,
				function(responseData) {
					printGridView(responseData, printPara);
				});

	}

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "050502 资产合同银行保函.xls",
					true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			create_date : $("#create_date").val(),
			state : $("#state").val(),
			bank_emp : $("#bank_emp").val(),
			delegate_emp : $("#delegate_emp").val()
		};
		ajaxJsonObjectByUrl("queryAssContractBank.do", exportPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.bond_no + "</td>";
				trHtml += "<td>" + item.contract_name + "</td>";
				trHtml += "<td>" + item.create_date + "</td>";
				trHtml += "<td>" + item.pay_money + "</td>";
				if (item.state == 1) {
					trHtml += "<td>审核</td>";
				} else if (item.state == 0) {
					trHtml += "<td>新建</td>";
				}
				trHtml += "<td>" + item.reason + "</td>";
				trHtml += "<td>" + item.bank_info + "</td>";
				trHtml += "<td>" + item.bank_emp_name + "</td>";
				trHtml += "<td>" + item.bank_tel + "</td>";
				trHtml += "<td>" + item.bank_account + "</td>";
				trHtml += "<td>" + item.delegate + "</td>";
				trHtml += "<td>" + item.delegate_emp_name + "</td>";
				trHtml += "<td>" + item.delegate_tel + "</td>";
				trHtml += "<td>" + item.hos_info + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint", "导出Excel", "050502 资产合同银行保函.xls",
					true);
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">登记日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">银行负责人：</td>
			<td align="left" class="l-table-edit-td"><input name="bank_emp"
				type="text" id="bank_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">委托方负责人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="delegate_emp" type="text" id="delegate_emp" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">保函单号</th>
					<th width="200">合同</th>
					<th width="200">登记日期</th>
					<th width="200">担保金额</th>
					<th width="200">状态</th>
					<th width="200">保函内容</th>
					<th width="200">银行信息</th>
					<th width="200">银行负责人</th>
					<th width="200">银行联系电话</th>
					<th width="200">银行帐号</th>
					<th width="200">委托方</th>
					<th width="200">委托方负责人</th>
					<th width="200">委托人联系电话</th>
					<th width="200">医院信息</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
