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

	var dialog = frameElement.dialog;

	$(function() {
		loadDict();
		loadHead(null);

		$("#formula_code").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query() {
		//console.log(999)
		grid.options.parms = [];

		grid.options.newPage = 1;

		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'formula_code',
			value : $("#formula_code").val()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [ {display : '公式编码',name : 'formula_id',align : 'left',width : 80	},

					{display : '公式名称',name : 'formula_name',align : 'left',width : 100},

					{display : '计算公式(中文)',name : 'formula_ca',align : 'left'},

					{display : '计算公式(英文)',name : 'formula_en',align : 'left'}

					],
					dataAction : 'server',dataType : 'server',usePager : true,url : 'queryBudgFormula.do?isCheck=false',
					width : '100%',	height:'100%', checkbox : true,pageSize : 10,	rownumbers : true,
					selectRowButtonOnly : true,
					/*toolbar: { items: [
					                {text : '添加（<u>A</u>）',	id : 'add',	click : addFormula,	icon : 'add'},
		                   	]},
		            */
					onSelectRow: function(rowdata, rowindex, value) {
						parentFrameUse().liger.get("formula_id").setValue(rowdata.formula_id);
						parentFrameUse().liger.get("formula_id").setText(rowdata.formula_id + ' '+ rowdata.formula_name);
						parentFrameUse().window.document.getElementById("selectFormula").focus(); 
						setTimeout(function(){
							dialog.close();
						},100)
						
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();

	}
	function addFormula() {
		parent.$.ligerDialog.open({
			url : 'hrp/budg/common/budgformula/budgFormulaAddPage.do?isCheck=false',height : 500,width : 750,title : '添加',
			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
			parentframename: window.name,
			buttons : [ {text : '确定',onclick : function(item, dialog) {dialog.frame.saveBudgFormula();},
				cls : 'l-dialog-btn-highlight'}, {text : '取消',onclick : function(item, dialog) {dialog.close();}
			} ]
		});

	}
	function loadDict() {

		$("#formula_id").ligerTextBox({width : 160});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式编码：</td>
			<td align="left" class="l-table-edit-td"><input
				name="formula_id" type="text" id="formula_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
