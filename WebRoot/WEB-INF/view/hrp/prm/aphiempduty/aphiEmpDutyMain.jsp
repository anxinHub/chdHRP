<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<jsp:include page="${path}/inc.jsp" />

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
		grid.options.parms.push({
			name : 'duty_code',
			value : liger.get("duty_name").getValue()
		});

		grid.options.parms.push({
			name : 'is_stop',
			value : liger.get("is_stop").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '职务编码',
				name : 'duty_code',
				align : 'left',

			}, {
				display : '职务名称',
				name : 'duty_name',
				align : 'left'

			}, {
				display : '备注',
				name : 'duty_note',
				align : 'left'

			}, {
				display : '是否停用',
				name : 'is_stop',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_stop == 1) {
						return "是";
					} else {
						return "否";
					}
					return "";
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAphiEmpDuty.do',
			width : '100%',
			height : '100%',
			checkbox : true,
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
					click : addEmpDuty,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '删除（<u>D</u>）',
					id : 'delete',
					click : deleteEmpDuty,
					icon : 'delete'
				}, {
					line : true
				},

				{
					text : '下载导入模板（<u>L</u>）',
					id : 'downTemplate',
					click : templateEmpDuty,
					icon : 'down'
				}, {
					line : true
				}, {
					text : '导入（<u>I</u>）',
					id : 'import',
					click : importEmpDuty,
					icon : 'up'
				}, {
					line : true
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		formatTrueFalse();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addEmpDuty);

		hotkeys('D', deleteEmpDuty);

		hotkeys('L', templateEmpDuty);

		hotkeys('I', importEmpDuty);

	}

	function addEmpDuty() {

		$.ligerDialog.open({
			url : 'aphiEmpDutyAddPage.do?isCheck=false',
			height : 250,
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
					dialog.frame.saveEmpDuty();
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

	function deleteEmpDuty() {
		var data = gridManager.getCheckedRows();

		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {

			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(	this.group_id + "@" + 
										this.hos_id + "@" + 
										this.copy_code + "@" + 
										this.duty_code)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAphiEmpDuty.do", {
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

	function templateEmpDuty() {

		location.href = "downTemplate.do?isCheck=false";

	}

	function importEmpDuty() {

		$.ligerDialog.open({
			url : 'empDutyImportPage.do?isCheck=false',
			height : 430,
			width : 760,
			isResize : true,
			title : '导入'
		});
	}

	function openUpdate(obj) {
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'empDutyUpdatePage.do?isCheck=false&duty_code=' + obj,
			data : {},
			height : 250,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveEmpDuty();
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
	function loadDict() {
		//字典下拉框
		autocomplete("#duty_name", "../queryPrmEmpDuty.do?isCheck=false", "id","text", true, true);

		$("#is_stop").ligerComboBox({
			width : 160
		});

		$("#duty_name").ligerComboBox({
			width : 160
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="duty_name" type="text" id="duty_name" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
