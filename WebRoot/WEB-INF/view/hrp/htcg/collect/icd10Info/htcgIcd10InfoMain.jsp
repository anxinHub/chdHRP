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
		grid.options.parms.push({
			name : 'mr_no',
			value : $("#mr_no").val()
		});
		grid.options.parms.push({
			name : 'in_hos_no',
			value : $("#in_hos_no").val()
		});
		grid.options.parms.push({
			name : 'patient_name',
			value : $("#patient_name").val()
		});
		grid.options.parms.push({
			name : 'icd10_type_code',
			value : $("#icd10_type_code").val()
		});
		grid.options.parms.push({
			name : 'icd10_code',
			value : $("#icd10_code").val()
		});
		grid.options.parms.push({
			name : 'outcome_code',
			value : liger.get("outcome_code").getValue()
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
								}
							}, {
								display : '住院号',
								name : 'in_hos_no',
								align : 'left'
							}, {
								display : '姓名',
								name : 'patient_name',
								align : 'left'
							}, {
								display : '诊断类别',
								name : 'icd10_type_name',
								align : 'left'
							}, {
								display : '诊断编码(ICD-10)',
								name : 'icd10_code',
								align : 'left'
							}, {
								display : '诊断名称',
								name : 'icd10_name',
								align : 'left'
							}, {
								display : '诊断序列',
								name : 'icd10_seq',
								align : 'left'
							}, {
								display : '转归',
								name : 'outcome_name',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcgIcd10Info.do',
					width : '100%',
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
						}, {
							text : '导入',
							id : 'import',
							click : importData,
							icon : 'up'
						} ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function add_open(){
		$.ligerDialog.open({
			url : 'htcgIcd10InfoAddPage.do?isCheck=false',
			height : 330,
			width : 470,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveIcd10Info();
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
						+ this.group_id + "@"
						+ this.hos_id + "@"
						+ this.copy_code + "@"
						+ this.mr_no + "@" 
						+ this.in_hos_no
						);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcgIcd10Info.do", {
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
	function importData() {
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
				"name" : "icd10_type_code_name",
				"display" : "诊断类别",
				"width" : "100",
				"require" : true
			}, {
				"name" : "icd10_code_name",
				"display" : "诊断编码",
				"width" : "100",
				"require" : true
			}, {
				"name" : "icd10_seq",
				"display" : "诊断序列",
				"width" : "100",
				"require" : true
			}, {
				"name" : "outcome_code_name",
				"display" : "转归",
				"width" : "100",
				"require" : true
			} ]
		};
		importSpreadView("hrp/htcg/collect/icd10Info/importHtcgIcd10Info.do?isCheck=false",para);

	}
	function openUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1]
		+ "&" + "copy_code=" + vo[2] + "&" +  "mr_no=" + vo[3]+"&" + "in_hos_no=" + vo[4]
		//实际代码中&temp替换主键
		$.ligerDialog.open({
			url : 'htcgIcd10InfoUpdatePage.do?isCheck=false&'+ parm,
			data : {},
			height : 300,
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
					dialog.frame.updateIcd10Info();
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
		autocomplete("#outcome_code","../../base/queryHtcgOutcomeDict.do?isCheck=false","id","text",true,true);

		$("#mr_no").ligerTextBox({
			width : 160
		});
		$("#in_hos_no").ligerTextBox({
			width : 160
		});
		$("#patient_name").ligerTextBox({
			width : 160
		});
		$("#icd10_type_code").ligerTextBox({
			width : 160
		});
		$("#icd10_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病案号码：</td>
			<td align="left" class="l-table-edit-td"><input name="mr_no"
				type="text" style="width: 160px;" id="mr_no" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				住院号码：</td>
			<td align="left" class="l-table-edit-td"><input name="in_hos_no"
				type="text" style="width: 160px;" id="in_hos_no" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				患者姓名：</td>
			<td align="left" class="l-table-edit-td"><input
				name="patient_name" type="text" style="width: 160px;"
				id="patient_name" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">诊断类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="icd10_type_code" type="text" id="icd10_type_code" ltype="text"  /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				诊断名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="icd10_code" type="text" style="width: 160px;" id="icd10_code"
				ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				转归名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="outcome_code" type="text" id="outcome_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
