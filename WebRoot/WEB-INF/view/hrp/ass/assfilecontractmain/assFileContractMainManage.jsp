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
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		$("#contract_no").ligerTextBox({
			width : 160
		});
		$("#contract_name").ligerTextBox({
			width : 160
		});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'contract_id',
			value : $("#contract_id").val()
		});
		grid.options.parms.push({
			name : 'contract_no',
			value : $("#contract_no").val()
		});
		grid.options.parms.push({
			name : 'contract_name',
			value : $("#contract_name").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '付款期号',
										name : 'payment_id',
										align : 'left',
										render : function(rowdata, rowindex, value) {
											return "<a href=javascript:openUpdate('"
													+ rowdata.group_id + "|"
													+ rowdata.hos_id + "|"
													+ rowdata.contract_id + "|"
													+ rowdata.contract_no + "|"
													+ rowdata.payment_id + "|"
													+ rowdata.cur_code + "|" + rowdata.id  + "')>"+rowdata.payment_id+"</a>";
										}
									},
									{
										display : '摘要',
										name : 'summary',
										align : 'left'
									},
									{
										display : '币种编码',
										name : 'cur_code',
										align : 'left'
									},
									{
										display : '付款金额',
										name : 'pay_money',
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.pay_money, '${ass_05005}', 1);
										},
										totalSummary : {
											type : 'Sum'
										}
									},
									{
										display : '付款期间开始',
										name : 'start_date',
										align : 'left'
									},
									{
										display : '付款期间结束',
										name : 'end_date',
										align : 'left'
									},
									{
										display : '实付日期',
										name : 'fact_pay_date',
										align : 'left'
									},
									{
										display : '状态',
										name : 'state',
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.state == 0) {
												return "新建";
											}
											return "审核";
										}
									}, {
										display : '创建日期',
										name : 'create_date',
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : 'queryAssFileContractManage.do?isCheck=false&contract_id=${contract_id}',
							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,
							selectRowButtonOnly : true,//
							toolbar : {
								items : [
									{
										text : '关闭',
										id : 'close',
										click : this_close,
										icon : 'candle'
										} 
								]
							},
							onDblClickRow : function(rowdata, rowindex, value) {
								openUpdate(rowdata.group_id + "|"
										+ rowdata.hos_id + "|"
										+ rowdata.contract_id + "|"
										+ rowdata.contract_no + "|"
										+ rowdata.payment_id + "|"
										+ rowdata.cur_code + "|" + rowdata.id);
							}

						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function this_close(){
		frameElement.dialog.close();
	}
	function openUpdate(obj) {

		var vo = obj.split("|");
		var parm = "&group_id=" + vo[0] + "&hos_id=" + vo[1] + "&contract_id="
				+ vo[2] + "&contract_no=" + vo[3] + "&payment_id=" + vo[4]
				+ "&cur_code=" + vo[5] + "&id=" + vo[6];
		$.ligerDialog.open({
			url : 'assFileContractManageUpdatePage.do?isCheck=false&' + parm,
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
				text : '关闭',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});

	}

	function loadDict() {
		//字典下拉框

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></b>合同号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_no" type="text" id="contract_no" ltype="text"
				disabled="disabled" value="${contract_no}"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">合同名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_name" type="text" id="contract_name" ltype="text"
				disabled="disabled" value="${contract_name}"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

	</table>
	<div id="maingrid"></div>
</body>
</html>
