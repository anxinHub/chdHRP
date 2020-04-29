<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!-- 绩效计算公式 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var dialog = frameElement.dialog;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		$("#formula_code").ligerTextBox({
			width : 160
		});
		$("#formula_name").ligerTextBox({
			width : 160
		});
		$("#formula_method_chs").ligerTextBox({
			width : 160
		});
		$("#formula_method_eng").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		loadHotkeys();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'formula_code',
			value : liger.get("formula_name").getValue()
		}); 
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '公式编码',
								name : 'formula_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.formula_code + "|"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>"
											+ rowdata.formula_code + "</a>";
								},width: '12%'
							}, {
								display : '公式名称',
								name : 'formula_name',
								align : 'left',width: '20%'
							}, {
								display : '计算公式(中文）',
								name : 'formula_method_chs',
								align : 'left',width: '55%'
							},  {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是";
									}
								},width: '12%'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHpmFormula.do',
					width : '100%',
					height : '100%',
					checkbox : false,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '添加（<u>A</u>）',
							id : 'add',
							click : addFormula,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : deleteFormula,
							icon : 'delete'
						}]
					},
					onDblClickRow : function(rowdata, rowindex, value) {

						parentFrameUse().$("#formula_code").val(rowdata.formula_code);

						parentFrameUse().$("#formula_name").val(rowdata.formula_name);

						parentFrameUse().$("#formula_method_chs").val(rowdata.formula_method_chs);

						dialog.close();
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addFormula);

		hotkeys('D', deleteFormula);

	}

	function addFormula() {
		parent.parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmformula/hpmFormulaAddPage.do?isCheck=false',
			height :$(window).height(),
			width : $(window).width(),
			showMax: true,
			parentframename : window.name,
			title : '添加',
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmFormula();
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

	function deleteFormula() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.formula_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHpmFormula.do", {
						ParamVo : ParamVo
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
		var parm = "&formula_code=" + vo[0] + "&group_id" + vo[1] + "&hos_id"
				+ vo[2] + "&copy_code" + vo[3]

		parent.parent.$.ligerDialog.open({
			url : 'hrp/hpm/hpmformula/hpmFormulaUpdatePage.do?isCheck=false&' + parm,
			data : {},
			parentframename: window.name,
			height :$(window).height(),
			width : $(window).width(),
			showMax: true,
			title : '修改', 
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveHpmFormula();
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
	
	function saveFormula(obj){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		}
		if(data.length > 1){
			$.ligerDialog.error('请选择单行操作');
			return;
		}
		obj.formula_code = data[0].formula_code;
		obj.formula_name = data[0].formula_name;
		obj.formula_method_chs = data[0].formula_method_chs;
	}
	
	
	function loadDict() {
		//字典下拉框
		autocomplete("#formula_name", "../queryFormula.do?isCheck=false",
				"id", "text", true, true);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr> 
		<td style="display:none">
			<input name="formula_code" type="hidden" id="formula_code" ltype="text" validate="{required:true,maxlength:20}" />  
		  </td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公式名称：</td>
			<td align="left" class="l-table-edit-td"><input name="formula_name" type="text" id="formula_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td><font style="color: red"><-双击选择公式-></font></td>
		</tr>

	</table>

	<div id="maingrid"></div>
</body>
</html>
