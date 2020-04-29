<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() { 
		loadDict();//加载下拉框
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
			name : 'store_id',
			value : liger.get("store_id").getValue().split(",")[0],
		});
		grid.options.parms.push({
			name : 'emp_id',
			value : liger.get("emp_id").getValue() 
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '仓库编码',
								name : 'store_code',
								align : 'left',
								render : function(rowdata, index, value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+ rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.store_id+"|"+rowdata.emp_id+"')>"
											+ rowdata.store_code + "</a>";
								}
							},
							{
								display : '仓库名称',
								name : 'store_name',
								align : 'left'
							},
							{
								display : '职工编码',
								name : 'emp_code',
								align : 'center',
								 
							},
							{
								display : '职工名称',
								name : 'emp_name',
								align : 'center',
								 
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryStoreEmp.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,
					heightDiff : -10,
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
						} 
						  ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id+"|"+ 
								   rowdata.hos_id+"|"+
								   rowdata.copy_code+"|"+
								   rowdata.store_id+"|"+
								   rowdata.emp_id
								  );
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {
		$.ligerDialog.open({
			url : 'matStoreEmpSetAddPage.do?isCheck=false',
			height : 300,
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
					dialog.frame.saveMatStoreSet();
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
				ParamVo.push(this.group_id+"@"+
						     this.hos_id+"@"+
						     this.copy_code+"@"+
						     this.store_id+"@"+
						     this.emp_id 
						)
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatStoreEmp.do?isCheck=true", {
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
 

	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"store_id="+vo[3]   +"&"+ 
			"emp_id="+vo[4] 
		
		$.ligerDialog.open({
			url : 'matStoreEmpSetUpdatePage.do?isCheck=false&' + parm,
			height : 300,
			width : 500,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveMatStoreSet();
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
		autocomplete("#store_id","../../../../queryMatStore.do?isCheck=false","id","text",true,true);
   	 
    	autocomplete("#emp_id","../../../../queryMatStockEmp.do?isCheck=false","id","text",true,true);   
	}
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove); 
	}
 

	 
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓库：</td>
			<td align="left" class="l-table-edit-td">
				<input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工:</td>
			<td align="left" class="l-table-edit-td">
				<input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
 
</body>
</html>
