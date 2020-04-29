<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
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
			name : 'mr_no',
			value : $("#mr_no").val()
		});
		grid.options.parms.push({
			name : 'in_hos_no',
			value : $("#in_hos_no").val()
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
								display : '病案号',
								name : 'mr_no',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.group_id + "|"
				                            + rowdata.hos_id + "|"
				                            + rowdata.copy_code + "|"
											+ rowdata.mr_no + "|"
											+ rowdata.in_hos_no + "');\" >"
											+ rowdata.mr_no + "</a>";
								},frozen:true,
								  minWidth:"120"
							}, {
								display : '住院号',
								name : 'in_hos_no',
								align : 'left',
								frozen:true,
								minWidth:"120"
							}, {
								display : '姓名',
								name : 'patient_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '性别',
								name : 'patient_sex',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.patient_sex == 1) {
										return '男'
									} else if(rowdata.patient_sex){
										return '女'
									}
								},
								minWidth:"80"
							}, {
								display : '年龄',
								name : 'patient_age',
								align : 'left',
								minWidth:"80"
							}, {
								display : '出生日期',
								name : 'birth_date',
								align : 'left',
								minWidth:"80"
							}, {
								display : '入院日期',
								name : 'in_date',
								align : 'left',
								minWidth:"80"
							}, {
								display : '入院科室',
								name : 'in_dept_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '出院日期',
								name : 'out_date',
								align : 'left',
								minWidth:"80"
							}, {
								display : '出院科室',
								name : 'out_dept_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '医保类型',
								name : 'identity_name',
								align : 'left',
								minWidth:"80"
							},{
								display : '住院天数',
								name : 'in_days',
								align : 'left',
								minWidth:"80"
							}, {
								display : '主任医师',
								name : 'director_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '主治医师',
								name : 'major_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '住院医师',
								name : 'in_hos_name',
								align : 'left',
								minWidth:"80"
							}, {
								display : '转归名称',
								name : 'outcome_name',
								align : 'left',
								minWidth:"80"
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcgMrInfo.do',
					//width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					selectRowButtonOnly : true,
					heightDiff : -10,
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
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcgMrInfoAddPage.do?isCheck=false',
			height : 370,
			width : 900,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveMrInfo();
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
						   +this.hos_id + "@"
						   +this.copy_code + "@"
						   +this.mr_no + "@"
						   +this.in_hos_no
						   );//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgMrInfo.do", {
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
				"name" : "mr_no",
				"display" : "病案号",
				"width" : "100",
				"require" : true
			}, {
				"name" : "in_hos_no",
				"display" : "住院号",
				"width" : "100",
				"require" : true
			}, {
				"name" : "patient_name",
				"display" : "姓名",
				"width" : "100"
			}, {
				"name" : "patient_sex",
				"display" : "性别",
				"width" : "50"
			}, {
				"name" : "patient_age",
				"display" : "年龄",
				"width" : "50"
			}, {
				"name" : "birth_date",
				"display" : "出生日期",
				"width" : "100"
			}, {
				"name" : "identity_code",
				"display" : "医保类型编码",
				"width" : "100"
			},{
				"name" : "identity_name",
				"display" : "医保类型名称",
				"width" : "100"
			}, {
				"name" : "in_date",
				"display" : "入院日期",
				"width" : "100"
			}, {
				"name" : "in_dept_code",
				"display" : "入院科室编码",
				"width" : "100"
			}, {
				"name" : "in_dept_name",
				"display" : "入院科室名称",
				"width" : "100"
			}, {
				"name" : "out_date",
				"display" : "出院日期",
				"width" : "100"
			}, {
				"name" : "out_dept_code",
				"display" : "出院科室编码",
				"width" : "100"
			}, {
				"name" : "out_dept_name",
				"display" : "出院科室名称",
				"width" : "100"
			}, {
				"name" : "in_days",
				"display" : "住院天数",
				"width" : "100"
			}, {
				"name" : "director_name",
				"display" : "主任医师",
				"width" : "100"
			}, {
				"name" : "major_name",
				"display" : "主治医师",
				"width" : "100"
			}, {
				"name" : "in_hos_name",
				"display" : "住院医师",
				"width" : "100"
			}, {
				"name" : "outcome_code",
				"display" : "转归编码",
				"width" : "100"
			}, {
				"name" : "outcome_name",
				"display" : "转归名称",
				"width" : "100"
			} ]
		};
		importSpreadView("hrp/htcg/collect/mrInfo/importHtcgMrInfo.do?isCheck=false", para);

	}

	function openUpdate(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "mr_no=" + vo[3]+"&" + "in_hos_no=" + vo[4]
		
		$.ligerDialog.open({
			url : 'htcgMrInfoUpdatePage.do?isCheck=false&'+ parm,
			data : {},
			height : 370,
			width : 900,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.updateMrInfo();
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
		$("#mr_no").ligerTextBox({
			width : 160
		});
		$("#in_hos_no").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病案号：</td>
			<td align="left" class="l-table-edit-td"><input name="mr_no"
				type="text" style="width: 160px;" id="mr_no" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">住院号：</td>
			<td align="left" class="l-table-edit-td"><input name="in_hos_no"
				type="text" style="width: 160px;" id="in_hos_no" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
