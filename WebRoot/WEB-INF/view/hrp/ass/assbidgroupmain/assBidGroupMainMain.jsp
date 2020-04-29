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
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);

		loadHotkeys();
		$("#ven_id").ligerTextBox({width:160});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'bid_id',
			value : $("#bid_id").val()
		});
		grid.options.parms.push({
			name : 'bid_no',
			value : $("#bid_no").val()
		});
		grid.options.parms.push({
			name : 'bid_date1',
			value : $("#bid_date1").val()
		});
		grid.options.parms.push({
			name : 'bid_date2',
			value : $("#bid_date2").val()
		});
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		//     	  grid.options.parms.push({name:'ven_no',value:$("#ven_no").val()}); 
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'create_emp',
			value : liger.get("create_emp").getValue()
		}); //
		grid.options.parms.push({
			name : 'create_date1',
			value : $("#create_date1").val()
		});
		grid.options.parms.push({
			name : 'create_date2',
			value : $("#create_date2").val()
		});
		grid.options.parms.push({
			name : 'audit_emp',
			value : liger.get("audit_emp").getValue()
		});// 
		grid.options.parms.push({
			name : 'audit_date1',
			value : $("#audit_date1").val()
		});
		grid.options.parms.push({
			name : 'audit_date2',
			value : $("#audit_date2").val()
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
								display : '招标编码',
								name : 'bid_no',
								align : 'left',
								frozen : true,
								render : function(rowdata, rowindex, value) {

									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.bid_id + "')>"
											+ rowdata.bid_no + "</a>";
								},
								width : 100
							}, {
								display : '招标日期',
								name : 'bid_date',
								align : 'left',
								frozen : true,
								width : 100
							}, {
								display : '供应商',
								name : 'ven_no',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.ven_name;
								},
								width : 200
							}, {
								display : '联系人',
								name : 'link_man',
								align : 'left',
								width : 100
							}, {
								display : '手机号码',
								name : 'tel_mobile',
								align : 'left',
								width : 100
							}, {
								display : '办公电话',
								name : 'tel_office',
								align : 'left',
								width : 100
							}, {
								display : '状态',
								name : 'state',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.state == 0) {
										return "新建";
									} else if (rowdata.state == 1) {

										return "审核";
									}

								},
								width : 100
							}, {
								display : '制单人',
								name : 'create_emp',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.create_emp_name;
								},
								width : 100
							}, {
								display : '制单日期',
								name : 'create_date',
								align : 'left',
								width : 100
							}, {
								display : '审核人',
								name : 'audit_emp',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.audit_emp_name;
								},
								width : 100
							}, {
								display : '审核日期',
								name : 'audit_date',
								align : 'left',
								width : 100
							}, {
								display : '备注',
								name : 'bid_note',
								align : 'left',
								width : 100
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAssBidGroupMain.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					//pageSize:10,
					selectRowButtonOnly : true,//heightDiff: -10,
					delayLoad : true,//初始化明细数据
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
							text : '审核（<u>S</u>）',
							id : 'toExamine',
							click : toExamine,
							icon : 'ok'
						}, {
							line : true
						}, {
							text : '消审（<u>X</u>）',
							id : 'notToExamine',
							click : notToExamine,
							icon : 'right'
						}, {
							line : true
						}, {
							text : '引入购置计划单（<u>I</u>）',
							id : 'importPlan',
							click : importPlans,
							icon : 'refresh'
						}, {
							line : true
						}, {
							text : '引入医院招标',
							id : 'importHosBid',
							click : importHosBid,
							icon : 'refresh'
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
						// 						                { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
						]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.bid_id);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		parent.$.ligerDialog
				.open({
					title : '招标管理添加',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assbidgroupmain/assBidGroupMainAddPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}

	//引入购置计划单
	function importPlans() {
		parent.$.ligerDialog
		.open({
			title : '引入购置计划单',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assbidgroupmain/assBidGroupMainImportPlanPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});

	}

	function importHosBid() {
		parent.$.ligerDialog
		.open({
			title : '引入医院招标',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assbidgroupmain/assBidGroupMainImportHosPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
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
								+ this.copy_code + "@" + this.bid_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				//alert(ParamVo)
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssBidGroupMain.do", {
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

	function openUpdate(obj) {

		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "bid_id=" + vo[3];
		
		parent.$.ligerDialog
		.open({
			title : '招标管理修改',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/ass/assbidgroupmain/assBidGroupMainUpdatePage.do?isCheck=false&'+parm,
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});

	}
	function loadDict() {

		var param = {
			query_key : ''
		};
		//字典下拉框
		//供应商
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, param, true,null,"400");
		//制单人
		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);
		//审核人
		autocomplete("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		$("#bid_no").ligerTextBox({
			width : 160
		});

		$("#bid_date1").ligerTextBox({
			width : 100
		});

		$("#bid_date2").ligerTextBox({
			width : 100
		});

		$("#create_date1").ligerTextBox({
			width : 100
		});

		$("#create_date2").ligerTextBox({
			width : 100
		});

		$("#audit_date1").ligerTextBox({
			width : 100
		});

		$("#audit_date2").ligerTextBox({
			width : 100
		});

		//状态
		$("#state").ligerComboBox({
			width : 160
		});

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);

		hotkeys('D', remove);

		hotkeys('S', toExamine);

		hotkeys('X', notToExamine);

		hotkeys('I', importPlans);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);

	}

	function toExamine() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.bid_id);

					});

			ajaxJsonObjectByUrl("updateToExamine.do?isCheck=false", {
				ParamVo : ParamVo.toString()
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
	function notToExamine() {
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.bid_id);
					});
			ajaxJsonObjectByUrl("updateNotToExamine.do?isCheck=false", {
				ParamVo : ParamVo.toString()
			}, function(responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
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
			title : '集团招标管理',
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
		ajaxJsonObjectByUrl("queryAssBidGroupMain.do?isCheck=false", selPara,
				function(responseData) {
					printGridView(responseData, printPara);
				});

	}

	//导出数据
	function exportExcel() {
		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "招标管理.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {
			usePager : false,
			bid_id : $("#bid_id").val(),
			bid_no : $("#bid_no").val(),
			bid_date1 : $("#bid_date1").val(),
			bid_date2 : $("#bid_date2").val(),
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			state : $("#state").val(),
			create_emp : liger.get("create_emp").getValue(),
			create_date1 : $("#create_date1").val(),
			create_date2 : $("#create_date2").val(),
			audit_emp : liger.get("audit_emp").getValue(),
			audit_date1 : $("#audit_date1").val(),
			audit_date2 : $("#audit_date2").val()
		};
		ajaxJsonObjectByUrl("queryAssBidGroupMain.do", exportPara, function(
				responseData) {
			$.each(responseData.Rows, function(idx, item) {
				var trHtml = "<tr>";
				trHtml += "<td>" + item.bid_id + "</td>";
				trHtml += "<td>" + item.bid_no + "</td>";
				trHtml += "<td>" + item.bid_date + "</td>";
				trHtml += "<td>" + item.ven_code + "</td>";
				trHtml += "<td>" + item.ven_name + "</td>";
				trHtml += "<td>" + item.link_man + "</td>";
				trHtml += "<td>" + item.tel_mobile + "</td>";
				trHtml += "<td>" + item.tel_office + "</td>";
				if (item.state == 0) {

					trHtml += "<td>新建</td>";
				} else {

					trHtml += "<td>审核</td>";
				}
				trHtml += "<td>" + item.create_emp_name + "</td>";
				trHtml += "<td>" + item.create_date + "</td>";
				trHtml += "<td>" + item.audit_emp_name + "</td>";
				trHtml += "<td>" + item.audit_date + "</td>";
				trHtml += "<td>" + item.is_group + "</td>";
				trHtml += "<td>" + item.bid_note + "</td>";
				trHtml += "</tr>";
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint", "导出Excel", "招标管理.xls", true);
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标日期：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date1"
				type="text" id="bid_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'bid_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date2"
				type="text" id="bid_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'bid_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_no"
				type="text" id="bid_no" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date1" type="text" id="create_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date2" type="text" id="create_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_emp" type="text" id="create_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><select id="state"
				name="state">
					<option value="">全部</option>
					<option value="0">新建</option>
					<option value="1">审核</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date1" type="text" id="audit_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'audit_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date2" type="text" id="audit_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核人：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_emp"
				type="text" id="audit_emp" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">招标ID</th>
					<th width="200">招标编码</th>
					<th width="200">招标日期</th>
					<th width="200">供应商编码</th>
					<th width="200">供应商名称</th>
					<th width="200">联系人</th>
					<th width="200">手机号码</th>
					<th width="200">办公电话</th>
					<th width="200">状态</th>
					<th width="200">制单人</th>
					<th width="200">制单日期</th>
					<th width="200">审核人</th>
					<th width="200">审核日期</th>
					<th width="200">集团招标</th>
					<th width="200">备注</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
