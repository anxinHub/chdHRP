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
			name : 'start_year_month',
			value : $("#start_year_month").val()
		});

		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});

		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'people_type_code',
			value : liger.get("people_type_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'people_code',
			value : liger.get("people_code").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'cost_item_no',
			value : liger.get("cost_item_code").getValue().split(".")[1]
		});
		grid.options.parms.push({
			name : 'cost_item_id',
			value : liger.get("cost_item_code").getValue().split(".")[0]
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").html("");
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '年月',
								name : '',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return rowdata.acc_year + rowdata.acc_month
								}
							},
							{
								display : '科室编码',
								name : 'dept_code',
								align : 'left'
							},
							{
								display : '科室名称',
								name : 'dept_name',
								align : 'left'
							},
							{
								display : '人员类别编码',
								name : 'people_type_code',
								align : 'left'
							},
							{
								display : '人员类别名称',
								name : 'people_type_name',
								align : 'left'
							},
							{
								display : '人员编码',
								name : 'people_code',
								align : 'left'
							},
							{
								display : '人员名称',
								name : 'people_name',
								align : 'left'
							},
							{
								display : '成本项目编码',
								name : 'cost_item_code',
								align : 'left',
								render : function(rowdata, rowindex, value) {
									return "<a href='#' onclick=\"openUpdate('"
											+ rowdata.group_id + "|"
											+ rowdata.hos_id + "|"
											+ rowdata.copy_code + "|"
											+ rowdata.acc_year + "|"
											+ rowdata.acc_month + "|"
											+ rowdata.dept_no + "|"
											+ rowdata.dept_id + "|"
											+ rowdata.people_type_code + "|"
											+ rowdata.people_code + "|"
											+ rowdata.cost_item_no + "|"
											+ rowdata.cost_item_id + "');\" >"
											+ rowdata.cost_item_code + "</a>";
								}
							},
							{
								display : '成本项目名称',
								name : 'cost_item_name',
								align : 'left'
							},
							{
								display : '调整金额',
								name : 'amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.amount, 2, 1);
								}
							},
							{
								display : '原始金额',
								name : 'orig_amount',
								align : 'right',
								render : function(rowdata, rowindex, value) {
									return formatNumber(rowdata.orig_amount, 2,
											1);
								}
							} ],
					dataAction : 'server',
					dataType : 'server',
					usePager : true,
					url : 'queryHtcPeopleCostDetail.do',
					width : '100%',
					height : '100%',
					checkbox : true,
					rownumbers : true,
					selectRowButtonOnly : true,//heightDiff: -10,
					delayLoad : true,
					toolbar : {
						items : [ {
							text : '查询',
							id : 'search',
							click : query,
							icon : 'search'
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
							text : '采集',
							id : 'collect',
							click : collect,
							icon : 'plus'
						}, {
							line : true
						}, {
							text : '数据核对',
							id : 'check',
							click : check,
							icon : 'settings'
						}, {
							line : true
						}, ]
					}
				});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function collect() {
		$.ligerDialog.open({
			url : 'htcPeopleCostDetailCollectPage.do?isCheck=false',
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
					dialog.frame.savePeopleCostDetail();
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

	function check() {
		$.ligerDialog.open({
			url : 'htcPeopleCostDetailCheckPage.do?isCheck=false',
			height : 450,
			width : 790,
			title : '数据核对',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true
		});
	}

	function remove() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.acc_year + "@"
								+ this.acc_month + "@" + this.dept_no + "@"
								+ this.dept_id + "@" + this.people_type_code
								+ "@" + this.people_code + "@"
								+ this.cost_item_no + "@" + this.cost_item_id);//实际代码中temp替换主键
					});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcPeopleCostDetail.do", {
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
		var parm = "group_id=" + vo[0] + "&" 
		         + "hos_id=" + vo[1] + "&"
				 + "copy_code=" + vo[2] + "&" 
				 + "acc_year=" + vo[3] + "&"
				 + "acc_month=" + vo[4] + "&"
				 + "dept_no=" + vo[5] + "&"
				 + "dept_id=" + vo[6] + "&"
				 + "people_type_code=" + vo[7] + "&"
				 + "people_code=" + vo[8] + "&"
				 + "cost_item_no=" + vo[9] + "&"
			     + "cost_item_id=" + vo[10];
		$.ligerDialog.open({
			url : 'htcPeopleCostDetailUpdatePage.do?isCheck=false&' + parm,
			data : {},
			height : 450,
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
					dialog.frame.savePeopleCostDetail();
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

		autocomplete("#dept_code",
				"../../../info/base/queryHtcDeptDict.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#people_type_code",
				"../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false",
				"id", "text", true, true);
		autocomplete("#people_code",
				"../../../info/base/queryHtcPeopleDict.do?isCheck=false", "id",
				"text", true, true);
		autocomplete("#cost_item_code",
				"../../../info/base/queryHtcCostItemDict.do?isCheck=false",
				"id", "text", true, true);

		$("#start_year_month").ligerTextBox({
			width : 70
		});
		$("#end_year_month").ligerTextBox({
			width : 70
		});
		autodate("#start_year_month", "YYYYmm");
		autodate("#end_year_month", "YYYYmm");

	}


</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="start_year_month" type="text" id="start_year_month"
				style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="right"><span>&nbsp;至&nbsp;</span></td>
			<td><input class="Wdate" name="end_year_month" type="text"
				id="end_year_month" style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="people_type_code" type="text" id="people_type_code"
				ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input
				name="people_code" type="text" id="people_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="cost_item_code" type="text" id="cost_item_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
