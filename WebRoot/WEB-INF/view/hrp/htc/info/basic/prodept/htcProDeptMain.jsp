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

		loadHead(null);
		loadDict();

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({name : 'proj_dept_code',value : $("#proj_dept_code").val()});
		grid.options.parms.push({name : 'natur_code',value : liger.get("natur_code").getValue()});
        grid.options.parms.push({name : 'is_stop',value:liger.get("is_stop").getValue()});
		grid.options.parms.push({name : 'is_last',value:liger.get("is_last").getValue()});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns: [{
						display: '核算科室编码',
						name: 'proj_dept_code',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							return "<a href='#' onclick=\"openUpdate('" 
							    + rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
								+ rowdata.proj_dept_id
								+ "');\" >" +
								rowdata.proj_dept_code + "</a>";
						}
					}, {
						display: '核算科室名称',
						name: 'proj_dept_name',
						align: 'left'
					}, {
						display: '科室性质',
						name: 'natur_name',
						align: 'left'
					}, {
						display: '是否停用',
						name: 'is_stop',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							if (rowdata.is_stop == 1) {
								return "是";
							} else {
								return "否";
							}
						}
					}, {
						display: '是否末级',
						name: 'is_last',
						align: 'left',
						render: function(rowdata, rowindex, value) {
							if (rowdata.is_last == 1) {
								return "是";
							} else {
								return "否";
							}
							return "";
						}
					}],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcProDept.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad:true,
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
						},{
							text : '同步',
							id : 'synchro',
							click : synchro,
							icon : 'bluebook'
						},{
							line : true
						},]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(
								  rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
								+ rowdata.proj_dept_id);//实际代码中temp替换主键
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcProDeptAddPage.do?isCheck=false',
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
					dialog.frame.saveDeptDict();
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
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = []; 
			$(data).each(function() {

				ParamVo.push(
						this.group_id + "@"
					+	this.hos_id + "@"
					+	this.copy_code + "@"
					+	this.proj_dept_id
						);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcProDept.do", {
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

	function synchro(){
        /* 
                          此同步功能是将科室字典的临床医技科室同步到核算科室字典里,计算时是按照核算科室核算
         */
     	$.ligerDialog.confirm('是否确定同步?', function(yes) {
     		if (yes) {
	   			ajaxJsonObjectByUrl("synchroHtcProDept.do?isCheck=false",null,function(responseData) {
	   				if (responseData.state == "true") {
	   					query()
	   				}
	   			  });
     		}
     	})
    
	}
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "proj_dept_id=" + vo[3]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcProDeptUpdatePage.do?isCheck=false&'+parm,
			data : {},
			height :300,
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
					dialog.frame.saveDeptDict();
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
	
	function loadDict(){

		autocomplete("#natur_code","../../base/queryHtcDeptNatur.do?isCheck=false", "id", "text", true,true);
		autocomplete("#is_stop","../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true,true);
		autocomplete("#is_last","../../base/queryHtcYearOrNo.do?isCheck=false", "id", "text", true,true);

	    $("#proj_dept_code").ligerTextBox({width:160});

	} 

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算科室：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" style="width:160px;"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室性质：</td>
			<td align="left" class="l-table-edit-td"><input
				name="natur_code" type="text" id="natur_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否末级：</td>
			<td align="left" class="l-table-edit-td">
			<input name="is_last" type="text" id="is_last" ltype="text"/></td>
			<td align="left"></td>
		</tr>

		<tr>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
