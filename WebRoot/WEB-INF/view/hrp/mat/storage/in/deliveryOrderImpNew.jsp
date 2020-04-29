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
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
		query();
		$("#sup_code").change( function () {
			query();
			loadHead();
		});
		
	});
	//查询
	function query() {
		if (liger.get("is_dir").getValue() == 1
				&& !liger.get("app_dept").getValue()) {
			$.ligerDialog.warn('请选择定向科室');
			return false;
		}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'begin_order_date',
			value : $("#begin_order_date").val()
		});
		grid.options.parms.push({
			name : 'end_order_date',
			value : $("#end_order_date").val()
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get(
			"sup_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'sup_no',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get(
			"sup_code").getValue().split(",")[1]
		});
		grid.options.parms.push({
			name : 'delivery_no',
			value : $("#delivery_no").val()
		});
		grid.options.parms.push({
			name : 'inv_code',
			value : $("#inv_code").val()
		});
		grid.options.parms.push({
			name : 'is_dir',
			value : liger.get("is_dir").getValue()
		});
		grid.options.parms.push({
			name : 'pur_type',
			value : liger.get("pur_type").getValue()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("app_dept").getValue() == null ? "" : liger.get(
					"app_dept").getValue().split(".")[0]
		});
		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("app_dept").getValue() == null ? "" : liger.get(
					"app_dept").getValue().split(".")[1]
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
											display : '材料变更号',name : 'inv_no',align : 'left',hide : true
										},
										{
											display : '订单ID',name : 'delivery_id',align : 'left',hide : true
										},
										{
											display : '订单号',name : 'delivery_no',align : 'left',width : '120',
//	 										render : function(rowdata, rowindex,value) {
//	 											return '<a href=javascript:update_open("'
//	 													+ rowdata.group_id
//	 													+ ','
//	 													+ rowdata.hos_id
//	 													+ ','
//	 													+ rowdata.copy_code
//	 													+ ','
//	 													+ rowdata.delivery_id
//	 													+ '")>'
//	 													+ rowdata.delivery_no
//	 													+ '</a>';
//	 										}
										},
										{
											display : '材料编码',name : 'inv_code',align : 'left',width : '120'
										},
										{
											display : '材料名称',name : 'inv_name',align : 'left',width : '220'
										},
										{
											display : '规格型号',name : 'inv_model',width : 120,align : 'left'
										},
										{
											display : '计量单位',name : 'unit_name',width : 80,align : 'left'
										},
										{
											display : '送货单数量',name : 'delivery_amount',width : 90,type : 'float',align : 'right',
											totalSummary : {
												align : 'right',
												render : function(suminf, column,cell) {
													return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
												}
											}
										},
										{
											display : '已入库数量(E)',name : 'already_amount',width : 90,type : 'float',align : 'right',
											totalSummary : {
												align : 'right',
												render : function(suminf, column,cell) {
													return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
												}
											}
										},
										{
											display : '入库数量(E)',name : 'amount',width : 90,type : 'float',align : 'right',
											editor : {type : 'float',},
											totalSummary : {
												align : 'right',
												render : function(suminf, column,cell) {
													return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
												}
											}
										},
										{
											display : '单价(E)',name : 'price',width : 90,align : 'right',
											render : function(rowdata, rowindex,value) {
												rowdata.price = value == null ? ""
														: formatNumber(value,'${p04006 }',0);
												return value == null ? ""
														: formatNumber(value,'${p04006 }',1);
											}
										},
										{
											display : '金额(E)',name : 'amount_money',width : 90,align : 'right',
											render : function(rowdata, rowindex,value) {
												rowdata.amount_money = value == null ? ""
														: formatNumber(value,'${p04005 }',0);
												return value == null ? ""
														: formatNumber(value,'${p04005 }',1);
											},
											totalSummary : {
												align : 'right',
												render : function(suminf, column,cell) {
													return '<div>'+ formatNumber(suminf.sum == null ? 0	: suminf.sum,'${p04005 }',1) + '</div>';
												}
											}

										}, {
											display : '生产批号(E)',name : 'batch_no',width : 80,align : 'left',editor : {type : 'text',}
										}, {
											display : '生产日期(E)',name : 'fac_date',type : 'date',align : 'left',format : 'yyyy-MM-dd',width : 90,
											editor : {type : 'date',},
										},{
											display : '有效日期(E)',name : 'inva_date',type : 'date',align : 'left',format : 'yyyy-MM-dd',width : 90,
											editor : {type : 'date',},
										}, {
											display : '灭菌日期(E)',name : 'disinfect_date',type : 'date',align : 'left',format : 'yyyy-MM-dd',width : 90,
											editor : {type : 'date',},
										}, {
											display : '条形码(E)',name : 'sn',width : 80,align : 'left',
											editor : {type : 'text',}
										}, {
											display : '生产厂商',name : 'fac_name',width : 180,align : 'left'
										}, {
											display : '备注(E)',name : 'note',width : 80,align : 'left',editor : {type : 'text',}
										} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : true,
							url : '../../../sup/supdeliverydetail/queryDetail.do?isCheck=false',
							width : '100%',
							height : '90%',
							checkbox : true,
							rownumbers : true,
							isAddRow: false,
							enabledEdit : true,
							delayLoad : true,//初始化不加载，默认false
							selectRowButtonOnly : true,//heightDiff: -10,
							 onAfterEdit: f_onAfterEdit,
							groupColumnName : 'inv_name',
							groupColumnDisplay : '材料名称         ',
							toolbar : {
								items : []
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		grid.toggleCol("inv_no", false);
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', this_close);
	}

	//修改
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "delivery_id=" + vo[3];
		$.ligerDialog.open({
			title : '订单修改',
			height : 500,
			width : 1000,
			url : '../../sup/suporder/supOrderUpdatePage.do?isCheck=false&'
					+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			top : 1
		});
	}

	function save() {
		var data = gridManager.getCheckedRows();
		 if (data.length == 0) {
			$.ligerDialog.warn('请选择行');
			return false;
		} else if (!$("#sup_code").val()) {
			$.ligerDialog.warn('请选择供货单位！');
			return false;
		}else { 
		if (validateGrid()) {
		
			var formPara = {
				in_no : $("#in_no").val(),
				bus_type_code: liger.get("bus_type_code").getValue(),
				store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				in_date: $("#in_date").val(),
				stocker: liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
				sup_id : liger.get("sup_code").getValue().split(",")[0],
				sup_no : liger.get("sup_code").getValue().split(",")[1],
				stock_type_code: liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
				//is_dir : liger.get("is_dir").getValue() == null ? "0" : liger.get("is_dir").getValue(),
				//bill_type : liger.get("pur_type").getValue() == null ? "": liger.get("pur_type").getValue(),
				//delivery_address : $("#delivery_address").val(),
				detailData : JSON.stringify(gridManager.getCheckedRows())
			};
			$.ligerDialog.confirm('确定生成入库单?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("../../../sup/supdeliverymain/addDeliveryGenerate.do?isCheck=false",
							formPara, function(responseData) {
						      if(responseData.state_mag==1){
						    	  $.ligerDialog.warn('请输入入库数量');
						      }else{
						    	  parentFrameUse().query();
							      parentFrameUse().update_open(responseData.update_para);
									this_close();
						      }
						      
							});
				}
			});
		}
		}
 	} 

	function validateGrid() {
		//主表
		if (!liger.get("bus_type_code").getValue()) {
			$.ligerDialog.warn("业务类型不能为空");
			return false;
		}
		if (!liger.get("store_code").getValue()) {
			$.ligerDialog.warn("仓库不能为空");
			return false;
		}
		if (!$("#in_date").val()) {
			$.ligerDialog.warn("制单日期不能为空");
			return false;
		}
		if (liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2) {
			if (!liger.get("stocker").getValue()) {
				$.ligerDialog.warn("采购员不能为空");
				return false;
			}
			if (!liger.get("stock_type_code").getValue()) {
				$.ligerDialog.warn("采购类型不能为空");
				return false;
			}
			
		}
		return true;
	}
	
	function this_close() {
		frameElement.dialog.close();
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		
// 		$("#sup_code").ligerComboBox({
// 			width : 160,
// 			disabled : true,
// 			cancelable : false
// 		});
// 		liger.get("sup_code").setValue("${sup_id}");
// 		liger.get("sup_code").setText("${sup_text}");
		$("#begin_order_date").ligerTextBox({
			width : 100
		});
		autodate("#begin_order_date", "yyyy-mm-dd", "month_first");
		$("#end_order_date").ligerTextBox({
			width : 100
		});
		autodate("#end_order_date", "yyyy-mm-dd", "month_last");
		$("#delivery_no").ligerTextBox({
			width : 160
		});
		$("#inv_code").ligerTextBox({
			width : 160
		});
		autoCompleteByData("#pur_type", matOrderMain_orderType.Rows, "id",
				"text", true, true, "", true);//订单类型
		liger.get("pur_type").setValue(1);
		autocomplete("#app_dept", "../../queryHosDeptByPerm.do?isCheck=false",
				"id", "text", true, true, {is_last : 1});

		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,
				'', true, false, '160');//是否定向

		autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, { sel_flag: 'in' }, true);
		autocompleteAsync("#store_code", "../../queryMatStoreStocker.do?isCheck=false", "id", "text", true, true, {is_com : 0}, true);
		autocomplete("#stocker", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, "", false);
		var emp_id=	liger.get("store_code").getValue().split(",")[2];
		var emp_name=liger.get("store_code").getValue().split(",")[3];
		liger.get("stocker").setValue(emp_id);
		liger.get("stocker").setText(emp_name);
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, '', true);
		$("#in_date").ligerTextBox({ width: 160 });
		autodate("#in_date");//默认当前日期
		$("#bus_type_code").ligerTextBox({ width: 160 });
		$("#store_code").ligerTextBox({ width: 160 });
		$("#stocker").ligerTextBox({ width: 160 });
		$("#stock_type_code").ligerTextBox({ width: 160 });
				
		//格式化按钮
		$("#query").ligerButton({
			click : query,
			width : 90
		});
		//格式化按钮
		$("#save").ligerButton({
			click : save,
			width : 90
		});
		$("#close").ligerButton({
			click : this_close,
			width : 90
		});
	}
	
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		if (e.value != "") {
			if (e.column.name == "inv_id") {
				//判断是否为自动有效日期
				if ('${p04009 }' != 0) {
					grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
				}
			} else if (e.column.name == "amount") {
				//自动计算金额
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if (e.record.sell_price != undefined && e.record.sell_price != "") {
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算批发金额
				if (e.record.sale_price != undefined && e.record.sale_price != "") {
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
				}
				//自动计算包装件数
				if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
					grid.updateCell('num', e.record.num_exchange ? e.value / e.record.num_exchange : 0, e.rowindex);
				}
			} else if (e.column.name == "price") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
				//计算包装单价
				if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
					grid.updateCell('pack_price', e.value * e.record.num_exchange, e.rowindex);
				}
			} else if (e.column.name == "num_exchange") {
				//自动计算包装件数
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('num', e.value ? e.record.amount / e.value : 0, e.rowindex);
				}
				//自动包装单价
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('pack_price', e.record.price * e.value, e.rowindex);
				}
			} else if (e.column.name == "num") {
				//自动计算数量与金额
				if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
					grid.updateCell('amount', e.value * e.record.num_exchange, e.rowindex);
					if (e.record.price != undefined && e.record.price != "") {
						grid.updateCell('amount_money', e.record.amount * e.record.price, e.rowindex);
					}
					if (e.record.sell_price != undefined && e.record.sell_price != "") {
						grid.updateCell('sell_money', e.record.amount * e.record.sell_price, e.rowindex);
					}
					if (e.record.sale_money != undefined && e.record.sale_money != "") {
						grid.updateCell('sale_price', e.record.amount ? e.record.sale_money / e.record.amount : 0, e.rowindex);
					} else if (e.record.sale_price != undefined && e.record.sale_price != "") {
						grid.updateCell('sale_money', e.record.amount * e.record.sale_price, e.rowindex);
					}
				}
			} else if (e.column.name == "sell_price") {
				//自动计算零售金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_price") {
				//自动计算批发金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_money") {
				//自动计算批发单价
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
			} else if (e.column.name == "amount_money") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
				//计算包装单价
				if (e.record.num_exchange != undefined && e.record.num_exchange != "") {
					grid.updateCell('pack_price', e.record.amount * e.record.num_exchange ? e.value / e.record.amount * e.record.num_exchange : 0, e.rowindex);
				}
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	
	function changeDir() {
		if (liger.get("is_dir").getValue() == '1') {
			$(".dept").attr("style", "visibility:true");
		} else {
			$(".dept").attr("style", "visibility:hidden");
		}
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">送货单日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left"><input class="Wdate" name="begin_order_date"
							id="begin_order_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="end_order_date" id="end_order_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td" width="10%"><span id="sup_code_span" style="color:red">*</span>供货单位：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input
				name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td">送货单类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="pur_type" type="text" id="pur_type" ltype="text" validate="{required:false,maxlength:100}" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">送货单号：</td>
			<td align="left" class="l-table-edit-td" width="20%"><input
				name="delivery_no" type="text" id="delivery_no" ltype="text" validate="{required:false,maxlength:100}" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否定向：</td>
			<td align="left" class="l-table-edit-td"><input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();" /></td>
			<td align="right" class="l-table-edit-td">定向科室：</td>
			<td align="left" class="l-table-edit-td"><input name="app_dept" type="text" id="app_dept" ltype="text" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">材料信息：</td>
			<td align="left" class="l-table-edit-td"><input name="inv_code"
				type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" /></td>
		</tr>
	</table>
	<hr />
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
		<td align="left" class="l-table-edit-td" style="display: none">
	<input name="in_no" type="text" id="in_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}"/>
</td>
				<td align="right" class="l-table-edit-td">
		<span style="color:red">*</span>制单日期：
</td>
<td align="left" class="l-table-edit-td">
	<input class="Wdate" name="in_date" id="in_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span style="color:red">*</span>业务类型：
</td>
<td align="left" class="l-table-edit-td">
	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span style="color:red">*</span>仓库：
</td>
<td align="left" class="l-table-edit-td">
	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
</td>
		</tr>
		<tr>
		<td align="right" class="l-table-edit-td">
	<span id="stocker_span" style="color:red">*</span>采购员：
</td>
<td align="left" class="l-table-edit-td">
	<input name="stocker" type="text" id="stocker" ltype="text" required="true" validate="{required:true}" />
</td>
<td align="right" class="l-table-edit-td">
	<span id="stock_type_code_span" style="color:red">*</span>采购类型：
</td>
<td align="left" class="l-table-edit-td">
	<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
</td>
		</tr>	
	</table>
	</form>
	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
<!-- 		<table cellpadding="0" cellspacing="0" class="l-table-edit" -->
<!-- 			width="100%"> -->
<!-- 			<tr> -->
<!-- 				<td align="left" class="l-table-edit-td">送货地址：</td> -->
<!-- 				<td align="left" class="l-table-edit-td" colspan="10"><textarea -->
<!-- 						rows="3" cols="180" name="delivery_address" id="delivery_address"></textarea> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id="query" accessKey="Q"><b>查询订单（<u>Q</u>）
						</b>
					</button> &nbsp;&nbsp;
					<button id="save" accessKey="B"><b>生成入库单（<u>B</u>）
						</b>
					</button> &nbsp;&nbsp;
					<button id="close" accessKey="C"><b>关闭（<u>C</u>）
						</b>
					</button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
