<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">

	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		$("#dept_code").ligerTextBox({
			width : 160
		});
		$("#dept_name").ligerTextBox({
			width : 160
		});
		$("#is_stop").ligerTextBox({
			width : 160
		});
		loadHotkeys();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'dept_code',
			value : liger.get("dept_code").getValue(),
		});

		grid.options.parms.push({
			name : 'is_stop',
			value : $("#is_stop").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值
	function f_getWhere() {
		if (!grid)
			return null;
		var clause = function(rowdata, rowindex) {

			if ($("#dept_code").val() != "") {
				return rowdata.dept_code.indexOf($("#dept_code").val()) > -1;
			}
			 
			if ($("#is_stop").val() != "") {
				return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;
			}
		};
		return clause;
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [

							{
								display : '科室编码',
								name : 'dept_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href=javascript:openUpdate('"
											+ rowdata.dept_id + "|"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "')>"
											+ rowdata.dept_code + "</a>";
								}
							}, {
								display : '科室名称',
								name : 'dept_name',
								align : 'left'
							}, {
								display : '科室分类',
								name : 'a',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.dept_kind_name;
								}
							}, {
								display : '科室性质',
								name : 'b',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.nature_name;
								}
							}, {
								display : '是否停用',
								name : 'is_stop',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_stop == 0) {
										return "否";
									} else {
										return "是";
									}
								}
							}, {
								display : '是否参与人均奖',
								name : 'is_avg',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									if (rowdata.is_avg == 0) {
										return "否";
									} else {
										return "是";
									}
								}
							}, {
								display : '科室描述',
								name : 'dept_note',
								align : 'left'
							}, ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryAphiDept_DeptKind_DeptNature.do',
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
							text : '添加（<u>A</u>）',
							id : 'add',
							click : addDept,
							icon : 'add'
						}, {
							line : true
						}, {
							text : '删除（<u>D</u>）',
							id : 'delete',
							click : deleteDept,
							icon : 'delete'
						}, {
							line : true
						}, {
							text : '导出Excel（<u>E</u>）',
							id : 'export',
							click : exportDept,
							icon : 'pager'
						}, {
							line : true
						}, {
							text : '打印（<u>P</u>）',
							id : 'print',
							click : printDept,
							icon : 'print'
						}, {
							line : true
						}, {
							text : '下载导入模板（<u>L</u>）',
							id : 'downTemplate',
							click : templateDept,
							icon : 'down'
						}, {
							line : true
						}, {
							text : '导入（<u>I</u>）',
							id : 'import',
							click : importDept,
							icon : 'up'
						} ]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.dept_id + "|" + rowdata.group_id
								+ "|" + rowdata.hos_id + "|"
								+ rowdata.copy_code);
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', addDept);

		hotkeys('D', deleteDept);

		hotkeys('E', exportDept);

		hotkeys('L', templateDept);

		hotkeys('I', importDept);

		hotkeys('P', printDept);

	}

	function addDept() {

		$.ligerDialog.open({
			url : 'aphiDeptAddPage.do?isCheck=false',
			height : 300,
			width :600,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveAphiDept();
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

	function deleteDept() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + 
									 this.hos_id + "@" + 
									 this.copy_code + "@" + 
									 this.dept_id)
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAphiDept.do", {
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

	function exportDept() {

		//有数据直接导出
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopExportExcel("resultPrint", "导出Excel", "8801 科室字典表集.xls", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara = {

			usePager : false,

			dept_id : $("#dept_id").val(),

			dept_code : $("#dept_code").val(),

			dept_name : $("#dept_name").val(),

			dept_note : $("#dept_note").val(),

			dept_kind_code : $("#dept_kind_code").val(),

			nature_code : $("#nature_code").val(),

			is_avg : $("#is_avg").val(),

			is_stop : $("#is_stop").val()

		};
		ajaxJsonObjectByUrl("queryAphiDept_DeptKind_DeptNature.do", exportPara,
				function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>";
						trHtml += "<td>" + item.dept_id + "</td>";
						trHtml += "<td>" + item.dept_code + "</td>";
						trHtml += "<td>" + item.dept_name + "</td>";
						trHtml += "<td>" + item.dept_note + "</td>";
						trHtml += "<td>" + item.dept_kind_name + "</td>";
						trHtml += "<td>" + item.nature_name + "</td>";
						trHtml += "<td>" + (item.is_avg == 0 ? '否' : '是') + "</td>";
						trHtml += "<td>" + (item.is_stop == 0 ? '否' : '是') + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					lodopExportExcel("resultPrint", "导出Excel", " 科室字典表.xls",
							true);
				}, true, manager);
	}

	function templateDept() {

		location.href = "downTemplate.do?isCheck=false";
	}

	function importDept() {
		$.ligerDialog.open({
			url : 'aphiDeptImportPage.do?isCheck=false',
			height : 500,
			width : 800,
			title : '导入',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true
		});

	}

	function printDept() {

		//有数据直接打印
		if ($("#resultPrint > table > tbody").html() != "") {
			lodopPrinterTable("resultPrint", "开始打印", "8801 科室字典表", true);
			return;
		}

		//重新查询数据，避免分页导致打印数据不全 
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');
		var printPara = {

			usePager : false, 

			dept_code : $("#dept_code").val(),

			dept_name : $("#dept_name").val(),

			dept_kind_code : $("#dept_kind_code").val(),

			nature_code : $("#nature_code").val(),

			is_avg : $("#is_avg").val(),

			is_stop : $("#is_stop").val()

		};
		ajaxJsonObjectByUrl(
				"queryAphiDept_DeptKind_DeptNature.do",printPara,function(responseData) {
					$.each(responseData.Rows, function(idx, item) {
						var trHtml = "<tr>"; 
						trHtml += "<td>" + item.dept_code + "</td>";
						trHtml += "<td>" + item.dept_name + "</td>";
						trHtml += "<td>" + item.dept_note + "</td>";
						trHtml += "<td>" + item.dept_kind_name + "</td>";
						trHtml += "<td>" + item.nature_name + "</td>";
						trHtml += "<td>" + (item.is_avg == 0 ? '否' : '是') + "</td>";
						trHtml += "<td>" + (item.is_stop == 0 ? '否' : '是') + "</td>";
						trHtml += "</tr>";
						$("#resultPrint > table > tbody").append(trHtml);
					});
					manager.close();
					//alert($("#resultPrint").html())
					lodopPrinterTable("resultPrint", "开始打印", "8801 科室字典表", true);
				}, true, manager);
	}

	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&dept_id=" + vo[0] + 
				   "&group_id=" + vo[1] + 
				   "&hos_id=" + vo[2] + 
				   "&copy_code=" + vo[3]
		$.ligerDialog.open({
			url : 'aphiDeptUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 500,
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
					dialog.frame.saveAphiDept();
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
		autocomplete("#dept_code", "../queryPrmDept.do?isCheck=false", "id","text", true, true);

		$("#is_stop").ligerComboBox({
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
		  <td style="display:none">
			<input name="dept_code" type="hidden" id="dept_name" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_code" type="hidden" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_code" type="hidden" id="nature_code" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_code" type="hidden" id="is_avg" ltype="text" validate="{required:true,maxlength:20}" />
			<input name="dept_code" type="hidden" id="dept_note" ltype="text" validate="{required:true,maxlength:20}" />
		  </td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px; ">科室名称：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="left" ></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
			<td align="left" class="l-table-edit-td" >
				<select id="is_stop" name="is_stop">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select>

			</td>
			<td align="left" ></td>
		</tr>
	</table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>
				<tr> 
					<th width="200">科室编码</th>
					<th width="200">科室名称</th>
					<th width="200">科室描述</th>
					<th width="200">科室分类</th>
					<th width="200">科室性质</th>
					<th width="200">是否参与奖金核算</th>
					<th width="200">是否停用</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
