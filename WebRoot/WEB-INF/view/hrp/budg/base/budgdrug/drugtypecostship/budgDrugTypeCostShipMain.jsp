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
	var budg_year;
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);//加载数据
		loadHotkeys();

		$("#budg_year").change(function() {
			budg_year = liger.get("budg_year").getValue();
			query()
		})

		$("#med_type_id").change(function() {
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
		console.log(liger.get("med_type_id").getValue().split(",")[0]);
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'budg_year',
			value : liger.get("budg_year").getValue()
		});
		grid.options.parms.push({
			name : 'med_type_id',
			value : liger.get("med_type_id").getValue().split(",")[0]
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
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '药品分类',
								name : 'med_type_code_name',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									console.log(rowdata)
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.budg_year + "|"
											+ rowdata.med_type_id  + "|"
											+ rowdata.med_type_no  + "|"
											+ rowdata.income_subj_code + "')>"
											+ rowdata.med_type_code_name + "</a>";
								}
							}, {
								display : '预算支出科目',
								name : 'cost_subj_code_name',
								align : 'left'
							}, {
								display : '预算收入科目',
								name : 'income_subj_code_name',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryBudgDrugTypeCostShip.do?isChekc=false',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
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
						/* }, {
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
							line : true */
						}, {
							text : '导入（<u>I</u>）',
							id : 'import',
							click : imp,
							icon : 'up'
						} , {
							line : true
						}, {
							text : '继承（<u>J</u>）',
							id : 'extend',
							click : extend,
							icon : 'extend'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.copy_code + "|"
								+ rowdata.budg_year + "|"
								+ rowdata.med_type_id+ "|"
								+ rowdata.med_type_no+ "|"
								+ rowdata.income_subj_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {

		$.ligerDialog.open({
			url : 'budgDrugTypeCostShipAddPage.do?isCheck=false&budg_year='+budg_year,
			data : {},
			height : 400,
			width : 500,
			title : '药品分类与支出预算科目对应关系',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveBudgDrugTypeCostShip();
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
	
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&budg_year=" + vo[3] + "&med_type_id=" + vo[4]  
				+ "&med_type_no=" + vo[5] + "&" + "income_subj_code=" + vo[6]

		$.ligerDialog.open({
			url : 'budgDrugTypeCostShipUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 400,
			width : 500,
			title : '药品分类与支出预算科目对应关系',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveBudgDrugTypeCostShip();
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
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.budg_year + "@"
								+ this.med_type_id+ "@"
								+ this.income_subj_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl(
							"deleteBudgDrugTypeCostShip.do?isCheck=false", {
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
			content : 'budgDrugTypeCostShipImportPage.do?isCheck=false'
		});
		layer.full(index);
	}
	function downTemplate() {

		location.href = "downTemplate.do?isCheck=false";
	}

	//打印数据
	function printDate() {
		if (grid.getData().length == 0) {
			$.ligerDialog.error("无打印数据！");
			return;
		}

		grid.options.parms = [];

		grid.options.parms.push({
			name : 'budg_year',
			value : liger.get("budg_year").getValue()
		});
		grid.options.parms.push({
			name : 'subj_code',
			value : liger.get("subj_code").getValue()
		});
		grid.options.parms.push({
			name : 'drug_type_id',
			value : liger.get("drug_type_id").getValue().split(",")[0]
		});
		var selPara = {};
		$.each(grid.options.parms, function(i, obj) {
			selPara[obj.name] = obj.value;
		});
		var printPara = {
			headCount : 2,
			title : "药品类型与预算支出科目对应关系",
			type : 3,
			columns : grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgDrugTypeCostShip.do?isCheck=false",
				selPara, function(responseData) {
					printGridView(responseData, printPara);
				});
	}
	
	//继承
	function extend(){
		$.ligerDialog.confirm('确定继承上一年度药品分类与预算科目对应关系', function (yes) { 
			if(yes){
				var formPara = {
					budg_year:liger.get("budg_year").getValue()
				};
				
				//console.log(formPara);
				ajaxJsonObjectByUrl("extendBudgDrugTypeCostShip.do?isCheck=false", formPara, function(
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
	
	//字典下拉框
	function loadDict() {
		//预算年度
	/* 	autocompleteAsync("#budg_year",
				"../../../queryBudgYear.do?isCheck=false", "id", "text", true,
				true, '', true); */

				$("#budg_year").ligerTextBox({ width:160 });
		 		 
				 autodate("#budg_year","yyyy");
		budg_year = liger.get("budg_year").getValue();

		//药品分类
		autocomplete("#med_type_id",
				"../../../queryBudgDrugType.do?isCheck=false", "id", "text",
				true, true);
		
		//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
		autocomplete("#cost_subj_code",
				"../../../queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1"
						+ "&budg_year=" + budg_year, "id", "text", true, true);
		autocomplete("#income_subj_code",
				"../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1"
						+ "&budg_year=" + budg_year, "id", "text", true, true);

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add_open);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('P', printDate);
		hotkeys('I', imp);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预算年度：</td>
			<td align="left" class="l-table-edit-td"><input name="budg_year"
				type="text" id="budg_year" ltype="text"
				validate="{required:true,maxlength:20}"  class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">药品分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="med_type_id" type="text" id="med_type_id" ltype="text"
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
