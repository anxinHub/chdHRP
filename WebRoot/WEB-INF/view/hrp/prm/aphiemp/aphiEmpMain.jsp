<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		$("#emp_id").ligerComboBox({width:160 });
		
		
		$("#is_stop").ligerTextBox({
			width : 160
		});
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
			name : 'emp_id',
			value : liger.get("emp_id").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'duty_code',
			value : liger.get("duty_code").getValue()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'dept_kind_code',
			value : liger.get("dept_kind_code").getValue()
		});
		grid.options.parms.push({
			name : 'nature_code',
			value : liger.get("nature_code").getValue()
		});
		if($("#is_dept").prop("checked")){
			grid.options.parms.push({
				name : 'is_dept',
				value : "1"
			});
		}
		if($("#is_acc").prop("checked")){
			grid.options.parms.push({
				name : 'is_acc',
				value : "1"
			});
		}

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '职工编码',
								name : 'emp_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.emp_id + "|"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>"
											+ rowdata.emp_code + "</a>";
								}
							}, {
								display : '职工名称',
								name : 'emp_name',
								align : 'left'

							}, {
								display : '科室名称',
								name : 'dept_name',
								align : 'left'

							}, {
								display : '职务名称',
								name : 'duty_name',
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
							}, {
								display : '是否参与核算',
								name : 'is_acc',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_acc == 1) {
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
					url : 'queryAphiEmp.do',
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
							text : '生成（<u>A</u>）',
							id : 'initEmp',
							click : initEmp,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : deleteEmp,
							icon : 'delete'
						}, {
							line : true
						}, {
							text : '下载导入模板（<u>L</u>）',
							id : 'downTemplate',
							click : templateEmp,
							icon : 'down'
						}, {
							line : true
						}, {
							text : '导入（<u>I</u>）',
							id : 'import',
							click : importEmp,
							icon : 'up'
						}, {
							line : true
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.emp_id + "|" + rowdata.group_id
								+ "|" + rowdata.hos_id + "|"
								+ rowdata.copy_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
		formatTrueFalse();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addEmp);

		hotkeys('D', deleteEmp);

		hotkeys('L', templateEmp);

		hotkeys('I', importEmp);

	}

	function initEmp() {
		
		$.ligerDialog.open({url: 'aphiEmpInitPage.do?isCheck=false', height: 230,width: 400, title:'生成',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
			data: {},
			buttons: [ 
			           { text: '确定', onclick: function (item, dialog) { dialog.frame.initAphiEmp(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
			           ] 
			});
	}

	function deleteEmp() {

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
										this.emp_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAphiEmp.do", {
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

	function templateEmp() {

		location.href = "downTemplateEmp.do?isCheck=false"

	}

	function importEmp() {

		$.ligerDialog.open({
			url : 'aphiEmpImportPage.do?isCheck=false',
			height : 430,
			width : 760,
			isResize : true,
			title : '导入'
		});

	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&emp_id=" + vo[0] + 
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3]

		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'empUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 330,
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
					dialog.frame.saveEmp();
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
		autocomplete("#dept_kind_code", "../queryPrmDeptKind.do?isCheck=false","id", "text", true, true);

		autocomplete("#nature_code", "../queryPrmDeptNature.do?isCheck=false","id", "text", true, true);

		autocomplete("#dept_code", "../queryPrmDeptDict.do?isCheck=false","id", "text", true, true);

		autocomplete("#duty_code", "../queryPrmEmpDuty.do?isCheck=false", "id","text", true, true);

		autocomplete("#emp_id", "../queryPrmEmpDict.do?isCheck=false", "id","text", true, true);

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室分类：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
			<td align="left" class="l-table-edit-td">
				<input name="nature_code" type="text" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><input type="checkbox" checked="checked" id="is_dept"/></td>
			<td align="left" class="l-table-edit-td">
				是否维护科室
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><input type="checkbox" checked="checked" id="is_acc"/></td>
			<td align="left" class="l-table-edit-td">
				是否参与核算
			</td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
