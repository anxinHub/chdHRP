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
			project_id : $("#project_id").val(),
			project_no : $("#project_no").val(),
			bid_date : $("#bid_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssBidProject.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#project_id").val(responseData.project_id);
					$("#project_no").val(responseData.project_no);
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
			name : 'project_id',
			value : $("#project_id").val()
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
										render : function(rowdata) {
											return formatNumber(
													rowdata.ass_price
															* rowdata.ass_num,
															'${ass_05005}', 1);
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
							url : 'queryAssBidProjectDetail.do',
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
								if (isnull(this.project_detail_id)) {
									gridManager.deleteSelectedRow();
								} else {
									ParamVo.push(this.group_id + "@"
											+ this.hos_id + "@"
											+ this.copy_code + "@"
											+ this.project_id + "@"
											+ this.project_detail_id);
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
													"deleteAssBidProjectDetail.do",
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
		$("#project_no").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});

		$("#bid_date").ligerTextBox({
			width : 160
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

<body onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="project_id" type="text" id="project_id"
		value="${project_id}" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>招标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="project_no"
				disabled="disabled" type="text" id="project_no" value="${project_no}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>招标日期：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date"
				type="text" id="bid_date" value="${bid_date}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
