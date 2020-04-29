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
	var gridItem;
	var gridManagerItem = null;
	var gridManager = null;
	var grid = null;
	var sourceid = null;
	var plan_id;
	var source_code;
	var editor;
	$(function() {

		$("#layout1").ligerLayout({
			rightWidth : '350',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
			}
		});

		loadDict();//加载下拉框
		loadHead(null);
		loadHeadItem(null);
		/* loadHotkeys(); */
	});
	function query(plan_id,source_id) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'plan_id',
			value : $("#plan_id").val() == "" ? "0" : $("#plan_id").val()
		});
		gridItem.options.parms.push({
			name : 'source_id',
			value : $("#source_id").val() == "" ? "1" : $("#source_id").val()
		});
		grid.loadData(grid.where);
		/* gridItem.loadData(gridItem.where); */
	}
	function queryItem(plan_id, plan_detail_id) {
		gridItem.options.parms = [];
		gridItem.options.newPage = 1;
		gridItem.options.parms.push({
			name : 'plan_id',
			value : $("#plan_id").val() == "" ? "0" : $("#plan_id").val()
		});
		gridItem.options.parms.push({
			name : 'plan_detail_id',
			value : plan_detail_id == "" || plan_detail_id == null
					|| plan_detail_id == "undefined" ? "0" : plan_detail_id
		});
		gridItem.loadData(gridItem.where);
	}
	function save() {
		gridManager.endEdit();
		if ($("#plan_year").val() == "") {
			$.ligerDialog.error('购置年度不能为空');
			return;
		}
		if ($("#dept_id").val() == "") {
			$.ligerDialog.error('编制科室不能为空');
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
		if (liger.get("is_add").getValue() == "") {
			$.ligerDialog.error('是否追究计划不能为空');
			return;
		}
		if (liger.get("buy_code").getValue() == "") {
			$.ligerDialog.error('采购方式不能为空');
			return;
		}

		var data = gridManager.getData();
		var source_id = null;
		var sum_price = null;
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
			plan_id : $("#plan_id").val() == "" ? 0 : $("#plan_id").val(),

			plan_no : $("#plan_no").val(),

			plan_year : $("#plan_year").val(),

			dept_id : liger.get("dept_id").getValue(),

			create_emp : liger.get("create_emp").getValue(),

			create_date : $("#create_date").val(),

			is_add : liger.get("is_add").getValue(),

			buy_code : liger.get("buy_code").getValue(),

			note : $("#note").val(),
			/* SOURCE_ID  */

		 source_id : $("#source_id").val() == "" ? "1" : $("#source_id").val(),
			sum_price : grid.getTotalInfoSum('sum_price'),
			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			/* console.log(plan_id),  */ajaxJsonObjectByUrl("addAssPlanDept.do",
					formPara, function(responseData) {
						if (responseData.state == "true") {
							parentFrameUse().query();
							$("#plan_id").val(responseData.plan_id);
							$("#plan_no").val(responseData.plan_no);
							$("#plan_money").val(responseData.plan_money);
							query(responseData.plan_id);
						}
					}, false);
		}
	}

	function validateGrid() {

		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code;
				var value = "第" + (i + 1) + "行";

				//if (isnull(v.ass_spec)) {
				//msg += "[规格]、";
				//}
				//if (isnull(v.ass_model)) {
				//msg += "[型号]、";
				//}
				/* if (v.fac_id == '@' || isnull(v.fac_id)) {
					msg += "[生产厂家]、";
				} */
				if (isnull(v.plan_amount)) {
					msg += "[数量]、";
				}
				if (isnull(v.advice_price)) {
					msg += "[建议单价]、";
				}
				/* if (isnull(v.ass_usage_code)) {
					msg += "[用途]、";
				} */
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
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
										editor : {
											type : 'select',
											valueField : 'ass_id_no',
											textField : 'ass_name',
											selectBoxWidth : 500,
											selectBoxHeight : 240,
											grid : {
												columns : [  {
													display : '资产编码',
													name : 'ass_code',
													align : 'left'
												}, {
													display : '资产名称',
													name : 'ass_name',
													align : 'left'
												}, {
													display : '资产分类',
													name : 'ass_type_name',
													align : 'left'
												}, {
													display : '规格',
													name : 'ass_spec',
													align : 'left'
												}, {
													display : '型号',
													name : 'ass_model',
													align : 'left'
												}, {
													display : '品牌',
													name : 'ass_brand',
													align : 'left'
												},{
													display : '计量单位',
													name : 'ass_unit_name',
													align : 'left'
												},{
													display : '资产ID',
													name : 'ass_id',
													hide : true
												}],
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
										},
										align : 'left',
										width : '200',
										totalSummary : {
											render : function(suminf, column,
													cell) {
												return '<div>合计</div>';
											},
											align : 'center'
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
											autocomplete : true
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
										width : 100
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
											return formatNumber(
													rowdata.advice_price  == null ? 0
															: rowdata.advice_price ,
													'${ass_05006}',
													1);
										},
										align : 'right',
										width : '90'
									},
									{
										display : '金额',
										name : 'sum_price',
										valueFieldID : 'sum_price',
										align : 'right',
										width : 200,
										render : function(item) {
											/* console.log(item) */
											item.sum_price = formatNumber(item.plan_amount
													* item.advice_price);
											return formatNumber(
													item.sum_price  == null ? 0
															: item.sum_price ,
													'${ass_05005}',
													1);
										},
										width : '90',
										totalSummary : {
											render : function(suminf, column,
													cell) {
												$("#plan_money").val(suminf.sum);
												return '<div>'
														+ formatNumber(suminf.sum)
														+ '</div>';
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
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAssPlanDeptDetail.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							delayLoad : true,//初始化明细表
							isScroll : true,
							rownumbers : true,
							selectRowButtonOnly : true, //heightDiff: -10,
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
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
                                 ass_id = data.ass_id.split("@")[0];
								
								checked&& ajaxJsonObjectByUrl(
												"queryAssPlanResource.do?isCheck=false",
												data,
												function(responseData) {
													if (responseData.Rows.length!= ""
															/* || responseData.Rows.length!= null */ || responseData.Rows.length!= 0) {
														var itemDataLength = gridItem.getData().length;//获取行数
														for(var i = itemDataLength;i >= 0; i--){
															gridItem.deleteRow(i);//删除行
														}
														/* console.log(itemDataLength); */
														for (var i = 0; i< responseData.Rows.length; i++) {
															//回充数据 
															gridItem.addRow({
																 source_code : responseData.Rows[i].source_code, 
																		source_name :responseData.Rows[i].source_code+" "+ responseData.Rows[i].source_name,
																		assresource_id : responseData.Rows[i].ass_name,
																		resource_price:responseData.Rows[i].price,
																	});
														} 

													

													}

												});

							}

						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function loadHeadItem() {
		gridItem = $("#acceptItemGrid").ligerGrid({
			columns : [ {
				display : '资产编码',
				name : 'assresource_id',
				align : 'center',
				width : 80
			}, {
				display : '资金来源',
				name : 'source_code',
				textField : 'source_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../querySourceDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				}
			},{
				display : '金额',
				name : 'resource_price',
				align : 'center',
				width : 90,
				render : function(rowdata, rowindex,
						value) {
					 return formatNumber(
								rowdata.resource_price == null ? 0
										: rowdata.resource_price,
								'${ass_05005}',
								1);
				}
						,
				editor : {
					type : 'float'
				}
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
			onBeforeSubmitEdit : f_onBeforeSubmitEditItem,
			onAfterEdit : f_onAfterEditItem,
			isScroll : true,
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			isAddRow : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '保存',
					id : 'saveItem',
					click : saveItem,
					icon : 'save'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'deleteItem',
					click : deleteItem,
					icon : 'delete'
				} ]
			}
		});

		gridManagerItem = $("#acceptItemGrid").ligerGetGridManager();
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
					ass_spec : data.ass_spec,
					ass_brand : data.ass_brand,
					plan_id : data.plan_id
				/* source_id:data.source_id,
				source_name:data.source_name */
				});

			}
		}
		return true;
	}
	function saveItem() {
		gridManagerItem.endEdit();
		var source_id;
		var source_code;	
		var dataItem;
		var dataprice=gridManager.getCheckedRows();
		var sum_price=0;
		var data = gridManager.getCheckedRows();
		var griddata = gridManagerItem.getCheckedRows();
		var gridManagerdata = gridManagerItem.getData();
		/*console.log(data[0].plan_id);*/
	  	if (data.length == 0 || data.length > 1 ) {
			$.ligerDialog.warn('选择单个计划操作');
			return false;
		} else {
			var ass_id = 0;
			var accept_id = 0;
			var accept_detail_id = 0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选计划未保存');
					flag = false;
				}
				ass_id = this.ass_id;

			});
			var ass_id = 0;
			var ass_no=0;
			var plan_id = 0;
			var plan_detail_id = 0;
			var plan_no=0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_no=this.ass_no;
				plan_id = this.plan_id;
				plan_detail_id = this.plan_detail_id;
				plan_no=this.plan_no;
			});
			for(var i=0;i<gridManagerdata.length;i++){
				if(gridManagerdata[i].resource_price!=undefined){
					sum_price+=gridManagerdata[i].resource_price;
					
				}
				
			}
			if(sum_price>dataprice[0].sum_price){
				$.ligerDialog.warn("资金来源金额不等于申请金额");
				flag = false;
				
			}
			if (flag) {
				 var dataItem = gridManagerItem.getData();
				if (validateGridItem()) {
					ajaxJsonObjectByUrl("saveResourceItem.do?isCheck=false", {
						dataItem : JSON.stringify(dataItem), plan_id : data[0].plan_id,
						 plan_no : data[0].plan_no,plan_detail_id:data[0].plan_detail_id,ass_id:data[0].ass_id,ass_no:data[0].ass_no,
					}, function(responseData) {
						if (responseData.state == "true") {
							queryItem(accept_id, accept_detail_id);
						}
					});
				}
			}
		}
	}
	function validateGridItem() {
		var data = gridManagerItem.getData();
		var dataprice=gridManager.getCheckedRows();
		var sum_price=0;
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.source_name)) {
				var key = v.source_name;
				var value = "第" + (i + 1) + "行";
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = "1";
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		for(var i=0;i<data.length;i++){
			if(data[i].resource_price != undefined ){
			sum_price+=data[i].resource_price;
			}
		}
		if(sum_price>dataprice[0].sum_price){
			$.ligerDialog.warn("资金来源金额大于申请金额");
			return false;
			
		}
		if (data.length == 0) {
			$.ligerDialog.warn("无数据保存");
			return false;
		}
		return true;
	}
	function deleteItem() {

		var data = gridManagerItem.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						if (isnull(this.group_id)) {
							gridManagerItem.deleteSelectedRow();
						} else {
							ParamVo.push(this.group_id + "@" + this.hos_id
									+ "@" + this.copy_code + "@"
									+ this.ass_card_no + "@" + this.source_id);
						}
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {

					ajaxJsonObjectByUrl("deleteAssResource.do", {
						ParamVo : ParamVo.toString(),
						ass_nature : '${ass_nature}'
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEditItem(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEditItem(e) {
		return true;
	}

	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
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

	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				gridManager.deleteSelectedRow();
				return;
			}
		}
	}

	function loadDict() {
		//字典下拉框
		$("#plan_no").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		$("#plan_money").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		$("#plan_money").val(0);
		//默认年
		autodate("#plan_year", "YYYY");

		//科室编码
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null);
		
		//获取当前用户科室默认值
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			var id = data == null || data == "" ? null : data[0].id;
			var text = data == null || data == "" ? null : data[0].text;
			liger.get("dept_id").setValue(id);
			liger.get("dept_id").setText(text);
		},false);
		
		//制单人
		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, null, null,'${user_id}');
		$("#create_emp").ligerComboBox({
			width : 160
		});
		//审核人
		autocomplete("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		$("#check_emp").ligerComboBox({
			width : 160
		});
		//是否追加计划
		/* $("#is_add").ligerComboBox({
			width : 160
		});
		$("#buy_code").ligerComboBox({
			width : 160
		}); */
		$('#buy_code').ligerComboBox({
			data:[{id:0,text:'自主采购'},{id:1,text:'集中采购'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		$('#is_add').ligerComboBox({
			data:[{id:0,text:'否'},{id:1,text:'是'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
		//制单日期
		$("#create_date").ligerTextBox({
			width : 160
		});
		//购置月份
		$("#plan_year").ligerTextBox({
			width : 160
		});
		$("#plan_month").ligerTextBox({
			width : 160
		});
		$("#plan_no").ligerTextBox({
			width : 160
		});
		$("#plan_money").ligerTextBox({
			width : 160
		})
		autodate("#create_date");

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}

	function isItem_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			gridItem.addRow();
		}, 1000);
	}

	function this_close() {
		frameElement.dialog.close();
	}
	function loadRows() {
		is_addRow();
		isItem_addRow();
	}
</script>

</head>

<body style="padding: 0px;" onload="loadRows()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="plan_id" type="text" id="plan_id" />
	<input type="hidden"  name="source_id"  type="text" id="source_id"  />
	<form >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" width="80"><b><font
					color="red">*</font></b>计划单号：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_no"
				type="text" id="plan_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>制&nbsp;&nbsp;单&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_emp" type="text" id="create_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>

			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>追加计划：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="is_add"
				name="is_add">
					<option value="0">否</option>
					<option value="1">是</option>
			</select> -->
			<input  name="is_add" type="text" id="is_add"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>采购方式：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="buy_code"
				name="buy_code">
					<option value="0">自主采购</option>
					<option value="1">集中采购</option>
			</select> -->
			<input  name="buy_code" type="text" id="buy_code"/>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td">计划金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_money" type="text" id="plan_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><textarea
					rows="3" cols="100" name="note" id="note" style="border-color: #aecaf0;"></textarea></td>
			<td align="left"></td>
		</tr>

	</table>
	</form>
	<div id="layout1">
		<div position="center" title="计划列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="资金来源列表">
			<div>
				<div id="acceptItemGrid"></div>
			</div>
		</div>
	</div>
</body>
</html>
