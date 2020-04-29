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
<style>
input {
	border: 1px solid #aecaf0;
	height: 20px;
}
</style>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		var $wdate = document.getElementsByClassName("Wdate");
		// 给每个wdatede表单添加聚焦事件，加载日期框
		for (var i = 0, len = $wdate.length; i < len; i++) {
			$wdate[i].onfocus = function() {
				WdatePicker({
					isShowClear : true,
					readOnly : false,
					dateFmt : 'yyyy-MM-dd'
				});
			}
		}
		loadDict() //加载下拉框
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
			name : 'level_code',
			value : liger.get("level_code").getValue()
		});
		grid.options.parms.push({
			name : 'app_date',
			value : $("#app_date").val()
		});
		grid.options.parms.push({
			name : 'end_app_date',
			value : $("#end_app_date").val()
		});
		grid.options.parms.push({
			name : 'type_code',
			value : liger.get("type_code").getValue()
		});
		grid.options.parms.push({
			name : 'proj_name',
			value : $("#proj_name").val()
		});
		grid.options.parms.push({
			name : 'proj_state',
			value : liger.get("proj_state").getValue()
		});
		grid.options.parms.push({
			name : 'con_emp_id',
			value : liger.get("con_emp_id").getValue()
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
			columns : [
	           {display : '项目编码',	name : 'proj_code',	align : 'left',	width : 100}, 
	           {display : '项目名称',	name : 'proj_name',	align : 'left',	width : 200}, 
	           {display : '项目类型', name : 'type_name',	align : 'left',	width : 80}, 
	           {display : '项目级别',	name : 'level_name',align : 'left',	width : 80}, 
	           {display : '项目负责人',name : 'con_emp_name',align : 'left',	width : 80}, 
	           {display : '财务负责人',name : 'acc_emp_name',align : 'left',	width : 80}, 
	           {display : '填报部门',	name : 'dept_name', align : 'left',	width : 100}, 
	           {display : '填报人',name : 'app_emp_name',	align : 'left',	width : 80}, 
	           {display : '填报日期', name : 'app_date',align : 'left',	width : 80}, 
	           {display : '立项日期',	name : 'set_up_date',align : 'left',	width : 80}, 
	           {display : '结题日期',	name : 'complete_date',	align : 'left',	width : 80}, 
	           {display : '报销截止日期',name : 'pay_end_date',align : 'left',	width : 80}, 
	           {display : '中止日期',	name : 'sespend_date',align : 'left',	width : 80}, 
	           {display : '项目状态',	name : 'value_name',align : 'left',	width : 80}, 
	           {display : '是否停用',	name : 'is_stop',align : 'center',width : 80,
					render : function(item) {
						if (parseInt(item.is_stop) == 1) {
							return '是';
						}
						return '否';
					}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHosProj.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true, //heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询（<u>Q</u>）',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '结题 ',
					id : 'endProj',
					click : endProj,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '取消结题 ',
					id : 'escEndProj',
					click : escEndProj,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '中止 ',
					id : 'suspendProj',
					click : suspendProj,
					icon : 'down'
				}, {
					line : true
				}, {
					text : '取消中止 ',
					id : 'escSuspendProj',
					click : escSuspendProj,
					icon : 'up'
				} ]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.proj_id);
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//结题
	function endProj() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + '02' + "@"
								+ this.proj_id)
					});
			$.ligerDialog.confirm('确定结题?', function(yes) {
                if (yes) {
				ajaxJsonObjectByUrl("endHosProj.do?isCheck=false", {
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
	//取消结题
	function escEndProj() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.group_id + "@" + this.hos_id + "@"
						+ this.copy_code + "@" + '01' + "@"
						+ this.proj_id)
			});
			$.ligerDialog.confirm('确定取消结题?', function(yes) {
                if (yes) {
					ajaxJsonObjectByUrl("escEndHosProj.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			})
		}

	}
	// 中止
	function suspendProj() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.group_id + "@" + this.hos_id + "@"
						+ this.copy_code + "@" + '03' + "@"
						+ this.proj_id)
			});
			$.ligerDialog.confirm('确定中止?', function(yes) {
                if (yes) {
					ajaxJsonObjectByUrl("suspendHosProj.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			})
		}

	}
	//取消中止
	function escSuspendProj() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(this.group_id + "@" + this.hos_id + "@"
						+ this.copy_code + "@" + '01' + "@"
						+ this.proj_id)
			});
			$.ligerDialog.confirm('确定取消中止?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("escSuspendHosProj.do?isCheck=false", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			})
		}

	}

	function openUpdate(obj) {
		$.ligerDialog.open({
			url : 'hosProjUpdatePage.do?isCheck=false&proj_id=' + obj,
			height : 570,
			width : 700,
			title : '项目详情',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}

	function loadDict() {
		//字典下拉框
		autocomplete("#level_code","../../../sys/queryProjLevelDict.do?isCheck=false", "id","text", true, true); //项目级别
		
		autocomplete("#con_emp_id",	"../../../sys/queryEmpDict.do?isCheck=false", "id", "text",	true, true); //项目负责人
		
		autocomplete("#type_code","../../../sys/queryProjTypeDict.do?isCheck=false", "id","text", true, true); //项目类别
	
		autocomplete("#proj_state","../../qureyProjStateSelect.do?isCheck=false", "id", "text",	true, true);
		
		$("#is_stop").ligerComboBox({width :80,data:[{text:'否',	id :'0'}, {text :'是',	id : '1'}],value : '0'})
		
		$("#type_code").ligerTextBox({width:180});
		$("#con_emp_id").ligerTextBox({width:203});
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
			<td align="left" class="l-table-edit-td"><input name="proj_id"
				type="hidden" id="proj_id" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
			<td align="left" class="l-table-edit-td"><input name="proj_name" id="proj_name" style="width: 180px" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目时间：</td>
			<td>
            	<table>
            		<tr>
            			<td align="left" class="l-table-edit-td"><input name="app_date" id="app_date" class="Wdate" style="width: 90px">至</td>
            			<td align="left" class="l-table-edit-td"><input name="end_app_date" id="end_app_date" class="Wdate" style="width: 90px"></td>
            		</tr>
            	</table>
            </td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目状态：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_state" id="proj_state" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目类型：</td>
			<td align="left" class="l-table-edit-td"><input name="type_code"id="type_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目负责人：</td>
			<td align="left" class="l-table-edit-td"><input name="con_emp_id" id="con_emp_id" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目级别：</td>
			<td align="left" class="l-table-edit-td"><input name="level_code" id="level_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td"><input name="is_stop"	id="is_stop" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
