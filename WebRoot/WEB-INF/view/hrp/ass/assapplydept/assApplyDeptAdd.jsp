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
	var apply_id;
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
		$("#apply_no").ligerTextBox({
			width : 160
		});
		$("#apply_year").ligerTextBox({
			width : 160
		});
		$("#apply_month").ligerTextBox({
			width : 160
		});
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#apply_emp").ligerTextBox({
			width : 160
		});
		$("#apply_date").ligerTextBox({
			width : 160
		});
		$("#create_emp").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#check_emp").ligerTextBox({
			width : 160
		});
		$("#check_date").ligerTextBox({
			width : 160
		});
		$("#is_add").ligerComboBox({
			width : 160
		});
		$("#apply_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#apply_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
	});
	function query(apply_id,source_id) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'apply_id',
			value : $("#apply_id").val() == "" ? "0" : $("#apply_id").val()
		});
		gridItem.options.parms.push({
			name : 'source_id',
			value : $("#source_id").val() == "" ? "1" : $("#source_id").val()
		});
		grid.loadData(grid.where);
		/* gridItem.loadData(gridItem.where); */
	}
	/* function queryItem(source_id, detail_id) {
		gridItem.options.parms = [];
		gridItem.options.newPage = 1;
		gridItem.options.parms.push({
			name : 'source_id',
			value : source_id== "" || source_id == null
			|| source_id == "undefined" ? 1 : source_id
		});
		gridItem.options.parms.push({
			name : 'detail_id',
			value : detail_id == "" || detail_id == null
					|| detail_id == "undefined" ? "0" : detail_id
		});
		gridItem.loadData(gridItem.where);
	} */
	function save() {
		gridManager.endEdit();
		if ($("#apply_year").val() == "") {
			$.ligerDialog.error('购置年度不能为空');
			return;
		}
		if ($("#apply_month").val() == "") {
			$.ligerDialog.error('购置月份不能为空');
			return;
		}
		if (liger.get("dept_id").getValue() == "") {
			$.ligerDialog.error('申请科室不能为空');
			return;
		}
		if (liger.get("apply_emp").getValue() == "") {
			$.ligerDialog.error('申请人不能为空');
			return;
		}
		if ($("#apply_date").val() == "") {
			$.ligerDialog.error('申请日期不能为空');
			return;
		}
		if (liger.get("is_add").getValue() == "") {
			$.ligerDialog.error('是否追加申请不能为空');
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
				        apply_id : $("#apply_id").val() == "" ? "0" : $("#apply_id").val(),
						apply_no : $("#apply_no").val() == "" ? "0" : $("#apply_no").val(),
						apply_year : $("#apply_year").val(),
						apply_month : $("#apply_month").val(),
						dept_id : liger.get("dept_id").getValue(),
						apply_emp : liger.get("apply_emp").getValue(),
						apply_date : $("#apply_date").val(),
						summary : $("#summary").val(),
						is_add : liger.get("is_add").getValue(),
		
            source_id : $("#source_id").val() == "" ? "1" : $("#source_id").val(),
			sum_price : grid.getTotalInfoSum('sum_price'),
			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssApplyDept.do",
					formPara, function(responseData) {
						if (responseData.state == "true") {
							parentFrameUse().query();
							$("#apply_id").val(responseData.apply_id);
							$("#apply_no").val(responseData.apply_no);
							$("#apply_money").val(responseData.apply_money);
							query(responseData.apply_id);
							grid.loadData();
                            is_addRow();
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
		$.each(data,function(i, v) {
			if (!isnull(v.ass_code)) {
					var key = v.ass_code;
					var value = "第" + (i + 1) + "行";
					
					if (isnull(v.apply_amount)) {
						msg += "[申请数量]、";
					}
					if (isnull(v.advice_price)) {
						msg += "[建议单价]、";
					}
					if (isnull(v.budg_purchase_id)) {
						msg += "[采购预算]、";
					}
					if (isnull(v.naturs_code)) {
						msg += "[设备类组]、";
					}
					if (isnull(v.budg_price)) {
						msg += "[预算单价]、";
					}
					if (isnull(v.need_date)) {
						msg += "[要求到货日期]、";
					}
					if (isnull(v.budg_date)) {
						msg += "[预计到货日期]、";
					}
					if (isnull(v.purchase_type_id)) {
						msg += "[申购类别]、";
					}
					
					if (msg != "") {
						msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
					}
					if (isnull(targetMap.get(key))) {
						targetMap.put(key, value);
					} else {
						msg = targetMap.get(key) + "与" + value + "重复！\n\r",
						value;
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
										display : '设备项',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										width : '150',
										editor : {
											type : 'select',
											valueField : 'ass_id_no',
											textField : 'ass_name',
											selectBoxWidth : 800,
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
													display : '规格',
													name : 'ass_spec',
													align : 'left'
												}, {
													display : '型号',
													name : 'ass_model',
													align : 'left'
												},{
													display : '计量单位',
													name : 'ass_unit_name',
													align : 'left'
												},{
													display : '生产厂商',
													name : 'fac_name',
													align : 'left'
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
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }

									},
									{display : '采购预算',	name : 'budg_purchase_id',align : 'left',	textField : 'budg_purchase_name',	width : '150',
										editor : {
											type : 'select',
											valueField : 'ass_item',
											textField : 'ass_item_name',
											selectBoxWidth : 800,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '项目编码',
													name : 'ass_item',
													align : 'left',
													width:'100'
												}, {
													display : '项目名称',
													name : 'ass_item_name',
													align : 'left',
													width:'120'
												}, {
													display : '预算单价',
													name : 'budg_price',
													type : 'int',
													align : 'right',
													width:'90',
													render : function(rowdata, rowindex,
															value) {
														 return formatNumber(
																	rowdata.budg_price == null ? 0
																			: rowdata.budg_price,
																	'${ass_05006}',
																	1);
													}
												}, {
													display : '预算数量',
													name : 'budg_amount',
													align : 'left',
													width:'90'
												}, {
													display : '预计费用',
													name : 'budg_money',
													type : 'int',
													align : 'right',
													width:'90',
													render : function(rowdata, rowindex,
															value) {
														 return formatNumber(
																	rowdata.budg_money == null ? 0
																			: rowdata.budg_money,
																	'${ass_05006}',
																	1);
													}
												}, {
													display : '预算科室',
													name : 'budg_dept_name',
													align : 'left',
													width:'100'
												}],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail_budg,
												url : '../queryAssBudgTable.do?isCheck=false',
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
										}
									},
									{
										display : '项目名称',
										name : 'proj_name',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '150',
									},
									{
										display : '使用科室',
										name : 'emp_dept_id',
										textField : 'emp_dept_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryDeptDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true
										},
										align : 'left',
										width : '150',
									},
									{
										display : '申购类别',
										name : 'purchase_type_id',
										textField : 'purchase_type_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryBuyDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true
										},
										align : 'left',
										width : '150',
									},
									{
										display : '设备评估信息',
										name : 'ass_info',
										align : 'center',
										width : '90',
										render : function(rowdata, rowindex,value) {
											if(rowdata){
												return "<a href=javascript:openAssInfo("+rowindex+")><b>评估信息</b></font></a>";
											}
										}
									},
									{
										display : '设备类组',
										name : 'naturs_code',
										textField : 'naturs_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryAssNaturs.do?isCheck=false',
											keySupport : true,
											autocomplete : true
										},
										align : 'left',
										width : '150'
									},
									{
										display : '设备类型',
										name : 'ass_type_id',
										textField : 'ass_type_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryAssTypeDict.do?isCheck=false',
											keySupport : true,
											autocomplete : true
										},
										align : 'left',
										width : '150',
									},
									{
										display : '型号',
										name : 'ass_model',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
									},
									{
										display : '规格',
										name : 'ass_spec',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
									},
									{
										display : '品牌',
										name : 'ass_brand',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
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
										width : '200',
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
										width : '90'
									},
									{
										display : '建议单价',
										name : 'advice_price',
										editor : {
											type : 'numberbox',
											/* precision : 2 */
										},
										align : 'right',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.advice_price == null ? 0
																: rowdata.advice_price,
														'${ass_05006}',
														1);
										}
													
									},
									{
										display : '金额',
										name : 'sum_price',
										width : 200,
										align : 'right',
										render : function(item) {
											item.sum_price = formatNumber(
													item.apply_amount
															* item.advice_price
													);
											return formatNumber(
													item.sum_price == null ? 0
															: item.sum_price,
													'${ass_05005}',
													1
													);
										},
										width : '90',
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                        	$("#apply_money").val(suminf.sum);
					                            return '<div>' + formatNumber(suminf.sum) + '</div>';
					                        }
					                    }
									},{
										display : '预算单价',
										name : 'budg_price',
										type : 'int',
										align : 'right',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.budg_price == null ? 0
																: rowdata.budg_price,
														'${ass_05006}',
														1);
										}
									}, {
										display : '预算数量',
										name : 'budg_amount',
										align : 'left',
										width : '90'
									}, {
										display : '预计费用',
										name : 'budg_money',
										type : 'int',
										align : 'right',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.budg_money == null ? 0
																: rowdata.budg_money,
														'${ass_05006}',
														1);
										}
									},
									{
										display : '要求到货日期',
										name : 'need_date',
										type : 'date',
										format : 'yyyy-MM-dd',
										editor : {
											type : 'date',showSelectBox:false
										},
										align : 'left',
										width : '90'
									},
									{
										display : '预计到货日期',
										name : 'budg_date',
										type : 'date',
										format : 'yyyy-MM-dd',
										editor : {
											type : 'date',showSelectBox:false
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
									},
									{
										display : '详细用途',
										name : 'usage_note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '160',
									}, 
									{
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
									} , 
									{
										display : '功能要求',
										name : 'features_req',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
									}  , 
									{
										display : '明细ID',
										name : 'detail_id',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '90',
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAssApplyDeptDetail.do',
							width : '100%',
							height : '99%',
							checkbox : false,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
							onAfterEdit : f_onAfterEdit,
							isScroll : true,
							checkbox : true,
							//isAddRow:false, //禁止自动添加行
							rownumbers : true,
							delayLoad : true,
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
								}, {line : true}, 
								{
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
									} ]
							},onCheckRow : function(checked, data, rowid, rowdata) {

								ass_id = data.ass_id.split("@")[0];
								loadHeadItem();
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
		grid.toggleCol('detail_id', false);//"City1"为要隐藏的列的id；false为隐藏，如果为true，则为显示
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function loadHeadItem() {
		gridItem = $("#acceptItemGrid").ligerGrid({
			columns : [ {
				display : '资产名称',
				name : 'assresource_id',
				align : 'center',
				width : 90
			}, {
				display : '资金来源',
				name : 'source_code',
				textField : 'source_name',
				width : 90,
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
				},
							
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
	
	//打开评估信息页面
	function openAssInfo(rowindex){
		parent.$.ligerDialog.open({
			title: '设备评估信息',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/assapplydept/assApplyProofPage.do?isCheck=false&rowindex='+rowindex,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name //用于parent弹出层调用本页面的方法或变量
		});
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
					ass_brand: data.ass_brand,
					naturs_code : data.naturs_code,
					naturs_name : data.naturs_name,
					ass_type_id : data.ass_type_id,
					ass_type_name : data.ass_type_name
				});

			}
		}
		return true;
	}
	//选中回充数据
	function f_onSelectRow_detail_budg(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "budg_purchase_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					proj_name : data.ass_item_name,
					budg_purchase_id : data.ass_item,
					budg_purchase_name : data.ass_item_name,
					budg_price : data.budg_price,
					budg_amount : data.budg_amount,
					budg_money : data.budg_money
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
			var apply_id = 0;
			var detail_id = 0;
			var apply_no=0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_no=this.ass_no;
				apply_id = this.apply_id;
				detail_id = this.detail_id;
				apply_no=this.apply_no;
			});
			for(var i=0;i<gridManagerdata.length;i++){
				if(gridManagerdata[i].resource_price != undefined ){
					sum_price+=gridManagerdata[i].resource_price;
					
				}
				
			}
			if(parseInt(sum_price)>parseInt(dataprice[0].sum_price)){
				$.ligerDialog.warn("资金来源金额大于申请金额");
				flag = false;
				
			}
			if (flag) {
				gridManagerItem.endEdit();
				 var dataItem = gridManagerItem.getData();
				if (validateGridItem()) {
					ajaxJsonObjectByUrl("saveResourceItem.do?isCheck=false", {
						dataItem : JSON.stringify(dataItem), apply_id : data[0].apply_id,
						apply_no : data[0].apply_no,detail_id:data[0].detail_id,ass_id:data[0].ass_id,ass_no:data[0].ass_no,
					}, function(responseData) {
						if (responseData.state == "true") {
							//queryItem(apply_id,source_id);
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
		 $('#is_add').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 160
		});
		//默认年
		autodate("#apply_year", "YYYY");
		//默认月
		autodate("#apply_month", "mm");
		
		autodate("#apply_date");
		
		autocomplete("#apply_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, null, null,'${user_id}');
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, null, null);
		
		//获取当前用户科室默认值
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			var id = data == null || data == "" ? null : data[0].id;
			var text = data == null || data == "" ? null : data[0].text;
			liger.get("dept_id").setValue(id);
			liger.get("dept_id").setText(text);
		},false)

		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, null, null,true,true);
		autocomplete("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, null, null,true,true);

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

<body style="padding: 0px;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" name="apply_id" type="text" id="apply_id" />
	<input type="hidden"  name="source_id"  type="text" id="source_id"  />
	<from>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"
				width="100"><b><font color="red">*</font></b>申请单号：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_no"
				type="text" id="apply_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_year" type="text" id="apply_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>购置月份：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_month" type="text" id="apply_month" class="Wdate"
				
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>申请科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><!-- <b><font
					color="red">*</font></b> -->申&nbsp;&nbsp;请&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="apply_emp"
				type="text" id="apply_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;"><b><font
					color="red">*</font></b>申请日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_date" type="text" id="apply_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_money" type="text" id="apply_money" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>追加申请：</td>
			<td align="left" class="l-table-edit-td">
			<!-- <select id="is_add"
				name="is_add">
					<option value="0">否</option>
					<option value="1">是</option>
			</select> -->
			<input  name="is_add" type="text" id="is_add" /> 
			</td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><textarea
					rows="2" cols="80" name="summary" id="summary" style="border-color: #aecaf0;"></textarea></td>
			<td align="left"></td>
		</tr>
	</table>

	</from>
	<div id="layout1">
		<div position="center" title="购置申请计划">
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
