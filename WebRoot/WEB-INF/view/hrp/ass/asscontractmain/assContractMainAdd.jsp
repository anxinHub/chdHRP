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
	var grid;
	var gridManager = null;
	var ForT = [ {
		id : 0,
		text : '否'
	}, {
		id : 1,
		text : '是'
	} ];
	$(function() {
		loadDict()//加载下拉框
		loadHead(null);
		$("#contract_no").ligerTextBox({
			width : 160
		});
		$("#contract_type").ligerTextBox({
			width : 160
		});
		$("#contract_ori_no").ligerTextBox({
			width : 160
		});
		$("#contract_name").ligerTextBox({
			width : 160
		});
		$("#ass_year").ligerTextBox({
			width : 160
		});
		$("#ass_month").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#sign_date").ligerTextBox({
			width : 160
		});
		$("#buy_type").ligerTextBox({
			width : 160
		});
		$("#emp_main").ligerTextBox({
			width : 160
		});
		$("#provider").ligerTextBox({
			width : 160
		});
		$("#is_bid").ligerTextBox({
			width : 160
		});
		$("#contract_money").ligerTextBox({
			width : 160
		});
		$("#start_date").ligerTextBox({
			width : 160
		});
		$("#end_date").ligerTextBox({
			width : 160
		});
	});

	function save() {
		if (liger.get("contract_type").getValue() == "") {
			$.ligerDialog.error('合同类别不能为空');
			return;
		}
		if ($("#contract_ori_no").val() == "") {
			$.ligerDialog.error('合同原始编号不能为空');
			return;
		}
		if ($("#contract_name").val() == "") {
			$.ligerDialog.error('合同名称不能为空');
			return;
		}
		if ($("#ass_year").val() == "") {
			$.ligerDialog.error('购置年度不能为空');
			return;
		}
		if ($("#ass_month").val() == "") {
			$.ligerDialog.error('购置月份不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if ($("#sign_date").val() == "") {
			$.ligerDialog.error('签订日期不能为空');
			return;
		}
		if ($("#buy_type").val() == "") {
			$.ligerDialog.error('采购方式不能为空');
			return;
		}
		if ($("#start_date").val() == "") {
			$.ligerDialog.error('开始日期不能为空');
			return;
		}
		if ($("#end_date").val() == "") {
			$.ligerDialog.error('结束日期不能为空');
			return;
		}
		if (liger.get("emp_main").getValue() == "") {
			$.ligerDialog.error('我方负责人不能为空');
			return;
		}
		if ($("#provider").val() == "") {
			$.ligerDialog.error('对方负责人不能为空');
			return;
		}
		if ($("#is_bid").val() == "") {
			$.ligerDialog.error('是否经过招标不能为空');
			return;
		}
		if (liger.get("cur_code").getValue() == "") {
			$.ligerDialog.error('币种不能为空');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_code) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {

			contract_id : $("#contract_id").val() == "" ? 0 : $("#contract_id")
					.val(),

			contract_no : $("#contract_no").val(),

			contract_type : $("#contract_type").val(),

			contract_ori_no : $("#contract_ori_no").val(),

			contract_name : $("#contract_name").val(),

			ass_year : $("#ass_year").val(),

			ass_month : $("#ass_month").val(),

			ven_id : liger.get("ven_id").getValue(),

			sign_date : $("#sign_date").val(),

			buy_type : $("#buy_type").val(),

			start_date : $("#start_date").val(),

			end_date : $("#end_date").val(),

			contract_detail : $("#contract_detail").val(),

			service_detail : $("#service_detail").val(),

			emp_main : liger.get("emp_main").getValue(),

			provider : $("#provider").val(),

			is_bid : liger.get("is_bid").getValue(),

			contract_money : $("#contract_money").val(),

			cur_code : liger.get("cur_code").getValue(),

			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			$.post("addAssContractMain.do", formPara, function(responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					$.ligerDialog.confirm('添加成功 。是否继续添加单据?', function(yes) {
						if (yes) {
							$("#contract_no").val("");
							$("#contract_type").val("");
							$("#contract_ori_no").val("");
							$("#contract_name").val("");
							$("#start_date").val("");
							$("#end_date").val("");
							$("#buy_type").val("");
							$("#contract_detail").val("");
							$("#service_detail").val("");
							$("#provider").val("");
							$("#contract_money").val("");
							autodate("#ass_year", "YYYY");
							autodate("#ass_month", "MM");
							autodate("#sign_date");
							liger.get("ven_id").setValue("");
							liger.get("ven_id").setText("");
							liger.get("emp_main").setValue("");
							liger.get("emp_main").setText("");
							liger.get("is_bid").setValue("");
							liger.get("is_bid").setText("");
							liger.get("cur_code").setValue("");
							liger.get("cur_code").setText("");
							grid.loadData();
							is_addRow();
						} else {
							this_close();
						}
					});
				}
			}, "json");
		}
	}

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_id',
										textField : 'ass_code',
										align : 'left',
										width : '80'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : {
											type : 'select',
											textField : 'ass_name',
											valueField : 'ass_id_no',
											selectBoxWidth : 500,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '编码',
													name : 'ass_code',
													align : 'left'
												}, {
													display : '名称',
													name : 'ass_name',
													align : 'left'
												}, {
													display : '分类名称',
													name : 'ass_type_name',
													align : 'left'
												}, {
													display : '是否停用',
													name : 'is_stop',
													align : 'left'
												}, {
													display : '资产ID',
													name : 'ass_id',
													hide : true
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../queryAssNoDictTable.do?isCheck=false',
												pageSize : 30
											},
											alwayShowInDown : true,
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
														".l-grid-row").find(
														"td:first").focus();
											}
										//
										},
										width : '80'
									},
									{
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '80'
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '80'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '80'
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
										width : '130'
									},
									{
										display : '合同数量',
										name : 'contract_amount',
										align : 'left',
										type : 'int',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : 80,
										totalSummary : {
											type : 'Sum'
										}
									},
									{
										display : '合同单价',
										name : 'contract_price',
										editor : {
											type : 'text'
										},
										align : 'left',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.contract_price, '${ass_05006}',
													1);
										},
										width : '80'
									},
									{
										display : '总额',
										name : 'sum_price',
										align : 'left',
										width : 80,
										render : function(rowdata) {
											return formatNumber(
													rowdata.contract_price
															* rowdata.contract_amount,
															'${ass_05005}', 1);
										},
										totalSummary : {
											type : 'Sum'
										}
									},
									{
										display : '交货日期',
										name : 'send_date',
										type : 'date',
										format : 'yyyy-MM-dd',
										editor : {
											type : 'date'
										},
										align : 'left',
										width : '80'
									},
									{
										display : '保修期',
										name : 'keep_repair_times',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '80'
									},
									{
										display : '保修期单位',
										name : 'times_unit',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '80'
									},
									{
										display : '是否安装',
										name : 'is_fix',
										align : 'left',
										width : 80,
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForT,
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
										render : function(item) {
											if (parseInt(item.is_fix) == 1) {
												return '是';
											} else if (parseInt(item.is_fix) == 0) {
												return '否';
											} else {
												return "";
											}
										}
									},
									{
										display : '是否验收',
										name : 'is_accept',
										align : 'left',
										width : 80,
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForT,
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
										render : function(item) {
											if (parseInt(item.is_accept) == 1) {
												return '是';
											} else if (parseInt(item.is_accept) == 0) {
												return '否';
											} else {
												return "";
											}
										}
									},
									{
										display : '是否招标',
										name : 'is_bid',
										align : 'left',
										width : 80,
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : ForT,
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
										render : function(item) {
											if (parseInt(item.is_bid) == 1) {
												return '是';
											} else if (parseInt(item.is_bid) == 0) {
												return '否';
											} else {
												return "";
											}
										}
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '160'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							rownumbers : true,
							delayLoad : true,//初始化明细数据
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'delete',
									click : itemclick,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								} ]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				gridManager.deleteSelectedRow();
				return;
			}
		}
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

		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					fac_id : data.fac_id,
					fac_name : data.fac_name
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
		if (e.column.name == "contract_price"
				|| e.column.name == "contract_amount") {

			var price = (e.record.contract_price == null
					|| e.record.contract_price == 'undefined' || e.record.contract_price == "") ? 0
					: e.record.contract_price;
			var num = (e.record.contract_amount == null
					|| e.record.contract_amount == 'undefined' || e.record.contract_amount == "") ? 0
					: e.record.contract_amount;

			grid.updateCell('sum_price', price * num, e.record);
		}
		return true;
	}

	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code + "|" + v.ass_model + "|" + v.ass_spec
						+ "|" + v.ass_brand + "|" + v.fac_id;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.ass_spec)) {
					msg += "[规格]、";
				}
				if (isnull(v.ass_model)) {
					msg += "[型号]、";
				}
				if (v.fac_id == '@' || isnull(v.fac_id)) {
					msg += "[生产厂家]、";
				}
				if (isnull(v.contract_amount)) {
					msg += "[合同数量]、";
				}
				if (isnull(v.contract_price)) {
					msg += "[合同单价]、";
				}
				if (v.is_bid != 0 && v.is_bid != 1) {
					msg += "[是否招标]";
				}
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		return true;
	}

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'contract_id',
			value : $("#contract_id").val() == "" ? "0" : $("#contract_id")
					.val()
		});
		grid.loadData(grid.where);
	}
	function loadDict() {
		//字典下拉框
		//默认年
		autodate("#ass_year", "YYYY");
		//默认月
		autodate("#ass_month", "mm");
		$("#contract_no").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		$("#contract_money").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		//供应商
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, null, null, "400");
		$("#ven_id").ligerComboBox({
			width : 135
		});
		//我方负责人
		autocomplete("#emp_main",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		$("#emp_main").ligerComboBox({
			width : 135
		});

		autocomplete("#cur_code", "../../../hrp/acc/queryCur.do?isCheck=false",
				"id", "text", true, true);
		//是否经过招标
		$("#is_bid").ligerComboBox({
			width : 135
		});
		//合同类别
		$("#contract_type").ligerComboBox({
			width : 135
		});
		//采购方式
		$("#buy_type").ligerComboBox({
			width : 135
		});
	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="contract_id" type="text" id="contract_id" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td">合同号：</td>
			<td align="left" class="l-table-edit-td"><input
				disabled="disabled" name="contract_no" type="text" id="contract_no" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>合同原始编号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_ori_no" type="text" id="contract_ori_no" /></td>

			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>合同名称：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_name" type="text" id="contract_name" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>合同类别：</td>
			<td align="left" class="l-table-edit-td"><select
				id="contract_type" name="contract_type">
					<option value=""></option>
					<option value="0">买卖合同</option>
					<option value="1">赠与合同</option>
					<option value="2">借款合同</option>
					<option value="3">租赁合同</option>
					<option value="4">融资租赁合同</option>
					<option value="5">承揽合同</option>
					<option value="6">建设工程合同</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>统计年度：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_year"
				type="text" id="ass_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>

			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>统计月份：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_month"
				type="text" id="ass_month" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>签订日期：</td>
			<td align="left" class="l-table-edit-td"><input name="sign_date"
				type="text" id="sign_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>开始日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="start_date" type="text" id="start_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>结束日期：</td>
			<td align="left" class="l-table-edit-td"><input name="end_date"
				type="text" id="end_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_type"
				name="buy_type">
					<option value=""></option>
					<option value="0">自主采购</option>
					<option value="1">集中采购</option>
			</select></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>是否经过招标：</td>
			<td align="left" class="l-table-edit-td"><select id="is_bid"
				name="is_bid">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>我方负责人：</td>
			<td align="left" class="l-table-edit-td"><input name="emp_main"
				type="text" id="emp_main" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>对方负责人：</td>
			<td align="left" class="l-table-edit-td"><input name="provider"
				type="text" id="provider" /></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>币种：</td>
			<td align="left" class="l-table-edit-td"><input name="cur_code"
				type="text" id="cur_code" /></td>
			<td align="right" class="l-table-edit-td">合同金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="contract_money" type="text" id="contract_money" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">合同描述：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="2" cols="100" name="contract_detail" id="contract_detail"></textarea>
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">售后服务说明：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="2" cols="100" name="service_detail" id="service_detail"></textarea>
			</td>
			<td align="left"></td>
		</tr>

	</table>
	<div id="maingrid"></div>
</body>
</html>
