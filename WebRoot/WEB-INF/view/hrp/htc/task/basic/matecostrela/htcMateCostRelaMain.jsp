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
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acc_year',
			value : $("#acc_year").val()
		});
		grid.options.parms.push({
			name : 'mate_type_code',
			value : liger.get("mate_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'cost_item_no',
			value : liger.get("cost_item_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'cost_item_id',
			value : liger.get("cost_item_code").getValue().split(".")[0]
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
								display : '材料分类编码',
								name : 'mate_type_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
									        + rowdata.group_id + "|"
									        + rowdata.hos_id + "|"
									        + rowdata.copy_code + "|"
									        + rowdata.acc_year + "|"
											+ rowdata.mate_type_code
											+ "');\" >"
											+ rowdata.mate_type_code + "</a>";
								}
							}, {
								display : '材料分类名称',
								name : 'mate_type_name',
								align : 'left'
							},{
								display : '成本项目编码',
								name : 'cost_item_code',
								align : 'left'
							}, {
								display : '成本项目名称',
								name : 'cost_item_name',
								align : 'left'
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcMateCostRela.do',
					width : '100%',
					height : '100%',
					delayLoad:true,
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
						}]
					},
					onDblClickRow : function(rowdata, rowindex, value) {
						openUpdate(rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
						        + rowdata.acc_year + "|"
								+ rowdata.mate_type_code);//实际代码中temp替换主键
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//添加
	function add_open() {
		$.ligerDialog.open({
			url : 'htcMateCostRelaAddPage.do?isCheck=false',
			height : 280,
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
					dialog.frame.saveMateCostRela();
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

	//删除
	function remove() {
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
						 + this.acc_year + "@"
						 + this.mate_type_code
						 );//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcMateCostRela.do", {
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
			  "&" + "acc_year=" + vo[3]+ 
		      "&" + "mate_type_code=" + vo[4];
		$.ligerDialog.open({
			url : 'htcMateCostRelaUpdatePage.do?isCheck=false&'+ parm,
			data : {},
			height : 280,
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
					dialog.frame.saveMateCostRela();
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
		//成本项目字典

		autocomplete("#mate_type_code","../../../info/base/queryHtcMaterialTypeDict.do?isCheck=false","id", "text", true, true);
		//成本项目字典
		autocomplete("#cost_item_code","../../../info/base/queryHtcCostItemDict.do?isCheck=false","id", "text", true, true);
		
		$("#acc_year").ligerTextBox({
			width : 160
		});
		autodate("#acc_year", "YYYY");
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				type="text" name="acc_year" id="acc_year"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">材料分类：</td>
			<td align="left" class="l-table-edit-td"><input name="mate_type_code" type="text" id="mate_type_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input name="cost_item_code" type="text" id="cost_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
