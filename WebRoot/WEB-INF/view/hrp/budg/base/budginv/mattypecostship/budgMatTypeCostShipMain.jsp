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
		loadHead(null); //加载数据
		loadHotkeys();
		$("#budg_year").change(function() {
			budg_year = liger.get("budg_year").getValue();
			query()
		})
		$("#mat_type_code").change(function() {
			query()
		})
		$("#cost_subj_code").change(function() {
			query()
		})
		$("#income_subj_code").change(function() {
			query()
		})
	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'budg_year',
			value : liger.get("budg_year").getValue()
		});
		
		grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'cost_subj_code',
			value : liger.get("cost_subj_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'income_subj_code',
			value : liger.get("income_subj_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
				columns : [ 
						    {display : '物资分类',name : 'mat_type_code_name',align : 'center',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.budg_year + "|"
											+ rowdata.mat_type_id + "|"
											+ rowdata.income_subj_code +"')>"
											+ rowdata.mat_type_code_name + "</a>";
								}
							}, 
							{display : '预算支出科目',name : 'cost_subj_code_name',align : 'center'}, 
							{display : '非医用标识',name : 'no_medical',align : 'center',
								render : function(rowdata, rowindex, value) {
									if(value == 1){
										return "是"; 
									}else if(value == 0){
										return "否"; 
									}
									
								}
							},
							{display : '预算收入科目',name : 'income_subj_code_name',align : 'center'
							}],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryBudgMatTypeCostShip.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [
								{text : '查询（<u>E</u>）',id : 'search',click : query,icon : 'search'}, 
								{line : true}, 
								{text : '添加（<u>A</u>）',id : 'add',click : add_open,icon : 'add'}, 
								{line : true}, 
								/* {text : '打印（<u>P</u>）',id : 'print',click : printDate,icon : 'print'}, 
								{line : true },  */
								{text : '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete'}, 
								{line : true}, 
								/* {text : '下载导入模板（<u>B</u>）',id : 'downTemplate',click : downTemplate,icon : 'down'}, 
								{line : true },  */
								{text : '导入（<u>I</u>）',id : 'import',click : importExcel,icon : 'up'},
								{line : true},
								{text : '继承（<u>J</u>）',id : 'extend',click : extend,icon : 'extend'
								} ]
							},
							onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.copy_code + "|"
										+ rowdata.budg_year + "|"
										+ rowdata.mat_type_id + "|"
										+ rowdata.income_subj_code);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		$.ligerDialog.open({
			url : 'budgMatTypeCostShipAddPage.do?isCheck=false&budg_year='+budg_year,
			data : {},
			height : 400,
			width : 500,
			title : '物资分类与预算科目对应关系',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '保存',
				onclick : function(item, dialog) {
					dialog.frame.saveBudgMatTypeSubj();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}
	
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + 
				   "hos_id=" + vo[1] + "&" + 
				   "copy_code=" + vo[2] + "&" + 
				   "budg_year=" + vo[3] + "&" + 
				   "mat_type_id=" + vo[4] + "&" + 
				   "income_subj_code=" + vo[5]

		$.ligerDialog.open({
			url : 'budgMatTypeCostShipUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 400,
			width : 500,
			title : '物资分类与预算科目对应关系',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveBudgMatTypeCostShip();
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
	
	//打印
	function printDate() {
		if (grid.getData().length == 0) {
			$.ligerDialog.error("无打印数据！");
			return;
		}

		grid.options.parms = [];
		grid.options.newPage = 1;

		grid.options.parms.push({
			name : 'budg_year',
			value : $("#budg_year").val()
		});
		grid.options.parms.push({
			name : 'cost_subj_code',
			value : $("#cost_subj_code").val()
		});
		grid.options.parms.push({
			name : 'mat_type_id',
			value : $("#mat_type_id").val()
		});
		grid.options.parms.push({
			name : 'mat_type_no',
			value : $("#mat_type_no").val()
		});
		var selPara = {};
		$.each(grid.options.parms, function(i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount : 2,
			title : "物资分类与预算科目对应关系",
			type : 3,
			columns : grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgMatTypeCostShip.do?isCheck=false",
				selPara, function(responseData) {
					printGridView(responseData, printPara);
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
							+ this.copy_code + "@" + this.budg_year + "@"
							+ this.mat_type_id+ "@"
							+ this.income_subj_code)
				});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBudgMatTypeCostShip.do", {
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
			content : 'budgMatTypeCostShipImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	//继承
	function extend(){
		$.ligerDialog.confirm('确定继承上一年度物资分类与预算科目对应关系', function (yes) { 
			if(yes){
				if(!liger.get("budg_year").getValue()){
					$.ligerDialog.error('预算年度不能为空');
					return ;
				}
				var formPara = {
					budg_year:liger.get("budg_year").getValue()
				};
				
				ajaxJsonObjectByUrl("extendBudgMatTypeCostShip.do?isCheck=false", formPara, function(
						responseData) {
					
					if (responseData.state == "true") {
						query();
					}
				}); 
			}else{
				$.ligerDialog.close();
			}
		});
	}
	
	function loadDict() {
		//字典下拉框
		//预算年度
		autocompleteAsync("#budg_year",
				"../../../queryBudgYear.do?isCheck=false", "id", "text", true,
				true, '', true);
		budg_year = liger.get("budg_year").getValue();
		//物资分类
		autocomplete("#mat_type_code",
				"../../../queryMatTypes.do?isCheck=false", "id", "text", true,
				true);
		//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）取末级
		autocomplete("#cost_subj_code",
				"../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1"
						+ "&budg_year=" + budg_year, "id", "text", true, true);
		
		autocomplete("#income_subj_code",
				"../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1"
						+ "&budg_year=" + budg_year, "id", "text", true, true);
		
		$("#mat_type_code,#cost_subj_code,#income_subj_code").ligerTextBox({
			width : 160
		});
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('I', imp);

	}
	
	//导入
	function importExcel(){
		var para = {
   			"column" : [ {
   				"name" : "budg_year",
   				"display" : "预算年度",
   				"width" : "200",
   				"require" : true
   			},{
   				"name" : "mat_type_name",
   				"display" : "物资分类",
   				"width" : "200",
   				"require" : true
   			},{
   				"name" : "cost_subj_code",
   				"display" : "预算支出科目编码",
   				"width" : "200",
   				"require" : true
   			},{
   				"name" : "no_medical",
   				"display" : "非医用标识",
   				"width" : "200",
   				"require" : true
   			},{
   				"name" : "income_subj_code",
   				"display" : "预算收入科目编码",
   				"width" : "200"
   			}]

   		};
   		importSpreadView("/hrp/budg/base/budginv/mattypecostship/importExcel.do?isCheck=false", para);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算年度：</td>
			<td align="left" class="l-table-edit-td"><input name="budg_year"
				type="text" id="budg_year" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">物资分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="mat_type_code" type="text" id="mat_type_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算支出科目：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_subj_code"
				type="text" id="cost_subj_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算收入科目：</td>
			<td align="left" class="l-table-edit-td"><input name="income_subj_code"
				type="text" id="income_subj_code" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>

	<div id="maingrid"></div>

</body>
</html>
