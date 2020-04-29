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
	var selectData = "";
	var clicked = 0;
	var next = true;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		query();
		$("#apply_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_year").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_month").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#dept_id").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_emp").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_date").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_money").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#create_emp").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#create_date").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#check_emp").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#check_date").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#is_add").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#apply_money").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});

		$("#apply_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#apply_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
	});
	//查询

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'apply_id',
			value : $("#apply_id").val()
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
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '90'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										width : '200',
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90'
									},

									{
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90'
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryHosFacDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
										align : 'left',
										width : '200'
									},
									{
										display : '申请数量',
										name : 'apply_amount',
										type : 'int',
										width : 100,
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '90',
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,0,0) + '</div>';
					                        }
					                    }
									},
									{
										display : '建议单价',
										name : 'advice_price',
										editor : {
											type : 'numberbox',
											precision : 2
										},
										align : 'right',
										width : '90'
									},
									{
										display : '总额',
										name : 'sum_price',
										width : 200,
										align : 'right',
										render : function(item) {
											item.sum_price = formatNumber(
													item.apply_amount
															* item.advice_price,
													2, 1);
											return formatNumber(item.sum_price,
													2, 1);
										},
										width : '90',
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>' + formatNumber(suminf.sum,2,1) + '</div>';
					                        }
					                    }
									},
									{
										display : '希望到货日期',
										name : 'need_date',
										type : 'date',
										format : 'yyyy-MM-dd',
										editor : {
											type : 'date'
										},
										align : 'left',
										width : '90'
									},
									{
										display : '用途编码',
										name : 'ass_usage_code',
										textField : 'ass_usage_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryAssUsageDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true,
											onSuccess : function(data) {
												if (initvalue != undefined
														&& initvalue != "") {
													this.setValue(initvalue);
													initvalue = "";
												}
											}
										},
										align : 'left',
										width : '90'
									}, {
										display : '详细用途',
										name : 'usage_note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '160'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : '../assapplydept/queryAssApplyDeptDetail.do',
							width : '100%',
							height : '100%',
							checkbox : false,
							enabledEdit : false,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							checkbox : false,
							rownumbers : true,
							delayLoad : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ 
								{
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
									} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;

	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_usage_code : data.usage_code,
					ass_usage_name : data.usage_name,
					fac_id : data.fac_id,
					fac_name : data.fac_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec
				});

			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if (e.column.name == "advice_price" || e.column.name == "apply_amount") {

			var price = (e.record.advice_price == null
					|| e.record.advice_price == 'undefined' || e.record.advice_price == "") ? 0
					: e.record.advice_price;
			var num = (e.record.apply_amount == null
					|| e.record.apply_amount == 'undefined' || e.record.apply_amount == "") ? 0
					: e.record.apply_amount;

			grid.updateCell('sum_price', price * num, e.record);
			grid.updateTotalSummary();
		}
		return true;
	}
	

	function loadDict() {

		//字典下拉框
		autocomplete("#apply_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, '', false, '${apply_emp}');

		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, '', false, '${dept_id}@${dept_no}');

		autocomplete("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, '', false, '${check_emp}');

		$("#is_add").ligerComboBox({
			width : 160
		});
		liger.get("is_add").setValue("${is_add}");

		$("#apply_year").ligerTextBox({
			width : 160
		});

		$("#apply_month").ligerTextBox({
			width : 160
		});

		$("#apply_no").ligerTextBox({
			width : 160
		});

		$("#apply_money").ligerTextBox({
			width : 160
		});

		$("#check_date").ligerTextBox({
			width : 160
		});

	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="apply_id" name="apply_id" value="${apply_id }" />
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"
				width="100"><b><font color="red">*</font></b>申请单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_no"
				type="text" id="apply_no" disabled="disabled" value="${apply_no }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input disabled="disabled"
				name="apply_year" type="text" id="apply_year" class="Wdate"
				value="${apply_year }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置月份：</td>
			<td align="left" class="l-table-edit-td"><input disabled="disabled"
				name="apply_month" type="text" id="apply_month" class="Wdate"
				value="${apply_month }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>申请科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" disabled="disabled"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>申&nbsp;&nbsp;请&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_emp" disabled="disabled"
				type="text" id="apply_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b><font
					color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_date" type="text" id="apply_date" class="Wdate" disabled="disabled"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"
				value="${apply_date }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_money" type="text" id="apply_money" disabled="disabled"
				value="${apply_money }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>追加申请：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add"
				name="is_add">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><textarea
					rows="2" cols="80" name="summary" id="summary" disabled="disabled">${summary }</textarea></td>
			<td align="left"></td>
		</tr>
		
	</table>

	<div id="maingrid"></div>
</body>
</html>
