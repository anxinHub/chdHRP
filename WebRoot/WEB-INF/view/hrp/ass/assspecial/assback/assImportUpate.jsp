<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager = null;
	var cardgrid;
	var cardgridManager = null;
	var userUpdateStr;
	var editor;
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '400',
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
		loadHotkeys();
		query();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('saveDetail');
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('buildCards');
			toobarmanage.setDisabled('delete');
			toobarmanageItem =cardgridManager.toolbarManager;
			toobarmanageItem.setDisabled('buildCard');
			toobarmanageItem.setDisabled('buildCards');
			toobarmanageItem.setDisabled('delete');
		}
		$("#ass_in_no").ligerTextBox({
			width : 160
		});
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#proj_id").ligerTextBox({
			width : 160
		});
		$("#purc_emp").ligerTextBox({
			width : 160
		});
		$("#ass_purpose").ligerTextBox({
			width : 160
		});
		$("#in_money").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#invoice_no").ligerTextBox({
			width : 160
		});
		$("#ass_in_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#in_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		
		$("#ven_id").change(function(){
			
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../../queryAssNoDictTable.do?isCheck=false&ass_naturs=02&ven_id='+ven_id+'&ven_no='+ven_no;
		});
	});

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if (liger.get("purc_emp").getValue() == "") {
			$.ligerDialog.error('采购员不能为空');
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
			ass_in_no : $("#ass_in_no").val() == "" ? '0' : $("#ass_in_no").val(),
			store_id : liger.get("store_id").getValue().split("@")[0],
			store_no : liger.get("store_id").getValue().split("@")[1],
			dept_id : liger.get("dept_id").getValue().split("@")[0],
			dept_no : liger.get("dept_id").getValue().split("@")[1],
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			proj_id : liger.get("proj_id").getValue().split("@")[0],
			proj_no : liger.get("proj_id").getValue().split("@")[1],
			purc_emp : liger.get("purc_emp").getValue(),
			invoice_no:$("#invoice_no").val(),
			bus_type_code : '01',
			note : $("#note").val(),
			ass_purpose : liger.get("ass_purpose").getValue(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		 if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssInMainSpecial.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#ass_in_no").val(responseData.ass_in_no);
					$("#in_money").val(responseData.in_money)
					query();
					queryCard("0","0@0");
					parentFrameUse().query();
				}
			});
		} 
	}
	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'ass_in_no',
			value : $("#ass_in_no").val() == "" ? "0" : $("#ass_in_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryCard(ass_in_no,ass_id) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'ass_in_no',
			value : ass_in_no
		});
		cardgrid.options.parms.push({
			name : 'ass_id',
			value : ass_id.split("@")[0]
		});
		cardgrid.options.parms.push({
			name : 'ass_no',
			value : ass_id.split("@")[1]
		});
		
		cardgrid.loadData(cardgrid.where);
	}
	
	

	function loadHead() {
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor = {
				type : 'select',
				valueField : 'ass_id_no',
				textField : 'ass_name',
				selectBoxWidth : 800,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
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
					} ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryAssNoDictTable.do?isCheck=false&ass_naturs=02&ven_id='+ven_id+'&ven_no='+ven_no,
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : true,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				}
			};
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '130'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : editor,
										render : function(rowdata, rowindex,
												value) {
											return rowdata.ass_name;
										},
										width : '130',
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
										align : 'left',
										width : '100',
										editor : {
											type : 'text'
										}
									},
									{
										display : '型号',
										name : 'ass_model',
										align : 'left',
										width : '100',
										editor : {
											type : 'text'
										}
									},
									{
										display : '品牌',
										name : 'ass_brand',
										align : 'left',
										editor : {
											type : 'text'
										},
										width : '100'
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../queryHosFacDict.do?isCheck=false',
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
										width : '190'
									},
									{
										display : '计量单位',
										name : 'unit_code',
										textField:'unit_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../queryHosUnitDict.do?isCheck=false',
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
										width : '100'
									},
									{
										display : '入库数量',
										name : 'in_amount',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '100'
									},
									{
										display : '单价',
										name : 'price',
										editor : {
											type : 'numberbox',
											precision: '${ass_04006}'
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.price, '${ass_05006}', 1);
										},
										width : '100'

									},
									{
										display : '金额',
										name : 'total_price',
										align : 'right',
										totalSummary : {
											render: function (suminf, column, cell)
					                        {
												$("#in_money").val(suminf.sum);
					                            return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
					                        }
										},
										render : function(item) {
											item.total_price = formatNumber(
													item.price
															* item.in_amount,
															'${ass_05005}', 1);
											return formatNumber(
													item.total_price, '${ass_05005}', 1);
										},
										width : '100'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '100'
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssInDetailSpecial.do',
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
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_id = data.ass_id;
								if(ass_id == null || ass_id == ""){
									ass_id = "0@0"
								}
								var ass_in_no = data.ass_in_no;
								if(ass_in_no == null || ass_in_no == ""){
									ass_in_no = "0"
								}
								queryCard(ass_in_no,ass_id);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		cardgrid = $("#cardgrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '卡片编码',
								name : 'ass_card_no',
								align : 'left',
								render : function(rowdata, rowindex,
										value) {
									return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.ass_card_no  +"')>"+rowdata.ass_card_no+"</a>";
								}
							},
							{
								display : '状态',
								name : 'use_state_name',
								align : 'left'
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssInCardSpecial.do',
					usePager : false,
					width : '100%',
					height : '100%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ ]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
		
	}
	
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=02";
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	
	function printBarcode(){
		
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
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					fac_id : data.fac_id,
					fac_name : data.fac_name,
					unit_code:data.ass_unit, 
					unit_name:data.ass_unit_name,
					ass_brand:data.ass_brand
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
		gridManager.updateCell('total_price', e.record.price
				* e.record.in_amount, e.record);
		grid.updateTotalSummary();
	}
	function initCard(){
		
	}
	
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		var price=0;
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.in_amount)) {
					msg += "[入库数量]、";
				}
				if (isnull(v.price)) {
					msg += "[单价]";
				}
				//if (isnull(v.unit_code)) {  
				//str += "第"+(i+1)+"[计量单位]、";  
				//is_flag = false;
				//} 
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
		for(var i=0;i<data.length;i++){
			if(data[i].price!=undefined){
				price+=data[i].price;
				
			}
			
		}
		if(price<0){
			$.ligerDialog.warn("入库单价不能为负数");
			return false;
			
		}
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

	
	
	
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#store_id", "../../queryHosStoreDict.do?naturs_code=02&isCheck=false",
				"id", "text", true, true, null, null, null, "300");
		
		autocomplete("#purc_emp",
				"../../queryMatStockEmp.do?isCheck=false&", "id",
				"text", true, true, null, null);
		
		$("#store_id").change(function(){
			
			var store_id = liger.get("store_id").getValue().split("@")[0];

			if (store_id == null || store_id == "") {
				store_id = "";
			}
			liger.get("purc_emp").setValue("");
			liger.get("purc_emp").setText("");
			autocomplete("#purc_emp",
					"../../queryMatStockEmp.do?isCheck=false&store_id="+store_id, "id",
					"text", true, true, null, true);
		});
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, null, null, "400");
		autocomplete("#dept_id", "../../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null,null);
		autocomplete("#ass_purpose", "../../queryAssUsageDict.do?isCheck=false",
				"id", "text", true, true, null, null);
		autocomplete("#proj_id", "../../queryAssProjDict.do?isCheck=false","id", "text",true,true,null,false,null,"400");
		
		liger.get("purc_emp").setValue("${purc_emp}");
		liger.get("purc_emp").setText("${purc_emp_name}");
		
		liger.get("store_id").setValue("${store_id}@${store_no}");
		liger.get("store_id").setText("${store_code} ${store_name}");
		
		liger.get("ven_id").setValue("${ven_id}@${ven_no}");
		liger.get("ven_id").setText("${ven_code} ${ven_name}");
		
		liger.get("dept_id").setValue("${dept_id}@${dept_no}");
		liger.get("dept_id").setText("${dept_code} ${dept_name}");
		
		liger.get("ass_purpose").setValue("${ass_purpose}");
		liger.get("ass_purpose").setText("${ass_purpose} ${ass_purpose_name}");
		
		liger.get("proj_id").setValue("${proj_id}@${proj_no}");
		liger.get("proj_id").setText("${proj_code} ${proj_name}");
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>入库单号：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_in_no" value="${ass_in_no }"
				type="text" id="ass_in_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>采&nbsp;&nbsp;购&nbsp;&nbsp;员：</td>
			<td align="left" class="l-table-edit-td"><input name="purc_emp"
				type="text" id="purc_emp" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_purpose" type="text" id="ass_purpose" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">领用科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="invoice_no" type="text" id="invoice_no" ltype="text" value="${invoice_no}" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入库金额：</td>
			<td align="left" class="l-table-edit-td"><input name="in_money" value="${in_money }"
				type="text" id="in_money" /></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note" style="border-color: #aecaf0;">${note }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="layout1">
		<div position="center" title="资产列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="卡片列表">
			<div>
				<div id="cardgrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
