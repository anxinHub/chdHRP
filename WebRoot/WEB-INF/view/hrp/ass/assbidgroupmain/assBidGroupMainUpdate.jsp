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
	$(function() {
		loadDict();
		loadHead(null);
		query();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('saveDetail');
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
		}
	});

	function save() {
		if ($("#bid_date").val() == "") {
			$.ligerDialog.error('招标日期不能为空');
			return;
		}
		if ($("#ven_id").val() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if ($("#create_emp").val() == "") {
			$.ligerDialog.error('制单人不能为空');
			return;
		}
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
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
			bid_id : $("#bid_id").val(),
			bid_no : $("#bid_no").val(),
			bid_date : $("#bid_date").val(),
			ven_id : liger.get("ven_id").getValue(),
			link_man : $("#link_man").val(),
			tel_mobile : $("#tel_mobile").val(),
			tel_office : $("#tel_office").val(),
			bid_note : $("#bid_note").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssBidGroupMain.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#bid_id").val(responseData.bid_id);
					$("#bid_no").val(responseData.bid_no);
					parentFrameUse().query();
					is_addRow();
					query();
				}
			});
		}
	}

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'bid_id',
			value : $("#bid_id").val()
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
										align : 'left'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : {
											type : 'select',
											valueField : 'ass_id_no',
											textField : 'ass_name',
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
										}

									},
									{
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
										display : '数量',
										name : 'ass_num',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : 100,
										totalSummary : {
											type : 'Sum'
										}
									},
									{
										display : '价格',
										name : 'ass_price',
										editor : {
											type : 'numberbox',
											precision : 2
										},
										align : 'right',
										render : function(rowdata, rowindex,
												value) {
											return formatNumber(
													rowdata.ass_price == null ? 0
															: rowdata.ass_price,
													'${ass_05006}',
													1);
										}
									},
									{
										display : '金额',
										name : 'ass_money',
										align : 'right',
										width : 200,
										render : function(item) {
											item.sum_price = formatNumber(
													item.apply_amount
															* item.advice_price);
											return formatNumber(
													item.sum_price == null ? 0
															: item.sum_price,
													'${ass_05005}',
													1);

										},
										totalSummary : {
											type : 'Sum'
										}
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
										align : 'left'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : '../assbiddetail/queryAssBidDetail.do?isCheck=false',
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : '${state}' != 0 ? false : true,
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
	
	function this_close(){
		frameElement.dialog.close();
	}

	function validateGrid() {

		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data,
				function(i, v) {
			if (!isnull(v.ass_code)) {
					var key = v.ass_code + "|" + v.ass_model + "|" + v.ass_spec
							+ "|" + v.fac_id;
					var value = "第" + (i + 1) + "行";
					
					if (isnull(v.ass_num)) {
						msg += "[数量]、";
					}
					if (isnull(v.ass_price)) {
						msg += "[建议单价]、";
					}
					if (v.fac_id == '@' || isnull(v.fac_id)) {
						msg += "[生产厂家]、";
					}
					if (msg != "") {
						msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
					}
					if (isnull(targetMap.get(key))) {
						targetMap.put(key, value);
					} else {
						msgMap.put(
								targetMap.get(key) + "与" + value + "重复！\n\r",
								value);
					}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					var i = 0;
					$(data).each(
							function() {
								if (isnull(this.bid_detail_id)) {
									gridManager.deleteSelectedRow();
								} else {
									ParamVo.push(this.group_id + "@"
											+ this.hos_id + "@"
											+ this.copy_code + "@"
											+ this.bid_id + "@"
											+ this.bid_detail_id);
								}
								i++;
							});
					if (ParamVo == "") {
						is_addRow();
						return;
					}
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"../assbiddetail/deleteAssBidDetail.do?isCheck=false",
													{
														ParamVo : ParamVo
																.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															query();
														}
													});
										}
									});
				}
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
		//alert(JSON.stringify(data)); 
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
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
		if (e.column.name == "ass_price" || e.column.name == "ass_num") {

			var price = (e.record.ass_price == null
					|| e.record.ass_price == 'undefined' || e.record.ass_price == "") ? 0
					: e.record.ass_price;
			var num = (e.record.ass_num == null
					|| e.record.ass_num == 'undefined' || e.record.ass_num == "") ? 0
					: e.record.ass_num;

			grid.updateCell('ass_money', price * num, e.record);
		}
		return true;
	}

	function loadDict() {
		//字典下拉框
		//只读  
		$("#bid_no").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});

		$("#audit_date").ligerTextBox({
			disabled : true,
			cancelable : false
		});

		$("#bid_date").ligerTextBox({
			width : 160
		});

		$("#link_man").ligerTextBox({
			width : 160
		});

		$("#tel_mobile").ligerTextBox({
			width : 160
		});

		$("#tel_office").ligerTextBox({
			width : 160
		});

		$("#create_date").ligerTextBox({
			width : 160
		});
		//供应商
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, "", false, "${ven_id}@${ven_no}","400");
		$("#ven_id").ligerComboBox({
			width : 160
		});
		//制单人
		autocomplete("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, "", false, "${audit_emp}");
		$("#audit_emp").ligerComboBox({
			width : 160,
			disabled : true,
			cancelable : false
		});

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
</script>

</head>

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="bid_id" type="text" id="bid_id"
		value="${bid_id}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>招标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_no"
				disabled="disabled" type="text" id="bid_no" value="${bid_no}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>招标日期：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date"
				type="text" id="bid_date" value="${bid_date}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" value="${ven_id}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系人：</td>
			<td align="left" class="l-table-edit-td"><input name="link_man"
				type="text" id="link_man" value="${link_man}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">手机号码：</td>
			<td align="left" class="l-table-edit-td"><input
				name="tel_mobile" type="text" id="tel_mobile" value="${tel_mobile}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">办公电话：</td>
			<td align="left" class="l-table-edit-td"><input
				name="tel_office" type="text" id="tel_office" value="${tel_office}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">备注：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" name="bid_note" id="bid_note">${bid_note}</textarea>
			</td>
			<td align="left"></td>
		</tr>

	</table>
	<div id="maingrid"></div>
</body>
</html>
