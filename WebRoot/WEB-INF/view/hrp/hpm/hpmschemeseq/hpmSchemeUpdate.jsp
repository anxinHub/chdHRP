<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	$(function() {
		$("#formula_method_chs").ligerTextBox({
			disabled : true,
			width : 400
		});
		$("#formula_name").ligerTextBox({
			disabled : true,
			width : 200
		});
		$("#dept_kind_code").ligerTextBox({
			disabled : true,
			width : 160
		});
		$("#dept_code").ligerTextBox({
			disabled : true,
			width : 200
		});
		$("#item_code").ligerTextBox({
			disabled : true,
			width : 160
		});
		$("#formula_code").ligerTextBox({
			disabled : true,
			width : 160
		});

		loadHead();
	});
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				name : 'target_code',
				display : '指标编码',
				align : 'left'
			},

			{
				name : 'target_name',
				display : '指标名称',

				align : 'left'
			}, {
				name : 'nature_name',
				display : '指标性质',

				align : 'left'
			}, {
				name : 'fun_code',
				display : '取值方法',

				align : 'left'
			}, {
				name : 'source_name',
				display : '来源配置',
				align : 'left',
				 render: function (rowdata, rowindex, value){
						return "<a href='#'>"+rowdata.source_name+"</a>";
		           }
			}, ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryTarget2_1.do?isCheck=false&formula_code='+$("#formula_code").val(),
			//delayLoad : 'false',
			width : '100%',
			height : '314',
			selectRowButtonOnly : true,//heightDiff: -10,
			rownumbers : true
		});

		gridManager = $("#maingrid").ligerGetGridManager();

	}

	function onpenformulaCodePage() {

		$.ligerDialog.open({
			url : 'hpmFormulaUpdatePage.do?isCheck=false&formula_code='+$("#formula_code").val(),
			data : {},
			height : 450,
			width : 800,
			title : '计算公式维护',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			 
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveFormula();
					var aaa=dialog.frame.getformulaCode();
					$("#formula_method_chs").val(aaa);
					
					dialog.close(); 
					loadHead();
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
	
	
	 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px; width: 100px">科室分类：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" value="${dept_kind_code }" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" value="${dept_code }" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项目：</td>
			<td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" value="${item_code}" disabled="disabled" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式编码：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text" value="${formula_code }"
				disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td" colspan="2"><input name="formula_name" type="text" id="formula_name" ltype="text" value="${formula_name }"
				disabled="disabled" /></td>
			<td align="left"></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计算公式：</td>
			<td align="left" class="l-table-edit-td" colspan="5"><input name="formula_method_chs" type="text" id="formula_method_chs" ltype="text"
				value="${formula_method_chs }" disabled="disabled" /></td>


			<td align="left" class="l-table-edit-td" style="padding-left: 120px;"><input type="button" ltype="text" value="编辑公式" onclick='onpenformulaCodePage()' /></td>

		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>