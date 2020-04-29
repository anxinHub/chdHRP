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
.tree {
	width: 360px;
	height: 480px;
	position: relative;
	overflow: auto;
}
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var menu;
	var actionNodeID;
	var tree_code;

	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 280
		});
		loadDict();

		loadHead(null); //加载数据
		loadTree();
	

	});
	/* 设置树形菜单 */
	function onSelect(note) {
		tree_code = note.data.code;
		query(note.data.code);
	}
	//是否存在指定变量 
	function isExitsVariable(variableName) {
		try {
			if (typeof (variableName) == "object") {
				return false;
			} else if (typeof (variableName) == "undefined") {
				return false;
			} else if (variableName == "undefined") {
				return false;
			} else {
				return true;
			}
		} catch (e) {
		}
		return false;
	}
	//查询
	function query(code) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		if (isExitsVariable(code)) {
			grid.options.parms.push({name:'dept_code',value:code});
		} else {
			grid.options.parms.push({name:'dept_code',value:$("#dept_code").val()});
		}

		grid.options.parms.push({name :'type_code',value:liger.get("type_code").getValue()});
		grid.options.parms.push({name :'natur_code',value:liger.get("natur_code").getValue()});
		grid.options.parms.push({name :'dept_level',value:liger.get("dept_level").getValue()});
		grid.options.parms.push({name :'is_last',value : liger.get("is_last").getValue()});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '部门编码',
								name : 'dept_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.dept_code + "|"
											+ rowdata.is_last + "')>"
											+ rowdata.dept_code + "</a>";
								}
							},
							{
								display : '部门名称',
								name : 'dept_name',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return formatSpace(rowdata.dept_name,
											rowdata.dept_level - 1);
								}

							}, {
								display : '部门类型',
								name : 'type_name',
								align : 'left'
							}, {
								display : '部门性质',
								name : 'natur_name',
								align : 'left'
							},{
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							}, {
								display : '是否末级',
								name : 'is_last',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_last == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcDeptDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						}, {
							text : '批量管理',
							id : 'manage',
							click : manage,
							icon : 'add'
						}, {
							line : true
						}]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|" + rowdata.hos_id
								+ "|" + rowdata.dept_id + "|"
								+ rowdata.dept_code +"|" + rowdata.is_last);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function manage(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id+ "@" + this.dept_id);
					});
			$.ligerDialog
					.open({
						url : 'htcDeptDictManagePage.do?isCheck=false&ParamVo='+ ParamVo.toString(),
						data : {},
						height : 300,
						width : 500,
						title : '批量管理',
						modal : true,
						showToggle : false,
						showMax : false,
						showMin : false,
						isResize : true,
						buttons : [ {
							text : '确定',
							onclick : function(item, dialog) {
								dialog.frame.saveDeptDictManage();
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

	
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&hos_id=" + vo[1] + "&dept_id="
				+ vo[2] + "&tree_code=" + tree_code+"&is_last="+vo[4];
		$.ligerDialog.open({
			url : 'htcDeptDictUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 500,
			width : 450,
			title : '查看',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			data : {
				name : vo[3]
			},

			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.save();
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
	function loadDict() {
		
		$("#dept_code").ligerTextBox({width:160});
		
		autocomplete("#type_code", "../../base/queryHtcDeptType.do?isCheck=false","id", "text", true, true);

		autocomplete("#natur_code","../../base/queryHtcDeptNatur.do?isCheck=false", "id", "text",true, true);
		
		autocomplete("#dept_level", "../../base/queryHtcDeptLevel.do?isCheck=false", "id","text", true, true);

		autocomplete("#is_last", "../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true, true);

	}

	function loadTree() {
		ajaxJsonObjectByUrl("queryHtcDeptDictByTree.do?isCheck=false", {},
				function(responseData) {
					if (responseData != null) {
						tree = $("#tree").height($(window).height() - 60)
								.ligerTree(
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
											nodeWidth : 240,
											slide : false,
											onContextmenu : function(node, e) {
												actionNodeID = ""
														+ node.data.group_id
														+ "|"
														+ node.data.hos_id
														+ "|"
														+ node.data.dept_id;
												menu.show({
													top : e.pageY,
													left : e.pageX
												});
												return false;
											}
										});
						treeManager = $("#tree").ligerGetTreeManager();
						treeManager.collapseAll(); //全部收起
					}
				});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<div id="layout1">
		<div position="left" title="部门">
			<h2>
				<font id="font1">编码规则：<font id="font2" color="red">"${rules_view}"</font></font>
			</h2>
			<div class="tree">
				<ul class="ztree" id="tree"></ul>
			</div>
		</div>
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">部门：</td>
					<td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">部门类型：</td>
					<td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;" style="display: none">部门性质：</td>
					<td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" ltype="text"/></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;" style="display: none">级次：</td>
					<td align="left" class="l-table-edit-td"><input name="dept_level" type="text" id="dept_level" ltype="text" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否末级：</td>
					<td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text" validate="{required:true}"/></td>
				</tr>
			</table>

			<div id="maingrid"></div>
		</div>

	</div>
</body>
</html>
