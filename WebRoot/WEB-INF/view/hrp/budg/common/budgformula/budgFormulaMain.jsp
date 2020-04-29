<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!-- 计算公式 -->
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

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name : 'formula_id', value : $("#formula_id").val()}); 
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{display : '公式编码',name : 'formula_id',align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.formula_id + "|"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>"
											+ rowdata.formula_id + "</a>";
								}
							}, {display : '公式名称',	name : 'formula_name',align : 'left'
							}, {display : '计算公式(中文）',name : 'formula_ca',align : 'left'
							}, {display : '计算公式(英文)',name : 'formula_en',align : 'left'
							}],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryBudgFormula.do?isCheck=false',
					width : '100%',	height : '100%',checkbox : true,rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ 
				          {	text : '查询（<u>Q</u>）',id : 'search',click : query,icon : 'search'}, 
				          {line : true}, 
				          {text : '添加（<u>A</u>）',	id : 'add',	click : addFormula,	icon : 'add'},
				          {line : true}, 
				          {text : '删除（<u>D</u>）',	id : 'delete',click : deleteFormula,icon : 'delete'},
				          {line : true}, 
				          {text : '打印（<u>P</u>）',	id : 'print',click : printDate,icon : 'print'},
				          {line : true} 
				          /* {text : '下载导入模板（<u>L</u>）',id : 'downTemplate',click : templateFormula,icon : 'down'}, 
				          {line : true}, 
				          {text : '导入（<u>I</u>）',	id : 'import',click : importFormula,icon : 'up'} */ 
				          ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.formula_id + "|"
								+ rowdata.group_id + "|" + rowdata.hos_id + "|"
								+ rowdata.copy_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addFormula);

		hotkeys('D', deleteFormula);

	/* 	hotkeys('L', templateFormula);

		hotkeys('I', importFormula); */

		hotkeys('P', printDate);

	}

	function addFormula() {
		$.ligerDialog.open({
			url : 'budgFormulaAddPage.do?isCheck=false',height : 500,width : 750,title : '添加',
			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
			buttons : [ {text : '确定',onclick : function(item, dialog) {dialog.frame.saveBudgFormula();},
				cls : 'l-dialog-btn-highlight'}, {text : '取消',onclick : function(item, dialog) {dialog.close();}
			} ]
		});

	}

	function deleteFormula() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(
							this.group_id + "@" + 
							this.hos_id + "@" +
							this.copy_code + "@" + 
							this.formula_id
						)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteBudgFormula.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function importFormula() {
		$.ligerDialog.open({url : 'budgFormulaImportPage.do?isCheck=false',	height : 500,width : 800,title : '导入',
			modal : true,showToggle : false,showMax : false,showMin : true,	isResize : true
		});

	}

	function templateFormula() {

		location.href = "downTemplate.do?isCheck=false";

	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&formula_id=" + vo[0] + "&group_id=" + vo[1] + "&hos_id="
				+ vo[2] + "&copy_code=" + vo[3]

		$.ligerDialog.open({
			url : 'budgFormulaUpdatePage.do?isCheck=false&' + parm,	data : {},height : 500,	width : 750,
			title : '修改',	modal : true,showToggle : false,showMax : true,	showMin : false,isResize : true,
			buttons : [ {text : '确定',onclick : function(item, dialog) {dialog.frame.saveBudgFormula();},
				cls : 'l-dialog-btn-highlight'}, {text : '取消',	onclick : function(item, dialog) {dialog.close();}
			} ]
		});

	}
	function loadDict() {
		//字典下拉框
		/* autocomplete("#formula_name", "../queryBudgFormula.do?isCheck=false",
				"id", "text", true, true); */
		$("#formula_id").ligerTextBox({width : 160});
	}
	
	//打印
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	   
		grid.options.parms=[];
		
		grid.options.parms.push({name:'formula_id',value:$("#formula_id").val()}); 
		var selPara={};
		$.each(grid.options.parms,function(i,obj){
			selPara[obj.name]=obj.value;
		});
		var printPara={
			headCount:2,
			title:"计算公式信息",
			type:3,
			columns:grid.getColumns(1)
		};
		ajaxJsonObjectByUrl("queryBudgFormula.do?isCheck=false", selPara, function(responseData) {
			printGridView(responseData,printPara);
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_id" type="text" id="formula_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
