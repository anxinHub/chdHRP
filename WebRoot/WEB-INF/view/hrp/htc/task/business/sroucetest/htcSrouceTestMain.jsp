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
			name : 'plan_code',
			value : liger.get("plan_code").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid(
						{
							columns : [
									{
										display : '核算科室编码',
										name : 'proj_dept_code',
										align : 'left'
									},
									{
										display : '核算科室名称',
										name : 'proj_dept_name',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return "<a href='#' onclick=\"queryCostItems('"
									        + rowdata.group_id + "|"
									        + rowdata.hos_id + "|"
									        + rowdata.copy_code + "|"
									        + rowdata.acc_year + "|"
									        + rowdata.plan_code + "|"
											+ rowdata.proj_dept_id
											+ "');\" >"
											+ rowdata.proj_dept_name + "</a>";
										}
									},
									{
										display : '医院全成本',
										name : 'total_cost',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.total_cost, 2, 1);
										}
									},
									{
										display : '直接记入项目成本',
										name : 'charge_direct_cost',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.charge_direct_cost,2, 1);
										}
									},
									{
										display : '单独收费材料成本',
										name : 'mate_charge_cost',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.mate_charge_cost,2, 1);
										}
									},
									{
										display : '作业总成本',
										name : 'work_cost',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.work_cost, 2, 1);
										}
									},
									{
										display : '差额',
										name : 'chae',
										align : 'left',
										render : function(rowdata, rowindex,value) {
											return formatNumber(rowdata.total_cost - rowdata.charge_direct_cost - rowdata.mate_charge_cost - rowdata.work_cost,2, 1);
										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryHtcSrouceTest.do',
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
									text : '校验',
									id : 'check',
									click : check,
									icon : 'add'
								}, {
									line : true
								},
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	
	function check() {
		var acc_year = $("#acc_year").val();
		var plan_code = liger.get("plan_code").getValue();
		if (acc_year == '') {
			alert('请选择年度');
			return false;
		}
		if (plan_code == '') {
			alert('请选择方案');
			return false;
		}

		var formPara = {
			acc_year : acc_year,
			plan_code : plan_code
		};
		ajaxJsonObjectByUrl("checkHtcSrouceTest.do?isCheck=false", formPara,
				function(responseData) {
					if (responseData.state == "true") {
						query();
					}
				});
	}

	function queryCostItems(obj) {
		//实际代码中&temp替换主键
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] 
		   + "&" + "hos_id=" + vo[1]
		   + "&" + "copy_code=" + vo[2]
		   + "&" + "acc_year=" + vo[3]
		   + "&" + "plan_code=" + vo[4]
		   + "&" + "proj_dept_id=" + vo[5];

		$.ligerDialog.open({
			url : 'htcSrouceTestCostItemMainPage.do?isCheck=false' + parm,
			data : {},
			height: 500,
			width: 1000,
			title:"成本项目",
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
		});

	}
	
	function loadDict() {

		autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true);

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
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">方案：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
