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
			name : 'work_cause_code',
			value : $("#work_cause_code").val()
		});
		$("#resultPrint > table > tbody").html("");
		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '作业动因编码',
								name : 'work_cause_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.work_cause_code
											+ "');\" >"
											+ rowdata.work_cause_code + "</a>";
								}
							}, {
								display : '作业动因名称',
								name : 'work_cause_name',
								align : 'left'

							}, {
								display : '说明',
								name : 'work_remark',
								align : 'left'

							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcWorkCauseDict.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					delayLoad:true,
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
						}]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(
								  rowdata.group_id + "|"
								+ rowdata.hos_id + "|"
								+ rowdata.copy_code + "|"
								+ rowdata.work_cause_code);//实际代码中temp替换主键
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open() {
		$.ligerDialog.open({
			url : 'htcWorkCauseDictAddPage.do?isCheck=false',
			height : 200,
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
					dialog.frame.saveWorkCauseDict();
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
				ParamVo.push(
						  this.group_id +"@"
						+ this.hos_id +"@"
						+ this.copy_code +"@"
						+ this.work_cause_code
						);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcWorkCauseDict.do", {
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
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0] + 
		      "&" + "hos_id=" + vo[1] + 
			  "&" + "copy_code=" + vo[2]+ 
		      "&" + "work_cause_code=" + vo[3];
		$.ligerDialog
				.open({
					url : 'htcWorkCauseDictUpdatePage.do?isCheck=false&'+parm,
					data : {},
					height : 200,
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
							dialog.frame.saveWorkCauseDict();
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
		$("#work_cause_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">作业动因编码：</td>
			<td align="left" class="l-table-edit-td"><input
				name="work_cause_code" type="text" id="work_cause_code" ltype="text"  /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
