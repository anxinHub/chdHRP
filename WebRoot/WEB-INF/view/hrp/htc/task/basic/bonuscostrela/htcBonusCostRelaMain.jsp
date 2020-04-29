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
			name : 'acc_year',
			value : $("#acc_year").val()
		});

		grid.options.parms.push({
			name : 'people_type_code',
			value : liger.get("people_type_code").getValue()
		});

		grid.options.parms.push({
			name : 'bonus_item_code',
			value : liger.get("bonus_item_code").getValue()
		});

		grid.options.parms.push({
			name : 'cost_item_code',
			value : $("#cost_item_code").val()
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
										display : '人员类别编码',
										name : 'people_type_code',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return "<a href='#' onclick=\"openUpdate('"
											        + rowdata.group_id+ "|"
											        + rowdata.hos_id+ "|"
											        + rowdata.copy_code+ "|"
											        + rowdata.acc_year+ "|"
													+ rowdata.people_type_code+ "|"
													+ rowdata.bonus_item_code
													+ "');\" >"
													+ rowdata.people_type_code
													+ "</a>";
										}
									}, {
										display : '人员类别名称',
										name : 'people_type_name',
										align : 'left'
									}, {
										display : '奖金项编码',
										name : 'bonus_item_code',
										align : 'left'
									}, {
										display : '奖金项名称',
										name : 'bonus_item_name',
										align : 'left'
									}, {
										display : '成本项目编码',
										name : 'cost_item_code',
										align : 'left'
									}, {
										display : '成本项目名称',
										name : 'cost_item_name',
										align : 'left'
									}

							],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcBonusCostRela.do',
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
										  rowdata.group_id+ "|"
								        + rowdata.hos_id+ "|"
								        + rowdata.copy_code+ "|"
								        + rowdata.acc_year+ "|"
										+ rowdata.people_type_code + "|"
										+ rowdata.bonus_item_code);//实际代码中temp替换主键
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//添加
	function add_open() {
		$.ligerDialog.open({
			url : 'htcBonusCostRelaAddPage.do?isCheck=false',
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
					dialog.frame.saveBonusCostRela();
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
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@"
								   + this.hos_id + "@"
								   + this.copy_code + "@"
								   + this.acc_year+ '@'
								   + this.people_type_code+ '@'
								   + this.bonus_item_code
                                 );//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcBonusCostRela.do", {
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

	//更新
	function openUpdate(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm =  "group_id=" + vo[0] + 
		      "&" + "hos_id=" + vo[1] + 
			  "&" + "copy_code=" + vo[2]+ 
			  "&" + "acc_year=" + vo[3]+ 
			  "&" + "people_type_code=" + vo[4]+ 
		      "&" + "bonus_item_code=" + vo[5];
		$.ligerDialog.open({
			url : 'htcBonusCostRelaUpdatePage.do?isCheck=false&' + parm,
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
					dialog.frame.saveBonusCostRela();
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

		$("#acc_year").ligerTextBox({
			width : 160
		});
		autodate("#acc_year", "YYYY");

		//人员类别信息
		autocomplete("#people_type_code","../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false", "id","text", true, true);
		//工资项
		autocomplete("#bonus_item_code","../../../info/base/queryHtcBonusItemDict.do?isCheck=false", "id","text", true, true);
		//成本项目
		autocomplete("#cost_item_code","../../../info/base/queryHtcCostItemDict.do?isCheck=false","id", "text", true, true);
		
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="people_type_code" type="text" id="people_type_code"ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">奖金项：</td>
			<td align="left" class="l-table-edit-td"><input
				name="bonus_item_code" type="text" id="bonus_item_code" ltype="text"/></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="cost_item_code" type="text" id="cost_item_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
