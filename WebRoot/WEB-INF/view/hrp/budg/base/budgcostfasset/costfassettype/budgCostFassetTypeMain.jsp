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
	width: 230px;
	height: 485px;
	border: 1px solid #ccc;
	overflow: auto;
}
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var menu;
	var actionNodeID;
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 235
		});
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);

		$("#ass_type_code").ligerTextBox({
			width : 160
		});
		$("#is_last").ligerTextBox({
			width : 160
		});
		$("#type_level").ligerTextBox({
			width : 160
		});
		$("#ass_naturs").ligerTextBox({
			width : 160
		});
		$("#super_code").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		loadTree();
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 120,
			items : [ {
				text : '增加',
				click : itemclick,
				id : 'add',
				icon : 'add'
			}, {
				text : '修改',
				click : update
			}, {
				line : true
			}, {
				text : '删除',
				id : 'delete',
				click : delete_tree
			} ]
		});
		
		$("#ass_type_code").change(function(){
			query("");
		})
		$("#super_code").change(function(){
			query("");
		})
		$("#type_level").change(function(){
			query("");
		})
		$("#ass_naturs").change(function(){
			query("");
		})
		$("#is_last").change(function(){
			query("");
		})
		$("#is_stop").change(function(){
			query("");
		})
		
		
	});

	/* 设置树形菜单 */
	function onSelect(note) {

		query(note.data.code);
	}
	function loadTree() {
		ajaxJsonObjectByUrl("queryAssTypeDictByTree.do?isCheck=false", {},
				function(responseData) {
					if (responseData != null) {
						tree = $("#tree").ligerTree(
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
									nodeWidth : 250,
									slide : false,
									onContextmenu : function(node, e) {
										actionNodeID = "" + node.data.group_id
												+ "|" + node.data.hos_id + "|"
												+ node.data.copy_code + "|"
												+ node.data.id;
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
	//是否存在指定变量 
	function isExitsVariable(variableName) {
		try {
			if (typeof (variableName) == "object") {
				return false;
			} else if (typeof (variableName) == "undefined") {
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
			grid.options.parms.push({
				name : 'ass_type_code',
				value : code
			});
		} else {
			grid.options.parms.push({
				name : 'ass_type_code',
				value : $("#ass_type_code").val()?$("#ass_type_code").val():""
			});
		}
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'is_last',
			value : $("#is_last").val()
		});
		grid.options.parms.push({
			name : 'type_level',
			value : $("#type_level").val()
		});
		grid.options.parms.push({
			name : 'ass_naturs',
			value : liger.get("ass_naturs").getValue()
		});
		grid.options.parms.push({
			name : 'super_code',
			value : liger.get("super_code").getValue()
		});
		
		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '分类编码',
								name : 'ass_type_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.ass_type_id + "')>"
											+ rowdata.ass_type_code + "</a>";
								}
							}, {
								display : '分类名称',
								name : 'ass_type_name',
								align : 'left'
							}, {
								display : '上级分类',
								name : 'super_code',
								align : 'left'
							}, {
								display : '是否末级分类',
								name : 'is_last',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_last == 0) {
										return "否";
									} else {
										return "是"
									}
								}
							}, {
								display : '分类级别',
								name : 'type_level',
								align : 'left'
							}, {
								display : '性质编码',
								name : 'ass_naturs',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.ass_naturs == '01') {
										return "专用设备";
									} else if (rowdata.ass_naturs == '02') {
										return "一般设备";
									} else if (rowdata.ass_naturs == '03') {
										return "房屋及建筑物";
									} else if (rowdata.ass_naturs == '04') {
										return "其他固定资产";
									} else {
										return "无形资产";
									}
								}
							}, {
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
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryBudgCostFassetType.do?isCheck=false',
					width : '100%',
					height : '100%',
					delayLoad :true,
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '查询',id : 'search',	click : query,icon : 'search'},
							{line : true}, 
							{text : '添加',id : 'add',click : itemclick,icon : 'add'},
							{line : true}, 
							{text : '删除',id : 'delete',click : itemclick,icon : 'delete'}, 
							{line : true}, 
							{text : '打印',id : 'print',	click : printDate,icon : 'print'},
							{line : true}, 
							{text : '下载导入模板',id : 'downTemplate',click : itemclick,icon : 'down'	}, 
							{line : true}, 
							{text : '导入',id : 'import',click : itemclick,icon : 'up'} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'budgCostFassetTypeAddPage.do?isCheck=false',
					height : 300,
					width : 500,
					title : '<h2>编码规则：<font color="red">' + "${rules_view}"
							+ '</font></h2>',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveBudgCostFassetType();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.ass_type_id)
							});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteBudgCostFassetType.do?isCheck=false", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
									loadTree();
								}
							});
						}
					});
				}
				return;
			case "import":
				$.ligerDialog.open({
					url : 'budgCostFassetTypeImportPage.do?isCheck=false',
					height : 500,
					width : 800,
					title : '导入',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true
				});
			case "export":
				return;
			case "downTemplate":
				location.href = "downTemplate.do?isCheck=false";
				return;
			}
		}

	}
	function update(item) {
		openUpdate(actionNodeID);
	}
	function delete_tree(item) {
		var vo = actionNodeID.split("|");
		var ParamVo = [];
		ParamVo.push(vo[0] + "@" + vo[1]
				+ "@" + vo[2] + "@"
				+ vo[3])
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("deleteBudgCostFassetType.do?isCheck=false", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						loadTree();
					}
				});
			}
		});
	}
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&copy_code="
				+ vo[2] + "&ass_type_id=" + vo[3];
		$.ligerDialog.open({
			url : 'budgCostFassetTypeUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 300,
			width : 500,
			title : '<h2>编码规则：<font color="red">' + "${rules_view}"
					+ '</font></h2>',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveBudgCostFassetType();
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
	
	//打印数据
	function printDate(){
		if(grid.getData().length==0){
			$.ligerDialog.error("无打印数据！");
			return;
		}
	    
    	grid.options.parms=[];
		
    	grid.options.parms.push({name : 'ass_type_code', value : $("#ass_type_code").val()});

		grid.options.parms.push({name : 'is_last',value : $("#is_last").val()});
		grid.options.parms.push({name : 'type_level',value : $("#type_level").val()});
		grid.options.parms.push({name : 'ass_naturs',value : liger.get("ass_naturs").getValue()});
		grid.options.parms.push({name : 'super_code',value : liger.get("super_code").getValue()});
		
		grid.options.parms.push({name : 'is_stop',value : $("#is_stop").val()});

        var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		var printPara={
   			headCount:2,
   			title:"固定资产分类信息",
   			type:3,
   			columns:grid.getColumns(1)
   		};
   		ajaxJsonObjectByUrl("queryBudgCostFassetType.do?isCheck=false", selPara, function(responseData) {
   			printGridView(responseData,printPara);
    	});
	}
	function loadDict() {
		var param = {
            	query_key:''
        };
		//字典下拉框
		autocomplete("#super_code",
				"../../../../ass/queryAssTypeSuperCode.do?isCheck=false", "id", "text",
				true, true, param, true);
		autocomplete("#ass_naturs", "../../../../ass/queryAssNaturs.do?isCheck=false", "id",
				"text", true, true, param, true);
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="left" title="资产类别">
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
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">分类编码：</td>
					<td align="left" class="l-table-edit-td"><input
						name="ass_type_code" type="text" id="ass_type_code" ltype="text"
						validate="{required:true,maxlength:20}"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">上级分类：</td>
					<td align="left" class="l-table-edit-td"><input
						name="super_code" type="text" id="super_code" ltype="text"
						validate="{maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">分类级别：</td>
					<td align="left" class="l-table-edit-td"><select
						id="type_level" name="type_level">
							<option value=""></option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
					</select></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">性质编码：</td>
					<td align="left" class="l-table-edit-td"><input
						name="ass_naturs" type="text" id="ass_naturs" ltype="text"
						validate="{maxlength:20}" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否末级分类：</td>
					<td align="left" class="l-table-edit-td"><select id="is_last"
						name="is_last">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">是否停用：</td>
					<td align="left" class="l-table-edit-td"><select id="is_stop"
						name="is_stop">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
					<td align="left"></td>
				</tr>

			</table>

			<div id="maingrid"></div>
		</div>

	</div>
</body>
</html>
