<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var gridManager = null;
	var grid = null;
	$(function() {
		loadDict();
		loadHead(null);
		querys();
	});

	function querys() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'plan_id',
			value : $("#plan_id").val()
		});
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
										textField : 'ass_name',
										align : 'left',
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
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '100'
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
										width : '260'
									},
									{
										display : '计划数量',
										name : 'plan_amount',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : 100,
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
										render : function(rowdata, rowindex,
												value) {
											return formatNumber((
													rowdata.advice_price == null ? 0
															: rowdata.advice_price,
													'${ass_05006}',
													1);
										},
										align : 'right',
										width : '90'
									},
									{
										display : '金额',
										name : 'sum_price',
										align : 'right',
										width : 200,
										render : function(item) {
											item.sum_price = formatNumber(
													item.plan_amount
															* item.advice_price,
													2, 1);
											return formatNumber((
													item.sum_price== null ? 0
															: item.sum_price,
													'${ass_05005}',
													1);
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
										display : '预计到货日期',
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
										width : '150'
									}],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAssPlanDeptGroupDetail.do',
							width : '100%',
							height : '100%',
							checkbox : false,
							enabledEdit :false,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							delayLoad : true,//初始化明细表
							isScroll : true,
							rownumbers : true,
							selectRowButtonOnly : true, //heightDiff: -10,
							toolbar : {
								items : [  {
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
		if (e.column.name == "advice_price" || e.column.name == "plan_amount") {

			var price = (e.record.advice_price == null
					|| e.record.advice_price == 'undefined' || e.record.advice_price == "") ? 0
					: e.record.advice_price;
			var num = (e.record.plan_amount == null
					|| e.record.plan_amount == 'undefined' || e.record.plan_amount == "") ? 0
					: e.record.plan_amount;

			grid.updateCell('sum_price', price * num, e.record);
			grid.updateTotalSummary();
		}
		return true;
	}

	
	function loadDict() {
		//字典下拉框

		//科室编码
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, "", false);
		$("#dept_id").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		liger.get("dept_id").setValue("${dept_id}@${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		//制单人
		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, "", false);
		$("#create_emp").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		liger.get("create_emp").setValue("${create_emp}");
		liger.get("create_emp").setText("${create_emp_name}");
		//是否追加计划
		$("#is_add").val('${is_add}');
		$("#is_add").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#buy_code").val('${buy_code}');
		$("#buy_code").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});

		$("#plan_year").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		//制单日期
		$("#create_date").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		//购置月份
		$("#plan_month").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#plan_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#plan_money").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		})

	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<input name="plan_id" type="hidden" id="plan_id" value="${plan_id}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计划单号：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_no"
				type="text" id="plan_no" disabled="disabled" value="${plan_no}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" value="${plan_year}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制&nbsp;&nbsp;单&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_emp" type="text" id="create_emp" value="${create_emp }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date"
				value="${create_date }" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" value="${dept_id }" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>追加计划：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add"
				name="is_add">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_code"
				name="buy_code">
					<option value=""></option>
					<option value="0">自主采购</option>
					<option value="1">集中采购</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计划金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_money" type="text" id="plan_money" disabled="disabled"
				value="${plan_money }" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="100" name="note" id="note" disabled="disabled">${note}</textarea></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
