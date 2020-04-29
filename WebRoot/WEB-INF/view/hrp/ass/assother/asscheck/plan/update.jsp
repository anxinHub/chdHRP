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
<style type="text/css">
    #menu1,.l-menu-shadow{top:0px; left:50px;}
    #menu1{  width:20px;}
</style>  
<script type="text/javascript">
var grid;
var gridManager = null;
var userUpdateStr;
var menu;
var is_dept='01';
var v_text="";
var bill_id=0;
var bill_no=0;
var check_no="";
$(function() {
	$("#layout1").ligerLayout({
		leftWidth : 235
	});
	loadDict()//加载下拉框
	//加载数据
	loadHead(null);
	loadHotkeys();
	
	/* if ('${state}' == 1) {//置黑不能点击
	toobarmanage = gridManager.toolbarManager;
	toobarmanage.setDisabled('add');
	toobarmanage.setDisabled('remove');
	toobarmanage.setDisabled('copyAmount');

} 
if ('${state}'!=1) {//置黑不能点击
toobarmanage = gridManager.toolbarManager;
toobarmanage.setDisabled('checkRegister');
} */
	$("#check_no").ligerTextBox({
		width : 160,
		disabled : true,
		cancelable : false
	});
	$("#check_no").ligerTextBox({
		width : 160
	});
	$("#state_name").ligerTextBox({
		width : 160,
		disabled : true,
		cancelable : false
	});
	$("#ass_card_no").ligerTextBox({
		width : 160
	});
	$("#ass_type_id").ligerTextBox({
		width : 160
	});
	$("#ass_id").ligerTextBox({
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
		}
		]
	});
	//query();
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
	if(note.data.check_no=="0"){
		liger.get("check_no").setValue("");
	}else{
		liger.get("check_no").setValue(note.data.check_no);
		liger.get("state_name").setValue(note.data.state_name);
	}
	
	 bill_id=note.data.bill_id;
	 bill_no=note.data.bill_no;
	 is_dept=note.data.pcode;
	 loadHead();
	 if (note.data.state == 1) {//置黑不能点击
		 toobarmanage = gridManager.toolbarManager;
		 toobarmanage.setDisabled('add');
		 toobarmanage.setDisabled('remove');
		 toobarmanage.setDisabled('copyAmount');
		 toobarmanage.setDisabled('save');
		 } 
		 if (is_dept=="02"&& note.data.state !=1) {//置黑不能点击
		 toobarmanage = gridManager.toolbarManager;
		 toobarmanage.setDisabled('checkRgister');
		 toobarmanage.setDisabled('inventory');
		 }
		 if (is_dept=="01"&& note.data.state !=1) {//置黑不能点击
			 toobarmanage = gridManager.toolbarManager;
			 toobarmanage.setDisabled('checkRgister');
			 toobarmanage.setDisabled('inventory');
			 }
	query(note);
	
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

			check_plan_no : '${check_plan_no}'

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
							is_dept = node.data.pcode;
							v_text=node.data.text;
							bill_id=node.data.bill_id;
							bill_no=node.data.bill_no;
							check_no=node.data.check_no;
							state=node.data.state;
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
function query(note) {
	grid.options.parms = [];
	grid.options.newPage = 1;
	if(isExitsVariable(note)){
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'check_plan_no',
			value : '${check_plan_no}'
		});
		grid.options.parms.push({
			name : 'check_no',
			value : note.data.check_no
		});
	}else{
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'check_plan_no',
			value : '${check_plan_no}'
		});
		grid.options.parms.push({
			name : 'check_no',
			value : $("#check_no").val()
		});
		grid.options.parms.push({
			name : 'ass_card_no',
			value : $("#ass_card_no").val()
		});
		grid.options.parms.push({
			name : 'ass_type_id',
			value : liger.get("ass_type_id").getValue()
		});
		grid.options.parms.push({
			name : 'ass_id',
			value : $("#ass_id").val()
		});
	}

	//加载查询条件
	grid.loadData(grid.where);
	$("#resultPrint > table > tbody").empty();
}

function loadHead() {
	if(is_dept=="02"){
	grid = $("#maingrid").ligerGrid(
			{
				columns : [ {
					display : '卡片号',
					name : 'ass_card_no',
					align : 'left'
				}, {
					display : '条形码',
					name : 'bar_code',
					align : 'left'
				}, {
					display : '资产分类',
					name : 'ass_type_name',
					align : 'left'
				}, {
					display : '资产名称',
					name : 'ass_name',
					align : 'left'
				}, {
					display : '规格型号',
					name : 'ass_spec',
					align : 'left'
				}, {
					display : '原值',
					name : 'price',
					align : 'left'
				}, {
					display : '序列号',
					name : 'ass_seq_no',
					align : 'left'
				}, {
					display : '入库日期',
					name : 'in_date',
					align : 'left'
				}, {
					display : '使用部门',
					name : 'dept_name',
					align : 'left'
				} , {
					display : '账面数量',
					name : 'acc_amount',
					align : 'left'
				} , {
					display : '实盘数量',
					name : 'check_amount',
					align : 'left',
					 editor: { type: 'text' },
					render : function(item) {
						return item.check_amount  == null || item.check_amount  == "" ? "0"
										: item.check_amount;
					}
				} , {
					display : '盈亏差异',
					name : 'pl_amount',
					align : 'left',
					render : function(item) {
						/* console.log(item) */
						item.pl_amount = formatNumber(item.check_amount
								- item.acc_amount);
						return item.pl_amount;

						
					}
				} , {
					display : '盘亏原因',
					name : 'pl_reason',
					align : 'left',
					 editor: { type: 'text' },
				} ],
				rowAttrRender:function(rowdata,rowid){
		 			if(rowdata.pl_amount==-1){
		 				return "style='background:#DD001B;'"
		 			}/* else if(rowdata.pl_amount==1){
		 				return "style='background:rgba(245, 245, 245, 0.31)'"
		 			} */
		 			return "";
	 			},
	 			alternatingRow:false,
				enabledEdit: true,
				dataAction : 'server',
				dataType : 'server',
				usePager : false,
				url : 'queryAssCheckDdetailOther.do?isCheck=false',
				width : '100%',
				height : '100%',
				checkbox : true,
				rownumbers : true,
				delayLoad : true,
				isAddRow: false,
				selectRowButtonOnly : true,
				toolbar : {
					items : [  {
						text : '生成（<u>A</u>）',
						id : 'add',
						click : generate,
						icon : 'add'
					},  {
						line : true
					}, {
						text : '提交（<u>D</u>）',
						id : 'audit',
						click : audit,
						icon : 'add'
					}, {
						line : true
					}, {
						text : '取消提交（<u>D</u>）',
						id : 'unAudit',
						click : unAudit,
						icon : 'add'
					}, {
						line : true
					}, {
						text : '复制涨面数（<u>T</u>）',
						id : 'copyAmount',
						click : copyAmount,
						icon : 'add'
					}, {
						line : true
					}, {
						text : '盘盈登记（<u>H</u>）',
						id : 'checkRgister',
						click : checkRgister,
						icon : 'add'
					},{
						line : true
					},{
						text : '生成盈亏单',
						id : 'inventory',
						click : inventory ,
						icon : 'add'
					},{
						line : true
					},  {
						text : '打印模板设置（<u>M</u>）',
						id : 'printSet',
						click : printSets,
						icon : 'settings'
					}, {
						line : true
					},
				  {
						text : '批量打印（<u>P</u>）',
						id : 'print',
						click : printDates,
						icon : 'print'
					} ,{
						line : true
					},  {
						text : '关闭',
						id : 'close',
						click : this_close,
						icon : 'candle'
					} ]
				},
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
	}else{
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {
						display : '卡片号',
						name : 'ass_card_no',
						align : 'left'
					}, {
						display : '条形码',
						name : 'bar_code',
						align : 'left'
					}, {
						display : '资产分类',
						name : 'ass_type_name',
						align : 'left'
					}, {
						display : '资产名称',
						name : 'ass_name',
						align : 'left'
					}, {
						display : '规格型号',
						name : 'ass_spec',
						align : 'left'
					}, {
						display : '原值',
						name : 'price',
						align : 'left'
					}, {
						display : '序列号',
						name : 'ass_seq_no',
						align : 'left'
					}, {
						display : '入库日期',
						name : 'in_date',
						align : 'left'
					}, {
						display : '所在仓库',
						name : 'store_name',
						align : 'left'
					} , {
						display : '账面数量',
						name : 'acc_amount',
						align : 'left'
					} , {
						display : '实盘数量',
						name : 'check_amount',
						align : 'left',
						 editor: { type: 'text' },
						render : function(item) {
							return item.check_amount  == null || item.check_amount  == "" ? "0"
											: item.check_amount;
						}
					} , {
						display : '盈亏差异',
						name : 'pl_amount',
						align : 'left',
						 render : function(item) {
							/* console.log(item) */
							item.pl_amount = formatNumber(item.check_amount
									- item.acc_amount);
							return (
									item.pl_amount  == null ? 0
											: item.pl_amount);

							
						}
					} , {
						display : '盘亏原因',
						name : 'pl_reason',
						align : 'left',
						 editor: { type: 'text' },
					} ],
					rowAttrRender:function(rowdata,rowid){
			 			if(rowdata.pl_amount==-1){
			 				return "style='background:#DD001B;'"
			 			}/* else if(rowdata.pl_amount==1){
			 				return "style='background:rgba(245, 245, 245, 0.31)'"
			 			} */
			 			return "";
		 			},
		 			alternatingRow:false,
					enabledEdit: true,
					dataAction : 'server',
					dataType : 'server',
					usePager : false,
					url : 'queryAssCheckSdetailOther.do?isCheck=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					isAddRow: false,
					selectRowButtonOnly : true,
					toolbar : {
						items : [{
							text : '生成（<u>A</u>）',
							id : 'add',
							click : generate,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '提交（<u>D</u>）',
							id : 'audit',
							click : audit,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '取消提交（<u>D</u>）',
							id : 'unAudit',
							click : unAudit,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '复制涨面数（<u>T</u>）',
							id : 'copyAmount',
							click : copyAmount,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '盘盈登记（<u>H</u>）',
							id : 'checkRgister',
							click : checkRgister,
							icon : 'add'
						},{
							line : true
						},{
							text : '生成盈亏单',
							id : 'inventory',
							click : inventory ,
							icon : 'add'
						}, {
							line : true
						},  {
							text : '打印模板设置（<u>M</u>）',
							id : 'printSet',
							click : printSet,
							icon : 'settings'
						}, {
							line : true
						},
					  {
							text : '批量打印（<u>P</u>）',
							id : 'print',
							click : printDate,
							icon : 'print'
						} ,{
							line : true
						},{
							text : '关闭',
							id : 'close',
							click : this_close,
							icon : 'candle'
						} ]
					},
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
}

//保存
function save(){
	if(is_dept=="01"){
		gridManager.endEdit();
		var data = gridManager.getCheckedRows();
		if (data.length == 0 ) {
			$.ligerDialog.warn('请选择行');
			return false;
		} else {
			
			var check = /^[0|1]$/;
			
			var check_amount_tip = ''	
			var check_amount_flag = false;
			var data = gridManager.getData();
			data.forEach(function (item,index) {
				if (!check.test(item.check_amount)) {
					check_amount_flag = true;
					check_amount_tip += '第'+(index+1) + '行,'
				}
			})
			if (check_amount_flag) {
				$.ligerDialog.error(check_amount_tip +'实盘数量只能是"1"或者是"0"');
				return;
			} 
			
			var formPara = {
					check_plan_no : '${check_plan_no}',
					check_no : $("#check_no").val(),
					acc_amount : $("#acc_amount").val(),
					check_amount :	$("#check_amount").val(),
					pl_amount : $("#pl_amount").val(),
					pl_reason : $("#pl_reason").val(),
					ParamVo : JSON.stringify(data)
				};
				
					ajaxJsonObjectByUrl("saveAssCheckSDetailOther.do?isCheck=false", formPara, function(
							responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				
		
		}
	}else if(is_dept=="02"){
		gridManager.endEdit();
		var data = gridManager.getCheckedRows();
		if (data.length == 0 ) {
			$.ligerDialog.warn('请选择行');
			return false;
		} else {

			var check = /^[0|1]$/;
			
			var check_amount_tip = ''	
			var check_amount_flag = false;
			var data = gridManager.getData();
			data.forEach(function (item,index) {
				if (!check.test(item.check_amount)) {
					check_amount_flag = true;
					check_amount_tip += '第'+(index+1) + '行,'
				}
			})
			if (check_amount_flag) {
				$.ligerDialog.error(check_amount_tip +'实盘数量只能是"1"或者是"0"');
				return;
			} 
			var formPara = {
					check_plan_no : '${check_plan_no}',
					check_no : $("#check_no").val(),
					acc_amount : $("#acc_amount").val(),
					check_amount :	$("#check_amount").val(),
					pl_amount : $("#pl_amount").val(),
					pl_reason : $("#pl_reason").val(),
					ParamVo : JSON.stringify(data)
				};
			ajaxJsonObjectByUrl("saveAssCheckDDetailOther.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
				}
			});
		}
	}
}

//生成盈亏单
function inventory() {
	var data = gridManager.getData();
	var data1 = [];//仓库
	var data2 = [];//科室
	//console.info(data);
	if (data.length == 0) {
		$.ligerDialog.error('请选择行');
	}else{
	if(is_dept=="01"){
		
	$(data).each(function(){
	
		data1.push(this.ass_id+"@"+this.hos_id+"@"+this.copy_code+"@"+this.group_id+"@"+this.store_id+"@"+this.ass_card_no+"@"+this.ass_type_name+"@"+this.ass_name+"@"+this.store_name+"@"+this.acc_amount+"@"+this.check_amount+"@"+this.pl_amount+"@"+this.pl_reason+"@"+this.store_no+"@"+this.ass_no+"@"+this.check_no+"@"+this.check_plan_no);
		
	});  

		 $.ligerDialog.confirm('确定生成么?', function(yes) {
		 if (yes) {
		 	ajaxJsonObjectByUrl("saveOtherInventory.do?isCheck=false"  ,
		 			{data1 : data1.toString()}, function(
		 			responseData) {
		 		if (responseData.state == "true") {
		 			query();
		 			loadTree();
		 		}
		 	});
		 }
		 })
	
		 } else if(is_dept=="02"){
			 $(data).each(function(){
					
				data2.push(this.ass_id+"@"+this.hos_id+"@"+this.copy_code+"@"+this.group_id+"@"+this.dept_id+"@"+this.ass_card_no+"@"+this.ass_type_name+"@"+this.ass_name+"@"+this.dept_name+"@"+this.acc_amount+"@"+this.check_amount+"@"+this.pl_amount+"@"+this.pl_reason+"@"+this.dept_no+"@"+this.ass_no+"@"+this.check_no+"@"+this.check_plan_no);
				
				});  

					 $.ligerDialog.confirm('确定生成么?', function(yes) {
					 if (yes) {
					 	ajaxJsonObjectByUrl("saveDeptOtherInventory.do?isCheck=false"  ,
					 			{data2 : data2.toString()}, function(
					 			responseData) {
					 		if (responseData.state == "true") {
					 			query();
					 			loadTree();
					 		}
					 	});
					 }
					 })
		 }

	}
}

function tree_open() {
	var parm = "check_plan_no=" + '${check_plan_no}' ;
	if(is_dept=="01" || v_text=="仓库"){
		$.ligerDialog.open({
			url : 'assCheckSOtherAddPage.do?isCheck=false&' + parm,
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
					dialog.frame.saveAssCheckSOther();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}else if(is_dept=="02" || v_text=="科室"){
		$.ligerDialog.open({
			url : 'assCheckDOtherAddPage.do?isCheck=falsefalse&' + parm,
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
					dialog.frame.saveAssCheckDOther();
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
	
}

function tree_del() {
	if(is_dept=="01"){
		
		var parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ check_no + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no;
$.ligerDialog.confirm('确定删除?', function(yes) {
	if (yes) {
		ajaxJsonObjectByUrl("delAssCheckSOther.do?isCheck=false&" + parm,
				{}, function(
				responseData) {
			if (responseData.state == "true") {
				query();
				loadTree();
			}
		});
	}
});
	}else{
		var parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ check_no + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no;
$.ligerDialog.confirm('确定删除?', function(yes) {
	if (yes) {
		ajaxJsonObjectByUrl("delAssCheckDOther.do?isCheck=false&" + parm,
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
	
}
function loadDict() {
	//字典下拉框
	autocomplete("#ass_type_id","../../../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"260");

}
//键盘事件
function loadHotkeys() {

	hotkeys('Q', query);

}


function generate(){
	if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	
	var parm="";
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}else if(is_dept=="02"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}
	
$.ligerDialog.confirm('确定生成?', function(yes) {
if (yes) {
	ajaxJsonObjectByUrl("OtherGenerateDetail.do?isCheck=false&" + parm,
			{}, function(
			responseData) {
		if (responseData.state == "true") {
			query();
			loadTree();
		}
	});
}
})
}



function audit(){
if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	var parm="";
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0&"+"&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}else if(is_dept=="02"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ check_no + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}
	
$.ligerDialog.confirm('确定提交?', function(yes) {
if (yes) {
	ajaxJsonObjectByUrl("auditOther.do?isCheck=false&" + parm,
			{}, function(
			responseData) {
		if (responseData.state == "true") {
			liger.get("state_name").setValue("提交");
			query();
			loadTree();
			 toobarmanage = gridManager.toolbarManager;
			 toobarmanage.setDisabled('add');
			 toobarmanage.setDisabled('remove');
			 toobarmanage.setDisabled('copyAmount');
			 toobarmanage.setDisabled('save');
			 toobarmanage.setEnabled('checkRgister');
			 toobarmanage.setEnabled('inventory');
		}
	});
}
})
}

function unAudit(){
if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	var parm="";
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}else if(is_dept=="02"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1&"+"ass_type_id="+liger.get("ass_type_id").getValue();
	}
	
$.ligerDialog.confirm('确定取消提交?', function(yes) {
if (yes) {
	ajaxJsonObjectByUrl("unAuditOther.do?isCheck=false&" + parm,
			{}, function(
			responseData) {
		if (responseData.state == "true") {
			liger.get("state_name").setValue("新建");
			query();
			loadTree();
			toobarmanage = gridManager.toolbarManager;
			 toobarmanage.setEnabled('add');
			 toobarmanage.setEnabled('remove');
			 toobarmanage.setEnabled('copyAmount');
			 toobarmanage.setEnabled('save');	
			 toobarmanage.setDisabled('checkRgister');
			 toobarmanage.setDisabled('inventory');
		}
	});
}
})
}
function copyAmount(){
if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	
	var data = gridManager.getCheckedRows();
	var group_id = "";
	var hos_id = "";
	var copy_code = "";
	if (data.length == 0) {
		$.ligerDialog.error('请选择行');
	} else {

		var ass_card_no_all = [];
		$(data).each(function() {

			group_id = this.group_id;
			hos_id = this.hos_id;
			copy_code = this.copy_code;

			ass_card_no_all.push(this.ass_card_no)
		});
		
	}
	var parm="";
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0&"+"ass_type_id="+liger.get("ass_type_id").getValue()+
	"&"+"ass_card_no_all="+ass_card_no_all.toString()+
	"&"+"group_id="+group_id+"&hos_id="+hos_id+"&copy_code="+copy_code;
		

		 $.ligerDialog.confirm('确定复制账面数量?', function(yes) {
		 if (yes) {
		 	ajaxJsonObjectByUrl("copyAmountAssCheckSOther.do?isCheck=false&" + parm,
		 			{}, function(
		 			responseData) {
		 		if (responseData.state == "true") {
		 			query();
		 			loadTree();
		 		}
		 	});
		 }
		 })
		 
	}else if(is_dept=="02"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1&"+"ass_type_id="+liger.get("ass_type_id").getValue()+
		"&"+"ass_card_no_all="+ass_card_no_all.toString()+
		"&"+"group_id="+group_id+"&hos_id="+hos_id+"&copy_code="+copy_code;
		 

		 $.ligerDialog.confirm('确定复制账面数量?', function(yes) {
		 if (yes) {
		 	ajaxJsonObjectByUrl("copyAmountAssCheckDOther.do?isCheck=false&" + parm,
		 			{}, function(
		 			responseData) {
		 		if (responseData.state == "true") {
		 			query();
		 			loadTree();
		 		}
		 	});
		 }
		 })
	}
	
}
function remove(){
	if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	
	var data = gridManager.getCheckedRows();
	var group_id = "";
	var hos_id = "";
	var copy_code = "";
	if (data.length == 0) {
		$.ligerDialog.error('请选择行');
	} else {

		var ass_card_no_all = [];
		$(data).each(function() {

			group_id = this.group_id;
			hos_id = this.hos_id;
			copy_code = this.copy_code;

			ass_card_no_all.push(this.ass_card_no)
		});
		
	}
	var parm="";
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0&"+"ass_type_id="+liger.get("ass_type_id").getValue()+
	"&"+"ass_card_no_all="+ass_card_no_all.toString()+
	"&"+"group_id="+group_id+"&hos_id="+hos_id+"&copy_code="+copy_code;
		

		 $.ligerDialog.confirm('确定删除?', function(yes) {
		 if (yes) {
		 	ajaxJsonObjectByUrl("delAmountAssCheckSOther.do?isCheck=false&" + parm,
		 			{}, function(
		 			responseData) {
		 		if (responseData.state == "true") {
		 			query();
		 			loadTree();
		 		}
		 	});
		 }
		 })
		 
	}else if(is_dept=="02"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1&"+"ass_type_id="+liger.get("ass_type_id").getValue()+
		"&"+"ass_card_no_all="+ass_card_no_all.toString()+
		"&"+"group_id="+group_id+"&hos_id="+hos_id+"&copy_code="+copy_code;
		 

		 $.ligerDialog.confirm('确定删除?', function(yes) {
		 if (yes) {
		 	ajaxJsonObjectByUrl("delAmountAssCheckDOther.do?isCheck=false&" + parm,
		 			{}, function(
		 			responseData) {
		 		if (responseData.state == "true") {
		 			query();
		 			loadTree();
		 		}
		 	});
		 }
		 })
	}
	
}

function this_close() {
	frameElement.dialog.close();
}

function checkRgister(){
	if(isnull($("#check_no").val())){
		
		$.ligerDialog.warn('请选择盘点单');
		return false;
	}
	var group_id = "";
	var hos_id = "";
	var copy_code = "";
	var parm="";
	
	if(is_dept=="01"){
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "store_id="
		+ bill_id+"&" +"store_no="+bill_no+"&"+"is_dept=0";
	}else if(is_dept=="02"){
		
		 parm = "check_plan_no=" + '${check_plan_no}' + "&" + "check_no="
		+ $("#check_no").val() + "&" + "dept_id="
		+ bill_id+"&" +"dept_no="+bill_no+"&"+"is_dept=1";
	}
	
	
	if(is_dept=="01"){
		$.ligerDialog.open({
			title: '仓库盘盈登记',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assCheckSpOtherPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}else if(is_dept=="02"){
		$.ligerDialog.open({
			title: '科室盘盈登记',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assCheckDpOtherPage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
}

//打印模板设置 最新版
function printSet(){
  
	var useId=0;//统一打印
	if('${ass_05040}'==1){
		//按用户打印
		useId='${user_id }';
	}else if('${ass_05040}'==2){
		//按仓库打印
		if(liger.get("store_code").getValue()==""){
			$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
			return;
		}
		useId=liger.get("store_code").getValue().split(",")[0];
	}
	
	officeFormTemplate({template_code:"05040",use_id : useId})
}

//打印 最新版
function printDate(){
	
	 var useId=0;//统一打印
		if('${ass_05040}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05040}'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}

		var data = gridManager.getCheckedRows();
	/* if (data.length == 0){
		$.ligerDialog.error('请选择行');
	}else{
		
		var check_no ="" ;
		
		$(data).each(function (){	
			
			check_no  += "'"+this.check_no+"',"
				
		}); */
		
		 var para={
				 
				template_code:'05040',
				class_name:"com.chd.hrp.ass.serviceImpl.check.other.AssCheckSdetailOtherServiceImpl", 
				method_name:"queryAssInSpecialByPrintTemlatePrint",
				isSetPrint:false,//是否套打，默认非套打
				isPreview:true,//是否预览，默认直接打印
				check_no:$("#check_no").val() ,
    			isPrintCount:false,//更新打印次数
    			use_id:useId,
    			p_num:1
    			//isSetPrint:flag
    	}; 
		ajaxJsonObjectByUrl("queryAssInSpecialState.do?isCheck=false",{check_no:$("#check_no").val(),state:1},function(responseData){
			if (responseData.state == "true") {
				officeFormPrint(para);
			}
		}); 
	}
		
		
		//打印模板设置 最新版
		function printSets(){
		  
			var useId=0;//统一打印
			if('${ass_05040}'==1){
				//按用户打印
				useId='${user_id }';
			}else if('${ass_05040}'==2){
				//按仓库打印
				if(liger.get("dept_code").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("dept_code").getValue().split(",")[0];
			}
			
			officeFormTemplate({template_code:"05040",use_id : useId})
		}

		//打印 最新版
		function printDates(){
			
			 var useId=0;//统一打印
				if('${ass_05040}'==1){
					//按用户打印
					useId='${user_id }';
				}else if('${ass_05040}'==2){
					//按仓库打印
					if(liger.get("dept_code").getValue()==""){
						$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
						return;
					}
					useId=liger.get("dept_code").getValue().split(",")[0];
				}

		
				 var para={
						 
						template_code:'05040',
						class_name:"com.chd.hrp.ass.serviceImpl.check.other.AssCheckDdetailOtherServiceImpl", 
						method_name:"queryAssInSpecialByPrintTemlatePrints",
						isSetPrint:false,//是否套打，默认非套打
						isPreview:true,//是否预览，默认直接打印
						check_no:$("#check_no").val() ,
		    			isPrintCount:false,//更新打印次数
		    			use_id:useId,
		    			p_num:1
		    			//isSetPrint:flag
		    	}; 
				ajaxJsonObjectByUrl("queryAssInSpecialStates.do?isCheck=false",{check_no:$("#check_no").val(),state:1},function(responseData){
					if (responseData.state == "true") {
						officeFormPrint(para);
					}
				});}
var menu1 = { width: 120, items:
        [
        { text: '查询', click: query },
        { line: true },
        { text: '保存', click: save },
        { line: true },
        { text: '删除', click: remove }
        ]
    };
				
$(function (){
 $("#topmenu").ligerToolBar({ items: [{ text: '基本功能', menu: menu1 }] });
});			
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>


<div id="layout1">
	<div position="left" title="按在库在用划分">
		<div class="tree">
			<ul class="ztree" id="tree"></ul>
		</div>
	</div>
	<%-- <div position="top">
			<table cellpadding="0" cellspacing="0" class="l-table-edit"
				width="100%">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">任务单号：</td>
					<td align="left" class="l-table-edit-td"><input
						name="check_plan_no" type="text" id="check_plan_no" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">盘点开始日期：</td>
					<td align="left" class="l-table-edit-td"><input
						name="beg_date" type="text" id="beg_date" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">盘点结束日期：</td>
					<td align="left" class="l-table-edit-td"><input
						name="end_date" type="text" id="end_date" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td align="left"></td>


				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">盘点完成日期：</td>
					<td align="left" class="l-table-edit-td"><input
						name="fin_date" type="text" id="fin_date" class="Wdate"
						onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">状态：</td>
					<td align="left" class="l-table-edit-td"><select id="state"
						name="state">
							<option value="">全部</option>
							<option value="0">新建</option>
							<option value="1">提交</option>
							<option value="1">完成</option>
					</select></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">任务说明：</td>
					<td align="left" class="l-table-edit-td" colspan="9"><textarea
							rows="3" cols="70" id="summary" name="summary">${summary }</textarea></td>
				</tr>
			</table>
		<!-- <div id="toptoolbar"></div> -->
	</div> --%>
	<div position="center">
	<table cellpadding="0" cellspacing="0" class="l-table-edit"	width="100%">
		<input type="hidden" name="check_plan_no" value="${check_plan_no}"/>
	
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">盘点单号：</td>
					<td align="left" class="l-table-edit-td"><input
						name="check_no" type="text" id="check_no" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">卡片号：</td>
					<td align="left" class="l-table-edit-td"><input
						name="ass_card_no" type="text" id="ass_card_no" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
				<td align="left"></td>
					 <td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">盘点单状态：</td>
					<td align="left" class="l-table-edit-td">
					<input type="hidden" name="state" id="state"/>
					<input
						name="state_name" type="text" id="state_name" ltype="text" 
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
				</tr>
				<tr>
				<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">资产分类：</td>
					<td align="left" class="l-table-edit-td"><input
						name="ass_type_id" type="text" id="ass_type_id" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">资产名称：</td>
					<td align="left" class="l-table-edit-td"><input
						name="ass_id" type="text" id="ass_id" ltype="text"
						validate="{required:true,maxlength:20}" /></td>
					<td align="left"></td>
				</tr>
			</table>
		<div id="topmenu"></div> 
		<div id="maingrid"></div>
	</div>
</div>
</body>
</html>

