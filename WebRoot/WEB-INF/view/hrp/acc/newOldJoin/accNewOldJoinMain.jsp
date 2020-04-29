<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新旧制度衔接</title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid, gridManager, caseStr, treeManager;
	
	$(function(){
		initLayout();
		
		$("#subj_code").ligerTextBox({ width: 160 });
		$("#subj_name").ligerTextBox({ width: 160 });

		leftTreeMenu();
		$('.choosebtn').ligerButton({width:80});
	});
	
	// 表头
	// 科目表头
	var columnsSubj = [
		{ display: '科目编码', name: 'subj_code', align: 'left', width: '120',
			render: function (rowdata, rowindex, value) {
				return "<a href=javascript:openSubjUpdatePage('" + rowdata.subj_code + "')>" + rowdata.subj_code + "</a>";
			}
		},
		{ display: '科目名称', name: 'subj_name', align: 'left', width: '200' },
		{ display: '科目全称', name: 'subj_name_all', align: 'left', width: '340' },
		{ display: '科目类别', name: 'subj_type_code', align: 'left', width: '120' },
		{ display: '科目性质', name: 'subj_nature_code', align: 'left', width: '120' },
		{ display: '方向', name: 'subj_dire', align: 'left', width: '100' },
		{ display: '科目级次', name: 'subj_level', align: 'left', width: '100' },
		{ display: '是否末级', name: 'is_last', align: 'left', width: '100' },
		{ display: '是否核算现金流', name: 'is_cash', align: 'left', width: '100' },
		{ display: '是否辅助核算', name: 'is_check', align: 'left', width: '100' },
		{ display: '辅助核算1', name: 'check1', align: 'left', width: '100' },
		{ display: '辅助核算2', name: 'check2', align: 'left', width: '100' },
		{ display: '辅助核算3', name: 'check3', align: 'left', width: '100' },
		{ display: '辅助核算4', name: 'check4', align: 'left', width: '100' },
		{ display: '备注', name: 'note', align: 'left', width: '120' }
	];
	
	// 科目余额表头
	var columnsLedger = [
		{ display: '科目编码', name: 'subj_code', align: 'left', width : '120',
			render: function (rowdata, rowindex, value) {
				if(rowdata.is_last == "1" || rowdata.is_last == "是"){
					return "<a href=javascript:openLedgerPage('" + rowdata.subj_code + "')>" + rowdata.subj_code + "</a>";
				}else{
					return rowdata.subj_code;
				}
			}
		},
		{ display: '科目名称', name: 'subj_name', align: 'left', width : '180' },
		{ display: '期初借方金额', name: 'bal_od', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.bal_od, 2, 1);
			}
		},
		{ display: '期初贷方金额', name: 'bal_oc', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.bal_oc, 2, 1);
			}
		},
		{ display: '本期借方金额', name: 'this_od', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.this_od, 2, 1);
			}
		},
		{ display: '本期贷方金额', name: 'this_oc', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.this_oc, 2, 1);
			}
		},
		{ display: '本年累计借方金额', name: 'sum_od', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.sum_od, 2, 1);
			}
		},
		{ display: '本年累计贷方金额', name: 'sum_oc', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.sum_oc, 2, 1);
			}
		},
		{ display: '期末借方金额', name: 'end_od', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.end_od, 2, 1);
			}
		},
		{ display: '期末贷方金额', name: 'end_oc', align: 'right', width : '120', formatter: "###,##0.00",
			render: function(rowData){
				return formatNumber(rowData.end_oc, 2, 1);
			}
		}
	];
	
	// 映射关系表头
	var columnsMap = [
		{ display: '原科目编码', name: 'SUBJ_CODE_OLD', align: 'left', width: '120' 
			,
			render: function (rowdata, rowindex, value) {
				return "<a href=javascript:updateTran(\"" + rowdata.SUBJ_CODE_OLD + "\",\""+rowdata.SUBJ_CODE_NEW+"\",\""+rowdata.SUBJ_CODE_NEW_B+"\")>" + rowdata.SUBJ_CODE_OLD + "</a>";
			}	
		},
		{ display: '原科目名称', name: 'SUBJ_NAME_OLD', align: 'left', width: '180' },
		{ display: '原科目全称', name: 'SUBJ_NAME_ALL_OLD', align: 'left', width: '200' },
		{ display: '财务新科目编码', name: 'SUBJ_CODE_NEW', align: 'left', width: '120' },
		{ display: '财务新科目名称', name: 'SUBJ_NAME_NEW', align: 'left', width: '180' },
		{ display: '财务新科目全称', name: 'SUBJ_NAME_ALL_NEW', align: 'left', width: '200' },
		{ display: '预算新科目编码', name: 'SUBJ_CODE_NEW_B', align: 'left', width: '120' },
		{ display: '预算新科目名称', name: 'SUBJ_NAME_NEW_B', align: 'left', width: '180' },
		{ display: '预算新科目全称', name: 'SUBJ_NAME_ALL_NEW_B', align: 'left', width: '200' },
	];
	
	// excel导入科目模板表头
	var paraSubj = {
		"column" : [
			{ "name" : "subj_code", "display" : "科目编码", "width" : "", "require" : true },
			{ "name" : "subj_name", "display" : "科目名称", "width" : "", "require" : true },
			{ "name" : "subj_name_all", "display" : "科目全称", "width" : ""},
			{ "name" : "subj_type_name", "display" : "科目类别", "width" : ""},
			{ "name" : "subj_nature_name", "display" : "科目性质", "width" : "" },
			{ "name" : "subj_dire_name", "display" : "方向", "width" : "" },
			{ "name" : "subj_level", "display" : "科目级次", "width" : "" },
			{ "name" : "is_cash_name", "display" : "是否核算现金流", "width" : "" },
			{ "name" : "is_last_name", "display" : "是否末级", "width" : ""},
			{ "name" : "check1", "display" : "辅助核算1", "width" : "" },
			{ "name" : "check2", "display" : "辅助核算2", "width" : "" },
			{ "name" : "check3", "display" : "辅助核算3", "width" : "" },
			{ "name" : "check4", "display" : "辅助核算4", "width" : "" },
			{ "name" : "note", "display" : "备注", "width" : "" }
		]
	};
	// excel导入余额模板表头
	var paraLedger = {
		"column" : [
			{ "name" : "subj_code", "display" : "科目编码", "width" : "", "require" : true },
			{ "name" : "subj_name", "display" : "科目名称", "width" : "", "require" : true },
			{ "name" : "bal_od", "display" : "期初借方金额", "width" : "", "require" : true },
			{ "name" : "bal_oc", "display" : "期初贷方金额", "width" : "", "require" : true },
			<%--
			{ "name" : "this_od", "display" : "本期借方金额", "width" : "", "require" : true },
			{ "name" : "this_oc", "display" : "本期贷方金额", "width" : "", "require" : true },
			{ "name" : "sum_od", "display" : "本年累计借方金额", "width" : "", "require" : true },
			{ "name" : "sum_oc", "display" : "本年累计贷方金额", "width" : "", "require" : true },
			--%>
			
			{ "name" : "end_od", "display" : "期末借方金额", "width" : "", "require" : true },
			{ "name" : "end_oc", "display" : "期末贷方金额", "width" : "", "require" : true }
		]
	};
	
	// excel导入映射关系模板表头
	var paraTranMap = {
		"column" : [
			{ "name" : "subj_code_old", "display" : "原科目编码", "width" : "", "require" : true },
			{ "name" : "subj_name_old", "display" : "原科目名称", "width" : "" },
			{ "name" : "subj_name_all_old", "display" : "原科目全称", "width" : "" },
			{ "name" : "subj_code_new", "display" : "财务新科目编码", "width" : "", "require" : true },
 			{ "name" : "subj_name_new", "display" : "财务新科目名称", "width" : "" },
 			{ "name" : "subj_name_all_new", "display" : "财务新科目全称", "width" : "" },
 			{ "name" : "subj_code_new_b", "display" : "会计新科目编码", "width" : "" },
 			{ "name" : "subj_name_new_b", "display" : "会计新科目名称", "width" : "" },
			{ "name" : "subj_name_all_new_b", "display" : "会计新科目全称", "width" : "" }
		]	
	};
	
	// toolbar
	// 医院账务制度（旧）
	var toolbar_items_old = [
		{ text : "查询", id : "", click : queryClick, icon : "search" },
		{ line : true },
		{ text : "添加", id : "", click : addClick, icon : "add" },
		{ line : true },
		{ text : "删除", id : "", click : deleteClick, icon : "delete" },
		{ line : true },
		{ text : "导入", id : "", click : importClick, icon : "up" },
		{ line : true },
		{ text : "导出", id : "", click : exportClick, icon : "down" },
		{ line : true }
	];
	var toolbar_items_old_ledger = [
		{ text : "查询", id : "", click : queryClick, icon : "search" },
		{ line : true },
		{ text : "删除", id : "", click : deleteClick, icon : "delete" },
		{ line : true },
		{ text : "导入", id : "", click : importClick, icon : "up" },
		{ line : true },
		{ text : "导出", id : "", click : exportClick, icon : "down" },
		{ line : true }
	];
	
	// 政府会计制度（新）
	var toolbar_items_new = [
		{ text : "查询", id : "", click : queryClick, icon : "search" },
		{ line : true },
		{ text : "生成", id : "", click : generate, icon : "initwage" },
		{ line : true },
		<%--
 		{ text : "添加", id : "", click : addClick, icon : "add" },
 		{ line : true },
		--%>
		{ text : "删除", id : "", click : deleteClick, icon : "delete" },
		{ line : true },
		{ text : "正式载入", id : "", click : loadToSys , icon : "initwage" },
		{ line : true },
		{ text : "导入", id : "", click : importClick, icon : "up" },
		{ line : true },
		{ text : "导出", id : "", click : exportClick, icon : "down" },
		{ line : true }
	];
	
	// 映射关系
	var toolbar_items_map = [
		{ text : "查询", id : "", click : queryClick, icon : "search" },
		{ line : true },
		{ text : "生成", id : "", click : generate, icon : "initwage" },
		{ line : true },
		<%--
		{ text : "保存", id : "", click : saveClick, icon : "save" },
		{ line : true },
		{ text : "修改", id : "", click : updateClick, icon : "edit" },
		{ line : true },
		--%>
		{ text : "删除", id : "", click : deleteClick, icon : "delete" },
		{ line : true },
		{ text : "导入", id : "", click : importClick, icon : "up" },
		{ line : true },
		{ text : "导出", id : "", click : exportClick, icon : "up" },
		{ line : true }
	];
	
	// 初始化布局
	function initLayout(){
		$("#layout1").ligerLayout({
			leftWidth: 200,
			allowLeftCollapse:false
        });
	}
	
	// 初始化左侧树菜单
	function leftTreeMenu(){
		var tree = $("#tree1").ligerTree({
			data: [
				{id: 1, pid: 0, text: "医院财务制度(旧)"},
				{id: 2, pid: 1, text: "会计科目"},
				{id: 3, pid: 1, text: "科目初始账"},
				{id: 4, pid: 0, text: "科目映射关系"},
				{id: 5, pid: 4, text: "科目映射关系"},
				{id: 6, pid: 0, text: "政府会计制度(新)"},
				{id: 7, pid: 6, text: "会计科目"},
				{id: 8, pid: 6, text: "科目初始账"}
			],
			idFieldName: 'id',
			slide: false,
			parentIDFieldName: 'pid',
			checkbox: false,
			onSelect: selectNode
		});
		treeManager = $("#tree1").ligerGetTreeManager();
	}

	// 选择节点
	function selectNode(node){
		var nodePid = node.data.pid;
		if(nodePid == 0){
			return;
		}else{
			$(".queryinp").val("");
			var nodeId = node.data.id;
			if(grid != undefined){
				grid.options.parms = [];
			}
			if(nodeId == 2){// 会计科目（旧）
				caseStr = "oldSubj";
				initGrid("queryAccSubjOld.do?isCheck=false", columnsSubj, {items: toolbar_items_old});
			}else if(nodeId == 3){// 科目初始账（旧）
				caseStr = "oldLedger";
				initGrid("queryAccLedgerOld.do?isCheck=false", columnsLedger, {items: toolbar_items_old_ledger});
			}else if(nodeId == 5){// 科目映射关系
				caseStr = "newOldMap";
				initGrid("queryAccSubjMap.do?isCheck=false", columnsMap, {items: toolbar_items_map});
			}else if(nodeId == 7){// 会计科目（新）
				caseStr = "newSubj";
				initGrid("queryAccSubjNew.do?isCheck=false", columnsSubj, {items: toolbar_items_new});
			}else if(nodeId == 8){// 科目初始账
				caseStr = "newLedger";
				initGrid("queryAccLedgerNew.do?isCheck=false", columnsLedger, {items: toolbar_items_new});
			}
			$(".l-panel-topbarinner").css({'width':'100%'})
		}
	}
	
	// 初始化表格
	function initGrid(url, columns, toolbar){
		grid = $("#rightgrid").ligerGrid({
			columns : columns,
			dataAction : "server",
			dataType : "server",
			usePager : true,
			url : url,
			width: '100%',
            height: '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,
			delayLoad: true,
			toolbar : toolbar
		});
		gridManager = $("#rightgrid").ligerGetGridManager();
	}
	
	// 查询
	function queryClick(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({ name: 'subj_code', value: $("#subj_code").val() });
		grid.options.parms.push({ name: 'subj_name', value: $("#subj_name").val() });
		grid.loadData(grid.where);
	}
	
	// 添加
	function addClick(){
		switch(caseStr){
			case "oldSubj":
				openSubjAddPage("会计科目添加(旧)", caseStr);
				return;
			<%--
			case "oldLedger":
				openLedgerAddPage();
				return;
			--%>
			case "newSubj":
				openSubjAddPage("会计科目添加(新)", caseStr);
				return;
			<%--
			case "newLedger":
				openLedgerAddPage();
			--%>
				return;
		}
	}
	
	// cs:caseStr
	function openSubjAddPage(title, cs){
		$.ligerDialog.open({
			url: "tmpAccSubjAdd.do?isCheck=false&caseStr=" + cs,
			height: 500,
			width: 700,
			title: title,
			modal: true,
			showToggle: false,
			buttons: [
				{text: '保存', onclick: function(item, dialog){
						dialog.frame.saveAccSubj();
					}, cls:'l-dialog-btn-highlight'
				},
				{text: '关闭', onclick: function(item, dialog){
						dialog.close();
					}
				}
			]
		});
	}
	
	// 打开科目编辑页面
	function openSubjUpdatePage(subjCode){
		$.ligerDialog.open({
			url: "tmpAccSubjUpdate.do?isCheck=false&subj_code=" + subjCode + "&caseStr=" + caseStr,
			height: 500,
			width: 700,
			title: "会计科目修改",
			modal: true,
			showToggle: false,
			buttons: [
				{text: '保存', onclick: function(item, dialog){
						dialog.frame.saveAccSubj();
					}, cls:'l-dialog-btn-highlight'
				},
				{text: '关闭', onclick: function(item, dialog){
						dialog.close();
					}
				}
			]
		});
	}
	
	// 打开余额编辑
	function openLedgerPage(subjCode){
		parent.openDialog({ 
			url : 'hrp/acc/join/openTmpAccLedgerEdit.do?isCheck=false&subj_code='+ subjCode + '&caseStr=' + caseStr,
			data:{}, height: 80,width: 100, title:'',
			modal:true,showToggle:false,showMin: false,isResize:true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [
				{ text: '保存', onclick: function (item, dialog) { dialog.frame.saveAccLeder(); },cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			] 
		});
	}
	
	// 删除
	function deleteClick(){
		var data = gridManager.getCheckedRows();
		var paramVo = [];
		if(data.length == 0){
			$.ligerDialog.warn('请选择行');
			return;
		}
		
		$.ligerDialog.confirm("确定删除所选行？", function(yes){
			if(yes){
				switch(caseStr){
					case "oldSubj":// 删除旧科目
						ajaxJsonObjectByUrl("deleteAccSubjOld.do?isCheck=false", {paramVo : JSON.stringify(data)}, function (resData){
							queryClick();
						});
						return;
					case "oldLedger":// 删除旧余额
						ajaxJsonObjectByUrl("deleteAccLedgerOld.do?isCheck=false", {paramVo : JSON.stringify(data)}, function (resData){
							queryClick();
						});
						return;
					case "newSubj":// 删除新科目
						ajaxJsonObjectByUrl("deleteAccSubjNew.do?isCheck=false", {paramVo : JSON.stringify(data)}, function (resData){
							queryClick();
						});
						return;
					case "newLedger":// 删除新余额
						ajaxJsonObjectByUrl("deleteAccLedgerNew.do?isCheck=false", {paramVo : JSON.stringify(data)}, function (resData){
							queryClick();
						});
						return;
					case "newOldMap":// 删除新余额
						ajaxJsonObjectByUrl("deleteAccNewOldMap.do?isCheck=false", {paramVo : JSON.stringify(data)}, function (resData){
								queryClick();
						});
						return;
				}
			}
		});
	}
	
	// 导入
	function importClick(){
		switch(caseStr){
			case "oldSubj":
				chooseFrame("旧科目导入");
				return;
			case "oldLedger":
				chooseFrame("旧余额导入");
				return;
			case "newSubj":
				importSpreadView("/hrp/acc/join/importAccSubjNew.do?isCheck=false", paraSubj, queryClick());
				return;
			case "newLedger":
				importSpreadView("/hrp/acc/join/importAccLedgerNew.do?isCheck=false", paraLedger, queryClick());
				return;
			case "newOldMap":
				importSpreadView("/hrp/acc/join/importAccTranMap.do?isCheck=false", paraTranMap, queryClick());
				return;
		}
	}
	
	// 导出
	function exportClick(){
		var selPara = {};
		$.each(grid.options.parms, function (i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount: 2,
			type: 3,
			columns: grid.getColumns(1),
			is_print: false
		};
		
		switch(caseStr){
			case "oldSubj":
				ajaxJsonObjectByUrl("queryAccSubjOld.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
				return;
			case "oldLedger":
				ajaxJsonObjectByUrl("queryAccLedgerOld.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
				return;
			case "newSubj":
				ajaxJsonObjectByUrl("queryAccSubjNew.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
				return;
			case "newLedger":
				ajaxJsonObjectByUrl("queryAccLedgerNew.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
				return;
			case "newOldMap":
				ajaxJsonObjectByUrl("queryAccSubjMap.do?isCheck=false", selPara, function (resData) {
					printGridView(resData, printPara);
				});
				return;
		}
	}
	
	// 生成
	function generate(){
		switch(caseStr){
			case "newSubj":
				ajaxJsonObjectByUrl("regenerateAccSubjOld.do?isCheck=false", {}, function (resData) {
				});
				return;
			case "newLedger":
				ajaxJsonObjectByUrl("regenerateAccSubjnewLedger.do?isCheck=false", {}, function (resData) {
				});
				return;
			case "newOldMap":
				ajaxJsonObjectByUrl("regenerateAccSubjnewOldMap.do?isCheck=false", {}, function (resData) {
				});
				return;
		}
	}
	
	<%--
	// 修改
	function updateClick(){
		switch(caseStr){
			case "newSubj":
				$.ligerDialog.warn("新科目修改");
				return;
			case "newLedger":
				$.ligerDialog.warn("新余额修改");
				return;
			case "newOldMap":
				$.ligerDialog.warn("映射关系修改");
				return;
		}
	}
	--%>
	
	// 正式载入
	function loadToSys(){
		switch(caseStr){
			case "newSubj":
				$.ligerDialog.confirm('是否确认将新科目正式载入系统？', function (yes) {
					if(yes){
						ajaxJsonObjectByUrl("addOfficiallyNewSubj.do?isCheck=false", {}, function (resData) {
							
						});
					}
				});
				return;
			case "newLedger":
				$.ligerDialog.confirm('是否确认将新科目余额正式载入系统？', function (yes) {
					if(yes){
						ajaxJsonObjectByUrl("queryNewLedgerExists.do?isCheck=false", {}, function (resData) {
							if(resData.state == "true"){
								var flag = false;
								if(resData.exists == "exists"){
									$.ligerDialog.confirm('系统已存在2019年期初最新数据，若载入将会清空，是否继续？', function (yes) {
			 							if(yes){
			 								var alertw = $.ligerDialog.waitting('正在载入中,请稍候...');
			 								ajaxJsonObjectByUrl("ledgerAndCheckIntoSys.do?isCheck=false", {}, function (resData) {
			 									alertw.close();
			 								});
			 							}
			 						});
								}else{
									flag = true;
								}
								
								if(flag){
									var alertw = $.ligerDialog.waitting('正在载入中,请稍候...');
									ajaxJsonObjectByUrl("ledgerAndCheckIntoSys.do?isCheck=false", {}, function (resData) {
										alertw.close();
									});
								}
							}
						});
					}
				});
				return;
		}
	}
	
	<%--
	// 映射关系保存
	function saveClick(){
		$.ligerDialog.warn("映射关系保存");
	}
	--%>
	
	// 导入选择框
	var impChoosetWManager;
	function chooseFrame(title){
		impChoosetWManager = $.ligerDialog.open({
    		target: $("#targetWindow"),
			height : '200',
			width : '200',
			title : title,
			modal : true,
			parentframename : window.name,
		});
	}
	
	// 从excel导入
	function excelImp(){
		impChoosetWManager.close();
		switch(caseStr){
			case "oldSubj":// 旧科目导入
				importSpreadView("/hrp/acc/join/importAccSubjOld.do?isCheck=false", paraSubj, queryClick());
				return;
			case "oldLedger":// 旧余额导入
				importSpreadView("/hrp/acc/join/importAccLedgerOld.do?isCheck=false", paraLedger, queryClick());
				return;
			<%--
			case "newSubj":// 新科目导入
				importSpreadView("/hrp/acc/join/importAccSubjNew.do?isCheck=false", paraSubj);
				return;
			case "newLedger":// 新余额导入
				importSpreadView("/hrp/acc/join/importAccLedgerNew.do?isCheck=false", paraLedger);
				return;
			--%>
		}
	}
	
	// 从系统导入
	function existsImp(){
		impChoosetWManager.close();
		var watw = $.ligerDialog.waitting('正在导入,请稍候...');
		ajaxJsonObjectByUrl(
			"importSystemAccSubj.do?isCheck=false", 
			{"caseStr" : caseStr}, 
			function(responseData) {
				watw.close();
				queryClick();
			}
		);
		setTimeout(function(){watw.close();}, 2000);
	}
	
	function updateTran(subj_code_old,subj_code_new,subj_code_new_b){
		$.ligerDialog.open({
			url: 'AccTranMapUpdatePage.do?isCheck=false&subj_code_old='+subj_code_old+"&subj_code_new="+subj_code_new+"&subj_code_new_b="+subj_code_new_b, 
			height: 380,
			width: 620, 
			title:'修改科目对照关系',
			modal:true,
			showToggle:false,
			showMax:false,
			showMin: true,
			isResize:true,
			buttons: [ 
				{ 
					text: '确定', 
					onclick: function (item, dialog) { 
						dialog.frame.save(subj_code_old,subj_code_new,subj_code_new_b,dialog);
					}, 
					cls:'l-dialog-btn-highlight'
				}, 
				{ 
					text: '关闭', 
					onclick: function (item, dialog) { 
						dialog.close(); 
					}
				} 
			]
		});
	}
	
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="layout1">
		<div position="left"><ul id="tree1"></ul></div>
		<div position="center" title=" ">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-bottom: 10px;margin-top: 10px">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码:</td>
					<td align="left" class="l-table-edit-td">
						<input class="queryinp" name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" />
					</td>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称:</td>
					<td align="left" class="l-table-edit-td">
						<input class="queryinp" name="subj_name" type="text" id="subj_name" ltype="text" validate="{required:true,maxlength:20}" />
					</td>
					<td align="left"></td>
				</tr>
			</table>
			<div id="rightgrid"></div>
		</div>
	</div>
	<div id="targetWindow" style="display:none;margin-top: 30px">
		<center>
			<input class="choosebtn" type="button" value="Excel导入" onclick="excelImp();"/><br/><br/>
			<input class="choosebtn" type="button" value="从系统导入" onclick="existsImp();"/>
		</center>
	</div>
</body>
</html>