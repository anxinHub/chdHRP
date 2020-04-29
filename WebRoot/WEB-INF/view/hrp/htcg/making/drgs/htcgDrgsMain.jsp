<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'drgs_code',
			value : $("#drgs_code").val()
		});
		grid.options.parms.push({
			name : 'drgs_type_code',
			value : liger.get("drgs_type_code").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '病种编码',
								name : 'drgs_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.group_id + "|"
				                            + rowdata.hos_id + "|"
				                            + rowdata.copy_code + "|"
											+ rowdata.drgs_code + "');\" >"
											+ rowdata.drgs_code + "</a>";
								}
							}, {
								display : '病种名称',
								name : 'drgs_name',
								align : 'left'
							}, {
								display : '病种分类',
								name : 'drgs_type_name',
								align : 'left'
							}, {
								display : '病种描述',
								name : 'drgs_note',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcgDrgs.do',
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
							text : '添加',
							id : 'add',
							click : add_open,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : remove,
							icon : 'delete'
						}, {
							line : true
						},{
							text : '导入',
							id : 'import',
							click : imp,
							icon : 'up'
						}, {
							line : true
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {
		$.ligerDialog.open({
			url : 'htcgDrgsAddPage.do?isCheck=false',
			height : 300,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDrgs();
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
			$(data).each(function() {
				ParamVo.push(this.drgs_code);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgDrgs.do", {
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
		var para = {
			"column" : [ {
				"name" : "drgs_code",
				"display" : "病种编码",
				"width" : "300",
				"require" : true
			}, {
				"name" : "drgs_name",
				"display" : "病种名称",
				"width" : "300",
				"require" : true
			}, {
				"name" : "drgs_type_code",
				"display" : "病种分类编码",
				"width" : "300",
				"require" : true
			}, {
				"name" : "drgs_type_name",
				"display" : "病种分类名称",
				"width" : "300",
				"require" : true
			}, {
				"name" : "drgs_note",
				"display" : "病种分类描述",
				"width" : "300",
				"require" : false
			} ]
		};
		importSpreadView("hrp/htcg/making/drgs/importHtcgDrgs.do?isCheck=false",para);
	}

	function openUpdate(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "drgs_code=" + vo[3]
		$.ligerDialog.open({
			url : 'htcgDrgsUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 300,
			width : 400,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveDrgs();
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
		autocomplete("#drgs_type_code",
				"../../base/queryHtcgDrgsTypeDict.do?isCheck=false", "id", "text",
				true, true);
		$("#drgs_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种：</td>
			<td align="left" class="l-table-edit-td"><input
				style="width: 160px;" name="drgs_code" type="text" id="drgs_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种分类：</td>
			<td align="left" class="l-table-edit-td"><input
				name="drgs_type_code" type="text" id="drgs_type_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
